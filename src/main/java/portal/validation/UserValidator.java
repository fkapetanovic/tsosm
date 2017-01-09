package portal.validation;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import portal.config.AppPropKeys;
import portal.config.MessageKeys;
import portal.domain.impl.User;
import portal.service.UserService;
import portal.util.Helper;
import portal.util.StringUtil;

@Component
public class UserValidator implements Validator {
	@Autowired
	private UserService userService;

	@Autowired
	private MessageSource messageSource;

	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.isAssignableFrom(clazz);
	}

	private final Properties APP_PROPERTIES = Helper.getAppProperties();

	@Override
	public void validate(Object target, Errors errors) {
		User user = (User) target;

		String userName = user.getUsername();
		String password = user.getPassword();
		String retypePassword = user.getRetypePassword();
		String eMailAddress = user.geteMailAddress();

		if (StringUtil.isNullEmptyOrAllWhiteSpace(userName)) {
			errors.rejectValue("username", MessageKeys.FIELD_REQUIRED);
		} else if (!InputCheck.isValidUserName(userName)) {
			errors.rejectValue("username", null, getReasonWhyInvalid(userName));
		} else if (InputCheck.isReservedUserName(userName)) {
			errors.rejectValue("username", MessageKeys.USERNAME_RESERVED);
		} else if (userService.userExists(userName)) {
			errors.rejectValue("username", MessageKeys.USERNAME_EXISTS);
		}

		if (StringUtil.isNullOrEmpty(password)) {
			errors.rejectValue("password", MessageKeys.FIELD_REQUIRED);
		} else if (!InputCheck.isValidPassword(password)) {
			errors.rejectValue("password", MessageKeys.PASSWORD_TOO_SHORT,
					new Object[] { String.valueOf(APP_PROPERTIES
							.getProperty(AppPropKeys.MIN_PASSWORD_LENGTH)) },
					null);
		} else if (password.toLowerCase().contains(userName.toLowerCase())) {
			errors.rejectValue("password", MessageKeys.PASS_SIMILAR_TO_USERNAME);
		}

		if (StringUtil.isNullOrEmpty(retypePassword)) {
			errors.rejectValue("retypePassword", MessageKeys.FIELD_REQUIRED);
		} else if (!retypePassword.equals(password)) {
			errors.rejectValue("retypePassword",
					MessageKeys.RETYPE_PASS_AND_PASS_DONT_MATCH);
		}

		if (StringUtil.isNullEmptyOrAllWhiteSpace(eMailAddress)) {
			errors.rejectValue("eMailAddress", MessageKeys.FIELD_REQUIRED);
		} else if (!InputCheck.isValidEmailAddress(eMailAddress)) {
			errors.rejectValue("eMailAddress", MessageKeys.EMAIL_INVALID);
		} else if (userService.emailExists(eMailAddress)) {
			errors.rejectValue("eMailAddress", MessageKeys.EMAIL_EXISTS);
		}
	}

	private String getReasonWhyInvalid(String userName) {
		String reason = "Invalid username.";

		if (userName.length() < Integer.parseInt(APP_PROPERTIES
				.getProperty(AppPropKeys.MIN_USERNAME_LENGTH))
				|| userName.length() > Integer.parseInt(APP_PROPERTIES
						.getProperty(AppPropKeys.MAX_USERNAME_LENGTH))) {
			reason = messageSource
					.getMessage(
							MessageKeys.USERNAME_INVALID_SIZE,
							new Object[] {
									String.valueOf(APP_PROPERTIES
											.getProperty(AppPropKeys.MIN_USERNAME_LENGTH)),
									String.valueOf(APP_PROPERTIES
											.getProperty(AppPropKeys.MAX_USERNAME_LENGTH)) },
							null);
		} else if (!userName.substring(0, 1).matches("[A-Za-z]")) {
			reason = messageSource.getMessage(
					MessageKeys.USERNAME_FIRST_CHARACTER_INVALID, null, null);
		} else if (!userName.substring(userName.length() - 1).matches(
				"[a-zA-Z0-9]")) {
			reason = messageSource.getMessage(
					MessageKeys.USERNAME_LAST_CHARACTER_INVALID, null, null);
		} else if (!userName.matches("^[a-zA-Z0-9_-]*$")) {
			reason = messageSource.getMessage(
					MessageKeys.USERNAME_ILLEGAL_CHARACTERS, null, null);
		} else {
			reason = messageSource.getMessage(
					MessageKeys.USERNAME_CONSECUTIVE_SPECIAL_CHARACTERS, null,
					null);
		}
		return reason;
	}
}
