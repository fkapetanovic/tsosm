package portal.web.controllers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import portal.config.MessageKeys;
import portal.domain.impl.Tag;
import portal.domain.impl.User;
import portal.domain.impl.WebItem;
import portal.domain.impl.WebItemImage;
import portal.domain.impl.WebItemType;
import portal.security.context.SecurityContextFacade;
import portal.service.TagService;
import portal.service.WebItemService;
import portal.service.WebItemTypeService;
import portal.util.propertyEditor.TagPropertyEditor;
import portal.util.propertyEditor.WebItemTypePropertyEditor;
import portal.util.web.WebHelper;
import portal.util.webItemMapper.WebItemMapper;
import portal.util.webItemMapper.WebItemMapperFactory;
import portal.validation.WebItemValidator;

@Controller
@RequestMapping("/panel/webItems")
public class EditWebItemController {
	@Autowired
	private WebItemService webItemService;
	@Autowired
	private WebItemTypeService webItemTypeService;
	@Autowired
	private TagService tagService;
	@Autowired
	private TagPropertyEditor tagEditor;
	@Autowired
	private WebItemValidator webItemValidator;
	@Autowired
	private SecurityContextFacade securityContext;
	@Autowired
	private WebHelper webHelper;
	@Autowired
	private WebItemTypePropertyEditor webItemTypeEditor;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    binder.registerCustomEditor(Date.class, new CustomDateEditor(
            dateFormat, true));

