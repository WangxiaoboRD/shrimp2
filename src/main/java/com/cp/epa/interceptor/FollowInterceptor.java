package com.cp.epa.interceptor;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.cp.epa.debug.services.IDebugSystemService;
import com.cp.epa.debug.utils.P6spyUtil;
import com.cp.epa.permission.entity.Users;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;


public class FollowInterceptor implements Interceptor {
	
	@Autowired
	private IDebugSystemService debugSystemService;

	private static final long serialVersionUID = 1L;
	public void destroy() {
		// TODO Auto-generated method stub
	}

	public void init() {
		// TODO Auto-generated method stub
	}

	public String intercept(ActionInvocation invocate) throws Exception {
		//判断用户sql跟踪与程序跟踪是否开启
		Map<String, Object> session = invocate.getInvocationContext().getSession();
		Users u = (Users)session.get("CURRENTUSER");
		//System.out.println("-------------------拦截器开始--------------------");
		String _ret= null;
		try{
			if(u != null){
				
				String sqlMark = (String)session.get(u.getUserCode()+P6spyUtil.sql);
				String systemMark = (String)session.get(u.getUserCode()+P6spyUtil.system);
				
				String actionName = "";
				String methodName = "";
				//只要有一个开启的就获取当前业务的方法名，业务类名，以及主要业务名称
				if("ON".equals(systemMark) || "ON".equals(sqlMark)){
					//业务对象
					actionName = invocate.getAction().getClass().getSimpleName();
					//将处理程序跟踪与程序sql跟踪的Action排除
					if("DebugSqlAction".equals(actionName) || "DebugSystemAction".equals(actionName)){
						invocate.invoke();
						P6spyUtil.serviceList.clear();
						P6spyUtil.daoList.clear();
						return null;
					}
					if(actionName.contains("Action")){
						actionName = actionName.substring(0,actionName.indexOf("Action"));	
					}
					//业务方法
					methodName = invocate.getProxy().getMethod();
					session.put(u.getUserCode()+P6spyUtil.business, actionName);
					session.put(u.getUserCode()+P6spyUtil.actionFunction, methodName);
					if("ON".equals(systemMark)){
						//耗时计算
						long times = System.currentTimeMillis();
						invocate.invoke();
						long endTime =(System.currentTimeMillis()-times);
						//List<DebugSystem> _list = new ArrayList<DebugSystem>();
						
						List<String> slist = P6spyUtil.daoList;
						//P6spyUtil.daoList.addAll(P6spyUtil.serviceList);
						//P6spyUtil.daoList.add(u.getUserCode()+","+u.getUserRealName()+","+actionName+","+methodName+","+endTime);
						//debugSystemService.saveEntityList(P6spyUtil.daoList);
						
						slist.addAll(P6spyUtil.serviceList);
						slist.add(u.getUserCode()+","+u.getUserRealName()+","+actionName+","+methodName+","+endTime);
						
						debugSystemService.saveEntityList(slist);
						
						slist.clear();
						P6spyUtil.serviceList.clear();
						P6spyUtil.daoList.clear();
						/*
						DebugSystem _ds = new DebugSystem();
						_ds.setOperatorId(u.getId());
						_ds.setOperTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
						_ds.setOperatorName(u.getName());
						_ds.setOperBusiness(actionName);
						_ds.setOperFunction(methodName);
						_ds.setTimeConsuming(endTime+"");
						
						_list.add(_ds);
						
						for(Object s : SpeedyUtil.serviceList){
							
							String _s = (String)s;
							if("" == _s || _s==null){
								continue;
							}
							String[] _sArray = _s.split(",");
							if(_sArray.length < 6){
								continue;
							}
							_ds = new DebugSystem();
							_ds.setOperatorId(_sArray[0]);
							_ds.setOperTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
							_ds.setOperatorName(_sArray[1]);
							_ds.setOperBusiness(_sArray[2]);
							_ds.setOperFunction(_sArray[3]);
							_ds.setServiceFunction(_sArray[4]);
							_ds.setTimeConsuming(_sArray[5]);
							
							_list.add(_ds);
							
						}
						SpeedyUtil.serviceList.clear();
						
						for(Object s : SpeedyUtil.daoList){
							String _s = (String)s;
							if("" == _s || _s==null){
								continue;
							}
							String[] _sArray = _s.split(",");
							if(_sArray.length < 7){
								continue;
							}
							_ds = new DebugSystem();
							_ds.setOperatorId(_sArray[0]);
							_ds.setOperTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
							_ds.setOperatorName(_sArray[1]);
							_ds.setOperBusiness(_sArray[2]);
							_ds.setOperFunction(_sArray[3]);
							_ds.setServiceFunction(_sArray[4]);
							_ds.setFollowFunction(_sArray[5]);
							_ds.setTimeConsuming(_sArray[6]);
							
							_list.add(_ds);
						}
						
						
						for(DebugSystem s : _list) 
							System.out.println("oooooo:"+s.getOperatorId()+"--"+s.getOperatorName()+"---"+s.getOperBusiness()+"--"+s.getOperTime()+"--"+s.getOperFunction()+"--"+s.getServiceFunction()+"---"+s.getFollowFunction()+"---"+s.getTimeConsuming());
						
						SpeedyUtil.daoList.clear();
						debugSystemService.create(_list);
						_list.clear();
						*/
						//System.out.println("----------------------拦截器结束--------------------");
						return null;
					}
				}
			}
			_ret = invocate.invoke();
		}finally{
			
		}
		//System.out.println("----------------------拦截器结束--------------------");
		return null;
	}

}
