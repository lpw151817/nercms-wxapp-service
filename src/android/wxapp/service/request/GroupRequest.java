package android.wxapp.service.request;

import java.security.Principal;
import java.security.acl.Group;
import java.util.Enumeration;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import android.wxapp.service.dao.AffairDao;
import android.wxapp.service.dao.GroupDao;
import android.wxapp.service.handler.MessageHandlerManager;
import android.wxapp.service.jerry.model.affair.TaskUpdateQueryRequest;
import android.wxapp.service.jerry.model.affair.TaskUpdateQueryResponse;
import android.wxapp.service.jerry.model.group.CreateGroupRequest;
import android.wxapp.service.jerry.model.group.CreateGroupResponse;
import android.wxapp.service.jerry.model.group.DeleteGroupRequest;
import android.wxapp.service.jerry.model.group.DeleteGroupResponse;
import android.wxapp.service.jerry.model.group.GroupUpdateQueryRequest;
import android.wxapp.service.jerry.model.group.GroupUpdateQueryRequestIds;
import android.wxapp.service.jerry.model.group.GroupUpdateQueryResponse;
import android.wxapp.service.jerry.model.group.ModifyGroupRequest;
import android.wxapp.service.jerry.model.group.ModifyGroupResponse;
import android.wxapp.service.jerry.model.normal.NormalServerResponse;
import android.wxapp.service.thread.SaveAffairUpdateThread;
import android.wxapp.service.thread.SaveGroupThread;
import android.wxapp.service.util.Constant;

import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.JsonObjectRequest;

public class GroupRequest extends BaseRequest {

	/**
	 * ��ȡȺ����� JERRY
	 * <p>
	 * �жϷ��ص�sֵ�Ƿ���Ҫ�����ٴε���
	 * 
	 * @param context
	 * @param count
	 *            ����ǵڼ��β�ѯ���ݣ����ڷ�ҳ�������ݡ�������Ҫά��countֵ
	 * @return
	 */
	public JsonObjectRequest getGroupUpdateRequest(final Context c, String count) {
		// ���Ϊ��ȡ���û���id����ֱ�ӷ���
		if (getUserId(c) == null || getUserIc(c) == null)
			return null;
		String lastUpdateTime = getLastGroupUpdateTime(c);
		if (lastUpdateTime == null)
			lastUpdateTime = "";
		GroupUpdateQueryRequest params = new GroupUpdateQueryRequest(getUserId(c), getUserIc(c),
				lastUpdateTime, count);
		this.url = Contants.SERVER_URL + Contants.MODEL_NAME + Contants.METHOD_GROUP_UPDATE
				+ Contants.PARAM_NAME + parase2Json(params);
		Log.e("URL", this.url);
		return new JsonObjectRequest(this.url, null, new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject arg0) {
				Log.e("Response", arg0.toString());
				try {
					// ��ʾ�Ѿ�û�и����������Ҫ�ٴν�������
					if (arg0.getString("s").equals(Contants.RESULT_SUCCESS)) {
						GroupUpdateQueryResponse r = gson.fromJson(arg0.toString(),
								GroupUpdateQueryResponse.class);
						// �������ݿ�Ĳ���,��������,���޸�ʱ���
						new SaveGroupThread(c, r).run();
					}
					// ���и����������Ҫ��������
					// ������������ֱ��һ���Է��ء�����������
					else if (arg0.getString("s").equals(Contants.RESULT_MORE)) {

						Log.v("GroupRequest", "getGroupUpdateRequest() �����ӿ�");

						// GroupUpdateQueryResponse r =
						// gson.fromJson(arg0.toString(),
						// GroupUpdateQueryResponse.class);
						// // ����˴�����
						//
						// // �����ؽ�����ظ�handler����ui����
						// MessageHandlerManager.getInstance().sendMessage(
						// Constant.GROUP_UPDATE_SECCESS, r,
						// Contants.METHOD_GROUP_UPDATE);

					} else {
						NormalServerResponse r = gson.fromJson(arg0.toString(),
								NormalServerResponse.class);
						// �����ؽ�����ظ�handler����ui����
						MessageHandlerManager.getInstance().sendMessage(Constant.GROUP_UPDATE_FAIL, r,
								Contants.METHOD_GROUP_UPDATE);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError arg0) {
				arg0.printStackTrace();
				Toast.makeText(c, "���ӷ�����ʧ��", Toast.LENGTH_LONG).show();
			}
		});
	}

