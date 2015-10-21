package android.wxapp.service.model;

import android.content.Context;
import android.wxapp.service.dao.DAOFactory;
import android.wxapp.service.dao.MessageDao;

public class MessageModel {
	/**
	 * // ��ϢID
	 */
	private String messageID;
	/**
	 * // ������ID
	 */
	private int senderID;
	/**
	 * // ������ID
	 */
	private int receiverID;
	/**
	 * // ����ʱ��
	 */
	private String sendTime;
	/**
	 * // ��Ϣ�ı����ݣ��������ϢΪ������Ϣ�����ֶ�Ϊ�գ�
	 */
	private String description;
	/**
	 * // ��Ϣ�������ͣ��������ϢΪ�ı���Ϣ�����ֶ�Ϊ�գ�����������: 1-�ı���2-ͼƬ��3-��Ƶ��4-����
	 */
	private int attachmentType;
	/**
	 * // �������ӵ�ַ���������ϢΪ�ı���Ϣ�����ֶ�Ϊ�գ�
	 */
	private String attachmentURL;
	/**
	 * �Ƿ�ΪȺ��Ϣ��0-���ǣ�1-��
	 */
	private int isGroup; // 2014-7-30 WeiHao ��
	/**
	 * // ��Ϣ���ֻ����Ƿ��Ѷ���0-δ����1-�Ѷ�
	 */
	private int isRead;

	private static DAOFactory daoFactory = DAOFactory.getInstance();

	public MessageModel(String messageID, int senderID, int receiverID, String sendTime,
			String description, int attachmentType, String attachmentURL, int isGroup, int isRead) {
		this.messageID = messageID;
		this.senderID = senderID;
		this.receiverID = receiverID;
		this.sendTime = sendTime;
		this.description = description;
		this.attachmentType = attachmentType;
		this.attachmentURL = attachmentURL;
		this.isGroup = isGroup;
		this.isRead = isRead;
	}

	public int getIsGroup() {
		return isGroup;
	}

	public void setIsGroup(int isGroup) {
		this.isGroup = isGroup;
	}

	public String getMessageID() {
		return messageID;
	}

	public void setMessageID(String messageID) {
		this.messageID = messageID;
	}

	public int getSenderID() {
		return senderID;
	}

	public void setSenderID(int senderID) {
		this.senderID = senderID;
	}

	public int getReceiverID() {
		return receiverID;
	}

	public void setReceiverID(int receiverID) {
		this.receiverID = receiverID;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public int getIsRead() {
		return isRead;
	}

	public void setIsRead(int isRead) {
		this.isRead = isRead;
	}

}
