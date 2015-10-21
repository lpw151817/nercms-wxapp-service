package android.wxapp.service.jerry.model.group;

public class GroupUpdateQueryRequestIds {
	String rid;

	public String getRid() {
		return rid;
	}

	public void setRid(String rid) {
		this.rid = rid;
	}

	public GroupUpdateQueryRequestIds(String rid) {
		super();
		this.rid = rid;
	}

	public GroupUpdateQueryRequestIds() {
		super();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((rid == null) ? 0 : rid.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GroupUpdateQueryRequestIds other = (GroupUpdateQueryRequestIds) obj;
		if (rid == null) {
			if (other.rid != null)
				return false;
		} else if (!rid.equals(other.rid))
			return false;
		return true;
	}

}
