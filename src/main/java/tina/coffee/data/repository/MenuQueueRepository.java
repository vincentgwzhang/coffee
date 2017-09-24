package tina.coffee.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tina.coffee.data.model.MenuQueueEntity;
import tina.coffee.data.model.OrderItemEntity;

import java.util.Optional;


public interface MenuQueueRepository extends JpaRepository<MenuQueueEntity, Integer> {

    void deleteByOrderItemEntity(OrderItemEntity orderItemEntity);

    Optional<MenuQueueEntity> findByMqId(Integer mqId);

}
