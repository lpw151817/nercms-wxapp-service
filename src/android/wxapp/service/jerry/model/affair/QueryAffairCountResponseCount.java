package android.wxapp.service.jerry.model.affair;

public class QueryAffairCountResponseCount {

	QueryAffairCountResponseCounter send, receive;

	public QueryAffairCountResponseCounter getSend() {
		return send;
	}

	public void setSend(QueryAffairCountResponseCounter send) {
		this.send = send;
	}

	public QueryAffairCountResponseCounter getReceive() {
		return receive;
	}

	public void setReceive(QueryAffairCountResponseCounter receive) {
		this.receive = receive;
	}

	public QueryAffairCountResponseCount(QueryAffairCountResponseCounter send,
			QueryAffairCountResponseCounter receive) {
		super();
		this.send = send;
		this.receive = receive;
	}

	public QueryAffairCountResponseCount() {
		super();
	}

}
