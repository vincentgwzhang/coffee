package tina.coffee.system.exceptions.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import tina.coffee.system.exceptions.desktop.DesktopExistException;
import tina.coffee.system.exceptions.desktop.DesktopNotAvailException;
import tina.coffee.system.exceptions.desktop.DesktopNotFoundException;
import tina.coffee.system.exceptions.generic.ErrorDTO;

@ControllerAdvice
public class DeskExceptionHandler {

    @ExceptionHandler(DesktopExistException.class)
    public ResponseEntity<ErrorDTO> handlerDesktopExistException(DesktopExistException ex) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setHttpCode(ex.getErrorStatus().getStatusCode());
        errorDTO.setMessage(ex.getMessage());
        return new ResponseEntity<>(errorDTO, ex.getErrorHttpStatus());
    }

    @ExceptionHandler(DesktopNotFoundException.class)
    public ResponseEntity<ErrorDTO> handlerDesktopNotFoundException(DesktopNotFoundException ex) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setHttpCode(ex.getErrorStatus().getStatusCode());
        errorDTO.setMessage(ex.getMessage());
        return new ResponseEntity<>(errorDTO, ex.getErrorHttpStatus());
    }

    @ExceptionHandler(DesktopNotAvailException.class)
    public ResponseEntity<ErrorDTO> handlerDesktopNotAvailException(DesktopNotAvailException ex) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setHttpCode(ex.getErrorStatus().getStatusCode());
        errorDTO.setMessage(ex.getMessage());
        return new ResponseEntity<>(errorDTO, ex.getErrorHttpStatus());
    }

}
