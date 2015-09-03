package android.wxapp.service.jerry.model.affair;

import java.io.Serializable;

public class CreateTaskRequestAttachment implements Serializable{
String at,u;

public String getAt() {
	return at;
}

public void setAt(String at) {
	this.at = at;
}

public String getU() {
	return u;
}

public void setU(String u) {
	this.u = u;
}

public CreateTaskRequestAttachment(String at, String u) {
	super();
	this.at = at;
	this.u = u;
}

public CreateTaskRequestAttachment() {
	super();
}

}
