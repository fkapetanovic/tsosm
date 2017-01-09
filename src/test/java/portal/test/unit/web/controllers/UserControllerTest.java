package portal.test.unit.web.controllers;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpSession;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import portal.config.MessageKeys;
import portal.domain.impl.User;
import portal.security.context.SecurityContextFacade;
import portal.service.UserService;
import portal.service.exceptions.BadActivationException;
import portal.service.exceptions.BadPasswordChangeRequestException;
import portal.util.web.WebHelper;
import portal.web.context.RequestContextFacade;
import portal.web.controllers.UserController;

public class UserControllerTest {
	@Autowired
	@InjectMocks
	private UserController controller;

	@Mock
	private UserService userService;

	@Mock
	private SecurityContextFacade securityContext;

	@Mock
	private RequestContextFacade requestContext;

	@Mock
	private HttpSession session;

	@Mock
	private MessageSource messageSource;

	@Mock
	private WebHelper webHelper;

	@Mock
	private BindingResult bindingResult;

	@BeforeMethod
	public void setUpMocks() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void handleLoginRequestAnonymousTest() {
		when(securityContext.isAnonymousUser()).thenReturn(true);

		String viewName = controller.handleLoginRequest();

		verify(securityContext).isAnonymousUser();
		Assert.assertEquals(viewName, "login");
	}

	@Test
	public void handleLoginRequestAuthorizedTest() {
		when(securityContext.isAnonymousUser()).thenReturn(false);

		String viewName = controller.handleLoginRequest();

		verify(securityContext).isAnonymousUser();
		Assert.assertEquals(viewName, "redirect:/");
	}

	@Test
	public void handleNewUserRequestTest() {
		ModelMap model = new ModelMap();

		String viewName = controller.handleNewUserRequest(model);

		Assert.assertEquals(viewName, "user");
		Assert.assertNotNull(model.get("user"));
		Assert.assertTrue(model.get("user") instanceof User);
	}

	@Test
	public void handleNewUserSubmitDataOKTest() {
		User user = new User();

		when(bindingResult.hasErrors()).thenReturn(false);

		String viewName = controller.handleNewUserSubmit(user, bindingResult);

		verify(userService).insertUser(user);
		verify(webHelper).putInfoMessageIntoSessionByKey(MessageKeys.USER_SUCCESSFULLY_CREATED);
		Assert.assertEquals(viewName, "redirect:/info/");
	}

	@Test
	public void handleNewUserSubmitInValidDataTest() {
		User user = new User();

		when(bindingResult.hasErrors()).thenReturn(true);
		when(requestContext.session()).thenReturn(session);

		String viewName = controller.handleNewUserSubmit(user, bindingResult);

		Assert.assertEquals(viewName, "user");
	}

	@Test
	public void handleActivateAccountRequestParametersOkTest() {
		String userName = "Some username";
		String activationCode = "Some activation code";

		String viewName = controller.handleActivateAccountRequest(userName, activationCode);

		verify(userService).activateAccount(userName, activationCode);
		verify(webHelper).putInfoMessageIntoSessionByKey(MessageKeys.ACCOUNT_ACTIVATED);
		Assert.assertEquals(viewName, "redirect:/info/");
	}

	@Test
	public void handleActivateAccountRequestInvalidParametersTest() {
		String userName = "Some username";
		String activationCode = "Some activation code";
		String exceptionMessage = "Invalid code or username.";

		doThrow(new BadActivationException(exceptionMessage)).when(userService)
				.activateAccount(userName, activationCode);

		String viewName = controller.handleActivateAccountRequest(userName, activationCode);

		verify(userService).activateAccount(userName, activationCode);
		verify(webHelper).putInfoMessageIntoSession(exceptionMessage);
		Assert.assertEquals(viewName, "redirect:/info/");
	}

	@Test
	public void handleResetPasswordRequestTest() {
		Assert.assertEquals(controller.handleResetPasswordRequest(), "resetPassword");
	}

	@Test
	public void handleResetPasswordSubmitEMailOKTest() {
		String eMail = "existing@mail.com";
		ModelMap model = new ModelMap();

		when(userService.emailExists(eMail)).thenReturn(true);

		String viewName = controller.handleResetPasswordSubmit(eMail, model);

		verify(userService).emailExists(eMail);
		verify(userService).initiatePasswordChange(eMail);
		verify(webHelper).putInfoMessageIntoSessionByKey(MessageKeys.PASSWORD_EMAIL_SENT);
		Assert.assertEquals(viewName, "redirect:/info/");
	}

	@Test
	public void handleResetPasswordSubmitEMailEmptyTest() {
		String eMail = "";
		String message = "This field is required.";
		ModelMap model = new ModelMap();

		when(messageSource.getMessage(MessageKeys.FIELD_REQUIRED, null, null)).thenReturn(message);

		String viewName = controller.handleResetPasswordSubmit(eMail, model);

		Assert.assertEquals(viewName, "resetPassword");
		Assert.assertEquals(model.get("message"), message);
	}

	@Test
	public void handleResetPasswordSubmitInvalidEMailTest() {
		String eMail = "invalidEmail@";
		String message = "Invalid e-mail address. Example of valid e-mail address: example@mail.com";
		ModelMap model = new ModelMap();

		when(messageSource.getMessage(MessageKeys.EMAIL_INVALID, null, null)).thenReturn(message);

		String viewName = controller.handleResetPasswordSubmit(eMail, model);

		Assert.assertEquals(viewName, "resetPassword");
		Assert.assertEquals(model.get("message"), message);
	}

