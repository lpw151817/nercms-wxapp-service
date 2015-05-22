package android.wxapp.service.request;

import android.content.Context;
import android.wxapp.service.util.MySharedPreference;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class BaseRequest {
	protected Gson gson;
	protected String url;

	public BaseRequest() {
		gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
	}

	protected String getUserId(Context c) {
		return MySharedPreference.get(c, MySharedPreference.USER_ID, "100002");
	}
}
