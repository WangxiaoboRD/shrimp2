/**
 * 文件名：@DateUtil.java <br/>
 * 包名：com.zhongpin.hr.personnel.utils <br/>
 * 项目名：hr <br/>
 * @author 孟雪勤 <br/>
 */
package com.cp.epa.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.cp.epa.exception.SystemException;

/**
 * 类名：DateUtil  <br />
 *
 * 功能：日期工具类
 *
 * @author 孟雪勤 <br />
 * 创建时间：2014-10-4 下午05:54:07  <br />
 * @version 2014-10-4
 */
public class DateUtil {
	
	/** yyyy-MM-dd HH:mm:ss日期格式 */
	public static final SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	/** yyyy-MM-dd日期格式 */
	public static final SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * 功能：校验日期合法性<br/>
	 * 
	 * @author 孟雪勤
	 * @version 2014-9-20 上午11:51:05 <br/>
	 */
	public static void isValidDate(String dateStr, String dateDesc) throws Exception {
		String date = dateStr;
		if (null != date && !"".equals(date)) {
			if (!date.contains("-") || date.length() != 10)
				throw new SystemException(dateDesc + "格式输入不合法！");

			try {
				sdfDate.setLenient(false);
				sdfDate.parse(dateStr);
			}catch (ParseException e) {
				throw new SystemException(dateDesc + "输入不合法！");
			}
		}
	}

	/**
	 * 功能：获得当前日期的加上指定月数后的日期<br/>
	 * 
	 * @author 孟雪勤
	 * @version 2014-9-15 上午11:49:30 <br/>
	 */
	public static String getAddMonthsDate(int months) throws Exception {
		Calendar now = Calendar.getInstance();
		now.add(Calendar.MONTH, months);
		return sdfDate.format(now.getTime());

	}
	
	/**
	 * 功能：获得当前日期的加上指定天数后的日期<br/>
	 *
	 * @author 孟雪勤
	 * @version 2014-10-4 下午05:40:35 <br/>
	 */
	public static String getAddDaysDate(int days) throws Exception {
		Calendar now = Calendar.getInstance();
		now.add(Calendar.DATE, days);
		return sdfDate.format(now.getTime());

	}

	/**
	 * 功能：获得指定日期加上指定月数后的日期<br/>
	 * 
	 * @author 孟雪勤
	 * @version 2014-9-15 上午10:58:36 <br/>
	 */
	public static String getAddMonthsDate(String dateStr, int months) throws Exception {
		Calendar now = Calendar.getInstance();
		try {
			now.setTime(sdfDate.parse(dateStr));
		}catch (ParseException e) {
			e.printStackTrace();
			new SystemException("日期解析错误，下个月日期获取失败！");
		}
		now.add(Calendar.MONTH, months);
		return sdfDate.format(now.getTime());
	}

	/**
	 * 功能：根据yyyyMM格式月份获得当月天数<br/>
	 * 
	 * @author 孟雪勤
	 * @version 2014-8-31 下午04:59:36 <br/>
	 */
	public static int getMonthDays(String dateStr) throws Exception {

		Calendar now = Calendar.getInstance();
		SimpleDateFormat simpleDate = new SimpleDateFormat("yyyyMM");
		try {
			now.setTime(simpleDate.parse(dateStr));
		}catch (ParseException e) {
			e.printStackTrace();
			new SystemException("日期解析错误，当月天数获取失败！");
		}
		return now.getActualMaximum(Calendar.DAY_OF_MONTH);
	}
	
