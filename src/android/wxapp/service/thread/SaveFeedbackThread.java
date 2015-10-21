package android.wxapp.service.thread;

import java.util.ArrayList;

import android.content.Context;
import android.wxapp.service.handler.MessageHandlerManager;
import android.wxapp.service.model.FeedbackModel;
import android.wxapp.service.util.Constant;

public class SaveFeedbackThread extends Thread {

	private Context context = null;
	private boolean flag = true;

	// ��Ϣ�Ƿ��ɱ�������
	private boolean isSend = true; // Ĭ�� ��

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

				// ���淴������
				if (feedback.getAttachment() != null) {
					feedback.getAttachment().save(context);
				}

				// ���淴��
				if (feedback.save(context)) {
					// ����Ǳ������ͣ����յ��ģ�����ʾNotification֪ͨ
					if (!isSend) {
						MessageHandlerManager.getInstance().sendMessage(
								Constant.SHOW_FEEDBACK_NOTIFICATION, feedback,
								"Main");
					}

					// ֪ͨUI�߳�ˢ�½���
					MessageHandlerManager.getInstance().sendMessage(
							Constant.SAVE_FEEDBACK_SUCCESS, "Main");
					MessageHandlerManager.getInstance().sendMessage(
							Constant.SAVE_FEEDBACK_SUCCESS, feedback,
							"ChatDetail");
				}
				feedbackList.remove(0);

			}

			// Ĭ��Ϊ�������ͣ�����ʾ֪ͨ
			this.setIsSend(true);

			// д������Ϻ�ȴ�
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
