package tina.coffee.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tina.coffee.Verifier.MenuVerifier;
import tina.coffee.data.model.MenuCategoryEntity;
import tina.coffee.data.model.MenuCategoryLanguageEntity;
import tina.coffee.data.model.MenuItemEntity;
import tina.coffee.data.repository.MenuCategoryLanguageRepository;
import tina.coffee.data.repository.MenuCategoryRepository;
import tina.coffee.data.repository.MenuItemRepository;
import tina.coffee.dozer.DozerMapper;
import tina.coffee.rest.dto.MenuCategoryDTO;
import tina.coffee.rest.dto.MenuCategoryWithMenuItems;
import tina.coffee.rest.dto.MenuItemDTO;
import tina.coffee.system.exceptions.menucategory.MenuCategoryBusinessException;
import tina.coffee.system.exceptions.menucategory.MenuCategoryCreateException;
import tina.coffee.system.exceptions.menucategory.MenuCategoryUpdateException;
import tina.coffee.system.exceptions.menucategorylanguage.MenuCategoryLanguageBusinessException;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class MenuCategoryService {

    private final MenuCategoryRepository repository;
    private final MenuCategoryLanguageRepository languageRepository;
    private final MenuItemRepository menuItemRepository;

    private final DozerMapper mapper;

    @Autowired
    public MenuCategoryService(MenuCategoryRepository repository,
                               MenuCategoryLanguageRepository languageRepository,
                               MenuItemRepository menuItemRepository,
                               DozerMapper mapper) {
        this.repository = repository;
        this.languageRepository = languageRepository;
        this.menuItemRepository = menuItemRepository;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public List<MenuCategoryDTO> listAllCategories() {
        List<MenuCategoryEntity> entities = repository.findAll();
        return mapper.map(entities, MenuCategoryDTO.class);
    }

    public List<MenuCategoryWithMenuItems> showMenu() {
        List<MenuCategoryDTO> menuCategoryDTOS = listAllCategories();
        List<MenuCategoryWithMenuItems> singleMenuCategories = mapper.map(menuCategoryDTOS, MenuCategoryWithMenuItems.class);
        singleMenuCategories.forEach(smc -> smc.setMenuItemDTOList(findMenuitemsAndFullfill(smc)));
        return singleMenuCategories;
    }

    private List<MenuItemDTO> findMenuitemsAndFullfill(MenuCategoryWithMenuItems menuCategoryWithItems) {
        List<MenuItemEntity> menuItemEntities = menuItemRepository.getMenuItemEntitiesByMenuCategoryEntityMcId(menuCategoryWithItems.getMenuCategoryDTO().getMcId());
        return mapper.map(menuItemEntities, MenuItemDTO.class);
    }

    @Transactional(readOnly = true)
    public List<MenuCategoryDTO> listEnabledCategories() {
        List<MenuCategoryEntity> entities = repository.findByMcEnable(true);
        return mapper.map(entities, MenuCategoryDTO.class);
    }

    @Transactional(readOnly = true)
    public MenuCategoryDTO getMenuCategoryById(Integer id) {
        Optional<MenuCategoryEntity> entity = repository.findByMcId(id);
        MenuVerifier.verifyIfMenuCategoryExistOrThrow(entity);
        return entity.map(en -> mapper.map(en,MenuCategoryDTO.class)).get();
    }

    @Transactional
    public void deleteMenuCategoryById(Integer id) {
        Optional<MenuCategoryEntity> entity = repository.findByMcId(id);
        MenuVerifier.verifyIfMenuCategoryExistOrThrow(entity);
        if(entity.isPresent()) {
            //check if it still has menu category sub item
            List<MenuItemEntity> menuItemEntities = menuItemRepository.getMenuItemEntitiesByMenuCategoryEntity(entity.get());
            if(menuItemEntities != null && !menuItemEntities.isEmpty()) {
                throw new MenuCategoryBusinessException();
            }

            deleteMenuCategoryLanguageEntitiesByMenuCategoryEntity(entity.get());
            repository.delete(id);
        }
    }

    @Transactional
    public void deleteMenuCategoryByCollection(Set<Integer> ids) {
        ids.stream().forEach(this::deleteMenuCategoryById);
    }

    @Transactional
    public MenuCategoryDTO addCategory(MenuCategoryDTO menuCategoryDTO) {
        verifiyDescriptionNotDuplicateOrThrow(menuCategoryDTO.getDescCN());
        verifiyDescriptionNotDuplicateOrThrow(menuCategoryDTO.getDescEN());
        verifiyDescriptionNotDuplicateOrThrow(menuCategoryDTO.getDescSP());

        final MenuCategoryEntity categoryEntity = mapper.map(menuCategoryDTO, MenuCategoryEntity.class);
        Set<MenuCategoryLanguageEntity> languages = categoryEntity.getLanguages();
        languages.forEach(language -> language.setMenuCategoryEntity(categoryEntity));
        MenuCategoryEntity savedEntity = repository.save(categoryEntity);
        languageRepository.save(languages);

        return Optional.ofNullable(savedEntity)
                .map(en -> mapper.map(en,MenuCategoryDTO.class))
                .orElseThrow(MenuCategoryCreateException.newMenuCategoryCreateException());
    }

    @Transactional
    public MenuCategoryDTO updateCategory(MenuCategoryDTO menuCategoryDTO) {
        Optional<MenuCategoryEntity> entityFromDB = repository.findByMcId(menuCategoryDTO.getMcId());
        MenuVerifier.verifyIfMenuCategoryExistOrThrow(entityFromDB);
        deleteMenuCategoryLanguageEntitiesByMenuCategoryEntity(entityFromDB.get());

        verifiyDescriptionNotDuplicateOrThrow(menuCategoryDTO.getDescCN(), entityFromDB.get());
        verifiyDescriptionNotDuplicateOrThrow(menuCategoryDTO.getDescEN(), entityFromDB.get());
        verifiyDescriptionNotDuplicateOrThrow(menuCategoryDTO.getDescSP(), entityFromDB.get());

        final MenuCategoryEntity entityToDB = mapper.map(menuCategoryDTO, MenuCategoryEntity.class);
        Set<MenuCategoryLanguageEntity> languages = entityToDB.getLanguages();
        languages.forEach(language -> language.setMenuCategoryEntity(entityToDB));
        languageRepository.save(languages);
        MenuCategoryEntity savedEntity = repository.save(entityToDB);

        return Optional.ofNullable(savedEntity)
                .map(en -> mapper.map(en,MenuCategoryDTO.class))
                .orElseThrow(MenuCategoryUpdateException.newMenuCategoryCreateException());
    }

    private void deleteMenuCategoryLanguageEntitiesByMenuCategoryEntity(MenuCategoryEntity entity) {
        Set<MenuCategoryLanguageEntity> languages = entity.getLanguages();
        languageRepository.delete(languages);
    }

    private void verifiyDescriptionNotDuplicateOrThrow(String languageDescription) {
        List<MenuCategoryLanguageEntity> entities = languageRepository.findByMclDescription(languageDescription);
        if(entities != null && !entities.isEmpty()) {
            throw new MenuCategoryLanguageBusinessException();
        }
    }

    private void verifiyDescriptionNotDuplicateOrThrow(String languageDescription, MenuCategoryEntity entity) {
        List<MenuCategoryLanguageEntity> entities = languageRepository.findByMclDescriptionAndMenuCategoryEntityNot(languageDescription, entity);
        if(entities != null && !entities.isEmpty()) {
            throw new MenuCategoryLanguageBusinessException();
        }
    }
}
