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
	 * 解析json对象中的OrgNode（数组）
	 * 
	 * @param resultJsonObject
	 * @return OrgNode模型列表
	 */

	public static ArrayList<OrgNodeModel> getOrgNodeList(
			JSONObject resultJsonObject) {
		ArrayList<OrgNodeModel> orgNodeList;

		// 解析JSON
		try {
			JSONArray orgNodeArray = resultJsonObject.optJSONArray("orgNode");

			// 根据返回信息中的消息数构造列表
			int size = orgNodeArray.length();
			orgNodeList = new ArrayList<OrgNodeModel>(size);

			// 解析消息
			String orgCode, description;

			for (int i = 0; i < size; i++) {
				JSONObject orgNodeJsonObject = orgNodeArray.getJSONObject(i);
				orgCode = orgNodeJsonObject.getString("ocd");
				description = orgNodeJsonObject.getString("dpt");

				// 添加到列表
				orgNodeList.add(new OrgNodeModel(orgCode, description));
			}

			return orgNodeList;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * 解析json对象中的OrgNodeStaff（数组）
	 * 
	 * @param resultJsonObject
	 * @return OrgNodeStaff模型列表
	 */
	public static ArrayList<OrgNodeStaffModel> getOrgNodeStaffList(
			JSONObject resultJsonObject) {
		ArrayList<OrgNodeStaffModel> orgNodeStaffList;
		// 解析JSON
		try {
			JSONArray orgNodeStaffArray = resultJsonObject
					.optJSONArray("orgNodeStaff");

			// 根据返回信息中的消息数构造列表
			int size = orgNodeStaffArray.length();
			orgNodeStaffList = new ArrayList<OrgNodeStaffModel>(size);

			// 解析消息
			String orgCode, contactID, sequence;

			for (int i = 0; i < size; i++) {
				JSONObject orgNodeStaffJsonObject = orgNodeStaffArray
						.getJSONObject(i);
				orgCode = orgNodeStaffJsonObject.getString("ocd");
				contactID = orgNodeStaffJsonObject.getString("cid");
				sequence = orgNodeStaffJsonObject.getString("sq");

				// 添加到列表
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
	 * 解析json对象中的OrgStaff（数组）
	 * 
	 * @param resultJsonObject
	 * @return OrgStaff模型列表
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
	 * 解析json对象中的Contact（数组）
	 * 
	 * @param resultJsonObject
	 * @return Contact模型列表
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
	 * 解析Json对象中的Customer（数组）
	 * 
	 * @param resultJsonObject
	 * @return Customer模型列表
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
	 * 解析Json对象中的CustomerContact（数组）
	 * 
	 * @param resultJsonObject
	 * @return CustomerContact模型列表
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
	 * 解析json对象中的事务
	 * 
	 * @param resultJsonObject
	 * @return 事务列表（包含责任人，附件列表）
	 */
	public static ArrayList<AffairModel> getAffairList(
			JSONObject resultJsonObject) {

		ArrayList<AffairModel> affairList;

		try {
			// Json 根节点 tasks[]， 事务数组
			JSONArray affairArray = resultJsonObject.optJSONArray("tasks");

			if (affairArray == null || affairArray.equals("")) {
				return null;
			} else {

				int size = affairArray.length();
				affairList = new ArrayList<AffairModel>(size);

				// Json 二级节点 tasks数组项：事务ID；事务内容；事务反馈
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

					// Json 三级节点1，content，事务内容Json对象，包含以下

					// String affairID = contentJsonObject.getString("aid");
					String beginTime = contentJsonObject.getString("bt");
					String endTime = contentJsonObject.getString("et");
					String completeTime = contentJsonObject.getString("ct");
					int lastOperateType = Integer.parseInt(contentJsonObject
							.getString("lotp"));
					String lastOperateTime = contentJsonObject
							.getString("lotm");
					int sponsorID = Integer.parseInt(contentJsonObject
							.getString("sid")); // 发起人ID
					int personID = Integer.parseInt(contentJsonObject
							.getString("pid")); // 责任人ID
					String title = contentJsonObject.getString("tit");
					String description = contentJsonObject.getString("dpt");
					int type = Integer.parseInt(contentJsonObject
							.getString("type"));
					int status = Integer.parseInt(contentJsonObject
							.getString("sts"));
					int fbCount = Integer.parseInt(contentJsonObject
							.getString("fc")); // 有反馈数目

					// Json 三级节点1，attm[]， 事务附件数组
					JSONArray affairAttachsArray = contentJsonObject
							.getJSONArray("attm");

					int affairAttSize = affairAttachsArray.length();
					ArrayList<AffairAttachModel> affairAttList = new ArrayList<AffairAttachModel>(
							affairAttSize);

					// Json 四级节点1.1，attm[]项， 事务附件
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

					// 添加单个事务模型到list
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
	 * 解析json对象中的反馈
	 * 
	 * @param resultJsonObject
	 * @return 反馈列表（包含附件）
	 */
	public static ArrayList<FeedbackModel> getFeedbackList(
			JSONObject resultJsonObject) {

		ArrayList<FeedbackModel> feedbackList;
		try {
			// Json 根节点 feedback[]， 事务数组
			JSONArray feedbackArray = resultJsonObject.optJSONArray("feedback");
			if (feedbackArray == null || feedbackArray.equals("")) {
				return null;
			} else {
				int size = feedbackArray.length();
				feedbackList = new ArrayList<FeedbackModel>(size);

				// Json 二级节点 feedback数组项

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

					// 新反馈
					FeedbackModel feedback = new FeedbackModel(feedbackID,
							affairID, senderID, feedbackTime, fbDescription,
							Constant.UNREAD);

					// Json 三级节点，attm， 反馈附件
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
	 * 解析Json对象中的消息
	 * 
	 * @param resultJsonObject
	 * @return 消息列表
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
				// 判断消息类型
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

				// 2014-7-30 群消息标志暂标为0，是否是群消息将在保存数据库的时候做判断
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
	 * 解析json对象中的时戳
	 * 
	 * @param resultJsonObject
	 * @return 时戳 字符串
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
