package tina.coffee.system.interceptors;

import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import tina.coffee.system.security.service.CoffeeUserDetails;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This class is for save the user actions, for some highly security setting, it should:
 * 1, In CoffeeWebConfig class, read parameter and dynamic load this interceptor
 * 2, In config package, it should create a log object, could switch to any log file, or log appender as component
 * 3, In this interceptor class, it should use above object in 2) step, switch according to different user
 * 4, In preHandle method, it should record down detail user tracing his behavior.
 */
public class SecurityInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        WebAuthenticationDetails details = (WebAuthenticationDetails)authentication.getDetails();
        details.getRemoteAddress();
        details.getSessionId();

        CoffeeUserDetails coffeeUserDetails = (CoffeeUserDetails)authentication.getPrincipal();
        coffeeUserDetails.getUser().getUsername();
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {

    }
}
