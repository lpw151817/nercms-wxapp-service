package android.wxapp.service.jerry.model.gps;

public class GpsUploadRequest {
	String uid, ic, g;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getIc() {
		return ic;
	}

	public void setIc(String ic) {
		this.ic = ic;
	}

	public String getG() {
		return g;
	}

	public void setG(String g) {
		this.g = g;
	}

	/**
	 * 
	 * @param uid
	 * @param ic
	 * @param g
	 *            gps–≈œ¢
	 */
	public GpsUploadRequest(String uid, String ic, String g) {
		super();
		this.uid = uid;
		this.ic = ic;
		this.g = g;
	}

	public GpsUploadRequest() {
		super();
	}

}
