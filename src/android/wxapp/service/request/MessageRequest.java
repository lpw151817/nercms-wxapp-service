package android.wxapp.service.request;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.widget.Toast;
import android.wxapp.service.dao.MessageDao;
import android.wxapp.service.handler.MessageHandlerManager;
import android.wxapp.service.jerry.model.message.MessageUpdateQueryRequest;
import android.wxapp.service.jerry.model.message.MessageUpdateQueryResponse;
import android.wxapp.service.jerry.model.message.QueryContactPersonMessageRequest;
import android.wxapp.service.jerry.model.message.QueryContactPersonMessageResponse;
import android.wxapp.service.jerry.model.message.ReceiveMessageRequest;
import android.wxapp.service.jerry.model.message.ReceiveMessageResponse;
import android.wxapp.service.jerry.model.message.SendMessageRequest;
import android.wxapp.service.jerry.model.message.SendMessageResponse;
import android.wxapp.service.jerry.model.normal.NormalServerResponse;
import android.wxapp.service.thread.SaveMessageThread;
import android.wxapp.service.thread.SaveMessageUpdateThread;
import android.wxapp.service.util.Constant;

import com.android.volley.AuthFailureError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

public class MessageRequest extends BaseRequest {

	// ======================================================
	//
	// JerryLiu 2015.5.22
	//
	// 1.getMessageUpdateRequest
	// 2.sendMessageRequest
	// 3.queryContactPersonMessage
	// 4.deleteMessage
	// 5.queryMessageHistory
	// 6.receiveMessage
	//
	// ======================================================

	public MessageRequest() {

	}

