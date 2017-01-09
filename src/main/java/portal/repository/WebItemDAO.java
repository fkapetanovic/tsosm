package portal.repository;

import java.util.Collection;
import java.util.Date;

import portal.domain.impl.Tag;
import portal.domain.impl.WebItem;
import portal.domain.impl.WebItemType;

/**
 * Data Access Object interface providing persistence operations.
 *
 */
public interface WebItemDAO extends DAO {

	/**
	 * Retrieves web items. The default ordering is by createDate descending.
	 *
	 * @param entitiesReturnedLimit
	 *            the maximum number of retrieved web items.
	 * @return a collection of web items.
	 */
	public Collection<WebItem> getWebItems(int entitiesReturnedLimit);

	/***
	 * Retrieves web items filtered by the collection of tags and the collection of web item types.
	 * A web item is matched if its belonging tags match the provided
	 * collection of tags, and if its type matches at least one of the types passed
	 * in the given collection of web item types. The default ordering is by createDate
	 * descending. With customized range.
	 *
	 * @param tags
	 *            the collection of tags used for filtering.
	 * @param webItemTypes
	 *            the collection of web item types used for filtering.
	 * @param rangeStart
	 *            start offset.
	 * @param rangeEnd
	 *            end offset.
	 * @return a collection of web items or an empty collection if no items match given
	 *         search criteria.
	 */
	public Collection<WebItem> getWebItems(Collection<Tag> tags,
			Collection<WebItemType> webItemTypes, int rangeStart, int rangeEnd);

	/**
	 * Retrieves web items marked as featured and of only those types contained
	 * in the passed collection of types. The default ordering is by createDate
	 * descending.
	 *
	 * @param webItemTypes
	 *            the collection of web item types used for filtering. Pass an empty
	 *            collection to retrieve items of all types.
	 * @param rangeStart
	 *            start offset.
	 * @param rangeEnd
	 *            end offset.
	 * @return a collection of web items or an empty collection if no items match the
	 *         search criteria.
	 */
	public Collection<WebItem> getFeaturedWebItems(
			Collection<WebItemType> webItemTypes, int rangeStart, int rangeEnd);

	/**
	 * Retrieves web items created by a certain user.
	 *
	 * @param UserId
	 * @param rangeStart
	 *            start offset.
	 * @param rangeEnd
	 *            end offset.
	 * @param dateParamStart
	 * @param dateParamEnd
	 * @return a collection of web items or an empty collection if no items match the
	 *         search criteria.
	 */
	public Collection<WebItem> getWebItemsByUserId(long UserId,
			long rangeStart, long rangeEnd, Date dateParamStart,
			Date dataParamEnd);
}
