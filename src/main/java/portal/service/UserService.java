package portal.service;

import portal.domain.impl.*;

/**
 * "Service" interface for managing users.
 *
 */
public interface UserService {

	/**
	 * Spring Security method for loading users by their username.
	 *
	 * @param userName
	 * @return a user with the given username or null if no such entity.
	 */
	public User loadUserByUsername(String userName);

	/**
	 * Retrieves a user by its username.
	 *
	 * @param userName
	 * @return a user with the given username or null if no such entity.
	 */
	public User getUserByUsername(String userName);

	/**
	 * Processes the activation of an account request. Throws BadActivation exception
	 * if provided with invalid parameters or with a nonexisting username-activationCode pair.
	 *
	 * @param userName
	 * @param activationCode
	 *            an activation code that was e-mailed to the user.
	 */
	public void activateAccount(String userName, String activationCode);

	/**
	 * Replaces the old password with a new one. Throws BadPasswordChangeRequest exception
	 * if provided with invalid parameters or with a nonexisting username-passwordChangeCode pair.
	 *
	 * @param userName
	 * @param passwordChangeCode
	 *           an password change code that was e-mailed to the user.
	 * @param newPassword
	 */
	public void changePassword(String userName, String passwordChangeCode, String newPassword);

	/**
	 * Checks whether a user with given username exists.
	 *
	 * @param userName
	 *            username.
	 * @return true if exists otherwise false.
	 */
	public boolean userExists(String userName);

	/**
	 * Checks whether given eMail exists.
	 *
	 * @param eMail
	 *            e-mail address.
	 * @return true if exists otherwise false.
	 */
	public boolean emailExists(String eMail);

	/**
	 * Checks whether a user with given username and activation code exists.
	 *
	 * @param userName
	 *            username
	 * @param activationCode
	 *            activation code that was generated and e-mailed to the user that
	 *            initiated the registration.
	 * @return true if such user exists otherwise false.
	 */
	public boolean isActivationValid(String userName, String activationCode);

	/**
	 * Checks whether user with given username and passwordChangeCode exists.
	 *
	 * @param userName
	 *            username
	 * @param passwordChangeCode
	 *            passwordChangeCode that was generated and e-mailed to the user
	 *            that initiated the password change procedure.
	 * @return true if such user exists otherwise false.
	 */
	public boolean isPasswordChangeRequestValid(String userName, String passwordChangeCode);

	/**
	 * Password change initiated by the user.
	 *
	 * @param eMail
	 *            e-mail address tied to a user account.
	 */
	public void initiatePasswordChange(String eMail);

	/**
	 * Manages account locks before authorization is initiated. Tries to
	 * release temporary locks if certain conditions are met.
	 *
	 *
	 * @param user
	 *            user that has initiated the authorization.
	 */
	public void managePreAuthorizationAccountLocks(User user);

	/**
	 * Manages the account details after an unsuccessful authorization attempt. Imposes
	 * an temporary account lock or disables the account completely if certain conditions
	 * are met.
	 *
	 *
	 * @param user
	 *            user that has initiated the authorization.
	 */
	public void manageAccountLocksOnAuthorizationFailure(User user);

	/**
	 * Manages the account details after a successful authorization attempt. Clears the
	 * counter of unsuccessful authorization attempts.
	 *
	 *
	 * @param user
	 *            user that has initiated the authorization.
	 */
	public void manageAccountLocksOnAuthorizationSuccess(User user);

	/**
	 * Inserts a new user into the persistent store.
	 *
	 * @param user
	 *            user to insert.
	 * @return inserted user.
	 */
	public User insertUser(User user);

	/**
	 * Saves a modified user into the persistent store.
	 *
	 * @param user
	 *            user to update.
	 * @return updated user.
	 */
	public User updateUser(User user);

	/**
	 * Deletes a user from the persistent store.
	 *
	 * @param user
	 *            user to delete.
	 */
	public void deleteUser(User user);

	/**
	 * Retrieves an authority by its name.
	 *
	 * @param authorityName
	 * @return authority with given name or null if no such
	 *         authority.
	 */
	public Authority getAuthority(String authorityName);
}
