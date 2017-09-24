package tina.coffee.system.exceptions.mapper;

import tina.coffee.system.exceptions.desktop.DesktopNotAvailException;
import tina.coffee.system.exceptions.EntityCreateException;
import tina.coffee.system.exceptions.EntityExistException;
import tina.coffee.system.exceptions.EntityNotFoundException;
import tina.coffee.system.exceptions.EntityUpdateException;
import tina.coffee.system.exceptions.order.OrderNotClosedException;
import tina.coffee.system.exceptions.order.OrderNotOpenException;
import tina.coffee.system.exceptions.security.CoffeeAccessDeniedException;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

public class CoffeeExceptionMapper {
    @Provider
    public static class EntityNotFoundExceptionMapper extends BasicExceptionMapper implements ExceptionMapper<EntityNotFoundException>{
        @Override
        public Response toResponse(EntityNotFoundException exception) {
            return this.buildResponse(Status.NOT_FOUND, exception);
        }
    }

    @Provider
    public static class EntityExistExceptionMapper extends BasicExceptionMapper implements ExceptionMapper<EntityExistException>{
        @Override
        public Response toResponse(EntityExistException exception) {
            return this.buildResponse(Status.BAD_REQUEST, exception.getMessage());
        }
    }

    @Provider
    public static class EntityCreateErrorExceptionMapper extends BasicExceptionMapper implements ExceptionMapper<EntityCreateException>{
        @Override
        public Response toResponse(EntityCreateException exception) {
            return this.buildResponse(Status.NOT_ACCEPTABLE, exception.getMessage());
        }
    }

    @Provider
    public static class EntityUpdateExceptionMapper extends BasicExceptionMapper implements ExceptionMapper<EntityUpdateException>{
        @Override
        public Response toResponse(EntityUpdateException exception) {
            return this.buildResponse(Status.NOT_ACCEPTABLE, exception.getMessage());
        }
    }

    @Provider
    public static class RuntimeExceptionMapper extends BasicExceptionMapper implements ExceptionMapper<RuntimeException>{
        @Override
        public Response toResponse(RuntimeException exception) {
            return this.buildResponse(Status.BAD_REQUEST, exception.getMessage());
        }
    }

    @Provider
    public static class CoffeeAccessDeniedExceptionMapper extends BasicExceptionMapper implements ExceptionMapper<CoffeeAccessDeniedException>{
        @Override
        public Response toResponse(CoffeeAccessDeniedException exception) {
            return this.buildResponse(Status.BAD_REQUEST, exception.getMessage());
        }
    }
}
