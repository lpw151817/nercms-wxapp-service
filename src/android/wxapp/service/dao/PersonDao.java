package android.wxapp.service.dao;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.util.TypedValue;
import android.wxapp.service.jerry.model.person.Contacts;
import android.wxapp.service.jerry.model.person.GetOrgCodePersonResponse;
import android.wxapp.service.jerry.model.person.GetOrgCodeResponse;
import android.wxapp.service.jerry.model.person.GetPersonInfoResponse;
import android.wxapp.service.jerry.model.person.Org;
import android.wxapp.service.jerry.model.person.OrgInfo;
import android.wxapp.service.jerry.model.person.OrgPersonInfo;
import android.wxapp.service.model.ContactModel;
import android.wxapp.service.model.CustomerContactModel;
import android.wxapp.service.model.CustomerModel;
import android.wxapp.service.model.OrgNodeModel;
import android.wxapp.service.model.OrgNodeStaffModel;
import android.wxapp.service.model.OrgStaffModel;
import android.wxapp.service.model.StructuredStaffModel;

public class PersonDao extends BaseDAO {

	private static String TAG = "PersonDao";

	public PersonDao(Context context) {
		super(context);
	}

	public boolean clearOrgCode() {
		db = dbHelper.getWritableDatabase();
		db.execSQL("delete * from " + DatabaseHelper.TABLE_ORG_CODE);
		return true;
	}

	/**
	 * 存储orgcode jerry 6.1 *
	 * <p>
	 * BUG:当服务器修改手机本地已经有的数据时，手机本地应该去修改数据，而不是去插入数据
	 * 
	 * @param r
	 * @return
	 */
	public boolean saveOrgCode(GetOrgCodeResponse r) {
		db = dbHelper.getWritableDatabase();
		long i = 0;
		ContentValues v = new ContentValues();
		for (OrgInfo item : r.getOrg_codes()) {
			v.clear();
			v.put(DatabaseHelper.FIELD_ORG_CODE_DESCRIPTION, item.getD());
			v.put(DatabaseHelper.FIELD_ORG_CODE_ORG_CODE, item.getOc());
			i = db.insertOrThrow(DatabaseHelper.TABLE_ORG_CODE, null, v);
			if (i == -1) {
				Log.i(TAG, "存储机构节点表失败!");
				return false;
			} else {
				continue;
			}
		}
		Log.i(TAG, "存储机构节点表成功!");
		return true;
	}

	/**
	 * 获取第一层结点
	 * 
	 * @return
	 */
	public List<OrgInfo> getOrgCodeInfosFirst() {
		db = dbHelper.getReadableDatabase();
		// SELECT * from OrgNode WHERE org_code LIKE '_';
		Cursor c = db.rawQuery("SELECT * from " + DatabaseHelper.TABLE_ORG_CODE + " WHERE "
				+ DatabaseHelper.FIELD_ORG_CODE_ORG_CODE + " LIKE '" + "_';", null);
		List<OrgInfo> result = new ArrayList<OrgInfo>();
		while (c.moveToNext()) {
			result.add(new OrgInfo(getData(c, DatabaseHelper.FIELD_ORG_CODE_ORG_CODE),
					getData(c, DatabaseHelper.FIELD_ORG_CODE_DESCRIPTION)));
		}
		c.close();
		return result;
	}

	/**
	 * 查询对应oc下面的组织结点 jerry 6.5
	 * 
	 * @param oc
	 *            待查询的orgcode
	 * @return
	 */
	public List<OrgInfo> getOrgCodeInfos(String oc) {
		db = dbHelper.getReadableDatabase();
		// SELECT * from OrgNode WHERE org_code LIKE '1_';
		Cursor c = db.rawQuery("SELECT * from " + DatabaseHelper.TABLE_ORG_CODE + " WHERE "
				+ DatabaseHelper.FIELD_ORG_CODE_ORG_CODE + " LIKE '" + oc + "_';", null);
		List<OrgInfo> result = new ArrayList<OrgInfo>();
		while (c.moveToNext()) {
			result.add(new OrgInfo(getData(c, DatabaseHelper.FIELD_ORG_CODE_ORG_CODE),
					getData(c, DatabaseHelper.FIELD_ORG_CODE_DESCRIPTION)));
		}
		c.close();
		return result;
	}

	public boolean clearOrgCodePerson() {
		db = dbHelper.getWritableDatabase();
		db.execSQL("delete * from " + DatabaseHelper.TABLE_ORG_PERSON);
		return true;
	}

