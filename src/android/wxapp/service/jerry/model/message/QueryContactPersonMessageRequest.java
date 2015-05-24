package android.wxapp.service.jerry.model.message;

public class QueryContactPersonMessageRequest {
String uid,ic;

public QueryContactPersonMessageRequest() {
	super();
}

public QueryContactPersonMessageRequest(String uid, String ic) {
	super();
	this.uid = uid;
	this.ic = ic;
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
