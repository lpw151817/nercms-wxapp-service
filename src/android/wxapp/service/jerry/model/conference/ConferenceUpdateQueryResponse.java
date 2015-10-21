package android.wxapp.service.jerry.model.conference;

import java.util.List;

import android.wxapp.service.jerry.model.BaseResponseModel;

public class ConferenceUpdateQueryResponse extends BaseResponseModel {

	List<ConferenceUpdateQueryResponseItem> cs;
	String tsp;

	public ConferenceUpdateQueryResponse(String s, List<ConferenceUpdateQueryResponseItem> cs, String tsp) {
		super(s);
		this.cs = cs;
		this.tsp = tsp;
	}

	public List<ConferenceUpdateQueryResponseItem> getCs() {
		return cs;
	}

	public void setCs(List<ConferenceUpdateQueryResponseItem> cs) {
		this.cs = cs;
	}

	public String getTsp() {
		return tsp;
	}

	public void setTsp(String tsp) {
		this.tsp = tsp;
	}

	public ConferenceUpdateQueryResponse() {
		super();
	}

}
