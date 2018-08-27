package com.cp.epa.log.services.impl;
import java.util.Map;

import com.cp.epa.base.BaseServiceImpl;
import com.cp.epa.log.dao.IOperateLogDao;
import com.cp.epa.log.entity.OperateLog;
import com.cp.epa.log.services.IOperateLogService;
import com.cp.epa.utils.Pager;
import com.cp.epa.utils.SqlMap;

public class OperateLogServiceImpl extends BaseServiceImpl<OperateLog,IOperateLogDao> implements IOperateLogService{
	/**
	 * 功能: <br/>
	 * 
	 * 重写：孟雪勤 <br/>
	 * 
	 * @version ：2014-7-1 下午06:09:45<br/>
	 * 
	 * @param entity
	 * @param page
	 * @throws Exception <br/>
	 * @see com.zhongpin.pap.base.BaseServiceImpl#selectAll(com.zhongpin.pap.base.BaseEntity, com.zhongpin.pap.utils.Pager)
	 */
	public void selectAll(OperateLog entity, Pager<OperateLog> page) throws Exception {
		
		Map<String, String> map = entity.getTempStack();
		SqlMap<String,String,Object> sqlMap = new SqlMap<String,String,Object>();
		if(map != null && map.size()>0){
			String startDate = map.get("startDate");
			if(startDate != null && !"".equals(startDate)){
				startDate = startDate+" 00:00:00";
				sqlMap.put("loginDate", ">=", startDate);
			}
			String endDate = map.get("endDate");
			if(endDate != null && !"".equals(endDate)){
				endDate = endDate+" 23:59:59";
				sqlMap.put("loginDate", "<=", endDate);
			}
		}
		dao.selectByConditionHQL(entity, sqlMap, page);
	}
}
