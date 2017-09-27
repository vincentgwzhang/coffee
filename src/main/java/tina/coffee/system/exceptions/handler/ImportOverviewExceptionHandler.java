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
import tina.coffee.system.exceptions.importhistorysummary.ImportHistorySummaryNotFoundException;

@ControllerAdvice
public class ImportOverviewExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(ImportHistorySummaryNotFoundException.class)
    public ResponseEntity<ErrorDTO> handlerImportHistorySummaryNotFoundException(ImportHistorySummaryNotFoundException ex) {
        String errorMessage = messageSource.getMessage(ex.getMessageTemplate(), ex.getParameters(), LocaleContextHolder.getLocale());
        logger.error(errorMessage);

        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setHttpCode(ex.getErrorStatus().getStatusCode());
        errorDTO.setMessage(errorMessage);
        return new ResponseEntity<>(errorDTO, ex.getErrorHttpStatus());
    }

}
