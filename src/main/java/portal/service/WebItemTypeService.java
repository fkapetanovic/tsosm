package portal.service;

import java.util.Collection;

import portal.domain.impl.WebItemType;

/**
 * "Service" interface for managing web item types.
 *
 */
public interface WebItemTypeService {

	/**
	 * Retrieves a web item type by its ID.
	 *
	 * @param webItemTypeId
	 * @return a web item type with the given ID or throws an exception if no such entity.
	 */
	public WebItemType getWebItemTypeById(long webItemTypeId);

	/**
	 * Retrieves a web item type by its name.
	 *
	 * @param webItemTypeName
	 * @return a web item type with the given name or null if no such entity.
	 */
	public WebItemType getWebItemTypeByName(String webItemTypeName);

	/**
	 * Retrieves web item types filtered by the query string.
	 *
	 * @param webItemTypeIds
	 *            a query string of web item type IDs delimited by a special character.
	 * @param delimiter
	 *            delimiter.
	 * @return a collection of web item types or empty collection if no such entities.
	 */
	public Collection<WebItemType> getWebItemTypes(String webItemTypeIds,
			String delimiter);

	/**
	 * Retrieves all web item types.
	 *
	 * @return collection of all web item types present in the persistent store.
	 */
	public Collection<WebItemType> getAllWebItemTypes();
}
