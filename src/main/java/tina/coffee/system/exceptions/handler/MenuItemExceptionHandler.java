package tina.coffee.system.exceptions.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import tina.coffee.system.exceptions.generic.ErrorDTO;
import tina.coffee.system.exceptions.menuitem.MenuItemAssociateOrdersException;
import tina.coffee.system.exceptions.menuitem.MenuItemBusinessException;
import tina.coffee.system.exceptions.menuitem.MenuItemNotFoundException;
import tina.coffee.system.exceptions.menuitem.MenuItemUpdateException;
import tina.coffee.system.exceptions.menuitemlanguage.MenuItemLanguageBusinessException;

@ControllerAdvice
public class MenuItemExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(MenuItemBusinessException.class)
    public ResponseEntity<ErrorDTO> handlerMenuItemBusinessException(MenuItemBusinessException ex) {
        String errorMessage = messageSource.getMessage(ex.getMessageTemplate(), ex.getParameters(), LocaleContextHolder.getLocale());
        logger.error(errorMessage);

        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setHttpCode(ex.getErrorStatus().getStatusCode());
        errorDTO.setMessage(ex.getMessage());
        return new ResponseEntity<>(errorDTO, ex.getErrorHttpStatus());
    }

    @ExceptionHandler(MenuItemUpdateException.class)
    public ResponseEntity<ErrorDTO> handlerMenuItemUpdateException(MenuItemUpdateException ex) {
        String errorMessage = messageSource.getMessage(ex.getMessageTemplate(), ex.getParameters(), LocaleContextHolder.getLocale());
        logger.error(errorMessage);

        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setHttpCode(ex.getErrorStatus().getStatusCode());
        errorDTO.setMessage(ex.getMessage());
        return new ResponseEntity<>(errorDTO, ex.getErrorHttpStatus());
    }

    @ExceptionHandler(MenuItemNotFoundException.class)
    public ResponseEntity<ErrorDTO> handlerMenuItemNotFoundException(MenuItemNotFoundException ex) {
        String errorMessage = messageSource.getMessage(ex.getMessageTemplate(), ex.getParameters(), LocaleContextHolder.getLocale());
        logger.error(errorMessage);

        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setHttpCode(ex.getErrorStatus().getStatusCode());
        errorDTO.setMessage(ex.getMessage());
        return new ResponseEntity<>(errorDTO, ex.getErrorHttpStatus());
    }

    @ExceptionHandler(MenuItemAssociateOrdersException.class)
    public ResponseEntity<ErrorDTO> handlerMenuItemAssociateOrdersException(MenuItemAssociateOrdersException ex) {
        String errorMessage = messageSource.getMessage(ex.getMessageTemplate(), ex.getParameters(), LocaleContextHolder.getLocale());
        logger.error(errorMessage);

        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setHttpCode(ex.getErrorStatus().getStatusCode());
        errorDTO.setMessage(ex.getMessage());
        return new ResponseEntity<>(errorDTO, ex.getErrorHttpStatus());
    }

    @ExceptionHandler(MenuItemLanguageBusinessException.class)
    public ResponseEntity<ErrorDTO> handlerMenuItemLanguageBusinessException(MenuItemLanguageBusinessException ex) {
        String errorMessage = messageSource.getMessage(ex.getMessageTemplate(), ex.getParameters(), LocaleContextHolder.getLocale());
        logger.error(errorMessage);

        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setHttpCode(ex.getErrorStatus().getStatusCode());
        errorDTO.setMessage(ex.getMessage());
        return new ResponseEntity<>(errorDTO, ex.getErrorHttpStatus());
    }
}
