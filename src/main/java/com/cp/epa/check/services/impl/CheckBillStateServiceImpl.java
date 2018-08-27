package com.cp.epa.check.services.impl;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import javax.annotation.Resource;

import com.cp.epa.base.BaseServiceImpl;
import com.cp.epa.check.dao.ICheckBillStateDao;
import com.cp.epa.check.entity.Check;
import com.cp.epa.check.entity.CheckBillState;
import com.cp.epa.check.entity.CheckRole;
import com.cp.epa.check.entity.CheckState;
import com.cp.epa.check.services.ICheckBillStateService;
import com.cp.epa.check.services.ICheckRoleService;
import com.cp.epa.check.services.ICheckService;
import com.cp.epa.exception.SystemException;
import com.cp.epa.permission.entity.Role;
import com.cp.epa.permission.entity.Users;
import com.cp.epa.permission.services.IUsersService;
import com.cp.epa.utils.PapUtil;
import com.cp.epa.utils.SqlMap;
import com.cp.epa.utils.SysContainer;
/**
 * 审核对象服务实现
 * 类名：CheckServiceImpl  
 *
 * 功能：
 *
 * @author dzl 
 * 创建时间：Dec 3, 2014 4:03:04 PM 
 * @version Dec 3, 2014
 */
public class CheckBillStateServiceImpl extends BaseServiceImpl<CheckBillState,ICheckBillStateDao> implements ICheckBillStateService{

	@Resource
	private ICheckRoleService checkRoleService;
	@Resource
	private ICheckService checkService;
	@Resource
	private IUsersService usersService;
	
	/**
	 * 审核功能
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Dec 7, 2014 12:00:38 PM <br/>
	 */
	public <ID extends Serializable> Boolean check(ID PK,String className)throws Exception{
		
		Check c = checkService.selectBySinglet("objName", className);
		//获得当前登录用户
		Users u= SysContainer.get();
		SqlMap<String,String,String> sqlMap = new SqlMap<String,String,String>();
		TreeSet<String> set = getUserAuth(PK,className,c,u,sqlMap);
	
		Iterator<String> it = set.iterator();
		//获得当前的单据审核状况
		sqlMap.put("billId", "=", PK.toString());
		sqlMap.put("billType", "=", className);
		List<CheckBillState> cbsList = super.selectHQL(sqlMap);
		sqlMap.clear();
		if(cbsList == null || cbsList.size()==0){
			String num = it.next();
			if(!"1".equals(num))
				throw new SystemException("一级审核未完成");
			else{
				CheckBillState _cbs = new CheckBillState();
				_cbs.setBillId(PK.toString());
				_cbs.setBillType(className);
				_cbs.setBussName(c.getBussName());
				
				_cbs.setOneCheck('Y');
				_cbs.setOneCheckDate(PapUtil.date(new Date()));
				_cbs.setOneCheckUser(u.getUserRealName());
				
				super.save(_cbs);
				return true;
			}
		}else{
			if(cbsList.size()>1)
				throw new SystemException("同一单据不能出现多条审核记录");
			CheckBillState cb = cbsList.get(0);
			//如果只有一个级别的审核
			if(set.size() == 1){
				String num = it.next();
				if(isCheck(num,cb))
					throw new SystemException("该审核已经完成");
				else{
					if(!"1".equals(num)){
						int _num = Integer.parseInt(num);
						if(!isCheck((_num-1)+"",cb))
							throw new SystemException((_num-1)+"级审核未完成");
					}
				}
				editCheckValue(num,cb,u.getUserRealName());
				return true;
			}
			//如果用户权限多级
			int i=0;
			while (it.hasNext()) {  
				String str = it.next(); 
				//判断是不是已经审核通过
				if(isCheck(str,cb)){
					i++;
					continue;
				}else{
					if(!"1".equals(str)){
						int _num = Integer.parseInt(str);
						if(!isCheck((_num-1)+"",cb))
							throw new SystemException((_num-1)+"级审核未完成");
					}
					editCheckValue(str,cb,u.getUserRealName());
					break;
				}
			}
			if(set.size() == i)
				throw new SystemException("当前用户单据审核已经完成");
			return true;
		}
	}
	
