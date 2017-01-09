package portal.test.integration.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import portal.config.AppPropKeys;
import portal.domain.impl.DataRequestLog;
import portal.domain.impl.Tag;
import portal.domain.impl.User;
import portal.domain.impl.WebItem;
import portal.domain.impl.WebItemType;
import portal.service.DataRequestLogService;
import portal.service.TagService;
import portal.service.UserService;
import portal.service.WebItemService;
import portal.service.WebItemTypeService;
import portal.test.integration.AbstractIntegrationTest;
import portal.test.integration.TestEntities;
import portal.util.Helper;

public class WebItemServiceIntegrationTest extends AbstractIntegrationTest {
	@Autowired
	private TagService tagService;

	@Autowired
	private UserService userService;

	@Autowired
	private WebItemService webItemService;

	@Autowired
	private WebItemTypeService webItemTypeService;

	@Autowired
	private DataRequestLogService dataRequestLogService;

	private User user;

	private Tag tag1;
	private Tag tag2;
	private Tag tag3;
	private Tag tag4;
	private Tag tag5;
	private Tag tag6;

	private WebItemType webItemType1;
	private WebItemType webItemType2;

	private final Properties APP_PROPERTIES = Helper.getAppProperties();
	private final String OR_DLM = APP_PROPERTIES.getProperty(AppPropKeys.LOGICAL_OR_DELIMITER);
	private final String AND_DLM = APP_PROPERTIES.
			getProperty(AppPropKeys.LOGICAL_AND_DELIMITER);

	@BeforeMethod
	public void setUp() {
		user = userService.loadUserByUsername(TestEntities.TEST_USER_1);

		tag1 = TestEntities.createTag1();
		tag2 = TestEntities.createTag2();
		tag3 = TestEntities.createTag3();
		tag4 = TestEntities.createTag4();
		tag5 = TestEntities.createPrimaryTag1();
		tag6 = TestEntities.createPrimaryTag2();

		tag1.setCreatedBy(user);
		tag2.setCreatedBy(user);
		tag3.setCreatedBy(user);
		tag4.setCreatedBy(user);
		tag5.setCreatedBy(user);
		tag6.setCreatedBy(user);

		tag1 = tagService.insertTag(tag1);
		tag2 = tagService.insertTag(tag2);
		tag3 = tagService.insertTag(tag3);
		tag4 = tagService.insertTag(tag4);
		tag5 = tagService.insertTag(tag5);
		tag6 = tagService.insertTag(tag6);

		webItemType1 = webItemTypeService
				.getWebItemTypeByName(TestEntities.WEB_ITEM_TYPE_1);
		webItemType2 = webItemTypeService
				.getWebItemTypeByName(TestEntities.WEB_ITEM_TYPE_2);
	}

	@Test
	public void insertWebItemTest() {
		WebItem webItem = TestEntities.createWebItem1();

		webItem.setCreatedBy(user);
		webItem.getTags().add(tag1);
		webItem.getTags().add(tag2);
		webItem.setWebItemType(webItemType1);

		WebItem insertedWebItem = webItemService.insertWebItem(webItem);

		Collection<WebItem> webItems = webItemService.getWebItems(1);

		Assert.assertEquals(webItems.size(), 1);
		Assert.assertEquals(insertedWebItem.getCreatedBy(), webItem.getCreatedBy());
		Assert.assertEquals(insertedWebItem.getSourceName(), webItem.getSourceName());
		Assert.assertEquals(insertedWebItem.getSourceURL(), webItem.getSourceURL());
		Assert.assertEquals(insertedWebItem.getText(), webItem.getText());
		Assert.assertEquals(insertedWebItem.getTitle(), webItem.getTitle());
		Assert.assertEquals(insertedWebItem.getWebItemDate(), webItem.getWebItemDate());
		Assert.assertEquals(insertedWebItem.getWebItemType(), webItem.getWebItemType());
		Assert.assertEquals(insertedWebItem.getTags().size(), webItem.getTags().size());
	}

