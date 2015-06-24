package android.wxapp.service.thread;

import android.content.Context;
import android.wxapp.service.dao.MessageDao;
import android.wxapp.service.handler.MessageHandlerManager;
import android.wxapp.service.jerry.model.message.MessageUpdateQueryResponse;
import android.wxapp.service.request.Contants;
import android.wxapp.service.util.Constant;

public class SaveMessageUpdateThread extends Thread {
	Context c;
	MessageUpdateQueryResponse data;
	public static final String TAG = "SaveMessageUpdateThread";

	public SaveMessageUpdateThread(Context c, MessageUpdateQueryResponse data) {
		this.c = c;
		this.data = data;
	}

	@Override
	public void run() {
		MessageDao dao = new MessageDao(c);
		if (dao.saveMessageUpdate(data)) {
			MessageHandlerManager.getInstance().sendMessage(Constant.SAVE_MESSAGE_SUCCESS, TAG);
		}
		else MessageHandlerManager.getInstance().sendMessage(Constant.SAVE_MESSAGE_FAIL, TAG);
		super.run();
	}
}
