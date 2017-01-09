package portal.domain.impl;

public class VoteStat extends DataEntity {
	private WebItem webItem;
	private FeedbackOption feedbackOption;
	private int voteCount;

	public WebItem getWebItem() {
		return webItem;
	}

	public void setWebItem(WebItem webItem) {
		this.webItem = webItem;
	}

	public FeedbackOption getFeedbackOption() {
		return feedbackOption;
	}

	public void setFeedbackOption(FeedbackOption feedbackOption) {
		this.feedbackOption = feedbackOption;
	}

	public int getVoteCount() {
		return voteCount;
	}

	public void setVoteCount(int voteCount) {
		this.voteCount = voteCount;
	}
}
