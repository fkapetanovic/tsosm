package portal.security.filters;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import portal.domain.impl.User;
import portal.security.context.SecurityContextFacade;
import portal.service.UserService;

public class CustomAuthenticationSuccessHandler extends
		SimpleUrlAuthenticationSuccessHandler {
	@Autowired
	private UserService userService;

	@Autowired
	private SecurityContextFacade securityContext;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication auth)
			throws IOException, ServletException {

		User user = securityContext.getLoggedInUser();

		if (user != null) {
			userService.manageAccountLocksOnAuthorizationSuccess(user);
		}

		super.onAuthenticationSuccess(request, response, auth);
	}
}
