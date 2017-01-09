package portal.repository;

import java.util.Collection;

import portal.domain.impl.Log;

/**
 * Data Access Object interface providing persistence operations.
 *
 */
public interface LogDAO extends DAO {
	/**
	 * Retrieves logs. The default ordering is by createDate descending.
	 *
	 * @param entitiesReturnedLimit
	 *            the maximum number of retrieved logs.
	 * @return a collection of logs.
	 */
	public Collection<Log> getLogs(int entitiesReturnedLimit);
}
