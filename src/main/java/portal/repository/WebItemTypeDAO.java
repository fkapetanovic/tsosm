package portal.repository;

import java.util.Collection;

import portal.domain.impl.WebItemType;

/**
 * Data Access Object interface providing persistence operations.
 *
 */
public interface WebItemTypeDAO extends DAO {

	/**
	 * Retrieves a web item type by its name.
	 *
	 * @param webItemTypeName
	 * @return a web item type with the given name or null if no such entity.
	 */
	public WebItemType getWebItemTypeByName(String webItemTypeName);


	/**
	 * Retrieves all web item types, default ordering is by position ascending.
	 *
	 * @return all existing web item types.
	 */
	public Collection<WebItemType> getAllWebItemTypes();
}
