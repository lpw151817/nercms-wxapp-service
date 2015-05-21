package android.wxapp.service.util;

public class Constant {

	public static final int MOBILE = 1; // ����;�����ֻ�

	/**
	 * Handlerע����س���
	 */
	// ����ɹ�
	public static final int REQUEST_SUCCESS = 0;
	// ����ʧ��
	public static final int REQUEST_FAIL = 1;
	// ��¼����ɹ�
	public static final int LOGIN_REQUEST_SUCCESS = 2;
	// ��¼����ʧ��
	public static final int LOGIN_REQUEST_FAIL = 3;
	// �޸���������ɹ�
	public static final int CHANGE_PASSWORD_REQUEST_SUCCESS = 4;
	// �޸���������ʧ��
	public static final int CHANGE_PASSWORD_REQUEST_FAIL = 5;
	// ������������ɹ�
	public static final int CREATE_AFFAIR_REQUEST_SUCCESS = 100;
	// ������������ʧ��
	public static final int CREATE_AFFAIR_REQUEST_FAIL = 101;
	// ���ͷ�������ɹ�
	public static final int SEND_FEEDBACK_REQUEST_SUCCESS = 102;
	// ���ͷ�������ʧ��
	public static final int SEND_FEEDBACK_REQUEST_FAIL = 103;
	// ������Ϣ����ɹ�
	public static final int SEND_MESSAGE_REQUEST_SUCCESS = 104;
	// ������Ϣ����ʧ��
	public static final int SEND_MESSAGE_REQUEST_FAIL = 105;
	// �½��ͻ�����ɹ�
	public static final int CREATE_CUSTOMER_REQUEST_SUCCESS = 106;
	// �½��ͻ�����ʧ��
	public static final int CREATE_CUSTOMER_REQUEST_FAIL = 107;
	// �½��ͻ�����ɹ�
	public static final int MODIFY_CUSTOMER_REQUEST_SUCCESS = 108;
	// �½��ͻ�����ʧ��
	public static final int MODIFY_CUSTOMER_REQUEST_FAIL = 109;
	// ������������ɹ�
	public static final int END_TASK_REQUEST_SUCCESS = 110;
	// ������������ʧ��
	public static final int END_TASK_REQUEST_FAIL = 111;
	// ������������ɹ�
	public static final int MODIFY_TASK_REQUEST_SUCCESS = 112;
	// ������������ʧ��
	public static final int MODIFY_TASK_REQUEST_FAIL = 113;
	// ɾ���ͻ�����ɹ�
	public static final int DELETE_CUSTOMER_REQUEST_SUCCESS = 114;
	// ɾ���ͻ�����ʧ��
	public static final int DELETE_CUSTOMER_REQUEST_FAIL = 115;
	
	
	
	
	// �������سɹ�
	public static final int FILE_DOWNLOAD_SUCCESS = 10;
	// ��������ʧ��
	public static final int FILE_DOWNLOAD_FAIL = 11;
	// �������سɹ�
	public static final int FILE_UPLOAD_SUCCESS = 12;
	// ��������ʧ��
	public static final int FILE_UPLOAD_FAIL = 13;
	// MQTT�յ�����������֪ͨ
	public static final int MQTT_NEW_TASK = 20;
	// MQTT�յ������������֪ͨ
	public static final int MQTT_END_TASK = 21;
	// MQTT�յ�����ʱ���޸�����֪ͨ
	public static final int MQTT_MODIFY_TASK = 22;
	// MQTT�յ�����������֪ͨ
	public static final int MQTT_NEW_FEEDBACK = 23;
	// MQTT�յ�����������֪ͨ
	public static final int MQTT_NEW_MESSAGE = 24;

	// ����Ϣ�������֪ͨ
	public static final int SAVE_MESSAGE_SUCCESS = 30;
	// �����񱣴����֪ͨ
	public static final int SAVE_TASK_SUCCESS = 31;
	// �·����������֪ͨ
	public static final int SAVE_FEEDBACK_SUCCESS = 32;

	// Notification ֪ͨ
	public static final int SHOW_MESSAGE_NOTIFICATION = 40;
	public static final int SHOW_TASK_NOTIFICATION = 41;
	public static final int SHOW_FEEDBACK_NOTIFICATION = 42;
	public static final int SHOW_TASK_FINISH_NOTIFICATION = 43;
	public static final int SHOW_TASK_DELAY_NOTIFICATION = 44;
	public static final int SHOW_TASK_MODIFY_NOTIFICATION = 45;

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

	public static final String SERVER_IP="192.168.1.100";
	
	// �ļ���������ַ
	// public static final String FILE_SERVER_BASE_URL =
	// "http://192.168.1.100:15005/HFSFileServer/";
	public static final String FILE_SERVER_BASE_URL = SERVER_IP+":15005/HFSFileServer/";

	// �����������ַ
	// public static final String SERVER_BASE_URL =
	// "http://192.168.1.100:15000/HttpService/";
	public static final String SERVER_BASE_URL = SERVER_IP+":15000/HttpService/";
	
	public static final String SERVER_CRASH_REPORT=SERVER_IP+":15000/HttpService/CrashReport";

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
