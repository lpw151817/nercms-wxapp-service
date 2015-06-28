package android.wxapp.service.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract.Contacts.Data;
import android.util.Log;
import android.wxapp.service.jerry.model.affair.QueryAffairInfoResponse;
import android.wxapp.service.jerry.model.message.MessageUpdateQueryResponse;
import android.wxapp.service.jerry.model.message.MessageUpdateQueryResponseMessages;
import android.wxapp.service.jerry.model.message.QueryContactPersonMessageResponseIds;
import android.wxapp.service.jerry.model.message.ReceiveMessageResponse;
import android.wxapp.service.model.MessageModel;
import android.wxapp.service.util.Constant;

public class MessageDao extends BaseDAO {

	private static String TAG = "MessageDao";

	public MessageDao(Context context) {
		super(context);
	}

	/**
	 * ������Ϣ���������ݿ�
	 * 
	 * @param message
	 * @return
	 */
	public boolean saveMessage(String mid, ReceiveMessageResponse message) {
		db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(DatabaseHelper.FIELD_MESSAGE_ATTACHMENT_TYPE, message.getAt());
		values.put(DatabaseHelper.FIELD_MESSAGE_ATTACHMENT_URL, message.getAu());
		values.put(DatabaseHelper.FIELD_MESSAGE_CONTENT, message.getC());
		values.put(DatabaseHelper.FIELD_MESSAGE_MESSAGE_ID, mid);
		// gson
		values.put(DatabaseHelper.FIELD_MESSAGE_RELATION_ID, message.getRid());
		values.put(DatabaseHelper.FIELD_MESSAGE_SEND_TIME, message.getSt());
		values.put(DatabaseHelper.FIELD_MESSAGE_SENDER_ID, message.getSid());
		values.put(DatabaseHelper.FIELD_MESSAGE_TYPE, message.getT());
		values.put(DatabaseHelper.FIELD_MESSAGE_UPDATE_TIME, message.getUt());
		long i = db.insert(DatabaseHelper.TABLE_MESSAGE, null, values);
		return i > 0;
	}

	public boolean saveMessageUpdate(MessageUpdateQueryResponse message) {
		for (MessageUpdateQueryResponseMessages item : message.getMids()) {
			if (saveMessage(item.getMid(), new ReceiveMessageResponse("", item.getT(), item.getSid(),
					item.getRid(), item.getSt(), item.getC(), item.getAt(), item.getAu(), item.getUt(),
					"")))
				continue;
			else
				return false;
		}
		return true;
	}

	/**
	 * ���ݷ���id�ͽ���id��ѯ�����¼
	 * 
	 * @param sid
	 * @param rid
	 * @return
	 */
	public List<ReceiveMessageResponse> getMessageBySidAndRid(String sid, String rid) {
		db = dbHelper.getReadableDatabase();
		Cursor c = db.rawQuery("select * from " + DatabaseHelper.TABLE_MESSAGE + " where (("
				+ DatabaseHelper.FIELD_MESSAGE_SENDER_ID + " = " + sid + ") and ("
				+ DatabaseHelper.FIELD_MESSAGE_RELATION_ID + " = '" + rid + "'))", null);
		List<ReceiveMessageResponse> result = new ArrayList<ReceiveMessageResponse>();
		while (c.moveToNext()) {
			result.add(new ReceiveMessageResponse("", getData(c, DatabaseHelper.FIELD_MESSAGE_TYPE),
					sid, rid, getData(c, DatabaseHelper.FIELD_MESSAGE_SEND_TIME), getData(c,
							DatabaseHelper.FIELD_MESSAGE_CONTENT), getData(c,
							DatabaseHelper.FIELD_MESSAGE_ATTACHMENT_TYPE), getData(c,
							DatabaseHelper.FIELD_MESSAGE_ATTACHMENT_URL), getData(c,
							DatabaseHelper.FIELD_MESSAGE_ATTACHMENT_URL), ""));
		}
		c.close();
		return result;
	}

