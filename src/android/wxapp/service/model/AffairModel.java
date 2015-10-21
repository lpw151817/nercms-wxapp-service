package android.wxapp.service.model;

import java.util.ArrayList;

import android.content.Context;
import android.wxapp.service.dao.AffairDao;
import android.wxapp.service.dao.DAOFactory;

/**
 * ����ģ��
 * 
 * @author WEIHAO
 * 
 */

public class AffairModel {
	/**
	 * // ����ID
	 */
	private String affairID;
	/**
	 * // �������ͣ�1-����
	 */
	private int type;
	/**
	 * // ������ID
	 */
	private int sponsorID;
	/**
	 * // ����������� �����������ģ��ʱ�����ֶηǱ�ѡ�
	 */
	private PersonOnDutyModel person;
	/**
	 * // ��������
	 */
	private String title;
	/**
	 * // �����ı�����
	 */
	private String description;
	/**
	 * // ��ʼʱ��
	 */
	private String beginTime;
	/**
	 * // ��ֹʱ��
	 */
	private String endTime;
	/**
	 * // ʵ�����ʱ��
	 */
	private String completeTime;
	/**
	 * // ���һ�β������ͣ� 1-�½���2-����ɣ��ֶ�����3-�������Զ�����4-�޸Ľ�ֹ����
	 */
	private int lastOperateType;
	/**
	 * // ���һ�β���ʱ��
	 */
	private String lastOperateTime;
	/**
	 * // �������ֻ����Ƿ��Ѷ���0-δ����1-�Ѷ�
	 */
	private int isRead;
	/**
	 * // ����״̬��1-δ��ɣ������У���2-����ɣ�3-������
	 */
	private int status;
	/**
	 * // ��������ĸ��������������ģ��ʱ�����ֶηǱ�ѡ�
	 */
	private ArrayList<AffairAttachModel> attachments;

	private static DAOFactory daoFactory = DAOFactory.getInstance();

	public AffairModel(String affairID,/* PersonOnDutyModel person, */
			int type, int sponsorID, String title, String description, String beginTime, String endTime,
			String completeTime, int lastOperateType, String lastOperateTime, int isRead, int status
	/* ArrayList<AttachmentModel> attachments */) {
		super();
		this.affairID = affairID;
		this.type = type;
		this.sponsorID = sponsorID;
		this.person = null; // �����ˣ�Ĭ��Ϊ�գ����ֶ�set
		this.title = title;
		this.description = description;
		this.beginTime = beginTime;
		this.endTime = endTime;
		this.completeTime = completeTime;
		this.lastOperateType = lastOperateType;
		this.lastOperateTime = lastOperateTime;
		this.isRead = isRead;
		this.status = status; // ����״̬(1-δ��ɣ�2-����ɣ�3-������)
		this.attachments = null; // ������Ĭ��Ϊ�գ����и��������ֶ�set
	}

//	/** ���� **/
//	public boolean save(Context context) {
//		AffairDao dao = null;
//		dao = daoFactory.getAffairDao(context);
//		try {
//			return dao.saveAffair(this);
//		} finally {
//		}
//	}

	public String getAffairID() {
		return affairID;
	}

	public void setAffairID(String affairID) {
		this.affairID = affairID;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getSponsorID() {
		return sponsorID;
	}

	public void setSponsorID(int sponsorID) {
		this.sponsorID = sponsorID;
	}

	public PersonOnDutyModel getPerson() {
		return person;
	}

	public void setPerson(PersonOnDutyModel person) {
		this.person = person;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getCompleteTime() {
		return completeTime;
	}

	public void setCompleteTime(String completeTime) {
		this.completeTime = completeTime;
	}

	public int getLastOperateType() {
		return lastOperateType;
	}

	public void setLastOperateType(int lastOperateType) {
		this.lastOperateType = lastOperateType;
	}

	public String getLastOperateTime() {
		return lastOperateTime;
	}

	public void setLastOperateTime(String lastOperateTime) {
		this.lastOperateTime = lastOperateTime;
	}

	public int getIsRead() {
		return isRead;
	}

	public void setIsRead(int isRead) {
		this.isRead = isRead;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public ArrayList<AffairAttachModel> getAttachments() {
		return attachments;
	}

	public void setAttachments(ArrayList<AffairAttachModel> attachments) {
		this.attachments = attachments;
	}

}
