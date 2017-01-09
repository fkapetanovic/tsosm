package portal.domain.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import portal.domain.Auditable;

public class DataRequestLog extends DataEntity implements Auditable {
	private String searchText;
	private String ipAddress;
	private Collection<Tag> requestedTags = new ArrayList<Tag>();
	private Collection<WebItemType> requestedWebItemTypes = new ArrayList<WebItemType>();

	private Date createDate;
	private User createdBy;

	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public Collection<Tag> getRequestedTags() {
		return requestedTags;
	}

	public void setRequestedTags(Collection<Tag> requestedTags) {
		this.requestedTags = requestedTags;
	}

	public Collection<WebItemType> getRequestedWebItemTypes() {
		return requestedWebItemTypes;
	}

	public void setRequestedWebItemTypes(
			Collection<WebItemType> requestedWebItemTypes) {
		this.requestedWebItemTypes = requestedWebItemTypes;
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
