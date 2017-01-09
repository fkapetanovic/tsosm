package portal.test.unit.security;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import portal.config.AppPropKeys;
import portal.domain.impl.User;
import portal.security.filters.CustomUsernamePasswordAuthenticationFilter;
import portal.service.UserService;
import portal.util.Helper;

public class CustomUsernamePasswordAuthenticationFilterTest {
	@Autowired
	@InjectMocks
	private CustomUsernamePasswordAuthenticationFilter filter;

	@Mock
	private UserService userService;

	@Mock
	private HttpServletRequest request;

	private final Properties APP_PROPERTIES = Helper.getAppProperties();
	private final int MAX_PASSWORD_LENGTH = Integer.parseInt
			(APP_PROPERTIES.getProperty(AppPropKeys.MAX_PASSWORD_LENGTH));

	@BeforeMethod
	public void setUp(){
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void setDetailsOKTest(){
		String userName = "username";
		String password = "password";
		User user = new User();

		when(request.getParameter("j_username")).thenReturn(userName);
		when(request.getParameter("j_password")).thenReturn(password);
		when(userService.getUserByUsername(userName)).thenReturn(user);

		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userName, password);

		filter.setDetails(request, token);

		verify(request).getParameter("j_username");
		verify(request).getParameter("j_password");
		verify(userService).getUserByUsername(userName);
		verify(userService).managePreAuthorizationAccountLocks(user);

		Assert.assertEquals(token.getCredentials(), password);
	}

	@Test
	public void setDetailsPasswordTooLongTest(){
		User user = new User();
		String userName = "username";
		String password  = new String(new char[MAX_PASSWORD_LENGTH * 2]).replace('\0', 'x');

		when(request.getParameter("j_username")).thenReturn(userName);
		when(request.getParameter("j_password")).thenReturn(password);
		when(userService.getUserByUsername(userName)).thenReturn(user);

		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userName, password);

		filter.setDetails(request, token);

		verify(request).getParameter("j_username");
		verify(request).getParameter("j_password");
		verify(userService).getUserByUsername(userName);
		verify(userService).managePreAuthorizationAccountLocks(user);
	}

	@Test
	public void setDetailsPasswordUserExistsNotTest(){
		String userName = "username123";
		String password  = "password";

		when(request.getParameter("j_username")).thenReturn(userName);
		when(request.getParameter("j_password")).thenReturn(password);
		when(userService.getUserByUsername(userName)).thenReturn(null);

		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userName, password);

		filter.setDetails(request, token);

		verify(userService).getUserByUsername(userName);
		verify(userService, times(0)).managePreAuthorizationAccountLocks(any(User.class));
	}
}
