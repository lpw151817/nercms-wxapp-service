package android.wxapp.service.request;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;
import android.wxapp.service.dao.DAOFactory;
import android.wxapp.service.dao.PersonDao;
import android.wxapp.service.handler.MessageHandlerManager;
import android.wxapp.service.jerry.model.normal.NormalServerResponse;
import android.wxapp.service.jerry.model.person.ChangePwdRequest;
import android.wxapp.service.jerry.model.person.ChangePwdResponse;
import android.wxapp.service.jerry.model.person.GetPersonInfoRequest;
import android.wxapp.service.jerry.model.person.GetPersonInfoResponse;
import android.wxapp.service.jerry.model.person.LoginRequest;
import android.wxapp.service.jerry.model.person.LoginResponse;
import android.wxapp.service.jerry.model.person.LogoutRequest;
import android.wxapp.service.jerry.model.person.ModifyCustomerRequest;
import android.wxapp.service.jerry.model.person.ModifyCustomerResponse;
import android.wxapp.service.model.CustomerContactModel;
import android.wxapp.service.model.CustomerModel;
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

public class PersonRequest extends BaseRequest {

	private Context context;
	/**
	 * // �û�ID
	 */
	private String personID;
	/**
	 * // �û���¼��
	 */
	private String aliasName;
	/**
	 * // �û�����
	 */
	private String identifyCode;
	/**
	 * // IMSI��
	 */
	private String imsi;

	/**
	 * // ���޸ĵ��û�������
	 */
	private String newIdentifyCode;

	/**
	 * // ���½�����༭���Ŀͻ�ģ��
	 */
	private CustomerModel customer;
	/**
	 * // ���½�����༭���Ŀͻ�����ϵ��ʽ�б�
	 */
	private ArrayList<CustomerContactModel> customerContactList = null;

	/**
	 * // ��ɾ���ͻ���ID
	 */
	private String customerID;

	/**
	 * // �½��ͻ������ַ���
	 */
	private String newCustomerRequestString = null;
	/**
	 * // ɾ���ͻ������ַ���
	 */
	private String deleteCustomerRequestString = null;
	/**
	 * // �û���¼��֤�����ַ���
	 */
	private String loginRequestString = null;
	/**
	 * // ��ȡ������ϵ�������ַ���
	 */
	private String getAllPersonRequestString = null;
	/**
	 * // �޸��û����������ַ���
	 */
	private String changePasswordRequestString = null;
	/**
	 * // �༭�ͻ���Ϣ�����ַ���
	 */
	private String modifyCustomerRequeString = null;

	// �༭�ͻ���Ϣ�Ĺ��캯��
	/**
	 * �½�����༭���ͻ�ʱ�Ĺ��캯��
	 * <p>
	 * ˵�����½��ͻ�ʱ���������Customerģ���У�customerID��Ϊ����int��ֵ��contactIDΪ��������ʵ��
	 * <p>
	 * ����������ID�󣬱���setCustomerID��ſɿ�ʼ���浽����;
	 * <p>
	 * �༭�ͻ���Ϣʱ���������Customerģ���У�customerID��contactIDΪ��������ʵ;
	 * <p>
	 * �κ�ʱ���������CustomerContactList����Ϊnull����ʾ�ÿͻ�����ϵ��ʽ
	 * 
	 * @param context
	 * @param customer
	 * @param customerContactList
	 */

	public PersonRequest() {

	}

	public PersonRequest(Context context, CustomerModel customer,
			ArrayList<CustomerContactModel> customerContactList) {
		this.context = context;
		this.customer = customer;
		this.customerContactList = customerContactList;
	}

