package com.cp.epa.aop;
//package com.zhongpin.pap.aop;
//
//import java.lang.reflect.Method;
//
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.reflect.MethodSignature;
//
//public class IdTypeHandlerAspect {
//	
//	public void doBefore(JoinPoint jp){
//		//拦截的实体类
//		Object target = jp.getTarget();
//		//拦截的方法名称
//		String methodName = jp.getSignature().getName();
//		//拦截的方法参数
//		Object[] args = jp.getArgs();
//		//拦截的方法参数类型
//		Class[] parameterTypes = ((MethodSignature)jp.getSignature()).getMethod().getParameterTypes();
//
//		Method m = null;
//		try {
//			//通过反射获得拦截的method
//			m = target.getClass().getMethod(methodName, parameterTypes);
//			//如果是桥则要获得实际拦截的method
////			if(m.isBridge()){
////				for(int i = 0; i < args.length; i++){
////					//获得泛型类型
////					Class genClazz = GenericsUtils.getSuperClassGenricType(target.getClass());
////					//根据实际参数类型替换parameterType中的类型
////					if(args[i].getClass().isAssignableFrom(genClazz)){
////						parameterTypes[i] = genClazz;
////					}
////				}
////				//获得parameterType参数类型的方法
////				m = target.getClass().getMethod(methodName, parameterTypes);
////			}
//		} catch (SecurityException e) {
//			e.printStackTrace();
//		} catch (NoSuchMethodException e) {
//			e.printStackTrace();
//		}
//	}
//}
