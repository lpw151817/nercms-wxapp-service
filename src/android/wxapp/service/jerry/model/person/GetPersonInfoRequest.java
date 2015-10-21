package android.wxapp.service.jerry.model.person;

public class GetPersonInfoRequest {
String uid;
String ic;
String pid;
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
public String getPid() {
	return pid;
}
public void setPid(String pid) {
	this.pid = pid;
}
public GetPersonInfoRequest(String uid, String ic, String pid) {
	super();
	this.uid = uid;
	this.ic = ic;
	this.pid = pid;
}
public GetPersonInfoRequest() {
	super();
}

}
