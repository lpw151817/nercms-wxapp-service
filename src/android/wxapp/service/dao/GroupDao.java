package android.wxapp.service.dao;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import com.google.gson.reflect.TypeToken;
import com.imooc.treeview.utils.Node;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract.Contacts.Data;
import android.wxapp.service.jerry.model.group.GroupUpdateQueryRequestGroups;
import android.wxapp.service.jerry.model.group.GroupUpdateQueryRequestIds;
import android.wxapp.service.jerry.model.group.GroupUpdateQueryResponse;
import android.wxapp.service.jerry.model.person.Org;

public class GroupDao extends BaseDAO {

	public GroupDao(Context context) {
		super(context);
	}

	public boolean saveGroupUpdate(GroupUpdateQueryResponse data) {
		for (GroupUpdateQueryRequestGroups item : data.getGs()) {
			if (saveGroupUpdate(item.getGid(), item.getT(), item.getN(), item.getCt(), item.getUt(),
					item.getRids()))
				continue;
			else
				return false;
		}
		return true;
	}

	public boolean saveGroupUpdate(String gid, String t, String n, String ct, String ut,
			List<GroupUpdateQueryRequestIds> rids) {
		db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(DatabaseHelper.FIELD_GROUP_CREATE_TIME, ct);
		values.put(DatabaseHelper.FIELD_GROUP_GROUP_ID, gid);
		values.put(DatabaseHelper.FIELD_GROUP_NAME, n);
		values.put(DatabaseHelper.FIELD_GROUP_RIDS, gson.toJson(rids));
		values.put(DatabaseHelper.FIELD_GROUP_TYPE, t);
		values.put(DatabaseHelper.FIELD_GROUP_UPDATE_TIME, ut);
		return db.insert(DatabaseHelper.TABLE_GROUP, null, values) > 0;
	}

	public boolean modifyGroup(String gid, String t, String n, String ct, String ut,
			List<GroupUpdateQueryRequestIds> rids) {
		db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(DatabaseHelper.FIELD_GROUP_CREATE_TIME, ct);
		values.put(DatabaseHelper.FIELD_GROUP_NAME, n);
		values.put(DatabaseHelper.FIELD_GROUP_RIDS, gson.toJson(rids));
		values.put(DatabaseHelper.FIELD_GROUP_TYPE, t);
		values.put(DatabaseHelper.FIELD_GROUP_UPDATE_TIME, ut);
		return db.update(DatabaseHelper.TABLE_GROUP, values, DatabaseHelper.FIELD_GROUP_GROUP_ID
				+ " = ?", new String[] { gid }) > 0;
	}

	public boolean deleteGroup(String gid) {
		db = dbHelper.getWritableDatabase();
		return db.delete(DatabaseHelper.TABLE_GROUP, DatabaseHelper.FIELD_GROUP_GROUP_ID + " = ?",
				new String[] { gid }) > 0;
	}

	public GroupUpdateQueryRequestGroups queryGroupById(String gid) {
		db = dbHelper.getReadableDatabase();
		Cursor c = db.rawQuery("select * from " + DatabaseHelper.TABLE_GROUP + " where "
				+ DatabaseHelper.FIELD_GROUP_GROUP_ID + " = " + gid, null);
		if (c.moveToFirst()) {
			List<GroupUpdateQueryRequestIds> rids = gson.fromJson(
					getData(c, DatabaseHelper.FIELD_GROUP_RIDS),
					new TypeToken<List<GroupUpdateQueryRequestIds>>() {
					}.getType());
			return new GroupUpdateQueryRequestGroups(gid, getData(c, DatabaseHelper.FIELD_GROUP_TYPE),
					getData(c, DatabaseHelper.FIELD_GROUP_NAME), getData(c,
							DatabaseHelper.FIELD_GROUP_CREATE_TIME), getData(c,
							DatabaseHelper.FIELD_GROUP_UPDATE_TIME), rids);
		}
		return null;
	}

