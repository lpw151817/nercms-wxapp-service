package android.wxapp.service.jerry.model.group;

import android.wxapp.service.jerry.model.BaseRequestModel;

public class DeleteGroupRequest extends BaseRequestModel {
	String gid;

	public String getGid() {
		return gid;
	}

	public void setGid(String gid) {
		this.gid = gid;
	}

	public DeleteGroupRequest(String uid, String ic, String gid) {
		super(uid, ic);
		this.gid = gid;
	}

	public DeleteGroupRequest() {
		super();
	}

}
