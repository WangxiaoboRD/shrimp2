/**
 * 文件名：@RoleAuthValueServiceImpl.java <br/>
 * 包名：com.zhongpin.pap.permission.services.impl <br/>
 * 项目名：PAP1.0 <br/>
 * @author 孟雪勤 <br/>
 */
package com.cp.epa.permission.services.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.cp.epa.base.BaseServiceImpl;
import com.cp.epa.bussobj.entity.BussinessEle;
import com.cp.epa.permission.dao.IRoleAuthValueDao;
import com.cp.epa.permission.entity.AuthField;
import com.cp.epa.permission.entity.RoleAuthValue;
import com.cp.epa.permission.entity.RoleAuthValueDetail;
import com.cp.epa.permission.entity.UserAuthValue;
import com.cp.epa.permission.services.IRoleAuthValueDetailService;
import com.cp.epa.permission.services.IRoleAuthValueService;
import com.cp.epa.permission.services.IUserAuthValueService;
import com.cp.epa.permission.utils.PmConstant;
import com.cp.epa.permission.utils.PmUtil;
import com.cp.epa.permission.utils.enums.AllotStatus;
import com.cp.epa.permission.utils.enums.AuthType;
import com.cp.epa.utils.SqlMap;

/**
 * 类名：RoleAuthValueServiceImpl <br />
 * 
 * 功能：角色权限规则值业务逻辑层实现
 * 
 * @author 孟雪勤 <br />
 *         创建时间：2013-11-6 下午02:45:53 <br />
 * @version 2013-11-6
 */
public class RoleAuthValueServiceImpl extends BaseServiceImpl<RoleAuthValue, IRoleAuthValueDao> implements IRoleAuthValueService {

	/** 角色权限规则明细业务注册 */
	@Resource
	private IRoleAuthValueDetailService detailService;
	/** 用户权限规则业务注册 */
	@Resource
	private IUserAuthValueService userAuthValueService;
	
	/**
	 * 功能: 根据条件[角色、权限对象]查询角色规则信息<br/>
	 * 
	 * 重写：孟雪勤 <br/>
	 * 
	 * @version ：2013-11-11 下午02:27:34<br/>
	 * 
	 * @param entity
	 * @return
	 * @throws Exception
	 * <br/>
	 * @see com.zhongpin.pap.permission.services.IRoleAuthValueService#selectByRoleAuthObj(com.zhongpin.pap.permission.entity.RoleAuthValue)
	 */
	public List<RoleAuthValue> selectByRoleAuthObj(RoleAuthValue entity) throws Exception {

		SqlMap<String, String, Object> sqlMap = new SqlMap<String, String, Object>();
		sqlMap.put("role.roleCode", "=", entity.getRole().getRoleCode());
		Map<String, String> tempStack = entity.getTempStack();
		if (null != tempStack && !"".equals(tempStack.get("authObjCodes"))) {
			sqlMap.put("authObj.code", "in", PmUtil.strToSQLStr(tempStack.get("authObjCodes")));
		}

		List<RoleAuthValue> ravs = dao.selectByConditionHQL(sqlMap);
		if (null != ravs && ravs.size() > 0) {
			for (RoleAuthValue rav : ravs) {
				String allotStatus = AllotStatus.ALLOTED.getValue();
				if (null == rav.getValue() || "".equals(rav.getValue())) {
					allotStatus = AllotStatus.UNALLOT.getValue();
				}
				rav.setAllotStatus(allotStatus);
			}
		}

		return ravs;
	}

