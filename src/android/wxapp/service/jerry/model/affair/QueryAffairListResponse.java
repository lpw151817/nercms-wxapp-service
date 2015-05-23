package android.wxapp.service.jerry.model.affair;

import java.util.List;

public class QueryAffairListResponse {
	String s;
	List<Aids> aids;

	public QueryAffairListResponse() {
		super();
	}

	public QueryAffairListResponse(String s, List<Aids> aids) {
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

	public List<Aids> getAids() {
		return aids;
	}

	public void setAids(List<Aids> aids) {
		this.aids = aids;
	}

	class Aids {
		String aid;

		public Aids(String aid) {
			super();
			this.aid = aid;
		}

		public Aids() {
			super();
		}

		public String getAid() {
			return aid;
		}

		public void setAid(String aid) {
			this.aid = aid;
		}

	}
}
