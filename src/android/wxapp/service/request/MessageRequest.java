package android.wxapp.service.request;

import java.net.URLEncoder;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;
import android.wxapp.service.handler.MessageHandlerManager;
import android.wxapp.service.model.MessageModel;
import android.wxapp.service.thread.ThreadManager;
import android.wxapp.service.util.Constant;
import android.wxapp.service.util.MyJsonParseUtil;
import android.wxapp.service.util.MySharedPreference;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

public class MessageRequest {

	private Context context;
	/**
	 * �û�id
	 */
	private String userID;
	/**
	 * ������ʱ��
	 */
	private String lastUpdateTime;
	/**
	 * 
	 */
	private String updateRequestString = null;

	private MessageModel message = null;
	private String sendRequestString = null;

	// ��ȡ����Ϣ�Ĺ��캯��
	public MessageRequest(Context context) {
		this.context = context;
	}

	// ��������Ϣ�Ĺ��캯��
	public MessageRequest(MessageModel message) {
		this.message = message;
	}

	/**
	 * �õ��ӷ�������ȡ����Ϣ��Request
	 * 
	 * @return
	 */
	public JsonObjectRequest getMessageUpdateRequest() {
		userID = MySharedPreference.get(context, MySharedPreference.USER_ID, "848982460");
		lastUpdateTime = MySharedPreference.get(context,
				MySharedPreference.LAST_UPDATE_MESSAGE_TIMESTAMP, "1398009600");

		updateRequestString = Constant.SERVER_BASE_URL + Constant.GET_MESSAGE_UPDATE_URL
				+ "?param={\"cid\":\"" + userID + "\",\"tsp\":\"" + lastUpdateTime + "\"}";
		Log.v("getMessageUpdate", updateRequestString);

		JsonObjectRequest getMessageUpdateRequest = new JsonObjectRequest(updateRequestString, null,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						// �жϷ������Ƿ񷵻سɹ�����֪ͨHandler������Ϣ
						Log.i("getMessageUpdate", response.toString());
						Log.v("getMessageUpdate", "��ȡ����Ϣ�ɹ�");

						ArrayList<MessageModel> messages = getMessageListFromJson(response);
						if (messages != null) {
							Log.v("getMessageUpdate", "��ת��Ϣ�����߳�֮ǰ");
							ThreadManager.getInstance().startSaveMessageThread(messages, false, context);
						} else {
							Log.v("getMessageUpdate", "�����ݸ���");
						}
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {

					}
				});
		return getMessageUpdateRequest;
	}

	/**
	 * �õ����������������Ϣ��Request
	 * 
	 * @return
	 */
	public JsonObjectRequest sendMessageRequest() {
		/* ������Ϣ */
		// ��������JSON����������ַ
		StringBuilder requestParams = new StringBuilder();
		requestParams.append("{\"mid\":\"" + message.getMessageID() + "\",");
		requestParams.append("\"sid\":\"" + message.getSenderID() + "\",");
		requestParams.append("\"rid\":\"" + message.getReceiverID() + "\",");
		requestParams.append("\"from\":\"" + Constant.MOBILE + "\",");
		requestParams.append("\"dpt\":\"" + message.getDescription() + "\",");
		requestParams.append("\"st\":\"" + message.getSendTime() + "\",");

		if (message.getDescription() == null // �ı�Ϊ�գ�������Ϣ
				|| message.getDescription().equalsIgnoreCase("")) {
			requestParams.append("\"attm\":{\"atype\":\"" + message.getAttachmentType() + "\",");
			requestParams.append("\"name\":\"" + message.getAttachmentURL() + "\"}}");
		} else { // ����Ϊ�գ��ı���Ϣ
			requestParams.append("\"attm\":null}");
		}

		Log.i("SendMessageRequest", Constant.SERVER_BASE_URL + Constant.SEND_MESSAGE_URL + "?param="
				+ requestParams.toString());

		try {
			sendRequestString = Constant.SERVER_BASE_URL + Constant.SEND_MESSAGE_URL + "?param="
					+ URLEncoder.encode(requestParams.toString(), "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}

		JsonObjectRequest sendMessageRequest = new JsonObjectRequest(sendRequestString, null,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						// ���ͳɹ����жϷ������Ƿ񷵻سɹ�����֪ͨHandler������Ϣ
						Log.i("sendMessageRequest", response.toString());

						try {
							if (response.getString("success").equals("0")) {
								MessageHandlerManager.getInstance().sendMessage(
										Constant.SEND_MESSAGE_REQUEST_SUCCESS, response, "MainActivity");
							} else if (response.getString("success").equals("1")) {
								MessageHandlerManager.getInstance().sendMessage(
										Constant.SEND_MESSAGE_REQUEST_FAIL, response, "MainActivity");
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {

					}
				});

		return sendMessageRequest;
	}

	private ArrayList<MessageModel> getMessageListFromJson(JSONObject resultJsonObject) {
		String updateTimeStamp = null;
		ArrayList<MessageModel> messageList = null;
		if (resultJsonObject != null) {
			messageList = null;
			messageList = MyJsonParseUtil.getMessageList(resultJsonObject);
			if (messageList == null) {
				return null;
			}
			updateTimeStamp = MyJsonParseUtil.getUpdateTimeStamp(resultJsonObject);

			// �������һ�θ�����Ϣ��ʱ��
			if (updateTimeStamp != null) {
				MySharedPreference.save(context, MySharedPreference.LAST_UPDATE_MESSAGE_TIMESTAMP,
						updateTimeStamp);
			}
		}

		return messageList;
	}

}
