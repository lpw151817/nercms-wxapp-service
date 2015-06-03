package android.wxapp.service.request;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;
import android.wxapp.service.dao.DAOFactory;
import android.wxapp.service.dao.PersonDao;
import android.wxapp.service.handler.MessageHandlerManager;
import android.wxapp.service.jerry.model.normal.NormalServerResponse;
import android.wxapp.service.jerry.model.person.AddPersonContactRequest;
import android.wxapp.service.jerry.model.person.AddPersonContactResponse;
import android.wxapp.service.jerry.model.person.ChangePwdRequest;
import android.wxapp.service.jerry.model.person.ChangePwdResponse;
import android.wxapp.service.jerry.model.person.Contacts;
import android.wxapp.service.jerry.model.person.GetOrgCodePersonRequest;
import android.wxapp.service.jerry.model.person.GetOrgCodePersonResponse;
import android.wxapp.service.jerry.model.person.GetOrgCodeRequest;
import android.wxapp.service.jerry.model.person.GetOrgCodeResponse;
import android.wxapp.service.jerry.model.person.GetPersonInfoRequest;
import android.wxapp.service.jerry.model.person.GetPersonInfoResponse;
import android.wxapp.service.jerry.model.person.LoginRequest;
import android.wxapp.service.jerry.model.person.LoginResponse;
import android.wxapp.service.jerry.model.person.LogoutRequest;
import android.wxapp.service.jerry.model.person.LogoutResponse;
import android.wxapp.service.jerry.model.person.ModifyCustomerRequest;
import android.wxapp.service.jerry.model.person.ModifyCustomerResponse;
import android.wxapp.service.model.CustomerContactModel;
import android.wxapp.service.model.CustomerModel;
import android.wxapp.service.thread.SaveOrgCodePersonThread;
import android.wxapp.service.thread.SaveOrgCodeThread;
import android.wxapp.service.thread.ThreadManager;
import android.wxapp.service.util.Constant;
import android.wxapp.service.util.MySharedPreference;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 完成文档中Person模块的所有接口 待测试
 * 
 * @author JerryLiu
 *
 */
public class PersonRequest extends BaseRequest {

	// private Context context;
	// /**
	// * // 用户ID
	// */
	// private String personID;
	// /**
	// * // 用户登录名
	// */
	// private String aliasName;
	// /**
	// * // 用户密码
	// */
	// private String identifyCode;
	// /**
	// * // IMSI号
	// */
	// private String imsi;
	//
	// /**
	// * // 待修改的用户新密码
	// */
	// private String newIdentifyCode;
	//
	// /**
	// * // 待新建（或编辑）的客户模型
	// */
	// private CustomerModel customer;
	// /**
	// * // 待新建（或编辑）的客户的联系方式列表
	// */
	// private ArrayList<CustomerContactModel> customerContactList = null;
	//
	// /**
	// * // 待删除客户的ID
	// */
	// private String customerID;
	//
	// /**
	// * // 新建客户请求字符串
	// */
	// private String newCustomerRequestString = null;
	// /**
	// * // 删除客户请求字符串
	// */
	// private String deleteCustomerRequestString = null;
	// /**
	// * // 用户登录验证请求字符串
	// */
	// private String loginRequestString = null;
	// /**
	// * // 获取所有联系人请求字符串
	// */
	// private String getAllPersonRequestString = null;
	// /**
	// * // 修改用户密码请求字符串
	// */
	// private String changePasswordRequestString = null;
	// /**
	// * // 编辑客户信息请求字符串
	// */
	// private String modifyCustomerRequeString = null;

	public PersonRequest() {

	}

