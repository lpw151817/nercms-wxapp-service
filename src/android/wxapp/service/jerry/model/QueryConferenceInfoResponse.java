package android.wxapp.service.jerry.model;

public class QueryConferenceInfoResponse {
	String s, ec, n, sid, ct, f, st, et, r;

	public QueryConferenceInfoResponse() {
		super();
	}

	public QueryConferenceInfoResponse(String s, String ec, String n, String sid, String ct, String f,
			String st, String et, String r) {
		super();
		this.s = s;
		this.ec = ec;
		this.n = n;
		this.sid = sid;
		this.ct = ct;
		this.f = f;
		this.st = st;
		this.et = et;
		this.r = r;
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

	public String getN() {
		return n;
	}

	public void setN(String n) {
		this.n = n;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getCt() {
		return ct;
	}

	public void setCt(String ct) {
		this.ct = ct;
	}

	public String getF() {
		return f;
	}

	public void setF(String f) {
		this.f = f;
	}

	public String getSt() {
		return st;
	}

	public void setSt(String st) {
		this.st = st;
	}

	public String getEt() {
		return et;
	}

	public void setEt(String et) {
		this.et = et;
	}

	public String getR() {
		return r;
	}

	public void setR(String r) {
		this.r = r;
	}
}
