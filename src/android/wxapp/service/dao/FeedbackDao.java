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
		// Cursor cursor = null;
		// SQLiteDatabase db = dbhHelper.getWritableDatabase();
		// try{
		// cursor = db
		// .rawQuery(
		// "SELECT COUNT(*) FROM affair_feedback WHERE feedback_id = ?",
		// new String[] { feedback.getFeedbackID() });
		// cursor.moveToFirst();
		// if (cursor.getInt(0) == 0) { // �÷��������ڣ�����֮
		// ContentValues values = createContentValues(feedback);
		// long id = db.insertOrThrow(DBConstants.FEEDBACK_TABLE_NAME,
		// null, values);
		// if (-1 == id) {
		// Log.i(TAG, "��������ʧ��!");
		// return false;
		// } else {
		// Log.i(TAG, "���������ɹ�!");
		// return true;
		// }
		// } else { // �÷����Ѵ��ڣ�������
		// Log.i(TAG, "�������ظ�����");
		// return false;
		// }
		// } finally {
		// if (cursor != null)
		// dbhHelper.closeCursor(cursor);
		// }
		return false;
	}

	/**
	 * ���ݷ���ID��ȡ�÷���
	 * 
	 * @param FeedbackID
	 * @return
	 */
	public FeedbackModel getFeedbackByID(String FeedbackID) {
		// Cursor cursor=null;
		// try {
		// SQLiteDatabase db = dbhHelper.getReadableDatabase();
		// cursor = db.query(DBConstants.FEEDBACK_TABLE_NAME,
		// DBConstants.AFFAIR_FEEDBACK_ALL_COLUMNS, "feedback_id = ?",
		// new String[] { FeedbackID }, null, null, null);
		// if (cursor.moveToFirst()) {
		// return createFeedbackFromCursor(cursor);
		// }else {
		// return null;
		// }
		// } finally{
		// if(cursor != null)
		// dbhHelper.closeCursor(cursor);
		// }
		return null;
	}

	/**
	 * ��������ID��ȡ��Ӧ�ķ����б�
	 * 
	 * @param affairID
	 * @return �����б�
	 */
	public ArrayList<FeedbackModel> getFeedbackByAffairID(String affairID) {
		// ArrayList<FeedbackModel> feedbacks = new ArrayList<FeedbackModel>();
		// Cursor cursor = null;
		// try {
		// SQLiteDatabase db=dbhHelper.getReadableDatabase();
		// cursor = db.query(DBConstants.FEEDBACK_TABLE_NAME,
		// DBConstants.AFFAIR_FEEDBACK_ALL_COLUMNS, "affair_id = ?",
		// new String[] { affairID }, null, null, null);
		//
		// while (cursor.moveToNext()) {
		// feedbacks.add(createFeedbackFromCursor(cursor));
		// }
		// } finally {
		// if (cursor != null)
		// dbhHelper.closeCursor(cursor);
		// }
		// return feedbacks;
		return null;
	}

	// -------------------------------------------------------------------

	// private ContentValues createContentValues(FeedbackModel feedback) {
	// ContentValues values = new ContentValues();
	// values.put("feedback_id", feedback.getFeedbackID());
	// values.put("affair_id", feedback.getAffairID());
	// values.put("person_id", feedback.getPersonID());
	// values.put("feedback_time", feedback.getFeedbackTime());
	// values.put("content", feedback.getContent());
	// values.put("is_read", feedback.getIsRead());
	// return values;
	// }
	//
	// private FeedbackModel createFeedbackFromCursor(Cursor cursor) {
	// String feedbackID = cursor.getString(0);
	// String affairID = cursor.getString(1);
	// int personID = cursor.getInt(2);
	// String feedbackTime = cursor.getString(3);
	// String content = cursor.getString(4);
	// int isRead = cursor.getInt(5);
	// return new FeedbackModel(feedbackID, affairID, personID, feedbackTime,
	// content, isRead);
	// }

}
