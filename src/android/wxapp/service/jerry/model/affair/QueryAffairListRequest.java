package android.wxapp.service.jerry.model.affair;

public class QueryAffairListRequest {
	String uid, ic, sor, t, count;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getIc() {
		return ic;
	}

	public void setIc(String ic) {
		this.ic = ic;
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

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public QueryAffairListRequest(String uid, String ic, String sor, String t, String count) {
		super();
		this.uid = uid;
		this.ic = ic;
		this.sor = sor;
		this.t = t;
		this.count = count;
	}

	public QueryAffairListRequest() {
		super();
	}

}
