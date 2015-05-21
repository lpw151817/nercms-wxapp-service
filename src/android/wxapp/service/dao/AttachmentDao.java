package android.wxapp.service.dao;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.wxapp.service.model.AffairAttachModel;
import android.wxapp.service.model.FeedbackAttachModel;

public class AttachmentDao {

	private DatabaseHelper dbHelper;

	private static String TAG = "AttachmentDAO";

	public AttachmentDao(Context context) {
		dbHelper = DatabaseHelper.getInstance(context);
	}

	/**
	 * 保存事务附件到数据库
	 * 
	 * @param attachment
	 * @return
	 */
	public boolean saveAttachment(AffairAttachModel attachment){
		ContentValues values = createContentValues(attachment);
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		long id = db.insertOrThrow(DBConstants.AFFAIR_ATTACHMENT_TABLE_NAME,
				null, values);
		if (id == -1) {
			Log.i(TAG, "添加任务附件失败!");
			return false;
		} else {
			Log.i(TAG, "添加任务附件成功!");
			return true;
		}
	}

	/**
	 * 保存反馈附件到数据
	 * 
	 * @param attachment
	 * @return
	 */
	public boolean saveAttachment(FeedbackAttachModel attachment) {
		Cursor cursor = null;
		SQLiteDatabase db = dbHelper.getWritableDatabase();

		try{
			cursor = db
					.rawQuery(
							"SELECT COUNT(*) FROM feedback_attachment WHERE feedback_id = ?",
							new String[] { attachment.getFeedbackID() });
			cursor.moveToFirst();
			if (cursor.getInt(0) == 0) { // 该反馈不存在，保存之
				ContentValues values = createContentValues(attachment);
				long id = db.insertOrThrow(DBConstants.FEEDBACK_ATTACHMENT_TABLE_NAME,
						null, values);
				if (id == -1) {
					Log.i(TAG, "添加反馈附件失败!");
					return false;
				} else {
					Log.i(TAG, "添加反馈附件成功!");
					return true;
				}
			} else { // 该反馈附件已存在，不保存
				Log.i(TAG, "不保存重复反馈附件");
				return false;
			}
		} finally {
			if (cursor != null)
				dbHelper.closeCursor(cursor);
		}

		
	}

	/**
	 * 根据事务ID，获取该事务的附件列表
	 * 
	 * @param affairID
	 * @return 附件列表
	 */
	public ArrayList<AffairAttachModel> getAttachByAffairID(String affairID) {
		ArrayList<AffairAttachModel> attchments = new ArrayList<AffairAttachModel>();
		Cursor cursor = null;
		try {
			SQLiteDatabase db = dbHelper.getReadableDatabase();
			cursor = db.query(DBConstants.AFFAIR_ATTACHMENT_TABLE_NAME,
					DBConstants.AFFAIR_ATTACHMENT_ALL_COLUMNS, "affair_id = ?",
					new String[] { affairID }, null, null, null);

			while (cursor.moveToNext()) {
				attchments.add(createAffairAttachFromCursor(cursor));
			}
		} finally {
			if (cursor != null) {
				dbHelper.closeCursor(cursor);
			}
		}
		return attchments;
	}

	/**
	 * 根据反馈ID，获取该反馈的附件列表
	 * 
	 * @param feedbackID
	 * @return 附件列表
	 */
	public ArrayList<FeedbackAttachModel> getAttachByFeedbackID(
			String feedbackID) {
		ArrayList<FeedbackAttachModel> attchments = new ArrayList<FeedbackAttachModel>();
		Cursor cursor = null;
		try {
			SQLiteDatabase db = dbHelper.getReadableDatabase();
			cursor = db.query(DBConstants.FEEDBACK_ATTACHMENT_TABLE_NAME,
					DBConstants.FEEDBACK_ATTACHMENT_ALL_COLUMNS,
					"feedback_id = ?", new String[] { feedbackID }, null, null,
					null);

			while (cursor.moveToNext()) {
				attchments.add(createFeedbackAttachFromCursor(cursor));
			}
		} finally {
			if (cursor != null) {
				dbHelper.closeCursor(cursor);
			}
		}
		return attchments;
	}

	// --------------------------------------------------------------------------------

	private ContentValues createContentValues(AffairAttachModel attachment) {
		ContentValues values = new ContentValues();
		values.put("affair_id", attachment.getAffairID());
		values.put("attachment_type", attachment.getAttachmentType());
		values.put("url", attachment.getAttachmentURL());
		return values;
	}

	private ContentValues createContentValues(FeedbackAttachModel attachment) {
		ContentValues values = new ContentValues();
		values.put("feedback_id", attachment.getFeedbackID());
		values.put("attachment_type", attachment.getAttachmentType());
		values.put("url", attachment.getAttachmentURL());
		return values;
	}

	private AffairAttachModel createAffairAttachFromCursor(Cursor cursor) {
		String affairID = cursor.getString(0);
		int attachmentType = cursor.getInt(1);
		String attachmentURL = cursor.getString(2);
		return new AffairAttachModel(affairID, attachmentType, attachmentURL);
	}

	private FeedbackAttachModel createFeedbackAttachFromCursor(Cursor cursor) {
		String feedbackID = cursor.getString(0);
		int attachmentType = cursor.getInt(1);
		String attachmentURL = cursor.getString(2);
		return new FeedbackAttachModel(feedbackID, attachmentType,
				attachmentURL);
	}

}
