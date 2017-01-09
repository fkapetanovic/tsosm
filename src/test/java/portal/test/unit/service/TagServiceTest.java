package portal.test.unit.service;

import static org.mockito.Mockito.verify;

import java.util.Properties;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import portal.config.AppPropKeys;
import portal.domain.impl.Tag;
import portal.repository.TagDAO;
import portal.service.impl.TagServiceImpl;
import portal.util.Helper;

public class TagServiceTest {
	@Mock
	private TagDAO tagDao;

	@Autowired
	@InjectMocks
	private TagServiceImpl tagService;

	@BeforeClass
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	private final Properties APP_PROPERTIES = Helper.getAppProperties();
	private final String OR_DLM = APP_PROPERTIES.getProperty(AppPropKeys.LOGICAL_OR_DELIMITER);

	@Test
	public void getTagByIdTest() {
		tagService.getTagById(1L);
		verify(tagDao).getEntityById(Tag.class, 1L);
	}

	@Test
	public void getAllTagsTest() {
		tagService.getAllTags();
		verify(tagDao).getAllTags();
	}

	@Test
	public void getPrimaryTagsTest() {
		tagService.getPrimaryTags();
		verify(tagDao).getPrimaryTags();
	}

	@Test
	public void getBelongingTagsTest() {
		tagService.getBelongingTags(1L);
		verify(tagDao).getBelongingTags(1L);
	}

	@Test
	public void getTagsTest() {
		String tagIds = "64" + OR_DLM + "14" + OR_DLM + "50" + OR_DLM;
		tagService.getTags(tagIds, OR_DLM);

		verify(tagDao).getEntityById(Tag.class, 64L);
		verify(tagDao).getEntityById(Tag.class, 14L);
		verify(tagDao).getEntityById(Tag.class, 50L);
	}

	@Test
	public void insertTagTest() {
		Tag tag = new Tag();
		tagService.insertTag(tag);
		verify(tagDao).insertEntity(tag);
	}

	@Test
	public void updateTagTest() {
		Tag tag = new Tag();
		tagService.updateTag(tag);
		verify(tagDao).updateEntity(tag);
	}

	@Test
	public void deleteTagTest() {
		Tag tag = new Tag();
		tagService.deleteTag(tag);
		verify(tagDao).deleteEntity(Tag.class, tag);
	}
}
