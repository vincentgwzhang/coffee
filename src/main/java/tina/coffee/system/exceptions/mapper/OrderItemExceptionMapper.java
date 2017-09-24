package tina.coffee.system.exceptions.mapper;

import tina.coffee.system.exceptions.orderItem.OrderItemBusinessException;
import tina.coffee.system.exceptions.orderItem.OrderItemNotFoundException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

public class OrderItemExceptionMapper {

    @Provider
    public static class OrderItemNotFoundExceptionMapper extends BasicExceptionMapper implements ExceptionMapper<OrderItemNotFoundException> {
        @Override
        public Response toResponse(OrderItemNotFoundException exception) {
            return this.buildResponse(exception.getErrorStatus(), exception);
        }
    }

    @Provider
    public static class OrderItemBusinessExceptionMapper extends BasicExceptionMapper implements ExceptionMapper<OrderItemBusinessException> {
        @Override
        public Response toResponse(OrderItemBusinessException exception) {
            return this.buildResponse(exception.getErrorStatus(), exception);
        }
    }

}
