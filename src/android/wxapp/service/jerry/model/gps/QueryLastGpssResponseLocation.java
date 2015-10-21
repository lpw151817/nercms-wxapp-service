package android.wxapp.service.jerry.model.gps;

import java.util.List;

public class QueryLastGpssResponseLocation {

	String cid, gid, t, ut, g, o;

	public QueryLastGpssResponseLocation(String cid, String gid, String t, String ut, String g, String o) {
		super();
		this.cid = cid;
		this.gid = gid;
		this.t = t;
		this.ut = ut;
		this.g = g;
		this.o = o;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
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

	public String getUt() {
		return ut;
	}

	public void setUt(String ut) {
		this.ut = ut;
	}

	public String getG() {
		return g;
	}

	public void setG(String g) {
		this.g = g;
	}

	public String getO() {
		return o;
	}

	public void setO(String o) {
		this.o = o;
	}

	public QueryLastGpssResponseLocation() {
		super();
	}

}
