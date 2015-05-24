package android.wxapp.service.jerry.model.message;

import java.util.List;

public class ReceiveMessageResponse {
	String s,t,sid;
List<QueryContactPersonMessageResponseIds> rids;
String st,c,at,au,ut,ir;
public String getS() {
	return s;
}
public void setS(String s) {
	this.s = s;
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
public List<QueryContactPersonMessageResponseIds> getRids() {
	return rids;
}
public void setRids(List<QueryContactPersonMessageResponseIds> rids) {
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
public String getIr() {
	return ir;
}
public void setIr(String ir) {
	this.ir = ir;
}
public ReceiveMessageResponse(String s, String t, String sid,
		List<QueryContactPersonMessageResponseIds> rids, String st, String c, String at, String au,
		String ut, String ir) {
	super();
	this.s = s;
	this.t = t;
	this.sid = sid;
	this.rids = rids;
	this.st = st;
	this.c = c;
	this.at = at;
	this.au = au;
	this.ut = ut;
	this.ir = ir;
}
public ReceiveMessageResponse() {
	super();
}

}
