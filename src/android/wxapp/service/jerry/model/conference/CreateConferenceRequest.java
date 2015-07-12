package android.wxapp.service.jerry.model.conference;

import java.util.List;

import android.wxapp.service.jerry.model.BaseRequestModel;

public class CreateConferenceRequest extends BaseRequestModel {
	String n, sid, ct, f, r;
	List<ConferenceUpdateQueryResponseRids> rids;

	public CreateConferenceRequest() {
		super();
	}

	public CreateConferenceRequest(String uid, String ic, String n, String sid, String ct, String f,
			String r, List<ConferenceUpdateQueryResponseRids> rids) {
		super(uid, ic);
		this.n = n;
		this.sid = sid;
		this.ct = ct;
		this.f = f;
		this.r = r;
		this.rids = rids;
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
}
