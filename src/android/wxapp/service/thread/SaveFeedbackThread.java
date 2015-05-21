package android.wxapp.service.thread;

import java.util.ArrayList;

import android.content.Context;
import android.wxapp.service.handler.MessageHandlerManager;
import android.wxapp.service.model.FeedbackModel;
import android.wxapp.service.util.Constant;

public class SaveFeedbackThread extends Thread {

	private Context context = null;
	private boolean flag = true;

	// 消息是否由本机发送
	private boolean isSend = true; // 默认 是

	private ArrayList<FeedbackModel> feedbackList;

	public SaveFeedbackThread(Context context,
			ArrayList<FeedbackModel> feedbackList) {
		this.context = context;
		this.feedbackList = new ArrayList<FeedbackModel>();
		this.feedbackList.addAll(feedbackList);
	}

	public void addFeedback(ArrayList<FeedbackModel> feedbacks) {
		this.feedbackList.addAll(feedbacks);
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public void setIsSend(boolean isSend) {
		this.isSend = isSend;
	}

	@Override
	public void run() {
		while (flag) {
			FeedbackModel feedback;
			while (feedbackList.size() != 0) {
				feedback = feedbackList.get(0);

				// 保存反馈附件
				if (feedback.getAttachment() != null) {
					feedback.getAttachment().save(context);
				}

				// 保存反馈
				if (feedback.save(context)) {
					// 如果非本机发送（接收到的），显示Notification通知
					if (!isSend) {
						MessageHandlerManager.getInstance().sendMessage(
								Constant.SHOW_FEEDBACK_NOTIFICATION, feedback,
								"Main");
					}

					// 通知UI线程刷新界面
					MessageHandlerManager.getInstance().sendMessage(
							Constant.SAVE_FEEDBACK_SUCCESS, "Main");
					MessageHandlerManager.getInstance().sendMessage(
							Constant.SAVE_FEEDBACK_SUCCESS, feedback,
							"ChatDetail");
				}
				feedbackList.remove(0);

			}

			// 默认为本机发送，不显示通知
			this.setIsSend(true);

			// 写数据完毕后等待
			synchronized (this) {
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
