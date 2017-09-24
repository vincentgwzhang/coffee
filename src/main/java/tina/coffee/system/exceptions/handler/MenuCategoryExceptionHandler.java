package tina.coffee.system.exceptions.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import tina.coffee.system.exceptions.desktop.DesktopExistException;
import tina.coffee.system.exceptions.desktop.DesktopNotAvailException;
import tina.coffee.system.exceptions.desktop.DesktopNotFoundException;
import tina.coffee.system.exceptions.generic.ErrorDTO;
import tina.coffee.system.exceptions.menucategory.MenuCategoryBusinessException;
import tina.coffee.system.exceptions.menucategorylanguage.MenuCategoryLanguageBusinessException;

@ControllerAdvice
public class MenuCategoryExceptionHandler {

    @ExceptionHandler(MenuCategoryLanguageBusinessException.class)
    public ResponseEntity<ErrorDTO> handlerMenuCategoryLanguageBusinessException(MenuCategoryLanguageBusinessException ex) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setHttpCode(ex.getErrorStatus().getStatusCode());
        errorDTO.setMessage(ex.getMessage());
        return new ResponseEntity<>(errorDTO, ex.getErrorHttpStatus());
    }

    @ExceptionHandler(MenuCategoryBusinessException.class)
    public ResponseEntity<ErrorDTO> handlerMenuCategoryBusinessException(MenuCategoryBusinessException ex) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setHttpCode(ex.getErrorStatus().getStatusCode());
        errorDTO.setMessage(ex.getMessage());
        return new ResponseEntity<>(errorDTO, ex.getErrorHttpStatus());
    }
}
