package tina.coffee.system.exceptions.order;

import tina.coffee.system.exceptions.EntityBusinessException;

public class OrderNotClosedException extends EntityBusinessException {

    public static final String EXCEPTION_MESSAGE = "Order for desktop %s has not been closed";

    public OrderNotClosedException(Integer desktopNumber) {
        super(String.format(EXCEPTION_MESSAGE, desktopNumber));
    }
}
