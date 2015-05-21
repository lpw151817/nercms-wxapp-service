package android.wxapp.service.jerry.model;

public class SendPersonalMsg {
	String id, t, sid, rid, time, d;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getT() {
		return t;
	}

	public void setT(String t) {
		this.t = t;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getRid() {
		return rid;
	}

	public void setRid(String rid) {
		this.rid = rid;
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

	public SendPersonalMsg(String id, String t, String sid, String rid, String time, String d) {
		super();
		this.id = id;
		this.t = t;
		this.sid = sid;
		this.rid = rid;
		this.time = time;
		this.d = d;
	}

	public SendPersonalMsg() {
		super();
	}

}
