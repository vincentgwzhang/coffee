package tina.coffee.business;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tina.coffee.Verifier.MenuVerifier;
import tina.coffee.data.model.ImportProductEntity;
import tina.coffee.data.model.MenuCategoryEntity;
import tina.coffee.data.model.MenuItemEntity;
import tina.coffee.data.model.MenuItemLanguageEntity;
import tina.coffee.data.model.OrderEntity;
import tina.coffee.data.model.OrderItemEntity;
import tina.coffee.data.repository.ImportProductRepository;
import tina.coffee.data.repository.MenuCategoryRepository;
import tina.coffee.data.repository.MenuItemLanguageRepository;
import tina.coffee.data.repository.MenuItemRepository;
import tina.coffee.data.repository.OrderItemRepository;
import tina.coffee.dozer.DozerMapper;
import tina.coffee.rest.dto.MenuItemDTO;
import tina.coffee.system.exceptions.menuitem.MenuItemAssociateOrdersException;
import tina.coffee.system.exceptions.menuitem.MenuItemBusinessException;
import tina.coffee.system.exceptions.menuitem.MenuItemNotFoundException;
import tina.coffee.system.exceptions.menuitem.MenuItemUpdateException;
import tina.coffee.system.exceptions.menuitemlanguage.MenuItemLanguageBusinessException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static tina.coffee.system.config.SystemConstant.LONG_DATE_FORMAT;

@Service
public class MenuItemService {

    private final MenuItemRepository repository;
    private final MenuCategoryRepository menuCategoryRepository;
    private final MenuItemLanguageRepository menuItemLanguageRepository;
    private final OrderItemRepository orderItemRepository;
    private final ImportProductRepository importProductRepository;

    private final DozerMapper mapper;

