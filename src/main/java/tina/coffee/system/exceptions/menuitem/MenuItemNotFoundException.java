package tina.coffee.system.exceptions.menuitem;

import tina.coffee.system.exceptions.EntityNotFoundException;

import java.util.function.Supplier;

public class MenuItemNotFoundException extends EntityNotFoundException {

    private static final String errorMessage = "Menu item is not exist";

    public MenuItemNotFoundException() {
        super(errorMessage);
    }

    public static Supplier<MenuItemNotFoundException> newMenuItemNotFoundException() {
        return () -> new MenuItemNotFoundException();
    }

}
