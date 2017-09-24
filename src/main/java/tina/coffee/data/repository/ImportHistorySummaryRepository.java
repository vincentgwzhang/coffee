package tina.coffee.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tina.coffee.data.model.ImportHistorySummaryEntity;

import java.sql.Date;
import java.util.List;
import java.util.Optional;


public interface ImportHistorySummaryRepository extends JpaRepository<ImportHistorySummaryEntity, Integer> {

    Optional<ImportHistorySummaryEntity> findByIhsId(Integer id);

    List<ImportHistorySummaryEntity> findByIhsTimeBetween(Date startDate, Date endDate);

    Optional<ImportHistorySummaryEntity> findFirstByIhsTime(Date date);

    void deleteByIhsTime(Date ihsTime);

    void deleteByIhsId(Integer id);

}
