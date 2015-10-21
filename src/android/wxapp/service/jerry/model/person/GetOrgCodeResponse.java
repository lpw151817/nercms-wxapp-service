package android.wxapp.service.jerry.model.person;

import java.util.List;

public class GetOrgCodeResponse {
String s,ut;
List<OrgInfo> org_codes;
public GetOrgCodeResponse(String s, String ut, List<OrgInfo> org_codes) {
	super();
	this.s = s;
	this.ut = ut;
	this.org_codes = org_codes;
}
public String getUt() {
	return ut;
}
public void setUt(String ut) {
	this.ut = ut;
}
public String getS() {
	return s;
}
public void setS(String s) {
	this.s = s;
}
public List<OrgInfo> getOrg_codes() {
	return org_codes;
}
public void setOrg_codes(List<OrgInfo> org_codes) {
	this.org_codes = org_codes;
}
public GetOrgCodeResponse() {
	super();
}

}
