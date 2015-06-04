package android.wxapp.service.handler;

import android.os.Handler;
import android.util.Log;

public class MessageHandlerManager {
	// ����ģʽ
	private volatile static MessageHandlerManager messageHandlerManagerInstance = null;
	private MessageHandlerList messageHandlerList = null;

	/** ��ȡ���� */
	public static MessageHandlerManager getInstance() {
		// ���ʵ��,���ǲ����ھͽ���ͬ��������
		if (messageHandlerManagerInstance == null) {
			// ���������,��ֹ�����߳�ͬʱ����ͬ��������
			synchronized (MessageHandlerManager.class) {
				// ����˫�ؼ��
				if (messageHandlerManagerInstance == null) {
					messageHandlerManagerInstance = new MessageHandlerManager();
				}
			}
		}
		return messageHandlerManagerInstance;
	}

	/** ˽�й��캯�� */
	private MessageHandlerManager() {
		// ����RegistrantList�ĵ���ʵ��
		if (messageHandlerList == null) {
			// ���������,��ֹ�����߳�ͬʱ����ͬ��������
			synchronized (MessageHandlerManager.class) {
				// ����˫�ؼ��
				if (messageHandlerList == null) {
					messageHandlerList = new MessageHandlerList();
				}
			}
		}
	}

	public void printNum() {
		messageHandlerList.printNum();
	}

	// ע�ᣬ��ע��ʱ������ṩ����
	public void register(Handler h, int what, String className) {
		messageHandlerList.add(h, what, className);
		Log.i("handler register", "what:" + what + "\t className:" + className);
	}

	// ע����whatΪ��Ϣ��ʶ��Registrant����
	public void unregister(int what) {
		messageHandlerList.remove(what);
		Log.i("handler unregister", "what:" + what);
	}

	// ע����whatΪ��Ϣ��ʶ��ͬʱ����Ϊclass_name��Registrant����
	public void unregister(int what, String className) {
		messageHandlerList.remove(what, className);
		Log.i("handler unregister", "what:" + what + "\t className:" + className);
	}

//	// ע������handler
//	public void unregisterAll() {
//		messageHandlerList.clear();
//	}

	// ��Ϣ����ɷ�Ϊwhat����Ϣ��ʶ������������arg1��arg2��obj

	// ֪ͨ��WhatΪ��Ϣ��ʶ����Ϣ����
	public void sendMessage(int what) {
		messageHandlerList.sendMessage(what, 0, 0, null, "");
	}

	// ֪ͨ��WhatΪ��Ϣ��ʶ����Ϣ������ͬʱ������arg1����
	public void sendMessage(int what, int arg1) {
		messageHandlerList.sendMessage(what, arg1, 0, null, "");
	}

	// ֪ͨ��WhatΪ��Ϣ��ʶ����Ϣ������ͬʱ������obj����
	public void sendMessage(int what, Object obj) {
		Log.i("what", Integer.toString(what));
		messageHandlerList.sendMessage(what, 0, 0, obj, "");
	}

	// ֪ͨ��WhatΪ��Ϣ��ʶ����Ϣ������ͬʱ������arg1��arg2����
	public void sendMessage(int what, int arg1, int arg2) {
		messageHandlerList.sendMessage(what, arg1, arg2, null, "");
	}

	// ֪ͨ��WhatΪ��Ϣ��ʶ����Ϣ������ͬʱ������arg1��arg2��obj����
	public void sendMessage(int what, int arg1, int arg2, int obj) {
		messageHandlerList.sendMessage(what, arg1, arg2, obj, "");
	}

	// ֪ͨ��WhatΪ��Ϣ��ʶ����Ϣ�����������Ϣֻ������Ϊclass_name���ദ��
	public void sendMessage(int what, String class_name) {
		messageHandlerList.sendMessage(what, 0, 0, null, class_name);
	}

	// ֪ͨ��WhatΪ��Ϣ��ʶ����Ϣ������ͬʱ������arg1���ݣ������Ϣֻ������Ϊclass_name���ദ��
	public void sendMessage(int what, int arg1, String class_name) {
		messageHandlerList.sendMessage(what, arg1, 0, null, class_name);
	}

	// ֪ͨ��WhatΪ��Ϣ��ʶ����Ϣ������ͬʱ������obj���ݣ������Ϣֻ������Ϊclass_name���ദ��
	public void sendMessage(int what, Object obj, String class_name) {
		messageHandlerList.sendMessage(what, 0, 0, obj, class_name);
		Log.i("handler sendMessage", "what:" + what + "\t className:" + class_name);
	}

	// ֪ͨ��WhatΪ��Ϣ��ʶ����Ϣ������ͬʱ������arg1��arg2���ݣ������Ϣֻ������Ϊclass_name���ദ��
	public void sendMessage(int what, int arg1, int arg2, String class_name) {
		messageHandlerList.sendMessage(what, arg1, arg2, null, class_name);
	}

	// ֪ͨ��WhatΪ��Ϣ��ʶ����Ϣ������ͬʱ������arg1��arg2��obj���ݣ������Ϣֻ������Ϊclass_name���ദ��
	public void sendMessage(int what, int arg1, int arg2, int obj, String class_name) {
		messageHandlerList.sendMessage(what, arg1, arg2, obj, class_name);
	}
}
