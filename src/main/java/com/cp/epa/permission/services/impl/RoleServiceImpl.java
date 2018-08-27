package com.cp.epa.permission.services.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.cp.epa.base.BaseServiceImpl;
import com.cp.epa.exception.SystemException;
import com.cp.epa.permission.dao.IRoleDao;
import com.cp.epa.permission.entity.AuthField;
import com.cp.epa.permission.entity.AuthObj;
import com.cp.epa.permission.entity.Role;
import com.cp.epa.permission.entity.RoleAuthValue;
import com.cp.epa.permission.entity.UserAuthValue;
import com.cp.epa.permission.entity.Users;
import com.cp.epa.permission.services.IAuthObjService;
import com.cp.epa.permission.services.IRoleAuthValueService;
import com.cp.epa.permission.services.IRoleService;
import com.cp.epa.permission.services.IUserAuthValueService;
import com.cp.epa.permission.services.IUsersService;
import com.cp.epa.permission.utils.PmUtil;
import com.cp.epa.utils.PapUtil;
import com.cp.epa.utils.SqlMap;
import com.cp.epa.utils.TypeUtil;



public class RoleServiceImpl extends BaseServiceImpl<Role,IRoleDao> implements IRoleService{
	
	/** 权限对象业务逻辑注册 */
	@Resource
	private IAuthObjService authObjService;
	/** 角色权限规则业务逻辑注册 */
	@Resource
	private IRoleAuthValueService roleAuthValueService;
	
	/** 用户角色规则业务注册 */
	@Resource
	private IUserAuthValueService userAuthValueService;
	/** 用户业务注册 */
	@Resource
	private IUsersService usersService;
	
	/**
	 * 功能: 角色新增<br/>
	 * 
	 * 重写：孟雪勤 <br/>
	 * 
	 * @version ：2013-11-11 上午10:44:59<br/>
	 * 
	 * @param entity
	 * @return
	 * @throws Exception <br/>
	 * @see com.zhongpin.pap.base.BaseServiceImpl#saveAndDetail(com.zhongpin.pap.base.BaseEntity)
	 */
	@Override
	public Object saveAndDetail(Role entity) throws Exception {
		
		// 编码不允许为空
		String code = entity.getRoleCode();
		if (null == PmUtil.emptyToNull(code)) {
			throw new SystemException("角色[编码]不允许为空！");
		}
		
		// 名称不允许为空
		if (null == PmUtil.emptyToNull(entity.getRoleName())) {
			throw new SystemException("角色[名称]不允许为空！");
		}
		
		// 编码不允许重复
		Role role = dao.selectBySinglet("roleCode", code);
		if (null != role) {
			throw new SystemException("角色编码[" + code + "]存在冲突！");
		}
		 
		Object PK = dao.insert(TypeUtil.getEntitySetDetail(entity));
		 // 权限对象集合
		 List<AuthObj> authObjs = entity.getAuthObjSet();
		 if (null != authObjs && authObjs.size() > 0) {
			 for (AuthObj obj : authObjs) {
				 AuthObj authObj = authObjService.selectById(obj.getCode());
				 // 权限字段集合
				 List<AuthField> authFields = authObj.getFieldSet();
				 if (null != authFields && authFields.size() > 0) {
					 for (AuthField field : authFields) {
						 RoleAuthValue value = new RoleAuthValue();
						 value.setRole(entity); // 角色
						 value.setAuthObj(obj); // 权限对象
						 value.setAuthField(field);// 权限字段
						 roleAuthValueService.save(value); // 保存角色权限规则信息
					 }
				 }
			 }
		 }
		 
		 return PK;
	}

