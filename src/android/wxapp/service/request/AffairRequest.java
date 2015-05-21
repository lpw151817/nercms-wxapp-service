package android.wxapp.service.request;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;
import android.wxapp.service.dao.AffairDao;
import android.wxapp.service.dao.DAOFactory;
import android.wxapp.service.handler.MessageHandlerManager;
import android.wxapp.service.model.AffairModel;
import android.wxapp.service.thread.ThreadManager;
import android.wxapp.service.util.Constant;
import android.wxapp.service.util.MyJsonParseUtil;
import android.wxapp.service.util.MySharedPreference;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

public class AffairRequest {

	private Context context;
	/**
	 * // ���༭������ID
	 */
	private String affairID;
	/**
	 * // �����ʹ�������ģ��
	 */
	private AffairModel affair;
	/**
	 * // �������������ַ���
	 */
	private String createAffairString = null;
	/**
	 * // ��¼�û�ID
	 */
	private String userID;
	/**
	 * // ��һ�θ��������ʱ��
	 */
	private String lastUpdateTime;
	/**
	 * // ��ȡ������������ַ���
	 */
	private String getAffairUpdateString = "";
	/**
	 * // �޸ĺ���������ʱ��
	 */
	private String modifiedEndTime;
	/**
	 * // ������������ַ���
	 */
	private String endTaskRequestString = null;
	/**
	 * // �޸��������ʱ�������ַ���
	 */
	private String modifyEndTimeString = null;

	// ��ȡ������¹��캯��
	public AffairRequest(Context context) {
		this.context = context;
	}

	// �����������캯��
	public AffairRequest(AffairModel affair) {
		this.affair = affair;
	}

	// �������Ĺ��캯��
	public AffairRequest(Context context, String affairID) {
		this.context = context;
		this.affairID = affairID;
	}

	// �޸��������ʱ�乹�캯��
	public AffairRequest(Context context, String affairID, String modifiedEndTime) {
		this.context = context;
		this.affairID = affairID;
		this.modifiedEndTime = modifiedEndTime;
	}

