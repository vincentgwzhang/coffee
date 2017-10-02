package tina.coffee.webmvc.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import tina.coffee.business.MenuCategoryService;
import tina.coffee.rest.dto.MenuCategoryDTO;

import java.util.List;

@Controller
@RequestMapping("/admin/menu/category")
@Secured("ROLE_ADMIN")
public class MenuCategoryController {

    @Autowired
    private MenuCategoryService service;

    @ModelAttribute("menuCategories")
    public List<MenuCategoryDTO> findAllDesktop() {
        List<MenuCategoryDTO> categories = service.listAllCategories();
        return categories;
    }

    @GetMapping
    public String menuCategoryPage() {
        return "admin/menu_category";
    }
}
