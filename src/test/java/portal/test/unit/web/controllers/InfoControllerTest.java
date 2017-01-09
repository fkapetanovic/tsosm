package portal.test.unit.web.controllers;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Properties;

import javax.servlet.http.HttpSession;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import portal.config.AppPropKeys;
import portal.util.Helper;
import portal.web.context.RequestContextFacade;
import portal.web.controllers.InfoController;

public class InfoControllerTest {
	@Autowired
	@InjectMocks
	private InfoController controller;

	@Mock
	private RequestContextFacade requestContext;

	@Mock
	private HttpSession session;

	private ModelMap model;

	private final Properties APP_PROPERTIES = Helper.getAppProperties();
	private final String INFO_MESSAGE_SESSION_KEY = APP_PROPERTIES
			.getProperty(AppPropKeys.INFO_MESSAGE_SESSION_KEY);

	@BeforeMethod
	public void setUpMocks() {
		MockitoAnnotations.initMocks(this);
		model = new ModelMap();
	}

	@Test
	public void getTestDataOK() {
		String messagePassedThroughSession = "Some info message.";

		when(requestContext.session()).thenReturn(session);
		when(requestContext.session().getAttribute(INFO_MESSAGE_SESSION_KEY))
				.thenReturn(messagePassedThroughSession);

		String viewName = controller.handleDisplayMessageRequest(model);

		verify(session).removeAttribute(INFO_MESSAGE_SESSION_KEY);

		Assert.assertEquals(viewName, "info");
		Assert.assertEquals(model.get("message").toString(),messagePassedThroughSession);
	}

	@Test
	public void getTestInfoMessageNotPresentInSession() {
		when(requestContext.session()).thenReturn(session);
		when(requestContext.session().getAttribute(INFO_MESSAGE_SESSION_KEY)).thenReturn(null);

		String viewName = controller.handleDisplayMessageRequest(model);

		Assert.assertEquals(viewName, "info");
		Assert.assertEquals(model.get("message"), null);
		verify(session, times(0)).removeAttribute(INFO_MESSAGE_SESSION_KEY);
	}
}
