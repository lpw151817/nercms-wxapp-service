package android.wxapp.service.dao;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.wxapp.service.model.AffairModel;
import android.wxapp.service.util.Constant;

public class AffairDao extends BaseDAO {

	private DatabaseHelper dbHelper;

	private static String TAG = "AffairDao";

	public AffairDao(Context context) {
		dbHelper = DatabaseHelper.getInstance(context);
	}

	/**
	 * 保存事务到数据库
	 * 
	 * @param affair
	 * @return
	 */
	public boolean saveAffair(AffairModel affair) {
		// Cursor cursor = null;
		// SQLiteDatabase db = dbHelper.getWritableDatabase();
		// try {
		// cursor = db.rawQuery(
		// "SELECT COUNT(*) FROM affairs_info WHERE affair_id = ?",
		// new String[] { affair.getAffairID() });
		// cursor.moveToFirst();
		// if (cursor.getInt(0) == 0) { // 该任务不存在，保存之
		// ContentValues values = createContentValues(affair);
		// long id = db.insert(DBConstants.AFFAIRS_TABLE_NAME, null,
		// values);
		// if (id == -1) {
		// Log.i(TAG, "创建任务失败!");
		// return false;
		// } else {
		// Log.i(TAG, "创建任务成功!");
		// return true;
		// }
		// } else { // 该任务已存在，不保存
		// Log.i(TAG, "不保存重复消息");
		// return false;
		// }
		// } finally {
		// if (cursor != null)
		// dbHelper.closeCursor(cursor);
		// }

		return false;
	}

	/**
	 * 根据事务状态，查询事务
	 * 
	 * @param status
	 *            要查询的事务状态
	 * @return 事务列表
	 */
	public ArrayList<AffairModel> getAffairByStatus(int status) {
		// ArrayList<AffairModel> affairs = new ArrayList<AffairModel>();
		// Cursor cursor = null;
		// try {
		// SQLiteDatabase db = dbHelper.getReadableDatabase();
		// cursor = db
		// .query(DBConstants.AFFAIRS_TABLE_NAME,
		// DBConstants.AFFAIR_ALL_COLUMNS, "status = ?",
		// new String[] { Integer.toString(status) }, null,
		// null, null);
		// while (cursor.moveToNext()) {
		// affairs.add(createAffairFromCursor(cursor));
		// }
		// } finally {
		// if (cursor != null)
		// dbHelper.closeCursor(cursor);
		// }
		// return affairs;
		return null;
	}

	/**
	 * 根据事务状态，查询发送的事务
	 * 
	 * @param status
	 *            要查询的事务状态
	 * @return 事务列表
	 */
	public ArrayList<AffairModel> getSendAffairByStatus(String sponsorID, int status) {
		// ArrayList<AffairModel> affairs = new ArrayList<AffairModel>();
		// Cursor cursor = null;
		// try {
		// SQLiteDatabase db = dbHelper.getReadableDatabase();
		// cursor = db.query(DBConstants.AFFAIRS_TABLE_NAME,
		// DBConstants.AFFAIR_ALL_COLUMNS,
		// "sponsor_id = ? and status = ?",
		// new String[] { sponsorID, Integer.toString(status) }, null, null,
		// null);
		// while (cursor.moveToNext()) {
		// affairs.add(createAffairFromCursor(cursor));
		// }
		// } finally {
		// if (cursor != null)
		// dbHelper.closeCursor(cursor);
		// }
		// return affairs;
		return null;
	}

	/**
	 * 根据事务状态，查询接收的事务
	 * 
	 * @param status
	 *            要查询的事务状态
	 * @return 事务列表
	 */
	public ArrayList<AffairModel> getReceiveAffairByStatus(String sponsorID, int status) {
		// ArrayList<AffairModel> affairs = new ArrayList<AffairModel>();
		// Cursor cursor = null;
		// try {
		// SQLiteDatabase db = dbHelper.getReadableDatabase();
		// cursor = db.query(DBConstants.AFFAIRS_TABLE_NAME,
		// DBConstants.AFFAIR_ALL_COLUMNS,
		// "sponsor_id <> ? and status = ?",
		// new String[] { sponsorID, Integer.toString(status) }, null, null,
		// null);
		// while (cursor.moveToNext()) {
		// affairs.add(createAffairFromCursor(cursor));
		// }
		// } finally {
		// if (cursor != null)
		// dbHelper.closeCursor(cursor);
		// }
		// return affairs;
		return null;
	}

