/**
 * 文件名：@LogClassServiceImpl.java <br/>
 * 包名：com.zhongpin.pap.log.services.impl <br/>
 * 项目名：ps <br/>
 * @author 席金红 <br/>
 */
package com.cp.epa.log.services.impl;

import java.io.Serializable;

import javax.annotation.Resource;

import com.cp.epa.base.BaseServiceImpl;
import com.cp.epa.exception.SystemException;
import com.cp.epa.log.dao.ILogClassDao;
import com.cp.epa.log.entity.LogClass;
import com.cp.epa.log.services.ILogClassService;
import com.cp.epa.log.services.ILogMethodService;

/**
 * 类名：LogClassServiceImpl  <br />
 *
 * 功能：日志类记录
 *
 * @author 席金红 <br />
 * 创建时间：2016-1-25 下午05:34:16  <br />
 * @version 2016-1-25
 */
public class LogClassServiceImpl extends BaseServiceImpl<LogClass, ILogClassDao> implements ILogClassService {
	@Resource
	private ILogMethodService methodService;
	public <ID extends Serializable> int deleteByIds(ID[] PK)throws Exception{
		for(ID id : PK){
			//验证该单据是不是已经配置
			int c = methodService.selectTotalRows("logClass.id", id.toString());
			if(c!=0)
				throw new SystemException("类对应方法已存在，不能删除");
			super.deleteById(id.toString());
		}
		return PK.length;
	}
}
