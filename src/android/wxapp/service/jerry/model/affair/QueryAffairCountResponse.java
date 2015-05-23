package android.wxapp.service.jerry.model.affair;

public class QueryAffairCountResponse {
	String s;
	Count count;

	public QueryAffairCountResponse(String s, Count count) {
		super();
		this.s = s;
		this.count = count;
	}

	public QueryAffairCountResponse() {
		super();
	}

	public String getS() {
		return s;
	}

	public void setS(String s) {
		this.s = s;
	}

	public Count getCount() {
		return count;
	}

	public void setCount(Count count) {
		this.count = count;
	}

	class Count {
		Counter send, receive;

		public Counter getSend() {
			return send;
		}

		public void setSend(Counter send) {
			this.send = send;
		}

		public Counter getReceive() {
			return receive;
		}

		public void setReceive(Counter receive) {
			this.receive = receive;
		}

		public Count(Counter send, Counter receive) {
			super();
			this.send = send;
			this.receive = receive;
		}

		public Count() {
			super();
		}

	}

	class Counter {
		String done, doing, delay;

		public String getDone() {
			return done;
		}

		public void setDone(String done) {
			this.done = done;
		}

		public String getDoing() {
			return doing;
		}

		public void setDoing(String doing) {
			this.doing = doing;
		}

		public String getDelay() {
			return delay;
		}

		public void setDelay(String delay) {
			this.delay = delay;
		}

		public Counter(String done, String doing, String delay) {
			super();
			this.done = done;
			this.doing = doing;
			this.delay = delay;
		}

		public Counter() {
			super();
		}

	}

}
