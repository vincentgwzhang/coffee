package tina.coffee.system.exceptions.menucategory;

import tina.coffee.system.exceptions.EntityBusinessException;

public class MenuCategoryBusinessException extends EntityBusinessException {

    private static final String errorMessageTmpl = "MenuCategoryBusinessException.message";

    public MenuCategoryBusinessException() {
        super(errorMessageTmpl, new Object[]{});
    }

}
