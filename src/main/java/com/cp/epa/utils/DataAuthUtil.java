/**
 * 文件名：@AuthorityUtil.java <br/>
 * 包名：com.zhongpin.pap.utils <br/>
 * 项目名：PAP1.0 <br/>
 * @author 孟雪勤 <br/>
 */
package com.cp.epa.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import com.cp.epa.base.Base;
import com.cp.epa.permission.entity.AuthField;
import com.cp.epa.permission.entity.BussObjAuthProperty;
import com.cp.epa.permission.entity.Role;
import com.cp.epa.permission.entity.RoleAuthValueDetail;
import com.cp.epa.permission.entity.UserAuthValue;
import com.cp.epa.permission.entity.Users;
import com.cp.epa.permission.services.IAuthFieldService;
import com.cp.epa.permission.services.IBussObjAuthPropertyService;
import com.cp.epa.permission.services.IRoleAuthValueDetailService;
import com.cp.epa.permission.services.IUserAuthValueService;
import com.cp.epa.permission.services.IUsersService;
import com.cp.epa.sysconfig.utils.SysConfigContext;


/**
 * 类名：DataAuthUtil  <br />
 *
 * 功能：数据权限工具类
 *
 * @author 孟雪勤 <br />
 * 创建时间：2013-12-19 上午09:51:27  <br />
 * @version 2013-12-19
 */
public class DataAuthUtil extends Base {
	/** 业务对象权限过滤属性业务注册 */
	@Autowired
	private IBussObjAuthPropertyService bussObjAuthPropertyService;
	/** 用户权限规则业务注册 */
	@Autowired
	private IUserAuthValueService userAuthValueService;
	/** 用户业务注册 */
	@Autowired
	private IUsersService usersService;
	/** 角色权限规则明细业务注册 */
	@Autowired
	private IRoleAuthValueDetailService authValueDetailService;
	/** 权限字段业务注册 */
	@Autowired
	private IAuthFieldService authFieldService;
	/**
	 * 权限设定，针对对象类型查询
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Nov 23, 2013 10:03:58 AM <br/>
	 */
	public <T> String buildDataAuthorityEntity(Class<T> clazz,Users u)throws Exception{
		String hql = "from "+clazz.getName()+" e where 1=1";
		if (null != u) {
			Character superMark = u.getSuperMark();
			if (null != superMark && superMark.charValue() == 'Y') {
				return hql;
			}
		}
		
		// 判断数据权限开关
		String status = SysConfigContext.getSwitch("Auth.Data");
		if(status != null && "N".equals(status))
			return hql;
		if(u == null)
			return hql;
		String authorityHql = getAuthorityHql(clazz,u);
		hql += authorityHql;
		return hql;
	}
	
