package com.cp.epa.aop;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.aspectj.lang.JoinPoint;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.cp.epa.bussobj.entity.BaseTableColumn;
import com.cp.epa.bussobj.entity.BussObjProperty;
import com.cp.epa.bussobj.entity.BussinessEle;
import com.cp.epa.bussobj.entity.BussinessObject;
import com.cp.epa.bussobj.services.IBaseTableColumnService;
import com.cp.epa.bussobj.services.IBussObjPropertyService;
import com.cp.epa.bussobj.services.IBussinessObjectService;
import com.cp.epa.log.entity.ModifyLog;
import com.cp.epa.log.entity.ModifyLogObject;
import com.cp.epa.log.services.IModifyLogObjectService;
import com.cp.epa.log.services.IModifyLogService;
import com.cp.epa.permission.entity.Users;
import com.cp.epa.sysconfig.utils.SysConfigContext;
import com.cp.epa.utils.SqlMap;
import com.cp.epa.utils.TypeUtil;
import com.opensymphony.xwork2.ActionContext;


@SuppressWarnings("unchecked")
public class ModifyLogAspect{
	
	@Resource
	private IBussinessObjectService bussinessObjectService;
	@Resource
	private IBussObjPropertyService bussObjPropertyService;
	@Resource
	private IBaseTableColumnService baseTableColumnService;
	@Resource
	private IModifyLogService modifyLogService;
	@Resource
	private SessionFactory sessionFactory;
	@Resource
	private IModifyLogObjectService modifyLogObjectService;

	public void doBefore(JoinPoint jp) throws Exception{
		
		// 判断数据权限开关
		String status = SysConfigContext.getSwitch("ModifyLog");
		if(status != null && "N".equals(status))
			return;
		//获得拦截的对象方法的参数
		Object[] objs = jp.getArgs();
		Object obj=null;
		//操作对象的class
		Class<?> _c=null;
		
		if(objs.length==1){
			//取第一个参数作为要处理的参数
			obj = objs[0];
			_c = obj.getClass();
		//针对对于通过ID进行更新的方法
		}else if(objs.length==3){
			obj = objs[2];
			_c = (Class<?>)obj;
		}
		if(_c == null)
			return;
		//该参数对象的全路径
		String classFullName = _c.getName();
		//对参数对象的类名
		String className = _c.getSimpleName();
		
		//判断该类是不是需要记录变更日志
		if(!checkModifyLog(className))
			return;
		/**---获得对象的所有属性并获取属性对象的业务元素，并判断业务元素是否具备变更日志记录条件--*/
		
		//获得业务对象对应的表名
		String _tableName = getTableName(className);
		if(_tableName==null || "".equals(_tableName))
			return;
	    SqlMap<String,String,String> sqlMap = new SqlMap<String,String,String>();
	    //获得该对象的所有属性
		Field[] fields = _c.getDeclaredFields();
		for(Field _f : fields){
			//获得属性名称
			String propertyName = _f.getName();
			//排除一些特殊属性
			if("serialVersionUID".equals(propertyName))
				continue;
			
			//获得属性对应的字段名
			String tableColumeName = getColumeName(propertyName,sqlMap,classFullName);
			if(tableColumeName==null || "".equals(tableColumeName))
				continue;
			//通过表名、字段名获得对应的业务元素名
			BussinessEle be = getBussinessEle(tableColumeName,_tableName,sqlMap);
			if(be == null)
				continue;
			//判断该业务元素是不是能够进行日志记录
			String logFlag = be.getFlagLog();
			if(logFlag != null && "on".equals(logFlag)){
				
				String newValue="";
				Object newValueObject = null;
				//1.获得对象对应的ID名称
				String id = TypeUtil.getClassIdField(_c);
				String idValue="";
				if(objs.length==1){
					//获得该对象该属性的当前值
					newValueObject = TypeUtil.getFieldValue(obj,propertyName);
					//2.获得对象对应的ID值
					idValue = TypeUtil.getFieldValue(obj,id).toString();
				}else if(objs.length==3){
					Map<String,Object> _m = (Map<String,Object>)objs[1];
					if(!_m.containsKey(propertyName))
						continue;
					newValueObject = _m.get(propertyName);
					idValue = objs[0].toString();
				}
				if(newValueObject != null){
					newValue = newValueObject.toString();
				}
				if(idValue == null || "".equals(idValue))
					continue;
				//3.拼接hql语句
				String hql = "from "+classFullName+" e where "+id+"='"+idValue+"'" ;
				//4.执行hql语句并获得值
				Session seseion = sessionFactory.openSession();
				Object _o = seseion.createQuery(hql).uniqueResult();
				seseion.close();
				
				//List<Object> list = ssion.find(hql);
				//if(list == null || list.size()==0)
				//	continue;
				//Object _o = list.get(0);
				String oldValue = TypeUtil.getFieldValue(_o,propertyName).toString();
				//记录日志
				if(newValue.equals(oldValue))
					continue;
				save(classFullName,propertyName,oldValue,newValue);
			}
		}
	  
		
		
		/**
		
		//如果参数不为空
		if(objs.length==1){
			//取第一个参数作为要处理的参数
			obj = objs[0];
			Class<?> _c = obj.getClass();
			//该参数对象的全路径
			classFullName = _c.getName();
			//对参数对象的类名
			className = _c.getSimpleName();
			//判断该类是不是需要记录变更日志
			if(!checkModifyLog(className))
				return;

			
			//获得业务对象对应的表名
			String _tableName = getTableName(className);
			if(_tableName==null || "".equals(_tableName))
				return;
		    SqlMap<String,String,String> sqlMap = new SqlMap<String,String,String>();
		  
			//获得该对象的所有属性
			Field[] fields = _c.getDeclaredFields();
			for(Field _f : fields){
				//获得属性名称
				String propertyName = _f.getName();
				//获得属性对应的字段名
				String tableColumeName = getColumeName(propertyName,sqlMap,classFullName);
				if(tableColumeName==null || "".equals(tableColumeName))
					continue;
				//通过表名、字段名获得对应的业务元素名
				BussinessEle be = getBussinessEle(tableColumeName,_tableName,sqlMap);
				if(be == null)
					continue;
				//判断该业务元素是不是能够进行日志记录
				String logFlag = be.getFlagLog();
				if(logFlag != null && "on".equals(logFlag)){
					//获得该对象该属性的当前值
					String newValue = TypeUtil.getFieldValue(obj,propertyName).toString();
					
					//获得该对象该属性的原始值
					//1.获得对象对应的ID名称
					String id = TypeUtil.getClassIdField(_c);
					//2.获得对象对应的ID值
					String idValue = TypeUtil.getFieldValue(obj,id).toString();
					if(idValue == null)
						continue;
					//3.拼接hql语句
					String hql = "from "+classFullName+" e where "+id+"='"+idValue+"'" ;
					//4.执行hql语句并获得值
					
					Session seseion = sessionFactory.openSession();
					Object _o = seseion.createQuery(hql).uniqueResult();
					seseion.close();
					
					//List<Object> list = ssion.find(hql);
					//if(list == null || list.size()==0)
					//	continue;
					//Object _o = list.get(0);
					String oldValue = TypeUtil.getFieldValue(_o,propertyName).toString();
					//记录日志
					save(classFullName,propertyName,oldValue,newValue);
				}
			}
		}else if(objs.length == 3){

				obj = objs[2];
				Class<?> clazz = (Class<?>)obj;
				//该参数对象的全路径
				classFullName = clazz.getName();
				//对参数对象的类名
				className = clazz.getSimpleName();
				//判断该类是不是需要记录变更日志
				if(!checkModifyLog(className))
					return;
				//获得业务对象对应的表名
				String _tableName = getTableName(className);
				if(_tableName==null || "".equals(_tableName))
					return;
				
				
				
		}*/
	}
	
