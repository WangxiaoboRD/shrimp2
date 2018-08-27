package com.cp.epa.bussobj.utils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.Modifier;
import javassist.NotFoundException;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;

public class ClassUtils {
	
	
	/**
	 * 
	 * 功能：<br/>
	 * 获得包下所有类
	 * @author zp
	 * @version 2013-7-4 下午06:05:20 <br/>
	 */
	public static List<Class> getClasses(String packageName)throws ClassNotFoundException, IOException {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		String path = packageName.replace('.', '/');
		Enumeration<URL> resources = classLoader.getResources(path);
		List<File> dirs = new ArrayList<File>();
		while (resources.hasMoreElements()) {
			URL resource = resources.nextElement();
			String paths=resource.getFile();
			if(paths.indexOf("%20")!=-1){
				paths=paths.replaceAll("%20", " ");				
			}
			if(paths.indexOf("jar")!=-1)
				continue;
			dirs.add(new File(paths));
		}
		ArrayList<Class> classes = new ArrayList<Class>();
		for (File directory : dirs) {
			classes.addAll(findClasses(directory, packageName));
		}
		return classes;
	}

	/**
	 * 递归查询包下面的所有对象并通过反射生成Class
	 * 功能：<br/>
	 *
	 * @author 
	 * @version Dec 4, 2014 8:37:44 AM <br/>
	 */
	private static List<Class> findClasses(File directory, String packageName)throws ClassNotFoundException {

		List<Class> classes = new ArrayList<Class>();
		if (!directory.exists()) {
			return classes;
		}
		File[] files = directory.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				assert !file.getName().contains(".");
				classes.addAll(findClasses(file, packageName + "."+ file.getName()));
			} else if (file.getName().endsWith(".class")) {
				if(!file.getParent().contains("entity"))
					continue;
				classes.add(Class.forName(packageName+ '.'+ file.getName().substring(0,file.getName().length() - 6)));
			}
		}
		return classes;
	}
	
	
	
	
	/**
	 * 
	 * 功能：<br/>
	 * 获得本类及超类的所有字段
	 * @author zp
	 * @version 2013-7-4 下午06:04:36 <br/>
	 */
	public static List<Field> getAllFields(Class<?> class1) throws Exception{
		
		Field [] fields=class1.getDeclaredFields();		
		List<Field> listFields=new ArrayList<Field>();
		for(Field field:fields){
			listFields.add(field);
		}								
		List<Field> supperFields=new ArrayList<Field>();
		getAllSupperClass(class1.getSuperclass(),supperFields);		
		if(null!=supperFields&&supperFields.size()!=0){			
			listFields.addAll(supperFields);
		}		
		return listFields;
	}
	
	

	public  static  List<Field> getAllSupperClass(Class<?> class1,List<Field> list) throws Exception{
		  
		if(class1.getSimpleName().equals("Object")){
			return list;
		}else{
		    Field [] fields=class1.getDeclaredFields();
		    if(null!=fields&&fields.length!=0){
		    	for(Field fd:fields){
		    		list.add(fd);
		    	}
		    }			
			Class<?> class2=class1.getSuperclass();
			return getAllSupperClass(class2, list);
			
		}
	}
	

	
	
	
	/**
	 * 
	 * 功能：<br/>
	 * 获得所有方法
	 * @author zp
	 * @version 2013-7-12 下午04:50:11 <br/>
	 */
	public static List<Method> getAllMethods(Class<?> class1){
		
		List<Method> set=null;
		
		Method [] methods=class1.getMethods();
		
		if(null!=methods&&methods.length!=0){
			
			set=new ArrayList<Method>();
			
			for(Method mm:methods){
								
				if(!set.contains(mm)){
					
					set.add(mm);
				}
				
			}
		}
		
		return set;
	}


	
	
	/**
	 * 
	 * 功能：<br/>
	 * 获得指定方法的实际参数名
	 * @author zp
	 * @version 2013-7-12 上午09:52:27 <br/>
	 */
	public static List<String> getMethodParamName(Class<?> clazz,Method method){
		
		List<String> list=new ArrayList<String>();
		
		 //  Class clazz = ClassUtils.class;  
	        try {  
	            ClassPool pool = ClassPool.getDefault();  
	            CtClass cc = pool.get(clazz.getName());  
	            
	            CtMethod cm=null;
	            
	            CtMethod [] ctMethods= cc.getMethods();
	            
	            for(CtMethod ct:ctMethods){
	            	
	            	if(ct.getName().equals(method.getName())){
	            		
	            		//System.out.println(method.getName());
	            		//方法参数类型
	            		CtClass [] classes=ct.getParameterTypes();
	            		
	            		//类型长度不等，则不相等
	            		if(classes.length!=method.getParameterTypes().length){
	            			continue;
	            		}
	            		
	            		Class<?> class1s [] =method.getParameterTypes();	            		
	            		List<String> typeList=null;
	            		if(null!=class1s&&class1s.length!=0){	            			
	            			typeList=new ArrayList<String>();
	            			for(Class<?> cll:class1s){
	            				typeList.add(cll.getName());
	            			}	            				            			
	            		}
	            		
	            		if(null==typeList){
	            			continue;
	            		}
	            		if(typeList.size()!=classes.length){
	            			continue;
	            		}
	            		
	            		boolean iseq=true;	            		            		
	            		for(int i=0;i<classes.length;i++){
	            			String type=classes[i].getName();
	            			if(type.indexOf("[]")!=-1){
	            				if(typeList.get(i).indexOf("[")!=-1){	            					
	            					type=type.substring(0,type.indexOf("[]"));	            					
	            					if(typeList.get(i).indexOf(type)!=-1){
	            						continue;
	            					}else{
	            						iseq=false;
	            					}
	            				}else{
	            					iseq=false;
	            				}	            				
	            			}else{
	            				//参数类型对应相等，不等则继续
		            			if(!type.equals(typeList.get(i))){
		            				iseq=false;
		            			}else{
		            				continue;
		            			}	            				            				            					            				
	            			}
	            			
	            		}

	            		if(iseq){
		            		cm=ct;
		            		break;	            		
	            		}
	            		
	            	}else{
	            		continue;
	            	}	            	
	            	
	            }
	            
	            if(null==cm){
	            	return null;
	            }
	            
	           // CtMethod cm = cc.getDeclaredMethod(methodName);  	      	    
	            //cm=ctMethod;	          	            
	           // cc.getMethods();
	            
	            MethodInfo methodInfo = cm.getMethodInfo();  
	            CodeAttribute codeAttribute = methodInfo.getCodeAttribute();  
	            if(null==codeAttribute){
	            	return null;
	            }
	            
	            LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);  
	            if (attr == null) {  
	                return list;
	            }  
	            String[] paramNames = new String[cm.getParameterTypes().length];  
	            int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;  
	            for (int i = 0; i < paramNames.length; i++){
	            	try{
		                paramNames[i] = attr.variableName(i + pos);  
	            	}catch(Exception e){
		            	System.out.println(" attr "+attr.length()+"    i+pos  "+(i+pos)+"    param  "+paramNames.length);
	            		continue;
	            	}

	            }  
	            	
	        
	            for (int i = 0; i < paramNames.length; i++) {  
	                list.add(paramNames[i]);
	            }  
	      
	        } catch (NotFoundException e) {  
	           System.out.println(e.getMessage());
	        }  
		
		
		return list;
	}
	
	
	/**
	 * 
	 * 功能：<br/>
	 * 获得方法参数类型和实际参数的对照表
	 * @author zp
	 * @version 2013-7-12 上午10:00:39 <br/>
	 */
	public static Map<String, String> getMethodParamMap(Class<?> class1,Method methodName){
		
		Map<String, String> map=null;
		Method [] methods=class1.getMethods();
		Method mm = null;
		for(Method method2:methods){
		   if(method2.equals(methodName)){
			   mm=method2;
			   break;
		   }				
		}
		
		if(null!=mm){
			Class<?> class2 []=mm.getParameterTypes();
			List<String> list=getMethodParamName(class1, mm);
			map=new HashMap<String, String>();
			//System.out.println(mm.getName());
			if(null==class2||null==list||class2.length!=list.size()){
				return map;
			}
			for(int i=0;i<class2.length;i++){
				map.put(class2[i].getName(),list.get(i));			
			}
		}
		
		return map;
	}
	
	
	/**
	 * 
	 * 功能：<br/>
	 * 获得处理后的method
	 * @author zp
	 * @version 2013-7-12 下午05:33:08 <br/>
	 */
	public static List<Method> getDealMethods(Class<?> class2){
		
		
		List<Method> set=getAllMethods(class2);
		
		List<Method> list=new ArrayList<Method>();
		
		
		for(Method mm:set){
			
			Class<?> class1[] =mm.getParameterTypes();
			
			String type="";
			
			if(null!=class1&&class1.length!=0){
			  
				for(Class<?> cll:class1){
					type=type+","+cll.getName();
				}
				
				if(type.indexOf(",")!=-1){
					
					type=type.substring(type.indexOf(",")+1);

				}
			}
			
			String med=mm.getDeclaringClass().getName()+"."+mm.getName()+"("+type+")";
						
			if(med.indexOf("impl")!=-1&&med.indexOf("BaseEntity")!=-1||med.indexOf("java.lang.Object")!=-1){

				continue;
			}else{
				
				list.add(mm);

			}
			
		}
		
		return list;
	}
	
	
	
	
	
	
	public static void main(String [] ss){
		
/*		Method[] methods=BaseTableColumnServiceImpl.class.getMethods();
		
		for(Method mm:methods){			
			
			Class<?> class1[] =mm.getParameterTypes();
			
			String type="";
			
			if(null!=class1&&class1.length!=0){
			  
				for(Class<?> cll:class1){
					type=type+","+cll.getName();
				}
				
				if(type.indexOf(",")!=-1){
					
					type=type.substring(type.indexOf(",")+1);

				}
			}
			
			String med=mm.getDeclaringClass().getName()+"."+mm.getName()+"("+type+")";
			
			if(med.indexOf("impl")!=-1&&med.indexOf("BaseEntity")!=-1||med.indexOf("java.lang.Object")!=-1){

				continue;
			}else{
				
				 // System.out.println(mm.getDeclaringClass().getName());				
				   Map<String, String> map=getMethodParamMap(BaseTableColumnServiceImpl.class, mm);				   
				   System.out.println(mm.getName()+"    "+map);		   

			}
				   
		}
*/				
		
		
		try {
			List<Class> list=getClasses("com");
			if(null!=list){
				for(Class<?> lt:list){
					
					System.out.println(lt.getName());
					
				}
				
			}
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
				
	}

	
	
	
	
	
	
	
	
	
}
