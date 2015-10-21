package android.wxapp.service.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * ��ṹ����
 * <p>
 * org_code(id,org_code,description);
 * <p>
 * org_code_person(id,user_id,user_name,org_code,remark,contacts,name);
 * <p>
 * myinfo(id,user_name,name,description,remark,contacts);
 * <p>
 * affair(id,affiarid,type ��1������ 2����ʾ 3��֪ͨ��, senderid,description, topic,
 * createtime, endtime, completetime, lastoperatetype��1-�½���2-����ɣ��ֶ�����3-�������Զ���
 * ��4-�޸Ľ�ֹ���ڣ�, lastoperatetime, updatetime,readtime, attachment [ {
 * "at":"XXXXXXXX", //�������ͣ�1���ı� 2��ͼƬ3��¼��4��¼��5 ��GPS�� "u":"XXXXXXXX" //�������� },�� ]
 * )
 * <p>
 * person_on_duty(id,affair_id,person_id,type(1:�����ˣ�2:������),update_time)
 * <p>
 * attachment(id,attachment_id,type(1���ı� 2��ͼƬ3��¼��4��¼��5��GPS),url)
 * <p>
 * message(id,mid,type,sender_id,relation_id,send_time,content,attachment_type,
 * attachment_url,update_time,isread)
 * <p>
 * conference(id,cid,name,sponsor_id,,convene_time,from��1��ͨ���ֻ���2��ͨ��Web��PC���ƶ��նˣ���
 * 3 ��ͨ��PC�ͻ��ˣ�4��ϵͳ�Զ����ã�5��������,start_time(����ʵ�ʿ�ʼʱ��),end_time,remark)
 * <p>
 * ConferencePerson(id,cid,uid,type//��Ա��ɫ��1�������ˣ�2�������ˣ�3����ƵԴ��4������+��ƵԴ��,
 * response_time,remark)
 * <p>
 * group(id,gid,type��1������Ⱥ�飬2���ǻ���Ⱥ�飩,name,create_time,update_time,rids)
 * <p>
 * gps(id,gid,person_id,time,longitude,latitude,gps_type(1:GPS,2:AGPS,3:��̨GPS),
 * accuracy,heigh,speed,update_time)
 * 
 * @author JerryLiu
 * @time 2015-6-1
 */
public class DatabaseHelper extends SQLiteOpenHelper {

	// �汾
	public static final int VERSION = 2;

	private static final String LOG_TAG = "DatabaseHelper";

	// ���ݿ���
	public static final String DATABASE_NAME = "App.db";

	// Jerry 6.1
	// ���� org_code
	public static final String TABLE_ORG_CODE = "org_code";
	// �ֶ�
	public static final String FIELD_ORG_CODE_ID = "id";
	public static final String FIELD_ORG_CODE_ORG_CODE = "org_code";
	public static final String FIELD_ORG_CODE_DESCRIPTION = "description";
	// �������SQL
	public static final String SQL_ORG_CODE_CREATE_TABLE = "create table " + TABLE_ORG_CODE + " ("
			+ FIELD_ORG_CODE_ID + " integer primary key autoincrement, " + FIELD_ORG_CODE_ORG_CODE
			+ " text," + FIELD_ORG_CODE_DESCRIPTION + " text)";

	// ���� org_code_person
	public static final String TABLE_ORG_PERSON = "org_code_person";
	// �ֶ�
	public static final String FIELD_ORG_PERSON_ID = "id";
	public static final String FIELD_ORG_PERSON_ORG_CODE = "org_code";
	public static final String FIELD_ORG_PERSON_USER_ID = "user_id";
	public static final String FIELD_ORG_PERSON_USER_NAME = "user_name";
	public static final String FIELD_ORG_PERSON_REMARK = "remark";
	public static final String FIELD_ORG_PERSON_CONTACTS = "contacts";
	public static final String FIELD_ORG_PERSON_NAME = "name";
	public static final String SQL_ORG_PERSON_CREATE_TABLE = "create table " + TABLE_ORG_PERSON + " ("
			+ FIELD_ORG_PERSON_ID + " integer primary key autoincrement, " + FIELD_ORG_PERSON_USER_ID
			+ " text," + FIELD_ORG_PERSON_ORG_CODE + " text," + FIELD_ORG_PERSON_REMARK + " text,"
			+ FIELD_ORG_PERSON_CONTACTS + " text," + FIELD_ORG_PERSON_USER_NAME + " text, "
			+ FIELD_ORG_PERSON_NAME + " text)";

	// ���� myinfo
	public static final String TABLE_MY_INFO = "myinfo";
	// �ֶ�
	public static final String FIELD_MY_INFO_ID = "id";
	public static final String FIELD_MY_INFO_USERNAME = "user_name";
	public static final String FIELD_MY_INFO_NAME = "name";
	public static final String FIELD_MY_INFO_DES = "description";
	public static final String FIELD_MY_INFO_REMARK = "remark";
	public static final String FIELD_MY_INFO_CONTACTS = "contacts";
	// �������SQL
	public static final String SQL_MY_INFO_CREATE_TABLE = "create table " + TABLE_MY_INFO + " ("
			+ FIELD_MY_INFO_ID + " integer primary key autoincrement, " + FIELD_MY_INFO_USERNAME
			+ " text," + FIELD_MY_INFO_NAME + " text," + FIELD_MY_INFO_DES + " text,"
			+ FIELD_MY_INFO_REMARK + " text," + FIELD_MY_INFO_CONTACTS + " text)";

	// ���� affiar
	public static final String TABLE_AFFIARINFO = "affair_info";
	// �ֶ�
	public static final String FIELD_AFFIARINFO_ID = "id";
	public static final String FIELD_AFFIARINFO_AFFAIR_ID = "affiarid";
	// ��1������ 2����ʾ 3��֪ͨ��
	public static final String FIELD_AFFIARINFO_TYPE = "type";
	public static final String FIELD_AFFIARINFO_SENDERID = "senderid";
	public static final String FIELD_AFFIARINFO_DES = "description";
	public static final String FIELD_AFFIARINFO_TOPIC = "topic";
	public static final String FIELD_AFFIARINFO_CREATETIME = "createtime";
	public static final String FIELD_AFFIARINFO_ENDTIME = "endtime";
	public static final String FIELD_AFFIARINFO_COMPLETETIME = "completetime";
	public static final String FIELD_AFFIARINFO_READTIME = "readtime";
	// ��1-�½���2-����ɣ��ֶ�����3-�������Զ��� ��4-�޸Ľ�ֹ���ڣ�
	public static final String FIELD_AFFIARINFO_LAST_OPERATE_TYPE = "lastoperatetype";
	public static final String FIELD_AFFIARINFO_LAST_OPERATE_TIME = "lastoperatetime";
	public static final String FIELD_AFFIARINFO_UPDATETIME = "updatetime";
	/**
	 * [ { "at":"XXXXXXXX", //�������ͣ�1���ı� 2��ͼƬ3��¼��4��¼��5 ��GPS�� "u":"XXXXXXXX"
	 * //�������� },�� ] )
	 */
	public static final String FIELD_AFFIARINFO_ATTACHMENT = "attachment";
	// �������SQL
	public static final String SQL_AFFAIRINFO_CREATE_TABLE = "create table " + TABLE_AFFIARINFO + " ("
			+ FIELD_AFFIARINFO_ID + " integer primary key autoincrement, " + FIELD_AFFIARINFO_AFFAIR_ID
			+ " text," + FIELD_AFFIARINFO_TYPE + " text," + FIELD_AFFIARINFO_SENDERID + " text,"
			+ FIELD_AFFIARINFO_DES + " text," + FIELD_AFFIARINFO_TOPIC + " text,"
			+ FIELD_AFFIARINFO_CREATETIME + " text," + FIELD_AFFIARINFO_ENDTIME + " text,"
			+ FIELD_AFFIARINFO_COMPLETETIME + " text," + FIELD_AFFIARINFO_READTIME + " text,"
			+ FIELD_AFFIARINFO_LAST_OPERATE_TYPE + " text," + FIELD_AFFIARINFO_LAST_OPERATE_TIME
			+ " text," + FIELD_AFFIARINFO_UPDATETIME + " text," + FIELD_AFFIARINFO_ATTACHMENT + " text)";

	// ���� personOnDuty
	public static final String TABLE_PERSON_ON_DUTY = "person_on_duty";
	// �ֶ�
	public static final String FIELD_POD_ID = "id";
	public static final String FIELD_POD_AID = "affair_id";
	public static final String FIELD_POD_PID = "person_id";
	// (1:�����ˣ�2:������)
	public static final String FIELD_POD_TYPE = "type";
	public static final String FIELD_POD_UT = "update_time";
	// �������SQL
	public static final String SQL_POD_CREATE_TABLE = "create table " + TABLE_PERSON_ON_DUTY + " ("
			+ FIELD_POD_ID + " integer primary key autoincrement, " + FIELD_POD_AID + " text,"
			+ FIELD_POD_PID + " text," + FIELD_POD_TYPE + " text," + FIELD_POD_UT + " text)";

	// ���� attachment
	public static final String TABLE_ATTACHMENT = "attachment";
	// �ֶ�
	public static final String FIELD_ATTACHMENT_ID = "id";
	public static final String FIELD_ATTACHMENT_ATTACHMENT_ID = "attachment_id";
	// (1���ı� 2��ͼƬ3��¼��4��¼��5��GPS)
	public static final String FIELD_ATTACHMENT_TYPE = "type";
	public static final String FIELD_ATTACHMENT_URL = "url";
	// �������SQL
	public static final String SQL_ATTACHMENT_CREATE_TABLE = "create table " + TABLE_ATTACHMENT + " ("
			+ FIELD_ATTACHMENT_ID + " integer primary key autoincrement, "
			+ FIELD_ATTACHMENT_ATTACHMENT_ID + " text," + FIELD_ATTACHMENT_TYPE + " text,"
			+ FIELD_ATTACHMENT_URL + " text)";

	// ���� Message
	public static final String TABLE_MESSAGE = "message";
	// �ֶ�
	public static final String FIELD_MESSAGE_ID = "id";
	public static final String FIELD_MESSAGE_MESSAGE_ID = "mid";
	// ��Ϣ����0.��ͨ����������Ϣ1.����Ⱥ����Ϣ 2.�ǻ���Ⱥ����Ϣ3.�����¼��Ϣ 4.��������Ϣ
	public static final String FIELD_MESSAGE_TYPE = "type";
	public static final String FIELD_MESSAGE_SENDER_ID = "sender_id";
	public static final String FIELD_MESSAGE_RELATION_ID = "relation_id";
	public static final String FIELD_MESSAGE_SEND_TIME = "send_time";
	public static final String FIELD_MESSAGE_CONTENT = "content";
	// (1���ı� 2��ͼƬ3��¼��4��¼��5��GPS)
	public static final String FIELD_MESSAGE_ATTACHMENT_TYPE = "attachment_type";
	public static final String FIELD_MESSAGE_ATTACHMENT_URL = "attachment_url";
	public static final String FIELD_MESSAGE_UPDATE_TIME = "update_time";
	public static final String FIELD_MESSAGE_READTIME = "readtime";
	// �������SQL
	public static final String SQL_MESSAGE_CREATE_TABLE = "create table " + TABLE_MESSAGE + " ("
			+ FIELD_MESSAGE_ID + " integer primary key autoincrement, " + FIELD_MESSAGE_MESSAGE_ID
			+ " text," + FIELD_MESSAGE_TYPE + " text," + FIELD_MESSAGE_SENDER_ID + " text,"
			+ FIELD_MESSAGE_RELATION_ID + " text," + FIELD_MESSAGE_SEND_TIME + " text,"
			+ FIELD_MESSAGE_CONTENT + " text," + FIELD_MESSAGE_ATTACHMENT_TYPE + " text,"
			+ FIELD_MESSAGE_ATTACHMENT_URL + " text," + FIELD_MESSAGE_UPDATE_TIME + " text,"
			+ FIELD_MESSAGE_READTIME + " text)";

	// ���� conference
	public static final String TABLE_CONFERENCE = "conference";
	// �ֶ�
	public static final String FIELD_CONFERENCE_ID = "id";
	public static final String FIELD_CONFERENCE_CONFERENCE_ID = "cid";
	public static final String FIELD_CONFERENCE_NAME = "name";
	public static final String FIELD_CONFERENCE_SPONSORID = "sponsor_id";
	public static final String FIELD_CONFERENCE_CONVENE_TIME = "convene_time";
	// ��1��ͨ���ֻ���2��ͨ��Web��PC���ƶ��նˣ���3 ��ͨ��PC�ͻ��ˣ�4��ϵͳ�Զ����ã�5��������
	public static final String FIELD_CONFERENCE_FROM = "type";
	public static final String FIELD_CONFERENCE_START_TIME = "start_time";
	public static final String FIELD_CONFERENCE_ENDTIME = "end_time";
	public static final String FIELD_CONFERENCE_REMARK = "remark";
	// �������SQL
	public static final String SQL_CONFERENCE_CREATE_TABLE = "create table " + TABLE_CONFERENCE + " ("
			+ FIELD_CONFERENCE_ID + " integer primary key autoincrement, "
			+ FIELD_CONFERENCE_CONFERENCE_ID + " text," + FIELD_CONFERENCE_NAME + " text,"
			+ FIELD_CONFERENCE_SPONSORID + " text," + FIELD_CONFERENCE_CONVENE_TIME + " text,"
			+ FIELD_CONFERENCE_FROM + " text," + FIELD_CONFERENCE_START_TIME + " text,"
			+ FIELD_CONFERENCE_ENDTIME + " text," + FIELD_CONFERENCE_REMARK + " text)";

	// ConferencePerson(id,cid,uid,type//��Ա��ɫ��1�������ˣ�2�������ˣ�3����ƵԴ��4������+��ƵԴ��,
	// response_time,remark)
	public static final String TABLE_CONFERENCE_PERSON = "ConferencePerson";
	public static final String FIELD_CONFERENCE_PERSON_ID = "id";
	public static final String FIELD_CONFERENCE_PERSON_CID = "cid";
	public static final String FIELD_CONFERENCE_PERSON_UID = "uid";
	public static final String FIELD_CONFERENCE_PERSON_TYPE = "type";
	public static final String FIELD_CONFERENCE_PERSON_RESPONSE_TIME = "response_time";
	public static final String FIELD_CONFERENCE_PERSON_REMARK = "remark";
	public static final String SQL_CONFERENCE_PERSON_CREATE_TABLE = "create table "
			+ TABLE_CONFERENCE_PERSON + " (" + FIELD_CONFERENCE_PERSON_ID
			+ " integer primary key autoincrement, " + FIELD_CONFERENCE_PERSON_CID + " text,"
			+ FIELD_CONFERENCE_PERSON_UID + " text," + FIELD_CONFERENCE_PERSON_TYPE + " text,"
			+ FIELD_CONFERENCE_PERSON_RESPONSE_TIME + " text," + FIELD_CONFERENCE_PERSON_REMARK
			+ " text)";

	// ���� Group
	public static final String TABLE_GROUP = "group_table";
	// �ֶ�
	public static final String FIELD_GROUP_ID = "id";
	public static final String FIELD_GROUP_GROUP_ID = "cid";
	public static final String FIELD_GROUP_TYPE = "type";
	public static final String FIELD_GROUP_NAME = "name";
	public static final String FIELD_GROUP_CREATE_TIME = "create_time";
	public static final String FIELD_GROUP_UPDATE_TIME = "update_time";
	public static final String FIELD_GROUP_RIDS = "rids";
	// �������SQL
	public static final String SQL_GROUP_CREATE_TABLE = "create table " + TABLE_GROUP + " ("
			+ FIELD_GROUP_ID + " integer primary key autoincrement, " + FIELD_GROUP_GROUP_ID + " text,"
			+ FIELD_GROUP_TYPE + " text," + FIELD_GROUP_NAME + " text," + FIELD_GROUP_CREATE_TIME
			+ " text," + FIELD_GROUP_UPDATE_TIME + " text," + FIELD_GROUP_RIDS + " text)";

	// ���� GPS
	public static final String TABLE_GPS = "gps_table";
	// �ֶ�
	public static final String FIELD_GPS_ID = "id";
	public static final String FIELD_GPS_GPS_ID = "gid";
	public static final String FIELD_GPS_PERSON_ID = "person_id";
	public static final String FIELD_GPS_TIME = "time";
	public static final String FIELD_GPS_LONG = "longitude";
	public static final String FIELD_GPS_LAT = "latitude";
	public static final String FIELD_GPS_TYPE = "type";
	public static final String FIELD_GPS_ACCURACY = "accuracy";
	public static final String FIELD_GPS_HEIGHT = "height";
	public static final String FIELD_GPS_SPEED = "speed";
	public static final String FIELD_GPS_UPDATE_TIME = "update_time";
	// �������SQL
	public static final String SQL_GPS_CREATE_TABLE = "create table " + TABLE_GPS + " (" + FIELD_GPS_ID
			+ " integer primary key autoincrement, " + FIELD_GPS_GPS_ID + " text," + FIELD_GPS_PERSON_ID
			+ " text," + FIELD_GPS_TIME + " text," + FIELD_GPS_LONG + " text," + FIELD_GPS_LAT
			+ " text," + FIELD_GPS_TYPE + " text," + FIELD_GPS_ACCURACY + " text," + FIELD_GPS_HEIGHT
			+ " text," + FIELD_GPS_SPEED + " text," + FIELD_GPS_UPDATE_TIME + " text)";

	// ��������
	private volatile static DatabaseHelper _unique_instance = null;

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, VERSION);
	}

	// ע��ÿ�����̶��ᴴ��һ��EvidenceDatabaseʵ��
	// ���Բ��ɲ���ͬ�������ķ�ʽ��ͬ�������������һ��ʵ��������
	public static DatabaseHelper getInstance(Context context) {
		// contextʵ������

		// ���ʵ��,���ǲ����ھͽ���ͬ��������
		if (null == _unique_instance) {
			// ���������,��ֹ�����߳�ͬʱ����ͬ��������
			synchronized (DatabaseHelper.class) {
				// ����˫�ؼ��
				if (null == _unique_instance) {
					_unique_instance = new DatabaseHelper(context);
				}
			}
		}

		return _unique_instance;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		Log.v(LOG_TAG, "SQLite: onCreate");

		// jerry create table 6.1
		db.execSQL(SQL_ORG_PERSON_CREATE_TABLE);
		Log.v(LOG_TAG, "SQLite: onCreate table ORG_PERSON");
		Log.v(LOG_TAG, SQL_ORG_PERSON_CREATE_TABLE);
		db.execSQL(SQL_ORG_CODE_CREATE_TABLE);
		Log.v(LOG_TAG, "SQLite: onCreate table ORG_CODE");
		Log.v(LOG_TAG, SQL_ORG_CODE_CREATE_TABLE);
		db.execSQL(SQL_MY_INFO_CREATE_TABLE);
		Log.v(LOG_TAG, "SQLite: onCreate table MY_INFO");
		Log.v(LOG_TAG, SQL_MY_INFO_CREATE_TABLE);
		db.execSQL(SQL_AFFAIRINFO_CREATE_TABLE);
		Log.v(LOG_TAG, "SQLite: onCreate table AFFAIRINFO");
		Log.v(LOG_TAG, SQL_AFFAIRINFO_CREATE_TABLE);
		db.execSQL(SQL_POD_CREATE_TABLE);
		Log.v(LOG_TAG, "SQLite: onCreate table POD");
		Log.v(LOG_TAG, SQL_POD_CREATE_TABLE);
		db.execSQL(SQL_ATTACHMENT_CREATE_TABLE);
		Log.v(LOG_TAG, "SQLite: onCreate table ATTACHMENT");
		Log.v(LOG_TAG, SQL_ATTACHMENT_CREATE_TABLE);
		db.execSQL(SQL_MESSAGE_CREATE_TABLE);
		Log.v(LOG_TAG, "SQLite: onCreate table MESSAGE");
		Log.v(LOG_TAG, SQL_MESSAGE_CREATE_TABLE);
		db.execSQL(SQL_CONFERENCE_CREATE_TABLE);
		Log.v(LOG_TAG, "SQLite: onCreate table CONFERENCE");
		Log.v(LOG_TAG, SQL_CONFERENCE_CREATE_TABLE);
		db.execSQL(SQL_GROUP_CREATE_TABLE);
		Log.v(LOG_TAG, "SQLite: onCreate table GROUP");
		Log.v(LOG_TAG, SQL_GROUP_CREATE_TABLE);
		db.execSQL(SQL_GPS_CREATE_TABLE);
		Log.v(LOG_TAG, "SQLite: onCreate table GPS");
		Log.v(LOG_TAG, SQL_GPS_CREATE_TABLE);
		db.execSQL(SQL_CONFERENCE_PERSON_CREATE_TABLE);
		Log.v(LOG_TAG, "SQLite: onCreate table SQL_CONFERENCE_PERSON_CREATE_TABLE");
		Log.v(LOG_TAG, SQL_CONFERENCE_PERSON_CREATE_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.v(LOG_TAG, "SQLite: onUpgrade");
	}

}
