package tina.coffee.webmvc.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tina.coffee.business.ImportProductService;
import tina.coffee.business.MenuCategoryService;
import tina.coffee.business.MenuItemService;
import tina.coffee.rest.dto.ImportProductDTO;
import tina.coffee.rest.dto.MenuCategoryDTO;
import tina.coffee.rest.dto.MenuItemDTO;

import java.util.List;

@Controller
@RequestMapping("/admin/menu/item")
@Secured("ROLE_ADMIN")
public class MenuItemController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MenuItemService service;

    @Autowired
    private ImportProductService importProductService;

    @Autowired
    private MenuCategoryService menuCategoryService;

    @ModelAttribute("menuitems")
    public List<MenuItemDTO> findAllDesktop(@RequestParam("mcId") Integer mcId) {
        logger.debug("{} triggered", "tina.coffee.webmvc.MenuItemController.findAllDesktop");
        logger.debug("parameter : {}", mcId);
        List<MenuItemDTO> menuItemDTOS = service.listAllMenuItemsByMenuCategory(mcId);
        return menuItemDTOS;
    }

    @ModelAttribute("menuCategories")
    public List<MenuCategoryDTO> findAllDesktop() {
        List<MenuCategoryDTO> categories = menuCategoryService.listAllCategories();
        return categories;
    }

    @ModelAttribute("importProducts")
    public List<ImportProductDTO> findAllImportProducts() {
        List<ImportProductDTO> importProductDTOS = importProductService.findAll();
        return importProductDTOS;
    }

    @GetMapping
    public String menuCategoryPage(@RequestParam("mcId") Integer mcId, Model map) {
        logger.debug("{} triggered", "tina.coffee.webmvc.MenuItemController.menuCategoryPage");
        map.addAttribute("this_category_id", mcId);
        return "/admin/menu_item";
    }
}
