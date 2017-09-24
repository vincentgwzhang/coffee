package tina.coffee.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tina.coffee.data.model.ImportHistoryEntity;
import tina.coffee.data.model.ImportHistorySummaryEntity;
import tina.coffee.data.model.ImportProductEntity;

import java.util.List;
import java.util.Optional;


public interface ImportHistoryRepository extends JpaRepository<ImportHistoryEntity, Integer> {

    Optional<ImportHistoryEntity> findByIhId(Integer ihId);

    List<ImportHistoryEntity> findByImportHistorySummary(ImportHistorySummaryEntity importHistorySummaryEntity);

    List<ImportHistoryEntity> findByImportProductOrderByImportHistorySummaryIhsTimeDesc(ImportProductEntity importProductEntity);

    void deleteByImportHistorySummary(ImportHistorySummaryEntity importHistorySummaryEntity);

    void deleteByImportProduct(ImportProductEntity importProductEntity);

}
