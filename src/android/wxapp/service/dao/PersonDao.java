package android.wxapp.service.dao;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.wxapp.service.jerry.model.person.GetOrgCodePersonResponse;
import android.wxapp.service.jerry.model.person.GetOrgCodeResponse;
import android.wxapp.service.jerry.model.person.OrgInfo;
import android.wxapp.service.jerry.model.person.OrgPersonInfo;
import android.wxapp.service.model.ContactModel;
import android.wxapp.service.model.CustomerContactModel;
import android.wxapp.service.model.CustomerModel;
import android.wxapp.service.model.OrgNodeModel;
import android.wxapp.service.model.OrgNodeStaffModel;
import android.wxapp.service.model.OrgStaffModel;
import android.wxapp.service.model.StructuredStaffModel;

public class PersonDao {

	private DatabaseHelper dbHelper;
	private SQLiteDatabase db;

	private static String TAG = "PersonDao";

	public PersonDao(Context context) {
		dbHelper = DatabaseHelper.getInstance(context);
	}

	/**
	 * 判断人员数据表是否为空
	 * 
	 * @return
	 */
	public boolean isDBTNull() {
		Cursor cursor = null;
		try {
			db = dbHelper.getReadableDatabase();
			cursor = db.rawQuery("SELECT COUNT(*) FROM " + DBConstants.ORG_STAFF_TABLE_NAME, null);
			cursor.moveToFirst();
			int i = cursor.getInt(0);
			if (i == 0) {
				return true;
			} else {
				return false;
			}

		} finally {
			if (cursor != null)
				dbHelper.closeCursor(cursor);
		}
	}

	public boolean clearOrgCode() {
		db = dbHelper.getWritableDatabase();
		db.execSQL("delete * from " + DatabaseHelper.TABLE_ORG_CODE);
		return true;
	}

	/**
	 * 存储orgcode jerry 6.1
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

	public boolean clearOrgCodePerson() {
		db = dbHelper.getWritableDatabase();
		db.execSQL("delete * from " + DatabaseHelper.TABLE_ORG_PERSON);
		return true;
	}

	/**
	 * 存储org_code_person jerry 6.1
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

	/**
	 * 以下四个均为添加联系人数据（4个表）到数据库
	 * 
	 * @param orgNode
	 * @return
	 */
	public boolean saveOrgNode(OrgNodeModel orgNode) {
		ContentValues values = createContentValues(orgNode);
		db = dbHelper.getWritableDatabase();
		long id = db.insertOrThrow(DBConstants.ORG_NODE_TABLE_NAME, null, values);
		if (id == -1) {
			Log.i(TAG, "存储机构节点表失败!");
			return false;
		} else {
			Log.i(TAG, "存储机构节点表成功!");
			return true;
		}
	}

	public boolean saveOrgNodeStaff(OrgNodeStaffModel orgNodeStaff) {
		ContentValues values = createContentValues(orgNodeStaff);
		db = dbHelper.getWritableDatabase();
		long id = db.insertOrThrow(DBConstants.ORG_NODE_STAFF_TABLE_NAME, null, values);
		if (id == -1) {
			Log.i(TAG, "存储机构节点成员表失败!");
			return false;
		} else {
			Log.i(TAG, "存储机构节点成员表成功!");
			return true;
		}
	}

	public boolean saveOrgStaff(OrgStaffModel orgStaff) {
		ContentValues values = createContentValues(orgStaff);
		db = dbHelper.getWritableDatabase();
		long id = db.insertOrThrow(DBConstants.ORG_STAFF_TABLE_NAME, null, values);
		if (id == -1) {
			Log.i(TAG, "存储机构联系人表失败!");
			return false;
		} else {
			Log.i(TAG, "存储机构联系人表成功!");
			return true;
		}
	}

	public boolean saveContact(ContactModel contact) {
		ContentValues values = createContentValues(contact);
		db = dbHelper.getWritableDatabase();
		long id = db.insertOrThrow(DBConstants.CONTACT_TABLE_NAME, null, values);
		if (id == -1) {
			Log.i(TAG, "存储联系方式表失败!");
			return false;
		} else {
			Log.i(TAG, "存储联系方式表成功!");
			return true;
		}
	}

