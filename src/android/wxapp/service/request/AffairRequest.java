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
	 * 获取事务更新 JERRY 5.23
	 * <p>
	 * 判断返回的s值是否需要进行再次调用
	 * 
	 * @param context
	 * @param count
	 *            标记是第几次查询数据，用于分页请求数据。请求需要维护count值
	 * @return
	 */
	public JsonObjectRequest getAffairUpdateRequest(final Context c, String count) {
		// 如果为获取到用户的id，则直接返回
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
					// 表示已经没有更多的数据需要再次进行请求
					if (arg0.getString("s").equals(Contants.RESULT_SUCCESS)) {
						TaskUpdateQueryResponse r = gson.fromJson(arg0.toString(),
								TaskUpdateQueryResponse.class);
						// 进行数据库的操作,保存数据
						new SaveAffairUpdateThread(c, r, new AffairDao(c)).run();

						// 将返回结果返回给handler进行ui处理
						MessageHandlerManager.getInstance().sendMessage(
								Constant.UPDATE_TASK_LIST_REQUEST_SUCCESS, r,
								Contants.METHOD_AFFAIRS_UPDATE_LIST);
					}
					// 还有更多的数据需要进行请求
					// 废弃。服务器直接一次性返回。。。。。。
					else if (arg0.getString("s").equals(Contants.RESULT_MORE)) {

						Log.v("AffairRequest", "getAffairUpdateRequest() 废弃接口");

						// TaskUpdateQueryResponse r =
						// gson.fromJson(arg0.toString(),
						// TaskUpdateQueryResponse.class);
						// // 保存此次数据
						// new SaveAffairUpdateThread(r, new AffairDao(c));
						// // 将返回结果返回给handler进行ui处理
						// MessageHandlerManager.getInstance().sendMessage(
						// Constant.UPDATE_TASK_LIST_REQUEST_SUCCESS, r,
						// Contants.METHOD_AFFAIRS_UPDATE_LIST);

					} else {
						NormalServerResponse r = gson.fromJson(arg0.toString(),
								NormalServerResponse.class);
						// 将返回结果返回给handler进行ui处理
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
	 * 创建事务 FINAL Jerry 5.23
	 * 
	 * @param c
	 * @param ic
	 *            用户密码
	 * @param t
	 *            事务类型（1：任务 2：请示 3：通知）
	 * @param sid
	 *            发起人id
	 * @param d
	 *            事务文本内容
	 * @param topic
	 *            事务主题
	 * @param bt
	 *            创建时间
	 * @param et
	 *            规定结束时间
	 * @param ct
	 *            实际完成时间 （可空）
	 * @param lot
	 *            最后一次操作类型（1-新建，2-置完成（手动），3-置延误（自动），4-修改截止日期）
	 * @param lotime
	 *            追后一次操作时间
	 * @param up
	 *            更新时间
	 * @param ats
	 *            附件类型数组（1：文本 2：图片3：录像4：录音5 ：GPS）
	 * @param us
	 *            附件链接数组
	 * @param rids
	 *            接受者的id数组
	 * @return
	 */
	public JsonObjectRequest getCreateAffairRequest(final Context c, final String t, final String d,
			final String topic, final String bt, final String et, final String ct, final String lot,
			final String lotime, final String up, final List<String> ats, final List<String> us,
			final List<String> rids, final List<String> pods) {
		// 如果为获取到用户的id，则直接返回
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

						// 将返回结果返回给handler进行ui处理
						MessageHandlerManager.getInstance().sendMessage(
								Constant.CREATE_AFFAIR_REQUEST_SUCCESS, r,
								Contants.METHOD_AFFAIRS_ADDAFFAIR);
					} else {
						NormalServerResponse r = gson.fromJson(arg0.toString(),
								NormalServerResponse.class);
						// 将返回结果返回给handler进行ui处理
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
				Toast.makeText(c, "服务器连接失败", Toast.LENGTH_LONG).show();
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
	 * 修改任务完成时间 Jerry 5.23
	 * 
	 * @param c
	 * @param ic
	 * @param aid
	 *            待编辑的事务ID
	 * @param et
	 *            修改后的任务结束时间
	 * @return
	 */
	public JsonObjectRequest getModifyEndTimeRequest(Context c, String ic, String aid, String et) {
		// 如果为获取到用户的id，则直接返回
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
								// TODO 是否需要进行数据库的操作

						// 将返回结果返回给handler进行ui处理
						MessageHandlerManager.getInstance().sendMessage(
								Constant.UPDATE_TASK_END_TIME_REQUEST_SUCCESS, r,
								Contants.METHOD_AFFAIRS_MODIFY_TASK);
					} else {
						NormalServerResponse r = gson.fromJson(arg0.toString(),
								NormalServerResponse.class);
						// 将返回结果返回给handler进行ui处理
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
	 * 结束任务 Jerry 5.23
	 * 
	 * @param c
	 * @param ic
	 * @param aid
	 *            待编辑的事务ID
	 * @param ct
	 *            修改后的任务完成时间，暂时为null
	 * @return
	 */
	public JsonObjectRequest getEndTaskRequest(final Context c, final String aid, final String ct) {
		// 如果为获取到用户的id，则直接返回
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
						// 将返回结果返回给handler进行ui处理
						MessageHandlerManager.getInstance().sendMessage(
								Constant.END_TASK_REQUEST_SUCCESS, r,
								Contants.METHOD_AFFAIRS_END_TASK);
					} else {
						NormalServerResponse r = gson.fromJson(arg0.toString(),
								NormalServerResponse.class);
						// 将返回结果返回给handler进行ui处理
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
	 * 获取每种任务的数量 Jerry 5.23
	 * 
	 * @param c
	 * @param ic
	 * @return
	 */
	public JsonObjectRequest queryAffairCount(Context c, String ic) {
		// 如果为获取到用户的id，则直接返回
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
						// 将返回结果返回给handler进行ui处理
						MessageHandlerManager.getInstance().sendMessage(
								Constant.QUERY_TASK_COUNT_REQUEST_SUCCESS, r,
								Contants.METHOD_AFFAIRS_QUERY_COUNT);
					} else {
						NormalServerResponse r = gson.fromJson(arg0.toString(),
								NormalServerResponse.class);
						// 将返回结果返回给handler进行ui处理
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
	 * 查询任务列表 Jerry 5.23
	 * 
	 * @param c
	 * @param ic
	 * @param sor
	 *            用于标记是自己发布的事务还是接收到的事务（0：自己发布的事务 1：自己接收到的事务）
	 * @param t
	 *            用于标记查询事务的状态（0：延误事务 1：已完成事务 2：正在完成中的事务）
	 * @param count
	 *            标记是第几次查询数据，用于分页请求数据
	 * @return
	 */
	public JsonObjectRequest queryAffairList(Context c, String ic, String sor, String t,
			String count) {
		// 如果为获取到用户的id，则直接返回
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
					// 表示已经没有更多的数据需要再次进行请求
					if (arg0.getString("s").equals(Contants.RESULT_SUCCESS)) {
						QueryAffairListResponse r = gson.fromJson(arg0.toString(),
								QueryAffairListResponse.class);
								// TODO 进行数据库的操作,保存数据

						// 将返回结果返回给handler进行ui处理
						MessageHandlerManager.getInstance().sendMessage(
								Constant.QUERY_TASK_LIST_REQUEST_SUCCESS, r,
								Contants.METHOD_AFFAIRS_QUERY_LIST);
					}
					// 还有更多的数据需要进行请求
					else if (arg0.getString("s").equals(Contants.RESULT_MORE)) {
						QueryAffairListResponse r = gson.fromJson(arg0.toString(),
								QueryAffairListResponse.class);
								// TODO 进行数据库的操作,保存数据

						// 将返回结果返回给handler进行ui处理
						MessageHandlerManager.getInstance().sendMessage(
								Constant.QUERY_TASK_LIST_REQUEST_SUCCESS, r,
								Contants.METHOD_AFFAIRS_QUERY_LIST);
					} else {
						NormalServerResponse r = gson.fromJson(arg0.toString(),
								NormalServerResponse.class);
						// 将返回结果返回给handler进行ui处理
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
	 * 查询任务详情 Jerry 5.23
	 * 
	 * @param c
	 * @param ic
	 * @param aid
	 *            任务id
	 * @return
	 */
	public JsonObjectRequest queryAffairInfo(final Context c, String aid) {
		// 如果为获取到用户的id，则直接返回
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
							// 将返回结果返回给handler进行ui处理
							MessageHandlerManager.getInstance().sendMessage(
									Constant.QUERY_TASK_INFO_REQUEST_SUCCESS, r,
									Contants.METHOD_AFFAIRS_QUERY_INFO);
						}
					} else {
						NormalServerResponse r = gson.fromJson(arg0.toString(),
								NormalServerResponse.class);
						// 将返回结果返回给handler进行ui处理
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
						// 将返回结果返回给handler进行ui处理
						MessageHandlerManager.getInstance().sendMessage(
								Constant.UPDATE_TASK_READTIME_SUCCESS, r,
								Contants.METHOD_AFFAIRS_UPDATE_READTIME);
					} else {
						NormalServerResponse r = gson.fromJson(arg0.toString(),
								NormalServerResponse.class);
						// 将返回结果返回给handler进行ui处理
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
		// 如果为获取到用户的id，则直接返回
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
							// 将返回结果返回给handler进行ui处理
							MessageHandlerManager.getInstance().sendMessage(
									Constant.UPDATE_TASK_INFO_SUCCESS, r,
									Contants.METHOD_AFFAIRS_UPDATE_INFO);
						}
					} else {
						NormalServerResponse r = gson.fromJson(arg0.toString(),
								NormalServerResponse.class);
						// 将返回结果返回给handler进行ui处理
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
