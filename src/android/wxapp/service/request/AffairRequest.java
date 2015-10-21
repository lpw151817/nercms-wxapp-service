package android.wxapp.service.request;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import android.wxapp.service.dao.AffairDao;
import android.wxapp.service.dao.DAOFactory;
import android.wxapp.service.handler.MessageHandlerManager;
import android.wxapp.service.jerry.model.affair.CreateTaskRequest;
import android.wxapp.service.jerry.model.affair.CreateTaskRequestAttachment;
import android.wxapp.service.jerry.model.affair.CreateTaskRequestIds;
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
import android.wxapp.service.jerry.model.affair.UpdateAffairInfoRequest;
import android.wxapp.service.jerry.model.affair.UpdateAffairInfoResponse;
import android.wxapp.service.jerry.model.affair.UpdateAffairReadTimeRequest;
import android.wxapp.service.jerry.model.affair.UpdateAffairReadTimeResponse;
import android.wxapp.service.jerry.model.gps.GpsUploadRequest;
import android.wxapp.service.jerry.model.normal.NormalServerResponse;
import android.wxapp.service.jerry.model.person.ModifyCustomerResponse;
import android.wxapp.service.model.AffairModel;
import android.wxapp.service.thread.SaveAffairThread;
import android.wxapp.service.thread.SaveAffairUpdateThread;
import android.wxapp.service.thread.ThreadManager;
import android.wxapp.service.util.Constant;
import android.wxapp.service.util.MyJsonParseUtil;
import android.wxapp.service.util.MySharedPreference;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.JsonObjectRequest;

public class AffairRequest extends BaseRequest {
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

	public AffairRequest() {
		super();
	}

