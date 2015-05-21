package android.wxapp.service.jerry.model;

public class GetFeedbackResponse {
	String s, ec, t, sid, rid, aid, time, u;

	public String getS() {
		return s;
	}

	public void setS(String s) {
		this.s = s;
	}

	public String getEc() {
		return ec;
	}

	public void setEc(String ec) {
		this.ec = ec;
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

	public String getAid() {
		return aid;
	}

	public void setAid(String aid) {
		this.aid = aid;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getU() {
		return u;
	}

	public void setU(String u) {
		this.u = u;
	}

	public GetFeedbackResponse(String s, String ec, String t, String sid, String rid, String aid,
			String time, String u) {
		super();
		this.s = s;
		this.ec = ec;
		this.t = t;
		this.sid = sid;
		this.rid = rid;
		this.aid = aid;
		this.time = time;
		this.u = u;
	}

	public GetFeedbackResponse() {
		super();
	}

}
