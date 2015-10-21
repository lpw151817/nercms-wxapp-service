package android.wxapp.service.model;

import android.content.Context;
import android.wxapp.service.dao.DAOFactory;
import android.wxapp.service.dao.PersonDao;

/**
 * ����ȫ����ϵ��ģ��
 * 
 * @author WEIHAO
 * 
 */
public class OrgStaffModel {
	private String contactID; // ��ϵ��ID
	private String name;// ����
	private String position;// ְ��
	private String rank;// ְ��

	private static DAOFactory daoFactory = DAOFactory.getInstance();

	public OrgStaffModel(String contactID, String name, String position,
			String rank) {
		super();
		this.contactID = contactID;
		this.name = name;
		this.position = position;
		this.rank = rank;
	}

	/** ���� **/
	public boolean save(Context context) {
		PersonDao dao = null;
		dao = daoFactory.getPersonDao(context);
		try {
			return dao.saveOrgStaff(this);
		} finally {
		}
	}

	public String getContactID() {
		return contactID;
	}

	public void setContactID(String contactID) {
		this.contactID = contactID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

}
