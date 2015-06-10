package android.wxapp.service.request;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;
import android.wxapp.service.handler.MessageHandlerManager;
import android.wxapp.service.jerry.model.gps.GpsInfo;
import android.wxapp.service.jerry.model.gps.GpsUploadRequest;
import android.wxapp.service.jerry.model.gps.QueryGpssRequest;
import android.wxapp.service.jerry.model.gps.QueryGpssRequestIds;
import android.wxapp.service.jerry.model.gps.QueryGpssResponse;
import android.wxapp.service.jerry.model.gps.QueryLastGpssRequestIds;
import android.wxapp.service.jerry.model.gps.QueryLastGpssRequest;
import android.wxapp.service.jerry.model.gps.QueryLastGpssResponse;
import android.wxapp.service.jerry.model.gps.QueryLastPersonalGpsRequest;
import android.wxapp.service.jerry.model.gps.QueryLastPersonalGpsResponse;
import android.wxapp.service.jerry.model.gps.QueryPersonalGpssReponse;
import android.wxapp.service.jerry.model.gps.QueryPersonalGpssRequest;
import android.wxapp.service.jerry.model.normal.NormalServerResponse;
import android.wxapp.service.jerry.model.person.ModifyCustomerRequest;
import android.wxapp.service.jerry.model.person.ModifyCustomerResponse;
import android.wxapp.service.util.Constant;

import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.JsonObjectRequest;

/**
 * ����ĵ���GPSģ���ȫ���ӿ����� ������
 * 
 * @author JerryLiu
 *
 */
public class GpsRequest extends BaseRequest {
	// ======================================================
	//
	// JerryLiu 2015.5.22 Complete
	//
	// 1.gpsUpload
	// 2.queryLastPersonalGPS
	// 3.queryLastGPSs
	// 4.queryPersonalGPSs
	// 5.queryGpss
	//
	// ======================================================

