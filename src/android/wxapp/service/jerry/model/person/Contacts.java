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

	public String getT() {
		return t;
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
