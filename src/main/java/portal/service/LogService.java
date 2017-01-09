package portal.service;

import java.util.Collection;

import portal.config.SysEnums;
import portal.domain.impl.*;

/**
 * "Service" interface for managing application logs.
 *
 */
public interface LogService {

	/**
	 * Inserts a new log into the persistent store.
	 *
	 * @param log
	 * @return the inserted log.
	 */
	public Log insertLog(Log log);

	/**
	 * Inserts a new log into the persistent store for actions performed in the web layer.
	 *
	 * @param messageCode
	 * @param severity
	 *            log severity.
	 * @return the inserted log.
	 */
	public Log insertWebLog(String messageCode, SysEnums.Severity severity);

	/**
	 * Retrieves logs. The default ordering is by createDate descending.
	 *
	 * @param entitiesReturnedLimit
	 *            the maximum number of retrieved logs.
	 * @return a collection of logs.
	 */
	public Collection<Log> getLogs(int entitiesReturnedLimit);

}
