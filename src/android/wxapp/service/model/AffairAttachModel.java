package android.wxapp.service.model;

import android.content.Context;
import android.wxapp.service.dao.AttachmentDao;
import android.wxapp.service.dao.DAOFactory;

/**
 * （事务）附件模型
 * 
 * @author WEIHAO
 * 
 */

public class AffairAttachModel {
	private String affairID; // 对应的 事务ID
	private int attachmentType; // 附件类型
	private String attachmentURL; // 附件名（包含后缀）

	private static DAOFactory daoFactory = DAOFactory.getInstance();

	public AffairAttachModel(String affairID, int attachmentType, String attachmentURL) {
		super();
		this.affairID = affairID;
		this.attachmentType = attachmentType;
		this.attachmentURL = attachmentURL;
	}

	public String getAffairID() {
		return affairID;
	}

	public void setAffairID(String affairID) {
		this.affairID = affairID;
	}

	public int getAttachmentType() {
		return attachmentType;
	}

	public void setAttachmentType(int attachmentType) {
		this.attachmentType = attachmentType;
	}

	public String getAttachmentURL() {
		return attachmentURL;
	}

	public void setAttachmentURL(String attachmentURL) {
		this.attachmentURL = attachmentURL;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((affairID == null) ? 0 : affairID.hashCode());
		result = prime * result + attachmentType;
		result = prime * result + ((attachmentURL == null) ? 0 : attachmentURL.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AffairAttachModel other = (AffairAttachModel) obj;
		if (affairID == null) {
			if (other.affairID != null)
				return false;
		} else if (!affairID.equals(other.affairID))
			return false;
		if (attachmentType != other.attachmentType)
			return false;
		if (attachmentURL == null) {
			if (other.attachmentURL != null)
				return false;
		} else if (!attachmentURL.equals(other.attachmentURL))
			return false;
		return true;
	}

}
