package com.cp.epa.number.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.cp.epa.number.entity.NumberCache;
import com.cp.epa.number.entity.NumberDetail;
import com.cp.epa.number.entity.Number;

public abstract class NumberManager {
	//定义缓存Map
	private static Map<String,Object> cacheMap = new HashMap<String,Object>(); 
	//测试数据
	//private static int num;
	private Lock lock = new ReentrantLock();// 锁对象  
	
	/**
	 * 获取缓存
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Aug 2, 2013 10:35:57 AM <br/>
	 */
	public abstract NumberCache load(NumberCache _cache,String number,String numberScope,String year,String subobject) throws Exception;

//	public synchronized String get(String number,String numberScope,String year,String subobject)throws Exception{
//		synchronized(NumberManager.class){
//			//当前线程
//			//System.out.println("--------threadName1-------:"+Thread.currentThread().getName());
//			//创建某个号码对象中其中某个明细生成的缓存对象
//			Map<String,NumberCache> _numberCacheMap = null;
//			_numberCacheMap = (Map)cacheMap.get(number);
//			
//			String numberScopeKey = numberScope;
//			
//			if(year != null && !"".equals(year))
//				numberScopeKey += year;
//			if(subobject != null && !"".equals(subobject))
//				numberScopeKey += subobject;
//			
//			if(_numberCacheMap == null){
//				//将号码存储对象缓存到Map中
//				_numberCacheMap = new HashMap<String,NumberCache>();
//				_numberCacheMap.put(numberScopeKey, load(null,number,numberScope,year,subobject));
//				cacheMap.put(number, _numberCacheMap);
//				
//			}else{
//				NumberCache cache = _numberCacheMap.get(numberScopeKey);
//				if(cache == null){
//					_numberCacheMap.put(numberScopeKey,load(null,number,numberScope,year,subobject));
//				}else{
//					if(cache.hasNext()){
//						_numberCacheMap.put(numberScopeKey, load(cache,number,numberScope,year,subobject));
//					}
//				}
//			}
//			
//			
//			//测试
//			
//			//Map<String,Object> _testmap = cacheMap;
//			//Map<String,Object> _testmap = getLoad(number,numberScope,year,subobject);
//			//Map<String,NumberCache> _numberCacheMap = (Map)_testmap.get(number);
//			//try {
//			//	Thread.sleep(1000);
//			//} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//			//	e.printStackTrace();
//			//}
//			//----------------
//			NumberCache _cache = _numberCacheMap.get(numberScopeKey);
//			//System.out.println("-------------:"+_cache.getCurrent());
//			return getNext(_cache);
//		}
//	}
	
	public String get(String number,String numberScope,String year,String subobject)throws Exception{
		lock.lock();
		String id = null;
		try{
			//当前线程
			//System.out.println("--------threadName1-------:"+Thread.currentThread().getName());
			//创建某个号码对象中其中某个明细生成的缓存对象
			Map<String,NumberCache> _numberCacheMap = null;
			_numberCacheMap = (Map)cacheMap.get(number);
			
			String numberScopeKey = numberScope;
			
			if(year != null && !"".equals(year))
				numberScopeKey += year;
			if(subobject != null && !"".equals(subobject))
				numberScopeKey += subobject;
			
			if(_numberCacheMap == null){
				//将号码存储对象缓存到Map中
				_numberCacheMap = new HashMap<String,NumberCache>();
				_numberCacheMap.put(numberScopeKey, load(null,number,numberScope,year,subobject));
				cacheMap.put(number, _numberCacheMap);
				
			}else{
				NumberCache cache = _numberCacheMap.get(numberScopeKey);
				if(cache == null){
					_numberCacheMap.put(numberScopeKey,load(null,number,numberScope,year,subobject));
				}else{
					if(cache.hasNext()){
						_numberCacheMap.put(numberScopeKey, load(cache,number,numberScope,year,subobject));
					}
				}
			}
			
			
			//测试
			
			//Map<String,Object> _testmap = cacheMap;
			//Map<String,Object> _testmap = getLoad(number,numberScope,year,subobject);
			//Map<String,NumberCache> _numberCacheMap = (Map)_testmap.get(number);
			//try {
			//	Thread.sleep(1000);
			//} catch (InterruptedException e) {
				// TODO Auto-generated catch block
			//	e.printStackTrace();
			//}
			//----------------
			NumberCache _cache = _numberCacheMap.get(numberScopeKey);
			//System.out.println("-------------:"+_cache.getCurrent());
			id = getNext(_cache);
		}finally{
			lock.unlock();
		}
		return id;
	
}
	