    @Autowired
    public MenuItemService(MenuItemRepository repository,
                           MenuCategoryRepository menuCategoryRepository,
                           MenuItemLanguageRepository menuItemLanguageRepository,
                           OrderItemRepository orderItemRepository,
                           ImportProductRepository importProductRepository,
                           DozerMapper mapper) {
        this.repository = repository;
        this.menuCategoryRepository = menuCategoryRepository;
        this.menuItemLanguageRepository = menuItemLanguageRepository;
        this.orderItemRepository = orderItemRepository;
        this.importProductRepository = importProductRepository;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public List<MenuItemDTO> listAllMenuItems() {
        List<MenuItemEntity> entities = repository.findAll();
        return mapper.map(entities, MenuItemDTO.class);
    }

    @Transactional(readOnly = true)
    public List<MenuItemDTO> listAllMenuItemsByMenuCategory(Integer menuCategoryId) {
        MenuCategoryEntity menuCategoryEntity = menuCategoryRepository.findOne(menuCategoryId);
        List<MenuItemEntity> entities = repository.getMenuItemEntitiesByMenuCategoryEntity(menuCategoryEntity);
        return mapper.map(entities, MenuItemDTO.class);
    }

    @Transactional(readOnly = true)
    public MenuItemDTO getMenuItemById(Integer id) {
        Optional<MenuItemEntity> entity = repository.findByMiId(id);
        MenuVerifier.verifyIfMenuItemExistOrThrow(entity);
        return entity
                .map(en -> mapper.map(en, MenuItemDTO.class))
                .orElseThrow(MenuItemNotFoundException.newMenuItemNotFoundException());
    }

    @Transactional(readOnly = true)
    public MenuItemEntity getMenuItemEntityById(Integer id) {
        Optional<MenuItemEntity> entity = repository.findByMiId(id);
        MenuVerifier.verifyIfMenuItemExistOrThrow(entity);
        return entity.orElseThrow(MenuItemNotFoundException.newMenuItemNotFoundException());
    }

    @Transactional
    public void deleteMenuItemById(Integer id) {
        Optional<MenuItemEntity> entity = repository.findByMiId(id);
        MenuVerifier.verifyIfMenuItemExistOrThrow(entity);
        List<OrderItemEntity> orderItemEntities = orderItemRepository.findByMenuItem(entity.get());

        if(orderItemEntities!=null && !orderItemEntities.isEmpty()) {
            Set<OrderEntity> orderEntitySet =  orderItemEntities.stream().map(OrderItemEntity::getOrder).collect(Collectors.toSet());
            Optional<String> orderDetail = orderEntitySet.stream().map(OrderEntity::getStartTime).map(this::calanderToString).map(str-> str.concat(" |")).reduce(String::concat);
            throw new MenuItemAssociateOrdersException(orderDetail.get());
        }
        deleteMenuItemLanguageEntitiesByMenuItemEntity(entity.get());
        repository.delete(id);
    }

    @Transactional
    public void deleteMenuItemsByCollection(Set<Integer> ids) {
        ids.stream().forEach(this::deleteMenuItemById);
    }

    @Transactional
    public void deleteMenuItemByCategoryId(Integer categoryId) {
        Optional<MenuCategoryEntity> menuCategoryEntity = menuCategoryRepository.findByMcId(categoryId);
        MenuVerifier.verifyIfMenuCategoryExistOrThrow(menuCategoryEntity);
        if(menuCategoryEntity.isPresent()) {
            List<MenuItemEntity> menuItemEntities = repository.getMenuItemEntitiesByMenuCategoryEntity(menuCategoryEntity.get());
            menuItemEntities.stream().forEach(this::deleteMenuItemLanguageEntitiesByMenuItemEntity);
            repository.deleteByMenuCategoryEntity(menuCategoryEntity.get());
        }
    }

    @Transactional
    public MenuItemDTO addItem(MenuItemDTO menuItemDTO) {
        //Check if menu category exist
        Optional<MenuCategoryEntity> menuCategoryEntity = menuCategoryRepository.findByMcId(menuItemDTO.getMiMcId());
        MenuVerifier.verifyIfMenuCategoryExistOrThrow(menuCategoryEntity);

        verifiyDescriptionNotDuplicateOrThrow(menuItemDTO.getDescCN());
        verifiyDescriptionNotDuplicateOrThrow(menuItemDTO.getDescEN());
        verifiyDescriptionNotDuplicateOrThrow(menuItemDTO.getDescSP());

        MenuItemEntity itemEntity = mapper.map(menuItemDTO, MenuItemEntity.class);
        itemEntity.setMenuCategoryEntity(menuCategoryEntity.get());

        //for binding import product
        itemEntity = bindMenuItemWithImportProduct(itemEntity, menuItemDTO.getIpId());

        //save item in the end
        itemEntity = repository.save(itemEntity);

        //save language first
        List<MenuItemLanguageEntity> languages = new ArrayList<>(itemEntity.getLanguages());
        menuItemLanguageRepository.save(languages);
        return Optional.ofNullable(itemEntity).map(en -> mapper.map(en, MenuItemDTO.class)).orElseThrow(MenuItemBusinessException.newMenuItemBusinessException(MenuItemBusinessException.ERROR_NOT_CREATED));
    }

    @Transactional
    public MenuItemDTO updateItem(MenuItemDTO menuItemDTO) {
        //Check if menu category exist
        Optional<MenuCategoryEntity> menuCategoryEntity = menuCategoryRepository.findByMcId(menuItemDTO.getMiMcId());
        MenuVerifier.verifyIfMenuCategoryExistOrThrow(menuCategoryEntity);

        //check if language duplicate
        Optional<MenuItemEntity> entityFromDB = repository.findByMiId(menuItemDTO.getMiId());
        verifiyDescriptionNotDuplicateOrThrow(menuItemDTO.getDescCN(), entityFromDB.get());
        verifiyDescriptionNotDuplicateOrThrow(menuItemDTO.getDescEN(), entityFromDB.get());
        verifiyDescriptionNotDuplicateOrThrow(menuItemDTO.getDescSP(), entityFromDB.get());

        //delete language associated
        deleteMenuItemLanguageEntitiesByMenuItemEntity(entityFromDB.get());

        //create a new one by DTO mapping
        MenuItemEntity entityToDB = mapper.map(menuItemDTO, MenuItemEntity.class);
        entityToDB.setMenuCategoryEntity(menuCategoryEntity.get());

        //for binding import product
        entityToDB = bindMenuItemWithImportProduct(entityToDB, menuItemDTO.getIpId());

        //save language first
        List<MenuItemLanguageEntity> languages = new ArrayList<>(entityToDB.getLanguages());
        menuItemLanguageRepository.save(languages);

        //save item in the end
        entityToDB = repository.save(entityToDB);
        return Optional.ofNullable(entityToDB).map(en -> mapper.map(en, MenuItemDTO.class)).orElseThrow(MenuItemUpdateException.newMenuCategoryCreateException());
    }

    private MenuItemEntity bindMenuItemWithImportProduct(MenuItemEntity entity, int importProductId) {
        if(importProductId == -1) {
            entity.setImportProducts(Lists.newArrayList());
        } else {
            Optional<ImportProductEntity> importProductEntityOptional = importProductRepository.findByIpId(importProductId);
            if(importProductEntityOptional.isPresent()) {
                entity.setImportProducts(Lists.newArrayList(importProductEntityOptional.get()));
            }
        }
        return entity;
    }

    private void verifiyDescriptionNotDuplicateOrThrow(String languageDescription, MenuItemEntity entity) {
        List<MenuItemLanguageEntity> entities = menuItemLanguageRepository.findByMilDescriptionAndMenuItemEntityNot(languageDescription, entity);
        if(entities!=null && !entities.isEmpty()) {
            throw new MenuItemLanguageBusinessException();
        }
    }

    private void verifiyDescriptionNotDuplicateOrThrow(String languageDescription) {
        List<MenuItemLanguageEntity> entities = menuItemLanguageRepository.findByMilDescription(languageDescription);
        if(entities!=null && !entities.isEmpty()) {
            throw new MenuItemLanguageBusinessException();
        }
    }

    private void deleteMenuItemLanguageEntitiesByMenuItemEntity(MenuItemEntity entity) {
        List<MenuItemLanguageEntity> languages = new ArrayList<>(entity.getLanguages());
        menuItemLanguageRepository.delete(languages);
    }

    private String calanderToString(Calendar source) {
        DateFormat dateFormat = new SimpleDateFormat(LONG_DATE_FORMAT);
        return dateFormat.format(source.getTime());
    }

}
