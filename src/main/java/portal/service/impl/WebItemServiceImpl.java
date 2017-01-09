package portal.service.impl;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import portal.config.AppPropKeys;

import portal.domain.impl.Tag;
import portal.domain.impl.WebItem;
import portal.domain.impl.WebItemImage;
import portal.domain.impl.WebItemType;
import portal.repository.WebItemDAO;
import portal.repository.WebItemImageDAO;
import portal.service.DataRequestLogService;
import portal.service.TagService;
import portal.service.WebItemService;
import portal.service.WebItemTypeService;
import portal.util.Helper;

@Service
@Transactional(rollbackFor = Exception.class)
public class WebItemServiceImpl implements WebItemService {
	@Autowired
	private WebItemDAO webItemDao;

	@Autowired
	private WebItemImageDAO webItemImageDAO;

	@Autowired
	private WebItemTypeService webItemTypeService;

	@Autowired
	private TagService tagService;

	@Autowired
	private DataRequestLogService dataRequestLogService;

	private final Properties APP_PROPERTIES = Helper.getAppProperties();
	private final int PAGE_SIZE = Integer.parseInt(APP_PROPERTIES
			.getProperty(AppPropKeys.RETURNED_WEB_ITEMS_LIMIT));
	private final String OR_DELIMITER = APP_PROPERTIES
			.getProperty(AppPropKeys.LOGICAL_OR_DELIMITER);
	private final String AND_DELIMITER = APP_PROPERTIES
			.getProperty(AppPropKeys.LOGICAL_AND_DELIMITER);

	@Override
	public WebItem getWebItemById(long webItemId) {
		return webItemDao.getEntityById(WebItem.class, webItemId);
	}

	@Override
	public Collection<WebItem> getWebItems(int entitiesReturnedLimit) {
		return webItemDao.getWebItems(entitiesReturnedLimit);
	}

	@Override
	public Collection<WebItem> getWebItems(String tagIdGroups,
			String webItemTypeIds, int page) {
		int pageSize = PAGE_SIZE;
		Collection<Tag> tags;
		Collection<WebItem> resultSet = new LinkedHashSet<WebItem>();

		if (tagIdGroups == "" && webItemTypeIds == "") {
			resultSet = getFeaturedWebItems(webItemTypeIds, page);
		}
		else {
			Collection<WebItem> webItems = new LinkedHashSet<WebItem>();
			Collection<WebItemType> webItemTypes = webItemTypeService
					.getWebItemTypes(webItemTypeIds, OR_DELIMITER);

			String[] tagIdGroupsArray = tagIdGroups.split(OR_DELIMITER);

			int rangeStart = (page - 1) * pageSize;
			int rangeEnd = page * pageSize;

			for (String tagIds : tagIdGroupsArray) {

				tags = tagService.getTags(tagIds, AND_DELIMITER);
				dataRequestLogService.insertDataRequestLog(tags, webItemTypes);
				webItems = webItemDao.getWebItems(tags, webItemTypes,
						rangeStart, rangeEnd);

				if (webItems.size() > 0) {
					resultSet.addAll(webItems);
				}
			}
		}
		return resultSet;
	}

	@Override
	public Collection<WebItem> getFeaturedWebItems(String webItemTypeIds, int page) {
		Collection<WebItem> result = new LinkedHashSet<WebItem>();
		Collection<Tag> tags = new ArrayList<Tag>();

		Collection<WebItemType> webItemTypes = webItemTypeService
				.getWebItemTypes(webItemTypeIds, OR_DELIMITER);

		int rangeStart = (page - 1) * PAGE_SIZE;
		int rangeEnd = page * PAGE_SIZE;

		dataRequestLogService.insertDataRequestLog(tags, webItemTypes);
		result = webItemDao.getFeaturedWebItems(webItemTypes, rangeStart,
				rangeEnd);

		return result;
	}

	@Override
	@Secured({ "ROLE_EDITOR","ROLE_MODERATOR" })
	public WebItem insertWebItem(WebItem webItem) {
		return webItemDao.insertEntity(webItem);
	}

