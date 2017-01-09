package portal.domain.impl;


import java.util.Date;
import portal.domain.Auditable;

public class Vote extends DataEntity implements Auditable {
	private WebItem webItem;
	private FeedbackOption feedbackOption;
	private String ipAddress;

	private Date createDate;
	private User createdBy;

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

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	@Override
	public Date getCreateDate() {
		return createDate;
	}

	@Override
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Override
	public User getCreatedBy() {
		return createdBy;
	}

	@Override
	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

}
