package tina.coffee.webmvc.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import tina.coffee.business.DesktopService;
import tina.coffee.rest.dto.DesktopDTO;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("admin")
@Secured("ROLE_ADMIN")
public class DesktopController {

    @Autowired
    private DesktopService service;

    /**
     * General management page
     */
    @ModelAttribute("desktops")
    public List<DesktopDTO> findAllDesktop() {
        // desktop number can not less than 1, otherwise it shows very strange
        return service.findAllOnSiteDesktop();
    }

    @GetMapping("/desktop")
    public String desktopPage() {
        return "/admin/desktop";
    }

}
