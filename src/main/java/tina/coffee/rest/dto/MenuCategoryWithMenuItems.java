package tina.coffee.rest.dto;

import tina.coffee.function.JsonFunction;

import java.util.List;

public class MenuCategoryWithMenuItems {

    private MenuCategoryDTO menuCategoryDTO;

    private List<MenuItemDTO> menuItemDTOList;

    public MenuCategoryDTO getMenuCategoryDTO() {
        return menuCategoryDTO;
    }

    public void setMenuCategoryDTO(MenuCategoryDTO menuCategoryDTO) {
        this.menuCategoryDTO = menuCategoryDTO;
    }

    public List<MenuItemDTO> getMenuItemDTOList() {
        return menuItemDTOList;
    }

    public void setMenuItemDTOList(List<MenuItemDTO> menuItemDTOList) {
        this.menuItemDTOList = menuItemDTOList;
    }

    @Override
    public String toString() {
        return JsonFunction.toJson(this);
    }
}