	@Override
	@Secured({ "ROLE_EDITOR","ROLE_MODERATOR" })
	public WebItem updateWebItem(WebItem webItem) {
		return webItemDao.updateEntity(webItem);
	}

	@Override
	@Secured({ "ROLE_EDITOR","ROLE_MODERATOR" })
	public void deleteWebItem(WebItem webItem) {
		webItemDao.deleteEntity(WebItem.class, webItem);
	}

	@SuppressWarnings("unused")
	private Collection<WebItem> createFinalResultSetFromCollectionOfSets(
			Collection<Collection<WebItem>> setOfWebItemResults) {
		int numberOfSets = setOfWebItemResults.size();
		int numberOfInsertedItems = 0;

		WebItem currentWebItem;
		Set<WebItem> finalResultSet = new LinkedHashSet<WebItem>();

		Iterator<WebItem> iterator;
		List<Iterator<WebItem>> iterators = new ArrayList<Iterator<WebItem>>();
		Set<Iterator<WebItem>> exhaustedIterators = new HashSet<Iterator<WebItem>>();

		for (Collection<WebItem> item : setOfWebItemResults) {
			iterators.add(item.iterator());
		}

		for (int i = numberOfSets; numberOfInsertedItems < Integer
				.parseInt(APP_PROPERTIES
						.getProperty(AppPropKeys.RETURNED_WEB_ITEMS_LIMIT)); i++) {
			iterator = iterators.get(i % numberOfSets);
			if (iterator.hasNext()) {
				currentWebItem = (WebItem) iterator.next();
				if (!finalResultSet.contains(currentWebItem)) {
					finalResultSet.add(currentWebItem);
					numberOfInsertedItems++;
				}
			} else {
				exhaustedIterators.add(iterator);
				if (exhaustedIterators.size() >= numberOfSets) {
					break;
				}
			}
		}
		return finalResultSet;
	}

	@Override
	public WebItemImage saveWebItemImage(WebItemImage webItemImage) {
		if (webItemImage.isNew()) {
			return webItemImageDAO.insertEntity(webItemImage);
		} else {
			return webItemImageDAO.updateEntity(webItemImage);
		}
	}

	@Override
	public WebItemImage getWebItemImageById(long webItemImageId) {
		return webItemImageDAO.getEntityById(WebItemImage.class,
				webItemImageId, "imageFetchGroup");
	}

	@Override
	public Collection<WebItem> getWebItemsByUserId(long UserId, int page, int year) {
		int rangeStart = (page - 1) * PAGE_SIZE;
		int rangeEnd = page * PAGE_SIZE;

		Calendar cal = Calendar.getInstance();

		Date dateStart = new Date();
		cal.set(year, 0, 1);
		dateStart = cal.getTime();

		Date dateEnd = new Date();
		cal.set(year + 1, 0, 1);
		dateEnd = cal.getTime();

		return webItemDao.getWebItemsByUserId(UserId, rangeStart, rangeEnd,
				dateStart, dateEnd);
	}

	@Override
	public WebItem getWebItemWithImageById(long webItemId) {
		return webItemDao.getEntityById(WebItem.class, webItemId,
				"imageFetchGroup");
	}

	@Override
	public  WebItemImage getWebItemImageFromUrl(String urlAsString,
		WebItemImage webItemImage) throws MalformedURLException,
		IOException {

		final int ImageSize  = Integer.parseInt(APP_PROPERTIES.getProperty(AppPropKeys.WEBITEMIMAGESIZE));

		if (urlAsString == "" || urlAsString == null) {
			return null;
		}

		BufferedImage image;

		if (webItemImage == null) {
			webItemImage = new WebItemImage();
		}

		webItemImage.setCreateDate(new Date());
		URL url = new URL(urlAsString);
		URLConnection uc = null;
		uc = url.openConnection();
		String stringType = uc.getContentType();
		image = ImageIO.read(uc.getInputStream());

		if (image == null) {
			throw new IOException();
		}

		if (image.getWidth() > 150 || image.getHeight() > 150) {
			image= Scalr.resize(image, ImageSize);
		}

		webItemImage.setSourceURL(urlAsString);
		webItemImage.setImage(image);
		webItemImage.setMimeType(stringType);

		return webItemImage;
	}
}