		binder.registerCustomEditor(Tag.class, tagEditor);
		binder.registerCustomEditor(WebItemType.class, webItemTypeEditor);
		binder.setValidator(webItemValidator);
	}

	/**
	 * returns webItems edited by user
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/my/{year}", method = RequestMethod.GET)
	public String getWebItemByUser(@PathVariable int year, Model model) {
		List<WebItem> webItemList = new ArrayList<WebItem>();

		webItemList.addAll(webItemService.getWebItemsByUserId(securityContext
				.getLoggedInUser().getId(), -1, year));

		//order webItems by descending createdate order
		Collections.sort(webItemList, new Comparator<WebItem>() {
			  public int compare(WebItem o1, WebItem o2) {
			      return o2.getCreateDate().compareTo(o1.getCreateDate());
			  }
		});

		model.addAttribute("webItemList", webItemList);

		return "webItemByUserView";
	}

/***
 * returns last 1000 times
 * @param model
 * @return
 */
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_MODERATOR')")
	public String showAllItems(Model model) {
		List<WebItem> webItemList = new ArrayList<WebItem>();

		webItemList.addAll(webItemService.getWebItems(1000));
		//order webItems by descending createdate order
		Collections.sort(webItemList, new Comparator<WebItem>() {
			  public int compare(WebItem o1, WebItem o2) {
			      return o2.getCreateDate().compareTo(o1.getCreateDate());
			  }
		});

		model.addAttribute("webItemList", webItemList);
		return "webItemByUserView";
	}

	/**
	 *
	 * @return Model and View for inserting new WebItems
	 */
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String getNewForm(Model model) {
		Map<String, Object> myModel = initializeModelForEntryForm();
		myModel.put("webItem", new WebItem());
		myModel.put("actionTitle", "Insert");
		model.addAllAttributes(myModel);
		return "webItemView";
	}

	/**
	 *
	 * @return Model and View for inserting new WebItems
	 */

	@RequestMapping(value = "/newFromUri", method = RequestMethod.GET)
	public String getNewFormFromUri(	@RequestParam String uri, Model model) throws URISyntaxException  {
		ArrayList<String> ImageUrls = new ArrayList<String>();
		model.addAllAttributes( initializeModelForEntryForm());
		WebItem webItem;

		try {
			WebItemMapper webItemMapper=WebItemMapperFactory.getMapper(uri);
			webItem=webItemMapper.getWebItem();
			ImageUrls=webItemMapper.getImageUrls();
		} catch (IOException ex) {
			return "webItemView";
		}

		model.addAttribute("imageUrls", ImageUrls);
		model.addAttribute("webItem", webItem);
		model.addAttribute("actionTitle", "Insert");

		return "webItemView";
	}

	/**
	 * returns webItem for editing
	 *
	 * @param id
	 * @return
	 * @throws URISyntaxException
	 * @throws IOException
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	@PreAuthorize("hasRole('ROLE_MODERATOR') or hasPermission(#id,'WebItem','edit')")
	public String getWebItem(@PathVariable long id, Model model) throws IOException, URISyntaxException {
		WebItem webItem = webItemService.getWebItemById(id);
		String uri = webItem.getSourceURL();
		ArrayList<String> ImageUrls = new ArrayList<String>();

		WebItemMapper webItemMapper=WebItemMapperFactory.getMapper(uri);
		ImageUrls=webItemMapper.getImageUrls();

		model.addAllAttributes(initializeModelForEntryForm());
		model.addAttribute("webItem", webItem);
		model.addAttribute("actionTitle", "Edit");
		model.addAttribute("imageUrls", ImageUrls);

		return "webItemView";
	}

	/**
	 * Handles deleting webItems
	 * @return
	 * 	returns info view with information about success of the operation
	 */
	@RequestMapping(method=RequestMethod.GET,value={"/delete/{id}"})
	@PreAuthorize("hasRole('ROLE_MODERATOR') or hasPermission(#id,'WebItem','delete')")
	public String deleteWebItem(@PathVariable long id) {
		WebItem webItemToDelete=webItemService.getWebItemById(id);

		webItemService.deleteWebItem(webItemToDelete);
		webHelper.putInfoMessageIntoSessionByKey(MessageKeys.WEBITEM_DELETED);

		return "redirect:/info/";
	}

	/**
	 * Handles saving edited webItem
	 *
	 * @param webItem
	 *            modified webItem
	 * @param result
	 * @return If no validation errors occurred returns SuccessModelAndView else
	 *         returns WebItemView with validation errors
	 */

	@RequestMapping(method = RequestMethod.POST, value = { "/{id}", "/new" })
	@PreAuthorize("hasRole('ROLE_MODERATOR') or hasPermission(#webItem.id,'WebItem','edit') ")
	public String saveEditedWebItem(
		@ModelAttribute("webItem") @Validated WebItem webItem,
		BindingResult result, Model model) {

		User currentUser = securityContext.getLoggedInUser();
		WebItem webItemToSave;

		if (webItem.isNew()) {
			webItemToSave = webItem;
			webItemToSave.setCreatedBy(currentUser);
		} else {
			webItemToSave = webItemService.getWebItemById(webItem.getId());

			webItemToSave.setUpdatedBy(currentUser);
			webItemToSave.setSourceName(webItem.getSourceName());
			webItemToSave.setSourceURL(webItem.getSourceURL());
			webItemToSave.setTitle(webItem.getTitle());
			webItemToSave.getTags().clear();
			webItemToSave.getTags().addAll(webItem.getTags());
			webItemToSave.setWebItemType(webItem.getWebItemType());
			webItemToSave.setFeatured(webItem.isFeatured());
			webItemToSave.setText(webItem.getText());
		}

		if (webItem.getImage()!=null){
			try {
				webItem.setImage(webItemService.getWebItemImageFromUrl(webItem.getImage().getSourceURL(), null));
			} catch (MalformedURLException ex) {
				Logger log = Logger.getLogger("Excpetions");
				log.error("InvalidUrl even after validation", ex);
				result.rejectValue("image.sourceURL", "url_invalid", "Not a valid image format");
			} catch (IOException ex) {
				Logger log = Logger.getLogger("Excpetions");
				log.error("Can't get image from address:" + webItem.getImage().getSourceURL(), ex);
				result.rejectValue("image.sourceURL", "file_format", "Not a valid image format");
			}
		}

		// Comparing old and new images, and setting the new image if necessary
		if (!equalWebItemImages(webItem.getImage(), webItemToSave.getImage()) || webItem.isNew()) {
			webItemToSave.setImage(webItem.getImage());

			if (webItemToSave.getImage() != null) {
				webItemToSave.getImage().setCreatedBy(currentUser);
			}
		}

		if (result.hasErrors()) {
			model.addAllAttributes(initializeModelForEntryForm());
			model.addAttribute("webItem", webItem);

			return "webItemView";
		}

		webItem = webItemService.updateWebItem(webItemToSave);
		webHelper.putInfoMessageIntoSessionByKey(MessageKeys.WEBITEM_SAVED);

		return "redirect:/info/";
	}

	/***
	 * This method generate default data for checkboxes.pulldowns etc for the
	 * new/edit form
	 *
	 * @return initialized hashmap for model object for the webItem insert/edit
	 *         form.
	 */
	private Map<String, Object> initializeModelForEntryForm() {
		Map<String, Object> myModel = new HashMap<String, Object>();
		Collection<Tag> primaryTags = tagService.getPrimaryTags();
		myModel.put("primaryTags", primaryTags);

		Map<String, Object> featured = new HashMap<String, Object>();
		featured.put("true", "featured");
		myModel.put("featured", featured);

		List<Tag> secondaryTags = new ArrayList<Tag>();

		for (Tag aTag : primaryTags) {
			secondaryTags.addAll(tagService.getBelongingTags(aTag.getId()));
		}

		Collections.sort(secondaryTags, new Comparator<Tag>() {
		    public int compare(Tag tag1, Tag tag2) {
		        return tag1.getName().compareTo(tag2.getName());
		    }
		});

		myModel.put("secondaryTags", secondaryTags);
		Collection<WebItemType> webItemTypes = webItemTypeService.getAllWebItemTypes();
		myModel.put("allWebItemTypes", webItemTypes);

		return myModel;
	}

	private  Boolean equalWebItemImages(WebItemImage webItemImageOne, WebItemImage webItemImageTwo) {
		// I needed a xor here
		if (webItemImageOne==null && webItemImageTwo==null) {
			return true;
		} else {
			if (webItemImageOne==null || webItemImageTwo==null) {
				return false;
			}
		}

		if (webItemImageOne.getSourceURL() == webItemImageTwo.getSourceURL()) {
			return true;
		 } else {
			return false;
		}
	}
}
