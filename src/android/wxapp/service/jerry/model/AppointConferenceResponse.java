package android.wxapp.service.jerry.model;

public class AppointConferenceResponse {
	String s, ec;

	public AppointConferenceResponse() {
		super();
	}

	public AppointConferenceResponse(String s, String ec) {
		super();
		this.s = s;
		this.ec = ec;
	}

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
}