	//验证该对象是否需要记录日志
	private boolean checkModifyLog(String className)throws Exception{
		ModifyLogObject _m = modifyLogObjectService.selectBySinglet("bussObj.bussCode", className);
		if(_m == null)
			return false;
		return true;
	}
	//获得表名
	private String getTableName(String className) throws Exception{
		//通过类名获得表名
		BussinessObject _b = bussinessObjectService.selectBySinglet("bussCode", className);
	    if(_b == null)
	    	return null;
	    //获得表名
	    String _tableName = _b.getTableCode();
	    if(_tableName == null || "".equals(_tableName))
	    	return null;
	    return _tableName;
	}
	
	//获得字段名
	private String getColumeName(String propertyName,SqlMap<String,String,String> sqlMap,String classFullName)throws Exception{
		
		//通过类名、属性名获得对性的表字段名
		sqlMap.put("propertyName", "=", propertyName);
		sqlMap.put("belongClass", "=", classFullName);
		
		List<BussObjProperty> lbop= bussObjPropertyService.selectHQL(sqlMap);
		sqlMap.clear();
		if(lbop== null || lbop.size()==0)
			return null;
		BussObjProperty bop = lbop.get(0);
		String tableColumeName = bop.getFieldName();
		if(tableColumeName==null || "".equals(tableColumeName))
			return null;
		return tableColumeName;
	}
	//获得属性对应的业务元素
	private BussinessEle getBussinessEle(String tableColumeName,String _tableName,SqlMap<String,String,String> sqlMap)throws Exception{
		//通过表名、字段名获得对应的业务元素名
		sqlMap.put("fdcode", "=", tableColumeName);
		sqlMap.put("baseTable.tabCode", "=", _tableName);
		List<BaseTableColumn> lbtc= baseTableColumnService.selectHQL(sqlMap);
		sqlMap.clear();
		if(lbtc== null || lbtc.size()==0)
			return null;
		BaseTableColumn btc = lbtc.get(0);
		if(btc==null)
			return null;
		BussinessEle be = btc.getBussinessEle();
		if(be == null)
			return null;
		return be;
	}
	
	//记录日志
	private void save(String classFullName,String propertyName,String oldValue,String newValue) throws Exception{
		Map<String,Object> session =  (Map<String,Object>)ActionContext.getContext().getSession();
		Users _u = (Users)session.get("CURRENTUSER");
		String userCode ="";
		if(_u != null)
			userCode = _u.getUserCode();
		HttpServletRequest request = (HttpServletRequest) ActionContext.getContext().get(ServletActionContext.HTTP_REQUEST);
		String ip = request.getRemoteAddr();
		
		ModifyLog ml = new ModifyLog();
		ml.setIp(ip);
		ml.setModelName(classFullName);
		ml.setNewValue(newValue);
		ml.setOldValue(oldValue);
		ml.setPropertyName(propertyName);
		ml.setUserCode(userCode);
		ml.setTime((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date()));
		modifyLogService.save(ml);
	}
}
