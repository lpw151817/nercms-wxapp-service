package android.wxapp.service.jerry.model.group;

import java.util.List;

import android.wxapp.service.jerry.model.BaseResponseModel;

public class GroupUpdateQueryResponse extends BaseResponseModel {
	List<GroupUpdateQueryRequestGroups> gs;
	String tsp;

	public List<GroupUpdateQueryRequestGroups> getGs() {
		return gs;
	}

	public void setGs(List<GroupUpdateQueryRequestGroups> gs) {
		this.gs = gs;
	}

	public String getTsp() {
		return tsp;
	}

	public void setTsp(String tsp) {
		this.tsp = tsp;
	}

	public GroupUpdateQueryResponse() {
		super();
	}

	public GroupUpdateQueryResponse(String s, List<GroupUpdateQueryRequestGroups> gs, String tsp) {
		super(s);
		this.gs = gs;
		this.tsp = tsp;
	}
}
