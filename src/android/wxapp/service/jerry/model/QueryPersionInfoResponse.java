package android.wxapp.service.jerry.model;

public class QueryPersionInfoResponse {
	String s, ec, i, n, r, imsi;

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

	public String getI() {
		return i;
	}

	public void setI(String i) {
		this.i = i;
	}

	public String getN() {
		return n;
	}

	public void setN(String n) {
		this.n = n;
	}

	public String getR() {
		return r;
	}

	public void setR(String r) {
		this.r = r;
	}

	public String getImsi() {
		return imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}

	public QueryPersionInfoResponse(String s, String ec, String i, String n, String r, String imsi) {
		super();
		this.s = s;
		this.ec = ec;
		this.i = i;
		this.n = n;
		this.r = r;
		this.imsi = imsi;
	}

	public QueryPersionInfoResponse() {
		super();
	}

}