	// // 编辑客户信息的构造函数
	// /**
	// * 新建（或编辑）客户时的构造函数
	// * <p>
	// * 说明：新建客户时，输入参数Customer模型中，customerID可为任意int数值，contactID为必填且真实，
	// * <p>
	// * 服务器返回ID后，必须setCustomerID后才可开始保存到本地;
	// * <p>
	// * 编辑客户信息时，输入参数Customer模型中，customerID和contactID为必填且真实;
	// * <p>
	// * 任何时候，输入参数CustomerContactList均可为null，表示该客户无联系方式
	// *
	// * @param context
	// * @param customer
	// * @param customerContactList
	// */
	// public PersonRequest(Context context, CustomerModel customer,
	// ArrayList<CustomerContactModel> customerContactList) {
	// this.context = context;
	// this.customer = customer;
	// this.customerContactList = customerContactList;
	// }

	/**
	 * 得到新建客户的Request
	 * 
	 * @param context
	 * @param customer
	 *            编辑客户信息时，输入参数Customer模型中，customerID和contactID为必填且真实;
	 * @param customerContactList
	 *            任何时候，输入参数CustomerContactList均可为null，表示该客户无联系方式
	 * @return
	 */
	@Deprecated
	public JsonObjectRequest sendNewCustomerRequest(Context context, CustomerModel customer,
			ArrayList<CustomerContactModel> customerContactList) {
		return null;
		// /* 发送事务 */
		// // 创建包含JSON对象的请求地址
		// StringBuilder requestParams = new StringBuilder();
		// requestParams.append("{\"name\":\"" + customer.getName() + "\",");
		// requestParams.append("\"unit\":\"" + customer.getUnit() + "\",");
		// requestParams.append("\"dpt\":\"" + customer.getDescription() +
		// "\",");
		// requestParams.append("\"coid\":\"" + customer.getContactID() +
		// "\",");
		//
		// if (customerContactList != null && customerContactList.size() != 0) {
		// requestParams.append("\"cots\":[");
		// for (int i = 0; i < customerContactList.size(); i++) {
		// requestParams.append("{\"type\":\"" +
		// customerContactList.get(i).getType() + "\",");
		// requestParams.append("\"cot\":\"" +
		// customerContactList.get(i).getContent() + "\"}");
		// if (i < customerContactList.size() - 1) {
		// requestParams.append(",");
		// }
		// }
		// requestParams.append("]}");
		// } else { // 无联系方式信息
		// requestParams.append("\"cots\":null}");
		// }
		//
		// try {
		// url = Constant.SERVER_BASE_URL + Constant.CREATE_CUSTOMER_URL +
		// "?param="
		// + URLEncoder.encode(requestParams.toString(), "UTF-8");
		// } catch (UnsupportedEncodingException e1) {
		// e1.printStackTrace();
		// }
		//
		// JsonObjectRequest newCustomerRequest = new JsonObjectRequest(url,
		// null,
		// new Response.Listener<JSONObject>() {
		//
		// @Override
		// public void onResponse(JSONObject response) {
		// // 发送成功，判断服务器是否返回成功，并通知Handler返回消息
		// Log.i("newCustomerRequest", response.toString());
		//
		// try {
		// if (response.getString("success").equals("0")) {
		// // 服务器保存成功
		// String customerID = response.getString("csid");
		// // 通知界面
		// MessageHandlerManager.getInstance().sendMessage(
		// Constant.CREATE_CUSTOMER_REQUEST_SUCCESS, customerID,
		// "ContactAdd");
		// // 0721添加通知contactFragment 刷新组织结构树
		// MessageHandlerManager.getInstance().sendMessage(
		// Constant.CREATE_CUSTOMER_REQUEST_SUCCESS, customerID, "Main");
		//
		// } else if (response.getString("success").equals("1")) {
		// Log.i("newCustomerRequest", response.getString("reason").toString());
		// MessageHandlerManager.getInstance().sendMessage(
		// Constant.CREATE_CUSTOMER_REQUEST_FAIL, "ContactAdd");
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
		//
		// }
		// });
		//
		// return newCustomerRequest;
	}

	// /**
	// * 删除客户时的构造函数
	// */
	// public PersonRequest(Context context, String customerID) {
	// this.context = context;
	// this.customerID = customerID;
	// }

