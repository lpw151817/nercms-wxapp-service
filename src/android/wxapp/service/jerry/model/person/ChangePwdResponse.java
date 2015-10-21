package android.wxapp.service.jerry.model.person;

@Deprecated
public class ChangePwdResponse {
	String s, ec;

	public String getS() {
		return s;
	}

	public void setS(String s) {
		this.s = s;
	}

	public String getEc() {
		return ec;
	}

	public void setEc(String ec) {
		this.ec = ec;
	}

	public ChangePwdResponse(String s, String ec) {
		super();
		this.s = s;
		this.ec = ec;
	}

	public ChangePwdResponse() {
		super();
	}

}
