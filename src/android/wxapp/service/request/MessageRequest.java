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
	 * 获取消息更新 JERRY 5.23
	 * <p>
	 * 判断返回的s值是否需要进行再次调用
	 * 
	 * @param c
	 * @param ic
	 * @param count
	 *            标记是第几次查询数据，用于分页请求数据。请求需要维护count值
	 * @return
	 */
	public JsonObjectRequest getMessageUpdateRequest(final Context c, String count) {
		// 如果为获取到用户的id，则直接返回
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
					// 表示已经没有更多的数据需要再次进行请求
					if (arg0.getString("s").equals(Contants.RESULT_SUCCESS)) {
						MessageUpdateQueryResponse r = gson.fromJson(arg0.toString(),
								MessageUpdateQueryResponse.class);
						// 进行数据库的操作,保存数据
						new SaveMessageUpdateThread(c, r).run();
						// 将返回结果返回给handler进行ui处理
						MessageHandlerManager.getInstance().sendMessage(
								Constant.UPDATE_MESSAGE_REQUEST_SUCCESS, r,
								Contants.METHOD_MESSAGE_UPDATE);
					}
					// 还有更多的数据需要进行请求
					else if (arg0.getString("s").equals(Contants.RESULT_MORE)) {
						MessageUpdateQueryResponse r = gson.fromJson(arg0.toString(),
								MessageUpdateQueryResponse.class);
						// 进行数据库的操作,保存数据
						new SaveMessageUpdateThread(c, r).run();
						// 将返回结果返回给handler进行ui处理
						MessageHandlerManager.getInstance().sendMessage(
								Constant.UPDATE_MESSAGE_REQUEST_SUCCESS, r,
								Contants.METHOD_MESSAGE_UPDATE);
					} else {
						NormalServerResponse r = gson.fromJson(arg0.toString(),
								NormalServerResponse.class);
						// 将返回结果返回给handler进行ui处理
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
		// // 判断服务器是否返回成功，并通知Handler返回消息
		// Log.i("getMessageUpdate", response.toString());
		// Log.v("getMessageUpdate", "获取新消息成功");
		//
		// ArrayList<MessageModel> messages = getMessageListFromJson(response);
		// if (messages != null) {
		// Log.v("getMessageUpdate", "跳转消息保存线程之前");
		// ThreadManager.getInstance().startSaveMessageThread(messages, false,
		// context);
		// } else {
		// Log.v("getMessageUpdate", "无数据更新");
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
	 * 发送新消息 JERRY 5.23
	 * 
	 * @param context
	 * @param ic
	 * @param t
	 *            消息类型,0 普通个人聊天消息1 基本群组消息 2.非基本群组消息3.会议记录消息 4.事务反馈消息
	 * @param sid
	 *            发送者ID
	 * @param rids
	 *            相关人员id数组
	 * @param st
	 *            发送时间
	 * @param c
	 *            消息文本内容，当消息为附件消息时该字段为空
	 * @param at
	 *            附件类型 1.文本 2.图片 3.录像 4.录音 5. GPS
	 * @param au
	 *            附件链接（当消息为文本消息时该字段为空）
	 * @param ut
	 *            更新时间
	 * @return
	 */
	public JsonObjectRequest sendMessageRequest(final Context context, String t, String sid, String rid,
			String st, String c, String at, String au, String ut) {
		// 如果为获取到用户的id，则直接返回
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
						// 进行数据库的insert
						new SaveMessageThread(context, r.getMid(), new ReceiveMessageResponse("", params
								.getT(), params.getSid(), params.getRid(), params.getSt(),
								params.getC(), params.getAt(), params.getAu(), params.getUt(), null))
								.run();
						// 将返回结果返回给handler进行ui处理
						MessageHandlerManager.getInstance().sendMessage(
								Constant.SEND_MESSAGE_REQUEST_SUCCESS, r, Contants.METHOD_MESSAGE_SEND);
					} else {
						NormalServerResponse r = gson.fromJson(arg0.toString(),
								NormalServerResponse.class);
						// 将返回结果返回给handler进行ui处理
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
				Toast.makeText(context, "服务器连接失败", Toast.LENGTH_LONG).show();
			}
		});

		// /* 发送消息 */
		// // 创建包含JSON对象的请求地址
		// StringBuilder requestParams = new StringBuilder();
		// requestParams.append("{\"mid\":\"" + message.getMessageID() + "\",");
		// requestParams.append("\"sid\":\"" + message.getSenderID() + "\",");
		// requestParams.append("\"rid\":\"" + message.getReceiverID() + "\",");
		// requestParams.append("\"from\":\"" + Constant.MOBILE + "\",");
		// requestParams.append("\"dpt\":\"" + message.getDescription() +
		// "\",");
		// requestParams.append("\"st\":\"" + message.getSendTime() + "\",");
		//
		// if (message.getDescription() == null // 文本为空，附件消息
		// || message.getDescription().equalsIgnoreCase("")) {
		// requestParams.append("\"attm\":{\"atype\":\"" +
		// message.getAttachmentType() + "\",");
		// requestParams.append("\"name\":\"" + message.getAttachmentURL() +
		// "\"}}");
		// } else { // 附件为空，文本消息
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
		// // 发送成功，判断服务器是否返回成功，并通知Handler返回消息
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
	 * 查询最近联系人 最近消息列表
	 * 
	 * @param context
	 * @param ic
	 * @return
	 */
	public JsonObjectRequest queryContactPersonMessage(Context context, String ic) {
		// 如果为获取到用户的id，则直接返回
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
					// 表示已经没有更多的数据需要再次进行请求
					if (arg0.getString("s").equals(Contants.RESULT_SUCCESS)) {
						QueryContactPersonMessageResponse r = gson.fromJson(arg0.toString(),
								QueryContactPersonMessageResponse.class);
						// 将返回结果返回给handler进行ui处理
						MessageHandlerManager.getInstance().sendMessage(
								Constant.QUERY_RECENT_MESSAGE_REQUEST_SUCCESS, r,
								Contants.METHOD_MESSAGE_QUERY_CONTACT_MERSON_MESSAGE);
					} else {
						NormalServerResponse r = gson.fromJson(arg0.toString(),
								NormalServerResponse.class);
						// 将返回结果返回给handler进行ui处理
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

	// 暂不实现
	public JsonObjectRequest deleteMessage() {
		return null;
	}

	// 暂不实现
	public JsonObjectRequest queryMessageHistory() {
		return null;
	}

	/**
	 * 查询接收到的消息的详细内容
	 * 
	 * @param context
	 * @param ic
	 * @param mid
	 *            需要查询的消息id
	 * @return
	 */
	public JsonObjectRequest receiveMessage(Context context, String ic, String mid) {
		// 如果为获取到用户的id，则直接返回
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
					// 表示已经没有更多的数据需要再次进行请求
					if (arg0.getString("s").equals(Contants.RESULT_SUCCESS)) {
						ReceiveMessageResponse r = gson.fromJson(arg0.toString(),
								ReceiveMessageResponse.class);
						// 将返回结果返回给handler进行ui处理
						MessageHandlerManager.getInstance().sendMessage(
								Constant.QUERY_MESSAGE_INFO_REQUEST_SUCCESS, r,
								Contants.METHOD_MESSAGE_RECEIVE);
					} else {
						NormalServerResponse r = gson.fromJson(arg0.toString(),
								NormalServerResponse.class);
						// 将返回结果返回给handler进行ui处理
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
	// // 更新最后一次更新消息的时戳
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
