package portal.security.context.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import portal.domain.impl.User;
import portal.security.context.SecurityContextFacade;
import portal.service.UserService;

@Component
public class SecurityContextHolderFacade implements SecurityContextFacade {
	@Autowired
	private UserService userService;

	@Override
	public SecurityContext getContext() {
		return SecurityContextHolder.getContext();
	}

	@Override
	public void setContext(SecurityContext securityContext) {
		SecurityContextHolder.setContext(securityContext);
	}

	@Override
	public User getLoggedInUser() {
		String userName;
		Authentication authentication = this.getContext().getAuthentication();

		if (this.isAnonymousUser()) {
			userName = "anonymousUser";
		} else {
			userName = authentication.getName();
		}
		return userService.getUserByUsername(userName);
	}

	@Override
	public boolean isAnonymousUser() {
		boolean result = false;

		if (this.getContext().getAuthentication() == null
				|| this.getContext().getAuthentication().getPrincipal()
						.equals("anonymousUser")) {
			result = true;
		}
		return result;
	}
}
