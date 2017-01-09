package portal.web.controllers;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import portal.config.AppPropKeys;
import portal.util.Helper;
import portal.web.context.RequestContextFacade;

@Controller
@RequestMapping("/info")
public class InfoController {
	@Autowired
	private RequestContextFacade requestContext;

	private final Properties APP_PROPERTIES = Helper.getAppProperties();

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String handleDisplayMessageRequest(ModelMap model) {
		Object infoMessage = requestContext.session().getAttribute(
				APP_PROPERTIES
						.getProperty(AppPropKeys.INFO_MESSAGE_SESSION_KEY));

		if (infoMessage != null) {
			model.put("message", infoMessage.toString());
			requestContext.session().removeAttribute(
					APP_PROPERTIES
							.getProperty(AppPropKeys.INFO_MESSAGE_SESSION_KEY));
		}

		return "info";
	}
}
