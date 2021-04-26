package cn.concox.app.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
/** 
 *  add by xiehongtao */
public class DateTimeUtil { 
	
	public static void setTimeend(Calendar d)
	{
		if ( d == null )
			return ;
		d.set(Calendar.HOUR_OF_DAY, 23);
		d.set(Calendar.MINUTE, 59);
		d.set(Calendar.SECOND, 59);
	}
	
	public static void setTimebegin(Calendar d)
	{
		if ( d == null )
			return ;
		d.set(Calendar.HOUR_OF_DAY, 0);
		d.set(Calendar.MINUTE, 0);
		d.set(Calendar.SECOND, 0);
	}
	
	public static int getNextDay()
	{
		return getNextDay(now());
	}
	
	public static int getNextDay(Date d)
	{
		if ( d == null )
			return 0 ;
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.add(1, Calendar.DAY_OF_MONTH);
		return getDay(cal.getTime());
	}
	
	public static int getDay(Date d)
	{
		if ( d == null )
			return 0;
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		return cal.get(Calendar.DAY_OF_MONTH);
	}
	
	public static Date now()
	{
		Calendar cal = Calendar.getInstance();
		return cal.getTime();
	}
	
	public static int getThisRentCycleDays(int rentday)
	{
		if ( rentday < 0 )
			return 0;
		Calendar from = Calendar.getInstance();
		Calendar end = Calendar.getInstance();
		int todayindex = from.get(Calendar.DAY_OF_MONTH);
		from.set(Calendar.DAY_OF_MONTH, rentday);
		end.set(Calendar.DAY_OF_MONTH, rentday);
		if ( todayindex < rentday )
			from.add(Calendar.MONTH, -1);
		else 
			end.add(Calendar.MONTH, 1);
		
		return getDaysSpan(from , end);
	}
	
	public static Date getNextCycleEndDay(int rentday)
	{
		if ( rentday < 0 )
			return null;
		Calendar day = Calendar.getInstance();
		if(day.get(Calendar.DAY_OF_MONTH) >= rentday )
			day.add(Calendar.MONTH, 1);
		day.set(Calendar.DAY_OF_MONTH, rentday);
		day.set(Calendar.HOUR_OF_DAY, 23);
		day.set(Calendar.MINUTE, 59);
		day.set(Calendar.SECOND, 59);
		return day.getTime();
	}
	
	public static int getRentCycleRemainDays(int rentday)
	{
		if ( rentday < 0 )
			return 0;
		Calendar from = Calendar.getInstance();
		Calendar end = Calendar.getInstance();
		int todayindex = from.get(Calendar.DAY_OF_MONTH);
		end.set(Calendar.DAY_OF_MONTH, rentday);
		if ( todayindex > rentday )
			end.add(Calendar.MONTH, 1);
		
		return getDaysSpan(from , end);
	}

	
	public static int getDaysSpan(Calendar from , Calendar end)
	{
		if ( from == null || end == null )
			return 0;
		if ( from.get(Calendar.YEAR) == end.get(Calendar.YEAR))
			return end.get(Calendar.DAY_OF_YEAR) - from.get(Calendar.DAY_OF_YEAR);
		Calendar last = getYearLastDay(from);
		return last.get(Calendar.DAY_OF_YEAR) - from.get(Calendar.DAY_OF_YEAR) + end.get(Calendar.DAY_OF_YEAR);
	}
	
	public static Calendar getYearLastDay(Calendar from)
	{
		if ( from == null )
			return null;
		Calendar tmp = Calendar.getInstance();
		tmp.set(Calendar.YEAR, from.get(Calendar.YEAR));
		tmp.set(Calendar.MONTH, 11);
		tmp.set(Calendar.DAY_OF_MONTH, 31);
		return tmp ;
	}
	
	public static String getDateTimeString(Date d)
	{
		if ( d == null )
			return "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");    
	    return sdf.format(d);
	}
	
	public static String formatString(Date d,String format)
	{
		if ( d == null )
			return "";
		SimpleDateFormat sdf = new SimpleDateFormat(format);    
	    return sdf.format(d);
	}
	
	public static String getDateString(Date d)
	{
		if ( d == null )
			return "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");    
	    return sdf.format(d);
	}
	
	public static Date getDate(String date)
	{
		if ( date == null || date.length() == 0)
			return null ;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");    
	    try {
			return sdf.parse(date);
		} catch (ParseException e) {
			return null ;
		}
	}
	public static Date getDateTime(String date)
	{
		if ( date == null || date.length() == 0)
			return null ;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");    
	    try {
			return sdf.parse(date);
		} catch (ParseException e) {
			return null ;
		}
	}
	
	/**
	 * 计算两个日期间的时间差
	 * @param fromDate
	 * @param toDate
	 * @return long 返回天数
	 */
	public static double dateDiff(final Date fromDate, final Date toDate) {
		long startDate = fromDate.getTime();
		long endDate = toDate.getTime();
		double day2 = (((double)(endDate-startDate))/1000/60/60/24);
		/*Calendar aCalendar = Calendar.getInstance();
		aCalendar.setTime(fromDate);
		int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);
		aCalendar.setTime(toDate);
		int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);*/
		/*return day2 - day1;	*/	
		return day2;
	}
	
	public static void main(String arg[])
	{
//		Calendar cal = Calendar.getInstance();
	//	System.out.println(getDateTimeString(getYearLastDay(cal).getTime()) );
		System.out.println(getDateTimeString(getNextCycleEndDay(16)) );
		System.out.println(getDateTimeString(getNextCycleEndDay(17)) );
		System.out.println(getDateTimeString(getNextCycleEndDay(18)) );
		
		
//		for(int i = 0 ; i < 31 ; i ++ )
//		{
//			//cal.add(Calendar.DAY_OF_MONTH, 1);
//			System.out.println(cal.get(Calendar.DAY_OF_MONTH) + "-" + DateTimeUitl.getDay(cal.getTime()));
//		}
	}
}