	/**
	 * 保存客户信息
	 * 
	 * @param customer
	 * @return
	 */
	public boolean saveCustomer(CustomerModel customer) {
		ContentValues values = createContentValues(customer);
		db = dbHelper.getWritableDatabase();
		long id = db.insertOrThrow(DBConstants.CUSTOMER_TABLE_NAME, null, values);
		if (id == -1) {
			Log.i(TAG, "存储客户表失败!");
			return false;
		} else {
			Log.i(TAG, "存储客户表成功!");
			return true;
		}
	}

	/**
	 * 编辑客户信息
	 * 
	 * @param customer
	 * @return
	 */
	public boolean modifyCostomer(CustomerModel customer) {
		ContentValues values = createContentValues(customer);
		db = dbHelper.getWritableDatabase();
		// 更新Customer表信息
		long id = db.update(DBConstants.CUSTOMER_TABLE_NAME, values, "customer_id = ?",
				new String[] { customer.getCustomerID() });
		// 删除该Customer对应的联系方式信息
		db.delete(DBConstants.CUSTOMER_CONTACT_TABLE_NAME, "customer_id = ?",
				new String[] { customer.getCustomerID() });
		if (id == -1) {
			Log.i(TAG, "编辑客户信息失败!");
			return false;
		} else {
			Log.i(TAG, "编辑客户信息成功!");
			return true;
		}
	}

	/**
	 * 保存客户联系方式信息
	 * 
	 * @param customerContact
	 * @return
	 */
	public boolean saveCustomerContact(CustomerContactModel customerContact) {
		ContentValues values = createContentValues(customerContact);
		db = dbHelper.getWritableDatabase();
		long id = db.insertOrThrow(DBConstants.CUSTOMER_CONTACT_TABLE_NAME, null, values);
		if (id == -1) {
			Log.i(TAG, "存储客户联系方式表失败!");
			return false;
		} else {
			Log.i(TAG, "存储客户联系方式表成功!");
			return true;
		}
	}

	/**
	 * 清空联系人相关4表的数据
	 * 
	 * @return
	 */
	public boolean cleanAllPersonInfo() {

		// 清空联系相关 4+2 表的数据SQL语句
		String cleanCmdText1 = "DELETE FROM " + DBConstants.ORG_NODE_TABLE_NAME;
		String cleanCmdText2 = "DELETE FROM " + DBConstants.ORG_NODE_STAFF_TABLE_NAME;
		String cleanCmdText3 = "DELETE FROM " + DBConstants.ORG_STAFF_TABLE_NAME;
		String cleanCmdText4 = "DELETE FROM " + DBConstants.CONTACT_TABLE_NAME;

		String cleanCmdText5 = "DELETE FROM " + DBConstants.CUSTOMER_TABLE_NAME;
		String cleanCmdText6 = "DELETE FROM " + DBConstants.CUSTOMER_CONTACT_TABLE_NAME;

		// 更新sqlite_sequence表（默认），自增列序号归零
		String updateCmdText1 = "UPDATE sqlite_sequence SET seq = 0 WHERE name='"
				+ DBConstants.ORG_NODE_TABLE_NAME + "'";
		String updateCmdText2 = "UPDATE sqlite_sequence SET seq = 0 WHERE name='"
				+ DBConstants.ORG_NODE_STAFF_TABLE_NAME + "'";
		String updateCmdText3 = "UPDATE sqlite_sequence SET seq = 0 WHERE name='"
				+ DBConstants.ORG_STAFF_TABLE_NAME + "'";
		String updateCmdText4 = "UPDATE sqlite_sequence SET seq = 0 WHERE name='"
				+ DBConstants.CONTACT_TABLE_NAME + "'";

		String updateCmdText5 = "UPDATE sqlite_sequence SET seq = 0 WHERE name='"
				+ DBConstants.CUSTOMER_TABLE_NAME + "'";
		String updateCmdText6 = "UPDATE sqlite_sequence SET seq = 0 WHERE name='"
				+ DBConstants.CUSTOMER_CONTACT_TABLE_NAME + "'";

		// 执行SQL语句
		db = dbHelper.getWritableDatabase();
		try {
			db.execSQL(cleanCmdText1);
			db.execSQL(cleanCmdText2);
			db.execSQL(cleanCmdText3);
			db.execSQL(cleanCmdText4);
			db.execSQL(cleanCmdText5);
			db.execSQL(cleanCmdText6);

			db.execSQL(updateCmdText1);
			db.execSQL(updateCmdText2);
			db.execSQL(updateCmdText3);
			db.execSQL(updateCmdText4);
			db.execSQL(updateCmdText5);
			db.execSQL(updateCmdText6);

			return true;
		} catch (Exception e) {
			Log.v(TAG, e.getMessage());
			return false;
		}
	}

