/**
 * 文件名：@LogMethodServiceImpl.java <br/>
 * 包名：com.zhongpin.ps.baseinfo.services.impl <br/>
 * 项目名：ps <br/>
 * @author 席金红 <br/>
 */
package com.cp.epa.log.services.impl;

import java.util.List;

import com.cp.epa.base.BaseServiceImpl;
import com.cp.epa.exception.SystemException;
import com.cp.epa.log.dao.ILogMethodDao;
import com.cp.epa.log.entity.LogMethod;
import com.cp.epa.log.services.ILogMethodService;
import com.cp.epa.utils.SqlMap;

/**
 * 类名：LogMethodServiceImpl  <br />
 *
 * 功能：日志方法
 *
 * @author 席金红 <br />
 * 创建时间：2016-1-25 上午10:15:57  <br />
 * @version 2016-1-25
 */
public class LogMethodServiceImpl extends BaseServiceImpl<LogMethod, ILogMethodDao> implements ILogMethodService {
	/**
	 * 添加
	 * 功能：验证重复<br/>
	 *
	 * @author 席金红
	 * @version 2016-2-1 上午11:27:55 <br/>
	 */
	@Override
	public Object save(LogMethod entity) throws Exception {
		SqlMap<String, String, Object> sqlMap = new SqlMap<String, String, Object>();
		sqlMap.put("logClass.id", "=", entity.getLogClass().getId());
		sqlMap.put("methodName", "=", entity.getMethodName());
		int rows = dao.selectTotalRows(sqlMap);
		if (rows > 0) {
			throw new SystemException("该方法已存在！");
		}
		return dao.insert(entity);
	}
	
	/**
	 * 功能: 根据类全名，方法名查询方法描述<br/>
	 * 
	 * 重写：孟雪勤 <br/>
	 * 
	 * @version ：2016-1-30 下午03:02:44<br/>
	 * 
	 * @param className
	 * @param method
	 * @return
	 * @throws Exception <br/>
	 * @see com.zhongpin.pap.log.services.ILogMethodService#selectByClass(java.lang.String, java.lang.String)
	 */
	@Override
	public LogMethod selectByClass(String className, String method) throws Exception {
		SqlMap<String, String, Object> sqlMap = new SqlMap<String, String, Object>();
		sqlMap.put("logClass.className", "=", className);
		sqlMap.put("methodName", "=", method);
		List<LogMethod> list = dao.selectByConditionHQL(sqlMap);
		if (null != list && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
}
