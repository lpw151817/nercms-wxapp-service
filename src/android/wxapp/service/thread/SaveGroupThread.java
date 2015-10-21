package android.wxapp.service.thread;

import android.content.Context;
import android.wxapp.service.dao.GroupDao;
import android.wxapp.service.handler.MessageHandlerManager;
import android.wxapp.service.jerry.model.group.GroupUpdateQueryResponse;
import android.wxapp.service.request.Contants;
import android.wxapp.service.util.Constant;
import android.wxapp.service.util.MySharedPreference;

public class SaveGroupThread extends Thread {
	Context c;
	GroupDao dao;
	GroupUpdateQueryResponse data;

	public SaveGroupThread(Context c, GroupUpdateQueryResponse data) {
		this.c = c;
		this.data = data;
		dao = new GroupDao(c);
	}

	@Override
	public void run() {
		if (dao.saveGroupUpdate(data)) {
			MySharedPreference.save(c, MySharedPreference.LAST_UPDATE_GROUP_TIMESTAMP,
					System.currentTimeMillis() + "");
			MessageHandlerManager.getInstance().sendMessage(Constant.GROUP_SAVE_SECCESS,
					Contants.METHOD_GROUP_UPDATE);
		} else {
			MessageHandlerManager.getInstance().sendMessage(Constant.GROUP_SAVE_FAIL,
					Contants.METHOD_GROUP_UPDATE);
		}
	}
}
