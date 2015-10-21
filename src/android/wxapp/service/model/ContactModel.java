package android.wxapp.service.model;

import android.content.Context;
import android.wxapp.service.dao.DAOFactory;
import android.wxapp.service.dao.PersonDao;

/**
 * ��ϵ��ʽģ��
 * 
 * @author WEIHAO
 * 
 */

public class ContactModel {

	private String contactID;// ��ϵ��ID
	private int type;// ��ϵ��ʽ���� ��1-ID��2-�ֻ����룻3-�������룻5-���䣻6-ͨ�ŵ�ַ
	private String content;// ��ϵ��ʽ����

	private static DAOFactory daoFactory = DAOFactory.getInstance();

	public ContactModel(String contactID, int type, String content) {
		super();
		this.contactID = contactID;
		this.type = type;
		this.content = content;
	}

	/** ���� **/
	public boolean save(Context context) {
		PersonDao dao = null;
		dao = daoFactory.getPersonDao(context);
		try {
			return dao.saveContact(this);
		} finally {
		}
	}

	public String getContactID() {
		return contactID;
	}

	public void setContactID(String contactID) {
		this.contactID = contactID;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