	/**
	 * 功能：计算两个字符串格式为yyyy-MM-dd日期之间相差的月数<br/>
	 * 
	 * @author 孟雪勤
	 * @version 2012-7-3 上午11:26:44 <br/>
	 */
	public static int getMonths(String fromDateStr, String toDateStr) {
		int iMonth = 0;
		int flag = 0;
		try {
			// 起始日期
			Calendar objCalendarDate1 = Calendar.getInstance();
			objCalendarDate1.setTime(sdfDate.parse(fromDateStr));

			// 截止日期
			Calendar objCalendarDate2 = Calendar.getInstance();
			objCalendarDate2.setTime(sdfDate.parse(toDateStr));

			if (objCalendarDate2.equals(objCalendarDate1))
				return 0;
			if (objCalendarDate1.after(objCalendarDate2)) {
				Calendar temp = objCalendarDate1;
				objCalendarDate1 = objCalendarDate2;
				objCalendarDate2 = temp;
			}
			if (objCalendarDate2.get(Calendar.DAY_OF_MONTH) < objCalendarDate1.get(Calendar.DAY_OF_MONTH))
				flag = 1;

			if (objCalendarDate2.get(Calendar.YEAR) > objCalendarDate1.get(Calendar.YEAR))
				iMonth = ((objCalendarDate2.get(Calendar.YEAR) - objCalendarDate1.get(Calendar.YEAR)) * 12 + objCalendarDate2.get(Calendar.MONTH) - flag) - objCalendarDate1.get(Calendar.MONTH);
			else
				iMonth = objCalendarDate2.get(Calendar.MONTH) - objCalendarDate1.get(Calendar.MONTH) - flag;

		}catch (Exception e) {
			e.printStackTrace();
		}
		return iMonth;
	}
	
	/**
	 * 功能：<br/>
	 * @param date1和date2 格式都是yyyy-MM-dd
	 * @author 孟雪勤
	 * @version 2014-9-27 下午03:23:40 <br/>
	 */
	public static int dateDiff(String date1, String date2){
		int year1 = Integer.parseInt(date1.substring(0, 4));
		int month1 = Integer.parseInt(date1.substring(5, 7));
		int day1 = Integer.parseInt(date1.substring(8, 10));

		int year2 = Integer.parseInt(date2.substring(0, 4));
		int month2 = Integer.parseInt(date2.substring(5, 7));
		int day2 = Integer.parseInt(date2.substring(8, 10));

		// 计算年份相差总天数
		int sumYearDays = 0;
		for (int year = year1 + 1; year < year2; year++) {
			if (isLeapYear(year) == true) {
				sumYearDays += 366;
			}else {
				sumYearDays += 365;
			}
		}

		// 计算月份相差总天数
		int sumMonthDays = 0;
		// 若为同一年
		if (year1 == year2) {
			if (month1 != month2) {
				for (int month = month1 + 1; month < month2; month++) {
					sumMonthDays += getDays(year1, month);
				}
			}
		}else {
			for (int month = month1 + 1; month <= 12; month++) {
				sumMonthDays += getDays(year1, month);
			}
			for (int month = 1; month < month2; month++) {
				sumMonthDays += getDays(year2, month);
			}
		}

		// 计算相差总天数
		int sumDays = 0;
		// 若日期在同一年同一个月
		if (year1 == year2 && month1 == month2) {
			sumDays = day2 - day1 + 1;
		}else {
			sumDays += (getDays(year1, month1) - day1 + 1) + day2;
		}

		return (sumYearDays + sumMonthDays + sumDays);
	}

	/**
	 * 功能：获得指定年月的天数<br/>
	 *
	 * @author 孟雪勤
	 * @version 2014-9-27 下午03:41:31 <br/>
	 */
	public static int getDays(int year, int month) {
		int days = 30;
	 	boolean isThirtyOneMonth = false;
	 	int[] thirtyOneMonth = new int[]{1, 3, 5, 7, 8, 10, 12};
	 	for (int a : thirtyOneMonth) {
	 		if (month == a) {
	 			isThirtyOneMonth = true;
	 			break;
	 		}
	 	}
	 	if (isThirtyOneMonth) {
	 		days = 31;
	 	} else if (month == 2) {
	 		if (isLeapYear(year) == true) {
	 			days = 29;
	 		}else {
	 			days = 28;
	 		}
	 	}
	 	return days;
	}
	
	/**
	 * 功能：判断是否是闰年<br/>
	 * 
	 * @author 孟雪勤
	 * @version 2014-9-27 下午03:39:17 <br/>
	 */
	public static boolean isLeapYear(int year) {
		if (year % 4 == 0 && ((year % 100 != 0) || (year % 400 == 0))) {
			return true;
		}
		return false;
	}
	
	public static void main(String[] args) {
		try {
			System.out.println(DateUtil.dateDiff("2015-11-16", "2015-11-01"));
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
