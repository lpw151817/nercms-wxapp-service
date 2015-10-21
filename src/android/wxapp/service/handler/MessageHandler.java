package android.wxapp.service.handler;

import android.os.Handler;
import android.os.Message;

public class MessageHandler {
	private Handler handler; // ������Ϣ�ľ��
	private int what; // ��ϢID
	private String className; // ������Ϣ��������

	MessageHandler(Handler handler, int what, String name) {
		this.handler = handler;
		this.what = what;
		this.className = name;
	}

	@Override
	protected void finalize() {
		clear();
	}

	/** ֵ����ж� */
	public boolean valueEqual(Handler handler, int what, String name) {
		if (this.handler.equals(handler) && this.what == what
				&& this.className.equals(name))
			return true;
		return false;
	}

	/** ֵ����ж� */
	public boolean valueEqual(MessageHandler messageHandler) {
		return valueEqual(messageHandler.handler, messageHandler.what,
				messageHandler.className);
	}

	/** ֪ͨhandler������ĵ����鷢������Ҫ��Ӧ�Ľ��洦�� */
	void sendMessage(int arg1, int arg2, Object obj) {
		if (handler == null)
			return;
		Message message = Message.obtain();
		message.what = what;
		message.obj = obj;
		message.arg1 = arg1;
		message.arg2 = arg2;
		handler.sendMessage(message);
	}

	/** ��� */
	void clear() {
		handler = null;
		className = null;
	}

	/** ��ȡ��ϢID */
	int getMessageWhat() {
		return what;
	}

	/** ��ȡ������Ϣ������ */
	String getClassName() {
		return className == null ? "" : className;
	}

}
