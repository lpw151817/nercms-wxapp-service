package android.wxapp.service.jerry.model.message;

import java.util.List;

public class MessageUpdateQueryResponse {
	String s;
	List<MessageUpdateQueryResponseMessages> mids;
	String tsp;

	public String getS() {
		return s;
	}

	public void setS(String s) {
		this.s = s;
	}

	public MessageUpdateQueryResponse(String s, List<MessageUpdateQueryResponseMessages> mids, String tsp) {
		super();
		this.s = s;
		this.mids = mids;
		this.tsp = tsp;
	}

	public List<MessageUpdateQueryResponseMessages> getMids() {
		return mids;
	}

	public void setMids(List<MessageUpdateQueryResponseMessages> mids) {
		this.mids = mids;
	}

	public String getTsp() {
		return tsp;
	}

	public void setTsp(String tsp) {
		this.tsp = tsp;
	}

	public MessageUpdateQueryResponse() {
		super();
	}

}
