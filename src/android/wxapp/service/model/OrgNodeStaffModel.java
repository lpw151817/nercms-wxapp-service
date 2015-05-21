package android.wxapp.service.model;

import android.content.Context;
import android.wxapp.service.dao.DAOFactory;
import android.wxapp.service.dao.PersonDao;

/**
 * 机构节点成员模型
 * 
 * @author WEIHAO
 * 
 */
public class OrgNodeStaffModel {
	private String orgCode; // 机构节点代码
	private String contactID;// 联系人ID
	private String sequence;// 节点内联系人排序

	private static DAOFactory daoFactory = DAOFactory.getInstance();

	public OrgNodeStaffModel(String orgCode, String contactID, String sequence) {
		super();
		this.orgCode = orgCode;
		this.contactID = contactID;
		this.sequence = sequence;
	}

	/** 保存 **/
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
