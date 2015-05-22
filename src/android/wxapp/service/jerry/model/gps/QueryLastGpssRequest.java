package android.wxapp.service.jerry.model.gps;

import java.util.List;

public class QueryLastGpssRequest {

	String uid, ic;
	List<QueryLastGpssRequestIds> p;

	public QueryLastGpssRequest() {
		super();
	}

	public QueryLastGpssRequest(String uid, String ic, List<QueryLastGpssRequestIds> p) {
		super();
		this.uid = uid;
		this.ic = ic;
		this.p = p;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getIc() {
		return ic;
	}

	public void setIc(String ic) {
		this.ic = ic;
	}

	public List<QueryLastGpssRequestIds> getP() {
		return p;
	}

	public void setP(List<QueryLastGpssRequestIds> p) {
		this.p = p;
	}

}
