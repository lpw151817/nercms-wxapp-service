package android.wxapp.service.jerry.model.affair;

import java.util.List;

public class TaskUpdateQueryResponse {
String s;
//��Ҫ���µ�����id
List<TaskUpdateQueryResponseAids> aids;
//���µ�ʱ���
String tsp;
public String getS() {
	return s;
}
public void setS(String s) {
	this.s = s;
}
public List<TaskUpdateQueryResponseAids> getAids() {
	return aids;
}
public void setAids(List<TaskUpdateQueryResponseAids> aids) {
	this.aids = aids;
}
public String getTsp() {
	return tsp;
}
public void setTsp(String tsp) {
	this.tsp = tsp;
}
public TaskUpdateQueryResponse(String s, List<TaskUpdateQueryResponseAids> aids, String tsp) {
	super();
	this.s = s;
	this.aids = aids;
	this.tsp = tsp;
}
public TaskUpdateQueryResponse() {
	super();
}

}
