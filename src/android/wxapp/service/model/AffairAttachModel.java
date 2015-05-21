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

	public AffairAttachModel(String affairID, int attachmentType,
			String attachmentURL) {
		super();
		this.affairID = affairID;
		this.attachmentType = attachmentType;
		this.attachmentURL = attachmentURL;
	}

	/** 保存 **/
	public boolean save(Context context) {
		AttachmentDao dao = null;
		dao = daoFactory.getAttachmentDao(context);
		try {
			return dao.saveAttachment(this);
		} finally {
		}
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

}
