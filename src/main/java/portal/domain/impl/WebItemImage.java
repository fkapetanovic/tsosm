package portal.domain.impl;

import java.awt.image.BufferedImage;
import java.util.Date;

import portal.domain.Auditable;

public class WebItemImage extends DataEntity implements Auditable {
	private Date createDate;
	private User createdBy;
	private BufferedImage image;
	private String mimeType;
	private String sourceURL;
	private WebItem webItem;

	public String getSourceURL() {
		return sourceURL;
	}

	public void setSourceURL(String sourceURL) {
		this.sourceURL = sourceURL;
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

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public WebItem getWebItem() {
		return webItem;
	}

	public void setWebItem(WebItem webItem) {
		this.webItem = webItem;
	}
}
