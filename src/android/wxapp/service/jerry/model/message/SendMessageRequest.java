package android.wxapp.service.jerry.model.message;

import java.util.List;

public class SendMessageRequest {
String uid,ic,t,sid;
List<SendMessageRequestIds> rids;
String st,c,at,au,ut;
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
public List<SendMessageRequestIds> getRids() {
	return rids;
}
public void setRids(List<SendMessageRequestIds> rids) {
	this.rids = rids;
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
public SendMessageRequest(String uid, String ic, String t, String sid, List<SendMessageRequestIds> rids,
		String st, String c, String at, String au, String ut) {
	super();
	this.uid = uid;
	this.ic = ic;
	this.t = t;
	this.sid = sid;
	this.rids = rids;
	this.st = st;
	this.c = c;
	this.at = at;
	this.au = au;
	this.ut = ut;
}
public SendMessageRequest() {
	super();
}

}
