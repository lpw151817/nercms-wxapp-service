package android.wxapp.service.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * 表结构如下
 * <p>
 * org_code(id,org_code,description);
 * <p>
 * org_code_person(id,user_id,user_name);
 * <p>
 * 
 * @author JerryLiu
 * @time 2015-6-1
 */
public class DatabaseHelper extends SQLiteOpenHelper {

	// 版本
	public static final int VERSION = 1;

	private static final String LOG_TAG = "DatabaseHelper";

	// 数据库名
	public static final String DATABASE_NAME = "App.db";

	// Jerry 6.1
	// 表名
	public static final String TABLE_ORG_CODE = "org_code";
	// 字段
	public static final String FIELD_ORG_CODE_ID = "id";
	public static final String FIELD_ORG_CODE_ORG_CODE = "org_code";
	public static final String FIELD_ORG_CODE_DESCRIPTION = "description";
	// 创建表的SQL
	public static final String SQL_ORG_CODE_CREATE_TABLE = "create table " + TABLE_ORG_CODE + " ("
			+ FIELD_ORG_CODE_ID + " integer primary key autoincrement, " + FIELD_ORG_CODE_ORG_CODE
			+ " text," + FIELD_ORG_CODE_DESCRIPTION + " text)";

	public static final String TABLE_ORG_PERSON = "org_code_person";
	// 字段
	public static final String FIELD_ORG_PERSON_ID = "id";
	public static final String FIELD_ORG_PERSON_USER_ID = "user_id";
	public static final String FIELD_ORG_PERSON_USER_NAME = "user_name";
	public static final String SQL_ORG_PERSON_CREATE_TABLE = "create table " + TABLE_ORG_PERSON + " ("
			+ FIELD_ORG_PERSON_ID + " integer primary key autoincrement, " + FIELD_ORG_PERSON_USER_ID
			+ " text," + FIELD_ORG_PERSON_USER_NAME + " text)";
	// //////////////////////

	// 事务相关数据表名
	public static final String AFFAIRS_TABLE_NAME = "affairs_info";
	public static final String PERSON_ON_DUTY_TABLE_NAME = "person_on_duty";
	public static final String AFFAIR_ATTACHMENT_TABLE_NAME = "affair_attachment";
	public static final String FEEDBACK_TABLE_NAME = "affair_feedback";
	public static final String FEEDBACK_ATTACHMENT_TABLE_NAME = "feedback_attachment";
	public static final String AFFAIR_OPERATE_LOG_TABLE_NAME = "affair_operate_log";

	// 消息表名
	public static final String MESSAGE_TABLE_NAME = "message";

	// 联系人相关 建表SQL语句
	public static final String CREATE_ORG_NODE_TABLE_SQL = "create table "
			+ DBConstants.ORG_NODE_TABLE_NAME + " (id integer  primary key autoincrement, "
			+ "org_code text," + "description text);";

	public static final String CREATE_ORG_NODE_STAFF_TABLE_SQL = "create table "
			+ DBConstants.ORG_NODE_STAFF_TABLE_NAME + " (id integer primary key autoincrement, "
			+ "org_code text," + "contact_id text," + "sequence text);";

	public static final String CREATE_ORG_STAFF_TABLE_SQL = "create table "
			+ DBConstants.ORG_STAFF_TABLE_NAME + " (id integer  primary key autoincrement, "
			+ "contact_id text," + "name text," + "position text," + "rank text);";

	public static final String CREATE_CONTACT_TABLE_SQL = "create table "
			+ DBConstants.CONTACT_TABLE_NAME + " (id integer primary key autoincrement, "
			+ "contact_id text," + "type integer," + "content text);";

	public static final String CREATE_CUSTOMER_TABLE_SQL = "create table "
			+ DBConstants.CUSTOMER_TABLE_NAME + " (id integer primary key autoincrement, "
			+ "customer_id text," + "name text," + "unit text," + "description text,"
			+ "contact_id text);";

	public static final String CREATE_CUSTOMER_CONTACT_TABLE_SQL = "create table "
			+ DBConstants.CUSTOMER_CONTACT_TABLE_NAME + " (id integer primary key autoincrement, "
			+ "customer_id text," + "type integer," + "content text);";

