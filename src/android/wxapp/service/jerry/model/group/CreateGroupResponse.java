package android.wxapp.service.jerry.model.group;

import android.wxapp.service.jerry.model.BaseResponseModel;

public class CreateGroupResponse extends BaseResponseModel {
	String gid;

	public String getGid() {
		return gid;
	}

	public void setGid(String gid) {
		this.gid = gid;
	}

	public CreateGroupResponse(String s, String gid) {
		super(s);
		this.gid = gid;
	}

	public CreateGroupResponse() {
		super();
	}

}
