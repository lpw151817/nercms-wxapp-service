package android.wxapp.service.request;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import android.wxapp.service.handler.MessageHandlerManager;
import android.wxapp.service.jerry.model.affair.TaskUpdateQueryRequest;
import android.wxapp.service.jerry.model.affair.TaskUpdateQueryResponse;
import android.wxapp.service.jerry.model.message.MessageUpdateQueryRequest;
import android.wxapp.service.jerry.model.message.MessageUpdateQueryResponse;
import android.wxapp.service.jerry.model.message.QueryContactPersonMessageRequest;
import android.wxapp.service.jerry.model.message.QueryContactPersonMessageResponse;
import android.wxapp.service.jerry.model.message.QueryContactPersonMessageResponseIds;
import android.wxapp.service.jerry.model.message.ReceiveMessageRequest;
import android.wxapp.service.jerry.model.message.ReceiveMessageResponse;
import android.wxapp.service.jerry.model.message.SendMessageRequest;
import android.wxapp.service.jerry.model.message.SendMessageRequestIds;
import android.wxapp.service.jerry.model.message.SendMessageResponse;
import android.wxapp.service.jerry.model.normal.NormalServerResponse;
import android.wxapp.service.model.MessageModel;
import android.wxapp.service.thread.SaveMessageThread;
import android.wxapp.service.thread.SaveMessageUpdateThread;
import android.wxapp.service.thread.ThreadManager;
import android.wxapp.service.util.Constant;
import android.wxapp.service.util.MyJsonParseUtil;
import android.wxapp.service.util.MySharedPreference;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.JsonObjectRequest;

public class MessageRequest extends BaseRequest {

	// ======================================================
	//
	// JerryLiu 2015.5.22
	//
	// 1.getMessageUpdateRequest
	// 2.sendMessageRequest
	// 3.queryContactPersonMessage
	// 4.deleteMessage
	// 5.queryMessageHistory
	// 6.receiveMessage
	//
	// ======================================================

	public MessageRequest() {

	}

