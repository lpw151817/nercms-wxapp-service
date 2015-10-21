package android.wxapp.service.jerry.model.affair;

import java.util.List;

public class QueryAffairListResponse {
	String s;
	List<QueryAffairListResponseAffairs> affairs;

	public QueryAffairListResponse() {
		super();
	}

	public String getS() {
		return s;
	}

	public void setS(String s) {
		this.s = s;
	}

	public List<QueryAffairListResponseAffairs> getAffairs() {
		return affairs;
	}

	public void setAffairs(List<QueryAffairListResponseAffairs> affairs) {
		this.affairs = affairs;
	}

	public QueryAffairListResponse(String s, List<QueryAffairListResponseAffairs> affairs) {
		super();
		this.s = s;
		this.affairs = affairs;
	}


}
