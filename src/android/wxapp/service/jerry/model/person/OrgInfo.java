package android.wxapp.service.jerry.model.person;

public class OrgInfo {
String oc,d;

public String getOc() {
	return oc;
}

public void setOc(String oc) {
	this.oc = oc;
}

public String getD() {
	return d;
}

public void setD(String d) {
	this.d = d;
}

public OrgInfo(String oc, String d) {
	super();
	this.oc = oc;
	this.d = d;
}

public OrgInfo() {
	super();
}

}
