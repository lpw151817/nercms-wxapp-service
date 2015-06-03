package android.wxapp.service.dao;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.wxapp.service.model.MessageModel;
import android.wxapp.service.util.Constant;

public class MessageDao extends BaseDAO{

	private DatabaseHelper dbHelper;
	private static String TAG = "MessageDao";

	public MessageDao(Context context) {
		dbHelper = DatabaseHelper.getInstance(context);
	}

	/**
	 * ������Ϣ���������ݿ�
	 * 
	 * @param message
	 * @return
	 */
	public boolean saveMessage(MessageModel message) {
//		Cursor cursor = null;
//		SQLiteDatabase db = dbHelper.getWritableDatabase();
//		try {
//			cursor = db.rawQuery(
//					"SELECT COUNT(*) FROM message WHERE message_id = ?",
//					new String[] { message.getMessageID() });
//			cursor.moveToFirst();
//			if (cursor.getInt(0) == 0) { // ����Ϣ�����ڣ�����֮
//				ContentValues values = createContentValues(message);
//				long id = db.insert(DBConstants.MESSAGE_TABLE_NAME, null,
//						values);
//				if (id == -1) {
//					Log.i(TAG, "������Ϣʧ��!");
//					return false;
//				} else {
//					Log.i(TAG, "������Ϣ�ɹ�!");
//					return true;
//				}
//			} else { // ����Ϣ�Ѵ��ڣ�������
//				Log.i(TAG, "�������ظ���Ϣ");
//				return false;
//			}
//		} finally {
//			if (cursor != null)
//				dbHelper.closeCursor(cursor);
//		}
return false;
	}

	public boolean isMessageExist(String messageID) {
//		Cursor cursor = null;
//		try {
//			SQLiteDatabase db = dbHelper.getReadableDatabase();
//			cursor = db.rawQuery(
//					"SELECT COUNT(*) FROM message WHERE message_id = ?",
//					new String[] { messageID });
//			cursor.moveToFirst();
//			if (cursor.getInt(0) == 0) {
//				return false;
//			} else {
//				return true;
//			}
//		} finally {
//			if (cursor != null)
//				dbHelper.closeCursor(cursor);
//		}
		return false;
	}

	/**
	 * ������Ϣ״̬Ϊ�Ѷ�
	 * 
	 * @param messageID
	 */
	public void updateMessageIsRead(String messageID) {
//		SQLiteDatabase db = dbHelper.getWritableDatabase();
//		ContentValues values = new ContentValues();
//		values.put("is_read", Constant.READ);
//		db.update(DBConstants.MESSAGE_TABLE_NAME, values, "message_id = ?",
//				new String[] { messageID });
	}

	/**
	 * ������Ϣ״̬Ϊ�Ѷ�
	 * 
	 * @param userID
	 * @param objectID
	 */
	public void updateMessageIsRead(String userID, String objectID) {
//		try {
//			SQLiteDatabase db = dbHelper.getReadableDatabase();
//			ContentValues values = new ContentValues();
//			values.put("is_read", Constant.READ);
//			db.update(
//					DBConstants.MESSAGE_TABLE_NAME,
//					values,
//					"(sender_id = ? and receiver_id = ?) or (sender_id = ? and receiver_id = ?) or (receiver_id = ? and is_group = 1)",
//					new String[] { objectID, userID, userID, objectID, objectID });
//
//		} finally {
//		}
	}

	public void deleteMessages(String userID, String objectID) {
//		SQLiteDatabase db = dbHelper.getWritableDatabase();
//		long id = db
//				.delete(DBConstants.MESSAGE_TABLE_NAME,
//						"(sender_id = ? and receiver_id = ?) or (sender_id = ? and receiver_id = ?) or (receiver_id = ? and is_group = 1)",
//						new String[] { objectID, userID, userID, objectID,
//								objectID });
//		if (id == -1) {
//			Log.i(TAG, "ɾ����Ϣʧ��!");
//		} else {
//			Log.i(TAG, "ɾ����Ϣ�ɹ�!");
//		}
	}

	/**
	 * ���ݱ���ID�ͶԻ���ID����ȡ˫��δ����Ϣ�б�
	 * 
	 * @param userID
	 * @param objectID
	 * @return
	 */
	public ArrayList<MessageModel> getUnreadListByIDs(String userID,
			String objectID) {
//		ArrayList<MessageModel> msgList = new ArrayList<MessageModel>();
//		Cursor cursor = null;
//		try {
//			SQLiteDatabase db = dbHelper.getReadableDatabase();
//			cursor = db
//					.rawQuery(
//							"SELECT message_id,sender_id,receiver_id,send_time,content,attachment_type,attachment_url,is_read"
//									+ " FROM "
//									+ DBConstants.MESSAGE_TABLE_NAME
//									+ " WHERE (sender_id = ? and receiver_id = ?) or (sender_id = ? and receiver_id = ?) and is_read = 0",
//							new String[] { userID, objectID, objectID, userID });
//			while (cursor.moveToNext()) {
//				msgList.add(createMessageFromCursor(cursor));
//			}
//
//		} finally {
//			if (cursor != null)
//				dbHelper.closeCursor(cursor);
//		}
//		return msgList;
		return null;
	}

