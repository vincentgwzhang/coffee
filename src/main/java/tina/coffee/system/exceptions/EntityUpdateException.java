package tina.coffee.system.exceptions;

import tina.coffee.system.exceptions.generic.CoffeeGenericException;

import javax.ws.rs.core.Response;

public abstract class EntityUpdateException extends CoffeeGenericException {

    private final Response.Status errorStatus = Response.Status.NOT_FOUND;

    public EntityUpdateException(String errorMessage) {
        super(errorMessage);
    }

    public Response.Status getErrorStatus() {
        return errorStatus;
    }

}
