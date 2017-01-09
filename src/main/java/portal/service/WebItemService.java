package portal.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Collection;

import portal.domain.impl.WebItem;
import portal.domain.impl.WebItemImage;

/**
 * "Service" interface for managing web items.
 *
 */
public interface WebItemService {

	/**
	 * Retrieves a web item by its ID.
	 *
	 * @param webItemId
	 * @return a web item with the given ID or throws an exception if no such entity.
	 */
	public WebItem getWebItemById(long webItemId);

	/**
	 * Retrieves web items. The default ordering is by createDate descending.
	 *
	 * @param entitiesReturnedLimit
	 *            the maximum number of retrieved web items.
	 * @return a collection of web items or empty collection if no such entities.
	 */
	public Collection<WebItem> getWebItems(int entitiesReturnedLimit);

	/**
	 * Retrieves web items filtered by the groups of tag IDs and web item type
	 * IDs. The expected format is:
	 * tagA1Id-tagA2Id-tagA3Id_tagB1Id-tagB2Id_tagC1Id_ for the tag groups, and
	 * webItemType1Id_webItemType2Id_webItemType3Id_ for the web item types.
	 * Basically, "-" represents a logical AND while "_" represents a logical
	 * OR. The default ordering is by createDate descending.
	 *
	 * @param tagIdGroups
	 *            the query string segment containing 1 to m groups where each
	 *            group holds 1 to n tag IDs. If empty, the most recent
	 *            featured web items are returned.
	 * @param webItemTypeIds
	 *            the query string segment containing 1 to p web item type IDs.
	 *            If empty, web items of all types are returned.
	 * @param page
	 *            current page.
	 * @return a collection of web items or an empty collection, if no items match
	 *         the search criteria.
	 */
	public Collection<WebItem> getWebItems(String tagIdGroups,
			String webItemTypeIds, int page);

	/**
	 * Retrieves web items marked as featured/top stories filtered by the web
	 * item type IDs. The default ordering is by createDate descending. With
	 * pagination.
	 *
	 * @param webItemTypeIds
	 *            a query string of "_" delimited web item type IDs. An empty string
	 *            retrieves items of all types.
	 * @param page
	 *            current page.
	 * @return a collection of web items or an empty collection if no items match the
	 *         search criteria.
	 */
	public Collection<WebItem> getFeaturedWebItems(String webItemTypeIds,
			int page);

	/**
	 * Inserts a new web item into the persistent store.
	 *
	 * @param webItem
	 * @return the inserted web item.
	 */
	public WebItem insertWebItem(WebItem webItem);

	/**
	 * Saves a modified web item into the persistent store.
	 *
	 * @param webItem
	 * @return the updated web item.
	 */
	public WebItem updateWebItem(WebItem webItem);

	/**
	 * Deletes a web item from the persistent store.
	 *
	 * @param webItem
	 */
	public void deleteWebItem(WebItem webItem);

	/**
	 * Inserts a WebItemImage
	 *
	 * @param webItemImage
	 * @return
	 */
	public WebItemImage saveWebItemImage(WebItemImage webItemImage);

	/**
	 * Returns an image by its ID
	 *
	 * @param webItemImageId
	 * @return
	 */
	public WebItemImage getWebItemImageById(long webItemImageId);

	/**
	 *
	 * @param UserId
	 * @param page
	 *            current page.
	 * @param year
	 *            the year for which to get the web items for. Null to ignore this
	 *            field.
	 * @return
	 */
	public Collection<WebItem> getWebItemsByUserId(long UserId, int page,
			int year);

	/**
	 *
	 * @param webItemId
	 * @return a webItem with Image loaded.
	 */
	public WebItem getWebItemWithImageById(long webItemId);

    /**
     *
     * @param urlAsString
     *            an url from which to fetch the image
     * @param webItemImage
     *            the webItemImage you want to use, if you don't want a new one
     *            created.
     * @return a webItemImage
     * @throws IOException
     * @throws MalformedURLException
     */
    	public  WebItemImage getWebItemImageFromUrl(String urlAsString,
    			WebItemImage webItemImage) throws MalformedURLException, IOException;
}
