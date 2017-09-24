package tina.coffee.system.exceptions.order;

import tina.coffee.system.exceptions.EntityBusinessException;

import java.util.function.Supplier;

public class OrderCreateException extends EntityBusinessException {

    public static final String EXCEPTION_MESSAGE = "Order for desktop %s can not be created";

    public OrderCreateException(Integer desktopNumber) {
        super(String.format(EXCEPTION_MESSAGE, desktopNumber));
    }

    public static Supplier<OrderCreateException> newOrderCreateException(Integer desktopNumber) {
        return () -> new OrderCreateException(desktopNumber);
    }
}
