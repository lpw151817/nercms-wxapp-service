package android.wxapp.service.jerry.model.gps;

import java.util.List;

public class QueryGpssResponseLocation {
	String cid;
	List<QueryPersonalGpssReponseLocationGps> gs;

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public QueryGpssResponseLocation(String cid, List<QueryPersonalGpssReponseLocationGps> gs) {
		super();
		this.cid = cid;
		this.gs = gs;
	}

	public List<QueryPersonalGpssReponseLocationGps> getGs() {
		return gs;
	}

	public void setGs(List<QueryPersonalGpssReponseLocationGps> gs) {
		this.gs = gs;
	}

	public QueryGpssResponseLocation() {
		super();
	}

}
