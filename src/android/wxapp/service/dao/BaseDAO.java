package android.wxapp.service.dao;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.app.ListActivity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class BaseDAO {

	protected DatabaseHelper dbHelper;
	protected SQLiteDatabase db;
	protected Gson gson;
	protected Context c;

	protected BaseDAO(Context context) {
		this.c = context;
		this.dbHelper = DatabaseHelper.getInstance(context);
		this.gson = new Gson();
	}

	protected String getData(Cursor c, String colomnName) {
		return c.getString(c.getColumnIndex(colomnName));

	}

}
