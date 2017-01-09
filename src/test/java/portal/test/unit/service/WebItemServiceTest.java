package portal.test.unit.service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Properties;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import portal.config.AppPropKeys;
import portal.domain.impl.Tag;
import portal.domain.impl.WebItem;
import portal.domain.impl.WebItemImage;
import portal.domain.impl.WebItemType;
import portal.repository.WebItemDAO;
import portal.repository.WebItemImageDAO;
import portal.service.DataRequestLogService;
import portal.service.TagService;
import portal.service.WebItemTypeService;
import portal.service.impl.WebItemServiceImpl;
import portal.util.Helper;

public class WebItemServiceTest {
	@Autowired
	@InjectMocks
	private WebItemServiceImpl webItemService;

	@Mock
	private WebItemDAO webItemDao;

	@Mock
	private WebItemImageDAO webItemImageDao;

	@Mock
	private WebItemTypeService webItemTypeService;

	@Mock
	private DataRequestLogService dataRequestLogService;

	@Mock
	private TagService tagService;

	private final Properties APP_PROPERTIES = Helper.getAppProperties();
	private final String OR_DLM = APP_PROPERTIES.getProperty(AppPropKeys.LOGICAL_OR_DELIMITER);
	private final String AND_DLM = APP_PROPERTIES.getProperty(AppPropKeys.LOGICAL_AND_DELIMITER);

	private final int RETURNED_ITEMS_LIMIT = Integer.parseInt(APP_PROPERTIES.
		getProperty(AppPropKeys.RETURNED_WEB_ITEMS_LIMIT));

	@BeforeMethod
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void getWebItemByIdTest() {
		webItemService.getWebItemById(1L);
		verify(webItemDao).getEntityById(WebItem.class, 1L);
	}

	@Test
	public void insertWebItemTest() {
		WebItem webItem = new WebItem();
		webItemService.insertWebItem(webItem);
		verify(webItemDao).insertEntity(webItem);
	}

	@Test
	public void updateWebItemTest() {
		WebItem webItem = new WebItem();
		webItemService.updateWebItem(webItem);
		verify(webItemDao).updateEntity(webItem);
	}

	@Test
	public void getWeBitemImageById() {
		webItemService.getWebItemImageById(1L);
		verify(webItemImageDao).getEntityById(WebItemImage.class, 1L, "imageFetchGroup");
	}

	@Test
	public void getWeBitemImageBy() {
		webItemService.getWebItemWithImageById(1L);
		verify(webItemDao).getEntityById(WebItem.class, 1L, "imageFetchGroup");
	}

	@Test
	public void deleteWebItemTest() {
		WebItem webItem = new WebItem();
		webItemService.deleteWebItem(webItem);
		verify(webItemDao).deleteEntity(WebItem.class, webItem);
	}

	@Test
	public void getWebItemsLimitResultsTest() {
		webItemService.getWebItems(RETURNED_ITEMS_LIMIT);
		verify(webItemDao).getWebItems(RETURNED_ITEMS_LIMIT);
	}

	@Test
	public void getFeaturedWebItemsTest() {
		String webItemTypeIds = "1" + OR_DLM + "2" + OR_DLM + "4" + OR_DLM;
		String delimiter = OR_DLM;
		int rangeStart = 20;
		int rangeEnd = 40;

		Collection<WebItemType> webItemTypes = new LinkedList<WebItemType>();
		Collection<Tag> tags = new ArrayList<Tag>();

		webItemService.getFeaturedWebItems(webItemTypeIds, 2);
		when(webItemTypeService.getWebItemTypes(webItemTypeIds, delimiter)).thenReturn(webItemTypes);

		verify(webItemTypeService).getWebItemTypes(webItemTypeIds, delimiter);
		verify(dataRequestLogService).insertDataRequestLog(tags, webItemTypes);
		verify(webItemDao).getFeaturedWebItems(webItemTypes, rangeStart, rangeEnd);
	}

