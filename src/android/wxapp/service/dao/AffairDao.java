package android.wxapp.service.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.R.id;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract.Contacts.Data;
import android.util.Log;
import android.wxapp.service.jerry.model.affair.CreateTaskRequestAttachment;
import android.wxapp.service.jerry.model.affair.CreateTaskRequestIds;
import android.wxapp.service.jerry.model.affair.QueryAffairInfoResponse;
import android.wxapp.service.jerry.model.affair.QueryAffairListResponseAffairs;
import android.wxapp.service.jerry.model.affair.TaskUpdateQueryResponse;
import android.wxapp.service.jerry.model.affair.TaskUpdateQueryResponseAffairs;
import android.wxapp.service.model.AffairModel;
import android.wxapp.service.request.WebRequestManager;
import android.wxapp.service.util.Constant;

public class AffairDao extends BaseDAO {

	private static String TAG = "AffairDao";

	public AffairDao(Context context) {
		super(context);
	}

	/**
	 * ����������µ����ݿ�
	 * 
	 * @param affair
	 * @return
	 */
	public void saveAffairUpdate(TaskUpdateQueryResponse affair) {
		db = dbHelper.getWritableDatabase();
		for (TaskUpdateQueryResponseAffairs item : affair.getAs()) {
			saveAffairInfo(new QueryAffairInfoResponse("", item.getAid(), item.getT(), item.getSid(),
					item.getD(), item.getTopic(), item.getBt(), item.getEt(), item.getCt(),
					item.getLot(), item.getLotime(), item.getUp(), item.getAtt(), item.getRids(),
					item.getPod()));
		}
		Log.v(TAG, "saveAffairUpdate failed");
	}

	public void saveAffairInfo(QueryAffairInfoResponse affairInfo) {
		db = dbHelper.getWritableDatabase();
		// ���뵽TABLE_AFFIARINFO��
		ContentValues values = new ContentValues();
		values.put(DatabaseHelper.FIELD_AFFIARINFO_TYPE, affairInfo.getT());
		values.put(DatabaseHelper.FIELD_AFFIARINFO_SENDERID, affairInfo.getSid());
		values.put(DatabaseHelper.FIELD_AFFIARINFO_DES, affairInfo.getD());
		values.put(DatabaseHelper.FIELD_AFFIARINFO_TOPIC, affairInfo.getTopic());
		values.put(DatabaseHelper.FIELD_AFFIARINFO_CREATETIME, affairInfo.getCt());
		values.put(DatabaseHelper.FIELD_AFFIARINFO_ENDTIME, affairInfo.getEt());
		values.put(DatabaseHelper.FIELD_AFFIARINFO_COMPLETETIME, affairInfo.getCt());
		values.put(DatabaseHelper.FIELD_AFFIARINFO_LAST_OPERATE_TYPE, affairInfo.getLot());
		values.put(DatabaseHelper.FIELD_AFFIARINFO_LAST_OPERATE_TIME, affairInfo.getLotime());
		values.put(DatabaseHelper.FIELD_AFFIARINFO_UPDATETIME, affairInfo.getUp());
		values.put(DatabaseHelper.FIELD_AFFIARINFO_ATTACHMENT, gson.toJson(affairInfo.getAtt()));
		values.put(DatabaseHelper.FIELD_AFFIARINFO_AFFAIR_ID, affairInfo.getAid());
		if (db.insert(DatabaseHelper.TABLE_AFFIARINFO, null, values) == -1) {
			Log.i(TAG, "�洢����ʧ��!");
		} else {
			Log.i(TAG, "�洢����ɹ�!");
		}

		// ������Ա��Ϣ��TABLE_PERSON_ON_DUTY
		for (CreateTaskRequestIds item : affairInfo.getPod()) {
			savePersonOnDuty(affairInfo.getAid(), item.getRid(), affairInfo.getUp());
		}
		for (CreateTaskRequestIds item : affairInfo.getRids()) {
			saveReceiver(affairInfo.getAid(), item.getRid(), affairInfo.getUp());
		}
	}

