package portal.domain;

import java.util.Date;

import portal.domain.impl.User;

public interface Auditable {

	public Date getCreateDate();

	public void setCreateDate(Date createDate);

	public User getCreatedBy();

	public void setCreatedBy(User user);
}
