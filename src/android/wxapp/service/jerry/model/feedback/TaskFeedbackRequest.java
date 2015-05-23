package android.wxapp.service.jerry.model.feedback;

import java.util.List;

public class TaskFeedbackRequest {
String uid,ic,t,sid,st,c,at,au,ut;
List<TaskFeedbackRequestIds> rids;
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
public String getT() {
	return t;
}
public void setT(String t) {
	this.t = t;
}
public String getSid() {
	return sid;
}
public void setSid(String sid) {
	this.sid = sid;
}
public String getSt() {
	return st;
}
public void setSt(String st) {
	this.st = st;
}
public String getC() {
	return c;
}
public void setC(String c) {
	this.c = c;
}
public String getAt() {
	return at;
}
public void setAt(String at) {
	this.at = at;
}
public String getAu() {
	return au;
}
public void setAu(String au) {
	this.au = au;
}
public String getUt() {
	return ut;
}
public void setUt(String ut) {
	this.ut = ut;
}
public List<TaskFeedbackRequestIds> getRids() {
	return rids;
}
public void setRids(List<TaskFeedbackRequestIds> rids) {
	this.rids = rids;
}
public TaskFeedbackRequest(String uid, String ic, String t, String sid, String st, String c, String at,
		String au, String ut, List<TaskFeedbackRequestIds> rids) {
	super();
	this.uid = uid;
	this.ic = ic;
	this.t = t;
	this.sid = sid;
	this.st = st;
	this.c = c;
	this.at = at;
	this.au = au;
	this.ut = ut;
	this.rids = rids;
}
public TaskFeedbackRequest() {
	super();
}

}
