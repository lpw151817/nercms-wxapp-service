package android.wxapp.service.jerry.model.gps;

import java.util.List;

public class QueryGpssResponseLocation {
String cid;
List<QueryPersonalGpssReponseLocation> i;
public String getCid() {
	return cid;
}
public void setCid(String cid) {
	this.cid = cid;
}
public List<QueryPersonalGpssReponseLocation> getI() {
	return i;
}
public void setI(List<QueryPersonalGpssReponseLocation> i) {
	this.i = i;
}
public QueryGpssResponseLocation(String cid, List<QueryPersonalGpssReponseLocation> i) {
	super();
	this.cid = cid;
	this.i = i;
}
public QueryGpssResponseLocation() {
	super();
}

}
