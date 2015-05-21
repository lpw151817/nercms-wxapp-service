package android.wxapp.service.jerry.model;

public class QueryConferenceInfo {
	String id, cid;

	public QueryConferenceInfo(String id, String cid) {
		super();
		this.id = id;
		this.cid = cid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCid() {
		return cid;
	}

	public QueryConferenceInfo() {
		super();
	}

	public void setCid(String cid) {
		this.cid = cid;
	}
}
