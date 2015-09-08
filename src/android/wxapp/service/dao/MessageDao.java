package android.wxapp.service.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.R.integer;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.SystemClock;
import android.provider.ContactsContract.Contacts.Data;
import android.util.Log;
import android.wxapp.service.jerry.model.affair.CreateTaskRequestIds;
import android.wxapp.service.jerry.model.affair.QueryAffairInfoResponse;
import android.wxapp.service.jerry.model.message.MessageUpdateQueryResponse;
import android.wxapp.service.jerry.model.message.MessageUpdateQueryResponseMessages;
import android.wxapp.service.jerry.model.message.QueryContactPersonMessageResponseIds;
import android.wxapp.service.jerry.model.message.ReceiveMessageResponse;
import android.wxapp.service.model.MessageModel;
import android.wxapp.service.util.Constant;
import android.wxapp.service.util.MySharedPreference;

public class MessageDao extends BaseDAO {

	private static String TAG = "MessageDao";

	public MessageDao(Context context) {
		super(context);
	}

	/**
	 * 获取对应事务下的反馈消息
	 * 
	 * @param aid
	 *            事务id
	 * @return
	 */
	public List<ReceiveMessageResponse> getFeedback(String aid) {
		List<ReceiveMessageResponse> result = new ArrayList<ReceiveMessageResponse>();
		// String uid = MySharedPreference.get(c, MySharedPreference.USER_ID,
		// null);
		// result.addAll(getMessageBySidAndRid(uid, aid, "4"));

		db = dbHelper.getReadableDatabase();
		Cursor c = db.rawQuery("select * from " + DatabaseHelper.TABLE_MESSAGE + " where "
				+ DatabaseHelper.FIELD_MESSAGE_RELATION_ID + " = ? order by "
				+ DatabaseHelper.FIELD_MESSAGE_SEND_TIME + " ASC", new String[] { aid });
		while (c.moveToNext()) {
			result.add(new ReceiveMessageResponse("",
					getData(c, DatabaseHelper.FIELD_MESSAGE_MESSAGE_ID),
					getData(c, DatabaseHelper.FIELD_MESSAGE_TYPE),
					getData(c, DatabaseHelper.FIELD_MESSAGE_SENDER_ID),
					getData(c, DatabaseHelper.FIELD_MESSAGE_RELATION_ID),
					getData(c, DatabaseHelper.FIELD_MESSAGE_SEND_TIME),
					getData(c, DatabaseHelper.FIELD_MESSAGE_CONTENT),
					getData(c, DatabaseHelper.FIELD_MESSAGE_ATTACHMENT_TYPE),
					getData(c, DatabaseHelper.FIELD_MESSAGE_ATTACHMENT_URL),
					getData(c, DatabaseHelper.FIELD_MESSAGE_ATTACHMENT_URL), ""));
		}

		return result;
	}

