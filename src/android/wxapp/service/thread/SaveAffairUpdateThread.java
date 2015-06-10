package android.wxapp.service.thread;

import android.util.Log;
import android.wxapp.service.dao.AffairDao;
import android.wxapp.service.dao.DAOFactory;
import android.wxapp.service.jerry.model.affair.TaskUpdateQueryResponse;

public class SaveAffairUpdateThread extends Thread {
	String TAG = "SaveAffairUpdateThread";
	TaskUpdateQueryResponse data;
	AffairDao dao;

	public SaveAffairUpdateThread(TaskUpdateQueryResponse data, AffairDao dao) {
		this.dao = dao;
		this.data = data;
	}

	@Override
	public void run() {

		dao.saveAffairUpdate(data);

		super.run();
	}
}
