package android.wxapp.service.jerry.model.person;

public class OrgPersonInfo {
String uid,un;

public String getUid() {
	return uid;
}

public void setUid(String uid) {
	this.uid = uid;
}

public String getUn() {
	return un;
}

public void setUn(String un) {
	this.un = un;
}

public OrgPersonInfo(String uid, String un) {
	super();
	this.uid = uid;
	this.un = un;
}

public OrgPersonInfo() {
	super();
}

}
