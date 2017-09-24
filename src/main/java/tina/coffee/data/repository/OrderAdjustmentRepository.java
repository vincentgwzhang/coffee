package tina.coffee.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tina.coffee.data.model.OrderAdjustmentEntity;

public interface OrderAdjustmentRepository extends JpaRepository<OrderAdjustmentEntity, Integer> {
}
