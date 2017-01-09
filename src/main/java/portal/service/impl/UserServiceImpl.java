package portal.service.impl;

import java.util.Date;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import portal.config.AppPropKeys;
import portal.config.MessageKeys;
import portal.config.SysEnums;
import portal.domain.impl.Authority;
import portal.domain.impl.User;
import portal.repository.UserDAO;
import portal.security.context.SecurityContextFacade;
import portal.service.EmailService;
import portal.service.LogService;
import portal.service.UserService;
import portal.service.exceptions.BadActivationException;
import portal.service.exceptions.BadPasswordChangeRequestException;
import portal.util.Helper;
import portal.util.StringUtil;
import portal.validation.InputCheck;

@Service("userDetailsService")
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService, UserDetailsService {
	@Autowired
	private LogService logService;

	@Autowired
	private EmailService eMailService;

	@Autowired
	private UserDAO userDao;

	@Autowired
	private SecurityContextFacade securityContext;

	@Autowired
	private MessageSource messageSource;

	private final Properties APP_PROPERTIES = Helper.getAppProperties();

	@Override
	public User loadUserByUsername(String userName) throws UsernameNotFoundException {
		User user = userDao.getUser(userName);

		if (user == null) {
			String msg = messageSource.getMessage(
					MessageKeys.USERNNAME_EXISTS_NOT,
					new Object[] { userName }, null);

			throw new UsernameNotFoundException(msg);
		}
		return user;
	}

	@Override
	public User getUserByUsername(String userName) {
		return userDao.getUser(userName);
	}

	@Override
	public User insertUser(User user) {
		user.setPassword(Helper.encodePassword(user.getPassword()));
		user.setCreatedBy(securityContext.getLoggedInUser());
		user.setAccountNonExpired(true);
		user.setAccountNonLocked(true);
		user.setCredentialsNonExpired(true);
		user.setEnabled(false);
		user.setActivationCode(Helper.generateUUID());
		user.addAuthority(this.getAuthority(APP_PROPERTIES
				.getProperty(AppPropKeys.DEFAULT_USER_AUTHORITY)));

		user = userDao.insertEntity(user);
		logService.insertWebLog(MessageKeys.NEW_USER_INSERTED,
				SysEnums.Severity.INFO);

		eMailService.sendActivationEmail(user);

		return user;
	}

	@Override
	public User updateUser(User user) {
		return userDao.updateEntity(user);
	}

	@Override
	@Secured({ "ROLE_SUPER_USER" })
	public void deleteUser(User user) {
		userDao.deleteEntity(User.class, user);
	}

	@Override
	public Authority getAuthority(String authorityName) {
		return userDao.getAuthority(authorityName);
	}

	@Override
	public boolean userExists(String userName) {
		User user = userDao.getUser(userName);

		if (user == null) {
			return false;
		}
		return true;
	}

	@Override
	public boolean emailExists(String eMail) {
		User user = userDao.getUserByEMail(eMail);

		if (user == null) {
			return false;
		}
		return true;
	}

	@Override
	public void initiatePasswordChange(String eMail) {
		User user = userDao.getUserByEMail(eMail);

		user.setPasswordChangeCode(Helper.generateUUID());
		user.setPasswordChangeCodeCreatedAt(new Date());

		user = this.updateUser(user);
		eMailService.sendChangePasswordEmail(user);
	}

	@Override
	public void activateAccount(String userName, String activationCode)
			throws BadActivationException {
		if (!this.isActivationValid(userName, activationCode)) {
			String msg = messageSource.getMessage(
					MessageKeys.INVALID_CODE_OR_USERNAME, null, null);
			throw new BadActivationException(msg);
		}

		User user = this.getUserByUsername(userName);
		user.setActivationCode(null);
		user.setEnabled(true);
		this.updateUser(user);
	}

	@Override
	public void changePassword(String userName, String passwordChangeCode,
			String newPassword) throws BadPasswordChangeRequestException {
		if (!this.isPasswordChangeRequestValid(userName, passwordChangeCode)) {
			String msg = messageSource.getMessage(
					MessageKeys.INVALID_CODE_OR_USERNAME, null, null);
			throw new BadPasswordChangeRequestException(msg);
		}

		User user = this.getUserByUsername(userName);
		user.setPassword(Helper.encodePassword(newPassword));
		user.setPasswordChangeCode(null);
		this.updateUser(user);
	}

	@Override
	public boolean isActivationValid(String userName, String activationCode) {
		boolean result = true;

		if (actionParametersInvalid(userName, activationCode)) {
			result = false;
		} else {
			User user = userDao.getUserByUserNameAndActivationCode(userName,
					activationCode);

			if (user == null) {
				result = false;
			}
		}
		return result;
	}

	@Override
	public boolean isPasswordChangeRequestValid(String userName, String passwordChangeCode) {
		boolean result = true;

		if (actionParametersInvalid(userName, passwordChangeCode)) {
			result = false;
		} else {
			User user = userDao.getUserByUserNameAndPasswordChangeCode(
					userName, passwordChangeCode);

			if (user == null || isPasswordChangeCodeExpired(user)) {
				result = false;
			}
		}
		return result;
	}

	private boolean actionParametersInvalid(String userName, String code) {
		return StringUtil.isNullEmptyOrAllWhiteSpace(userName)
				|| StringUtil.isNullEmptyOrAllWhiteSpace(code)
				|| !InputCheck.isValidUserName(userName)
				|| !InputCheck.isValidGeneratedCode(code);
	}

	private boolean isPasswordChangeCodeExpired(User user) {
		return user.getPasswordChangeCodeCreatedAt() == null
				|| (new Date().getTime())
						- (user.getPasswordChangeCodeCreatedAt().getTime()) > Integer
							.parseInt(APP_PROPERTIES
									.getProperty(AppPropKeys.
											PSW_CHANGE_CODE_VALIDITY_IN_MILLISECONDS));
	}

	@Override
	public void manageAccountLocksOnAuthorizationFailure(User user) {
		if (user.isEnabled()) {
			user.setUpdateDate(new Date());
			user.setTotalNumberOfFailedAttempts(user
					.getTotalNumberOfFailedAttempts() + 1);

			if (user.getTotalNumberOfFailedAttempts() > Integer
					.parseInt(APP_PROPERTIES
							.getProperty(AppPropKeys.
									TOTAL_NO_OF_FAILED_ATTS_BEFORE_ACC_DISABLED))) {
				user.setEnabled(false);

			} else if (user.isAccountNonLocked()) {
				user.setLastFailedAttemptTime(new Date());
				user.setNumberOfFailedAttempts(user.getNumberOfFailedAttempts() + 1);

				if (user.getNumberOfFailedAttempts() > Integer
						.parseInt(APP_PROPERTIES
								.getProperty(AppPropKeys.
										NO_OF_FAILED_ATTS_BEFORE_ACC_LOCKED))) {

					user.setAccountNonLocked(false);
					user.setNumberOfAccountLocks(user.getNumberOfAccountLocks() + 1);

					if (user.getNumberOfAccountLocks() > Integer
							.parseInt(APP_PROPERTIES
									.getProperty(AppPropKeys.
											NO_OF_LOCKS_BEFORE_ACC_DISABLED))) {

						user.setEnabled(false);
					}
				}
			}
			this.updateUser(user);
		}
	}

	@Override
	public void manageAccountLocksOnAuthorizationSuccess(User user) {
		if (user.getNumberOfFailedAttempts() > 0) {
			user.setNumberOfFailedAttempts(0);
			this.updateUser(user);
		}
	}

	@Override
	public void managePreAuthorizationAccountLocks(User user) {
		if (isPreAuthAccountLockManagementAllowed(user)
				&& isBlockingPeriodSinceLastUnsuccessfulAttemptExpired(user
						.getLastFailedAttemptTime())) {

			user.setAccountNonLocked(true);
			user.setNumberOfFailedAttempts(0);
			this.updateUser(user);
		}
	}

	private boolean isPreAuthAccountLockManagementAllowed(User user) {
		return (user.isEnabled() && (!user.isAccountNonLocked() || user
				.getNumberOfFailedAttempts() > 0));
	}

	private boolean isBlockingPeriodSinceLastUnsuccessfulAttemptExpired(
			Date lastUnsuccessfulAttemptTime) {
		if (lastUnsuccessfulAttemptTime != null) {
			return new Date().getTime() - lastUnsuccessfulAttemptTime.getTime() > Integer
					.parseInt(APP_PROPERTIES
							.getProperty(AppPropKeys.ACCOUNT_LOCK_PERIOD_IN_MILLISECONDS));
		}
		return false;
	}
}
