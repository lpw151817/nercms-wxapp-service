package android.wxapp.service.thread;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.wxapp.service.dao.AffairDao;
import android.wxapp.service.dao.AttachmentDao;
import android.wxapp.service.dao.DAOFactory;
import android.wxapp.service.handler.MessageHandlerManager;
import android.wxapp.service.jerry.model.affair.CreateTaskRequest;
import android.wxapp.service.jerry.model.affair.CreateTaskRequestAttachment;
import android.wxapp.service.jerry.model.affair.QueryAffairInfoResponse;
import android.wxapp.service.model.AffairAttachModel;
import android.wxapp.service.model.AffairModel;
import android.wxapp.service.util.Constant;
import android.wxapp.service.util.MySharedPreference;

public class SaveAffairThread extends Thread {
	Context c;
	QueryAffairInfoResponse data;

	@Override
	public void run() {
		AffairDao affairDao = new AffairDao(c);
		affairDao.saveAffairInfo(this.data);
		super.run();
	}

	public SaveAffairThread(Context c, QueryAffairInfoResponse data) {
		super();
		this.c = c;
		this.data = data;
	}

}
