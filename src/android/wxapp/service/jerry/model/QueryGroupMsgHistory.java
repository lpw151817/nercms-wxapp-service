package android.wxapp.service.jerry.model;

public class QueryGroupMsgHistory {
	String id, t, sid, gid, s;

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

	public String getGid() {
		return gid;
	}

	public void setGid(String gid) {
		this.gid = gid;
	}

	public String getS() {
		return s;
	}

	public void setS(String s) {
		this.s = s;
	}

	public QueryGroupMsgHistory(String id, String t, String sid, String gid, String s) {
		super();
		this.id = id;
		this.t = t;
		this.sid = sid;
		this.gid = gid;
		this.s = s;
	}

	public QueryGroupMsgHistory() {
		super();
	}

}
