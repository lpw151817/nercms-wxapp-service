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
	 * // 待编辑的事务ID
	 */
	private String affairID;
	/**
	 * // 待发送创建事务模型
	 */
	private AffairModel affair;
	/**
	 * // 创建事务请求字符串
	 */
	private String createAffairString = null;
	/**
	 * // 登录用户ID
	 */
	private String userID;
	/**
	 * // 上一次更新事务的时间
	 */
	private String lastUpdateTime;
	/**
	 * // 获取事务更新请求字符串
	 */
	private String getAffairUpdateString = "";
	/**
	 * // 修改后的任务完成时间
	 */
	private String modifiedEndTime;
	/**
	 * // 任务完成请求字符串
	 */
	private String endTaskRequestString = null;
	/**
	 * // 修改任务结束时间请求字符串
	 */
	private String modifyEndTimeString = null;

	// 获取任务更新构造函数
	public AffairRequest(Context context) {
		this.context = context;
	}

	// 创建新任务构造函数
	public AffairRequest(AffairModel affair) {
		this.affair = affair;
	}

	// 完成任务的构造函数
	public AffairRequest(Context context, String affairID) {
		this.context = context;
		this.affairID = affairID;
	}

	// 修改任务完成时间构造函数
	public AffairRequest(Context context, String affairID, String modifiedEndTime) {
		this.context = context;
		this.affairID = affairID;
		this.modifiedEndTime = modifiedEndTime;
	}

	/**
	 * 向服务器端获取事务更新的Request
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
						// 判断服务器是否返回成功，并通知Handler返回消息
						Log.i("getAffairUpdate", response.toString());
						Log.v("getAffairUpdate", "获取任务更新成功");

						ArrayList<AffairModel> affairs = getArrayListFromJson(response);
						if (affairs != null) {
							Log.v("getAffairUpdate", "跳转事务保存线程之前");
							ThreadManager.getInstance().startSaveAffairThread(affairs, false, context);
						} else {
							Log.v("getAffairUpdate", "无数据更新");
							// 2014-7-23 WeiHao
							// 没有数据更新，将
							// 更新首次运行标志为“否”
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
		/* 发送事务 */
		// 创建包含JSON对象的请求地址
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
		// //status在创建事务时默认为1，无须传值到服务器
		requestParams.append("\"remark\":\"" + "m\","); // remark默认置为空

		if (affair.getAttachments() != null) { // 有附件
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
		} else { // 无附件
			requestParams.append("\"attr\":[]}");
		}

		Log.i("test", Constant.SERVER_BASE_URL + Constant.CREATE_TASK_URL + requestParams.toString());
		try {
			createAffairString = Constant.SERVER_BASE_URL + Constant.CREATE_TASK_URL
					+ URLEncoder.encode(requestParams.toString(), "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 向服务器发送请求
		JsonObjectRequest sendAffairRequest = new JsonObjectRequest(createAffairString, null,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						// 发送成功，判断服务器是否返回成功，并通知Handler返回消息
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
						// 发送失败，通知Handler错误信息
						MessageHandlerManager.getInstance().sendMessage(
								Constant.CREATE_AFFAIR_REQUEST_FAIL, error.getMessage(), "TaskAdd");
					}
				});

		return sendAffairRequest;
	}

	/**
	 * 向服务器端发送修改任务完成时间的Request
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
								Log.v("ModifyEndTimeRequest", "任务完成时间已修改");

								// 服务器端置修改时间成功，下一步手机端数据库更新完成时间
								// 获取服务器端返回的最后一次操作时间
								String lastOperateTime = response.getString("lotm");
								AffairDao dao = DAOFactory.getInstance().getAffairDao(context);
								dao.updateAffairEndTime(affairID, modifiedEndTime, lastOperateTime);

								// 通知界面
								MessageHandlerManager.getInstance().sendMessage(
										Constant.MODIFY_TASK_REQUEST_SUCCESS, "TaskDetail");

							} else {
								Log.i("ModifyEndTimeRequest", response.toString());
								Log.v("ModifyEndTimeRequest", "修改任务完成时间异常");
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						// 通知界面
						MessageHandlerManager.getInstance().sendMessage(
								Constant.MODIFY_TASK_REQUEST_FAIL, "TaskDetail");
					}
				});

		return modifyEndTimeRequest;
	}

	/**
	 * 向服务器端发送结束任务的Request
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
								Log.v("endTaskRequest", "任务已经置完成");

								// 服务器端置完成成功，下一步手机端数据库更新为已完成
								// 获取服务器端返回的事务完成时间
								String completeTime = response.getString("ct");
								AffairDao dao = DAOFactory.getInstance().getAffairDao(context);
								dao.updateAffairCompleted(affairID, completeTime);
								// 通知界面
								MessageHandlerManager.getInstance().sendMessage(
										Constant.END_TASK_REQUEST_SUCCESS, "TaskDetail");

							} else {
								Log.i("endTaskRequest", response.toString());
								Log.v("endTaskRequest", "任务置完成异常");
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
						// 通知界面
						MessageHandlerManager.getInstance().sendMessage(Constant.END_TASK_REQUEST_FAIL,
								"TaskDetail");
					}
				});
		return endTaskRequest;
	}

	/**
	 * 服务器反馈Json解析成Affair列表
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

			// 更新最后一次更新任务的时戳
			if (updateTimeStamp != null) {
				MySharedPreference.save(context, MySharedPreference.LAST_UPDATE_TASK_TIMESTAMP,
						updateTimeStamp);
			}
		}

		return affairList;
	}

}
