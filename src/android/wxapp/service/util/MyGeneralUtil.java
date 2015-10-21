package android.wxapp.service.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;

/**
 * ���ù�����
 * 
 * @author WEIHAO
 * 
 */

public class MyGeneralUtil {

	/**
	 * ��ȡ����ʱ����1970��1��1��0��0�������
	 * 
	 * @param dateTime
	 *            ʱ��
	 * @return ����ʱ����1970��1��1��0��0�������
	 */
	@SuppressLint("SimpleDateFormat")
	public static String getInterval(String dateTime) {
		// ʱ���ʽ��������
		SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		// ����ʱ��
		Date end = null;
		try {
			end = mSimpleDateFormat.parse(dateTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		// �����������ʳ���1000
		return Long.toString(end.getTime() / 1000);
	}

}
