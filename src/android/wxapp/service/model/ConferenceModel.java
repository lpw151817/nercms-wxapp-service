package android.wxapp.service.model;

import java.util.ArrayList;

import android.content.Context;
import android.wxapp.service.dao.ConferenceDao;
import android.wxapp.service.dao.DAOFactory;

public class ConferenceModel {

	private String conferenceID; // ����ID
	private String conferenceName; // �������ƣ����⣩
	// �������ͣ�
	// 1-�ֻ�����ʱ���飻2-�ֻ�����������飨SOS����3-�ֻ�����ԤԼ����
	private int type;
	private int sponsorID; // ������ID
	// ���鷢��ʱ�䣺
	// ��ʱ����ʱ��createTime == startTime
	// ԤԼ����ʱ��createTime == ����ԤԼ�ĵ�ǰʱ��
	private String createTime;
	private String reservationTime; // ԤԼ�Ŀ�ʼʱ��
	private String startTime; // ʵ�ʿ�ʼʱ��
	private String endTime; // �������ʱ��
	// ����״̬��1-��������У�2-���������3-ԤԼ�У��ȴ���ʼ��
	private int status;
	// ����������б� ��ͨ��set������
	private ArrayList<ConferencePersonModel> personList;

	private DAOFactory daoFactory = DAOFactory.getInstance();

	public ConferenceModel(String conferenceID, String conferenceName, int type, int sponsorID,
			String createTime, String reservationTime, String startTime, String endTime, int status) {
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
