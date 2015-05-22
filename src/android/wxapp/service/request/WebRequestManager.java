package android.wxapp.service.request;

import java.util.ArrayList;

import android.content.Context;
import android.wxapp.service.AppApplication;
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

	// 登录验证
	public void loginVarification(String aliasName, String identifyCode, String imsi) {
		queue.add(new PersonRequest().getLoginRequest(aliasName, identifyCode, imsi));
	}

	// 修改用户密码
	@Deprecated
	public void changePassword(String identifyCode, String newIdentifyCode) {
		queue.add(new PersonRequest().getChangePasswordrRequest(this.context, identifyCode,
				newIdentifyCode));
	}

	// 获取所有联系人信息
	public void getAllPerson() {
		// queue.add(new GetAllPersonRequest(context).getRequest());
		queue.add(new PersonRequest().getAllPersonRequest(context));
	}

	// 新建客户
	public void newCustomer(CustomerModel customer, ArrayList<CustomerContactModel> contactList) {
		queue.add(new PersonRequest().sendNewCustomerRequest(context, customer, contactList));
	}

	// 编辑客户信息
	public void modifyCustomer(CustomerModel customer, ArrayList<CustomerContactModel> contactList) {
		queue.add(new PersonRequest().sendModifyCustomerRequest(customer, contactList));
	}

	// 删除客户
	public void deleteCustomer(String customerID) {
		queue.add(new PersonRequest().sendDeleteCustomerRequest(context, customerID));
	}

	// 创建新事务
	public void sendAffair(AffairModel affair) {
		// 添加到RequestQueue
		// queue.add(new SendAffairRequest(affair).getRequest());
		queue.add(new AffairRequest(affair).getCreateAffairRequest());
	}

	// 修改任务完成时间
	public void modifyAffairEndTime(String affairID, String timeString) {
		queue.add(new AffairRequest(context, affairID, timeString).getModifyEndTimeRequest());
	}

	// 完成事务
	public void endAffair(String affairID) {
		queue.add(new AffairRequest(context, affairID).getEndTaskRequest());
	}

	// 获取任务更新
	public void getAffairUpdate() {
		// queue.add(new GetAffairUpdateRequest(context).getRequest());
		queue.add(new AffairRequest(context).getAffairUpdateRequest());
	}

	// 发送新反馈
	public void sendFeedback(FeedbackModel feedback) {
		queue.add(new FeedbackRequest(feedback).sendFeedbackRequest());
	}

	// 获取新反馈
	public void getFeedbackUpdate() {
		// queue.add(new GetFeedbackUpdateRequest(context).getRequest());
		queue.add(new FeedbackRequest(context).getFeedbackUpdateRequest());
	}

	// 发送新消息
	public void sendMessage(MessageModel message) {
		queue.add(new MessageRequest(message).sendMessageRequest());
	}

	// 获取新消息
	public void getMessageUpdate() {
		queue.add(new MessageRequest(context).getMessageUpdateRequest());
	}

	public void sendCrashReport(JsonObjectRequest request) {
		queue.add(request);
	}

}
