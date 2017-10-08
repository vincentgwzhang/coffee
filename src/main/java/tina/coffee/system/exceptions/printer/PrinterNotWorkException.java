package tina.coffee.system.exceptions.printer;

import tina.coffee.system.exceptions.EntityBusinessException;

public class PrinterNotWorkException extends EntityBusinessException {

    private static final String errorMessageTmpl = "PrinterNotWorkException.message";

    public PrinterNotWorkException() {
        super(errorMessageTmpl, new Object[]{});
    }
}
