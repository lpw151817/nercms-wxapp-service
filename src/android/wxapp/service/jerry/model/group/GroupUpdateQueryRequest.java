package android.wxapp.service.jerry.model.group;

import android.wxapp.service.jerry.model.BaseRequestModel;

public class GroupUpdateQueryRequest extends BaseRequestModel {
	String ut, count;

	public String getUt() {
		return ut;
	}

	public void setUt(String ut) {
		this.ut = ut;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public GroupUpdateQueryRequest(String uid, String ic, String ut, String count) {
		super(uid, ic);
		this.ut = ut;
		this.count = count;
	}

	public GroupUpdateQueryRequest() {
		super();
	}

}
