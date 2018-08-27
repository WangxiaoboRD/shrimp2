/**
 * 文件名：@IBussObjAuthPropertyDao.java <br/>
 * 包名：com.zhongpin.pap.permission.dao <br/>
 * 项目名：PAP1.0 <br/>
 * @author 孟雪勤 <br/>
 */
package com.cp.epa.permission.dao;

import java.util.List;

import com.cp.epa.base.IBaseDao;
import com.cp.epa.permission.entity.BussObjAuthProperty;

/**
 * 类名：IBussObjAuthPropertyDao  <br />
 *
 * 功能：业务对象权限过滤属性持久层定义
 *
 * @author 孟雪勤 <br />
 * 创建时间：2013-11-6 下午02:32:40  <br />
 * @version 2013-11-6
 */
public interface IBussObjAuthPropertyDao extends IBaseDao<BussObjAuthProperty> {

	/**
	 * 功能：单一条件查询<br/>
	 *
	 * @author 孟雪勤
	 * @version 2013-11-27 上午09:25:58 <br/>
	 */
	public List<BussObjAuthProperty> selectByUCondition(String property, Object value) throws Exception;
}
