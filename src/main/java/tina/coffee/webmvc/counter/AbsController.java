package tina.coffee.webmvc.counter;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Locale;

public class AbsController {

    @ModelAttribute("language")
    public String getCurrentLocal() {
        Locale locale = LocaleContextHolder.getLocale();
        return locale.getLanguage();
    }

    @ModelAttribute("local_language")
    public String getCurrentLocal2() {
        Locale locale = LocaleContextHolder.getLocale();
        return locale.toString();
    }

}
