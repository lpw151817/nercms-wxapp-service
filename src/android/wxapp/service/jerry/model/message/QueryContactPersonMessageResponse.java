package android.wxapp.service.jerry.model.message;

import java.util.List;

public class QueryContactPersonMessageResponse {
String s,sid,t;
List<QueryContactPersonMessageResponseIds> rids;
String c,st,au;
public String getS() {
	return s;
}
public void setS(String s) {
	this.s = s;
}
public String getSid() {
	return sid;
}
public void setSid(String sid) {
	this.sid = sid;
}
public String getT() {
	return t;
}
public void setT(String t) {
	this.t = t;
}
public List<QueryContactPersonMessageResponseIds> getRids() {
	return rids;
}
public void setRids(List<QueryContactPersonMessageResponseIds> rids) {
	this.rids = rids;
}
public String getC() {
	return c;
}
public void setC(String c) {
	this.c = c;
}
public String getSt() {
	return st;
}
public void setSt(String st) {
	this.st = st;
}
public String getAu() {
	return au;
}
public void setAu(String au) {
	this.au = au;
}
public QueryContactPersonMessageResponse(String s, String sid, String t,
		List<QueryContactPersonMessageResponseIds> rids, String c, String st, String au) {
	super();
	this.s = s;
	this.sid = sid;
	this.t = t;
	this.rids = rids;
	this.c = c;
	this.st = st;
	this.au = au;
}
public QueryContactPersonMessageResponse() {
	super();
}

}
