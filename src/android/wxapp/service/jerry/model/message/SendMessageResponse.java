package android.wxapp.service.jerry.model.message;

public class SendMessageResponse {
	String s, mid;

	public SendMessageResponse(String s, String mid) {
		super();
		this.s = s;
		this.mid = mid;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getS() {
		return s;
	}

	public void setS(String s) {
		this.s = s;
	}

	public SendMessageResponse() {
		super();
	}

}
