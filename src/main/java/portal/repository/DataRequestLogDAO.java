package portal.repository;

import java.util.Collection;

import portal.domain.impl.DataRequestLog;

/**
 * Data Access Object interface providing persistence operations.
 *
 */
public interface DataRequestLogDAO extends DAO {

	/**
	 * Retrieves data request logs. The default ordering is by createDate
	 * descending.
	 *
	 * @param entitiesReturnedLimit
	 *            the max number of retrieved data request logs.
	 * @return a collection of data request logs.
	 */
	public Collection<DataRequestLog> getDataRequestLogs(
			int entitiesReturnedLimit);
}
