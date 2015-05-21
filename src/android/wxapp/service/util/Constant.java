package android.wxapp.service.util;

public class Constant {

	public static final int MOBILE = 1; // 操作途径：手机

	/**
	 * Handler注册相关常量
	 */
	// 请求成功
	public static final int REQUEST_SUCCESS = 0;
	// 请求失败
	public static final int REQUEST_FAIL = 1;
	// 登录请求成功
	public static final int LOGIN_REQUEST_SUCCESS = 2;
	// 登录请求失败
	public static final int LOGIN_REQUEST_FAIL = 3;
	// 修改密码请求成功
	public static final int CHANGE_PASSWORD_REQUEST_SUCCESS = 4;
	// 修改密码请求失败
	public static final int CHANGE_PASSWORD_REQUEST_FAIL = 5;
	// 创建事务请求成功
	public static final int CREATE_AFFAIR_REQUEST_SUCCESS = 100;
	// 创建事务请求失败
	public static final int CREATE_AFFAIR_REQUEST_FAIL = 101;
	// 发送反馈请求成功
	public static final int SEND_FEEDBACK_REQUEST_SUCCESS = 102;
	// 发送反馈请求失败
	public static final int SEND_FEEDBACK_REQUEST_FAIL = 103;
	// 发送消息请求成功
	public static final int SEND_MESSAGE_REQUEST_SUCCESS = 104;
	// 发送消息请求失败
	public static final int SEND_MESSAGE_REQUEST_FAIL = 105;
	// 新建客户请求成功
	public static final int CREATE_CUSTOMER_REQUEST_SUCCESS = 106;
	// 新建客户请求失败
	public static final int CREATE_CUSTOMER_REQUEST_FAIL = 107;
	// 新建客户请求成功
	public static final int MODIFY_CUSTOMER_REQUEST_SUCCESS = 108;
	// 新建客户请求失败
	public static final int MODIFY_CUSTOMER_REQUEST_FAIL = 109;
	// 结束任务请求成功
	public static final int END_TASK_REQUEST_SUCCESS = 110;
	// 结束任务请求失败
	public static final int END_TASK_REQUEST_FAIL = 111;
	// 结束任务请求成功
	public static final int MODIFY_TASK_REQUEST_SUCCESS = 112;
	// 结束任务请求失败
	public static final int MODIFY_TASK_REQUEST_FAIL = 113;
	// 删除客户请求成功
	public static final int DELETE_CUSTOMER_REQUEST_SUCCESS = 114;
	// 删除客户请求失败
	public static final int DELETE_CUSTOMER_REQUEST_FAIL = 115;
	
	
	
	
	// 附件下载成功
	public static final int FILE_DOWNLOAD_SUCCESS = 10;
	// 附件下载失败
	public static final int FILE_DOWNLOAD_FAIL = 11;
	// 附件下载成功
	public static final int FILE_UPLOAD_SUCCESS = 12;
	// 附件下载失败
	public static final int FILE_UPLOAD_FAIL = 13;
	// MQTT收到新任务推送通知
	public static final int MQTT_NEW_TASK = 20;
	// MQTT收到任务完成推送通知
	public static final int MQTT_END_TASK = 21;
	// MQTT收到任务时间修改推送通知
	public static final int MQTT_MODIFY_TASK = 22;
	// MQTT收到新任务推送通知
	public static final int MQTT_NEW_FEEDBACK = 23;
	// MQTT收到新任务推送通知
	public static final int MQTT_NEW_MESSAGE = 24;

	// 新消息保存完成通知
	public static final int SAVE_MESSAGE_SUCCESS = 30;
	// 新任务保存完成通知
	public static final int SAVE_TASK_SUCCESS = 31;
	// 新反馈保存完成通知
	public static final int SAVE_FEEDBACK_SUCCESS = 32;

	// Notification 通知
	public static final int SHOW_MESSAGE_NOTIFICATION = 40;
	public static final int SHOW_TASK_NOTIFICATION = 41;
	public static final int SHOW_FEEDBACK_NOTIFICATION = 42;
	public static final int SHOW_TASK_FINISH_NOTIFICATION = 43;
	public static final int SHOW_TASK_DELAY_NOTIFICATION = 44;
	public static final int SHOW_TASK_MODIFY_NOTIFICATION = 45;

	// 未读状态
	public static final int UNREAD = 0;
	// 已读状态
	public static final int READ = 1;

	// 电话类型
	public static final int IP_CALL_TYPE = 1;

	// 保存联系人线程完成
	public static final int SAVE_ALL_PERSON_SUCCESS = 5;

	// // 事务附件手机端保存路径
	// public static final String FILE_SAVE_AFFAIR_ATTACH_PATH =
	// "/WhuNERCMS/Schedule/DownloadFiles/AffairsAttachments/";
	// // 反馈附件手机端保存路径
	// public static final String FILE_SAVE_FEEDBACK_ATTACH_PATH =
	// "/WhuNERCMS/Schedule/DownloadFiles/FeedbackAttachments/";
	// // 事务附件的服务器下载地址
	// public static final String FILE_SERVER_AFFAIR_ATTACH_URL =
	// "http://192.168.1.100:15005/HFSFileServer/AffairAttachments/";
	// // 反馈附件的服务器下载地址
	// public static final String FILE_SERVER_FEEDBACK_ATTACH_URL =
	// "http://192.168.1.100:15005/HFSFileServer/FeedbackAttachments/";

	public static final String SERVER_IP="192.168.1.100";
	
	// 文件服务器基址
	// public static final String FILE_SERVER_BASE_URL =
	// "http://192.168.1.100:15005/HFSFileServer/";
	public static final String FILE_SERVER_BASE_URL = SERVER_IP+":15005/HFSFileServer/";

	// 服务器请求地址
	// public static final String SERVER_BASE_URL =
	// "http://192.168.1.100:15000/HttpService/";
	public static final String SERVER_BASE_URL = SERVER_IP+":15000/HttpService/";
	
	public static final String SERVER_CRASH_REPORT=SERVER_IP+":15000/HttpService/CrashReport";

	// 业务接口
	public static final String LOGIN_URL = "Login"; // 登录验证
	public static final String CHANGE_PASSWORD_URL = "ChangePassword"; // 修改用户密码
	public static final String GET_ALL_PERSON_URL = "GetAllPerson"; // 获取所有联系人信息
	public static final String CREATE_TASK_URL = "CreateTask?param="; // 创建新任务
	public static final String GET_AFFAIR_UPDATE_URL = "TaskUpdateQuery"; // 获取有新状态的任务
	public static final String MODIFY_END_TIME_URL = "ModifyTask"; // 修改任务完成时间
	public static final String END_TASK_URL = "EndTask"; // 任务置为已完成

	public static final String CREATE_CUSTOMER_URL = "NewCustomer"; // 新建客户
	public static final String DELETE_CUSTOMER_URL = "DeleteCustomer"; // 删除客户
	public static final String MODIFY_CUSTOMER_URL = "ModifyCustomer"; // 编辑客户

	public static final String GET_FEEDBACK_UPDATE_URL = "FeedbackUpdateQuery"; // 获取有新反馈
	public static final String SEND_FEEDBACK_URL = "TaskFeedback"; // 发送新反馈
	public static final String GET_MESSAGE_UPDATE_URL = "MessageUpdateQuery";// 获取新消息
	public static final String SEND_MESSAGE_URL = "SendMessage";// 发送消息

}
