package android.wxapp.service.request;

import java.io.File;

public class Contants {
	// TIP：所有的url结尾均带/

	// 服务器地址
	public static final String SERVER_URL = "http://202.114.66.77:8080" + File.separator;
	// 模块名称
	public static final String MODEL_NAME = "HttpService" + File.separator;

	// Person模块
	@Deprecated
	public static final String MODEL_PERSON = "LoginService" + File.separator;
	public static final String METHOD_PERSON_LOGIN = "Login";
	public static final String METHOD_PERSON_MODIFYUSERINFO="ModifyCustomer";
	@Deprecated
	public static final String METHOD_PERSON_CHANGEPWD = "ChangePwd";

	public static final String MODEL_CONTACT = "ContactService" + File.separator;
	public static final String METHOD_CONTACT_QUERYORGPERSON = "QueryOrgPerson";
	public static final String METHOD_CONTACT_QUERYPERSONINFO = "QueryPersionInfo";

	public static final String MODEL_AFFAIRS = "AffairsService" + File.separator;
	public static final String METHOD_AFFAIRS_ADDAFFAIR = "AddAffair";
	public static final String METHOD_AFFAIRS_CloseAffair = "CloseAffair";
	public static final String METHOD_AFFAIRS_EditAffair = "EditAffair";
	public static final String METHOD_AFFAIRS_QueryAffairList = "QueryAffairList";
	public static final String METHOD_AFFAIRS_QueryAffairInfo = "QueryAffairInfo";
	public static final String METHOD_AFFAIRS_SendFeedback = "SendFeedback";
	public static final String METHOD_AFFAIRS_GetFeedback = "GetFeedback";

	public static final String MODEL_MESSAGE = "MessageService" + File.separator;
	public static final String METHOD_MESSAGE_SendPersonalMsg = "SendPersonalMsg";
	public static final String METHOD_MESSAGE_QueryPersonalMsgHistory = "QueryPersonalMsgHistory";
	public static final String METHOD_MESSAGE_SendGroupMsg = "SendGroupMsg";
	public static final String METHOD_MESSAGE_QueryGroupMsgHistory = "QueryGroupMsgHistory";
	public static final String METHOD_MESSAGE_CreatNonbasicGroup = "CreatNonbasicGroup";
	public static final String METHOD_MESSAGE_DeleteNonbasicGroup = "DeleteNonbasicGroup";

	public static final String MODEL_CONFERECNCE = "ConferenceService" + File.separator;
	public static final String METHOD_CONFERECNCE_AppointConference = "AppointConference";
	public static final String METHOD_CONFERECNCE_BeginConference = "BeginConference";
	public static final String METHOD_CONFERECNCE_QueryConferenceInfo = "QueryConferenceInfo";
	public static final String METHOD_CONFERECNCE_QueryConferenceList = "QueryConferenceList";
	public static final String METHOD_CONFERECNCE_QueryConferenceMemberInfo = "QueryConferenceMemberInfo";

	// 参数名
	public static final String PARAM_NAME = "?param=";

	// 服务器是否返回成功标志
	public static final String RESULT_SUCCESS = "0";
	@Deprecated
	public static final String RESULT_FAIL = "1";

}
