package tina.coffee.system.exceptions.weberror;

import org.springframework.http.HttpStatus;

import javax.ws.rs.core.Response;

public class ParameterErrorException extends RuntimeException {

    private final Response.Status errorStatus = Response.Status.BAD_REQUEST;

    private final HttpStatus errorHttpStatus = HttpStatus.BAD_REQUEST;

    public ParameterErrorException(String errorMessage) {
        super(errorMessage);
    }

    public Response.Status getErrorStatus() {
        return errorStatus;
    }

    public HttpStatus getErrorHttpStatus() {
        return errorHttpStatus;
    }

}