	@Test
	public void getWebItemsTagStringEmptyTest() {
		String tagIdGroups = "";
		int rangeStart = 20;
		int rangeEnd = 40;
		String webItemTypeIds = "1" + OR_DLM + "2" + OR_DLM;
		Collection<Tag> tags = new ArrayList<Tag>();
		Collection<WebItemType> webItemTypes = new LinkedList<WebItemType>();

		when(webItemTypeService.getWebItemTypes(webItemTypeIds, OR_DLM)).thenReturn(webItemTypes);

		webItemService.getWebItems(tagIdGroups, webItemTypeIds, 2);

		verify(webItemDao).getWebItems(tags, webItemTypes, rangeStart, rangeEnd);
		verify(webItemTypeService).getWebItemTypes(webItemTypeIds, OR_DLM);
	}

	@Test
	public void getWebItemsSeveralTagGroupsTest() {
		Collection<WebItemType> webItemTypes = new ArrayList<WebItemType>();
		Collection<Tag> tags1 = new ArrayList<Tag>();
		Collection<Tag> tags2 = new ArrayList<Tag>(1);
		tags2.add(new Tag());
		Collection<Tag> tags3 = new ArrayList<Tag>(2);
		tags3.add(new Tag());
		tags3.add(new Tag());
		int rangeStart = 0;
		int rangeEnd = 20;
		String tagIdGroup1 = "1" + AND_DLM + "2" + AND_DLM + "3";
		String tagIdGroup2 = "4" + AND_DLM + "5";
		String tagIdGroup3 = "1" + AND_DLM + "7";
		String tagIdGroups = tagIdGroup1 + OR_DLM + tagIdGroup2 + OR_DLM + tagIdGroup3 + OR_DLM;
		String webItemTypeIds = "1" + OR_DLM + "2" + OR_DLM;

		when(tagService.getTags(tagIdGroup1, AND_DLM)).thenReturn(tags1);
		when(tagService.getTags(tagIdGroup2, AND_DLM)).thenReturn(tags2);
		when(tagService.getTags(tagIdGroup3, AND_DLM)).thenReturn(tags3);
		when(webItemTypeService.getWebItemTypes(webItemTypeIds, OR_DLM)).thenReturn(webItemTypes);

		webItemService.getWebItems(tagIdGroups, webItemTypeIds, 1);

		verify(webItemTypeService).getWebItemTypes(webItemTypeIds, OR_DLM);
		verify(tagService).getTags(tagIdGroup1, AND_DLM);
		verify(tagService).getTags(tagIdGroup2, AND_DLM);
		verify(tagService).getTags(tagIdGroup3, AND_DLM);
		verify(dataRequestLogService).insertDataRequestLog(tags1, webItemTypes);
		verify(dataRequestLogService).insertDataRequestLog(tags2, webItemTypes);
		verify(dataRequestLogService).insertDataRequestLog(tags3, webItemTypes);
		verify(webItemDao).getWebItems(tags1, webItemTypes, rangeStart, rangeEnd);
		verify(webItemDao).getWebItems(tags2, webItemTypes, rangeStart, rangeEnd);
		verify(webItemDao).getWebItems(tags3, webItemTypes, rangeStart, rangeEnd);
	}

	@Test
	public void getWebItemsOneTagGroupTest() {
		Collection<WebItemType> webItemTypes = new ArrayList<WebItemType>();
		Collection<Tag> tags = new ArrayList<Tag>();
		int rangeStart = 0;
		int rangeEnd = 20;

		String tagIdGroup = "1" + AND_DLM + "2" + AND_DLM + "3";
		String webItemTypeIds = "1" + OR_DLM + "2" + OR_DLM;

		when(tagService.getTags(tagIdGroup, AND_DLM)).thenReturn(tags);
		when(webItemTypeService.getWebItemTypes(webItemTypeIds, OR_DLM)).thenReturn(webItemTypes);

		webItemService.getWebItems(tagIdGroup, webItemTypeIds, 1);

		verify(webItemTypeService).getWebItemTypes(webItemTypeIds, OR_DLM);
		verify(tagService).getTags(tagIdGroup, AND_DLM);
		verify(dataRequestLogService).insertDataRequestLog(tags, webItemTypes);
		verify(webItemDao).getWebItems(tags, webItemTypes, rangeStart, rangeEnd);
	}
}
