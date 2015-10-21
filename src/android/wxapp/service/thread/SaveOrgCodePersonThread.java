package android.wxapp.service.thread;

import android.content.Context;
import android.util.Log;
import android.wxapp.service.dao.PersonDao;
import android.wxapp.service.handler.MessageHandlerManager;
import android.wxapp.service.jerry.model.person.GetOrgCodePersonRequest;
import android.wxapp.service.jerry.model.person.GetOrgCodePersonResponse;
import android.wxapp.service.util.Constant;
import android.wxapp.service.util.MySharedPreference;

public class SaveOrgCodePersonThread extends Thread {

	private Context context;
	private GetOrgCodePersonResponse data;
	private PersonDao dao;
	public static final String TAG = "SaveOrgCodePersonThread";

	public SaveOrgCodePersonThread(Context c, GetOrgCodePersonResponse data) {
		this.context = c;
		this.data = data;
		dao = new PersonDao(c);
	}

	@Override
	public void run() {
		Log.v("SaveOrgCodePersonThread", "saving data");
		if (this.dao.saveOrgCodePerson(this.data)) {
			 // ֪ͨUI�߳�ˢ�½���
			 MessageHandlerManager.getInstance().sendMessage(Constant.SAVE_ORG_PERSON_SUCCESS,
			 TAG);
			// ����ʱ���
			MySharedPreference.save(context, MySharedPreference.LAST_UPDATE_ORGPERSON_TIMESTAMP,
					data.getUt());
			Log.v("SaveOrgCodePersonThread", "saving data success!");
		} else {
			// ֪ͨUI�߳�ˢ�½���
			MessageHandlerManager.getInstance().sendMessage(Constant.SAVE_ORG_PERSON_FAIL, TAG);
			Log.v("SaveOrgCodePersonThread", "saving data fail!");
		}
	}
}
