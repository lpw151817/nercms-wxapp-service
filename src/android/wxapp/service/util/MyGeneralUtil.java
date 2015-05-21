package android.wxapp.service.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;

/**
 * 常用工具栏
 * 
 * @author WEIHAO
 * 
 */

public class MyGeneralUtil {

	/**
	 * 获取参数时戳距1970年1月1日0分0秒的秒数
	 * 
	 * @param dateTime
	 *            时戳
	 * @return 参数时戳距1970年1月1日0分0秒的秒数
	 */
	@SuppressLint("SimpleDateFormat")
	public static String getInterval(String dateTime) {
		// 时间格式化工具类
		SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		// 参数时戳
		Date end = null;
		try {
			end = mSimpleDateFormat.parse(dateTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		// 返回秒数，故除以1000
		return Long.toString(end.getTime() / 1000);
	}

}