	/**
	 * ����mid��ѯ��Ϣ
	 * 
	 * @param mid
	 * @return
	 */
	public ReceiveMessageResponse getMessageByMid(String mid) {
		db = dbHelper.getReadableDatabase();
		Cursor c = db.rawQuery("select * from " + DatabaseHelper.TABLE_MESSAGE + " where "
				+ DatabaseHelper.FIELD_MESSAGE_MESSAGE_ID + " = " + mid, null);
		ReceiveMessageResponse result = null;
		if (c.moveToFirst()) {
			result = new ReceiveMessageResponse("", getData(c, DatabaseHelper.FIELD_MESSAGE_TYPE),
					getData(c, DatabaseHelper.FIELD_MESSAGE_SENDER_ID), getData(c,
							DatabaseHelper.FIELD_MESSAGE_RELATION_ID), getData(c,
							DatabaseHelper.FIELD_MESSAGE_SEND_TIME), getData(c,
							DatabaseHelper.FIELD_MESSAGE_CONTENT), getData(c,
							DatabaseHelper.FIELD_MESSAGE_ATTACHMENT_TYPE), getData(c,
							DatabaseHelper.FIELD_MESSAGE_ATTACHMENT_URL), getData(c,
							DatabaseHelper.FIELD_MESSAGE_ATTACHMENT_URL), "");
		}
		c.close();
		return result;
	}

	/**
	 * ��ѯ�����¼
	 *
	 * 
	 * @param uid
	 * @return
	 */
	public List<ReceiveMessageResponse> getMessageRecord(String uid) {
		db = dbHelper.getReadableDatabase();
		Cursor c = db.rawQuery("select * from " + DatabaseHelper.TABLE_MESSAGE + "where "
				+ DatabaseHelper.FIELD_MESSAGE_SENDER_ID + " = " + uid, null);
		List<ReceiveMessageResponse> result = new ArrayList<ReceiveMessageResponse>();
		while (c.moveToNext()) {
			result.add(new ReceiveMessageResponse("", getData(c, DatabaseHelper.FIELD_MESSAGE_TYPE),
					getData(c, DatabaseHelper.FIELD_MESSAGE_SENDER_ID), getData(c,
							DatabaseHelper.FIELD_MESSAGE_RELATION_ID), getData(c,
							DatabaseHelper.FIELD_MESSAGE_SEND_TIME), getData(c,
							DatabaseHelper.FIELD_MESSAGE_CONTENT), getData(c,
							DatabaseHelper.FIELD_MESSAGE_ATTACHMENT_TYPE), getData(c,
							DatabaseHelper.FIELD_MESSAGE_ATTACHMENT_URL), getData(c,
							DatabaseHelper.FIELD_MESSAGE_ATTACHMENT_URL), ""));
		}
		c.close();
		return result;
	}

	/**
	 * ���ݱ���ID����ȡ��������Ự�б�
	 * 
	 * @param userID
	 * @return
	 */
	public ArrayList<MessageModel> getRecentMessageListByUserID(String userID) {
		// ArrayList<String> idList = new ArrayList<String>();
		// ArrayList<MessageModel> recentMsgList = new
		// ArrayList<MessageModel>();
		// Cursor cursor = null;
		// Cursor cursor2 = null;
		// String objectID = null;
		// try {
		// SQLiteDatabase db = dbHelper.getReadableDatabase();
		// cursor = db
		// .rawQuery(
		// "SELECT sender_id,receiver_id,message_id,is_group FROM message WHERE sender_id = ? OR receiver_id = ? OR is_group = 1",
		// new String[] { userID, userID });
		//
		// while (cursor.moveToNext()) {
		//
		// if (cursor.getInt(3) == 1) { // ΪȺ��Ϣ������IDΪȺ��
		// objectID = String.valueOf(cursor.getInt(1));
		// } else { // Ϊ������Ϣ
		// if (Integer.parseInt(userID) == cursor.getInt(0)) { // �û������Ƿ�����
		// objectID = String.valueOf(cursor.getInt(1)); // ,����ID���ǽ�����ID
		// } else if (Integer.parseInt(userID) == cursor.getInt(1)) { // �����ǽ�����
		// objectID = String.valueOf(cursor.getInt(0)); // ������ID���Ƿ�����ID
		// }
		// }
		//
		//
		// if (objectID != null && !idList.contains(objectID)) //
		// ÿ���������������ID�б�һ��
		// idList.add(objectID);
		// }
		//
		// for (int i = 0; i < idList.size(); i++) { //
		// ���ݶ���ID�б��ҳ���ö������µ�һ��Message
		// cursor2 = db
		// .rawQuery(
		// "SELECT message_id,sender_id,receiver_id,send_time,content,attachment_type,attachment_url,is_group,is_read"
		// + " FROM "
		// + DBConstants.MESSAGE_TABLE_NAME
		// +
		// " WHERE (sender_id = ? and receiver_id = ?) or (receiver_id = ? and sender_id = ?) or (receiver_id = ? and is_group = 1) order by send_time desc",
		// new String[] { idList.get(i), userID,
		// idList.get(i), userID, idList.get(i) });
		// cursor2.moveToFirst(); // ֻȡ��һ�����ݣ�������Ϣ��
		// MessageModel msg = createMessageFromCursor(cursor2);
		// recentMsgList.add(msg); // ÿ�����������һ��Message�����б�
		// }
		//
		// } finally {
		// if (cursor != null)
		// dbHelper.closeCursor(cursor);
		// if (cursor2 != null)
		// dbHelper.closeCursor(cursor2);
		// }
		// return recentMsgList;
		return null;
	}

