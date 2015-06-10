package android.wxapp.service.jerry.model.message;

import java.util.List;

public class MessageUpdateQueryResponse {
	String s;
	List<MessageUpdateQueryResponseMessages> ms;
	String tsp;

	public String getS() {
		return s;
	}

	public void setS(String s) {
		this.s = s;
	}

	public List<MessageUpdateQueryResponseMessages> getMs() {
		return ms;
	}

	public void setMs(List<MessageUpdateQueryResponseMessages> ms) {
		this.ms = ms;
	}

	public String getTsp() {
		return tsp;
	}

	public void setTsp(String tsp) {
		this.tsp = tsp;
	}

	public MessageUpdateQueryResponse(String s, List<MessageUpdateQueryResponseMessages> ms, String tsp) {
		super();
		this.s = s;
		this.ms = ms;
		this.tsp = tsp;
	}

	public MessageUpdateQueryResponse() {
		super();
	}

}
