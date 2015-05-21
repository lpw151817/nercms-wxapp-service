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
	 * // �û�ID
	 */
	private String userID;
	/**
	 * // ��һ�η�������ʱ��
	 */
	private String lastUpdateTime;
	/**
	 * // ��ȡ�������������ַ���
	 */
	private String requestsString = null;
	/**
	 * // ���ͷ���ģ��
	 */
	private FeedbackModel feedback = null;
	/**
	 * // ���ͷ��������ַ���
	 */
	private String sendFeedbackString = null;

	// ���ͷ����Ĺ��캯��
	public FeedbackRequest(FeedbackModel feedback) {
		this.feedback = feedback;
	}

	// ��ȡ�·���ʱ�Ĺ��캯��
	public FeedbackRequest(Context context) {
		this.context = context;
	}

	/**
	 * �õ�������������·�����Request
	 * 
	 * @return
	 */
	public JsonObjectRequest sendFeedbackRequest() {
		/* ���ͷ��� */
		// ��������JSON����������ַ
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
						// ���ͳɹ����жϷ������Ƿ񷵻سɹ�����֪ͨHandler������Ϣ
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
	 * �õ���������������·�����Request
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
						// ���ͳɹ����жϷ������Ƿ񷵻سɹ�����֪ͨHandler������Ϣ
						Log.i("getFeedbackUpdate", response.toString());
						Log.v("getFeedbackUpdate", "��ȡ�������³ɹ�");

						ArrayList<FeedbackModel> feedbackList = getFeedbackListFromJson(response);
						if (feedbackList != null) {
							// ��ת�����������߳�
							Log.v("getFeedbackUpdate", "��ת���������߳�֮ǰ");
							ThreadManager.getInstance().startSaveFeedbackThread(feedbackList, false,
									context);

						} else {
							Log.v("getFeedbackUpdate", "�����ݸ���");
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
	 * ����������Json������Feedback�б�
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

			// �������һ�θ��·�����ʱ��
			if (updateTimeStamp != null) {
				MySharedPreference.save(context, MySharedPreference.LAST_UPDATE_FEEDBACK_TIMESTAMP,
						updateTimeStamp);
			}
		}

		return feedbackList;
	}
}
