package com.cp.epa.utils;
import java.beans.Introspector;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import com.cp.epa.base.BaseEntity;
import com.cp.epa.bussobj.entity.BussinessEle;
import com.cp.epa.number.entity.Number;

public class TypeUtil {
	
	/**
	 * 获取当前类的基类所持有的泛型类型（按照基类泛型排列顺序生成类型Type的数组）
	 * 功能：<br/>
	 * @author 杜中良
	 * @version Mar 17, 2013 10:05:48 AM <br/>
	 */
	public static Type[] getActualTypes(Class<?> clazz) {
		
		Type[] parameterizedType = null;
		//Class clazz = getClass();
		//通过getGenericSuperclass()返回表示此 Class 所表示的实体（类、接口、基本类型或 void）等表示类型的类的直接超类的 Type
		Type type = clazz.getGenericSuperclass();
		//将Type直接转换为ParameterizedType，这个类表示获取的是这个类的参数化类型，也就是它的泛型类型
		if (type != null && type instanceof ParameterizedType) {
			// 通过ParameterizedType的getActualTypeArguments()获取这个类的所有泛型化参数的真是类型
			parameterizedType = ((ParameterizedType) type).getActualTypeArguments();
			//取它的第一个类型
			//this.etype = (Class<E>) parameterizedType[0];
		}
		return parameterizedType;
	}
	
	/**
	 * 通过反射的功能获取类的主键属性名称
	 * 功能：<br/>
	 * @author 杜中良
	 * @version Apr 25, 2013 10:19:18 AM <br/>
	 */
	public static String getClassIdField(Class<?> clazz){
		if(clazz == null)
			return null;
		Method[] methods = clazz.getMethods();
		String idName="";
        for(Method m : methods){
        	//m.getParameterAnnotations();
        	Annotation anm = m.getAnnotation(Id.class);
        	if(anm != null){
        		idName = Introspector.decapitalize(m.getName().substring(3));
        		break;
        	}
//        	for(Annotation amm : anm){
//        		
//        		Class<?> type = amm.annotationType();
//        		type.equals(Id.class);
//        		type.cast(obj)
//        		
//        		
//        		System.out.println("--------------:"+amm.annotationType());
//        	}
        }
		return idName;
	}
	
	/**
	 * 通过反射获取对象中不需要存储到数据库的字段
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Jun 14, 2013 3:29:59 PM <br/>
	 */
	public static Set<String> getTransientField(Class<?> clazz){
		if(clazz == null)
			return null;
		Method[] methods = clazz.getMethods();
		Set<String> fields = new HashSet<String>();
        for(Method m : methods){
        	//m.getParameterAnnotations();
        	Annotation anm = m.getAnnotation(Transient.class);
        	if(anm != null){
        		String filedName = Introspector.decapitalize(m.getName().substring(3));
        		fields.add(filedName);
        	}
        }
        return fields;
	}
	
	//public static Set
	
	/**
	 * 通过反射的功能获取类的主键的类型
	 * 功能：<br/>
	 * @author 杜中良
	 * @version Apr 25, 2013 10:19:18 AM <br/>
	 */
	public static String getClassIdType(Class<?> clazz){
		if(clazz == null)
			return null;
		Method[] methods = clazz.getMethods();
		String typeName="";
        for(Method m : methods){
        	Annotation anm = m.getAnnotation(Id.class);
        	if(anm != null){
        		typeName = m.getReturnType().getName();
        		break;
        	}
        }
		return typeName;
	}
	
	/**
	 * 通过给定对象与给定的属性名获得值
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Jul 10, 2013 5:58:25 PM <br/> 
	 */
	public static <T extends BaseEntity> List<Object> getFieldValues(T t,List<String> fieldName){
		if(t == null)
			return null;
		if(fieldName == null || fieldName.size()==0)
			return null;
		List<Object> list = new ArrayList<Object>();
		for(String field : fieldName){
			try {
				list.add(getFieldValue(t,field));
			} catch (Exception e) {
				e.printStackTrace();
			} 	
		}
		return list;
	}
	
