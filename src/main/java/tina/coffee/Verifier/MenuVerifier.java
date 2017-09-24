package tina.coffee.Verifier;

import tina.coffee.data.model.MenuCategoryEntity;
import tina.coffee.data.model.MenuItemEntity;
import tina.coffee.system.exceptions.menucategory.MenuCategoryNotFoundException;
import tina.coffee.system.exceptions.menuitem.MenuItemBusinessException;
import tina.coffee.system.exceptions.menuitem.MenuItemNotFoundException;

import java.util.Optional;

public class MenuVerifier {

    public static void verifyIfMenuItemExistOrThrow(Optional<MenuItemEntity> entity) {
        entity.orElseThrow(MenuItemNotFoundException.newMenuItemNotFoundException());
    }

    public static void verifyIfMenuCategoryExistOrThrow(Optional<MenuCategoryEntity> menuCategoryEntity) {
        menuCategoryEntity.orElseThrow(MenuCategoryNotFoundException.newMenuCategoryNotFoundException());
    }

    public static void verifyIfMenuItemEnabledOrThrow(Optional<MenuItemEntity> entity) {
        if(entity.isPresent()) {
            MenuItemEntity en = entity.get();
            if(!en.isMiEnable()) {
                throw new MenuItemBusinessException(MenuItemBusinessException.ERROR_DISABLED);
            }
        }
    }

    public static void newOrderMenuItemProcedure(Optional<MenuItemEntity> entity) {
        verifyIfMenuItemExistOrThrow(entity);
        verifyIfMenuItemEnabledOrThrow(entity);
    }
}
