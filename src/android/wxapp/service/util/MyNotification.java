package android.wxapp.service.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.wxapp.service.R;

/**
 * ֪ͨ����ʾ������ ˵����Intent �ڶ���������������Ҫ�����޸ĳɶ�Ӧ��Activity.class
 * 
 * @author WEIHAO
 * @version 2014-6-25 �޸ľ���ʵ��
 * 
 */
public class MyNotification {
	public static final int AFFAIR_NOTIFICATION_ID = 123321;
	public static final int FEEDBACK_NOTIFICATION_ID = 123322;
	public static final int MESSAGE_NOTIFICATION_ID = 123323;

	/**
	 * ��ʾ������֪ͨ
	 * 
	 * @param talkerName
	 * @param talkerID
	 * @param context
	 */
	public static void showAffairNotification(int type, int status,
			boolean isModify, String taskID, String sponsorName, String title,
			Context context, Intent mIntent) {
		String notificationTitle = "";
		String notificationText = "";
		mIntent.putExtra("type", type);
		mIntent.putExtra("status", status);
		mIntent.putExtra("id", taskID);
		if (isModify) {
			notificationTitle = "�����ֹʱ�����޸�";
			notificationText = "����" + sponsorName + "������:" + title + " ��ֹʱ�����޸�";
		} else if (status == 1) {
			notificationTitle = "�յ�������";
			notificationText = "����" + sponsorName + "��������:" + title;
		} else if (status == 2) {
			notificationTitle = "���������";
			notificationText = "����" + sponsorName + "������:" + title + " �����";
		} else if (status == 3) {
			notificationTitle = "����������";
			notificationText = "����" + sponsorName + "������:" + title + " ������";
		}
		int notificationDrawable = R.drawable.notify_icon;
		// ��ʾ֪ͨ
		showNotification(mIntent, notificationTitle, notificationText,
				notificationDrawable, AFFAIR_NOTIFICATION_ID, context);
	}

	/**
	 * ��ʾ����Ϣ֪ͨ
	 * 
	 * @param talkerName
	 * @param talkerID
	 * @param context
	 */
	public static void showMessageNotification(String talkerName,
			String talkerID, Context context, Intent mIntent) {
		String notificationText = "���� " + talkerName + " ����Ϣ";

		mIntent.putExtra("entrance_type", 1);
		mIntent.putExtra("selected_name", talkerName);
		mIntent.putExtra("selected_id", Integer.parseInt(talkerID));
		int notificationDrawable = R.drawable.notify_icon;
		// ��ʾ֪ͨ
		showNotification(mIntent, "�յ�����Ϣ", notificationText,
				notificationDrawable, MESSAGE_NOTIFICATION_ID, context);
	}

	/**
	 * ��ʾ�·���֪ͨ
	 * 
	 * @param talkerName
	 * @param talkerID
	 * @param context
	 * @param mIntent
	 */
	public static void showFeedbackNotification(String talkerName,
			String taskID, Context context, Intent mIntent) {
		String notificationText = "���� " + talkerName + " �ķ���";

		mIntent.putExtra("entrance_type", 2);
		mIntent.putExtra("task_id", taskID);
		int notificationDrawable = R.drawable.notify_icon;
		// ��ʾ֪ͨ
		showNotification(mIntent, "�յ��·���", notificationText,
				notificationDrawable, FEEDBACK_NOTIFICATION_ID, context);
	}

	/**
	 * ��֪ͨ����ʾ֪ͨ
	 * 
	 * @param intent
	 *            ���֪ͨ����ת��Intent
	 * @param text
	 *            ֪ͨ����
	 * @param drawable
	 *            ֪ͨͼ��
	 * @param id
	 *            ֪ͨID
	 * @param context
	 */

	private static int requestCode = 0;

	private static void showNotification(Intent intent, String title,
			String text, int drawable, int id, Context context) {
		// ���֪ͨ���������Ӧ����
		NotificationManager mNotificationManager = (NotificationManager) context
				.getSystemService(android.content.Context.NOTIFICATION_SERVICE);
		Notification mNotification = new Notification(drawable, title,
				System.currentTimeMillis());
		mNotification.defaults = Notification.DEFAULT_ALL;
		mNotification.flags |= Notification.FLAG_AUTO_CANCEL;
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
				| Intent.FLAG_ACTIVITY_NEW_TASK);
		PendingIntent mPendingIntent = PendingIntent.getActivity(context,
				requestCode++,
				intent, 0);
		mNotification.setLatestEventInfo(context, title, text, mPendingIntent);
		mNotificationManager.notify(id, mNotification);
	}

	/**
	 * ����֪ͨ
	 * 
	 * @param context
	 */
	public static void cancelNotification(int id, Context context) {
		NotificationManager mNotificationManager = (NotificationManager) context
				.getSystemService(android.content.Context.NOTIFICATION_SERVICE);
		mNotificationManager.cancel(id);
	}
}
