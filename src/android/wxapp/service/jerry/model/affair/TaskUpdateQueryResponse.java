package android.wxapp.service.jerry.model.affair;

import java.util.List;

public class TaskUpdateQueryResponse {
	String s;
	// ��Ҫ���µ�����id
	List<TaskUpdateQueryResponseAffairs> affairs;
	// ���µ�ʱ���
	String tsp;

	public TaskUpdateQueryResponse(String s, List<TaskUpdateQueryResponseAffairs> affairs, String tsp) {
		super();
		this.s = s;
		this.affairs = affairs;
		this.tsp = tsp;
	}

	public String getS() {
		return s;
	}

	public void setS(String s) {
		this.s = s;
	}

	public List<TaskUpdateQueryResponseAffairs> getAs() {
		return affairs;
	}

	public void setAs(List<TaskUpdateQueryResponseAffairs> affairs) {
		this.affairs = affairs;
	}

	public String getTsp() {
		return tsp;
	}

	public void setTsp(String tsp) {
		this.tsp = tsp;
	}

	public TaskUpdateQueryResponse() {
		super();
	}

}
