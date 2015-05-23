package android.wxapp.service.request;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;
import android.wxapp.service.dao.AffairDao;
import android.wxapp.service.dao.DAOFactory;
import android.wxapp.service.handler.MessageHandlerManager;
import android.wxapp.service.jerry.model.affair.CreateTaskRequest;
import android.wxapp.service.jerry.model.affair.CreateTaskRequestAttachment;
import android.wxapp.service.jerry.model.affair.CreateTaskResponse;
import android.wxapp.service.jerry.model.affair.EndTaskRequest;
import android.wxapp.service.jerry.model.affair.EndTaskResponse;
import android.wxapp.service.jerry.model.affair.ModifyTaskRequest;
import android.wxapp.service.jerry.model.affair.ModifyTaskResponse;
import android.wxapp.service.jerry.model.affair.QueryAffairCountRequest;
import android.wxapp.service.jerry.model.affair.QueryAffairCountResponse;
import android.wxapp.service.jerry.model.affair.QueryAffairInfoRequest;
import android.wxapp.service.jerry.model.affair.QueryAffairInfoResponse;
import android.wxapp.service.jerry.model.affair.QueryAffairListRequest;
import android.wxapp.service.jerry.model.affair.QueryAffairListResponse;
import android.wxapp.service.jerry.model.affair.TaskUpdateQueryRequest;
import android.wxapp.service.jerry.model.affair.TaskUpdateQueryResponse;
import android.wxapp.service.jerry.model.gps.GpsUploadRequest;
import android.wxapp.service.jerry.model.normal.NormalServerResponse;
import android.wxapp.service.jerry.model.person.ModifyCustomerResponse;
import android.wxapp.service.model.AffairModel;
import android.wxapp.service.thread.ThreadManager;
import android.wxapp.service.util.Constant;
import android.wxapp.service.util.MyJsonParseUtil;
import android.wxapp.service.util.MySharedPreference;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.JsonObjectRequest;

public class AffairRequest extends BaseRequest {

	// private Context context;
	// /**
	// * // ���༭������ID
	// */
	// private String affairID;
	// /**
	// * // �����ʹ�������ģ��
	// */
	// private AffairModel affair;
	// /**
	// * // �������������ַ���
	// */
	// private String createAffairString = null;
	// /**
	// * // ��¼�û�ID
	// */
	// private String userID;
	// /**
	// * // ��һ�θ��������ʱ��
	// */
	// private String lastUpdateTime;
	// /**
	// * // ��ȡ������������ַ���
	// */
	// private String getAffairUpdateString = "";
	// /**
	// * // �޸ĺ���������ʱ��
	// */
	// private String modifiedEndTime;
	// /**
	// * // ������������ַ���
	// */
	// private String endTaskRequestString = null;
	// /**
	// * // �޸��������ʱ�������ַ���
	// */
	// private String modifyEndTimeString = null;

	public AffairRequest() {
	}

	// // ��ȡ������¹��캯��
	// public AffairRequest(Context context) {
	// this.context = context;
	// }
	//
	// // �����������캯��
	// public AffairRequest(AffairModel affair) {
	// this.affair = affair;
	// }
	//
	// // �������Ĺ��캯��
	// public AffairRequest(Context context, String affairID) {
	// this.context = context;
	// this.affairID = affairID;
	// }
	//
	// // �޸��������ʱ�乹�캯��
	// public AffairRequest(Context context, String affairID, String
	// modifiedEndTime) {
	// this.context = context;
	// this.affairID = affairID;
	// this.modifiedEndTime = modifiedEndTime;
	// }

	// ======================================================
	//
	// JerryLiu 2015.5.22
	//
	// 1.getAffairUpdateRequest
	// 2.getCreateAffairRequest
	// 3.getModifyEndTimeRequest
	// 4.getEndTaskRequest
	// 5.queryAffairCount
	// 6.queryAffairList
	// 7.queryAffairInfo
	//
	// ======================================================

