package android.wxapp.service.model;

import android.content.Context;
import android.wxapp.service.dao.DAOFactory;
import android.wxapp.service.dao.FeedbackDao;

/**
 * 反馈模型
 * 
 * @author WEIHAO
 * 
 */

public class FeedbackModel {
	/**
	 * // 反馈ID
	 */
	private String feedbackID;
	/**
	 * // 对应的事务Id
	 */
	private String affairID;
	/**
	 * // 发布该反馈人的ID
	 */
	private int personID;
	/**
	 * // 发布反馈时间
	 */
	private String feedbackTime;
	/**
	 * // 反馈的文本内容
	 */
	private String content;
	/**
	 * // 事务在手机端是否已读：0-未读，1-已读
	 */
	private int isRead;
	/**
	 * // 反馈包含的附件（构造该反馈模型时，此字段非必选项）
	 */
	private FeedbackAttachModel attachment;

	private static DAOFactory daoFactory = DAOFactory.getInstance();

	public FeedbackModel(String feedbackID, String affairID, int personID, String feedbackTime,
			String content, int isRead) {
		super();
		this.feedbackID = feedbackID;
		this.affairID = affairID;
		this.personID = personID;
		this.feedbackTime = feedbackTime;
		this.content = content;
		this.isRead = isRead;
		this.attachment = null; // 附件，默认为空；需手动set
	}

	/** 保存 **/
	public boolean save(Context context) {
		FeedbackDao dao = null;
		dao = daoFactory.getFeedbackDao(context);
		try {
			return dao.saveFeedback(this);
		} finally {
		}
	}

	public String getFeedbackID() {
		return feedbackID;
	}

	public void setFeedbackID(String feedbackID) {
		this.feedbackID = feedbackID;
	}

	public String getAffairID() {
		return affairID;
	}

	public void setAffairID(String affairID) {
		this.affairID = affairID;
	}

	public int getPersonID() {
		return personID;
	}

	public void setPersonID(int personID) {
		this.personID = personID;
	}

	public String getFeedbackTime() {
		return feedbackTime;
	}

	public void setFeedbackTime(String feedbackTime) {
		this.feedbackTime = feedbackTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getIsRead() {
		return isRead;
	}

	public void setIsRead(int isRead) {
		this.isRead = isRead;
	}

	public FeedbackAttachModel getAttachment() {
		return attachment;
	}

	public void setAttachment(FeedbackAttachModel attachment) {
		this.attachment = attachment;
	}

}
