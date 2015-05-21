package android.wxapp.service.jerry.model;

public class QueryAffairListResponse {
	String s, ec, a;

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

	public String getA() {
		return a;
	}

	public void setA(String a) {
		this.a = a;
	}

	public QueryAffairListResponse(String s, String ec, String a) {
		super();
		this.s = s;
		this.ec = ec;
		this.a = a;
	}

	public QueryAffairListResponse() {
		super();
	}

}
