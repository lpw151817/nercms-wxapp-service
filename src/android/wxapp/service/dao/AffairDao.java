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
	 * �����������ݿ�
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
		// if (cursor.getInt(0) == 0) { // �����񲻴��ڣ�����֮
		// ContentValues values = createContentValues(affair);
		// long id = db.insert(DBConstants.AFFAIRS_TABLE_NAME, null,
		// values);
		// if (id == -1) {
		// Log.i(TAG, "��������ʧ��!");
		// return false;
		// } else {
		// Log.i(TAG, "��������ɹ�!");
		// return true;
		// }
		// } else { // �������Ѵ��ڣ�������
		// Log.i(TAG, "�������ظ���Ϣ");
		// return false;
		// }
		// } finally {
		// if (cursor != null)
		// dbHelper.closeCursor(cursor);
		// }

		return false;
	}

	/**
	 * ��������״̬����ѯ����
	 * 
	 * @param status
	 *            Ҫ��ѯ������״̬
	 * @return �����б�
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
	 * ��������״̬����ѯ���͵�����
	 * 
	 * @param status
	 *            Ҫ��ѯ������״̬
	 * @return �����б�
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
	 * ��������״̬����ѯ���յ�����
	 * 
	 * @param status
	 *            Ҫ��ѯ������״̬
	 * @return �����б�
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
	 * �����������ͺ�״̬����ѯ��������
	 * 
	 * @param type
	 *            �������ͣ�1-��������2-��������
	 * @param status
	 *            ״̬�� 1-�����У�δ��ɣ���2-����ɣ�3-���ӳ�
	 * @param userID
	 *            �û�ID
	 * @return ��������
	 */
	public int getAffairCountByTypeAndStatus(int type, int status, String userID) {
		// Cursor cursor = null;
		// try {
		// SQLiteDatabase db = dbHelper.getReadableDatabase();
		// if (type == 1) { // ��ѯ��������
		// cursor = db.rawQuery("SELECT COUNT(*) FROM " +
		// DBConstants.AFFAIRS_TABLE_NAME
		// + " WHERE sponsor_id = ? and status = ?",
		// new String[] { userID, String.valueOf(status) });
		// } else { // ��ѯ��������
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

	// 2014-7-7 WeiHao ����
	/**
	 * �����û�ID���������ͺ�����״̬����ȡ���ϸ�������δ����������
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
		// if (type == 1) { // ��ѯ��������
		// cursor = db.rawQuery("SELECT COUNT(*) FROM " +
		// DBConstants.AFFAIRS_TABLE_NAME
		// + " WHERE sponsor_id = ? and status = ? and is_read = 0", new
		// String[] { userID,
		// String.valueOf(status) });
		// } else { // ��ѯ��������
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
	 * ��������ID���õ��������Ƿ��Ѷ�
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
	 * ��������״̬���Լ��Ѳ�ѯ�����������ٻ�ȡʮ������
	 * 
	 * @param status
	 *            Ҫ��ѯ������״̬
	 * @param id
	 *            �ӵ�id�п�ʼ��ѯ
	 * @return �����б�<=10)
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
	 * ��������ID���ҵ�������
	 * 
	 * @param affairID
	 * @return ����
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
	 * ������Ϊ�����
	 * 
	 * @param affairID
	 * @param completeTime
	 *            (�ɷ������˷��صõ�)
	 */
	public void updateAffairCompleted(String affairID, String completeTime) {
		// SQLiteDatabase db = dbHelper.getWritableDatabase();
		// ContentValues values = new ContentValues();
		// values.put("status", DBConstants.AFFAIR_COMPLETED);
		// values.put("is_read", 0); // ����Ϊδ��
		// values.put("complete_time", completeTime);
		// values.put("last_operate_type", 2); // ���һ�β������Ϊ 2-�����
		// values.put("last_operate_time", completeTime); // ���һ�β���ʱ�䣬��������ɵ�ʱ��
		// int id = db.update(DBConstants.AFFAIRS_TABLE_NAME, values,
		// "affair_id = ?",
		// new String[] { affairID });
		// if (id == -1) {
		// Log.i(TAG, "������Ϊ�����ʧ��!");
		// } else {
		// Log.i(TAG, "������Ϊ�����!");
		// }
	}

	/**
	 * �޸�����Ľ���ʱ��
	 * 
	 * @param affairID
	 * @param endTime
	 * @param lastOperateTime
	 *            ���ɷ������˷��صõ���
	 */
	public void updateAffairEndTime(String affairID, String endTime, String lastOperateTime) {
		// SQLiteDatabase db = dbHelper.getWritableDatabase();
		// ContentValues values = new ContentValues();
		// values.put("end_time", endTime);
		// values.put("is_read", 0); // ����Ϊδ��
		// values.put("status", DBConstants.AFFAIR_UNCOMPLETED); //
		// ����״̬Ϊ�����У��������������޸�
		// values.put("last_operate_type", 4); // ���һ�β������Ϊ 4-�޸Ľ�ֹ����
		// values.put("last_operate_time", lastOperateTime);
		// int id = db.update(DBConstants.AFFAIRS_TABLE_NAME, values,
		// "affair_id = ?",
		// new String[] { affairID });
		// if (id == -1) {
		// Log.i(TAG, "�����ֹʱ���޸�ʧ��!");
		// } else {
		// Log.i(TAG, "�����ֹʱ���޸ĳɹ�!");
		// }
	}

	/**
	 * �޸�����״̬Ϊ������
	 * 
	 * @param affairID
	 * @param lastOperateTime
	 */
	public void updateAffairTimeout(String affairID, String lastOperateTime) {
		// SQLiteDatabase db = dbHelper.getWritableDatabase();
		// ContentValues values = new ContentValues();
		// values.put("status", DBConstants.AFFAIR_DELAYED);
		// values.put("is_read", 0); // ����Ϊδ��
		// values.put("last_operate_type", 3); // ���һ�β������Ϊ 3-������
		// values.put("last_operate_time", lastOperateTime);
		// int id = db.update(DBConstants.AFFAIRS_TABLE_NAME, values,
		// "affair_id = ?",
		// new String[] { affairID });
		// if (id == -1) {
		// Log.i(TAG, "����������ʧ��!");
		// } else {
		// Log.i(TAG, "�����ѱ��Ϊ����!");
		// }
	}

	/**
	 * ��������״̬Ϊ�Ѷ�
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