	@Test
	public void updateWebItemTest() {
		WebItem webItem = TestEntities.createWebItem1();

		webItem.setCreatedBy(user);
		webItem.getTags().add(tag1);
		webItem.getTags().add(tag2);
		webItem.setWebItemType(webItemType1);

		webItem = webItemService.insertWebItem(webItem);

		Date updatedWebItemDate = new Date();

		webItem.setFeatured(true);
		webItem.setSourceName("updated source name");
		webItem.setSourceURL("updated source url");
		webItem.setText("updated text");
		webItem.setTitle("updated title");
		webItem.setUpdatedBy(user);
		webItem.setWebItemDate(updatedWebItemDate);
		webItem.setWebItemType(webItemType2);

		WebItem updatedWebItem = webItemService.updateWebItem(webItem);

		Assert.assertEquals(updatedWebItem.isFeatured(), true);
		Assert.assertEquals(updatedWebItem.getSourceName(),
				"updated source name");
		Assert.assertEquals(updatedWebItem.getSourceURL(), "updated source url");
		Assert.assertEquals(updatedWebItem.getText(), "updated text");
		Assert.assertEquals(updatedWebItem.getTitle(), "updated title");
		Assert.assertEquals(updatedWebItem.getWebItemDate(), updatedWebItemDate);
		Assert.assertEquals(updatedWebItem.getWebItemType(), webItemType2);
	}

	@Test
	public void updateWebItemsTagsTest() {
		WebItem webItem = TestEntities.createWebItem1();

		webItem.setCreatedBy(user);
		webItem.getTags().add(tag1);
		webItem.getTags().add(tag2);
		webItem.setWebItemType(webItemType1);

		webItem = webItemService.insertWebItem(webItem);

		// add one more tag to web item
		webItem.getTags().add(tag3);
		webItem = webItemService.updateWebItem(webItem);

		// new tag actually added?
		Assert.assertEquals(webItem.getTags().size(), 3);

		// remove tag2 and tag3
		webItem.getTags().clear();
		webItem.getTags().add(tag1);
		webItem = webItemService.updateWebItem(webItem);

		// tag2 and tag3 removed?
		Assert.assertEquals(webItem.getTags().size(), 1);
		Assert.assertEquals(webItem.getTags().iterator().next().getId(),
				tag1.getId());

		// remove all tags
		webItem.getTags().clear();
		webItem = webItemService.updateWebItem(webItem);

		// all tags removed?
		Assert.assertEquals(webItem.getTags().size(), 0);
	}

	@Test
	public void deleteWebItemTest() {
		WebItem webItem = TestEntities.createFeaturedWebItem1();

		webItem.setCreatedBy(user);
		webItem.getTags().add(tag1);
		webItem.getTags().add(tag2);
		webItem.setWebItemType(webItemType1);

		webItem = webItemService.insertWebItem(webItem);

		Collection<WebItem> webItems = webItemService.getWebItems(1);

		// item inserted?
		Assert.assertEquals(webItems.size(), 1);

		webItemService.deleteWebItem(webItem);

		webItem = webItemService.getWebItemById(webItem.getId());

		// item marked as 'deleted'?
		Assert.assertTrue(webItem.isDeleted());

		webItems = webItemService.getWebItems(1);

		// item omitted from lookup results?
		Assert.assertEquals(webItems.size(), 0);
	}

	@Test
	public void getWebItemByIdTest() {
		WebItem webItem1 = TestEntities.createFeaturedWebItem1();
		WebItem webItem2 = TestEntities.createFeaturedWebItem2();

		webItem1.setCreatedBy(user);
		webItem1.getTags().add(tag1);
		webItem1.getTags().add(tag2);
		webItem1.setWebItemType(webItemType1);

		webItem2.setCreatedBy(user);
		webItem2.getTags().add(tag3);
		webItem2.setWebItemType(webItemType1);

		webItem1 = webItemService.insertWebItem(webItem1);
		webItem2 = webItemService.insertWebItem(webItem2);

		WebItem result1 = webItemService.getWebItemById(webItem1.getId());
		WebItem result2 = webItemService.getWebItemById(webItem2.getId());

		Assert.assertEquals(result1.getId(), webItem1.getId());
		Assert.assertEquals(result2.getId(), webItem2.getId());
	}

