package android.wxapp.service.thread;

import java.util.ArrayList;

import android.content.Context;
import android.wxapp.service.dao.MessageDao;
import android.wxapp.service.handler.MessageHandlerManager;
import android.wxapp.service.jerry.model.message.ReceiveMessageResponse;
import android.wxapp.service.model.MessageModel;
import android.wxapp.service.util.Constant;
import android.wxapp.service.util.MySharedPreference;

public class SaveMessageThread extends Thread {

	private Context context;
	private String mid;
	private ReceiveMessageResponse data;

	public SaveMessageThread(Context context, String mid, ReceiveMessageResponse data) {
		super();
		this.context = context;
		this.mid = mid;
		this.data = data;
	}

	@Override
	public void run() {

		if (new MessageDao(context).saveMessage(mid, data)) {
			MySharedPreference.save(context, MySharedPreference.LAST_UPDATE_TASK_TIMESTAMP,
					System.currentTimeMillis() + "");
		} else {

		}
		super.run();
	}
	// private Context context = null;
	// private boolean flag = true;
	//
	// // ��Ϣ�Ƿ��ɱ�������
	// private boolean isSend = true; // Ĭ�� ��
	//
	// private ArrayList<MessageModel> messageList;
	//
	// public SaveMessageThread(Context context,
	// ArrayList<MessageModel> messageList) {
	// this.context = context;
	// this.messageList = new ArrayList<MessageModel>();
	// this.messageList.addAll(messageList);
	// }
	//
	// public void addMessage(ArrayList<MessageModel> messages) {
	// this.messageList.addAll(messages);
	// }
	//
	// public void setFlag(boolean flag) {
	// this.flag = flag;
	// }
	//
	// public void setIsSend(boolean isSend) {
	// this.isSend = isSend;
	// }
	//
	// @Override
	// public void run() {
	// while (flag) {
	// MessageModel message;
	// while (messageList.size() != 0) {
	// message = messageList.get(0);
	//
	// // 2014-6-19 WeiHao
	// // �ж���Ϣ�Ƿ��Ѵ���
	// // ...
	//
	// // 2014-7-15 �ж��Ƿ�ΪȺ��Ϣ
	// if (String.valueOf(message.getReceiverID()).length() != 6) {
	// message.setIsGroup(1);
	// } else {
	// message.setIsGroup(0);
	// }
	//
	// // if (message.save(context)) {
	// // // ����Ǳ������ͣ����յ��ģ�����ʾNotification֪ͨ
	// // if (!isSend) {
	// // // MyNotification.showMessageNotification(context);
	// // // MyNotification.showMessageNotification(
	// // // String.valueOf(message.getSenderID()),
	// // // String.valueOf(message.getSenderID()),
	// // // context);
	// // MessageHandlerManager.getInstance().sendMessage(
	// // Constant.SHOW_MESSAGE_NOTIFICATION, message,
	// // "Main");
	// // }
	// //
	// // // ֪ͨUI�߳�ˢ�½���
	// // MessageHandlerManager.getInstance().sendMessage(
	// // Constant.SAVE_MESSAGE_SUCCESS, "Main");
	// // MessageHandlerManager.getInstance().sendMessage(
	// // Constant.SAVE_MESSAGE_SUCCESS, message,
	// // "ChatDetail");
	// // }
	// messageList.remove(0);
	//
	// }
	//
	// // Ĭ��Ϊ�������ͣ�����ʾ֪ͨ
	// this.setIsSend(true);
	//
	// // д������Ϻ�ȴ�
	// synchronized (this) {
	// try {
	// wait();
	// } catch (InterruptedException e) {
	// e.printStackTrace();
	// }
	// }
	// }
	// }

}
