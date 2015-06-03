package android.wxapp.service.dao;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.app.ListActivity;
import android.database.Cursor;

public class BaseDAO {
	protected String getData(Cursor c, String colomnName) {
		return c.getString(c.getColumnIndex(colomnName));

	}

	protected <T> List<T> json2List(String js, Class<T> t) {
		return new Gson().fromJson(js, new TypeToken<List<T>>() {
		}.getType());
	}
}
