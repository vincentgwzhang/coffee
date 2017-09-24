package tina.coffee.system.exceptions.mapper;

import tina.coffee.system.exceptions.desktop.DesktopExistException;
import tina.coffee.system.exceptions.desktop.DesktopNotAvailException;
import tina.coffee.system.exceptions.desktop.DesktopNotFoundException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

public class DesktopExceptionMapper {

    @Provider
    public static class DesktopNotFoundExceptionMapper extends BasicExceptionMapper implements ExceptionMapper<DesktopNotFoundException> {
        @Override
        public Response toResponse(DesktopNotFoundException exception) {
            return this.buildResponse(exception.getErrorStatus(), exception);
        }
    }

    @Provider
    public static class DesktopNotAvailExceptionMapper extends BasicExceptionMapper implements ExceptionMapper<DesktopNotAvailException> {
        @Override
        public Response toResponse(DesktopNotAvailException exception) {
            return this.buildResponse(exception.getErrorStatus(), exception);
        }
    }

    @Provider
    public static class DesktopExistExceptionMapper extends BasicExceptionMapper implements ExceptionMapper<DesktopExistException> {
        @Override
        public Response toResponse(DesktopExistException exception) {
            return this.buildResponse(exception.getErrorStatus(), exception);
        }
    }

}
