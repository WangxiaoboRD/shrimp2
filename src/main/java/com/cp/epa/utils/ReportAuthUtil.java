/**
 * 文件名：@ReportAuthUtil.java <br/>
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
import com.cp.epa.bussobj.entity.BussObjProperty;
import com.cp.epa.bussobj.entity.BussinessObject;
import com.cp.epa.bussobj.services.IBussObjPropertyService;
import com.cp.epa.bussobj.services.IBussinessObjectService;
import com.cp.epa.exception.SystemException;
import com.cp.epa.permission.entity.BussObjAuthProperty;
import com.cp.epa.permission.entity.UserAuthValue;
import com.cp.epa.permission.entity.Users;
import com.cp.epa.permission.services.IBussObjAuthPropertyService;
import com.cp.epa.permission.services.IUserAuthValueService;
import com.cp.epa.permission.services.IUsersService;
import com.cp.epa.sysconfig.utils.SysConfigContext;

/**
 * 类名：ReportAuthUtil  <br />
 *
 * 功能：报表权限工具类：获得权限片段
 *
 * @author 孟雪勤 <br />
 * 创建时间：2013-12-19 上午09:56:49  <br />
 * @version 2013-12-19
 */
public class ReportAuthUtil extends Base {

	/** 业务对象权限过滤属性业务注册 */
	@Autowired
	private IBussObjAuthPropertyService bussObjAuthPropertyService;
	/** 用户权限规则业务注册 */
	@Autowired
	private IUserAuthValueService userAuthValueService;
	/** 业务对象属性业务注册 */
	@Autowired
	private IBussObjPropertyService bussObjPropertyService;
	/** 业务对象业务注册 */
	@Autowired
	private IBussinessObjectService bussinessObjectService;
	/** 用户业务逻辑注册 */
	@Autowired
	private IUsersService usersService;
	
	/**
	 * 功能：根据表名、字段名获得权限SQL片段<br/>
	 *
	 * @author 孟雪勤
	 * @version 2013-12-5 上午10:14:56 <br/>
	 */
	public String getAfqlByTable(String tableCode, String fieldCode, String sqlField) throws Exception {
		
		// 判断数据权限开关
		String status = SysConfigContext.getSwitch("Auth.Data");
		if (null != status && "N".equals(status)) {
			return "";
		}
		
		// 获得当前登录用户
		//Map<String, Object> session = SysContext.get();
		Users user = SysContainer.get();
//		if(null != session)
//			user = (Users)session.get("CURRENTUSER");
		if (null == user) {
			return "";
			//throw new SystemException("获取权限sql片段失败！您可能还没有登录或登录已过期！");
		}
		
		return getAfqlByTable(user.getUserCode(), tableCode, fieldCode, sqlField);
	}
	
	/**
	 * 功能：根据表名、字段名获得权限SQL片段<br/>
	 *
	 * @author 孟雪勤
	 * @version 2013-12-5 上午11:39:45 <br/>
	 */
	public String getAfqlByTable(String userCode, String tableCode, String fieldCode, String sqlField) throws Exception {
		
		// 判断数据权限开关
		String status = SysConfigContext.getSwitch("Auth.Data");
		if (null != status && "N".equals(status)) {
			return "";
		}
		
		// 查询表名对象的业务对象
		BussinessObject bussObj = bussinessObjectService.selectBySinglet("tableCode", tableCode);
		if (null == bussObj) 
			return "";
		
		SqlMap<String, String, Object> sqlMap = new SqlMap<String, String, Object>();
		sqlMap.put("propertyName", "=", bussObj.getBussCode()); // 业务对象名
		sqlMap.put("isRoot", "=", "Y"); // 业务对象标识
		List<BussObjProperty> roots = bussObjPropertyService.selectHQL(sqlMap);
		if (null == roots || roots.size()== 0 ) 
			return "";
		
			BussObjProperty root = roots.get(0);
			sqlMap.clear();
			sqlMap.put("pid", "=", root.getId());
			sqlMap.put("fieldName", "=", fieldCode);
			List<BussObjProperty> properties = bussObjPropertyService.selectHQL(sqlMap); // 查询业务对象属性
			if (null == properties || properties.size() == 0) 
				return "";
		
		return getAfqlByBussObj(userCode, bussObj.getBussCode(), properties.get(0).getPropertyName(), sqlField);
	}
	
