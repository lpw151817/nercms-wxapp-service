package android.wxapp.service.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.reflect.TypeToken;

import android.R.string;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract.Contacts.Data;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.wxapp.service.jerry.model.conference.ConferenceUpdateQueryResponse;
import android.wxapp.service.jerry.model.conference.ConferenceUpdateQueryResponseItem;
import android.wxapp.service.jerry.model.conference.ConferenceUpdateQueryResponseRids;
import android.wxapp.service.jerry.model.conference.CreateConferenceRequest;
import android.wxapp.service.model.ConferenceModel;

public class ConferenceDao extends BaseDAO {

	private static String TAG = "ConferenceDao";

	public ConferenceDao(Context context) {
		super(context);
	}

	public boolean saveConferenceUpdate(ConferenceUpdateQueryResponse cs) {
		for (ConferenceUpdateQueryResponseItem item : cs.getCs()) {
			if (saveConference(item.getCid(), item.getN(), item.getSid(), item.getCt(), item.getF(),
					item.getSt(), item.getEt(), item.getR(), item.getRids()))
				continue;
			else
				return false;
		}
		return true;

	}

	public boolean saveConferencePerson(String cid, ConferenceUpdateQueryResponseRids data) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(DatabaseHelper.FIELD_CONFERENCE_PERSON_CID, cid);
		values.put(DatabaseHelper.FIELD_CONFERENCE_PERSON_TYPE, data.getT());
		values.put(DatabaseHelper.FIELD_CONFERENCE_PERSON_UID, data.getRid());
		return db.insert(DatabaseHelper.TABLE_CONFERENCE_PERSON, null, values) > 0;
	}

	public List<ConferenceUpdateQueryResponseRids> getConferencePersonByCid(String cid) {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor c = db.rawQuery("select * from " + DatabaseHelper.TABLE_CONFERENCE_PERSON + " where "
				+ DatabaseHelper.FIELD_CONFERENCE_PERSON_CID + " = " + cid, null);
		List<ConferenceUpdateQueryResponseRids> result = new ArrayList<ConferenceUpdateQueryResponseRids>();
		while (c.moveToNext()) {
			result.add(new ConferenceUpdateQueryResponseRids(getData(c,
					DatabaseHelper.FIELD_CONFERENCE_PERSON_UID), getData(c,
					DatabaseHelper.FIELD_CONFERENCE_PERSON_TYPE)));
		}
		c.close();
		return result;
	}

	public List<String> getConferenceIdByPidInPerson(String pid) {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor c = db.rawQuery("select DISTINCT " + DatabaseHelper.FIELD_CONFERENCE_PERSON_CID
				+ " from " + DatabaseHelper.TABLE_CONFERENCE_PERSON + " where "
				+ DatabaseHelper.FIELD_CONFERENCE_PERSON_UID + " = " + pid, null);
		List<String> result = new ArrayList<String>();
		while (c.moveToNext()) {
			result.add(getData(c, DatabaseHelper.FIELD_CONFERENCE_PERSON_CID));
		}
		c.close();
		return result;
	}

	public Map<String, List<ConferenceUpdateQueryResponseRids>> getConferencePersonByPid(String pid) {
		Map<String, List<ConferenceUpdateQueryResponseRids>> result = new HashMap<String, List<ConferenceUpdateQueryResponseRids>>();
		List<String> cids = getConferenceIdByPidInPerson(pid);
		for (String item : cids) {
			List<ConferenceUpdateQueryResponseRids> rids = getConferencePersonByCid(item);
			result.put(item, rids);
		}
		return result;
	}

	public boolean saveConference(String cid, String n, String sid, String ct, String f, String st,
			String et, String r, List<ConferenceUpdateQueryResponseRids> rids) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues values1 = new ContentValues();
		values1.put(DatabaseHelper.FIELD_CONFERENCE_CONFERENCE_ID, cid);
		values1.put(DatabaseHelper.FIELD_CONFERENCE_NAME, n);
		values1.put(DatabaseHelper.FIELD_CONFERENCE_SPONSORID, sid);
		values1.put(DatabaseHelper.FIELD_CONFERENCE_CONVENE_TIME, ct);
		values1.put(DatabaseHelper.FIELD_CONFERENCE_FROM, f);
		values1.put(DatabaseHelper.FIELD_CONFERENCE_START_TIME, st);
		values1.put(DatabaseHelper.FIELD_CONFERENCE_ENDTIME, et);
		values1.put(DatabaseHelper.FIELD_CONFERENCE_REMARK, r);
		int i = 0;
		for (; i < rids.size(); i++) {
			if (saveConferencePerson(cid, rids.get(i)))
				continue;
			else
				break;
		}
		return (db.insert(DatabaseHelper.TABLE_CONFERENCE, null, values1) > 0) && (i == rids.size());
	}

	public boolean startConference(String cid, String starttime) {
		db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(DatabaseHelper.FIELD_CONFERENCE_START_TIME, starttime);
		return db.update(DatabaseHelper.TABLE_CONFERENCE, values,
				DatabaseHelper.FIELD_CONFERENCE_CONFERENCE_ID + " = ?", new String[] { cid }) > 0;
	}

	public boolean endConference(String cid, String endtime) {
		db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(DatabaseHelper.FIELD_CONFERENCE_ENDTIME, endtime);
		return db.update(DatabaseHelper.TABLE_CONFERENCE, values,
				DatabaseHelper.FIELD_CONFERENCE_CONFERENCE_ID + " = ?", new String[] { cid }) > 0;
	}

	public ConferenceUpdateQueryResponseItem getConferenceByCid(String conferenceID) {
		db = dbHelper.getReadableDatabase();
		Cursor c = db.rawQuery("select * from " + DatabaseHelper.TABLE_CONFERENCE + " where "
				+ DatabaseHelper.FIELD_CONFERENCE_CONFERENCE_ID + " = " + conferenceID, null);
		ConferenceUpdateQueryResponseItem result = null;
		if (c.moveToFirst()) {

			result = new ConferenceUpdateQueryResponseItem(conferenceID, getData(c,
					DatabaseHelper.FIELD_CONFERENCE_NAME), getData(c,
					DatabaseHelper.FIELD_CONFERENCE_SPONSORID), getData(c,
					DatabaseHelper.FIELD_CONFERENCE_CONVENE_TIME), getData(c,
					DatabaseHelper.FIELD_CONFERENCE_FROM), getData(c,
					DatabaseHelper.FIELD_CONFERENCE_START_TIME), getData(c,
					DatabaseHelper.FIELD_CONFERENCE_ENDTIME), getData(c,
					DatabaseHelper.FIELD_CONFERENCE_REMARK), getConferencePersonByCid(conferenceID));
		}
		c.close();
		return result;
	}

	public List<ConferenceUpdateQueryResponseItem> getConferenceListByPid(String userID) {
		db = dbHelper.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT * from " + DatabaseHelper.TABLE_CONFERENCE + " where "
				+ DatabaseHelper.FIELD_CONFERENCE_SPONSORID + " = " + userID, null);
		List<ConferenceUpdateQueryResponseItem> result = new ArrayList<ConferenceUpdateQueryResponseItem>();
		// 作为发起人
		while (c.moveToNext()) {
			result.add(new ConferenceUpdateQueryResponseItem(getData(c,
					DatabaseHelper.FIELD_CONFERENCE_CONFERENCE_ID), getData(c,
					DatabaseHelper.FIELD_CONFERENCE_NAME), getData(c,
					DatabaseHelper.FIELD_CONFERENCE_SPONSORID), getData(c,
					DatabaseHelper.FIELD_CONFERENCE_CONVENE_TIME), getData(c,
					DatabaseHelper.FIELD_CONFERENCE_FROM), getData(c,
					DatabaseHelper.FIELD_CONFERENCE_START_TIME), getData(c,
					DatabaseHelper.FIELD_CONFERENCE_ENDTIME), getData(c,
					DatabaseHelper.FIELD_CONFERENCE_REMARK), getConferencePersonByCid(getData(c,
					DatabaseHelper.FIELD_CONFERENCE_CONFERENCE_ID))));
		}
		// 作为接收人
		Map<String, List<ConferenceUpdateQueryResponseRids>> data = getConferencePersonByPid(userID);
		for (String item : data.keySet()) {
			if (!result.contains(new ConferenceUpdateQueryResponseItem(item, null, null, null, null,
					null, null, null, null)))
				result.add(getConferenceByCid(item));
		}
		c.close();
		// 按预约时间的倒叙排列
		Collections.sort(result);
		return result;
	}

	public void deleteConferenceByID(String conferenceID) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		long id = db.delete(DatabaseHelper.TABLE_CONFERENCE, "conference_id = ?",
				new String[] { conferenceID });
		if (id == -1) {
			Log.i(TAG, "删除会议失败!");
		} else {
			Log.i(TAG, "删除会议成功!");
		}
	}

	public boolean deleteAll() {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		return db.delete(DatabaseHelper.TABLE_CONFERENCE, null, null) > 0;
	}

	// -----------------------------------------------------------------------------

	// private ContentValues createContentValues(ConferenceModel con) {
	// ContentValues values = new ContentValues();
	// values.put("conference_id", con.getConferenceID());
	// values.put("conference_name", con.getConferenceName());
	// values.put("type", con.getType());
	// values.put("sponsor_id", con.getSponsorID());
	// values.put("create_time", con.getCreateTime());
	// values.put("reservation_time", con.getReservationTime());
	// values.put("start_time", con.getStartTime());
	// values.put("end_time", con.getEndTime());
	// values.put("status", con.getStatus());
	//
	// return values;
	// }
	//
	// private ConferenceModel createConferenceFromCursor(Cursor cursor) {
	// String conferenceID = cursor.getString(0);
	// String conferenceName = cursor.getString(1);
	// int type = cursor.getInt(2);
	// int sponsorID = cursor.getInt(3);
	// String createTime = cursor.getString(4);
	// String reservationTime = cursor.getString(5);
	// String startTime = cursor.getString(6);
	// String endTime = cursor.getString(7);
	// int status = cursor.getInt(8);
	//
	// return new ConferenceModel(conferenceID, conferenceName, type,
	// sponsorID, createTime, reservationTime, startTime, endTime,
	// status);
	//
	// }

}