	/**
	 * ��ȡ������� JERRY 5.23
	 * <p>
	 * �жϷ��ص�sֵ�Ƿ���Ҫ�����ٴε���
	 * 
	 * @param context
	 * @param count
	 *            ����ǵڼ��β�ѯ���ݣ����ڷ�ҳ�������ݡ�������Ҫά��countֵ
	 * @return
	 */
	public JsonObjectRequest getAffairUpdateRequest(final Context c, String count) {
		// ���Ϊ��ȡ���û���id����ֱ�ӷ���
		if (getUserId(c) == null || getUserIc(c) == null)
			return null;
		String lastUpdateTime = getLastAffairUpdateTime(c);
		if (lastUpdateTime == null)
			// lastUpdateTime = System.currentTimeMillis() + "";
			lastUpdateTime = "";
		TaskUpdateQueryRequest params = new TaskUpdateQueryRequest(getUserId(c), getUserIc(c),
				lastUpdateTime, count);
		this.url = Contants.SERVER_URL + Contants.MODEL_NAME + Contants.METHOD_AFFAIRS_UPDATE_LIST
				+ Contants.PARAM_NAME + parase2Json(params);
		Log.e("URL", this.url);
		return new JsonObjectRequest(this.url, null, new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject arg0) {
				Log.e("Response", arg0.toString());
				try {
					// ��ʾ�Ѿ�û�и����������Ҫ�ٴν�������
					if (arg0.getString("s").equals(Contants.RESULT_SUCCESS)) {
						TaskUpdateQueryResponse r = gson.fromJson(arg0.toString(),
								TaskUpdateQueryResponse.class);
						// �������ݿ�Ĳ���,��������
						new SaveAffairUpdateThread(c, r, new AffairDao(c)).run();

						// �����ؽ�����ظ�handler����ui����
						MessageHandlerManager.getInstance().sendMessage(
								Constant.UPDATE_TASK_LIST_REQUEST_SUCCESS, r,
								Contants.METHOD_AFFAIRS_UPDATE_LIST);
					}
					// ���и����������Ҫ��������
					// ������������ֱ��һ���Է��ء�����������
					else if (arg0.getString("s").equals(Contants.RESULT_MORE)) {

						Log.v("AffairRequest", "getAffairUpdateRequest() �����ӿ�");

						// TaskUpdateQueryResponse r =
						// gson.fromJson(arg0.toString(),
						// TaskUpdateQueryResponse.class);
						// // ����˴�����
						// new SaveAffairUpdateThread(r, new AffairDao(c));
						// // �����ؽ�����ظ�handler����ui����
						// MessageHandlerManager.getInstance().sendMessage(
						// Constant.UPDATE_TASK_LIST_REQUEST_SUCCESS, r,
						// Contants.METHOD_AFFAIRS_UPDATE_LIST);

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

	}

	/**
	 * �������� FINAL Jerry 5.23
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
	 * @param rids
	 *            �����ߵ�id����
	 * @return
	 */
	public JsonObjectRequest getCreateAffairRequest(final Context c, final String t, final String d,
			final String topic, final String bt, final String et, final String ct, final String lot,
			final String lotime, final String up, final List<String> ats, final List<String> us,
			final List<String> rids, final List<String> pods) {
		// ���Ϊ��ȡ���û���id����ֱ�ӷ���
		if (getUserId(c) == null || (ats.size() != us.size()) || getUserIc(c) == null)
			return null;
		List<CreateTaskRequestAttachment> l = new ArrayList<CreateTaskRequestAttachment>();
		for (int i = 0; i < us.size(); i++) {
			l.add(new CreateTaskRequestAttachment(ats.get(i), us.get(i)));
		}
		List<CreateTaskRequestIds> l2 = new ArrayList<CreateTaskRequestIds>();
		for (String s : rids) {
			l2.add(new CreateTaskRequestIds(s));
		}
		List<CreateTaskRequestIds> l3 = new ArrayList<CreateTaskRequestIds>();
		for (String s : pods) {
			l3.add(new CreateTaskRequestIds(s));
		}
		final CreateTaskRequest params = new CreateTaskRequest(getUserId(c), getUserIc(c), t,
				getUserId(c), d, topic, bt, et, ct, lot, lotime, up, l, l2, l3);
		this.url = Contants.SERVER_URL + Contants.MODEL_NAME + Contants.METHOD_AFFAIRS_ADDAFFAIR
				+ Contants.PARAM_NAME + parase2Json(params);
		Log.e("URL", this.url);
		return new JsonObjectRequest(this.url, null, new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject arg0) {
				Log.e("Response", arg0.toString());
				try {
					if (arg0.getString("s").equals(Contants.RESULT_SUCCESS)) {
						CreateTaskResponse r = gson.fromJson(arg0.toString(),
								CreateTaskResponse.class);

						// DB insert
						List<CreateTaskRequestAttachment> att = new ArrayList<CreateTaskRequestAttachment>();
						for (int i = 0; i < ats.size(); i++) {
							att.add(new CreateTaskRequestAttachment(ats.get(i), us.get(i)));
						}
						List<CreateTaskRequestIds> ids1 = new ArrayList<CreateTaskRequestIds>();
						for (int i = 0; i < rids.size(); i++) {
							ids1.add(new CreateTaskRequestIds(rids.get(i)));
						}
						List<CreateTaskRequestIds> ids2 = new ArrayList<CreateTaskRequestIds>();
						for (int i = 0; i < pods.size(); i++) {
							ids2.add(new CreateTaskRequestIds(pods.get(i)));
						}
						new SaveAffairThread(c,
								new QueryAffairInfoResponse("", r.getAid(), t, getUserId(c), d,
										topic, bt, et, ct, lot, lotime, up, att, ids1, ids2)).run();

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
				Toast.makeText(c, "����������ʧ��", Toast.LENGTH_LONG).show();
			}
		}) {

			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				HashMap<String, String> headers = new HashMap<String, String>();
				headers.put("Content-Type", "application/json; charset=utf-8");
				return headers;
			}

		};
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
				+ Contants.PARAM_NAME + parase2Json(params);
		System.out.println("request>>>>>>" + this.url);
		return new JsonObjectRequest(this.url, null, new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject arg0) {
				System.out.println("response>>>>>>>" + arg0.toString());
				try {
					if (arg0.getString("s").equals(Contants.RESULT_SUCCESS)) {
						ModifyTaskResponse r = gson.fromJson(arg0.toString(),
								ModifyTaskResponse.class);
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
		}) {

			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				HashMap<String, String> headers = new HashMap<String, String>();
				headers.put("Content-Type", "application/json; charset=utf-8");
				return headers;
			}

		};

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
	public JsonObjectRequest getEndTaskRequest(final Context c, final String aid, final String ct) {
		// ���Ϊ��ȡ���û���id����ֱ�ӷ���
		if (getUserId(c) == null || getUserIc(c) == null)
			return null;
		EndTaskRequest params = new EndTaskRequest(getUserId(c), getUserIc(c), aid, ct);
		this.url = Contants.SERVER_URL + Contants.MODEL_NAME + Contants.METHOD_AFFAIRS_END_TASK
				+ Contants.PARAM_NAME + parase2Json(params);
		System.out.println("request>>>>>>" + this.url);
		return new JsonObjectRequest(this.url, null, new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject arg0) {
				System.out.println("response>>>>>>>" + arg0.toString());
				try {
					if (arg0.getString("s").equals(Contants.RESULT_SUCCESS)) {
						EndTaskResponse r = gson.fromJson(arg0.toString(), EndTaskResponse.class);
						// DB update
						new AffairDao(c).updateAffairCompleted(aid, ct);
						// �����ؽ�����ظ�handler����ui����
						MessageHandlerManager.getInstance().sendMessage(
								Constant.END_TASK_REQUEST_SUCCESS, r,
								Contants.METHOD_AFFAIRS_END_TASK);
					} else {
						NormalServerResponse r = gson.fromJson(arg0.toString(),
								NormalServerResponse.class);
						// �����ؽ�����ظ�handler����ui����
						MessageHandlerManager.getInstance().sendMessage(
								Constant.END_TASK_REQUEST_FAIL, r,
								Contants.METHOD_AFFAIRS_END_TASK);
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
		}) {

			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				HashMap<String, String> headers = new HashMap<String, String>();
				headers.put("Content-Type", "application/json; charset=utf-8");
				return headers;
			}

		};

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
				+ Contants.PARAM_NAME + parase2Json(params);
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
		}) {

			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				HashMap<String, String> headers = new HashMap<String, String>();
				headers.put("Content-Type", "application/json; charset=utf-8");
				return headers;
			}

		};
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
	public JsonObjectRequest queryAffairList(Context c, String ic, String sor, String t,
			String count) {
		// ���Ϊ��ȡ���û���id����ֱ�ӷ���
		if (getUserId(c) == null)
			return null;
		QueryAffairListRequest params = new QueryAffairListRequest(getUserId(c), ic, sor, t, count);
		this.url = Contants.SERVER_URL + Contants.MODEL_NAME + Contants.METHOD_AFFAIRS_QUERY_LIST
				+ Contants.PARAM_NAME + parase2Json(params);
		Log.e("URL", this.url);
		return new JsonObjectRequest(this.url, null, new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject arg0) {
				Log.e("Response", arg0.toString());
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
		}) {

			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				HashMap<String, String> headers = new HashMap<String, String>();
				headers.put("Content-Type", "application/json; charset=utf-8");
				return headers;
			}

		};
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
	public JsonObjectRequest queryAffairInfo(final Context c, String aid) {
		// ���Ϊ��ȡ���û���id����ֱ�ӷ���
		if (getUserId(c) == null || getUserIc(c) == null)
			return null;
		QueryAffairInfoRequest params = new QueryAffairInfoRequest(getUserId(c), getUserIc(c), aid);
		this.url = Contants.SERVER_URL + Contants.MODEL_NAME + Contants.METHOD_AFFAIRS_QUERY_INFO
				+ Contants.PARAM_NAME + parase2Json(params);
		System.out.println("request>>>>>>" + this.url);
		return new JsonObjectRequest(this.url, null, new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject arg0) {
				System.out.println("response>>>>>>>" + arg0.toString());
				try {
					if (arg0.getString("s").equals(Contants.RESULT_SUCCESS)) {
						QueryAffairInfoResponse r = gson.fromJson(arg0.toString(),
								QueryAffairInfoResponse.class);
						// db insert
						if (new AffairDao(c).saveAffairInfo(r)) {
							// �����ؽ�����ظ�handler����ui����
							MessageHandlerManager.getInstance().sendMessage(
									Constant.QUERY_TASK_INFO_REQUEST_SUCCESS, r,
									Contants.METHOD_AFFAIRS_QUERY_INFO);
						}
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
		}) {

			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				HashMap<String, String> headers = new HashMap<String, String>();
				headers.put("Content-Type", "application/json; charset=utf-8");
				return headers;
			}

		};

	}