	/**
	 * 功能: 角色编辑<br/>
	 * 
	 * 重写：孟雪勤 <br/>
	 * 
	 * @version ：2013-11-11 上午08:39:46<br/>
	 * 
	 * @param entity
	 * @throws Exception <br/>
	 * @see com.zhongpin.pap.base.BaseServiceImpl#update(com.zhongpin.pap.base.BaseEntity)
	 */
	@Override
	public void update(Role entity) throws Exception {
		
		// 名称不允许为空
		if (null == PmUtil.emptyToNull(entity.getRoleName())) {
			throw new SystemException("角色[名称]不允许为空！");
		}
		
		// 编辑角色
		Role role = dao.selectById(entity.getRoleCode());
		role.setRoleName(entity.getRoleName());// 名称
		role.setRoleStatus(entity.getRoleStatus());// 类型
		List<AuthObj> authObjs = entity.getAuthObjSet();
		role.setAuthObjSet(authObjs);// 权限对象集合
		
		// 角色权限规则更新方式：先删除，后新增
		Map<String, String> tempStack = entity.getTempStack();
		if (null != tempStack && ! "".equals(tempStack.get("delAuthObjCodes"))) {
			// 删除的权限对象编码集合
			String delAuthObjCodes = tempStack.get("delAuthObjCodes");
			if (! "".equals(delAuthObjCodes)) {
				SqlMap<String, String, String> sqlMap = new SqlMap<String, String, String>();
				sqlMap.put("role.roleCode", "=", entity.getRoleCode());
				sqlMap.put("authObj.code", "in", PmUtil.arrayToSQLStr(delAuthObjCodes.split(",")));
				List<RoleAuthValue> list = roleAuthValueService.selectHQL(sqlMap);
				if (null != list && list.size() > 0) {
					roleAuthValueService.delete(list);
				}
				
				// 删除用户权限规则信息
				SqlMap<String, String, String> userAuthSqlMap = new SqlMap<String, String, String>();
				userAuthSqlMap.put("roleCode", "=", entity.getRoleCode());
				userAuthSqlMap.put("authObjCode", "in", PmUtil.arrayToSQLStr(delAuthObjCodes.split(",")));
				userAuthValueService.delete(userAuthSqlMap);
			}
		}
		
		// 查看所有引用该角色的用户
		List<Users> quoteUsers = new ArrayList<Users>();
		List<Users> users = usersService.selectAll();
		if (null != users && users.size() > 0) {
			for (Users user : users) {
				List<Role> roles = user.getRoleSet();
				if (null != roles && roles.size() > 0) {
					for (Role r : roles) {
						if (r.getRoleCode().equals(entity.getRoleCode())) {
							quoteUsers.add(user);
						}
					}
				}
			}
		}
		
		// 权限对象集合
		if (null != authObjs && authObjs.size() > 0) {
			for (AuthObj obj : authObjs) {
				AuthObj authObj = authObjService.selectById(obj.getCode());
				// 权限字段集合
				List<AuthField> authFields = authObj.getFieldSet();
				if (null != authFields && authFields.size() > 0) {
					for (AuthField field : authFields) {
						
						// 查询角色权限规则是否存在
						SqlMap<String, String, String> sqlMap = new SqlMap<String, String, String>();
						sqlMap.put("role.roleCode", "=", entity.getRoleCode()); 
						sqlMap.put("authObj.code", "=", obj.getCode());
						sqlMap.put("authField.code", "=", field.getCode());
						List<RoleAuthValue> authValues = roleAuthValueService.selectHQL(sqlMap);
						if (null == authValues || authValues.isEmpty()) {
							RoleAuthValue rav = new RoleAuthValue();
							rav.setRole(entity); // 角色
							rav.setAuthObj(obj); // 权限对象
							rav.setAuthField(field);// 权限字段
							roleAuthValueService.save(rav); // 保存角色权限规则信息
							
							// 保存用户权限规则信息
							if (quoteUsers.size() > 0) {
								for(Users qu : quoteUsers) {
									UserAuthValue uav = new UserAuthValue();
									uav.setUser(qu); // 用户
									uav.setRoleCode(entity.getRoleCode()); // 角色编码
									uav.setAuthType(obj.getType());// 权限类型
									uav.setAuthObjCode(obj.getCode());// 权限对象编码
									uav.setAuthFieldCode(field.getCode());// 权限字段编码
									userAuthValueService.save(uav);
								}
							}
						}
					}
				}
			}
		}
		
		dao.update(role);
	}

	/**
	 * 功能: 删除<br/>
	 * 
	 * 重写：孟雪勤 <br/>
	 * 
	 * @version ：2013-11-16 上午11:30:21<br/>
	 * 
	 * @param <ID>
	 * @param PK
	 * @return
	 * @throws Exception <br/>
	 * @see com.zhongpin.pap.base.BaseServiceImpl#deleteByIds(ID[])
	 */
	@Override
	public <ID extends Serializable > int deleteByIds(ID[] PK) throws Exception {
		
		String codes = PapUtil.arrayToString((String[])PK);
		// 若已被用户引用，则不允许删除
		List<String> quoteCodes = new ArrayList<String>();
		List<Users> users = usersService.selectAll();
		if (null != users && users.size() > 0) {
			for (Users user : users) {
				List<Role> roles = user.getRoleSet();
				if (null != roles && roles.size() > 0) {
					for (Role role : roles) {
						String code = role.getRoleCode();
						if (codes.contains(code)) {
							quoteCodes.add(code);// 添加已被引用的角色编码
						}
					}
				}
			}
		}
		
		if (quoteCodes.size() > 0) {
			throw new SystemException("所删除的角色已被用户引用，不允许删除！");
		}
		
		// 手动级联删除角色权限规则信息
		SqlMap<String, String, String> sqlMap = new SqlMap<String, String, String>();
		sqlMap.put("role.roleCode", "in", PmUtil.arrayToSQLStr((String[])PK));
		List<RoleAuthValue> ravs = roleAuthValueService.selectHQL(sqlMap);
		if (null != ravs && ravs.size() > 0) {
			roleAuthValueService.delete(ravs);
		}
		
		return dao.deleteByIds(PK);
	}
	
}
