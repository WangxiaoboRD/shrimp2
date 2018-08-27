package com.cp.epa.quartz.services.impl;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.cp.epa.base.BaseServiceImpl;
import com.cp.epa.exception.SystemException;
import com.cp.epa.quartz.dao.IQuartzGroupDao;
import com.cp.epa.quartz.entity.QuartzGroup;
import com.cp.epa.quartz.entity.QuartzJob;
import com.cp.epa.quartz.services.IQuartzGroupService;
import com.cp.epa.quartz.services.IQuartzJobService;
import com.cp.epa.utils.SqlMap;

/**
 * 类名：QuartzGroupServiceImpl  <br />
 *
 * 功能：调度作业组业务逻辑层实现
 *
 * @author 孟雪勤 <br />
 * 创建时间：2014-1-4 下午05:23:27  <br />
 * @version 2014-1-4
 */
public class QuartzGroupServiceImpl extends BaseServiceImpl<QuartzGroup, IQuartzGroupDao> implements IQuartzGroupService {
	
	/** 调度作业业务注册 */
	@Autowired
	private IQuartzJobService quartzJobService;

	/**
	 * 功能: 删除<br/>
	 * 
	 * 重写：孟雪勤 <br/>
	 * 
	 * @version ：2014-1-4 下午05:48:35<br/>
	 * 
	 * @param <ID>
	 * @param PK
	 * @return
	 * @throws Exception <br/>
	 * @see com.zhongpin.pap.base.BaseServiceImpl#deleteByIds(ID[])
	 */
	@Override
	public <ID extends Serializable> int deleteByIds(ID[] PK) throws Exception {
		// 若被调度作业引用则不允许删除
		SqlMap<String, String, String> sqlMap = new SqlMap<String, String, String>();
		sqlMap.put("quartzGroup.id", "in", Arrays.toString(PK).replaceAll("[\\[\\]\\s]", ""));
		List<QuartzJob> quartzJobs = quartzJobService.selectHQL(sqlMap);
		if (null != quartzJobs && quartzJobs.size() > 0) {
			throw new SystemException("所删除的作业组已被调度作业引用，不允许删除！");
		}
		
		return dao.deleteByIds(PK);
	}
	
}