	/**
	 * 
	 * @param aid
	 * @param id
	 * @param type
	 *            1�������ˣ�2��������
	 * @param updateTime
	 * @return
	 */
	public boolean saveInPersonOnDuty(String aid, String id, String type, String updateTime) {
		db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(DatabaseHelper.FIELD_POD_AID, aid);
		values.put(DatabaseHelper.FIELD_POD_PID, id);
		values.put(DatabaseHelper.FIELD_POD_TYPE, type);
		values.put(DatabaseHelper.FIELD_POD_UT, updateTime);
		long i = db.insert(DatabaseHelper.TABLE_PERSON_ON_DUTY, null, values);
		if (i == -1) {
			Log.i(TAG, "savePersonOnDutyʧ��!");
			return false;
		} else {
			Log.i(TAG, "savePersonOnDuty�ɹ�!");
			return true;
		}
	}

	public boolean savePersonOnDuty(String aid, String pid, String updateTime) {
		return saveInPersonOnDuty(aid, pid, 1 + "", updateTime);
	}

	public boolean saveReceiver(String aid, String rid, String updateTime) {
		return saveInPersonOnDuty(aid, rid, 2 + "", updateTime);
	}

	/**
	 * ͨ��personid��type�����Ӧ��affairid
	 * 
	 * @param pid
	 * @param type
	 *            ��Ϊnull 1�������ˣ�2��������
	 * 
	 * @return affairid��list
	 */
	public List<String> getAffairIdByPersonId(String pid, String type) {
		SQLiteDatabase database = dbHelper.getReadableDatabase();
		String sql;
		if (type != null)
			sql = "SELECT * FROM " + DatabaseHelper.TABLE_PERSON_ON_DUTY + " WHERE "
					+ DatabaseHelper.FIELD_POD_PID + " = " + pid + " and "
					+ DatabaseHelper.FIELD_POD_TYPE + " = " + type;
		else
			sql = "SELECT * FROM " + DatabaseHelper.TABLE_PERSON_ON_DUTY + " WHERE "
					+ DatabaseHelper.FIELD_POD_PID + " = " + pid;
		Cursor c = database.rawQuery(sql, null);
		List<String> ids = new ArrayList<String>();
		while (c.moveToNext()) {
			ids.add(getData(c, DatabaseHelper.FIELD_POD_AID));
		}
		c.close();
		return ids;
	}

