package tina.coffee.system.exceptions;

import org.springframework.http.HttpStatus;
import tina.coffee.system.exceptions.generic.CoffeeGenericException;

import javax.ws.rs.core.Response.Status;

public abstract class EntityNotFoundException extends CoffeeGenericException {

    private final Status errorStatus = Status.NOT_FOUND;

    private final HttpStatus errorHttpStatus = HttpStatus.NOT_FOUND;

    public EntityNotFoundException(String errorMessageTpl, Object[] _parameters) {
        super(errorMessageTpl, _parameters);
    }

    public EntityNotFoundException(String errorMessage){
        super(errorMessage);
    }

    public Status getErrorStatus() {
        return errorStatus;
    }

    public HttpStatus getErrorHttpStatus() {
        return errorHttpStatus;
    }
}