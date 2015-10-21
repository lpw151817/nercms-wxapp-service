package android.wxapp.service.thread;

import android.content.Context;
import android.wxapp.service.dao.ConferenceDao;
import android.wxapp.service.handler.MessageHandlerManager;
import android.wxapp.service.jerry.model.conference.ConferenceUpdateQueryResponse;
import android.wxapp.service.request.Contants;
import android.wxapp.service.util.Constant;
import android.wxapp.service.util.MySharedPreference;

public class SaveConferenceThread extends Thread {
	public static final String TAG = "SaveConferenceThread";
	ConferenceUpdateQueryResponse data;
	Context c;
	ConferenceDao dao;

	public SaveConferenceThread(Context c, ConferenceUpdateQueryResponse data) {
		this.data = data;
		this.c = c;
		this.dao = new ConferenceDao(c);
	}

	@Override
	public void run() {
		if (dao.saveConferenceUpdate(data)) {
			// 更新本地时间戳
			MySharedPreference.save(c, MySharedPreference.LAST_UPDATE_CONFERENCE_TIMESTAMP,
					System.currentTimeMillis() + "");
			MessageHandlerManager.getInstance().sendMessage(Constant.CONFERENCE_SAVE_SUCCESS, TAG);
		} else {
			MessageHandlerManager.getInstance().sendMessage(Constant.CONFERENCE_SAVE_FAIL, TAG);
		}
	}
}
