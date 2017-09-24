package tina.coffee.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tina.coffee.data.model.DesktopEntity;
import tina.coffee.data.model.OrderEntity;
import tina.coffee.data.model.OrderType;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<OrderEntity, Integer> {

    Optional<OrderEntity> findByDesktopEntityAndOrderType(DesktopEntity entity, OrderType orderType);

    Optional<OrderEntity> findByOrderId(Integer orderId);

    List<OrderEntity> findByEndTimeBetweenAndOrderTypeOrderByOrderId(Calendar startTime, Calendar endTime, OrderType orderType);
}
