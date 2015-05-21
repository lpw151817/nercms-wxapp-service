package android.wxapp.service.jerry.model;

public class AddAffair {
	String id, sid, rid, d, t, bt, et, lot, a, ut;

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

	public String getBt() {
		return bt;
	}

	public void setBt(String bt) {
		this.bt = bt;
	}

	public String getEt() {
		return et;
	}

	public void setEt(String et) {
		this.et = et;
	}

	public String getLot() {
		return lot;
	}

	public void setLot(String lot) {
		this.lot = lot;
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

	public AddAffair(String id, String sid, String rid, String d, String t, String bt, String et,
			String lot, String a, String ut) {
		super();
		this.id = id;
		this.sid = sid;
		this.rid = rid;
		this.d = d;
		this.t = t;
		this.bt = bt;
		this.et = et;
		this.lot = lot;
		this.a = a;
		this.ut = ut;
	}

	public AddAffair() {
		super();
	}

}
