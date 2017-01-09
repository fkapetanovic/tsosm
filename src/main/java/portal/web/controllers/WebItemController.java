package portal.web.controllers;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import portal.config.MessageKeys;
import portal.config.SysEnums;
import portal.config.SysEnums.Severity;
import portal.domain.impl.Log;
import portal.domain.impl.Tag;
import portal.domain.impl.WebItem;
import portal.domain.impl.WebItemImage;
import portal.security.context.SecurityContextFacade;
import portal.service.LogService;
import portal.service.TagService;
import portal.service.WebItemService;
import portal.service.WebItemTypeService;
import portal.service.exceptions.ResourceNotFoundException;
import portal.validation.InputCheck;
import portal.web.context.RequestContextFacade;

@Controller
public class WebItemController {
	@Autowired
	private WebItemService webItemService;

	@Autowired
	private TagService tagService;

	@Autowired
	private WebItemTypeService webItemTypeService;

	@Autowired
	private SecurityContextFacade securityContext;

	@Autowired
	private LogService logService;

	@Autowired
	private RequestContextFacade requestContext;

	@Autowired
	private MessageSource messageSource;

	@RequestMapping(method = RequestMethod.GET, value = "/")
	public String handleLoadHomePageRequest(ModelMap model) {
		logService.insertWebLog(MessageKeys.USER_REQUESTED_HOME_PAGE, SysEnums.Severity.INFO);

		model.put("webItemTypes", webItemTypeService.getAllWebItemTypes());
		model.put("primaryTags", tagService.getPrimaryTags());
		return "portal";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/home/getWebItems")
	public @ResponseBody
	Collection<WebItem> handleGetWebItemsOnHomePageLoadedRequest(
			@RequestParam int page) {

		return webItemService.getFeaturedWebItems("", page);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/home/searchWebItems")
	public @ResponseBody
	Collection<WebItem> handleSearchWebItemsRequest(
			@RequestParam String tagIdGroups,
			@RequestParam String webItemTypeIds, @RequestParam int page) {

		if (!InputCheck.isValidStringOfTagIdGroups(tagIdGroups)
				|| !InputCheck.isValidStringOfWebItemTypeIds(webItemTypeIds)) {
			logService.insertWebLog(
					MessageKeys.INVALID_SEARCH_WEB_ITEM_PARAMETERS,
					SysEnums.Severity.WARNING);
			tagIdGroups = "";
			webItemTypeIds = "";
		}

		return webItemService.getWebItems(tagIdGroups, webItemTypeIds, page);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/home/getBelongingTags")
	public @ResponseBody
	Collection<Tag> handleGetBelongingTagsRequest(@RequestParam long tagId) {
		return tagService.getBelongingTags(tagId);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/home/webItemOpened")
	public @ResponseBody String handleWebItemOpenedPost(@RequestParam long webItemId) {
		Log log = new Log();

		log.setCreatedBy(securityContext.getLoggedInUser());
		log.setIpAddress(requestContext.ipAddress());
		log.setAdditionalInfo(String.valueOf(webItemId));
		log.setSeverity(Severity.INFO.getValue());
		log.setText(messageSource.getMessage(MessageKeys.WEB_ITEM_OPENED, null,
				null));

		logService.insertLog(log);

		return "success";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/webitem/image/{id}")
	public ResponseEntity<byte[]> getWebItemImage(@PathVariable int id) {
		WebItemImage webItemImage = webItemService.getWebItemWithImageById(id).getImage();
		BufferedImage theImage = webItemImage.getImage();

		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType(webItemImage
				.getMimeType()));

		if (theImage == null) {
			throw new ResourceNotFoundException("image " + String.valueOf(id) + " not found");
		}

		try {
			return new ResponseEntity<byte[]>(
					portal.util.ImageHelper.BufferedImageToByte(theImage),
					headers, HttpStatus.CREATED);
		} catch (IOException ex) {
			throw new ResourceNotFoundException("image " + String.valueOf(id) + " not found");
		}
	}
}
