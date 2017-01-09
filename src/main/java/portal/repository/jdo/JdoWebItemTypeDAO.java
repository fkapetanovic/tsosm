package portal.repository.jdo;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.stereotype.Repository;

import portal.domain.impl.WebItemType;
import portal.repository.WebItemTypeDAO;

@Repository
public class JdoWebItemTypeDAO extends JdoDAO implements WebItemTypeDAO {
	@Override
	public WebItemType getWebItemTypeByName(String webItemTypeName) {
		String filter = "name == p1";
		String parameters = "java.lang.String p1";
		String ordering = "";

		Object[] values = new Object[1];

		values[0] = webItemTypeName;

		Collection<WebItemType> webItemTypes = getEntities(WebItemType.class,
				filter, parameters, ordering, values);

		if (webItemTypes.isEmpty()) {
			return null;
		}

		return webItemTypes.iterator().next();
	}

	@Override
	public Collection<WebItemType> getAllWebItemTypes() {
		Collection<WebItemType> webItemTypes = new ArrayList<WebItemType>();

		String filter = "";
		String parameters = "";
		String ordering = "position ASC";

		Object[] values = new Object[0];

		webItemTypes = getEntities(WebItemType.class, filter, parameters,
				ordering, values);

		return webItemTypes;
	}
}
