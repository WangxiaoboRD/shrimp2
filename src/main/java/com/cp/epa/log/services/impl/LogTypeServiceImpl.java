/**
 * 文件名：@LogTypeServiceImpl.java <br/>
 * 包名：com.zhongpin.ps.baseinfo.services.impl <br/>
 * 项目名：ps <br/>
 * @author 席金红 <br/>
 */
package com.cp.epa.log.services.impl;

import java.io.Serializable;

import javax.annotation.Resource;

import com.cp.epa.base.BaseServiceImpl;
import com.cp.epa.exception.SystemException;
import com.cp.epa.log.dao.ILogTypeDao;
import com.cp.epa.log.entity.LogType;
import com.cp.epa.log.services.ILogMethodService;
import com.cp.epa.log.services.ILogTypeService;

/**
 * 类名：LogTypeServiceImpl  <br />
 *
 * 功能：日志类型
 *
 * @author 席金红 <br />
 * 创建时间：2016-1-25 上午10:04:58  <br />
 * @version 2016-1-25
 */
public class LogTypeServiceImpl extends BaseServiceImpl<LogType, ILogTypeDao> implements ILogTypeService {
	@Resource
	private ILogMethodService methodService;
	public <ID extends Serializable> int deleteByIds(ID[] PK)throws Exception{
		for(ID id : PK){
			//验证该单据是不是已经配置
			int c = methodService.selectTotalRows("logType.id", id);
			if(c!=0)
				throw new SystemException("类型对应的方法已存在，不能删除");
			super.deleteById(id);
		}
		return PK.length;
	}
}
