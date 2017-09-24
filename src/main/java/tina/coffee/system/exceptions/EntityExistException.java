package tina.coffee.system.exceptions;

import org.springframework.http.HttpStatus;
import tina.coffee.system.exceptions.generic.CoffeeGenericException;

import javax.ws.rs.core.Response.Status;

public abstract class EntityExistException extends CoffeeGenericException {

    private final Status errorStatus = Status.CONFLICT;
    private final HttpStatus errorHttpStatus = HttpStatus.CONFLICT;

    public EntityExistException(String errorMessage){
        super(errorMessage);
    }

    public Status getErrorStatus() {
        return errorStatus;
    }

    public HttpStatus getErrorHttpStatus() {
        return errorHttpStatus;
    }
}