	/**
	 * GPS��Ϣ�ϴ�
	 * 
	 * @param c
	 * @param identifyCode
	 *            ����
	 * @param g
	 *            GPS��Ϣ����
	 * @return
	 */
	public JsonObjectRequest gpsUpload(Context c, String identifyCode, GpsInfo g) {
		if (getUserId(c) == null)
			return null;
		// ��gps��Ϣֱ�Ӵ����json���䣬֮����Ҫ��gps��Ϣ����ѹ������
		GpsUploadRequest params = new GpsUploadRequest(getUserId(c), identifyCode, gson.toJson(g));
		this.url = Contants.SERVER_URL + Contants.MODEL_NAME + Contants.METHOD_GPS_UPLOAD
				+ Contants.PARAM_NAME + super.gson.toJson(params);
		Log.e("URL", this.url);
		return new JsonObjectRequest(this.url, null, new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject arg0) {
				Log.e("Response", arg0.toString());
				try {
					if (arg0.getString("s").equals(Contants.RESULT_SUCCESS)) {
						ModifyCustomerResponse r = gson.fromJson(arg0.toString(),
								ModifyCustomerResponse.class);
						// �����ؽ�����ظ�handler����ui����
						MessageHandlerManager.getInstance().sendMessage(
								Constant.GPS_UPLOAD_REQUEST_SECCESS, r, Contants.METHOD_GPS_UPLOAD);
					} else {
						NormalServerResponse r = gson.fromJson(arg0.toString(),
								NormalServerResponse.class);
						// �����ؽ�����ظ�handler����ui����
						MessageHandlerManager.getInstance().sendMessage(
								Constant.GPS_UPLOAD_REQUEST_FAIL, r, Contants.METHOD_GPS_UPLOAD);
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
	 * ��ѯ����ʵʱGPS��Ϣ
	 * 
	 * @param c
	 * @param identifyCode
	 * @param id
	 *            ������Ա��id
	 * @return
	 */
	public JsonObjectRequest queryLastPersonalGPS(Context c, String identifyCode, String id) {
		if (getUserId(c) == null)
			return null;
		QueryLastPersonalGpsRequest params = new QueryLastPersonalGpsRequest(getUserId(c), identifyCode,
				id);
		this.url = Contants.SERVER_URL + Contants.MODEL_NAME
				+ Contants.METHOD_GPS_QUERY_LAST_PERSONAL_GPS + Contants.PARAM_NAME
				+ super.gson.toJson(params);
		Log.e("URL", this.url);
		return new JsonObjectRequest(this.url, null, new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject arg0) {
				Log.e("Response", arg0.toString());
				try {
					if (arg0.getString("s").equals(Contants.RESULT_SUCCESS)) {
						QueryLastPersonalGpsResponse r = gson.fromJson(arg0.toString(),
								QueryLastPersonalGpsResponse.class);
						// �����ؽ�����ظ�handler����ui����
						MessageHandlerManager.getInstance().sendMessage(
								Constant.QUERY_PERSONAL_LAST_GPS_REQUEST_SECCESS, r,
								Contants.METHOD_GPS_QUERY_LAST_PERSONAL_GPS);
					} else {
						NormalServerResponse r = gson.fromJson(arg0.toString(),
								NormalServerResponse.class);
						// �����ؽ�����ظ�handler����ui����
						MessageHandlerManager.getInstance().sendMessage(
								Constant.QUERY_PERSONAL_LAST_GPS_REQUEST_FAIL, r,
								Contants.METHOD_GPS_QUERY_LAST_PERSONAL_GPS);
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
	 * ��ѯ����û����µ�gps��Ϣ
	 * 
	 * @param c
	 * @param identifyCode
	 * @param ids
	 *            ����ѯ���û�id����
	 * @return
	 */
	public JsonObjectRequest queryLastGPSs(Context c, String identifyCode, String[] ids) {
		List<QueryLastGpssRequestIds> idList = new ArrayList<QueryLastGpssRequestIds>();
		for (int i = 0; i < ids.length; i++) {
			idList.add(new QueryLastGpssRequestIds(ids[i]));
		}
		if (getUserId(c) == null)
			return null;
		QueryLastGpssRequest params = new QueryLastGpssRequest(getUserId(c), identifyCode, idList);
		this.url = Contants.SERVER_URL + Contants.MODEL_NAME + Contants.METHOD_GPS_QUERY_LAST_GPSS
				+ Contants.PARAM_NAME + super.gson.toJson(params);
		Log.e("URL", this.url);
		return new JsonObjectRequest(this.url, null, new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject arg0) {
				Log.e("Response", arg0.toString());
				try {
					if (arg0.getString("s").equals(Contants.RESULT_SUCCESS)) {
						QueryLastGpssResponse r = gson.fromJson(arg0.toString(),
								QueryLastGpssResponse.class);
						// �����ؽ�����ظ�handler����ui����
						MessageHandlerManager.getInstance().sendMessage(
								Constant.QUERY_LAST_GPSS_REQUEST_SECCESS, r,
								Contants.METHOD_GPS_QUERY_LAST_GPSS);
					} else {
						NormalServerResponse r = gson.fromJson(arg0.toString(),
								NormalServerResponse.class);
						// �����ؽ�����ظ�handler����ui����
						MessageHandlerManager.getInstance().sendMessage(
								Constant.QUERY_LAST_GPSS_REQUEST_FAIL, r,
								Contants.METHOD_GPS_QUERY_LAST_GPSS);
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
	 * ��ѯ���˹켣
	 * 
	 * @param c
	 * @param identifyCode
	 * @param cid
	 *            ��Ҫ��ѯ����Աid
	 * @return
	 */
	public JsonObjectRequest queryPersonalGPSs(Context c, String identifyCode, String cid) {
		if (getUserId(c) == null)
			return null;
		QueryPersonalGpssRequest params = new QueryPersonalGpssRequest(getUserId(c), identifyCode, cid);
		this.url = Contants.SERVER_URL + Contants.MODEL_NAME + Contants.METHOD_GPS_QUERY_PERSONAL_GPSS
				+ Contants.PARAM_NAME + super.gson.toJson(params);
		Log.e("URL", this.url);
		return new JsonObjectRequest(this.url, null, new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject arg0) {
				Log.e("Response", arg0.toString());
				try {
					if (arg0.getString("s").equals(Contants.RESULT_SUCCESS)) {
						QueryPersonalGpssReponse r = gson.fromJson(arg0.toString(),
								QueryPersonalGpssReponse.class);
						// �����ؽ�����ظ�handler����ui����
						MessageHandlerManager.getInstance().sendMessage(
								Constant.QUERY_PERSONAL_GPSS_REQUEST_SECCESS, r,
								Contants.METHOD_GPS_QUERY_PERSONAL_GPSS);
					} else {
						NormalServerResponse r = gson.fromJson(arg0.toString(),
								NormalServerResponse.class);
						// �����ؽ�����ظ�handler����ui����
						MessageHandlerManager.getInstance().sendMessage(
								Constant.QUERY_PERSONAL_GPSS_REQUEST_FAIL, r,
								Contants.METHOD_GPS_QUERY_PERSONAL_GPSS);
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
	 * ��ѯ���˹켣
	 * 
	 * @param c
	 * @param identifyCode
	 * @param cids
	 *            ����ѯ���û�id����
	 * @return
	 */
	public JsonObjectRequest queryGpss(Context c, String identifyCode, String[] cids) {
		List<QueryGpssRequestIds> l = new ArrayList<QueryGpssRequestIds>();
		for (int i = 0; i < cids.length; i++) {
			l.add(new QueryGpssRequestIds(cids[i]));
		}
		if (getUserId(c) == null)
			return null;
		QueryGpssRequest params = new QueryGpssRequest(getUserId(c), identifyCode, l);
		this.url = Contants.SERVER_URL + Contants.MODEL_NAME + Contants.METHOD_GPS_QUERY_GPSS
				+ Contants.PARAM_NAME + super.gson.toJson(params);
		Log.e("URL", this.url);
		return new JsonObjectRequest(this.url, null, new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject arg0) {
				Log.e("Response", arg0.toString());
				try {
					if (arg0.getString("s").equals(Contants.RESULT_SUCCESS)) {
						QueryGpssResponse r = gson.fromJson(arg0.toString(), QueryGpssResponse.class);
						// �����ؽ�����ظ�handler����ui����
						MessageHandlerManager.getInstance().sendMessage(
								Constant.QUERY_GPSS_REQUEST_SECCESS, r, Contants.METHOD_GPS_QUERY_GPSS);
					} else {
						NormalServerResponse r = gson.fromJson(arg0.toString(),
								NormalServerResponse.class);
						// �����ؽ�����ظ�handler����ui����
						MessageHandlerManager.getInstance().sendMessage(
								Constant.QUERY_GPSS_REQUEST_FAIL, r, Contants.METHOD_GPS_QUERY_GPSS);
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
}
