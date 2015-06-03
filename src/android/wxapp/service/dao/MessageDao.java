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
	 * 保存消息到本地数据库
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
//			if (cursor.getInt(0) == 0) { // 该消息不存在，保存之
//				ContentValues values = createContentValues(message);
//				long id = db.insert(DBConstants.MESSAGE_TABLE_NAME, null,
//						values);
//				if (id == -1) {
//					Log.i(TAG, "创建消息失败!");
//					return false;
//				} else {
//					Log.i(TAG, "创建消息成功!");
//					return true;
//				}
//			} else { // 该消息已存在，不保存
//				Log.i(TAG, "不保存重复消息");
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
	 * 更新消息状态为已读
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
	 * 更新消息状态为已读
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
//			Log.i(TAG, "删除消息失败!");
//		} else {
//			Log.i(TAG, "删除消息成功!");
//		}
	}

	/**
	 * 根据本人ID和对话人ID，获取双方未读消息列表
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
	 * 根据本人ID和对话人ID，获取双方的对话列表
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
	 * 根据本人ID，获取本人最近会话列表
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
//				if (cursor.getInt(3) == 1) { // 为群消息，对象ID为群名
//					objectID = String.valueOf(cursor.getInt(1));
//				} else { // 为个人消息
//					if (Integer.parseInt(userID) == cursor.getInt(0)) { // 用户本人是发送人
//						objectID = String.valueOf(cursor.getInt(1)); // ,对象ID就是接收人ID
//					} else if (Integer.parseInt(userID) == cursor.getInt(1)) { // 本人是接收人
//						objectID = String.valueOf(cursor.getInt(0)); // ，对象ID就是发送人ID
//					}
//				}
//
//
//				if (objectID != null && !idList.contains(objectID)) // 每个聊天对象加入对象ID列表一次
//					idList.add(objectID);
//			}
//
//			for (int i = 0; i < idList.size(); i++) { // 根据对象ID列表，找出与该对象最新的一次Message
//				cursor2 = db
//						.rawQuery(
//								"SELECT message_id,sender_id,receiver_id,send_time,content,attachment_type,attachment_url,is_group,is_read"
//										+ " FROM "
//										+ DBConstants.MESSAGE_TABLE_NAME
//										+ " WHERE (sender_id = ? and receiver_id = ?) or (receiver_id = ? and sender_id = ?) or (receiver_id = ? and is_group = 1) order by send_time desc",
//								new String[] { idList.get(i), userID,
//										idList.get(i), userID, idList.get(i) });
//				cursor2.moveToFirst(); // 只取第一条数据（最新消息）
//				MessageModel msg = createMessageFromCursor(cursor2);
//				recentMsgList.add(msg); // 每个对象的最新一次Message进入列表
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
	 * 根据本人ID和对话人ID，获取双方消息的未读条数
	 * 
	 * @param userID
	 * @param objectID
	 * @return 未读消息条数
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
//		// 2014-7-15 判断是否为群消息
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
