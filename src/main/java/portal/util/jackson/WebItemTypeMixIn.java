package portal.util.jackson;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"description", "new", "position", "updatedBy"})
public class WebItemTypeMixIn {

}