	/**
	 * ��ȡ��Ϣ���� JERRY 5.23
	 * <p>
	 * �жϷ��ص�sֵ�Ƿ���Ҫ�����ٴε���
	 * 
	 * @param c
	 * @param ic
	 * @param count
	 *            ����ǵڼ��β�ѯ���ݣ����ڷ�ҳ�������ݡ�������Ҫά��countֵ
	 * @return
	 */
	public JsonObjectRequest getMessageUpdateRequest(final Context c, String count) {
		// ���Ϊ��ȡ���û���id����ֱ�ӷ���
		if (getUserId(c) == null || getUserIc(c) == null)
			return null;
		String tempLastMessageUpdateTime;
		if (getLastMessageUpdateTime(c) == null)
			tempLastMessageUpdateTime = "";
		else
			tempLastMessageUpdateTime = getLastMessageUpdateTime(c);
		MessageUpdateQueryRequest params = new MessageUpdateQueryRequest(getUserId(c), getUserIc(c),
				tempLastMessageUpdateTime, count);
		this.url = Contants.SERVER_URL + Contants.MODEL_NAME + Contants.METHOD_MESSAGE_UPDATE
				+ Contants.PARAM_NAME + super.gson.toJson(params);
		System.out.println(this.url);
		return new JsonObjectRequest(this.url, null, new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject arg0) {
				System.out.println(arg0.toString());
				try {
					// ��ʾ�Ѿ�û�и����������Ҫ�ٴν�������
					if (arg0.getString("s").equals(Contants.RESULT_SUCCESS)) {
						MessageUpdateQueryResponse r = gson.fromJson(arg0.toString(),
								MessageUpdateQueryResponse.class);
						// �������ݿ�Ĳ���,��������
						new SaveMessageUpdateThread(c, r).run();
						// �����ؽ�����ظ�handler����ui����
						MessageHandlerManager.getInstance().sendMessage(
								Constant.UPDATE_MESSAGE_REQUEST_SUCCESS, r,
								Contants.METHOD_MESSAGE_UPDATE);
					}
					// ���и����������Ҫ��������
					else if (arg0.getString("s").equals(Contants.RESULT_MORE)) {
						MessageUpdateQueryResponse r = gson.fromJson(arg0.toString(),
								MessageUpdateQueryResponse.class);
						// �������ݿ�Ĳ���,��������
						new SaveMessageUpdateThread(c, r).run();
						// �����ؽ�����ظ�handler����ui����
						MessageHandlerManager.getInstance().sendMessage(
								Constant.UPDATE_MESSAGE_REQUEST_SUCCESS, r,
								Contants.METHOD_MESSAGE_UPDATE);
					} else {
						NormalServerResponse r = gson.fromJson(arg0.toString(),
								NormalServerResponse.class);
						// �����ؽ�����ظ�handler����ui����
						MessageHandlerManager.getInstance().sendMessage(
								Constant.UPDATE_MESSAGE_REQUEST_FAIL, r, Contants.METHOD_MESSAGE_UPDATE);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError arg0) {
				arg0.printStackTrace();
			}
		});

		// userID = MySharedPreference.get(context, MySharedPreference.USER_ID,
		// "848982460");
		// lastUpdateTime = MySharedPreference.get(context,
		// MySharedPreference.LAST_UPDATE_MESSAGE_TIMESTAMP, "1398009600");
		//
		// updateRequestString = Constant.SERVER_BASE_URL +
		// Constant.GET_MESSAGE_UPDATE_URL
		// + "?param={\"cid\":\"" + userID + "\",\"tsp\":\"" + lastUpdateTime +
		// "\"}";
		// Log.v("getMessageUpdate", updateRequestString);
		//
		// JsonObjectRequest getMessageUpdateRequest = new
		// JsonObjectRequest(updateRequestString, null,
		// new Response.Listener<JSONObject>() {
		//
		// @Override
		// public void onResponse(JSONObject response) {
		// // �жϷ������Ƿ񷵻سɹ�����֪ͨHandler������Ϣ
		// Log.i("getMessageUpdate", response.toString());
		// Log.v("getMessageUpdate", "��ȡ����Ϣ�ɹ�");
		//
		// ArrayList<MessageModel> messages = getMessageListFromJson(response);
		// if (messages != null) {
		// Log.v("getMessageUpdate", "��ת��Ϣ�����߳�֮ǰ");
		// ThreadManager.getInstance().startSaveMessageThread(messages, false,
		// context);
		// } else {
		// Log.v("getMessageUpdate", "�����ݸ���");
		// }
		// }
		// }, new Response.ErrorListener() {
		//
		// @Override
		// public void onErrorResponse(VolleyError error) {
		//
		// }
		// });
		// return getMessageUpdateRequest;
	}