	/**
	 * 保存消息到本地数据库
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
			if (saveMessage(item.getMid(),
					new ReceiveMessageResponse("", item.getMid(), item.getT(), item.getSid(),
							item.getRid(), item.getSt(), item.getC(), item.getAt(), item.getAu(),
							item.getUt(), "")))
				continue;
			else
				return false;
		}
		return true;
	}

	/**
	 * 查询sid和rid两个人的聊天记录
	 * 
	 * @param sid
	 * @param rid
	 * @param type
	 *            消息类型0.普通个人聊天消息1.基本群组消息 2.非基本群组消息3.会议记录消息 4.事务反馈消息
	 * @return 按照将序排列
	 */
	public List<ReceiveMessageResponse> getMessageBySidAndRid(String sid, String rid, String type) {
		SQLiteDatabase db = dbHelper.getReadableDatabase();

		String sql = "select * from " + DatabaseHelper.TABLE_MESSAGE + " where ( ("
				+ DatabaseHelper.FIELD_MESSAGE_TYPE + " = '" + type + "' ) and (" + "(" + "("
				+ DatabaseHelper.FIELD_MESSAGE_SENDER_ID + " = '" + sid + "') and ("
				+ DatabaseHelper.FIELD_MESSAGE_RELATION_ID + " = '" + rid + "')" + ") or (" + "("
				+ DatabaseHelper.FIELD_MESSAGE_RELATION_ID + " = '" + sid + "') and ("
				+ DatabaseHelper.FIELD_MESSAGE_SENDER_ID + " = '" + rid + "')" + ")" + ")"
				+ ") order by " + DatabaseHelper.FIELD_MESSAGE_SEND_TIME + " ASC";
		Cursor c = db.rawQuery(sql, null);
		List<ReceiveMessageResponse> result = new ArrayList<ReceiveMessageResponse>();
		while (c.moveToNext()) {
			result.add(new ReceiveMessageResponse("",
					getData(c, DatabaseHelper.FIELD_MESSAGE_MESSAGE_ID),
					getData(c, DatabaseHelper.FIELD_MESSAGE_TYPE),
					getData(c, DatabaseHelper.FIELD_MESSAGE_SENDER_ID),
					getData(c, DatabaseHelper.FIELD_MESSAGE_RELATION_ID),
					getData(c, DatabaseHelper.FIELD_MESSAGE_SEND_TIME),
					getData(c, DatabaseHelper.FIELD_MESSAGE_CONTENT),
					getData(c, DatabaseHelper.FIELD_MESSAGE_ATTACHMENT_TYPE),
					getData(c, DatabaseHelper.FIELD_MESSAGE_ATTACHMENT_URL),
					getData(c, DatabaseHelper.FIELD_MESSAGE_ATTACHMENT_URL), ""));
		}
		c.close();
		return result;
	}

	/**
	 * 获取最近联系人界面的最近联系内容
	 * 
	 * @param uid
	 * @param type
	 * @return
	 */
	public List<Map<String, String>> getLastMessageRecodes(String uid, String type) {
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		List<String> rids = getRecodeIds(uid, type);
		for (String rid : rids) {
			result.add(getLastMessageRecode(uid, rid, type));
		}

		// 按倒序排序！！！
		Collections.sort(result, new Comparator<Map<String, String>>() {

			@Override
			public int compare(Map<String, String> lhs, Map<String, String> rhs) {
				return rhs.get("time").compareTo(lhs.get("time"));
			}
		});
		return result;
	}

	/**
	 * 获取uid和rid聊天的最近的记录内容
	 * 
	 * @param uid
	 * @param rid
	 * @param type
	 * @return
	 */
	public Map<String, String> getLastMessageRecode(String uid, String rid, String type) {
		Map<String, String> result = new HashMap<String, String>();
		List<ReceiveMessageResponse> list = getMessageBySidAndRid(uid, rid, type);
		ReceiveMessageResponse lastMessage = list.get(list.size() - 1);
		String messageAttachmentType = lastMessage.getAt();
		String recode = "";

		// 附件类型（当消息为文本消息时该字段为空）（1：文本2：图片3：录像4：录音5：GPS）
		if (messageAttachmentType.equals("1")) {
			recode += lastMessage.getC();
		} else if (messageAttachmentType.equals("2")) {
			recode += "[图片消息]";
		} else if (messageAttachmentType.equals("3")) {
			recode += "[录像消息]";
		} else if (messageAttachmentType.equals("4")) {
			recode += "[录音消息]";
		} else if (messageAttachmentType.equals("5")) {
			recode += "[GPS消息]";
		} else {
			recode += "[消息]";
		}
		// 如果发送者为rid
		if (lastMessage.getSid().equals(rid)) {
			// TODO 将rid改为对方用户的名字
			// recode = rid + ":" + recode;
		} else {
			recode = "我：" + recode;
		}
		result.put("recode", recode);
		result.put("time", lastMessage.getSt());
		result.put("uid", uid);
		result.put("rid", rid);
		return result;
	}

