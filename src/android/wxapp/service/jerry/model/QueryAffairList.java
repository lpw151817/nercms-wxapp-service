package android.wxapp.service.jerry.model;

public class QueryAffairList {
	String uid, sor, t;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getSor() {
		return sor;
	}

	public void setSor(String sor) {
		this.sor = sor;
	}

	public String getT() {
		return t;
	}

	public void setT(String t) {
		this.t = t;
	}

	public QueryAffairList(String uid, String sor, String t) {
		super();
		this.uid = uid;
		this.sor = sor;
		this.t = t;
	}

	public QueryAffairList() {
		super();
	}

}