	/**
	 * ��������Ϣ JERRY 5.23
	 * 
	 * @param context
	 * @param ic
	 * @param t
	 *            ��Ϣ����,0 ��ͨ����������Ϣ1 ����Ⱥ����Ϣ 2.�ǻ���Ⱥ����Ϣ3.�����¼��Ϣ 4.��������Ϣ
	 * @param sid
	 *            ������ID
	 * @param rids
	 *            �����Աid����
	 * @param st
	 *            ����ʱ��
	 * @param c
	 *            ��Ϣ�ı����ݣ�����ϢΪ������Ϣʱ���ֶ�Ϊ��
	 * @param at
	 *            �������� 1.�ı� 2.ͼƬ 3.¼�� 4.¼�� 5. GPS
	 * @param au
	 *            �������ӣ�����ϢΪ�ı���Ϣʱ���ֶ�Ϊ�գ�
	 * @param ut
	 *            ����ʱ��
	 * @return
	 */
	public JsonObjectRequest sendMessageRequest(final Context context, String t, String sid, String rid,
			String st, String c, String at, String au, String ut) {
		// ���Ϊ��ȡ���û���id����ֱ�ӷ���
		if (getUserId(context) == null || getUserIc(context) == null)
			return null;
		final SendMessageRequest params = new SendMessageRequest(getUserId(context), getUserIc(context),
				t, sid, rid, st, c, at, au, ut);
		this.url = Contants.SERVER_URL + Contants.MODEL_NAME + Contants.METHOD_MESSAGE_SEND
				+ Contants.PARAM_NAME + super.gson.toJson(params);
		System.out.println(this.url);
		return new JsonObjectRequest(this.url, null, new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject arg0) {
				System.out.println(arg0.toString());
				try {
					if (arg0.getString("s").equals(Contants.RESULT_SUCCESS)) {
						SendMessageResponse r = gson.fromJson(arg0.toString(), SendMessageResponse.class);
						// �������ݿ��insert
						new SaveMessageThread(context, r.getMid(), new ReceiveMessageResponse("", params
								.getT(), params.getSid(), params.getRid(), params.getSt(),
								params.getC(), params.getAt(), params.getAu(), params.getUt(), null))
								.run();
						// �����ؽ�����ظ�handler����ui����
						MessageHandlerManager.getInstance().sendMessage(
								Constant.SEND_MESSAGE_REQUEST_SUCCESS, r, Contants.METHOD_MESSAGE_SEND);
					} else {
						NormalServerResponse r = gson.fromJson(arg0.toString(),
								NormalServerResponse.class);
						// �����ؽ�����ظ�handler����ui����
						MessageHandlerManager.getInstance().sendMessage(
								Constant.SEND_MESSAGE_REQUEST_FAIL, r, Contants.METHOD_MESSAGE_SEND);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError arg0) {
				arg0.printStackTrace();
				Toast.makeText(context, "����������ʧ��", Toast.LENGTH_LONG).show();
			}
		});

		// /* ������Ϣ */
		// // ��������JSON����������ַ
		// StringBuilder requestParams = new StringBuilder();
		// requestParams.append("{\"mid\":\"" + message.getMessageID() + "\",");
		// requestParams.append("\"sid\":\"" + message.getSenderID() + "\",");
		// requestParams.append("\"rid\":\"" + message.getReceiverID() + "\",");
		// requestParams.append("\"from\":\"" + Constant.MOBILE + "\",");
		// requestParams.append("\"dpt\":\"" + message.getDescription() +
		// "\",");
		// requestParams.append("\"st\":\"" + message.getSendTime() + "\",");
		//
		// if (message.getDescription() == null // �ı�Ϊ�գ�������Ϣ
		// || message.getDescription().equalsIgnoreCase("")) {
		// requestParams.append("\"attm\":{\"atype\":\"" +
		// message.getAttachmentType() + "\",");
		// requestParams.append("\"name\":\"" + message.getAttachmentURL() +
		// "\"}}");
		// } else { // ����Ϊ�գ��ı���Ϣ
		// requestParams.append("\"attm\":null}");
		// }
		//
		// Log.i("SendMessageRequest", Constant.SERVER_BASE_URL +
		// Constant.SEND_MESSAGE_URL + "?param="
		// + requestParams.toString());
		//
		// try {
		// sendRequestString = Constant.SERVER_BASE_URL +
		// Constant.SEND_MESSAGE_URL + "?param="
		// + URLEncoder.encode(requestParams.toString(), "UTF-8");
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		//
		// JsonObjectRequest sendMessageRequest = new
		// JsonObjectRequest(sendRequestString, null,
		// new Response.Listener<JSONObject>() {
		//
		// @Override
		// public void onResponse(JSONObject response) {
		// // ���ͳɹ����жϷ������Ƿ񷵻سɹ�����֪ͨHandler������Ϣ
		// Log.i("sendMessageRequest", response.toString());
		//
		// try {
		// if (response.getString("success").equals("0")) {
		// MessageHandlerManager.getInstance().sendMessage(
		// Constant.SEND_MESSAGE_REQUEST_SUCCESS, response, "MainActivity");
		// } else if (response.getString("success").equals("1")) {
		// MessageHandlerManager.getInstance().sendMessage(
		// Constant.SEND_MESSAGE_REQUEST_FAIL, response, "MainActivity");
		// }
		// } catch (JSONException e) {
		// e.printStackTrace();
		// }
		// }
		// }, new Response.ErrorListener() {
		//
		// @Override
		// public void onErrorResponse(VolleyError error) {
		//
		// }
		// });
		//
		// return sendMessageRequest;
	}

