package android.wxapp.service.jerry.model.normal;

/**
 * ����������ʧ��(s)���������(ec)
 * 
 * @author JerryLiu
 *
 */
public class NormalServerResponse {
	private String s;
	private String ec;

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

	public NormalServerResponse(String s, String ec) {
		super();
		this.s = s;
		this.ec = ec;
	}

	public NormalServerResponse() {
		super();
	}

}