	/**
	 * ��������˻�ȡ������µ�Request
	 * 
	 * @return
	 */
	public JsonObjectRequest getAffairUpdateRequest() {
		userID = MySharedPreference.get(context, MySharedPreference.USER_ID, "848982460");
		lastUpdateTime = MySharedPreference.get(context, MySharedPreference.LAST_UPDATE_TASK_TIMESTAMP,
				"1398009600");
		// try {
		getAffairUpdateString = Constant.SERVER_BASE_URL + Constant.GET_AFFAIR_UPDATE_URL
				+ "?param={\"cid\":\"" + userID + "\",\"tsp\":\"" + lastUpdateTime + "\"}";
		Log.v("getAffairUpdate", getAffairUpdateString);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		JsonObjectRequest getAffairUpdateRequest = new JsonObjectRequest(getAffairUpdateString, null,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						// �жϷ������Ƿ񷵻سɹ�����֪ͨHandler������Ϣ
						Log.i("getAffairUpdate", response.toString());
						Log.v("getAffairUpdate", "��ȡ������³ɹ�");

						ArrayList<AffairModel> affairs = getArrayListFromJson(response);
						if (affairs != null) {
							Log.v("getAffairUpdate", "��ת���񱣴��߳�֮ǰ");
							ThreadManager.getInstance().startSaveAffairThread(affairs, false, context);
						} else {
							Log.v("getAffairUpdate", "�����ݸ���");
							// 2014-7-23 WeiHao
							// û�����ݸ��£���
							// �����״����б�־Ϊ����
							MySharedPreference.save(context, MySharedPreference.IS_FIRST_RUN, false);
						}
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						// TODO Auto-generated method stub

					}
				});
		return getAffairUpdateRequest;
	}

	public JsonObjectRequest getCreateAffairRequest() {
		/* �������� */
		// ��������JSON����������ַ
		StringBuilder requestParams = new StringBuilder();
		requestParams.append("{\"aid\":\"" + affair.getAffairID() + "\",");
		requestParams.append("\"type\":\"" + affair.getType() + "\",");
		requestParams.append("\"sid\":\"" + affair.getSponsorID() + "\",");
		requestParams.append("\"pid\":\"" + affair.getPerson().getPersonID() + "\",");
		requestParams.append("\"from\":\"" + Constant.MOBILE + "\",");
		requestParams.append("\"tit\":\"" + affair.getTitle() + "\",");
		requestParams.append("\"dpt\":\"" + affair.getDescription() + "\",");
		requestParams.append("\"bt\":\"" + affair.getBeginTime() + "\",");
		requestParams.append("\"et\":\"" + affair.getEndTime() + "\",");
		// requestParams.append("\"status\":\"" + affair.getStatus() + "\",");
		// //status�ڴ�������ʱĬ��Ϊ1�����봫ֵ��������
		requestParams.append("\"remark\":\"" + "m\","); // remarkĬ����Ϊ��

		if (affair.getAttachments() != null) { // �и���
			requestParams.append("\"attr\":[");
			for (int i = 0; i < affair.getAttachments().size(); i++) {
				requestParams.append("{\"atype\":\""
						+ affair.getAttachments().get(i).getAttachmentType() + "\",");
				requestParams.append("\"name\":\"" + affair.getAttachments().get(i).getAttachmentURL()
						+ "\"}");
				if (i < affair.getAttachments().size() - 1) {
					requestParams.append(",");
				}
			}
			requestParams.append("]}");
		} else { // �޸���
			requestParams.append("\"attr\":[]}");
		}

		Log.i("test", Constant.SERVER_BASE_URL + Constant.CREATE_TASK_URL + requestParams.toString());
		try {
			createAffairString = Constant.SERVER_BASE_URL + Constant.CREATE_TASK_URL
					+ URLEncoder.encode(requestParams.toString(), "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}

		// ���������������
		JsonObjectRequest sendAffairRequest = new JsonObjectRequest(createAffairString, null,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						// ���ͳɹ����жϷ������Ƿ񷵻سɹ�����֪ͨHandler������Ϣ
						Log.i("sendAffairRequest", response.toString());

						try {
							if (response.getString("success").equals("0")) {
								MessageHandlerManager.getInstance().sendMessage(
										Constant.CREATE_AFFAIR_REQUEST_SUCCESS, response, "TaskAdd");
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						// ����ʧ�ܣ�֪ͨHandler������Ϣ
						MessageHandlerManager.getInstance().sendMessage(
								Constant.CREATE_AFFAIR_REQUEST_FAIL, error.getMessage(), "TaskAdd");
					}
				});

		return sendAffairRequest;
	}

	/**
	 * ��������˷����޸��������ʱ���Request
	 * 
	 * @return
	 */
	public JsonObjectRequest getModifyEndTimeRequest() {
		try {
			modifyEndTimeString = Constant.SERVER_BASE_URL
					+ Constant.MODIFY_END_TIME_URL
					+ "?param="
					+ URLEncoder.encode("{\"aid\":\"" + affairID + "\",\"met\":\"" + modifiedEndTime
							+ "\"}", "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		Log.v("ModifyEndTimeRequest", Constant.SERVER_BASE_URL + Constant.MODIFY_END_TIME_URL
				+ "?param=" + "{\"aid\":\"" + affairID + "\",\"met\":\"" + modifiedEndTime + "\"}");

		JsonObjectRequest modifyEndTimeRequest = new JsonObjectRequest(modifyEndTimeString, null,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						try {
							if (response.getString("success").equalsIgnoreCase("0")) {
								Log.i("ModifyEndTimeRequest", response.toString());
								Log.v("ModifyEndTimeRequest", "�������ʱ�����޸�");

								// �����������޸�ʱ��ɹ�����һ���ֻ������ݿ�������ʱ��
								// ��ȡ�������˷��ص����һ�β���ʱ��
								String lastOperateTime = response.getString("lotm");
								AffairDao dao = DAOFactory.getInstance().getAffairDao(context);
								dao.updateAffairEndTime(affairID, modifiedEndTime, lastOperateTime);

								// ֪ͨ����
								MessageHandlerManager.getInstance().sendMessage(
										Constant.MODIFY_TASK_REQUEST_SUCCESS, "TaskDetail");

							} else {
								Log.i("ModifyEndTimeRequest", response.toString());
								Log.v("ModifyEndTimeRequest", "�޸��������ʱ���쳣");
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						// ֪ͨ����
						MessageHandlerManager.getInstance().sendMessage(
								Constant.MODIFY_TASK_REQUEST_FAIL, "TaskDetail");
					}
				});

		return modifyEndTimeRequest;
	}

	/**
	 * ��������˷��ͽ��������Request
	 * 
	 * @return
	 */
	public JsonObjectRequest getEndTaskRequest() {
		endTaskRequestString = Constant.SERVER_BASE_URL + Constant.END_TASK_URL + "?param={\"aid\":\""
				+ affairID + "\",\"ct\":\"\"}";
		Log.v("endTaskRequest", endTaskRequestString);

		JsonObjectRequest endTaskRequest = new JsonObjectRequest(endTaskRequestString, null,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						try {
							if (response.getString("success").equalsIgnoreCase("0")) {
								Log.i("endTaskRequest", response.toString());
								Log.v("endTaskRequest", "�����Ѿ������");

								// ������������ɳɹ�����һ���ֻ������ݿ����Ϊ�����
								// ��ȡ�������˷��ص��������ʱ��
								String completeTime = response.getString("ct");
								AffairDao dao = DAOFactory.getInstance().getAffairDao(context);
								dao.updateAffairCompleted(affairID, completeTime);
								// ֪ͨ����
								MessageHandlerManager.getInstance().sendMessage(
										Constant.END_TASK_REQUEST_SUCCESS, "TaskDetail");

							} else {
								Log.i("endTaskRequest", response.toString());
								Log.v("endTaskRequest", "����������쳣");
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						Log.e("endTaskRequest", error.toString());
						// ֪ͨ����
						MessageHandlerManager.getInstance().sendMessage(Constant.END_TASK_REQUEST_FAIL,
								"TaskDetail");
					}
				});
		return endTaskRequest;
	}

	/**
	 * ����������Json������Affair�б�
	 * 
	 * @param resultJsonObject
	 * @return
	 */
	private ArrayList<AffairModel> getArrayListFromJson(JSONObject resultJsonObject) {
		String updateTimeStamp = null;
		ArrayList<AffairModel> affairList = null;
		if (resultJsonObject != null) {
			affairList = null;
			affairList = MyJsonParseUtil.getAffairList(resultJsonObject);
			if (affairList == null) {
				return null;
			}
			updateTimeStamp = MyJsonParseUtil.getUpdateTimeStamp(resultJsonObject);

			// �������һ�θ��������ʱ��
			if (updateTimeStamp != null) {
				MySharedPreference.save(context, MySharedPreference.LAST_UPDATE_TASK_TIMESTAMP,
						updateTimeStamp);
			}
		}

		return affairList;
	}

}
