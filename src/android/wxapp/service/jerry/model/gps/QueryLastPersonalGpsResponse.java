package android.wxapp.service.jerry.model.gps;

public class QueryLastPersonalGpsResponse {
String s,cid,g,o;

public String getS() {
	return s;
}

public void setS(String s) {
	this.s = s;
}

public String getCid() {
	return cid;
}

public void setCid(String cid) {
	this.cid = cid;
}

public String getG() {
	return g;
}

public void setG(String g) {
	this.g = g;
}

public String getO() {
	return o;
}

public void setO(String o) {
	this.o = o;
}

public QueryLastPersonalGpsResponse(String s, String cid, String g, String o) {
	super();
	this.s = s;
	this.cid = cid;
	this.g = g;
	this.o = o;
}

public QueryLastPersonalGpsResponse() {
	super();
}

}
