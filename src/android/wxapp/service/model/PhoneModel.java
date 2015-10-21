package android.wxapp.service.model;

import android.content.Context;
import android.wxapp.service.dao.DAOFactory;
import android.wxapp.service.dao.PhoneDao;

public class PhoneModel {

	// 电话（呼叫）ID
	// 当type为1时，此字段为数据索引电话的id
	// 当type为2时，此字段表示本地呼叫者的号码number
	private String phoneID;

	private int type; // 呼叫类型 :1-IP电话；2-本地电话 2014-6-13

	// 默认构造函数中没有此字段 ，通过setter和getter来设值和取值
	// 当type为1时，此字段无值无意义
	// 当type为2时，此字段表示本地呼叫者姓名
	private String callerName;

	// 默认构造函数中没有此字段 ，通过setter和getter来设值和取值
	// 当type为1时，此字段无值无意义
	// 当type为2时，此字段表示本地呼叫的类型：1-incoming;2-outgoing;3-missed
	private int callType;

	private int callerID; // 呼叫者ID
	private int calleeID; // 被呼叫者ID
	private String startTime; // 开始时间
	private int isAnswered; // 是否接通 :0-未接通，1-已接通
	private String endTime; // 结束时间
	private String duration; // 呼叫持续时间
	private int isRead; // 电话在手机端是否已读：0-未读，1-已读

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

	// 保存
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
