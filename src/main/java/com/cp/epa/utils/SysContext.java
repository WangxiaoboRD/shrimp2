package com.cp.epa.utils;

import java.util.HashMap;
import java.util.Map;

/** 
 * 类名：SysContext  
 * 功能：
 * 系统内置容器
 * @author dzl 
 * 创建时间：Mar 28, 2013 9:31:15 AM 
 * @version Mar 28, 2013
 */
public class SysContext {
	//request容器
	
	/**
	 * ThreadLocal 内存泄露问题
	 * 
	 * 事实上每个Thread实例都有一个ThreadLocalMap成员变量，它以ThreadLocal对象为key，
	 * 以ThreadLocal绑定的对象为Value。我们调用ThreadLocal的set()方法，
	 * 只是把要绑定的对象存放在当前线程的ThreadLocalMap成员变量中，以便下次通过get()方法取得它。
	 * ThreadLocalMap和普通map的最大区别就是它的Entry是针对ThreadLocal弱引用的，
	 * 即当ThreadLocal没有其他引用为空时，JVM就可以GC回收ThreadLocal，从而得到一个null的key。
	 * ThreadlocalMap维护了ThreadLocal对象和其绑定对象之间的关系，这个ThreadLocalMap有threshold(临界值)，
	 * 当超过threshold时，ThreadLocalMap会首先检查内部ThreadLocal引用（前文说过，ThreadLocal是弱引用可以释放）是否为null，
	 * 如果存在null，那么把绑定对象的引用设置为null,以便释放ThreadLocal绑定的对象，这样就腾出了位置给新的ThreadLocal。
	 *
	 * 从上可以看出Java已经充分考虑了时间和空间的权衡，
	 * 但是因为置为null的ThreadLocal对应的Object Value在无外部引用时，任然无法及时回收。
	 * 因为ThreadLocalMap只有到达threshold(临界值)时或添加entry时才做检查，不似gc是定时检查，
	 * 不过我们可以手工通过ThreadLocal的remove()方法或set(null)解除ThreadLocalMap对ThreadLocal绑定对象的引用，
	 * 及时的清理废弃的threadlocal绑定对象的内存以。remove()往往还能做更多的清理工作，因此推荐使用它,而不使用set(null)
	 * 需要说明的是，只要不往不用的threadlocal中放入大量数据，问题不大，毕竟还有回收的机制。
	 * 
	 * 被废弃了的ThreadLocal所绑定对象的引用，会在以下4情况被清理。
	 * 如果此时外部没有绑定对象的引用，则该绑定对象就能被回收了：
	 * 1 Thread结束时。
	 * 2 当Thread的ThreadLocalMap的threshold超过最大值时。
	 * 3 向Thread的ThreadLocalMap中存放一个ThreadLocal，hash算法没有命中既有Entry,而需要新建一个Entry时。
	 * 4 手工通过ThreadLocal的remove()方法或set(null)
	 */
	private static ThreadLocal<Map<String,Object>> sessionLocal= new ThreadLocal<Map<String,Object>>();
	
	/**
	 * 设置单个键值对到当前线程中去
	 * 功能：<br/>
	 * @author 杜中良
	 * @version Mar 30, 2013 10:32:08 AM <br/>
	 */
	public static <T> void setSession(String key,T value){
		Map<String,Object> map = sessionLocal.get();
		if(map == null)
			sessionLocal.set(map = new HashMap<String, Object>());
		map.put(key, value);
	}
	
	/**
	 * 通过键值对的方式获取单个值
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Mar 30, 2013 10:34:08 AM <br/>
	 */
	@SuppressWarnings({"unchecked", "unused" })
	public static <T> T get(String key) {
		Map<String, Object> map = sessionLocal.get();
		if (null == map)
			return null;
		return (T) map.get(key);
	}
	
	/**
	 * 每次保存一个Map对象
	 * 功能：<br/>
	 * @author 杜中良
	 * @version Mar 30, 2013 10:37:51 AM <br/>
	 */
	public static void set(Map<String,Object> map){
		if(sessionLocal.get() == null)
			sessionLocal.set(map);
	}
	
	/**
	 * 每次获取一个map对象
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Mar 30, 2013 10:40:02 AM <br/>
	 */
	public static Map<String,Object> get(){
		Map<String, Object> map = sessionLocal.get();
		if(map == null)
			return null;
		return map;
	}
	
	/**
	 * 功能：<br/>
	 * 注销掉当前线程容器
	 * @author 杜中良
	 * @version Mar 30, 2013 10:40:22 AM <br/>
	 */
	public static void romove(){
		Map<String, Object> map = sessionLocal.get();
		sessionLocal.remove();
		Map<String, Object> map1 = sessionLocal.get();
		System.out.println();
		//sessionLocal = null;
	}
}
