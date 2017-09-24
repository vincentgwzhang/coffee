package tina.coffee.system.exceptions.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import tina.coffee.system.exceptions.generic.ErrorDTO;
import tina.coffee.system.exceptions.importhistorysummary.ImportHistorySummaryNotFoundException;

@ControllerAdvice
public class ImportOverviewExceptionHandler {

    @ExceptionHandler(ImportHistorySummaryNotFoundException.class)
    public ResponseEntity<ErrorDTO> handlerImportHistorySummaryNotFoundException(ImportHistorySummaryNotFoundException ex) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setHttpCode(ex.getErrorStatus().getStatusCode());
        errorDTO.setMessage(ex.getMessage());
        return new ResponseEntity<>(errorDTO, ex.getErrorHttpStatus());
    }

}
