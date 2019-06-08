package com.wolves.util;

import org.apache.commons.lang.time.DateUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author msi
 */
public class DateUtil extends DateUtils{
	private final static SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");

	private final static SimpleDateFormat sdfDay = new SimpleDateFormat(
			"yyyy-MM-dd");
	
	private final static SimpleDateFormat sdfDays = new SimpleDateFormat(
	"yyyyMMdd");

	private final static SimpleDateFormat sdfTime = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	/**
	 * 获取YYYY格式
	 */
	public static String getYear() {
		return sdfYear.format(new Date());
	}

	/**
	 * 获取YYYY-MM-DD格式
	 */
	public static String getDay() {
		return sdfDay.format(new Date());
	}
	
	/**
	 * 获取YYYYMMDD格式
	 */
	public static String getDays(){
		return sdfDays.format(new Date());
	}

	/**
	 * 获取YYYY-MM-DD HH:mm:ss格式
	 */
	public static String getTime() {
		return sdfTime.format(new Date());
	}

	/**
	 * 格式化日期
	 */
	public static Date fomatDate(String date) {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return fmt.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
    
    /**
     * 得到n天之后的日期
     */
    public static String getAfterDayDate(String days) {
    	int daysInt = Integer.parseInt(days);
    	
        Calendar canlendar = Calendar.getInstance();
        canlendar.add(Calendar.DATE, daysInt);
        Date date = canlendar.getTime();
        
        SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = sdfd.format(date);
        
        return dateStr;
    }

	//1 minute = 60 seconds
	//1 hour = 60 x 60 = 3600
	//1 day = 3600 x 24 = 86400
	/**
	 * 计算时长
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static long printDifference(Date startDate, Date endDate){

		//milliseconds
		long different = endDate.getTime() - startDate.getTime();

		System.out.println("startDate : " + startDate);
		System.out.println("endDate : "+ endDate);
		System.out.println("different : " + different);

		long secondsInMilli = 1000;
		long minutesInMilli = secondsInMilli * 60;
		long hoursInMilli = minutesInMilli * 60;
		long daysInMilli = hoursInMilli * 24;

		long elapsedDays = different / daysInMilli;
		different = different % daysInMilli;

		long elapsedHours = different / hoursInMilli;
		different = different % hoursInMilli;

		long elapsedMinutes = different / minutesInMilli;
		different = different % minutesInMilli;

		long elapsedSeconds = different / secondsInMilli;

		System.out.printf(
				"%d days, %d hours, %d minutes, %d seconds%n",
				elapsedDays,
				elapsedHours, elapsedMinutes, elapsedSeconds);

		return elapsedHours;
	}

	/**
	 * 获取固定间隔时刻集合
	 * @param start 开始时间 HH:mm
	 * @param end 结束时间 HH:mm
	 * @param interval 时间间隔(单位：分钟)
	 * @return
	 */
	public static List<String> getIntervalTimeList(String start, String end, int interval){
		Date startDate = convertString2Date("HH:mm", start);
		Date endDate = convertString2Date("HH:mm", end);
		List<String> list = new ArrayList<String>();
		while(startDate.getTime() <= endDate.getTime()){
			list.add(convertDate2String("HH:mm",startDate));
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(startDate);
			calendar.add(Calendar.MINUTE,interval);
			if(calendar.getTime().getTime()>endDate.getTime()){
				if(!startDate.equals(endDate)){
					list.add(convertDate2String("HH:mm",endDate));
				}
				startDate = calendar.getTime();
			}else{
				startDate = calendar.getTime();
			}

		}
		return list;
	}

	public static Date convertString2Date(String format, String time) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);

		try {
			return sdf.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String convertDate2String(String format, Date date){
		SimpleDateFormat f = new SimpleDateFormat(format);
		return f.format(date);
	}

	public static void main(String[] args) {

		DateUtil obj = new DateUtil();
		SimpleDateFormat simpleDateFormat =
				new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date date1 = simpleDateFormat.parse("2019-04-01 12:00:00");
			Date date2 = simpleDateFormat.parse("2019-04-01 15:30:00");

			printDifference(date1, date2);

		} catch (ParseException e) {
			e.printStackTrace();
		}

	}
}