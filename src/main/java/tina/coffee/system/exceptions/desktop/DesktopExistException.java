package tina.coffee.system.exceptions.desktop;

import tina.coffee.system.exceptions.EntityExistException;

import java.util.function.Supplier;

public class DesktopExistException extends EntityExistException {

    private static final String errorMessage = "Desktop number %s already exist";

    public DesktopExistException(Integer desktopNumber) {
        super(String.format(errorMessage, desktopNumber));
    }

    public static Supplier<DesktopExistException> newDesktopExistException(Integer desktopNumber) {
        return () -> new DesktopExistException(desktopNumber);
    }

}