	/**
	 * 反审核功能
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Dec 7, 2014 12:00:38 PM <br/>
	 */
	public<ID extends Serializable> Boolean cancleCheck(ID PK,String className)throws Exception{
//		Check c = checkService.selectBySinglet("objName", className);
//		//获得当前登录用户
//		Users u= SysContainer.get();
//		//获得用户对于该单据的审核权限
		SqlMap<String,String,String> sqlMap = new SqlMap<String,String,String>();
//		TreeSet<String> set = getUserAuth(PK,className,c,u,sqlMap);

//		Iterator<String> it = set.iterator();
		//获得当前的单据审核状况
		sqlMap.put("billId", "=", PK.toString());
		sqlMap.put("billType", "=", className);
		List<CheckBillState> cbsList = super.selectHQL(sqlMap);
		sqlMap.clear();
		if(cbsList == null || cbsList.size()==0){
			throw new SystemException("该单据的审核信息不存在");
		}else{
			if(cbsList.size()>1)
				throw new SystemException("同一单据不能出现多条审核记录");
			CheckBillState cb = cbsList.get(0);
			//如果只有一个级别的审核
//			if(set.size() == 1){
//				String num = it.next();
//				//非八级的情况下，需要验证上一级的审核状况
//				if(!"8".equals(num)){
//					int _num = Integer.parseInt(num);
//					if(isCheck((_num+1)+"",cb))
//						throw new SystemException("上一级已经审核，不能进行反审核");
//					//进行反审核
//				}
//				setCencelCheckValue(num,cb);
//				return true;
//			}
//			//多个级别
//			List<String> list = new ArrayList<String>(set);
//			String str = list.get(list.size()-1);
//			if(!"8".equals(str)){
//				int _num = Integer.parseInt(str);
//				if(isCheck((_num+1)+"",cb))
//					throw new SystemException("上一级已经审核，不能进行反审核");
//			}
			//进行反审核
			cencelCheckValue("8",cb);
			return true;
		}
	}
	
	//验证单据是不是配置审核，验证当前用户有没有权限，返回当前用户的权限集合
	private <ID extends Serializable> TreeSet<String> getUserAuth(ID PK,String className,Check c,Users u,SqlMap<String,String,String> sqlMap)throws Exception{
		//获得class对象，并判断该对象是不是存在审核
		if(className==null || "".equals(className))
			throw new SystemException("请在页面中配置className属性");
		if(c == null)
			throw new SystemException("请先对业务对象进行审批配置");
		//获得当前用户对于该单据的审核权限
		TreeSet<String> set = getAllAuth(u, c,className,sqlMap);
		if(set==null || set.size()==0)
			throw new SystemException("当前用户没有对该单据的审核权限");
		return set;
	}
	
	
	//获得用户对该单据的所有审批权限并去除重复，排序
	private TreeSet<String> getAllAuth(Users u, Check c, String className,SqlMap<String,String,String> sqlMap)throws Exception{
	
		//获得角色集
		u = usersService.selectById(u.getUserCode());
		// 若用户是超级管理员，则拥有所有审批权限
		Character superMark = u.getSuperMark();
		StringBuffer sb = new StringBuffer();
		if (null != superMark && superMark.charValue() == 'Y') {
			String checkLevel = c.getObjLevel();
			int cl = Integer.parseInt(checkLevel);
			for (int i = 1; i <= cl; i ++) {
				sb.append(i + ",");
			}
		}else {
			List<Role> rList = u.getRoleSet();
			if(rList == null || rList.size()==0)
				return null;
			for(Role r : rList){
				//根据角色与类名获得用户对于该业务对象的审核权限
				sqlMap.put("className", "=", className);
				sqlMap.put("role.roleCode", "=", r.getRoleCode());
				List<CheckRole> crList = checkRoleService.selectHQL(sqlMap);
				sqlMap.clear();
				if(crList == null ||crList.size()==0)
					continue;
				if(crList.size() >1)
					throw new SystemException("角色【"+r.getRoleName()+"】对于单据【"+className+"】设置重复");
				CheckRole cr = crList.get(0);
				sb.append(cr.getCheckLevels()+",");
			}
		}
		if(sb.toString().length()==0)
			return null;
		
		String[] _arrayValue = sb.toString().split(",") ;
		return new TreeSet<String>(Arrays.asList(_arrayValue));
	}
	