	public JsonObjectRequest updateAffairReadtime(Context c, String aid) {
		if (getUserIc(c) == null || getUserId(c) == null)
			return null;
		UpdateAffairReadTimeRequest params = new UpdateAffairReadTimeRequest(getUserId(c),
				getUserIc(c), aid, System.currentTimeMillis() + "");
		this.url = Contants.SERVER_URL + Contants.MODEL_NAME
				+ Contants.METHOD_AFFAIRS_UPDATE_READTIME + Contants.PARAM_NAME
				+ parase2Json(params);
		System.out.println("request>>>>>>" + this.url);
		return new JsonObjectRequest(this.url, null, new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject arg0) {
				System.out.println("response>>>>>>>" + arg0.toString());
				try {
					if (arg0.getString("s").equals(Contants.RESULT_SUCCESS)) {
						UpdateAffairReadTimeResponse r = gson.fromJson(arg0.toString(),
								UpdateAffairReadTimeResponse.class);
						// �����ؽ�����ظ�handler����ui����
						MessageHandlerManager.getInstance().sendMessage(
								Constant.UPDATE_TASK_READTIME_SUCCESS, r,
								Contants.METHOD_AFFAIRS_UPDATE_READTIME);
					} else {
						NormalServerResponse r = gson.fromJson(arg0.toString(),
								NormalServerResponse.class);
						// �����ؽ�����ظ�handler����ui����
						MessageHandlerManager.getInstance().sendMessage(
								Constant.UPDATE_TASK_READTIME_FAIL, r,
								Contants.METHOD_AFFAIRS_UPDATE_READTIME);
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
		}) {

			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				HashMap<String, String> headers = new HashMap<String, String>();
				headers.put("Content-Type", "application/json; charset=utf-8");
				return headers;
			}

		};

	}

