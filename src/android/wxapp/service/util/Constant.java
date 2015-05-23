package android.wxapp.service.util;

public class Constant {

	public static final int MOBILE = 1; // 操作途径：手机

	/**
	 * Handler注册相关常量
	 */
	// Person 模块 Jerry 15.5.21
	// 请求成功
	public static final int REQUEST_SUCCESS = 100;
	// 请求失败
	public static final int REQUEST_FAIL = 101;
	// 登录请求成功
	public static final int LOGIN_REQUEST_SUCCESS = 102;
	// 登录请求失败
	public static final int LOGIN_REQUEST_FAIL = 103;
	// 退出请求成功
	public static final int LOGOUT_REQUEST_SUCCESS = 104;
	// 退出请求失败
	public static final int LOGOUT_REQUEST_FAIL = 105;
	// 修改密码请求成功
	public static final int CHANGE_PASSWORD_REQUEST_SUCCESS = 106;
	// 修改密码请求失败
	public static final int CHANGE_PASSWORD_REQUEST_FAIL = 107;
	// 新建客户请求成功
	public static final int CREATE_CUSTOMER_REQUEST_SUCCESS = 108;
	// 新建客户请求失败
	public static final int CREATE_CUSTOMER_REQUEST_FAIL = 109;
	// 新建客户请求成功
	public static final int MODIFY_CUSTOMER_REQUEST_SUCCESS = 110;
	// 新建客户请求失败
	public static final int MODIFY_CUSTOMER_REQUEST_FAIL = 111;
	// 删除客户请求成功
	public static final int DELETE_CUSTOMER_REQUEST_SUCCESS = 112;
	// 删除客户请求失败
	public static final int DELETE_CUSTOMER_REQUEST_FAIL = 113;
	// 修改用户信息成功
	public static final int MODIFY_USERINFO_REQUEST_SUCCESS = 114;
	// 修改用户信息失败
	public static final int MODIFY_USERINFO_REQUEST_FAIL = 115;
	// 查询个人信息成功
	public static final int QUERY_PERSON_INFO_REQUEST_SUCCESS = 116;
	// 查询个人信息失败
	public static final int QUERY_PERSON_INFO_REQUEST_FAIL = 117;
	// 添加联系方式成功
	public static final int ADD_PERSON_CONTACT_REQUEST_SUCCESS = 118;
	// 添加联系方式失败
	public static final int ADD_PERSON_CONTACT_REQUEST_FAIL = 119;
	// 获取组织结点成功
	public static final int QUERY_ORG_NODE_REQUEST_SUCCESS = 120;
	// 获取组织结点失败
	public static final int QUERY_ORG_NODE_REQUEST_FAIL = 121;
	// 获取组织结点人员成功
	public static final int QUERY_ORG_PERSON_REQUEST_SUCCESS = 122;
	// 获取组织结点人员失败
	public static final int QUERY_ORG_PERSON_REQUEST_FAIL = 123;

	// Affair Jerry
	// 创建事务请求成功
	public static final int CREATE_AFFAIR_REQUEST_SUCCESS = 200;
	// 创建事务请求失败
	public static final int CREATE_AFFAIR_REQUEST_FAIL = 201;
	// 结束任务请求成功
	public static final int END_TASK_REQUEST_SUCCESS = 202;
	// 结束任务请求失败
	public static final int END_TASK_REQUEST_FAIL = 203;
	// 结束任务请求成功
	public static final int MODIFY_TASK_REQUEST_SUCCESS = 204;
	// 结束任务请求失败
	public static final int MODIFY_TASK_REQUEST_FAIL = 205;
	// 新任务保存完成通知
	public static final int SAVE_TASK_SUCCESS = 206;
	// 更新任务列表成功
	public static final int UPDATE_TASK_LIST_REQUEST_SUCCESS = 207;
	// 更新任务列表失败
	public static final int UPDATE_TASK_LIST_REQUEST_FAIL = 208;
	// 修改任务结束时间成功
	public static final int UPDATE_TASK_END_TIME_REQUEST_SUCCESS = 209;
	// 修改任务结束时间失败
	public static final int UPDATE_TASK_END_TIME_REQUEST_FAIL = 210;
	// 请求任务数量成功
	public static final int QUERY_TASK_COUNT_REQUEST_SUCCESS = 211;
	// 请求任务数量失败
	public static final int QUERY_TASK_COUNT_REQUEST_FAIL = 212;
	// 请求任务列表成功
	public static final int QUERY_TASK_LIST_REQUEST_SUCCESS = 213;
	// 请求任务列表失败
	public static final int QUERY_TASK_LIST_REQUEST_FAIL = 214;
	// 请求任务详情成功
	public static final int QUERY_TASK_INFO_REQUEST_SUCCESS = 215;
	// 请求任务详情失败
	public static final int QUERY_TASK_INFO_REQUEST_FAIL = 216;

