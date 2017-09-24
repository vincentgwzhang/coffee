package tina.coffee.system.exceptions.menuitem;

import tina.coffee.system.exceptions.EntityBusinessException;

import java.util.function.Supplier;

public class MenuItemBusinessException extends EntityBusinessException {

    public static final String ERROR_DISABLED    = "Menu item is disabled";
    public static final String ERROR_NOT_CREATED = "Menu item can not be created";

    public MenuItemBusinessException(String errorMessage) {
        super(errorMessage);
    }

    public static Supplier<MenuItemBusinessException> newMenuItemBusinessException(String errorMessage) {
        return () -> new MenuItemBusinessException(errorMessage);
    }

}
