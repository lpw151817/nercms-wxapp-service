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
	 * 保存反馈到数据库
	 * 
	 * @param feedback
	 * @return
	 */
	public boolean saveFeedback(FeedbackModel feedback) {

		return false;
	}

	/**
	 * 根据反馈ID获取该反馈
	 * 
	 * @param FeedbackID
	 * @return
	 */
	public FeedbackModel getFeedbackByID(String FeedbackID) {
		return null;
	}

	/**
	 * 根据事务ID获取对应的反馈列表
	 * 
	 * @param affairID
	 * @return 反馈列表
	 */
	public ArrayList<FeedbackModel> getFeedbackByAffairID(String affairID) {
		return null;
	}

}