	@Test
	public void getWebItemsTest() {
		WebItem webItem1 = TestEntities.createFeaturedWebItem1();
		WebItem webItem2 = TestEntities.createFeaturedWebItem2();
		WebItem webItem3 = TestEntities.createFeaturedWebItem3();

		webItem1.setCreatedBy(user);
		webItem1.getTags().add(tag1);
		webItem1.getTags().add(tag2);
		webItem1.setWebItemType(webItemType1);

		webItem2.setCreatedBy(user);
		webItem2.getTags().add(tag3);
		webItem2.setWebItemType(webItemType1);

		webItem3.setCreatedBy(user);
		webItem3.getTags().add(tag4);
		webItem3.setWebItemType(webItemType1);

		webItem1 = webItemService.insertWebItem(webItem1);
		webItem2 = webItemService.insertWebItem(webItem2);
		webItem3 = webItemService.insertWebItem(webItem3);

		Collection<WebItem> webItems = webItemService.getWebItems(3);
		Assert.assertEquals(webItems.size(), 3);

		webItems = webItemService.getWebItems(2);
		Assert.assertEquals(webItems.size(), 2);

		webItems = webItemService.getWebItems(0);
		Assert.assertEquals(webItems.size(), 0);

		webItems = webItemService.getWebItems(4);
		Assert.assertEquals(webItems.size(), 3);
	}

	@Test
	public void getFeaturedWebItemsTest() {
		WebItem featuredWebItem1 = TestEntities.createFeaturedWebItem1();
		WebItem featuredWebItem2 = TestEntities.createFeaturedWebItem2();
		WebItem featuredWebItem3 = TestEntities.createFeaturedWebItem3();
		WebItem webItem1 = TestEntities.createWebItem1();

		featuredWebItem1.setCreatedBy(user);
		featuredWebItem1.getTags().add(tag1);
		featuredWebItem1.getTags().add(tag2);
		featuredWebItem1.setWebItemType(webItemType1);

		featuredWebItem2.setCreatedBy(user);
		featuredWebItem2.getTags().add(tag3);
		featuredWebItem2.setWebItemType(webItemType2);

		featuredWebItem3.setCreatedBy(user);
		featuredWebItem3.getTags().add(tag4);
		featuredWebItem3.setWebItemType(webItemType1);

		webItem1.setCreatedBy(user);
		webItem1.setWebItemType(webItemType1);

		featuredWebItem1 = webItemService.insertWebItem(featuredWebItem1);
		featuredWebItem2 = webItemService.insertWebItem(featuredWebItem2);
		featuredWebItem3 = webItemService.insertWebItem(featuredWebItem3);
		webItem1 = webItemService.insertWebItem(webItem1);

		// empty string = all web item types
		Collection<WebItem> webItems = webItemService.getFeaturedWebItems("",
				1);
		Assert.assertEquals(webItems.size(), 3);

		// get web items of web item type 1
		webItems = webItemService.getFeaturedWebItems(webItemType1.getId()
				+ OR_DLM, 1);
		Assert.assertEquals(webItems.size(), 2);

		// get web items of web item type 2
		webItems = webItemService.getFeaturedWebItems(webItemType2.getId()
				+ OR_DLM, 1);
		Assert.assertEquals(webItems.size(), 1);

		// get web items of web item type 1 and type 2
		webItems = webItemService.getFeaturedWebItems(webItemType1.getId()
				+ OR_DLM + webItemType2.getId() + OR_DLM, 1);
		Assert.assertEquals(webItems.size(), 3);
	}

