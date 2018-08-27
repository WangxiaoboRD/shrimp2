package com.cp.epa.permission.services.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.cp.epa.base.BaseServiceImpl;
import com.cp.epa.exception.SystemException;
import com.cp.epa.permission.dao.IAuthObjDao;
import com.cp.epa.permission.entity.AuthObj;
import com.cp.epa.permission.entity.BussObjAuthProperty;
import com.cp.epa.permission.entity.Role;
import com.cp.epa.permission.services.IAuthObjService;
import com.cp.epa.permission.services.IBussObjAuthPropertyService;
import com.cp.epa.permission.services.IRoleService;
import com.cp.epa.permission.utils.PmUtil;
import com.cp.epa.utils.PapUtil;
import com.cp.epa.utils.SqlMap;
import com.cp.epa.utils.TypeUtil;



/**
 * 类名：AuthObjServiceImpl  <br />
 *
 * 功能：权限对象业务逻辑层实现
 *
 * @author 孟雪勤 <br />
 * 创建时间：2013-11-6 下午02:14:40  <br />
 * @version 2013-11-6
 */
public class AuthObjServiceImpl extends BaseServiceImpl<AuthObj, IAuthObjDao> implements IAuthObjService {

	/** 权限过滤属性业务注册 */
	@Resource
	private IBussObjAuthPropertyService authPropertyService;
	/** 角色业务注册 */
	@Resource
	private IRoleService roleService;
	
	/**
	 * 功能: 新增<br/>
	 * 
	 * 重写：孟雪勤 <br/>
	 * 
	 * @version ：2013-11-19 下午02:03:07<br/>
	 * 
	 * @param entity
	 * @return
	 * @throws Exception <br/>
	 * @see com.zhongpin.pap.base.BaseServiceImpl#saveAndDetail(com.zhongpin.pap.base.BaseEntity)
	 */
	@Override
	public Object saveAndDetail(AuthObj entity) throws Exception {
		
		// 编码不允许为空
		String code = entity.getCode();
		if (null == PmUtil.emptyToNull(code)) {
			throw new SystemException("权限对象[编码]不允许为空！");
		}
		
		// 名称不允许为空
		if (null == PmUtil.emptyToNull(entity.getName())) {
			throw new SystemException("权限对象[名称]不允许为空！");
		}
		
		// 类型不允许为空
		if (null == PmUtil.emptyToNull(entity.getType())) {
			throw new SystemException("权限对象[类型]不允许为空！");
		}
		
		// 编码不允许重复
		AuthObj obj = dao.selectBySinglet("code", code);
		if (null != obj) {
			throw new SystemException("权限对象编码[" + code + "]存在冲突！");
		}
		
		return dao.insert(TypeUtil.getEntitySetDetail(entity));
	}

	/**
	 * 功能: <br/>
	 * 
	 * 重写：孟雪勤 <br/>
	 * 
	 * @version ：2013-11-9 下午02:38:20<br/>
	 * 
	 * @param entity
	 * @throws Exception <br/>
	 * @see com.zhongpin.pap.base.BaseServiceImpl#update(com.zhongpin.pap.base.BaseEntity)
	 */
	@Override
	public void update(AuthObj entity) throws Exception {
		
		// 名称不允许为空
		if (null == PmUtil.emptyToNull(entity.getName())) {
			throw new SystemException("权限对象[名称]不允许为空！");
		}
		
		// 若该权限对象与业务对象权限过滤属性绑定了则不允许进行编辑
		SqlMap<String, String, String> sqlMap = new SqlMap<String, String, String>();
		sqlMap.put("authObj.code", "=", entity.getCode());
		List<BussObjAuthProperty> authProperties = authPropertyService.selectHQL(sqlMap);
		if (null != authProperties && authProperties.size() > 0) {
			throw new SystemException("此权限对象已与业务对象权限过滤属性绑定，不允许编辑！");
		}
		
		// 若已被角色引用，则不允许编辑
		List<Role> roles = roleService.selectAll();
		if (null != roles && roles.size() > 0) {
			for (Role role : roles) {
				List<AuthObj> objs = role.getAuthObjSet();
				if (null != objs && objs.size() > 0) {
					for (AuthObj obj : objs) {
						if (entity.getCode().contains(obj.getCode())) {
							throw new SystemException("此权限对象已被角色引用，不允许编辑！");
						}
					}
				}
			}
		}
		
		AuthObj obj = dao.selectById(entity.getCode());
		obj.setName(entity.getName());// 名称
		obj.setFieldSet(entity.getFieldSet());// 权限字段集合
		dao.update(obj);
	}

	/**
	 * 功能: 根据编码集合删除<br/>
	 * 
	 * 重写：孟雪勤 <br/>
	 * 
	 * @version ：2013-11-19 下午02:27:32<br/>
	 * 
	 * @param <ID>
	 * @param PK
	 * @return
	 * @throws Exception <br/>
	 * @see com.zhongpin.pap.base.BaseServiceImpl#deleteByIds(ID[])
	 */
	@Override
	public <ID extends Serializable> int deleteByIds(ID[] PK) throws Exception {
		
		String codes = PapUtil.arrayToString((String[])PK);
		// 若已被角色引用，则不允许删除
		List<String> quoteCodes = new ArrayList<String>();
		List<Role> roles = roleService.selectAll();
		if (null != roles && roles.size() > 0) {
			for (Role role : roles) {
				List<AuthObj> objs = role.getAuthObjSet();
				if (null != objs && objs.size() > 0) {
					for (AuthObj obj : objs) {
						String code = obj.getCode();
						if (codes.contains(code)) {
							quoteCodes.add(code);// 添加已被引用的权限对象编码
						}
					}
				}
			}
		}
		
		if (quoteCodes.size() > 0) {
			throw new SystemException("所删除的权限对象已被角色引用，不允许删除！");
		}
		
		// 若该权限对象与业务对象权限过滤属性绑定了则不允许进行编辑
		SqlMap<String, String, String> sqlMap = new SqlMap<String, String, String>();
		sqlMap.put("authObj.code", "in", PmUtil.arrayToSQLStr((String[])PK));
		List<BussObjAuthProperty> authProperties = authPropertyService.selectHQL(sqlMap);
		if (null != authProperties && authProperties.size() > 0) {
			throw new SystemException("所删除的权限对象已与业务对象权限过滤属性绑定，不允许删除！");
		}
		
		return dao.deleteByIds(PK);
	}
	
}
