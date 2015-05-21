package android.wxapp.service.jerry.model;

public class BeginConference {
	String id, cid, t;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getT() {
		return t;
	}

	public void setT(String t) {
		this.t = t;
	}

	public BeginConference() {
		super();
	}

	public BeginConference(String id, String cid, String t) {
		super();
		this.id = id;
		this.cid = cid;
		this.t = t;
	}
}
