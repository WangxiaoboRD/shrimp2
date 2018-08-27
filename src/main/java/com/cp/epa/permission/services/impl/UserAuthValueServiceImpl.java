/**
 * 文件名：@UserAuthValueServiceImpl.java <br/>
 * 包名：com.zhongpin.pap.permission.services.impl <br/>
 * 项目名：PAP1.0 <br/>
 * @author 孟雪勤 <br/>
 */
package com.cp.epa.permission.services.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.cp.epa.base.BaseServiceImpl;
import com.cp.epa.exception.SystemException;
import com.cp.epa.permission.dao.IUserAuthValueDao;
import com.cp.epa.permission.entity.Item;
import com.cp.epa.permission.entity.UserAuthValue;
import com.cp.epa.permission.entity.Users;
import com.cp.epa.permission.services.IItemService;
import com.cp.epa.permission.services.IUserAuthValueService;
import com.cp.epa.permission.utils.enums.AuthType;
import com.cp.epa.utils.SqlMap;

/**
 * 类名：UserAuthValueServiceImpl  <br />
 *
 * 功能：用户权限规则业务逻辑层实现
 *
 * @author 孟雪勤 <br />
 * 创建时间：2013-11-6 下午02:58:32  <br />
 * @version 2013-11-6
 */
public class UserAuthValueServiceImpl extends BaseServiceImpl<UserAuthValue, IUserAuthValueDao> implements IUserAuthValueService {
	
	@Autowired
	private IItemService itemService;
	/**
	 * 根据用户生成菜单
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Nov 17, 2013 10:14:35 AM <br/>
	 */
	public List<Item> getAllItemByMenu(Users u)throws Exception{
		
		Character superMark = u.getSuperMark(); // 超级管理员标识
		List<Item> its = null;
		// 若用户是超级管理员，则不限制功能权限
		if (null != superMark && superMark.charValue() == 'Y') {
			SqlMap<String, String, Object> sqlMap = new SqlMap<String, String, Object>();
			sqlMap.put("itemType", "=", 0);
			sqlMap.put("status", "=", "Y");
			sqlMap.put("rank", "orderby", "asc");
			its = itemService.selectHQL(sqlMap);
		}else {
			//获得某一个用户的所有功能权限值
			List<UserAuthValue> uavs = selectAllValue(u);
			if(uavs == null || uavs.size()==0)
				throw new SystemException("没有找到用户对应的权限");
			//去除功能权限值中的重复值，并验证权限值的合法性
			String[] _endValue = selectCheckValue(uavs);
			//验证每一个功能ID是不是一个合法的ID，并生成菜单列表
			List<Item> litems = new ArrayList<Item>();
			String regix = "^\\d+$";
			for(String _tmpValue : _endValue){
				if(_tmpValue.matches(regix)){
					Integer itemId = Integer.parseInt(_tmpValue);
					Item _it = itemService.selectById(itemId);
					//不是菜单的排除
					if(_it.getItemType() != 0)
						continue;
					//菜单不启用的排除
					if("N".equals(_it.getStatus()))
						continue;
					litems.add(_it);
				}
			}
			if(litems == null || litems.size()==0)
				throw new SystemException("没有找到用户对应的菜单");
			
			its = sortValue(litems);
		}
		return its;
	}
	
	//获得某一个用户的所有功能权限值
	private List<UserAuthValue> selectAllValue(Users u)throws Exception{
		if(u == null)
			throw new SystemException("用户信息不存在");
		SqlMap<String,String,String> sqlmap = new SqlMap<String,String,String>();
		sqlmap.put("user.userCode", "=", u.getUserCode());
		//"FUNTION" 代表的类型为功能权限
		sqlmap.put("authType", "=", AuthType.FUNCTION.toString());
		List<UserAuthValue> uavs = selectHQL(sqlmap);
		return uavs;
	}
	
	//去除功能权限值中的重复值，并验证权限值的合法性
	private String[] selectCheckValue(List<UserAuthValue> authList){
		
		String _value = "";
		for(UserAuthValue uav: authList)
			_value += uav.getValue();
		if("".equals(_value))
			throw new SystemException("没有找到用户对应的菜单");
		//将获得的权限对一个的功能ID号生成数组并去除重复值
		String[] _arrayValue = _value.split(",") ;
		List<String> list = Arrays.asList(_arrayValue);
		Set<String> set = new HashSet<String>(list);
		String[] _endValue=(String[])set.toArray(new String[0]);
		return _endValue;
	}
	
