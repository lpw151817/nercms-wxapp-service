package android.wxapp.service.jerry.model.gps;

public class QueryLastPersonalGpsResponse {
	String s, gid, g, o;

	public String getS() {
		return s;
	}

	public void setS(String s) {
		this.s = s;
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

	public QueryLastPersonalGpsResponse() {
		super();
	}

	public QueryLastPersonalGpsResponse(String s, String gid, String g, String o) {
		super();
		this.s = s;
		this.gid = gid;
		this.g = g;
		this.o = o;
	}

	public String getGid() {
		return gid;
	}

	public void setGid(String gid) {
		this.gid = gid;
	}

}
