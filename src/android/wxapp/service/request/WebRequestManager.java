package android.wxapp.service.request;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.provider.MediaStore.Video;
import android.wxapp.service.AppApplication;
import android.wxapp.service.jerry.model.affair.CreateTaskRequestAttachment;
import android.wxapp.service.jerry.model.affair.CreateTaskRequestIds;
import android.wxapp.service.jerry.model.conference.ConferenceUpdateQueryResponseRids;
import android.wxapp.service.jerry.model.feedback.TaskFeedbackRequestIds;
import android.wxapp.service.jerry.model.group.GroupUpdateQueryRequestIds;
import android.wxapp.service.jerry.model.person.GetPersonInfoRequest;
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

	//
	// Person Model
	//

	// ��¼��֤ FINAL Jerry 5.25
	public void loginVarification(String aliasName, String identifyCode, String imsi) {
		queue.add(new PersonRequest().getLoginRequest(aliasName, identifyCode, imsi));
	}

	public void logOut() {
		queue.add(new PersonRequest().logOut(context));
	}

	// ��ȡ��Ҫ���µ���֯���
	public void getOrgCodeUpdate() {
		queue.add(new PersonRequest().getOrgCodeUpdate(this.context));
	}

	// ��ȡ��Ҫ���µ���Ա��Ϣ
	public void getOrgPersonUpdate() {
		queue.add(new PersonRequest().getOrgCodePersonUpdate(this.context));
	}

	// ��ѯ��Ӧperson id���û���Ϣ
	public void GetPersonInfo(String personId) {
		queue.add(new PersonRequest().getPersonInfo(context, personId));
	}

	// �޸��û����� �����ӿ� Jerry 5.25
	@Deprecated
	public void changePassword(String identifyCode, String newIdentifyCode) {
		// queue.add(new PersonRequest().getChangePasswordrRequest(this.context,
		// identifyCode,
		// newIdentifyCode));
	}

	// ��ȡ������ϵ����Ϣ �����ӿ� Jerry 5.25
	@Deprecated
	public void getAllPerson() {
		// queue.add(new GetAllPersonRequest(context).getRequest());
		// queue.add(new PersonRequest().getAllPersonRequest(context));
	}

	// �½��ͻ� �����ӿ� Jerry 5.25
	@Deprecated
	public void newCustomer(CustomerModel customer, ArrayList<CustomerContactModel> contactList) {
		// queue.add(new PersonRequest().sendNewCustomerRequest(context,
		// customer, contactList));
	}

	// �༭�ͻ���Ϣ �����ӿ� Jerry 5.25
	@Deprecated
	public void modifyCustomer(CustomerModel customer,
			ArrayList<CustomerContactModel> contactList) {
		// queue.add(new PersonRequest().sendModifyCustomerRequest(customer,
		// contactList));
	}

	// ɾ���ͻ� �����ӿ� Jerry 5.25
	@Deprecated
	public void deleteCustomer(String customerID) {
		// queue.add(new PersonRequest().sendDeleteCustomerRequest(context,
		// customerID));
	}

	//
	// Affair Model
	//

	// ��ȡ�������
	public void getAffairUpdate(String count) {
		queue.add(new AffairRequest().getAffairUpdateRequest(context, count));
	}

	public void getAffair(String aid) {
		queue.add(new AffairRequest().queryAffairInfo(context, aid));
	}

	// ����������
	public void sendAffair(String t, String d, String topic, String bt, String et, String ct,
			String lot, String lotime, String up, List<String> ats, List<String> us,
			List<String> rids, List<String> pods) {
		queue.add(new AffairRequest().getCreateAffairRequest(context, t, d, topic, bt, et, ct, lot,
				lotime, up, ats, us, rids, pods));
	}

	// �޸��������ʱ��
	public void modifyAffairEndTime(String ic, String affairID, String timeString) {
		// queue.add(new AffairRequest().getModifyEndTimeRequest(context, ic,
		// affairID, timeString));
	}

	public void modifyAffair(String aid, List<CreateTaskRequestIds> pod,
			List<CreateTaskRequestIds> rids, String d, String topic, String et,
			List<CreateTaskRequestAttachment> att) {
		queue.add(new AffairRequest().updateAffairInfo(context, aid, pod, rids, d, topic, et, att));
	}

	// �������
	public void endAffair(String affairID) {
		queue.add(new AffairRequest().getEndTaskRequest(context, affairID,
				System.currentTimeMillis() + ""));
	}

	//
	// Feedback Model
	//

	// �����·���
	public void sendFeedback(String sid, String rid, String st, String c, String at, String au,
			String ut) {
		queue.add(new FeedbackRequest().sendFeedbackRequest(context, sid, st, c, at, au, ut, rid));
	}

	// ��ѯ��������
	public void getFeedbackInfo(String aid) {
		// queue.add(new FeedbackRequest().getFeedbackInfoRequest(context, ic,
		// aid));
	}

	// ��ȡ�·���
	public void getFeedbackUpdate() {
		// queue.add(new GetFeedbackUpdateRequest(context).getRequest());
		// queue.add(new FeedbackRequest().getFeedbackUpdateRequest());
	}

	//
	// Message Model
	//

	// ��������Ϣ
	public void sendMessage(String t, String sid, String rid, String st, String c, String at,
			String au, String ut) {
		queue.add(new MessageRequest().sendMessageRequest(context, t, sid, rid, st, c, at, au, ut));
	}

	// ��ȡ����Ϣ
	public void getMessageUpdate(String count) {
		queue.add(new MessageRequest().getMessageUpdateRequest(context, count));
	}

	public void getMessage(String mid) {
		queue.add(new MessageRequest().receiveMessage(context, mid));
	}

	public void sendCrashReport(JsonObjectRequest request) {
		// queue.add(request);
	}

	//
	// Conference Model
	//

	// ���»���
	public void updateConference(String count) {
		queue.add(new ConferenceRequest().updateConference(context, count));
	}

	public void getConference(String cid) {
		queue.add(new ConferenceRequest().getConference(context, cid));
	}

	// ��������
	public void createConference(String n, String sid, String ct, String f, String r, String et,
			List<ConferenceUpdateQueryResponseRids> rids) {
		queue.add(new ConferenceRequest().createConference(context, n, sid, ct, f, r, et, rids));
	}

	// ��ʼ����
	public void startConference(String cid) {
		queue.add(new ConferenceRequest().startConference(context, cid));
	}

	// ��������
	public void endConference(String cid) {
		queue.add(new ConferenceRequest().endConference(context, cid));
	}

	//
	// Group Model
	//

	// ����
	public void getGroupUpdateRequest(String count) {
		queue.add(new GroupRequest().getGroupUpdateRequest(context, count));
	}

	public void createGroup(String t, String n, String ct, String ut,
			List<GroupUpdateQueryRequestIds> rids) {
		queue.add(new GroupRequest().createGroup(context, t, n, ct, ut, rids));
	}

	public void modifyGroup(String gid, String t, String n, String ct, String ut,
			List<GroupUpdateQueryRequestIds> rids) {
		queue.add(new GroupRequest().modifyGroup(context, gid, t, n, ct, ut, rids));
	}

	public void deleteGroup(String gid) {
		queue.add(new GroupRequest().deleteGroup(context, gid));
	}

	//
	// GPS Medel
	//

	// update
	public void getGpsUpdateRequest(String count) {
		queue.add(new GpsRequest().getGroupUpdate(context, count));
	}
}
