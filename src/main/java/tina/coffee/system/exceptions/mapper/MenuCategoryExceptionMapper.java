package tina.coffee.system.exceptions.mapper;

import tina.coffee.system.exceptions.menucategory.MenuCategoryCreateException;
import tina.coffee.system.exceptions.menucategory.MenuCategoryNotFoundException;
import tina.coffee.system.exceptions.menucategory.MenuCategoryUpdateException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

public class MenuCategoryExceptionMapper {

    @Provider
    public static class MenuCategoryCreateExceptionMapper extends BasicExceptionMapper implements ExceptionMapper<MenuCategoryCreateException> {
        @Override
        public Response toResponse(MenuCategoryCreateException exception) {
            return this.buildResponse(exception.getErrorStatus(), exception);
        }
    }

    @Provider
    public static class MenuCategoryNotFoundExceptionMapper extends BasicExceptionMapper implements ExceptionMapper<MenuCategoryNotFoundException> {
        @Override
        public Response toResponse(MenuCategoryNotFoundException exception) {
            return this.buildResponse(exception.getErrorStatus(), exception);
        }
    }

    @Provider
    public static class MenuCategoryUpdateExceptionMapper extends BasicExceptionMapper implements ExceptionMapper<MenuCategoryUpdateException> {
        @Override
        public Response toResponse(MenuCategoryUpdateException exception) {
            return this.buildResponse(exception.getErrorStatus(), exception);
        }
    }
}
