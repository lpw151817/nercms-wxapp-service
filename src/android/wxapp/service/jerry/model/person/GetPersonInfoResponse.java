package android.wxapp.service.jerry.model.person;

import java.util.List;

public class GetPersonInfoResponse {
	String s, un, n, d, r;
	List<Contacts> contacts;

	public String getS() {
		return s;
	}

	public void setS(String s) {
		this.s = s;
	}

	public String getUn() {
		return un;
	}

	public void setUn(String un) {
		this.un = un;
	}

	public String getN() {
		return n;
	}

	public void setN(String n) {
		this.n = n;
	}

	public String getD() {
		return d;
	}

	public void setD(String d) {
		this.d = d;
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

	public GetPersonInfoResponse(String s, String un, String n, String d, String r,
			List<Contacts> contacts) {
		super();
		this.s = s;
		this.un = un;
		this.n = n;
		this.d = d;
		this.r = r;
		this.contacts = contacts;
	}

	public GetPersonInfoResponse() {
		super();
	}

}
