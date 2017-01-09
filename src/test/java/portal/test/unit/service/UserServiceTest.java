package portal.test.unit.service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.times;

import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import portal.config.AppPropKeys;
import portal.config.SysEnums;
import portal.config.MessageKeys;
import portal.domain.impl.Authority;
import portal.domain.impl.User;
import portal.repository.UserDAO;
import portal.security.context.SecurityContextFacade;
import portal.service.EmailService;
import portal.service.LogService;
import portal.service.exceptions.BadActivationException;
import portal.service.exceptions.BadPasswordChangeRequestException;
import portal.service.impl.UserServiceImpl;
import portal.util.Helper;

public class UserServiceTest {
	@Mock
	private UserDAO userDao;

	@Mock
	private LogService logService;

	@Mock
	private EmailService eMailService;

	@Mock
	private SecurityContextFacade securityContext;

	@SuppressWarnings("unused")
	@Mock
	private MessageSource messageSource;

	@Autowired
	@InjectMocks
	private UserServiceImpl userService;

	private final Properties APP_PROPERTIES = Helper.getAppProperties();
	private final String DEFAULT_USER_AUTHORITY = APP_PROPERTIES.
			getProperty(AppPropKeys.DEFAULT_USER_AUTHORITY);
	private final int NO_OF_FAILED_ATTS_BEFORE_ACC_LOCKED = Integer.
			parseInt(APP_PROPERTIES.getProperty(AppPropKeys.
					NO_OF_FAILED_ATTS_BEFORE_ACC_LOCKED));
	private final int NO_OF_LOCKS_BEFORE_ACC_DISABLED = Integer.
			parseInt(APP_PROPERTIES.getProperty(AppPropKeys.
					NO_OF_LOCKS_BEFORE_ACC_DISABLED));
	private final int TOTAL_NO_OF_FAILED_ATTS_BEFORE_ACC_DISABLED = Integer.
			parseInt(APP_PROPERTIES.getProperty(AppPropKeys.
					TOTAL_NO_OF_FAILED_ATTS_BEFORE_ACC_DISABLED));
	private final int PSW_CHANGE_CODE_VALIDITY_IN_MILLISECONDS = Integer.
			parseInt(APP_PROPERTIES.getProperty(AppPropKeys.
					PSW_CHANGE_CODE_VALIDITY_IN_MILLISECONDS));

	private final int ACCOUNT_LOCK_PERIOD_IN_MILLISECONDS = Integer.
			parseInt(APP_PROPERTIES.getProperty(AppPropKeys.
					ACCOUNT_LOCK_PERIOD_IN_MILLISECONDS));

	@BeforeMethod
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void getUserByUsername() {
		String userName = "test";
		userService.getUserByUsername(userName);
		verify(userDao).getUser(userName);
	}

	@Test
	public void loadUserByUsernameUserFoundTest() {
		String userName = "test";
		User user = new User();
		when(userDao.getUser(userName)).thenReturn(user);
		User result = userService.loadUserByUsername(userName);
		verify(userDao).getUser(userName);
		Assert.assertEquals(user, result);
	}

	@Test(expectedExceptions = UsernameNotFoundException.class)
	public void loadUserByUsernameUserNotFoundTest() {
		String userName = "test";
		when(userDao.getUser("test")).thenReturn(null);
		userService.loadUserByUsername(userName);
	}

	@Test
	public void insertUserTest() {
		User anonymousUser = new User();
		User user = new User();
		String preEncryptionPassword = "pre-encryption pass";
		user.setPassword(preEncryptionPassword);
		Authority defaultAuthority = new Authority();
		defaultAuthority.setAuthority(DEFAULT_USER_AUTHORITY);

		when(userDao.getAuthority(DEFAULT_USER_AUTHORITY)).thenReturn(defaultAuthority);
		when(securityContext.getLoggedInUser()).thenReturn(anonymousUser);
		when(userDao.insertEntity(user)).thenReturn(user);

		InOrder inOrder = inOrder(userDao, logService, eMailService);
		userService.insertUser(user);
		String grantedAuthority = user.getAuthorities().iterator().next().getAuthority();

		inOrder.verify(userDao).insertEntity(user);
		inOrder.verify(logService).insertWebLog(MessageKeys.NEW_USER_INSERTED, SysEnums.Severity.INFO);
		inOrder.verify(eMailService).sendActivationEmail(user);

		Assert.assertEquals(anonymousUser, user.getCreatedBy());
		Assert.assertEquals(true, user.isAccountNonExpired());
		Assert.assertEquals(true, user.isAccountNonLocked());
		Assert.assertEquals(true, user.isCredentialsNonExpired());
		Assert.assertEquals(false, user.isEnabled());
		Assert.assertEquals(60, user.getPassword().length());
		Assert.assertEquals(DEFAULT_USER_AUTHORITY, grantedAuthority);
		Assert.assertFalse(preEncryptionPassword.equals(user.getPassword()));
	}

