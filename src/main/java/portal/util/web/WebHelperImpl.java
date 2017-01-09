package portal.util.web;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import portal.config.AppPropKeys;
import portal.util.Helper;
import portal.web.context.RequestContextFacade;

@Component
public class WebHelperImpl implements WebHelper {
	@Autowired
	private RequestContextFacade requestContext;

	@Autowired
	private MessageSource messageSource;

	private final Properties APP_PROPERTIES = Helper.getAppProperties();
	private final String SESSION_KEY_INFO_MESSAGE = APP_PROPERTIES
			.getProperty(AppPropKeys.INFO_MESSAGE_SESSION_KEY);

	public void putInfoMessageIntoSessionByKey(String messageKey) {
		String message = messageSource.getMessage(messageKey, null, null);
		requestContext.session()
				.setAttribute(SESSION_KEY_INFO_MESSAGE, message);
	}

	public void putInfoMessageIntoSession(String message) {
		requestContext.session()
				.setAttribute(SESSION_KEY_INFO_MESSAGE, message);
	}
}
