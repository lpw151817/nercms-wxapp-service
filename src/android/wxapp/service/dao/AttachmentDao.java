package android.wxapp.service.dao;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.wxapp.service.jerry.model.affair.CreateTaskRequestAttachment;
import android.wxapp.service.model.AffairAttachModel;
import android.wxapp.service.model.FeedbackAttachModel;

public class AttachmentDao extends BaseDAO {

	private static String TAG = "AttachmentDAO";

	public AttachmentDao(Context context) {
		super(context);
	}

	/**
	 * �������񸽼������ݿ�
	 * 
	 * @param attachment
	 * @return
	 */
	public boolean saveAttachment(CreateTaskRequestAttachment attachment) {
		db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(DatabaseHelper.FIELD_ATTACHMENT_TYPE, attachment.getAt());
		values.put(DatabaseHelper.FIELD_ATTACHMENT_URL, attachment.getU());
		long i = db.insert(DatabaseHelper.TABLE_ATTACHMENT, null, values);
		return i > 0;
	}

	/**
	 * ���淴������������
	 * 
	 * @param attachment
	 * @return
	 */
	public boolean saveAttachment(FeedbackAttachModel attachment) {
		// Cursor cursor = null;
		// SQLiteDatabase db = dbHelper.getWritableDatabase();
		//
		// try{
		// cursor = db
		// .rawQuery(
		// "SELECT COUNT(*) FROM feedback_attachment WHERE feedback_id = ?",
		// new String[] { attachment.getFeedbackID() });
		// cursor.moveToFirst();
		// if (cursor.getInt(0) == 0) { // �÷��������ڣ�����֮
		// ContentValues values = createContentValues(attachment);
		// long id =
		// db.insertOrThrow(DBConstants.FEEDBACK_ATTACHMENT_TABLE_NAME,
		// null, values);
		// if (id == -1) {
		// Log.i(TAG, "��ӷ�������ʧ��!");
		// return false;
		// } else {
		// Log.i(TAG, "��ӷ��������ɹ�!");
		// return true;
		// }
		// } else { // �÷��������Ѵ��ڣ�������
		// Log.i(TAG, "�������ظ���������");
		// return false;
		// }
		// } finally {
		// if (cursor != null)
		// dbHelper.closeCursor(cursor);
		// }
		return false;

	}

	/**
	 * ��������ID����ȡ������ĸ����б�
	 * 
	 * @param affairID
	 * @return �����б�
	 */
	public ArrayList<AffairAttachModel> getAttachByAffairID(String affairID) {
		// ArrayList<AffairAttachModel> attchments = new
		// ArrayList<AffairAttachModel>();
		// Cursor cursor = null;
		// try {
		// SQLiteDatabase db = dbHelper.getReadableDatabase();
		// cursor = db.query(DBConstants.AFFAIR_ATTACHMENT_TABLE_NAME,
		// DBConstants.AFFAIR_ATTACHMENT_ALL_COLUMNS, "affair_id = ?",
		// new String[] { affairID }, null, null, null);
		//
		// while (cursor.moveToNext()) {
		// attchments.add(createAffairAttachFromCursor(cursor));
		// }
		// } finally {
		// if (cursor != null) {
		// dbHelper.closeCursor(cursor);
		// }
		// }
		// return attchments;
		return null;
	}

	/**
	 * ���ݷ���ID����ȡ�÷����ĸ����б�
	 * 
	 * @param feedbackID
	 * @return �����б�
	 */
	public ArrayList<FeedbackAttachModel> getAttachByFeedbackID(String feedbackID) {
		// ArrayList<FeedbackAttachModel> attchments = new
		// ArrayList<FeedbackAttachModel>();
		// Cursor cursor = null;
		// try {
		// SQLiteDatabase db = dbHelper.getReadableDatabase();
		// cursor = db.query(DBConstants.FEEDBACK_ATTACHMENT_TABLE_NAME,
		// DBConstants.FEEDBACK_ATTACHMENT_ALL_COLUMNS,
		// "feedback_id = ?", new String[] { feedbackID }, null, null,
		// null);
		//
		// while (cursor.moveToNext()) {
		// attchments.add(createFeedbackAttachFromCursor(cursor));
		// }
		// } finally {
		// if (cursor != null) {
		// dbHelper.closeCursor(cursor);
		// }
		// }
		// return attchments;
		return null;
	}

	// --------------------------------------------------------------------------------

	// private ContentValues createContentValues(AffairAttachModel attachment) {
	// ContentValues values = new ContentValues();
	// values.put("affair_id", attachment.getAffairID());
	// values.put("attachment_type", attachment.getAttachmentType());
	// values.put("url", attachment.getAttachmentURL());
	// return values;
	// }
	//
	// private ContentValues createContentValues(FeedbackAttachModel attachment)
	// {
	// ContentValues values = new ContentValues();
	// values.put("feedback_id", attachment.getFeedbackID());
	// values.put("attachment_type", attachment.getAttachmentType());
	// values.put("url", attachment.getAttachmentURL());
	// return values;
	// }
	//
	// private AffairAttachModel createAffairAttachFromCursor(Cursor cursor) {
	// String affairID = cursor.getString(0);
	// int attachmentType = cursor.getInt(1);
	// String attachmentURL = cursor.getString(2);
	// return new AffairAttachModel(affairID, attachmentType, attachmentURL);
	// }
	//
	// private FeedbackAttachModel createFeedbackAttachFromCursor(Cursor cursor)
	// {
	// String feedbackID = cursor.getString(0);
	// int attachmentType = cursor.getInt(1);
	// String attachmentURL = cursor.getString(2);
	// return new FeedbackAttachModel(feedbackID, attachmentType,
	// attachmentURL);
	// }

}
