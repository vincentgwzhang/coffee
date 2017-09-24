package tina.coffee.system.exceptions.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import tina.coffee.system.exceptions.generic.ErrorDTO;
import tina.coffee.system.exceptions.weberror.ParameterErrorException;

@ControllerAdvice
public class WebPageExceptionHandler {

    @ExceptionHandler(ParameterErrorException.class)
    public ModelAndView handlerParameterErrorException(ParameterErrorException ex) {

        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setHttpCode(ex.getErrorStatus().getStatusCode());
        errorDTO.setMessage(ex.getMessage());

        ModelAndView errorPage = new ModelAndView("/error/errorpage");
        errorPage.addObject("error", errorDTO);
        return errorPage;
    }

}