	// 事务相关 建表SQL语句
	public static final String CREATE_AFFAIRS_TABLE_SQL = "create table affairs_info (id integer  primary key autoincrement, "
			+ "affair_id text, "
			+ "type integer, "
			+ "sponsor_id integer, "
			+ "title text, "
			+ "description text, "
			+ "begin_time text, "
			+ "end_time text, "
			+ "complete_time text, "
			+ "last_operate_type integer, "
			+ "last_operate_time text, "
			+ "is_read integer, "
			+ "status integer);";

	public static final String CREATE_PERSON_ON_DUTY_TABLE_SQL = "create table person_on_duty (id integer  primary key autoincrement, "
			+ "affair_id text, " + "person_id integer);";

	public static final String CREATE_AFFAIR_ATTACHMENT_TABLE_SQL = "create table affair_attachment (id integer  primary key autoincrement, "
			+ "affair_id text, " + "attachment_type integer, " + "url text);";

	public static final String CREATE_AFFAIR_FEEDBACK_TABLE_SQL = "create table affair_feedback (id integer primary key autoincrement, "
			+ "feedback_id text, "
			+ "affair_id text, "
			+ "person_id integer, "
			+ "feedback_time text, "
			+ "content text, " + "is_read integer);";

	public static final String CREATE_FEEDBACK_ATTACHMENT_TABLE_SQL = "create table feedback_attachment (id integer primary key autoincrement, "
			+ "feedback_id text, " + "attachment_type integer, " + "url text);";

	public static final String CREATE_AFFAIR_LOG_TABLE_SQL = "create table affair_operate_log (id integer  primary key autoincrement, "
			+ "affair_id text, " + "type integer, " + "time_stamp text);";

	// 消息表建表语句
	public static final String CREATE_MESSAGE_TABLE_SQL = "create table message (id integer  primary key autoincrement, "
			+ "message_id text, "
			+ "sender_id integer, "
			+ "receiver_id integer,"
			+ "send_time text, "
			+ "content text, "
			+ "attachment_type integer, "
			+ "attachment_url text, "
			+ "is_group integer, " + "is_read integer);";

	// 电话表建表语句
	public static final String CREATE_PHONE_TABLE_SQL = "create table phone (id integer  primary key autoincrement, "
			+ "phone_id text, "
			+ "type integer, "
			+ "caller_id integer, "
			+ "callee_id integer, "
			+ "start_time text, "
			+ "is_answered integer, "
			+ "end_time text, "
			+ "duration text, "
			+ "is_read integer);";

	// 电话表建表语句
	public static final String CREATE_CONFERENCE_TABLE_SQL = "create table conference (id integer  primary key autoincrement, "
			+ "conference_id text, "
			+ "conference_name text, "
			+ "type integer, "
			+ "sponsor_id integer, "
			+ "create_time text, "
			+ "reservation_time text, "
			+ "start_time text, " + "end_time text, " + "status integer);";

	// 电话表建表语句
	public static final String CREATE_CONFERENCE_PERSON_TABLE_SQL = "create table conference_person (id integer  primary key autoincrement, "
			+ "conference_id text, "
			+ "person_id integer, "
			+ "is_sponsor integer, "
			+ "is_speaker integer, "
			+ "is_video_sharer text, "
			+ "join_time text, "
			+ "leave_time text);";

