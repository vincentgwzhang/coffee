package tina.coffee.system.exceptions.desktop;

import tina.coffee.system.exceptions.EntityExistException;

import java.util.function.Supplier;

public class DesktopExistException extends EntityExistException {

    private static final String errorMessageTpl = "DesktopExistException.message";

    public DesktopExistException(Integer desktopNumber) {
        super(errorMessageTpl, new Object[]{desktopNumber});
    }

    public static Supplier<DesktopExistException> newDesktopExistException(Integer desktopNumber) {
        return () -> new DesktopExistException(desktopNumber);
    }

}
