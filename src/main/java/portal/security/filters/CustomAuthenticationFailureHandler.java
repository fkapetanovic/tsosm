package portal.security.filters;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import portal.config.AppPropKeys;
import portal.domain.impl.User;
import portal.service.UserService;
import portal.util.Helper;
import portal.validation.InputCheck;

public class CustomAuthenticationFailureHandler extends
		SimpleUrlAuthenticationFailureHandler {
	@Autowired
	private UserService userService;
	private final Properties APP_PROPERTIES = Helper.getAppProperties();

	@Override
	public void onAuthenticationFailure(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException exception)
			throws IOException, ServletException {
		String userName = request
				.getParameter(APP_PROPERTIES.getProperty
						(AppPropKeys.SPRING_SECURITY_FORM_USERNAME_KEY));

		if (userName != null && InputCheck.isValidUserName(userName)) {
			User user = userService.getUserByUsername(userName);

			if (user != null) {
				userService.manageAccountLocksOnAuthorizationFailure(user);
			}
		}
		super.onAuthenticationFailure(request, response, exception);
	}
}
