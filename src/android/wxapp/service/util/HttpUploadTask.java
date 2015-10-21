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
 * Http�����ϴ��첽Task��
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
		String result = "�ļ��ϴ�ʧ��";
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
	 * ? ?Description?:uploadFile?�ϴ��ļ�??????? ?@param?srcPath?�ϴ��ļ�·��??????
	 * 
	 * @param?uploadUrl?�������˽��յ�ַ????? ?@return?String?????? ?
	 */
	public String post(String pathToOurFile, String urlServer) throws ClientProtocolException,
			IOException, JSONException {
		String result = null;
		if (!URLUtil.isNetworkUrl(urlServer)) {
			result = "��������ַ��Ч";
			return result;
		}

		HttpClient httpclient = new DefaultHttpClient();
		// ����ͨ��Э��汾
		httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);

		HttpPost httppost = new HttpPost(urlServer);
		File file = new File(pathToOurFile);

		// MultipartEntity mpEntity = new MultipartEntity(); //�ļ�����
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
		// name="userfile" /> ��Ӧ��

		httppost.setEntity(multipartContent);
		System.out.println("executing request " + httppost.getRequestLine());

		HttpParams params = httpclient.getParams();
		HttpConnectionParams.setConnectionTimeout(params, 3000);
		HttpResponse response = httpclient.execute(httppost);
		HttpEntity resEntity = response.getEntity();

		System.out.println(response.getStatusLine());// ͨ��Ok

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
				mytext.setText("�����ϴ��ɹ�");
			}
		} else {
			MessageHandlerManager.getInstance().sendMessage(Constant.FILE_UPLOAD_FAIL,
					context.getClass().getSimpleName());
			if (mytext != null) {
				mytext.setText("�����ϴ�ʧ��");
			}
		}
		pd.dismiss();
	}

	@Override
	protected void onCancelled() {
		// TODO Auto-generated method stub
		super.onCancelled();
		if (mytext != null) {
			mytext.setText("��ȡ���ϴ�");
		}
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();

		this.pd = new ProgressDialog(context);
		pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		pd.setMessage("�����ϴ��� ...");
		pd.setCancelable(false);
		pd.show();

		if (mytext != null) {
			mytext.setText("�ϴ���");
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
