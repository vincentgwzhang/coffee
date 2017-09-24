package tina.coffee.system.exceptions.menucategorylanguage;

import tina.coffee.system.exceptions.EntityBusinessException;

public class MenuCategoryLanguageBusinessException extends EntityBusinessException {

    public static final String ERROR_MESSAGE_DUPLICATE = "menu category language duplicate";

    public MenuCategoryLanguageBusinessException(String errorMessage) {
        super(errorMessage);
    }
}
