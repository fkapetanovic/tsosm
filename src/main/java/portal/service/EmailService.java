package portal.service;

import portal.domain.impl.User;

public interface EmailService {

	/**
	 * Sends an activation e-mail after the user submitted a valid registration form.
	 *
	 * @param user
	 *            an e-mail is sent to this user.
	 */
	public void sendActivationEmail(User user);

	/**
	 * Sends an e-mail after the user submitted a password reset request.
	 *
	 * @param user
	 *            an e-mail is sent to this user.
	 */
	public void sendChangePasswordEmail(User user);
}
