package portal.test.integration.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

import portal.config.AppPropKeys;
import portal.domain.impl.FeedbackOption;
import portal.domain.impl.FeedbackType;
import portal.domain.impl.WebItemType;
import portal.service.WebItemTypeService;
import portal.test.integration.AbstractIntegrationTest;
import portal.test.integration.TestEntities;
import portal.util.Helper;

public class WebItemTypeServiceIntegrationTest extends AbstractIntegrationTest {
	@Autowired
	private WebItemTypeService webItemTypeService;

	private final Properties APP_PROPERTIES = Helper.getAppProperties();
	private final String OR_DLM = APP_PROPERTIES.getProperty(AppPropKeys.LOGICAL_OR_DELIMITER);

	@Test
	public void getAllWebItemTypesTest() {
		Collection<WebItemType> webItemTypes = webItemTypeService.getAllWebItemTypes();
		Assert.assertEquals(webItemTypes.size(), 8);
	}

	@Test
	public void getWebItemTypeByNameTest() {
		WebItemType webItemType = webItemTypeService.getWebItemTypeByName(TestEntities.WEB_ITEM_TYPE_1);
		Assert.assertEquals(webItemType.getName(), TestEntities.WEB_ITEM_TYPE_1);
	}

	@Test
	public void getWebItemTypeByIdTest() {
		WebItemType webItemType = webItemTypeService.getWebItemTypeByName(TestEntities.WEB_ITEM_TYPE_1);
		WebItemType webItemTypeById = webItemTypeService.getWebItemTypeById(webItemType.getId());
		Assert.assertEquals(webItemTypeById.getId(), webItemType.getId());
	}

	@Test
	public void feedBackTypeAndOptionsTest() {
		WebItemType webItemType = webItemTypeService.getWebItemTypeByName(TestEntities.WEB_ITEM_TYPE_1);
		FeedbackType feedbackType = webItemType.getFeedbackType();
		Collection<FeedbackOption> feedbackOptions = feedbackType.getFeedbackOptions();
		Assert.assertEquals(feedbackOptions.size(), 6);
	}

	@Test
	public void getWebItemTypesByQueryStringTest() {
		WebItemType webItemType1 = webItemTypeService.getWebItemTypeByName(TestEntities.WEB_ITEM_TYPE_1);
		WebItemType webItemType2 = webItemTypeService.getWebItemTypeByName(TestEntities.WEB_ITEM_TYPE_2);
		long webItemType1Id = webItemType1.getId();
		long webItemType2Id = webItemType2.getId();
		String queryString = webItemType1Id + OR_DLM + webItemType2Id + OR_DLM;
		Collection<WebItemType> webItemTypes = webItemTypeService.getWebItemTypes(queryString, OR_DLM);

		Assert.assertEquals(webItemTypes.size(), 2);

		Collection<Long> webItemTypeIds = new ArrayList<Long>();

		for (WebItemType webItemType : webItemTypes) {
			webItemTypeIds.add(webItemType.getId());
		}

		Assert.assertTrue(webItemTypeIds.contains(webItemType1Id) && webItemTypeIds.contains(webItemType2Id));

		queryString = webItemType1Id + OR_DLM;
		webItemTypes = webItemTypeService.getWebItemTypes(queryString, OR_DLM);

		Assert.assertEquals(webItemTypes.size(), 1);
		Assert.assertEquals(webItemTypes.iterator().next().getId(), webItemType1Id);

		webItemTypes = webItemTypeService.getWebItemTypes("", OR_DLM);

		Assert.assertEquals(webItemTypes.size(), 0);
	}
}
