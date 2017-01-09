package portal.service;

import java.util.Collection;

import portal.domain.impl.*;

/**
 * "Service" interface for managing data request logs.
 *
 */
public interface DataRequestLogService {

	/**
	 * Inserts a new data request log into the persistent store.
	 *
	 * @param dataRequestLog
	 * @return the inserted data request log.
	 */
	public DataRequestLog insertDataRequestLog(DataRequestLog dataRequestLog);

	/**
	 * Inserts a new data request log into the persistent store.
	 *
	 * @param tags
	 *            the collection of tags that the user selected.
	 * @param webItemTypes
	 *            the collection of web item types that the user selected.
	 * @return the inserted data request log.
	 */
	public DataRequestLog insertDataRequestLog(Collection<Tag> tags,
			Collection<WebItemType> webItemTypes);

	/**
	 * Retrieves data request logs. The default ordering is by createDate
	 * descending.
	 *
	 * @param entitiesReturnedLimit
	 *            the maximum number of retrieved data request logs.
	 * @return a collection of data request logs.
	 */
	public Collection<DataRequestLog> getDataRequestLogs(
			int entitiesReturnedLimit);
}
