package android.wxapp.service.jerry.model;

public class CloseAffairResponse {
	String s, ec;

	public String getS() {
		return s;
	}

	public void setS(String s) {
		this.s = s;
	}

	public String getEc() {
		return ec;
	}

	public void setEc(String ec) {
		this.ec = ec;
	}

	public CloseAffairResponse() {
		super();
	}

	public CloseAffairResponse(String s, String ec) {
		super();
		this.s = s;
		this.ec = ec;
	}
}
