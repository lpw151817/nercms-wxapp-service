package android.wxapp.service.jerry.model.affair;

public class QueryAffairCountRequest {
String uid,ic;

public QueryAffairCountRequest(String uid, String ic) {
	super();
	this.uid = uid;
	this.ic = ic;
}

public QueryAffairCountRequest() {
	super();
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

}
