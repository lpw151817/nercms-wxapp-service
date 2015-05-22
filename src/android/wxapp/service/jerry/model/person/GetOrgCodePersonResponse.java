package android.wxapp.service.jerry.model.person;

import java.util.List;

public class GetOrgCodePersonResponse {
String s;
List<OrgPersonInfo> persons;
public String getS() {
	return s;
}
public void setS(String s) {
	this.s = s;
}
public List<OrgPersonInfo> getPersons() {
	return persons;
}
public void setPersons(List<OrgPersonInfo> persons) {
	this.persons = persons;
}
public GetOrgCodePersonResponse(String s, List<OrgPersonInfo> persons) {
	super();
	this.s = s;
	this.persons = persons;
}
public GetOrgCodePersonResponse() {
	super();
}

}
