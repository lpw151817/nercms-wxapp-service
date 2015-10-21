package android.wxapp.service.util;

import java.io.File;
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.webkit.URLUtil;
import android.widget.TextView;
import android.wxapp.service.handler.MessageHandlerManager;
import android.wxapp.service.util.CustomMultipartEntity.ProgressListener;

/**
 * Http附件上传异步Task类
 * 
 * @author YuWei
 * 
 */

public class HttpUploadTask extends AsyncTask<String, Integer, String> {
	private TextView mytext;
	private long totalSize;
	private ProgressDialog pd;
	private Context context;

	public HttpUploadTask(TextView mytext, Context context) {
		this.mytext = mytext;
		this.context = context;
	}

	@Override
	protected String doInBackground(String... params) {
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>in upload");
		String result = "文件上传失败";
		try {
			result = post(params[0], params[1]);
			return result;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * ? ?Description?:uploadFile?上传文件??????? ?@param?srcPath?上传文件路径??????
	 * 
	 * @param?uploadUrl?服务器端接收地址????? ?@return?String?????? ?
	 */
	public String post(String pathToOurFile, String urlServer) throws ClientProtocolException,
			IOException, JSONException {
		String result = null;
		if (!URLUtil.isNetworkUrl(urlServer)) {
			result = "服务器地址无效";
			return result;
		}

		HttpClient httpclient = new DefaultHttpClient();
		// 设置通信协议版本
		httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);

		HttpPost httppost = new HttpPost(urlServer);
		File file = new File(pathToOurFile);

		// MultipartEntity mpEntity = new MultipartEntity(); //文件传输
		FileBody cbFile = new FileBody(file);

		CustomMultipartEntity multipartContent = new CustomMultipartEntity(new ProgressListener() {
			@Override
			public void transferred(long num) {
				// TODO Auto-generated method stub
				publishProgress((int) ((num / (float) totalSize) * 100));
			}

		});

		multipartContent.addPart("data", cbFile);
		totalSize = multipartContent.getContentLength();
		// mpEntity.addPart("userfile", cbFile); // <input type="file"
		// name="userfile" /> 对应的

		httppost.setEntity(multipartContent);
		System.out.println("executing request " + httppost.getRequestLine());

		HttpParams params = httpclient.getParams();
		HttpConnectionParams.setConnectionTimeout(params, 3000);
		HttpResponse response = httpclient.execute(httppost);
		HttpEntity resEntity = response.getEntity();

		System.out.println(response.getStatusLine());// 通信Ok

		if (response.getStatusLine().getStatusCode() == 200)
			result = "success";
		else
			result = "failed";

		httpclient.getConnectionManager().shutdown();
		return result;

	}

	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>out upload");
		if (result.equals("success")) {
			MessageHandlerManager.getInstance().sendMessage(Constant.FILE_UPLOAD_SUCCESS,
					context.getClass().getSimpleName());
			if (mytext != null) {
				mytext.setText("附件上传成功");
			}
		} else {
			MessageHandlerManager.getInstance().sendMessage(Constant.FILE_UPLOAD_FAIL,
					context.getClass().getSimpleName());
			if (mytext != null) {
				mytext.setText("附件上传失败");
			}
		}
		pd.dismiss();
	}

	@Override
	protected void onCancelled() {
		// TODO Auto-generated method stub
		super.onCancelled();
		if (mytext != null) {
			mytext.setText("已取消上传");
		}
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();

		this.pd = new ProgressDialog(context);
		pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		pd.setMessage("附件上传中 ...");
		pd.setCancelable(false);
		pd.show();

		if (mytext != null) {
			mytext.setText("上传中");
		}
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		// TODO Auto-generated method stub
		super.onProgressUpdate(values);
		pd.setProgress((int) (values[0]));
		System.out.println(values[0]);
	}

}
