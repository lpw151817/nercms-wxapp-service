package android.wxapp.service.dao;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.wxapp.service.model.ConferencePersonModel;

public class ConferencePersonDao extends BaseDAO{

	private DatabaseHelper dbHelper;
	private static String TAG = "ConferencePersonDao";

	public ConferencePersonDao(Context context) {
		dbHelper = DatabaseHelper.getInstance(context);
	}

	public boolean saveConferencePerson(ConferencePersonModel cp) {
//		ContentValues values = createContentValues(cp);
//		SQLiteDatabase db = dbHelper.getWritableDatabase();
//		long id = db.insert(DBConstants.CONFERENCE_PERSON_TABLE_NAME, null,
//				values);
//		if (id == -1) {
//			Log.i(TAG, "创建会议参与者失败!");
//			return false;
//		} else {
//			Log.i(TAG, "创建会议参与者成功!");
//			return true;
//		}
		return false;
	}

	public ArrayList<ConferencePersonModel> getConferencePersonListByID(
			String conferenceID) {
//		ArrayList<ConferencePersonModel> cpList = new ArrayList<ConferencePersonModel>();
//		Cursor cursor = null;
//		try {
//			SQLiteDatabase db = dbHelper.getReadableDatabase();
//			cursor = db.query(DBConstants.CONFERENCE_PERSON_TABLE_NAME,
//					DBConstants.CONFERENCE_PERSON_ALL_COLUMNS,
//					" conference_id = ? ", new String[] { conferenceID }, null,
//					null, null);
//			while (cursor.moveToNext()) {
//				cpList.add(createConferencePersonFromCursor(cursor));
//			}
//
//		} finally {
//			if (cursor != null)
//				dbHelper.closeCursor(cursor);
//		}
//
//		return cpList;
		return null;
	}

//	private ContentValues createContentValues(ConferencePersonModel cp) {
//		ContentValues values = new ContentValues();
//		values.put("conference_id", cp.getConferenceID());
//		values.put("person_id", cp.getPersonID());
//		values.put("is_sponsor", cp.getIsSponsor());
//		values.put("is_speaker", cp.getIsSpeaker());
//		values.put("is_video_sharer", cp.getIsVideoSharer());
//		values.put("join_time", cp.getJoinTime());
//		values.put("leave_time", cp.getLeaveTime());
//
//		return values;
//	}
//
//	private ConferencePersonModel createConferencePersonFromCursor(Cursor cursor) {
//		String conferenceID = cursor.getString(0);
//		int personID = cursor.getInt(1);
//		int isSponsor = cursor.getInt(2);
//		int isSpeaker = cursor.getInt(3);
//		int isVideoSharer = cursor.getInt(4);
//		String joinTime = cursor.getString(5);
//		String leaveTime = cursor.getString(6);
//
//		return new ConferencePersonModel(conferenceID, personID, isSponsor,
//				isSpeaker, isVideoSharer, joinTime, leaveTime);
//	}

}
