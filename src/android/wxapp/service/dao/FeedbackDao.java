package android.wxapp.service.dao;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.wxapp.service.model.FeedbackModel;

public class FeedbackDao extends BaseDAO {

	private static String TAG = "FeedbackDao";

	public FeedbackDao(Context context) {
		super(context);
	}

	/**
	 * ���淴�������ݿ�
	 * 
	 * @param feedback
	 * @return
	 */
	public boolean saveFeedback(FeedbackModel feedback) {

		return false;
	}

	/**
	 * ���ݷ���ID��ȡ�÷���
	 * 
	 * @param FeedbackID
	 * @return
	 */
	public FeedbackModel getFeedbackByID(String FeedbackID) {
		return null;
	}

	/**
	 * ��������ID��ȡ��Ӧ�ķ����б�
	 * 
	 * @param affairID
	 * @return �����б�
	 */
	public ArrayList<FeedbackModel> getFeedbackByAffairID(String affairID) {
		return null;
	}

}
