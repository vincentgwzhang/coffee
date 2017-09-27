package tina.coffee.system.exceptions.desktop;

import tina.coffee.system.exceptions.EntityBusinessException;

import java.util.function.Supplier;

public class DesktopNotAvailException extends EntityBusinessException {

    public static final String REJECT_IS_DISABLED     = "DesktopNotAvailException.REJECT_IS_DISABLED.message";
    public static final String REJECT_IS_OCCUPIED     = "DesktopNotAvailException.REJECT_IS_OCCUPIED.message";
    public static final String REJECT_IS_NOT_OCCUPIED = "DesktopNotAvailException.REJECT_IS_NOT_OCCUPIED.message";

    public DesktopNotAvailException(String errorMessageTpl, Integer desktopNumber) {
        super(errorMessageTpl, new Object[]{desktopNumber});
    }

    public static Supplier<DesktopNotAvailException> newDesktopNotAvailException(String errorMessage, Integer desktopID) {
        return () -> new DesktopNotAvailException(errorMessage, desktopID);
    }

}