	/**
	 * ��ȡ��Ϣ���� JERRY 5.23
	 * <p>
	 * �жϷ��ص�sֵ�Ƿ���Ҫ�����ٴε���
	 * 
	 * @param c
	 * @param ic
	 * @param count
	 *            ����ǵڼ��β�ѯ���ݣ����ڷ�ҳ�������ݡ�������Ҫά��countֵ
	 * @return
	 */
	public JsonObjectRequest getMessageUpdateRequest(final Context c, String count) {
		// ���Ϊ��ȡ���û���id����ֱ�ӷ���
		if (getUserId(c) == null || getUserIc(c) == null)
			return null;
		String tempLastMessageUpdateTime;
		if (getLastMessageUpdateTime(c) == null)
			tempLastMessageUpdateTime = "";
		else
			tempLastMessageUpdateTime = getLastMessageUpdateTime(c);
		MessageUpdateQueryRequest params = new MessageUpdateQueryRequest(getUserId(c), getUserIc(c),
				tempLastMessageUpdateTime, count);
		this.url = Contants.SERVER_URL + Contants.MODEL_NAME + Contants.METHOD_MESSAGE_UPDATE
				+ Contants.PARAM_NAME + parase2Json(params);
		System.out.println(this.url);
		return new JsonObjectRequest(this.url, null, new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject arg0) {
				System.out.println(arg0.toString());
				try {
					// ��ʾ�Ѿ�û�и����������Ҫ�ٴν�������
					if (arg0.getString("s").equals(Contants.RESULT_SUCCESS)) {
						MessageUpdateQueryResponse r = gson.fromJson(arg0.toString(),
								MessageUpdateQueryResponse.class);
						// �������ݿ�Ĳ���,��������
						new SaveMessageUpdateThread(c, r).run();
						// �����ؽ�����ظ�handler����ui����
						MessageHandlerManager.getInstance().sendMessage(
								Constant.UPDATE_MESSAGE_REQUEST_SUCCESS, r,
								Contants.METHOD_MESSAGE_UPDATE);
					}
					// ���и����������Ҫ��������
					else if (arg0.getString("s").equals(Contants.RESULT_MORE)) {
						MessageUpdateQueryResponse r = gson.fromJson(arg0.toString(),
								MessageUpdateQueryResponse.class);
						// �������ݿ�Ĳ���,��������
						new SaveMessageUpdateThread(c, r).run();
						// �����ؽ�����ظ�handler����ui����
						MessageHandlerManager.getInstance().sendMessage(
								Constant.UPDATE_MESSAGE_REQUEST_SUCCESS, r,
								Contants.METHOD_MESSAGE_UPDATE);
					} else {
						NormalServerResponse r = gson.fromJson(arg0.toString(),
								NormalServerResponse.class);
						// �����ؽ�����ظ�handler����ui����
						MessageHandlerManager.getInstance().sendMessage(
								Constant.UPDATE_MESSAGE_REQUEST_FAIL, r,
								Contants.METHOD_MESSAGE_UPDATE);
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
	 * ��������Ϣ JERRY 5.23
	 * 
	 * @param context
	 * @param ic
	 * @param t
	 *            ��Ϣ����,0 ��ͨ����������Ϣ1 ����Ⱥ����Ϣ 2.�ǻ���Ⱥ����Ϣ3.�����¼��Ϣ 4.��������Ϣ
	 * @param sid
	 *            ������ID
	 * @param rids
	 *            �����Աid����
	 * @param st
	 *            ����ʱ��
	 * @param c
	 *            ��Ϣ�ı����ݣ�����ϢΪ������Ϣʱ���ֶ�Ϊ��
	 * @param at
	 *            �������� 1.�ı� 2.ͼƬ 3.¼�� 4.¼�� 5. GPS
	 * @param au
	 *            �������ӣ�����ϢΪ�ı���Ϣʱ���ֶ�Ϊ�գ�
	 * @param ut
	 *            ����ʱ��
	 * @return
	 */
	public JsonObjectRequest sendMessageRequest(final Context context, String t, String sid,
			String rid, String st, String c, String at, String au, String ut) {
		// ���Ϊ��ȡ���û���id����ֱ�ӷ���
		if (getUserId(context) == null || getUserIc(context) == null)
			return null;
		final SendMessageRequest params = new SendMessageRequest(getUserId(context),
				getUserIc(context), t, sid, rid, st, c, at, au, ut);
		this.url = Contants.SERVER_URL + Contants.MODEL_NAME + Contants.METHOD_MESSAGE_SEND
				+ Contants.PARAM_NAME + parase2Json(params);
		System.out.println(this.url);
		return new JsonObjectRequest(this.url, null, new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject arg0) {
				System.out.println(arg0.toString());
				try {
					if (arg0.getString("s").equals(Contants.RESULT_SUCCESS)) {
						SendMessageResponse r = gson.fromJson(arg0.toString(),
								SendMessageResponse.class);
						// �������ݿ��insert
						new SaveMessageThread(context, r.getMid(),
								new ReceiveMessageResponse("", r.getMid(), params.getT(),
										params.getSid(), params.getRid(), params.getSt(),
										params.getC(), params.getAt(), params.getAu(),
										params.getUt(), null)).run();
						// �����ؽ�����ظ�handler����ui����
						MessageHandlerManager.getInstance().sendMessage(
								Constant.SEND_MESSAGE_REQUEST_SUCCESS, r,
								Contants.METHOD_MESSAGE_SEND);
					} else {
						NormalServerResponse r = gson.fromJson(arg0.toString(),
								NormalServerResponse.class);
						// �����ؽ�����ظ�handler����ui����
						MessageHandlerManager.getInstance().sendMessage(
								Constant.SEND_MESSAGE_REQUEST_FAIL, r,
								Contants.METHOD_MESSAGE_SEND);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError arg0) {
				arg0.printStackTrace();
				Toast.makeText(context, "����������ʧ��", Toast.LENGTH_LONG).show();
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
	 * ��ѯ�����ϵ�� �����Ϣ�б�
	 * 
	 * @param context
	 * @param ic
	 * @return
	 */
	public JsonObjectRequest queryContactPersonMessage(Context context, String ic) {
		// ���Ϊ��ȡ���û���id����ֱ�ӷ���
		if (getUserId(context) == null)
			return null;
		QueryContactPersonMessageRequest params = new QueryContactPersonMessageRequest(
				getUserId(context), ic);
		this.url = Contants.SERVER_URL + Contants.MODEL_NAME
				+ Contants.METHOD_MESSAGE_QUERY_CONTACT_MERSON_MESSAGE + Contants.PARAM_NAME
				+ parase2Json(params);
		System.out.println(this.url);
		return new JsonObjectRequest(this.url, null, new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject arg0) {
				System.out.println(arg0.toString());
				try {
					// ��ʾ�Ѿ�û�и����������Ҫ�ٴν�������
					if (arg0.getString("s").equals(Contants.RESULT_SUCCESS)) {
						QueryContactPersonMessageResponse r = gson.fromJson(arg0.toString(),
								QueryContactPersonMessageResponse.class);
						// �����ؽ�����ظ�handler����ui����
						MessageHandlerManager.getInstance().sendMessage(
								Constant.QUERY_RECENT_MESSAGE_REQUEST_SUCCESS, r,
								Contants.METHOD_MESSAGE_QUERY_CONTACT_MERSON_MESSAGE);
					} else {
						NormalServerResponse r = gson.fromJson(arg0.toString(),
								NormalServerResponse.class);
						// �����ؽ�����ظ�handler����ui����
						MessageHandlerManager.getInstance().sendMessage(
								Constant.QUERY_RECENT_MESSAGE_REQUEST_FAIL, r,
								Contants.METHOD_MESSAGE_QUERY_CONTACT_MERSON_MESSAGE);
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

	// �ݲ�ʵ��
	public JsonObjectRequest deleteMessage() {
		return null;
	}

	// �ݲ�ʵ��
	public JsonObjectRequest queryMessageHistory() {
		return null;
	}

	/**
	 * ��ѯ���յ�����Ϣ����ϸ����
	 * 
	 * @param context
	 * @param ic
	 * @param mid
	 *            ��Ҫ��ѯ����Ϣid
	 * @return
	 */
	public JsonObjectRequest receiveMessage(final Context context, final String mid) {
		// ���Ϊ��ȡ���û���id����ֱ�ӷ���
		if (getUserId(context) == null || getUserIc(context) == null)
			return null;
		ReceiveMessageRequest params = new ReceiveMessageRequest(getUserId(context),
				getUserIc(context), mid);
		this.url = Contants.SERVER_URL + Contants.MODEL_NAME + Contants.METHOD_MESSAGE_RECEIVE
				+ Contants.PARAM_NAME + parase2Json(params);
		System.out.println(this.url);
		return new JsonObjectRequest(this.url, null, new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject arg0) {
				System.out.println(arg0.toString());
				try {
					// ��ʾ�Ѿ�û�и����������Ҫ�ٴν�������
					if (arg0.getString("s").equals(Contants.RESULT_SUCCESS)) {
						ReceiveMessageResponse r = gson.fromJson(arg0.toString(),
								ReceiveMessageResponse.class);
						// db insert
						new MessageDao(context).saveMessage(mid, r);
						// �����ؽ�����ظ�handler����ui����
						MessageHandlerManager.getInstance().sendMessage(
								Constant.QUERY_MESSAGE_INFO_REQUEST_SUCCESS, r,
								Contants.METHOD_MESSAGE_RECEIVE);
					} else {
						NormalServerResponse r = gson.fromJson(arg0.toString(),
								NormalServerResponse.class);
						// �����ؽ�����ظ�handler����ui����
						MessageHandlerManager.getInstance().sendMessage(
								Constant.QUERY_MESSAGE_INFO_REQUEST_FAIL, r,
								Contants.METHOD_MESSAGE_RECEIVE);
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
