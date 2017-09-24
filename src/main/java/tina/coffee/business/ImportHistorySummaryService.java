package tina.coffee.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tina.coffee.Verifier.ImportHistoryVerifier;
import tina.coffee.data.model.ImportHistoryEntity;
import tina.coffee.data.model.ImportHistorySummaryEntity;
import tina.coffee.data.model.ImportProductCountEntity;
import tina.coffee.data.model.ImportProductEntity;
import tina.coffee.data.repository.ImportHistoryRepository;
import tina.coffee.data.repository.ImportHistorySummaryRepository;
import tina.coffee.data.repository.ImportProductCountRepository;
import tina.coffee.dozer.DozerMapper;
import tina.coffee.rest.dto.ImportHistorySummaryDTO;
import tina.coffee.rest.dto.ImportHistorySummaryWrapper;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
public class ImportHistorySummaryService {

    private final ImportHistoryRepository historyRepository;

    private final ImportProductCountRepository importProductCountRepository;

    private final ImportHistorySummaryRepository repository;

    private final DozerMapper mapper;

    @Autowired
    public ImportHistorySummaryService(ImportHistoryRepository historyRepository,
                                       ImportProductCountRepository importProductCountRepository,
                                       ImportHistorySummaryRepository repository,
                                       DozerMapper mapper) {
        this.repository = repository;
        this.historyRepository = historyRepository;
        this.importProductCountRepository = importProductCountRepository;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public ImportHistorySummaryWrapper getImportProductOverviewListInSpecificMonth(Calendar calendar) {
        calendar.set(Calendar.DATE, 1);
        Date monthStartDate = new Date(calendar.getTimeInMillis());

        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
        Date monthEndDate   = new Date(calendar.getTimeInMillis());

        List<ImportHistorySummaryEntity> importHistorySummaryEntities = repository.findByIhsTimeBetween(monthStartDate, monthEndDate);
        List<ImportHistorySummaryDTO> importHistorySummaryDTOS = mapper.map(importHistorySummaryEntities, ImportHistorySummaryDTO.class);
        return new ImportHistorySummaryWrapper(calendar.getActualMaximum(Calendar.DATE), importHistorySummaryDTOS);
    }

    @Transactional(readOnly = true)
    public List<ImportHistorySummaryDTO> findAll() {
        List<ImportHistorySummaryEntity> importHistorySummaryEntities = repository.findAll();
        return mapper.map(importHistorySummaryEntities, ImportHistorySummaryDTO.class);
    }

    @Transactional
    public ImportHistorySummaryDTO addNewImportHistorySummary(ImportHistorySummaryDTO inputDTO) {
        ImportHistorySummaryEntity entity = mapper.map(inputDTO, ImportHistorySummaryEntity.class);
        entity = repository.save(entity);
        return mapper.map(entity, ImportHistorySummaryDTO.class);
    }

    @Transactional
    public ImportHistorySummaryDTO updateImportHistorySummary(ImportHistorySummaryDTO inputDTO) {
        Optional<ImportHistorySummaryEntity> importHistorySummaryEntityOptional = repository.findByIhsId(inputDTO.getIhsId());
        ImportHistoryVerifier.verifyIfImportHistorySummaryExistOrThrow(importHistorySummaryEntityOptional);

        ImportHistorySummaryEntity entity = mapper.map(inputDTO, ImportHistorySummaryEntity.class);
        entity = repository.save(entity);
        return mapper.map(entity, ImportHistorySummaryDTO.class);
    }

    @Transactional(readOnly = true)
    public ImportHistorySummaryDTO getHistorySummaryByID(Integer id) {
        Optional<ImportHistorySummaryEntity> importHistorySummaryEntityOptional = repository.findByIhsId(id);
        ImportHistoryVerifier.verifyIfImportHistorySummaryExistOrThrow(importHistorySummaryEntityOptional);

        return mapper.map(importHistorySummaryEntityOptional.get(), ImportHistorySummaryDTO.class);
    }

    @Transactional
    public void deleteHistorySummary(Integer id) {
        Optional<ImportHistorySummaryEntity> importHistorySummaryEntityOptional = repository.findByIhsId(id);
        ImportHistoryVerifier.verifyIfImportHistorySummaryExistOrThrow(importHistorySummaryEntityOptional);

        ImportHistorySummaryEntity importHistorySummaryEntity = importHistorySummaryEntityOptional.get();
        deleteImportProductCountByHistorySummary(importHistorySummaryEntity);

        historyRepository.deleteByImportHistorySummary(importHistorySummaryEntity);
        repository.deleteByIhsId(id);
    }

    @Transactional
    public void deleteImportProductCountByHistorySummary(ImportHistorySummaryEntity importHistorySummaryEntity) {
        List<ImportHistoryEntity> importHistoryEntities = historyRepository.findByImportHistorySummary(importHistorySummaryEntity);
        for(ImportHistoryEntity entity : importHistoryEntities) {
            ImportProductEntity importProductEntity = entity.getImportProduct();
            int count = entity.getCount();
            Optional<ImportProductCountEntity> importProductCountEntityOptional = importProductCountRepository.findByImportProduct(importProductEntity);
            if(importProductCountEntityOptional.isPresent()) {
                ImportProductCountEntity importProductCountEntity = importProductCountEntityOptional.get();
                importProductCountEntity.setCount(importProductCountEntity.getCount().subtract(new BigDecimal(count)));
                importProductCountRepository.save(importProductCountEntity);
            }
        }
    }


}
