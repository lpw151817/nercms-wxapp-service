package android.wxapp.service.jerry.model;

public class QueryOrgPerson {
	String id, gid;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGid() {
		return gid;
	}

	public void setGid(String gid) {
		this.gid = gid;
	}

	public QueryOrgPerson(String id, String gid) {
		super();
		this.id = id;
		this.gid = gid;
	}

	public QueryOrgPerson() {
		super();
	}

}