	//为生成的菜单排序
	private List<Item> sortValue(List<Item> items){
		//排序
		Collections.sort(items,new Comparator<Item>(){
            public int compare(Item _it, Item _its) {
                return _it.getRank().compareTo(_its.getRank());
            }
        });
		return items;
	}
	
	/**
	 * 
	 * 功能：<br/>
	 * 验证按钮的url是否合法
	 * @author 杜中良
	 * @version Nov 17, 2013 10:14:35 AM <br/>
	 */
	public boolean validate(String url,Users u)throws Exception{
		
		Character superMark = u.getSuperMark(); // 超级管理员标识
		if (null != superMark && superMark.charValue() == 'Y') 
			return true;
		
		url = editUrl(url);
		if(url == null || "".equals(url))
			return true;
		//1.通过url获得对应的ID
		Item it = itemService.selectBySinglet("url", url);
		if(it == null)
			return true;
		//判断url是菜单或者是按钮
		if(it.getItemType() == 0)
			return true;
		if("N".equals(it.getStatus()))
			//throw new SystemException("该功能没有启用");
			return false;
		//2.查询用户权限中是否包含这个ID
		List<UserAuthValue> luav =  selectAllValue(u);
		if(luav == null || luav.size()==0)
			//throw new SystemException("没有找到用户权限");
			return false;
		//去除功能权限值中的重复值，并验证权限值的合法性
		String[] _endValue = selectCheckValue(luav);
		//判断查询的ID是否在用户的权限中
		for(String _value : _endValue){
			if((it.getId()+"").equals(_value))
				return true;
		}
		return false;
	}
	
	/**
	 * 对URL进行整理编辑
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Nov 28, 2013 11:54:33 AM <br/>
	 */
	private String editUrl(String url){
		//判断路径如果包含有？或/，将其去除
		if(url == null || "".equals(url))
			return null;
		if(url.indexOf("/")>-1)
			url = url.substring(url.lastIndexOf("/")+1);
		if(url.indexOf("?")>-1)
			url = url.substring(0,url.indexOf("?"));
		return url;
	}
	
	
	
	/**
	 * 根据条件查询
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Nov 24, 2013 3:44:17 PM <br/>
	 */
	public List<UserAuthValue> selectAllByCod(String authObj,String authField,Users u)throws Exception{
		
		//List<UserAuthValue> uav = new ArrayList<>
		SqlMap<String,String,String> sqlmap = new SqlMap<String,String,String>();
		sqlmap.put("user.userCode", "=", u.getUserCode());
		sqlmap.put("authType", "=", AuthType.DATA.toString());
		sqlmap.put("authObjCode","=",authObj);
		sqlmap.put("authFieldCode","=",authField);
		return dao.selectByConditionHQL(sqlmap);
		
	}

	/**
	 * 通过条件查询结果集，权限专用
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Nov 27, 2013 9:25:25 AM <br/>
	 */
	public List<UserAuthValue> selectAllByAuth(Map<String, String> map)throws Exception {
		// TODO Auto-generated method stub
		return dao.selectAllByAuth(map);
	}
	
	/**
	 * 接口权限的验证
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Nov 29, 2013 9:18:50 AM <br/>
	 */
	public boolean checkInterfaceAuthority(String methodName,String userId)throws Exception{
		if(methodName==null || userId == null)
			return false;
		//1.查找该方法对应的Item（功能列表）对象
		Item it = itemService.selectBySinglet("functionName", methodName);
		if(it == null) 
			return false;
		if("N".equals(it.getStatus()))
			return false;
		//2.通过用户名与功能权限标示查找对应的 用户权限表
		Users u = new Users();
		u.setUserCode(userId);
		List<UserAuthValue> luav =  selectAllValue(u);
		if(luav == null || luav.size()==0)
			return false;
		//去除功能权限值中的重复值，并验证权限值的合法性
		String[] _endValue = selectCheckValue(luav);
		//3.判断Item的编码是否存在与查询结果中
		//判断查询的ID是否在用户的权限中
		for(String _value : _endValue){
			if((it.getId()+"").equals(_value))
				return true;
		}
		return false;
	}
}
