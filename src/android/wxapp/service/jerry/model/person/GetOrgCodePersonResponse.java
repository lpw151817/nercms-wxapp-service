package android.wxapp.service.jerry.model.person;

import java.util.List;

public class GetOrgCodePersonResponse {
	String s, ut;
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

	public GetOrgCodePersonResponse(String s, String ut, List<OrgPersonInfo> persons) {
		super();
		this.s = s;
		this.ut = ut;
		this.persons = persons;
	}

	public GetOrgCodePersonResponse() {
		super();
	}

	public String getUt() {
		return ut;
	}

	public void setUt(String ut) {
		this.ut = ut;
	}

}
