package android.wxapp.service.jerry.model.person;

public class GetOrgCodeRequest {
String uid,ic,oc;

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

public String getOc() {
	return oc;
}

public void setOc(String oc) {
	this.oc = oc;
}

public GetOrgCodeRequest(String uid, String ic, String oc) {
	super();
	this.uid = uid;
	this.ic = ic;
	this.oc = oc;
}

public GetOrgCodeRequest() {
	super();
}

}
