package android.wxapp.service.jerry.model.person;

public class ModifyCustomerRequest {
	private String uid;// �û�id(���磺12����ͬ)
	private String ic;// �û�����(���磺123456����ͬ)
	private String un;// ��Ҫ�û��������磺capesky��

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
