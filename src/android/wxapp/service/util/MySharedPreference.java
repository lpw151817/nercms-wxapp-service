package android.wxapp.service.util;

import android.content.Context;

/**
 * TODO ������Ϊ����Ӧ�ó���Ĺ������������
 */
public class MySharedPreference {
	private static final String PREFERENCE_NAME = "android.wxapp.service";

	// ...
	public static final String IS_FIRST_RUN = "is_first_run";
	// ��ǰ�û���ID
	public static final String USER_ID = "user_id";
	// ��ǰ�û�������
	public static final String USER_NAME = "user_name";
	// ��ǰ�û�������
	public static final String USER_IC = "user_ic";

	// �ϴθ��������ʱ��
	public static final String LAST_UPDATE_TASK_TIMESTAMP = "last_update_task_timestamp";
	// �ϴθ��·�����ʱ��
	public static final String LAST_UPDATE_FEEDBACK_TIMESTAMP = "last_update_feedback_timestamp";
	// �ϴθ�����Ϣ��ʱ��
	public static final String LAST_UPDATE_MESSAGE_TIMESTAMP = "last_update_message_timestamp";
	// �ϴθ���orgcode��ʱ��
	public static final String LAST_UPDATE_ORGCODE_TIMESTAMP = "LAST_UPDATE_ORGCODE_TIMESTAMP";
	// �ϴθ���orgperson��ʱ��
	public static final String LAST_UPDATE_ORGPERSON_TIMESTAMP = "LAST_UPDATE_ORGPERSON_TIMESTAMP";
	// �ϴθ���conference��ʱ��
	public static final String LAST_UPDATE_CONFERENCE_TIMESTAMP = "LAST_UPDATE_CONFERENCE_TIMESTAMP";
	// �ϴθ���group��ʱ��
	public static final String LAST_UPDATE_GROUP_TIMESTAMP = "LAST_UPDATE_GROUP_TIMESTAMP";
	// �ϴθ���gps��ʱ��
	public static final String LAST_UPDATE_GPS_TIMESTAMP = "LAST_UPDATE_GPS_TIMESTAMP";

	// ������ʱ��
	public static final String PREF_LAST_UPDATE_SERVER_TIMESTAMP = "server_timestamp";
	// ������IP
	public static final String PREF_SERVER_IP = "server_ip";
	// �������˿�
	public static final String PREF_SERVER_PORT = "server_port";
	// ���񸽼���FTPĿ¼
	public static final String PREF_TASK_ATTACHMENT_DIR = "attachment_dir";
	// �汾��
	public static final String PREF_VERSION_CODE = "version_code";

	/** ���沼��ֵ */
	public static void save(Context context, String key, boolean value) {
		context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE).edit()
				.putBoolean(key, value).commit();
	}

	/** ��ȡ����ֵ */
	public static boolean get(Context context, String key, boolean defaultValue) {
		return context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE).getBoolean(key,
				defaultValue);
	}

	/** ��������ֵ */
	public static void save(Context context, String key, int value) {
		context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE).edit().putInt(key, value)
				.commit();
	}

	/** ��ȡ����ֵ */
	public static int get(Context context, String key, int defaultValue) {
		return context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE).getInt(key,
				defaultValue);
	}

	/** ���泤����ֵ */
	public static void save(Context context, String key, long value) {
		context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE).edit().putLong(key, value)
				.commit();
	}

	/** ��ȡ������ֵ */
	public static long get(Context context, String key, long defaultValue) {
		return context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE).getLong(key,
				defaultValue);
	}

	/** ���渡���� */
	public static void save(Context context, String key, float value) {
		context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE).edit().putFloat(key, value)
				.commit();
	}

	/** ��ȡ������ */
	public static float get(Context context, String key, float defaultValue) {
		return context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE).getFloat(key,
				defaultValue);
	}

	/** �����ַ���ֵ */
	public static void save(Context context, String key, String value) {
		context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE).edit().putString(key, value)
				.commit();
	}

	/** ��ȡ�ַ���ֵ */
	public static String get(Context context, String key, String defaultValue) {
		return context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE).getString(key,
				defaultValue);
	}
}