	//获得用户对于某个业务对象的权限
	/*private <T> String getAuthorityHql(Class<T> clazz,Users u)throws Exception{
		String hql = "";
		//通过对象获得过滤属性
		List<BussObjAuthProperty> lbop = bussObjAuthPropertyService.selectByBussObj(clazz.getSimpleName());
		if(lbop == null || lbop.size()==0)
			return hql;
		//构建Map
		Map<String,String> map = new HashMap<String,String>();
		//通过属性获得对应的权限对象与权限字段
		for(BussObjAuthProperty bop:lbop){
			//判断属性是否是对象类型的
			//String _property = bop.getBussObjProperty().getPropertyName();
			String _property = bop.getPropertyFullName();
			//如果是对象类型
			/*String _objName = TypeUtil.getPropertySuperType(clazz,_property);
			if("com.zhongpin.pap.base.BaseEntity".equals(_objName)){
				//对象类型的属性取对象的编码作为条件
				Class<?> clas = TypeUtil.getPropertyType(clazz,_property);
				String idName =TypeUtil.getClassIdField(clas);
				_property = _property+"."+idName;
			}*/
			//过滤属性如果没有找到对应的权限字段，设置该属性为IS NULL
			/*if(bop.getAuthField()== null || "".equals(bop.getAuthField()))
				hql +=" and e."+_property+" is null";
			//权限对象不启用时，设置属性为IS NULL
			else if(bop.getEnabled() == 0)
				hql +=" and e."+_property+" is null";
			//权限对象启用状态
			else if(bop.getEnabled() == 1){
				//判断登录用户是否包含该权限字段
				map.put("authObjCode",bop.getAuthObj().getCode());
				map.put("authFieldCode",bop.getAuthField().getCode());
				map.put("user.userCode",u.getUserCode());
				List<UserAuthValue> luav = userAuthValueService.selectAllByAuth(map);
				map.clear();
				//用户没有该权限对象的权限
				if(luav==null || luav.size()==0) {
					hql +=" and e."+_property+" is null";
				}else {
					//如果用户对于该权限对象和权限字段拥有多个应用，要取并集
					//（这个过程中实际上是要判断多个相同的权限对象之间的角色是否一致）
					//只有角色不一致的情况下才能取并集
					String uavHql = "";
					for(int i=0;i<luav.size();i++){
						String _value = luav.get(i).getValue();
						if (_value != null && !"".equals(_value))  {
							if(_value.indexOf("@")>-1)
								_value = _value.replaceAll("@", "e."+_property);
							if ("".equals(uavHql)) {
								uavHql += " and (" + _value;
							}else {
								uavHql += " or " + _value;
							}
						}
					}
					if (!"".equals(uavHql) && uavHql.contains("("))
						uavHql += ")";
					
					hql += uavHql;
				}
			}
		}
		return hql;
	}*/
	
	private <T> String getAuthorityHql(Class<T> clazz,Users u)throws Exception{
		String hql = "";
		//通过对象获得过滤属性
		List<BussObjAuthProperty> lbop = bussObjAuthPropertyService.selectByBussObj(clazz.getSimpleName());
		if(lbop == null || lbop.size()==0)
			return hql;
		//通过属性获得对应的权限对象与权限字段
		for(BussObjAuthProperty bop:lbop){
			//判断属性是否是对象类型的
			//String _property = bop.getBussObjProperty().getPropertyName();
			String _property = bop.getPropertyFullName();
			//如果是对象类型
			/*String _objName = TypeUtil.getPropertySuperType(clazz,_property);
			if("com.zhongpin.pap.base.BaseEntity".equals(_objName)){
				//对象类型的属性取对象的编码作为条件
				Class<?> clas = TypeUtil.getPropertyType(clazz,_property);
				String idName =TypeUtil.getClassIdField(clas);
				_property = _property+"."+idName;
			}*/
			//过滤属性如果没有找到对应的权限字段，设置该属性为IS NULL
			if(bop.getAuthField()== null || "".equals(bop.getAuthField()))
				hql +=" and e."+_property+" is null";
			//权限对象不启用时，设置属性为IS NULL
			else if(bop.getEnabled() == 0)
				hql +=" and e."+_property+" is null";
			//权限对象启用状态
			else if(bop.getEnabled() == 1){
				hql += getAuthHqlPart(u.getUserCode(), bop.getAuthObj().getCode(), bop.getAuthField().getCode(), _property);
			}
		}
		return hql;
	}
	
	/**
	 * 功能：获得Hql权限片段<br/>
	 *
	 * @author 孟雪勤
	 * @version 2014-11-8 上午09:35:12 <br/>
	 */
	/*public String getAuthHqlPart(String userCode, String authObjCode, String authFieldCode, String propertyName) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("user.userCode", userCode);
		map.put("authObjCode", authObjCode);
		map.put("authFieldCode", authFieldCode);
		List<UserAuthValue> luav = userAuthValueService.selectAllByAuth(map);
		map.clear();
		//用户没有该权限对象的权限
		if(luav==null || luav.size()==0) {
			return " and e." + propertyName + " is null";
		}else {
			//如果用户对于该权限对象和权限字段拥有多个应用，要取并集
			//（这个过程中实际上是要判断多个相同的权限对象之间的角色是否一致）
			//只有角色不一致的情况下才能取并集
			String uavHql = "";
			for(int i=0;i<luav.size();i++){
				String _value = luav.get(i).getValue();
				if (_value != null && !"".equals(_value))  {
					if(_value.indexOf("@")>-1)
						_value = _value.replaceAll("@", "e." + propertyName);
					if ("".equals(uavHql)) {
						uavHql += " and (" + _value;
					}else {
						uavHql += " or " + _value;
					}
				}
			}
			if (!"".equals(uavHql) && uavHql.contains("("))
				uavHql += ")";
			
			return uavHql;
		}
	}*/
	
