package tina.coffee.system.exceptions;

import org.springframework.http.HttpStatus;
import tina.coffee.system.exceptions.generic.CoffeeGenericException;

import javax.ws.rs.core.Response.Status;

public abstract class EntityBusinessException extends CoffeeGenericException {

    private final Status errorStatus = Status.BAD_REQUEST;

    private final HttpStatus errorHttpStatus = HttpStatus.BAD_REQUEST;

    public EntityBusinessException(String messageTemplate, Object[] _parameters) {
        super(messageTemplate, _parameters);
    }

    public EntityBusinessException(String errorMessage){
        super(errorMessage);
    }

    public Status getErrorStatus() {
        return errorStatus;
    }

    public HttpStatus getErrorHttpStatus() {
        return errorHttpStatus;
    }
}