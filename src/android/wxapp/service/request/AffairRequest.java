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
	// * // 待编辑的事务ID
	// */
	// private String affairID;
	// /**
	// * // 待发送创建事务模型
	// */
	// private AffairModel affair;
	// /**
	// * // 创建事务请求字符串
	// */
	// private String createAffairString = null;
	// /**
	// * // 登录用户ID
	// */
	// private String userID;
	// /**
	// * // 上一次更新事务的时间
	// */
	// private String lastUpdateTime;
	// /**
	// * // 获取事务更新请求字符串
	// */
	// private String getAffairUpdateString = "";
	// /**
	// * // 修改后的任务完成时间
	// */
	// private String modifiedEndTime;
	// /**
	// * // 任务完成请求字符串
	// */
	// private String endTaskRequestString = null;
	// /**
	// * // 修改任务结束时间请求字符串
	// */
	// private String modifyEndTimeString = null;

	public AffairRequest() {
	}

	// // 获取任务更新构造函数
	// public AffairRequest(Context context) {
	// this.context = context;
	// }
	//
	// // 创建新任务构造函数
	// public AffairRequest(AffairModel affair) {
	// this.affair = affair;
	// }
	//
	// // 完成任务的构造函数
	// public AffairRequest(Context context, String affairID) {
	// this.context = context;
	// this.affairID = affairID;
	// }
	//
	// // 修改任务完成时间构造函数
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
	 * 获取事务更新 逻辑有问题，需要重新结合接口 JERRY 5.23
	 * <p>
	 * 判断返回的s值是否需要进行再次调用
	 * 
	 * @param context
	 * @param ic
	 * @param count
	 *            标记是第几次查询数据，用于分页请求数据。请求需要维护count值
	 * @return
	 */
	public JsonObjectRequest getAffairUpdateRequest(final Context c, String ic, String count) {
		// 如果为获取到用户的id，则直接返回
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
					// 表示已经没有更多的数据需要再次进行请求
					if (arg0.getString("s").equals(Contants.RESULT_SUCCESS)) {
						TaskUpdateQueryResponse r = gson.fromJson(arg0.toString(),
								TaskUpdateQueryResponse.class);
						// TODO 进行数据库的操作,保存数据

						// 更新本地时间戳
						MySharedPreference.save(c, MySharedPreference.LAST_UPDATE_TASK_TIMESTAMP,
								System.currentTimeMillis());
						// 将返回结果返回给handler进行ui处理
						MessageHandlerManager.getInstance().sendMessage(
								Constant.UPDATE_TASK_LIST_REQUEST_SUCCESS, r,
								Contants.METHOD_AFFAIRS_UPDATE_LIST);
					}
					// 还有更多的数据需要进行请求
					else if (arg0.getString("s").equals(Contants.RESULT_MORE)) {
						TaskUpdateQueryResponse r = gson.fromJson(arg0.toString(),
								TaskUpdateQueryResponse.class);
						// TODO 保存此次数据

						// 将返回结果返回给handler进行ui处理
						MessageHandlerManager.getInstance().sendMessage(
								Constant.UPDATE_TASK_LIST_REQUEST_SUCCESS, r,
								Contants.METHOD_AFFAIRS_UPDATE_LIST);
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
		// // 判断服务器是否返回成功，并通知Handler返回消息
		// Log.i("getAffairUpdate", response.toString());
		// Log.v("getAffairUpdate", "获取任务更新成功");
		//
		// ArrayList<AffairModel> affairs = getArrayListFromJson(response);
		// if (affairs != null) {
		// Log.v("getAffairUpdate", "跳转事务保存线程之前");
		// ThreadManager.getInstance().startSaveAffairThread(affairs, false,
		// context);
		// } else {
		// Log.v("getAffairUpdate", "无数据更新");
		// // 2014-7-23 WeiHao
		// // 没有数据更新，将
		// // 更新首次运行标志为“否”
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
	 * 创建事务 FINAL　Jerry 5.23
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
	 * @return
	 */
	public JsonObjectRequest getCreateAffairRequest(Context c, String ic, String t, String sid,
			String d, String topic, String bt, String et, String ct, String lot, String lotime,
			String up, List<String> ats, List<String> us) {
		// 如果为获取到用户的id，则直接返回
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
			}
		});

		// /* 发送事务 */
		// // 创建包含JSON对象的请求地址
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
		// // //status在创建事务时默认为1，无须传值到服务器
		// requestParams.append("\"remark\":\"" + "m\","); // remark默认置为空
		//
		// if (affair.getAttachments() != null) { // 有附件
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
		// } else { // 无附件
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
		// // 向服务器发送请求
		// JsonObjectRequest sendAffairRequest = new
		// JsonObjectRequest(createAffairString, null,
		// new Response.Listener<JSONObject>() {
		//
		// @Override
		// public void onResponse(JSONObject response) {
		// // 发送成功，判断服务器是否返回成功，并通知Handler返回消息
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
		// // 发送失败，通知Handler错误信息
		// MessageHandlerManager.getInstance().sendMessage(
		// Constant.CREATE_AFFAIR_REQUEST_FAIL, error.getMessage(), "TaskAdd");
		// }
		// });
		//
		// return sendAffairRequest;
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
				+ Contants.PARAM_NAME + super.gson.toJson(params);
		System.out.println("request>>>>>>" + this.url);
		return new JsonObjectRequest(this.url, null, new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject arg0) {
				System.out.println("response>>>>>>>" + arg0.toString());
				try {
					if (arg0.getString("s").equals(Contants.RESULT_SUCCESS)) {
						ModifyTaskResponse r = gson.fromJson(arg0.toString(), ModifyTaskResponse.class);
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
		// Log.v("ModifyEndTimeRequest", "任务完成时间已修改");
		//
		// // 服务器端置修改时间成功，下一步手机端数据库更新完成时间
		// // 获取服务器端返回的最后一次操作时间
		// String lastOperateTime = response.getString("lotm");
		// AffairDao dao = DAOFactory.getInstance().getAffairDao(context);
		// dao.updateAffairEndTime(affairID, modifiedEndTime, lastOperateTime);
		//
		// // 通知界面
		// MessageHandlerManager.getInstance().sendMessage(
		// Constant.MODIFY_TASK_REQUEST_SUCCESS, "TaskDetail");
		//
		// } else {
		// Log.i("ModifyEndTimeRequest", response.toString());
		// Log.v("ModifyEndTimeRequest", "修改任务完成时间异常");
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
		// // 通知界面
		// MessageHandlerManager.getInstance().sendMessage(
		// Constant.MODIFY_TASK_REQUEST_FAIL, "TaskDetail");
		// }
		// });
		//
		// return modifyEndTimeRequest;
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
	public JsonObjectRequest getEndTaskRequest(Context c, String ic, String aid, String ct) {
		// 如果为获取到用户的id，则直接返回
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
						// 将返回结果返回给handler进行ui处理
						MessageHandlerManager.getInstance().sendMessage(
								Constant.END_TASK_REQUEST_SUCCESS, r, Contants.METHOD_AFFAIRS_END_TASK);
					} else {
						NormalServerResponse r = gson.fromJson(arg0.toString(),
								NormalServerResponse.class);
						// 将返回结果返回给handler进行ui处理
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
		// Log.v("endTaskRequest", "任务已经置完成");
		//
		// // 服务器端置完成成功，下一步手机端数据库更新为已完成
		// // 获取服务器端返回的事务完成时间
		// String completeTime = response.getString("ct");
		// AffairDao dao = DAOFactory.getInstance().getAffairDao(context);
		// dao.updateAffairCompleted(affairID, completeTime);
		// // 通知界面
		// MessageHandlerManager.getInstance().sendMessage(
		// Constant.END_TASK_REQUEST_SUCCESS, "TaskDetail");
		//
		// } else {
		// Log.i("endTaskRequest", response.toString());
		// Log.v("endTaskRequest", "任务置完成异常");
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
		// // 通知界面
		// MessageHandlerManager.getInstance().sendMessage(Constant.END_TASK_REQUEST_FAIL,
		// "TaskDetail");
		// }
		// });
		// return endTaskRequest;
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
		});
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
	public JsonObjectRequest queryAffairList(Context c, String ic, String sor, String t, String count) {
		// 如果为获取到用户的id，则直接返回
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
		});
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
	public JsonObjectRequest queryAffairInfo(Context c, String ic, String aid) {
		// 如果为获取到用户的id，则直接返回
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
						// 将返回结果返回给handler进行ui处理
						MessageHandlerManager.getInstance().sendMessage(
								Constant.QUERY_TASK_INFO_REQUEST_SUCCESS, r,
								Contants.METHOD_AFFAIRS_QUERY_INFO);
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
		});

	}

	// /**
	// * 服务器反馈Json解析成Affair列表
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
	// // 更新最后一次更新任务的时戳
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
