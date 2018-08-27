package com.cp.epa.number.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.cp.epa.base.BaseDaoImpl;
import com.cp.epa.bussobj.entity.BussinessObject;
import com.cp.epa.number.dao.IVacantNumberDao;
import com.cp.epa.number.entity.VacantNumber;



/**
 * 类名：VacantNumberDaoImpl  <br />
 *
 * 功能：空置号码业务逻辑层实现
 *
 * @author 孟雪勤 <br />
 * 创建时间：2015-10-7 下午02:52:52  <br />
 * @version 2015-10-7
 */
public class VacantNumberDaoImpl extends BaseDaoImpl<VacantNumber> implements IVacantNumberDao {

	/**
	 * 功能: 查询空号中的所有业务对象<br/>
	 * 
	 * 重写：孟雪勤 <br/>
	 * 
	 * @version ：2016-1-4 下午01:24:04<br/>
	 * 
	 * @return
	 * @throws Exception <br/>
	 * @see com.zhongpin.pap.number.dao.IVacantNumberDao#selectBussObj()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BussinessObject> selectBussObj() throws Exception {
		String hql = "select e.bussObj, e.bussName from VacantNumber e group by e.bussObj, e.bussName";
		List<Object[]> objs = (List<Object[]>) hibernateTemplate.find(hql);
		List<BussinessObject> list = new ArrayList<BussinessObject>();
		if (null != objs && objs.size() > 0) {
			for (Object[] obj : objs) {
				BussinessObject bussObj = new BussinessObject();
				bussObj.setBussCode(String.valueOf(obj[0]));
				bussObj.setBussName(String.valueOf(obj[1]));
				list.add(bussObj);
			}
		}
		return list;
	}
}
