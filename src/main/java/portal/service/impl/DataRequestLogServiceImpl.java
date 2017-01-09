package portal.service.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import portal.domain.impl.DataRequestLog;
import portal.domain.impl.Tag;
import portal.domain.impl.WebItemType;
import portal.repository.DataRequestLogDAO;
import portal.security.context.SecurityContextFacade;
import portal.service.DataRequestLogService;
import portal.web.context.RequestContextFacade;

@Service
@Transactional(rollbackFor = Exception.class)
public class DataRequestLogServiceImpl implements DataRequestLogService{
  @Autowired
	private DataRequestLogDAO dataRequestLogDao;

  @Autowired
	private SecurityContextFacade securityContextFacade;

	@Autowired
	@Qualifier("requestContext")
	private RequestContextFacade requestContext;

	@Override
	public DataRequestLog insertDataRequestLog(DataRequestLog dataRequestLog) {
		return dataRequestLogDao.insertEntity(dataRequestLog);
	}

	@Override
	public Collection<DataRequestLog> getDataRequestLogs(int entitiesReturnedLimit) {
		return dataRequestLogDao.getDataRequestLogs(entitiesReturnedLimit);
	}

	@Override
	public DataRequestLog insertDataRequestLog(Collection<Tag> tags,
			Collection<WebItemType> webItemTypes) {
		DataRequestLog dataRequestLog = new DataRequestLog();

		dataRequestLog.setRequestedTags(tags);
		dataRequestLog.setRequestedWebItemTypes(webItemTypes);
		dataRequestLog.setCreatedBy(securityContextFacade.getLoggedInUser());
		dataRequestLog.setIpAddress(requestContext.ipAddress());

		return this.insertDataRequestLog(dataRequestLog);
	}
}
