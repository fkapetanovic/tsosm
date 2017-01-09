package portal.test.unit.validation;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Properties;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.validation.Errors;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import portal.config.AppPropKeys;
import portal.config.MessageKeys;
import portal.domain.impl.User;
import portal.service.UserService;
import portal.util.Helper;
import portal.validation.UserValidator;

public class UserValidatorTest {
	@Mock
	private UserService userService;

	@Mock
	private Errors errors;

	@Mock
	private MessageSource messageSource;

	@Autowired
	@InjectMocks
	private UserValidator validator;

	private final Properties APP_PROPERTIES = Helper.getAppProperties();

	@BeforeMethod
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void validateUserEmptyFieldsTest(){
		User user = new User();
		user.setUsername("");
		user.setPassword("");
		user.setRetypePassword("");
		user.seteMailAddress("");

		validator.validate(user, errors);

		verify(errors).rejectValue("username", MessageKeys.FIELD_REQUIRED);
		verify(errors).rejectValue("password", MessageKeys.FIELD_REQUIRED);
		verify(errors).rejectValue("retypePassword", MessageKeys.FIELD_REQUIRED);
		verify(errors).rejectValue("eMailAddress", MessageKeys.FIELD_REQUIRED);
	}

	@Test
	public void validateUserInvalidDataTest(){
		String message = "Some message.";

		User user = new User();
		user.setUsername("'username");
		user.setPassword("short");
		user.setRetypePassword("sh0rt");
		user.seteMailAddress("m'ail@mail.com");

		when(messageSource.getMessage(MessageKeys.USERNAME_FIRST_CHARACTER_INVALID,
				null, null)).thenReturn(message);

		validator.validate(user, errors);

		verify(errors).rejectValue("username", null, message);
		verify(errors).rejectValue("password", MessageKeys.PASSWORD_TOO_SHORT, new Object[] {
				String.valueOf(APP_PROPERTIES.getProperty(AppPropKeys.MIN_PASSWORD_LENGTH))},
				null);
		verify(errors).rejectValue("retypePassword", MessageKeys.RETYPE_PASS_AND_PASS_DONT_MATCH);
		verify(errors).rejectValue("eMailAddress", MessageKeys.EMAIL_INVALID);
	}

	@Test
	public void validateUserUserExistsMailExistsPasTooSimilarToUsernameTest(){
		User user = new User();
		user.setUsername("username");
		user.setPassword("xxusernAmExx");
		user.setRetypePassword("xxusernAmExx");
		user.seteMailAddress("mail@mail.com");

		when(userService.userExists("username")).thenReturn(true);
		when(userService.emailExists("mail@mail.com")).thenReturn(true);

		validator.validate(user, errors);

		verify(errors).rejectValue("username", MessageKeys.USERNAME_EXISTS);
		verify(errors).rejectValue("password", MessageKeys.PASS_SIMILAR_TO_USERNAME);
		verify(errors).rejectValue("eMailAddress", MessageKeys.EMAIL_EXISTS);
	}

	@Test
	public void validateUserReservedUserNameTest(){
		User user = new User();
		user.setUsername("admin");
		
		validator.validate(user, errors);

		verify(errors).rejectValue("username", MessageKeys.USERNAME_RESERVED);
	}
}
