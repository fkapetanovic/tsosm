package portal.domain.impl;

import portal.domain.AuditableOnUpdate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import portal.domain.SoftDeletable;

public class WebItem extends DataEntity implements SoftDeletable,
		AuditableOnUpdate {

	private String title;
	private String text;
	private Date webItemDate;
	private String sourceURL;
	private String sourceName;
	private boolean featured;
	private WebItemType webItemType;
	private String embedCode;


	private Date createDate;
	private Date updateDate;
	private User createdBy;
	private User updatedBy;

	private WebItemImage image;
	

	public WebItemImage getImage() {
		return image;
	}

	public void setImage(WebItemImage webItemImage) {
		this.image = webItemImage;
	}

	private boolean deleted;

	private Collection<Tag> tags = new ArrayList<Tag>();

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getWebItemDate() {
		return webItemDate;
	}

	public void setWebItemDate(Date webItemDate) {
		this.webItemDate = webItemDate;
	}

	public String getSourceURL() {
		return sourceURL;
	}

	public void setSourceURL(String sourceURL) {
		this.sourceURL = sourceURL;
	}

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public Collection<Tag> getTags() {
		return tags;
	}

	public void setTags(Collection<Tag> tags) {
		this.tags = tags;
	}

	public WebItemType getWebItemType() {
		return webItemType;
	}

	public void setWebItemType(WebItemType webItemType) {
		this.webItemType = webItemType;
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
	public Date getUpdateDate() {
		return updateDate;
	}

	/**
	 * @param updateDate
	 *            the updateDate to set
	 */
	@Override
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@Override
	public User getCreatedBy() {
		return createdBy;
	}

	@Override
	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	@Override
	public User getUpdatedBy() {
		return updatedBy;
	}

	@Override
	public void setUpdatedBy(User updatedBy) {
		this.updatedBy = updatedBy;
	}

	@Override
	public boolean isDeleted() {
		return deleted;
	}

	@Override
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public boolean isFeatured() {
		return featured;
	}

	public void setFeatured(boolean featured) {
		this.featured = featured;
	}

	public String getEmbedCode() {
		return embedCode;
	}

	public void setEmbedCode(String embedCode) {
		this.embedCode = embedCode;
	}

}