	/**
	 * 检测
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Aug 4, 2013 10:43:13 AM <br/>
	 */
	public static boolean check(String id,Number number,NumberDetail numberDetail)throws Exception{
		if(id == null || "".equals(id))
			return false;
		//长度不合法
		int length = number.getLength();
		if(id.length() != length)
			return false;
		//前缀不合法
		String prefix = numberDetail.getPrefix();
		if(null != prefix && !"".equals(prefix)){
			int prefixLength = prefix.length();
			String idPrefix = id.substring(0,prefixLength);
			if(prefix.equals(idPrefix))
				return false;
		}
		//起始值终止值不合法
		String _id = id.replaceAll("[^\\d]", "");
		long _value = Long.parseLong(_id);
		 
	    if(_value > numberDetail.getEndNumber() || _value<numberDetail.getStartNumber())
	    	return false;
		return true;
	}	
	

	/**
	 * 通过缓存对象获得一个号码，并更新缓存对象
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Aug 2, 2013 11:46:47 AM <br/>
	 */
	public String getNext(NumberCache cache)throws Exception{
		//获得缓存的当前值
		int current =cache.getCurrentNumDyn();
		//System.out.println("当前值:"+current);
		//获得缓存的个数
		int cacheNumber = cache.getCacheNumDyn();
		//前缀
		String startWith = cache.getPrefix();
		//获取步长
		int step = cache.getStep();
		//缓存个数减一
		cache.setCacheNumDyn(cacheNumber-1);
		//cache.setCacheNumber(cacheNumber-1);
		//缓存的当前值+步长 作为使用以后的当前值
		//cache.setCurrentNumber(current+step);
		cache.setCurrentNumDyn(current+step);
		//System.out.println("--------threadName2-------:"+Thread.currentThread().getName());
		if(startWith==null)
			return String.format("%0"+ (cache.getLength()) + "d", current);
		return String.format(startWith+"%0"+ (cache.getLength()-(startWith.length())) + "d", current);
	}
	
	/**
	 * 获取缓存Map
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Aug 4, 2013 10:33:30 AM <br/>
	 */
//	private Map<String,Object> getLoad(String number,String numberScope,String year,String subobject)throws Exception{
//		
//		Map<String,NumberCache> _numberCacheMap = null;
//		_numberCacheMap = (Map)cacheMap.get(number);
//		
//		if(year != null && !"".equals(year))
//			numberScope += year;
//		if(subobject != null && !"".equals(subobject))
//			numberScope += subobject;
//		
//		if(_numberCacheMap == null){
//			//将号码存储对象缓存到Map中
//			_numberCacheMap = new HashMap<String,NumberCache>();
//			_numberCacheMap.put(numberScope, load(null,number,numberScope,year,subobject));
//			cacheMap.put(number, _numberCacheMap);
//			
//		}else{
//			NumberCache cache = _numberCacheMap.get(numberScope);
//			if(cache == null){
//				_numberCacheMap.put(numberScope,load(null,number,numberScope,year,subobject));
//			}else{
//				if(cache.hasNext()){
//					_numberCacheMap.put(numberScope, load(cache,number,numberScope,year,subobject));
//				}
//			}
//		}
//		return cacheMap;
//	}
}
