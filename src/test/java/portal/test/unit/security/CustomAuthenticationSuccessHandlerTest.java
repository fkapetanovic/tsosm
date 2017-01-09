package portal.test.unit.security;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import portal.domain.impl.User;
import portal.security.context.SecurityContextFacade;
import portal.security.filters.CustomAuthenticationSuccessHandler;
import portal.service.UserService;

public class CustomAuthenticationSuccessHandlerTest {
	@Mock
	private UserService userService;

	@Mock
	private SecurityContextFacade securityContext;

	@Mock
	private HttpServletRequest request;

	@Mock
	private HttpServletResponse response;

	@Mock
	private Authentication auth;

	@Autowired
	@InjectMocks
	private CustomAuthenticationSuccessHandler filter;

	@BeforeMethod
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void onAuthenticationSuccessOKTest() throws IOException, ServletException {
		User user = new User();
		when(securityContext.getLoggedInUser()).thenReturn(user);
		filter.onAuthenticationSuccess(request, response, auth);
		verify(userService).manageAccountLocksOnAuthorizationSuccess(user);
	}

	@Test
	public void onAuthenticationSuccessUserNullTest() throws IOException, ServletException {
		when(securityContext.getLoggedInUser()).thenReturn(null);
		filter.onAuthenticationSuccess(request, response, auth);
		verify(userService, times(0)).manageAccountLocksOnAuthorizationSuccess(any(User.class));
	}
}
