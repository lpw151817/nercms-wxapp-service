package android.wxapp.service.jerry.model.person;

public class LoginResponse {
	String s;
	String uid;

	public String getS() {
		return s;
	}

	public void setS(String s) {
		this.s = s;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public LoginResponse(String s, String uid) {
		super();
		this.s = s;
		this.uid = uid;
	}

	public LoginResponse() {
		super();
	}

}
