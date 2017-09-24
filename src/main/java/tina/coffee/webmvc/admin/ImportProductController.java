package tina.coffee.webmvc.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import tina.coffee.business.ImportProductService;
import tina.coffee.rest.dto.ImportProductDTO;

import java.util.List;

@Controller
@RequestMapping("/admin/importproduct/config")
@Secured("ROLE_ADMIN")
public class ImportProductController {

    @Autowired
    private ImportProductService service;

    @ModelAttribute("products")
    public List<ImportProductDTO> listAllImportProduct() {
        return service.findAll();
    }

    @GetMapping
    public String showPage() {
        return "/admin/import_product";
    }
}
