package android.wxapp.service.jerry.model.person;

/**
 * ��ϵ��ʽ
 * 
 * @author JerryLiu
 *
 */
public class Contacts {
	/**
	 * ��ϵ��ʽ����
	 * <p>
	 * 1 �ֻ��� 2 ������ 3 SIM�� 4 ��̨���� 5 ����
	 */
	String t;
	String c;// ��ϵ��ʽ����

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
