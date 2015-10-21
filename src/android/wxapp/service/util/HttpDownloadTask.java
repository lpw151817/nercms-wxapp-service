package android.wxapp.service.util;

import java.io.File;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.app.DownloadManager.Request;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;
import android.wxapp.service.handler.MessageHandlerManager;

/**
 * Http附件下载异步类
 * 
 * @author YuWei
 * 
 */

@SuppressLint("NewApi")
public class HttpDownloadTask extends AsyncTask<String, Integer, Boolean> {

	private String TAG = "HttpDownloadTask";

	// 2014-5-28 WeiHao 新增：用户在下载完成时返回给界面的文件名
	private String mediaName;

	// 接受下载完成后的intent
	private class DownloadCompleteReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
				Log.i(TAG,
						"download completely "
								+ intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1));
			}
		}
	}

	private Context context;
	private DownloadManager _download_manager;
	// private DownloadCompleteReceiver _download_receiver;
	private DownloadManager.Request _download_request;

	public static long _download_id = -1;

	// 该方法运行在UI线程当中,并且运行在UI线程当中 可以对UI空间进行设置

	public HttpDownloadTask(Context context) {
		this.context = context;
	}

	@Override
	protected void onPreExecute() {
		_download_id = 0;// 表明下载任务已创建
		Log.i(TAG, "start download new application.");

		_download_manager = (DownloadManager) context.getSystemService(context.DOWNLOAD_SERVICE);

		// _download_receiver = new DownloadCompleteReceiver();

		Toast.makeText(context, "准备下载", Toast.LENGTH_SHORT).show();
	}

	private void delete_old_file(String filepath, String filename) {
		File file = new File(Environment.getExternalStorageDirectory() + "/tencent/1.jpg");

		if (file == null || false == file.exists() || true == file.isDirectory())
			return;

		file.delete();
	}

	private void start_downloading(String url, String filepath, String filename) {
		System.out.println("begin download!!!!!!");
		try {
			delete_old_file(filepath, filename);

			Log.i(TAG, "download url " + url);
			// String url = "http://10.0.2.2/android/film/G3.mp4";
			_download_request = new DownloadManager.Request(Uri.parse(url));

			// 设置下载网络
			_download_request.setAllowedNetworkTypes(Request.NETWORK_MOBILE | Request.NETWORK_WIFI);
			// 漫游时不下载
			_download_request.setAllowedOverRoaming(false);

			// 在通知栏中显示，否则后台静默下载
			// _download_request.setShowRunningNotification(true);
			// 显示下载界面
			_download_request.setVisibleInDownloadsUi(true);

			// 设在下载路径为sdcard的目录下的schedule文件夹
			_download_request.setDestinationInExternalPublicDir(filepath, filename);

			// 支持无TF卡更新
			// 下载路径null，则默认为data/data/com.android.provider.downloads/cache/
			// _download_request.setDestinationInExternalFilesDir(context, null,
			// "1.jpg");

			_download_request.setTitle("下载附件");

			// 加入下载队列
			_download_id = _download_manager.enqueue(_download_request);

			// context.registerReceiver(_download_receiver, new
			// IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
		} catch (Exception e) {
			Log.i(TAG, "download error " + e.toString());
		}
	}

	// 查询下载状态
	int query_status(long id) {
		Cursor cursor = _download_manager.query(new DownloadManager.Query().setFilterById(id));

		if (null == cursor || false == cursor.moveToFirst())// 必须调用moveToFirst
		{
			Log.i(TAG, "query cursor is null");
			return DownloadManager.STATUS_SUCCESSFUL;
		}

		int status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
		cursor.close();
		cursor = null;
		return status;
	}

	// 该方法并不运行在UI线程当中，主要用于异步操作，所有在该方法中不能对UI当中的空间进行设置和修改
	@SuppressLint("NewApi")
	@Override
	protected Boolean doInBackground(String... params) {

		mediaName = params[2];

		start_downloading(params[0], params[1], params[2]);

		Log.i(TAG, "Download app id: " + _download_id);

		if (0 >= _download_id)
			return false;

		int status = -1;
		while (true) {
			status = query_status(_download_id);

			publishProgress(status);

			if (DownloadManager.STATUS_FAILED == status) {
				Log.i(TAG, "Download app fail " + status);
				_download_manager.remove(_download_id);
				return false;
			} else if (DownloadManager.STATUS_PAUSED == status
					|| DownloadManager.STATUS_PENDING == status
					|| DownloadManager.STATUS_RUNNING == status) {
				// Log.v("Baidu", "Downloading app " + status);
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (DownloadManager.STATUS_SUCCESSFUL == status) {
				Log.i(TAG, "Download app success " + status);
				break;
			}
		}

		return true;
	}

	// 在doInBackground方法执行结束之后在运行，并且运行在UI线程当中 可以对UI空间进行设置
	@Override
	protected void onPostExecute(Boolean result) {
		// Log.v("Baidu", "install new apk 1");

		// context.unregisterReceiver(_download_receiver);
		if (result) {
			// 2014-5-28 通知相应的页面刷新显示
			MessageHandlerManager.getInstance().sendMessage(Constant.FILE_DOWNLOAD_SUCCESS, mediaName,
					context.getClass().getSimpleName());
			Toast.makeText(context, "下载完成", Toast.LENGTH_SHORT).show();
		} else {
			MessageHandlerManager.getInstance().sendMessage(Constant.FILE_DOWNLOAD_FAIL,
					context.getClass().getName());
			Toast.makeText(context, "下载失败", Toast.LENGTH_SHORT).show();
		}
		_download_id = -1;
	}

	// onProgressUpdate是在UI线程中执行，所有可以对UI空间进行操作
	@Override
	protected void onProgressUpdate(Integer... values) {
		// Log.v("Baidu", "download onProgressUpdate " + values[0]);
	}

	// onCancelled方法用于在取消执行中的任务时更改UI
	@Override
	protected void onCancelled() {
		_download_id = -1;
	}
}