	/**
	 * 功能：根据前端收集的列名获得其对应字段值<br/>
	 *
	 * @author 孟雪勤
	 * @version 2014-10-5 上午11:35:40 <br/>
	 */
	public static <T extends BaseEntity> List<Object> getValueByNaviField(T t, List<String> fieldName){
		if(t == null)
			return null;
		if(fieldName == null || fieldName.size()==0)
			return null;
		List<Object> list = new ArrayList<Object>();
		for(String field : fieldName){
			list.add(getNavigationEndValue(t, field));
		}
		return list;
	}
	
	/**
	 * 通过类与属性获得值
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Jul 11, 2013 4:37:42 PM <br/>
	 */
	public static Object getFieldValue(Object t,String field) throws Exception{
		String fileName = field;
		int index = fileName.indexOf(".");
		if(index != -1){
			fileName = field.substring(0,index);
			//修改1
			field = field.substring(index+1);
		}
		String getMethodName = "get"+ fileName.substring(0, 1).toUpperCase()+ fileName.substring(1);
		Method getMethod = t.getClass().getMethod(getMethodName,new Class[] {});
		Object value = getMethod.invoke(t, new Object[] {});
		if(index == -1 || value==null)
			return value;
		else
			//修改2
			return getFieldValue(value,field);
//		return null;
	}
	
