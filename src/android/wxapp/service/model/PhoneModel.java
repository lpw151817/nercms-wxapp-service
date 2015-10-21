package android.wxapp.service.model;

import android.content.Context;
import android.wxapp.service.dao.DAOFactory;
import android.wxapp.service.dao.PhoneDao;

public class PhoneModel {

	// �绰�����У�ID
	// ��typeΪ1ʱ�����ֶ�Ϊ���������绰��id
	// ��typeΪ2ʱ�����ֶα�ʾ���غ����ߵĺ���number
	private String phoneID;

	private int type; // �������� :1-IP�绰��2-���ص绰 2014-6-13

	// Ĭ�Ϲ��캯����û�д��ֶ� ��ͨ��setter��getter����ֵ��ȡֵ
	// ��typeΪ1ʱ�����ֶ���ֵ������
	// ��typeΪ2ʱ�����ֶα�ʾ���غ���������
	private String callerName;

	// Ĭ�Ϲ��캯����û�д��ֶ� ��ͨ��setter��getter����ֵ��ȡֵ
	// ��typeΪ1ʱ�����ֶ���ֵ������
	// ��typeΪ2ʱ�����ֶα�ʾ���غ��е����ͣ�1-incoming;2-outgoing;3-missed
	private int callType;

	private int callerID; // ������ID
	private int calleeID; // ��������ID
	private String startTime; // ��ʼʱ��
	private int isAnswered; // �Ƿ��ͨ :0-δ��ͨ��1-�ѽ�ͨ
	private String endTime; // ����ʱ��
	private String duration; // ���г���ʱ��
	private int isRead; // �绰���ֻ����Ƿ��Ѷ���0-δ����1-�Ѷ�

	private static DAOFactory daoFactory = DAOFactory.getInstance();

	public PhoneModel(String phoneID, int type, int callerID, int calleeID,
			String startTime, int isAnswered, String endTime, String duration,int isRead) {
		this.phoneID = phoneID;
		this.type = type;
		this.callerID = callerID;
		this.calleeID = calleeID;
		this.startTime = startTime;
		this.isAnswered = isAnswered;
		this.endTime = endTime;
		this.duration = duration;
		this.isRead = isRead;
	}

	// ����
	public boolean save(Context context){
		PhoneDao dao=null;
		dao = daoFactory.getPhoneDao(context);
		try {
			return dao.savePhone(this);
		} finally {
		}
	}

	public int getCallType() {
		return callType;
	}

	public void setCallType(int callType) {
		this.callType = callType;
	}

	public String getCallerName() {
		return callerName;
	}

	public void setCallerName(String callerName) {
		this.callerName = callerName;
	}

	public String getPhoneID() {
		return phoneID;
	}

	public void setPhoneID(String phoneID) {
		this.phoneID = phoneID;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getCallerID() {
		return callerID;
	}

	public void setCallerID(int callerID) {
		this.callerID = callerID;
	}

	public int getCalleeID() {
		return calleeID;
	}

	public void setCalleeID(int calleeID) {
		this.calleeID = calleeID;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public int getIsAnswered() {
		return isAnswered;
	}

	public void setIsAnswered(int isAnswered) {
		this.isAnswered = isAnswered;
	}

	public int getIsRead() {
		return isRead;
	}

	public void setIsRead(int isRead) {
		this.isRead = isRead;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

}
