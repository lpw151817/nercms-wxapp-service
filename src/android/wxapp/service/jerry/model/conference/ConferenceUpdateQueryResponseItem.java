package android.wxapp.service.jerry.model.conference;

import java.util.List;

public class ConferenceUpdateQueryResponseItem {
	String cid, n, sid, ct, f, st, et, r;
	List<ConferenceUpdateQueryResponseRids> rids;

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
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

	public List<ConferenceUpdateQueryResponseRids> getRids() {
		return rids;
	}

	public void setRids(List<ConferenceUpdateQueryResponseRids> rids) {
		this.rids = rids;
	}

	public ConferenceUpdateQueryResponseItem(String cid, String n, String sid, String ct, String f,
			String st, String et, String r, List<ConferenceUpdateQueryResponseRids> rids) {
		super();
		this.cid = cid;
		this.n = n;
		this.sid = sid;
		this.ct = ct;
		this.f = f;
		this.st = st;
		this.et = et;
		this.r = r;
		this.rids = rids;
	}

	public ConferenceUpdateQueryResponseItem() {
		super();
	}

}
