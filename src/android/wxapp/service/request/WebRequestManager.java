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
 * �������������
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

	// ��¼��֤
	public void loginVarification(String aliasName, String identifyCode, String imsi) {
		queue.add(new PersonRequest().getLoginRequest(aliasName, identifyCode, imsi));
	}

	// �޸��û�����
	@Deprecated
	public void changePassword(String identifyCode, String newIdentifyCode) {
		queue.add(new PersonRequest().getChangePasswordrRequest(this.context, identifyCode,
				newIdentifyCode));
	}

	// ��ȡ������ϵ����Ϣ
	public void getAllPerson() {
		// queue.add(new GetAllPersonRequest(context).getRequest());
		queue.add(new PersonRequest().getAllPersonRequest(context));
	}

	// �½��ͻ�
	public void newCustomer(CustomerModel customer, ArrayList<CustomerContactModel> contactList) {
		queue.add(new PersonRequest().sendNewCustomerRequest(context, customer, contactList));
	}

	// �༭�ͻ���Ϣ
	public void modifyCustomer(CustomerModel customer, ArrayList<CustomerContactModel> contactList) {
		queue.add(new PersonRequest().sendModifyCustomerRequest(customer, contactList));
	}

	// ɾ���ͻ�
	public void deleteCustomer(String customerID) {
		queue.add(new PersonRequest().sendDeleteCustomerRequest(context, customerID));
	}

	// ����������
	public void sendAffair(AffairModel affair) {
		// ��ӵ�RequestQueue
		// queue.add(new SendAffairRequest(affair).getRequest());
		queue.add(new AffairRequest(affair).getCreateAffairRequest());
	}

	// �޸��������ʱ��
	public void modifyAffairEndTime(String affairID, String timeString) {
		queue.add(new AffairRequest(context, affairID, timeString).getModifyEndTimeRequest());
	}

	// �������
	public void endAffair(String affairID) {
		queue.add(new AffairRequest(context, affairID).getEndTaskRequest());
	}

	// ��ȡ�������
	public void getAffairUpdate() {
		// queue.add(new GetAffairUpdateRequest(context).getRequest());
		queue.add(new AffairRequest(context).getAffairUpdateRequest());
	}

	// �����·���
	public void sendFeedback(FeedbackModel feedback) {
		queue.add(new FeedbackRequest(feedback).sendFeedbackRequest());
	}

	// ��ȡ�·���
	public void getFeedbackUpdate() {
		// queue.add(new GetFeedbackUpdateRequest(context).getRequest());
		queue.add(new FeedbackRequest(context).getFeedbackUpdateRequest());
	}

	// ��������Ϣ
	public void sendMessage(MessageModel message) {
		queue.add(new MessageRequest(message).sendMessageRequest());
	}

	// ��ȡ����Ϣ
	public void getMessageUpdate() {
		queue.add(new MessageRequest(context).getMessageUpdateRequest());
	}

	public void sendCrashReport(JsonObjectRequest request) {
		queue.add(request);
	}

}