	// ------------------------------------------------------------------------------

	/**
	 * 获取所有的机构节
	 * 
	 * @return 机构节点模型列表
	 */
	public ArrayList<OrgNodeModel> getAllOrgNode() {
		ArrayList<OrgNodeModel> orgList = new ArrayList<OrgNodeModel>();
		Cursor cursor = null;
		try {
			db = dbHelper.getReadableDatabase();
			cursor = db.rawQuery("SELECT org_code, description FROM org_node", null);

			while (cursor.moveToNext()) {
				orgList.add(createOrgNodeFromCursor(cursor));
			}

		} finally {
			if (cursor != null)
				dbHelper.closeCursor(cursor);
		}

		return orgList;
	}

	/**
	 * 获取第二级节点
	 * 
	 * @return 机构节点模型列表
	 */
	public ArrayList<OrgNodeModel> getSecondOrgNode() {
		ArrayList<OrgNodeModel> orgList = new ArrayList<OrgNodeModel>();
		Cursor cursor = null;
		try {
			db = dbHelper.getReadableDatabase();
			cursor = db.rawQuery("SELECT org_code, description FROM org_node WHERE org_code like '1_'",
					null);

			while (cursor.moveToNext()) {
				orgList.add(createOrgNodeFromCursor(cursor));
			}

		} finally {
			if (cursor != null)
				dbHelper.closeCursor(cursor);
		}

		return orgList;
	}

	/**
	 * 获取第三级节点
	 * 
	 * @param secondNodeCode
	 *            二级节点代码
	 * @return 机构节点模型列表
	 */
	public ArrayList<OrgNodeModel> getThirdOrgNode(String secondNodeCode) {
		ArrayList<OrgNodeModel> orgList = new ArrayList<OrgNodeModel>();
		Cursor cursor = null;
		try {
			db = dbHelper.getReadableDatabase();
			cursor = db.rawQuery("SELECT org_code, description FROM org_node WHERE org_code like '"
					+ secondNodeCode + "_'", null);

			while (cursor.moveToNext()) {
				orgList.add(createOrgNodeFromCursor(cursor));
			}

		} finally {
			if (cursor != null)
				dbHelper.closeCursor(cursor);
		}

		return orgList;
	}

	// -------------------------------------------------------------------------------

	/**
	 * 取得不包含Contact联系方式的SSM模型（全部）
	 * 
	 * @return
	 */
	public ArrayList<StructuredStaffModel> getSSMFromAll() {
		ArrayList<StructuredStaffModel> ssmList = new ArrayList<StructuredStaffModel>();
		Cursor cursor = null;
		try {
			db = dbHelper.getReadableDatabase();
			cursor = db.rawQuery(
					"SELECT OS.contact_id, ONN.org_code, ONN.description, ONS.sequence, OS.name, OS.position, OS.rank"
							+ " FROM org_staff OS"
							+ " inner join org_node_staff ONS on OS.contact_id=ONS.contact_id"
							+ " inner join org_node ONN on ONS.org_code=ONN.org_code", null);
			while (cursor.moveToNext()) {
				ssmList.add(createSSMFromCursor(cursor));
			}
		} finally {
			if (cursor != null)
				dbHelper.closeCursor(cursor);
		}

		return ssmList;
	}

