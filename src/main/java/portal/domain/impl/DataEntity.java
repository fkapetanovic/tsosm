package portal.domain.impl;

public abstract class DataEntity {
	private long id;

	public long getId() {
		return id;
	}

	public void setId(long value) {
		 id=value;
	}

	public boolean isNew() {
		boolean result = (id == 0) ? true : false;
		return result;
	}

	public String getIdText() {
		return String.valueOf(this.getId());
	}

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }

    final DataEntity other = (DataEntity) obj;

    if (this.getId() != other.getId() ) {
      return false;
    }

    return true;
  }
}
