package tina.coffee.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tina.coffee.data.model.OrderItemAdjustmentEntity;

public interface OrderItemAdjustmentRepository extends JpaRepository<OrderItemAdjustmentEntity, Integer> {
}
