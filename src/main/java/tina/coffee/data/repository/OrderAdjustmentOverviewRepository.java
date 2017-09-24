package tina.coffee.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tina.coffee.data.model.OrderAdjustmentOverviewEntity;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface OrderAdjustmentOverviewRepository extends JpaRepository<OrderAdjustmentOverviewEntity, Integer> {

    List<OrderAdjustmentOverviewEntity> findByAdjustDateBetween(Date startDate, Date endDate);

    Optional<OrderAdjustmentOverviewEntity> findByAdjustDate(Date date);

}