	/**
	 * 存储org_code_person jerry 6.1
	 * <p>
	 * BUG:当服务器修改手机本地已经有的数据时，手机本地应该去修改数据，而不是去插入数据
	 * 
	 * @param r
	 * @return
	 */
	public boolean saveOrgCodePerson(GetOrgCodePersonResponse r) {
		db = dbHelper.getWritableDatabase();
		long i = 0;
		ContentValues v = new ContentValues();
		for (OrgPersonInfo item : r.getPersons()) {
			v.clear();
			v.put(DatabaseHelper.FIELD_ORG_PERSON_USER_ID, item.getUid());
			v.put(DatabaseHelper.FIELD_ORG_PERSON_USER_NAME, item.getUn());
			v.put(DatabaseHelper.FIELD_ORG_PERSON_ORG_CODE, item.getOc());
			v.put(DatabaseHelper.FIELD_ORG_PERSON_CONTACTS, gson.toJson(item.getContacts()));
			v.put(DatabaseHelper.FIELD_ORG_PERSON_REMARK, item.getR());
			v.put(DatabaseHelper.FIELD_ORG_PERSON_NAME, item.getN());
			i = db.insertOrThrow(DatabaseHelper.TABLE_ORG_PERSON, null, v);
			if (i == -1) {
				Log.i(TAG, "存储机构节点人员表失败!");
				return false;
			} else {
				continue;
			}
		}
		Log.i(TAG, "存储机构节点人员表成功!");
		return true;
	}

	public String getOrgDes(String orgId) {
		db = dbHelper.getReadableDatabase();
		Cursor c = db.rawQuery("select * from " + DatabaseHelper.TABLE_ORG_CODE + " where "
				+ DatabaseHelper.FIELD_ORG_CODE_ORG_CODE + " = ?", new String[] { orgId });
		if (c.moveToFirst())
			return getData(c, DatabaseHelper.FIELD_ORG_CODE_DESCRIPTION);
		else
			return null;

	}

