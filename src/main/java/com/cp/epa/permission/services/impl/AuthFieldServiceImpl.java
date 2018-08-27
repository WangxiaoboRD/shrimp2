/**
 * 文件名：@AuthFieldServiceImpl.java <br/>
 * 包名：com.zhongpin.pap.permission.services.impl <br/>
 * 项目名：PAP1.0 <br/>
 * @author 孟雪勤 <br/>
 */
package com.cp.epa.permission.services.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.cp.epa.base.BaseServiceImpl;
import com.cp.epa.bussobj.entity.BussinessEle;
import com.cp.epa.exception.SystemException;
import com.cp.epa.permission.dao.IAuthFieldDao;
import com.cp.epa.permission.entity.AuthField;
import com.cp.epa.permission.entity.AuthObj;
import com.cp.epa.permission.entity.BussObjAuthProperty;
import com.cp.epa.permission.entity.RoleAuthValue;
import com.cp.epa.permission.services.IAuthFieldService;
import com.cp.epa.permission.services.IAuthObjService;
import com.cp.epa.permission.services.IBussObjAuthPropertyService;
import com.cp.epa.permission.services.IRoleAuthValueService;
import com.cp.epa.permission.utils.PmUtil;
import com.cp.epa.utils.PapUtil;
import com.cp.epa.utils.SqlMap;



/**
 * 类名：AuthFieldServiceImpl  <br />
 *
 * 功能：权限字段业务逻辑层实现
 *
 * @author 孟雪勤 <br />
 * 创建时间：2013-11-6 下午02:08:33  <br />
 * @version 2013-11-6
 */
public class AuthFieldServiceImpl extends BaseServiceImpl<AuthField, IAuthFieldDao> implements IAuthFieldService {

	/** 权限对象业务注册 */
	@Resource
	private IAuthObjService authObjService;
	
	/** 权限过滤属性业务注册 */
	@Resource
	private IBussObjAuthPropertyService authPropertyService;
	/** 角色权限规则业务注册 */
	@Resource
	private IRoleAuthValueService roleAuthValueService;
	
	/**
	 * 功能: 新增<br/>
	 * 
	 * 重写：孟雪勤 <br/>
	 * 
	 * @version ：2013-11-19 上午11:00:21<br/>
	 * 
	 * @param entity
	 * @return
	 * @throws Exception <br/>
	 * @see com.zhongpin.pap.base.BaseServiceImpl#save(com.zhongpin.pap.base.BaseEntity)
	 */
	@Override
	public Object save(AuthField entity) throws Exception {
		
		// 编码不允许为空
		String code = entity.getCode();
		if (null == PmUtil.emptyToNull(code)) {
			throw new SystemException("权限字段[编码]不允许为空！");
		}
		
		// 名称不允许为空
		if (null == PmUtil.emptyToNull(entity.getName())) {
			throw new SystemException("权限字段[名称]不允许为空！");
		}
		
		// 类型不允许为空
		if (null == PmUtil.emptyToNull(entity.getType())) {
			throw new SystemException("权限字段[类型]不允许为空！");
		}
		
		// 业务元素不允许为空
		BussinessEle bussinessEle = entity.getBussinessEle();
		if (null == bussinessEle) {
			throw new SystemException("权限字段[业务元素]不允许为空！");
		}
		
		// 编码不允许重复
		AuthField field = dao.selectBySinglet("code", code);
		if (null != field) {
			throw new SystemException("权限字段编码[" + code + "]存在冲突！");
		}
		
		return dao.insert(entity);
	}

	/**
	 * 功能: 编辑<br/>
	 * 
	 * 重写：孟雪勤 <br/>
	 * 
	 * @version ：2013-11-21 上午10:23:33<br/>
	 * 
	 * @param entity
	 * @throws Exception <br/>
	 * @see com.zhongpin.pap.base.BaseServiceImpl#update(com.zhongpin.pap.base.BaseEntity)
	 */
	@Override
	public void update(AuthField entity) throws Exception {
		
		// 名称不允许为空
		if (null == PmUtil.emptyToNull(entity.getName())) {
			throw new SystemException("权限字段[名称]不允许为空！");
		}
		
		// 类型不允许为空
		if (null == PmUtil.emptyToNull(entity.getType())) {
			throw new SystemException("权限字段[类型]不允许为空！");
		}
		
		// 业务元素不允许为空
		BussinessEle bussinessEle = entity.getBussinessEle();
		if (null == bussinessEle) {
			throw new SystemException("权限字段[业务元素]不允许为空！");
		}
		
		// 若该权限字段与业务对象权限过滤属性绑定了则不允许进行编辑
		SqlMap<String, String, String> sqlMap = new SqlMap<String, String, String>();
		sqlMap.put("authField.code", "=", entity.getCode());
		List<BussObjAuthProperty> authProperties = authPropertyService.selectHQL(sqlMap);
		if (null != authProperties && authProperties.size() > 0) {
			throw new SystemException("此权限字段已与业务对象权限过滤属性绑定，不允许编辑！");
		}
		
		// 若权限字段已被角色权限规则引用则不允许编辑
		List<RoleAuthValue> authValues = roleAuthValueService.selectHQL(sqlMap);
		if (null != authValues && authValues.size() > 0) {
			throw new SystemException("此权限字段已参与角色授权，不允许编辑！");
		}
		
		AuthField field = dao.selectById(entity.getCode());
		field.setName(entity.getName());
		field.setType(entity.getType());
		field.setBussinessEle(entity.getBussinessEle());
		field.setTree(entity.getTree());
		
		dao.update(field);
		
	}

	/**
	 * 功能: 根据id集合删除<br/>
	 * 
	 * 重写：孟雪勤 <br/>
	 * 
	 * @version ：2013-11-19 上午11:30:30<br/>
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
		// 若已被权限对象引用，则不允许删除
		List<String> quoteCodes = new ArrayList<String>();
		List<AuthObj> authObjs = authObjService.selectAll();
		if (null != authObjs && authObjs.size() > 0) {
			for (AuthObj authObj : authObjs) {
				List<AuthField> fields = authObj.getFieldSet();
				if (null != fields && fields.size() > 0) {
					for (AuthField field : fields) {
						String code = field.getCode();
						if (codes.contains(code)) {
							quoteCodes.add(code);// 添加已被引用的权限字段编码
						}
					}
				}
			}
		}
		
		if (quoteCodes.size() > 0) {
			throw new SystemException("所删除的权限字段已被权限对象引用，不允许删除！");
		}
		
		// 若该权限字段与业务对象权限过滤属性绑定了则不允许进行编辑
		SqlMap<String, String, String> sqlMap = new SqlMap<String, String, String>();
		sqlMap.put("authField.code", "in", PmUtil.arrayToSQLStr((String[])PK));
		List<BussObjAuthProperty> authProperties = authPropertyService.selectHQL(sqlMap);
		if (null != authProperties && authProperties.size() > 0) {
			throw new SystemException("所删除的权限字段已与业务对象权限过滤属性绑定，不允许删除！");
		}
		
		return dao.deleteByIds(PK);
	}
	
}