	//判断是不是已经执行审核
	private boolean ischeck(String num,CheckBillState c)throws Exception{
		String methodName = "get";
		//将数字转换为英文
		String englishNum = PapUtil.toEnglish(Integer.parseInt(num));
		//将首字母大写
		String _englishNum = englishNum.replaceFirst(englishNum.substring(0, 1), englishNum.substring(0, 1).toUpperCase());
		//拼接方法名
		methodName = methodName+_englishNum+"Check";
//		if("1".equals(num))
//			methodName = methodName+"OneCheck";
//		if("2".equals(num))
//			methodName = methodName+"TwoCheck";
//		if("3".equals(num))
//			methodName = methodName+"ThreeCheck";
//		if("4".equals(num))
//			methodName = methodName+"FourCheck";
//		if("5".equals(num))
//			methodName = methodName+"FiveCheck";
//		if("6".equals(num))
//			methodName = methodName+"SixCheck";
//		if("7".equals(num))
//			methodName = methodName+"SevenCheck";
//		if("8".equals(num))
//			methodName = methodName+"EightCheck";
		//通过反射执行给方法
		Method getMethod = c.getClass().getMethod(methodName,new Class[] {});
		Object value = getMethod.invoke(c, new Object[] {});
		if(value != null)
			return true;
		else 
			return false;
	}
	
	//判断是不是已经执行审核
	private boolean isCheck(String num,CheckBillState c)throws Exception{
		Character cha = null;
		if("1".equals(num))
			cha = c.getOneCheck();
		if("2".equals(num))
			cha = c.getTwoCheck();
		if("3".equals(num))
			cha = c.getThreeCheck();
		if("4".equals(num))
			cha = c.getFourCheck();
		if("5".equals(num))
			cha = c.getFiveCheck();
		if("6".equals(num))
			cha = c.getSixCheck();
		if("7".equals(num))
			cha = c.getSevenCheck();
		if("8".equals(num))
			cha = c.getEightCheck();
		if(cha != null)
			return true;
		else 
			return false;
	}
	
	
	
	//set反射输入值
	@SuppressWarnings("unused")
	private void editValue(String num,CheckBillState c,String userName)throws Exception{
		//获得方法名称
		String set = "set";
		String methodName = "";
		String methodDate = "Date";
		String methodUser = "User";
		
		//将数字转换为英文
		String englishNum = PapUtil.toEnglish(Integer.parseInt(num));
		//将首字母大写
		String _englishNum = englishNum.replaceFirst(englishNum.substring(0, 1), englishNum.substring(0, 1).toUpperCase());
		//拼接方法名
		methodName = set+_englishNum+"Check";
	    methodDate = methodName+methodDate;
	    methodUser = methodName+methodUser;
		
		Class<?> clazz = c.getClass();
		clazz.getMethod(methodName, Character.class).invoke(c, 'Y');
		clazz.getMethod(methodDate, String.class).invoke(c, PapUtil.date(new Date()));
		clazz.getMethod(methodUser, String.class).invoke(c, userName);
	} 
	
