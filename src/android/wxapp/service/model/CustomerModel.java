package android.wxapp.service.model;

import android.content.Context;
import android.wxapp.service.dao.DAOFactory;
import android.wxapp.service.dao.PersonDao;

/**
 * �ͻ�ģ��
 * 
 * @author WEIHAO
 * 
 */

public class CustomerModel {
	/**
	 * // �ͻ�ID
	 */
	private String customerID;
	/**
	 * // ����
	 */
	private String name;
	/**
	 * // ���ڵ�λ
	 */
	private String unit;
	/**
	 * // ���������š�ְ�񡢼���ȣ�
	 */
	private String description;
	/**
	 * // ����������ϵ��ID
	 */
	private String contactID;

	private static DAOFactory daoFactory = DAOFactory.getInstance();

	public CustomerModel(String customerID, String name, String unit, String description,
			String contactID) {
		super();
		this.customerID = customerID;
		this.name = name;
		this.unit = unit;
		this.description = description;
		this.contactID = contactID;
	}

//	/** ���� **/
//	public boolean save(Context context) {
//		PersonDao dao = null;
//		dao = daoFactory.getPersonDao(context);
//		try {
//			return dao.saveCustomer(this);
//		} finally {
//		}
//	}

	public String getCustomerID() {
		return customerID;
	}

	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getContactID() {
		return contactID;
	}

	public void setContactID(String contactID) {
		this.contactID = contactID;
	}

}
