package tina.coffee.system.exceptions.menuitem;

import tina.coffee.system.exceptions.EntityNotFoundException;

import java.util.function.Supplier;

public class MenuItemNotFoundException extends EntityNotFoundException {

    private static final String errorMessageTmpl = "MenuItemNotFoundException.message";

    public MenuItemNotFoundException() {
        super(errorMessageTmpl, new Object[]{});
    }

    public static Supplier<MenuItemNotFoundException> newMenuItemNotFoundException() {
        return () -> new MenuItemNotFoundException();
    }

}
