package android.wxapp.service.thread;

import java.util.ArrayList;

import org.json.JSONObject;

import android.content.Context;
import android.util.Log;
import android.wxapp.service.model.AffairModel;
import android.wxapp.service.model.FeedbackModel;
import android.wxapp.service.model.MessageModel;

/**
 * 线程管理器
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
	 * 开启保存事务线程
	 * 
	 * @param affair
	 */
	public void startSaveAffairThread(ArrayList<AffairModel> affairList,
			boolean isSend, Context context) {

		if (saveAffairThread == null) {
			// 新建线程
			Log.v("ThreadManager", "新建保存事务线程");
			saveAffairThread = new SaveAffairThread(context, affairList);
			saveAffairThread.setIsSend(isSend);
			saveAffairThread.start();
		} else {
			synchronized (saveAffairThread) {
				// 唤醒线程
				Log.v("ThreadManager", "唤醒保存事务线程");
				saveAffairThread.addAffair(affairList);
				saveAffairThread.setIsSend(isSend);
				saveAffairThread.notify();
			}
		}
	}

	/**
	 * 开启保存反馈线程
	 * 
	 * @param feedbackList
	 * @param isSend
	 * @param context
	 */
	public void startSaveFeedbackThread(ArrayList<FeedbackModel> feedbackList,
			boolean isSend, Context context) {
		if (saveFeedbackThread == null) {
			Log.v("ThreadManager", "新建保存反馈线程");
			saveFeedbackThread = new SaveFeedbackThread(context, feedbackList);
			saveFeedbackThread.setIsSend(isSend);
			saveFeedbackThread.start();
		} else {
			synchronized (saveFeedbackThread) {
				Log.v("ThreadManager", "唤醒保存反馈线程");
				saveFeedbackThread.addFeedback(feedbackList);
				saveFeedbackThread.setIsSend(isSend);
				saveFeedbackThread.notify();
			}
		}
	}

	/**
	 * 开启保存消息线程
	 * 
	 * @param messageList
	 * @param isSend
	 * @param context
	 */
	public void startSaveMessageThread(ArrayList<MessageModel> messageList,
			boolean isSend, Context context) {
		if (saveMessageThread == null) {
			Log.v("ThreadManager", "新建保存消息线程");
			saveMessageThread = new SaveMessageThread(context, messageList);
			saveMessageThread.setIsSend(isSend);
			saveMessageThread.start();
		} else {
			synchronized (saveMessageThread) {
				Log.v("ThreadManager", "唤醒保存消息线程");
				saveMessageThread.addMessage(messageList);
				saveMessageThread.setIsSend(isSend);
				saveMessageThread.notify();
			}
		}
	}

	/**
	 * 开启保存所有联系人线程 2014-5-9 改为临时线程
	 * 
	 * @param resultJsonObject
	 * @param context
	 */
	public void startSaveAllPersonThread(JSONObject resultJsonObject,
			Context context) {

		SaveAllPersonThread saveAllPersonThread = new SaveAllPersonThread(
				resultJsonObject, context);
		saveAllPersonThread.start();
	}

	/**
	 * 停止消息保存线程
	 */
	public void stopSaveMessageThread() {
		saveMessageThread.setFlag(false);
		synchronized (saveMessageThread) {
			saveMessageThread.notify();
		}
		try {
			saveMessageThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		saveMessageThread = null;
	}

	/**
	 * 停止反馈保存线程
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
	 * 停止事务保存线程
	 */
	public void stopSaveAffairThread() {
		saveAffairThread.setFlag(false);
		synchronized (saveAffairThread) {
			saveAffairThread.notify();
		}
		try {
			saveAffairThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		saveAffairThread = null;
	}

	// ------------------以下为测试方法------------------------------------

	public void startTestThread() {
		if (testThread == null) {
			Log.v("ThreadManager", "新建test线程");
			testThread = new TestThread();
			testThread.start();
		} else {
			synchronized (testThread) {
				Log.v("ThreadManager", "唤醒test线程");
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
