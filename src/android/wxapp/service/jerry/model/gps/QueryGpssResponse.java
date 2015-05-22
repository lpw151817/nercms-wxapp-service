package android.wxapp.service.jerry.model.gps;

import java.util.List;

public class QueryGpssResponse {
	String s;
	List<QueryGpssResponseLocation> location;

	public String getS() {
		return s;
	}

	public void setS(String s) {
		this.s = s;
	}

	public List<QueryGpssResponseLocation> getLocation() {
		return location;
	}

	public void setLocation(List<QueryGpssResponseLocation> location) {
		this.location = location;
	}

	public QueryGpssResponse(String s, List<QueryGpssResponseLocation> location) {
		super();
		this.s = s;
		this.location = location;
	}

	public QueryGpssResponse() {
		super();
	}

}