	/**
	 * 根据机构节点代码，取得该节点下不包含Contact联系方式的SSM模型（全部）
	 * 
	 * @param orgCode
	 *            机构节点代码
	 * @return SSM列表
	 */
	public ArrayList<StructuredStaffModel> getSSMFromOrgCode(String orgCode) {
		ArrayList<StructuredStaffModel> ssmList = new ArrayList<StructuredStaffModel>();
		Cursor cursor = null;
		try {
			db = dbHelper.getReadableDatabase();
			cursor = db.rawQuery(
					"SELECT OS.contact_id, ONN.org_code, ONN.description, ONS.sequence, OS.name, OS.position, OS.rank"
							+ " FROM org_staff OS"
							+ " inner join org_node_staff ONS on OS.contact_id=ONS.contact_id"
							+ " inner join org_node ONN on ONS.org_code=ONN.org_code "
							+ "WHERE ONN.org_code=" + orgCode, null);
			while (cursor.moveToNext()) {
				ssmList.add(createSSMFromCursor(cursor));
			}
		} finally {
			if (cursor != null)
				dbHelper.closeCursor(cursor);
		}

		return ssmList;
	}

	/**
	 * 取得包含Contact联系方式的SSM模型
	 * 
	 * @param ssmList2
	 *            SSM模型（不含contactList）
	 * @return
	 */
	public ArrayList<StructuredStaffModel> getSSMWithContactFromAll(
			ArrayList<StructuredStaffModel> ssmList2) {
		ArrayList<StructuredStaffModel> ssmList = ssmList2;
		int i = ssmList.size();
		ArrayList<StructuredStaffModel> ssmcList = new ArrayList<StructuredStaffModel>(i);

		Cursor cursor = null;

		try {
			db = dbHelper.getReadableDatabase();
			if (0 != i) {
				for (int j = 0; j < i; j++) {

					ArrayList<ContactModel> contact = new ArrayList<ContactModel>();

					StructuredStaffModel ssm = ssmList.get(j);
					cursor = db.rawQuery(
							"SELECT contact_id, type, content FROM contact WHERE contact_id = ?",
							new String[] { ssm.getContactID() });

					while (cursor.moveToNext()) {
						contact.add(createContactFromCursor(cursor));
					}

					ssm.setContact(contact);
					ssmcList.add(ssm);
				}
			}

		} finally {
			if (cursor != null)
				dbHelper.closeCursor(cursor);
		}

		return ssmcList;
	}

	/**
	 * 根据ContactID取得该联系人的联系方式（列表）
	 * 
	 * @param contactID
	 * @return 联系方式（列表）
	 */
	public ArrayList<ContactModel> getContactListByID(String contactID) {
		ArrayList<ContactModel> contactList = new ArrayList<ContactModel>();
		Cursor cursor = null;
		try {
			db = dbHelper.getReadableDatabase();
			cursor = db.rawQuery("SELECT contact_id, type, content FROM contact WHERE contact_id = ?",
					new String[] { contactID });

			while (cursor.moveToNext()) {
				contactList.add(createContactFromCursor(cursor));
			}
		} finally {
			if (cursor != null)
				dbHelper.closeCursor(cursor);
		}
		return contactList;
	}

	// 2014-5-5
	/**
	 * 取得所有的Customer（不包含CustomerContact联系方式）
	 * 
	 * @return Customer模型列表
	 */
	public ArrayList<CustomerModel> getCustomersByUserID(String userID) {
		ArrayList<CustomerModel> customerList = new ArrayList<CustomerModel>();
		Cursor cursor = null;
		try {
			db = dbHelper.getReadableDatabase();
			cursor = db
					.rawQuery(
							"SELECT customer_id, name, unit, description, contact_id FROM customer WHERE contact_id = ?",
							new String[] { userID });
			while (cursor.moveToNext()) {
				customerList.add(createCustomerFromCursor(cursor));
			}
		} finally {
			if (cursor != null)
				dbHelper.closeCursor(cursor);
		}

		return customerList;
	}

