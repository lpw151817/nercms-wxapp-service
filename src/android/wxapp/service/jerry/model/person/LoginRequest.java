package android.wxapp.service.jerry.model.person;

public class LoginRequest {
	String un, ic, i;

	public LoginRequest(String un, String ic, String i) {
		super();
		this.un = un;
		this.ic = ic;
		this.i = i;
	}

	public LoginRequest() {
		super();
	}

	public String getUn() {
		return un;
	}

	public void setUn(String un) {
		this.un = un;
	}

	public String getIc() {
		return ic;
	}

	public void setIc(String ic) {
		this.ic = ic;
	}

	public String getI() {
		return i;
	}

	public void setI(String i) {
		this.i = i;
	}

}
