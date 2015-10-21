package android.wxapp.service.request;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
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
 * ����ĵ���Personģ������нӿ� ������
 * 
 * @author JerryLiu
 *
 */
public class PersonRequest extends BaseRequest {

	/**
	 * �õ��½��ͻ���Request
	 * 
	 * @param context
	 * @param customer
	 *            �༭�ͻ���Ϣʱ���������Customerģ���У�customerID��contactIDΪ��������ʵ;
	 * @param customerContactList
	 *            �κ�ʱ���������CustomerContactList����Ϊnull����ʾ�ÿͻ�����ϵ��ʽ
	 * @return
	 */
	@Deprecated
	public JsonObjectRequest sendNewCustomerRequest(Context context, CustomerModel customer,
			ArrayList<CustomerContactModel> customerContactList) {
		return null;
		// /* �������� */
		// // ��������JSON����������ַ
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
		// } else { // ����ϵ��ʽ��Ϣ
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
		// // ���ͳɹ����жϷ������Ƿ񷵻سɹ�����֪ͨHandler������Ϣ
		// Log.i("newCustomerRequest", response.toString());
		//
		// try {
		// if (response.getString("success").equals("0")) {
		// // ����������ɹ�
		// String customerID = response.getString("csid");
		// // ֪ͨ����
		// MessageHandlerManager.getInstance().sendMessage(
		// Constant.CREATE_CUSTOMER_REQUEST_SUCCESS, customerID,
		// "ContactAdd");
		// // 0721���֪ͨcontactFragment ˢ����֯�ṹ��
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
		// // ����ʧ�ܣ�֪ͨHandler������Ϣ
		//
		// }
		// });
		//
		// return newCustomerRequest;
	}

	// /**
	// * ɾ���ͻ�ʱ�Ĺ��캯��
	// */
	// public PersonRequest(Context context, String customerID) {
	// this.context = context;
	// this.customerID = customerID;
	// }

