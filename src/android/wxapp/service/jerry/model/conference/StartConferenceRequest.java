package android.wxapp.service.jerry.model.conference;

import android.wxapp.service.jerry.model.BaseRequestModel;

public class StartConferenceRequest extends BaseRequestModel {
	String cid, st;

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getSt() {
		return st;
	}

	public void setSt(String st) {
		this.st = st;
	}

	public StartConferenceRequest(String uid, String ic, String cid, String st) {
		super(uid, ic);
		this.cid = cid;
		this.st = st;
	}

	public StartConferenceRequest() {
		super();
	}

}
