package portal.domain;

public interface SoftDeletable {

	public boolean isDeleted();

	public void setDeleted(boolean deleted);
}