	@Test
	public void updateUserTest() {
		User user = new User();
		userService.updateUser(user);
		verify(userDao).updateEntity(user);
	}

	@Test
	public void deleteUserTest() {
		User user = new User();
		userService.deleteUser(user);
		verify(userDao).deleteEntity(User.class, user);
	}

	@Test
	public void getAuthorityTest() {
		userService.getAuthority("AUTHORITY_NAME");
		verify(userDao).getAuthority("AUTHORITY_NAME");
	}

	@Test
	public void userExistsTest(){
		String nonExistingUsername = "user123";
		String existingUsername = "userabc";
		User existingUser = new User();
		when(userDao.getUser(nonExistingUsername)).thenReturn(null);
		when(userDao.getUser(existingUsername)).thenReturn(existingUser);
		boolean result = userService.userExists(nonExistingUsername);
		Assert.assertEquals(false, result);
		result = userService.userExists(existingUsername);
		Assert.assertEquals(true, result);
	}

	@Test
	public void eMailExistsTest(){
		String nonExistingEmail = "user123@mail.com";
		String existingEmail = "userabc@mail.com";
		User existingUser = new User();
		when(userDao.getUserByEMail(nonExistingEmail)).thenReturn(null);
		when(userDao.getUserByEMail(existingEmail)).thenReturn(existingUser);
		boolean result = userService.emailExists(nonExistingEmail);
		Assert.assertEquals(false, result);
		result = userService.emailExists(existingEmail);
		Assert.assertEquals(true, result);
	}

	@Test
	public void initiatePasswordChangeTest(){
		User user = new User();
		String eMail = "user@mail.com";

		when(userDao.getUserByEMail(eMail)).thenReturn(user);
		when(userDao.updateEntity(user)).thenReturn(user);

		InOrder inOrder = inOrder(userDao, eMailService);

		userService.initiatePasswordChange(eMail);

		inOrder.verify(userDao).getUserByEMail(eMail);
		inOrder.verify(userDao).updateEntity(user);
		inOrder.verify(eMailService).sendChangePasswordEmail(user);

		Assert.assertNotNull(user.getPasswordChangeCode());
		Assert.assertNotNull(user.getPasswordChangeCodeCreatedAt());
	}

	@Test
	public void activateAccountValidParamsTest(){
		String activationCode = "5a1d1d2bcac94e06bb5b31a952583129";
		String userName = "username";
		User user = new User();

		when(userDao.getUserByUserNameAndActivationCode(userName,
				activationCode)).thenReturn(user);
		when(userDao.getUser(userName)).thenReturn(user);

		userService.activateAccount(userName, activationCode);

		verify(userDao).getUserByUserNameAndActivationCode(userName, activationCode);
		verify(userDao).getUser(userName);
		verify(userDao).updateEntity(user);

		Assert.assertEquals(true, user.isEnabled());
		Assert.assertNull(user.getActivationCode());
	}

	@Test(expectedExceptions = BadActivationException.class)
	public void activateAccountNonExistingActivationTest(){
		String activationCode = "5a1d1d2bcac94e06bb5b31a952583111";
		String userName = "username123";

		when(userDao.getUserByUserNameAndActivationCode(userName, activationCode)).thenReturn(null);
		userService.activateAccount(userName, activationCode);
	}

	@Test(expectedExceptions = BadActivationException.class)
	public void activateAccountInvalidParamsTest(){
		String activationCode = "invalid activation code";
		String userName = "invalid username";

		userService.activateAccount(userName, activationCode);
	}

