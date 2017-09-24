package tina.coffee.system.exceptions.order;

import tina.coffee.system.exceptions.EntityBusinessException;

import java.util.function.Supplier;

public class OrderNotOpenException extends EntityBusinessException {

    public static final String EXCEPTION_MESSAGE = "Order for desktop %s has not openned";

    public OrderNotOpenException(Integer desktopNumber) {
        super(String.format(EXCEPTION_MESSAGE, desktopNumber));
    }

    public static Supplier<OrderNotOpenException> newOrderNotOpenException(Integer desktopNumber) {
        return () -> new OrderNotOpenException(desktopNumber);
    }
}
