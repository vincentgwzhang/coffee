package tina.coffee.system.exceptions.menuitemlanguage;

import tina.coffee.system.exceptions.EntityBusinessException;

public class MenuItemLanguageBusinessException extends EntityBusinessException {

    private static final String errorMessageTmpl = "MenuItemLanguageBusinessException.message";

    public MenuItemLanguageBusinessException() {
        super(errorMessageTmpl, new Object[]{});
    }
}
