package tina.coffee.system.exceptions.desktop;

import tina.coffee.system.exceptions.EntityNotFoundException;

import java.util.function.Supplier;

public class DesktopNotFoundException extends EntityNotFoundException {

    private static final String errorMessageTpl = "DesktopNotFoundException.message";

    public DesktopNotFoundException(Integer desktopNumber) {
        super(errorMessageTpl, new Object[]{desktopNumber});
    }

    public static Supplier<DesktopNotFoundException> newDesktopNotFoundException(Integer desktopNumber) {
        return () -> new DesktopNotFoundException(desktopNumber);
    }

}
