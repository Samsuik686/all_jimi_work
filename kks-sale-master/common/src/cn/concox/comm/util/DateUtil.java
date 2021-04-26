package cn.concox.comm.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	public static Timestamp getTimestampByStr(String time) throws ParseException{
		if(!StringUtil.isRealEmpty(time)){
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			format.setLenient(false);
			Timestamp ts = new Timestamp(format.parse(time).getTime()); 
			return ts;
		}else{
			return null;
		}
	}
	
	/**
	 * 计算两个日期直接相差的天数，可跨年
	 * @author TangYuping
	 * @version 2017年2月16日 下午6:32:58
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int daysBetween(String startDate,String endDate)  
    {  
        Calendar cal = Calendar.getInstance();  
        cal.setTime(DateUtil.getDate(startDate));  
        long time1 = cal.getTimeInMillis();               
        cal.setTime(DateUtil.getDate(endDate));  
        long time2 = cal.getTimeInMillis();       
        long between_days=(time2-time1)/(1000*3600*24);  
          
       return Integer.parseInt(String.valueOf(between_days));         
    }

	/**
	 * 获取是第几个小时，从0开始
	 * @param date
	 * @return
	 */
	public static int hourOfDay(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.HOUR_OF_DAY);
	}
	/**
	 * 返回两个日期间的天数差，同一天返回0.
	 * d1在d2之前返回正值，d1在d2之后返回负值
	 * @param d1
	 * @param d2
	 */
	public static int getDayDiff(Date d1,Date d2){
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			long startDateTime = dateFormat.parse(dateFormat.format(d1)).getTime();
			long endDateTime = dateFormat.parse(dateFormat.format(d2)).getTime();
			return (int) ((endDateTime - startDateTime) / (1000 * 3600 * 24));
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 返回是周几
	 * 1表示周一
	 * 7表示周日
	 * @param date
	 * @return
	 */
	public static int dayOfWeek(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int day = calendar.get(Calendar.DAY_OF_WEEK)-1;
		return day == 0 ? 7:day;
	}

	/**
	 * 返回当日的00:00:00
	 * @return
	 */
	public static Date initDate(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND,0);
		return calendar.getTime();
	}
	/**
	 * 获取date日期  String转Date
	 * @author TangYuping
	 * @version 2017年2月16日 下午6:32:25
	 * @param date
	 * @return
	 */
	public static Date getDate(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return sdf.parse(date);
		} catch (ParseException e) {
			return null;
		}
	}
	
	public static String getCurrentDate(String pattern) {
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		String result = formatter.format(new Date());
		return result;
	}
}
