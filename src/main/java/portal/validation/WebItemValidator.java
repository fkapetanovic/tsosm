package portal.validation;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import portal.domain.impl.Tag;
import portal.domain.impl.WebItem;
import portal.domain.impl.WebItemType;
import portal.service.WebItemService;

@Component
public class WebItemValidator implements Validator {
	@Autowired
	WebItemService userService;

	@Override
	public boolean supports(Class<?> clazz) {
		return WebItem.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		WebItem webItem = (WebItem) target;

		String title = webItem.getTitle();
		WebItemType webItemType = webItem.getWebItemType();
		Collection<Tag> tags = webItem.getTags();
		String sourceURL = webItem.getSourceURL();
		String imagePath = webItem.getImage().getSourceURL();
		String text = webItem.getText();

		if (text.length() > 1000) {
			errors.rejectValue("text", "text_maximum_length", "Maximum lenght of text is 1000 characters");
		}

		if (title.trim() == "") {
			errors.rejectValue("title", "title_empty", "title field can't be empty.");
		}

		if (webItemType == null) {
			errors.rejectValue("webItemType", "webItemType_empty", "You must choose a Web ItemType");
		}

		if ((tags == null) || tags.isEmpty()) {
			errors.rejectValue("tags", "tags_empty", "Select at least one tag for webItem.");
		}

		if (sourceURL.trim() == "") {
			errors.rejectValue("sourceURL", "url_empty",
					"Source url must be filled.");
		} else if (!isValidUrl(sourceURL)) {
			errors.rejectValue("sourceURL", "url_invalid", "URL is not in a valid format.");
		}

		if (imagePath != "" && !isValidUrl(imagePath)) {
			errors.rejectValue("image.sourceURL", "url_invalid", "URL is not in a valid format.");
		}
	}

	private boolean isValidUrl(String URL) {
		try {
			URL u = new URL(URL);
			u.toURI();
		} catch (URISyntaxException e) {
			return false;
		} catch (MalformedURLException e) {
			return false;
		}
		return true;
	}
}