	private boolean setIsRead(String mid) {
		db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(DatabaseHelper.FIELD_MESSAGE_READTIME, System.currentTimeMillis());
		return db.update(DatabaseHelper.TABLE_MESSAGE, values,
				DatabaseHelper.FIELD_MESSAGE_MESSAGE_ID + " = ?", new String[] { mid }) > 0;
	}

	/**
	 * 查询关于uid用户的所有聊天人的id
	 * 
	 * @param uid
	 * @param type
	 *            消息类型0.普通个人聊天消息1.基本群组消息 2.非基本群组消息3.会议记录消息 4.事务反馈消息
	 * @return 返回与uid用户聊过天的所有人的id
	 */
	public List<String> getRecodeIds(String uid, String type) {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		List<String> result = new ArrayList<String>();

		if (type.equals("0")) {
			// 先查询sendid为uid的消息数据
			Cursor c1 = db.rawQuery("select * from " + DatabaseHelper.TABLE_MESSAGE + " where ( ("
					+ DatabaseHelper.FIELD_MESSAGE_SENDER_ID + " = " + uid + " ) and ( "
					+ DatabaseHelper.FIELD_MESSAGE_TYPE + " = " + type + " ) )", null);
			while (c1.moveToNext()) {
				String tempRid = getData(c1, DatabaseHelper.FIELD_MESSAGE_RELATION_ID);
				if (result.contains(tempRid))
					continue;
				else
					result.add(tempRid);
			}
			c1.close();

			Cursor c2 = db.rawQuery("select * from " + DatabaseHelper.TABLE_MESSAGE + " where ( ("
					+ DatabaseHelper.FIELD_MESSAGE_RELATION_ID + " = " + uid + " ) and ( "
					+ DatabaseHelper.FIELD_MESSAGE_TYPE + " = " + type + " ) )", null);
			while (c2.moveToNext()) {
				String tempRid = getData(c2, DatabaseHelper.FIELD_MESSAGE_SENDER_ID);
				if (result.contains(tempRid))
					continue;
				else
					result.add(tempRid);
			}
			c2.close();
		}

		return result;
	}

	/**
	 * 根据mid查询消息
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
			result = new ReceiveMessageResponse("",
					getData(c, DatabaseHelper.FIELD_MESSAGE_MESSAGE_ID),
					getData(c, DatabaseHelper.FIELD_MESSAGE_TYPE),
					getData(c, DatabaseHelper.FIELD_MESSAGE_SENDER_ID),
					getData(c, DatabaseHelper.FIELD_MESSAGE_RELATION_ID),
					getData(c, DatabaseHelper.FIELD_MESSAGE_SEND_TIME),
					getData(c, DatabaseHelper.FIELD_MESSAGE_CONTENT),
					getData(c, DatabaseHelper.FIELD_MESSAGE_ATTACHMENT_TYPE),
					getData(c, DatabaseHelper.FIELD_MESSAGE_ATTACHMENT_URL),
					getData(c, DatabaseHelper.FIELD_MESSAGE_ATTACHMENT_URL), "");
		}
		c.close();
		return result;
	}

	/**
	 * 根据本人ID和对话人ID，获取双方消息的未读条数
	 * 
	 * @param uid
	 * @param rid
	 * @return 未读消息条数
	 */
	public int getUnreadNumByIDs(String uid, String rid, String type) {
		db = dbHelper.getReadableDatabase();
		Cursor c = db.rawQuery("select * from " + DatabaseHelper.TABLE_MESSAGE + " where (("
				+ DatabaseHelper.FIELD_MESSAGE_READTIME + " = \"\" ) and ("
				+ DatabaseHelper.FIELD_MESSAGE_TYPE + " = " + type + ") and ((("
				+ DatabaseHelper.FIELD_MESSAGE_RELATION_ID + " = " + uid + ") and ("
				+ DatabaseHelper.FIELD_MESSAGE_SENDER_ID + " = " + rid + ")) or (("
				+ DatabaseHelper.FIELD_MESSAGE_SENDER_ID + " = " + uid + ") and ("
				+ DatabaseHelper.FIELD_MESSAGE_RELATION_ID + " = " + rid + "))))", null);
		int result = c.getCount();
		c.close();
		return result;
	}

