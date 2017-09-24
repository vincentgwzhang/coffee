package tina.coffee.Verifier;

import tina.coffee.data.model.OrderItemEntity;
import tina.coffee.system.exceptions.orderItem.OrderItemNotFoundException;

import java.util.Optional;

public class MenuQueueVerifyProcedure {

    public static void updateOrderItemToProgressProcedure(Optional<OrderItemEntity> orderItemEntity){
        verifyIfOrderItemEntityExist(orderItemEntity);
    }

    public static void verifyIfOrderItemEntityExist(Optional<OrderItemEntity> orderItemEntity) {
        orderItemEntity.orElseThrow(OrderItemNotFoundException.newOrderItemNotFoundException());
    }


}