	/**
	 * 查询对应oc下面的的人员信息 jerry 6.5
	 * 
	 * @param oc
	 * @return
	 */
	public List<OrgPersonInfo> getOrgPersonInfos(String oc) {
		db = dbHelper.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT * from " + DatabaseHelper.TABLE_ORG_PERSON + " WHERE "
				+ DatabaseHelper.FIELD_ORG_PERSON_ORG_CODE + " = ?", new String[] { oc });
		List<OrgPersonInfo> result = new ArrayList<OrgPersonInfo>();
		while (c.moveToNext()) {
			List<Contacts> tempContacts = gson.fromJson(
					getData(c, DatabaseHelper.FIELD_ORG_PERSON_CONTACTS),
					new TypeToken<List<Contacts>>() {
					}.getType());
			result.add(new OrgPersonInfo(getData(c, DatabaseHelper.FIELD_ORG_PERSON_USER_ID),
					getData(c, DatabaseHelper.FIELD_ORG_PERSON_USER_NAME), oc,
					getData(c, DatabaseHelper.FIELD_ORG_PERSON_NAME),
					getData(c, DatabaseHelper.FIELD_ORG_PERSON_REMARK), tempContacts));
		}
		c.close();
		return result;
	}

	public String getOrgByPersonId(String uid) {
		db = dbHelper.getReadableDatabase();
		// 选出对应uid的oc
		String sql = "select * from " + DatabaseHelper.TABLE_ORG_CODE + " where "
				+ DatabaseHelper.FIELD_ORG_CODE_ORG_CODE + "= (select "
				+ DatabaseHelper.FIELD_ORG_PERSON_ORG_CODE + " from "
				+ DatabaseHelper.TABLE_ORG_PERSON + " where "
				+ DatabaseHelper.FIELD_ORG_PERSON_USER_ID + " = " + uid + " )";
		Log.e("PersonDao SQL", sql);
		Cursor c = db.rawQuery(sql, null);
		String result = "";
		if (c.moveToFirst()) {
			result = getData(c, DatabaseHelper.FIELD_ORG_CODE_DESCRIPTION);
		}
		c.close();
		return result;
	}

	/**
	 * 通过userid查询人员信息
	 * 
	 * @param userid
	 * @return
	 */
	public GetPersonInfoResponse getPersonInfo(String userid) {
		db = dbHelper.getReadableDatabase();
		GetPersonInfoResponse result = null;
		Cursor c = db.rawQuery("select * from " + DatabaseHelper.TABLE_ORG_PERSON + " where "
				+ DatabaseHelper.FIELD_ORG_PERSON_USER_ID + " = " + userid, null);
		if (c.moveToFirst()) {
			String temp = getData(c, DatabaseHelper.FIELD_ORG_PERSON_USER_NAME);
			List<Contacts> tempContacts = gson.fromJson(
					getData(c, DatabaseHelper.FIELD_ORG_PERSON_CONTACTS),
					new TypeToken<List<Contacts>>() {
					}.getType());
			result = new GetPersonInfoResponse("", temp,
					getData(c, DatabaseHelper.FIELD_ORG_PERSON_NAME), "",
					getData(c, DatabaseHelper.FIELD_ORG_PERSON_REMARK), tempContacts);
		}
		c.close();
		return result;
	}

	/**
	 * 保存客户信息 jerry 6.3
	 * 
	 * @param customer
	 * @return
	 */
	public boolean saveCustomer(GetPersonInfoResponse customer) {
		db = dbHelper.getWritableDatabase();
		ContentValues v = new ContentValues();
		v.put(DatabaseHelper.FIELD_MY_INFO_CONTACTS, new Gson().toJson(customer.getContacts()));
		v.put(DatabaseHelper.FIELD_MY_INFO_DES, customer.getD());
		v.put(DatabaseHelper.FIELD_MY_INFO_NAME, customer.getN());
		v.put(DatabaseHelper.FIELD_MY_INFO_REMARK, customer.getR());
		v.put(DatabaseHelper.FIELD_MY_INFO_USERNAME, customer.getUn());
		long id = db.insertOrThrow(DatabaseHelper.TABLE_MY_INFO, null, v);
		if (id == -1) {
			Log.i(TAG, "存储客户表失败!");
			return false;
		} else {
			Log.i(TAG, "存储客户表成功!");
			return true;
		}
	}

	/**
	 * 获取客户信息 jerry 6.3
	 * 
	 * @return
	 */
	public GetPersonInfoResponse getCustomer() {
		db = dbHelper.getReadableDatabase();
		Cursor c = db.rawQuery("select * from " + DatabaseHelper.TABLE_MY_INFO, null);
		if (c.getCount() <= 0) {
			c.close();
			return null;
		} else {
			if (c.moveToFirst()) {
				String temp = getData(c, DatabaseHelper.FIELD_MY_INFO_CONTACTS);
				List<Contacts> paramContacts = new Gson().fromJson(temp,
						new TypeToken<List<Contacts>>() {
						}.getType());
				GetPersonInfoResponse r = new GetPersonInfoResponse(0 + "",
						getData(c, DatabaseHelper.FIELD_MY_INFO_USERNAME),
						getData(c, DatabaseHelper.FIELD_MY_INFO_NAME),
						getData(c, DatabaseHelper.FIELD_MY_INFO_DES),
						getData(c, DatabaseHelper.FIELD_MY_INFO_REMARK), paramContacts);
				c.close();
				return r;
			}
			c.close();
			return null;
		}
	}

	// /////////////////以下用于组织结构树使用//////////////////////////
	public List<Org> getOrg2() {
		List<Org> result = new ArrayList<Org>();
		result.addAll(getAllOrgs());
		result.addAll(getAllPersons());
		return result;
	}

	private List<Org> getAllOrgs() {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		// SELECT * from OrgNode WHERE org_code LIKE '_';
		Cursor c = db.rawQuery("SELECT * from " + DatabaseHelper.TABLE_ORG_CODE, null);
		List<Org> r = new ArrayList<Org>();
		while (c.moveToNext()) {
			String name = getData(c, DatabaseHelper.FIELD_ORG_CODE_DESCRIPTION);
			String org_code = getData(c, DatabaseHelper.FIELD_ORG_CODE_ORG_CODE);
			if (org_code.length() == 1)
				r.add(new Org("o" + org_code, 0 + "", name));
			else
				r.add(new Org("o" + org_code, "o" + org_code.substring(0, org_code.length() - 1),
						name));
		}
		c.close();
		return r;
	}

	private List<Org> getAllPersons() {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		// SELECT * from OrgNode WHERE org_code LIKE '_';
		Cursor c = db.rawQuery("SELECT * from " + DatabaseHelper.TABLE_ORG_PERSON, null);
		List<Org> r = new ArrayList<Org>();
		while (c.moveToNext()) {
			String orgcode = getData(c, DatabaseHelper.FIELD_ORG_PERSON_ORG_CODE);
			String name = getData(c, DatabaseHelper.FIELD_ORG_PERSON_NAME);
			String uid = getData(c, DatabaseHelper.FIELD_ORG_PERSON_USER_ID);
			r.add(new Org("p" + uid, "o" + orgcode, name));
		}
		c.close();
		return r;
	}

	// ///////////////////////////////////////////

	/**
	 * 以下四个均为添加联系人数据（4个表）到数据库
	 * 
	 * @param orgNode
	 * @return
	 */
	@Deprecated
	public boolean saveOrgNode(OrgNodeModel orgNode) {
		// ContentValues values = createContentValues(orgNode);
		// db = dbHelper.getWritableDatabase();
		// long id = db.insertOrThrow(DBConstants.ORG_NODE_TABLE_NAME, null,
		// values);
		// if (id == -1) {
		// Log.i(TAG, "存储机构节点表失败!");
		// return false;
		// } else {
		// Log.i(TAG, "存储机构节点表成功!");
		// return true;
		// }
		return false;
	}

	@Deprecated
	public boolean saveOrgNodeStaff(OrgNodeStaffModel orgNodeStaff) {
		// ContentValues values = createContentValues(orgNodeStaff);
		// db = dbHelper.getWritableDatabase();
		// long id = db.insertOrThrow(DBConstants.ORG_NODE_STAFF_TABLE_NAME,
		// null, values);
		// if (id == -1) {
		// Log.i(TAG, "存储机构节点成员表失败!");
		// return false;
		// } else {
		// Log.i(TAG, "存储机构节点成员表成功!");
		// return true;
		// }
		return false;
	}

	@Deprecated
	public boolean saveOrgStaff(OrgStaffModel orgStaff) {
		// ContentValues values = createContentValues(orgStaff);
		// db = dbHelper.getWritableDatabase();
		// long id = db.insertOrThrow(DBConstants.ORG_STAFF_TABLE_NAME, null,
		// values);
		// if (id == -1) {
		// Log.i(TAG, "存储机构联系人表失败!");
		// return false;
		// } else {
		// Log.i(TAG, "存储机构联系人表成功!");
		// return true;
		// }
		return false;
	}

	@Deprecated
	public boolean saveContact(ContactModel contact) {
		// ContentValues values = createContentValues(contact);
		// db = dbHelper.getWritableDatabase();
		// long id = db.insertOrThrow(DBConstants.CONTACT_TABLE_NAME, null,
		// values);
		// if (id == -1) {
		// Log.i(TAG, "存储联系方式表失败!");
		// return false;
		// } else {
		// Log.i(TAG, "存储联系方式表成功!");
		// return true;
		// }
		return false;
	}

	/**
	 * 判断人员数据表是否为空
	 * 
	 * @return
	 */
	@Deprecated
	public boolean isDBTNull() {
		// Cursor cursor = null;
		// try {
		// db = dbHelper.getReadableDatabase();
		// cursor = db.rawQuery("SELECT COUNT(*) FROM " +
		// DBConstants.ORG_STAFF_TABLE_NAME, null);
		// cursor.moveToFirst();
		// int i = cursor.getInt(0);
		// if (i == 0) {
		// return true;
		// } else {
		// return false;
		// }
		//
		// } finally {
		// if (cursor != null)
		// dbHelper.closeCursor(cursor);
		// }
		return false;
	}

	/**
	 * 编辑客户信息
	 * 
	 * @param customer
	 * @return
	 */
	@Deprecated
	public boolean modifyCostomer(CustomerModel customer) {
		// ContentValues values = createContentValues(customer);
		// db = dbHelper.getWritableDatabase();
		// // 更新Customer表信息
		// long id = db.update(DBConstants.CUSTOMER_TABLE_NAME, values,
		// "customer_id = ?",
		// new String[] { customer.getCustomerID() });
		// // 删除该Customer对应的联系方式信息
		// db.delete(DBConstants.CUSTOMER_CONTACT_TABLE_NAME, "customer_id = ?",
		// new String[] { customer.getCustomerID() });
		// if (id == -1) {
		// Log.i(TAG, "编辑客户信息失败!");
		// return false;
		// } else {
		// Log.i(TAG, "编辑客户信息成功!");
		// return true;
		// }
		return false;
	}

	/**
	 * 保存客户联系方式信息
	 * 
	 * @param customerContact
	 * @return
	 */
	@Deprecated
	public boolean saveCustomerContact(CustomerContactModel customerContact) {
		// ContentValues values = createContentValues(customerContact);
		// db = dbHelper.getWritableDatabase();
		// long id = db.insertOrThrow(DBConstants.CUSTOMER_CONTACT_TABLE_NAME,
		// null, values);
		// if (id == -1) {
		// Log.i(TAG, "存储客户联系方式表失败!");
		// return false;
		// } else {
		// Log.i(TAG, "存储客户联系方式表成功!");
		// return true;
		// }
		return false;
	}

	/**
	 * 清空联系人相关4表的数据
	 * 
	 * @return
	 */
	@Deprecated
	public boolean cleanAllPersonInfo() {

		// // 清空联系相关 4+2 表的数据SQL语句
		// String cleanCmdText1 = "DELETE FROM " +
		// DBConstants.ORG_NODE_TABLE_NAME;
		// String cleanCmdText2 = "DELETE FROM " +
		// DBConstants.ORG_NODE_STAFF_TABLE_NAME;
		// String cleanCmdText3 = "DELETE FROM " +
		// DBConstants.ORG_STAFF_TABLE_NAME;
		// String cleanCmdText4 = "DELETE FROM " +
		// DBConstants.CONTACT_TABLE_NAME;
		//
		// String cleanCmdText5 = "DELETE FROM " +
		// DBConstants.CUSTOMER_TABLE_NAME;
		// String cleanCmdText6 = "DELETE FROM " +
		// DBConstants.CUSTOMER_CONTACT_TABLE_NAME;
		//
		// // 更新sqlite_sequence表（默认），自增列序号归零
		// String updateCmdText1 =
		// "UPDATE sqlite_sequence SET seq = 0 WHERE name='"
		// + DBConstants.ORG_NODE_TABLE_NAME + "'";
		// String updateCmdText2 =
		// "UPDATE sqlite_sequence SET seq = 0 WHERE name='"
		// + DBConstants.ORG_NODE_STAFF_TABLE_NAME + "'";
		// String updateCmdText3 =
		// "UPDATE sqlite_sequence SET seq = 0 WHERE name='"
		// + DBConstants.ORG_STAFF_TABLE_NAME + "'";
		// String updateCmdText4 =
		// "UPDATE sqlite_sequence SET seq = 0 WHERE name='"
		// + DBConstants.CONTACT_TABLE_NAME + "'";
		//
		// String updateCmdText5 =
		// "UPDATE sqlite_sequence SET seq = 0 WHERE name='"
		// + DBConstants.CUSTOMER_TABLE_NAME + "'";
		// String updateCmdText6 =
		// "UPDATE sqlite_sequence SET seq = 0 WHERE name='"
		// + DBConstants.CUSTOMER_CONTACT_TABLE_NAME + "'";
		//
		// // 执行SQL语句
		// db = dbHelper.getWritableDatabase();
		// try {
		// db.execSQL(cleanCmdText1);
		// db.execSQL(cleanCmdText2);
		// db.execSQL(cleanCmdText3);
		// db.execSQL(cleanCmdText4);
		// db.execSQL(cleanCmdText5);
		// db.execSQL(cleanCmdText6);
		//
		// db.execSQL(updateCmdText1);
		// db.execSQL(updateCmdText2);
		// db.execSQL(updateCmdText3);
		// db.execSQL(updateCmdText4);
		// db.execSQL(updateCmdText5);
		// db.execSQL(updateCmdText6);
		//
		// return true;
		// } catch (Exception e) {
		// Log.v(TAG, e.getMessage());
		// return false;
		// }
		return false;
	}

	// ------------------------------------------------------------------------------

	/**
	 * 获取所有的机构节
	 * 
	 * @return 机构节点模型列表
	 */
	@Deprecated
	public ArrayList<OrgNodeModel> getAllOrgNode() {
		// ArrayList<OrgNodeModel> orgList = new ArrayList<OrgNodeModel>();
		// Cursor cursor = null;
		// try {
		// db = dbHelper.getReadableDatabase();
		// cursor = db.rawQuery("SELECT org_code, description FROM org_node",
		// null);
		//
		// while (cursor.moveToNext()) {
		// orgList.add(createOrgNodeFromCursor(cursor));
		// }
		//
		// } finally {
		// if (cursor != null)
		// dbHelper.closeCursor(cursor);
		// }
		//
		// return orgList;
		return null;
	}

	/**
	 * 获取第二级节点
	 * 
	 * @return 机构节点模型列表
	 */
	@Deprecated
	public ArrayList<OrgNodeModel> getSecondOrgNode() {
		// ArrayList<OrgNodeModel> orgList = new ArrayList<OrgNodeModel>();
		// Cursor cursor = null;
		// try {
		// db = dbHelper.getReadableDatabase();
		// cursor =
		// db.rawQuery("SELECT org_code, description FROM org_node WHERE
		// org_code like '1_'",
		// null);
		//
		// while (cursor.moveToNext()) {
		// orgList.add(createOrgNodeFromCursor(cursor));
		// }
		//
		// } finally {
		// if (cursor != null)
		// dbHelper.closeCursor(cursor);
		// }
		//
		// return orgList;
		return null;
	}

	/**
	 * 获取第三级节点
	 * 
	 * @param secondNodeCode
	 *            二级节点代码
	 * @return 机构节点模型列表
	 */
	@Deprecated
	public ArrayList<OrgNodeModel> getThirdOrgNode(String secondNodeCode) {
		// ArrayList<OrgNodeModel> orgList = new ArrayList<OrgNodeModel>();
		// Cursor cursor = null;
		// try {
		// db = dbHelper.getReadableDatabase();
		// cursor =
		// db.rawQuery("SELECT org_code, description FROM org_node WHERE
		// org_code like '"
		// + secondNodeCode + "_'", null);
		//
		// while (cursor.moveToNext()) {
		// orgList.add(createOrgNodeFromCursor(cursor));
		// }
		//
		// } finally {
		// if (cursor != null)
		// dbHelper.closeCursor(cursor);
		// }
		//
		// return orgList;
		return null;
	}

	// -------------------------------------------------------------------------------

	/**
	 * 取得不包含Contact联系方式的SSM模型（全部）
	 * 
	 * @return
	 */
	@Deprecated
	public ArrayList<StructuredStaffModel> getSSMFromAll() {
		// ArrayList<StructuredStaffModel> ssmList = new
		// ArrayList<StructuredStaffModel>();
		// Cursor cursor = null;
		// try {
		// db = dbHelper.getReadableDatabase();
		// cursor = db.rawQuery(
		// "SELECT OS.contact_id, ONN.org_code, ONN.description, ONS.sequence,
		// OS.name, OS.position, OS.rank"
		// + " FROM org_staff OS"
		// + " inner join org_node_staff ONS on OS.contact_id=ONS.contact_id"
		// + " inner join org_node ONN on ONS.org_code=ONN.org_code", null);
		// while (cursor.moveToNext()) {
		// ssmList.add(createSSMFromCursor(cursor));
		// }
		// } finally {
		// if (cursor != null)
		// dbHelper.closeCursor(cursor);
		// }
		//
		// return ssmList;
		return null;
	}

	/**
	 * 根据机构节点代码，取得该节点下不包含Contact联系方式的SSM模型（全部）
	 * 
	 * @param orgCode
	 *            机构节点代码
	 * @return SSM列表
	 */
	@Deprecated
	public ArrayList<StructuredStaffModel> getSSMFromOrgCode(String orgCode) {
		// ArrayList<StructuredStaffModel> ssmList = new
		// ArrayList<StructuredStaffModel>();
		// Cursor cursor = null;
		// try {
		// db = dbHelper.getReadableDatabase();
		// cursor = db.rawQuery(
		// "SELECT OS.contact_id, ONN.org_code, ONN.description, ONS.sequence,
		// OS.name, OS.position, OS.rank"
		// + " FROM org_staff OS"
		// + " inner join org_node_staff ONS on OS.contact_id=ONS.contact_id"
		// + " inner join org_node ONN on ONS.org_code=ONN.org_code "
		// + "WHERE ONN.org_code=" + orgCode, null);
		// while (cursor.moveToNext()) {
		// ssmList.add(createSSMFromCursor(cursor));
		// }
		// } finally {
		// if (cursor != null)
		// dbHelper.closeCursor(cursor);
		// }
		//
		// return ssmList;
		return null;
	}

	/**
	 * 取得包含Contact联系方式的SSM模型
	 * 
	 * @param ssmList2
	 *            SSM模型（不含contactList）
	 * @return
	 */
	@Deprecated
	public ArrayList<StructuredStaffModel> getSSMWithContactFromAll(
			ArrayList<StructuredStaffModel> ssmList2) {
		// ArrayList<StructuredStaffModel> ssmList = ssmList2;
		// int i = ssmList.size();
		// ArrayList<StructuredStaffModel> ssmcList = new
		// ArrayList<StructuredStaffModel>(i);
		//
		// Cursor cursor = null;
		//
		// try {
		// db = dbHelper.getReadableDatabase();
		// if (0 != i) {
		// for (int j = 0; j < i; j++) {
		//
		// ArrayList<ContactModel> contact = new ArrayList<ContactModel>();
		//
		// StructuredStaffModel ssm = ssmList.get(j);
		// cursor = db.rawQuery(
		// "SELECT contact_id, type, content FROM contact WHERE contact_id = ?",
		// new String[] { ssm.getContactID() });
		//
		// while (cursor.moveToNext()) {
		// contact.add(createContactFromCursor(cursor));
		// }
		//
		// ssm.setContact(contact);
		// ssmcList.add(ssm);
		// }
		// }
		//
		// } finally {
		// if (cursor != null)
		// dbHelper.closeCursor(cursor);
		// }
		//
		// return ssmcList;
		return null;
	}

	/**
	 * 根据ContactID取得该联系人的联系方式（列表）
	 * 
	 * @param contactID
	 * @return 联系方式（列表）
	 */
	@Deprecated
	public ArrayList<ContactModel> getContactListByID(String contactID) {
		// ArrayList<ContactModel> contactList = new ArrayList<ContactModel>();
		// Cursor cursor = null;
		// try {
		// db = dbHelper.getReadableDatabase();
		// cursor =
		// db.rawQuery("SELECT contact_id, type, content FROM contact WHERE
		// contact_id = ?",
		// new String[] { contactID });
		//
		// while (cursor.moveToNext()) {
		// contactList.add(createContactFromCursor(cursor));
		// }
		// } finally {
		// if (cursor != null)
		// dbHelper.closeCursor(cursor);
		// }
		// return contactList;
		return null;
	}

	// 2014-5-5
	/**
	 * 取得所有的Customer（不包含CustomerContact联系方式）
	 * 
	 * @return Customer模型列表
	 */
	@Deprecated
	public ArrayList<CustomerModel> getCustomersByUserID(String userID) {
		// ArrayList<CustomerModel> customerList = new
		// ArrayList<CustomerModel>();
		// Cursor cursor = null;
		// try {
		// db = dbHelper.getReadableDatabase();
		// cursor = db
		// .rawQuery(
		// "SELECT customer_id, name, unit, description, contact_id FROM
		// customer WHERE contact_id = ?",
		// new String[] { userID });
		// while (cursor.moveToNext()) {
		// customerList.add(createCustomerFromCursor(cursor));
		// }
		// } finally {
		// if (cursor != null)
		// dbHelper.closeCursor(cursor);
		// }
		//
		// return customerList;
		return null;
	}

	/**
	 * 根据客户ID，取得该客户的联系方式（多种联系方式）
	 * 
	 * @param customerID
	 *            客户ID
	 * @return 该客户的联系方式列表
	 */
	@Deprecated
	public ArrayList<CustomerContactModel> getCustomerContact(String customerID) {
		// ArrayList<CustomerContactModel> customerContactList = new
		// ArrayList<CustomerContactModel>();
		// Cursor cursor = null;
		// try {
		// db = dbHelper.getReadableDatabase();
		// cursor = db.rawQuery(
		// "SELECT customer_id, type, content FROM customer_contact WHERE
		// customer_id = ?",
		// new String[] { customerID });
		// while (cursor.moveToNext()) {
		// customerContactList.add(creatCustomerContactFromCursor(cursor));
		// }
		//
		// } finally {
		// if (cursor != null) {
		// dbHelper.closeCursor(cursor);
		// }
		// }
		//
		// return customerContactList;
		return null;
	}

	/**
	 * 删除客户信息
	 * 
	 * @param customerID
	 *            客户ID
	 */
	@Deprecated
	public void deleteCustomer(String customerID) {
		// db = dbHelper.getWritableDatabase();
		// long id = db.delete(DBConstants.CUSTOMER_TABLE_NAME,
		// "customer_id = ?",
		// new String[] { customerID });
		// if (id == -1) {
		// Log.i(TAG, "删除客户失败!");
		// } else {
		// Log.i(TAG, "删除客户成功!");
		// }
	}

	// 2014-5-20
	/**
	 * 根据用户ID取得该用户的SSM模型
	 * 
	 * @param userID
	 * @return
	 */
	@Deprecated
	public StructuredStaffModel getSSMByID(String userID) {
		// Cursor cursor = null;
		// try {
		// db = dbHelper.getReadableDatabase();
		// cursor = db.rawQuery(
		// "SELECT OS.contact_id, ONN.org_code, ONN.description, ONS.sequence,
		// OS.name, OS.position, OS.rank"
		// + " FROM org_staff OS"
		// + " inner join org_node_staff ONS on OS.contact_id=ONS.contact_id"
		// + " inner join org_node ONN on ONS.org_code=ONN.org_code"
		// + " WHERE OS.contact_id = ?", new String[] { userID });
		//
		// if (cursor.moveToFirst()) {
		// return createSSMFromCursor(cursor);
		// } else {
		// return null;
		// }
		//
		// } finally {
		// if (cursor != null) {
		// dbHelper.closeCursor(cursor);
		// }
		// }
		return null;
	}

	/**
	 * 根据客户ID取得该用户的Customer模型 20140620
	 * 
	 * @param customerID
	 * @return
	 */
	@Deprecated
	public CustomerModel getCustomerByID(String customerID) {
		// Cursor cursor = null;
		// try {
		// db = dbHelper.getReadableDatabase();
		// cursor = db
		// .rawQuery(
		// "SELECT customer_id, name, unit, description, contact_id FROM
		// customer WHERE customer_id = ?",
		// new String[] { customerID });
		// if (cursor.moveToFirst()) {
		// return createCustomerFromCursor(cursor);
		// } else {
		// return null;
		// }
		// } finally {
		// if (cursor != null) {
		// dbHelper.closeCursor(cursor);
		// }
		// }
		return null;
	}

	// 2014-7-30
	/**
	 * 根据OrgID获取OrgNode模型
	 * 
	 * @param orgID
	 * @return
	 */
	@Deprecated
	public OrgNodeModel getOrgNodeByOrgID(String orgID) {
		// Cursor cursor = null;
		// try {
		// db = dbHelper.getReadableDatabase();
		// cursor =
		// db.rawQuery("SELECT org_code, description FROM org_node WHERE
		// org_code = ?",
		// new String[] { orgID });
		// if (cursor.moveToFirst()) {
		// return createOrgNodeFromCursor(cursor);
		// } else {
		// return null;
		// }
		// } finally {
		// if (cursor != null) {
		// dbHelper.closeCursor(cursor);
		// }
		// }
		return null;
	}

	// ------------------------------------------------------------------------------

	// private ContentValues createContentValues(OrgNodeModel orgNode) {
	// ContentValues values = new ContentValues();
	// values.put("org_code", orgNode.getOrgCode());
	// values.put("description", orgNode.getDescription());
	//
	// return values;
	// }
	//
	// private ContentValues createContentValues(OrgNodeStaffModel orgNodeStaff)
	// {
	// ContentValues values = new ContentValues();
	// values.put("org_code", orgNodeStaff.getOrgCode());
	// values.put("contact_id", orgNodeStaff.getContactID());
	// values.put("sequence", orgNodeStaff.getSequence());
	//
	// return values;
	// }
	//
	// private ContentValues createContentValues(OrgStaffModel orgStaff) {
	// ContentValues values = new ContentValues();
	// values.put("contact_id", orgStaff.getContactID());
	// values.put("name", orgStaff.getName());
	// values.put("position", orgStaff.getPosition());
	// values.put("rank", orgStaff.getRank());
	//
	// return values;
	// }
	//
	// private ContentValues createContentValues(ContactModel contact) {
	// ContentValues values = new ContentValues();
	// values.put("contact_id", contact.getContactID());
	// values.put("type", contact.getType());
	// values.put("content", contact.getContent());
	//
	// return values;
	// }
	//
	// private ContentValues createContentValues(CustomerModel customer) {
	// ContentValues values = new ContentValues();
	// values.put("customer_id", customer.getCustomerID());
	// values.put("name", customer.getName());
	// values.put("unit", customer.getUnit());
	// values.put("description", customer.getDescription());
	// values.put("contact_id", customer.getContactID());
	//
	// return values;
	// }
	//
	// private ContentValues createContentValues(CustomerContactModel
	// customerContact) {
	// ContentValues values = new ContentValues();
	// values.put("customer_id", customerContact.getCustomerID());
	// values.put("type", customerContact.getType());
	// values.put("content", customerContact.getContent());
	//
	// return values;
	// }
	//
	// // 由cursor创建SSM结构化人员模型(不含contactList)
	// private StructuredStaffModel createSSMFromCursor(Cursor cursor) {
	// String contactID = cursor.getString(0);
	// String orgCode = cursor.getString(1);
	// String orgDescription = cursor.getString(2);
	// String sequence = cursor.getString(3);
	// String name = cursor.getString(4);
	// String position = cursor.getString(5);
	// String rank = cursor.getString(6);
	//
	// return new StructuredStaffModel(contactID, orgCode, orgDescription,
	// sequence, name, position,
	// rank);
	// }
	//
	// // 由cursor创建Contact模型
	// private ContactModel createContactFromCursor(Cursor cursor) {
	// String contactID = cursor.getString(0);
	// int type = cursor.getInt(1);
	// String content = cursor.getString(2);
	//
	// return new ContactModel(contactID, type, content);
	// }
	//
	// // 由cursor创建OrgNode模型
	// private OrgNodeModel createOrgNodeFromCursor(Cursor cursor) {
	// String orgCode = cursor.getString(0);
	// String description = cursor.getString(1);
	//
	// return new OrgNodeModel(orgCode, description);
	// }
	//
	// private CustomerModel createCustomerFromCursor(Cursor cursor) {
	// String customerID = cursor.getString(0);
	// String name = cursor.getString(1);
	// String unit = cursor.getString(2);
	// String description = cursor.getString(3);
	// String contactID = cursor.getString(4);
	//
	// return new CustomerModel(customerID, name, unit, description, contactID);
	// }
	//
	// private CustomerContactModel creatCustomerContactFromCursor(Cursor
	// cursor) {
	// String customerID = cursor.getString(0);
	// int type = cursor.getInt(1);
	// String content = cursor.getString(2);
	//
	// return new CustomerContactModel(customerID, type, content);
	// }
}
