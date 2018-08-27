package com.cp.epa.sysconfig.utils;

import java.util.List;

/**
 * 类名：SysConfigUtil  <br />
 *
 * 功能：系统配置工具类
 *
 * @author 孟雪勤 <br />
 * 创建时间：2013-11-22 上午10:25:45  <br />
 * @version 2013-11-22
 */
public class SysConfigUtil {

	/**
	 * 功能：集合转换成以","分隔的SQL字符串<br/>
	 * 
	 * @author 孟雪勤
	 * @version 2012-6-11 下午05:05:27 <br/>
	 */
	public static String listToSQLStr(List<String> strList) {
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
	 * 功能：字符串数组转换为以","分隔的SQL字符串<br/>
	 *
	 * @author 孟雪勤
	 * @version 2013-11-16 上午11:40:18 <br/>
	 */
	public static String arrayToSQLStr(String[] array) {
		StringBuilder splitStr = new StringBuilder();
		if (null != array && array.length > 0) {
			for (String str : array) {
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
	 * 功能：将以逗号分隔的字符串转换为SQL字符串<br/>
	 *
	 * @author 孟雪勤
	 * @version 2012-10-11 上午10:14:38 <br/>
	 */
	public static String strToSQLStr(String str) {
		StringBuilder splitStr = new StringBuilder();
		String[] recs = str.split(",");
			for (String rec : recs) {
				splitStr.append("'" + rec + "'");
				splitStr.append(",");
			}
			splitStr.deleteCharAt(splitStr.length() - 1);
			
		return splitStr.toString();
	}
	
	/**
	 * 
	 * 功能：null转换为空<br/>
	 * 
	 * @author 孟雪勤
	 * @version 2011-9-5 上午08:41:15 <br/>
	 */
	public static String nullToEmpty(String strParam) {
		return (null == strParam || "".equals(strParam.trim())) ? "" : strParam.trim();
	}

	/**
	 * 
	 * 功能：空转换为null<br/>
	 * 
	 * @author 孟雪勤
	 * @version 2011-9-5 上午08:41:15 <br/>
	 */
	public static String emptyToNull(String str) {
		return (null == str || "".equals(str.trim())) ? null : str.trim();
	}
	
}
