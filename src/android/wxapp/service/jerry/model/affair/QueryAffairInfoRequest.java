package android.wxapp.service.jerry.model.affair;

public class QueryAffairInfoRequest {
String uid,ic,aid;

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

public String getAid() {
	return aid;
}

public void setAid(String aid) {
	this.aid = aid;
}

public QueryAffairInfoRequest(String uid, String ic, String aid) {
	super();
	this.uid = uid;
	this.ic = ic;
	this.aid = aid;
}

public QueryAffairInfoRequest() {
	super();
}

}
