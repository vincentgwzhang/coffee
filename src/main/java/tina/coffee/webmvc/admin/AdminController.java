package tina.coffee.webmvc.admin;

import org.perf4j.aop.Profiled;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Secured("ROLE_ADMIN")
public class AdminController {

    @GetMapping("/admin")
    @Profiled
    public String admin() {
        return "admin";
    }

}
