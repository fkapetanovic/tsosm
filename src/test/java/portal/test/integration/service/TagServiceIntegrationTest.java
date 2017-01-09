package portal.test.integration.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import portal.config.AppPropKeys;
import portal.domain.impl.Tag;
import portal.domain.impl.User;
import portal.service.TagService;
import portal.service.UserService;
import portal.test.integration.AbstractIntegrationTest;
import portal.test.integration.TestEntities;
import portal.util.Helper;

public class TagServiceIntegrationTest extends AbstractIntegrationTest {
	@Autowired
	private TagService tagService;

	@Autowired
	private UserService userService;

	private User user;

	@BeforeMethod
	public void setUp() {
		user = userService.loadUserByUsername(TestEntities.TEST_USER_1);
	}

	private final Properties APP_PROPERTIES = Helper.getAppProperties();
	private final String OR_DLM = APP_PROPERTIES.getProperty(AppPropKeys.LOGICAL_OR_DELIMITER);

	@Test
	public void basicCRUDTest() {
		Tag tagToInsert = TestEntities.createPrimaryTag1();
		tagToInsert.setCreatedBy(user);
		Tag insertedTag = tagService.insertTag(tagToInsert);

		Collection<Tag> tags = tagService.getAllTags();

		// tag inserted?
		Assert.assertEquals(tags.size(), 1);

		Assert.assertEquals(insertedTag.getCreatedBy(), tagToInsert.getCreatedBy());
		Assert.assertEquals(insertedTag.getDescription(), tagToInsert.getDescription());
		Assert.assertEquals(insertedTag.getIconPath(), tagToInsert.getIconPath());
		Assert.assertEquals(insertedTag.getName(), tagToInsert.getName());
		Assert.assertEquals(insertedTag.getIsPrimary(), tagToInsert.getIsPrimary());

		String newName = "New name for Primary tag 1";
		insertedTag.setName(newName);
		Tag updatedTag = tagService.updateTag(insertedTag);

		// tag updated?
		Assert.assertEquals(newName, updatedTag.getName());

		tagService.deleteTag(updatedTag);
		Tag deletedTag = tagService.getTagById(updatedTag.getId());

		// tag soft deleted?
		Assert.assertTrue(deletedTag.isDeleted());

		tags = tagService.getAllTags();

		// deleted tag ignored?
		Assert.assertEquals(tags.size(), 0);
	}

	@Test
	public void getBelongingTagsTest() {
		Tag parentTag = TestEntities.createPrimaryTag1();

		Tag childTag1 = TestEntities.createTag1();
		Tag childTag2 = TestEntities.createTag2();

		parentTag.setCreatedBy(user);
		childTag1.setCreatedBy(user);
		childTag2.setCreatedBy(user);

		parentTag = tagService.insertTag(parentTag);

		childTag1.setParentTag(parentTag);
		childTag2.setParentTag(parentTag);

		childTag1 = tagService.insertTag(childTag1);
		childTag1 = tagService.insertTag(childTag2);

		Collection<Tag> tags = tagService.getAllTags();

		// tags successfully inserted and relationship between them preserved?
		Assert.assertEquals(tags.size(), 3);
		Assert.assertEquals(childTag1.getParentTag(), parentTag);
		Assert.assertEquals(childTag2.getParentTag(), parentTag);

		Collection<Tag> childrenTags = tagService.getBelongingTags(parentTag
				.getId());

		List<Long> childrenIds = new ArrayList<Long>();

		for (Tag childrenTag : childrenTags) {
			childrenIds.add(childrenTag.getId());
		}

		// correct children tags retrieved for a parent tag?
		Assert.assertEquals(childrenTags.size(), 2);
		Assert.assertTrue(childrenIds.contains(childTag1.getId())
				&& childrenIds.contains(childTag2.getId()));

		tagService.deleteTag(childTag2);

		childrenTags = tagService.getBelongingTags(parentTag.getId());

		// after deletion of one tag result was decremented by 1?
		Assert.assertEquals(childrenTags.size(), 1);
	}