	@Test
	public void changePasswordValidParamsTest(){
		String passCode = "5a1d1d2bcac94e06bb5b31a952583129";
		String userName = "username";
		String newPassword = "new password";
		User user = new User();

		Calendar cal = Calendar.getInstance();
    cal.setTime(new Date());
    cal.add(Calendar.MILLISECOND, - PSW_CHANGE_CODE_VALIDITY_IN_MILLISECONDS / 2);
    user.setPasswordChangeCodeCreatedAt(cal.getTime());

		when(userDao.getUserByUserNameAndPasswordChangeCode(userName, passCode)).thenReturn(user);
		when(userDao.getUser(userName)).thenReturn(user);

		userService.changePassword(userName, passCode, newPassword);

		verify(userDao).getUserByUserNameAndPasswordChangeCode(userName, passCode);
		verify(userDao).getUser(userName);
		verify(userDao).updateEntity(user);

		Assert.assertNotNull(user.getPassword());
		Assert.assertNull(user.getPasswordChangeCode());
	}

	@Test(expectedExceptions = BadPasswordChangeRequestException.class)
	public void changePasswordPassCodeExpiredTest(){
		String passCode = "5a1d1d2bcac94e06bb5b31a952583129";
		String userName = "username";
		String newPassword = "new password";
		User user = new User();
		Calendar cal = Calendar.getInstance();
    cal.setTime(new Date());
    cal.add(Calendar.MILLISECOND, -PSW_CHANGE_CODE_VALIDITY_IN_MILLISECONDS * 2);
    user.setPasswordChangeCodeCreatedAt(cal.getTime());

    when(userDao.getUserByUserNameAndPasswordChangeCode(userName, passCode)).thenReturn(user);
		userService.changePassword(userName, passCode, newPassword);
	}

	@Test(expectedExceptions = BadPasswordChangeRequestException.class)
	public void changePasswordNonExistingPasswordCodeUsernamePairTest(){
		String passCode = "5a1d1d2bcac94e06bb5b31a952583111";
		String userName = "username123";
		String newPassword = "new password";

		when(userDao.getUserByUserNameAndPasswordChangeCode(userName, passCode)).thenReturn(null);
		userService.changePassword(userName, passCode, newPassword);
	}

	@Test(expectedExceptions = BadPasswordChangeRequestException.class)
	public void changePasswordInvalidParamsTest(){
		String passCode = "invalid activation code";
		String userName = "invalid existing username";
		String newPassword = "new password";

		userService.changePassword(userName, passCode, newPassword);
	}

	@Test
	public void isActivationValidOKTest(){
		String userName = "username";
		String activationCode = "5a1d1d2bcac94e06bb5b31a952583129";
		User user = new User();

		when(userDao.getUserByUserNameAndActivationCode(userName, activationCode)).thenReturn(user);

		boolean result = userService.isActivationValid(userName, activationCode);

		verify(userDao).getUserByUserNameAndActivationCode(userName, activationCode);
		Assert.assertEquals(true, result);
	}

	@Test
	public void isActivationValidBadParamsTest(){
		String emptyUserName = "";
		String emptyActivationCode = "";
		String badUserName = "bad user name";
		String badActivationCode = "bad activation code";
		String validUsername = "username123";
		String validActivationCode = "5a1d1d2bcac94e06bb5b31a952583111";

		when(userDao.getUserByUserNameAndActivationCode(validUsername, validActivationCode)).thenReturn(null);

		boolean result = userService.isActivationValid(emptyUserName, validActivationCode);
		Assert.assertEquals(false, result);

		result = userService.isActivationValid(badUserName, validActivationCode);
		Assert.assertEquals(false, result);

		result = userService.isActivationValid(validUsername, emptyActivationCode);
		Assert.assertEquals(false, result);

		result = userService.isActivationValid(validUsername, badActivationCode);
		Assert.assertEquals(false, result);

		result = userService.isActivationValid(validUsername, validActivationCode);
		Assert.assertEquals(false, result);

		verify(userDao).getUserByUserNameAndActivationCode(validUsername, validActivationCode);
	}

	@Test
	public void isPasswordChangeRequestValidOKTest(){
		String userName = "username";
		String passCode = "5a1d1d2bcac94e06bb5b31a952583129";
		User user = new User();

		Calendar cal = Calendar.getInstance();
    cal.setTime(new Date());
    cal.add(Calendar.MILLISECOND, PSW_CHANGE_CODE_VALIDITY_IN_MILLISECONDS / 2);
    user.setPasswordChangeCodeCreatedAt(cal.getTime());

		when(userDao.getUserByUserNameAndPasswordChangeCode(userName, passCode)).thenReturn(user);

		boolean result = userService.isPasswordChangeRequestValid(userName, passCode);

		verify(userDao).getUserByUserNameAndPasswordChangeCode(userName, passCode);
		Assert.assertEquals(true, result);
	}


