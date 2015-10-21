package android.wxapp.service.jerry.model.person;

@Deprecated
public class ChangePwdRequest {
	String id, np;

	public ChangePwdRequest(String id, String np) {
		super();
		this.id = id;
		this.np = np;
	}

	public ChangePwdRequest() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNp() {
		return np;
	}

	public void setNp(String np) {
		this.np = np;
	}

}
