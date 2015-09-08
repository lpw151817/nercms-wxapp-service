package android.wxapp.service.request;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import android.wxapp.service.dao.FeedbackDao;
import android.wxapp.service.dao.MessageDao;
import android.wxapp.service.handler.MessageHandlerManager;
import android.wxapp.service.jerry.model.affair.TaskUpdateQueryRequest;
import android.wxapp.service.jerry.model.affair.TaskUpdateQueryResponse;
import android.wxapp.service.jerry.model.feedback.FeedbackUpdateQueryRequest;
import android.wxapp.service.jerry.model.feedback.FeedbackUpdateQueryResponse;
import android.wxapp.service.jerry.model.feedback.TaskFeedbackRequest;
import android.wxapp.service.jerry.model.feedback.TaskFeedbackRequestIds;
import android.wxapp.service.jerry.model.feedback.TaskFeedbackResponse;
import android.wxapp.service.jerry.model.message.ReceiveMessageResponse;
import android.wxapp.service.jerry.model.normal.NormalServerResponse;
import android.wxapp.service.model.FeedbackModel;
import android.wxapp.service.thread.ThreadManager;
import android.wxapp.service.util.Constant;
import android.wxapp.service.util.MyJsonParseUtil;
import android.wxapp.service.util.MySharedPreference;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.JsonObjectRequest;

public class FeedbackRequest extends BaseRequest {

	public FeedbackRequest() {

	}

	/**
	 * 
	 * 发送反馈 Jerry 5.23
	 * 
	 * @param context
	 * @param ic
	 * @param sid
	 *            发送者ID
	 * @param st
	 *            发起时间
	 * @param c
	 *            消息文本内容（当消息为附件消息时该字段为空）
	 * @param at
	 *            附件类型（当消息为文本消息时该字段为空）（1：文本2：图片3：录像4：录音5：GPS）
	 * @param au
	 *            附件链接（当消息为文本消息时该字段为空）
	 * @param ut
	 *            更新时间
	 * @param rids
	 *            相关人员id的数组
	 * @return
	 */
	public JsonObjectRequest sendFeedbackRequest(final Context context, final String sid,
			final String st, final String c, final String at, final String au, final String ut,
			final String rid) {
		// 如果为获取到用户的id，则直接返回
		if (getUserId(context) == null || getUserIc(context) == null)
			return null;
		TaskFeedbackRequest params = new TaskFeedbackRequest(getUserId(context), getUserIc(context),
				4 + "", sid, st, c, at, au, ut, rid);
		this.url = Contants.SERVER_URL + Contants.MODEL_NAME + Contants.METHOD_FEEDBACK_SEND
				+ Contants.PARAM_NAME + parase2Json(params);
		Log.e("URL", this.url);
		return new JsonObjectRequest(this.url, null, new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject arg0) {
				Log.e("Response", arg0.toString());
				try {
					if (arg0.getString("s").equals(Contants.RESULT_SUCCESS)) {
						TaskFeedbackResponse r = gson.fromJson(arg0.toString(),
								TaskFeedbackResponse.class);
						// db insert
						MessageDao dao = new MessageDao(context);
						dao.saveMessage(r.getMid(), new ReceiveMessageResponse("", r.getMid(), "4",
								sid, rid, st, c, at, au, ut, ""));
						// 将返回结果返回给handler进行ui处理
						MessageHandlerManager.getInstance().sendMessage(
								Constant.SEND_FEEDBACK_REQUEST_SUCCESS, r,
								Contants.METHOD_FEEDBACK_SEND);
					} else {
						NormalServerResponse r = gson.fromJson(arg0.toString(),
								NormalServerResponse.class);
						// 将返回结果返回给handler进行ui处理
						MessageHandlerManager.getInstance().sendMessage(
								Constant.SEND_FEEDBACK_REQUEST_FAIL, r,
								Contants.METHOD_FEEDBACK_SEND);
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

	}

	/**
	 * ！！！！用户登录后获取返回更新 未实现
	 */
	public JsonObjectRequest getFeedbackUpdateRequest() {
		return null;
	}

	/**
	 * 
	 * 查询反馈 jerry 5.23
	 * 
	 * @param context
	 * @param ic
	 * @param aid
	 *            事务id
	 * @return
	 */
	public JsonObjectRequest getFeedbackInfoRequest(Context context, String ic, String aid) {
		// 如果为获取到用户的id，则直接返回
		if (getUserId(context) == null)
			return null;
		FeedbackUpdateQueryRequest params = new FeedbackUpdateQueryRequest(getUserId(context), ic,
				aid);
		this.url = Contants.SERVER_URL + Contants.MODEL_NAME + Contants.METHOD_FEEDBACK_QUERY
				+ Contants.PARAM_NAME + parase2Json(params);
		Log.e("URL", this.url);
		return new JsonObjectRequest(this.url, null, new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject arg0) {
				Log.e("Response", arg0.toString());
				try {
					if (arg0.getString("s").equals(Contants.RESULT_SUCCESS)) {
						FeedbackUpdateQueryResponse r = gson.fromJson(arg0.toString(),
								FeedbackUpdateQueryResponse.class);
								// TODO db insert

						// 将返回结果返回给handler进行ui处理
						MessageHandlerManager.getInstance().sendMessage(
								Constant.QUERY_FEEDBACK_REQUEST_SUCCESS, r,
								Contants.METHOD_FEEDBACK_QUERY);
					} else {
						NormalServerResponse r = gson.fromJson(arg0.toString(),
								NormalServerResponse.class);
						// 将返回结果返回给handler进行ui处理
						MessageHandlerManager.getInstance().sendMessage(
								Constant.QUERY_FEEDBACK_REQUEST_FAIL, r,
								Contants.METHOD_FEEDBACK_QUERY);
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
}
