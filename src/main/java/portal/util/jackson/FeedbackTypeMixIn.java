package portal.util.jackson;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"description", "id", "name", "new"})
public class FeedbackTypeMixIn {

}
