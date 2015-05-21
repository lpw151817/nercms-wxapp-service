package android.wxapp.service.jerry.model;

public class QueryConferenceMemberInfo {
	String id, cid;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public QueryConferenceMemberInfo(String id, String cid) {
		super();
		this.id = id;
		this.cid = cid;
	}

	public QueryConferenceMemberInfo() {
		super();
	}

}
