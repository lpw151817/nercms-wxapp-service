package android.wxapp.service.util;

import android.content.Context;

/**
 * TODO 作用域为整个应用程序的共享变量控制类
 */
public class MySharedPreference {
	private static final String PREFERENCE_NAME = "android.wxapp.service";

	// ...
	public static final String IS_FIRST_RUN = "is_first_run";
	// 当前用户的ID
	public static final String USER_ID = "user_id";
	// 当前用户的姓名
	public static final String USER_NAME = "user_name";
	// 当前用户的密码
	public static final String USER_IC = "user_ic";

	// 上次更新事务的时戳
	public static final String LAST_UPDATE_TASK_TIMESTAMP = "last_update_task_timestamp";
	// 上次更新反馈的时戳
	public static final String LAST_UPDATE_FEEDBACK_TIMESTAMP = "last_update_feedback_timestamp";
	// 上次更新消息的时戳
	public static final String LAST_UPDATE_MESSAGE_TIMESTAMP = "last_update_message_timestamp";
	// 上次更新orgcode的时间
	public static final String LAST_UPDATE_ORGCODE_TIMESTAMP = "LAST_UPDATE_ORGCODE_TIMESTAMP";
	// 上次更新orgperson的时间
	public static final String LAST_UPDATE_ORGPERSON_TIMESTAMP = "LAST_UPDATE_ORGPERSON_TIMESTAMP";

	// 服务器时间
	public static final String PREF_LAST_UPDATE_SERVER_TIMESTAMP = "server_timestamp";
	// 服务器IP
	public static final String PREF_SERVER_IP = "server_ip";
	// 服务器端口
	public static final String PREF_SERVER_PORT = "server_port";
	// 任务附件的FTP目录
	public static final String PREF_TASK_ATTACHMENT_DIR = "attachment_dir";
	// 版本号
	public static final String PREF_VERSION_CODE = "version_code";

	/** 保存布尔值 */
	public static void save(Context context, String key, boolean value) {
		context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE).edit()
				.putBoolean(key, value).commit();
	}

	/** 获取布尔值 */
	public static boolean get(Context context, String key, boolean defaultValue) {
		return context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE).getBoolean(key,
				defaultValue);
	}

	/** 保存整型值 */
	public static void save(Context context, String key, int value) {
		context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE).edit().putInt(key, value)
				.commit();
	}

	/** 获取整型值 */
	public static int get(Context context, String key, int defaultValue) {
		return context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE).getInt(key,
				defaultValue);
	}

	/** 保存长整型值 */
	public static void save(Context context, String key, long value) {
		context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE).edit().putLong(key, value)
				.commit();
	}

	/** 获取长整型值 */
	public static long get(Context context, String key, long defaultValue) {
		return context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE).getLong(key,
				defaultValue);
	}

	/** 保存浮点数 */
	public static void save(Context context, String key, float value) {
		context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE).edit().putFloat(key, value)
				.commit();
	}

	/** 获取浮点数 */
	public static float get(Context context, String key, float defaultValue) {
		return context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE).getFloat(key,
				defaultValue);
	}

	/** 保存字符串值 */
	public static void save(Context context, String key, String value) {
		context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE).edit().putString(key, value)
				.commit();
	}

	/** 获取字符串值 */
	public static String get(Context context, String key, String defaultValue) {
		return context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE).getString(key,
				defaultValue);
	}
}
