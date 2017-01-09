package portal.web.controllers;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import portal.config.AppPropKeys;
import portal.config.MessageKeys;
import portal.domain.impl.User;
import portal.security.context.SecurityContextFacade;
import portal.service.UserService;
import portal.service.exceptions.BadActivationException;
import portal.service.exceptions.BadPasswordChangeRequestException;
import portal.util.Helper;
import portal.util.StringUtil;
import portal.util.web.WebHelper;
import portal.validation.InputCheck;
import portal.validation.UserValidator;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;

	@Autowired
	private SecurityContextFacade securityContext;

	@Autowired
	private UserValidator userValidator;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private WebHelper webHelper;

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(userValidator);
	}

	private final Properties APP_PROPERTIES = Helper.getAppProperties();

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String handleLoginRequest() {
		String viewName;

		if (securityContext.isAnonymousUser()) {
			viewName = "login";
		} else {
			viewName = "redirect:/";
		}

		return viewName;
	}

	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String handleNewUserRequest(ModelMap model) {
		model.put("user", new User());

		return "user";
	}

	@RequestMapping(value = "/new", method = RequestMethod.POST)
	public String handleNewUserSubmit(@Validated User user, BindingResult result) {
		String viewName;

		if (!result.hasErrors()) {
			userService.insertUser(user);
			webHelper.putInfoMessageIntoSessionByKey(MessageKeys.USER_SUCCESSFULLY_CREATED);
			viewName = "redirect:/info/";
		} else {
			viewName = "user";
		}

		return viewName;
	}

	@RequestMapping(method = RequestMethod.GET, value =
			"/activate/{userName}/{activationCode}")
	public String handleActivateAccountRequest(@PathVariable String userName,
			@PathVariable String activationCode) {
		String viewName = "redirect:/info/";

		try {
			userService.activateAccount(userName, activationCode);
			webHelper.putInfoMessageIntoSessionByKey(MessageKeys.ACCOUNT_ACTIVATED);
		} catch (BadActivationException e) {
			webHelper.putInfoMessageIntoSession(e.getMessage());
		}

		return viewName;
	}

	@RequestMapping(value = "/resetPassword", method = RequestMethod.GET)
	public String handleResetPasswordRequest() {
		return "resetPassword";
	}

	@RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
	public String handleResetPasswordSubmit(@RequestParam String eMail, ModelMap model) {
		boolean isEmailValid = validateEmail(eMail, model);
		String viewName;

		if (isEmailValid) {
			userService.initiatePasswordChange(eMail);
			webHelper.putInfoMessageIntoSessionByKey(MessageKeys.PASSWORD_EMAIL_SENT);
			viewName = "redirect:/info/";
		} else {
			viewName = "resetPassword";
		}

		return viewName;
	}

	@RequestMapping(method = RequestMethod.GET, value =
			"/changePassword/{userName}/{passwordChangeCode}")
	public String handleChangePasswordRequest(@PathVariable String userName,
			@PathVariable String passwordChangeCode, ModelMap model) {
		String viewName;

		if (userService.isPasswordChangeRequestValid(userName,passwordChangeCode)) {
			model.put("userName", userName);
			model.put("passwordChangeCode", passwordChangeCode);
			viewName = "changePassword";
		} else {
			webHelper.putInfoMessageIntoSessionByKey(MessageKeys.INVALID_CODE_OR_USERNAME);
			viewName = "redirect:/info/";
		}

		return viewName;
	}

	@RequestMapping(method = RequestMethod.POST, value =
			"/changePassword/{userName}/{passwordChangeCode}")
	public String handleChangePasswordSubmit(@PathVariable String userName,
			@PathVariable String passwordChangeCode, @RequestParam String password,
			@RequestParam String retypePassword, ModelMap model) {
		String viewName;
		boolean passwordFieldsValid = validatePasswordFields(password, retypePassword, userName, model);

		if (passwordFieldsValid) {
			viewName = "redirect:/info/";
			try {
				userService.changePassword(userName, passwordChangeCode, password);
				webHelper.putInfoMessageIntoSessionByKey(MessageKeys.PASSWORD_RESET_SUCCESS);
			} catch (BadPasswordChangeRequestException e) {
				webHelper.putInfoMessageIntoSession(e.getMessage());
			}
		} else {
			viewName = "changePassword";
		}

		return viewName;
	}

	private boolean validateEmail(String eMail, ModelMap model) {
		String modelKey = "message";
		boolean isValid = true;

		if (StringUtil.isNullEmptyOrAllWhiteSpace(eMail)) {
			model.put(modelKey, messageSource.getMessage(MessageKeys.FIELD_REQUIRED, null, null));
			isValid = false;
		} else if (!InputCheck.isValidEmailAddress(eMail)) {
			model.put(modelKey, messageSource.getMessage(MessageKeys.EMAIL_INVALID, null, null));
			isValid = false;
		} else if (!userService.emailExists(eMail)) {
			model.put(modelKey, messageSource.getMessage(MessageKeys.EMAIL_EXISTS_NOT, null, null));
			isValid = false;
		}
		return isValid;
	}

	private boolean validatePasswordFields(String password, String retypePassword,
			String userName, ModelMap model) {
		String msg;
		boolean isValid = true;

		if (StringUtil.isNullOrEmpty(password)) {
			msg = messageSource.getMessage(MessageKeys.FIELD_REQUIRED, null, null);
			model.put("error1", msg);
			isValid = false;
		} else if (!InputCheck.isValidPassword(password)) {
			msg = messageSource.getMessage(
					MessageKeys.PASSWORD_TOO_SHORT, new Object[] { String.
							valueOf(APP_PROPERTIES.getProperty
									(AppPropKeys.MIN_PASSWORD_LENGTH))}, null);
			model.put("error1", msg);
			isValid = false;
		} else if (password.toLowerCase().contains(userName.toLowerCase())) {
			msg = messageSource.getMessage(MessageKeys.PASS_SIMILAR_TO_USERNAME, null, null);
			model.put("error1", msg);
			isValid = false;
		}

		if (StringUtil.isNullOrEmpty(retypePassword)) {
			msg = messageSource.getMessage(MessageKeys.FIELD_REQUIRED, null, null);
			model.put("error2", msg);
			isValid = false;
		} else if (!retypePassword.equals(password)) {
			msg = messageSource.getMessage(MessageKeys.RETYPE_PASS_AND_PASS_DONT_MATCH, null, null);
			model.put("error2", msg);
			isValid = false;
		}
		return isValid;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/help")
	public String handleHelpRequest() {
		return "help";
	}
}