	public JsonObjectRequest createGroup(final Context c, final String t, final String n,
			final String ct, final String ut, final List<GroupUpdateQueryRequestIds> rids) {
		// ���Ϊ��ȡ���û���id����ֱ�ӷ���
		if (getUserId(c) == null || getUserIc(c) == null)
			return null;
		CreateGroupRequest params = new CreateGroupRequest(getUserId(c), getUserIc(c), t, n, ct, ut,
				rids);
		this.url = Contants.SERVER_URL + Contants.MODEL_NAME + Contants.METHOD_GROUP_CREATE
				+ Contants.PARAM_NAME + parase2Json(params);
		Log.e("URL", this.url);
		return new JsonObjectRequest(this.url, null, new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject arg0) {
				Log.e("Response", arg0.toString());
				try {
					// ��ʾ�Ѿ�û�и����������Ҫ�ٴν�������
					if (arg0.getString("s").equals(Contants.RESULT_SUCCESS)) {
						CreateGroupResponse r = gson.fromJson(arg0.toString(), CreateGroupResponse.class);
						// �������ݿ�Ĳ���,��������
						if (new GroupDao(c).saveGroupUpdate(r.getGid(), t, n, ct, ut, rids))
							// �����ؽ�����ظ�handler����ui����
							MessageHandlerManager.getInstance().sendMessage(
									Constant.GROUP_CREATE_SECCESS, r, Contants.METHOD_GROUP_CREATE);
					} else {
						NormalServerResponse r = gson.fromJson(arg0.toString(),
								NormalServerResponse.class);
						// �����ؽ�����ظ�handler����ui����
						MessageHandlerManager.getInstance().sendMessage(Constant.GROUP_CREATE_FAIL, r,
								Contants.METHOD_GROUP_CREATE);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError arg0) {
				arg0.printStackTrace();
				Toast.makeText(c, "���ӷ�����ʧ��", Toast.LENGTH_LONG).show();
			}
		});
	}

	public JsonObjectRequest modifyGroup(final Context c, final String gid, final String t,
			final String n, final String ct, final String ut, final List<GroupUpdateQueryRequestIds> rids) {
		// ���Ϊ��ȡ���û���id����ֱ�ӷ���
		if (getUserId(c) == null || getUserIc(c) == null)
			return null;
		ModifyGroupRequest params = new ModifyGroupRequest(getUserId(c), getUserIc(c), gid, t, n, ct,
				ut, rids);
		this.url = Contants.SERVER_URL + Contants.MODEL_NAME + Contants.METHOD_GROUP_MODIFY
				+ Contants.PARAM_NAME + parase2Json(params);
		Log.e("URL", this.url);
		return new JsonObjectRequest(this.url, null, new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject arg0) {
				Log.e("Response", arg0.toString());
				try {
					// ��ʾ�Ѿ�û�и����������Ҫ�ٴν�������
					if (arg0.getString("s").equals(Contants.RESULT_SUCCESS)) {
						ModifyGroupResponse r = gson.fromJson(arg0.toString(), ModifyGroupResponse.class);
						// �������ݿ�Ĳ���,��������
						if (new GroupDao(c).modifyGroup(gid, t, n, ct, ut, rids))
							// �����ؽ�����ظ�handler����ui����
							MessageHandlerManager.getInstance().sendMessage(
									Constant.GROUP_MODIFY_SECCESS, r, Contants.METHOD_GROUP_MODIFY);
					} else {
						NormalServerResponse r = gson.fromJson(arg0.toString(),
								NormalServerResponse.class);
						// �����ؽ�����ظ�handler����ui����
						MessageHandlerManager.getInstance().sendMessage(Constant.GROUP_MODIFY_FAIL, r,
								Contants.METHOD_GROUP_MODIFY);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError arg0) {
				arg0.printStackTrace();
				Toast.makeText(c, "���ӷ�����ʧ��", Toast.LENGTH_LONG).show();
			}
		});
	}

	public JsonObjectRequest deleteGroup(final Context c, final String gid) {
		// ���Ϊ��ȡ���û���id����ֱ�ӷ���
		if (getUserId(c) == null || getUserIc(c) == null)
			return null;
		DeleteGroupRequest params = new DeleteGroupRequest(getUserId(c), getUserIc(c), gid);
		this.url = Contants.SERVER_URL + Contants.MODEL_NAME + Contants.METHOD_GROUP_DELETE
				+ Contants.PARAM_NAME + parase2Json(params);
		Log.e("URL", this.url);
		return new JsonObjectRequest(this.url, null, new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject arg0) {
				Log.e("Response", arg0.toString());
				try {
					// ��ʾ�Ѿ�û�и����������Ҫ�ٴν�������
					if (arg0.getString("s").equals(Contants.RESULT_SUCCESS)) {
						DeleteGroupResponse r = gson.fromJson(arg0.toString(), DeleteGroupResponse.class);
						// �������ݿ�Ĳ���,��������
						if (new GroupDao(c).deleteGroup(gid))
							// �����ؽ�����ظ�handler����ui����
							MessageHandlerManager.getInstance().sendMessage(
									Constant.GROUP_DELETE_SECCESS, r, Contants.METHOD_GROUP_DELETE);
					} else {
						NormalServerResponse r = gson.fromJson(arg0.toString(),
								NormalServerResponse.class);
						// �����ؽ�����ظ�handler����ui����
						MessageHandlerManager.getInstance().sendMessage(Constant.GROUP_DELETE_FAIL, r,
								Contants.METHOD_GROUP_DELETE);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError arg0) {
				arg0.printStackTrace();
				Toast.makeText(c, "���ӷ�����ʧ��", Toast.LENGTH_LONG).show();
			}
		});
	}
}
