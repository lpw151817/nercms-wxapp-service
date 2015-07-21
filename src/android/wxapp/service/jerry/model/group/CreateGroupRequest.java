package android.wxapp.service.jerry.model.group;

import java.util.List;

import android.wxapp.service.jerry.model.BaseRequestModel;

public class CreateGroupRequest extends BaseRequestModel {
	String t, n, ct, ut;
	List<GroupUpdateQueryRequestIds> rids;

	public String getT() {
		return t;
	}

	public void setT(String t) {
		this.t = t;
	}

	public String getN() {
		return n;
	}

	public void setN(String n) {
		this.n = n;
	}

	public String getCt() {
		return ct;
	}

	public void setCt(String ct) {
		this.ct = ct;
	}

	public String getUt() {
		return ut;
	}

	public void setUt(String ut) {
		this.ut = ut;
	}

	public List<GroupUpdateQueryRequestIds> getRids() {
		return rids;
	}

	public void setRids(List<GroupUpdateQueryRequestIds> rids) {
		this.rids = rids;
	}

	public CreateGroupRequest(String uid, String ic, String t, String n, String ct, String ut,
			List<GroupUpdateQueryRequestIds> rids) {
		super(uid, ic);
		this.t = t;
		this.n = n;
		this.ct = ct;
		this.ut = ut;
		this.rids = rids;
	}

	public CreateGroupRequest() {
		super();
	}

}
