package portal.test.integration.service;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import portal.domain.impl.DataRequestLog;
import portal.domain.impl.Tag;
import portal.domain.impl.User;
import portal.domain.impl.WebItemType;
import portal.service.DataRequestLogService;
import portal.service.TagService;
import portal.service.UserService;
import portal.service.WebItemTypeService;
import portal.test.integration.AbstractIntegrationTest;
import portal.test.integration.TestEntities;

public class DataRequestLogServiceIntegrationTest extends AbstractIntegrationTest {
	@Autowired
	private TagService tagService;

	@Autowired
	private UserService userService;

	@Autowired
	private WebItemTypeService webItemTypeService;

	@Autowired
	private DataRequestLogService drLogService;

	private User user;
	private Tag tag1;
	private Tag tag2;
	private WebItemType webItemType1;

	@BeforeMethod
	public void setUp() {
		user = userService.loadUserByUsername(TestEntities.TEST_USER_1);

		tag1 = TestEntities.createTag1();
		tag2 = TestEntities.createTag2();

		tag1.setCreatedBy(user);
		tag2.setCreatedBy(user);

		tag1 = tagService.insertTag(tag1);
		tag2 = tagService.insertTag(tag2);

		webItemType1 = webItemTypeService.getWebItemTypeByName(TestEntities.WEB_ITEM_TYPE_1);
	}

	@Test
	public void insertDataRequestLogTest() {
		DataRequestLog drLog = new DataRequestLog();

		drLog.getRequestedTags().add(tag1);
		drLog.getRequestedTags().add(tag2);
		drLog.getRequestedWebItemTypes().add(webItemType1);
		drLog.setCreatedBy(user);
		drLog.setSearchText("Search text");

		DataRequestLog insertedDRLog = drLogService.insertDataRequestLog(drLog);

		Assert.assertEquals(insertedDRLog.getSearchText(),
				drLog.getSearchText());
		Assert.assertEquals(insertedDRLog.getCreatedBy(), drLog.getCreatedBy());
		Assert.assertEquals(insertedDRLog.getRequestedTags().size(), drLog
				.getRequestedTags().size());
		Assert.assertEquals(insertedDRLog.getRequestedWebItemTypes().size(),
				drLog.getRequestedWebItemTypes().size());
	}

	@Test
	public void getDataRequestLogTest() {
		DataRequestLog drLog = new DataRequestLog();

		drLog.getRequestedTags().add(tag1);
		drLog.getRequestedTags().add(tag2);
		drLog.getRequestedWebItemTypes().add(webItemType1);
		drLog.setCreatedBy(user);
		drLog.setSearchText("Search text");

		drLogService.insertDataRequestLog(drLog);

		Collection<DataRequestLog> drLogs = drLogService.getDataRequestLogs(5);

		Assert.assertEquals(drLogs.size(), 1);

		Collection<Tag> requestedTags = drLogs.iterator().next()
				.getRequestedTags();
		Collection<WebItemType> requestedWebItemTypes = drLogs.iterator()
				.next().getRequestedWebItemTypes();

		Assert.assertEquals(requestedTags.size(), 2);
		Assert.assertEquals(requestedWebItemTypes.size(), 1);

		Collection<Long> tagIds = new ArrayList<Long>();

		for (Tag tag : drLogs.iterator().next().getRequestedTags()) {
			tagIds.add(tag.getId());
		}

		Assert.assertTrue(tagIds.contains(tag1.getId())
				&& tagIds.contains(tag2.getId()));
	}
}
