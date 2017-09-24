package tina.coffee.system.exceptions.orderItem;

import tina.coffee.system.exceptions.EntityNotFoundException;

import java.util.function.Supplier;

public class OrderItemNotFoundException extends EntityNotFoundException {

    private static final String EXCEPTION_MESSAGE = "Order Item not found";

    public OrderItemNotFoundException() {
        super(EXCEPTION_MESSAGE);
    }

    public static Supplier<OrderItemNotFoundException> newOrderItemNotFoundException() {
        return () -> new OrderItemNotFoundException();
    }
}
