/**
 * 文件名：@BussObjAuthPropertyServiceImpl.java <br/>
 * 包名：com.zhongpin.pap.permission.services.impl <br/>
 * 项目名：PAP1.0 <br/>
 * @author 孟雪勤 <br/>
 */
package com.cp.epa.permission.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.Table;

import com.cp.epa.base.BaseServiceImpl;
import com.cp.epa.bussobj.entity.BaseTableColumn;
import com.cp.epa.bussobj.entity.BussObjProperty;
import com.cp.epa.bussobj.entity.BussinessEle;
import com.cp.epa.bussobj.services.IBaseTableColumnService;
import com.cp.epa.bussobj.services.IBussObjPropertyService;
import com.cp.epa.permission.dao.IBussObjAuthPropertyDao;
import com.cp.epa.permission.entity.BussObjAuthProperty;
import com.cp.epa.permission.services.IBussObjAuthPropertyService;
import com.cp.epa.utils.SqlMap;


/**
 * 类名：BussObjAuthPropertyServiceImpl  <br />
 *
 * 功能：业务对象过滤属性业务逻辑层实现
 *
 * @author 孟雪勤 <br />
 * 创建时间：2013-11-6 下午02:37:25  <br />
 * @version 2013-11-6
 */
public class BussObjAuthPropertyServiceImpl extends BaseServiceImpl<BussObjAuthProperty, IBussObjAuthPropertyDao> implements IBussObjAuthPropertyService {

	/** 业务对象属性业务注册 */
	@Resource
	private IBussObjPropertyService bussObjPropertyService;
	
	/** 表列业务注册 */
	@Resource
	private IBaseTableColumnService baseTableColumnService;
	/**
	 * 功能: 根据条件查询<br/>
	 * 
	 * 重写：孟雪勤 <br/>
	 * 
	 * @version ：2013-11-14 下午05:18:42<br/>
	 * 
	 * @param entity
	 * @return
	 * @throws Exception <br/>
	 * @see com.zhongpin.pap.base.BaseServiceImpl#selectAll(com.zhongpin.pap.base.BaseEntity)
	 */
	@Override
	public List<BussObjAuthProperty> selectAll(BussObjAuthProperty entity) throws Exception {
		
		//List<BussObjAuthProperty> properties = dao.selectAll(entity);
		List<BussObjAuthProperty> properties = super.selectBySingletAll("bussObj.bussCode", entity.getBussObj().getBussCode());
		
		if (null != properties && properties.size() > 0) {
			for (BussObjAuthProperty property : properties) {
				
				BussObjProperty objProperty = property.getBussObjProperty();
				
				// 查询属性所属业务对象
				BussObjProperty bussObjProperty = bussObjPropertyService.selectById(objProperty.getPid());
				// 所属业务对象类全名
				String fullClassName = bussObjProperty.getClassName();

				// 反射获得业务对象
				Class<?> bussObj = Class.forName(fullClassName);
				if (null != bussObj) {
					// 表名默认为业务对象实体名
					String tableName = bussObj.getSimpleName().toLowerCase();
					Table tableAnnotation = bussObj.getAnnotation(Table.class);
					if (null != tableAnnotation) {
						String tabName = tableAnnotation.name();
						if (null != tabName && ! "".equals(tabName)) {
							tableName = tabName.toLowerCase();
						}
					}
					
					// 查询具体的表列信息
					SqlMap<String, String, String> sqlMap = new SqlMap<String, String, String>();
					sqlMap.put("baseTable.tabCode", "=", tableName); // 表名
					sqlMap.put("fdcode", "=", objProperty.getFieldName());// 字段名
					List<BaseTableColumn> columns = baseTableColumnService.selectHQL(sqlMap);
					if (null != columns && columns.size() > 0) {
						BaseTableColumn column = columns.get(0);
						
						// 设置业务对象属性信息
						BussinessEle ele = column.getBussinessEle();
						if (null != ele) {
							objProperty.setBussEleCode(ele.getEcode()); // 业务元素编码
						}
						objProperty.setFieldtype(column.getDataType());// 字段类型
						Integer len = column.getLen();
						if (null != len) {
							objProperty.setFieldLen(len.toString());// 字段长度
						}
						
						objProperty.setIsPk(column.getIsPk().toString()); // 主键
					}
					
				}
				
				// 设置绑定状态
				Map<String, String> tempStack = property.getTempStack();
				if (null == tempStack)
					property.setTempStack(tempStack = new HashMap<String, String>());
				//if (null != property.getAuthObj() || null != property.getAuthField()) {
				//	tempStack.put("bind", "true");
				//}
			}
		}
		
		return properties;
	}
	
