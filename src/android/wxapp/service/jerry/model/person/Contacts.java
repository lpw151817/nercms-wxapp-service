package android.wxapp.service.jerry.model.person;

/**
 * 联系方式
 * 
 * @author JerryLiu
 *
 */
public class Contacts {
	/**
	 * 联系方式种类
	 * <p>
	 * 1 手机号 2 座机号 3 SIM号 4 手台号码 5 邮箱
	 */
	String t;
	String c;// 联系方式内容

	public enum CONTACT_ITEM {
		MOBILE, PHONE, SIMI, HAND, EMAIL
	}

	public CONTACT_ITEM getT() throws Exception {
		switch (Integer.parseInt(t)) {
		case 1:
			return CONTACT_ITEM.MOBILE;
		case 2:
			return CONTACT_ITEM.PHONE;
		case 3:
			return CONTACT_ITEM.SIMI;
		case 4:
			return CONTACT_ITEM.HAND;
		case 5:
			return CONTACT_ITEM.EMAIL;
		default:
			return null;
		}
	}

	public void setT(String t) {
		this.t = t;
	}

	public String getC() {
		return c;
	}

	public void setC(String c) {
		this.c = c;
	}

	public Contacts(String t, String c) {
		super();
		this.t = t;
		this.c = c;
	}

	public Contacts() {
		super();
	}

}
