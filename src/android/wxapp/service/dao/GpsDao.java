package android.wxapp.service.dao;

import android.content.ContentValues;
import android.content.Context;
import android.wxapp.service.jerry.model.gps.GpsInfo;
import android.wxapp.service.jerry.model.gps.GpsUpdateQueryResponse;
import android.wxapp.service.jerry.model.gps.GpsUpdateQueryResponseGpss;
import android.wxapp.service.jerry.model.gps.QueryPersonalGpssReponseLocationGps;

public class GpsDao extends BaseDAO {

	// //////
	//
	// gps(id,gid,person_id,time,longitude,latitude,gps_type(1:GPS,2:AGPS,3:ÊÖÌ¨GPS),
	// accuracy,heigh,speed,update_time)
	//
	// //////

	public GpsDao(Context context) {
		super(context);
	}

	public boolean saveGpsUpdate(GpsUpdateQueryResponse data) {
		for (GpsUpdateQueryResponseGpss item : data.getGss()) {
			if (saveGpsUpdate(item))
				continue;
			else
				return false;
		}
		return true;
	}

	private boolean saveGpsUpdate(GpsUpdateQueryResponseGpss data) {
		for (QueryPersonalGpssReponseLocationGps item : data.getGs()) {
			if (saveGpsUpdate(data.getUid(), item.getGid(), item.getG(), item.getT(), item.getUt()))
				continue;
			else
				return false;
		}
		return true;
	}

	public boolean saveGpsUpdate(String uid, String gid, GpsInfo gpsInfo, String type, String updateTime) {
		db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(DatabaseHelper.FIELD_GPS_PERSON_ID, uid);
		values.put(DatabaseHelper.FIELD_GPS_GPS_ID, gid);
		values.put(DatabaseHelper.FIELD_GPS_ACCURACY, gpsInfo.getmAccuracy());
		values.put(DatabaseHelper.FIELD_GPS_HEIGHT, gpsInfo.getmHeight());
		values.put(DatabaseHelper.FIELD_GPS_LAT, gpsInfo.getmLatitude());
		values.put(DatabaseHelper.FIELD_GPS_LONG, gpsInfo.getmLongitude());
		values.put(DatabaseHelper.FIELD_GPS_SPEED, gpsInfo.getmSpeed());
		values.put(DatabaseHelper.FIELD_GPS_TIME, gpsInfo.getmTime());
		values.put(DatabaseHelper.FIELD_GPS_TYPE, type);
		values.put(DatabaseHelper.FIELD_GPS_UPDATE_TIME, updateTime);
		return db.insert(DatabaseHelper.TABLE_GPS, null, values) > 0;
	}

	public boolean saveGpsUpdate(String uid, String gid, String g, String type, String updateTime) {
		GpsInfo gpsInfo = gson.fromJson(g, GpsInfo.class);
		return saveGpsUpdate(uid, gid, gpsInfo, type, updateTime);
	}

}
