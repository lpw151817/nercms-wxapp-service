package android.wxapp.service.jerry.model.affair;

public class QueryAffairCountResponseCounter {

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

	public QueryAffairCountResponseCounter(String done, String doing, String delay) {
		super();
		this.done = done;
		this.doing = doing;
		this.delay = delay;
	}

	public QueryAffairCountResponseCounter() {
		super();
	}


}
