package android.wxapp.service.jerry.model.gps;

import java.util.List;

public class QueryPersonalGpssReponse {
String s,cid;
List<QueryPersonalGpssReponseLocation> location;
public String getS() {
	return s;
}
public void setS(String s) {
	this.s = s;
}
public String getCid() {
	return cid;
}
public void setCid(String cid) {
	this.cid = cid;
}
public List<QueryPersonalGpssReponseLocation> getLocation() {
	return location;
}
public void setLocation(List<QueryPersonalGpssReponseLocation> location) {
	this.location = location;
}
public QueryPersonalGpssReponse(String s, String cid, List<QueryPersonalGpssReponseLocation> location) {
	super();
	this.s = s;
	this.cid = cid;
	this.location = location;
}
public QueryPersonalGpssReponse() {
	super();
}

}
