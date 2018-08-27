package com.cp.epa.aop;
//package com.zhongpin.pap.aop;
//import java.io.IOException;
//import java.lang.reflect.Method;
//import java.util.Map;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.struts2.ServletActionContext;
//import org.springframework.aop.ThrowsAdvice; 
//
//import com.opensymphony.xwork2.ActionContext;
//
///**
// * 类名：ExceptionAspect 
// * @deprecated
// * 功能：
// *
// * @author dzl 
// * 创建时间：May 22, 2013 3:39:55 PM 
// * @version May 22, 2013
// */
//public class ExceptionAspect_temp implements ThrowsAdvice {
// 
//	/**
//	 * 
//	 * 功能：<br/>
//	 * 参数解释 Method method 执行的方法  
//	 * Object[] args 方法参数 
//	 * Object target 代理的目标对象
//	 * Throwable throwable 产生的异常
//	 * @author 杜中良
//	 * @version May 22, 2013 3:36:20 PM <br/>
//	 */
//    public void afterThrowing(Method method, Object[] args, Object target,Exception  throwable) {  
//        System.out.println("产生异常的方法名称：  " + method.getName());  
//          
//        for(Object o:args){  
//            System.out.println("方法的参数：   " + o.toString());  
//        }  
//          
//        System.out.println("代理对象：   " + target.getClass().getName());  
//        System.out.println("抛出的异常:    " + throwable.getMessage()+">>>>>>>"  + throwable.getCause());  
//        System.out.println("异常详细信息：　　　"+throwable.fillInStackTrace());  
//        
//       Map<String,Object> session =  (Map<String,Object>)ActionContext.getContext().getSession();
//		HttpServletRequest request = (HttpServletRequest) ActionContext.getContext().get(ServletActionContext.HTTP_REQUEST);
//		HttpServletResponse response = (HttpServletResponse) ActionContext.getContext().get(ServletActionContext.HTTP_RESPONSE);  
//        
//		System.out.println("---session---:"+session);
//		System.out.println("---request---:"+request);
//		System.out.println("---response---:"+response);
//
//		try {
//			request.getRequestDispatcher("base!modifyById").forward(request, response);
//			return;
//		} catch (ServletException e1) {
//			e1.printStackTrace();
//		} catch (IOException e1) {
//			e1.printStackTrace();
//		}
//		
//    }  
//}
