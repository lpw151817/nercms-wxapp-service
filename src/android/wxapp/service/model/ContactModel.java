package android.wxapp.service.model;

import android.content.Context;
import android.wxapp.service.dao.DAOFactory;
import android.wxapp.service.dao.PersonDao;

/**
 * 联系方式模型
 * 
 * @author WEIHAO
 * 
 */

public class ContactModel {

	private String contactID;// 联系人ID
	private int type;// 联系方式类型 ：1-ID；2-手机号码；3-座机号码；5-邮箱；6-通信地址
	private String content;// 联系方式内容

	private static DAOFactory daoFactory = DAOFactory.getInstance();

	public ContactModel(String contactID, int type, String content) {
		super();
		this.contactID = contactID;
		this.type = type;
		this.content = content;
	}

	/** 保存 **/
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
