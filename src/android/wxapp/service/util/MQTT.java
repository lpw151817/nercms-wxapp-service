package android.wxapp.service.util;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import android.util.Log;
import android.wxapp.service.handler.MessageHandlerManager;

public class MQTT {
	public static String SERVER_URL = "tcp://202.114.66.77:1883";
	// public static String CLIENT_ID = MqttClient.generateClientId();//���ɳ���23�ֽ�
	public static String CLIENT_ID = ""; // WeiHao��Ĭ��Ϊ�գ������ʼ�����û�Login֮�����
	public final static String SUBSCRIBE_TOPIC_PREFIX = "nercms/schedule/";
	private final int SUBSCRIBE_QOS = 1;
	// Qos 0: ����һ��,��Ϣ������ȫ�����ײ�����,�ᷢ����Ϣ��ʧ���ظ�;
	// Qos 1: ����һ��,ȷ����Ϣ����,����Ϣ�ظ����ܻᷢ��;
	// Qos 2: ֻ��һ��, ȷ����Ϣ����ֻ����һ��.

	private final int CONNECTION_TIMEOUT = 10;// seconds
	private final int KEEP_ALIVE_INTERVAL = 45;// seconds
	private final long PUBLISH_TIMEOUT = 10000;// millisecond
	private final long DISCONNECTION_TIMEOUT = 10000;// millisecond

	private volatile static MQTT _unique_instance = null;

	public static MQTT get_instance() throws MqttException {
		if (null == _unique_instance) {
			synchronized (MQTT.class) {
				if (null == _unique_instance) {
					_unique_instance = new MQTT();
				}
			}
		}

		return _unique_instance;
	}

	private MqttClient _client = null;

	private MQTT() throws MqttException {
		// ����MQTT�ͻ���
		// try {
		// ����
		_client = new MqttClient(SERVER_URL, CLIENT_ID, new MemoryPersistence());

		// ���ûص�
		MQTTClientCallback callback = new MQTTClientCallback();
		_client.setCallback(callback);

		// ��ʼ�������Ӽ�����
		init();
		// } catch (MqttException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// Log.v("MQTT", "create mqtt client error " + e.toString());
		// }
	}

	private final String WILL_TOPIC = "mqtt/errors";
	private final String WILL_MESSAGE = "connection crashed";
	private Thread _check_thread = null;
	private boolean _check_thread_exit_flag = false;

