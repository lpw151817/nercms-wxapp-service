package android.wxapp.service.jerry.model;

public class GetFeedback {
	String uid, id;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public GetFeedback(String uid, String id) {
		super();
		this.uid = uid;
		this.id = id;
	}

	public GetFeedback() {
		super();
	}

}
