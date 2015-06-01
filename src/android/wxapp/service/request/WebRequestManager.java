package android.wxapp.service.request;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.wxapp.service.AppApplication;
import android.wxapp.service.jerry.model.feedback.TaskFeedbackRequestIds;
import android.wxapp.service.model.AffairModel;
import android.wxapp.service.model.CustomerContactModel;
import android.wxapp.service.model.CustomerModel;
import android.wxapp.service.model.FeedbackModel;
import android.wxapp.service.model.MessageModel;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;

/**
 * 网络请求管理器
 * 
 * @author WEIHAO
 * 
 */
public class WebRequestManager {

	private Context context;
	private AppApplication application = null;
	private RequestQueue queue = null;

	public WebRequestManager(AppApplication application, Context context) {
		this.context = context;
		this.application = application;
		this.queue = this.application.myQueue;
	}

	//
	// Person Model
	//

	// 登录验证 FINAL Jerry 5.25
	public void loginVarification(String aliasName, String identifyCode, String imsi) {
		queue.add(new PersonRequest().getLoginRequest(aliasName, identifyCode, imsi));
	}

	// 获取组织结点
	public void getOrgCode(String orgCode) {
		queue.add(new PersonRequest().getOrgCode(this.context, orgCode));
	}

	// 修改用户密码 废弃接口 Jerry 5.25
	@Deprecated
	public void changePassword(String identifyCode, String newIdentifyCode) {
		queue.add(new PersonRequest().getChangePasswordrRequest(this.context, identifyCode,
				newIdentifyCode));
	}

	// 获取所有联系人信息 废弃接口 Jerry 5.25
	@Deprecated
	public void getAllPerson() {
		// queue.add(new GetAllPersonRequest(context).getRequest());
		queue.add(new PersonRequest().getAllPersonRequest(context));
	}

	// 新建客户 废弃接口 Jerry 5.25
	@Deprecated
	public void newCustomer(CustomerModel customer, ArrayList<CustomerContactModel> contactList) {
		queue.add(new PersonRequest().sendNewCustomerRequest(context, customer, contactList));
	}

	// 编辑客户信息 废弃接口 Jerry 5.25
	@Deprecated
	public void modifyCustomer(CustomerModel customer, ArrayList<CustomerContactModel> contactList) {
		queue.add(new PersonRequest().sendModifyCustomerRequest(customer, contactList));
	}

	// 删除客户 废弃接口 Jerry 5.25
	@Deprecated
	public void deleteCustomer(String customerID) {
		queue.add(new PersonRequest().sendDeleteCustomerRequest(context, customerID));
	}

	//
	// Affair Model
	//

	// 创建新事务
	public void sendAffair(String ic, String t, String sid, String d, String topic, String bt,
			String et, String ct, String lot, String lotime, String up, List<String> ats,
			List<String> us, String[] rids) {
		// 添加到RequestQueue
		// queue.add(new SendAffairRequest(affair).getRequest());
		queue.add(new AffairRequest().getCreateAffairRequest(context, ic, t, sid, d, topic, bt, et, ct,
				lot, lotime, up, ats, us, rids));
	}

	// 修改任务完成时间
	public void modifyAffairEndTime(String ic, String affairID, String timeString) {
		queue.add(new AffairRequest().getModifyEndTimeRequest(context, ic, affairID, timeString));
	}

	// 完成事务
	public void endAffair(String ic, String affairID) {
		queue.add(new AffairRequest().getEndTaskRequest(context, ic, affairID, null));
	}

	// ！！！！获取任务更新 逻辑需要调整
	public void getAffairUpdate(String ic, String count) {
		// queue.add(new GetAffairUpdateRequest(context).getRequest());
		queue.add(new AffairRequest().getAffairUpdateRequest(context, ic, count));
	}

	// 发送新反馈
	public void sendFeedback(Context context, String ic, String sid, String st, String c, String at,
			String au, String ut, String[] rids) {
		queue.add(new FeedbackRequest().sendFeedbackRequest(context, ic, sid, st, 4 + "", c, at, au, ut,
				rids));
	}

	// 查询反馈详情
	public void getFeedbackInfo(String ic, String aid) {
		queue.add(new FeedbackRequest().getFeedbackInfoRequest(context, ic, aid));
	}

	// 获取新反馈
	public void getFeedbackUpdate() {
		// queue.add(new GetFeedbackUpdateRequest(context).getRequest());
		queue.add(new FeedbackRequest().getFeedbackUpdateRequest());
	}

	// 发送新消息
	public void sendMessage(String ic, String t, String sid, String[] rids, String st, String c,
			String at, String au, String ut) {
		queue.add(new MessageRequest().sendMessageRequest(context, ic, t, sid, rids, st, c, at, au, ut));
	}

	// 获取新消息
	public void getMessageUpdate(String ic, String count) {
		queue.add(new MessageRequest().getMessageUpdateRequest(context, ic, count));
	}

	public void sendCrashReport(JsonObjectRequest request) {
		queue.add(request);
	}

}