	/**
	 * ��ѯ�����ϵ�� �����Ϣ�б�
	 * 
	 * @param context
	 * @param ic
	 * @return
	 */
	public JsonObjectRequest queryContactPersonMessage(Context context, String ic) {
		// ���Ϊ��ȡ���û���id����ֱ�ӷ���
		if (getUserId(context) == null)
			return null;
		QueryContactPersonMessageRequest params = new QueryContactPersonMessageRequest(
				getUserId(context), ic);
		this.url = Contants.SERVER_URL + Contants.MODEL_NAME
				+ Contants.METHOD_MESSAGE_QUERY_CONTACT_MERSON_MESSAGE + Contants.PARAM_NAME
				+ super.gson.toJson(params);
		System.out.println(this.url);
		return new JsonObjectRequest(this.url, null, new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject arg0) {
				System.out.println(arg0.toString());
				try {
					// ��ʾ�Ѿ�û�и����������Ҫ�ٴν�������
					if (arg0.getString("s").equals(Contants.RESULT_SUCCESS)) {
						QueryContactPersonMessageResponse r = gson.fromJson(arg0.toString(),
								QueryContactPersonMessageResponse.class);
						// �����ؽ�����ظ�handler����ui����
						MessageHandlerManager.getInstance().sendMessage(
								Constant.QUERY_RECENT_MESSAGE_REQUEST_SUCCESS, r,
								Contants.METHOD_MESSAGE_QUERY_CONTACT_MERSON_MESSAGE);
					} else {
						NormalServerResponse r = gson.fromJson(arg0.toString(),
								NormalServerResponse.class);
						// �����ؽ�����ظ�handler����ui����
						MessageHandlerManager.getInstance().sendMessage(
								Constant.QUERY_RECENT_MESSAGE_REQUEST_FAIL, r,
								Contants.METHOD_MESSAGE_QUERY_CONTACT_MERSON_MESSAGE);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError arg0) {
				arg0.printStackTrace();
			}
		});
	}

	// �ݲ�ʵ��
	public JsonObjectRequest deleteMessage() {
		return null;
	}

	// �ݲ�ʵ��
	public JsonObjectRequest queryMessageHistory() {
		return null;
	}

	/**
	 * ��ѯ���յ�����Ϣ����ϸ����
	 * 
	 * @param context
	 * @param ic
	 * @param mid
	 *            ��Ҫ��ѯ����Ϣid
	 * @return
	 */
	public JsonObjectRequest receiveMessage(Context context, String ic, String mid) {
		// ���Ϊ��ȡ���û���id����ֱ�ӷ���
		if (getUserId(context) == null)
			return null;
		ReceiveMessageRequest params = new ReceiveMessageRequest(getUserId(context), ic, mid);
		this.url = Contants.SERVER_URL + Contants.MODEL_NAME + Contants.METHOD_MESSAGE_RECEIVE
				+ Contants.PARAM_NAME + super.gson.toJson(params);
		System.out.println(this.url);
		return new JsonObjectRequest(this.url, null, new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject arg0) {
				System.out.println(arg0.toString());
				try {
					// ��ʾ�Ѿ�û�и����������Ҫ�ٴν�������
					if (arg0.getString("s").equals(Contants.RESULT_SUCCESS)) {
						ReceiveMessageResponse r = gson.fromJson(arg0.toString(),
								ReceiveMessageResponse.class);
						// �����ؽ�����ظ�handler����ui����
						MessageHandlerManager.getInstance().sendMessage(
								Constant.QUERY_MESSAGE_INFO_REQUEST_SUCCESS, r,
								Contants.METHOD_MESSAGE_RECEIVE);
					} else {
						NormalServerResponse r = gson.fromJson(arg0.toString(),
								NormalServerResponse.class);
						// �����ؽ�����ظ�handler����ui����
						MessageHandlerManager.getInstance().sendMessage(
								Constant.QUERY_MESSAGE_INFO_REQUEST_FAIL, r,
								Contants.METHOD_MESSAGE_RECEIVE);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError arg0) {
				arg0.printStackTrace();
			}
		});
	}

	// private ArrayList<MessageModel> getMessageListFromJson(JSONObject
	// resultJsonObject) {
	// String updateTimeStamp = null;
	// ArrayList<MessageModel> messageList = null;
	// if (resultJsonObject != null) {
	// messageList = null;
	// messageList = MyJsonParseUtil.getMessageList(resultJsonObject);
	// if (messageList == null) {
	// return null;
	// }
	// updateTimeStamp = MyJsonParseUtil.getUpdateTimeStamp(resultJsonObject);
	//
	// // �������һ�θ�����Ϣ��ʱ��
	// if (updateTimeStamp != null) {
	// MySharedPreference.save(context,
	// MySharedPreference.LAST_UPDATE_MESSAGE_TIMESTAMP,
	// updateTimeStamp);
	// }
	// }
	//
	// return messageList;
	// }

}