	/**
	 * ���ݱ���ID�ͶԻ���ID����ȡ˫���ĶԻ��б�
	 * 
	 * @param userID
	 * @param personID
	 * @return
	 */
	public ArrayList<MessageModel> getMessageListByID(String userID,
			String personID) {
//		ArrayList<MessageModel> msgList = new ArrayList<MessageModel>();
//		Cursor cursor = null;
//		try {
//			SQLiteDatabase db = dbHelper.getReadableDatabase();
//			cursor = db
//					.rawQuery(
//							"SELECT message_id,sender_id,receiver_id,send_time,content,attachment_type,attachment_url,is_group,is_read"
//									+ " FROM "
//									+ DBConstants.MESSAGE_TABLE_NAME
//									+ " WHERE (sender_id = ? and receiver_id = ?) or (sender_id = ? and receiver_id = ?) or (receiver_id = ? and is_group = 1)",
//							new String[] { userID, personID, personID, userID,
//									personID });
//			while (cursor.moveToNext()) {
//				msgList.add(createMessageFromCursor(cursor));
//			}
//
//		} finally {
//			if (cursor != null)
//				dbHelper.closeCursor(cursor);
//		}
//		return msgList;
		return null;
	}

	/**
	 * ���ݱ���ID����ȡ��������Ự�б�
	 * 
	 * @param userID
	 * @return
	 */
	public ArrayList<MessageModel> getRecentMessageListByUserID(String userID) {
//		ArrayList<String> idList = new ArrayList<String>();
//		ArrayList<MessageModel> recentMsgList = new ArrayList<MessageModel>();
//		Cursor cursor = null;
//		Cursor cursor2 = null;
//		String objectID = null;
//		try {
//			SQLiteDatabase db = dbHelper.getReadableDatabase();
//			cursor = db
//					.rawQuery(
//							"SELECT sender_id,receiver_id,message_id,is_group FROM message WHERE sender_id = ? OR receiver_id = ? OR is_group = 1",
//							new String[] { userID, userID });
//
//			while (cursor.moveToNext()) {
//
//				if (cursor.getInt(3) == 1) { // ΪȺ��Ϣ������IDΪȺ��
//					objectID = String.valueOf(cursor.getInt(1));
//				} else { // Ϊ������Ϣ
//					if (Integer.parseInt(userID) == cursor.getInt(0)) { // �û������Ƿ�����
//						objectID = String.valueOf(cursor.getInt(1)); // ,����ID���ǽ�����ID
//					} else if (Integer.parseInt(userID) == cursor.getInt(1)) { // �����ǽ�����
//						objectID = String.valueOf(cursor.getInt(0)); // ������ID���Ƿ�����ID
//					}
//				}
//
//
//				if (objectID != null && !idList.contains(objectID)) // ÿ���������������ID�б�һ��
//					idList.add(objectID);
//			}
//
//			for (int i = 0; i < idList.size(); i++) { // ���ݶ���ID�б��ҳ���ö������µ�һ��Message
//				cursor2 = db
//						.rawQuery(
//								"SELECT message_id,sender_id,receiver_id,send_time,content,attachment_type,attachment_url,is_group,is_read"
//										+ " FROM "
//										+ DBConstants.MESSAGE_TABLE_NAME
//										+ " WHERE (sender_id = ? and receiver_id = ?) or (receiver_id = ? and sender_id = ?) or (receiver_id = ? and is_group = 1) order by send_time desc",
//								new String[] { idList.get(i), userID,
//										idList.get(i), userID, idList.get(i) });
//				cursor2.moveToFirst(); // ֻȡ��һ�����ݣ�������Ϣ��
//				MessageModel msg = createMessageFromCursor(cursor2);
//				recentMsgList.add(msg); // ÿ�����������һ��Message�����б�
//			}
//
//		} finally {
//			if (cursor != null)
//				dbHelper.closeCursor(cursor);
//			if (cursor2 != null)
//				dbHelper.closeCursor(cursor2);
//		}
//		return recentMsgList;
		return null;
	}

	/**
	 * ���ݱ���ID�ͶԻ���ID����ȡ˫����Ϣ��δ������
	 * 
	 * @param userID
	 * @param objectID
	 * @return δ����Ϣ����
	 */
	public int getUnreadNumByIDs(String userID, String objectID) {

//		int num = 0;
//		Cursor cursor = null;
//		try {
//			SQLiteDatabase db = dbHelper.getReadableDatabase();
//			cursor = db
//					.rawQuery(
//							"SELECT COUNT(*) FROM message WHERE ((sender_id = ? and receiver_id = ?) OR (receiver_id = ? and is_group = 1)) AND is_read = 0",
//							new String[] { objectID, userID, objectID });
//			cursor.moveToFirst();
//			num = cursor.getInt(0);
//		} finally {
//			if (cursor != null)
//				dbHelper.closeCursor(cursor);
//		}
//		return num;
		return 0;
	}

	// ----------------------------------------------------------
//	private ContentValues createContentValues(MessageModel message) {
//		ContentValues values = new ContentValues();
//		values.put("message_id", message.getMessageID());
//		values.put("sender_id", message.getSenderID());
//		values.put("receiver_id", message.getReceiverID());
//		values.put("send_time", message.getSendTime());
//		values.put("content", message.getDescription());
//		values.put("attachment_type", message.getAttachmentType());
//		values.put("attachment_url", message.getAttachmentURL());
//		// 2014-7-15 �ж��Ƿ�ΪȺ��Ϣ
//		values.put("is_group", message.getIsGroup());
//		values.put("is_read", message.getIsRead());
//
//		return values;
//	}
//
//	private MessageModel createMessageFromCursor(Cursor cursor) {
//		String messageID = cursor.getString(0);
//		int senderID = cursor.getInt(1);
//		int receiverID = cursor.getInt(2);
//		String sendTime = cursor.getString(3);
//		String description = cursor.getString(4);
//		int attachmentType = cursor.getInt(5);
//		String attachmentURL = cursor.getString(6);
//		int isGroup = cursor.getInt(7);
//		int isRead = cursor.getInt(8);
//
//		return new MessageModel(messageID, senderID, receiverID, sendTime,
//				description, attachmentType, attachmentURL, isGroup, isRead);
//
//	}
}