	/**
	 * ��ȡ������� �߼������⣬��Ҫ���½�Ͻӿ� JERRY 5.23
	 * <p>
	 * �жϷ��ص�sֵ�Ƿ���Ҫ�����ٴε���
	 * 
	 * @param context
	 * @param ic
	 * @param count
	 *            ����ǵڼ��β�ѯ���ݣ����ڷ�ҳ�������ݡ�������Ҫά��countֵ
	 * @return
	 */
	public JsonObjectRequest getAffairUpdateRequest(final Context c, String ic, String count) {
		// ���Ϊ��ȡ���û���id����ֱ�ӷ���
		if (getUserId(c) == null || getLastUpdateTime(c) == null)
			return null;
		TaskUpdateQueryRequest params = new TaskUpdateQueryRequest(getUserId(c), ic,
				getLastUpdateTime(c), count);
		this.url = Contants.SERVER_URL + Contants.MODEL_NAME + Contants.METHOD_AFFAIRS_UPDATE_LIST
				+ Contants.PARAM_NAME + super.gson.toJson(params);
		System.out.println(this.url);
		return new JsonObjectRequest(this.url, null, new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject arg0) {
				System.out.println(arg0.toString());
				try {
					// ��ʾ�Ѿ�û�и����������Ҫ�ٴν�������
					if (arg0.getString("s").equals(Contants.RESULT_SUCCESS)) {
						TaskUpdateQueryResponse r = gson.fromJson(arg0.toString(),
								TaskUpdateQueryResponse.class);
						// TODO �������ݿ�Ĳ���,��������

						// ���±���ʱ���
						MySharedPreference.save(c, MySharedPreference.LAST_UPDATE_TASK_TIMESTAMP,
								System.currentTimeMillis());
						// �����ؽ�����ظ�handler����ui����
						MessageHandlerManager.getInstance().sendMessage(
								Constant.UPDATE_TASK_LIST_REQUEST_SUCCESS, r,
								Contants.METHOD_AFFAIRS_UPDATE_LIST);
					}
					// ���и����������Ҫ��������
					else if (arg0.getString("s").equals(Contants.RESULT_MORE)) {
						TaskUpdateQueryResponse r = gson.fromJson(arg0.toString(),
								TaskUpdateQueryResponse.class);
						// TODO ����˴�����

						// �����ؽ�����ظ�handler����ui����
						MessageHandlerManager.getInstance().sendMessage(
								Constant.UPDATE_TASK_LIST_REQUEST_SUCCESS, r,
								Contants.METHOD_AFFAIRS_UPDATE_LIST);
					} else {
						NormalServerResponse r = gson.fromJson(arg0.toString(),
								NormalServerResponse.class);
						// �����ؽ�����ظ�handler����ui����
						MessageHandlerManager.getInstance().sendMessage(
								Constant.UPDATE_TASK_LIST_REQUEST_FAIL, r,
								Contants.METHOD_AFFAIRS_UPDATE_LIST);
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

		// String userID = MySharedPreference.get(context,
		// MySharedPreference.USER_ID, null);
		// String lastUpdateTime = MySharedPreference.get(context,
		// MySharedPreference.LAST_UPDATE_TASK_TIMESTAMP,
		// "1398009600");
		// // try {
		// getAffairUpdateString = Constant.SERVER_BASE_URL +
		// Constant.GET_AFFAIR_UPDATE_URL
		// + "?param={\"cid\":\"" + userID + "\",\"tsp\":\"" + lastUpdateTime +
		// "\"}";
		// Log.v("getAffairUpdate", getAffairUpdateString);
		// // } catch (Exception e) {
		// // e.printStackTrace();
		// // }
		// JsonObjectRequest getAffairUpdateRequest = new
		// JsonObjectRequest(getAffairUpdateString, null,
		// new Response.Listener<JSONObject>() {
		//
		// @Override
		// public void onResponse(JSONObject response) {
		// // �жϷ������Ƿ񷵻سɹ�����֪ͨHandler������Ϣ
		// Log.i("getAffairUpdate", response.toString());
		// Log.v("getAffairUpdate", "��ȡ������³ɹ�");
		//
		// ArrayList<AffairModel> affairs = getArrayListFromJson(response);
		// if (affairs != null) {
		// Log.v("getAffairUpdate", "��ת���񱣴��߳�֮ǰ");
		// ThreadManager.getInstance().startSaveAffairThread(affairs, false,
		// context);
		// } else {
		// Log.v("getAffairUpdate", "�����ݸ���");
		// // 2014-7-23 WeiHao
		// // û�����ݸ��£���
		// // �����״����б�־Ϊ����
		// MySharedPreference.save(context, MySharedPreference.IS_FIRST_RUN,
		// false);
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
		// return getAffairUpdateRequest;

	}

	/**
	 * �������� FINAL��Jerry 5.23
	 * 
	 * @param c
	 * @param ic
	 *            �û�����
	 * @param t
	 *            �������ͣ�1������ 2����ʾ 3��֪ͨ��
	 * @param sid
	 *            ������id
	 * @param d
	 *            �����ı�����
	 * @param topic
	 *            ��������
	 * @param bt
	 *            ����ʱ��
	 * @param et
	 *            �涨����ʱ��
	 * @param ct
	 *            ʵ�����ʱ�� ���ɿգ�
	 * @param lot
	 *            ���һ�β������ͣ�1-�½���2-����ɣ��ֶ�����3-�������Զ�����4-�޸Ľ�ֹ���ڣ�
	 * @param lotime
	 *            ׷��һ�β���ʱ��
	 * @param up
	 *            ����ʱ��
	 * @param ats
	 *            �����������飨1���ı� 2��ͼƬ3��¼��4��¼��5 ��GPS��
	 * @param us
	 *            ������������
	 * @return
	 */
	public JsonObjectRequest getCreateAffairRequest(Context c, String ic, String t, String sid,
			String d, String topic, String bt, String et, String ct, String lot, String lotime,
			String up, List<String> ats, List<String> us) {
		// ���Ϊ��ȡ���û���id����ֱ�ӷ���
		if (getUserId(c) == null || (ats.size() != us.size()))
			return null;
		List<CreateTaskRequestAttachment> l = new ArrayList<CreateTaskRequestAttachment>();
		for (int i = 0; i < us.size(); i++) {
			l.add(new CreateTaskRequestAttachment(ats.get(i), us.get(i)));
		}

		CreateTaskRequest params = new CreateTaskRequest(getUserId(c), ic, t, sid, d, topic, bt, et, ct,
				lot, lotime, up, l);
		this.url = Contants.SERVER_URL + Contants.MODEL_NAME + Contants.METHOD_AFFAIRS_ADDAFFAIR
				+ Contants.PARAM_NAME + super.gson.toJson(params);
		System.out.println(this.url);
		return new JsonObjectRequest(this.url, null, new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject arg0) {
				try {
					if (arg0.getString("s").equals(Contants.RESULT_SUCCESS)) {
						CreateTaskResponse r = gson.fromJson(arg0.toString(), CreateTaskResponse.class);
						// �����ؽ�����ظ�handler����ui����
						MessageHandlerManager.getInstance().sendMessage(
								Constant.CREATE_AFFAIR_REQUEST_SUCCESS, r,
								Contants.METHOD_AFFAIRS_ADDAFFAIR);
					} else {
						NormalServerResponse r = gson.fromJson(arg0.toString(),
								NormalServerResponse.class);
						// �����ؽ�����ظ�handler����ui����
						MessageHandlerManager.getInstance().sendMessage(
								Constant.CREATE_AFFAIR_REQUEST_FAIL, r,
								Contants.METHOD_AFFAIRS_ADDAFFAIR);
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

		// /* �������� */
		// // ��������JSON����������ַ
		// StringBuilder requestParams = new StringBuilder();
		// requestParams.append("{\"aid\":\"" + affair.getAffairID() + "\",");
		// requestParams.append("\"type\":\"" + affair.getType() + "\",");
		// requestParams.append("\"sid\":\"" + affair.getSponsorID() + "\",");
		// requestParams.append("\"pid\":\"" + affair.getPerson().getPersonID()
		// + "\",");
		// requestParams.append("\"from\":\"" + Constant.MOBILE + "\",");
		// requestParams.append("\"tit\":\"" + affair.getTitle() + "\",");
		// requestParams.append("\"dpt\":\"" + affair.getDescription() + "\",");
		// requestParams.append("\"bt\":\"" + affair.getBeginTime() + "\",");
		// requestParams.append("\"et\":\"" + affair.getEndTime() + "\",");
		// // requestParams.append("\"status\":\"" + affair.getStatus() +
		// "\",");
		// // //status�ڴ�������ʱĬ��Ϊ1�����봫ֵ��������
		// requestParams.append("\"remark\":\"" + "m\","); // remarkĬ����Ϊ��
		//
		// if (affair.getAttachments() != null) { // �и���
		// requestParams.append("\"attr\":[");
		// for (int i = 0; i < affair.getAttachments().size(); i++) {
		// requestParams.append("{\"atype\":\""
		// + affair.getAttachments().get(i).getAttachmentType() + "\",");
		// requestParams.append("\"name\":\"" +
		// affair.getAttachments().get(i).getAttachmentURL()
		// + "\"}");
		// if (i < affair.getAttachments().size() - 1) {
		// requestParams.append(",");
		// }
		// }
		// requestParams.append("]}");
		// } else { // �޸���
		// requestParams.append("\"attr\":[]}");
		// }
		//
		// Log.i("test", Constant.SERVER_BASE_URL + Constant.CREATE_TASK_URL +
		// requestParams.toString());
		// try {
		// createAffairString = Constant.SERVER_BASE_URL +
		// Constant.CREATE_TASK_URL
		// + URLEncoder.encode(requestParams.toString(), "UTF-8");
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		//
		// // ���������������
		// JsonObjectRequest sendAffairRequest = new
		// JsonObjectRequest(createAffairString, null,
		// new Response.Listener<JSONObject>() {
		//
		// @Override
		// public void onResponse(JSONObject response) {
		// // ���ͳɹ����жϷ������Ƿ񷵻سɹ�����֪ͨHandler������Ϣ
		// Log.i("sendAffairRequest", response.toString());
		//
		// try {
		// if (response.getString("success").equals("0")) {
		// MessageHandlerManager.getInstance().sendMessage(
		// Constant.CREATE_AFFAIR_REQUEST_SUCCESS, response, "TaskAdd");
		// }
		// } catch (JSONException e) {
		// e.printStackTrace();
		// }
		// }
		// }, new Response.ErrorListener() {
		//
		// @Override
		// public void onErrorResponse(VolleyError error) {
		// // ����ʧ�ܣ�֪ͨHandler������Ϣ
		// MessageHandlerManager.getInstance().sendMessage(
		// Constant.CREATE_AFFAIR_REQUEST_FAIL, error.getMessage(), "TaskAdd");
		// }
		// });
		//
		// return sendAffairRequest;
	}

	/**
	 * �޸��������ʱ�� Jerry 5.23
	 * 
	 * @param c
	 * @param ic
	 * @param aid
	 *            ���༭������ID
	 * @param et
	 *            �޸ĺ���������ʱ��
	 * @return
	 */
	public JsonObjectRequest getModifyEndTimeRequest(Context c, String ic, String aid, String et) {
		// ���Ϊ��ȡ���û���id����ֱ�ӷ���
		if (getUserId(c) == null)
			return null;
		ModifyTaskRequest params = new ModifyTaskRequest(getUserId(c), ic, aid, et);
		this.url = Contants.SERVER_URL + Contants.MODEL_NAME + Contants.METHOD_AFFAIRS_MODIFY_TASK
				+ Contants.PARAM_NAME + super.gson.toJson(params);
		System.out.println("request>>>>>>" + this.url);
		return new JsonObjectRequest(this.url, null, new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject arg0) {
				System.out.println("response>>>>>>>" + arg0.toString());
				try {
					if (arg0.getString("s").equals(Contants.RESULT_SUCCESS)) {
						ModifyTaskResponse r = gson.fromJson(arg0.toString(), ModifyTaskResponse.class);
						// TODO �Ƿ���Ҫ�������ݿ�Ĳ���

						// �����ؽ�����ظ�handler����ui����
						MessageHandlerManager.getInstance().sendMessage(
								Constant.UPDATE_TASK_END_TIME_REQUEST_SUCCESS, r,
								Contants.METHOD_AFFAIRS_MODIFY_TASK);
					} else {
						NormalServerResponse r = gson.fromJson(arg0.toString(),
								NormalServerResponse.class);
						// �����ؽ�����ظ�handler����ui����
						MessageHandlerManager.getInstance().sendMessage(
								Constant.UPDATE_TASK_END_TIME_REQUEST_FAIL, r,
								Contants.METHOD_AFFAIRS_MODIFY_TASK);
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

		// try {
		// modifyEndTimeString = Constant.SERVER_BASE_URL
		// + Constant.MODIFY_END_TIME_URL
		// + "?param="
		// + URLEncoder.encode("{\"aid\":\"" + affairID + "\",\"met\":\"" +
		// modifiedEndTime
		// + "\"}", "UTF-8");
		// } catch (UnsupportedEncodingException e1) {
		// e1.printStackTrace();
		// }
		// Log.v("ModifyEndTimeRequest", Constant.SERVER_BASE_URL +
		// Constant.MODIFY_END_TIME_URL
		// + "?param=" + "{\"aid\":\"" + affairID + "\",\"met\":\"" +
		// modifiedEndTime + "\"}");
		//
		// JsonObjectRequest modifyEndTimeRequest = new
		// JsonObjectRequest(modifyEndTimeString, null,
		// new Response.Listener<JSONObject>() {
		//
		// @Override
		// public void onResponse(JSONObject response) {
		// try {
		// if (response.getString("success").equalsIgnoreCase("0")) {
		// Log.i("ModifyEndTimeRequest", response.toString());
		// Log.v("ModifyEndTimeRequest", "�������ʱ�����޸�");
		//
		// // �����������޸�ʱ��ɹ�����һ���ֻ������ݿ�������ʱ��
		// // ��ȡ�������˷��ص����һ�β���ʱ��
		// String lastOperateTime = response.getString("lotm");
		// AffairDao dao = DAOFactory.getInstance().getAffairDao(context);
		// dao.updateAffairEndTime(affairID, modifiedEndTime, lastOperateTime);
		//
		// // ֪ͨ����
		// MessageHandlerManager.getInstance().sendMessage(
		// Constant.MODIFY_TASK_REQUEST_SUCCESS, "TaskDetail");
		//
		// } else {
		// Log.i("ModifyEndTimeRequest", response.toString());
		// Log.v("ModifyEndTimeRequest", "�޸��������ʱ���쳣");
		// }
		// } catch (JSONException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// }
		// }, new Response.ErrorListener() {
		//
		// @Override
		// public void onErrorResponse(VolleyError error) {
		// // ֪ͨ����
		// MessageHandlerManager.getInstance().sendMessage(
		// Constant.MODIFY_TASK_REQUEST_FAIL, "TaskDetail");
		// }
		// });
		//
		// return modifyEndTimeRequest;
	}

	/**
	 * �������� Jerry 5.23
	 * 
	 * @param c
	 * @param ic
	 * @param aid
	 *            ���༭������ID
	 * @param ct
	 *            �޸ĺ���������ʱ�䣬��ʱΪnull
	 * @return
	 */
	public JsonObjectRequest getEndTaskRequest(Context c, String ic, String aid, String ct) {
		// ���Ϊ��ȡ���û���id����ֱ�ӷ���
		if (getUserId(c) == null)
			return null;
		EndTaskRequest params = new EndTaskRequest(getUserId(c), ic, aid, ct);
		this.url = Contants.SERVER_URL + Contants.MODEL_NAME + Contants.METHOD_AFFAIRS_END_TASK
				+ Contants.PARAM_NAME + super.gson.toJson(params);
		System.out.println("request>>>>>>" + this.url);
		return new JsonObjectRequest(this.url, null, new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject arg0) {
				System.out.println("response>>>>>>>" + arg0.toString());
				try {
					if (arg0.getString("s").equals(Contants.RESULT_SUCCESS)) {
						EndTaskResponse r = gson.fromJson(arg0.toString(), EndTaskResponse.class);
						// �����ؽ�����ظ�handler����ui����
						MessageHandlerManager.getInstance().sendMessage(
								Constant.END_TASK_REQUEST_SUCCESS, r, Contants.METHOD_AFFAIRS_END_TASK);
					} else {
						NormalServerResponse r = gson.fromJson(arg0.toString(),
								NormalServerResponse.class);
						// �����ؽ�����ظ�handler����ui����
						MessageHandlerManager.getInstance().sendMessage(Constant.END_TASK_REQUEST_FAIL,
								r, Contants.METHOD_AFFAIRS_END_TASK);
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

		// endTaskRequestString = Constant.SERVER_BASE_URL +
		// Constant.END_TASK_URL + "?param={\"aid\":\""
		// + affairID + "\",\"ct\":\"\"}";
		// Log.v("endTaskRequest", endTaskRequestString);
		//
		// JsonObjectRequest endTaskRequest = new
		// JsonObjectRequest(endTaskRequestString, null,
		// new Response.Listener<JSONObject>() {
		//
		// @Override
		// public void onResponse(JSONObject response) {
		// try {
		// if (response.getString("success").equalsIgnoreCase("0")) {
		// Log.i("endTaskRequest", response.toString());
		// Log.v("endTaskRequest", "�����Ѿ������");
		//
		// // ������������ɳɹ�����һ���ֻ������ݿ����Ϊ�����
		// // ��ȡ�������˷��ص��������ʱ��
		// String completeTime = response.getString("ct");
		// AffairDao dao = DAOFactory.getInstance().getAffairDao(context);
		// dao.updateAffairCompleted(affairID, completeTime);
		// // ֪ͨ����
		// MessageHandlerManager.getInstance().sendMessage(
		// Constant.END_TASK_REQUEST_SUCCESS, "TaskDetail");
		//
		// } else {
		// Log.i("endTaskRequest", response.toString());
		// Log.v("endTaskRequest", "����������쳣");
		// }
		// } catch (JSONException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// }
		// }, new Response.ErrorListener() {
		//
		// @Override
		// public void onErrorResponse(VolleyError error) {
		// Log.e("endTaskRequest", error.toString());
		// // ֪ͨ����
		// MessageHandlerManager.getInstance().sendMessage(Constant.END_TASK_REQUEST_FAIL,
		// "TaskDetail");
		// }
		// });
		// return endTaskRequest;
	}

	/**
	 * ��ȡÿ����������� Jerry 5.23
	 * 
	 * @param c
	 * @param ic
	 * @return
	 */
	public JsonObjectRequest queryAffairCount(Context c, String ic) {
		// ���Ϊ��ȡ���û���id����ֱ�ӷ���
		if (getUserId(c) == null)
			return null;
		QueryAffairCountRequest params = new QueryAffairCountRequest(getUserId(c), ic);
		this.url = Contants.SERVER_URL + Contants.MODEL_NAME + Contants.METHOD_AFFAIRS_QUERY_COUNT
				+ Contants.PARAM_NAME + super.gson.toJson(params);
		System.out.println("request>>>>>>" + this.url);
		return new JsonObjectRequest(this.url, null, new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject arg0) {
				System.out.println("response>>>>>>>" + arg0.toString());
				try {
					if (arg0.getString("s").equals(Contants.RESULT_SUCCESS)) {
						QueryAffairCountResponse r = gson.fromJson(arg0.toString(),
								QueryAffairCountResponse.class);
						// �����ؽ�����ظ�handler����ui����
						MessageHandlerManager.getInstance().sendMessage(
								Constant.QUERY_TASK_COUNT_REQUEST_SUCCESS, r,
								Contants.METHOD_AFFAIRS_QUERY_COUNT);
					} else {
						NormalServerResponse r = gson.fromJson(arg0.toString(),
								NormalServerResponse.class);
						// �����ؽ�����ظ�handler����ui����
						MessageHandlerManager.getInstance().sendMessage(
								Constant.QUERY_TASK_COUNT_REQUEST_FAIL, r,
								Contants.METHOD_AFFAIRS_QUERY_COUNT);
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

	/**
	 * ��ѯ�����б� Jerry 5.23
	 * 
	 * @param c
	 * @param ic
	 * @param sor
	 *            ���ڱ�����Լ������������ǽ��յ�������0���Լ����������� 1���Լ����յ�������
	 * @param t
	 *            ���ڱ�ǲ�ѯ�����״̬��0���������� 1����������� 2����������е�����
	 * @param count
	 *            ����ǵڼ��β�ѯ���ݣ����ڷ�ҳ��������
	 * @return
	 */
	public JsonObjectRequest queryAffairList(Context c, String ic, String sor, String t, String count) {
		// ���Ϊ��ȡ���û���id����ֱ�ӷ���
		if (getUserId(c) == null)
			return null;
		QueryAffairListRequest params = new QueryAffairListRequest(getUserId(c), ic, sor, t, count);
		this.url = Contants.SERVER_URL + Contants.MODEL_NAME + Contants.METHOD_AFFAIRS_QUERY_LIST
				+ Contants.PARAM_NAME + super.gson.toJson(params);
		System.out.println(this.url);
		return new JsonObjectRequest(this.url, null, new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject arg0) {
				System.out.println(arg0.toString());
				try {
					// ��ʾ�Ѿ�û�и����������Ҫ�ٴν�������
					if (arg0.getString("s").equals(Contants.RESULT_SUCCESS)) {
						QueryAffairListResponse r = gson.fromJson(arg0.toString(),
								QueryAffairListResponse.class);
						// TODO �������ݿ�Ĳ���,��������

						// �����ؽ�����ظ�handler����ui����
						MessageHandlerManager.getInstance().sendMessage(
								Constant.QUERY_TASK_LIST_REQUEST_SUCCESS, r,
								Contants.METHOD_AFFAIRS_QUERY_LIST);
					}
					// ���и����������Ҫ��������
					else if (arg0.getString("s").equals(Contants.RESULT_MORE)) {
						QueryAffairListResponse r = gson.fromJson(arg0.toString(),
								QueryAffairListResponse.class);
						// TODO �������ݿ�Ĳ���,��������

						// �����ؽ�����ظ�handler����ui����
						MessageHandlerManager.getInstance().sendMessage(
								Constant.QUERY_TASK_LIST_REQUEST_SUCCESS, r,
								Contants.METHOD_AFFAIRS_QUERY_LIST);
					} else {
						NormalServerResponse r = gson.fromJson(arg0.toString(),
								NormalServerResponse.class);
						// �����ؽ�����ظ�handler����ui����
						MessageHandlerManager.getInstance().sendMessage(
								Constant.QUERY_TASK_LIST_REQUEST_FAIL, r,
								Contants.METHOD_AFFAIRS_QUERY_LIST);
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

	/**
	 * ��ѯ�������� Jerry 5.23
	 * 
	 * @param c
	 * @param ic
	 * @param aid
	 *            ����id
	 * @return
	 */
	public JsonObjectRequest queryAffairInfo(Context c, String ic, String aid) {
		// ���Ϊ��ȡ���û���id����ֱ�ӷ���
		if (getUserId(c) == null)
			return null;
		QueryAffairInfoRequest params = new QueryAffairInfoRequest(getUserId(c), ic, aid);
		this.url = Contants.SERVER_URL + Contants.MODEL_NAME + Contants.METHOD_AFFAIRS_QUERY_INFO
				+ Contants.PARAM_NAME + super.gson.toJson(params);
		System.out.println("request>>>>>>" + this.url);
		return new JsonObjectRequest(this.url, null, new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject arg0) {
				System.out.println("response>>>>>>>" + arg0.toString());
				try {
					if (arg0.getString("s").equals(Contants.RESULT_SUCCESS)) {
						QueryAffairInfoResponse r = gson.fromJson(arg0.toString(),
								QueryAffairInfoResponse.class);
						// �����ؽ�����ظ�handler����ui����
						MessageHandlerManager.getInstance().sendMessage(
								Constant.QUERY_TASK_INFO_REQUEST_SUCCESS, r,
								Contants.METHOD_AFFAIRS_QUERY_INFO);
					} else {
						NormalServerResponse r = gson.fromJson(arg0.toString(),
								NormalServerResponse.class);
						// �����ؽ�����ظ�handler����ui����
						MessageHandlerManager.getInstance().sendMessage(
								Constant.QUERY_TASK_INFO_REQUEST_FAIL, r,
								Contants.METHOD_AFFAIRS_QUERY_INFO);
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

	// /**
	// * ����������Json������Affair�б�
	// *
	// * @param resultJsonObject
	// * @return
	// * @deprecated
	// */
	// private ArrayList<AffairModel> getArrayListFromJson(JSONObject
	// resultJsonObject) {
	// String updateTimeStamp = null;
	// ArrayList<AffairModel> affairList = null;
	// if (resultJsonObject != null) {
	// affairList = null;
	// affairList = MyJsonParseUtil.getAffairList(resultJsonObject);
	// if (affairList == null) {
	// return null;
	// }
	// updateTimeStamp = MyJsonParseUtil.getUpdateTimeStamp(resultJsonObject);
	//
	// // �������һ�θ��������ʱ��
	// if (updateTimeStamp != null) {
	// MySharedPreference.save(context,
	// MySharedPreference.LAST_UPDATE_TASK_TIMESTAMP,
	// updateTimeStamp);
	// }
	// }
	//
	// return affairList;
	// }

}
