/**
 * 文件名：@BussObjAuthPropertyDaoImpl.java <br/>
 * 包名：com.zhongpin.pap.permission.dao.impl <br/>
 * 项目名：PAP1.0 <br/>
 * @author 孟雪勤 <br/>
 */
package com.cp.epa.permission.dao.impl;

import java.util.List;

import com.cp.epa.base.BaseDaoImpl;
import com.cp.epa.permission.dao.IBussObjAuthPropertyDao;
import com.cp.epa.permission.entity.BussObjAuthProperty;

/**
 * 类名：BussObjAuthPropertyDaoImpl  <br />
 *
 * 功能：业务对象过滤属性持久层实现
 *
 * @author 孟雪勤 <br />
 * 创建时间：2013-11-6 下午02:33:54  <br />
 * @version 2013-11-6
 */
public class BussObjAuthPropertyDaoImpl extends BaseDaoImpl<BussObjAuthProperty> implements IBussObjAuthPropertyDao {

	/**
	 * 功能：单一条件查询<br/>
	 *
	 * @author 孟雪勤
	 * @version 2013-11-27 上午09:20:43 <br/>
	 */
	@SuppressWarnings("unchecked")
	public List<BussObjAuthProperty> selectByUCondition(String property, Object value) throws Exception {
		String hql = "from BussObjAuthProperty e where 1 = 1 " ;
		if (null != property && ! "".equals(property)) {
			hql += "and " + property + " = ?";
		}
		return getSession().createQuery(hql).setParameter(0, value).list();
	}
	
}
