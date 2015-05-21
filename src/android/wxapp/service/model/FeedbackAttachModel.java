package android.wxapp.service.model;

import android.content.Context;
import android.wxapp.service.dao.AttachmentDao;
import android.wxapp.service.dao.DAOFactory;

/**
 * （反馈）附件模型
 * 
 * @author WEIHAO
 * 
 */

public class FeedbackAttachModel {
	private String feedbackID; // 对应 反馈的ID
	private int attachmentType; // 附件类型: 1-文本；2-图片；3-视频；4-语音
	private String attachmentURL; // 附件名（包含后缀）

	private static DAOFactory daoFactory = DAOFactory.getInstance();

	public FeedbackAttachModel(String feedbackID, int attachmentType,
			String attachmentURL) {
		super();
		this.feedbackID = feedbackID;
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
