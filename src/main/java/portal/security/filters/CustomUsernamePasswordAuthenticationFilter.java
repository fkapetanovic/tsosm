package portal.security.filters;

import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.Assert;

import portal.config.AppPropKeys;
import portal.config.MessageKeys;
import portal.domain.impl.User;
import portal.service.UserService;
import portal.util.Helper;
import portal.validation.InputCheck;

public class CustomUsernamePasswordAuthenticationFilter extends
		UsernamePasswordAuthenticationFilter {
	@Autowired
	private UserService userService;

	@Autowired
	private MessageSource messageSource;
	private final Properties APP_PROPERTIES = Helper.getAppProperties();
	private final int MAX_PASSWORD_LENGTH = Integer.parseInt
			(APP_PROPERTIES.getProperty(AppPropKeys.MAX_PASSWORD_LENGTH));

	@Override
	public void setDetails(HttpServletRequest request,
			UsernamePasswordAuthenticationToken authRequest) {
		String userName = obtainUsername(request);

		if (InputCheck.isValidUserName(userName)) {
			tryToClearAccountLockAndFailedAuthCounter(userName);
		}
		else{
			String msg = messageSource.getMessage(
					MessageKeys.BAD_CREDENTIALS, null, null);
			throw new BadCredentialsException(msg);
		}

		String password = obtainPassword(request);

		if (password.length() > MAX_PASSWORD_LENGTH) {
			password = password.substring(0, MAX_PASSWORD_LENGTH);
		}

		Assert.isTrue(password.length() <=  MAX_PASSWORD_LENGTH);

		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
				userName, password);

		super.setDetails(request, token);
	}

	private void tryToClearAccountLockAndFailedAuthCounter(String userName) {
		User user = userService.getUserByUsername(userName);

		if (user != null){
			userService.managePreAuthorizationAccountLocks(user);
		}
	}
}
