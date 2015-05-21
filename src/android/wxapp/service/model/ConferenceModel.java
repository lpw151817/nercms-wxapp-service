package android.wxapp.service.model;

import java.util.ArrayList;

import android.content.Context;
import android.wxapp.service.dao.ConferenceDao;
import android.wxapp.service.dao.DAOFactory;

public class ConferenceModel {

	private String conferenceID; // 会议ID
	private String conferenceName; // 会议名称（主题）
	// 会议类型：
	// 1-手机发起即时会议；2-手机发起紧急会议（SOS）；3-手机发起预约会议
	private int type;
	private int sponsorID; // 发起人ID
	// 会议发起时间：
	// 即时会议时，createTime == startTime
	// 预约会议时，createTime == 创建预约的当前时间
	private String createTime;
	private String reservationTime; // 预约的开始时间
	private String startTime; // 实际开始时间
	private String endTime; // 会议结束时间
	// 会议状态：1-会议进行中；2-会议结束；3-预约中（等待开始）
	private int status;
	// 会议参与者列表 ，通过set来设置
	private ArrayList<ConferencePersonModel> personList;

	private DAOFactory daoFactory = DAOFactory.getInstance();

	public ConferenceModel(String conferenceID, String conferenceName,
			int type, int sponsorID, String createTime, String reservationTime,
			String startTime, String endTime, int status) {
		this.conferenceID = conferenceID;
		this.conferenceName = conferenceName;
		this.type = type;
		this.sponsorID = sponsorID;
		this.createTime = createTime;
		this.reservationTime = reservationTime;
		this.startTime = startTime;
		this.endTime = endTime;
		this.status = status;
	}

	/** 保存 **/
	public boolean save(Context context) {
		ConferenceDao dao = null;
		dao = daoFactory.getConferenceDao(context);
		try {
			return dao.saveConference(this);
		} finally {
		}
	}

	public String getConferenceID() {
		return conferenceID;
	}

	public void setConferenceID(String conferenceID) {
		this.conferenceID = conferenceID;
	}
	public String getConferenceName() {
		return conferenceName;
	}
	public void setConferenceName(String conferenceName) {
		this.conferenceName = conferenceName;
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
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getReservationTime() {
		return reservationTime;
	}
	public void setReservationTime(String reservationTime) {
		this.reservationTime = reservationTime;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public ArrayList<ConferencePersonModel> getPersonList() {
		return personList;
	}
	public void setPersonList(ArrayList<ConferencePersonModel> personList) {
		this.personList = personList;
	}


}
