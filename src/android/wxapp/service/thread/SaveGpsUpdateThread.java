package android.wxapp.service.thread;

import android.content.Context;
import android.wxapp.service.dao.GpsDao;
import android.wxapp.service.handler.MessageHandlerManager;
import android.wxapp.service.jerry.model.gps.GpsUpdateQueryResponse;
import android.wxapp.service.request.Contants;
import android.wxapp.service.util.Constant;
import android.wxapp.service.util.MySharedPreference;

public class SaveGpsUpdateThread extends Thread {
	Context c;
	GpsDao gpsDao;
	GpsUpdateQueryResponse data;

	public SaveGpsUpdateThread(Context c, GpsUpdateQueryResponse data) {
		this.c = c;
		this.gpsDao = new GpsDao(c);
		this.data = data;
	}

	@Override
	public void run() {

		if (this.gpsDao.saveGpsUpdate(data)) {
			MySharedPreference.save(c, MySharedPreference.LAST_UPDATE_GPS_TIMESTAMP,
					System.currentTimeMillis() + "");
			// 将返回结果返回给handler进行ui处理
			MessageHandlerManager.getInstance().sendMessage(Constant.SAVE_GPS_SECCESS,
					Contants.METHOD_GPS_UPDAET);
		} else {
			// sendmessage
			MessageHandlerManager.getInstance().sendMessage(Constant.SAVE_GPS_FAIL,
					Contants.METHOD_GPS_UPDAET);
		}

	}
}
