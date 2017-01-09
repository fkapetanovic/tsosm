package portal.web.context.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import portal.validation.InputCheck;
import portal.web.context.RequestContextFacade;

@Component
public class RequestContextHolderFacade implements RequestContextFacade {
	@Override
	public HttpSession session() {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.
	    		currentRequestAttributes();
    return attr.getRequest().getSession(true);
	}

	@Override
	public HttpServletRequest request() {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.
	    		currentRequestAttributes();
		return attr.getRequest();
	}

	@Override
	public String ipAddress() {
		String ipAddress;

		// Because the Java application server is behind a proxy.
		if (this.request().getHeader("X-FORWARDED-FOR") != null
				&& InputCheck.isSafeIpAddress(this.request().getHeader("X-FORWARDED-FOR"))){
			ipAddress = this.request().getHeader("X-FORWARDED-FOR");
		} else {
			ipAddress = "";
		}

		return ipAddress;
	}
}
