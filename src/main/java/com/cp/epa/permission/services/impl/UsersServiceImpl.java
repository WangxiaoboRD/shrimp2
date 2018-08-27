package com.cp.epa.permission.services.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.cp.epa.base.BaseServiceImpl;
import com.cp.epa.exception.SystemException;
import com.cp.epa.permission.dao.IUsersDao;
import com.cp.epa.permission.entity.Role;
import com.cp.epa.permission.entity.RoleAuthValue;
import com.cp.epa.permission.entity.UserAuthValue;
import com.cp.epa.permission.entity.Users;
import com.cp.epa.permission.services.IRoleAuthValueService;
import com.cp.epa.permission.services.IRoleService;
import com.cp.epa.permission.services.IUserAuthValueService;
import com.cp.epa.permission.services.IUsersService;
import com.cp.epa.permission.utils.PmUtil;
import com.cp.epa.utils.PapUtil;
import com.cp.epa.utils.SqlMap;

public class UsersServiceImpl extends BaseServiceImpl<Users,IUsersDao> implements IUsersService {
	
	
	@Resource
	private IRoleService roleServiceImpl;
	
	/** 角色权限规则业务注册 */
	@Resource
	private IRoleAuthValueService roleAuthValueService;
	/** 用户角色规则业务注册 */
	@Resource
	private IUserAuthValueService userAuthValueService;
	
	/**
	 * 功能：<br/>
	 * 简单用户登录
	 * @author 杜中良
	 * @version Mar 26, 2013 4:36:57 PM <br/>
	 */
	public Users selectLoginUser(String name,String password){
		return dao.selectLoginUser(name, password);
	}
	
	/**
	 * 获取user对象以及所有角色，以及user对象目前所包含的角色
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version May 30, 2013 9:01:19 AM <br/>
	 * @throws Exception 
	 */
	public Users selectById(String PK,List<Role> rlist) throws Exception{
		rlist.addAll(roleServiceImpl.selectAll());
		return dao.selectById(PK);
	}
	
	/**
	 * 功能: <br/>
	 * 
	 * 重写：孟雪勤 <br/>
	 * 
	 * @version ：2015-12-31 下午05:37:17<br/>
	 * 
	 * @param entity
	 * @return
	 * @throws Exception <br/>
	 * @see com.zhongpin.pap.base.BaseServiceImpl#save(com.zhongpin.pap.base.BaseEntity)
	 */
	@Override
	public Object save(Users entity) throws Exception {
		entity.setPinyinHead(PapUtil.getHeadChar(entity.getUserRealName()));
		return super.save(entity);
	}

	/**
	 * 更新对象，多对多关系
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version May 31, 2013 3:39:27 PM <br/>
	 */
	public void update(Users u)throws Exception{
		
		if(u != null){
			Users _u = dao.selectById(u.getUserCode());
			List<Role> _r = u.getRoleSet();
			_u.setRoleSet(_r);
			_u.setPinyinHead(PapUtil.getHeadChar(u.getUserRealName()));
			dao.update(_u);
		}
	}
	
	/**
	 * 功能: 授权角色<br/>
	 * 
	 * 重写：孟雪勤 <br/>
	 * 
	 * @version ：2013-11-19 上午08:36:16<br/>
	 * 
	 * @param entity
	 * @throws Exception <br/>
	 * @see com.zhongpin.pap.permission.services.IUserService#grantRole(com.zhongpin.pap.permission.entity.User)
	 */
	public void grantRole(Users entity) throws Exception {
		if(null != entity){
			
			// 删除角色所做处理
			Map<String, String> tempStack = entity.getTempStack();
			if (null != tempStack && ! "".equals(tempStack.get("delRoleCodes"))) {
				SqlMap<String, String, String> roleSqlMap = new SqlMap<String, String, String>();
				roleSqlMap.put("user.userCode", "=", entity.getUserCode());
				roleSqlMap.put("roleCode", "in", PmUtil.arrayToSQLStr(tempStack.get("delRoleCodes").split(",")));
				userAuthValueService.delete(roleSqlMap);
			}
			
			Users user = dao.selectById(entity.getUserCode());
			List<Role> roles = entity.getRoleSet();
			if (null != roles && roles.size() > 0) {
				for (Role role : roles) {
					SqlMap<String, String, String> uavSqlMap = new SqlMap<String, String, String>();
					uavSqlMap.put("user.userCode", "=", user.getUserCode());
					uavSqlMap.put("roleCode", "=", role.getRoleCode());
					List<UserAuthValue> uavList = userAuthValueService.selectHQL(uavSqlMap);
					if (null == uavList || uavList.size() == 0) {
						SqlMap<String, String, String> sqlMap = new SqlMap<String, String, String>();
						sqlMap.put("role.roleCode", "=", role.getRoleCode());
						List<RoleAuthValue> roleAuthValues = roleAuthValueService.selectHQL(sqlMap);
						if (null != roleAuthValues && roleAuthValues.size() > 0) {
							for (RoleAuthValue value : roleAuthValues) {
								// 创建用户权限规则信息
								UserAuthValue authValue = new UserAuthValue();
								authValue.setUser(user);//用户
								authValue.setRoleCode(value.getRole().getRoleCode());//  角色编码
								authValue.setAuthType(value.getAuthObj().getType()); // 权限类型
								authValue.setAuthObjCode(value.getAuthObj().getCode()); // 权限对象编码
								authValue.setAuthFieldCode(value.getAuthField().getCode());// 权限字段编码
								
								authValue.setValue(value.getValue());// 权限规则值
								
								// 保存用户权限规则值
								userAuthValueService.save(authValue);
							}
						}
					}
				}
			}
			
			user.setRoleSet(roles);
			dao.update(user);
		}
	}

