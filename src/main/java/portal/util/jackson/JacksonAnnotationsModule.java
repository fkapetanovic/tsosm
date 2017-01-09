package portal.util.jackson;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;

import portal.domain.impl.*;

public class JacksonAnnotationsModule extends SimpleModule{
	public JacksonAnnotationsModule() {
		super("Annotations", new Version(0, 0, 1,null,null,null));
	}

	@Override
	public void setupModule(SetupContext context) {
    context.setMixInAnnotations(User.class, UserMixIn.class);
    context.setMixInAnnotations(WebItem.class, WebItemMixIn.class);
    context.setMixInAnnotations(Tag.class, TagMixIn.class);
    context.setMixInAnnotations(VoteStat.class, VoteStatMixIn.class);
    context.setMixInAnnotations(WebItemImage.class, WebItemImageMixIn.class);
    context.setMixInAnnotations(FeedbackOption.class, FeedbackOptionMixIn.class);
    context.setMixInAnnotations(FeedbackType.class, FeedbackTypeMixIn.class);
    context.setMixInAnnotations(WebItemType.class, WebItemTypeMixIn.class);
    // and other set up, if any
  }
}