	/**
	 * 功能: 启用<br/>
	 * 
	 * 重写：孟雪勤 <br/>
	 * 
	 * @version ：2013-11-18 下午03:53:38<br/>
	 * 
	 * @param entity
	 * @throws Exception <br/>
	 * @see com.zhongpin.pap.permission.services.IBussObjAuthPropertyService#disable(com.zhongpin.pap.permission.entity.BussObjAuthProperty)
	 */
	public void disable(Integer[] ids) throws Exception {

		// 更新参数
		Map<String, Object> map = new HashMap<String, Object>();
		// 记忆标识
		map.put("enabled", 0);
		
		for (int id : ids) {
			dao.updateById(id, map);
		}
	}
	
	/**
	 * 功能: 禁用、停用<br/>
	 * 
	 * 重写：孟雪勤 <br/>
	 * 
	 * @version ：2013-11-18 下午03:53:42<br/>
	 * 
	 * @param entity
	 * @throws Exception <br/>
	 * @see com.zhongpin.pap.permission.services.IBussObjAuthPropertyService#enable(com.zhongpin.pap.permission.entity.BussObjAuthProperty)
	 */
	public void enable(Integer[] ids) throws Exception {
		
		// 更新参数
		Map<String, Object> map = new HashMap<String, Object>();
		// 启用标识
		map.put("enabled", 1);
		
		for (int id : ids) {
			dao.updateById(id, map);
		}
	}
	
	/**
	 * 功能: 根据业务对象编码查询权限过滤属性信息<br/>
	 * 
	 * 重写：孟雪勤 <br/>
	 * 
	 * @version ：2013-11-24 下午02:53:26<br/>
	 * 
	 * @param bussObjCode
	 * @return
	 * @throws Exception <br/>
	 * @see com.zhongpin.pap.permission.services.IBussObjAuthPropertyService#selectByBussObj(java.lang.String)
	 */
	public List<BussObjAuthProperty> selectByBussObj(String bussObjCode) throws Exception {
		return dao.selectByUCondition("e.bussObj.bussCode", bussObjCode);
	}

	/**
	 * 功能: 根据业务对象编码和属性编号查询权限过滤属性信息<br/>
	 * 
	 * 重写：孟雪勤 <br/>
	 * 
	 * @version ：2013-12-5 上午10:53:24<br/>
	 * 
	 * @param bussObjCode
	 * @param propertyId
	 * @return
	 * @throws Exception <br/>
	 * @see com.zhongpin.pap.permission.services.IBussObjAuthPropertyService#selectByBussObj(java.lang.String, java.lang.String)
	 */
	public BussObjAuthProperty selectByBussObj(String bussObjCode, int propertyId) throws Exception {
		SqlMap<String, String, Object> sqlMap = new SqlMap<String, String, Object>();
		sqlMap.put("bussObj.bussCode", "=", bussObjCode);
		sqlMap.put("bussObjProperty.id", "=", propertyId);
		
		List<BussObjAuthProperty> list = dao.selectByConditionHQL(sqlMap);
		if (null != list && list.size() > 0) {
			return list.get(0);
		}
		
		return null;
	}
}
