package android.wxapp.service.jerry.model.person;

public class ModifyCustomerRequest {
	private String uid;// 用户id(形如：12；下同)
	private String ic;// 用户密码(形如：123456；下同)
	private String un;// 需要用户名（形如：capesky）

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getIc() {
		return ic;
	}

	public void setIc(String ic) {
		this.ic = ic;
	}

	public String getUn() {
		return un;
	}

	public void setUn(String un) {
		this.un = un;
	}

	public ModifyCustomerRequest(String uid, String ic, String un) {
		super();
		this.uid = uid;
		this.ic = ic;
		this.un = un;
	}

	public ModifyCustomerRequest() {
		super();
	}

}
