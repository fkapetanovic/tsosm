package portal.service.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import portal.config.SysEnums;
import portal.domain.impl.Log;
import portal.repository.LogDAO;
import portal.security.context.SecurityContextFacade;
import portal.service.LogService;
import portal.web.context.RequestContextFacade;

@Service
@Transactional(rollbackFor = Exception.class)
public class LogServiceImpl implements LogService{
  @Autowired
	private LogDAO logDao;

  @Autowired
	private SecurityContextFacade securityContext;

	@Autowired
	@Qualifier("requestContext")
	private RequestContextFacade requestContext;

	@Autowired
	private MessageSource messageSource;

	@Override
	public Collection<Log> getLogs(int entitiesReturnedLimit) {
		return logDao.getLogs(entitiesReturnedLimit);
	}

	@Override
	public Log insertLog(Log log) {
		return logDao.insertEntity(log);
	}

	@Override
	public Log insertWebLog(String messageCode, SysEnums.Severity severity) {
		Log log = new Log();

		log.setCreatedBy(securityContext.getLoggedInUser());
		log.setIpAddress(requestContext.ipAddress());
		log.setText( messageSource.getMessage(messageCode, null, null));
		log.setSeverity(severity.getValue());

		return this.insertLog(log);
	}
}
