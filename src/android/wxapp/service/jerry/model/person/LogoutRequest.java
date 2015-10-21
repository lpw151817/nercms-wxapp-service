package android.wxapp.service.jerry.model.person;

public class LogoutRequest {
	String uid;
	String ic;

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

	public LogoutRequest(String uid, String ic) {
		super();
		this.uid = uid;
		this.ic = ic;
	}

	public LogoutRequest() {
		super();
	}

}
