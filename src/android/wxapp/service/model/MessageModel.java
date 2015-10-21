package android.wxapp.service.model;

import android.content.Context;
import android.wxapp.service.dao.DAOFactory;
import android.wxapp.service.dao.MessageDao;

public class MessageModel {
	/**
	 * // 消息ID
	 */
	private String messageID;
	/**
	 * // 发送者ID
	 */
	private int senderID;
	/**
	 * // 接收者ID
	 */
	private int receiverID;
	/**
	 * // 发送时间
	 */
	private String sendTime;
	/**
	 * // 消息文本内容（如果该消息为附件消息，该字段为空）
	 */
	private String description;
	/**
	 * // 消息附件类型（如果该消息为文本消息，该字段为空）：附件类型: 1-文本；2-图片；3-视频；4-语音
	 */
	private int attachmentType;
	/**
	 * // 附件链接地址（如果该消息为文本消息，该字段为空）
	 */
	private String attachmentURL;
	/**
	 * 是否为群消息：0-不是，1-是
	 */
	private int isGroup; // 2014-7-30 WeiHao ；
	/**
	 * // 消息在手机端是否已读：0-未读，1-已读
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
