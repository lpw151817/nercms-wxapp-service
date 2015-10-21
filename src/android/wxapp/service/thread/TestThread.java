package android.wxapp.service.thread;

import android.util.Log;

public class TestThread extends Thread {

	private boolean flag = true;

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public TestThread() {
	}

	@Override
	public void run() {
		while (flag) {
			for (int i = 0; i < 10; i++) {
				Log.v("TestThread", "test");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

			synchronized (this) {
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		Log.v("TestThread", "stop");
	}

}