	// 2014-5-23
	/**
	 * 根据任务类型和状态，查询任务条数
	 * 
	 * @param type
	 *            任务类型：1-发起任务，2-接收任务
	 * @param status
	 *            状态： 1-进行中（未完成）；2-已完成；3-已延迟
	 * @param userID
	 *            用户ID
	 * @return 任务条数
	 */
	public int getAffairCountByTypeAndStatus(int type, int status, String userID) {
		// Cursor cursor = null;
		// try {
		// SQLiteDatabase db = dbHelper.getReadableDatabase();
		// if (type == 1) { // 查询发起任务
		// cursor = db.rawQuery("SELECT COUNT(*) FROM " +
		// DBConstants.AFFAIRS_TABLE_NAME
		// + " WHERE sponsor_id = ? and status = ?",
		// new String[] { userID, String.valueOf(status) });
		// } else { // 查询接收任务
		// cursor = db.rawQuery("SELECT COUNT(*) FROM " +
		// DBConstants.AFFAIRS_TABLE_NAME
		// + " WHERE sponsor_id <> ? and status = ?",
		// new String[] { userID, String.valueOf(status) });
		// }
		//
		// if (cursor.moveToFirst()) {
		// return cursor.getInt(0);
		// } else {
		// return 0;
		// }
		//
		// } finally {
		// if (cursor != null)
		// dbHelper.closeCursor(cursor);
		// }
		return 0;
	}

	// 2014-7-7 WeiHao 新增
	/**
	 * 根据用户ID，任务类型和任务状态，获取符合该条件的未读任务条数
	 * 
	 * @param type
	 * @param status
	 * @param userID
	 * @return
	 */
	public int getUnreadNumByTypeAndStatus(int type, int status, String userID) {
		// Cursor cursor = null;
		// try {
		// SQLiteDatabase db = dbHelper.getReadableDatabase();
		// if (type == 1) { // 查询发起任务
		// cursor = db.rawQuery("SELECT COUNT(*) FROM " +
		// DBConstants.AFFAIRS_TABLE_NAME
		// + " WHERE sponsor_id = ? and status = ? and is_read = 0", new
		// String[] { userID,
		// String.valueOf(status) });
		// } else { // 查询接收任务
		// cursor = db.rawQuery("SELECT COUNT(*) FROM " +
		// DBConstants.AFFAIRS_TABLE_NAME
		// + " WHERE sponsor_id <> ? and status = ? and is_read = 0", new
		// String[] {
		// userID, String.valueOf(status) });
		// }
		//
		// if (cursor.moveToFirst()) {
		// return cursor.getInt(0);
		// } else {
		// return 0;
		// }
		//
		// } finally {
		// if (cursor != null)
		// dbHelper.closeCursor(cursor);
		// }
		return 0;
	}

	/**
	 * 根据任务ID，得到该任务是否已读
	 * 
	 * @param affairID
	 * @return
	 */
	public int getAffairIsReadByID(String affairID) {
		// Cursor cursor = null;
		// try {
		// SQLiteDatabase db = dbHelper.getReadableDatabase();
		// cursor = db.rawQuery("SELECT is_read FROM " +
		// DBConstants.AFFAIRS_TABLE_NAME
		// + " WHERE affair_id = ? ", new String[] { affairID });
		//
		// if (cursor.moveToFirst()) {
		// return cursor.getInt(0);
		// } else {
		// return -1;
		// }
		//
		// } finally {
		// if (cursor != null)
		// dbHelper.closeCursor(cursor);
		// }
		return 0;
	}

	/**
	 * 根据事务状态，以及已查询到的条数，再获取十条事务
	 * 
	 * @param status
	 *            要查询的事务状态
	 * @param id
	 *            从第id行开始查询
	 * @return 事务列表（<=10)
	 */
	public ArrayList<AffairModel> getAffairByStatus(int status, int id) {
		// ArrayList<AffairModel> affairs = new ArrayList<AffairModel>();
		// Cursor cursor = null;
		// try {
		// SQLiteDatabase db = dbHelper.getReadableDatabase();
		// cursor = db.query(DBConstants.AFFAIRS_TABLE_NAME,
		// DBConstants.AFFAIR_ALL_COLUMNS,
		// "status = ?", new String[] { Integer.toString(status) }, null, null,
		// "begin_time desc", id + " , " + DBConstants.QUERY_LIMIT);
		// while (cursor.moveToNext()) {
		// affairs.add(createAffairFromCursor(cursor));
		// }
		// } finally {
		// if (cursor != null)
		// dbHelper.closeCursor(cursor);
		// }
		// return affairs;
		return null;
	}

	/**
	 * 根据事务ID查找单个事务
	 * 
	 * @param affairID
	 * @return 事务
	 */
	public AffairModel getAffairById(String affairID) {
		// Cursor cursor = null;
		// try {
		// SQLiteDatabase db = dbHelper.getReadableDatabase();
		// cursor = db.query(DBConstants.AFFAIRS_TABLE_NAME,
		// DBConstants.AFFAIR_ALL_COLUMNS,
		// "affair_id = ?", new String[] { affairID }, null, null, null);
		// if (cursor.moveToFirst()) {
		// return createAffairFromCursor(cursor);
		// } else {
		// return null;
		// }
		//
		// } finally {
		// if (cursor != null)
		// dbHelper.closeCursor(cursor);
		// }

		return null;
	}

