package android.wxapp.service.jerry.model.conference;

import android.wxapp.service.jerry.model.BaseRequestModel;

public class ConferenceQueryRequest extends BaseRequestModel {
	String cid;

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public ConferenceQueryRequest(String uid, String ic, String cid) {
		super(uid, ic);
		this.cid = cid;
	}

	public ConferenceQueryRequest() {
		super();
	}

}
