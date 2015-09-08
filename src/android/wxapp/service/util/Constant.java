package android.wxapp.service.util;

public class Constant {

	public static final int MOBILE = 1; // ����;�����ֻ�

	/**
	 * Handlerע����س���
	 */
	// Person ģ�� Jerry 15.5.21
	// ����ɹ�
	public static final int REQUEST_SUCCESS = 100;
	// ����ʧ��
	public static final int REQUEST_FAIL = 101;
	// ��¼����ɹ�
	public static final int LOGIN_REQUEST_SUCCESS = 102;
	// ��¼����ʧ��
	public static final int LOGIN_REQUEST_FAIL = 103;
	// �˳�����ɹ�
	public static final int LOGOUT_REQUEST_SUCCESS = 104;
	// �˳�����ʧ��
	public static final int LOGOUT_REQUEST_FAIL = 105;
	// �޸���������ɹ�
	public static final int CHANGE_PASSWORD_REQUEST_SUCCESS = 106;
	// �޸���������ʧ��
	public static final int CHANGE_PASSWORD_REQUEST_FAIL = 107;
	// �½��ͻ�����ɹ�
	public static final int CREATE_CUSTOMER_REQUEST_SUCCESS = 108;
	// �½��ͻ�����ʧ��
	public static final int CREATE_CUSTOMER_REQUEST_FAIL = 109;
	// �½��ͻ�����ɹ�
	public static final int MODIFY_CUSTOMER_REQUEST_SUCCESS = 110;
	// �½��ͻ�����ʧ��
	public static final int MODIFY_CUSTOMER_REQUEST_FAIL = 111;
	// ɾ���ͻ�����ɹ�
	public static final int DELETE_CUSTOMER_REQUEST_SUCCESS = 112;
	// ɾ���ͻ�����ʧ��
	public static final int DELETE_CUSTOMER_REQUEST_FAIL = 113;
	// �޸��û���Ϣ�ɹ�
	public static final int MODIFY_USERINFO_REQUEST_SUCCESS = 114;
	// �޸��û���Ϣʧ��
	public static final int MODIFY_USERINFO_REQUEST_FAIL = 115;
	// ��ѯ������Ϣ�ɹ�
	public static final int QUERY_PERSON_INFO_REQUEST_SUCCESS = 116;
	// ��ѯ������Ϣʧ��
	public static final int QUERY_PERSON_INFO_REQUEST_FAIL = 117;
	// �����ϵ��ʽ�ɹ�
	public static final int ADD_PERSON_CONTACT_REQUEST_SUCCESS = 118;
	// �����ϵ��ʽʧ��
	public static final int ADD_PERSON_CONTACT_REQUEST_FAIL = 119;
	// ��ȡ��֯���ɹ�
	public static final int QUERY_ORG_NODE_REQUEST_SUCCESS = 120;
	// ��ȡ��֯���ʧ��
	public static final int QUERY_ORG_NODE_REQUEST_FAIL = 121;
	// ��ȡ��֯�����Ա�ɹ�
	public static final int QUERY_ORG_PERSON_REQUEST_SUCCESS = 122;
	// ��ȡ��֯�����Աʧ��
	public static final int QUERY_ORG_PERSON_REQUEST_FAIL = 123;
	// ������֯���ɹ�
	public static final int SAVE_ORG_CODE_SUCCESS = 124;
	// ������֯���ʧ��
	public static final int SAVE_ORG_CODE_FAIL = 125;
	// ������֯�����Ա�ɹ�
	public static final int SAVE_ORG_PERSON_SUCCESS = 126;
	// ������֯�����Աʧ��
	public static final int SAVE_ORG_PERSON_FAIL = 127;