	/**
	 * �õ��½��ͻ���Request
	 * 
	 * @return
	 */
	public JsonObjectRequest sendNewCustomerRequest() {
		/* �������� */
		// ��������JSON����������ַ
		StringBuilder requestParams = new StringBuilder();
		requestParams.append("{\"name\":\"" + customer.getName() + "\",");
		requestParams.append("\"unit\":\"" + customer.getUnit() + "\",");
		requestParams.append("\"dpt\":\"" + customer.getDescription() + "\",");
		requestParams.append("\"coid\":\"" + customer.getContactID() + "\",");

		if (customerContactList != null && customerContactList.size() != 0) {
			requestParams.append("\"cots\":[");
			for (int i = 0; i < customerContactList.size(); i++) {
				requestParams.append("{\"type\":\"" + customerContactList.get(i).getType() + "\",");
				requestParams.append("\"cot\":\"" + customerContactList.get(i).getContent() + "\"}");
				if (i < customerContactList.size() - 1) {
					requestParams.append(",");
				}
			}
			requestParams.append("]}");
		} else { // ����ϵ��ʽ��Ϣ
			requestParams.append("\"cots\":null}");
		}

		try {
			newCustomerRequestString = Constant.SERVER_BASE_URL + Constant.CREATE_CUSTOMER_URL
					+ "?param=" + URLEncoder.encode(requestParams.toString(), "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		JsonObjectRequest newCustomerRequest = new JsonObjectRequest(newCustomerRequestString, null,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						// ���ͳɹ����жϷ������Ƿ񷵻سɹ�����֪ͨHandler������Ϣ
						Log.i("newCustomerRequest", response.toString());

						try {
							if (response.getString("success").equals("0")) {
								// ����������ɹ�
								String customerID = response.getString("csid");
								// ֪ͨ����
								MessageHandlerManager.getInstance().sendMessage(
										Constant.CREATE_CUSTOMER_REQUEST_SUCCESS, customerID,
										"ContactAdd");
								// 0721���֪ͨcontactFragment ˢ����֯�ṹ��
								MessageHandlerManager.getInstance().sendMessage(
										Constant.CREATE_CUSTOMER_REQUEST_SUCCESS, customerID, "Main");

							} else if (response.getString("success").equals("1")) {
								Log.i("newCustomerRequest", response.getString("reason").toString());
								MessageHandlerManager.getInstance().sendMessage(
										Constant.CREATE_CUSTOMER_REQUEST_FAIL, "ContactAdd");
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						// ����ʧ�ܣ�֪ͨHandler������Ϣ

					}
				});

		return newCustomerRequest;
	}

	/**
	 * ɾ���ͻ�ʱ�Ĺ��캯��
	 */
	public PersonRequest(Context context, String customerID) {
		this.context = context;
		this.customerID = customerID;
	}

	/**
	 * �õ�ɾ���ͻ���Request
	 * 
	 * @return
	 */
	public JsonObjectRequest sendDeleteCustomerRequest() {
		deleteCustomerRequestString = Constant.SERVER_BASE_URL + Constant.DELETE_CUSTOMER_URL
				+ "?param={\"csid\":\"" + customerID + "\"}";
		JsonObjectRequest deleteCustomerRequest = new JsonObjectRequest(deleteCustomerRequestString,
				null, new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						try {
							if (response.getString("success").equalsIgnoreCase("0")) {
								Log.i("deleteCustomerRequest", response.toString());
								Log.v("deleteCustomerRequest", "�ͻ��ѱ��ɾ��");

								// �������˱�־ɾ����ɣ���һ���ֻ������ݿ�ɾ���ÿͻ���Ϣ
								PersonDao dao = DAOFactory.getInstance().getPersonDao(context);
								dao.deleteCustomer(customerID);
								// 0722 ֪ͨUI
								MessageHandlerManager.getInstance().sendMessage(
										Constant.DELETE_CUSTOMER_REQUEST_SUCCESS, customerID, "Main");

							} else {

							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Log.e("deleteCustomerRequest", error.toString());
					}
				});

		return deleteCustomerRequest;
	}

	public JsonObjectRequest sendModifyCustomerRequest() {
		// ��������JSON����������ַ
		StringBuilder requestParams = new StringBuilder();
		requestParams.append("{\"csid\":\"" + customer.getCustomerID() + "\",");
		requestParams.append("\"name\":\"" + customer.getName() + "\",");
		requestParams.append("\"unit\":\"" + customer.getUnit() + "\",");
		requestParams.append("\"dpt\":\"" + customer.getDescription() + "\",");

		if (customerContactList != null && customerContactList.size() != 0) {
			requestParams.append("\"cots\":[");
			for (int i = 0; i < customerContactList.size(); i++) {
				requestParams.append("{\"type\":\"" + customerContactList.get(i).getType() + "\",");
				requestParams.append("\"cot\":\"" + customerContactList.get(i).getContent() + "\"}");
				if (i < customerContactList.size() - 1) {
					requestParams.append(",");
				}
			}
			requestParams.append("]}");
		} else { // ����ϵ��ʽ��Ϣ
			requestParams.append("\"cots\":null}");
		}
		try {
			modifyCustomerRequeString = Constant.SERVER_BASE_URL + Constant.MODIFY_CUSTOMER_URL
					+ "?param=" + URLEncoder.encode(requestParams.toString(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JsonObjectRequest modifyCustomerRequest = new JsonObjectRequest(modifyCustomerRequeString, null,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						// ���ͳɹ����жϷ������Ƿ񷵻سɹ�����֪ͨHandler������Ϣ
						Log.i("modifyCustomerRequest", response.toString());

						try {
							if (response.getString("success").equals("0")) {

								// ����������ɹ�
								MessageHandlerManager.getInstance().sendMessage(
										Constant.MODIFY_CUSTOMER_REQUEST_SUCCESS, "ContactAdd");
								// 0721 ֪ͨUI ˢ����֯�ṹ��
								MessageHandlerManager.getInstance().sendMessage(
										Constant.MODIFY_CUSTOMER_REQUEST_SUCCESS, "Main");

							} else if (response.getString("success").equals("1")) {
								Log.i("newCustomerRequest", response.getString("reason").toString());
								MessageHandlerManager.getInstance().sendMessage(
										Constant.MODIFY_CUSTOMER_REQUEST_FAIL, "ContactAdd");
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

		return modifyCustomerRequest;
	}

	// /**
	// * ��ȡ������ϵ��ʱ�Ĺ��캯��
	// *
	// * @param context
	// */
	// public PersonRequest(Context context, String pid) {
	// this.context = context;
	// this.customerID = pid;
	// }

	/**
	 * �õ���ȡ������ϵ�˵�Request
	 * 
	 * @return
	 */
	public JsonObjectRequest getAllPersonRequest() {

		personID = MySharedPreference.get(context, MySharedPreference.USER_ID, "100002");
		getAllPersonRequestString = Constant.SERVER_BASE_URL + Constant.GET_ALL_PERSON_URL
				+ "?param={\"mid\":\"" + personID + "\"}";
		JsonObjectRequest getAllPersonRequest = new JsonObjectRequest(getAllPersonRequestString, null,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						// ���ͳɹ����жϷ������Ƿ񷵻سɹ�����֪ͨHandler������Ϣ
						Log.i("getAllPersonsInfo", "������ϵ�˳ɹ���ת�������߳�");
						ThreadManager.getInstance().startSaveAllPersonThread(response, context);
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						// TODO Auto-generated method stub

					}
				});

		return getAllPersonRequest;
	}

	public PersonRequest(Context c) {
		this.context = c;
	}

	/**
	 * �����û�id��ѯ�û���Ϣ FINAL Jerry 15.5.22
	 * 
	 * @param c
	 *            activity������
	 * @param identifyCode
	 *            ����
	 * @param personId
	 *            ��Ҫ��ѯ���û�id
	 * @return
	 */
	public JsonObjectRequest getPersonInfo(Context c, String identifyCode, String personId) {
		// ��ȡ�û�id
		String userId = MySharedPreference.get(c, MySharedPreference.USER_ID, "100002");
		GetPersonInfoRequest params = new GetPersonInfoRequest(userId, identifyCode, personId);
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
						// �����յ��Ķ����͵�ui�߳�
						MessageHandlerManager.getInstance().sendMessage(
								Constant.QUERY_PERSON_INFO_REQUEST_SUCCESS, r,
								Contants.METHOD_PERSON_LOGOUT);
					} else {
						NormalServerResponse r = gson.fromJson(arg0.toString(),
								NormalServerResponse.class);
						// ���ش������
						MessageHandlerManager.getInstance().sendMessage(
								Constant.QUERY_PERSON_INFO_REQUEST_FAIL, r.getEc(),
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
	 * �û��˳� FINAL Jerry 15.5.21
	 */
	public JsonObjectRequest logOut(Context c, String identifyCode) {
		String personID = MySharedPreference.get(c, MySharedPreference.USER_ID, "100002");
		LogoutRequest params = new LogoutRequest(personID, identifyCode);
		this.url = Contants.SERVER_URL + Contants.MODEL_NAME + Contants.METHOD_PERSON_LOGOUT
				+ Contants.PARAM_NAME + super.gson.toJson(params);
		System.out.println(this.url);
		return new JsonObjectRequest(this.url, null, new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject arg0) {
				try {
					if (arg0.getString("s").equals(Contants.RESULT_SUCCESS)) {
						MessageHandlerManager.getInstance().sendMessage(Constant.LOGOUT_REQUEST_SUCCESS,
								Contants.METHOD_PERSON_LOGOUT);
					} else {
						NormalServerResponse r = gson.fromJson(arg0.toString(),
								NormalServerResponse.class);
						// ���ش������
						MessageHandlerManager.getInstance().sendMessage(Constant.LOGOUT_REQUEST_FAIL,
								r.getEc(), Contants.METHOD_PERSON_LOGOUT);
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
	 * �޸��û���Ϣ FINAL Jerry 15.5.21
	 */
	public JsonObjectRequest modifyCustomerInfo(Context c, String identifyCode, String aliasName) {
		String personID = MySharedPreference.get(c, MySharedPreference.USER_ID, "100002");
		ModifyCustomerRequest params = new ModifyCustomerRequest(personID, identifyCode, aliasName);
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
								Constant.MODIFY_USERINFO_REQUEST_SUCCESS,
								Contants.METHOD_PERSON_MODIFYUSERINFO);
					} else {
						NormalServerResponse r = gson.fromJson(arg0.toString(),
								NormalServerResponse.class);
						// ���ش������
						MessageHandlerManager.getInstance().sendMessage(
								Constant.MODIFY_USERINFO_REQUEST_FAIL, r.getEc(),
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

	// /**
	// * ��¼��֤ʱ�Ĺ��캯�� FINAL Jerry 15.5.21
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

	/**
	 * �û���¼ FINAL Jerry 15.5.21
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
						// �����û�id
						MessageHandlerManager.getInstance().sendMessage(Constant.LOGIN_REQUEST_SUCCESS,
								r.getUid(), Contants.METHOD_PERSON_LOGIN);
					} else {
						NormalServerResponse r = gson.fromJson(arg0.toString(),
								NormalServerResponse.class);
						// ���ش������
						MessageHandlerManager.getInstance().sendMessage(Constant.LOGIN_REQUEST_FAIL,
								r.getEc(), Contants.METHOD_PERSON_LOGIN);
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
		// // TODO Auto-generated catch block
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
		// // ��¼��֤�ɹ�,�����û�ID
		// String id = response.getString("id");
		// Log.i("LoginRequest", response.toString());
		// // ֪ͨ����
		// MessageHandlerManager.getInstance().sendMessage(
		// Constant.LOGIN_REQUEST_SUCCESS, id, "Login");
		// } else if (response.getString("success").equals("1")) {
		// // ��¼��֤ʧ��
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

	// /**
	// * // * �޸��û���Ϣ�Ĺ��캯�� FINAL Jerry 15.5.21 // *
	// * <p>
	// * // * (�޸��û����� deprecated) // * // * @param context // * @param personID
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
	 * �޸��û����� FINAL Jerry 15.5.21
	 * 
	 * �����ӿ�
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
		// // �޸�����ɹ�
		// Log.i("changePasswordRequest", response.toString());
		// // ֪ͨ����
		// MessageHandlerManager.getInstance().sendMessage(
		// Constant.CHANGE_PASSWORD_REQUEST_SUCCESS, "Profile");
		// } else if (response.getString("success").equals("1")) {
		// // ��¼��֤ʧ��
		// MessageHandlerManager.getInstance().sendMessage(
		// Constant.CHANGE_PASSWORD_REQUEST_FAIL, "Profile");
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
		// // TODO Auto-generated method stub
		//
		// }
		// });
		// return changePasswordRequest;

		ChangePwdRequest changePwd = new ChangePwdRequest(personID, newIdentifyCode);
		this.url = Contants.SERVER_URL + Contants.MODEL_NAME + Contants.METHOD_PERSON_CHANGEPWD
				+ Contants.PARAM_NAME + gson.toJson(changePwd);
		return new JsonObjectRequest(this.url, null, new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject arg0) {
				ChangePwdResponse r = gson.fromJson(arg0.toString(), ChangePwdResponse.class);
				if (r.getS().equals(Contants.RESULT_SUCCESS)) {
					// ֪ͨ����
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
