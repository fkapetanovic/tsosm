package portal.domain;

import java.util.Date;
import portal.domain.impl.User;

public interface AuditableOnUpdate extends Auditable {

	public Date getUpdateDate();

	public void setUpdateDate(Date createDate);

	public User getUpdatedBy();

	public void setUpdatedBy(User user);
}
