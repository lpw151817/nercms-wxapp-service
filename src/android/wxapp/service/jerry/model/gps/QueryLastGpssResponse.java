package android.wxapp.service.jerry.model.gps;

import java.util.List;

public class QueryLastGpssResponse {

	String s;
	List<QueryLastGpssResponseLocation> location;

	public QueryLastGpssResponse(String s, List<QueryLastGpssResponseLocation> location) {
		super();
		this.s = s;
		this.location = location;
	}

	public QueryLastGpssResponse() {
		super();
	}

	public String getS() {
		return s;
	}

	public void setS(String s) {
		this.s = s;
	}

	public List<QueryLastGpssResponseLocation> getLocation() {
		return location;
	}

	public void setLocation(List<QueryLastGpssResponseLocation> location) {
		this.location = location;
	}


}
