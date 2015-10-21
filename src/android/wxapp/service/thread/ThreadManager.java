package android.wxapp.service.thread;

import java.util.ArrayList;

import org.json.JSONObject;

import android.content.Context;
import android.util.Log;
import android.wxapp.service.model.AffairModel;
import android.wxapp.service.model.FeedbackModel;
import android.wxapp.service.model.MessageModel;

/**
 * �̹߳�����
 * 
 * @author WEIHAO
 * 
 */
public class ThreadManager {

	private static ThreadManager instance = null;

	private static SaveAffairThread saveAffairThread;

	private static SaveFeedbackThread saveFeedbackThread;

	private static SaveMessageThread saveMessageThread;

	private static TestThread testThread;

	public ThreadManager() {
	}

	public static ThreadManager getInstance() {
		if (instance == null) {
			synchronized (ThreadManager.class) {
				// check twice
				if (instance == null) {
					instance = new ThreadManager();
				}
			}
		}
		return instance;
	}

	/**
	 * �������������߳�
	 * 
	 * @param affair
	 */
	public void startSaveAffairThread(ArrayList<AffairModel> affairList, boolean isSend, Context context) {

		// if (saveAffairThread == null) {
		// // �½��߳�
		// Log.v("ThreadManager", "�½����������߳�");
		// saveAffairThread = new SaveAffairThread(context, affairList);
		// saveAffairThread.setIsSend(isSend);
		// saveAffairThread.start();
		// } else {
		// synchronized (saveAffairThread) {
		// // �����߳�
		// Log.v("ThreadManager", "���ѱ��������߳�");
		// saveAffairThread.addAffair(affairList);
		// saveAffairThread.setIsSend(isSend);
		// saveAffairThread.notify();
		// }
		// }
	}

	/**
	 * �������淴���߳�
	 * 
	 * @param feedbackList
	 * @param isSend
	 * @param context
	 */
	public void startSaveFeedbackThread(ArrayList<FeedbackModel> feedbackList, boolean isSend,
			Context context) {
		if (saveFeedbackThread == null) {
			Log.v("ThreadManager", "�½����淴���߳�");
			saveFeedbackThread = new SaveFeedbackThread(context, feedbackList);
			saveFeedbackThread.setIsSend(isSend);
			saveFeedbackThread.start();
		} else {
			synchronized (saveFeedbackThread) {
				Log.v("ThreadManager", "���ѱ��淴���߳�");
				saveFeedbackThread.addFeedback(feedbackList);
				saveFeedbackThread.setIsSend(isSend);
				saveFeedbackThread.notify();
			}
		}
	}

	/**
	 * ����������Ϣ�߳�
	 * 
	 * @param messageList
	 * @param isSend
	 * @param context
	 */
	public void startSaveMessageThread(ArrayList<MessageModel> messageList, boolean isSend,
			Context context) {
//		if (saveMessageThread == null) {
//			Log.v("ThreadManager", "�½�������Ϣ�߳�");
//			saveMessageThread = new SaveMessageThread(context, messageList);
//			saveMessageThread.setIsSend(isSend);
//			saveMessageThread.start();
//		} else {
//			synchronized (saveMessageThread) {
//				Log.v("ThreadManager", "���ѱ�����Ϣ�߳�");
//				saveMessageThread.addMessage(messageList);
//				saveMessageThread.setIsSend(isSend);
//				saveMessageThread.notify();
//			}
//		}
	}

	/**
	 * ��������������ϵ���߳� 2014-5-9 ��Ϊ��ʱ�߳�
	 * 
	 * @param resultJsonObject
	 * @param context
	 */
	public void startSaveAllPersonThread(JSONObject resultJsonObject, Context context) {

		SaveAllPersonThread saveAllPersonThread = new SaveAllPersonThread(resultJsonObject, context);
		saveAllPersonThread.start();
	}

	/**
	 * ֹͣ��Ϣ�����߳�
	 */
	public void stopSaveMessageThread() {
		// saveMessageThread.setFlag(false);
		// synchronized (saveMessageThread) {
		// saveMessageThread.notify();
		// }
		// try {
		// saveMessageThread.join();
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }
		// saveMessageThread = null;
	}

	/**
	 * ֹͣ���������߳�
	 */
	public void stopSaveFeedbackThread() {
		saveFeedbackThread.setFlag(false);
		synchronized (saveFeedbackThread) {
			saveFeedbackThread.notify();
		}
		try {
			saveFeedbackThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		saveFeedbackThread = null;
	}

	/**
	 * ֹͣ���񱣴��߳�
	 */
	public void stopSaveAffairThread() {
		// saveAffairThread.setFlag(false);
		// synchronized (saveAffairThread) {
		// saveAffairThread.notify();
		// }
		// try {
		// saveAffairThread.join();
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }
		// saveAffairThread = null;
	}

	// ------------------����Ϊ���Է���------------------------------------

	public void startTestThread() {
		if (testThread == null) {
			Log.v("ThreadManager", "�½�test�߳�");
			testThread = new TestThread();
			testThread.start();
		} else {
			synchronized (testThread) {
				Log.v("ThreadManager", "����test�߳�");
				testThread.notify();
			}
		}
	}

	public void stopTestThread() {
		testThread.setFlag(false);
		synchronized (testThread) {
			testThread.notify();
		}
		try {
			testThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		testThread = null;

	}

}
