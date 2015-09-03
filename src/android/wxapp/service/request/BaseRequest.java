package android.wxapp.service.request;

import java.net.URLEncoder;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.wxapp.service.util.MySharedPreference;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class BaseRequest {
	protected Gson gson;
	protected String url;

	protected String parase2Json(Object o) {
		return URLEncoder.encode(gson.toJson(o));
	}

	public BaseRequest() {
		gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
	}

	protected String getUserId(Context c) {
		return MySharedPreference.get(c, MySharedPreference.USER_ID, null);
	}

	protected String getLastOrgUpdateTime(Context c) {
		return MySharedPreference.get(c, MySharedPreference.LAST_UPDATE_ORGCODE_TIMESTAMP, null);
	}

	protected String getLastOrgPersonUpdateTime(Context c) {
		return MySharedPreference.get(c, MySharedPreference.LAST_UPDATE_ORGPERSON_TIMESTAMP, null);
	}

	protected String getLastAffairUpdateTime(Context c) {
		return MySharedPreference.get(c, MySharedPreference.LAST_UPDATE_TASK_TIMESTAMP, null);
	}

	protected String getLastMessageUpdateTime(Context context) {
		return MySharedPreference.get(context, MySharedPreference.LAST_UPDATE_MESSAGE_TIMESTAMP, null);
	}

	protected String getUserIc(Context context) {
		return MySharedPreference.get(context, MySharedPreference.USER_IC, null);
	}

	protected String getLastConferenceUpdateTime(Context c) {
		return MySharedPreference.get(c, MySharedPreference.LAST_UPDATE_CONFERENCE_TIMESTAMP, null);
	}

	protected String getLastGroupUpdateTime(Context c) {
		return MySharedPreference.get(c, MySharedPreference.LAST_UPDATE_GROUP_TIMESTAMP, null);
	}

	protected String getLastGpsUpdateTime(Context c) {
		return MySharedPreference.get(c, MySharedPreference.LAST_UPDATE_GPS_TIMESTAMP, null);
	}
}