	public JsonObjectRequest updateAffairInfo(final Context c, final String aid,
			final List<CreateTaskRequestIds> pod, final List<CreateTaskRequestIds> rids,
			final String d, final String topic, final String et,
			final List<CreateTaskRequestAttachment> att) {
		// ���Ϊ��ȡ���û���id����ֱ�ӷ���
		if (getUserId(c) == null || getUserIc(c) == null)
			return null;
		UpdateAffairInfoRequest params = new UpdateAffairInfoRequest(getUserId(c), getUserIc(c),
				aid, pod, rids, d, topic, et, att);
		this.url = Contants.SERVER_URL + Contants.MODEL_NAME + Contants.METHOD_AFFAIRS_UPDATE_INFO
				+ Contants.PARAM_NAME + parase2Json(params);
		System.out.println("request>>>>>>" + this.url);
		return new JsonObjectRequest(this.url, null, new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject arg0) {
				System.out.println("response>>>>>>>" + arg0.toString());
				try {
					if (arg0.getString("s").equals(Contants.RESULT_SUCCESS)) {
						UpdateAffairInfoResponse r = gson.fromJson(arg0.toString(),
								UpdateAffairInfoResponse.class);
						// db update
						if (new AffairDao(c).updateAffairInfo(aid, pod, rids, d, topic, et, att)) {
							// �����ؽ�����ظ�handler����ui����
							MessageHandlerManager.getInstance().sendMessage(
									Constant.UPDATE_TASK_INFO_SUCCESS, r,
									Contants.METHOD_AFFAIRS_UPDATE_INFO);
						}
					} else {
						NormalServerResponse r = gson.fromJson(arg0.toString(),
								NormalServerResponse.class);
						// �����ؽ�����ظ�handler����ui����
						MessageHandlerManager.getInstance().sendMessage(
								Constant.UPDATE_TASK_INFO_FAIL, r,
								Contants.METHOD_AFFAIRS_UPDATE_INFO);
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
		}) {

			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				HashMap<String, String> headers = new HashMap<String, String>();
				headers.put("Content-Type", "application/json; charset=utf-8");
				return headers;
			}

		};

	}
}
