package tina.coffee.system.exceptions;

import tina.coffee.system.exceptions.generic.CoffeeGenericException;

import javax.ws.rs.core.Response;

public abstract class EntityCreateException extends CoffeeGenericException {

    private final Response.Status errorStatus = Response.Status.NOT_FOUND;

    public EntityCreateException(String errorMessage){
        super(errorMessage);
    }

    public Response.Status getErrorStatus() {
        return errorStatus;
    }

}
