package android.wxapp.service.jerry.model.affair;

public class EndTaskRequest {
String uid,ic,aid,ct;

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

public String getCt() {
	return ct;
}

public void setCt(String ct) {
	this.ct = ct;
}

public EndTaskRequest(String uid, String ic, String aid, String ct) {
	super();
	this.uid = uid;
	this.ic = ic;
	this.aid = aid;
	this.ct = ct;
}

public EndTaskRequest() {
	super();
}

}
