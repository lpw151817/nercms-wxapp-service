package android.wxapp.service.jerry.model.affair;

import java.util.List;

import android.wxapp.service.jerry.model.BaseRequestModel;

public class UpdateAffairInfoRequest extends BaseRequestModel{
	String aid;
List<CreateTaskRequestIds> pod;
List<CreateTaskRequestIds> rids;
String d,topic,et;
List<CreateTaskRequestAttachment> att;
public List<CreateTaskRequestIds> getPod() {
	return pod;
}
public void setPod(List<CreateTaskRequestIds> pod) {
	this.pod = pod;
}
public List<CreateTaskRequestIds> getRids() {
	return rids;
}
public void setRids(List<CreateTaskRequestIds> rids) {
	this.rids = rids;
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
public String getEt() {
	return et;
}
public void setEt(String et) {
	this.et = et;
}
public List<CreateTaskRequestAttachment> getAtt() {
	return att;
}
public void setAtt(List<CreateTaskRequestAttachment> att) {
	this.att = att;
}
public UpdateAffairInfoRequest() {
	super();
}
public UpdateAffairInfoRequest(String uid, String ic, String aid, List<CreateTaskRequestIds> pod,
		List<CreateTaskRequestIds> rids, String d, String topic, String et,
		List<CreateTaskRequestAttachment> att) {
	super(uid, ic);
	this.aid = aid;
	this.pod = pod;
	this.rids = rids;
	this.d = d;
	this.topic = topic;
	this.et = et;
	this.att = att;
}
public String getAid() {
	return aid;
}
public void setAid(String aid) {
	this.aid = aid;
}


}
