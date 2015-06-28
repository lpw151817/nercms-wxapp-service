package android.wxapp.service.jerry.model.affair;

public class UpdateAffairReadTimeRequest {
String uid,ic,aid,t;

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

public String getT() {
	return t;
}

public void setT(String t) {
	this.t = t;
}

public UpdateAffairReadTimeRequest(String uid, String ic, String aid, String t) {
	super();
	this.uid = uid;
	this.ic = ic;
	this.aid = aid;
	this.t = t;
}

public UpdateAffairReadTimeRequest() {
	super();
}

}
