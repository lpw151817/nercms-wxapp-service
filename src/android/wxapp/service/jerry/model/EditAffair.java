package android.wxapp.service.jerry.model;

public class EditAffair {
	String id, aid, sid, rid, d, t, et, a, ut;

	public EditAffair() {
		super();
	}

	public EditAffair(String id, String aid, String sid, String rid, String d, String t, String et,
			String a, String ut) {
		super();
		this.id = id;
		this.aid = aid;
		this.sid = sid;
		this.rid = rid;
		this.d = d;
		this.t = t;
		this.et = et;
		this.a = a;
		this.ut = ut;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAid() {
		return aid;
	}

	public void setAid(String aid) {
		this.aid = aid;
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

	public String getD() {
		return d;
	}

	public void setD(String d) {
		this.d = d;
	}

	public String getT() {
		return t;
	}

	public void setT(String t) {
		this.t = t;
	}

	public String getEt() {
		return et;
	}

	public void setEt(String et) {
		this.et = et;
	}

	public String getA() {
		return a;
	}

	public void setA(String a) {
		this.a = a;
	}

	public String getUt() {
		return ut;
	}

	public void setUt(String ut) {
		this.ut = ut;
	}

}
