/**
 * 文件名：@ReportUtil.java <br/>
 * 包名：com.zhongpin.pap.bussobj.utils <br/>
 * 项目名：PAP1.0 <br/>
 * @author 孟雪勤 <br/>
 */
package com.cp.epa.report.utils;

import java.io.File;

import org.apache.struts2.ServletActionContext;

/**
 * 类名：ReportUtil  <br />
 *
 * 功能：报表工具类
 *
 * @author 孟雪勤 <br />
 * 创建时间：2013-12-12 上午11:08:33  <br />
 * @version 2013-12-12
 */
public class ReportUtil {

	/**
	 * 功能：获得报表根路径<br/>
	 *
	 * @author 孟雪勤
	 * @version 2013-12-12 上午11:09:29 <br/>
	 */
	public static String getReportRootPath() {
		String root = ServletActionContext.getServletContext().getRealPath("/");
		String path = root + "WEB-INF/reportlets/";
		return path;
	}
	
	/**
	 * 功能：返回指定路径的报表完整路径<br/>
	 * 格式：report/
	 * @author 孟雪勤
	 * @version 2013-12-12 上午11:12:38 <br/>
	 */
	public static String getReportPath(String path) {
		if (null != path) {
			return getReportRootPath() + path;
		}
		return getReportRootPath();
	}
	
	/**
	 * 功能：返回指定<br/>
	 *
	 * @author 孟雪勤
	 * @version 2013-12-12 上午11:15:46 <br/>
	 */
	public static String getReportFile(String path, String fileName) {
		if (null != fileName) {
			return getReportPath(path) + fileName;
		}
		return null;
	}
	
	/**
	 * 功能：<br/>
	 *
	 * @author 孟雪勤
	 * @version 2013-12-12 上午11:47:01 <br/>
	 */
	public static File getReportFile(String fileName) {
		if (null == fileName || "".equals(fileName)) {
			return null;
		}
		return new File(getReportRootPath() + fileName);
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
