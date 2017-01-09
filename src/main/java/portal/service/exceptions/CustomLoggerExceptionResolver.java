package portal.service.exceptions;

import javax.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

/***
 *
 * Extends simpleMappingExceptionResolver with capability to log different types of exception to different logs
 * It does this by adding a map of exceptions and loggers
 * Overrides logExcetion method so it looks up the exception in the map and logs error into the appropriate log.
 *
 */
public class CustomLoggerExceptionResolver extends SimpleMappingExceptionResolver {
	private Map<Class<? extends Throwable>,String> loggers= new HashMap<Class<? extends Throwable>, String>();
	private String WarnLogCategory;

	public Map<Class<? extends Throwable>,String> getLoggers() {
		return loggers;
	}

	public void setLoggers(Map<Class<? extends Throwable>,String> loggers) {
		this.loggers = loggers;
	}

	@Override
	public void setWarnLogCategory(String loggerName) {
		this.WarnLogCategory =loggerName;
	}

	@Override
	protected void logException(Exception ex, HttpServletRequest request) {
		Logger logger;
		String logCategory =loggers.get(ex.getClass());

		if (logCategory != null) {
			 logger =Logger.getLogger(logCategory);
		} else if (WarnLogCategory!="") {
			 logger =Logger.getLogger(WarnLogCategory);
		} else {
			return;
		}

		logger.error("Error wih request"+request.toString(), ex);
	}
}
