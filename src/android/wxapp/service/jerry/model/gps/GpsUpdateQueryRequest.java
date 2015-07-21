package android.wxapp.service.jerry.model.gps;

import android.wxapp.service.jerry.model.BaseRequestModel;

public class GpsUpdateQueryRequest extends BaseRequestModel {
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

	public GpsUpdateQueryRequest(String uid, String ic, String ut, String count) {
		super(uid, ic);
		this.ut = ut;
		this.count = count;
	}

	public GpsUpdateQueryRequest() {
		super();
	}

}
