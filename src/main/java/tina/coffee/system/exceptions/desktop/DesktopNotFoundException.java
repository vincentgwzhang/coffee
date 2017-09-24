package tina.coffee.system.exceptions.desktop;

import tina.coffee.system.exceptions.EntityNotFoundException;

import java.util.function.Supplier;

public class DesktopNotFoundException extends EntityNotFoundException {

    private static final String errorMessage = "Desktop number %s not found";

    public DesktopNotFoundException(Integer desktopNumber) {
        super(String.format(errorMessage, desktopNumber));
    }

    public static Supplier<DesktopNotFoundException> newDesktopNotFoundException(Integer desktopNumber) {
        return () -> new DesktopNotFoundException(desktopNumber);
    }

}
