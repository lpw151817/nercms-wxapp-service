package android.wxapp.service.jerry.model;

public class CreatNonbasicGroup {
	String id, n, ct, m;

	public CreatNonbasicGroup() {
		super();
	}

	public CreatNonbasicGroup(String id, String n, String ct, String m) {
		super();
		this.id = id;
		this.n = n;
		this.ct = ct;
		this.m = m;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getN() {
		return n;
	}

	public void setN(String n) {
		this.n = n;
	}

	public String getCt() {
		return ct;
	}

	public void setCt(String ct) {
		this.ct = ct;
	}

	public String getM() {
		return m;
	}

	public void setM(String m) {
		this.m = m;
	}

}
