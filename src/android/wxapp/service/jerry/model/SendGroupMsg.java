package android.wxapp.service.jerry.model;

public class SendGroupMsg {
	String id, sid, gid, t, time, d;

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

	public String getGid() {
		return gid;
	}

	public void setGid(String gid) {
		this.gid = gid;
	}

	public String getT() {
		return t;
	}

	public void setT(String t) {
		this.t = t;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getD() {
		return d;
	}

	public void setD(String d) {
		this.d = d;
	}

	public SendGroupMsg(String id, String sid, String gid, String t, String time, String d) {
		super();
		this.id = id;
		this.sid = sid;
		this.gid = gid;
		this.t = t;
		this.time = time;
		this.d = d;
	}

	public SendGroupMsg() {
		super();
	}

}
