package android.wxapp.service.model;

import android.content.Context;
import android.wxapp.service.dao.ConferencePersonDao;
import android.wxapp.service.dao.DAOFactory;

public class ConferencePersonModel {

	private String conferenceID; // ����ID
	private int personID; // ������ID

	// ...
	private int isSponsor; // �Ƿ�Ϊ���鷢����
	private int isSpeaker; // �Ƿ�Ϊ������
	private int isVideoSharer; // �Ƿ�����Ƶ�ϴ���
	private String joinTime; // �������ʱ��
	private String leaveTime; // �뿪����ʱ��

	private DAOFactory daoFactory = DAOFactory.getInstance();

	public ConferencePersonModel(String conferenceID, int personID,
			int isSponsor,
			int isSpeaker, int isVideoSharer, String joinTime, String leaveTime) {
		this.conferenceID = conferenceID;
		this.personID = personID;
		this.isSponsor = isSponsor;
		this.isSpeaker = isSpeaker;
		this.isVideoSharer = isVideoSharer;
		this.joinTime = joinTime;
		this.leaveTime = leaveTime;
	}

	/** ���� **/
	public boolean save(Context context) {
		ConferencePersonDao dao = null;
		dao = daoFactory.getConferencePersonDao(context);
		try {
			return dao.saveConferencePerson(this);
		} finally {
		}
	}

	public String getConferenceID() {
		return conferenceID;
	}

	public void setConferenceID(String conferenceID) {
		this.conferenceID = conferenceID;
	}


	public int getPersonID() {
		return personID;
	}

	public void setPersonID(int personID) {
		this.personID = personID;
	}

	public int getIsSponsor() {
		return isSponsor;
	}

	public void setIsSponsor(int isSponsor) {
		this.isSponsor = isSponsor;
	}

	public int getIsSpeaker() {
		return isSpeaker;
	}

	public void setIsSpeaker(int isSpeaker) {
		this.isSpeaker = isSpeaker;
	}

	public int getIsVideoSharer() {
		return isVideoSharer;
	}

	public void setIsVideoSharer(int isVideoSharer) {
		this.isVideoSharer = isVideoSharer;
	}

	public String getJoinTime() {
		return joinTime;
	}

	public void setJoinTime(String joinTime) {
		this.joinTime = joinTime;
	}

	public String getLeaveTime() {
		return leaveTime;
	}

	public void setLeaveTime(String leaveTime) {
		this.leaveTime = leaveTime;
	}

}