	/**
	 * 通过类与属性 设置值
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Sep 30, 2013 4:27:43 PM <br/>
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public static void setFieldValue(Object t,String field,String argsType,Object args) {
		String setMethodName = "set" + PapUtil.toFirstLetterUpperCase(field);
		//System.out.println("-----:"+o.getClass().getMethod(setMethodName, entity.getClass()));
		Method _m = null;
		try {
			if("String".equals(argsType))
				_m = t.getClass().getMethod(setMethodName,String.class);
			if("int".equals(argsType))
				_m = t.getClass().getMethod(setMethodName,int.class);
			if("Integer".equals(argsType))
				_m = t.getClass().getMethod(setMethodName,Integer.class);
			if("Long".equals(argsType))
				_m = t.getClass().getMethod(setMethodName,Long.class);
			if("long".equals(argsType))
				_m = t.getClass().getMethod(setMethodName,long.class);
			_m.invoke(t, args);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 将表头对象传入到明细信息中
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Jun 15, 2013 5:56:00 PM <br/>
	 */
	public static <T> T getEntitySetDetail(T entity){
		// //////////////方法的参数
//        System.out.println(" paramTypeType: ");
//		Type[] paramTypeList = m.getGenericParameterTypes();// 方法的参数列表
//		for (Type paramType : paramTypeList) {
//			System.out.println("  " + paramType);// 参数类型
//			if (paramType instanceof ParameterizedType)/* 如果是泛型类型 */{
//				Type[] types = ((ParameterizedType) paramType).getActualTypeArguments();// 泛型类型列表
//				System.out.println("  TypeArgument: ");
//				for (Type type : types) {
//					System.out.println("   " + type);
//				}
//			}
//		}
		 // //////////////方法的返回值
//        System.out.println(" returnType: ");
//        Type returnType = method.getGenericReturnType();// 返回类型
//        System.out.println("  " + returnType);
//        if (returnType instanceof ParameterizedType)/* 如果是泛型类型 */{
//            Type[] types = ((ParameterizedType) returnType)
//                    .getActualTypeArguments();// 泛型类型列表
//            System.out.println("  TypeArgument: ");
//            for (Type type : types) {
//                System.out.println("   " + type);
//            }
//        }
//		
		if(entity == null)
			return null;
		Method[] methods = entity.getClass().getMethods();
		for(Method m : methods){
			Annotation anm = m.getAnnotation(OneToMany.class);
        	if(anm != null){
				try {
					Object _o = m.invoke(entity);
					Collection c=(Collection)_o;
					if(c!= null && c.size()>0){
						for(Object o : c){
							Field[] fields = o.getClass().getDeclaredFields();
							for(Field _f : fields){
								if(_f.getType().getSimpleName().equals(entity.getClass().getSimpleName())){
									//System.out.println("--------:"+_f.getName());
									String setMethodName = "set" + PapUtil.toFirstLetterUpperCase(_f.getName());
									//System.out.println("-----:"+o.getClass().getMethod(setMethodName, entity.getClass()));
									Method _m = o.getClass().getMethod(setMethodName, entity.getClass());
									_m.invoke(o, entity);
								}
							}
						}
					}
				} catch (IllegalArgumentException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (InvocationTargetException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
        }
		return entity;
	} 
	
	/**
	 * 通过类与类的属性获得该属性对应的父类型名称
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Dec 5, 2013 4:32:46 PM <br/>
	 */
	public static String getPropertySuperType(Class<?> clazz,String propertyName){
		//通过类获得属性列表
		String _typeName = "";
		try {
			Field filed = clazz.getDeclaredField(propertyName);
			Class<?> _c = filed.getType();
			//判断是基本类型
			if(_c.isPrimitive())
				return "java.lang.Object";
			_typeName = _c.getSuperclass().getName();

		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		System.out.println(int.class.isPrimitive());
//		System.out.println(Integer.class.isPrimitive());
//		System.out.println(short.class.isPrimitive());
//		System.out.println(Short.class.isPrimitive());
//		System.out.println(char.class.isPrimitive());
//		System.out.println(Character.class.isPrimitive());
//		System.out.println(byte.class.isPrimitive());
//		System.out.println(Byte.class.isPrimitive());
//		System.out.println(boolean.class.isPrimitive());
//		System.out.println(Boolean.class.isPrimitive());
//		System.out.println(Void.class.isPrimitive());
//		System.out.println(void.class.isPrimitive());
//		System.out.println(String.class.isPrimitive());
		return _typeName;
	}
	
	/**
	 * 通过类与类的属性获得该属性对应的父类型名称
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Dec 5, 2013 4:32:46 PM <br/>
	 */
	public static Class<?> getPropertyType(Class<?> clazz,String propertyName){
		//通过类获得属性列表
		Class<?> cla= null;
		try {
			Field filed = clazz.getDeclaredField(propertyName);
			cla  = filed.getType();

		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return cla;
	}
	
	
	/**
	 * 对象copy功能
	 * 功能：<br/>
	 * t1原对象 指原始对象
	 * t2目标对象  指新对象 要将原对象的值复制到这个对象上
	 * @author 杜中良
	 * @version Jul 1, 2014 4:34:21 PM <br/>
	 */
	public static <T> T copy(T source,T target){
		
		//获得目标对象的class
		Class<?> _targetClazz = target.getClass();
		//获得目标对象super的clazz
		Class<?> _tSuperClazz = _targetClazz.getSuperclass();
		//获得目标对象的所有属性，包含父类与子类
		List<Field> fields = new ArrayList<Field>(Arrays.asList(_tSuperClazz.getDeclaredFields()));
		fields.addAll(new ArrayList<Field>(Arrays.asList(_targetClazz.getDeclaredFields())));
		//获得源对象的属性及父类属性
		//获得源对象的class
		Class<?> _sourceClazz = source.getClass();
		//获得源对象super的clazz
		Class<?> _sSuperClazz = _sourceClazz.getSuperclass();
		//获得源对象的所有属性，包含父类与子类
		List<Field> sourcefields = new ArrayList<Field>(Arrays.asList(_sSuperClazz.getDeclaredFields()));
		sourcefields.addAll(new ArrayList<Field>(Arrays.asList(_sourceClazz.getDeclaredFields())));
		
		//遍历目标属性 将源对象中与目标对象属性相同的并且值不为空的属性值赋给目标对象
		loop:for(Field f : fields){
			String typeName = f.getType().getName();
			String propertyName = f.getName();
			if("org.apache.log4j.Logger".equals(typeName))
				continue;
			if("serialVersionUID".equals(propertyName))
				continue;
			for(Field _f : sourcefields){
				//判断 属性名称 与属性类型
				if(typeName.equals(_f.getType().getName()) && propertyName.equals(_f.getName())){
					//如果一致的情况下，将源对象该属性的值赋给目标对象的该属性
					//1.源对象该属性的值
					try {
						Object obj = getFieldValue(source,_f.getName());
						if(obj != null){
							//将值set到目标对象中
							//设置方法名
							String setMethodName = "set" + PapUtil.toFirstLetterUpperCase(propertyName);
							Method method = _targetClazz.getMethod(setMethodName,f.getType());
							//调用该方法
							method.invoke(target, obj);
						}
						continue loop;
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SecurityException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (NoSuchMethodException e) {
						//
						e.printStackTrace();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		return target;
	}
	
	/**
	 * 给定属性与Class，判断该对象是不是存在该属性
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Aug 17, 2014 1:11:27 PM <br/>
	 */
	public static boolean isIncludeField(String fieldName,Class<?> clazz){
		//获得对象super的clazz
		Class<?> _tSuperClazz = clazz.getSuperclass();
		//获得目标对象的所有属性，包含父类与子类
		List<Field> fields = new ArrayList<Field>(Arrays.asList(_tSuperClazz.getDeclaredFields()));
		fields.addAll(new ArrayList<Field>(Arrays.asList(clazz.getDeclaredFields())));
		for(Field _f : fields){
			if(fieldName.equals(_f.getName()))
				return true;
		}
		return false;
	} 
	
	/**
	 * 给定属性与Class，判断该对象是不是存在该属性
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Aug 17, 2014 1:11:27 PM <br/>
	 */
	public static boolean isIncludeFieldBySelf(String fieldName,Class<?> clazz){
		Field [] fields = clazz.getDeclaredFields();
		for(Field _f : fields){
			if(fieldName.equals(_f.getName()))
				return true;
		}
		return false;
	} 
	
	
	/**
	 * 通过反射的功能获取类属性的值
	 * 功能：<br/>
	 * @author 杜中良
	 * @version Apr 25, 2013 10:19:18 AM <br/>
	 */
//	public static String getFieldValue(Class<?> clazz){
//		if(clazz == null)
//			return null;
//		Method[] methods = clazz.getMethods();
//		String typeName="";
//        for(Method m : methods){
//        	
//        	typeName = m.getName();
//        	System.out.println("m:"+m);
//        }
//		return typeName;
//	}
	
	/**
	 * 
	 * 功能：获得给定对象导航末端的属性值<br/>
	 *
	 * @author xtwin
	 * @version 2011-9-22 上午11:05:54 <br/>
	 */
	public static Object getNavigationEndValue(Object m, String navi) {
		if (null == m)
			return null;
		
		try {
			Object tmp = m;
			do {
				int index = navi.indexOf('.');
				
				String name = null;
				if (index != -1) {
					name = navi.substring(0, index);
					navi = navi.substring(index + 1);
				} else
					name = navi;
				
				Class<?> clazz = tmp.getClass();
				
				Method method = clazz.getMethod(toGetMethodName(name));
				
				Object v = method.invoke(tmp);
				
				if (index == -1 || null == v)
					return v;
				
				tmp = v;
			} while (true);
		}catch (SecurityException e) {
			e.printStackTrace();
		}catch (NoSuchMethodException e) {
			e.printStackTrace();
		}catch (IllegalArgumentException e) {
			e.printStackTrace();
		}catch (IllegalAccessException e) {
			e.printStackTrace();
		}catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * 
	 * 功能：获取标准get方法名<br/>
	 *
	 * @author xtwin
	 * @version 2011-9-16 上午11:14:37 <br/>
	 */
	public static String toGetMethodName(String name) {
		return toBeanMethodName(name, "get");
	}
	
	/**
	 * 
	 * 功能：获取标准set方法名<br/>
	 *
	 * @author xtwin
	 * @version 2011-9-16 上午11:18:32 <br/>
	 */
	public static String toSetMethodName(String name) {
		return toBeanMethodName(name, "set");
	}
	
	/**
	 * 
	 * 功能：<br/>
	 *
	 * @author xtwin
	 * @version 2011-9-16 上午11:19:18 <br/>
	 */
	private static String toBeanMethodName(String name, String getOrSet) {
		StringBuilder mname = new StringBuilder(getOrSet).append(("" + name.charAt(0)).toUpperCase());
		
		if (name.length() > 1)
			mname.append(name.substring(1));
		
		return mname.toString();
	}
	
	/**
	 * 根据实体对象的类型，转换从页面获取到的id类型，初始类型为String
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version May 28, 2013 6:26:32 PM <br/>????????????
	 */
	public static Integer getIdType(String id,Class<?> clazz){
		if(id == null || "".equals(id))
			return null;
		String typeName = TypeUtil.getClassIdType(clazz);
		if("".equals(typeName) || null == typeName)
			return null;
		if("int".equals(typeName) || "java.lang.Integer".equals(typeName))
			return Integer.parseInt(id);
		return null;
	}
	
	/**
	 * 根据实体对象的类型，转换从页面获取到的id类型，初始类型为String
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version May 28, 2013 6:26:32 PM <br/>???????????????
	 */
	public static Integer[] getIdsType(String ids,Class<?> clazz){
		if(ids == null || "".equals(ids))
			return null;
		String typeName = TypeUtil.getClassIdType(clazz);
		if("".equals(typeName) || null == typeName)
			return null;
		if("int".equals(typeName) || "java.lang.Integer".equals(typeName)){
			String[] order = ids.split(",");
			Integer[] order_int=new Integer[order.length];
			for(int i=0;i<order.length;i++){
			    order_int[i]=Integer.parseInt(order[i]);
			}
			return order_int;
		}
			
		return null;
	}
	
	public static void main(String[] args){
		//TypeUtil.getFieldValue(BaseAction.class);
		//TypeUtil.getClassIdType(Menu.class);
		
//		InStock in = new InStock();
//		
//		List<InStockDetail> ssd = new ArrayList<InStockDetail>();
//		
//		InStockDetail isd = new InStockDetail();
//		
//		isd.setId(1);
//		ssd.add(isd);
//		
//		in.setSsd(ssd);
//		
//		
//		in = TypeUtil.getEntitySetDetail(in );
//		
//		List<InStockDetail> ssd2 = in.getSsd();
//		
//		for(InStockDetail ii : ssd2){
//			
//			System.out.println("uuuuuuuu:"+ii.getInStock());
//			
//		}
		
		
//		Field[] files = Consume.class.getDeclaredFields();
//		
//		for(Field f : files)
//			System.out.println("-----:"+f.getName());
//		
//		Class<?> _c = BussObjProperty.class;
//		TypeUtil.getPropertyType(_c,"btc");
//		Number _e = new Number();
//		
//		System.out.println(isIncludeField("modifyUser",_e.getClass()));
//		
//		
//		
//		_e.setCacheNumber(20);
//		_e.setLength(11);
//		_e.setWarnNumber(30);
//		
//		_e.setCreateDate("6666666666666");
//		
//		BussinessEle markSub = new BussinessEle();
//		markSub.setEcode("33333333333");
//		_e.setMarkSub(markSub);
//		Number _e1 = new Number();	
//		Number _e2 = (Number)copy(_e,_e1);
//		System.out.println(_e2.getCreateDate());
	}
}
