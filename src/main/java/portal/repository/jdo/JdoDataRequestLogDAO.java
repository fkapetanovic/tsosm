package portal.repository.jdo;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.stereotype.Repository;

import portal.domain.impl.DataRequestLog;
import portal.repository.DataRequestLogDAO;

@Repository
public class JdoDataRequestLogDAO extends JdoDAO implements DataRequestLogDAO {
	@Override
	public Collection<DataRequestLog> getDataRequestLogs(int entitiesReturnedLimit) {
		String ordering = "createDate DESC";
		int rangeStart = 0;
		int rangeEnd = entitiesReturnedLimit;

		Collection<DataRequestLog> dataRequestLogs = new HashSet<DataRequestLog>();

		if (entitiesReturnedLimit > 0) {
			dataRequestLogs = getEntities(DataRequestLog.class, "", ordering,
					rangeStart, rangeEnd);
		}

		return dataRequestLogs;
	}
}
