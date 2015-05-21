package android.wxapp.service.dao;

import android.content.Context;

public class DAOFactory {
	private static DAOFactory daoFactoryInstance = null;

	private DAOFactory() {
	}

	public static DAOFactory getInstance() {
		if (daoFactoryInstance == null) {
			synchronized (DAOFactory.class) {
				// check twice
				if (daoFactoryInstance == null) {
					daoFactoryInstance = new DAOFactory();
				}
			}
		}
		return daoFactoryInstance;
	}

	public AffairDao getAffairDao(Context context) {
		return new AffairDao(context);
	}

	public PersonOnDutyDao getPersonOnDutyDao(Context context) {
		return new PersonOnDutyDao(context);
	}

	public AttachmentDao getAttachmentDao(Context context) {
		return new AttachmentDao(context);
	}

	public FeedbackDao getFeedbackDao(Context context) {
		return new FeedbackDao(context);
	}

	public PersonDao getPersonDao(Context context) {
		return new PersonDao(context);
	}

	public MessageDao getMessageDao(Context context) {
		return new MessageDao(context);
	}

	public PhoneDao getPhoneDao(Context context) {
		return new PhoneDao(context);
	}

	public ConferenceDao getConferenceDao(Context context) {
		return new ConferenceDao(context);
	}

	public ConferencePersonDao getConferencePersonDao(Context context) {
		return new ConferencePersonDao(context);
	}
}
