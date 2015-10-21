package android.wxapp.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.acra.ACRA;
import org.acra.ErrorReporter;
import org.acra.ReportField;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;
import org.acra.collector.CrashReportData;
import org.acra.sender.ReportSender;
import org.acra.sender.ReportSenderException;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.wxapp.service.request.WebRequestManager;
import android.wxapp.service.util.Constant;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

/**
 * ȫ��Application��
 * 
 * @author WEIHAO
 * @version
 * 
 */

@ReportsCrashes(formKey = "", mailTo = "ponyw@qq.com", customReportContent = {
		ReportField.APP_VERSION_NAME, ReportField.APP_VERSION_CODE, ReportField.ANDROID_VERSION,
		ReportField.PHONE_MODEL, ReportField.CUSTOM_DATA, ReportField.STACK_TRACE, ReportField.LOGCAT }, mode = ReportingInteractionMode.SILENT, forceCloseDialogAfterToast = false)
public class AppApplication extends Application {

	public static AppApplication appInstance;
	public RequestQueue myQueue;

	public WebRequestManager webRequestManager;

	@Override
	public void onCreate() {
		// Ӧ�ó�������ʱ�򴴽�RequestQueue
		myQueue = Volley.newRequestQueue(getApplicationContext());
		Log.d("debug", "Request Queue is Created!");
		webRequestManager = new WebRequestManager(this, getApplicationContext());

		// ͼƬ��������
		File cacheDir = new File(Environment.getExternalStorageDirectory().getPath()
				+ "/nercms-Schedule/cache/");
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
				.denyCacheImageMultipleSizesInMemory().memoryCache(new LruMemoryCache(2 * 1024 * 1024))
				.memoryCacheSize(2 * 1024 * 1024).discCacheSize(50 * 1024 * 1024)
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.discCache(new UnlimitedDiscCache(cacheDir))// �Զ��建��·��
				.discCacheFileCount(100).writeDebugLogs().build();
		ImageLoader.getInstance().init(config);// ȫ�ֳ�ʼ��������

		// �쳣����ACRA����
		ACRA.init(appInstance);
		ErrorReporter.getInstance().setReportSender(new CrashReportSender(getApplicationContext()));
		super.onCreate();

	}

	public AppApplication() {
		appInstance = this;
	}

	public static AppApplication getInstance() {
		return appInstance;
	}

	public static Context getContext() {
		return getInstance();
	}

	// 2014-6-24
	public class CrashReportSender implements ReportSender {
		private static final String TAG = "CrashReportSender";
		public String formUri = Constant.SERVER_CRASH_REPORT;

		private Context context = null;

		public CrashReportSender(Context context) {
			this.context = context;
		}

		@Override
		public void send(CrashReportData arg0) throws ReportSenderException {

			// ACRA֧�ֵ��쳣���ͽӿ�
			// ������ֻ����Get��ʽ��URL���󣬸÷�����ֹ
			// EmailIntentSender emailSender = new EmailIntentSender(context);
			// emailSender.send(arg0);
			// Map<ReportField, String> maping = new HashMap<ReportField,
			// String>();
			// maping.put(ReportField.STACK_TRACE,
			// arg0.getProperty(ReportField.STACK_TRACE));
			// try {
			// formUri = formUri + URLEncoder.encode(arg0.toString(), "UTF-8");
			// } catch (UnsupportedEncodingException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
			// HttpSender httpSender = new HttpSender(
			// org.acra.sender.HttpSender.Method.PUT, Type.JSON, formUri,
			// maping);
			// httpSender.send(arg0);

			// String crashString =
			// arg0.getProperty(ReportField.ANDROID_VERSION);
			//
			// try {
			// crashString = URLEncoder.encode(crashString, "UTF-8");
			// } catch (UnsupportedEncodingException e) {
			// e.printStackTrace();
			// }

			// // �Զ����쳣�ϴ��ӿ�
			// Volleyʵ��
			// URL����ĳ������ޣ��÷�����ֹ
			// String reportUrl = formUri + "?param=" + crashString;
			// Log.v("AppApplication", reportUrl);
			//
			// JsonObjectRequest reportRequest = new
			// JsonObjectRequest(reportUrl,
			// null, new Response.Listener<JSONObject>() {
			//
			// @Override
			// public void onResponse(JSONObject response) {
			// // TODO Auto-generated method stub
			//
			// }
			// }, new Response.ErrorListener() {
			//
			// @Override
			// public void onErrorResponse(VolleyError error) {
			// // TODO Auto-generated method stub
			// Log.v("AppApplication", error.getMessage());
			// }
			// });
			// webRequestManager.sendCrashReport(reportRequest);

			// ��SD������дcrash��־
			// Ȼ��һ���ϴ���������
			// ..

			String appVersionName = arg0.getProperty(ReportField.APP_VERSION_NAME);
			String appVersionCode = arg0.getProperty(ReportField.APP_VERSION_CODE);
			String androidVersion = arg0.getProperty(ReportField.ANDROID_VERSION);
			String phoneModel = arg0.getProperty(ReportField.PHONE_MODEL);
			String customData = arg0.getProperty(ReportField.CUSTOM_DATA);
			String stackTrace = arg0.getProperty(ReportField.STACK_TRACE);

			String logName = "CrashReport_"
					+ new SimpleDateFormat("yyyyMMddHHmmss")
							.format(new Date(System.currentTimeMillis())) + ".txt";
			File logFile = new File(Environment.getExternalStorageDirectory().getPath()
					+ "/nercms-Schedule/Log/", logName);
			if (!logFile.exists()) {
				try {
					logFile.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			try {
				FileWriter filerWriter = new FileWriter(logFile, true);// ����������������ǲ���Ҫ�����ļ���ԭ�������ݣ������и���
				BufferedWriter bufWriter = new BufferedWriter(filerWriter);
				bufWriter.write("APP_VERSION_NAME=" + appVersionName);
				bufWriter.newLine();
				bufWriter.write("APP_VERSION_CODE=" + appVersionCode);
				bufWriter.newLine();
				bufWriter.write("ANDROID_VERSION=" + androidVersion);
				bufWriter.newLine();
				bufWriter.write("PHONE_MODEL=" + phoneModel);
				bufWriter.newLine();
				bufWriter.write("CUSTOM_DATA=" + customData);
				bufWriter.newLine();
				bufWriter.write("STACK_TRACE=" + stackTrace);
				bufWriter.newLine();
				bufWriter.close();
				filerWriter.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// �����ϴ�
			// new HttpUploadTask(null, getApplicationContext()).execute(
			// logFile.getPath(), Constant.FILE_SERVER_BASE_URL
			// + "CrashReport/");

		}

	}

}
