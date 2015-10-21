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
 * 全局Application类
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
		// 应用程序启动时候创建RequestQueue
		myQueue = Volley.newRequestQueue(getApplicationContext());
		Log.d("debug", "Request Queue is Created!");
		webRequestManager = new WebRequestManager(this, getApplicationContext());

		// 图片缓存配置
		File cacheDir = new File(Environment.getExternalStorageDirectory().getPath()
				+ "/nercms-Schedule/cache/");
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
				.denyCacheImageMultipleSizesInMemory().memoryCache(new LruMemoryCache(2 * 1024 * 1024))
				.memoryCacheSize(2 * 1024 * 1024).discCacheSize(50 * 1024 * 1024)
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.discCache(new UnlimitedDiscCache(cacheDir))// 自定义缓存路径
				.discCacheFileCount(100).writeDebugLogs().build();
		ImageLoader.getInstance().init(config);// 全局初始化此配置

		// 异常捕获ACRA配置
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

			// ACRA支持的异常发送接口
			// 服务器只接受Get方式的URL请求，该方法废止
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

			// // 自定义异常上传接口
			// Volley实现
			// URL请求的长度受限，该方法废止
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

			// 在SD卡上重写crash日志
			// 然后一步上传到服务器
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
				FileWriter filerWriter = new FileWriter(logFile, true);// 后面这个参数代表是不是要接上文件中原来的数据，不进行覆盖
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

			// 开启上传
			// new HttpUploadTask(null, getApplicationContext()).execute(
			// logFile.getPath(), Constant.FILE_SERVER_BASE_URL
			// + "CrashReport/");

		}

	}

}
