package android.wxapp.service.thread;

import android.content.Context;
import android.util.Log;
import android.wxapp.service.dao.AffairDao;
import android.wxapp.service.dao.DAOFactory;
import android.wxapp.service.handler.MessageHandlerManager;
import android.wxapp.service.jerry.model.affair.TaskUpdateQueryResponse;
import android.wxapp.service.util.Constant;
import android.wxapp.service.util.MySharedPreference;

public class SaveAffairUpdateThread extends Thread {
	public static final String TAG = "SaveAffairUpdateThread";
	TaskUpdateQueryResponse data;
	AffairDao dao;
	Context c;

	public SaveAffairUpdateThread(Context c, TaskUpdateQueryResponse data, AffairDao dao) {
		this.dao = dao;
		this.data = data;
		this.c = c;
	}

	@Override
	public void run() {

		if (dao.saveAffairUpdate(data)) {
			// 更新本地时间戳
			MySharedPreference.save(c, MySharedPreference.LAST_UPDATE_TASK_TIMESTAMP, data.getTsp());
			MessageHandlerManager.getInstance().sendMessage(Constant.SAVE_TASK_SUCCESS, TAG);
		} else {
			MessageHandlerManager.getInstance().sendMessage(Constant.SAVE_TASK_FAIL, TAG);
		}
		super.run();
	}
}
