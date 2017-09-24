package tina.coffee.system.exceptions.order;

import tina.coffee.system.exceptions.EntityNotFoundException;

import java.util.function.Supplier;

public class OrderNotFoundException extends EntityNotFoundException {

    private static final String errorMessage = "Order can not found";

    public OrderNotFoundException() {
        super(errorMessage);
    }

    public static Supplier<OrderNotFoundException> newOrderNotFoundException() {
        return () -> new OrderNotFoundException();
    }

}
