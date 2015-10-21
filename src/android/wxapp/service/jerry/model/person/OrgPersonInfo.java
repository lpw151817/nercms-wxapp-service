package android.wxapp.service.jerry.model.person;

import java.util.List;

public class OrgPersonInfo {
	String uid, un, oc, r, n;
	List<Contacts> contacts;

	public String getN() {
		return n;
	}

	public void setN(String n) {
		this.n = n;
	}

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

	public OrgPersonInfo(String uid, String un, String oc, String r, String n, List<Contacts> contacts) {
		super();
		this.uid = uid;
		this.un = un;
		this.oc = oc;
		this.r = r;
		this.n = n;
		this.contacts = contacts;
	}

	public String getR() {
		return r;
	}

	public void setR(String r) {
		this.r = r;
	}

	public List<Contacts> getContacts() {
		return contacts;
	}

	public void setContacts(List<Contacts> contacts) {
		this.contacts = contacts;
	}

	public OrgPersonInfo() {
		super();
	}

}
