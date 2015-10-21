package android.wxapp.service.jerry.model.affair;

import java.util.List;

public class QueryAffairListResponseAffairs {

	String aid, t, sid, d, topic, bt, et, ct, lot, lotime, up;
	List<CreateTaskRequestAttachment> att;
	List<CreateTaskRequestIds> rids;
	List<CreateTaskRequestIds> pod;
	public QueryAffairListResponseAffairs(String aid, String t, String sid, String d, String topic,
			String bt, String et, String ct, String lot, String lotime, String up,
			List<CreateTaskRequestAttachment> att, List<CreateTaskRequestIds> rids,
			List<CreateTaskRequestIds> pod) {
		super();
		this.aid = aid;
		this.t = t;
		this.sid = sid;
		this.d = d;
		this.topic = topic;
		this.bt = bt;
		this.et = et;
		this.ct = ct;
		this.lot = lot;
		this.lotime = lotime;
		this.up = up;
		this.att = att;
		this.rids = rids;
		this.pod = pod;
	}
	public List<CreateTaskRequestIds> getPod() {
		return pod;
	}
	public void setPod(List<CreateTaskRequestIds> pod) {
		this.pod = pod;
	}
	public String getAid() {
		return aid;
	}
	public void setAid(String aid) {
		this.aid = aid;
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
	public String getD() {
		return d;
	}
	public void setD(String d) {
		this.d = d;
	}
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	public String getBt() {
		return bt;
	}
	public void setBt(String bt) {
		this.bt = bt;
	}
	public String getEt() {
		return et;
	}
	public void setEt(String et) {
		this.et = et;
	}
	public String getCt() {
		return ct;
	}
	public void setCt(String ct) {
		this.ct = ct;
	}
	public String getLot() {
		return lot;
	}
	public void setLot(String lot) {
		this.lot = lot;
	}
	public String getLotime() {
		return lotime;
	}
	public void setLotime(String lotime) {
		this.lotime = lotime;
	}
	public String getUp() {
		return up;
	}
	public void setUp(String up) {
		this.up = up;
	}
	public List<CreateTaskRequestAttachment> getAtt() {
		return att;
	}
	public void setAtt(List<CreateTaskRequestAttachment> att) {
		this.att = att;
	}
	public List<CreateTaskRequestIds> getRids() {
		return rids;
	}
	public void setRids(List<CreateTaskRequestIds> rids) {
		this.rids = rids;
	}
	public QueryAffairListResponseAffairs() {
		super();
	}

	


}
