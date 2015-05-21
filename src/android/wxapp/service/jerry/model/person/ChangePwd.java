package android.wxapp.service.jerry.model.person;

@Deprecated
public class ChangePwd {
	String id, np;

	public ChangePwd(String id, String np) {
		super();
		this.id = id;
		this.np = np;
	}

	public ChangePwd() {
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
