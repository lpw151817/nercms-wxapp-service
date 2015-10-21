package android.wxapp.service.model;

import android.content.Context;
import android.wxapp.service.dao.DAOFactory;
import android.wxapp.service.dao.PersonOnDutyDao;

public class PersonOnDutyModel {

	private String affairID;// 事务ID
	private int personID; // 责任人ID

	private static DAOFactory daoFactory = DAOFactory.getInstance();

	public PersonOnDutyModel(String affairID, int personID) {
		super();
		this.affairID = affairID;
		this.personID = personID;
	}

	/** 保存 **/
	public boolean save(Context context) {
		PersonOnDutyDao dao = null;
		dao = daoFactory.getPersonOnDutyDao(context);
		try {
			return dao.savePersonOnDuty(this);

		} finally {
		}
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

}