	/**
	 * 根据客户ID，取得该客户的联系方式（多种联系方式）
	 * 
	 * @param customerID
	 *            客户ID
	 * @return 该客户的联系方式列表
	 */
	public ArrayList<CustomerContactModel> getCustomerContact(String customerID) {
		ArrayList<CustomerContactModel> customerContactList = new ArrayList<CustomerContactModel>();
		Cursor cursor = null;
		try {
			db = dbHelper.getReadableDatabase();
			cursor = db.rawQuery(
					"SELECT customer_id, type, content FROM customer_contact WHERE customer_id = ?",
					new String[] { customerID });
			while (cursor.moveToNext()) {
				customerContactList.add(creatCustomerContactFromCursor(cursor));
			}

		} finally {
			if (cursor != null) {
				dbHelper.closeCursor(cursor);
			}
		}

		return customerContactList;
	}

	/**
	 * 删除客户信息
	 * 
	 * @param customerID
	 *            客户ID
	 */
	public void deleteCustomer(String customerID) {
		db = dbHelper.getWritableDatabase();
		long id = db.delete(DBConstants.CUSTOMER_TABLE_NAME, "customer_id = ?",
				new String[] { customerID });
		if (id == -1) {
			Log.i(TAG, "删除客户失败!");
		} else {
			Log.i(TAG, "删除客户成功!");
		}
	}

	// 2014-5-20
	/**
	 * 根据用户ID取得该用户的SSM模型
	 * 
	 * @param userID
	 * @return
	 */
	public StructuredStaffModel getSSMByID(String userID) {
		Cursor cursor = null;
		try {
			db = dbHelper.getReadableDatabase();
			cursor = db.rawQuery(
					"SELECT OS.contact_id, ONN.org_code, ONN.description, ONS.sequence, OS.name, OS.position, OS.rank"
							+ " FROM org_staff OS"
							+ " inner join org_node_staff ONS on OS.contact_id=ONS.contact_id"
							+ " inner join org_node ONN on ONS.org_code=ONN.org_code"
							+ " WHERE OS.contact_id = ?", new String[] { userID });

			if (cursor.moveToFirst()) {
				return createSSMFromCursor(cursor);
			} else {
				return null;
			}

		} finally {
			if (cursor != null) {
				dbHelper.closeCursor(cursor);
			}
		}

	}

	/**
	 * 根据客户ID取得该用户的Customer模型 20140620
	 * 
	 * @param customerID
	 * @return
	 */
	public CustomerModel getCustomerByID(String customerID) {
		Cursor cursor = null;
		try {
			db = dbHelper.getReadableDatabase();
			cursor = db
					.rawQuery(
							"SELECT customer_id, name, unit, description, contact_id FROM customer WHERE customer_id = ?",
							new String[] { customerID });
			if (cursor.moveToFirst()) {
				return createCustomerFromCursor(cursor);
			} else {
				return null;
			}
		} finally {
			if (cursor != null) {
				dbHelper.closeCursor(cursor);
			}
		}

	}

	// 2014-7-30
	/**
	 * 根据OrgID获取OrgNode模型
	 * 
	 * @param orgID
	 * @return
	 */

	public OrgNodeModel getOrgNodeByOrgID(String orgID) {
		Cursor cursor = null;
		try {
			db = dbHelper.getReadableDatabase();
			cursor = db.rawQuery("SELECT org_code, description FROM org_node WHERE org_code = ?",
					new String[] { orgID });
			if (cursor.moveToFirst()) {
				return createOrgNodeFromCursor(cursor);
			} else {
				return null;
			}
		} finally {
			if (cursor != null) {
				dbHelper.closeCursor(cursor);
			}
		}
	}

