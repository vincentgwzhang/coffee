package tina.coffee.system.exceptions.menucategory;

import tina.coffee.system.exceptions.EntityBusinessException;

import java.util.function.Supplier;

public class MenuCategoryUpdateException extends EntityBusinessException {

    private static final String EXCEPTION_MESSAGE = "Menu category can not be updated";

    public MenuCategoryUpdateException() {
        super(EXCEPTION_MESSAGE);
    }

    public static Supplier<MenuCategoryUpdateException> newMenuCategoryCreateException() {
        return () -> new MenuCategoryUpdateException();
    }
}
