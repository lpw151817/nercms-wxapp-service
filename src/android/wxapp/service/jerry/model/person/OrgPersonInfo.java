package android.wxapp.service.jerry.model.person;

public class OrgPersonInfo {
String uid,un,oc;

public String getOc() {
	return oc;
}

public void setOc(String oc) {
	this.oc = oc;
}

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


public OrgPersonInfo(String uid, String un, String oc) {
	super();
	this.uid = uid;
	this.un = un;
	this.oc = oc;
}

public OrgPersonInfo() {
	super();
}

}
