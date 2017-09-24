package tina.coffee.Verifier;

import tina.coffee.data.model.OrderEntity;
import tina.coffee.data.model.OrderItemEntity;
import tina.coffee.data.model.OrderItemStatus;
import tina.coffee.data.model.OrderType;
import tina.coffee.system.exceptions.orderItem.OrderItemBusinessException;
import tina.coffee.system.exceptions.orderItem.OrderItemNotFoundException;
import tina.coffee.system.exceptions.order.OrderNotFoundException;
import tina.coffee.system.exceptions.order.OrderNotOpenException;

import java.util.Optional;
import java.util.Set;

public class OrderVerifier {

    public static void verifiyIfOrderExistOrThrow(Optional<OrderEntity> orderEntity) {
        orderEntity.orElseThrow(OrderNotFoundException.newOrderNotFoundException());
    }

    public static void verifiyIfOrderOpenOrThrow(Optional<OrderEntity> orderEntity, Integer desktopNumber) {
        OrderEntity entity = orderEntity.get();
        if(entity.getOrderType() != OrderType.OPEN) {
            OrderNotOpenException.newOrderNotOpenException(desktopNumber);
        }
    }

    public static void closeOrderProcedure(Optional<OrderEntity> orderEntity, Integer desktopNumber) {
        verifiyIfOrderExistOrThrow(orderEntity);
        verifiyIfOrderOpenOrThrow(orderEntity, desktopNumber);

    }

    public static void verifiyIfOrderItemExistOrThrow(Optional<OrderItemEntity> orderItemEntity) {
        orderItemEntity.orElseThrow(OrderItemNotFoundException.newOrderItemNotFoundException());
    }

    public static void verifyIfOrderItemStatusOrThrow(Optional<OrderItemEntity> orderItemEntity, Set<OrderItemStatus> statuses, String errorMessage) {
        if(!statuses.contains(orderItemEntity.get().getStatus())) {
            throw new OrderItemBusinessException(errorMessage);
        }
    }

    public static void operatorUpdateOrderItemProcedure(Optional<OrderItemEntity> orderItemEntity) {
        verifiyIfOrderItemExistOrThrow(orderItemEntity);
        verifyIfOrderItemStatusOrThrow(orderItemEntity, OrderItemStatus.operatorAllowUpdateStatus(), OrderItemBusinessException.ERR_CAN_NOT_UPDATE);
    }

    public static void operatorCancelOrderItemProcedure(Optional<OrderItemEntity> orderItemEntity) {
        verifiyIfOrderItemExistOrThrow(orderItemEntity);
        verifyIfOrderItemStatusOrThrow(orderItemEntity, OrderItemStatus.operatorAllowUpdateStatus(), OrderItemBusinessException.ERR_CAN_NOT_CANCEL);
    }


    public static void chiefUpdateOrderItemProcedure(Optional<OrderItemEntity> orderItemEntity) {
        verifiyIfOrderItemExistOrThrow(orderItemEntity);
        verifyIfOrderItemStatusOrThrow(orderItemEntity, OrderItemStatus.chiefAllowUpdateStatus(), OrderItemBusinessException.ERR_CAN_NOT_UPDATE);
    }

    public static void chiefCompleteOrderItemProcedure(Optional<OrderItemEntity> orderItemEntity) {
        verifiyIfOrderItemExistOrThrow(orderItemEntity);
        verifyIfOrderItemStatusOrThrow(orderItemEntity, OrderItemStatus.chiefAllowUpdateStatus(), OrderItemBusinessException.ERR_CAN_NOT_COMPLETE);
    }

}
