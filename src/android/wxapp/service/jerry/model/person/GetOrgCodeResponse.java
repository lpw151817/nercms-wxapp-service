package android.wxapp.service.jerry.model.person;

import java.util.List;

public class GetOrgCodeResponse {
String s;
List<OrgInfo> org_codes;
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
public GetOrgCodeResponse(String s, List<OrgInfo> org_codes) {
	super();
	this.s = s;
	this.org_codes = org_codes;
}
public GetOrgCodeResponse() {
	super();
}

}
