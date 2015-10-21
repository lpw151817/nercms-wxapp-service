package android.wxapp.service.jerry.model.affair;

public class TaskUpdateQueryRequest {
String uid,ic,ut,count;

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

public String getUt() {
	return ut;
}

public void setUt(String ut) {
	this.ut = ut;
}

public String getCount() {
	return count;
}

public void setCount(String count) {
	this.count = count;
}

public TaskUpdateQueryRequest(String uid, String ic, String ut, String count) {
	super();
	this.uid = uid;
	this.ic = ic;
	this.ut = ut;
	this.count = count;
}

public TaskUpdateQueryRequest() {
	super();
}

}
