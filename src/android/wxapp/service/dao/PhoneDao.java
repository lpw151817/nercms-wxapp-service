package android.wxapp.service.dao;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.wxapp.service.model.PhoneModel;

public class PhoneDao {

	private DatabaseHelper dbHelper;
	private static String TAG = "PhoneDao";

	public PhoneDao(Context context) {
		dbHelper = DatabaseHelper.getInstance(context);
	}

	public boolean savePhone(PhoneModel phone) {
		ContentValues values = createContentValues(phone);
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		long id = db.insert(DBConstants.PHONE_TABLE_NAME, null, values);
		if (id == -1) {
			Log.i(TAG, "创建电话失败!");
			return false;
		} else {
			Log.i(TAG, "创建电话成功!");
			return true;
		}
	}

	public PhoneModel getPhoneByID(String phoneID) {
		PhoneModel phone = null;
		Cursor cursor = null;
		try {
			SQLiteDatabase db = dbHelper.getReadableDatabase();
			cursor = db.query(DBConstants.PHONE_TABLE_NAME,
					DBConstants.PHONE_ALL_COLUMNS, " phone_id = ?",
					new String[] { phoneID }, null, null, null);
			if (cursor.moveToFirst()) {
				phone = createPhoneFromCursor(cursor);
			}
		} finally {
			if (cursor != null)
				dbHelper.closeCursor(cursor);
		}
		return phone;
	}

	public void deletePhoneByID(String phoneID) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		long id = db.delete(DBConstants.PHONE_TABLE_NAME, "phone_id = ?",
				new String[] { phoneID });
		if (id == -1) {
			Log.i(TAG, "删除电话失败!");
		} else {
			Log.i(TAG, "删除电话成功!");
		}
	}

	public ArrayList<PhoneModel> getPhoneListByID(String userID) {
		ArrayList<PhoneModel> phoneList = new ArrayList<PhoneModel>();
		Cursor cursor = null;
		try {
			SQLiteDatabase db = dbHelper.getReadableDatabase();
			cursor = db
					.rawQuery(
							"SELECT phone_id,type,caller_id,callee_id,start_time,is_answered,end_time,duration,is_read FROM phone WHERE caller_id = ? OR callee_id = ? order by start_time desc",
							new String[] { userID, userID });
			while (cursor.moveToNext()) {
				phoneList.add(createPhoneFromCursor(cursor));
			}
		} finally {
			if (cursor != null)
				dbHelper.closeCursor(cursor);
		}
		return phoneList;
	}

	private ContentValues createContentValues(PhoneModel phone) {
		ContentValues values = new ContentValues();
		values.put("phone_id", phone.getPhoneID());
		values.put("type", phone.getType());
		values.put("caller_id", phone.getCallerID());
		values.put("callee_id", phone.getCalleeID());
		values.put("start_time", phone.getStartTime());
		values.put("is_answered", phone.getIsAnswered());
		values.put("end_time", phone.getEndTime());
		values.put("duration", phone.getDuration());
		values.put("is_read", phone.getIsRead());

		return values;
	}

	private PhoneModel createPhoneFromCursor(Cursor cursor) {
		String phoneID = cursor.getString(0);
		int type = cursor.getInt(1);
		int callerID = cursor.getInt(2);
		int calleeID = cursor.getInt(3);
		String startTime = cursor.getString(4);
		int isAnswered = cursor.getInt(5);
		String endTime = cursor.getString(6);
		String duration = cursor.getString(7);
		int isRead = cursor.getInt(8);

		return new PhoneModel(phoneID, type, callerID, calleeID, startTime,
				isAnswered, endTime, duration, isRead);

	}

}