	// Feedback
	// 发送反馈请求成功
	public static final int SEND_FEEDBACK_REQUEST_SUCCESS = 300;
	// 发送反馈请求失败
	public static final int SEND_FEEDBACK_REQUEST_FAIL = 301;
	// 新反馈保存完成通知
	public static final int SAVE_FEEDBACK_SUCCESS = 302;
	// 查询反馈成功
	public static final int QUERY_FEEDBACK_REQUEST_SUCCESS = 303;
	// 查询反馈失败
	public static final int QUERY_FEEDBACK_REQUEST_FAIL = 304;

	// Message
	// 发送消息请求成功
	public static final int SEND_MESSAGE_REQUEST_SUCCESS = 400;
	// 发送消息请求失败
	public static final int SEND_MESSAGE_REQUEST_FAIL = 401;
	// 新消息保存完成通知
	public static final int SAVE_MESSAGE_SUCCESS = 402;

	// Attachment
	// 附件下载成功
	public static final int FILE_DOWNLOAD_SUCCESS = 500;
	// 附件下载失败
	public static final int FILE_DOWNLOAD_FAIL = 501;
	// 附件下载成功
	public static final int FILE_UPLOAD_SUCCESS = 502;
	// 附件下载失败
	public static final int FILE_UPLOAD_FAIL = 503;

	// MQTT
	// MQTT收到新任务推送通知
	public static final int MQTT_NEW_TASK = 600;
	// MQTT收到任务完成推送通知
	public static final int MQTT_END_TASK = 601;
	// MQTT收到任务时间修改推送通知
	public static final int MQTT_MODIFY_TASK = 602;
	// MQTT收到新任务推送通知
	public static final int MQTT_NEW_FEEDBACK = 603;
	// MQTT收到新任务推送通知
	public static final int MQTT_NEW_MESSAGE = 604;

	// Notification 通知
	public static final int SHOW_MESSAGE_NOTIFICATION = 700;
	public static final int SHOW_TASK_NOTIFICATION = 701;
	public static final int SHOW_FEEDBACK_NOTIFICATION = 702;
	public static final int SHOW_TASK_FINISH_NOTIFICATION = 703;
	public static final int SHOW_TASK_DELAY_NOTIFICATION = 704;
	public static final int SHOW_TASK_MODIFY_NOTIFICATION = 705;

	// GPS模块 Jerry 15.5.21
	// GPS信息上传成功
	public static final int GPS_UPLOAD_REQUEST_SECCESS = 800;
	// GPS信息上传失败
	public static final int GPS_UPLOAD_REQUEST_FAIL = 801;
	// 查询个人最新GPS信息成功
	public static final int QUERY_PERSONAL_LAST_GPS_REQUEST_SECCESS = 802;
	// 查询个人最新GPS信息失败
	public static final int QUERY_PERSONAL_LAST_GPS_REQUEST_FAIL = 803;
	// 查询多个人最新GPS信息成功
	public static final int QUERY_LAST_GPSS_REQUEST_SECCESS = 804;
	// 查询多个人最新GPS信息失败
	public static final int QUERY_LAST_GPSS_REQUEST_FAIL = 805;
	// 查询个人GPS轨迹信息成功
	public static final int QUERY_PERSONAL_GPSS_REQUEST_SECCESS = 806;
	// 查询个人GPS轨迹信息失败
	public static final int QUERY_PERSONAL_GPSS_REQUEST_FAIL = 807;
	// 查询多个人GPS轨迹信息成功
	public static final int QUERY_GPSS_REQUEST_SECCESS = 808;
	// 查询多个人GPS轨迹信息失败
	public static final int QUERY_GPSS_REQUEST_FAIL = 809;

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

	public static final String SERVER_IP = "192.168.1.100";

	// 文件服务器基址
	// public static final String FILE_SERVER_BASE_URL =
	// "http://192.168.1.100:15005/HFSFileServer/";
	public static final String FILE_SERVER_BASE_URL = SERVER_IP + ":15005/HFSFileServer/";

	// 服务器请求地址
	// public static final String SERVER_BASE_URL =
	// "http://192.168.1.100:15000/HttpService/";
	public static final String SERVER_BASE_URL = SERVER_IP + ":15000/HttpService/";

	public static final String SERVER_CRASH_REPORT = SERVER_IP + ":15000/HttpService/CrashReport";

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
