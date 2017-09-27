package tina.coffee.system.exceptions.menuitem;

import tina.coffee.system.exceptions.EntityBusinessException;

import java.util.function.Supplier;

public class MenuItemAssociateOrdersException extends EntityBusinessException {

    private static final String errorMessageTmpl = "MenuItemAssociateOrdersException.message";

    public MenuItemAssociateOrdersException(String detail) {
        super(String.format(errorMessageTmpl, new Object[]{detail}));
    }

    public static Supplier<MenuItemAssociateOrdersException> newMenuItemBusinessException(String detail) {
        return () -> new MenuItemAssociateOrdersException(detail);
    }

}
