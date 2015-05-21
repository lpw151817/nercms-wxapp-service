package android.wxapp.service.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.wxapp.service.model.PersonOnDutyModel;

public class PersonOnDutyDao {

	private DatabaseHelper dbHelper;

	private static String TAG = "PersonOnDutyDAO";

	public PersonOnDutyDao(Context context) {
		dbHelper = DatabaseHelper.getInstance(context);
	}

	/**
	 * 保存事务责任人到数据库
	 * 
	 * @param person
	 * @return
	 */
	public boolean savePersonOnDuty(PersonOnDutyModel person) {
		ContentValues values = createContentValues(person);
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		long id = db.insertOrThrow(DBConstants.PERSON_ON_DUTY_TABLE_NAME, null,
				values);
		if (-1 == id) {
			Log.i(TAG, "创建责任人失败!");
			return false;
		} else {
			Log.i(TAG, "创建责任人成功!");
			return true;
		}

	}

	/**
	 * 根据事务ID获取该事务的责任人
	 * 
	 * @param affairID
	 * @return
	 */
	public PersonOnDutyModel getPersonByAffairID(String affairID) {
		PersonOnDutyModel person = null;
		Cursor cursor = null;
		try {
			SQLiteDatabase db = dbHelper.getReadableDatabase();
			cursor = db.query(DBConstants.PERSON_ON_DUTY_TABLE_NAME,
					DBConstants.PERSON_ON_DUTY_ALL_COLUMNS, "affair_id = ?",
					new String[] { affairID }, null, null, null);
			if (cursor.moveToFirst()) {
				return createPersonFromCursor(cursor);
			} else {
				return null;
			}
		} finally {
			if (cursor != null) {
				dbHelper.closeCursor(cursor);
			}
		}
	}

	// -------------------------------------------------------------------

	private ContentValues createContentValues(PersonOnDutyModel person) {
		ContentValues values = new ContentValues();
		values.put("affair_id", person.getAffairID());
		values.put("person_id", person.getPersonID());
		return values;
	}

	private PersonOnDutyModel createPersonFromCursor(Cursor cursor) {
		String affairID = cursor.getString(0);
		int personID = cursor.getInt(1);
		return new PersonOnDutyModel(affairID, personID);
	}
}
