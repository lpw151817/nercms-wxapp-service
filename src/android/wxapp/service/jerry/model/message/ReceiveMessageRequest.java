package android.wxapp.service.jerry.model.message;

public class ReceiveMessageRequest {
String uid,ic,mid;

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

public String getMid() {
	return mid;
}

public void setMid(String mid) {
	this.mid = mid;
}

public ReceiveMessageRequest(String uid, String ic, String mid) {
	super();
	this.uid = uid;
	this.ic = ic;
	this.mid = mid;
}

public ReceiveMessageRequest() {
	super();
}

}
