package android.wxapp.service.jerry.model.person;

public class GetOrgCodePersonRequest {
	String uid, ic, t;

	public String getUid() {
		return uid;
	}

	public String getT() {
		return t;
	}

	public void setT(String t) {
		this.t = t;
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

	public GetOrgCodePersonRequest() {
		super();
	}

	public GetOrgCodePersonRequest(String uid, String ic, String t) {
		super();
		this.uid = uid;
		this.ic = ic;
		this.t = t;
	}

}
