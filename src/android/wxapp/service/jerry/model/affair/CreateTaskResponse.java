package android.wxapp.service.jerry.model.affair;

public class CreateTaskResponse {
	String s, aid;

	public String getS() {
		return s;
	}

	public void setS(String s) {
		this.s = s;
	}

	public CreateTaskResponse(String s) {
		super();
		this.s = s;
	}

	public CreateTaskResponse(String s, String aid) {
		super();
		this.s = s;
		this.aid = aid;
	}

	public void setAid(String aid) {
		this.aid = aid;
	}

	public String getAid() {
		return aid;
	}

	public CreateTaskResponse() {
		super();
	}

}
