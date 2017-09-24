package tina.coffee.system.exceptions.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import tina.coffee.system.exceptions.generic.ErrorDTO;
import tina.coffee.system.exceptions.importproduct.ImportProductBusinessException;

@ControllerAdvice
public class ImportProductExceptionHandler {

    @ExceptionHandler(ImportProductBusinessException.class)
    public ResponseEntity<ErrorDTO> handlerImportProductBusinessException(ImportProductBusinessException ex) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setHttpCode(ex.getErrorStatus().getStatusCode());
        errorDTO.setMessage(ex.getMessage());
        return new ResponseEntity<>(errorDTO, ex.getErrorHttpStatus());
    }
}
