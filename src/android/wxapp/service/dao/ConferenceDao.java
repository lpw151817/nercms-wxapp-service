package android.wxapp.service.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract.Contacts.Data;
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

	public boolean saveConference(String cid, String n, String sid, String ct, String f, String st,
			String et, String r, List<ConferenceUpdateQueryResponseRids> rids) {
		db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(DatabaseHelper.FIELD_CONFERENCE_CONFERENCE_ID, cid);
		values.put(DatabaseHelper.FIELD_CONFERENCE_NAME, n);
		values.put(DatabaseHelper.FIELD_CONFERENCE_SPONSORID, sid);
		values.put(DatabaseHelper.FIELD_CONFERENCE_CONVENE_TIME, ct);
		values.put(DatabaseHelper.FIELD_CONFERENCE_FROM, f);
		values.put(DatabaseHelper.FIELD_CONFERENCE_START_TIME, st);
		values.put(DatabaseHelper.FIELD_CONFERENCE_ENDTIME, et);
		values.put(DatabaseHelper.FIELD_CONFERENCE_REMARK, r);
		values.put(DatabaseHelper.FIELD_CONFERENCE_RELATIONID, gson.toJson(rids));
		return db.insert(DatabaseHelper.TABLE_CONFERENCE, null, values) > 0;
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

	public ConferenceModel getConferenceByID(String conferenceID) {
		// ConferenceModel conference = null;
		// Cursor cursor = null;
		// try {
		// SQLiteDatabase db = dbHelper.getReadableDatabase();
		// cursor = db.query(DBConstants.CONFERENCE_TABLE_NAME,
		// DBConstants.CONFERENCE_ALL_COLUMNS, " conference_id = ?",
		// new String[] { conferenceID }, null, null, null);
		// if (cursor.moveToFirst()) {
		// conference = createConferenceFromCursor(cursor);
		// }
		// } finally {
		// if (cursor != null)
		// dbHelper.closeCursor(cursor);
		// }
		// return conference;
		return null;
	}

	public ArrayList<ConferenceModel> getConferenceListByID(String userID) {
		// ArrayList<ConferenceModel> conList = new
		// ArrayList<ConferenceModel>();
		// Cursor cursor=null;
		// try{
		// SQLiteDatabase db=dbHelper.getReadableDatabase();
		// cursor = db
		// .rawQuery(
		// "SELECT DISTINCT C.conference_id,C.conference_name,C.type,C.sponsor_id, "
		// +
		// "C.create_time,C.reservation_time,C.start_time,C.end_time,C.status "
		// + "FROM conference C INNER JOIN conference_person CP "
		// + "ON C.conference_id=CP.conference_id "
		// + "WHERE C.sponsor_id = ? OR CP.person_id = ? "
		// + "ORDER BY C.create_time DESC",
		// new String[] { userID, userID });
		// while (cursor.moveToNext()) {
		// conList.add(createConferenceFromCursor(cursor));
		// }
		//
		// } finally {
		// if (cursor != null)
		// dbHelper.closeCursor(cursor);
		// }
		//
		// return conList;
		return null;
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