	public boolean isMessageExist(String messageID) {
		// Cursor cursor = null;
		// try {
		// SQLiteDatabase db = dbHelper.getReadableDatabase();
		// cursor = db.rawQuery(
		// "SELECT COUNT(*) FROM message WHERE message_id = ?",
		// new String[] { messageID });
		// cursor.moveToFirst();
		// if (cursor.getInt(0) == 0) {
		// return false;
		// } else {
		// return true;
		// }
		// } finally {
		// if (cursor != null)
		// dbHelper.closeCursor(cursor);
		// }
		return false;
	}

	/**
	 * ������Ϣ״̬Ϊ�Ѷ�
	 * 
	 * @param messageID
	 */
	public void updateMessageIsRead(String messageID) {
		// SQLiteDatabase db = dbHelper.getWritableDatabase();
		// ContentValues values = new ContentValues();
		// values.put("is_read", Constant.READ);
		// db.update(DBConstants.MESSAGE_TABLE_NAME, values, "message_id = ?",
		// new String[] { messageID });
	}

	/**
	 * ������Ϣ״̬Ϊ�Ѷ�
	 * 
	 * @param userID
	 * @param objectID
	 */
	public void updateMessageIsRead(String userID, String objectID) {
		// try {
		// SQLiteDatabase db = dbHelper.getReadableDatabase();
		// ContentValues values = new ContentValues();
		// values.put("is_read", Constant.READ);
		// db.update(
		// DBConstants.MESSAGE_TABLE_NAME,
		// values,
		// "(sender_id = ? and receiver_id = ?) or (sender_id = ? and receiver_id = ?) or (receiver_id = ? and is_group = 1)",
		// new String[] { objectID, userID, userID, objectID, objectID });
		//
		// } finally {
		// }
	}

	public void deleteMessages(String userID, String objectID) {
		// SQLiteDatabase db = dbHelper.getWritableDatabase();
		// long id = db
		// .delete(DBConstants.MESSAGE_TABLE_NAME,
		// "(sender_id = ? and receiver_id = ?) or (sender_id = ? and receiver_id = ?) or (receiver_id = ? and is_group = 1)",
		// new String[] { objectID, userID, userID, objectID,
		// objectID });
		// if (id == -1) {
		// Log.i(TAG, "ɾ����Ϣʧ��!");
		// } else {
		// Log.i(TAG, "ɾ����Ϣ�ɹ�!");
		// }
	}

