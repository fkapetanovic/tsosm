package portal.test.unit.web.controllers;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Properties;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import portal.config.AppPropKeys;
import portal.config.SysEnums;
import portal.config.MessageKeys;
import portal.domain.impl.Tag;
import portal.domain.impl.WebItem;
import portal.domain.impl.WebItemType;
import portal.service.LogService;
import portal.service.TagService;
import portal.service.WebItemService;
import portal.service.WebItemTypeService;
import portal.util.Helper;
import portal.web.controllers.WebItemController;

public class WebItemControllerTest {
	@Autowired
	@InjectMocks
	private WebItemController controller;

	@Mock
	private LogService logService;

	@Mock
	private TagService tagService;

	@Mock
	private WebItemService webItemService;

	@Mock
	private WebItemTypeService webItemTypeService;

	@BeforeMethod
	public void setUpMocks() {
		MockitoAnnotations.initMocks(this);
	}

	private final Properties APP_PROPERTIES = Helper.getAppProperties();
	private final String OR_DLM = APP_PROPERTIES.getProperty(AppPropKeys.LOGICAL_OR_DELIMITER);

	@Test
	public void handleLoadHomePageRequest() {
		ModelMap model = new ModelMap();

		Collection<Tag> tags = new ArrayList<Tag>();
		Collection<WebItemType> wit = new ArrayList<WebItemType>();

		when(tagService.getPrimaryTags()).thenReturn(tags);
		when(webItemTypeService.getAllWebItemTypes()).thenReturn(wit);

		String viewName = controller.handleLoadHomePageRequest(model);

		verify(logService).insertWebLog(MessageKeys.USER_REQUESTED_HOME_PAGE, SysEnums.Severity.INFO);
		verify(webItemTypeService).getAllWebItemTypes();
		verify(tagService).getPrimaryTags();
		Assert.assertEquals(model.get("primaryTags"), tags);
		Assert.assertEquals(model.get("webItemTypes"), wit);
		Assert.assertEquals(viewName, "portal");
	}

	@Test
	public void handleGetWebItemsOnHomePageLoadedRequestTest() {
		Collection<WebItem> webItems = new LinkedHashSet<WebItem>();

		when(webItemService.getFeaturedWebItems("", 1)).thenReturn(webItems);

		Collection<WebItem> result = controller.handleGetWebItemsOnHomePageLoadedRequest(1);

		verify(webItemService).getFeaturedWebItems("", 1);
		Assert.assertEquals(result, webItems);
	}

	@Test
	public void handleSearchWebItemsRequestTestInputParamsOK() {
		String tagIdGroups = "1" + OR_DLM + "2"
				+ OR_DLM + "3"
				+ OR_DLM;
		String witIds = "4" + OR_DLM + "5"
				+ OR_DLM;
		Collection<WebItem> webItems = new LinkedHashSet<WebItem>();

		when(webItemService.getWebItems(tagIdGroups, witIds, 1)).thenReturn(webItems);

		Collection<WebItem> result = controller.handleSearchWebItemsRequest(tagIdGroups, witIds, 1);

		verify(webItemService).getWebItems(tagIdGroups, witIds, 1);
		Assert.assertEquals(result, webItems);
	}

	@Test
	public void handleSearchWebItemsRequestTestTagIdGroupsBad() {
		String tagIdGroups = "This in not an expected string";
		String witIds = "4" + OR_DLM + "5" + OR_DLM;
		Collection<WebItem> webItems = new LinkedHashSet<WebItem>();

		when(webItemService.getWebItems("", "", 1)).thenReturn(webItems);

		Collection<WebItem> result = controller.handleSearchWebItemsRequest(tagIdGroups, witIds, 1);

		verify(logService).insertWebLog(MessageKeys.INVALID_SEARCH_WEB_ITEM_PARAMETERS, SysEnums.Severity.WARNING);
		verify(webItemService).getWebItems("", "", 1);
		Assert.assertEquals(result, webItems);
	}

	@Test
	public void handleSearchWebItemsRequestTestWITIdsBad() {
		String tagIdGroups = "1" + OR_DLM + "2" + OR_DLM + "3" + OR_DLM;
		String witIds = "This in not an expected string";
		Collection<WebItem> webItems = new LinkedHashSet<WebItem>();

		when(webItemService.getWebItems("", "", 1)).thenReturn(webItems);

		Collection<WebItem> result = controller.handleSearchWebItemsRequest(tagIdGroups, witIds, 1);

		verify(logService).insertWebLog(MessageKeys.INVALID_SEARCH_WEB_ITEM_PARAMETERS, SysEnums.Severity.WARNING);
		verify(webItemService).getWebItems("", "", 1);
		Assert.assertEquals(result, webItems);
	}

	@Test
	public void handleSearchWebItemsRequestTestBothParamsBad() {
		String tagIdGroups = "This in not an expected string";
		String witIds = "This in not an expected string";
		Collection<WebItem> webItems = new LinkedHashSet<WebItem>();

		when(webItemService.getWebItems("", "", 1)).thenReturn(webItems);

		Collection<WebItem> result = controller.handleSearchWebItemsRequest(tagIdGroups, witIds, 1);

		verify(logService).insertWebLog(MessageKeys.INVALID_SEARCH_WEB_ITEM_PARAMETERS, SysEnums.Severity.WARNING);
		verify(webItemService).getWebItems("", "", 1);
		Assert.assertEquals(result, webItems);
	}

	@Test
	public void handleGetBelongingTagsRequest() {
		long tagId = 0;
		Collection<Tag> tags = new ArrayList<Tag>();

		when(tagService.getBelongingTags(tagId)).thenReturn(tags);

		Collection<Tag> result = controller.handleGetBelongingTagsRequest(tagId);

		verify(tagService).getBelongingTags(tagId);
		Assert.assertEquals(result, tags);
	}
}