	/**
	 * 功能: 删除<br/>
	 * 
	 * 重写：孟雪勤 <br/>
	 * 
	 * @version ：2013-11-19 下午02:40:00<br/>
	 * 
	 * @param <ID>
	 * @param PK
	 * @return
	 * @throws Exception <br/>
	 * @see com.zhongpin.pap.base.BaseServiceImpl#deleteByIds(ID[])
	 */
	@Override
	public <ID extends Serializable> int deleteByIds(ID[] PK) throws Exception {
		
		SqlMap<String, String, Object> sqlMap = new SqlMap<String, String, Object>();
		sqlMap.put("userCode", "in", PmUtil.arrayToSQLStr((String[])PK));
		sqlMap.put("userStatus", "=", 1);
		List<Users> users = dao.selectByConditionHQL(sqlMap);
		if (null != users && users.size() > 0) {
			throw new SystemException("所删除的用户已启用，不允许删除！");
		}
		
		// 手动级联删除用户权限规则信息
		sqlMap.clear();
		sqlMap.put("user.userCode", "in", PmUtil.arrayToSQLStr((String[])PK));
		userAuthValueService.delete(sqlMap);
		
		return dao.deleteByIds(PK);
	}

	/**
	 * 功能: 修改密码<br/>
	 * 
	 * 重写：孟雪勤 <br/>
	 * 
	 * @version ：2014-7-2 上午08:52:42<br/>
	 * 
	 * @param entity
	 * @return
	 * @throws Exception <br/>
	 * @see com.zhongpin.pap.permission.services.IUsersService#updatePwd(com.zhongpin.pap.permission.entity.Users)
	 */
	@Override
	public int updatePwd(Users entity) throws Exception {
		
		Users user = dao.selectById(entity.getUserCode());
		Map<String, String> tempStack = entity.getTempStack();
		if (null != tempStack) {
			String oldPwd = tempStack.get("oldPassword");
			if (!user.getUserPassword().equals(oldPwd)) {
				throw new SystemException("原密码输入不正确！");
			}
			
			user.setUserPassword(entity.getUserPassword());
		}
		
		return 1;
	}

	/**
	 * 功能: 根据拼音首字母查询<br/>
	 * 
	 * 重写：孟雪勤 <br/>
	 * 
	 * @version ：2015-12-31 下午05:46:30<br/>
	 * 
	 * @param key
	 * @return
	 * @throws Exception <br/>
	 * @see com.zhongpin.pap.permission.services.IUsersService#selectByPinyinHead(java.lang.String)
	 */
	@Override
	public List<Users> selectByPinyinHead(String key) throws Exception {
		List<Users> list = null;
		if(key == null || "".equals(key)){
			list = super.selectTopValue(1, 1, 100);
		}else{
			//判断key是否包含中文
			boolean b=false;
			key = key.trim();
			for (int i = 0; i < key.length(); i++) {  
	            if (key.substring(i, i + 1).matches("[\\u4e00-\\u9fbb]+")) {  
	               b=true;
	               break;
	            }  
	        }
			if(b) // 若包含中文按名称查询，否则按拼音首字母查
				list = super.selectTopValue("userRealName", "like", key, 20);
			else
				list = super.selectTopValue("pinyinHead", "like", key, 20);
		}
		return list;
	}
}
