package android.wxapp.service.request;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import android.wxapp.service.dao.AffairDao;
import android.wxapp.service.dao.ConferenceDao;
import android.wxapp.service.handler.MessageHandlerManager;
import android.wxapp.service.jerry.model.affair.TaskUpdateQueryRequest;
import android.wxapp.service.jerry.model.affair.TaskUpdateQueryResponse;
import android.wxapp.service.jerry.model.conference.ConferenceQueryRequest;
import android.wxapp.service.jerry.model.conference.ConferenceQueryResponse;
import android.wxapp.service.jerry.model.conference.ConferenceUpdateQueryRequest;
import android.wxapp.service.jerry.model.conference.ConferenceUpdateQueryResponse;
import android.wxapp.service.jerry.model.conference.ConferenceUpdateQueryResponseRids;
import android.wxapp.service.jerry.model.conference.CreateConferenceRequest;
import android.wxapp.service.jerry.model.conference.CreateConferenceResponse;
import android.wxapp.service.jerry.model.conference.EndConferenceRequest;
import android.wxapp.service.jerry.model.conference.EndConferenceResponse;
import android.wxapp.service.jerry.model.conference.StartConferenceRequest;
import android.wxapp.service.jerry.model.conference.StartConferenceResponse;
import android.wxapp.service.jerry.model.normal.NormalServerResponse;
import android.wxapp.service.thread.SaveAffairUpdateThread;
import android.wxapp.service.thread.SaveConferenceThread;
import android.wxapp.service.util.Constant;

import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.JsonObjectRequest;

public class ConferenceRequest extends BaseRequest {

