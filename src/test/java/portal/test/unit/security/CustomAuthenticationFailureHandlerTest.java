package portal.test.unit.security;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import portal.config.AppPropKeys;
import portal.domain.impl.User;
import portal.security.filters.CustomAuthenticationFailureHandler;
import portal.service.UserService;
import portal.util.Helper;

public class CustomAuthenticationFailureHandlerTest {
	@Mock
	private UserService userService;

	@Mock
	private HttpServletRequest request;

	@Mock
	private HttpServletResponse response;

	private BadCredentialsException exception = new BadCredentialsException("");

	@Autowired
	@InjectMocks
	private CustomAuthenticationFailureHandler filter;

	private final Properties APP_PROPERTIES = Helper.getAppProperties();
	private final String SPRING_SECURITY_FORM_USERNAME_KEY = APP_PROPERTIES
			.getProperty(AppPropKeys.SPRING_SECURITY_FORM_USERNAME_KEY);

	@BeforeMethod
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void onAuthenticationFailureOKTest() throws IOException, ServletException {
		String userName = "userName";
		User user = new User();

		when(request.getParameter(SPRING_SECURITY_FORM_USERNAME_KEY)).thenReturn(userName);
		when(userService.getUserByUsername(userName)).thenReturn(user);

		filter.onAuthenticationFailure(request, response, exception);

		verify(request).getParameter(SPRING_SECURITY_FORM_USERNAME_KEY);
		verify(userService).getUserByUsername(userName);
		verify(userService).manageAccountLocksOnAuthorizationFailure(user);
	}

	@Test
	public void onAuthenticationFailureUserNotFoundTest() throws IOException, ServletException {
		String userName = "userName";
		when(request.getParameter(SPRING_SECURITY_FORM_USERNAME_KEY)).thenReturn(userName);
		when(userService.getUserByUsername(userName)).thenReturn(null);

		filter.onAuthenticationFailure(request, response, exception);

		verify(request).getParameter(SPRING_SECURITY_FORM_USERNAME_KEY);
		verify(userService).getUserByUsername(userName);
		verify(userService, times(0)).manageAccountLocksOnAuthorizationFailure(any(User.class));
	}

	@Test
	public void onAuthenticationFailureInvalidUserNameTest() throws IOException, ServletException {
		String userName = "invalid username";
		when(request.getParameter(SPRING_SECURITY_FORM_USERNAME_KEY)).thenReturn(userName);

		filter.onAuthenticationFailure(request, response, exception);

		verify(request).getParameter(SPRING_SECURITY_FORM_USERNAME_KEY);
		verify(userService, times(0)).getUserByUsername(userName);
		verify(userService, times(0)).manageAccountLocksOnAuthorizationFailure(any(User.class));
	}
}
