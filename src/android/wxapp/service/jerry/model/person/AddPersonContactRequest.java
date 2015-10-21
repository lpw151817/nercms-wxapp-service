package android.wxapp.service.jerry.model.person;

import java.util.List;

public class AddPersonContactRequest {
String uid,ic,pid;
List<Contacts> contacts;
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
public String getPid() {
	return pid;
}
public void setPid(String pid) {
	this.pid = pid;
}
public List<Contacts> getContacts() {
	return contacts;
}
public void setContacts(List<Contacts> contacts) {
	this.contacts = contacts;
}
public AddPersonContactRequest(String uid, String ic, String pid, List<Contacts> contacts) {
	super();
	this.uid = uid;
	this.ic = ic;
	this.pid = pid;
	this.contacts = contacts;
}
public AddPersonContactRequest() {
	super();
}

}