	@Test
	public void isPasswordChangeRequestValidBadParamsTest(){
		String emptyUserName = "";
		String emptyPassCode = "";
		String badUserName = "bad user name";
		String badPassCode = "bad activation code";
		String validUsername = "username123";
		String validPassCode = "5a1d1d2bcac94e06bb5b31a952583111";

		when(userDao.getUserByUserNameAndPasswordChangeCode(validUsername, validPassCode)).thenReturn(null);

		boolean result = userService.isPasswordChangeRequestValid(emptyUserName, validPassCode);
		Assert.assertEquals(false, result);

		result = userService.isPasswordChangeRequestValid(badUserName, validPassCode);
		Assert.assertEquals(false, result);

		result = userService.isPasswordChangeRequestValid(validUsername, emptyPassCode);
		Assert.assertEquals(false, result);

		result = userService.isPasswordChangeRequestValid(validUsername, badPassCode);
		Assert.assertEquals(false, result);

		result = userService.isPasswordChangeRequestValid(validUsername, validPassCode);
		Assert.assertEquals(false, result);

		verify(userDao).getUserByUserNameAndPasswordChangeCode(validUsername, validPassCode);
	}

	@Test
	public void isPasswordChangeRequestCodeExpiredTest(){
		String validUsername = "username123";
		String validPassCode = "5a1d1d2bcac94e06bb5b31a952583111";
		User user = new User();

		Calendar cal = Calendar.getInstance();
    cal.setTime(new Date());
    cal.add(Calendar.MILLISECOND, - PSW_CHANGE_CODE_VALIDITY_IN_MILLISECONDS * 2);
    user.setPasswordChangeCodeCreatedAt(cal.getTime());

		when(userDao.getUserByUserNameAndPasswordChangeCode(validUsername, validPassCode)).thenReturn(user);

		boolean result = userService.isPasswordChangeRequestValid(validUsername, validPassCode);
		Assert.assertEquals(false, result);

		verify(userDao).getUserByUserNameAndPasswordChangeCode(validUsername, validPassCode);
	}

	@Test
	public void manageAcctLocksOnAuthFailureTotalNoOfFailedAttemptsReachedTest(){
		User user = new User();
		user.setEnabled(true);
		user.setTotalNumberOfFailedAttempts(TOTAL_NO_OF_FAILED_ATTS_BEFORE_ACC_DISABLED + 1);

		userService.manageAccountLocksOnAuthorizationFailure(user);

		Assert.assertEquals(false, user.isEnabled());
		Assert.assertNotNull(user.getUpdateDate());
		verify(userDao).updateEntity(user);
	}

	@Test
	public void manageAcctLocksOnAuthFailureNoOfFailedAttemptsReachedTest(){
		User user = new User();
		int noOfFailedAttempts = NO_OF_FAILED_ATTS_BEFORE_ACC_LOCKED;
		int totNoOfFailedAttempts = noOfFailedAttempts;
		user.setEnabled(true);
		user.setAccountNonLocked(true);
		user.setTotalNumberOfFailedAttempts(totNoOfFailedAttempts);
		user.setNumberOfFailedAttempts(noOfFailedAttempts);
		user.setNumberOfAccountLocks(0);

		userService.manageAccountLocksOnAuthorizationFailure(user);

		Assert.assertEquals(false, user.isAccountNonLocked());
		Assert.assertEquals(true, user.isEnabled());
		Assert.assertEquals(1, user.getNumberOfAccountLocks());
		Assert.assertEquals(noOfFailedAttempts + 1, user.getNumberOfFailedAttempts());
		Assert.assertEquals(totNoOfFailedAttempts + 1, user.getTotalNumberOfFailedAttempts());
		Assert.assertNotNull(user.getUpdateDate());
		verify(userDao).updateEntity(user);
	}

	@Test
	public void manageAcctLocksOnAuthFailureNoOfLocksReachedTest(){
		User user = new User();
		int noOfLocks = NO_OF_LOCKS_BEFORE_ACC_DISABLED;
		int noOfFailedAttempts = NO_OF_FAILED_ATTS_BEFORE_ACC_LOCKED;
		int totNoOfFailedAttempts = noOfFailedAttempts;
		user.setEnabled(true);
		user.setAccountNonLocked(true);
		user.setTotalNumberOfFailedAttempts(totNoOfFailedAttempts);
		user.setNumberOfFailedAttempts(noOfFailedAttempts);
		user.setNumberOfAccountLocks(noOfLocks);

		userService.manageAccountLocksOnAuthorizationFailure(user);

		Assert.assertEquals(false, user.isAccountNonLocked());
		Assert.assertEquals(false, user.isEnabled());
		Assert.assertEquals(noOfLocks + 1, user.getNumberOfAccountLocks());
		Assert.assertEquals(noOfFailedAttempts + 1, user.getNumberOfFailedAttempts());
		Assert.assertEquals(totNoOfFailedAttempts + 1, user.getTotalNumberOfFailedAttempts());
		Assert.assertNotNull(user.getUpdateDate());
		verify(userDao).updateEntity(user);
	}

