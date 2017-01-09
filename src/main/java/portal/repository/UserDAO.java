package portal.repository;

import portal.domain.impl.Authority;
import portal.domain.impl.User;

public interface UserDAO extends DAO {

	/**
	 * Data Access Object interface providing persistence operations.
	 *
	 */

	/**
	 * Retrieves a user by its username.
	 *
	 * @param userName
	 *            userName.
	 * @return a user or null if no such entity.
	 */
	public User getUser(String userName);

	/**
	 * Retrieves a user by its e-mail address.
	 *
	 * @param eMail
	 *            eMail
	 * @return a user or null if no such entity.
	 */
	public User getUserByEMail(String eMail);

	/**
	 * Retrieves a user by its username and a password change code.
	 *
	 * @param userName
	 *            userName
	 * @param passwordChangeCode
	 *            passwordChangeCode
	 * @return a user or null if no such entity.
	 */
	public User getUserByUserNameAndPasswordChangeCode(String userName,
			String passwordChangeCode);

	/**
	 * Retrieves a user by its username and an activation code.
	 *
	 * @param userName
	 *            username.
	 * @param activationCode
	 *            activationCode.
	 * @return a user or null if no such entity.
	 */
	public User getUserByUserNameAndActivationCode(String userName, String activationCode);

	/**
	 * Retrieves an authority by its name.
	 *
	 * @param authorityName
	 * @return an authority or null if no such entity.
	 */
	public Authority getAuthority(String authorityName);
}