	@Test
	public void getTagsTest() {
		Tag parentTag1 = TestEntities.createPrimaryTag1();
		Tag parentTag2 = TestEntities.createPrimaryTag2();
		Tag childTag1 = TestEntities.createTag1();
		Tag childTag2 = TestEntities.createTag2();

		parentTag1.setCreatedBy(user);
		parentTag2.setCreatedBy(user);
		childTag1.setCreatedBy(user);
		childTag2.setCreatedBy(user);

		parentTag1 = tagService.insertTag(parentTag1);
		parentTag2 = tagService.insertTag(parentTag2);
		childTag1 = tagService.insertTag(childTag1);
		childTag2 = tagService.insertTag(childTag2);

		String delimiter = OR_DLM;

		// construct query string (delimited list of primary keys)
		String stringOfIds = parentTag1.getId() + delimiter
				+ parentTag2.getId() + delimiter + childTag1.getId()
				+ delimiter;

		// get tags by a query string
		Collection<Tag> tags = tagService.getTags(stringOfIds, delimiter);

		// all tags retrieved?
		Assert.assertEquals(tags.size(), 3);

		Collection<Long> tagIds = new ArrayList<Long>();

		for (Tag tag : tags) {
			tagIds.add(tag.getId());
		}

		// correct tags retrieved?
		Assert.assertTrue(tagIds.contains(parentTag1.getId())
				&& tagIds.contains(parentTag2.getId())
				&& tagIds.contains(childTag1.getId()));

		tagService.deleteTag(parentTag1);
		tags = tagService.getTags(stringOfIds, delimiter);

		// after deletion of one tag result was decremented by 1?
		Assert.assertEquals(tags.size(), 2);
	}

	@Test
	public void getPrimaryTagsTest() {
		Tag parentTag1 = TestEntities.createPrimaryTag1();
		Tag parentTag2 = TestEntities.createPrimaryTag2();
		Tag childTag1 = TestEntities.createTag1();
		Tag childTag2 = TestEntities.createTag2();

		parentTag1.setCreatedBy(user);
		parentTag2.setCreatedBy(user);
		childTag1.setCreatedBy(user);
		childTag2.setCreatedBy(user);

		parentTag1 = tagService.insertTag(parentTag1);
		parentTag2 = tagService.insertTag(parentTag2);
		childTag1 = tagService.insertTag(childTag1);
		childTag2 = tagService.insertTag(childTag2);

		Collection<Tag> tags = tagService.getPrimaryTags();
		Collection<Long> tagIds = new ArrayList<Long>();

		for (Tag tag : tags) {
			tagIds.add(tag.getId());
		}

		Assert.assertEquals(tags.size(), 2);
		Assert.assertTrue(tagIds.contains(parentTag1.getId())
				&& tagIds.contains(parentTag2.getId()));

		tagService.deleteTag(parentTag1);
		tags = tagService.getPrimaryTags();

		// after deletion of one tag result was decremented by 1?
		Assert.assertEquals(tags.size(), 1);
	}

	@Test
	public void getAllTagsTest() {
		Tag parentTag1 = TestEntities.createPrimaryTag1();
		Tag parentTag2 = TestEntities.createPrimaryTag2();
		Tag childTag1 = TestEntities.createTag1();
		Tag childTag2 = TestEntities.createTag2();

		parentTag1.setCreatedBy(user);
		parentTag2.setCreatedBy(user);
		childTag1.setCreatedBy(user);
		childTag2.setCreatedBy(user);

		parentTag1 = tagService.insertTag(parentTag1);
		parentTag2 = tagService.insertTag(parentTag2);
		childTag1 = tagService.insertTag(childTag1);
		childTag2 = tagService.insertTag(childTag2);

		Collection<Tag> tags = tagService.getAllTags();
		Collection<Long> tagIds = new ArrayList<Long>();

		for (Tag tag : tags) {
			tagIds.add(tag.getId());
		}

		Assert.assertEquals(tags.size(), 4);
		Assert.assertTrue(tagIds.contains(parentTag1.getId())
				&& tagIds.contains(parentTag2.getId())
				&& tagIds.contains(childTag1.getId())
				&& tagIds.contains(childTag2.getId()));

		tagService.deleteTag(parentTag1);
		tags = tagService.getAllTags();

		// after deletion of one tag result was decremented by 1?
		Assert.assertEquals(tags.size(), 3);
	}

	@Test
	public void getTagByIdTest() {
		Tag parentTag1 = TestEntities.createPrimaryTag1();
		Tag parentTag2 = TestEntities.createPrimaryTag2();

		parentTag1.setCreatedBy(user);
		parentTag2.setCreatedBy(user);

		parentTag1 = tagService.insertTag(parentTag1);
		parentTag2 = tagService.insertTag(parentTag2);

		Tag resultTag = tagService.getTagById(parentTag1.getId());

		Assert.assertEquals(resultTag.getId(), parentTag1.getId());
	}
}
