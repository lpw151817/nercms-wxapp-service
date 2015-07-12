package android.wxapp.service.request;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
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

	// private Context context;
	// /**
	// * // �û�ID
	// */
	// private String userID;
	// /**
	// * // ��һ�η�������ʱ��
	// */
	// private String lastUpdateTime;
	// /**
	// * // ��ȡ�������������ַ���
	// */
	// private String requestsString = null;
	// /**
	// * // ���ͷ���ģ��
	// */
	// private FeedbackModel feedback = null;
	// /**
	// * // ���ͷ��������ַ���
	// */
	// private String sendFeedbackString = null;
	//
	// // ���ͷ����Ĺ��캯��
	// public FeedbackRequest(FeedbackModel feedback) {
	// this.feedback = feedback;
	// }
	//
	// // ��ȡ�·���ʱ�Ĺ��캯��
	// public FeedbackRequest(Context context) {
	// this.context = context;
	// }

	public FeedbackRequest() {

	}

	/**
	 * 
	 * ���ͷ��� Jerry 5.23
	 * 
	 * @param context
	 * @param ic
	 * @param sid
	 *            ������ID
	 * @param st
	 *            ����ʱ��
	 * @param c
	 *            ��Ϣ�ı����ݣ�����ϢΪ������Ϣʱ���ֶ�Ϊ�գ�
	 * @param at
	 *            �������ͣ�����ϢΪ�ı���Ϣʱ���ֶ�Ϊ�գ���1���ı�2��ͼƬ3��¼��4��¼��5��GPS��
	 * @param au
	 *            �������ӣ�����ϢΪ�ı���Ϣʱ���ֶ�Ϊ�գ�
	 * @param ut
	 *            ����ʱ��
	 * @param rids
	 *            �����Աid������
	 * @return
	 */
	public JsonObjectRequest sendFeedbackRequest(final Context context, final String sid,
			final String st, final String c, final String at, final String au, final String ut,
			final String rid) {
		// ���Ϊ��ȡ���û���id����ֱ�ӷ���
		if (getUserId(context) == null || getUserIc(context) == null)
			return null;
		TaskFeedbackRequest params = new TaskFeedbackRequest(getUserId(context), getUserIc(context),
				4 + "", sid, st, c, at, au, ut, rid);
		this.url = Contants.SERVER_URL + Contants.MODEL_NAME + Contants.METHOD_FEEDBACK_SEND
				+ Contants.PARAM_NAME + super.gson.toJson(params);
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
						dao.saveMessage(r.getMid(), new ReceiveMessageResponse("", "4", sid, rid, st, c,
								at, au, ut, ""));
						// �����ؽ�����ظ�handler����ui����
						MessageHandlerManager.getInstance()
								.sendMessage(Constant.SEND_FEEDBACK_REQUEST_SUCCESS, r,
										Contants.METHOD_FEEDBACK_SEND);
					} else {
						NormalServerResponse r = gson.fromJson(arg0.toString(),
								NormalServerResponse.class);
						// �����ؽ�����ظ�handler����ui����
						MessageHandlerManager.getInstance().sendMessage(
								Constant.SEND_FEEDBACK_REQUEST_FAIL, r, Contants.METHOD_FEEDBACK_SEND);
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

		// /* ���ͷ��� */
		// // ��������JSON����������ַ
		// StringBuilder requestParams = new StringBuilder();
		// requestParams.append("{\"fid\":\"" + feedback.getFeedbackID() +
		// "\",");
		// requestParams.append("\"aid\":\"" + feedback.getAffairID() + "\",");
		// requestParams.append("\"pid\":\"" + feedback.getPersonID() + "\",");
		// requestParams.append("\"from\":\"" + 1 + "\",");
		// requestParams.append("\"dpt\":\"" + feedback.getContent() + "\",");
		// requestParams.append("\"tsp\":\"" + feedback.getFeedbackTime() +
		// "\",");
		//
		// if (feedback.getAttachment() != null) {
		// requestParams.append("\"attm\":{\"atype\":\"" +
		// feedback.getAttachment().getAttachmentType()
		// + "\",");
		// requestParams.append("\"name\":\"" +
		// feedback.getAttachment().getAttachmentURL() + "\"}}");
		// } else {
		// requestParams.append("\"attm\":null}");
		// }
		//
		// Log.i("sendFeedbackRequest", Constant.SERVER_BASE_URL +
		// Constant.SEND_FEEDBACK_URL
		// + requestParams.toString());
		//
		// try {
		// sendFeedbackString = Constant.SERVER_BASE_URL +
		// Constant.SEND_FEEDBACK_URL + "?param="
		// + URLEncoder.encode(requestParams.toString(), "UTF-8");
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		//
		// JsonObjectRequest sendFeedbackRequest = new
		// JsonObjectRequest(sendFeedbackString, null,
		// new Response.Listener<JSONObject>() {
		//
		// @Override
		// public void onResponse(JSONObject response) {
		// // ���ͳɹ����жϷ������Ƿ񷵻سɹ�����֪ͨHandler������Ϣ
		// Log.i("sendFeedbackRequest", response.toString());
		//
		// try {
		// if (response.getString("success").equals("0")) {
		// MessageHandlerManager.getInstance().sendMessage(
		// Constant.SEND_FEEDBACK_REQUEST_SUCCESS, "ChatDetail");
		// } else if (response.getString("success").equals("1")) {
		// MessageHandlerManager.getInstance().sendMessage(
		// Constant.SEND_FEEDBACK_REQUEST_FAIL, "ChatDetail");
		// }
		// } catch (JSONException e) {
		// e.printStackTrace();
		// }
		// }
		// }, new Response.ErrorListener() {
		//
		// @Override
		// public void onErrorResponse(VolleyError error) {
		// // TODO Auto-generated method stub
		//
		// }
		// });
		//
		// return sendFeedbackRequest;
	}

	/**
	 * ���������û���¼���ȡ���ظ��� δʵ��
	 */
	public JsonObjectRequest getFeedbackUpdateRequest() {
		return null;
	}

	/**
	 * 
	 * ��ѯ���� jerry 5.23
	 * 
	 * @param context
	 * @param ic
	 * @param aid
	 *            ����id
	 * @return
	 */
	public JsonObjectRequest getFeedbackInfoRequest(Context context, String ic, String aid) {
		// ���Ϊ��ȡ���û���id����ֱ�ӷ���
		if (getUserId(context) == null)
			return null;
		FeedbackUpdateQueryRequest params = new FeedbackUpdateQueryRequest(getUserId(context), ic, aid);
		this.url = Contants.SERVER_URL + Contants.MODEL_NAME + Contants.METHOD_FEEDBACK_QUERY
				+ Contants.PARAM_NAME + super.gson.toJson(params);
		Log.e("URL", this.url);
		return new JsonObjectRequest(this.url, null, new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject arg0) {
				Log.e("Response", arg0.toString());
				try {
					if (arg0.getString("s").equals(Contants.RESULT_SUCCESS)) {
						FeedbackUpdateQueryResponse r = gson.fromJson(arg0.toString(),
								FeedbackUpdateQueryResponse.class);
						// �����ؽ�����ظ�handler����ui����
						MessageHandlerManager.getInstance().sendMessage(
								Constant.QUERY_FEEDBACK_REQUEST_SUCCESS, r,
								Contants.METHOD_FEEDBACK_QUERY);
					} else {
						NormalServerResponse r = gson.fromJson(arg0.toString(),
								NormalServerResponse.class);
						// �����ؽ�����ظ�handler����ui����
						MessageHandlerManager.getInstance().sendMessage(
								Constant.QUERY_FEEDBACK_REQUEST_FAIL, r, Contants.METHOD_FEEDBACK_QUERY);
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
		// MySharedPreference.LAST_UPDATE_FEEDBACK_TIMESTAMP, "1398009600");
		// // try {
		// requestsString = Constant.SERVER_BASE_URL +
		// Constant.GET_FEEDBACK_UPDATE_URL
		// + "?param={\"cid\":\"" + userID + "\",\"tsp\":\"" + lastUpdateTime +
		// "\"}";
		// Log.v("getFeedbackUpdate", requestsString);
		//
		// JsonObjectRequest getFeedbackUpdateRequest = new
		// JsonObjectRequest(requestsString, null,
		// new Response.Listener<JSONObject>() {
		//
		// @Override
		// public void onResponse(JSONObject response) {
		// // ���ͳɹ����жϷ������Ƿ񷵻سɹ�����֪ͨHandler������Ϣ
		// Log.i("getFeedbackUpdate", response.toString());
		// Log.v("getFeedbackUpdate", "��ȡ�������³ɹ�");
		//
		// ArrayList<FeedbackModel> feedbackList =
		// getFeedbackListFromJson(response);
		// if (feedbackList != null) {
		// // ��ת�����������߳�
		// Log.v("getFeedbackUpdate", "��ת���������߳�֮ǰ");
		// ThreadManager.getInstance().startSaveFeedbackThread(feedbackList,
		// false,
		// context);
		//
		// } else {
		// Log.v("getFeedbackUpdate", "�����ݸ���");
		// }
		// }
		// }, new Response.ErrorListener() {
		//
		// @Override
		// public void onErrorResponse(VolleyError error) {
		// // TODO Auto-generated method stub
		//
		// }
		// });
		//
		// return getFeedbackUpdateRequest;
	}

	// /**
	// * ����������Json������Feedback�б�
	// *
	// * @param resultJsonObject
	// * @return
	// */
	// private ArrayList<FeedbackModel> getFeedbackListFromJson(JSONObject
	// resultJsonObject) {
	// String updateTimeStamp = null;
	// ArrayList<FeedbackModel> feedbackList = null;
	// if (resultJsonObject != null) {
	// feedbackList = null;
	// feedbackList = MyJsonParseUtil.getFeedbackList(resultJsonObject);
	// if (feedbackList == null) {
	// return null;
	// }
	// updateTimeStamp = MyJsonParseUtil.getUpdateTimeStamp(resultJsonObject);
	//
	// // �������һ�θ��·�����ʱ��
	// if (updateTimeStamp != null) {
	// MySharedPreference.save(context,
	// MySharedPreference.LAST_UPDATE_FEEDBACK_TIMESTAMP,
	// updateTimeStamp);
	// }
	// }
	//
	// return feedbackList;
	// }
}
