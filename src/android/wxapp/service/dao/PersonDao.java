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
	 * �ж���Ա���ݱ��Ƿ�Ϊ��
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
	 * �洢orgcode jerry 6.1
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
				Log.i(TAG, "�洢�����ڵ��ʧ��!");
				return false;
			} else {
				continue;
			}
		}
		Log.i(TAG, "�洢�����ڵ��ɹ�!");
		return true;
	}

	public boolean clearOrgCodePerson() {
		db = dbHelper.getWritableDatabase();
		db.execSQL("delete * from " + DatabaseHelper.TABLE_ORG_PERSON);
		return true;
	}

	/**
	 * �洢org_code_person jerry 6.1
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
				Log.i(TAG, "�洢�����ڵ���Ա��ʧ��!");
				return false;
			} else {
				continue;
			}
		}
		Log.i(TAG, "�洢�����ڵ���Ա��ɹ�!");
		return true;
	}

	/**
	 * �����ĸ���Ϊ�����ϵ�����ݣ�4���������ݿ�
	 * 
	 * @param orgNode
	 * @return
	 */
	public boolean saveOrgNode(OrgNodeModel orgNode) {
		ContentValues values = createContentValues(orgNode);
		db = dbHelper.getWritableDatabase();
		long id = db.insertOrThrow(DBConstants.ORG_NODE_TABLE_NAME, null, values);
		if (id == -1) {
			Log.i(TAG, "�洢�����ڵ��ʧ��!");
			return false;
		} else {
			Log.i(TAG, "�洢�����ڵ��ɹ�!");
			return true;
		}
	}

	public boolean saveOrgNodeStaff(OrgNodeStaffModel orgNodeStaff) {
		ContentValues values = createContentValues(orgNodeStaff);
		db = dbHelper.getWritableDatabase();
		long id = db.insertOrThrow(DBConstants.ORG_NODE_STAFF_TABLE_NAME, null, values);
		if (id == -1) {
			Log.i(TAG, "�洢�����ڵ��Ա��ʧ��!");
			return false;
		} else {
			Log.i(TAG, "�洢�����ڵ��Ա��ɹ�!");
			return true;
		}
	}

	public boolean saveOrgStaff(OrgStaffModel orgStaff) {
		ContentValues values = createContentValues(orgStaff);
		db = dbHelper.getWritableDatabase();
		long id = db.insertOrThrow(DBConstants.ORG_STAFF_TABLE_NAME, null, values);
		if (id == -1) {
			Log.i(TAG, "�洢������ϵ�˱�ʧ��!");
			return false;
		} else {
			Log.i(TAG, "�洢������ϵ�˱�ɹ�!");
			return true;
		}
	}

	public boolean saveContact(ContactModel contact) {
		ContentValues values = createContentValues(contact);
		db = dbHelper.getWritableDatabase();
		long id = db.insertOrThrow(DBConstants.CONTACT_TABLE_NAME, null, values);
		if (id == -1) {
			Log.i(TAG, "�洢��ϵ��ʽ��ʧ��!");
			return false;
		} else {
			Log.i(TAG, "�洢��ϵ��ʽ��ɹ�!");
			return true;
		}
	}

	/**
	 * ����ͻ���Ϣ
	 * 
	 * @param customer
	 * @return
	 */
	public boolean saveCustomer(CustomerModel customer) {
		ContentValues values = createContentValues(customer);
		db = dbHelper.getWritableDatabase();
		long id = db.insertOrThrow(DBConstants.CUSTOMER_TABLE_NAME, null, values);
		if (id == -1) {
			Log.i(TAG, "�洢�ͻ���ʧ��!");
			return false;
		} else {
			Log.i(TAG, "�洢�ͻ���ɹ�!");
			return true;
		}
	}

	/**
	 * �༭�ͻ���Ϣ
	 * 
	 * @param customer
	 * @return
	 */
	public boolean modifyCostomer(CustomerModel customer) {
		ContentValues values = createContentValues(customer);
		db = dbHelper.getWritableDatabase();
		// ����Customer����Ϣ
		long id = db.update(DBConstants.CUSTOMER_TABLE_NAME, values, "customer_id = ?",
				new String[] { customer.getCustomerID() });
		// ɾ����Customer��Ӧ����ϵ��ʽ��Ϣ
		db.delete(DBConstants.CUSTOMER_CONTACT_TABLE_NAME, "customer_id = ?",
				new String[] { customer.getCustomerID() });
		if (id == -1) {
			Log.i(TAG, "�༭�ͻ���Ϣʧ��!");
			return false;
		} else {
			Log.i(TAG, "�༭�ͻ���Ϣ�ɹ�!");
			return true;
		}
	}

	/**
	 * ����ͻ���ϵ��ʽ��Ϣ
	 * 
	 * @param customerContact
	 * @return
	 */
	public boolean saveCustomerContact(CustomerContactModel customerContact) {
		ContentValues values = createContentValues(customerContact);
		db = dbHelper.getWritableDatabase();
		long id = db.insertOrThrow(DBConstants.CUSTOMER_CONTACT_TABLE_NAME, null, values);
		if (id == -1) {
			Log.i(TAG, "�洢�ͻ���ϵ��ʽ��ʧ��!");
			return false;
		} else {
			Log.i(TAG, "�洢�ͻ���ϵ��ʽ��ɹ�!");
			return true;
		}
	}

	/**
	 * �����ϵ�����4�������
	 * 
	 * @return
	 */
	public boolean cleanAllPersonInfo() {

		// �����ϵ��� 4+2 �������SQL���
		String cleanCmdText1 = "DELETE FROM " + DBConstants.ORG_NODE_TABLE_NAME;
		String cleanCmdText2 = "DELETE FROM " + DBConstants.ORG_NODE_STAFF_TABLE_NAME;
		String cleanCmdText3 = "DELETE FROM " + DBConstants.ORG_STAFF_TABLE_NAME;
		String cleanCmdText4 = "DELETE FROM " + DBConstants.CONTACT_TABLE_NAME;

		String cleanCmdText5 = "DELETE FROM " + DBConstants.CUSTOMER_TABLE_NAME;
		String cleanCmdText6 = "DELETE FROM " + DBConstants.CUSTOMER_CONTACT_TABLE_NAME;

		// ����sqlite_sequence��Ĭ�ϣ�����������Ź���
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

		// ִ��SQL���
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
	 * ��ȡ���еĻ�����
	 * 
	 * @return �����ڵ�ģ���б�
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
	 * ��ȡ�ڶ����ڵ�
	 * 
	 * @return �����ڵ�ģ���б�
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
	 * ��ȡ�������ڵ�
	 * 
	 * @param secondNodeCode
	 *            �����ڵ����
	 * @return �����ڵ�ģ���б�
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
	 * ȡ�ò�����Contact��ϵ��ʽ��SSMģ�ͣ�ȫ����
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
	 * ���ݻ����ڵ���룬ȡ�øýڵ��²�����Contact��ϵ��ʽ��SSMģ�ͣ�ȫ����
	 * 
	 * @param orgCode
	 *            �����ڵ����
	 * @return SSM�б�
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
	 * ȡ�ð���Contact��ϵ��ʽ��SSMģ��
	 * 
	 * @param ssmList2
	 *            SSMģ�ͣ�����contactList��
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
	 * ����ContactIDȡ�ø���ϵ�˵���ϵ��ʽ���б�
	 * 
	 * @param contactID
	 * @return ��ϵ��ʽ���б�
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
	 * ȡ�����е�Customer��������CustomerContact��ϵ��ʽ��
	 * 
	 * @return Customerģ���б�
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
	 * ���ݿͻ�ID��ȡ�øÿͻ�����ϵ��ʽ��������ϵ��ʽ��
	 * 
	 * @param customerID
	 *            �ͻ�ID
	 * @return �ÿͻ�����ϵ��ʽ�б�
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
	 * ɾ���ͻ���Ϣ
	 * 
	 * @param customerID
	 *            �ͻ�ID
	 */
	public void deleteCustomer(String customerID) {
		db = dbHelper.getWritableDatabase();
		long id = db.delete(DBConstants.CUSTOMER_TABLE_NAME, "customer_id = ?",
				new String[] { customerID });
		if (id == -1) {
			Log.i(TAG, "ɾ���ͻ�ʧ��!");
		} else {
			Log.i(TAG, "ɾ���ͻ��ɹ�!");
		}
	}

	// 2014-5-20
	/**
	 * �����û�IDȡ�ø��û���SSMģ��
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
	 * ���ݿͻ�IDȡ�ø��û���Customerģ�� 20140620
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
	 * ����OrgID��ȡOrgNodeģ��
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
	 * ������ԱID�õ���Ա���֣����ڽ���Ҫ������ʾ�ĳ�����
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

	// ��cursor����SSM�ṹ����Աģ��(����contactList)
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

	// ��cursor����Contactģ��
	private ContactModel createContactFromCursor(Cursor cursor) {
		String contactID = cursor.getString(0);
		int type = cursor.getInt(1);
		String content = cursor.getString(2);

		return new ContactModel(contactID, type, content);
	}

	// ��cursor����OrgNodeģ��
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
