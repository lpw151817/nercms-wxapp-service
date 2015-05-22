package android.wxapp.service.jerry.model.gps;

import java.util.List;

public class QueryGpssRequest {
String uid,ic;
List<QueryGpssRequestIds> p;
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
public List<QueryGpssRequestIds> getP() {
	return p;
}
public void setP(List<QueryGpssRequestIds> p) {
	this.p = p;
}
public QueryGpssRequest(String uid, String ic, List<QueryGpssRequestIds> p) {
	super();
	this.uid = uid;
	this.ic = ic;
	this.p = p;
}
public QueryGpssRequest() {
	super();
}

}