	// 单键处理
	private volatile static DatabaseHelper _unique_instance = null;

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, VERSION);
	}

	// 注意每个进程都会创建一个EvidenceDatabase实例
	// 绝对不可采用同步方法的方式，同步方法仅对类的一个实例起作用
	public static DatabaseHelper getInstance(Context context) {
		// context实际无用

		// 检查实例,如是不存在就进入同步代码区
		if (null == _unique_instance) {
			// 对其进行锁,防止两个线程同时进入同步代码区
			synchronized (DatabaseHelper.class) {
				// 必须双重检查
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
		// /////////////////////

		db.execSQL(CREATE_ORG_NODE_TABLE_SQL);
		Log.v(LOG_TAG, "SQLite: onCreate table org_node");
		db.execSQL(CREATE_ORG_NODE_STAFF_TABLE_SQL);
		Log.v(LOG_TAG, "SQLite: onCreate table org_node_staff");
		db.execSQL(CREATE_ORG_STAFF_TABLE_SQL);
		Log.v(LOG_TAG, "SQLite: onCreate table org_staff");
		db.execSQL(CREATE_CONTACT_TABLE_SQL);
		Log.v(LOG_TAG, "SQLite: onCreate table contact");

		db.execSQL(CREATE_CUSTOMER_TABLE_SQL);
		Log.v(LOG_TAG, "SQLite: onCreate table customer");
		db.execSQL(CREATE_CUSTOMER_CONTACT_TABLE_SQL);
		Log.v(LOG_TAG, "SQLite: onCreate table customer_contact");

		db.execSQL(CREATE_AFFAIRS_TABLE_SQL);
		Log.v(LOG_TAG, "SQLite: onCreate table affairs_info");
		db.execSQL(CREATE_PERSON_ON_DUTY_TABLE_SQL);
		Log.v(LOG_TAG, "SQLite: onCreate table person_on_duty");
		db.execSQL(CREATE_AFFAIR_ATTACHMENT_TABLE_SQL);
		Log.v(LOG_TAG, "SQLite: onCreate table affairs_attachment");
		db.execSQL(CREATE_AFFAIR_FEEDBACK_TABLE_SQL);
		Log.v(LOG_TAG, "SQLite: onCreate table affair_feedback");
		db.execSQL(CREATE_FEEDBACK_ATTACHMENT_TABLE_SQL);
		Log.v(LOG_TAG, "SQLite: onCreate table feedback_attachment");
		db.execSQL(CREATE_AFFAIR_LOG_TABLE_SQL);
		Log.v(LOG_TAG, "SQLite: onCreate table affairs_operate_log");

		db.execSQL(CREATE_MESSAGE_TABLE_SQL);
		Log.v(LOG_TAG, "SQLite: onCreate table message");

		db.execSQL(CREATE_PHONE_TABLE_SQL);
		Log.v(LOG_TAG, "SQLite: onCreate table phone");

		db.execSQL(CREATE_CONFERENCE_TABLE_SQL);
		Log.v(LOG_TAG, "SQLite: onCreate table conference");
		db.execSQL(CREATE_CONFERENCE_PERSON_TABLE_SQL);
		Log.v(LOG_TAG, "SQLite: onCreate table conference_person");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.v(LOG_TAG, "SQLite: onUpgrade");

		db.execSQL("DROP TABLE IF EXISTS " + DBConstants.ORG_NODE_TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + DBConstants.ORG_NODE_STAFF_TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + DBConstants.ORG_STAFF_TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + DBConstants.CONTACT_TABLE_NAME);

		db.execSQL("DROP TABLE IF EXISTS " + DBConstants.CUSTOMER_TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + DBConstants.CUSTOMER_CONTACT_TABLE_NAME);

		db.execSQL("DROP TABLE IF EXISTS " + DBConstants.AFFAIRS_TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + DBConstants.PERSON_ON_DUTY_TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + DBConstants.AFFAIR_ATTACHMENT_TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + DBConstants.FEEDBACK_TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + DBConstants.FEEDBACK_ATTACHMENT_TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + DBConstants.AFFAIR_OPERATE_LOG_TABLE_NAME);

		db.execSQL("DROP TABLE IF EXISTS " + DBConstants.MESSAGE_TABLE_NAME);

		db.execSQL("DROP TABLE IF EXISTS " + DBConstants.PHONE_TABLE_NAME);

		db.execSQL("DROP TABLE IF EXISTS " + DBConstants.CONFERENCE_TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + DBConstants.CONFERENCE_PERSON_TABLE_NAME);

		onCreate(db);

	}

	/** close cursor */
	protected void closeCursor(Cursor cursor) {
		cursor.close();
	}

}