	public String getAuthHqlPart(String userCode, String authObjCode, String authFieldCode, String propertyName) throws Exception {
		
		String hql = "";
		//判断登录用户是否包含该权限字段
		AuthField authField = authFieldService.selectById(authFieldCode);
		Integer tree = authField.getTree();
		if ((null != tree && tree.intValue() == 1) && (propertyName.contains("."))) {
			Users user = usersService.selectById(userCode);
			List<Role> roles = user.getRoleSet();
			StringBuilder roleCodes = new StringBuilder();
			if (null != roles && roles.size() > 0) {
				for (Role role : roles) {
					if (roleCodes.length() > 0) {
						roleCodes.append(",");
					}
					roleCodes.append("'").append(role.getRoleCode()).append("'");
				}
				SqlMap<String, String, Object> sqlMap = new SqlMap<String, String, Object>();
				sqlMap.put("roleAuthValue.authObj.code", "=", authObjCode);
				sqlMap.put("roleAuthValue.authField.code", "=", authFieldCode);
				sqlMap.put("roleAuthValue.role.roleCode", "in", roleCodes);
				List<RoleAuthValueDetail> ravds = authValueDetailService.selectHQL(sqlMap);
				if (null == ravds || ravds.size() == 0) {
					hql +=" and e."+propertyName+" is null";
				}else {
					String uavHql = "";
					for (RoleAuthValueDetail ravd : ravds) {
						String value = ravd.getValue();
						if (null != value && !"".equals(value)) {
							StringBuilder sb = new StringBuilder();
							String vs[] = value.split(",");
							for (String v : vs) {
								String[] rec = v.split(":");
								if ("Y".equals(rec[1])) {
									if (sb.length() > 0) {
										sb.append(",");
									}
									sb.append("'").append(rec[0]).append("'");
								}
							}
							if ("".equals(uavHql)) {
								uavHql += " and (" + "e." + propertyName + " in (" + sb.toString() + ")";
							}else {
								uavHql += " or " + "e." + propertyName + " in (" + sb.toString() + ")";
							}
						}
					}
					if (!"".equals(uavHql) && uavHql.contains("("))
						uavHql += ")";
					
					hql += uavHql;
				}
			}else {
				hql +=" and e."+propertyName+" is null";
			}
			
		}else {
			//构建Map
			Map<String,String> map = new HashMap<String,String>();
			map.put("authObjCode", authObjCode);
			map.put("authFieldCode", authFieldCode);
			map.put("user.userCode", userCode);
			List<UserAuthValue> luav = userAuthValueService.selectAllByAuth(map);
			map.clear();
			//用户没有该权限对象的权限
			if(luav==null || luav.size()==0) {
				hql +=" and e."+propertyName+" is null";
			}else {
				//如果用户对于该权限对象和权限字段拥有多个应用，要取并集
				//（这个过程中实际上是要判断多个相同的权限对象之间的角色是否一致）
				//只有角色不一致的情况下才能取并集
				String uavHql = "";
				for(int i=0;i<luav.size();i++){
					String _value = luav.get(i).getValue();
					if (_value != null && !"".equals(_value))  {
						if(_value.indexOf("@")>-1)
							_value = _value.replaceAll("@", "e."+propertyName);
						if ("".equals(uavHql)) {
							uavHql += " and (" + _value;
						}else {
							uavHql += " or " + _value;
						}
					}
				}
				if (!"".equals(uavHql) && uavHql.contains("("))
					uavHql += ")";
				
				hql += uavHql;
			}
		}
		return hql;
	}
	
