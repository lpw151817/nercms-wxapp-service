package android.wxapp.service.jerry.model.gps;

public class GpsUploadResponse {
	String s;
	String gid;

	public GpsUploadResponse(String s, String gid) {
		super();
		this.s = s;
		this.gid = gid;
	}

	public String getGid() {
		return gid;
	}

	public void setGid(String gid) {
		this.gid = gid;
	}

	public String getS() {
		return s;
	}

	public void setS(String s) {
		this.s = s;
	}

	public GpsUploadResponse() {
		super();
	}
}
