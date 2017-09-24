package tina.coffee.system.exceptions.menucategory;

import tina.coffee.system.exceptions.EntityBusinessException;

import java.util.function.Supplier;

public class MenuCategoryCreateException extends EntityBusinessException {

    private static final String EXCEPTION_MESSAGE = "Menu category can not be created";

    public MenuCategoryCreateException() {
        super(EXCEPTION_MESSAGE);
    }

    public static Supplier<MenuCategoryCreateException> newMenuCategoryCreateException() {
        return () -> new MenuCategoryCreateException();
    }
}
