package android.wxapp.service.request;

import java.net.URLEncoder;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;
import android.wxapp.service.handler.MessageHandlerManager;
import android.wxapp.service.model.FeedbackModel;
import android.wxapp.service.thread.ThreadManager;
import android.wxapp.service.util.Constant;
import android.wxapp.service.util.MyJsonParseUtil;
import android.wxapp.service.util.MySharedPreference;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

public class FeedbackRequest {

	private Context context;
	/**
	 * // 用户ID
	 */
	private String userID;
	/**
	 * // 上一次反馈更新时间
	 */
	private String lastUpdateTime;
	/**
	 * // 获取反馈更新请求字符串
	 */
	private String requestsString = null;
	/**
	 * // 发送反馈模型
	 */
	private FeedbackModel feedback = null;
	/**
	 * // 发送反馈请求字符串
	 */
	private String sendFeedbackString = null;

	// 发送反馈的构造函数
	public FeedbackRequest(FeedbackModel feedback) {
		this.feedback = feedback;
	}

	// 获取新反馈时的构造函数
	public FeedbackRequest(Context context) {
		this.context = context;
	}

	/**
	 * 得到向服务器发送新反馈的Request
	 * 
	 * @return
	 */
	public JsonObjectRequest sendFeedbackRequest() {
		/* 发送反馈 */
		// 创建包含JSON对象的请求地址
		StringBuilder requestParams = new StringBuilder();
		requestParams.append("{\"fid\":\"" + feedback.getFeedbackID() + "\",");
		requestParams.append("\"aid\":\"" + feedback.getAffairID() + "\",");
		requestParams.append("\"pid\":\"" + feedback.getPersonID() + "\",");
		requestParams.append("\"from\":\"" + 1 + "\",");
		requestParams.append("\"dpt\":\"" + feedback.getContent() + "\",");
		requestParams.append("\"tsp\":\"" + feedback.getFeedbackTime() + "\",");

		if (feedback.getAttachment() != null) {
			requestParams.append("\"attm\":{\"atype\":\"" + feedback.getAttachment().getAttachmentType()
					+ "\",");
			requestParams.append("\"name\":\"" + feedback.getAttachment().getAttachmentURL() + "\"}}");
		} else {
			requestParams.append("\"attm\":null}");
		}

		Log.i("sendFeedbackRequest", Constant.SERVER_BASE_URL + Constant.SEND_FEEDBACK_URL
				+ requestParams.toString());

		try {
			sendFeedbackString = Constant.SERVER_BASE_URL + Constant.SEND_FEEDBACK_URL + "?param="
					+ URLEncoder.encode(requestParams.toString(), "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}

		JsonObjectRequest sendFeedbackRequest = new JsonObjectRequest(sendFeedbackString, null,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						// 发送成功，判断服务器是否返回成功，并通知Handler返回消息
						Log.i("sendFeedbackRequest", response.toString());

						try {
							if (response.getString("success").equals("0")) {
								MessageHandlerManager.getInstance().sendMessage(
										Constant.SEND_FEEDBACK_REQUEST_SUCCESS, "ChatDetail");
							} else if (response.getString("success").equals("1")) {
								MessageHandlerManager.getInstance().sendMessage(
										Constant.SEND_FEEDBACK_REQUEST_FAIL, "ChatDetail");
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						// TODO Auto-generated method stub

					}
				});

		return sendFeedbackRequest;
	}

	/**
	 * 得到从向服务器请求新反馈的Request
	 * 
	 * @return
	 */
	public JsonObjectRequest getFeedbackUpdateRequest() {
		userID = MySharedPreference.get(context, MySharedPreference.USER_ID, "848982460");
		lastUpdateTime = MySharedPreference.get(context,
				MySharedPreference.LAST_UPDATE_FEEDBACK_TIMESTAMP, "1398009600");
		// try {
		requestsString = Constant.SERVER_BASE_URL + Constant.GET_FEEDBACK_UPDATE_URL
				+ "?param={\"cid\":\"" + userID + "\",\"tsp\":\"" + lastUpdateTime + "\"}";
		Log.v("getFeedbackUpdate", requestsString);

		JsonObjectRequest getFeedbackUpdateRequest = new JsonObjectRequest(requestsString, null,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						// 发送成功，判断服务器是否返回成功，并通知Handler返回消息
						Log.i("getFeedbackUpdate", response.toString());
						Log.v("getFeedbackUpdate", "获取反馈更新成功");

						ArrayList<FeedbackModel> feedbackList = getFeedbackListFromJson(response);
						if (feedbackList != null) {
							// 跳转到反馈保存线程
							Log.v("getFeedbackUpdate", "跳转反馈保存线程之前");
							ThreadManager.getInstance().startSaveFeedbackThread(feedbackList, false,
									context);

						} else {
							Log.v("getFeedbackUpdate", "无数据更新");
						}
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						// TODO Auto-generated method stub

					}
				});

		return getFeedbackUpdateRequest;
	}

	/**
	 * 服务器反馈Json解析成Feedback列表
	 * 
	 * @param resultJsonObject
	 * @return
	 */
	private ArrayList<FeedbackModel> getFeedbackListFromJson(JSONObject resultJsonObject) {
		String updateTimeStamp = null;
		ArrayList<FeedbackModel> feedbackList = null;
		if (resultJsonObject != null) {
			feedbackList = null;
			feedbackList = MyJsonParseUtil.getFeedbackList(resultJsonObject);
			if (feedbackList == null) {
				return null;
			}
			updateTimeStamp = MyJsonParseUtil.getUpdateTimeStamp(resultJsonObject);

			// 更新最后一次更新反馈的时戳
			if (updateTimeStamp != null) {
				MySharedPreference.save(context, MySharedPreference.LAST_UPDATE_FEEDBACK_TIMESTAMP,
						updateTimeStamp);
			}
		}

		return feedbackList;
	}
}
