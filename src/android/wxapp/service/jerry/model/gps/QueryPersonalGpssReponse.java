package android.wxapp.service.jerry.model.gps;

import java.util.List;

public class QueryPersonalGpssReponse {
	String s;
	List<QueryPersonalGpssReponseLocationGps> location;

	public String getS() {
		return s;
	}

	public void setS(String s) {
		this.s = s;
	}

	public List<QueryPersonalGpssReponseLocationGps> getLocation() {
		return location;
	}

	public void setLocation(List<QueryPersonalGpssReponseLocationGps> location) {
		this.location = location;
	}

	public QueryPersonalGpssReponse(String s, List<QueryPersonalGpssReponseLocationGps> location) {
		super();
		this.s = s;
		this.location = location;
	}

	public QueryPersonalGpssReponse() {
		super();
	}

}
