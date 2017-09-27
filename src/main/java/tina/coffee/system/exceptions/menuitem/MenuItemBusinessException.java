package tina.coffee.system.exceptions.menuitem;

import tina.coffee.system.exceptions.EntityBusinessException;

import java.util.function.Supplier;

public class MenuItemBusinessException extends EntityBusinessException {

    public static final String ERROR_DISABLED    = "MenuItemBusinessException.ERROR_DISABLED.message";
    public static final String ERROR_NOT_CREATED = "MenuItemBusinessException.ERROR_NOT_CREATED.message";

    public MenuItemBusinessException(String errorMessage) {
        super(errorMessage, new Object[]{});
    }

    public static Supplier<MenuItemBusinessException> newMenuItemBusinessException(String errorMessage) {
        return () -> new MenuItemBusinessException(errorMessage);
    }

}
