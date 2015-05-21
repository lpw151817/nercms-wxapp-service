package android.wxapp.service.jerry.model;

public class QueryPersonalMsgHistory {
	String id, t, sid, rid, s;

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

	public String getS() {
		return s;
	}

	public void setS(String s) {
		this.s = s;
	}

	public QueryPersonalMsgHistory(String id, String t, String sid, String rid, String s) {
		super();
		this.id = id;
		this.t = t;
		this.sid = sid;
		this.rid = rid;
		this.s = s;
	}

	public QueryPersonalMsgHistory() {
		super();
	}

}
