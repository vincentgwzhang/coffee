package tina.coffee.function;

import tina.coffee.data.model.*;

import java.util.Calendar;

public class MenuItemFunction {

    public static OrderItemEntity generateOrderItemEntity(OrderEntity orderEntity, MenuItemEntity menuItemEntity, Integer count) {
        OrderItemEntity entity = new OrderItemEntity();
        entity.setOrder(orderEntity);
        entity.setMenuItem(menuItemEntity);
        entity.setCount(count);
        entity.setStartTime(Calendar.getInstance());
        entity.setEndTime(null);
        entity.setStatus(OrderItemStatus.OPEN);
        return entity;
    }

}