	/**
	 * ����AffairId��ѯ����Ա��Ϣ
	 * 
	 * @param aid
	 * @return < key , value >:<"1",List<String> pod>,<"2",List<String> rids>
	 */
	public Map<String, List<CreateTaskRequestIds>> getPersonIdByAffairId(String aid) {
		SQLiteDatabase database = dbHelper.getReadableDatabase();
		// SELECT * FROM PersonOnDuty WHERE affair_id='1';
		Cursor c = database.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_PERSON_ON_DUTY + " WHERE "
				+ DatabaseHelper.FIELD_POD_AID + " = ? ", new String[] { aid });
		Map<String, List<CreateTaskRequestIds>> result = new HashMap<String, List<CreateTaskRequestIds>>();
		List<CreateTaskRequestIds> pod = new ArrayList<CreateTaskRequestIds>();
		List<CreateTaskRequestIds> rids = new ArrayList<CreateTaskRequestIds>();
		while (c.moveToNext()) {
			String type = getData(c, DatabaseHelper.FIELD_POD_TYPE);
			// ������
			if (type.equals("1")) {
				pod.add(new CreateTaskRequestIds(getData(c, DatabaseHelper.FIELD_POD_PID)));
				continue;
			}
			// ������
			else if (type.equals("2")) {
				rids.add(new CreateTaskRequestIds(getData(c, DatabaseHelper.FIELD_POD_PID)));
				continue;
			}
		}
		result.put("1", pod);
		result.put("2", rids);
		c.close();
		return result;
	}

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
		return getAffairByTypeAndStatus(type, status, userID).size();
	}

	/**
	 * �����û�ID���������ͺ�����״̬����ȡ���ϸ�������δ����������
	 * 
	 * @param type
	 *            �������ͣ�1-��������2-��������
	 * @param status
	 *            ״̬�� 1-�����У�δ��ɣ���2-����ɣ�3-���ӳ�
	 * @param userID
	 * @return
	 */
	public int getUnreadNumByTypeAndStatus(int type, int status, String userID) {
		db = dbHelper.getReadableDatabase();
		Cursor cursor = null;
		String sql = "select * from " + DatabaseHelper.TABLE_AFFIARINFO + " where ";
		// ��ѯ��������
		if (type == 1) {
			switch (status) {
			// doing
			case 1:
				sql += "(" + "( " + DatabaseHelper.FIELD_AFFIARINFO_COMPLETETIME + " is null ) and ( "
						+ DatabaseHelper.FIELD_AFFIARINFO_CREATETIME + " < datetime('now')" + ") and ("
						+ DatabaseHelper.FIELD_AFFIARINFO_READTIME + " is null ) and ("
						+ DatabaseHelper.FIELD_AFFIARINFO_ENDTIME + " > datetime('now') ) and ("
						+ DatabaseHelper.FIELD_AFFIARINFO_SENDERID + " = " + userID + ")" + ")";
				break;
			// done
			case 2:
				sql += "(" + "( " + DatabaseHelper.FIELD_AFFIARINFO_COMPLETETIME + " is not null"
						+ " ) and ( " + DatabaseHelper.FIELD_AFFIARINFO_READTIME + " is null ) and ( "
						+ DatabaseHelper.FIELD_AFFIARINFO_SENDERID + " = " + userID + ")" + ")";
				break;
			// delay
			case 3:
				sql += "(" + "( " + DatabaseHelper.FIELD_AFFIARINFO_COMPLETETIME + " is null ) and ( "
						+ DatabaseHelper.FIELD_AFFIARINFO_ENDTIME + " < datetime('now')" + " ) and ( "
						+ DatabaseHelper.FIELD_AFFIARINFO_READTIME + " is null ) and ( "
						+ DatabaseHelper.FIELD_AFFIARINFO_SENDERID + " = " + userID + ")" + ")";
				break;
			default:
				Log.e(TAG, "getAffairCountByTypeAndStatus default");
				break;
			}
		}
		// ��ѯ��������
		else if (type == 2) {
			switch (status) {
			case 1:
				sql += "(" + "( " + DatabaseHelper.FIELD_AFFIARINFO_AFFAIR_ID + " in ( select "
						+ DatabaseHelper.FIELD_POD_AID + " from " + DatabaseHelper.TABLE_PERSON_ON_DUTY
						+ " where " + DatabaseHelper.FIELD_POD_PID + " = " + userID + " )" + ") and "
						+ "(" + DatabaseHelper.FIELD_AFFIARINFO_COMPLETETIME + " is null and "
						+ DatabaseHelper.FIELD_AFFIARINFO_CREATETIME + " < datetime('now')" + ") and "
						+ "(" + DatabaseHelper.FIELD_AFFIARINFO_READTIME + " is null" + ") and" + "("
						+ DatabaseHelper.FIELD_AFFIARINFO_ENDTIME + " > datetime('now')" + ")" + ")";
				break;
			case 2:
				sql += "(" + "( " + DatabaseHelper.FIELD_AFFIARINFO_AFFAIR_ID + " in ( select "
						+ DatabaseHelper.FIELD_POD_AID + " from " + DatabaseHelper.TABLE_PERSON_ON_DUTY
						+ " where " + DatabaseHelper.FIELD_POD_PID + " = " + userID + " )" + ") and "
						+ "(" + DatabaseHelper.FIELD_AFFIARINFO_COMPLETETIME + " is not null" + ") and "
						+ "(" + DatabaseHelper.FIELD_AFFIARINFO_READTIME + " is null" + ")" + ")";
				break;
			case 3:
				sql += "(" + "( " + DatabaseHelper.FIELD_AFFIARINFO_AFFAIR_ID + " in ( select "
						+ DatabaseHelper.FIELD_POD_AID + " from " + DatabaseHelper.TABLE_PERSON_ON_DUTY
						+ " where " + DatabaseHelper.FIELD_POD_PID + " = " + userID + " )" + ") and "
						+ "(" + DatabaseHelper.FIELD_AFFIARINFO_COMPLETETIME + " is null and "
						+ DatabaseHelper.FIELD_AFFIARINFO_ENDTIME + " < datetime('now')" + ") and "
						+ "(" + DatabaseHelper.FIELD_AFFIARINFO_READTIME + " is null" + ")" + ")";
				break;
			default:
				Log.e(TAG, "getUnreadNumByTypeAndStatus default");
				break;
			}
		}
		Log.e("AffairDao SQL", sql);
		cursor = db.rawQuery(sql, null);
		int result = cursor.getCount();
		cursor.close();
		return result;

	}

	/**
	 * ��������״̬����ѯ���͵�����
	 * 
	 * @param type
	 *            �������ͣ�1-��������2-��������
	 * @param status
	 *            ״̬�� 1-�����У�δ��ɣ���2-����ɣ�3-���ӳ�
	 * 
	 * @param userID
	 * @return
	 */
	public List<QueryAffairListResponseAffairs> getAffairByTypeAndStatus(int type, int status,
			String userID) {
		db = dbHelper.getReadableDatabase();
		Cursor c = null;
		String sql = "select * from " + DatabaseHelper.TABLE_AFFIARINFO + " where ";
		// ��ѯ��������
		if (type == 1) {
			switch (status) {
			// doing
			case 1:
				sql += "(" + "( " + DatabaseHelper.FIELD_AFFIARINFO_COMPLETETIME + " is null )and( "
						+ DatabaseHelper.FIELD_AFFIARINFO_SENDERID + " = " + userID + " )and( "
						+ DatabaseHelper.FIELD_AFFIARINFO_CREATETIME + " < datetime('now') )and( "
						+ DatabaseHelper.FIELD_AFFIARINFO_ENDTIME + " > datetime('now')" + ")" + ")";
				break;
			// done
			case 2:
				sql += "(" + "( " + DatabaseHelper.FIELD_AFFIARINFO_COMPLETETIME + " is not null )and( "
						+ DatabaseHelper.FIELD_AFFIARINFO_SENDERID + " = " + userID + ")" + ")";
				break;
			// delay
			case 3:
				sql += "(" + "( " + DatabaseHelper.FIELD_AFFIARINFO_COMPLETETIME + " is null )and( "
						+ DatabaseHelper.FIELD_AFFIARINFO_ENDTIME + " < datetime('now') )and( "
						+ DatabaseHelper.FIELD_AFFIARINFO_SENDERID + " = " + userID + ")" + ")";
				break;
			default:
				Log.e(TAG, "getAffairCountByTypeAndStatus default");
				break;
			}
		}
		// ��ѯ��������
		else if (type == 2) {
			switch (status) {
			case 1:
				sql += "(" + "( " + DatabaseHelper.FIELD_AFFIARINFO_AFFAIR_ID + " in ( select "
						+ DatabaseHelper.FIELD_POD_AID + " from " + DatabaseHelper.TABLE_PERSON_ON_DUTY
						+ " where " + DatabaseHelper.FIELD_POD_PID + " = " + userID + " )" + ") and "
						+ "(" + DatabaseHelper.FIELD_AFFIARINFO_COMPLETETIME + " is null and "
						+ DatabaseHelper.FIELD_AFFIARINFO_CREATETIME + " < datetime('now')" + ") and "
						+ "(" + DatabaseHelper.FIELD_AFFIARINFO_ENDTIME + " > datetime('now')" + ")"
						+ ")";
				break;
			case 2:
				sql += "(" + "( " + DatabaseHelper.FIELD_AFFIARINFO_AFFAIR_ID + " in ( select "
						+ DatabaseHelper.FIELD_POD_AID + " from " + DatabaseHelper.TABLE_PERSON_ON_DUTY
						+ " where " + DatabaseHelper.FIELD_POD_PID + " = " + userID + " )" + ") and "
						+ "(" + DatabaseHelper.FIELD_AFFIARINFO_COMPLETETIME + " is not null" + ")"
						+ ")";
				break;
			case 3:
				sql += "(" + "( " + DatabaseHelper.FIELD_AFFIARINFO_AFFAIR_ID + " in ( select "
						+ DatabaseHelper.FIELD_POD_AID + " from " + DatabaseHelper.TABLE_PERSON_ON_DUTY
						+ " where " + DatabaseHelper.FIELD_POD_PID + " = " + userID + " )" + ") and "
						+ "(" + DatabaseHelper.FIELD_AFFIARINFO_COMPLETETIME + " is null and "
						+ DatabaseHelper.FIELD_AFFIARINFO_ENDTIME + " < datetime('now')" + ")" + ")";
				break;
			default:
				Log.e(TAG, "getAffairCountByTypeAndStatus default");
				break;
			}
		}
		Log.e("AffairDao SQL", sql);
		c = db.rawQuery(sql, null);
		List<QueryAffairListResponseAffairs> result = new ArrayList<QueryAffairListResponseAffairs>();
		while (c.moveToNext()) {
			// ����id
			String aid = getData(c, DatabaseHelper.FIELD_AFFIARINFO_AFFAIR_ID);
			// ��ȡ����id�ĸ����˺ͳ�����
			Map<String, List<CreateTaskRequestIds>> idMap = getPersonIdByAffairId(aid);
			result.add(new QueryAffairListResponseAffairs(aid, getData(c,
					DatabaseHelper.FIELD_AFFIARINFO_TYPE), getData(c,
					DatabaseHelper.FIELD_AFFIARINFO_SENDERID), getData(c,
					DatabaseHelper.FIELD_AFFIARINFO_DES), getData(c,
					DatabaseHelper.FIELD_AFFIARINFO_TOPIC), getData(c,
					DatabaseHelper.FIELD_AFFIARINFO_CREATETIME), getData(c,
					DatabaseHelper.FIELD_AFFIARINFO_ENDTIME), getData(c,
					DatabaseHelper.FIELD_AFFIARINFO_COMPLETETIME), getData(c,
					DatabaseHelper.FIELD_AFFIARINFO_LAST_OPERATE_TYPE), getData(c,
					DatabaseHelper.FIELD_AFFIARINFO_LAST_OPERATE_TIME), getData(c,
					DatabaseHelper.FIELD_AFFIARINFO_LAST_OPERATE_TIME), json2List(
					getData(c, DatabaseHelper.FIELD_AFFIARINFO_ATTACHMENT),
					CreateTaskRequestAttachment.class), idMap.get("2"), idMap.get("1")));
		}
		c.close();
		return result;

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
		// cursor = db.query(DBConstants.AFFAIRS_TABLE_NAME,
		// DBConstants.AFFAIR_ALL_COLUMNS,
		// "status = ?", new String[] { Integer.toString(status) }, null, null,
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
	 * ��������ID���õ��������Ƿ��Ѷ�
	 * 
	 * @param affairID
	 * @return true:�Ѷ�
	 */
	public boolean getAffairIsReadByID(String affairID) {
		db = dbHelper.getReadableDatabase();
		Cursor c = db.rawQuery("select * from " + DatabaseHelper.TABLE_AFFIARINFO + " where "
				+ DatabaseHelper.FIELD_AFFIARINFO_AFFAIR_ID + " = " + affairID, null);
		if (c.moveToFirst()) {
			String r = getData(c, DatabaseHelper.FIELD_AFFIARINFO_READTIME);
			if (r == null || r == "") {
				return false;
			} else {
				return true;
			}
		} else {
			return true;
		}
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
