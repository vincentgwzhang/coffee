package tina.coffee.webmvc;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DefaultController {

    @GetMapping("/")
    public String home1() {
        return "/home";
    }

    @GetMapping("/home")
    public String home() {
        return "/home";
    }

    @Secured("ROLE_CHIEF")
    @GetMapping("/chief")
    public String chief() {
        return "/chief";
    }

    @GetMapping("/about")
    public String about() {
        System.out.println("what happen? 2");
        return "/about";
    }

    @GetMapping("/login")
    public String login() {
        System.out.println("what happen? 1");
        return "/login";
    }

    @GetMapping("/accessdeny")
    public String error403() {
        return "/error/403";
    }

}
