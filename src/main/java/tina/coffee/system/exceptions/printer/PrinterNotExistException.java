package tina.coffee.system.exceptions.printer;

import tina.coffee.system.exceptions.EntityBusinessException;

/**
 * Created by Vincent Zhang
 * Created on 2017/10/7 - 19:29
 * Create this class only for study
 * Source from:
 */
public class PrinterNotExistException extends EntityBusinessException {

    private static final String errorMessageTmpl = "PrinterNotExistException.message";

    public PrinterNotExistException() {
        super(errorMessageTmpl, new Object[]{});
    }
}
