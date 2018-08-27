package com.cp.epa.sysconfig.services.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.cp.epa.base.BaseServiceImpl;
import com.cp.epa.exception.SystemException;
import com.cp.epa.sysconfig.dao.ISysSwitchDao;
import com.cp.epa.sysconfig.entity.SysSwitch;
import com.cp.epa.sysconfig.services.ISysSwitchService;
import com.cp.epa.sysconfig.utils.SysConfigContext;
import com.cp.epa.sysconfig.utils.SysConfigUtil;

/**
 * 类名：SysSwitchServiceImpl  <br />
 *
 * 功能：系统开关业务逻辑层实现
 *
 * @author 孟雪勤 <br />
 * 创建时间：2013-12-20 上午10:26:55  <br />
 * @version 2013-12-20
 */
public class SysSwitchServiceImpl extends BaseServiceImpl<SysSwitch, ISysSwitchDao> implements ISysSwitchService {

	/**
	 * 功能: <br/>
	 * 
	 * 重写：孟雪勤 <br/>
	 * 
	 * @version ：2013-12-20 上午10:34:47<br/>
	 * 
	 * @param entity
	 * @return
	 * @throws Exception <br/>
	 * @see com.zhongpin.pap.base.BaseServiceImpl#save(com.zhongpin.pap.base.BaseEntity)
	 */
	@Override
	public Object save(SysSwitch entity) throws Exception {

		// 编码不允许为空
		String code = entity.getCode();
		if (null == SysConfigUtil.emptyToNull(code)) {
			throw new SystemException("系统开关[编码]不允许为空！");
		}
		
		// 名称不允许为空
		String name = entity.getName();
		if (null == SysConfigUtil.emptyToNull(name)) {
			throw new SystemException("系统开关[名称]不允许为空！");
		}
		
		// 编码不允许重复
		SysSwitch sysSwitch = dao.selectBySinglet("code", code);
		if (null != sysSwitch) {
			throw new SystemException("系统开关编码[" + code + "]存在冲突！");
		}
		Object PK = dao.insert(entity);
		
		// 更新系统开关缓存
		SysConfigContext.updateSwitchCache(entity);
		
		return PK;
	}

	/**
	 * 功能: <br/>
	 * 
	 * 重写：孟雪勤 <br/>
	 * 
	 * @version ：2013-12-20 上午10:35:12<br/>
	 * 
	 * @param entity
	 * @throws Exception <br/>
	 * @see com.zhongpin.pap.base.BaseServiceImpl#update(com.zhongpin.pap.base.BaseEntity)
	 */
	@Override
	public void update(SysSwitch entity) throws Exception {
		// 名称不允许为空
		String name = entity.getName();
		if (null == SysConfigUtil.emptyToNull(name)) {
			throw new SystemException("系统开关[名称]不允许为空！");
		}
		
		SysSwitch sysSwitch = dao.selectById(entity.getCode());
		sysSwitch.setName(entity.getName());
		sysSwitch.setStatus(entity.getStatus());
		
		dao.update(sysSwitch);
		
		// 更新系统开关缓存
		SysConfigContext.updateSwitchCache(sysSwitch);
	}

	/**
	 * 功能: <br/>
	 * 
	 * 重写：孟雪勤 <br/>
	 * 
	 * @version ：2013-12-20 上午10:35:20<br/>
	 * 
	 * @param <ID>
	 * @param PK
	 * @return
	 * @throws Exception <br/>
	 * @see com.zhongpin.pap.base.BaseServiceImpl#deleteByIds(ID[])
	 */
	@Override
	public <ID extends Serializable> int deleteByIds(ID[] PK) throws Exception {

		int deletes = dao.deleteByIds(PK);
		
		// 删除系统开关缓存
		SysConfigContext.removeSwitchCache((String[])PK);
		
		return deletes;
	}
	
	/**
	 * 功能: 启用<br/>
	 * 
	 * 重写：孟雪勤 <br/>
	 * 
	 * @version ：2013-12-20 上午11:33:57<br/>
	 * 
	 * @param ids
	 * @throws Exception <br/>
	 * @see com.zhongpin.pap.sysconfig.services.ISysSwitchService#enable(java.lang.String[])
	 */
	public void enable(String[] ids) throws Exception {
		// 更新参数
		Map<String, Object> map = new HashMap<String, Object>();
		// 状态标识
		map.put("status", "Y");
		
		for (String id : ids) {
			dao.updateById(id, map);
			SysConfigContext.updateSwitchCache(new SysSwitch(id, "Y"));
		}
		
	}

	/**
	 * 功能: 禁用<br/>
	 * 
	 * 重写：孟雪勤 <br/>
	 * 
	 * @version ：2013-12-20 上午11:34:05<br/>
	 * 
	 * @param ids
	 * @throws Exception <br/>
	 * @see com.zhongpin.pap.sysconfig.services.ISysSwitchService#disable(java.lang.String[])
	 */
	public void disable(String[] ids) throws Exception {
		// 更新参数
		Map<String, Object> map = new HashMap<String, Object>();
		// 状态标识
		map.put("status", "N");
		
		for (String id : ids) {
			dao.updateById(id, map);
			SysConfigContext.updateSwitchCache(new SysSwitch(id, "N"));
		}
	}

}
