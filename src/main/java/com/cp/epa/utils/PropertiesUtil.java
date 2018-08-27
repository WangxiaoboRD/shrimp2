/**
 * 文件名：@Configuration.java <br/>
 * 包名：com.zhongpin.finereport <br/>
 * 项目名：CommonUtil0 <br/>
 * @author 孟雪勤 <br/>
 */
package com.cp.epa.utils;

import java.io.UnsupportedEncodingException;
import java.util.ResourceBundle;

/**
 * 类名：Configuration <br />
 * 
 * 功能：读入properties属性文件
 * 
 * @author 孟雪勤 <br />
 *         创建时间：2012-6-7 上午10:49:57 <br />
 * @version 2012-6-7
 */
public class PropertiesUtil {
	
	/** 属性文件获取 */
	private static ResourceBundle resource;

	public PropertiesUtil(String fileName) {
		resource = ResourceBundle.getBundle(fileName);
	}
	
	/**
	 * 功能：根据属性名获得属性值<br/>
	 *
	 * @author 孟雪勤
	 * @version 2012-8-7 上午10:40:47 <br/>
	 */
	public String getProperty(String key) {
	    try {
			return resource.containsKey(key) ? new String(resource.getString(key).getBytes("ISO-8859-1"), "UTF-8") : null;
		}catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	 }
	
}
