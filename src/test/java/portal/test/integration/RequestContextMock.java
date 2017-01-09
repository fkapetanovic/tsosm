package portal.test.integration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;

import portal.web.context.RequestContextFacade;

public class RequestContextMock implements RequestContextFacade{
	@Override
	public HttpSession session() {
    return new MockHttpSession();
	}

	@Override
	public HttpServletRequest request() {
		return new MockHttpServletRequest();
	}

	@Override
	public String ipAddress() {
		return "127.0.0.1";
	}
}
