package portal.domain.impl;

import java.util.ArrayList;
import java.util.Collection;

public class FeedbackType extends DataEntity {

	private String name;
	private String description;

	private Collection<FeedbackOption> feedbackOptions = new ArrayList<FeedbackOption>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Collection<FeedbackOption> getFeedbackOptions() {
		return feedbackOptions;
	}

	public void setFeedbackOptions(Collection<FeedbackOption> feedbackOptions) {
		this.feedbackOptions = feedbackOptions;
	}
}