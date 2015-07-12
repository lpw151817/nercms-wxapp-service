package android.wxapp.service.jerry.model.conference;

import android.wxapp.service.jerry.model.BaseRequestModel;

public class EndConferenceRequest extends BaseRequestModel {
	String cid, et;

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getEt() {
		return et;
	}

	public void setEt(String et) {
		this.et = et;
	}

	public EndConferenceRequest(String uid, String ic, String cid, String et) {
		super(uid, ic);
		this.cid = cid;
		this.et = et;
	}

	public EndConferenceRequest() {
		super();
	}

}
