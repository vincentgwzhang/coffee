package tina.coffee.system.security.handler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import tina.coffee.system.security.roles.Role;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collection;

@Component
public class CoffeeAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final Log logger = LogFactory.getLog(this.getClass());

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    public CoffeeAuthenticationSuccessHandler() {
        super();
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        handle(request, response, authentication);
        clearAuthenticationAttributes(request);
    }

    protected void handle(final HttpServletRequest request, final HttpServletResponse response, final Authentication authentication) throws IOException {
        final String targetUrl = determineTargetUrl(authentication);

        if (response.isCommitted()) {
            logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
            return;
        }

        redirectStrategy.sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(final Authentication authentication) {

        //Default admin > counter > operator > chief
        boolean isAdmin, isCounter, isOperator, isChief;
        String resultPage = null;
        final Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        isAdmin    = authorities.stream().filter(au -> au.getAuthority().equalsIgnoreCase(Role.ROLE_ADMIN.name())).findAny().isPresent();
        isCounter  = authorities.stream().filter(au -> au.getAuthority().equalsIgnoreCase(Role.ROLE_COUNTER.name())).findAny().isPresent();
        isOperator = authorities.stream().filter(au -> au.getAuthority().equalsIgnoreCase(Role.ROLE_OPERATOR.name())).findAny().isPresent();
        isChief    = authorities.stream().filter(au -> au.getAuthority().equalsIgnoreCase(Role.ROLE_CHIEF.name())).findAny().isPresent();

        if(isAdmin) {
            resultPage = "/admin";
        } else if (isCounter) {
            resultPage = "/counter";
        } else if (isOperator) {
            resultPage = "/operator/showdesktops";
        } else if( isChief ){
            resultPage = "/chief";
        } else {
            resultPage = "/login";
        }

        return resultPage;
    }

    protected final void clearAuthenticationAttributes(final HttpServletRequest request) {
        final HttpSession session = request.getSession(false);

        if (session == null) {
            return;
        }

        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }
}
