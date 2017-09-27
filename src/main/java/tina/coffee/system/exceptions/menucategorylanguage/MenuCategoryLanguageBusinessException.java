package tina.coffee.system.exceptions.menucategorylanguage;

import tina.coffee.system.exceptions.EntityBusinessException;

public class MenuCategoryLanguageBusinessException extends EntityBusinessException {

    private static final String errorMessageTmpl = "MenuCategoryLanguageBusinessException.message";

    public MenuCategoryLanguageBusinessException() {
        super(errorMessageTmpl, new Object[]{});
    }
}
