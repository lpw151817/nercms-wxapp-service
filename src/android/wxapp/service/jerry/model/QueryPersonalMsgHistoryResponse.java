package android.wxapp.service.jerry.model;

public class QueryPersonalMsgHistoryResponse {
	String s, ec, m;

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

	public String getM() {
		return m;
	}

	public void setM(String m) {
		this.m = m;
	}

	public QueryPersonalMsgHistoryResponse(String s, String ec, String m) {
		super();
		this.s = s;
		this.ec = ec;
		this.m = m;
	}

	public QueryPersonalMsgHistoryResponse() {
		super();
	}

}
