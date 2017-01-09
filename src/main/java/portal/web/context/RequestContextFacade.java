package portal.web.context;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public interface RequestContextFacade {
	/**
	 * Returns the current HTTP session.
	 *
	 * @return HTTP session object.
	 */
	HttpSession session();

	/**
	 * Returns the current HTTP request.
	 *
	 * @return HTTP request object.
	 */
	HttpServletRequest request();

	/**
	 * Returns the IP address of the current client.
	 *
	 * @return the IP address.
	 */
	String ipAddress();
}