	//设置值
	private void editCheckValue(String num,CheckBillState c,String userName)throws Exception{
		if("1".equals(num)){
			c.setOneCheck('Y');
			c.setOneCheckDate(PapUtil.date(new Date()));
			c.setOneCheckUser(userName);
		}
		if("2".equals(num)){
			c.setTwoCheck('Y');
			c.setTwoCheckDate(PapUtil.date(new Date()));
			c.setTwoCheckUser(userName);
		}
		if("3".equals(num)){
			c.setThreeCheck('Y');
			c.setThreeCheckDate(PapUtil.date(new Date()));
			c.setThreeCheckUser(userName);
		}
		if("4".equals(num)){
			c.setFourCheck('Y');
			c.setFourCheckDate(PapUtil.date(new Date()));
			c.setFourCheckUser(userName);
		}
		if("5".equals(num)){
			c.setFiveCheck('Y');
			c.setFiveCheckDate(PapUtil.date(new Date()));
			c.setFiveCheckUser(userName);
		}
		if("6".equals(num)){
			c.setSixCheck('Y');
			c.setSixCheckDate(PapUtil.date(new Date()));
			c.setSixCheckUser(userName);
		}
		if("7".equals(num)){
			c.setSevenCheck('Y');
			c.setSevenCheckDate(PapUtil.date(new Date()));
			c.setSevenCheckUser(userName);
		}
		if("8".equals(num)){
			c.setEightCheck('Y');
			c.setEightCheckDate(PapUtil.date(new Date()));
			c.setEightCheckUser(userName);
		}
	}
	
	//设置反审核值
	private void cencelCheckValue(String num,CheckBillState c)throws Exception{
		int level = Integer.parseInt(num);
		for(int i =1;i<level+1;i++){
			if(1==i){
				c.setOneCheck(null);
				c.setOneCheckDate(null);
				c.setOneCheckUser(null);
			}
			if(2==i){
				c.setTwoCheck(null);
				c.setTwoCheckDate(null);
				c.setTwoCheckUser(null);
			}
			if(3==i){
				c.setThreeCheck(null);
				c.setThreeCheckDate(null);
				c.setThreeCheckUser(null);
			}
			if(4==i){
				c.setFourCheck(null);
				c.setFourCheckDate(null);
				c.setFourCheckUser(null);
			}
			if(5==i){
				c.setFiveCheck(null);
				c.setFiveCheckDate(null);
				c.setFiveCheckUser(null);
			}
			if(6==i){
				c.setSixCheck(null);
				c.setSixCheckDate(null);
				c.setSixCheckUser(null);
			}
			if(7==i){
				c.setSevenCheck(null);
				c.setSevenCheckDate(null);
				c.setSevenCheckUser(null);
			}
			if(8==i){
				c.setEightCheck(null);
				c.setEightCheckDate(null);
				c.setEightCheckUser(null);
			}
		}
	} 
	
	/**
	 * 查询审核状况
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Dec 8, 2014 3:49:18 PM <br/>
	 */
	public <ID extends Serializable> CheckState selectShow(ID PK,String className,String level)throws Exception{
		SqlMap<String,String,String> sqlMap = new SqlMap<String,String,String>();
		sqlMap.put("billId", "=", PK.toString());
		sqlMap.put("billType", "=", className);
		List<CheckBillState> cbsList = super.selectHQL(sqlMap);
		sqlMap.clear();
		CheckState cs = new CheckState();
		if(cbsList != null && cbsList.size()>0){
			CheckBillState _c = cbsList.get(0);
			if("1".equals(level)){
				cs.setCheckState(_c.getOneCheck());
                cs.setCheckDate(_c.getOneCheckDate());
				cs.setUserName(_c.getOneCheckUser());
			}
			if("2".equals(level)){
				cs.setCheckState(_c.getTwoCheck());
                cs.setCheckDate(_c.getTwoCheckDate());
				cs.setUserName(_c.getTwoCheckUser());
			}
			if("3".equals(level)){
				cs.setCheckState(_c.getThreeCheck());
                cs.setCheckDate(_c.getThreeCheckDate());
				cs.setUserName(_c.getThreeCheckUser());
			}
			if("4".equals(level)){
				cs.setCheckState(_c.getFourCheck());
                cs.setCheckDate(_c.getFourCheckDate());
				cs.setUserName(_c.getFourCheckUser());
			}
			if("5".equals(level)){
				cs.setCheckState(_c.getFiveCheck());
                cs.setCheckDate(_c.getFiveCheckDate());
				cs.setUserName(_c.getFiveCheckUser());
			}
			if("6".equals(level)){
				cs.setCheckState(_c.getSixCheck());
                cs.setCheckDate(_c.getSixCheckDate());
				cs.setUserName(_c.getSixCheckUser());
			}
			if("7".equals(level)){
				cs.setCheckState(_c.getSevenCheck());
                cs.setCheckDate(_c.getSevenCheckDate());
				cs.setUserName(_c.getSevenCheckUser());
			}
			if("8".equals(level)){
				cs.setCheckState(_c.getEightCheck());
                cs.setCheckDate(_c.getEightCheckDate());
				cs.setUserName(_c.getEightCheckUser());
			}
			cs.setLevel(level);
		}
		return cs;
			
	}
	
