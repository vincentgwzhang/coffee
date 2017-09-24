package tina.coffee.business;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tina.coffee.Verifier.ImportHistoryVerifier;
import tina.coffee.Verifier.ImportProductVerifier;
import tina.coffee.data.model.ImportHistoryEntity;
import tina.coffee.data.model.ImportHistorySummaryEntity;
import tina.coffee.data.model.ImportProductEntity;
import tina.coffee.data.repository.ImportHistoryRepository;
import tina.coffee.data.repository.ImportHistorySummaryRepository;
import tina.coffee.data.repository.ImportProductRepository;
import tina.coffee.dozer.DozerMapper;
import tina.coffee.function.CalFunction;
import tina.coffee.rest.dto.ImportHistoryDTO;
import tina.coffee.rest.dto.ImportProductTraceDTO;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

import static tina.coffee.system.config.SystemConstant.FRONT_END_SHORT_DATE_FORMAT;
import static tina.coffee.system.config.SystemConstant.SHORT_DATE_FORMAT;

@Service
public class ImportHistoryService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private final ImportProductRepository importProductRepository;

    private final ImportHistoryRepository repository;

    private final ImportHistorySummaryRepository summaryRepository;

    private final DozerMapper mapper;

    @Autowired
    public ImportHistoryService(ImportProductRepository importProductRepository, ImportHistoryRepository repository, ImportHistorySummaryRepository summaryRepository, DozerMapper mapper) {
        this.repository = repository;
        this.importProductRepository = importProductRepository;
        this.summaryRepository = summaryRepository;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public List<ImportProductTraceDTO> getImportHistoryByImportProduct(Integer ipID) {
        Optional<ImportProductEntity> importProductEntityOptional = importProductRepository.findByIpId(ipID);
        ImportProductVerifier.verifyIfImportProductExistOrThrow(importProductEntityOptional);
        ImportProductEntity entity = importProductEntityOptional.get();
        List<ImportHistoryEntity> importHistoryEntities = repository.findByImportProductOrderByImportHistorySummaryIhsTimeDesc(entity);
        return mapper.map(importHistoryEntities, ImportProductTraceDTO.class);
    }

    @Transactional(readOnly = true)
    public List<ImportHistoryDTO> findAll() {
        List<ImportHistoryEntity> importHistoryEntities = repository.findAll();
        return mapper.map(importHistoryEntities, ImportHistoryDTO.class);
    }

    @Transactional(readOnly = true)
    public ImportHistoryDTO getImportHistoryById(Integer id) {
        Optional<ImportHistoryEntity> importHistoryEntityOptional = repository.findByIhId(id);
        ImportHistoryVerifier.verifyIfImportHistoryExistOrThrow(importHistoryEntityOptional);
        return mapper.map(importHistoryEntityOptional.get(), ImportHistoryDTO.class);
    }

    @Transactional
    public ImportHistoryDTO addImportHistoryDTO(String dateString, ImportHistoryDTO inputDTO) {
        Optional<ImportProductEntity> importProductEntityOptional = importProductRepository.findByIpId(inputDTO.getIhIpId());
        ImportProductVerifier.verifyIfImportProductExistOrThrow(importProductEntityOptional);

        Optional<ImportHistorySummaryEntity> entityOptional = summaryRepository.findFirstByIhsTime(CalFunction.stringToDate(FRONT_END_SHORT_DATE_FORMAT, dateString));
        ImportHistorySummaryEntity summaryEntity = entityOptional.orElseGet(()->newSummaryEntity(FRONT_END_SHORT_DATE_FORMAT, dateString));

        ImportHistoryEntity entity = new ImportHistoryEntity();
        entity.setImportHistorySummary(summaryEntity);
        entity.setImportProduct(importProductEntityOptional.get());
        entity.setCount(inputDTO.getCount());
        entity.setIhPrice(inputDTO.getIhPrice());

        summaryEntity.setIhsPrice(summaryEntity.getIhsPrice().add(inputDTO.getIhPrice()));
        summaryRepository.save(summaryEntity);

        entity = repository.save(entity);
        return mapper.map(entity, ImportHistoryDTO.class);
    }

    public ImportHistorySummaryEntity newSummaryEntity(String dateFormat, String dateString) {
        ImportHistorySummaryEntity entity = new ImportHistorySummaryEntity();
        entity.setIhsPrice(BigDecimal.ZERO);
        entity.setIhsTime(CalFunction.stringToDate(dateFormat, dateString));
        return entity;
    }

    @Transactional
    public ImportHistoryDTO updateImportHistory(ImportHistoryDTO importHistoryDTO) {
        Optional<ImportProductEntity> importProductEntityOptional = importProductRepository.findByIpId(importHistoryDTO.getIhIpId());
        Optional<ImportHistorySummaryEntity> importHistorySummaryEntityOptional = summaryRepository.findByIhsId(importHistoryDTO.getIhIhsId());
        ImportProductVerifier.verifyIfImportProductExistOrThrow(importProductEntityOptional);
        ImportHistoryVerifier.verifyIfImportHistorySummaryExistOrThrow(importHistorySummaryEntityOptional);

        Optional<ImportHistoryEntity> entityOptional = repository.findByIhId(importHistoryDTO.getIhId());
        ImportHistoryVerifier.verifyIfImportHistoryExistOrThrow(entityOptional);

        ImportHistoryEntity entity = entityOptional.get();
        ImportHistorySummaryEntity summaryEntity = importHistorySummaryEntityOptional.get();
        entity.setIhPrice(importHistoryDTO.getIhPrice());
        entity.setCount(importHistoryDTO.getCount());
        entity.setImportProduct(importProductEntityOptional.get());
        entity.setImportHistorySummary(summaryEntity);
        entity = repository.save(entity);

        List<ImportHistoryEntity> entities = repository.findByImportHistorySummary(summaryEntity);
        summaryEntity = reCalulateTotalPrice(summaryEntity, entities);
        summaryRepository.save(summaryEntity);

        return mapper.map(entity, ImportHistoryDTO.class);
    }

    @Transactional
    public void deleteImportHistoryById(Integer id) {
        Optional<ImportHistoryEntity> entityOptional = repository.findByIhId(id);
        ImportHistoryVerifier.verifyIfImportHistoryExistOrThrow(entityOptional);
        repository.delete(id);

        ImportHistoryEntity entity = entityOptional.get();
        ImportHistorySummaryEntity summaryEntity = entity.getImportHistorySummary();
        List<ImportHistoryEntity> entities = repository.findByImportHistorySummary(summaryEntity);
        if(entities.isEmpty()) {
            summaryRepository.delete(summaryEntity);
        } else {
            summaryEntity = reCalulateTotalPrice(summaryEntity, entities);
            summaryRepository.save(summaryEntity);
        }
    }

    private ImportHistorySummaryEntity reCalulateTotalPrice(ImportHistorySummaryEntity summaryEntity, List<ImportHistoryEntity> entities) {
        BigDecimal total = entities.stream().map(ImportHistoryEntity::getIhPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        summaryEntity.setIhsPrice(total);
        return summaryEntity;
    }

    public List<ImportHistoryDTO> findAllByDate(String dateString) {
        Date sqlDate = CalFunction.stringToDate(SHORT_DATE_FORMAT, dateString);
        Optional<ImportHistorySummaryEntity> summaryEntity = summaryRepository.findFirstByIhsTime(sqlDate);

        if(!summaryEntity.isPresent()) {
            return Lists.newArrayList();
        }

        List<ImportHistoryEntity> entityList = repository.findByImportHistorySummary(summaryEntity.get());
        return mapper.map(entityList, ImportHistoryDTO.class);
    }

    @Transactional
    public List<ImportHistoryDTO> findAllBySummaryId(Integer importHistorySummaryId) {
        Optional<ImportHistorySummaryEntity> importHistorySummaryEntityOptional = summaryRepository.findByIhsId(importHistorySummaryId);
        ImportHistoryVerifier.verifyIfImportHistorySummaryExistOrThrow(importHistorySummaryEntityOptional);

        List<ImportHistoryEntity> entityList = repository.findByImportHistorySummary(importHistorySummaryEntityOptional.get());
        return mapper.map(entityList, ImportHistoryDTO.class);
    }

    @Transactional
    public void deleteAllBySummaryId(Integer importHistorySummaryId) {
        Optional<ImportHistorySummaryEntity> importHistorySummaryEntityOptional = summaryRepository.findByIhsId(importHistorySummaryId);
        ImportHistoryVerifier.verifyIfImportHistorySummaryExistOrThrow(importHistorySummaryEntityOptional);

        repository.deleteByImportHistorySummary(importHistorySummaryEntityOptional.get());
    }

}