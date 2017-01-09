package portal.repository.jdo;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.stereotype.Repository;

import portal.domain.impl.Log;
import portal.repository.*;

@Repository
public class JdoLogDAO extends JdoDAO implements LogDAO {
	@Override
	public Collection<Log> getLogs(int entitiesReturnedLimit) {
		String ordering = "createDate DESC";
		int rangeStart = 0;
		int rangeEnd = entitiesReturnedLimit;

		Collection<Log> logs = new HashSet<Log>();

		if (entitiesReturnedLimit > 0) {
			logs = getEntities(Log.class, "", ordering, rangeStart, rangeEnd);
		}

		return logs;
	}
}
