package android.wxapp.service.jerry.model.group;

import java.util.List;

import android.wxapp.service.jerry.model.BaseRequestModel;

public class ModifyGroupRequest extends BaseRequestModel {
	String gid, t, n, ct, ut;
	List<GroupUpdateQueryRequestIds> rids;

	public String getGid() {
		return gid;
	}

	public void setGid(String gid) {
		this.gid = gid;
	}

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

	public ModifyGroupRequest(String uid, String ic, String gid, String t, String n, String ct,
			String ut, List<GroupUpdateQueryRequestIds> rids) {
		super(uid, ic);
		this.gid = gid;
		this.t = t;
		this.n = n;
		this.ct = ct;
		this.ut = ut;
		this.rids = rids;
	}

	public ModifyGroupRequest() {
		super();
	}

}
