package tina.coffee.system.exceptions.menucategory;

import tina.coffee.system.exceptions.EntityBusinessException;

public class MenuCategoryBusinessException extends EntityBusinessException {

    public static final String EXCEPTION_MESSAGE_HAVING_MENU_ITEMS = "Menu category has sub menu items";

    public MenuCategoryBusinessException(String errorMessage) {
        super(errorMessage);
    }

}
