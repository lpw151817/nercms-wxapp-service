package android.wxapp.service.jerry.model.conference;

public class ConferenceUpdateQueryResponseRids {
	String rid, t;

	public ConferenceUpdateQueryResponseRids() {
		super();
	}

	public ConferenceUpdateQueryResponseRids(String rid, String t) {
		super();
		this.rid = rid;
		this.t = t;
	}

	public String getRid() {
		return rid;
	}

	public void setRid(String rid) {
		this.rid = rid;
	}

	public String getT() {
		return t;
	}

	public void setT(String t) {
		this.t = t;
	}
}