	/**
	 * ɾ���ͻ�
	 * 
	 * @param context
	 * @param customerID
	 *            ��ɾ���ͻ���ID
	 * @return
	 */
	@Deprecated
	public JsonObjectRequest sendDeleteCustomerRequest(final Context context,
			final String customerID) {
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
		// Log.v("deleteCustomerRequest", "�ͻ��ѱ��ɾ��");
		//
		// // �������˱�־ɾ����ɣ���һ���ֻ������ݿ�ɾ���ÿͻ���Ϣ
		// PersonDao dao = DAOFactory.getInstance().getPersonDao(context);
		// dao.deleteCustomer(customerID);
		// // 0722 ֪ͨUI
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
	 * �޸Ŀͻ���Ϣ
	 * 
	 * @param customer
	 *            ���༭�Ŀͻ�ģ��
	 * @param customerContactList
	 *            ���༭�Ŀͻ�����ϵ��ʽ�б�
	 * @return
	 */
	@Deprecated
	public JsonObjectRequest sendModifyCustomerRequest(CustomerModel customer,
			ArrayList<CustomerContactModel> customerContactList) {
		return null;
		// // ��������JSON����������ַ
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
		// } else { // ����ϵ��ʽ��Ϣ
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
		// // ���ͳɹ����жϷ������Ƿ񷵻سɹ�����֪ͨHandler������Ϣ
		// Log.i("modifyCustomerRequest", response.toString());
		//
		// try {
		// if (response.getString("success").equals("0")) {
		//
		// // ����������ɹ�
		// MessageHandlerManager.getInstance().sendMessage(
		// Constant.MODIFY_CUSTOMER_REQUEST_SUCCESS, "ContactAdd");
		// // 0721 ֪ͨUI ˢ����֯�ṹ��
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
	 * �õ���ȡ������ϵ�˵�Request
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
		// // ���ͳɹ����жϷ������Ƿ񷵻سɹ�����֪ͨHandler������Ϣ
		// Log.i("getAllPersonsInfo", "������ϵ�˳ɹ���ת�������߳�");
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
	 * �޸��û���Ϣ FINAL Jerry 15.5.21
	 */
	public JsonObjectRequest modifyCustomerInfo(Context c, String identifyCode, String aliasName) {
		if (getUserId(c) == null)
			return null;
		ModifyCustomerRequest params = new ModifyCustomerRequest(getUserId(c), identifyCode,
				aliasName);
		this.url = Contants.SERVER_URL + Contants.MODEL_NAME + Contants.METHOD_PERSON_MODIFYUSERINFO
				+ Contants.PARAM_NAME + parase2Json(params);
		Log.e("URL", this.url);
		return new JsonObjectRequest(this.url, null, new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject arg0) {
				Log.e("Response", arg0.toString());
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
						// ���ش������
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
	 * ��ѯ��֯�ṹ���еĽ�� FINAL Jerry 15.5.22
	 * 
	 * @param c
	 * @return
	 */
	public JsonObjectRequest getOrgCodeUpdate(final Context c) {
		if (getUserId(c) == null || getUserIc(c) == null)
			return null;
		String orgCodeUptateTime = getLastOrgUpdateTime(c);
		if (orgCodeUptateTime == null)
			// orgCodeUptateTime = System.currentTimeMillis() + "";
			orgCodeUptateTime = "";
		GetOrgCodeRequest params = new GetOrgCodeRequest(getUserId(c), getUserIc(c),
				orgCodeUptateTime);
		this.url = Contants.SERVER_URL + Contants.MODEL_NAME + Contants.METHOD_PERSON_GET_ORG_CODE
				+ Contants.PARAM_NAME + parase2Json(params);
		Log.e("URL", this.url);
		return new JsonObjectRequest(this.url, null, new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject arg0) {
				Log.e("Response", arg0.toString());
				try {
					if (arg0.getString("s").equals(Contants.RESULT_SUCCESS)) {
						GetOrgCodeResponse r = gson.fromJson(arg0.toString(),
								GetOrgCodeResponse.class);
						// �������ݿ�����,������ʱ���������handler��ui�߳̽��в���
						new SaveOrgCodeThread(c, r).run();
					} else {
						NormalServerResponse r = gson.fromJson(arg0.toString(),
								NormalServerResponse.class);
						// ���ش������
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
	 * ��ѯ��Ӧ��֯����µ���Ա FINAL Jerry 15.5.22
	 * 
	 * @param c
	 * @return
	 */
	public JsonObjectRequest getOrgCodePersonUpdate(final Context c) {
		if (getUserId(c) == null || getUserIc(c) == null)
			return null;
		String orgPersonUptateTime = getLastOrgPersonUpdateTime(c);
		if (orgPersonUptateTime == null)
			// orgPersonUptateTime = System.currentTimeMillis() + "";
			orgPersonUptateTime = "";
		GetOrgCodePersonRequest params = new GetOrgCodePersonRequest(getUserId(c), getUserIc(c),
				orgPersonUptateTime);
		this.url = Contants.SERVER_URL + Contants.MODEL_NAME + Contants.METHOD_PERSON_GET_ORG_PERSON
				+ Contants.PARAM_NAME + parase2Json(params);
		Log.e("URL", this.url);
		return new JsonObjectRequest(this.url, null, new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject arg0) {
				Log.e("Response", arg0.toString());
				try {
					if (arg0.getString("s").equals(Contants.RESULT_SUCCESS)) {
						GetOrgCodePersonResponse r = gson.fromJson(arg0.toString(),
								GetOrgCodePersonResponse.class);
						// �������ݿ�����,������handler��ui�߳̽��в���
						new SaveOrgCodePersonThread(c, r).run();

						// MessageHandlerManager.getInstance().sendMessage(
						// Constant.QUERY_ORG_PERSON_REQUEST_SUCCESS, r,
						// Contants.METHOD_PERSON_GET_ORG_PERSON);
					} else {
						NormalServerResponse r = gson.fromJson(arg0.toString(),
								NormalServerResponse.class);
						// ���ش������
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
	 * �û���¼ FINAL Jerry 15.5.21
	 */
	public JsonObjectRequest getLoginRequest(String aliasName, String identifyCode, String imsi) {
		LoginRequest lr = new LoginRequest(aliasName, identifyCode, imsi);
		this.url = Contants.SERVER_URL + Contants.MODEL_NAME + Contants.METHOD_PERSON_LOGIN
				+ Contants.PARAM_NAME + super.gson.toJson(lr);
		Log.e("URL", this.url);
		return new JsonObjectRequest(this.url, null, new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject arg0) {
				Log.e("Response", arg0.toString());
				try {
					if (arg0.getString("s").equals(Contants.RESULT_SUCCESS)) {
						LoginResponse r = gson.fromJson(arg0.toString(), LoginResponse.class);
						// �����û�id
						MessageHandlerManager.getInstance().sendMessage(
								Constant.LOGIN_REQUEST_SUCCESS, r, Contants.METHOD_PERSON_LOGIN);
					} else {
						NormalServerResponse r = gson.fromJson(arg0.toString(),
								NormalServerResponse.class);
						// ���ش������
						MessageHandlerManager.getInstance().sendMessage(Constant.LOGIN_REQUEST_FAIL,
								r, Contants.METHOD_PERSON_LOGIN);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError arg0) {
				arg0.printStackTrace();
				MessageHandlerManager.getInstance().sendMessage(Constant.LOGIN_REQUEST_FAIL,
						Contants.METHOD_PERSON_LOGIN);
			}
		});

	}

	/**
	 * �û��˳� FINAL Jerry 15.5.21
	 */
	public JsonObjectRequest logOut(Context c) {
		if (getUserId(c) == null || getUserIc(c) == null)
			return null;
		LogoutRequest params = new LogoutRequest(getUserId(c), getUserIc(c));
		this.url = Contants.SERVER_URL + Contants.MODEL_NAME + Contants.METHOD_PERSON_LOGOUT
				+ Contants.PARAM_NAME + parase2Json(params);
		Log.e("URL", this.url);
		return new JsonObjectRequest(this.url, null, new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject arg0) {
				Log.e("Response", arg0.toString());
				try {
					if (arg0.getString("s").equals(Contants.RESULT_SUCCESS)) {
						LogoutResponse r = gson.fromJson(arg0.toString(), LogoutResponse.class);
						MessageHandlerManager.getInstance().sendMessage(
								Constant.LOGOUT_REQUEST_SUCCESS, r, Contants.METHOD_PERSON_LOGOUT);
					} else {
						NormalServerResponse r = gson.fromJson(arg0.toString(),
								NormalServerResponse.class);
						// ���ش������
						MessageHandlerManager.getInstance().sendMessage(
								Constant.LOGOUT_REQUEST_FAIL, r, Contants.METHOD_PERSON_LOGOUT);
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
	public JsonObjectRequest getPersonInfo(final Context c, String personId) {
		if (getUserId(c) == null || getUserIc(c) == null)
			return null;
		GetPersonInfoRequest params = new GetPersonInfoRequest(getUserId(c), getUserIc(c),
				personId);
		this.url = Contants.SERVER_URL + Contants.MODEL_NAME
				+ Contants.METHOD_PERSON_GET_PERSON_INFO + Contants.PARAM_NAME
				+ parase2Json(params);
		Log.e("URL", this.url);
		return new JsonObjectRequest(this.url, null, new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject arg0) {
				Log.e("Response", arg0.toString());
				try {
					if (arg0.getString("s").equals(Contants.RESULT_SUCCESS)) {
						GetPersonInfoResponse r = gson.fromJson(arg0.toString(),
								GetPersonInfoResponse.class);
						// �������ݿ�
						new PersonDao(c).saveCustomer(r);
						// �����յ��Ķ����͵�ui�߳�
						MessageHandlerManager.getInstance().sendMessage(
								Constant.QUERY_PERSON_INFO_REQUEST_SUCCESS, r,
								Contants.METHOD_PERSON_GET_PERSON_INFO);
					} else {
						NormalServerResponse r = gson.fromJson(arg0.toString(),
								NormalServerResponse.class);
						// ���ش������
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
	 * ����û���ϵ��ʽ FINAL Jerry 15.5.22
	 * <p>
	 * TIP��ts��cs������������Ҫһһ��Ӧ
	 * 
	 * @param c
	 * @param identifyCode
	 *            ����
	 * @param personId
	 *            ��Ҫ����ϵ��ʽ��ӵ��û���id
	 * @param ts
	 *            ��ϵ��ʽ����(1���ֻ���2��������3��SIM��4����̨����5������)
	 * @param cs
	 *            ��ϵ��ʽ����
	 * @return
	 */
	public JsonObjectRequest addPersonContact(Context c, String identifyCode, String personId,
			String[] ts, String[] cs) {
		// �����ϵ��ʽ���鲻��ƥ�䣬ֱ�ӷ���null
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
				+ parase2Json(params);
		Log.e("URL", this.url);
		return new JsonObjectRequest(this.url, null, new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject arg0) {
				Log.e("Response", arg0.toString());
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
						// ���ش�����Ϣ
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
		Log.e("URL", url);
		return new JsonObjectRequest(this.url, null, new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject arg0) {
				Log.e("Response", arg0.toString());
				ChangePwdResponse r = gson.fromJson(arg0.toString(), ChangePwdResponse.class);
				if (r.getS().equals(Contants.RESULT_SUCCESS)) {
					// ֪ͨ����
					MessageHandlerManager.getInstance()
							.sendMessage(Constant.CHANGE_PASSWORD_REQUEST_SUCCESS, "Profile");
				} else if (r.getS().equals(Contants.RESULT_FAIL)) {
					MessageHandlerManager.getInstance()
							.sendMessage(Constant.CHANGE_PASSWORD_REQUEST_FAIL, "Profile");
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