	@Test
	public void manageAcctLocksOnAuthFailureIncrementNoOfFailedAttTest(){
		User user = new User();
		int noOfFailedAttempts = NO_OF_FAILED_ATTS_BEFORE_ACC_LOCKED - 1;
		int totNoOfFailedAttempts = noOfFailedAttempts;
		user.setEnabled(true);
		user.setAccountNonLocked(true);
		user.setTotalNumberOfFailedAttempts(totNoOfFailedAttempts);
		user.setNumberOfFailedAttempts(noOfFailedAttempts);

		userService.manageAccountLocksOnAuthorizationFailure(user);

		Assert.assertEquals(true, user.isAccountNonLocked());
		Assert.assertEquals(true, user.isEnabled());
		Assert.assertEquals(noOfFailedAttempts + 1, user.getNumberOfFailedAttempts());
		Assert.assertEquals(totNoOfFailedAttempts + 1, user.getTotalNumberOfFailedAttempts());
		Assert.assertNotNull(user.getUpdateDate());
		verify(userDao).updateEntity(user);
	}

	@Test
	public void manageAcctLocksOnAuthFailureAccountDisabledTest(){
		User user = new User();
		int noOfFailedAttempts = NO_OF_FAILED_ATTS_BEFORE_ACC_LOCKED - 1;
		int totNoOfFailedAttempts = noOfFailedAttempts;
		user.setEnabled(false);
		user.setAccountNonLocked(true);
		user.setTotalNumberOfFailedAttempts(totNoOfFailedAttempts);
		user.setNumberOfFailedAttempts(noOfFailedAttempts);

		userService.manageAccountLocksOnAuthorizationFailure(user);

		Assert.assertEquals(true, user.isAccountNonLocked());
		Assert.assertEquals(false, user.isEnabled());
		Assert.assertEquals(noOfFailedAttempts, user.getNumberOfFailedAttempts());
		Assert.assertEquals(totNoOfFailedAttempts, user.getTotalNumberOfFailedAttempts());
		Assert.assertNull(user.getUpdateDate());
		verify(userDao, times(0)).updateEntity(user);
	}

	@Test
	public void manageAcctLocksOnAuthFailureAccountLockedTest(){
		User user = new User();
		Date lastFailedAttemptTime = new Date();
		int noOfFailedAttempts = NO_OF_FAILED_ATTS_BEFORE_ACC_LOCKED + 1;
		int totNoOfFailedAttempts = noOfFailedAttempts;
		user.setLastFailedAttemptTime(lastFailedAttemptTime);
		user.setAccountNonLocked(false);
		user.setEnabled(true);
		user.setTotalNumberOfFailedAttempts(totNoOfFailedAttempts);
		user.setNumberOfFailedAttempts(noOfFailedAttempts);

		userService.manageAccountLocksOnAuthorizationFailure(user);

		Assert.assertEquals(false, user.isAccountNonLocked());
		Assert.assertEquals(true, user.isEnabled());
		Assert.assertEquals(user.getLastFailedAttemptTime(), lastFailedAttemptTime);
		Assert.assertEquals(noOfFailedAttempts, user.getNumberOfFailedAttempts());
		Assert.assertEquals(totNoOfFailedAttempts + 1, user.getTotalNumberOfFailedAttempts());
		Assert.assertNotNull(user.getUpdateDate());
		verify(userDao).updateEntity(user);
	}

	@Test
	public void manageAccountLocksOnAuthorization3FailedAttSuccessTest(){
		User user1 = new User();
		user1.setNumberOfFailedAttempts(3);

		userService.manageAccountLocksOnAuthorizationSuccess(user1);
		verify(userDao).updateEntity(user1);
		Assert.assertEquals(0, user1.getNumberOfFailedAttempts());
	}

