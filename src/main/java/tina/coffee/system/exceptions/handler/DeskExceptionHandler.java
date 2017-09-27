package tina.coffee.system.exceptions.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import tina.coffee.system.exceptions.desktop.DesktopExistException;
import tina.coffee.system.exceptions.desktop.DesktopNotAvailException;
import tina.coffee.system.exceptions.desktop.DesktopNotFoundException;
import tina.coffee.system.exceptions.generic.ErrorDTO;

@ControllerAdvice
public class DeskExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(DesktopExistException.class)
    public ResponseEntity<ErrorDTO> handlerDesktopExistException(DesktopExistException ex) {
        String errorMessage = messageSource.getMessage(ex.getMessageTemplate(), ex.getParameters(), LocaleContextHolder.getLocale());
        logger.error(errorMessage);

        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setHttpCode(ex.getErrorStatus().getStatusCode());
        errorDTO.setMessage(errorMessage);
        return new ResponseEntity<>(errorDTO, ex.getErrorHttpStatus());
    }

    @ExceptionHandler(DesktopNotFoundException.class)
    public ResponseEntity<ErrorDTO> handlerDesktopNotFoundException(DesktopNotFoundException ex) {
        String errorMessage = messageSource.getMessage(ex.getMessageTemplate(), ex.getParameters(), LocaleContextHolder.getLocale());
        logger.error(errorMessage);

        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setHttpCode(ex.getErrorStatus().getStatusCode());
        errorDTO.setMessage(errorMessage);
        return new ResponseEntity<>(errorDTO, ex.getErrorHttpStatus());
    }

    @ExceptionHandler(DesktopNotAvailException.class)
    public ResponseEntity<ErrorDTO> handlerDesktopNotAvailException(DesktopNotAvailException ex) {
        String errorMessage = messageSource.getMessage(ex.getMessageTemplate(), ex.getParameters(), LocaleContextHolder.getLocale());
        logger.error(errorMessage);

        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setHttpCode(ex.getErrorStatus().getStatusCode());
        errorDTO.setMessage(errorMessage);
        return new ResponseEntity<>(errorDTO, ex.getErrorHttpStatus());
    }

}