	// Affair Jerry 5.23
	// ������������ɹ�
	public static final int CREATE_AFFAIR_REQUEST_SUCCESS = 200;
	// ������������ʧ��
	public static final int CREATE_AFFAIR_REQUEST_FAIL = 201;
	// ������������ɹ�
	public static final int END_TASK_REQUEST_SUCCESS = 202;
	// ������������ʧ��
	public static final int END_TASK_REQUEST_FAIL = 203;
	// ������������ɹ�
	public static final int MODIFY_TASK_REQUEST_SUCCESS = 204;
	// ������������ʧ��
	public static final int MODIFY_TASK_REQUEST_FAIL = 205;
	// ���������б�ɹ�
	public static final int UPDATE_TASK_LIST_REQUEST_SUCCESS = 207;
	// ���������б�ʧ��
	public static final int UPDATE_TASK_LIST_REQUEST_FAIL = 208;
	// �޸��������ʱ��ɹ�
	public static final int UPDATE_TASK_END_TIME_REQUEST_SUCCESS = 209;
	// �޸��������ʱ��ʧ��
	public static final int UPDATE_TASK_END_TIME_REQUEST_FAIL = 210;
	// �������������ɹ�
	public static final int QUERY_TASK_COUNT_REQUEST_SUCCESS = 211;
	// ������������ʧ��
	public static final int QUERY_TASK_COUNT_REQUEST_FAIL = 212;
	// ���������б�ɹ�
	public static final int QUERY_TASK_LIST_REQUEST_SUCCESS = 213;
	// ���������б�ʧ��
	public static final int QUERY_TASK_LIST_REQUEST_FAIL = 214;
	// ������������ɹ�
	public static final int QUERY_TASK_INFO_REQUEST_SUCCESS = 215;
	// ������������ʧ��
	public static final int QUERY_TASK_INFO_REQUEST_FAIL = 216;
	// ��������ɹ�
	public static final int SAVE_TASK_SUCCESS = 217;
	// ��������ʧ��
	public static final int SAVE_TASK_FAIL = 218;
	// �޸������Ѷ�ʱ��ɹ�
	public static final int UPDATE_TASK_READTIME_SUCCESS = 219;
	// �޸������Ѷ�ʱ��ʧ��
	public static final int UPDATE_TASK_READTIME_FAIL = 220;
	// �޸������Ѷ�ʱ��ɹ�
	public static final int UPDATE_TASK_INFO_SUCCESS = 221;
	// �޸������Ѷ�ʱ��ʧ��
	public static final int UPDATE_TASK_INFO_FAIL = 222;

	// Feedback jerry 5.23
	// ���ͷ�������ɹ�
	public static final int SEND_FEEDBACK_REQUEST_SUCCESS = 300;
	// ���ͷ�������ʧ��
	public static final int SEND_FEEDBACK_REQUEST_FAIL = 301;
	// �·����������֪ͨ
	public static final int SAVE_FEEDBACK_SUCCESS = 302;
	// ��ѯ�����ɹ�
	public static final int QUERY_FEEDBACK_REQUEST_SUCCESS = 303;
	// ��ѯ����ʧ��
	public static final int QUERY_FEEDBACK_REQUEST_FAIL = 304;

	// Message jerry 5.23
	// ������Ϣ����ɹ�
	public static final int SEND_MESSAGE_REQUEST_SUCCESS = 400;
	// ������Ϣ����ʧ��
	public static final int SEND_MESSAGE_REQUEST_FAIL = 401;
	// ������Ϣ����ɹ�
	public static final int UPDATE_MESSAGE_REQUEST_SUCCESS = 403;
	// ������Ϣ����ʧ��
	public static final int UPDATE_MESSAGE_REQUEST_FAIL = 404;
	// ��ѯ�����ϵ����Ϣ����ɹ�
	public static final int QUERY_RECENT_MESSAGE_REQUEST_SUCCESS = 405;
	// ��ѯ�����ϵ����Ϣ����ʧ��
	public static final int QUERY_RECENT_MESSAGE_REQUEST_FAIL = 406;
	// ��ѯ�����ϵ����Ϣ����ɹ�
	public static final int QUERY_MESSAGE_INFO_REQUEST_SUCCESS = 407;
	// ��ѯ�����ϵ����Ϣ����ʧ��
	public static final int QUERY_MESSAGE_INFO_REQUEST_FAIL = 408;
	// ������Ϣ�ɹ�
	public static final int SAVE_MESSAGE_SUCCESS = 409;
	// ������Ϣʧ��
	public static final int SAVE_MESSAGE_FAIL = 410;

