package tina.coffee.system.exceptions.menuitem;

import tina.coffee.system.exceptions.EntityBusinessException;

import java.util.function.Supplier;

public class MenuItemUpdateException extends EntityBusinessException {

    private static final String errorMessageTmpl = "MenuItemUpdateException.message";

    public MenuItemUpdateException() {
        super(errorMessageTmpl, new Object[]{});
    }

    public static Supplier<MenuItemUpdateException> newMenuCategoryCreateException() {
        return () -> new MenuItemUpdateException();
    }
}
