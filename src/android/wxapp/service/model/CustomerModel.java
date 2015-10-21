package android.wxapp.service.model;

import android.content.Context;
import android.wxapp.service.dao.DAOFactory;
import android.wxapp.service.dao.PersonDao;

/**
 * 客户模型
 * 
 * @author WEIHAO
 * 
 */

public class CustomerModel {
	/**
	 * // 客户ID
	 */
	private String customerID;
	/**
	 * // 姓名
	 */
	private String name;
	/**
	 * // 所在单位
	 */
	private String unit;
	/**
	 * // 描述（部门、职务、级别等）
	 */
	private String description;
	/**
	 * // 归属机构联系人ID
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

//	/** 保存 **/
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
