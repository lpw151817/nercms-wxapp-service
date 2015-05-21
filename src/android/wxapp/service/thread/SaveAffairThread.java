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

	// ��Ϣ�Ƿ��ɱ�������
	private boolean isSend = true; // Ĭ�� ��

	private ArrayList<AffairModel> affairList;

	// ��������ʱ�Ĺ��캯��
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

	// // ���������ȡ��������ʱ�Ĺ��캯��
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

				Log.v("SaveAffairThread", "����ǵ�һ������");

				AffairModel affair;
				AffairDao dao = DAOFactory.getInstance().getAffairDao(context);

				while (affairList.size() != 0) {

					affair = affairList.get(0);

					if (affair.getLastOperateType() == 1) { // ���һ�β���Ϊ�½�����

						// ����������
						// 2014-6-26 WeiHao
						// ����
						if (affair.save(context)) {
							// ��������������
							affair.getPerson().save(context);

							// ���渽��
							if (affair.getAttachments() != null) {
								for (AffairAttachModel attach : affair.getAttachments()) {
									attach.save(context);
								}
							}

						}

					} else if (affair.getLastOperateType() == 2) { // ���һ�β���Ϊ���������
						// ������Ӧ�ı������ݿ�
						dao.updateAffairCompleted(affair.getAffairID(), affair.getCompleteTime());

					} else if (affair.getLastOperateType() == 4) { // ���һ�β���Ϊ�޸Ľ���ʱ��
						dao.updateAffairEndTime(affair.getAffairID(), affair.getEndTime(),
								affair.getLastOperateTime());

					} else if (affair.getLastOperateType() == 3) { // ���һ�β���Ϊ�������Զ���
						dao.updateAffairTimeout(affair.getAffairID(), affair.getLastOperateTime());
					}

					// ֪ͨUI�߳�ˢ�½���
					MessageHandlerManager.getInstance().sendMessage(Constant.SAVE_TASK_SUCCESS, "Task");

					// ����Ǳ������ͣ����յ��ģ�����ʾNotification֪ͨ
					if (!isSend) {
						// MyNotification.showAffairNotification(context);
						MessageHandlerManager.getInstance().sendMessage(Constant.SHOW_TASK_NOTIFICATION,
								affair, "Main");
					}

					affairList.remove(0);

				}

				// Ĭ��Ϊ�������ͣ�����ʾ֪ͨ
				this.setIsSend(true);

			} else { // ����Ϊ��һ�����л��߱�������ݣ����е�����ֱ�ӱ��棬�޸��£�����ʾ
				Log.v("SaveAffairThread", "����Ϊ��һ�����л��߱��������");
				AffairModel affair;
				while (affairList.size() != 0) {
					affair = affairList.get(0);

					// ��������������
					affair.getPerson().save(context);

					// ���渽��
					if (affair.getAttachments() != null) {
						for (AffairAttachModel attach : affair.getAttachments()) {
							attach.save(context);
						}
					}
					// ����������
					affair.save(context);

					affairList.remove(0);
				}
				MessageHandlerManager.getInstance().sendMessage(Constant.SAVE_TASK_SUCCESS, "Task");
				// �����״����б�־Ϊ����
				MySharedPreference.save(context, MySharedPreference.IS_FIRST_RUN, false);
			}

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
