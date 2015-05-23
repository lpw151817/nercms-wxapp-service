package android.wxapp.service.jerry.model.affair;

public class ModifyTaskRequest {
String uid,ic,aid,et;

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

public String getEt() {
	return et;
}

public void setEt(String et) {
	this.et = et;
}

public ModifyTaskRequest(String uid, String ic, String aid, String et) {
	super();
	this.uid = uid;
	this.ic = ic;
	this.aid = aid;
	this.et = et;
}

public ModifyTaskRequest() {
	super();
}

}
