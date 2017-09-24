package tina.coffee.system.exceptions.menuitem;

import tina.coffee.system.exceptions.EntityBusinessException;

import java.util.function.Supplier;

public class MenuItemUpdateException extends EntityBusinessException {

    private static final String EXCEPTION_MESSAGE = "Menu item can not be updated";

    public MenuItemUpdateException() {
        super(EXCEPTION_MESSAGE);
    }

    public static Supplier<MenuItemUpdateException> newMenuCategoryCreateException() {
        return () -> new MenuItemUpdateException();
    }
}
