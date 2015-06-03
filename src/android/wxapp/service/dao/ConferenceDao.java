package android.wxapp.service.dao;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.wxapp.service.model.ConferenceModel;

public class ConferenceDao extends BaseDAO{

	private DatabaseHelper dbHelper;
	private static String TAG = "ConferenceDao";

	public ConferenceDao(Context context) {
		dbHelper = DatabaseHelper.getInstance(context);
	}

	public boolean saveConference(ConferenceModel con) {
//		ContentValues values = createContentValues(con);
//		SQLiteDatabase db = dbHelper.getWritableDatabase();
//		long id = db.insert(DBConstants.CONFERENCE_TABLE_NAME, null, values);
//		if (id == -1) {
//			Log.i(TAG, "创建会议失败!");
//			return false;
//		} else {
//			Log.i(TAG, "创建会议成功!");
//			return true;
//		}
		return false;
	}

	public ConferenceModel getConferenceByID(String conferenceID) {
//		ConferenceModel conference = null;
//		Cursor cursor = null;
//		try {
//			SQLiteDatabase db = dbHelper.getReadableDatabase();
//			cursor = db.query(DBConstants.CONFERENCE_TABLE_NAME,
//					DBConstants.CONFERENCE_ALL_COLUMNS, " conference_id = ?",
//					new String[] { conferenceID }, null, null, null);
//			if (cursor.moveToFirst()) {
//				conference = createConferenceFromCursor(cursor);
//			}
//		} finally {
//			if (cursor != null)
//				dbHelper.closeCursor(cursor);
//		}
//		return conference;
		return null;
	}

	public ArrayList<ConferenceModel> getConferenceListByID(String userID){
//		ArrayList<ConferenceModel> conList = new ArrayList<ConferenceModel>();
//		Cursor cursor=null;
//		try{
//			SQLiteDatabase db=dbHelper.getReadableDatabase();
//			cursor = db
//					.rawQuery(
//							"SELECT DISTINCT C.conference_id,C.conference_name,C.type,C.sponsor_id, "
//									+ "C.create_time,C.reservation_time,C.start_time,C.end_time,C.status "
//									+ "FROM conference C INNER JOIN conference_person CP "
//									+ "ON C.conference_id=CP.conference_id "
//									+ "WHERE C.sponsor_id = ? OR CP.person_id = ? "
//									+ "ORDER BY C.create_time DESC",
//							new String[] { userID, userID });
//			while (cursor.moveToNext()) {
//				conList.add(createConferenceFromCursor(cursor));
//			}
//
//		} finally {
//			if (cursor != null)
//				dbHelper.closeCursor(cursor);
//		}
//
//		return conList;
		return null;
	}

	public void deleteConferenceByID(String conferenceID) {
//		SQLiteDatabase db = dbHelper.getWritableDatabase();
//		long id = db.delete(DBConstants.CONFERENCE_TABLE_NAME,
//				"conference_id = ?", new String[] { conferenceID });
//		if (id == -1) {
//			Log.i(TAG, "删除会议失败!");
//		} else {
//			Log.i(TAG, "删除会议成功!");
//		}
	}

	// -----------------------------------------------------------------------------

//	private ContentValues createContentValues(ConferenceModel con) {
//		ContentValues values = new ContentValues();
//		values.put("conference_id", con.getConferenceID());
//		values.put("conference_name", con.getConferenceName());
//		values.put("type", con.getType());
//		values.put("sponsor_id", con.getSponsorID());
//		values.put("create_time", con.getCreateTime());
//		values.put("reservation_time", con.getReservationTime());
//		values.put("start_time", con.getStartTime());
//		values.put("end_time", con.getEndTime());
//		values.put("status", con.getStatus());
//
//		return values;
//	}
//
//	private ConferenceModel createConferenceFromCursor(Cursor cursor) {
//		String conferenceID = cursor.getString(0);
//		String conferenceName = cursor.getString(1);
//		int type = cursor.getInt(2);
//		int sponsorID = cursor.getInt(3);
//		String createTime = cursor.getString(4);
//		String reservationTime = cursor.getString(5);
//		String startTime = cursor.getString(6);
//		String endTime = cursor.getString(7);
//		int status = cursor.getInt(8);
//
//		return new ConferenceModel(conferenceID, conferenceName, type,
//				sponsorID, createTime, reservationTime, startTime, endTime,
//				status);
//
//	}

}
