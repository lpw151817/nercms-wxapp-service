package android.wxapp.service.jerry.model;

public class DeleteNonbasicGroupResponse {
	String uid, gid;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getGid() {
		return gid;
	}

	public void setGid(String gid) {
		this.gid = gid;
	}

	public DeleteNonbasicGroupResponse(String uid, String gid) {
		super();
		this.uid = uid;
		this.gid = gid;
	}

	public DeleteNonbasicGroupResponse() {
		super();
	}

}