	/**
	 * 删除客户
	 * 
	 * @param context
	 * @param customerID
	 *            待删除客户的ID
	 * @return
	 */
	@Deprecated
	public JsonObjectRequest sendDeleteCustomerRequest(final Context context, final String customerID) {
		return null;
		// url = Constant.SERVER_BASE_URL + Constant.DELETE_CUSTOMER_URL +
		// "?param={\"csid\":\""
		// + customerID + "\"}";
		// JsonObjectRequest deleteCustomerRequest = new JsonObjectRequest(url,
		// null,
		// new Response.Listener<JSONObject>() {
		//
		// @Override
		// public void onResponse(JSONObject response) {
		// try {
		// if (response.getString("success").equalsIgnoreCase("0")) {
		// Log.i("deleteCustomerRequest", response.toString());
		// Log.v("deleteCustomerRequest", "客户已标记删除");
		//
		// // 服务器端标志删除完成，下一步手机端数据库删除该客户信息
		// PersonDao dao = DAOFactory.getInstance().getPersonDao(context);
		// dao.deleteCustomer(customerID);
		// // 0722 通知UI
		// MessageHandlerManager.getInstance().sendMessage(
		// Constant.DELETE_CUSTOMER_REQUEST_SUCCESS, customerID, "Main");
		//
		// } else {
		//
		// }
		// } catch (JSONException e) {
		// e.printStackTrace();
		// }
		// }
		// }, new Response.ErrorListener() {
		// @Override
		// public void onErrorResponse(VolleyError error) {
		// Log.e("deleteCustomerRequest", error.toString());
		// }
		// });
		//
		// return deleteCustomerRequest;
	}

	/**
	 * 修改客户信息
	 * 
	 * @param customer
	 *            待编辑的客户模型
	 * @param customerContactList
	 *            待编辑的客户的联系方式列表
	 * @return
	 */
	@Deprecated
	public JsonObjectRequest sendModifyCustomerRequest(CustomerModel customer,
			ArrayList<CustomerContactModel> customerContactList) {
		return null;
		// // 创建包含JSON对象的请求地址
		// StringBuilder requestParams = new StringBuilder();
		// requestParams.append("{\"csid\":\"" + customer.getCustomerID() +
		// "\",");
		// requestParams.append("\"name\":\"" + customer.getName() + "\",");
		// requestParams.append("\"unit\":\"" + customer.getUnit() + "\",");
		// requestParams.append("\"dpt\":\"" + customer.getDescription() +
		// "\",");
		//
		// if (customerContactList != null && customerContactList.size() != 0) {
		// requestParams.append("\"cots\":[");
		// for (int i = 0; i < customerContactList.size(); i++) {
		// requestParams.append("{\"type\":\"" +
		// customerContactList.get(i).getType() + "\",");
		// requestParams.append("\"cot\":\"" +
		// customerContactList.get(i).getContent() + "\"}");
		// if (i < customerContactList.size() - 1) {
		// requestParams.append(",");
		// }
		// }
		// requestParams.append("]}");
		// } else { // 无联系方式信息
		// requestParams.append("\"cots\":null}");
		// }
		// try {
		// url = Constant.SERVER_BASE_URL + Constant.MODIFY_CUSTOMER_URL +
		// "?param="
		// + URLEncoder.encode(requestParams.toString(), "UTF-8");
		// } catch (UnsupportedEncodingException e) {
		// e.printStackTrace();
		// }
		// JsonObjectRequest modifyCustomerRequest = new JsonObjectRequest(url,
		// null,
		// new Response.Listener<JSONObject>() {
		//
		// @Override
		// public void onResponse(JSONObject response) {
		// // 发送成功，判断服务器是否返回成功，并通知Handler返回消息
		// Log.i("modifyCustomerRequest", response.toString());
		//
		// try {
		// if (response.getString("success").equals("0")) {
		//
		// // 服务器保存成功
		// MessageHandlerManager.getInstance().sendMessage(
		// Constant.MODIFY_CUSTOMER_REQUEST_SUCCESS, "ContactAdd");
		// // 0721 通知UI 刷新组织结构树
		// MessageHandlerManager.getInstance().sendMessage(
		// Constant.MODIFY_CUSTOMER_REQUEST_SUCCESS, "Main");
		//
		// } else if (response.getString("success").equals("1")) {
		// Log.i("newCustomerRequest", response.getString("reason").toString());
		// MessageHandlerManager.getInstance().sendMessage(
		// Constant.MODIFY_CUSTOMER_REQUEST_FAIL, "ContactAdd");
		// }
		// } catch (JSONException e) {
		// e.printStackTrace();
		// }
		//
		// }
		// }, new Response.ErrorListener() {
		//
		// @Override
		// public void onErrorResponse(VolleyError error) {
		//
		// }
		// });
		//
		// return modifyCustomerRequest;
	}

