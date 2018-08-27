package com.cp.epa.utils;

/**
 * 内部类回调接口
 * 类名：HqlCallBack  
 * 功能：
 * @author dzl 
 * 创建时间：May 20, 2013 8:58:47 AM 
 * @version May 20, 2013
 */
public interface HqlCallBack {
	String doIn(String preHql,String setHql,String posHql);
}
