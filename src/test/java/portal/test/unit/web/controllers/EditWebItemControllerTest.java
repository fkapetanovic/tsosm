package portal.test.unit.web.controllers;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import portal.config.MessageKeys;
import portal.domain.impl.Tag;
import portal.domain.impl.User;
import portal.domain.impl.WebItem;
import portal.domain.impl.WebItemType;
import portal.security.context.SecurityContextFacade;
import portal.service.TagService;
import portal.service.WebItemService;
import portal.service.WebItemTypeService;
import portal.util.web.WebHelper;
import portal.web.context.RequestContextFacade;
import portal.web.controllers.EditWebItemController;

public class EditWebItemControllerTest {

	@Autowired
	@InjectMocks
	private EditWebItemController controller;
	@Mock
	private WebItemService webItemService;

	@Mock
	private WebItemTypeService webItemTypeService;

	@Mock
	private TagService tagService;

	@Mock
	private User user;

	@Mock
	private RequestContextFacade requestContext;

	@Mock
	private HttpSession session;

	@Mock
	private SecurityContextFacade securityContext;

	@Mock
	private Model model;

	@Mock
	private MessageSource messageSource;

	@Mock
	private BindingResult bindingResult;

	@Mock
	private WebHelper webHelper;

	@BeforeMethod
	public void setUpMocks() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void SaveEditedWebItemTest() {
		WebItem webItem = new WebItem();

		when(bindingResult.hasErrors()).thenReturn(false);
		when(securityContext.getLoggedInUser()).thenReturn(user);

		String view = controller.saveEditedWebItem(webItem, bindingResult, model);

		verify(webItemService).updateWebItem(webItem);
		verify(webHelper).putInfoMessageIntoSessionByKey(MessageKeys.WEBITEM_SAVED);

		Assert.assertEquals(view, "redirect:/info/");
	}

	@Test
	public void failedSaveEditedWebItemTest() throws MalformedURLException, IOException {
		WebItem webItem = new WebItem();
		String infoMessage = "WebItem Saved";

		when(bindingResult.hasErrors()).thenReturn(true);
		when(requestContext.session()).thenReturn(session);
		when(messageSource.getMessage(MessageKeys.WEBITEM_SAVED, null, null)).thenReturn(infoMessage);
		when(securityContext.getLoggedInUser()).thenReturn(user);

		String view = controller.saveEditedWebItem(webItem, bindingResult,model);

		Assert.assertEquals(view, "webItemView");
	}

	@Test
	public void GetWebItemTest() throws IOException, URISyntaxException {
		int id = 0;
		WebItem webItemOld = new WebItem();
		webItemOld.setCreatedBy(new User());

		when(securityContext.getLoggedInUser()).thenReturn(user);
		when(webItemService.getWebItemById(id)).thenReturn(webItemOld);
		when(tagService.getPrimaryTags()).thenReturn(new ArrayList<Tag>());
		when(webItemTypeService.getAllWebItemTypes()).thenReturn(new ArrayList<WebItemType>());

		String view = controller.getWebItem(id, model);

		Assert.assertEquals(view, "webItemView");
	}

	@Test
	public void GetNewFormTest() {
		int id = 0;
		WebItem webItemOld = new WebItem();
		webItemOld.setCreatedBy(new User());

		when(securityContext.getLoggedInUser()).thenReturn(user);
		when(webItemService.getWebItemById(id)).thenReturn(webItemOld);
		when(tagService.getPrimaryTags()).thenReturn(new ArrayList<Tag>());
		when(webItemTypeService.getAllWebItemTypes()).thenReturn(new ArrayList<WebItemType>());

		String view = controller.getNewForm(model);

		Assert.assertEquals(view, "webItemView");
	}

	@Test
	public void GetWebItemByUserTest() {
		int year = 2012;
		WebItem webItemOld = new WebItem();
		webItemOld.setCreatedBy(new User());

		when(securityContext.getLoggedInUser()).thenReturn(user);
		when(webItemService.getWebItemsByUserId(securityContext.getLoggedInUser().getId(), -1, year))
			.thenReturn(new ArrayList<WebItem>());

		String view = controller.getWebItemByUser(year, model);

		Assert.assertEquals(view, "webItemByUserView");
	}
}
