package android.wxapp.service.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.wxapp.service.R;

/**
 * 通知栏显示工具类 说明：Intent 第二个参数，根据需要自行修改成对应的Activity.class
 * 
 * @author WEIHAO
 * @version 2014-6-25 修改具体实现
 * 
 */
public class MyNotification {
	public static final int AFFAIR_NOTIFICATION_ID = 123321;
	public static final int FEEDBACK_NOTIFICATION_ID = 123322;
	public static final int MESSAGE_NOTIFICATION_ID = 123323;

	/**
	 * 显示新事务通知
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
			notificationTitle = "任务截止时间已修改";
			notificationText = "来自" + sponsorName + "的任务:" + title + " 截止时间已修改";
		} else if (status == 1) {
			notificationTitle = "收到新任务";
			notificationText = "来自" + sponsorName + "的新任务:" + title;
		} else if (status == 2) {
			notificationTitle = "任务已完成";
			notificationText = "来自" + sponsorName + "的任务:" + title + " 已完成";
		} else if (status == 3) {
			notificationTitle = "任务已延误";
			notificationText = "来自" + sponsorName + "的任务:" + title + " 已延误";
		}
		int notificationDrawable = R.drawable.notify_icon;
		// 显示通知
		showNotification(mIntent, notificationTitle, notificationText,
				notificationDrawable, AFFAIR_NOTIFICATION_ID, context);
	}

	/**
	 * 显示新消息通知
	 * 
	 * @param talkerName
	 * @param talkerID
	 * @param context
	 */
	public static void showMessageNotification(String talkerName,
			String talkerID, Context context, Intent mIntent) {
		String notificationText = "来自 " + talkerName + " 的消息";

		mIntent.putExtra("entrance_type", 1);
		mIntent.putExtra("selected_name", talkerName);
		mIntent.putExtra("selected_id", Integer.parseInt(talkerID));
		int notificationDrawable = R.drawable.notify_icon;
		// 显示通知
		showNotification(mIntent, "收到新消息", notificationText,
				notificationDrawable, MESSAGE_NOTIFICATION_ID, context);
	}

	/**
	 * 显示新反馈通知
	 * 
	 * @param talkerName
	 * @param talkerID
	 * @param context
	 * @param mIntent
	 */
	public static void showFeedbackNotification(String talkerName,
			String taskID, Context context, Intent mIntent) {
		String notificationText = "来自 " + talkerName + " 的反馈";

		mIntent.putExtra("entrance_type", 2);
		mIntent.putExtra("task_id", taskID);
		int notificationDrawable = R.drawable.notify_icon;
		// 显示通知
		showNotification(mIntent, "收到新反馈", notificationText,
				notificationDrawable, FEEDBACK_NOTIFICATION_ID, context);
	}

	/**
	 * 在通知栏显示通知
	 * 
	 * @param intent
	 *            点击通知后跳转的Intent
	 * @param text
	 *            通知文字
	 * @param drawable
	 *            通知图标
	 * @param id
	 *            通知ID
	 * @param context
	 */

	private static int requestCode = 0;

	private static void showNotification(Intent intent, String title,
			String text, int drawable, int id, Context context) {
		// 点击通知栏后进入相应界面
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
	 * 擦除通知
	 * 
	 * @param context
	 */
	public static void cancelNotification(int id, Context context) {
		NotificationManager mNotificationManager = (NotificationManager) context
				.getSystemService(android.content.Context.NOTIFICATION_SERVICE);
		mNotificationManager.cancel(id);
	}
}
