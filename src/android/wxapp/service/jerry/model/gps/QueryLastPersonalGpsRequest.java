package android.wxapp.service.jerry.model.gps;

public class QueryLastPersonalGpsRequest {
String uid,ic,cid;

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

public String getCid() {
	return cid;
}

public void setCid(String cid) {
	this.cid = cid;
}

public QueryLastPersonalGpsRequest(String uid, String ic, String cid) {
	super();
	this.uid = uid;
	this.ic = ic;
	this.cid = cid;
}

public QueryLastPersonalGpsRequest() {
	super();
}

}
