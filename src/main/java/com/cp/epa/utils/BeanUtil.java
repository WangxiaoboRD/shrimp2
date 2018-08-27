package com.cp.epa.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.struts2.json.JSONUtil;

public class BeanUtil {
	
	
	
	/**
	 * 
	 * 功能：<br/>
	 * map封装成对象
	 * @author zp
	 * @version 2013-6-12 下午03:43:06 <br/>
	 * @throws Exception 
	 */
	public static <T> T toBean(Class<T> class1,Map<String, Object> map) throws Exception{
			
		Object obj=class1.getConstructor(null).newInstance(null);
		for(String key:map.keySet()){
		  if(!key.contains(".")){				  
			try {
			   Field field=class1.getDeclaredField(key);	
			   if(null==field){
				   continue;
			   }
			   String meth="set"+field.getName().substring(0, 1).toUpperCase()+field.getName().substring(1);
			   Method method= class1.getMethod(meth, field.getType());
		       Constructor constructor= field.getType().getConstructor(String.class);
			   if(null!=constructor){
				   if(null!=map.get(key)&&!"".equals(map.get(key))){
					   method.invoke(obj, constructor.newInstance(map.get(key).toString()));
				   }else{
					   Object oo=null;
					   if(constructor.getName().indexOf("Stri")!=-1){
						   oo="";
					   }
					   method.invoke(obj,oo);					   
				   }
			   }else{
				   method.invoke(obj, map.get(key));
			  }
			} catch (Exception e) {
				e.printStackTrace();
			} 				  
		  }else{				  
			   try {
			   //consume
				Field field=class1.getDeclaredField(key.substring(0, key.indexOf(".")));	
				if(null==field){
					continue;
				}
				String meth="set"+field.getName().substring(0, 1).toUpperCase()+field.getName().substring(1);
				Method method= class1.getMethod(meth, field.getType());
				//consume
				Constructor  constructor=field.getType().getConstructor(null);
				Object object=constructor.newInstance(null);
				//consumeCode 类型
				Class<?> subclass=field.getType();
				Field subField=subclass.getDeclaredField(key.substring(key.indexOf(".")+1));					
				//setConsumeCode 方法
				@SuppressWarnings("unused")
				String submeth="set"+subField.getName().substring(0, 1).toUpperCase()+subField.getName().substring(1);				 
				System.out.println("--subclass--"+subclass.getSimpleName()+"---meth-------------------");
				@SuppressWarnings("unused")
				Method subMethod= subclass.getMethod(submeth, subField.getType());
			    Constructor consu= subField.getType().getConstructor(String.class);
				if(null!=consu){
					  if(null!=map.get(key)&&!"".equals(map.get(key))){							  
						   System.out.println("------"+consu.getName()+"---------"+map.get(key));
						   String param=map.get(key).toString();
						   subMethod.invoke(object, consu.newInstance(map.get(key).toString()));
				  }else{
						 // subMethod.invoke(object, null);
					  }
				}else{
					   subMethod.invoke(object, map.get(key));
			    }
				method.invoke(obj, object);
			} catch (Exception e) {
				e.printStackTrace();
			} 
		  }					
		}
				
		return (T) obj;
	}
	
	
	
	
	
	@SuppressWarnings("unchecked")
	public static <T> List<T> toBeanList(Class<T> class1,String jsondetail) throws Exception{
		
		List<T> li=new ArrayList<T>();		
/*		for(String key:detail.keySet()){
			if(key.equals("e.detail")){
				String jsondetail=detail.get(key).toString();*/
			    if(jsondetail.indexOf("_")!=-1){
			    	jsondetail=jsondetail.replaceAll("_", ".");
			    }
		      //  jsondetail="["+jsondetail.replaceAll("_", ".")+"]";;
				List<Map<String, Object>> list=(List<Map<String, Object>>) JSONUtil.deserialize(jsondetail);
				if(null!=list&&list.size()!=0){					
					for(Map<String, Object> map:list){
						T outStockDetail=BeanUtil.toBean(class1, map);
						li.add(outStockDetail);						
					}					
					System.out.println("================");
				}
				
			//}
		//}
		
		return li;
	}
	
	
	
	public static void main(String [] sd){
		
	}
	
	
	
	

}