	// Attachment
	// �������سɹ�
	public static final int FILE_DOWNLOAD_SUCCESS = 500;
	// ��������ʧ��
	public static final int FILE_DOWNLOAD_FAIL = 501;
	// �������سɹ�
	public static final int FILE_UPLOAD_SUCCESS = 502;
	// ��������ʧ��
	public static final int FILE_UPLOAD_FAIL = 503;

	// MQTT
	// MQTT�յ�����������֪ͨ
	public static final int MQTT_NEW_TASK = 600;
	// MQTT�յ������������֪ͨ
	public static final int MQTT_END_TASK = 601;
	// MQTT�յ�����ʱ���޸�����֪ͨ
	public static final int MQTT_MODIFY_TASK = 602;
	// MQTT�յ�����������֪ͨ
	public static final int MQTT_NEW_FEEDBACK = 603;
	// MQTT�յ�����������֪ͨ
	public static final int MQTT_NEW_MESSAGE = 604;

	// Notification ֪ͨ
	public static final int SHOW_MESSAGE_NOTIFICATION = 700;
	public static final int SHOW_TASK_NOTIFICATION = 701;
	public static final int SHOW_FEEDBACK_NOTIFICATION = 702;
	public static final int SHOW_TASK_FINISH_NOTIFICATION = 703;
	public static final int SHOW_TASK_DELAY_NOTIFICATION = 704;
	public static final int SHOW_TASK_MODIFY_NOTIFICATION = 705;

	// GPSģ�� Jerry 15.5.21
	// GPS��Ϣ�ϴ��ɹ�
	public static final int GPS_UPLOAD_REQUEST_SECCESS = 800;
	// GPS��Ϣ�ϴ�ʧ��
	public static final int GPS_UPLOAD_REQUEST_FAIL = 801;
	// ��ѯ��������GPS��Ϣ�ɹ�
	public static final int QUERY_PERSONAL_LAST_GPS_REQUEST_SECCESS = 802;
	// ��ѯ��������GPS��Ϣʧ��
	public static final int QUERY_PERSONAL_LAST_GPS_REQUEST_FAIL = 803;
	// ��ѯ���������GPS��Ϣ�ɹ�
	public static final int QUERY_LAST_GPSS_REQUEST_SECCESS = 804;
	// ��ѯ���������GPS��Ϣʧ��
	public static final int QUERY_LAST_GPSS_REQUEST_FAIL = 805;
	// ��ѯ����GPS�켣��Ϣ�ɹ�
	public static final int QUERY_PERSONAL_GPSS_REQUEST_SECCESS = 806;
	// ��ѯ����GPS�켣��Ϣʧ��
	public static final int QUERY_PERSONAL_GPSS_REQUEST_FAIL = 807;
	// ��ѯ�����GPS�켣��Ϣ�ɹ�
	public static final int QUERY_GPSS_REQUEST_SECCESS = 808;
	// ��ѯ�����GPS�켣��Ϣʧ��
	public static final int QUERY_GPSS_REQUEST_FAIL = 809;
	public static final int SAVE_GPS_SECCESS = 810;
	public static final int SAVE_GPS_FAIL = 811;

	// Conferenceģ��
	public static final int CONFERENCE_UPDATE_SECCESS = 900;
	public static final int CONFERENCE_UPDATE_FAIL = 901;
	public static final int CONFERENCE_CREATE_SECCESS = 902;
	public static final int CONFERENCE_CREATE_FAIL = 903;
	public static final int CONFERENCE_START_SECCESS = 904;
	public static final int CONFERENCE_START_FAIL = 905;
	public static final int CONFERENCE_END_SECCESS = 906;
	public static final int CONFERENCE_END_FAIL = 907;
	public static final int CONFERENCE_SAVE_SUCCESS = 908;
	public static final int CONFERENCE_SAVE_FAIL = 909;
	public static final int CONFERENCE_QUERY_SECCUESS = 910;
	public static final int CONFERENCE_QUERY_FAIL = 911;

