package tina.coffee.webmvc.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tina.coffee.business.ImportHistoryService;
import tina.coffee.business.ImportProductService;
import tina.coffee.rest.dto.ImportHistoryDTO;
import tina.coffee.rest.dto.ImportProductTraceDTO;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 这是查询该物品的进货记录的
 */
@Controller
@RequestMapping("/admin/importproduct/trace")
@Secured("ROLE_ADMIN")
public class ImportProductTraceController {

    @Autowired
    private ImportProductService importProductService;

    @Autowired
    private ImportHistoryService service;

    @ModelAttribute("records")
    public List<ImportProductTraceDTO> retrieveImportRecordsForSpecProduct(@RequestParam(name="prd_id", required=true) Integer prdID) {
        return service.getImportHistoryByImportProduct(prdID);
    }

    @GetMapping
    public String showPage() {
        return "/admin/import_records";
    }

}
