package tina.coffee.system.exceptions.menucategory;

import tina.coffee.system.exceptions.EntityNotFoundException;

import java.util.function.Supplier;

public class MenuCategoryNotFoundException extends EntityNotFoundException {

    private static final String errorMessage = "Menu Category is not exist";

    public MenuCategoryNotFoundException() {
        super(errorMessage);
    }

    public static Supplier<MenuCategoryNotFoundException> newMenuCategoryNotFoundException() {
        return () -> new MenuCategoryNotFoundException();
    }

}