	/**
	 * 得到获取所有联系人的Request
	 * 
	 * @return
	 */
	@Deprecated
	public JsonObjectRequest getAllPersonRequest(final Context context) {
		return null;
		// if (getUserId(context) == null)
		// return null;
		// url = Constant.SERVER_BASE_URL + Constant.GET_ALL_PERSON_URL +
		// "?param={\"mid\":\""
		// + getUserId(context) + "\"}";
		// JsonObjectRequest getAllPersonRequest = new JsonObjectRequest(url,
		// null,
		// new Response.Listener<JSONObject>() {
		//
		// @Override
		// public void onResponse(JSONObject response) {
		// // 发送成功，判断服务器是否返回成功，并通知Handler返回消息
		// Log.i("getAllPersonsInfo", "下载联系人成功，转到保存线程");
		// ThreadManager.getInstance().startSaveAllPersonThread(response,
		// context);
		// }
		// }, new Response.ErrorListener() {
		//
		// @Override
		// public void onErrorResponse(VolleyError error) {
		//
		// }
		// });
		//
		// return getAllPersonRequest;
	}

	// public PersonRequest(Context c) {
	// this.context = c;
	// }

	// ======================================================
	//
	// JerryLiu 2015.5.22 Complete
	//
	// 1.modifyCustomerInfo
	// 2.getOrgCode
	// 3.getOrgCodePerson
	// 4.getLoginRequest
	// 5.logOut
	// 6.getPersonInfo
	// 7.addPersonContact
	//
	// ======================================================

