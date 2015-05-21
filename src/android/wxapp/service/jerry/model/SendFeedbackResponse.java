package android.wxapp.service.jerry.model;

public class SendFeedbackResponse {
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

	public SendFeedbackResponse() {
		super();
	}

	public SendFeedbackResponse(String s, String ec) {
		super();
		this.s = s;
		this.ec = ec;
	}
}