	@Test
	public void handleResetPasswordSubmitEMailExistsNotTest() {
		String eMail = "doesnotexist@mail.com";
		String message = "E-mail address does not exist.";
		ModelMap model = new ModelMap();

		when(messageSource.getMessage(MessageKeys.EMAIL_EXISTS_NOT, null, null)).thenReturn(message);
		when(userService.emailExists(eMail)).thenReturn(false);

		String viewName = controller.handleResetPasswordSubmit(eMail, model);

		verify(userService).emailExists(eMail);
		Assert.assertEquals(viewName, "resetPassword");
		Assert.assertEquals(model.get("message"), message);
	}

	@Test
	public void handleChangePasswordRequestParametersOKTest() {
		String userName = "userName";
		String passwordChangeCode = "Password change code";
		ModelMap model = new ModelMap();

		when(userService.isPasswordChangeRequestValid(userName,passwordChangeCode)).thenReturn(true);

		String viewName = controller.handleChangePasswordRequest(userName, passwordChangeCode, model);

		verify(userService).isPasswordChangeRequestValid(userName,passwordChangeCode);
		Assert.assertEquals(viewName, "changePassword");
		Assert.assertEquals(model.get("userName"), userName);
		Assert.assertEquals(model.get("passwordChangeCode"), passwordChangeCode);
	}

	@Test
	public void handleChangePasswordRequestInvalidParametersTest() {
		String userName = "Invalid user name";
		String passwordChangeCode = "Invalid password change code";
		ModelMap model = new ModelMap();

		when(userService.isPasswordChangeRequestValid(userName, passwordChangeCode)).thenReturn(false);

		String viewName = controller.handleChangePasswordRequest(userName, passwordChangeCode, model);

		verify(userService).isPasswordChangeRequestValid(userName, passwordChangeCode);
		verify(webHelper).putInfoMessageIntoSessionByKey(MessageKeys.INVALID_CODE_OR_USERNAME);
		Assert.assertEquals(viewName, "redirect:/info/");
	}

	@Test
	public void handleChangePasswordSubmitParametersOK() {
		String passwordChangeCode = "valid password change code";
		String userName = "valid user name";
		String newPassword = "password321";
		String retypePassword = "password321";
		ModelMap model = new ModelMap();

		String viewName = controller.handleChangePasswordSubmit(userName,
				passwordChangeCode, newPassword, retypePassword, model);

		verify(userService).changePassword(userName, passwordChangeCode, newPassword);
		verify(webHelper).putInfoMessageIntoSessionByKey(MessageKeys.PASSWORD_RESET_SUCCESS);
		Assert.assertEquals(viewName, "redirect:/info/");
	}

	@Test
	public void handleChangePasswordSubmitUsernameAndCodeInvalidTest() {
		String passwordChangeCode = "invalid password change code";
		String userName = "invalid user name";
		String newPassword = "password321";
		String retypePassword = "password321";
		String exceptionMessage = "Invalid code or username.";
		ModelMap model = new ModelMap();

		doThrow(new BadPasswordChangeRequestException(exceptionMessage)).when(
				userService).changePassword(userName, passwordChangeCode,
				newPassword);

		String viewName = controller.handleChangePasswordSubmit(userName,
				passwordChangeCode, newPassword, retypePassword, model);

		verify(userService).changePassword(userName, passwordChangeCode, newPassword);
		verify(webHelper).putInfoMessageIntoSession(exceptionMessage);
		Assert.assertEquals(viewName, "redirect:/info/");
	}

	@Test
	public void handleChangePasswordSubmitPassAndRTPassEmptyTest() {
		String passwordChangeCode = "valid password change code";
		String userName = "valid user name";
		String newPassword = "";
		String retypePassword = "";
		String message = "This field is required.";
		ModelMap model = new ModelMap();

		when(messageSource.getMessage(MessageKeys.FIELD_REQUIRED, null, null)).thenReturn(message);

		String viewName = controller.handleChangePasswordSubmit(userName,
				passwordChangeCode, newPassword, retypePassword, model);

		Assert.assertEquals(viewName, "changePassword");
		Assert.assertEquals(model.get("error1"), message);
		Assert.assertEquals(model.get("error2"), message);
	}

	@Test
	public void handleChangePasswordSubmitPassShortRTPassMatchesNotTest() {
		String passwordChangeCode = "valid password change code";
		String userName = "valid user name";
		String newPassword = "pswd1";
		String retypePassword = "psswrd1";
		String message1 = "Password must be at least 8 characters long.";
		String message2 = "Password and Retype password fields don't match.";
		ModelMap model = new ModelMap();

		when(messageSource.getMessage(MessageKeys.PASSWORD_TOO_SHORT,
					new Object[]{String.valueOf(8)}, null)).thenReturn(message1);

		when(messageSource.getMessage(MessageKeys.RETYPE_PASS_AND_PASS_DONT_MATCH, null, null))
			.thenReturn(message2);

		String viewName = controller.handleChangePasswordSubmit(userName,
				passwordChangeCode, newPassword, retypePassword, model);

		Assert.assertEquals(viewName, "changePassword");
		Assert.assertEquals(model.get("error1"), message1);
		Assert.assertEquals(model.get("error2"), message2);
	}

	@Test
	public void handleChangePasswordSubmitPassToSimilarToUsernameTest() {
		String passwordChangeCode = "valid password change code";
		String userName = "username";
		String newPassword = "xxUsErNamExx";
		String retypePassword = "username";
		String message = "Your password is too similar to your username.";
		ModelMap model = new ModelMap();

		when(messageSource.getMessage(MessageKeys.PASS_SIMILAR_TO_USERNAME,null, null))
			.thenReturn(message);

		String viewName = controller.handleChangePasswordSubmit(userName,
				passwordChangeCode, newPassword, retypePassword, model);

		Assert.assertEquals(viewName, "changePassword");
		Assert.assertEquals(model.get("error1"), message);
	}
}
