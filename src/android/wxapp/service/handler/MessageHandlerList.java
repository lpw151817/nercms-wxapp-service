package android.wxapp.service.handler;

import java.util.ArrayList;
import android.os.Handler;

public class MessageHandlerList {
	private ArrayList<MessageHandler> messageHandlerList = new ArrayList<MessageHandler>();

	public synchronized void printNum() {

	}

	/** ��ӦMessageHandler�Ƿ������ */
	protected synchronized boolean exist(Handler handler, int what, String name) {
		for (int i = 0; i < messageHandlerList.size(); i++) {
			if (messageHandlerList.get(i).valueEqual(handler, what, name))
				return true;
		}
		return false;
	}

	/** ��ӦMessageHandler�Ƿ������ */
	protected synchronized boolean exist(MessageHandler messageHandler) {
		for (int i = 0; i < messageHandlerList.size(); i++) {
			if (messageHandlerList.get(i).valueEqual(messageHandler))
				return true;
		}
		return false;
	}

	/** ����³�Ա */
	public synchronized void add(Handler handler, int what, String className) {
		if (!exist(handler, what, className))
			messageHandlerList
					.add(new MessageHandler(handler, what, className));
	}

	/** ����whatɾ����Ա */
	public synchronized void remove(int what) {
		remove(what, "");
	}

	/** ����what������ɾ����Ա */
	public synchronized void remove(int what, String className) {
		for (int i = 0; i < messageHandlerList.size(); i++) {
			MessageHandler messageHandler = messageHandlerList.get(i);
			if (messageHandler.getMessageWhat() == what
					&& ((className.equals("") || className == null) ? true
							: messageHandler.getClassName().equals(className))) {
				messageHandler.clear();
				messageHandlerList.remove(i);
			}
		}
	}

	/** ɾ�����г�Ա */
	public synchronized void clear() {
		while (!messageHandlerList.isEmpty()) {
			messageHandlerList.get(0).clear();
			messageHandlerList.remove(0);
		}
	}

	/** ��������className����Ϣ��־what��֪ͨ��Ӧ���ദ����Ϣ����classNameΪ�գ���ֻƥ��what */
	public synchronized void sendMessage(int what, int arg1, int arg2,
			Object obj, String className) {
		for (int i = 0; i < messageHandlerList.size(); i++) {
			MessageHandler messageHandler = messageHandlerList.get(i);
			if (messageHandler.getMessageWhat() == what
					&& ((className.equals("") || className == null) ? true
							: messageHandler.getClassName().equals(className))) {
				messageHandler.sendMessage(arg1, arg2, obj);
			}
		}
	}
}
