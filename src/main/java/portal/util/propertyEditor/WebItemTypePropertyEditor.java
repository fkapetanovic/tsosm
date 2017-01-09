package portal.util.propertyEditor;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import portal.domain.impl.WebItemType;
import portal.service.WebItemTypeService;

@Component
public class WebItemTypePropertyEditor extends PropertyEditorSupport {
	@Autowired(required = true)
	private WebItemTypeService webItemTypeService;

	@Override
	public void setAsText(String tagId) {
		int id = Integer.parseInt(tagId);
		setValue(webItemTypeService.getWebItemTypeById(id));
	}

	@Override
	public String getAsText() {
		WebItemType theWebItemType = (WebItemType) getValue();
		if (theWebItemType != null) {
			return String.valueOf(theWebItemType.getId());
		}

		return "";
	}
}
