package android.wxapp.service.jerry.model.gps;

import java.util.List;

public class GpsUpdateQueryResponseGpss {
	String uid;
	List<QueryPersonalGpssReponseLocationGps> gs;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public List<QueryPersonalGpssReponseLocationGps> getGs() {
		return gs;
	}

	public void setGs(List<QueryPersonalGpssReponseLocationGps> gs) {
		this.gs = gs;
	}

	public GpsUpdateQueryResponseGpss(String uid, List<QueryPersonalGpssReponseLocationGps> gs) {
		super();
		this.uid = uid;
		this.gs = gs;
	}

	public GpsUpdateQueryResponseGpss() {
		super();
	}

}