	/**
	 * 
	 * @param type
	 *            （1：基本群组，2：非基本群组，空或者null则查询所有）
	 * @param uid
	 *            查询对应用户id下面的群组，null或者空则表示查询所有
	 * @return
	 */
	public List<GroupUpdateQueryRequestGroups> queryMyAllGroups(String type, String uid) {
		db = dbHelper.getReadableDatabase();
		List<GroupUpdateQueryRequestGroups> result = new ArrayList<GroupUpdateQueryRequestGroups>();
		String sql = "select * from " + DatabaseHelper.TABLE_GROUP;
		if (type != null && !type.equals("")) {
			sql += " where " + DatabaseHelper.FIELD_GROUP_TYPE + " = " + type;
		}
		sql += " order by " + DatabaseHelper.FIELD_GROUP_CREATE_TIME + " DESC";
		Cursor c = db.rawQuery(sql, null);
		if (uid != null && !uid.equals("")) {
			while (c.moveToNext()) {
				List<GroupUpdateQueryRequestIds> rids = gson.fromJson(
						getData(c, DatabaseHelper.FIELD_GROUP_RIDS),
						new TypeToken<List<GroupUpdateQueryRequestIds>>() {
						}.getType());
				if (rids.contains(new GroupUpdateQueryRequestIds(uid)))
					result.add(new GroupUpdateQueryRequestGroups(getData(c,
							DatabaseHelper.FIELD_GROUP_GROUP_ID), getData(c,
							DatabaseHelper.FIELD_GROUP_TYPE),
							getData(c, DatabaseHelper.FIELD_GROUP_NAME), getData(c,
									DatabaseHelper.FIELD_GROUP_CREATE_TIME), getData(c,
									DatabaseHelper.FIELD_GROUP_UPDATE_TIME), rids));
			}
		} else {
			while (c.moveToNext()) {
				List<GroupUpdateQueryRequestIds> rids = gson.fromJson(
						getData(c, DatabaseHelper.FIELD_GROUP_RIDS),
						new TypeToken<List<GroupUpdateQueryRequestIds>>() {
						}.getType());
				result.add(new GroupUpdateQueryRequestGroups(getData(c,
						DatabaseHelper.FIELD_GROUP_GROUP_ID),
						getData(c, DatabaseHelper.FIELD_GROUP_TYPE), getData(c,
								DatabaseHelper.FIELD_GROUP_NAME), getData(c,
								DatabaseHelper.FIELD_GROUP_CREATE_TIME), getData(c,
								DatabaseHelper.FIELD_GROUP_UPDATE_TIME), rids));
			}
		}

		c.close();
		return result;
	}

	// ======================================================

	/**
	 * 
	 * @param type
	 *            （1：基本群组，2：非基本群组，空或者null则查询所有）
	 * @param uid
	 *            查询对应用户id下面的群组，null或者空则表示查询所有
	 * @return
	 */
	public List<Org> queryMyAllGroups2(String type, String uid) {
		db = dbHelper.getReadableDatabase();
		List<Org> result = new ArrayList<Org>();
		result.add(new Org("g2", "0", "群组"));
		String sql = "select * from " + DatabaseHelper.TABLE_GROUP;
		if (type != null && !type.equals("")) {
			sql += " where " + DatabaseHelper.FIELD_GROUP_TYPE + " = " + type;
		}
		sql += " order by " + DatabaseHelper.FIELD_GROUP_CREATE_TIME + " DESC";
		Cursor c = db.rawQuery(sql, null);
		if (uid == null || uid.equals("")) {
			while (c.moveToNext()) {
				result.add(new Org("g" + getData(c, DatabaseHelper.FIELD_GROUP_GROUP_ID), "g2", getData(
						c, DatabaseHelper.FIELD_GROUP_NAME)));
			}
		} else {
			while (c.moveToNext()) {
				List<GroupUpdateQueryRequestIds> rids = gson.fromJson(
						getData(c, DatabaseHelper.FIELD_GROUP_RIDS),
						new TypeToken<List<GroupUpdateQueryRequestIds>>() {
						}.getType());
				if (rids.contains(new GroupUpdateQueryRequestIds(uid)))
					result.add(new Org("g" + getData(c, DatabaseHelper.FIELD_GROUP_GROUP_ID), "g2",
							getData(c, DatabaseHelper.FIELD_GROUP_NAME)));
			}
		}
		c.close();
		return result;
	}
}
