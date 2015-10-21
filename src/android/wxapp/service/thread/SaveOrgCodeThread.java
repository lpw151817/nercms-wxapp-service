package android.wxapp.service.thread;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.wxapp.service.dao.PersonDao;
import android.wxapp.service.handler.MessageHandlerManager;
import android.wxapp.service.jerry.model.person.GetOrgCodeResponse;
import android.wxapp.service.util.Constant;
import android.wxapp.service.util.MySharedPreference;

public class SaveOrgCodeThread extends Thread {
	private Context context;
	private GetOrgCodeResponse data;
	private PersonDao dao;
	public static final String TAG = "SaveOrgCodeThread";

	public SaveOrgCodeThread(Context c, GetOrgCodeResponse data) {
		this.context = c;
		this.data = data;
		this.dao = new PersonDao(c);
	}

	@Override
	public void run() {
		Log.v("SaveOrgCodeThread", "saving data");
		if (this.dao.saveOrgCode(this.data)) {
			// ֪ͨUI�߳�ˢ�½���
			MessageHandlerManager.getInstance().sendMessage(Constant.SAVE_ORG_CODE_SUCCESS, TAG);
			// ����ʱ���
			MySharedPreference.save(context, MySharedPreference.LAST_UPDATE_ORGCODE_TIMESTAMP,
					data.getUt());
			Log.v("SaveOrgCodeThread", "saving data success!");
		} else {
			// ֪ͨUI�߳�ˢ�½���
			MessageHandlerManager.getInstance().sendMessage(Constant.SAVE_ORG_CODE_FAIL, TAG);
			Log.v("SaveOrgCodeThread", "saving data fail!");
		}
	}

}