	/**
	 * 功能: 分配权限<br/>
	 * 
	 * 重写：孟雪勤 <br/>
	 * 
	 * @version ：2013-11-16 下午04:04:51<br/>
	 * 
	 * @param entity
	 * @throws Exception <br/>
	 * @see com.zhongpin.pap.permission.services.IRoleAuthValueService#allotAuth(com.zhongpin.pap.permission.entity.RoleAuthValue)
	 */
	public void allotAuth(RoleAuthValue entity) throws Exception {

		RoleAuthValue rav = dao.selectById(entity.getId());
		AuthField authField = rav.getAuthField();
		BussinessEle ele = authField.getBussinessEle();
		// 业务元素数据类型
		String dataType = ele.getDataType();
		
		// 角色权限规则明细删除
		Map<String, String> tempStack = entity.getTempStack();
		if (null != tempStack && ! "".equals(tempStack.get("delIds"))) {
			for (String delId : tempStack.get("delIds").split(",")) {
				detailService.deleteById(Integer.parseInt(delId));
			}
		}
		
		// 若业务元素的值类型为2=参考表，3=固定值
		// 业务元素值类型
		String valueType = ele.getValueType();
		if ("2".equals(valueType) || "3".equals(valueType)) {
			String value = entity.getValue();
			
			// 设置规则明细信息
			List<RoleAuthValueDetail> details = rav.getDetails();
			RoleAuthValueDetail detail = null;
			if (null != details && details.size() > 0) {
				detail = details.get(0);
				detail.setValue(value);// 值
			}else {
				detail = new RoleAuthValueDetail();
				detail.setRoleAuthValue(rav);// 角色权限规则
				detail.setConnector("and");// 连接符
				detail.setOperator("in"); // 操作符
				detail.setValue(value);// 值
			}
			rav.setDetails(Arrays.asList(detail)); // 设置明细值
			
			String rule = "";
			if (null != value && ! "".equals(value)) {
				// 树状
				if (authField.getTree() == 1) {
					// 树状格式：1:Y,2:X,3:Y   X=半选中状态，Y=全选中状态
					StringBuilder valueSb = new StringBuilder();
					String[] values = value.split(",");
					for (String v : values) {
						valueSb.append(v.split(":")[0]).append(",");
					}
					value = valueSb.toString();
					
					// 若是功能权限则以" ，"都会分隔
					if (authField.getType().equals(AuthType.FUNCTION.toString())) {
						rule = value;
					}else {
						value = value.substring(0, value.length() - 1);
						// 若权限字段类型为varText字符串
						if (dataType.equals("varText")) {
							value = PmUtil.strToSQLStr(value);
						}
						rule = PmConstant.authReplaceMark + " in (" + value + ")";
					}
				}else {
					// 若是功能权限则以" ，"都会分隔
					if (authField.getType().equals(AuthType.FUNCTION.toString())) {
						rule = value;
					}else {
						value = value.substring(0, value.length() - 1);
						// 若权限字段类型为varText字符串
						if (dataType.equals("varText")) {
							value = PmUtil.strToSQLStr(value);
						}
						rule = PmConstant.authReplaceMark + " in (" + value + ")";
					}
				}
			}
			rav.setValue(rule);// 设置抬头规则值
		}else {
			// 值类型 0=无，1=范围段
			StringBuilder sbValue = new StringBuilder();
			List<RoleAuthValueDetail> details = entity.getDetails();
			if (null != details && details.size() > 0) {
				int flag = 1;// 标记
				for (RoleAuthValueDetail detail : details) {
					detail.setRoleAuthValue(entity);// 角色权限规则
					if (flag != 1) {
						sbValue.append(detail.getConnector()).append(" ");
					}
					sbValue.append(PmConstant.authReplaceMark).append(detail.getOperator());
					if (dataType.equals("varText")) {
						sbValue.append(" '").append(detail.getValue()).append("' ");
					}else {
						sbValue.append(" ").append(detail.getValue()).append(" ");
					}
					
					flag ++;
				}
			}
			rav.setDetails(details); // 设置明细值
			rav.setValue(sbValue.toString());// 设置抬头规则值
		}

		// 更新用户权限规则值信息
		updateUserAuthValue(rav, rav.getValue());
		
		dao.update(rav);
	}
	
	
	
	/**
	 * 功能: 清空已分配的权限<br/>
	 * 
	 * 重写：孟雪勤 <br/>
	 * 
	 * @version ：2013-11-16 下午04:05:45<br/>
	 * 
	 * @param entity
	 * @throws Exception <br/>
	 * @see com.zhongpin.pap.permission.services.IRoleAuthValueService#cleanAuth(com.zhongpin.pap.permission.entity.RoleAuthValue)
	 */
	public void cancelAuth(RoleAuthValue entity) throws Exception {
		// 删除角色权限规则明细记录
		// 删除明细
		SqlMap<String, String, Object> sqlMap = new SqlMap<String, String, Object>();
		sqlMap.put("roleAuthValue.id", "=", entity.getId());
		detailService.delete(sqlMap);
		
		// 清空角色权限规则value值
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("value", "");
		dao.updateById(entity.getId(), map);
		
		RoleAuthValue rav = dao.selectById(entity.getId());
		// 清空用户权限规则值信息
		updateUserAuthValue(rav, "");
		
	}

	/**
	 * 功能：更新用户权限规则值<br/>
	 *
	 * @author 孟雪勤
	 * @version 2013-11-19 下午04:24:41 <br/>
	 */
	private void updateUserAuthValue(RoleAuthValue rav, String value) throws Exception {
		SqlMap<String, String, String> sqlMap = new SqlMap<String, String, String>();
		// 角色编码
		sqlMap.put("roleCode", "=", rav.getRole().getRoleCode());
		// 权限对象编码
		sqlMap.put("authObjCode", "=", rav.getAuthObj().getCode());
		// 权限字段编码
		sqlMap.put("authFieldCode", "=", rav.getAuthField().getCode());
		
		// 用户权限规则信息更新参数
		List<UserAuthValue> values = userAuthValueService.selectHQL(sqlMap);
		if (null != values && values.size() > 0) {
			for (UserAuthValue uav : values) {
				uav.setValue(value);
				userAuthValueService.update(uav);
			}
		}
	}
	
}
