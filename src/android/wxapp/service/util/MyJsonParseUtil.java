package android.wxapp.service.util;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.wxapp.service.model.AffairAttachModel;
import android.wxapp.service.model.AffairModel;
import android.wxapp.service.model.ContactModel;
import android.wxapp.service.model.CustomerContactModel;
import android.wxapp.service.model.CustomerModel;
import android.wxapp.service.model.FeedbackAttachModel;
import android.wxapp.service.model.FeedbackModel;
import android.wxapp.service.model.MessageModel;
import android.wxapp.service.model.OrgNodeModel;
import android.wxapp.service.model.OrgNodeStaffModel;
import android.wxapp.service.model.OrgStaffModel;
import android.wxapp.service.model.PersonOnDutyModel;

public class MyJsonParseUtil {

	/**
	 * ����json�����е�OrgNode�����飩
	 * 
	 * @param resultJsonObject
	 * @return OrgNodeģ���б�
	 */

	public static ArrayList<OrgNodeModel> getOrgNodeList(
			JSONObject resultJsonObject) {
		ArrayList<OrgNodeModel> orgNodeList;

		// ����JSON
		try {
			JSONArray orgNodeArray = resultJsonObject.optJSONArray("orgNode");

			// ���ݷ�����Ϣ�е���Ϣ�������б�
			int size = orgNodeArray.length();
			orgNodeList = new ArrayList<OrgNodeModel>(size);

			// ������Ϣ
			String orgCode, description;

			for (int i = 0; i < size; i++) {
				JSONObject orgNodeJsonObject = orgNodeArray.getJSONObject(i);
				orgCode = orgNodeJsonObject.getString("ocd");
				description = orgNodeJsonObject.getString("dpt");

				// ��ӵ��б�
				orgNodeList.add(new OrgNodeModel(orgCode, description));
			}

			return orgNodeList;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * ����json�����е�OrgNodeStaff�����飩
	 * 
	 * @param resultJsonObject
	 * @return OrgNodeStaffģ���б�
	 */
	public static ArrayList<OrgNodeStaffModel> getOrgNodeStaffList(
			JSONObject resultJsonObject) {
		ArrayList<OrgNodeStaffModel> orgNodeStaffList;
		// ����JSON
		try {
			JSONArray orgNodeStaffArray = resultJsonObject
					.optJSONArray("orgNodeStaff");

			// ���ݷ�����Ϣ�е���Ϣ�������б�
			int size = orgNodeStaffArray.length();
			orgNodeStaffList = new ArrayList<OrgNodeStaffModel>(size);

			// ������Ϣ
			String orgCode, contactID, sequence;

			for (int i = 0; i < size; i++) {
				JSONObject orgNodeStaffJsonObject = orgNodeStaffArray
						.getJSONObject(i);
				orgCode = orgNodeStaffJsonObject.getString("ocd");
				contactID = orgNodeStaffJsonObject.getString("cid");
				sequence = orgNodeStaffJsonObject.getString("sq");

				// ��ӵ��б�
				orgNodeStaffList.add(new OrgNodeStaffModel(orgCode, contactID,
						sequence));
			}

			return orgNodeStaffList;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * ����json�����е�OrgStaff�����飩
	 * 
	 * @param resultJsonObject
	 * @return OrgStaffģ���б�
	 */
	public static ArrayList<OrgStaffModel> getOrgStaffList(
			JSONObject resultJsonObject) {
		ArrayList<OrgStaffModel> orgStaffList;
		try {
			JSONArray orgStaffArray = resultJsonObject.optJSONArray("orgStaff");

			int size = orgStaffArray.length();
			orgStaffList = new ArrayList<OrgStaffModel>(size);

			String contactID, name, position, rank;
			for (int i = 0; i < size; i++) {
				JSONObject orgStaffJsonObject = orgStaffArray.getJSONObject(i);
				contactID = orgStaffJsonObject.getString("id");
				name = orgStaffJsonObject.getString("name");
				position = orgStaffJsonObject.getString("pos");
				rank = orgStaffJsonObject.getString("rk");

				orgStaffList.add(new OrgStaffModel(contactID, name, position,
						rank));
			}

			return orgStaffList;

		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * ����json�����е�Contact�����飩
	 * 
	 * @param resultJsonObject
	 * @return Contactģ���б�
	 */
	public static ArrayList<ContactModel> getContactList(
			JSONObject resultJsonObject) {
		ArrayList<ContactModel> contactList;

		try {
			JSONArray contactArray = resultJsonObject.optJSONArray("contact");

			int size = contactArray.length();
			contactList = new ArrayList<ContactModel>(size);

			String contactID, content;
			int type;
			for (int i = 0; i < size; i++) {
				JSONObject contactJsonObject = contactArray.getJSONObject(i);
				contactID = contactJsonObject.getString("cid");
				type = Integer.parseInt(contactJsonObject.getString("type"));
				content = contactJsonObject.getString("cot");

				contactList.add(new ContactModel(contactID, type, content));
			}

			return contactList;

		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * ����Json�����е�Customer�����飩
	 * 
	 * @param resultJsonObject
	 * @return Customerģ���б�
	 */
	public static ArrayList<CustomerModel> getCustomerList(
			JSONObject resultJsonObject) {
		ArrayList<CustomerModel> customerList;

		try {
			JSONArray customerArray = resultJsonObject.optJSONArray("customer");

			int size = customerArray.length();
			customerList = new ArrayList<CustomerModel>(size);

			String customerID, name, unit, description, contactID;
			for (int i = 0; i < size; i++) {
				JSONObject customerJsonObject = customerArray.getJSONObject(i);
				customerID = customerJsonObject.getString("csid");
				name = customerJsonObject.getString("name");
				unit = customerJsonObject.getString("unit");
				description = customerJsonObject.getString("dpt");
				contactID = customerJsonObject.getString("coid");

				customerList.add(new CustomerModel(customerID, name, unit,
						description, contactID));
			}

			return customerList;

		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * ����Json�����е�CustomerContact�����飩
	 * 
	 * @param resultJsonObject
	 * @return CustomerContactģ���б�
	 */
	public static ArrayList<CustomerContactModel> getCustomerContactList(
			JSONObject resultJsonObject) {
		ArrayList<CustomerContactModel> customerContactList;

		try {

			JSONArray custContactArray = resultJsonObject
					.optJSONArray("custContact");

			int size = custContactArray.length();
			customerContactList = new ArrayList<CustomerContactModel>(size);

			String customerID, content;
			int type;
			for (int i = 0; i < size; i++) {
				JSONObject custContactJsonObject = custContactArray
						.getJSONObject(i);
				customerID = custContactJsonObject.getString("csid");
				type = Integer
						.parseInt(custContactJsonObject.getString("type"));
				content = custContactJsonObject.getString("cot");

				customerContactList.add(new CustomerContactModel(customerID,
						type, content));
			}

			return customerContactList;

		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * ����json�����е�����
	 * 
	 * @param resultJsonObject
	 * @return �����б����������ˣ������б�
	 */
	public static ArrayList<AffairModel> getAffairList(
			JSONObject resultJsonObject) {

		ArrayList<AffairModel> affairList;

		try {
			// Json ���ڵ� tasks[]�� ��������
			JSONArray affairArray = resultJsonObject.optJSONArray("tasks");

			if (affairArray == null || affairArray.equals("")) {
				return null;
			} else {

				int size = affairArray.length();
				affairList = new ArrayList<AffairModel>(size);

				// Json �����ڵ� tasks���������ID���������ݣ�������
				String affairID;
				JSONObject contentJsonObject = new JSONObject();
				// JSONArray feedbackJsonaArray = new JSONArray();
				for (int i = 0; i < size; i++) {
					JSONObject affairJsonObject = affairArray.getJSONObject(i);
					affairID = affairJsonObject.getString("aid");
					contentJsonObject = affairJsonObject
							.getJSONObject("content");
					// feedbackJsonaArray =
					// affairJsonObject.getJSONArray("feedback");

					// Json �����ڵ�1��content����������Json���󣬰�������

					// String affairID = contentJsonObject.getString("aid");
					String beginTime = contentJsonObject.getString("bt");
					String endTime = contentJsonObject.getString("et");
					String completeTime = contentJsonObject.getString("ct");
					int lastOperateType = Integer.parseInt(contentJsonObject
							.getString("lotp"));
					String lastOperateTime = contentJsonObject
							.getString("lotm");
					int sponsorID = Integer.parseInt(contentJsonObject
							.getString("sid")); // ������ID
					int personID = Integer.parseInt(contentJsonObject
							.getString("pid")); // ������ID
					String title = contentJsonObject.getString("tit");
					String description = contentJsonObject.getString("dpt");
					int type = Integer.parseInt(contentJsonObject
							.getString("type"));
					int status = Integer.parseInt(contentJsonObject
							.getString("sts"));
					int fbCount = Integer.parseInt(contentJsonObject
							.getString("fc")); // �з�����Ŀ

					// Json �����ڵ�1��attm[]�� ���񸽼�����
					JSONArray affairAttachsArray = contentJsonObject
							.getJSONArray("attm");

					int affairAttSize = affairAttachsArray.length();
					ArrayList<AffairAttachModel> affairAttList = new ArrayList<AffairAttachModel>(
							affairAttSize);

					// Json �ļ��ڵ�1.1��attm[]� ���񸽼�
					for (int j = 0; j < affairAttSize; j++) {
						JSONObject affairAttachJsonObject = affairAttachsArray
								.getJSONObject(j);
						int affairAttachType = Integer
								.parseInt(affairAttachJsonObject
										.getString("atype"));
						String affairAttachURL = affairAttachJsonObject
								.getString("url");

						affairAttList.add(new AffairAttachModel(affairID,
								affairAttachType, affairAttachURL));
					}

					// ��ӵ�������ģ�͵�list
					PersonOnDutyModel pod = new PersonOnDutyModel(affairID,
							personID);
					AffairModel affair = new AffairModel(affairID, type,
							sponsorID, title, description, beginTime, endTime,
							completeTime, lastOperateType, lastOperateTime,
							Constant.UNREAD, status);
					affair.setPerson(pod);
					affair.setAttachments(affairAttList);
					affairList.add(affair);
				}
			}

			return affairList;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * ����json�����еķ���
	 * 
	 * @param resultJsonObject
	 * @return �����б�����������
	 */
	public static ArrayList<FeedbackModel> getFeedbackList(
			JSONObject resultJsonObject) {

		ArrayList<FeedbackModel> feedbackList;
		try {
			// Json ���ڵ� feedback[]�� ��������
			JSONArray feedbackArray = resultJsonObject.optJSONArray("feedback");
			if (feedbackArray == null || feedbackArray.equals("")) {
				return null;
			} else {
				int size = feedbackArray.length();
				feedbackList = new ArrayList<FeedbackModel>(size);

				// Json �����ڵ� feedback������

				for (int i = 0; i < size; i++) {
					JSONObject feedbackJsonObject = feedbackArray
							.getJSONObject(i);
					String feedbackID = feedbackJsonObject.getString("fid");
					String affairID = feedbackJsonObject.getString("aid");
					int senderID = Integer.parseInt(feedbackJsonObject
							.getString("sid"));
					int receiverID = Integer.parseInt(feedbackJsonObject
							.getString("rid"));
					String feedbackTime = feedbackJsonObject.getString("st");
					String fbDescription = feedbackJsonObject.getString("dpt");

					// �·���
					FeedbackModel feedback = new FeedbackModel(feedbackID,
							affairID, senderID, feedbackTime, fbDescription,
							Constant.UNREAD);

					// Json �����ڵ㣬attm�� ��������
					if (feedbackJsonObject.getString("attm").equalsIgnoreCase(
							"")) {
						feedback.setAttachment(null);
					} else {
						JSONObject fbAttachJsonObject = feedbackJsonObject
								.getJSONObject("attm");
						int fbAttachType = Integer.parseInt(fbAttachJsonObject
								.getString("atype"));
						String fbAttachURL = fbAttachJsonObject
								.getString("url");

						FeedbackAttachModel fbAttach = new FeedbackAttachModel(
								feedbackID, fbAttachType, fbAttachURL);

						feedback.setAttachment(fbAttach);
					}

					feedbackList.add(feedback);

				}
			}
			return feedbackList;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * ����Json�����е���Ϣ
	 * 
	 * @param resultJsonObject
	 * @return ��Ϣ�б�
	 */
	public static ArrayList<MessageModel> getMessageList(
			JSONObject resultJsonObject) {
		ArrayList<MessageModel> messageList;
		try {

			if (resultJsonObject.toString().equalsIgnoreCase("{}")) {
				return null;
			}

			JSONArray messageArray = resultJsonObject.getJSONArray("message");

			int size = messageArray.length();
			messageList = new ArrayList<MessageModel>(size);

			for (int i = 0; i < size; i++) {
				JSONObject messageJsonObject = messageArray.getJSONObject(i);
				String messageID = messageJsonObject.getString("mid");
				int senderID = Integer.parseInt(messageJsonObject
						.getString("sid"));
				int receiverID = Integer.parseInt(messageJsonObject
						.getString("rid"));
				int from = Integer
						.parseInt(messageJsonObject.getString("from"));
				String sendTime = messageJsonObject.getString("st");

				String description;
				int attachmentType;
				String attachmentURL;
				// �ж���Ϣ����
				if (messageJsonObject.getString("dpt").equalsIgnoreCase("")) {
					description = null;
					JSONObject attachJsonObject = messageJsonObject
							.getJSONObject("attm");
					attachmentType = Integer.parseInt(attachJsonObject
							.getString("atype"));
					attachmentURL = attachJsonObject.getString("url");
				} else {
					description = messageJsonObject.getString("dpt");
					attachmentType = 0;
					attachmentURL = null;
				}

				// 2014-7-30 Ⱥ��Ϣ��־�ݱ�Ϊ0���Ƿ���Ⱥ��Ϣ���ڱ������ݿ��ʱ�����ж�
				messageList.add(new MessageModel(messageID, senderID,
						receiverID, sendTime, description, attachmentType,
						attachmentURL, 0, Constant.UNREAD));
			}

			return messageList;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * ����json�����е�ʱ��
	 * 
	 * @param resultJsonObject
	 * @return ʱ�� �ַ���
	 */
	public static String getUpdateTimeStamp(JSONObject resultJsonObject) {
		try {
			return resultJsonObject.getString("tsp");
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
}