	/**
	 * 功能：根据业务对象编码、业务对象属性名获得权限SQL片段<br/>
	 *
	 * @author 孟雪勤
	 * @version 2013-12-5 上午11:27:22 <br/>
	 */
	public String getAfqlByBussObj(String bussObjCode, String propertyName, String sqlField) throws Exception {
		// 判断数据权限开关
		String status = SysConfigContext.getSwitch("Auth.Data");
		if (null != status && "N".equals(status)) {
			return "";
		}
		
		// 获得当前登录用户
//		Map<String, Object> session = SysContext.get();
//		Users user = null;
//		if(null != session)
//			user = (Users)session.get("CURRENTUSER");
		Users user = SysContainer.get();
		if (null == user) {
			throw new SystemException("获取权限sql片段失败！您可能还没有登录或登录已过期！");
		}
		
		return getAfqlByBussObj(user.getUserCode(), bussObjCode, propertyName, sqlField);
	}
	
	/**
	 * 功能：根据业务对象编码、业务对象属性名获得权限SQL片段<br/>
	 *
	 * @author 孟雪勤
	 * @version 2013-12-5 上午10:16:36 <br/>
	 */
	public String getAfqlByBussObj(String userCode, String bussObjCode, String propertyName, String sqlField) throws Exception {
		
		// 判断数据权限开关
		String status = SysConfigContext.getSwitch("Auth.Data");
		if (null != status && "N".equals(status)) {
			return "";
		}
		
		String afql = ""; // 权限sql片段
		
		// 当前用户
		Users user = usersService.selectById(userCode);
		// 没有任何权限
		if (null == user.getRoleSet() || user.getRoleSet().size() == 0) 
			return " and 1 != 1 ";
		
		SqlMap<String, String, Object> sqlMap = new SqlMap<String, String, Object>();
		sqlMap.put("propertyName", "=", bussObjCode); // 业务对象名
		sqlMap.put("isRoot", "=", "Y"); // 业务对象标识
		
		List<BussObjProperty> roots = bussObjPropertyService.selectHQL(sqlMap);
		if (null == roots || roots.size() == 0) 
			return "";
		
		BussObjProperty root = roots.get(0);
		sqlMap.clear();
		sqlMap.put("pid", "=", root.getId());
		sqlMap.put("propertyName", "=", propertyName);
		List<BussObjProperty> properties = bussObjPropertyService.selectHQL(sqlMap); // 查询业务对象属性
		if (null == properties || properties.size() == 0) 
			return "";
		BussObjProperty objProperty = properties.get(0);
		// 若属性不是权限过滤属性
		if (! objProperty.getIsAuthProperty().equals("Y")) 
			return "";
		
		// 若是权限过滤属性
		BussObjAuthProperty authProperty = bussObjAuthPropertyService.selectByBussObj(bussObjCode, objProperty.getId());
		if (null == authProperty) 
			return "";

		//过滤属性如果没有找到对应的权限字段，设置该属性为IS NULL
		if(authProperty.getAuthField() == null || "".equals(authProperty.getAuthField()))
			return " and " + sqlField + " is null ";
		//权限对象不启用时，设置属性为IS NULL
		else if(authProperty.getEnabled() == 0)
			return " and " + sqlField + " is null ";
		//权限对象启用状态
		else if(authProperty.getEnabled() == 1){
			//构建Map
			Map<String, String> map = new HashMap<String, String>();
			//判断登录用户是否包含该权限字段
			map.put("authObjCode", authProperty.getAuthObj().getCode());
			map.put("authFieldCode", authProperty.getAuthField().getCode());
			map.put("user.userCode", userCode);
			List<UserAuthValue> uavs = userAuthValueService.selectAllByAuth(map);
			map.clear();
			//用户没有该权限对象的权限
			if(null == uavs || uavs.size() == 0){
				return " and " + sqlField + " is null ";
			} else {
				//如果用户对于该权限对象和权限字段拥有多个应用，要取并集
				//（这个过程中实际上是要判断多个相同的权限对象之间的角色是否一致）
				//只有角色不一致的情况下才能取并集
				String uavSql = "";
				for (int i = 0; i < uavs.size();i++){
					String _value = uavs.get(i).getValue();
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
				
				return uavSql;
			}
		}
		
		return afql;
	}
}