	/**
	 * ���ݱ���ID�ͶԻ���ID����ȡ˫��δ����Ϣ�б�
	 * 
	 * @param userID
	 * @param objectID
	 * @return
	 */
	public ArrayList<MessageModel> getUnreadListByIDs(String userID, String objectID) {
		// ArrayList<MessageModel> msgList = new ArrayList<MessageModel>();
		// Cursor cursor = null;
		// try {
		// SQLiteDatabase db = dbHelper.getReadableDatabase();
		// cursor = db
		// .rawQuery(
		// "SELECT message_id,sender_id,receiver_id,send_time,content,attachment_type,attachment_url,is_read"
		// + " FROM "
		// + DBConstants.MESSAGE_TABLE_NAME
		// +
		// " WHERE (sender_id = ? and receiver_id = ?) or (sender_id = ? and receiver_id = ?) and is_read = 0",
		// new String[] { userID, objectID, objectID, userID });
		// while (cursor.moveToNext()) {
		// msgList.add(createMessageFromCursor(cursor));
		// }
		//
		// } finally {
		// if (cursor != null)
		// dbHelper.closeCursor(cursor);
		// }
		// return msgList;
		return null;
	}

	/**
	 * ���ݱ���ID�ͶԻ���ID����ȡ˫���ĶԻ��б�
	 * 
	 * @param userID
	 * @param personID
	 * @return
	 */
	public ArrayList<MessageModel> getMessageListByID(String userID, String personID) {
		// ArrayList<MessageModel> msgList = new ArrayList<MessageModel>();
		// Cursor cursor = null;
		// try {
		// SQLiteDatabase db = dbHelper.getReadableDatabase();
		// cursor = db
		// .rawQuery(
		// "SELECT message_id,sender_id,receiver_id,send_time,content,attachment_type,attachment_url,is_group,is_read"
		// + " FROM "
		// + DBConstants.MESSAGE_TABLE_NAME
		// +
		// " WHERE (sender_id = ? and receiver_id = ?) or (sender_id = ? and receiver_id = ?) or (receiver_id = ? and is_group = 1)",
		// new String[] { userID, personID, personID, userID,
		// personID });
		// while (cursor.moveToNext()) {
		// msgList.add(createMessageFromCursor(cursor));
		// }
		//
		// } finally {
		// if (cursor != null)
		// dbHelper.closeCursor(cursor);
		// }
		// return msgList;
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

		// int num = 0;
		// Cursor cursor = null;
		// try {
		// SQLiteDatabase db = dbHelper.getReadableDatabase();
		// cursor = db
		// .rawQuery(
		// "SELECT COUNT(*) FROM message WHERE ((sender_id = ? and receiver_id = ?) OR (receiver_id = ? and is_group = 1)) AND is_read = 0",
		// new String[] { objectID, userID, objectID });
		// cursor.moveToFirst();
		// num = cursor.getInt(0);
		// } finally {
		// if (cursor != null)
		// dbHelper.closeCursor(cursor);
		// }
		// return num;
		return 0;
	}

	// ----------------------------------------------------------
	// private ContentValues createContentValues(MessageModel message) {
	// ContentValues values = new ContentValues();
	// values.put("message_id", message.getMessageID());
	// values.put("sender_id", message.getSenderID());
	// values.put("receiver_id", message.getReceiverID());
	// values.put("send_time", message.getSendTime());
	// values.put("content", message.getDescription());
	// values.put("attachment_type", message.getAttachmentType());
	// values.put("attachment_url", message.getAttachmentURL());
	// // 2014-7-15 �ж��Ƿ�ΪȺ��Ϣ
	// values.put("is_group", message.getIsGroup());
	// values.put("is_read", message.getIsRead());
	//
	// return values;
	// }
	//
	// private MessageModel createMessageFromCursor(Cursor cursor) {
	// String messageID = cursor.getString(0);
	// int senderID = cursor.getInt(1);
	// int receiverID = cursor.getInt(2);
	// String sendTime = cursor.getString(3);
	// String description = cursor.getString(4);
	// int attachmentType = cursor.getInt(5);
	// String attachmentURL = cursor.getString(6);
	// int isGroup = cursor.getInt(7);
	// int isRead = cursor.getInt(8);
	//
	// return new MessageModel(messageID, senderID, receiverID, sendTime,
	// description, attachmentType, attachmentURL, isGroup, isRead);
	//
	// }
}
