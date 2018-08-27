package com.cp.epa.aop;

import java.util.Map;
import org.aspectj.lang.ProceedingJoinPoint;
import com.opensymphony.xwork2.ActionContext;
import com.cp.epa.debug.utils.P6spyUtil;
import com.cp.epa.permission.entity.Users;


//@Aspect
public class DebugServiceAspect{
	
	//private List v = Collections.synchronizedList(new ArrayList());
	
//	@Pointcut("execution(public * com.*.*.*.impl.*.*(..))")
//	public void pointcutExpress(){}
//	
//	@Pointcut("execution(public * com.p6spy.engine.common.P6LogQuery.doLog(..))")
//	public void pointcutExpressLog(){}
//	
//	@Pointcut("execution(* com.p6spy.engine.common.Ceshiyixia.ceshi(..))")
//	public void pointcutExpressCeshi(){}
//	
//	@Pointcut("execution(public * com.p6spy.engine.spy.P6PreparedStatement.*(..))")
//	public void pointcutExpressStatm(){}
//	
//	@Pointcut("execution(* *..service*..*(..))")
//	public void pointcutExpressNew(){}
//	
	
	
	/*                                                                                                                                       
	@Before("pointcutExpress()")
	public void doBefore(JoinPoint jp) { 
		System.out.println("qqqqqqqqqqq:"+ActionContext.getContext());
		if(ActionContext.getContext() != null){
			Map<String, Object> session = (Map<String, Object>)ActionContext.getContext().getSession();
			User u = (User)session.get("__key_user");		
			if(u != null){
				String open = (String)session.get(u.getId()+SpeedyUtil.sql);
				String openSystem = (String)session.get(u.getId()+SpeedyUtil.system);
				//判断是sql跟踪
				if("ON".equals(open)){
					session.put(u.getId()+SpeedyUtil.serviceFunction, jp.getSignature().getName());
				}
				
			}
		}
    } 
	*/
	
	//@Around("AopPointcut.pointcutServiceExpress()")
	public Object doAround(ProceedingJoinPoint pjp) throws Throwable { 
		
		Object retVal = null;
		//System.out.println("---------------------------Service AOP 启动-------------------------------");
		if(ActionContext.getContext() != null){
			Map<String, Object> session = (Map<String, Object>)ActionContext.getContext().getSession();
			Users u = (Users)session.get("CURRENTUSER");		
			if(u != null){
				String open = (String)session.get(u.getUserCode()+P6spyUtil.sql);
				String openSystem = (String)session.get(u.getUserCode()+P6spyUtil.system);
				//判断是sql跟踪
				if("ON".equals(open) || "ON".equals(openSystem)){
					session.put(u.getUserCode()+P6spyUtil.serviceFunction, pjp.getSignature().getName());
					
					StringBuffer sb = new StringBuffer(); 
					sb.append(u.getUserCode()+",");
					sb.append(u.getUserRealName()+",");
					sb.append((String)session.get(u.getUserCode()+P6spyUtil.business)+",");
					sb.append((String)session.get(u.getUserCode()+P6spyUtil.actionFunction)+",");
					sb.append(pjp.getSignature().getName()+",");
					
					if("ON".equals(openSystem)){
						long startTime = System.currentTimeMillis();
						retVal = pjp.proceed();
						long endTime = System.currentTimeMillis() - startTime;
						sb.append(endTime);
						P6spyUtil.serviceList.add(sb.toString());
						sb = null;
						return retVal;
					}
					sb = null;
//					if(P6OutDataInteface.vector.size() != 0){
//						v.addAll(P6OutDataInteface.vector);
//						System.out.println("--------v--------:"+v.size());
//						P6OutDataInteface.vector.clear();
//						System.out.println("--------P6OutDataInteface.vector--------:"+P6OutDataInteface.vector.size());
//					}
				//}//else if("ON".equals(open)){
				//	session.put(u.getId()+SpeedyUtil.serviceFunction, pjp.getSignature().getName());
					//if(P6OutDataInteface.vector.size() != 0){
					//	v.addAll(P6OutDataInteface.vector);
					//	P6OutDataInteface.vector.clear();

					//}
//				}else if("ON".equals(openSystem)){
//					session.put(u.getId()+SpeedyUtil.serviceFunction, pjp.getSignature().getName());
//					StringBuffer sb = new StringBuffer(); 
//					sb.append(u.getId()+",");
//					sb.append(u.getName()+",");
//					sb.append((String)session.get(u.getId()+SpeedyUtil.business)+",");
//					sb.append((String)session.get(u.getId()+SpeedyUtil.actionFunction)+",");
//					long startTime = System.currentTimeMillis();
//					retVal = pjp.proceed();
//					long endTime = System.currentTimeMillis() - startTime;
//					sb.append(pjp.getSignature().getName()+",");
//					sb.append(endTime);
//					SpeedyUtil.serviceList.add(sb.toString());

					//System.out.println("---------------------------Service AOP 结束-------------------------------");
				}
			}
		}
		//retVal = pjp.proceed();  
		retVal = pjp.proceed(); 
		//System.out.println("---------------------------Service AOP 结束-------------------------------");
        return retVal;  
    } 
	
	/*
	@AfterReturning("pointcutExpress()")
	public void doAfter(JoinPoint jp){
		
		System.out.println("pppppppppppppppppp");
		if(P6OutDataInteface.vector.size() != 0){
			v.addAll(P6OutDataInteface.vector);
			P6OutDataInteface.vector.clear();
		}
		//信息保存到数据库
	}*/
}
