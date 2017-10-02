package tina.coffee.webmvc.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tina.coffee.business.ImportHistoryService;
import tina.coffee.business.ImportProductService;
import tina.coffee.rest.dto.ImportHistoryDTO;
import tina.coffee.rest.dto.ImportProductDTO;

import java.util.List;

@Controller
@RequestMapping("/admin/importproduct/day")
@Secured("ROLE_ADMIN")
public class ImportProductSpeDayController {

    @Autowired
    private ImportProductService importProductService;

    @Autowired
    private ImportHistoryService service;

    @ModelAttribute("products")
    public List<ImportProductDTO> allImportProducts() {
        return importProductService.findAll();
    }

    @ModelAttribute("import_product_list")
    public List<ImportHistoryDTO> importedProductListByDay(@RequestParam(name="date", required=true) String dateString) {
        return service.findAllByDate(dateString.replaceAll("_","-"));
    }

    @GetMapping
    public String showPage() {
        return "admin/import_bydate";
    }
}