	/**
	 * 修改用户信息 FINAL Jerry 15.5.21
	 */
	public JsonObjectRequest modifyCustomerInfo(Context c, String identifyCode, String aliasName) {
		if (getUserId(c) == null)
			return null;
		ModifyCustomerRequest params = new ModifyCustomerRequest(getUserId(c), identifyCode, aliasName);
		this.url = Contants.SERVER_URL + Contants.MODEL_NAME + Contants.METHOD_PERSON_MODIFYUSERINFO
				+ Contants.PARAM_NAME + super.gson.toJson(params);
		System.out.println(this.url);
		return new JsonObjectRequest(this.url, null, new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject arg0) {
				try {
					if (arg0.getString("s").equals(Contants.RESULT_SUCCESS)) {
						ModifyCustomerResponse r = gson.fromJson(arg0.toString(),
								ModifyCustomerResponse.class);
						MessageHandlerManager.getInstance().sendMessage(
								Constant.MODIFY_USERINFO_REQUEST_SUCCESS, r,
								Contants.METHOD_PERSON_MODIFYUSERINFO);
					} else {
						NormalServerResponse r = gson.fromJson(arg0.toString(),
								NormalServerResponse.class);
						// 返回错误代码
						MessageHandlerManager.getInstance().sendMessage(
								Constant.MODIFY_USERINFO_REQUEST_FAIL, r,
								Contants.METHOD_PERSON_MODIFYUSERINFO);
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
	 * 查询组织结构树中的结点 FINAL Jerry 15.5.22
	 * 
	 * @param c
	 * @return
	 */
	public JsonObjectRequest getOrgCode(final Context c) {
		if (getUserId(c) == null || getUserIc(c) == null)
			return null;
		String orgCodeUptateTime = MySharedPreference.get(c,
				MySharedPreference.LAST_UPDATE_ORGCODE_TIMESTAMP, null);
		if (orgCodeUptateTime == null)
			orgCodeUptateTime = System.currentTimeMillis() + "";
		GetOrgCodeRequest params = new GetOrgCodeRequest(getUserId(c), getUserIc(c), orgCodeUptateTime);
		this.url = Contants.SERVER_URL + Contants.MODEL_NAME + Contants.METHOD_PERSON_GET_ORG_CODE
				+ Contants.PARAM_NAME + super.gson.toJson(params);
		System.out.println(this.url);
		return new JsonObjectRequest(this.url, null, new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject arg0) {
				try {
					if (arg0.getString("s").equals(Contants.RESULT_SUCCESS)) {
						GetOrgCodeResponse r = gson.fromJson(arg0.toString(), GetOrgCodeResponse.class);
						// 进行数据库的添加,并保存时间戳，返回handler到ui线程进行操作
						new SaveOrgCodeThread(c, r).run();

						// MessageHandlerManager.getInstance().sendMessage(
						// Constant.QUERY_ORG_NODE_REQUEST_SUCCESS, r,
						// Contants.METHOD_PERSON_GET_ORG_CODE);
					} else {
						NormalServerResponse r = gson.fromJson(arg0.toString(),
								NormalServerResponse.class);
						// 返回错误代码
						MessageHandlerManager.getInstance().sendMessage(
								Constant.QUERY_ORG_NODE_REQUEST_FAIL, r,
								Contants.METHOD_PERSON_GET_ORG_CODE);
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
	 * 查询对应组织结点下的人员 FINAL Jerry 15.5.22
	 * 
	 * @param c
	 * @return
	 */
	public JsonObjectRequest getOrgCodePerson(final Context c) {
		if (getUserId(c) == null || getUserIc(c) == null)
			return null;
		String orgPersonUptateTime = MySharedPreference.get(c,
				MySharedPreference.LAST_UPDATE_ORGPERSON_TIMESTAMP, null);
		if (orgPersonUptateTime == null)
			orgPersonUptateTime = System.currentTimeMillis() + "";
		GetOrgCodePersonRequest params = new GetOrgCodePersonRequest(getUserId(c), getUserIc(c),
				orgPersonUptateTime);
		this.url = Contants.SERVER_URL + Contants.MODEL_NAME + Contants.METHOD_PERSON_GET_ORG_PERSON
				+ Contants.PARAM_NAME + super.gson.toJson(params);
		System.out.println(this.url);
		return new JsonObjectRequest(this.url, null, new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject arg0) {
				try {
					if (arg0.getString("s").equals(Contants.RESULT_SUCCESS)) {
						GetOrgCodePersonResponse r = gson.fromJson(arg0.toString(),
								GetOrgCodePersonResponse.class);
						// 进行数据库的添加,并返回handler到ui线程进行操作
						new SaveOrgCodePersonThread(c, r);

						// MessageHandlerManager.getInstance().sendMessage(
						// Constant.QUERY_ORG_PERSON_REQUEST_SUCCESS, r,
						// Contants.METHOD_PERSON_GET_ORG_PERSON);
					} else {
						NormalServerResponse r = gson.fromJson(arg0.toString(),
								NormalServerResponse.class);
						// 返回错误代码
						MessageHandlerManager.getInstance().sendMessage(
								Constant.QUERY_ORG_PERSON_REQUEST_FAIL, r,
								Contants.METHOD_PERSON_GET_ORG_PERSON);
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
	 * 用户登录 FINAL Jerry 15.5.21
	 */
	public JsonObjectRequest getLoginRequest(String aliasName, String identifyCode, String imsi) {
		LoginRequest lr = new LoginRequest(aliasName, identifyCode, imsi);
		this.url = Contants.SERVER_URL + Contants.MODEL_NAME + Contants.METHOD_PERSON_LOGIN
				+ Contants.PARAM_NAME + super.gson.toJson(lr);
		System.out.println(this.url);
		return new JsonObjectRequest(this.url, null, new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject arg0) {
				try {
					if (arg0.getString("s").equals(Contants.RESULT_SUCCESS)) {
						LoginResponse r = gson.fromJson(arg0.toString(), LoginResponse.class);
						// 返回用户id
						MessageHandlerManager.getInstance().sendMessage(Constant.LOGIN_REQUEST_SUCCESS,
								r, Contants.METHOD_PERSON_LOGIN);
					} else {
						NormalServerResponse r = gson.fromJson(arg0.toString(),
								NormalServerResponse.class);
						// 返回错误代码
						MessageHandlerManager.getInstance().sendMessage(Constant.LOGIN_REQUEST_FAIL, r,
								Contants.METHOD_PERSON_LOGIN);
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
		// loginRequestString = Constant.SERVER_BASE_URL + Constant.LOGIN_URL +
		// "?param={\"i\":\""
		// + aliasName + "\",\"c\":\"" + identifyCode + "\",\"s\":\"" + imsi +
		// "\"}";
		// Log.v("LoginRequest", loginRequestString);
		// try {
		// loginRequestString = Constant.SERVER_BASE_URL
		// + Constant.LOGIN_URL
		// + "?param="
		// + URLEncoder.encode("{\"i\":\"" + aliasName + "\",\"c\":\"" +
		// identifyCode
		// + "\",\"s\":\"" + imsi + "\"}", "UTF-8");
		// } catch (UnsupportedEncodingException e1) {
		// e1.printStackTrace();
		// }
		//
		// JsonObjectRequest loginRequest = new
		// JsonObjectRequest(loginRequestString, null,
		// new Response.Listener<JSONObject>() {
		//
		// @Override
		// public void onResponse(JSONObject response) {
		//
		// try {
		// if (response.getString("success").equals("0")) {
		// // 登录验证成功,保存用户ID
		// String id = response.getString("id");
		// Log.i("LoginRequest", response.toString());
		// // 通知界面
		// MessageHandlerManager.getInstance().sendMessage(
		// Constant.LOGIN_REQUEST_SUCCESS, id, "Login");
		// } else if (response.getString("success").equals("1")) {
		// // 登录验证失败
		// MessageHandlerManager.getInstance().sendMessage(
		// Constant.LOGIN_REQUEST_FAIL, "Login");
		// }
		// } catch (JSONException e) {
		// e.printStackTrace();
		// }
		// }
		// }, new Response.ErrorListener() {
		//
		// @Override
		// public void onErrorResponse(VolleyError error) {
		// for (int i = 0; i < error.getStackTrace().length; i++) {
		// Log.i("LoginRequest", error.getStackTrace()[i].toString());
		// }
		// }
		// });
		//
		// return loginRequest;

	}

	/**
	 * 用户退出 FINAL Jerry 15.5.21
	 */
	public JsonObjectRequest logOut(Context c) {
		if (getUserId(c) == null || getUserIc(c) == null)
			return null;
		LogoutRequest params = new LogoutRequest(getUserId(c), getUserIc(c));
		this.url = Contants.SERVER_URL + Contants.MODEL_NAME + Contants.METHOD_PERSON_LOGOUT
				+ Contants.PARAM_NAME + super.gson.toJson(params);
		System.out.println(this.url);
		return new JsonObjectRequest(this.url, null, new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject arg0) {
				try {
					if (arg0.getString("s").equals(Contants.RESULT_SUCCESS)) {
						LogoutResponse r = gson.fromJson(arg0.toString(), LogoutResponse.class);
						MessageHandlerManager.getInstance().sendMessage(Constant.LOGOUT_REQUEST_SUCCESS,
								r, Contants.METHOD_PERSON_LOGOUT);
					} else {
						NormalServerResponse r = gson.fromJson(arg0.toString(),
								NormalServerResponse.class);
						// 返回错误代码
						MessageHandlerManager.getInstance().sendMessage(Constant.LOGOUT_REQUEST_FAIL, r,
								Contants.METHOD_PERSON_LOGOUT);
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
	 * 根据用户id查询用户信息 FINAL Jerry 15.5.22
	 * 
	 * @param c
	 *            activity上下文
	 * @param identifyCode
	 *            密码
	 * @param personId
	 *            需要查询的用户id
	 * @return
	 */
	public JsonObjectRequest getPersonInfo(final Context c, String personId) {
		if (getUserId(c) == null || getUserIc(c) == null)
			return null;
		GetPersonInfoRequest params = new GetPersonInfoRequest(getUserId(c), getUserIc(c), personId);
		this.url = Contants.SERVER_URL + Contants.MODEL_NAME + Contants.METHOD_PERSON_GET_PERSON_INFO
				+ Contants.PARAM_NAME + super.gson.toJson(params);
		System.out.println(this.url);
		return new JsonObjectRequest(this.url, null, new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject arg0) {
				try {
					if (arg0.getString("s").equals(Contants.RESULT_SUCCESS)) {
						GetPersonInfoResponse r = gson.fromJson(arg0.toString(),
								GetPersonInfoResponse.class);
						// 保存数据库
						new PersonDao(c).saveCustomer(r);
						// 将接收到的对象发送到ui线程
						MessageHandlerManager.getInstance().sendMessage(
								Constant.QUERY_PERSON_INFO_REQUEST_SUCCESS, r,
								Contants.METHOD_PERSON_GET_PERSON_INFO);
					} else {
						NormalServerResponse r = gson.fromJson(arg0.toString(),
								NormalServerResponse.class);
						// 返回错误代码
						MessageHandlerManager.getInstance().sendMessage(
								Constant.QUERY_PERSON_INFO_REQUEST_FAIL, r,
								Contants.METHOD_PERSON_GET_PERSON_INFO);
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
	 * 添加用户联系方式 FINAL Jerry 15.5.22
	 * <p>
	 * TIP：ts与cs的两个数组需要一一对应
	 * 
	 * @param c
	 * @param identifyCode
	 *            密码
	 * @param personId
	 *            需要将联系方式添加到用户的id
	 * @param ts
	 *            联系方式类型(1：手机号2：座机号3：SIM号4：手台号码5：邮箱)
	 * @param cs
	 *            联系方式内容
	 * @return
	 */
	public JsonObjectRequest addPersonContact(Context c, String identifyCode, String personId,
			String[] ts, String[] cs) {
		// 如果联系方式数组不能匹配，直接返回null
		if (ts.length != cs.length)
			return null;
		List<Contacts> contacts = new ArrayList<Contacts>();
		for (int i = 0; i < cs.length; i++) {
			contacts.add(new Contacts(ts[i], cs[i]));
		}
		if (getUserId(c) == null)
			return null;
		AddPersonContactRequest params = new AddPersonContactRequest(getUserId(c), identifyCode,
				personId, contacts);
		this.url = Contants.SERVER_URL + Contants.MODEL_NAME
				+ Contants.METHOD_PERSON_ADD_PERSON_CONTACTS + Contants.PARAM_NAME
				+ super.gson.toJson(params);
		System.out.println(this.url);
		return new JsonObjectRequest(this.url, null, new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject arg0) {
				try {
					if (arg0.getString("s").equals(Contants.RESULT_SUCCESS)) {
						AddPersonContactResponse r = gson.fromJson(arg0.toString(),
								AddPersonContactResponse.class);
						MessageHandlerManager.getInstance().sendMessage(
								Constant.ADD_PERSON_CONTACT_REQUEST_SUCCESS, r,
								Contants.METHOD_PERSON_ADD_PERSON_CONTACTS);
					} else {
						NormalServerResponse r = gson.fromJson(arg0.toString(),
								NormalServerResponse.class);
						// 返回错误信息
						MessageHandlerManager.getInstance().sendMessage(
								Constant.ADD_PERSON_CONTACT_REQUEST_FAIL, r,
								Contants.METHOD_PERSON_ADD_PERSON_CONTACTS);
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
	// * 登录验证时的构造函数 FINAL Jerry 15.5.21
	// *
	// * @param context
	// * @param aliasName
	// * @param identifyCode
	// * @param imsi
	// * @param noMean
	// */
	// public PersonRequest(Context context, String aliasName, String
	// identifyCode, String imsi,
	// String noMean) {
	// this.context = context;
	// this.aliasName = aliasName;
	// this.identifyCode = identifyCode;
	// this.imsi = imsi;
	// }

	// /**
	// * // * 修改用户信息的构造函数 FINAL Jerry 15.5.21 // *
	// * <p>
	// * // * (修改用户密码 deprecated) // * // * @param context // * @param personID
	// //
	// * * @param identifyCode // * @param newIdentifyCode // * //
	// */
	// public PersonRequest(Context context, String personID, String
	// identifyCode, String newIdentifyCode) {
	// this.context = context;
	// this.personID = personID;
	// this.identifyCode = identifyCode;
	// this.newIdentifyCode = newIdentifyCode;
	// }

	/**
	 * 修改用户密码 FINAL Jerry 15.5.21
	 * 
	 * 废弃接口
	 * 
	 * @deprecated
	 */
	public JsonObjectRequest getChangePasswordrRequest(Context c, String identifyCode,
			String newIdentifyCode) {
		// String personID = MySharedPreference.get(c,
		// MySharedPreference.USER_ID, "100002");
		// changePasswordRequestString = Constant.SERVER_BASE_URL +
		// Constant.CHANGE_PASSWORD_URL
		// + "?param={\"cid\":\"" + personID + "\",\"op\":\"" + identifyCode +
		// "\",\"np\":\""
		// + newIdentifyCode + "\"}";
		// Log.v("ChangePasswordRequest", changePasswordRequestString);
		// JsonObjectRequest changePasswordRequest = new
		// JsonObjectRequest(changePasswordRequestString,
		// null, new Response.Listener<JSONObject>() {
		//
		// @Override
		// public void onResponse(JSONObject response) {
		// try {
		// if (response.getString("success").equals("0")) {
		// // 修改密码成功
		// Log.i("changePasswordRequest", response.toString());
		// // 通知界面
		// MessageHandlerManager.getInstance().sendMessage(
		// Constant.CHANGE_PASSWORD_REQUEST_SUCCESS, "Profile");
		// } else if (response.getString("success").equals("1")) {
		// // 登录验证失败
		// MessageHandlerManager.getInstance().sendMessage(
		// Constant.CHANGE_PASSWORD_REQUEST_FAIL, "Profile");
		// }
		// } catch (JSONException e) {
		// e.printStackTrace();
		// }
		// }
		// }, new Response.ErrorListener() {
		//
		// @Override
		// public void onErrorResponse(VolleyError error) {
		//
		// }
		// });
		// return changePasswordRequest;
		if (getUserId(c) == null)
			return null;
		ChangePwdRequest changePwd = new ChangePwdRequest(getUserId(c), newIdentifyCode);
		this.url = Contants.SERVER_URL + Contants.MODEL_NAME + Contants.METHOD_PERSON_CHANGEPWD
				+ Contants.PARAM_NAME + gson.toJson(changePwd);
		return new JsonObjectRequest(this.url, null, new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject arg0) {
				ChangePwdResponse r = gson.fromJson(arg0.toString(), ChangePwdResponse.class);
				if (r.getS().equals(Contants.RESULT_SUCCESS)) {
					// 通知界面
					MessageHandlerManager.getInstance().sendMessage(
							Constant.CHANGE_PASSWORD_REQUEST_SUCCESS, "Profile");
				} else if (r.getS().equals(Contants.RESULT_FAIL)) {
					MessageHandlerManager.getInstance().sendMessage(
							Constant.CHANGE_PASSWORD_REQUEST_FAIL, "Profile");
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