	/**
	 * 获取会议更新 JERRY
	 * <p>
	 * 判断返回的s值是否需要进行再次调用
	 * 
	 * @param context
	 * @param count
	 *            标记是第几次查询数据，用于分页请求数据。请求需要维护count值
	 * @return
	 */
	public JsonObjectRequest updateConference(final Context c, String count) {
		// 如果为获取到用户的id，则直接返回
		if (getUserId(c) == null || getUserIc(c) == null)
			return null;
		String lastUpdateTime = getLastConferenceUpdateTime(c);
		if (lastUpdateTime == null)
			lastUpdateTime = "";
		ConferenceUpdateQueryRequest params = new ConferenceUpdateQueryRequest(getUserId(c),
				getUserIc(c), lastUpdateTime, count);
		this.url = Contants.SERVER_URL + Contants.MODEL_NAME + Contants.METHOD_CONFERENCE_UPDATE
				+ Contants.PARAM_NAME + parase2Json(params);
		Log.e("URL", this.url);
		return new JsonObjectRequest(this.url, null, new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject arg0) {
				Log.e("Response", arg0.toString());
				try {
					// 表示已经没有更多的数据需要再次进行请求
					if (arg0.getString("s").equals(Contants.RESULT_SUCCESS)) {
						ConferenceUpdateQueryResponse r = gson.fromJson(arg0.toString(),
								ConferenceUpdateQueryResponse.class);
						// 进行数据库的操作,保存数据,并进行本地时间戳的修改
						new SaveConferenceThread(c, r).run();
						// 将返回结果返回给handler进行ui处理
						MessageHandlerManager.getInstance()
								.sendMessage(Constant.CONFERENCE_UPDATE_SECCESS, r,
										Contants.METHOD_CONFERENCE_UPDATE);
					}
					// 还有更多的数据需要进行请求
					// 废弃。服务器直接一次性返回。。。。。。
					else if (arg0.getString("s").equals(Contants.RESULT_MORE)) {

						Log.v("ConferenceRequest", "getConferenceUpdateRequest() 废弃接口");

						// ConferenceUpdateQueryResponse r =
						// gson.fromJson(arg0.toString(),
						// ConferenceUpdateQueryResponse.class);
						// // 保存此次数据

						// // 将返回结果返回给handler进行ui处理
						// MessageHandlerManager.getInstance().sendMessage(
						// Constant.CONFERENCE_UPDATE_SECCESS, r,
						// Contants.METHOD_CONFERENCE_UPDATE);

					} else {
						NormalServerResponse r = gson.fromJson(arg0.toString(),
								NormalServerResponse.class);
						// 将返回结果返回给handler进行ui处理
						MessageHandlerManager.getInstance().sendMessage(Constant.CONFERENCE_UPDATE_FAIL,
								r, Contants.METHOD_CONFERENCE_UPDATE);
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
		});
	}

	/**
	 * 创建会议
	 * 
	 */
	public JsonObjectRequest createConference(final Context c, final String n, final String sid,
			final String ct, final String f, final String r, final String et,
			final List<ConferenceUpdateQueryResponseRids> rids) {
		// 如果为获取到用户的id，则直接返回
		if (getUserId(c) == null || getUserIc(c) == null)
			return null;
		CreateConferenceRequest params = new CreateConferenceRequest(getUserId(c), getUserIc(c), n, sid,
				ct, f, r, et, rids);
		this.url = Contants.SERVER_URL + Contants.MODEL_NAME + Contants.METHOD_CONFERENCE_CREATE
				+ Contants.PARAM_NAME + parase2Json(params);
		Log.e("URL", this.url);
		return new JsonObjectRequest(this.url, null, new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject arg0) {
				Log.e("Response", arg0.toString());
				try {
					if (arg0.getString("s").equals(Contants.RESULT_SUCCESS)) {
						CreateConferenceResponse result = gson.fromJson(arg0.toString(),
								CreateConferenceResponse.class);
						// 进行数据库的操作,保存数据
						if (new ConferenceDao(c).saveConference(result.getCid(), n, sid, ct, f, "", "",
								r, rids))
							// save成功将返回结果返回给handler进行ui处理
							MessageHandlerManager.getInstance().sendMessage(
									Constant.CONFERENCE_CREATE_SECCESS, result,
									Contants.METHOD_CONFERENCE_CREATE);
					} else {
						NormalServerResponse r = gson.fromJson(arg0.toString(),
								NormalServerResponse.class);
						// 将返回结果返回给handler进行ui处理
						MessageHandlerManager.getInstance().sendMessage(Constant.CONFERENCE_CREATE_FAIL,
								r, Contants.METHOD_CONFERENCE_CREATE);
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
		});
	}

	/**
	 * 开始会议
	 * 
	 * @param c
	 * @param cid
	 * @return
	 */
	public JsonObjectRequest startConference(final Context c, final String cid) {
		// 如果为获取到用户的id，则直接返回
		if (getUserId(c) == null || getUserIc(c) == null)
			return null;
		final String starttime = System.currentTimeMillis() + "";
		StartConferenceRequest params = new StartConferenceRequest(getUserId(c), getUserIc(c), cid,
				starttime);
		this.url = Contants.SERVER_URL + Contants.MODEL_NAME + Contants.METHOD_CONFERENCE_START
				+ Contants.PARAM_NAME + parase2Json(params);
		Log.e("URL", this.url);
		return new JsonObjectRequest(this.url, null, new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject arg0) {
				Log.e("Response", arg0.toString());
				try {
					if (arg0.getString("s").equals(Contants.RESULT_SUCCESS)) {
						StartConferenceResponse r = gson.fromJson(arg0.toString(),
								StartConferenceResponse.class);
						// 进行数据库的操作,保存数据
						if (new ConferenceDao(c).startConference(cid, starttime)) {
							// 将返回结果返回给handler进行ui处理
							MessageHandlerManager.getInstance().sendMessage(
									Constant.CONFERENCE_START_SECCESS, r,
									Contants.METHOD_CONFERENCE_START);
						}
					} else {
						NormalServerResponse r = gson.fromJson(arg0.toString(),
								NormalServerResponse.class);
						// 将返回结果返回给handler进行ui处理
						MessageHandlerManager.getInstance().sendMessage(Constant.CONFERENCE_START_FAIL,
								r, Contants.METHOD_CONFERENCE_START);
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
		});
	}

	/**
	 * 结束会议
	 * 
	 * @param c
	 * @param cid
	 * @return
	 */
	public JsonObjectRequest endConference(final Context c, final String cid) {
		// 如果为获取到用户的id，则直接返回
		if (getUserId(c) == null || getUserIc(c) == null)
			return null;
		final String endtime = System.currentTimeMillis() + "";
		EndConferenceRequest params = new EndConferenceRequest(getUserId(c), getUserIc(c), cid, endtime);
		this.url = Contants.SERVER_URL + Contants.MODEL_NAME + Contants.METHOD_CONFERENCE_END
				+ Contants.PARAM_NAME + parase2Json(params);
		Log.e("URL", this.url);
		return new JsonObjectRequest(this.url, null, new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject arg0) {
				Log.e("Response", arg0.toString());
				try {
					if (arg0.getString("s").equals(Contants.RESULT_SUCCESS)) {
						EndConferenceResponse r = gson.fromJson(arg0.toString(),
								EndConferenceResponse.class);
						// 进行数据库的操作,保存数据
						if (new ConferenceDao(c).endConference(cid, endtime)) {
							// 将返回结果返回给handler进行ui处理
							MessageHandlerManager.getInstance().sendMessage(
									Constant.CONFERENCE_END_SECCESS, r, Contants.METHOD_CONFERENCE_END);
						}
					} else {
						NormalServerResponse r = gson.fromJson(arg0.toString(),
								NormalServerResponse.class);
						// 将返回结果返回给handler进行ui处理
						MessageHandlerManager.getInstance().sendMessage(Constant.CONFERENCE_END_FAIL, r,
								Contants.METHOD_CONFERENCE_END);
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
		});
	}

	public JsonObjectRequest getConference(final Context c, final String cid) {
		// 如果为获取到用户的id，则直接返回
		if (getUserId(c) == null || getUserIc(c) == null)
			return null;
		ConferenceQueryRequest params = new ConferenceQueryRequest(getUserId(c), getUserIc(c), cid);
		this.url = Contants.SERVER_URL + Contants.MODEL_NAME + Contants.METHOD_CONFERENCE_QUERY
				+ Contants.PARAM_NAME + parase2Json(params);
		Log.e("URL", this.url);
		return new JsonObjectRequest(this.url, null, new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject arg0) {
				Log.e("Response", arg0.toString());
				try {
					if (arg0.getString("s").equals(Contants.RESULT_SUCCESS)) {
						ConferenceQueryResponse r = gson.fromJson(arg0.toString(),
								ConferenceQueryResponse.class);
						// 进行数据库的操作,保存数据
						if (new ConferenceDao(c).saveConference(cid, r.getN(), r.getSid(), r.getCt(),
								r.getF(), r.getSt(), r.getEt(), r.getR(), r.getRids())) {
							// 将返回结果返回给handler进行ui处理
							MessageHandlerManager.getInstance().sendMessage(
									Constant.CONFERENCE_QUERY_SECCUESS, r,
									Contants.METHOD_CONFERENCE_QUERY);
						}
					} else {
						NormalServerResponse r = gson.fromJson(arg0.toString(),
								NormalServerResponse.class);
						// 将返回结果返回给handler进行ui处理
						MessageHandlerManager.getInstance().sendMessage(Constant.CONFERENCE_QUERY_FAIL,
								r, Contants.METHOD_CONFERENCE_QUERY);
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
		});
	}
}
