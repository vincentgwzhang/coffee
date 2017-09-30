package tina.coffee.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tina.coffee.Verifier.ImportProductVerifier;
import tina.coffee.data.model.ImportHistoryEntity;
import tina.coffee.data.model.ImportProductCountEntity;
import tina.coffee.data.model.ImportProductEntity;
import tina.coffee.data.model.MenuItemToImportProductEntity;
import tina.coffee.data.repository.ImportHistoryRepository;
import tina.coffee.data.repository.ImportProductCountRepository;
import tina.coffee.data.repository.ImportProductRepository;
import tina.coffee.data.repository.MenuItemToImportProductRepository;
import tina.coffee.dozer.DozerMapper;
import tina.coffee.rest.dto.ImportProductDTO;
import tina.coffee.system.exceptions.importproduct.ImportProductBusinessException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ImportProductService {

    private final ImportProductRepository repository;

    private final ImportProductCountRepository importProductCountRepository;

    private final ImportHistoryRepository importHistoryRepository;

    private final MenuItemToImportProductRepository menuItemToImportProductRepository;

    private final DozerMapper mapper;

    @Autowired
    public ImportProductService(ImportProductRepository repository,
                                ImportProductCountRepository importProductCountRepository,
                                ImportHistoryRepository importHistoryRepository,
                                MenuItemToImportProductRepository menuItemToImportProductRepository,
                                DozerMapper mapper) {
        this.repository = repository;
        this.importProductCountRepository = importProductCountRepository;
        this.importHistoryRepository = importHistoryRepository;
        this.menuItemToImportProductRepository = menuItemToImportProductRepository;
        this.mapper = mapper;
    }

    public ImportProductDTO findById(Integer id) {
        Optional<ImportProductEntity> importProductEntityOptional = repository.findByIpId(id);
        ImportProductVerifier.verifyIfImportProductExistOrThrow(importProductEntityOptional);
        return mapper.map(importProductEntityOptional.get(), ImportProductDTO.class);
    }

    @Transactional(readOnly = true)
    public List<ImportProductDTO> findAll() {
        List<ImportProductEntity> entities = repository.findAll();
        List<ImportProductDTO> importProductDTOS = mapper.map(entities, ImportProductDTO.class);
        importProductDTOS = importProductDTOS.stream().map(this::fullFillProductCount).collect(Collectors.toList());
        return importProductDTOS;
    }

    private ImportProductDTO fullFillProductCount(ImportProductDTO dto) {
        Optional<ImportProductCountEntity> entityOptional = importProductCountRepository.findByImportProductIpId(dto.getIpId());
        if(entityOptional.isPresent()) {
            dto.setCount(entityOptional.get().getCount());
        }
        return dto;
    }

    @Transactional
    public ImportProductDTO addImportProduct(ImportProductDTO inputDTO) {
        //verify if duplicate first
        List<ImportProductEntity> entities = repository.findAll();
        ImportProductVerifier.addImportProductProcedure(entities, inputDTO);

        //add to new
        ImportProductEntity entity = mapper.map(inputDTO, ImportProductEntity.class);
        entity = repository.save(entity);

        ImportProductCountEntity countEntity = new ImportProductCountEntity();
        countEntity.setImportProduct(entity);
        countEntity.setCount(inputDTO.getCount());
        importProductCountRepository.save(countEntity);

        return mapper.map(entity, ImportProductDTO.class);
    }

    @Transactional
    public void deleteImportProductById(Integer id) {
        Optional<ImportProductEntity> importProductEntityOptional = repository.findByIpId(id);
        ImportProductVerifier.verifyIfImportProductExistOrThrow(importProductEntityOptional);
        verifyIfImportProductHistoryExistAndThrow(importProductEntityOptional.get());
        verifyIfImportProductMapToMenuItem(importProductEntityOptional.get());
        importProductCountRepository.deleteByImportProduct(importProductEntityOptional.get());
        repository.deleteByIpId(id);
    }

    public void verifyIfImportProductHistoryExistAndThrow(ImportProductEntity entity) {
        List<ImportHistoryEntity> importHistoryEntities = findImportProductHistoryByImportProduct(entity);
        if(importHistoryEntities.size() > 0) {
            throw new ImportProductBusinessException(ImportProductBusinessException.errorMessageHistTmpl);
        }
    }

    public void verifyIfImportProductMapToMenuItem(ImportProductEntity entity) {
        List<MenuItemToImportProductEntity> entities = menuItemToImportProductRepository.findByImportProductEntity(entity);
        if(entities.size() > 0) {
            throw new ImportProductBusinessException(ImportProductBusinessException.errorMessageMenuTmpl);
        }
    }

    public List<ImportHistoryEntity> findImportProductHistoryByImportProduct(ImportProductEntity entity) {
        return importHistoryRepository.findByImportProductOrderByImportHistorySummaryIhsTimeDesc(entity);
    }

    @Transactional
    public ImportProductDTO updateImportProduct(ImportProductDTO inputDTO) {
        //verify if duplicate first
        Optional<ImportProductEntity> importProductEntityOptional = repository.findByIpId(inputDTO.getIpId());
        ImportProductVerifier.verifyIfImportProductExistOrThrow(importProductEntityOptional);

        List<ImportProductEntity> entities = repository.findAll();
        ImportProductVerifier.addImportProductProcedure(entities, inputDTO);

        //update entity
        ImportProductEntity entity = mapper.map(inputDTO, ImportProductEntity.class);
        entity = repository.save(entity);

        //check if has such count before, if has before, then no need to update
        Optional<ImportProductCountEntity> EntityOptional = importProductCountRepository.findByImportProduct(entity);

        ImportProductCountEntity countEntity = EntityOptional.orElse(new ImportProductCountEntity());
        countEntity.setImportProduct(entity);
        countEntity.setCount(inputDTO.getCount());
        importProductCountRepository.save(countEntity);

        return mapper.map(entity, ImportProductDTO.class);
    }

}