	private void init() {
		connect();

		_check_thread = new Thread() {
			@Override
			public void run() {
				while (false == _check_thread_exit_flag) {
					if (false == _client.isConnected()) {
						disconnect();
						connect();
					}

					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		};
		_check_thread.start();
	}

	// ����ӿ�
	public void close() {
		disconnect();

		_check_thread_exit_flag = true;

		try {
			_check_thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		_check_thread = null;
	}

	private void connect() {
		synchronized (MQTT.class) {
			if (null == _client)
				return;

			try {
				if (false == _client.isConnected()) {
					// ����
					MqttConnectOptions options = new MqttConnectOptions();
					// cleanSessionΪtrue��ͻ�����������ʱ������ȥ�ͻ������κξ�Ԥ�������ͻ����Ͽ�����ʱ�����ȥ�ͻ����ڻỰ�ڼ䴴�����κ���Ԥ����
					// Ϊfalse��ͻ����������κ�Ԥ�����ᱻ������ͻ���������֮ǰ���Ѵ��ڵ�����Ԥ�������ͻ����Ͽ�����ʱ������Ԥ���Ա��ֻ״̬��
					// cleanSessionΪtrueʱ�ͻ������ڻỰ���������ڴ���Ԥ���ͽ��շ���
					// cleanSessionΪfalseʱ�ǳ־�Ԥ�������ۿͻ����Ƿ����Ӷ���Ԥ�����ֻ״̬�����ͻ�������ʱ�������κ�δ���ݵķ�������������֮����޸Ĵ��ڻ״̬��Ԥ������
					// Ҫ���Ĵ����Ե����ã����뽫�ͻ����Ͽ����ӣ�Ȼ�����������ӿͻ��������������ʽ��ʹ��
					// cleanSession=false ����Ϊ
					// cleanSession=true����ô�˿ͻ�����ǰ������Ԥ���Լ���δ���յ����κη���������������

					// 2014-6-10 false->true
					options.setCleanSession(true);
					options.setConnectionTimeout(CONNECTION_TIMEOUT);// �������ӳ�ʱʱ��
					options.setKeepAliveInterval(KEEP_ALIVE_INTERVAL);// �����������
					options.setWill(_client.getTopic(WILL_TOPIC), WILL_MESSAGE.getBytes(), 2, true);
					_client.connect(options);

					options = null;
				}

				if (true == _client.isConnected()) {
					Log.v("MQTT", "mqtt connect " + _client.isConnected());

					_client.subscribe(SUBSCRIBE_TOPIC_PREFIX + CLIENT_ID, SUBSCRIBE_QOS);

					Log.v("MQTT", "mqtt subscribe " + _client.isConnected());
				}
			} catch (MqttSecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MqttException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void disconnect() {
		synchronized (MQTT.class) {
			if (null == _client)
				return;

			try {
				_client.disconnect(DISCONNECTION_TIMEOUT);
			} catch (MqttException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Log.v("MQTT", "mqtt disconnect " + _client.isConnected());
		}
	}

	// ����ӿ�
	public boolean publish_message(String message_topic, String content, int QoS) {
		synchronized (MQTT.class) {
			if (null == _client || false == _client.isConnected()) {
				init();
			}

			if (null == _client || false == _client.isConnected()) {
				return false;
			}

			// ������Ϣ����ȡ��ִ
			try {
				MqttTopic topic = _client.getTopic(message_topic);// ����topic

				// MqttDeliveryToken token = topic.publish(content.getBytes(),
				// QoS, false);
				MqttMessage message = new MqttMessage();
				message.setQos(QoS);
				message.setRetained(false);// �Ƿ��ڷ������б�����Ϣ��
				message.setPayload(content.getBytes());

				// long t = System.currentTimeMillis();
				MqttDeliveryToken token = topic.publish(message);
				// Log.v("Baidu", "mqtt 1 " + (System.currentTimeMillis() - t));
				while (false == token.isComplete()) {
					token.waitForCompletion(PUBLISH_TIMEOUT);
				}
				// Log.v("Baidu", "mqtt 2 " + (System.currentTimeMillis() - t));

				if (true == token.isComplete()) {
					// Log.v("MQTT", "client(" + _client.getClientId() +
					// ") Publishing " + message.toString() + " on topic " +
					// topic.getName() + " with QoS = " + message.getQos());
				} else {
					disconnect();
					connect();
				}

				return token.isComplete();
			} catch (MqttPersistenceException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
				Log.v("MQTT", "mqtt publish error 1: " + e.toString());
			} catch (MqttException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
				Log.v("MQTT", "mqtt publish error 2: " + e.toString());
			}
		}

		return false;
	}

	public class MQTTClientCallback implements MqttCallback {
		@Override
		public void messageArrived(String topic, MqttMessage message) {
			Log.i("MQTT", "Recv message: " + message.toString() + " on topic: " + topic.toString());

			// 2014-4-21 WeiHao�������յ�������֪֮ͨ��ľ��崦��
			// 1. �жϸ�����������
			String pushMessage = message.toString();
			// TODO �õ�Message����д���
			if (pushMessage.contains(":")) {
				String pushMessageType = pushMessage.substring(0, pushMessage.indexOf(":"));
				if (pushMessageType.equalsIgnoreCase("Test")) {
					Log.i("MQTT", pushMessageType);
				}

				// 2. �������ͣ�֪ͨ��UI�̣߳���UI�߳̽��յ������Ӧ��request��������Ϣ�Ļ�ȡ
				if (pushMessageType.equalsIgnoreCase("New Task")
						|| pushMessageType.equalsIgnoreCase("Task Complete")
						|| pushMessageType.equalsIgnoreCase("Task Modified")
						|| pushMessageType.equalsIgnoreCase("Task Timeout")) {
					Log.i("MQTT", pushMessageType);
					MessageHandlerManager.getInstance().sendMessage(Constant.MQTT_NEW_TASK, "Task");
				} else if (pushMessageType.equalsIgnoreCase("New Feedback")) {
					Log.i("MQTT", pushMessageType);
					MessageHandlerManager.getInstance().sendMessage(Constant.MQTT_NEW_FEEDBACK, "Task");
				} else if (pushMessageType.equalsIgnoreCase("New Message")) {
					Log.i("MQTT", pushMessageType);
					MessageHandlerManager.getInstance().sendMessage(Constant.MQTT_NEW_MESSAGE, "Main");
				}

			}

		}

		@Override
		public void connectionLost(Throwable cause) {
			Log.v("MQTT",
					"connection lost due to " + cause.getMessage() + ", "
							+ ((MqttException) cause).getReasonCode() + ", "
							+ ((MqttException) cause).getCause());

			disconnect();
			connect();
		}

		@Override
		public void deliveryComplete(IMqttDeliveryToken token) {
			// Log.v("MQTT", "delivery complete: " + token.toString());
		}
	}

}
