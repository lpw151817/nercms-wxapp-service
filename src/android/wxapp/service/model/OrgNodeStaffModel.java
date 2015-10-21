package android.wxapp.service.model;

import android.content.Context;
import android.wxapp.service.dao.DAOFactory;
import android.wxapp.service.dao.PersonDao;

/**
 * �����ڵ��Աģ��
 * 
 * @author WEIHAO
 * 
 */
public class OrgNodeStaffModel {
	private String orgCode; // �����ڵ����
	private String contactID;// ��ϵ��ID
	private String sequence;// �ڵ�����ϵ������

	private static DAOFactory daoFactory = DAOFactory.getInstance();

	public OrgNodeStaffModel(String orgCode, String contactID, String sequence) {
		super();
		this.orgCode = orgCode;
		this.contactID = contactID;
		this.sequence = sequence;
	}

	/** ���� **/
	public boolean save(Context context) {
		PersonDao dao = null;
		dao = daoFactory.getPersonDao(context);
		try {
			return dao.saveOrgNodeStaff(this);
		} finally {
		}
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getContactID() {
		return contactID;
	}

	public void setContactID(String contactID) {
		this.contactID = contactID;
	}

	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

}
