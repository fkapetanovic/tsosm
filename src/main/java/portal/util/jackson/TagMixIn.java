package portal.util.jackson;

import java.util.Collection;

import portal.domain.impl.WebItem;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"createdBy", "createDate", "deleted", "description", "isPrimary",
	"new", "parentTag", "position","updatedBy", "updateDate"})
public class TagMixIn {
	@JsonBackReference  Collection<WebItem> webItems;
}
