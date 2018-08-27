package com.cp.epa.aop;

import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;

import com.cp.epa.debug.utils.P6spyUtil;
import com.cp.epa.permission.entity.Users;
import com.opensymphony.xwork2.ActionContext;
import com.p6spy.engine.common.P6OutDataInteface;



//@Aspect
public class DebugDaoAspect{

//	@Before("pointcutExpress()")
//	public void doBefore(JoinPoint jp) { 
//		
//		System.out.println("sssssssssss:"+ActionContext.getContext());
//		if(ActionContext.getContext() != null){
//			Map<String, Object> session = (Map<String, Object>)ActionContext.getContext().getSession();
//			User u = (User)session.get("__key_user");		
//			if(u != null){
//				String open = (String)session.get(u.getId()+SpeedyUtil.sql);
//				String openSystem = (String)session.get(u.getId()+SpeedyUtil.system);
//				//判断是sql跟踪
//				if("ON".equals(open)){
//					/*
//					P6OutDataInteface.userId=u.getId();
//					P6OutDataInteface.userName=u.getName();
//					P6OutDataInteface.business= (String)session.get(u.getId()+SpeedyUtil.business);
//					P6OutDataInteface.actionFunction= (String)session.get(u.getId()+SpeedyUtil.actionFunction);
//					P6OutDataInteface.serviceFunction = (String)session.get(u.getId()+SpeedyUtil.serviceFunction);
//					//前台跟踪
//					P6OutDataInteface.mark="0";
//					//跟踪Dao层方法
//					P6OutDataInteface.daoFunction = jp.getSignature().getName();
//					*/
//					StringBuffer sb = new StringBuffer(); 
//					sb.append(u.getId()+",");
//					sb.append(u.getName()+",");
//					sb.append((String)session.get(u.getId()+SpeedyUtil.business)+",");
//					sb.append((String)session.get(u.getId()+SpeedyUtil.actionFunction)+",");
//					sb.append((String)session.get(u.getId()+SpeedyUtil.serviceFunction)+",");
//					sb.append(jp.getSignature().getName()+",");
//					sb.append(SpeedyUtil.FRONTMARK);
//					P6OutDataInteface.threadSession.set(sb.toString());
//					sb = null;
//					
//				}
//				if("ON".equals(openSystem)){
//					startTime = System.currentTimeMillis();
//				}
//			}
//		}
//    }  
	
	//@Around("AopPointcut.pointcutDaoExpress()")
	public Object doAround(ProceedingJoinPoint pjp) throws Throwable {  
		//System.out.println("----------------------DAO AOP 启动--------------------------");
		Object retVal;
		if(ActionContext.getContext() != null){
			Map<String, Object> session = (Map<String, Object>)ActionContext.getContext().getSession();
			Users u = (Users)session.get("CURRENTUSER");		
			if(u != null){
				String open = (String)session.get(u.getUserCode()+P6spyUtil.sql);
				String openSystem = (String)session.get(u.getUserCode()+P6spyUtil.system);
				
				StringBuffer sb = new StringBuffer(); 
				sb.append(u.getUserCode()+",");
				sb.append(u.getUserRealName()+",");
				sb.append((String)session.get(u.getUserCode()+P6spyUtil.business)+",");
				sb.append((String)session.get(u.getUserCode()+P6spyUtil.actionFunction)+",");
				sb.append((String)session.get(u.getUserCode()+P6spyUtil.serviceFunction)+",");
				sb.append(pjp.getSignature().getName()+",");
				//判断是sql跟踪
				if("ON".equals(open)){
					/*
					P6OutDataInteface.userId=u.getId();
					P6OutDataInteface.userName=u.getName();
					P6OutDataInteface.business= (String)session.get(u.getId()+SpeedyUtil.business);
					P6OutDataInteface.actionFunction= (String)session.get(u.getId()+SpeedyUtil.actionFunction);
					P6OutDataInteface.serviceFunction = (String)session.get(u.getId()+SpeedyUtil.serviceFunction);
					//前台跟踪
					P6OutDataInteface.mark="0";
					//跟踪Dao层方法
					P6OutDataInteface.daoFunction = jp.getSignature().getName();
					*/
					sb.append(P6spyUtil.FRONTMARK+",");
					sb.append((String)session.get(u.getUserCode()+P6spyUtil.FILENAME));
					P6OutDataInteface.threadSession.set(sb.toString());
				}
				if("ON".equals(openSystem)){
					long startTime = System.currentTimeMillis();
					retVal = pjp.proceed(); 
					long endTime = System.currentTimeMillis()-startTime;
					sb.append(endTime);
					P6spyUtil.daoList.add(sb.toString());
					//SpeedyUtil.m.put(sb.toString(), pjp.getSignature().getName()+","+endTime);
					sb = null;
					//System.out.println("---------------------DAO AOP 结束---------------------");
					return retVal;
				}
				sb = null;
			}
		}
		retVal = pjp.proceed();
		//System.out.println("---------------------DAO AOP 结束---------------------");
		P6OutDataInteface.threadSession.remove();
        return retVal;  
    } 
    
    
	
//	@AfterReturning("pointcutExpress()")
//	public void doAfter(JoinPoint jp){
//		
//		System.out.println("sssssssssssssssssssssss");
//		P6OutDataInteface.threadSession.remove();
//		/*
//		P6OutDataInteface.userId=null;
//		P6OutDataInteface.userName=null;
//		P6OutDataInteface.business= null;
//		P6OutDataInteface.actionFunction= null;
//		P6OutDataInteface.serviceFunction= null;
//		P6OutDataInteface.daoFunction= null;
//		P6OutDataInteface.mark=null;
//		*/
//	}

	
	/*
	@AfterThrowing
    public void doThrowing(JoinPoint jp, Throwable ex) {  
        //出现异常
		System.out.println("出现异常:"+ex.getMessage());
    } 
    */ 
}
