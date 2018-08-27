package com.cp.epa.sysconfig.services.impl;

import java.io.Serializable;

import com.cp.epa.base.BaseServiceImpl;
import com.cp.epa.exception.SystemException;
import com.cp.epa.sysconfig.dao.ISysParamDao;
import com.cp.epa.sysconfig.entity.SysParam;
import com.cp.epa.sysconfig.services.ISysParamService;
import com.cp.epa.sysconfig.utils.SysConfigContext;
import com.cp.epa.sysconfig.utils.SysConfigUtil;

/**
 * 类名：SysParamServiceImpl  <br />
 *
 * 功能：系统参数业务逻辑层实现
 *
 * @author 孟雪勤 <br />
 * 创建时间：2013-12-19 上午10:50:41  <br />
 * @version 2013-12-19
 */
public class SysParamServiceImpl extends BaseServiceImpl<SysParam, ISysParamDao> implements ISysParamService {

	/**
	 * 功能: <br/>
	 * 
	 * 重写：孟雪勤 <br/>
	 * 
	 * @version ：2013-12-19 下午02:46:06<br/>
	 * 
	 * @param entity
	 * @return
	 * @throws Exception <br/>
	 * @see com.zhongpin.pap.base.BaseServiceImpl#save(com.zhongpin.pap.base.BaseEntity)
	 */
	@Override
	public Object save(SysParam entity) throws Exception {
		// 编码不允许为空
		String code = entity.getCode();
		if (null == SysConfigUtil.emptyToNull(code)) {
			throw new SystemException("系统参数[编码]不允许为空！");
		}
		
		// 名称不允许为空
		String name = entity.getName();
		if (null == SysConfigUtil.emptyToNull(name)) {
			throw new SystemException("系统参数[名称]不允许为空！");
		}
		
		// 默认值不允许为空
		String defaultValue = entity.getDefaultValue();
		if (null == SysConfigUtil.emptyToNull(defaultValue)) {
			throw new SystemException("系统参数[默认值]不允许为空！");
		}
		
		// 编码不允许重复
		SysParam param = dao.selectBySinglet("code", code);
		if (null != param) {
			throw new SystemException("系统参数编码[" + code + "]存在冲突！");
		}
		Object PK = dao.insert(entity);
		
		// 更新系统参数缓存
		SysConfigContext.updateParamCache(entity);
		
		return PK;
	}

	/**
	 * 功能: <br/>
	 * 
	 * 重写：孟雪勤 <br/>
	 * 
	 * @version ：2013-12-19 下午02:46:18<br/>
	 * 
	 * @param entity
	 * @throws Exception <br/>
	 * @see com.zhongpin.pap.base.BaseServiceImpl#update(com.zhongpin.pap.base.BaseEntity)
	 */
	@Override
	public void update(SysParam entity) throws Exception {
		
		// 名称不允许为空
		String name = entity.getName();
		if (null == SysConfigUtil.emptyToNull(name)) {
			throw new SystemException("系统参数[名称]不允许为空！");
		}
		
		// 默认值不允许为空
		String defaultValue = entity.getDefaultValue();
		if (null == SysConfigUtil.emptyToNull(defaultValue)) {
			throw new SystemException("系统参数[默认值]不允许为空！");
		}
		
		SysParam param = dao.selectById(entity.getCode());
		param.setName(entity.getName());// 名称
		param.setDefaultValue(entity.getDefaultValue()); // 默认值
		param.setValue(entity.getValue()); // 值
		param.setDescription(entity.getDescription());// 描述
		
		dao.update(param);
		
		// 更新系统参数缓存
		SysConfigContext.updateParamCache(param);
	}

	/**
	 * 功能: 根据id集合删除<br/>
	 * 
	 * 重写：孟雪勤 <br/>
	 * 
	 * @version ：2013-12-19 下午02:47:01<br/>
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
		
		// 删除系统参数缓存
		SysConfigContext.removeParamCache((String[])PK);
		
		return deletes;
	}
	
}
