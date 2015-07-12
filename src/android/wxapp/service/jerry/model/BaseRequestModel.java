package android.wxapp.service.jerry.model;

public class BaseRequestModel {
	String uid, ic;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getIc() {
		return ic;
	}

	public void setIc(String ic) {
		this.ic = ic;
	}

	public BaseRequestModel(String uid, String ic) {
		super();
		this.uid = uid;
		this.ic = ic;
	}

	public BaseRequestModel() {
		super();
	}

}