	// 2014-7-31
	/**
	 * 根据人员ID得到人员名字（用于仅需要名字显示的场景）
	 * 
	 * @param personID
	 * @return
	 */
	public String getPersonNameByID(String personID) {
		Cursor cursor = null;
		try {
			db = dbHelper.getReadableDatabase();
			cursor = db.rawQuery("SELECT name " + " FROM org_staff " + " WHERE contact_id = ?",
					new String[] { personID });

			if (cursor.moveToFirst()) {
				return cursor.getString(0);
			} else {
				return null;
			}

		} finally {
			if (cursor != null) {
				dbHelper.closeCursor(cursor);
			}
		}
	}

	// ------------------------------------------------------------------------------

	private ContentValues createContentValues(OrgNodeModel orgNode) {
		ContentValues values = new ContentValues();
		values.put("org_code", orgNode.getOrgCode());
		values.put("description", orgNode.getDescription());

		return values;
	}

	private ContentValues createContentValues(OrgNodeStaffModel orgNodeStaff) {
		ContentValues values = new ContentValues();
		values.put("org_code", orgNodeStaff.getOrgCode());
		values.put("contact_id", orgNodeStaff.getContactID());
		values.put("sequence", orgNodeStaff.getSequence());

		return values;
	}

	private ContentValues createContentValues(OrgStaffModel orgStaff) {
		ContentValues values = new ContentValues();
		values.put("contact_id", orgStaff.getContactID());
		values.put("name", orgStaff.getName());
		values.put("position", orgStaff.getPosition());
		values.put("rank", orgStaff.getRank());

		return values;
	}

	private ContentValues createContentValues(ContactModel contact) {
		ContentValues values = new ContentValues();
		values.put("contact_id", contact.getContactID());
		values.put("type", contact.getType());
		values.put("content", contact.getContent());

		return values;
	}

	private ContentValues createContentValues(CustomerModel customer) {
		ContentValues values = new ContentValues();
		values.put("customer_id", customer.getCustomerID());
		values.put("name", customer.getName());
		values.put("unit", customer.getUnit());
		values.put("description", customer.getDescription());
		values.put("contact_id", customer.getContactID());

		return values;
	}

	private ContentValues createContentValues(CustomerContactModel customerContact) {
		ContentValues values = new ContentValues();
		values.put("customer_id", customerContact.getCustomerID());
		values.put("type", customerContact.getType());
		values.put("content", customerContact.getContent());

		return values;
	}

	// 由cursor创建SSM结构化人员模型(不含contactList)
	private StructuredStaffModel createSSMFromCursor(Cursor cursor) {
		String contactID = cursor.getString(0);
		String orgCode = cursor.getString(1);
		String orgDescription = cursor.getString(2);
		String sequence = cursor.getString(3);
		String name = cursor.getString(4);
		String position = cursor.getString(5);
		String rank = cursor.getString(6);

		return new StructuredStaffModel(contactID, orgCode, orgDescription, sequence, name, position,
				rank);
	}

	// 由cursor创建Contact模型
	private ContactModel createContactFromCursor(Cursor cursor) {
		String contactID = cursor.getString(0);
		int type = cursor.getInt(1);
		String content = cursor.getString(2);

		return new ContactModel(contactID, type, content);
	}

	// 由cursor创建OrgNode模型
	private OrgNodeModel createOrgNodeFromCursor(Cursor cursor) {
		String orgCode = cursor.getString(0);
		String description = cursor.getString(1);

		return new OrgNodeModel(orgCode, description);
	}

	private CustomerModel createCustomerFromCursor(Cursor cursor) {
		String customerID = cursor.getString(0);
		String name = cursor.getString(1);
		String unit = cursor.getString(2);
		String description = cursor.getString(3);
		String contactID = cursor.getString(4);

		return new CustomerModel(customerID, name, unit, description, contactID);
	}

	private CustomerContactModel creatCustomerContactFromCursor(Cursor cursor) {
		String customerID = cursor.getString(0);
		int type = cursor.getInt(1);
		String content = cursor.getString(2);

		return new CustomerContactModel(customerID, type, content);
	}
}
