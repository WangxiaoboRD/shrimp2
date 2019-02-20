package com.cp.epa.utils;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.cp.epa.exception.SystemException;
import com.cp.epa.permission.entity.Users;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class PapUtil {
//	private static final Logger logger = Logger.getLogger(PapUtil.class);
//	public  final static SimpleDateFormat PAPTime  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//	public final static SimpleDateFormat PAPDate = new SimpleDateFormat("yyyy-MM-dd");
//	public final static SimpleDateFormat PAPSDate = new SimpleDateFormat("yyyy/M/d");
//	public  final static SimpleDateFormat noSepTime  = new SimpleDateFormat("yyyyMMddHHmm");
//	public final static SimpleDateFormat PAPMonth = new SimpleDateFormat("yyyy-MM");
//	private final static Pattern pattern = Pattern.compile("^[-+]?[0-9]+\\.{0,1}[0-9]{0,20}$"); 
//	public  final static SimpleDateFormat PAPTimeNoSecond  = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	
	/**
	 * 功能：获得当前用户<br/>
	 * 
	 * @author 孟雪勤
	 * @version 2014-3-7 上午09:42:59 <br/>
	 */
	public static String dateNoTimeSS(Date d) {
		SimpleDateFormat PAPTimeNoSecond  = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return PAPTimeNoSecond.format(d);
	}
	/**
	 * 功能：获得当前用户<br/>
	 * 
	 * @author 孟雪勤
	 * @version 2014-3-7 上午09:42:59 <br/>
	 */
	public static Users getCurrentUser() {
		Users u = SysContainer.get();
		if (null == u)
			throw new SystemException("当前用户信息获取失败！");

		return u;
	}
	
	/**
	 * 功能：手动录入编码内容限制（只能是英文字母或数字）<br/>
	 *
	 * @author 孟雪勤
	 * @version 2015-2-3 下午12:03:34 <br/>
	 */
	public static boolean idRestrict(String str) {
		String regex = "[a-zA-Z\\d]+";
		return str.matches(regex);
	}
	
	
	/**
	 * 格式化日期
	 * 功能：<br/>
	 * @author 杜中良
	 * @version Mar 20, 2013 6:03:36 PM <br/>
	 */
	public static String date(Date d){
		SimpleDateFormat PAPTime  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return PAPTime.format(d);
	} 
	
	/**
	 * 通过年份与月份获得该月的最后一天的日期
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Jul 30, 2014 6:14:31 PM <br/>
	 */
	public static String getMonthLastDay(String year,String month){
		String dateStr = year +"-"+month;
		Calendar c = Calendar.getInstance();
		SimpleDateFormat PAPDate = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat PAPMonth = new SimpleDateFormat("yyyy-MM");
		try {
			Date d = PAPMonth.parse(dateStr);
			c.setTime(d);
		    //c.set(Calendar.DAY_OF_MONTH, 1);  
	        //System.out.println("当前月的第一天：" + df.format(c.getTime()));
	        c.add(Calendar.MONTH, 1);  
	        c.set(Calendar.DAY_OF_MONTH, 0);  
	        //System.out.println("当前月的第一天：" + df.format(c.getTime()));
	        return PAPDate.format(c.getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获得指定月份中的最大天数
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Jul 30, 2014 6:28:54 PM <br/>
	 */
	public static int getMonthMaxDayNum(String year,String month){
		String dateStr = year +"-"+month;
		Calendar c = Calendar.getInstance();
		Date d=null;
		SimpleDateFormat PAPMonth = new SimpleDateFormat("yyyy-MM");
		try {
			d = PAPMonth.parse(dateStr);
			c.setTime(d);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return c.getActualMaximum(Calendar.DATE);
	}
	
	/**
	 * 将字符串转化为日期
	 * 功能：<br/>
	 * @author 杜中良
	 * @version Mar 20, 2013 6:03:36 PM <br/>
	 */
	public static Date parse(String d)throws Exception{
		SimpleDateFormat PAPDate = new SimpleDateFormat("yyyy-MM-dd");
		return PAPDate.parse(d);
	} 
	
	/**
	 * 两个日期相减获得天数
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Mar 29, 2014 10:38:22 AM <br/>
	 */
	public static long dayNum(String sdate,String edate)throws Exception{
		Date beginDate = parse(sdate);    
		Date endDate = parse(edate);     
		return (endDate.getTime() - beginDate.getTime())/(24 * 60 * 60 * 1000);
		
	}
	
	/**
	 * 根据年月获得上一个年月
	 * @return
	 * @throws Exception
	 */
	public static String getMonth(String month)throws Exception{
		SimpleDateFormat PAPMonth = new SimpleDateFormat("yyyy-MM");
		Calendar c=Calendar.getInstance();
		c.setTime(PAPMonth.parse(month));
		c.add(Calendar.MONTH, -1);
		return PAPMonth.format(c.getTime());
	}
	/**
	 * 获得下一个年月
	 * @param month
	 * @return
	 * @throws Exception
	 */
	public static String getNextMonth(String month)throws Exception{
		SimpleDateFormat PAPMonth = new SimpleDateFormat("yyyy-MM");
		Calendar c=Calendar.getInstance();
		c.setTime(PAPMonth.parse(month));
		c.add(Calendar.MONTH, 1);
		return PAPMonth.format(c.getTime());
	}
	
	
	/**
	 * 格式化日期
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Jul 10, 2013 9:19:07 AM <br/>
	 */
	public static String notFullDate(Date d){
		SimpleDateFormat PAPDate = new SimpleDateFormat("yyyy-MM-dd");
		return PAPDate.format(d);
	}
	
	/**
	* @Title: getPapTime
	* @Description: 返回当前时间，时间格式为：yyyy-MM-dd HH:mm:ss
	* @param @return    设定文件
	* @return Object    返回类型
	* @throws
	*/
	public static String getPapTime() {
		SimpleDateFormat PAPTime  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return PAPTime.format(new Date());
	}
	
	/**
	 * 将字符串数组转化为字符串
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Jul 10, 2013 9:20:03 AM <br/>
	 */
	public static String arrayToString(String[] arrayStr){
		return Arrays.toString(arrayStr).replaceAll("[\\[\\]\\s]", "");
	}
	/**
	 * 功能：集合转换成以“,”分隔的SQL字符串<br/>
	 * 
	 * @author 孟雪勤
	 * @version 2012-6-11 下午05:05:27 <br/>
	 */
	public static String listToSQLString(List<String> strList) {
		StringBuilder splitStr = new StringBuilder();
		if (null != strList && !strList.isEmpty()) {
			for (String str : strList) {
				splitStr.append("'" + str + "'");
				splitStr.append(",");
			}
			splitStr.deleteCharAt(splitStr.length() - 1);
		}else {
			return null;
		}
		return splitStr.toString();
	}

	/**
	 * 功能：集合转换成以“,”分隔的字符串<br/>
	 * 
	 * @author 孟雪勤
	 * @version 2012-6-11 下午06:30:13 <br/>
	 */
	public static String listToString(Collection<String> strList) {
		StringBuilder splitStr = new StringBuilder();
		if (null != strList && !strList.isEmpty()) {
			for (String str : strList) {
				splitStr.append(str);
				splitStr.append(",");
			}
			splitStr.deleteCharAt(splitStr.length() - 1);
		}else {
			return null;
		}
		return splitStr.toString();
	}

	/**
	 * 
	 * 功能：<br/>
	 * 判断是不是数字
	 * @author zp
	 * @version 2013-7-9 下午04:02:12 <br/>
	 */
	public static boolean isNum(String num){
		boolean fg=true;
		try{
			Float.parseFloat(num);
		}catch(Exception e){
			fg=false;
		}
		return fg;
	}
	
	
	/**
	 * 
	 * 功能：<br/>
	 * 判断是不是整数
	 * @author zp
	 * @version 2013-7-9 下午04:06:55 <br/>
	 */
	public static boolean isInt(String num){		
		boolean fg=true;
		try{
			Integer.parseInt(num);
		}catch(Exception e){
			fg=false;
		}
		return fg;		
	}
	
	/**
	 * 将首字母大写
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Dec 5, 2013 4:30:47 PM <br/>
	 */
	public static String toFirstLetterUpperCase(String str) {
		if (str == null || str.length() < 2) {
			return str;
		}
		String firstLetter = str.substring(0, 1).toUpperCase();
		return firstLetter + str.substring(1, str.length());
//		char[]arr=str.toCharArray();
//		arr[0]-=32;
//		String value=arr.toString();
//		return value;
	}  
	
	/**
	 * 功能：将数组转换为字符串<br/>
	 *
	 * @author 孟雪勤
	 * @version 2014-3-7 下午02:59:53 <br/>
	 */
	public static <T> String arrayToStr(T[] array){
		if (null == array || array.length == 0) 
			return "";
		return Arrays.toString(array).replaceAll("[\\[\\]\\s]", "");
	}
	
	/**
	 * 功能：字符串数组转换为以逗号分隔，单引号包括的SQL字符串<br/>
	 * 如：[a, b, c]
	 * 转换后为'a','b', 'c'
	 * @author 孟雪勤
	 * @version 2014-4-23 上午10:16:48 <br/>
	 */
	public static String arrayToSQLStr(String[] arrayStr) {
		if (null == arrayStr || arrayStr.length == 0) 
			return "";
		StringBuilder sb = new StringBuilder();
		for (String str : arrayStr) {
			sb.append("'").append(str.trim()).append("'").append(",");
		}
		
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}
	
	/**
	 * 功能：下载文件名中文乱码解决<br/>
	 *
	 * @author 孟雪勤
	 * @version 2014-6-19 下午04:09:27 <br/>
	 */
	public static String getFileName(String fileName) {
		try {
			return new String(fileName.getBytes(), "ISO8859-1");
		}catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new SystemException("文件名不支持编码异常：" + e.getMessage());
		}
	}
	
	/**
	 * 验证字符串为double double类型最多保留4位小数
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Oct 16, 2014 4:22:04 PM <br/>
	 */
	public static boolean checkDouble(String doubleStr){
		Pattern pattern = Pattern.compile("^[-+]?[0-9]+\\.{0,1}[0-9]{0,20}$"); 
		return pattern.matcher(doubleStr).matches();
	}
	/**
	 * 
	 * 功能:身份证号码校验方法<br/>
	 * 
	 * @author 孟雪勤
	 * @version 2011-9-26 上午09:08:18 <br/>
	 */
	public static String identityCheck(String creditID) {
		String[] errors = new String[]{ "OK", "身份证号码位数不正确！", "身份证号码出生日期超出范围或含有非法字符！", "身份证号码校验错误！", "身份证地区非法！" };
		// 所有地区的身份证号前两位
		List<String> areas = Arrays.asList("11", "12", "13", "14", "15", "21", "22", "23", "31", "32", "33", "34", "35", "36", "37", "41", "42", "43", "44", "45", "46", "50", "51", "52", "53", "54",
				"61", "62", "63", "64", "65", "71", "81", "82", "91");

		// 以空串截取身份证号
		String[] id_array = creditID.split("");
		// 地区检验
		if (!areas.contains(creditID.substring(0, 2))) {
			return errors[4];
		}

		String regex = null;
		// 身份号码位数及格式检验
		switch (creditID.length()) {
			case 15:
				// 15位身份证校验
				int year2 = Integer.parseInt(creditID.substring(6, 8)) + 1900;
				if (year2 % 4 == 0 || (year2 % 100 == 0 && year2 % 4 == 0)) {
					// 测试出生日期的合法性
					regex = "^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}$";
				}else {
					// 测试出生日期的合法性
					regex = "^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}$";
				}

				if (!creditID.matches(regex)) {
					return errors[2];
				}
				break;
			case 18:
				// 18位身份号码检测
				// 出生日期的合法性检查
				int year4 = Integer.parseInt(creditID.substring(6, 10));
				if (year4 % 4 == 0 || (year4 % 100 == 0 && year4 % 4 == 0)) {
					// 闰年出生日期的合法性正则表达式
					regex = "^[1-9][0-9]{5}(19|20)[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}[0-9|X|x]$";

				}else {
					// 平年出生日期的合法性正则表达式
					regex = "^[1-9][0-9]{5}(19|20)[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}[0-9|X|x]$";
				}
				// 测试出生日期的合法性
				if (creditID.matches(regex)) {
					// 计算校验位
					int sum = (Integer.parseInt(id_array[1]) + Integer.parseInt(id_array[11])) * 7 + (Integer.parseInt(id_array[2]) + Integer.parseInt(id_array[12])) * 9
							+ (Integer.parseInt(id_array[3]) + Integer.parseInt(id_array[13])) * 10 + (Integer.parseInt(id_array[4]) + Integer.parseInt(id_array[14])) * 5
							+ (Integer.parseInt(id_array[5]) + Integer.parseInt(id_array[15])) * 8 + (Integer.parseInt(id_array[6]) + Integer.parseInt(id_array[16])) * 4
							+ (Integer.parseInt(id_array[7]) + Integer.parseInt(id_array[17])) * 2 + Integer.parseInt(id_array[8]) * 1 + Integer.parseInt(id_array[9]) * 6
							+ Integer.parseInt(id_array[10]) * 3;
					// 余数
					int remainder = sum % 11;
					String flag = "F";
					String checkCode = "10X98765432";
					flag = checkCode.substring(remainder, remainder + 1);// 判断校验位
					// 校验码错误
					if (!flag.equals(id_array[18])) // 检测ID的校验位
					{
						return errors[3];
					}
				}else {
					// 出生日期不合法
					return errors[2];
				}
				break;
			default:
				// 身份证号尾数不正确
				return errors[1];
		}
		// 验证通过
		return errors[0];
	}
	
	/**
	 * 功能：身份证简单校验：只校验身份证位数和出生日期<br/>
	 *
	 * @author 孟雪勤
	 * @version 2016-1-2 下午03:28:46 <br/>
	 */
	public static String identitySimpleCheck(String creditID) {
		String[] errors = new String[]{ "OK", "身份证号码位数不正确！", "身份证号码出生日期超出范围或含有非法字符！"};
		String regex = null;
		// 身份号码位数及格式检验
		switch (creditID.length()) {
			case 15:
				// 15位身份证校验
				int year2 = Integer.parseInt(creditID.substring(6, 8)) + 1900;
				if (year2 % 4 == 0 || (year2 % 100 == 0 && year2 % 4 == 0)) {
					// 测试出生日期的合法性
					regex = "^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}$";
				}else {
					// 测试出生日期的合法性
					regex = "^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}$";
				}

				if (!creditID.matches(regex)) {
					return errors[2];
				}
				break;
			case 18:
				// 18位身份号码检测
				// 出生日期的合法性检查
				int year4 = Integer.parseInt(creditID.substring(6, 10));
				if (year4 % 4 == 0 || (year4 % 100 == 0 && year4 % 4 == 0)) {
					// 闰年出生日期的合法性正则表达式
					regex = "^[1-9][0-9]{5}(19|20)[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}[0-9|X|x]$";

				}else {
					// 平年出生日期的合法性正则表达式
					regex = "^[1-9][0-9]{5}(19|20)[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}[0-9|X|x]$";
				}
				// 测试出生日期的合法性
				if (!creditID.matches(regex)){
					// 出生日期不合法
					return errors[2];
				}
				break;
			default:
				// 身份证号位数不正确
				return errors[1];
		}
		// 验证通过
		return errors[0];
	}
	
	/**
	 * 将阿拉伯数字转换为中文小写
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Dec 6, 2014 5:02:41 PM <br/>
	 */
	public static String transform(String num){
		 String []aa={"","十","百","千","万","十万","百万","千万","亿","十亿"};
		 String []bb={"一","二","三","四","五","六","七","八","九"};
		 char[] ch=num.toCharArray();
		 int maxindex=ch.length;
		 // 字符的转换
		 // 两位数的特殊转换
		 if(maxindex==2){
			 for(int i=maxindex-1,j=0;i>=0;i--,j++){
				 if(ch[j]!=48){
					 if(j==0&&ch[j]==49){
						 return aa[i];
					 }else{
						 return bb[ch[j]-49]+aa[i];
					 }
				 }
			 }
		 // 其他位数的特殊转换，使用的是int类型最大的位数为十亿
		 }else{
			 for(int i=maxindex-1,j=0;i>=0;i--,j++){
	  			if(ch[j]!=48){
	  				return bb[ch[j]-49]+aa[i];
	  			}
	  		 }
		 }
		 return "";
     } 
	
	/**
	 * 将数字转化为英文
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version 2015-3-3 下午05:47:19 <br/>
	 */
	public static String toEnglish(int num) {
		String[] BITS = {"one","two","three","four","five","six","seven","eight","nine","ten"};
		String[] TEENS = {"eleven","twelf","thirteen","fourteen","fifteen","sixteen","seventeen","eighteen","nighteen"};
		String[] TIES = {"twenty","thrity","forty","fifty","sixty","seventy","eighty","ninety"};
		if(num == 0) {
            return "Zero";
        }
        StringBuffer buffer = new StringBuffer();
        if(num >= 100) {
        	int hunder = num / 100;
            buffer.append(BITS[hunder - 1] + " hundered");
            if(num % 100 != 0) {
                buffer.append(" and ");
            }
            num -= (num / 100) * 100;
        }
        boolean flag = false;
        if(num >= 20) {
        	flag = true;
            int ties = num / 10;
            buffer.append(TIES[ties - 2]);
            num -= (num / 10) * 10;
        }
        if(!flag && num > 10) {
            buffer.append(TEENS[num - 11]);
            num = 0;
        }
        if(num > 0) {
            String bit =BITS[num - 1];
            if(flag) {
                buffer.append(" ");
            }
            buffer.append(bit);
        }
        return buffer.toString();
    }
	
	/**
	 * 获得汉字首字母
	 * 功能：<br/>
	 *
	 * @author DZL
	 * @version 2015-11-5 下午03:17:40 <br/>
	 * @throws BadHanyuPinyinOutputFormatCombination 
	 */
	public static String getHeadChar(String str){
		String convert = "";
		for (int j = 0; j < str.length(); j++) {
            char word = str.charAt(j);
            String[] pinyinArray = null;
			try {
				pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word,new HanyuPinyinOutputFormat());
			}catch (BadHanyuPinyinOutputFormatCombination e) {
				e.printStackTrace();
			}
            if (pinyinArray != null) {
                convert += pinyinArray[0].charAt(0);
            } else {
                convert += word;
            }
        }
        return convert;
	}
	/**
	 * 判断一个字符是不是中文
	 * @param c
	 * @return
	 */
	public static boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
			return true;
		}
		return false;
	}
	
	/**
	 * 根据给定的日期与天数，计算该天数之后的日期
	 * @param dayNum
	 * @param d
	 * @return
	 */
	public static String getDay(int dayNum,Date d){
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.add(Calendar.DAY_OF_MONTH, dayNum);   
	        
	   return date(c.getTime()); 
	        
	}
	
	/**
	 * 根据指定日期获取年度第几周
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public static String getweek(String date)throws Exception{
		SimpleDateFormat PAPDate = new SimpleDateFormat("yyyy-MM-dd");
		Date _date = PAPDate.parse(date);
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.SUNDAY);
		calendar.setTime(_date);
		int n = calendar.get(Calendar.WEEK_OF_YEAR);
		String _n = String.format("%02d", n);
		String v=date.substring(2,4);
		int mm = calendar.get(Calendar.MONTH);
		if("01".equals(_n)){
	    	if(mm!=0)
	    		v = ArithUtil.add(v, "1", 0);
	    }
		
		return v+_n;
	}
	
	/**
	 * 根据给定的日期与天数，计算该天数之后的日期
	 * @param dayNum
	 * @param d
	 * @return
	 */
	public static String getDayNoTime(int dayNum,String d){
		d = d.substring(0,10);
		Calendar c = Calendar.getInstance();
		SimpleDateFormat PAPDate = new SimpleDateFormat("yyyy-MM-dd");
		try {
			c.setTime(PAPDate.parse(d));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		c.add(Calendar.DAY_OF_MONTH, dayNum);   
	        
	   return notFullDate(c.getTime()); 
	        
	}
	
	/**
	 * 判断一个字符串是不是全中文
	 * @param str
	 * @return
	 */
	public static boolean isChineseStr(String str){
		char[] ch = str.toCharArray();  
        for (int i = 0; i < ch.length; i++) {  
            char c = ch[i];  
            if (!isChinese(c)) {  
                return false;  
            }  
        }  
        return true;  
	}
	/**
	 * 获取给定年份的日历
	 * @Description: TODO
	 * @param @param year
	 * @param @return   
	 * @return Calendar  
	 * @throws
	 * @author 丁佳浩
	 * @date 2016-9-29
	 */
	private static Calendar getCalendarFormYear(int year){  
        Calendar cal = Calendar.getInstance();  
        //cal.setFirstDayOfWeek(Calendar.SUNDAY);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);        
        cal.set(Calendar.YEAR, year);  
        boolean isFirstSunday = (cal.getFirstDayOfWeek() == Calendar.SUNDAY);
        return cal;  
    }  
	
	/**
	 * 获取某年的第几周的结束日期
	 * @Description: TODO
	 * @param @param year
	 * @param @param weekNo
	 * @param @return   
	 * @return String  
	 * @throws
	 * @author 丁佳浩
	 * @date 2016-9-29
	 */
	public static String getEndDayOfWeekNo(int year,int weekNo){  
        Calendar cal = getCalendarFormYear(year);
        cal.set(Calendar.WEEK_OF_YEAR, weekNo);  
        //cal.add(Calendar.DAY_OF_WEEK, 7);  
        return cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" +  
               cal.get(Calendar.DAY_OF_MONTH);      
    }  
	
	/**
	 * 通过周龄获取出生周
	 * @Description: TODO
	 * @param @param week
	 * @param @return
	 * @param @throws Exception   
	 * @return String  
	 * @throws
	 * @author 丁佳浩
	 * @date 2016-9-12
	 */
	public static String getBirthWeek(String week) throws Exception {
		SimpleDateFormat PAPDate = new SimpleDateFormat("yyyy-MM-dd");
		int day = Integer.parseInt(ArithUtil.mul(ArithUtil.sub(week, "1"), "7",0));
		String d = getDay(-day, new Date());
	    String dd = notFullDate(parse(d));
	    //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
		Calendar c=Calendar.getInstance();
		c.setTime(PAPDate.parse(dd)); 
		int ii = c.get(Calendar.WEEK_OF_YEAR);
	    int yy = c.get(Calendar.YEAR);
	    int mm = c.get(Calendar.MONTH);
	    String wk = Integer.toString(ii);
	    String ye = Integer.toString(yy);
	    String year = ye.substring(2, 4);
	    if(wk.length()==1){
	    	wk = '0'+wk;
	    }
	    if("01".equals(wk)){
	    	if(mm!=0)
	    		year = ArithUtil.add(year, "1", 0);
	    }
		String birthweek = year + wk;
		return birthweek;
		/***********************另一种方法**************************/
//		Calendar cl = new GregorianCalendar();
//		cl.add(Calendar.WEEK_OF_YEAR, -Integer.valueOf(week)+1);
//		int year = cl.get(Calendar.YEAR);
//		int weeks = cl.get(Calendar.WEEK_OF_YEAR);
//		DecimalFormat df =new DecimalFormat("00");
//		return (year+"").substring(2) + df.format(weeks);
		/******************************************************/
	}
	
	/**
	 * 通过出生周获得周龄
	 * @Description: TODO
	 * @param @param birthWeek
	 * @param @return
	 * @param @throws Exception   
	 * @return String  
	 * @throws
	 * @author 丁佳浩
	 * @date 2016-9-13
	 */
	public static String getWeeks(String birthWeek) throws Exception{
		
		if(birthWeek.length()==4){
			String now = notFullDate(parse(getPapTime()));
			String y = birthWeek.substring(0,2);
			String year = "20"+y;
			String wek = birthWeek.substring(2, 4);
			
			String start = getEndDayOfWeekNo(Integer.parseInt(year), Integer.parseInt(wek));
			long day = dayNum(start, now);
			int temp = 0;
			if(day <= 0 && day>=-6)
				temp =1;
			else if(day>0){
				int week = (int) (day/8);
				temp = week + 2;
			}else{
				throw new SystemException("出生周不能大于当前周");
			}
			return Integer.toString(temp);
		}else
			return null;
		//************************************************
//		//出生周
//		Calendar cl = new GregorianCalendar();
//		int year = Integer.parseInt("20" + birthWeek.substring(0, 2));
//		int week = Integer.parseInt(birthWeek.substring(2));
//		cl.set(Calendar.YEAR, year);
//		cl.set(Calendar.WEEK_OF_YEAR, Integer.parseInt(birthWeek.substring(2)));
//		//当前时间
//		Calendar c2 = new GregorianCalendar();
//		c2.setTime(new Date());
//		c2.get(Calendar.WEEK_OF_YEAR);
//		
//		if(c2.get(Calendar.YEAR) < year)
//			throw new RuntimeException("错误");
//		else if(c2.get(Calendar.YEAR) == year){
//			if((c2.get(Calendar.WEEK_OF_YEAR)-week)<0)
//				throw new RuntimeException("错误");
//			else
//				return String.valueOf(c2.get(Calendar.WEEK_OF_YEAR)-week+1);
//		}else{
//			int weeks = 0;
//			for(int i = year;i<c2.get(Calendar.YEAR);i++){
//				Calendar c = new GregorianCalendar();
//				c.set(Calendar.YEAR,year);
//				weeks += c.getMaximum(Calendar.WEEK_OF_YEAR);
//			}
//			int weks = weeks - week + c2.get(Calendar.WEEK_OF_YEAR) + 1;
//			return weks + "";
//		}
		//***********************************************************
	}
	
	/**
	 * 根据起始日期与结束日期计算 每一个月的天数
	 * @param startDate 格式 2015-10-26
	 * @param endDate 格式 2015-10-26
	 * @return
	 * @throws Exception
	 */
	public static List<String> getMonthDayNum(String startDate,String endDate)throws Exception{
		SimpleDateFormat PAPDate = new SimpleDateFormat("yyyy-MM-dd");
		String stDate = startDate.substring(0,10).replace("-", "");
		String enDate = endDate.substring(0,10).replace("-", "");
		int sd = Integer.parseInt(stDate); 
		int ed = Integer.parseInt(enDate); 
		if(sd>=ed)
			return null;
		List<String> slist = new ArrayList<String>();
		Date dateStart = PAPDate.parse(startDate.substring(0,10));
	    Date dateEnd = PAPDate.parse(endDate.substring(0,10));
		Calendar c=Calendar.getInstance();
		c.setTime(dateStart);
	    int sd_y = c.get(Calendar.YEAR);
	    int sd_m = c.get(Calendar.MONTH);
	    int sd_d = c.get(Calendar.DATE);
	    int sd_num = c.getActualMaximum(Calendar.DATE);
	    
	    Calendar a=Calendar.getInstance();
	    a.setTime(dateEnd);
	    int ed_y = a.get(Calendar.YEAR);
	    int ed_m = a.get(Calendar.MONTH);
	    int ed_d = a.get(Calendar.DATE);
	    
	    //年份月份相同，天数不一样也就是同一个月里处理
	    String startD="";
	    String endD ="";
	    
	    if(sd_y == ed_y && sd_m == ed_m){
	    	startD = sd_y+"-"+String.format("%02d", (sd_m+1))+"-"+String.format("%02d", (sd_d));
	    	endD = ed_y+"-"+String.format("%02d", (ed_m+1))+"-"+String.format("%02d", (ed_d));
	    	slist.add(sd_y+"-"+String.format("%02d", (sd_m+1))+","+(ed_d-sd_d)+","+startD+","+endD);
	    }else{
	    	startD = sd_y+"-"+String.format("%02d", (sd_m+1))+"-"+String.format("%02d", (sd_d));
	    	endD = sd_y+"-"+String.format("%02d", (sd_m+1))+"-"+String.format("%02d", (sd_num));
		    slist.add(sd_y+"-"+String.format("%02d", (sd_m+1))+","+(sd_num+1-sd_d)+","+startD+","+endD);
		    for(int i=0;i<100;i++){
		    	c.add(Calendar.MONTH, 1);
		    	
		    	int cu_y = c.get(Calendar.YEAR);
		 	    int cu_m = c.get(Calendar.MONTH);
		 	    int cu_d = c.getActualMaximum(Calendar.DATE);
			    if(cu_y==ed_y && cu_m == ed_m)
			    	break;
			    else{
			    	startD = cu_y+"-"+String.format("%02d", (cu_m+1))+"-01";
			    	endD = cu_y+"-"+String.format("%02d", (cu_m+1))+"-"+String.format("%02d", (cu_d));
			    	slist.add(cu_y+"-"+String.format("%02d", (cu_m+1))+","+cu_d+","+startD+","+endD);
			    }
		    }
		    startD = ed_y+"-"+String.format("%02d", (ed_m+1))+"-01";
	    	endD = ed_y+"-"+String.format("%02d", (ed_m+1))+"-"+String.format("%02d", (ed_d));
		    slist.add(ed_y+"-"+String.format("%02d", (ed_m+1))+","+(ed_d-1)+","+startD+","+endD);
	    }
	    return slist;
	} 
	/**
	 * 计算两个字符串日期的天数
	 * @param smdate
	 * @param bdate
	 * @return
	 * @throws ParseException
	 */
	public static int daysBetween(String startDate, String endDate)throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		startDate = startDate.substring(0,10);
		endDate = endDate.substring(0,10);
		Calendar cal = Calendar.getInstance();
		cal.setTime(sdf.parse(startDate));
		long time1 = cal.getTimeInMillis();
		cal.setTime(sdf.parse(endDate));
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);

		return Integer.parseInt(String.valueOf(between_days));
	}
	
	/**
	 * 根据年份与周次获得该周的日期范围
	 * @param year
	 * @param week
	 * @return
	 * @throws ParseException
	 */
	public static String[] getWeekDays(String year, String week)throws ParseException {
		
		String[] str = new String[2];
		
		Calendar cal = Calendar.getInstance();  
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);        
        cal.set(Calendar.YEAR, Integer.parseInt(year));  
        
        cal.set(Calendar.WEEK_OF_YEAR, Integer.parseInt(week));  
        str[0]=cal.get(Calendar.YEAR) + "-" + String.format("%02d",(cal.get(Calendar.MONTH) + 1)) + "-" +String.format("%02d",cal.get(Calendar.DAY_OF_MONTH));
        
        cal.add(Calendar.DAY_OF_WEEK, 6); 
	    
        str[1]=cal.get(Calendar.YEAR) + "-" + String.format("%02d",(cal.get(Calendar.MONTH) + 1)) + "-" +String.format("%02d",cal.get(Calendar.DAY_OF_MONTH));
        
        return str;
	}
	/**
	 * 根据年份与周次获得该周的日期范围
	 * @param year
	 * @param week
	 * @return
	 * @throws ParseException
	 */
	public static String[] getStartOrEnd(String year, String week)throws ParseException {
		
		String[] str = new String[2];
		
		Calendar cal = Calendar.getInstance();  
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);        
        cal.set(Calendar.YEAR, Integer.parseInt(year));  
        
        cal.set(Calendar.WEEK_OF_YEAR, Integer.parseInt(week));  
        str[0]=cal.get(Calendar.YEAR) + "/" + String.format("%02d",(cal.get(Calendar.MONTH) + 1)) + "/" +String.format("%02d",cal.get(Calendar.DAY_OF_MONTH));
        
        cal.add(Calendar.DAY_OF_WEEK, 6); 
	    
        str[1]=cal.get(Calendar.YEAR) + "/" + String.format("%02d",(cal.get(Calendar.MONTH) + 1)) + "/" +String.format("%02d",cal.get(Calendar.DAY_OF_MONTH));
        
        return str;
	}
	
	/**
	 * 根据年份与周次获得该周的中间日期
	 * @param year
	 * @param week
	 * @return
	 * @throws ParseException
	 */
	public static String getWeekMidDays(String year, String week)throws ParseException {
		Calendar cal = Calendar.getInstance();  
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);        
        cal.set(Calendar.YEAR, Integer.parseInt(year));  
        cal.set(Calendar.WEEK_OF_YEAR, Integer.parseInt(week));  
        cal.add(Calendar.DAY_OF_WEEK, 3); 
	    
        String day = cal.get(Calendar.YEAR) + "-" + String.format("%02d",(cal.get(Calendar.MONTH) + 1)) + "-" +String.format("%02d",cal.get(Calendar.DAY_OF_MONTH));
        
        return day;
	}
	/**
	 * 根据年份与周次获得该周的中间日期
	 * @param year
	 * @param week
	 * @return
	 * @throws ParseException
	 */
	public static String getWeekMidDaysByweek(String weeks)throws ParseException {
		//计算日期
		String year = "20"+(weeks.substring(0,2));
		String week = weeks.substring(2,4); 
		
		Calendar cal = Calendar.getInstance();  
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);        
        cal.set(Calendar.YEAR, Integer.parseInt(year));  
        cal.set(Calendar.WEEK_OF_YEAR, Integer.parseInt(week));  
        cal.add(Calendar.DAY_OF_WEEK, 3); 
	    
        String day = cal.get(Calendar.YEAR) + "-" + String.format("%02d",(cal.get(Calendar.MONTH) + 1)) + "-" +String.format("%02d",cal.get(Calendar.DAY_OF_MONTH));
        
        return day;
	}
	
	/**
	 * 判断两个字符串日期大小
	 * @param smdate
	 * @param bdate
	 * @return
	 * @throws ParseException
	 */
	public static int compareDate(String startDate, String endDate)throws ParseException {
		startDate = startDate.substring(0,10).replace("-", "");
		endDate = endDate.substring(0,10).replace("-", "");
		//转化为int
		int sdate = Integer.parseInt(startDate);
		int edate = Integer.parseInt(endDate);
		int num = 0;
		if(sdate > edate)
			num = -1;
		else if(sdate < edate)
			num = 1;
		return num;
	}
	
	/**
	 * 
	 * 功能:给定月份计算上一个月份，如"2016-01"返回"2015-12"
	 * @author:wxb
	 * @data:2016-12-14下午03:16:08
	 * @file:PapUtil.java
	 * @param month
	 * @return
	 * @throws Exception
	 */
	public static String computePastMonth(String month)throws Exception{
		String mon=month.substring(5);
		String year=month.substring(0, 4);
		if(ArithUtil.comparison(mon, "1")==0){
			year=ArithUtil.sub(year, "1");
			month=year+"-"+"12";
		}else{
			mon=ArithUtil.sub(mon, "1");
			if(mon.length()==1)
				mon="0"+mon;
			month=year+"-"+mon;
		}
		return month;
	}
	/**
	 * 
	 * 功能:计算下一个月份
	 * @author:wxb
	 * @data:2017-4-10下午05:20:58
	 * @file:PapUtil.java
	 * @param month
	 * @return
	 * @throws Exception
	 */
	public static String computeNextMonth(String month)throws Exception{
		String mon=month.substring(5);
		String year=month.substring(0, 4);
		if(ArithUtil.comparison(mon, "12")==0){
			year=ArithUtil.add(year, "1",0);
			month=year+"-"+"01";
		}else{
			mon=ArithUtil.add(mon, "1",0);
			if(mon.length()==1)
				mon="0"+mon;
			month=year+"-"+mon;
		}
		return month;
	}
	/**
	 * 
	 * 功能:返回输入数的百分比表达形式，保留两位小数
	 * @author:wxb
	 * @data:2017-3-27上午11:55:57
	 * @file:PapUtil.java
	 * @param num
	 * @return
	 */
	public  static String convertPercent(String num)throws Exception{
		if(ArithUtil.comparison(num, "0")==0)
			return "0%";
		else
			return ArithUtil.mul(num, String.valueOf(100), 2)+"%";
	}
	/**
	 * 
	 * 功能:正则表达式匹配如"122/222"
	 * @author:wxb
	 * @data:2017-3-27下午04:51:16
	 * @file:PapUtil.java
	 * @param num
	 * @param flag
	 * @return
	 */
	public static String pickNum(String num,int flag)throws Exception{
		String regex = flag==0?"^\\d+":"/\\d+$";
		Matcher match=Pattern.compile(regex).matcher(num);
		if(match.find()){
			if(match.group().contains("/"))
				return match.group().substring(1);
			else
				return match.group();
		}else
			return null;
	}
	/**
	 * 
	 * 功能:查询月份有多少天
	 * @author:wxb
	 * @data:2017-4-11上午11:36:05
	 * @file:PapUtil.java
	 * @param month
	 * @return
	 * @throws Exception
	 */
	public static int getMonthDays(String month)throws Exception{
		SimpleDateFormat PAPDate = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal=Calendar.getInstance();
		String firstDay=month+"-01";
		Date date=PAPDate.parse(firstDay);
		cal.setTime(date);
//		System.out.println(cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		
//		String year=month.substring(0, 4);
//		String mon=month.substring(5, 7);
//		cal.set(Calendar.YEAR, Integer.parseInt(year));
//		cal.set(Calendar.MONTH, Integer.parseInt(mon));
//		cal.set(Calendar.DAY_OF_MONTH, 1);
//		
//		System.out.println(cal.getActualMaximum(Calendar.DAY_OF_MONTH));
//		System.out.println(cal.getMaximum(Calendar.DAY_OF_MONTH));
//		System.out.println(cal.get(Calendar.YEAR));
//		System.out.println(cal.get(Calendar.MONTH));
//		System.out.println(cal.get(Calendar.DAY_OF_MONTH));
		return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	}
	/**
	 * 判断字符串是否是日期
	 * @param str
	 * @return
	 */
	public static boolean isDate(String str){
		SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
		try {
			sdf.parse(str);
		} catch (ParseException e) {
			return false;
		}
		return true;
	}
	
//	/**
//	 * 根据给定的日期与天数，计算该天数之前的日期
//	 * @param dayNum
//	 * @param d
//	 * @return
//	 */
//	public static String getDayBefore(int dayNum,String d){
//		d = d.substring(0,10);
//		Calendar c = Calendar.getInstance();
//		try {
//			c.setTime(PAPDate.parse(d));
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		//c.add(Calendar.DAY_OF_MONTH, dayNum);
//		c.roll(Calendar.DAY_OF_MONTH, dayNum);
//	        
//	   return notFullDate(c.getTime()); 
//	        
//	}
	
	public static void main(String[] args) throws Exception {
			

		//System.out.println(compareDate("2017-10-10", "2017-10-08"));
		System.out.println(getWeeks("1801"));
		Integer.toHexString(100);
		System.out.println("testSomething");
		System.out.println("testSomething2");
	}
}
