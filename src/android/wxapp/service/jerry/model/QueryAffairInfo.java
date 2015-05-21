package android.wxapp.service.jerry.model;

public class QueryAffairInfo {
	String uid, aid;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getAid() {
		return aid;
	}

	public void setAid(String aid) {
		this.aid = aid;
	}

	public QueryAffairInfo(String uid, String aid) {
		super();
		this.uid = uid;
		this.aid = aid;
	}

	public QueryAffairInfo() {
		super();
	}

}
