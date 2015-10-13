package android.wxapp.service.request;

import java.io.File;

/**
 * �ӿ�url������
 * 
 * @author JerryLiu
 *
 */
public class Contants {
	// TIP�����е�url��β����/
	// public static final String SERVER="202.114.66.77";
	// public static final String PORT="8080";
	public static final String SERVER = "120.26.78.7";
	public static final String PORT = "8012";

	public static final String SERVER_URL = "http://" + SERVER + ":" + PORT + File.separator;

	// ģ������
	public static final String MODEL_NAME = "HttpService" + File.separator;

	// Personģ�� Jerry FINAL 5.22
	@Deprecated
	public static final String MODEL_PERSON = "LoginService" + File.separator;
	public static final String METHOD_PERSON_LOGIN = "Login";
	public static final String METHOD_PERSON_MODIFYUSERINFO = "ModifyCustomer";
	public static final String METHOD_PERSON_LOGOUT = "Logout";
	public static final String METHOD_PERSON_GET_PERSON_INFO = "GetPersonInfo";
	public static final String METHOD_PERSON_ADD_PERSON_CONTACTS = "AddPersonContact";
	public static final String METHOD_PERSON_GET_ORG_CODE = "GetOrgCode";
	public static final String METHOD_PERSON_GET_ORG_PERSON = "GetOrgCodePerson";
	@Deprecated
	public static final String METHOD_PERSON_CHANGEPWD = "ChangePwd";

	// Affairģ�� Jerry 5.23
	@Deprecated
	public static final String MODEL_AFFAIRS = "AffairsService" + File.separator;
	public static final String METHOD_AFFAIRS_UPDATE_LIST = "TaskUpdateQuery";
	public static final String METHOD_AFFAIRS_ADDAFFAIR = "CreateTask";
	public static final String METHOD_AFFAIRS_MODIFY_TASK = "ModifyTask";
	public static final String METHOD_AFFAIRS_QUERY_COUNT = "QueryAffairCount";
	public static final String METHOD_AFFAIRS_END_TASK = "EndTask";
	public static final String METHOD_AFFAIRS_QUERY_LIST = "QueryAffairList";
	public static final String METHOD_AFFAIRS_QUERY_INFO = "QueryAffairInfo";
	public static final String METHOD_AFFAIRS_UPDATE_READTIME = "UpdateAffairReadTime";
	public static final String METHOD_AFFAIRS_UPDATE_INFO = "UpdateAffairInfo";
	// ===========����Ϊ�����ӿ�==============
	public static final String METHOD_AFFAIRS_CloseAffair = "CloseAffair";
	public static final String METHOD_AFFAIRS_QueryAffairList = "QueryAffairList";
	public static final String METHOD_AFFAIRS_QueryAffairInfo = "QueryAffairInfo";
	public static final String METHOD_AFFAIRS_SendFeedback = "SendFeedback";
	public static final String METHOD_AFFAIRS_GetFeedback = "GetFeedback";
	// ==================================

	// Feedbackģ�� Jerry
	public static final String METHOD_FEEDBACK_SEND = "TaskFeedback";
	public static final String METHOD_FEEDBACK_QUERY = "FeedbackInfoQuery";

	// Messageģ�� Jerry
	@Deprecated
	public static final String MODEL_MESSAGE = "MessageService" + File.separator;
	public static final String METHOD_MESSAGE_UPDATE = "MessageUpdateQuery";
	public static final String METHOD_MESSAGE_SEND = "SendMessage";
	public static final String METHOD_MESSAGE_QUERY_CONTACT_MERSON_MESSAGE = "QueryContactPersonMessage";
	public static final String METHOD_MESSAGE_RECEIVE = "ReceiveMessage";
	public static final String METHOD_MESSAGE_SendPersonalMsg = "SendPersonalMsg";
	public static final String METHOD_MESSAGE_QueryPersonalMsgHistory = "QueryPersonalMsgHistory";
	public static final String METHOD_MESSAGE_SendGroupMsg = "SendGroupMsg";
	public static final String METHOD_MESSAGE_QueryGroupMsgHistory = "QueryGroupMsgHistory";
	public static final String METHOD_MESSAGE_CreatNonbasicGroup = "CreatNonbasicGroup";
	public static final String METHOD_MESSAGE_DeleteNonbasicGroup = "DeleteNonbasicGroup";

	// GPSģ�� Jerry 5.22
	public static final String METHOD_GPS_UPDAET = "GpsUpdateQuery";
	public static final String METHOD_GPS_UPLOAD = "GPSUpload";
	public static final String METHOD_GPS_QUERY_LAST_PERSONAL_GPS = "QueryLastPersonalGPS";
	public static final String METHOD_GPS_QUERY_LAST_GPSS = "QueryLastGPSs";
	public static final String METHOD_GPS_QUERY_PERSONAL_GPSS = "QueryPersonalGPSs";
	public static final String METHOD_GPS_QUERY_GPSS = "QueryGPSs";

	// Conferenceģ�� Jerry 5.22
	public static final String METHOD_CONFERENCE_UPDATE = "ConferenceUpdateQuery";
	public static final String METHOD_CONFERENCE_CREATE = "CreateConference";
	public static final String METHOD_CONFERENCE_START = "StartConference";
	public static final String METHOD_CONFERENCE_END = "EndConference";
	public static final String METHOD_CONFERENCE_QUERY = "ConferenceQuery";

	// Groupģ�� Jerry 5.22
	public static final String METHOD_GROUP_UPDATE = "GroupUpdateQuery";
	public static final String METHOD_GROUP_CREATE = "CreateGroup";
	public static final String METHOD_GROUP_MODIFY = "ModifyGroup";
	public static final String METHOD_GROUP_DELETE = "DeleteGroup";

	// ====================================����Ϊ�ɽӿ�=========================
	public static final String MODEL_CONTACT = "ContactService" + File.separator;
	public static final String METHOD_CONTACT_QUERYORGPERSON = "QueryOrgPerson";
	public static final String METHOD_CONTACT_QUERYPERSONINFO = "QueryPersionInfo";

	public static final String MODEL_CONFERECNCE = "ConferenceService" + File.separator;
	public static final String METHOD_CONFERECNCE_AppointConference = "AppointConference";
	public static final String METHOD_CONFERECNCE_BeginConference = "BeginConference";
	public static final String METHOD_CONFERECNCE_QueryConferenceInfo = "QueryConferenceInfo";
	public static final String METHOD_CONFERECNCE_QueryConferenceList = "QueryConferenceList";
	public static final String METHOD_CONFERECNCE_QueryConferenceMemberInfo = "QueryConferenceMemberInfo";

	// ������
	public static final String PARAM_NAME = "?param=";

	// �������Ƿ񷵻سɹ���־
	public static final String RESULT_SUCCESS = "0";
	public static final String RESULT_MORE = "-1";
	@Deprecated
	public static final String RESULT_FAIL = "1";

}
