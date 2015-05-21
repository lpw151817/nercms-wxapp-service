package android.wxapp.service.thread;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.wxapp.service.dao.AffairDao;
import android.wxapp.service.dao.DAOFactory;
import android.wxapp.service.handler.MessageHandlerManager;
import android.wxapp.service.model.AffairAttachModel;
import android.wxapp.service.model.AffairModel;
import android.wxapp.service.util.Constant;
import android.wxapp.service.util.MySharedPreference;

public class SaveAffairThread extends Thread {

	private Context context = null;
	private boolean flag = true;
	private boolean isFirstRun;

	// 消息是否由本机发送
	private boolean isSend = true; // 默认 是

	private ArrayList<AffairModel> affairList;

	// 保存事务时的构造函数
	public SaveAffairThread(Context context, ArrayList<AffairModel> affairList) {
		this.context = context;
		this.affairList = new ArrayList<AffairModel>();
		this.affairList.addAll(affairList);
	}

	public void addAffair(ArrayList<AffairModel> affairs) {
		this.affairList.addAll(affairs);
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public void setIsSend(boolean isSend) {
		this.isSend = isSend;
	}

	// // 保存网络获取的新事务时的构造函数
	// public SaveAffairThread(Context context, JSONObject resultJsonObject) {
	// this.context = context;
	// this.resultJsonObject = resultJsonObject;
	// this.flag = 2;
	// }

	@Override
	public void run() {
		while (flag) {
			this.isFirstRun = MySharedPreference.get(context, MySharedPreference.IS_FIRST_RUN, true);
			if (!isFirstRun) {

				Log.v("SaveAffairThread", "程序非第一次运行");

				AffairModel affair;
				AffairDao dao = DAOFactory.getInstance().getAffairDao(context);

				while (affairList.size() != 0) {

					affair = affairList.get(0);

					if (affair.getLastOperateType() == 1) { // 最后一次操作为新建事务

						// 保存事务本身
						// 2014-6-26 WeiHao
						// 事务
						if (affair.save(context)) {
							// 保存事务责任人
							affair.getPerson().save(context);

							// 保存附件
							if (affair.getAttachments() != null) {
								for (AffairAttachModel attach : affair.getAttachments()) {
									attach.save(context);
								}
							}

						}

					} else if (affair.getLastOperateType() == 2) { // 最后一次操作为置完成事务
						// 更新相应的本地数据库
						dao.updateAffairCompleted(affair.getAffairID(), affair.getCompleteTime());

					} else if (affair.getLastOperateType() == 4) { // 最后一次操作为修改结束时间
						dao.updateAffairEndTime(affair.getAffairID(), affair.getEndTime(),
								affair.getLastOperateTime());

					} else if (affair.getLastOperateType() == 3) { // 最后一次操作为置延误（自动）
						dao.updateAffairTimeout(affair.getAffairID(), affair.getLastOperateTime());
					}

					// 通知UI线程刷新界面
					MessageHandlerManager.getInstance().sendMessage(Constant.SAVE_TASK_SUCCESS, "Task");

					// 如果非本机发送（接收到的），显示Notification通知
					if (!isSend) {
						// MyNotification.showAffairNotification(context);
						MessageHandlerManager.getInstance().sendMessage(Constant.SHOW_TASK_NOTIFICATION,
								affair, "Main");
					}

					affairList.remove(0);

				}

				// 默认为本机发送，不显示通知
				this.setIsSend(true);

			} else { // 程序为第一次运行或者被清空数据，所有的任务直接保存，无更新，无提示
				Log.v("SaveAffairThread", "程序为第一次运行或者被清空数据");
				AffairModel affair;
				while (affairList.size() != 0) {
					affair = affairList.get(0);

					// 保存事务责任人
					affair.getPerson().save(context);

					// 保存附件
					if (affair.getAttachments() != null) {
						for (AffairAttachModel attach : affair.getAttachments()) {
							attach.save(context);
						}
					}
					// 保存事务本身
					affair.save(context);

					affairList.remove(0);
				}
				MessageHandlerManager.getInstance().sendMessage(Constant.SAVE_TASK_SUCCESS, "Task");
				// 更新首次运行标志为“否”
				MySharedPreference.save(context, MySharedPreference.IS_FIRST_RUN, false);
			}

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
