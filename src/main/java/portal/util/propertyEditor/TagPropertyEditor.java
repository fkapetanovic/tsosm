package portal.util.propertyEditor;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import portal.domain.impl.Tag;
import portal.service.TagService;

@Component
public class TagPropertyEditor extends PropertyEditorSupport {
	@Autowired(required = true)
	private TagService tagService;

	@Override
	public void setAsText(String tagId) {
		int id = Integer.parseInt(tagId);
		setValue(tagService.getTagById(id));
	}

	@Override
	public String getAsText() {
		Tag theTag = (Tag) getValue();

		if (theTag != null) {
			return String.valueOf(theTag.getId());
		}

		return "";
	}
}
