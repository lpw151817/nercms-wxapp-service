package android.wxapp.service.jerry.model.conference;

import android.wxapp.service.jerry.model.BaseResponseModel;

public class CreateConferenceResponse extends BaseResponseModel {
	String cid;

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public CreateConferenceResponse(String s, String cid) {
		super(s);
		this.cid = cid;
	}

	public CreateConferenceResponse() {
		super();
	}

}
