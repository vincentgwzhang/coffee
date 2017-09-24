package tina.coffee.system.exceptions.order;

import tina.coffee.system.exceptions.EntityBusinessException;

public class OrderAppendItemException extends EntityBusinessException {

    public static final String EXCEPTION_MESSAGE_MENU_ITEM_DISABLE = "Order item is not available";

    public OrderAppendItemException(String errorMesage) {
        super(String.format(errorMesage));
    }
}
