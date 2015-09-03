package android.wxapp.service.jerry.model.affair;

import java.io.Serializable;

public class CreateTaskRequestIds implements Serializable {
	String rid;

	public String getRid() {
		return rid;
	}

	public void setRid(String rid) {
		this.rid = rid;
	}

	public CreateTaskRequestIds(String rid) {
		super();
		this.rid = rid;
	}

	public CreateTaskRequestIds() {
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
		CreateTaskRequestIds other = (CreateTaskRequestIds) obj;
		if (rid == null) {
			if (other.rid != null)
				return false;
		} else if (!rid.equals(other.rid))
			return false;
		return true;
	}

}
