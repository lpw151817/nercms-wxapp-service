package android.wxapp.service.model;

import android.content.Context;
import android.wxapp.service.dao.AttachmentDao;
import android.wxapp.service.dao.DAOFactory;

/**
 * ������������ģ��
 * 
 * @author WEIHAO
 * 
 */

public class FeedbackAttachModel {
	private String feedbackID; // ��Ӧ ������ID
	private int attachmentType; // ��������: 1-�ı���2-ͼƬ��3-��Ƶ��4-����
	private String attachmentURL; // ��������������׺��

	private static DAOFactory daoFactory = DAOFactory.getInstance();

	public FeedbackAttachModel(String feedbackID, int attachmentType,
			String attachmentURL) {
		super();
		this.feedbackID = feedbackID;
		this.attachmentType = attachmentType;
		this.attachmentURL = attachmentURL;
	}

	/** ���� **/
	public boolean save(Context context) {
		AttachmentDao dao = null;
		dao = daoFactory.getAttachmentDao(context);
		try {
			return dao.saveAttachment(this);
		} finally {
		}
	}

	public String getFeedbackID() {
		return feedbackID;
	}
	public void setFeedbackID(String feedbackID) {
		this.feedbackID = feedbackID;
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
