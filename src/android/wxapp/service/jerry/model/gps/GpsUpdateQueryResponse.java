package android.wxapp.service.jerry.model.gps;

import java.util.List;

import android.wxapp.service.jerry.model.BaseResponseModel;

public class GpsUpdateQueryResponse extends BaseResponseModel {
	List<GpsUpdateQueryResponseGpss> gss;
	String tsp;

	public List<GpsUpdateQueryResponseGpss> getGss() {
		return gss;
	}

	public void setGss(List<GpsUpdateQueryResponseGpss> gss) {
		this.gss = gss;
	}

	public String getTsp() {
		return tsp;
	}

	public void setTsp(String tsp) {
		this.tsp = tsp;
	}

	public GpsUpdateQueryResponse(String s, List<GpsUpdateQueryResponseGpss> gss, String tsp) {
		super(s);
		this.gss = gss;
		this.tsp = tsp;
	}

	public GpsUpdateQueryResponse() {
		super();
	}

}
