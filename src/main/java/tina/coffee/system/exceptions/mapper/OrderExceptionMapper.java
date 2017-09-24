package tina.coffee.system.exceptions.mapper;

import tina.coffee.system.exceptions.order.OrderNotClosedException;
import tina.coffee.system.exceptions.order.OrderNotOpenException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

public class OrderExceptionMapper {

    @Provider
    public static class OrderNotCloseExceptionMapper extends BasicExceptionMapper implements ExceptionMapper<OrderNotClosedException> {
        @Override
        public Response toResponse(OrderNotClosedException exception) {
            return this.buildResponse(Response.Status.NOT_ACCEPTABLE, exception.getMessage());
        }
    }

    @Provider
    public static class OrderNotOpenExceptionMapper extends BasicExceptionMapper implements ExceptionMapper<OrderNotOpenException>{
        @Override
        public Response toResponse(OrderNotOpenException exception) {
            return this.buildResponse(Response.Status.NOT_ACCEPTABLE, exception.getMessage());
        }
    }

}
