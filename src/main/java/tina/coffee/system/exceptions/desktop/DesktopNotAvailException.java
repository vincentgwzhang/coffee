package tina.coffee.system.exceptions.desktop;

import tina.coffee.system.exceptions.EntityBusinessException;

import java.util.function.Supplier;

public class DesktopNotAvailException extends EntityBusinessException {

    public static final String REJECT_IS_DISABLED     = "desktop %s is disabled";
    public static final String REJECT_IS_OCCUPIED     = "desktop %s has customer";
    public static final String REJECT_IS_NOT_OCCUPIED = "desktop %s has no customer";

    public DesktopNotAvailException(String errorMessage, Integer desktopNumber) {
        super(String.format(errorMessage, desktopNumber));
    }

    public static Supplier<DesktopNotAvailException> newDesktopNotAvailException(String errorMessage, Integer desktopID) {
        return () -> new DesktopNotAvailException(errorMessage, desktopID);
    }

}
