package android.wxapp.service.jerry.model;

public class AppointConference {
	String id, sid, t, m, ct;

	public AppointConference() {
		super();
	}

	public AppointConference(String id, String sid, String t, String m, String ct) {
		super();
		this.id = id;
		this.sid = sid;
		this.t = t;
		this.m = m;
		this.ct = ct;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getT() {
		return t;
	}

	public void setT(String t) {
		this.t = t;
	}

	public String getM() {
		return m;
	}

	public void setM(String m) {
		this.m = m;
	}

	public String getCt() {
		return ct;
	}

	public void setCt(String ct) {
		this.ct = ct;
	}

}
