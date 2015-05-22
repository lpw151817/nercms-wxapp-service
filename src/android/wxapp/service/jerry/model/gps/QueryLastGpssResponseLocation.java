package android.wxapp.service.jerry.model.gps;

public class QueryLastGpssResponseLocation {

	String cid, g, o;

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
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

	public QueryLastGpssResponseLocation(String cid, String g, String o) {
		super();
		this.cid = cid;
		this.g = g;
		this.o = o;
	}

	public QueryLastGpssResponseLocation() {
		super();
	}


}