	@Test
	public void manageAccountLocksOnAuthorizationSuccessZeroFailedAttTest(){
		User user1 = new User();
		user1.setNumberOfFailedAttempts(0);

		userService.manageAccountLocksOnAuthorizationSuccess(user1);
		verify(userDao, times(0)).updateEntity(user1);
		Assert.assertEquals(0, user1.getNumberOfFailedAttempts());
	}

	@Test
	public void manageAccountLocksBeforeAuthFailedLockLiftedTest(){
		User user = new User();
		user.setEnabled(true);
		user.setAccountNonLocked(false);
		int noOfFailedAttempts = NO_OF_FAILED_ATTS_BEFORE_ACC_LOCKED + 1;
		user.setNumberOfFailedAttempts(noOfFailedAttempts);
		Calendar cal = Calendar.getInstance();
    cal.setTime(new Date());
    cal.add(Calendar.MILLISECOND, - ACCOUNT_LOCK_PERIOD_IN_MILLISECONDS * 2);
    user.setLastFailedAttemptTime(cal.getTime());

		userService.managePreAuthorizationAccountLocks(user);

		Assert.assertEquals(true, user.isEnabled());
		Assert.assertEquals(true, user.isAccountNonLocked());
		Assert.assertEquals(0, user.getNumberOfFailedAttempts());

		verify(userDao).updateEntity(user);
	}

	@Test
	public void manageAccountLocksBeforeAuthFailedCounterResetTest(){
		User user = new User();
		user.setEnabled(true);
		user.setAccountNonLocked(true);
		int noOfFailedAttempts = NO_OF_FAILED_ATTS_BEFORE_ACC_LOCKED;
		user.setNumberOfFailedAttempts(noOfFailedAttempts);
		Calendar cal = Calendar.getInstance();
    cal.setTime(new Date());
    cal.add(Calendar.MILLISECOND, - ACCOUNT_LOCK_PERIOD_IN_MILLISECONDS * 2);
    user.setLastFailedAttemptTime(cal.getTime());

		userService.managePreAuthorizationAccountLocks(user);

		Assert.assertEquals(true, user.isEnabled());
		Assert.assertEquals(true, user.isAccountNonLocked());
		Assert.assertEquals(0, user.getNumberOfFailedAttempts());

		verify(userDao).updateEntity(user);
	}

	@Test
	public void manageAccountLocksBeforeAuthorizationAccDisabledTest(){
		User user = new User();
		user.setEnabled(false);
		user.setAccountNonLocked(true);
		int noOfFailedAttempts = NO_OF_FAILED_ATTS_BEFORE_ACC_LOCKED;
		user.setNumberOfFailedAttempts(noOfFailedAttempts);

		userService.managePreAuthorizationAccountLocks(user);

		Assert.assertEquals(false, user.isEnabled());
		Assert.assertEquals(true, user.isAccountNonLocked());
		Assert.assertEquals(noOfFailedAttempts, user.getNumberOfFailedAttempts());

		verify(userDao, times(0)).updateEntity(user);
	}

	@Test
	public void manageAccountLocksBeforeAuthorizationAccNonLockedTest(){
		User user = new User();
		user.setEnabled(true);
		user.setAccountNonLocked(true);
		user.setNumberOfFailedAttempts(0);

		userService.managePreAuthorizationAccountLocks(user);

		Assert.assertEquals(true, user.isEnabled());
		Assert.assertEquals(true, user.isAccountNonLocked());
		Assert.assertEquals(0, user.getNumberOfFailedAttempts());

		verify(userDao, times(0)).updateEntity(user);
	}

	@Test
	public void manageAccountLocksBeforeAuthFailedLockNotExpiredTest(){
		User user = new User();
		user.setEnabled(true);
		user.setAccountNonLocked(false);
		int noOfFailedAttempts = NO_OF_FAILED_ATTS_BEFORE_ACC_LOCKED + 1;
		user.setNumberOfFailedAttempts(noOfFailedAttempts);
		Calendar cal = Calendar.getInstance();
    cal.setTime(new Date());
    cal.add(Calendar.MILLISECOND, - ACCOUNT_LOCK_PERIOD_IN_MILLISECONDS / 2);
    user.setLastFailedAttemptTime(cal.getTime());

		userService.managePreAuthorizationAccountLocks(user);
		
		Assert.assertEquals(true, user.isEnabled());
		Assert.assertEquals(false, user.isAccountNonLocked());
		Assert.assertEquals(noOfFailedAttempts, user.getNumberOfFailedAttempts());

		verify(userDao, times(0)).updateEntity(user);
	}
}