	/**
	 * 根据本人ID，获取本人最近会话列表
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
		// "SELECT sender_id,receiver_id,message_id,is_group FROM message WHERE
		// sender_id = ? OR receiver_id = ? OR is_group = 1",
		// new String[] { userID, userID });
		//
		// while (cursor.moveToNext()) {
		//
		// if (cursor.getInt(3) == 1) { // 为群消息，对象ID为群名
		// objectID = String.valueOf(cursor.getInt(1));
		// } else { // 为个人消息
		// if (Integer.parseInt(userID) == cursor.getInt(0)) { // 用户本人是发送人
		// objectID = String.valueOf(cursor.getInt(1)); // ,对象ID就是接收人ID
		// } else if (Integer.parseInt(userID) == cursor.getInt(1)) { // 本人是接收人
		// objectID = String.valueOf(cursor.getInt(0)); // ，对象ID就是发送人ID
		// }
		// }
		//
		//
		// if (objectID != null && !idList.contains(objectID)) //
		// 每个聊天对象加入对象ID列表一次
		// idList.add(objectID);
		// }
		//
		// for (int i = 0; i < idList.size(); i++) { //
		// 根据对象ID列表，找出与该对象最新的一次Message
		// cursor2 = db
		// .rawQuery(
		// "SELECT
		// message_id,sender_id,receiver_id,send_time,content,attachment_type,attachment_url,is_group,is_read"
		// + " FROM "
		// + DBConstants.MESSAGE_TABLE_NAME
		// +
		// " WHERE (sender_id = ? and receiver_id = ?) or (receiver_id = ? and
		// sender_id = ?) or (receiver_id = ? and is_group = 1) order by
		// send_time desc",
		// new String[] { idList.get(i), userID,
		// idList.get(i), userID, idList.get(i) });
		// cursor2.moveToFirst(); // 只取第一条数据（最新消息）
		// MessageModel msg = createMessageFromCursor(cursor2);
		// recentMsgList.add(msg); // 每个对象的最新一次Message进入列表
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
	 * 更新消息状态为已读
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
	 * 更新消息状态为已读
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
		// "(sender_id = ? and receiver_id = ?) or (sender_id = ? and
		// receiver_id = ?) or (receiver_id = ? and is_group = 1)",
		// new String[] { objectID, userID, userID, objectID, objectID });
		//
		// } finally {
		// }
	}

	public void deleteMessages(String userID, String objectID) {
		// SQLiteDatabase db = dbHelper.getWritableDatabase();
		// long id = db
		// .delete(DBConstants.MESSAGE_TABLE_NAME,
		// "(sender_id = ? and receiver_id = ?) or (sender_id = ? and
		// receiver_id = ?) or (receiver_id = ? and is_group = 1)",
		// new String[] { objectID, userID, userID, objectID,
		// objectID });
		// if (id == -1) {
		// Log.i(TAG, "删除消息失败!");
		// } else {
		// Log.i(TAG, "删除消息成功!");
		// }
	}

	/**
	 * 根据本人ID和对话人ID，获取双方未读消息列表
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
		// "SELECT
		// message_id,sender_id,receiver_id,send_time,content,attachment_type,attachment_url,is_read"
		// + " FROM "
		// + DBConstants.MESSAGE_TABLE_NAME
		// +
		// " WHERE (sender_id = ? and receiver_id = ?) or (sender_id = ? and
		// receiver_id = ?) and is_read = 0",
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
	 * 根据本人ID和对话人ID，获取双方的对话列表
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
		// "SELECT
		// message_id,sender_id,receiver_id,send_time,content,attachment_type,attachment_url,is_group,is_read"
		// + " FROM "
		// + DBConstants.MESSAGE_TABLE_NAME
		// +
		// " WHERE (sender_id = ? and receiver_id = ?) or (sender_id = ? and
		// receiver_id = ?) or (receiver_id = ? and is_group = 1)",
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
	// // 2014-7-15 判断是否为群消息
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
