package android.wxapp.service.thread;

import android.content.Context;
import android.util.Log;
import android.wxapp.service.dao.AffairDao;
import android.wxapp.service.dao.DAOFactory;
import android.wxapp.service.jerry.model.affair.TaskUpdateQueryResponse;
import android.wxapp.service.util.MySharedPreference;

public class SaveAffairUpdateThread extends Thread {
	String TAG = "SaveAffairUpdateThread";
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

		dao.saveAffairUpdate(data);
		// 更新本地时间戳
		MySharedPreference.save(c, MySharedPreference.LAST_UPDATE_TASK_TIMESTAMP, data.getTsp());
		super.run();
	}
}