	@Test
	public void getWebItemsByQueryStringTest() {
		WebItem featuredWebItem1 = TestEntities.createFeaturedWebItem1();
		WebItem featuredWebItem2 = TestEntities.createFeaturedWebItem2();
		WebItem featuredWebItem3 = TestEntities.createFeaturedWebItem3();
		WebItem webItem1 = TestEntities.createWebItem1();
		WebItem webItem2 = TestEntities.createWebItem2();

		featuredWebItem1.setCreatedBy(user);
		featuredWebItem1.getTags().add(tag1);
		featuredWebItem1.getTags().add(tag2);
		featuredWebItem1.getTags().add(tag5);
		featuredWebItem1.setWebItemType(webItemType1);

		featuredWebItem2.setCreatedBy(user);
		featuredWebItem2.getTags().add(tag3);
		featuredWebItem2.setWebItemType(webItemType2);

		featuredWebItem3.setCreatedBy(user);
		featuredWebItem3.getTags().add(tag1);
		featuredWebItem3.getTags().add(tag4);
		featuredWebItem3.setWebItemType(webItemType1);

		webItem1.setCreatedBy(user);
		webItem1.getTags().add(tag2);
		webItem1.getTags().add(tag5);
		webItem1.getTags().add(tag6);
		webItem1.setWebItemType(webItemType2);

		webItem2.setCreatedBy(user);
		webItem2.getTags().add(tag1);
		webItem2.getTags().add(tag2);
		webItem2.getTags().add(tag4);
		webItem2.getTags().add(tag6);
		webItem2.setWebItemType(webItemType1);

		featuredWebItem1 = webItemService.insertWebItem(featuredWebItem1);
		featuredWebItem2 = webItemService.insertWebItem(featuredWebItem2);
		featuredWebItem3 = webItemService.insertWebItem(featuredWebItem3);
		webItem1 = webItemService.insertWebItem(webItem1);
		webItem2 = webItemService.insertWebItem(webItem2);

		String tagIdGroups = tag3.getId() + OR_DLM;
		String webItemTypeIds = "";

		Collection<WebItem> webItems = webItemService.getWebItems(tagIdGroups,
				webItemTypeIds, 1);
		Assert.assertEquals(webItems.size(), 1);

		tagIdGroups = tag6.getId() + OR_DLM;
		webItemTypeIds = webItemType1.getId() + OR_DLM;

		webItems = webItemService.getWebItems(tagIdGroups, webItemTypeIds, 1);
		Assert.assertEquals(webItems.size(), 1);

		webItemTypeIds = "";

		webItems = webItemService.getWebItems(tagIdGroups, webItemTypeIds, 1);
		Assert.assertEquals(webItems.size(), 2);

		tagIdGroups = tag2.getId() + AND_DLM + tag5.getId() + OR_DLM
				+ tag6.getId() + OR_DLM;
		webItemTypeIds = webItemType1.getId() + OR_DLM;

		webItems = webItemService.getWebItems(tagIdGroups, webItemTypeIds, 1);
		Assert.assertEquals(webItems.size(), 2);

		Collection<Long> webItemIds = new ArrayList<Long>();

		for (WebItem webItem : webItems) {
			webItemIds.add(webItem.getId());
		}

		Assert.assertTrue(webItemIds.contains(featuredWebItem1.getId())
				&& webItemIds.contains(webItem2.getId()));

		tagIdGroups = tag1.getId() + AND_DLM + tag2.getId() + AND_DLM
				+ tag5.getId() + OR_DLM + tag1.getId() + AND_DLM + tag4.getId()
				+ OR_DLM + tag3.getId() + OR_DLM;

		webItemTypeIds = webItemType1.getId() + OR_DLM + webItemType2.getId()
				+ OR_DLM;

		webItems = webItemService.getWebItems(tagIdGroups, webItemTypeIds, 1);
		Assert.assertEquals(webItems.size(), 4);

		for (WebItem webItem : webItems) {
			webItemIds.add(webItem.getId());
		}

		Assert.assertTrue(webItemIds.contains(featuredWebItem1.getId())
				&& webItemIds.contains(featuredWebItem3.getId())
				&& webItemIds.contains(webItem2.getId())
				&& webItemIds.contains(featuredWebItem2.getId()));
	}

