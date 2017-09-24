package tina.coffee.system.exceptions.mapper;

import tina.coffee.system.exceptions.menuitem.MenuItemBusinessException;
import tina.coffee.system.exceptions.menuitem.MenuItemNotFoundException;
import tina.coffee.system.exceptions.menuitem.MenuItemUpdateException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

public class MenuItemExceptionMapper {

    @Provider
    public static class MenuItemBusinessExceptionMapper extends BasicExceptionMapper implements ExceptionMapper<MenuItemBusinessException> {
        @Override
        public Response toResponse(MenuItemBusinessException exception) {
            return this.buildResponse(exception.getErrorStatus(), exception);
        }
    }

    @Provider
    public static class MenuItemNotFoundExceptionMapper extends BasicExceptionMapper implements ExceptionMapper<MenuItemNotFoundException>{
        @Override
        public Response toResponse(MenuItemNotFoundException exception) {
            return this.buildResponse(exception.getErrorStatus(), exception);
        }
    }

    @Provider
    public static class MenuItemUpdateExceptionMapper extends BasicExceptionMapper implements ExceptionMapper<MenuItemUpdateException>{
        @Override
        public Response toResponse(MenuItemUpdateException exception) {
            return this.buildResponse(exception.getErrorStatus(), exception);
        }
    }
}
