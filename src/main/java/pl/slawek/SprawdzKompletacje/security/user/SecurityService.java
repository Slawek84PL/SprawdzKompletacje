package pl.slawek.SprawdzKompletacje.security.user;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.server.VaadinServletRequest;
import com.vaadin.flow.spring.security.AuthenticationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {
    private static final String LOGOUT_SUCCESS_PATH = "/";
    private final transient AuthenticationContext authenticationContext;

    public SecurityService(final AuthenticationContext authenticationContext) {
        this.authenticationContext = authenticationContext;
    }

    public UserDetails getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AppUser user = (AppUser) authentication.getPrincipal();
        if (user != null) {
            return user;
        }

        return null;
    }

    public boolean isAuthenticated() {
        return authenticationContext.isAuthenticated();
    }

    public void logout() {
        UI.getCurrent().getPage().setLocation(LOGOUT_SUCCESS_PATH);
        SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
        logoutHandler.logout(VaadinServletRequest.getCurrent().getHttpServletRequest(),null, null);
    }
}