	// Groupģ��
	public static final int GROUP_UPDATE_SECCESS = 0;
	public static final int GROUP_UPDATE_FAIL = 1;
	public static final int GROUP_CREATE_SECCESS = 2;
	public static final int GROUP_CREATE_FAIL = 3;
	public static final int GROUP_MODIFY_SECCESS = 4;
	public static final int GROUP_MODIFY_FAIL = 5;
	public static final int GROUP_DELETE_SECCESS = 6;
	public static final int GROUP_DELETE_FAIL = 7;
	public static final int GROUP_SAVE_SECCESS = 8;
	public static final int GROUP_SAVE_FAIL = 9;

	// δ��״̬
	public static final int UNREAD = 0;
	// �Ѷ�״̬
	public static final int READ = 1;

	// �绰����
	public static final int IP_CALL_TYPE = 1;

	// ������ϵ���߳����
	public static final int SAVE_ALL_PERSON_SUCCESS = 5;

	// // ���񸽼��ֻ��˱���·��
	// public static final String FILE_SAVE_AFFAIR_ATTACH_PATH =
	// "/WhuNERCMS/Schedule/DownloadFiles/AffairsAttachments/";
	// // ���������ֻ��˱���·��
	// public static final String FILE_SAVE_FEEDBACK_ATTACH_PATH =
	// "/WhuNERCMS/Schedule/DownloadFiles/FeedbackAttachments/";
	// // ���񸽼��ķ��������ص�ַ
	// public static final String FILE_SERVER_AFFAIR_ATTACH_URL =
	// "http://192.168.1.100:15005/HFSFileServer/AffairAttachments/";
	// // ���������ķ��������ص�ַ
	// public static final String FILE_SERVER_FEEDBACK_ATTACH_URL =
	// "http://192.168.1.100:15005/HFSFileServer/FeedbackAttachments/";

	public static final String SERVER_IP = "202.114.66.77";

	// �ļ���������ַ
	// public static final String FILE_SERVER_BASE_URL =
	// "http://192.168.1.100:15005/HFSFileServer/";
	public static final String FILE_SERVER_BASE_URL = SERVER_IP + ":15005/HFSFileServer/";

	// �����������ַ
	// public static final String SERVER_BASE_URL =
	// "http://192.168.1.100:15000/HttpService/";
	public static final String SERVER_BASE_URL = SERVER_IP + ":15000/HttpService/";

	public static final String SERVER_CRASH_REPORT = SERVER_IP + ":15000/HttpService/CrashReport";

	// ҵ��ӿ�
	public static final String LOGIN_URL = "Login"; // ��¼��֤
	public static final String CHANGE_PASSWORD_URL = "ChangePassword"; // �޸��û�����
	public static final String GET_ALL_PERSON_URL = "GetAllPerson"; // ��ȡ������ϵ����Ϣ
	public static final String CREATE_TASK_URL = "CreateTask?param="; // ����������
	public static final String GET_AFFAIR_UPDATE_URL = "TaskUpdateQuery"; // ��ȡ����״̬������
	public static final String MODIFY_END_TIME_URL = "ModifyTask"; // �޸��������ʱ��
	public static final String END_TASK_URL = "EndTask"; // ������Ϊ�����

	public static final String CREATE_CUSTOMER_URL = "NewCustomer"; // �½��ͻ�
	public static final String DELETE_CUSTOMER_URL = "DeleteCustomer"; // ɾ���ͻ�
	public static final String MODIFY_CUSTOMER_URL = "ModifyCustomer"; // �༭�ͻ�

	public static final String GET_FEEDBACK_UPDATE_URL = "FeedbackUpdateQuery"; // ��ȡ���·���
	public static final String SEND_FEEDBACK_URL = "TaskFeedback"; // �����·���
	public static final String GET_MESSAGE_UPDATE_URL = "MessageUpdateQuery";// ��ȡ����Ϣ
	public static final String SEND_MESSAGE_URL = "SendMessage";// ������Ϣ

}
