package android.wxapp.service.jerry.model.affair;

import java.util.List;

public class QueryAffairListResponse {
	String s;
	List<QueryAffairListResponseIds> aids;

	public QueryAffairListResponse() {
		super();
	}

	public QueryAffairListResponse(String s, List<QueryAffairListResponseIds> aids) {
		super();
		this.s = s;
		this.aids = aids;
	}

	public String getS() {
		return s;
	}

	public void setS(String s) {
		this.s = s;
	}

	public List<QueryAffairListResponseIds> getAids() {
		return aids;
	}

	public void setAids(List<QueryAffairListResponseIds> aids) {
		this.aids = aids;
	}
}
