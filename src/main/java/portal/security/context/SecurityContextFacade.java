package portal.security.context;

import org.springframework.security.core.context.SecurityContext;
import portal.domain.impl.User;

public interface SecurityContextFacade {

	/**
	 * Gets current Spring's security context.
	 *
	 * @return Spring's security context.
	 */
	SecurityContext getContext();

	/**
	 * Gets currently logged in user.
	 *
	 * @return logged in user.
	 */
	User getLoggedInUser();

	/**
	 * Sets current Spring's security context.
	 *
	 * @param SecurityContext
	 *            Spring's security context.
	 */
	void setContext(SecurityContext securityContext);

	/**
	 * Checks to see if the current user is authenticated.
	 *
	 * @return returns true if the user is authenticated or false if the user is anonymous.
	 */
	boolean isAnonymousUser();

}