	/**
	 * 功能：获得SQL权限片段<br/>
	 *
	 * @author 孟雪勤
	 * @version 2015-1-17 上午10:33:09 <br/>
	 */
	public String getAuthSqlPart(String userCode, String authObjCode, String authFieldCode, String sqlField) throws Exception {
		
		String sql = "";
		//判断登录用户是否包含该权限字段
		AuthField authField = authFieldService.selectById(authFieldCode);
		Integer tree = authField.getTree();
		if ((null != tree && tree.intValue() == 1)) {
			Users user = usersService.selectById(userCode);
			List<Role> roles = user.getRoleSet();
			StringBuilder roleCodes = new StringBuilder();
			if (null != roles && roles.size() > 0) {
				for (Role role : roles) {
					if (roleCodes.length() > 0) {
						roleCodes.append(",");
					}
					roleCodes.append("'").append(role.getRoleCode()).append("'");
				}
				SqlMap<String, String, Object> sqlMap = new SqlMap<String, String, Object>();
				sqlMap.put("roleAuthValue.authObj.code", "=", authObjCode);
				sqlMap.put("roleAuthValue.authField.code", "=", authFieldCode);
				sqlMap.put("roleAuthValue.role.roleCode", "in", roleCodes);
				List<RoleAuthValueDetail> ravds = authValueDetailService.selectHQL(sqlMap);
				if (null == ravds || ravds.size() == 0) {
					sql +=" and " + sqlField + " is null";
				}else {
					String uavSql = "";
					for (RoleAuthValueDetail ravd : ravds) {
						String value = ravd.getValue();
						if (null != value && !"".equals(value)) {
							StringBuilder sb = new StringBuilder();
							String vs[] = value.split(",");
							for (String v : vs) {
								String[] rec = v.split(":");
								if ("Y".equals(rec[1])) {
									if (sb.length() > 0) {
										sb.append(",");
									}
									sb.append("'").append(rec[0]).append("'");
								}
							}
							if ("".equals(uavSql)) {
								uavSql += " and (" + sqlField + " in (" + sb.toString() + ")";
							}else {
								uavSql += " or " + sqlField + " in (" + sb.toString() + ")";
							}
						}
					}
					if (!"".equals(uavSql) && uavSql.contains("("))
						uavSql += ")";
					
					sql += uavSql;
				}
			}else {
				sql +=" and " + sqlField + " is null";
			}
			
		}else {
			//构建Map
			Map<String,String> map = new HashMap<String,String>();
			map.put("authObjCode", authObjCode);
			map.put("authFieldCode", authFieldCode);
			map.put("user.userCode", userCode);
			List<UserAuthValue> luav = userAuthValueService.selectAllByAuth(map);
			map.clear();
			//用户没有该权限对象的权限
			if(luav==null || luav.size()==0) {
				sql +=" and " + sqlField + " is null";
			}else {
				//如果用户对于该权限对象和权限字段拥有多个应用，要取并集
				//（这个过程中实际上是要判断多个相同的权限对象之间的角色是否一致）
				//只有角色不一致的情况下才能取并集
				String uavSql = "";
				for(int i=0;i<luav.size();i++){
					String _value = luav.get(i).getValue();
					if (_value != null && !"".equals(_value))  {
						if(_value.indexOf("@")>-1)
							_value = _value.replaceAll("@", sqlField);
						if ("".equals(uavSql)) {
							uavSql += " and (" + _value;
						}else {
							uavSql += " or " + _value;
						}
					}
				}
				if (!"".equals(uavSql) && uavSql.contains("("))
					uavSql += ")";
				
				sql += uavSql;
			}
		}
		return sql;
	}
}