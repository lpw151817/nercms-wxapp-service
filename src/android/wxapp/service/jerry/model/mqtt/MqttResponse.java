package android.wxapp.service.jerry.model.mqtt;

public class MqttResponse {
	String type, id;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public MqttResponse(String type, String id) {
		super();
		this.type = type;
		this.id = id;
	}

	public MqttResponse() {
		super();
	}

}