	//获得指定单据的所有审核状态
	public List<CheckState> getCheckedState(CheckBillState cs,String num)throws Exception{
		
		List<CheckState> cstate = new ArrayList<CheckState>();
		if(num != null && !"".equals(num)){
			int _num = Integer.parseInt(num);
			for(int i=1;i<_num+1;i++){
				CheckState _cs = new CheckState();
				if(i==1){
					_cs.setCheckState(cs.getOneCheck());
				}
				if(i==2){
					_cs.setCheckState(cs.getTwoCheck());
				}
				if(i==3){
					_cs.setCheckState(cs.getThreeCheck());
				}
				if(i==4){
					_cs.setCheckState(cs.getFourCheck());
				}
				if(i==5){
					_cs.setCheckState(cs.getFiveCheck());
				}
				if(i==6){
					_cs.setCheckState(cs.getSixCheck());
				}
				if(i==7){
					_cs.setCheckState(cs.getSevenCheck());
				}
				if(i==8){
					_cs.setCheckState(cs.getEightCheck());
				}
				_cs.setLevel(i+"");
				cstate.add(_cs);
			}
		}
		return cstate;
	}
	
	//单据没有审核状态的情况展现
	public List<CheckState> getUnCheckState(String num)throws Exception{
		
		List<CheckState> cstate = new ArrayList<CheckState>();
		if(num != null && !"".equals(num)){
			int _num = Integer.parseInt(num);
			for(int i=1;i<_num+1;i++){
				CheckState _cs = new CheckState();
				_cs.setCheckState(null);
				_cs.setLevel(i+"");
				cstate.add(_cs);
			}
		}
		return cstate;
	}
	
	/**
	 * 判断单据是不是已经审核完成
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version 2015-3-3 下午05:30:22 <br/>
	 */
	public <ID extends Serializable> Boolean isCheckOk(ID PK,String className)throws Exception{
		//判断该对象有多少级审核
		Check c = checkService.selectBySinglet("objName", className);
		if(c == null)
			throw new SystemException("该单据没有配置审核流程");
		//获得当前的单据审核状况
		SqlMap<String,String,String> sqlMap = new SqlMap<String,String,String>();
		sqlMap.put("billId", "=", PK.toString());
		sqlMap.put("billType", "=", className);
		List<CheckBillState> cbsList = super.selectHQL(sqlMap);
		sqlMap.clear();
		if(cbsList == null || cbsList.size()==0){
			throw new SystemException("该单据未审核完成！");
		}else{
			if(cbsList.size()>1)
				throw new SystemException("同一单据不能出现多条审核记录");
			return ischeck(c.getObjLevel(),cbsList.get(0));
		}
	}
	
	public static void main(String[] args) throws Exception{
		CheckBillStateServiceImpl csi = new CheckBillStateServiceImpl();
		Check c = new Check();
		//c.setOneCheck('Y');
		//System.out.println(csi.ischeck("1",c));
		//csi.setValue("2",c,"zhangsan");
	}
}
