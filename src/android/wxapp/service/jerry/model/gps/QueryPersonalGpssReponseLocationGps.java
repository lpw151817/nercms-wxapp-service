package android.wxapp.service.jerry.model.gps;

public class QueryPersonalGpssReponseLocationGps {
	String gid, g, t, ut;

	public QueryPersonalGpssReponseLocationGps(String gid, String g, String t, String ut) {
		super();
		this.gid = gid;
		this.g = g;
		this.t = t;
		this.ut = ut;
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

	public String getGid() {
		return gid;
	}

	public void setGid(String gid) {
		this.gid = gid;
	}

	public String getG() {
		return g;
	}

	public void setG(String g) {
		this.g = g;
	}

	public QueryPersonalGpssReponseLocationGps() {
		super();
	}

}
