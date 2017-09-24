package tina.coffee.data.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import tina.coffee.data.model.MenuItemEntity;
import tina.coffee.data.model.OrderEntity;
import tina.coffee.data.model.OrderItemEntity;
import tina.coffee.data.model.OrderItemStatus;

import java.util.List;
import java.util.Optional;

public interface OrderItemRepository extends JpaRepository<OrderItemEntity, Integer> {

    Optional<OrderItemEntity> findByOrderItemId(Integer orderItemId);

    List<OrderItemEntity> findByOrderAndStatusInOrderByStartTimeDesc(OrderEntity order, List<OrderItemStatus> statuses, Pageable pageable);

    List<OrderItemEntity> findByMenuItem(MenuItemEntity entity);

}
