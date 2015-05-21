package android.wxapp.service.model;

import java.util.ArrayList;

import android.content.Context;
import android.wxapp.service.dao.AffairDao;
import android.wxapp.service.dao.DAOFactory;

/**
 * 事务模型
 * 
 * @author WEIHAO
 * 
 */

public class AffairModel {
	/**
	 * // 事务ID
	 */
	private String affairID;
	/**
	 * // 事务类型：1-任务
	 */
	private int type;
	/**
	 * // 发起人ID
	 */
	private int sponsorID;
	/**
	 * // 事务的责任人 （构造该事务模型时，此字段非必选项）
	 */
	private PersonOnDutyModel person;
	/**
	 * // 事务主题
	 */
	private String title;
	/**
	 * // 事务文本内容
	 */
	private String description;
	/**
	 * // 开始时间
	 */
	private String beginTime;
	/**
	 * // 截止时间
	 */
	private String endTime;
	/**
	 * // 实际完成时间
	 */
	private String completeTime;
	/**
	 * // 最后一次操作类型： 1-新建，2-置完成（手动），3-置延误（自动），4-修改截止日期
	 */
	private int lastOperateType;
	/**
	 * // 最后一次操作时间
	 */
	private String lastOperateTime;
	/**
	 * // 事务在手机端是否已读：0-未读，1-已读
	 */
	private int isRead;
	/**
	 * // 事务状态：1-未完成（进行中），2-已完成，3-已延误
	 */
	private int status;
	/**
	 * // 事务包含的附件（构造该事务模型时，此字段非必选项）
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
		this.person = null; // 责任人，默认为空；需手动set
		this.title = title;
		this.description = description;
		this.beginTime = beginTime;
		this.endTime = endTime;
		this.completeTime = completeTime;
		this.lastOperateType = lastOperateType;
		this.lastOperateTime = lastOperateTime;
		this.isRead = isRead;
		this.status = status; // 事务状态(1-未完成，2-已完成，3-已延误)
		this.attachments = null; // 附件，默认为空；如有附件，需手动set
	}

	/** 保存 **/
	public boolean save(Context context) {
		AffairDao dao = null;
		dao = daoFactory.getAffairDao(context);
		try {
			return dao.saveAffair(this);
		} finally {
		}
	}

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
