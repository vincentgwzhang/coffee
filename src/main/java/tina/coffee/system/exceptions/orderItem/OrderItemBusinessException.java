package tina.coffee.system.exceptions.orderItem;

import tina.coffee.system.exceptions.EntityBusinessException;

public class OrderItemBusinessException extends EntityBusinessException {

    public static final String ERR_CAN_NOT_CANCEL   = "Order Item can not be canceled";
    public static final String ERR_CAN_NOT_UPDATE   = "Order Item can not be canceled";
    public static final String ERR_CAN_NOT_COMPLETE = "Order Item can not be complete";

    public OrderItemBusinessException(String errorMessage) {
        super(errorMessage);
    }
}
