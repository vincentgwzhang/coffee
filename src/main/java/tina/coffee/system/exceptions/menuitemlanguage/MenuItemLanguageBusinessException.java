package tina.coffee.system.exceptions.menuitemlanguage;

import tina.coffee.system.exceptions.EntityBusinessException;

public class MenuItemLanguageBusinessException extends EntityBusinessException {

    public static final String ERROR_MESSAGE_DUPLICATE = "menu item language duplicate";

    public MenuItemLanguageBusinessException(String errorMessage) {
        super(errorMessage);
    }
}
