package android.wxapp.service.model;

import android.content.Context;
import android.wxapp.service.dao.DAOFactory;
import android.wxapp.service.dao.PersonDao;

/**
 * �ͻ���ϵ��ʽģ��
 * 
 * @author WEIHAO
 * 
 */

public class CustomerContactModel {

	private String customerID;// �ͻ�ID
	private int type;// ��ϵ��ʽ���� ��1-ID��2-�ֻ����룻3-�������룻5-���䣻6-ͨ�ŵ�ַ
	private String content;// ��ϵ��ʽ����

	private static DAOFactory daoFactory = DAOFactory.getInstance();

	public CustomerContactModel(String customerID, int type, String content) {
		super();
		this.customerID = customerID;
		this.type = type;
		this.content = content;
	}

	/** ���� **/
	public boolean save(Context context) {
		PersonDao dao = null;
		dao = daoFactory.getPersonDao(context);
		try {
			return dao.saveCustomerContact(this);
		} finally {
		}
	}

	public String getCustomerID() {
		return customerID;
	}

	public void setCustomerID(String customerID) {
		this.customerID = customerID;
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