	/**
	 * 事务标记为已完成
	 * 
	 * @param affairID
	 * @param completeTime
	 *            (由服务器端返回得到)
	 */
	public void updateAffairCompleted(String affairID, String completeTime) {
		// SQLiteDatabase db = dbHelper.getWritableDatabase();
		// ContentValues values = new ContentValues();
		// values.put("status", DBConstants.AFFAIR_COMPLETED);
		// values.put("is_read", 0); // 更新为未读
		// values.put("complete_time", completeTime);
		// values.put("last_operate_type", 2); // 最后一次操作标记为 2-置完成
		// values.put("last_operate_time", completeTime); // 最后一次操作时间，等于置完成的时间
		// int id = db.update(DBConstants.AFFAIRS_TABLE_NAME, values,
		// "affair_id = ?",
		// new String[] { affairID });
		// if (id == -1) {
		// Log.i(TAG, "任务标记为已完成失败!");
		// } else {
		// Log.i(TAG, "任务标记为已完成!");
		// }
	}

	/**
	 * 修改事务的结束时间
	 * 
	 * @param affairID
	 * @param endTime
	 * @param lastOperateTime
	 *            （由服务器端返回得到）
	 */
	public void updateAffairEndTime(String affairID, String endTime, String lastOperateTime) {
		// SQLiteDatabase db = dbHelper.getWritableDatabase();
		// ContentValues values = new ContentValues();
		// values.put("end_time", endTime);
		// values.put("is_read", 0); // 更新为未读
		// values.put("status", DBConstants.AFFAIR_UNCOMPLETED); //
		// 更新状态为进行中，针对延误任务的修改
		// values.put("last_operate_type", 4); // 最后一次操作标记为 4-修改截止日期
		// values.put("last_operate_time", lastOperateTime);
		// int id = db.update(DBConstants.AFFAIRS_TABLE_NAME, values,
		// "affair_id = ?",
		// new String[] { affairID });
		// if (id == -1) {
		// Log.i(TAG, "任务截止时间修改失败!");
		// } else {
		// Log.i(TAG, "任务截止时间修改成功!");
		// }
	}

	/**
	 * 修改事务状态为已延误
	 * 
	 * @param affairID
	 * @param lastOperateTime
	 */
	public void updateAffairTimeout(String affairID, String lastOperateTime) {
		// SQLiteDatabase db = dbHelper.getWritableDatabase();
		// ContentValues values = new ContentValues();
		// values.put("status", DBConstants.AFFAIR_DELAYED);
		// values.put("is_read", 0); // 更新为未读
		// values.put("last_operate_type", 3); // 最后一次操作标记为 3-置延误
		// values.put("last_operate_time", lastOperateTime);
		// int id = db.update(DBConstants.AFFAIRS_TABLE_NAME, values,
		// "affair_id = ?",
		// new String[] { affairID });
		// if (id == -1) {
		// Log.i(TAG, "任务置延误失败!");
		// } else {
		// Log.i(TAG, "任务已标记为延误!");
		// }
	}

	/**
	 * 更新事务状态为已读
	 * 
	 * @param affairID
	 */
	public void updateAffairIsRead(String affairID) {
		// SQLiteDatabase db = dbHelper.getWritableDatabase();
		// ContentValues values = new ContentValues();
		// values.put("is_read", Constant.READ);
		// db.update(DBConstants.AFFAIRS_TABLE_NAME, values, "affair_id = ?",
		// new String[] { affairID });
	}

	// ---------------------------------------------------------------------

	// private ContentValues createContentValues(AffairModel affair) {
	// ContentValues values = new ContentValues();
	// values.put("affair_id", affair.getAffairID());
	// values.put("type", affair.getType());
	// values.put("sponsor_id", affair.getSponsorID());
	// values.put("title", affair.getTitle());
	// values.put("description", affair.getDescription());
	// values.put("begin_time", affair.getBeginTime());
	// values.put("end_time", affair.getEndTime());
	// values.put("complete_time", affair.getCompleteTime());
	// values.put("last_operate_type", affair.getLastOperateType());
	// values.put("last_operate_time", affair.getLastOperateTime());
	// values.put("is_read", affair.getIsRead()); //
	// values.put("status", affair.getStatus());
	//
	// return values;
	// }

	// private AffairModel createAffairFromCursor(Cursor cursor) {
	// String affairID = cursor.getString(0);
	// int type = cursor.getInt(1);
	// int sponsorID = cursor.getInt(2);
	// String title = cursor.getString(3);
	// String description = cursor.getString(4);
	// String beginTime = cursor.getString(5);
	// String endTime = cursor.getString(6);
	// String completeTime = cursor.getString(7);
	// int lastOperateType = cursor.getInt(8);
	// String lastOperateTime = cursor.getString(9);
	// int isRead = cursor.getInt(9);
	// int status = cursor.getInt(10);
	//
	// return new AffairModel(affairID, type, sponsorID, title, description,
	// beginTime, endTime,
	// completeTime, lastOperateType, lastOperateTime, isRead, status);
	// }

}