	@Test
	public void dataRequestLogsTest() {
		WebItem featuredWebItem1 = TestEntities.createFeaturedWebItem1();
		WebItem featuredWebItem2 = TestEntities.createFeaturedWebItem2();
		WebItem featuredWebItem3 = TestEntities.createFeaturedWebItem3();
		WebItem webItem1 = TestEntities.createWebItem1();
		WebItem webItem2 = TestEntities.createWebItem2();

		featuredWebItem1.setCreatedBy(user);
		featuredWebItem1.getTags().add(tag1);
		featuredWebItem1.getTags().add(tag2);
		featuredWebItem1.getTags().add(tag5);
		featuredWebItem1.setWebItemType(webItemType1);

		featuredWebItem2.setCreatedBy(user);
		featuredWebItem2.getTags().add(tag3);
		featuredWebItem2.setWebItemType(webItemType2);

		featuredWebItem3.setCreatedBy(user);
		featuredWebItem3.getTags().add(tag1);
		featuredWebItem3.getTags().add(tag4);
		featuredWebItem3.setWebItemType(webItemType1);

		webItem1.setCreatedBy(user);
		webItem1.getTags().add(tag2);
		webItem1.getTags().add(tag5);
		webItem1.getTags().add(tag6);
		webItem1.setWebItemType(webItemType2);

		webItem2.setCreatedBy(user);
		webItem2.getTags().add(tag1);
		webItem2.getTags().add(tag2);
		webItem2.getTags().add(tag4);
		webItem2.getTags().add(tag6);
		webItem2.setWebItemType(webItemType1);

		featuredWebItem1 = webItemService.insertWebItem(featuredWebItem1);
		featuredWebItem2 = webItemService.insertWebItem(featuredWebItem2);
		featuredWebItem3 = webItemService.insertWebItem(featuredWebItem3);
		webItem1 = webItemService.insertWebItem(webItem1);
		webItem2 = webItemService.insertWebItem(webItem2);

		String tagIdGroups = tag1.getId() + AND_DLM + tag2.getId() + AND_DLM
				+ tag5.getId() + OR_DLM + tag1.getId() + AND_DLM + tag4.getId()
				+ OR_DLM + tag3.getId() + OR_DLM;

		String webItemTypeIds = webItemType1.getId() + OR_DLM
				+ webItemType2.getId() + OR_DLM;

		Collection<WebItem> webItems = webItemService.getWebItems(tagIdGroups,webItemTypeIds, 1);
		Assert.assertEquals(webItems.size(), 4);

		Collection<DataRequestLog> dataRequestLogs = dataRequestLogService.getDataRequestLogs(5);

		Assert.assertEquals(dataRequestLogs.size(), 3);

		Iterator<DataRequestLog> it = dataRequestLogs.iterator();
		DataRequestLog drl = it.next();

		Collection<Long> tagIds = new ArrayList<Long>();

		for (Tag tag : drl.getRequestedTags()) {
			tagIds.add(tag.getId());
		}

		Assert.assertTrue(tagIds.contains(tag1.getId())
				&& tagIds.contains(tag2.getId())
				&& tagIds.contains(tag5.getId()));

		Assert.assertEquals(drl.getRequestedWebItemTypes().size(), 2);

		drl = it.next();
		tagIds = new ArrayList<Long>();

		for (Tag tag : drl.getRequestedTags()) {
			tagIds.add(tag.getId());
		}

		Assert.assertTrue(tagIds.contains(tag1.getId()) && tagIds.contains(tag4.getId()));
		Assert.assertEquals(drl.getRequestedWebItemTypes().size(), 2);

		drl = it.next();

		tagIds = new ArrayList<Long>();

		for (Tag tag : drl.getRequestedTags()) {
			tagIds.add(tag.getId());
		}

		Assert.assertTrue(tagIds.contains(tag3.getId()));
		Assert.assertEquals(drl.getRequestedWebItemTypes().size(), 2);

		Collection<Long> requestedWebItemTypeIds = new ArrayList<Long>();

		for (WebItemType webItemType : drl.getRequestedWebItemTypes()) {
			requestedWebItemTypeIds.add(webItemType.getId());
		}

		Assert.assertTrue(requestedWebItemTypeIds.contains(webItemType1.getId())
				&& requestedWebItemTypeIds.contains(webItemType2.getId()));
	}
}
