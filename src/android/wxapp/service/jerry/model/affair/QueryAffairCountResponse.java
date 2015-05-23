package android.wxapp.service.jerry.model.affair;

public class QueryAffairCountResponse {
	String s;
	QueryAffairCountResponseCount count;

	public QueryAffairCountResponse(String s, QueryAffairCountResponseCount count) {
		super();
		this.s = s;
		this.count = count;
	}

	public QueryAffairCountResponse() {
		super();
	}

	public String getS() {
		return s;
	}

	public void setS(String s) {
		this.s = s;
	}

	public QueryAffairCountResponseCount getCount() {
		return count;
	}

	public void setCount(QueryAffairCountResponseCount count) {
		this.count = count;
	}

}
