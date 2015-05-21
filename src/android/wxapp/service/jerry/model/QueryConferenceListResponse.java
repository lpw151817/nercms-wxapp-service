package android.wxapp.service.jerry.model;

public class QueryConferenceListResponse {
	String s, ec, d;

	public QueryConferenceListResponse() {
		super();
	}

	public QueryConferenceListResponse(String s, String ec, String d) {
		super();
		this.s = s;
		this.ec = ec;
		this.d = d;
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

	public String getD() {
		return d;
	}

	public void setD(String d) {
		this.d = d;
	}
}
