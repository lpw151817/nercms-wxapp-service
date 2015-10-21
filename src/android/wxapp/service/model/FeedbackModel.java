package android.wxapp.service.model;

import android.content.Context;
import android.wxapp.service.dao.DAOFactory;
import android.wxapp.service.dao.FeedbackDao;

/**
 * ����ģ��
 * 
 * @author WEIHAO
 * 
 */

public class FeedbackModel {
	/**
	 * // ����ID
	 */
	private String feedbackID;
	/**
	 * // ��Ӧ������Id
	 */
	private String affairID;
	/**
	 * // �����÷����˵�ID
	 */
	private int personID;
	/**
	 * // ��������ʱ��
	 */
	private String feedbackTime;
	/**
	 * // �������ı�����
	 */
	private String content;
	/**
	 * // �������ֻ����Ƿ��Ѷ���0-δ����1-�Ѷ�
	 */
	private int isRead;
	/**
	 * // ���������ĸ���������÷���ģ��ʱ�����ֶηǱ�ѡ�
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
		this.attachment = null; // ������Ĭ��Ϊ�գ����ֶ�set
	}

	/** ���� **/
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
