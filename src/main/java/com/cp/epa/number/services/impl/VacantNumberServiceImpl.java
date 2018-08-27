/**
 * 文件名：@VacantNumberServiceImpl.java <br/>
 * 包名：com.zhongpin.pap.number.services.impl <br/>
 * 项目名：ps <br/>
 * @author 孟雪勤 <br/>
 */
package com.cp.epa.number.services.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.cp.epa.base.BaseServiceImpl;
import com.cp.epa.bussobj.entity.BussinessObject;
import com.cp.epa.number.dao.IVacantNumberDao;
import com.cp.epa.number.entity.VacantManage;
import com.cp.epa.number.entity.VacantNumber;
import com.cp.epa.number.entity.VacantRule;
import com.cp.epa.number.services.INumberService;
import com.cp.epa.number.services.IVacantManageService;
import com.cp.epa.number.services.IVacantNumberService;
import com.cp.epa.utils.Pager;
import com.cp.epa.utils.PapUtil;
import com.cp.epa.utils.SqlMap;

/**
 * 类名：VacantNumberServiceImpl  <br />
 *
 * 功能：
 *
 * @author 孟雪勤 <br />
 * 创建时间：2015-10-7 下午02:54:51  <br />
 * @version 2015-10-7
 */
public class VacantNumberServiceImpl extends BaseServiceImpl<VacantNumber, IVacantNumberDao> implements IVacantNumberService {

	/** 号码业务注册 */
	@Resource
	private INumberService numberService;
	/** 号码管理业务注册 */
	@Resource
	private IVacantManageService vacantManageService;
	
	/**
	 * 功能: 创建空号<br/>
	 * 
	 * 重写：孟雪勤 <br/>
	 * 
	 * @version ：2015-12-15 上午10:03:22<br/>
	 * 
	 * @param objCode
	 * @throws Exception <br/>
	 * @see com.zhongpin.pap.number.services.IVacantNumberService#createNumber(java.lang.String)
	 */
	@Override
	public void createNumber(String objCode) throws Exception {
		// 判断生成空号数量是否等于设定数量，等于的话则停止调度
		String nowDate = PapUtil.notFullDate(new Date());
		boolean isOver = false;
		VacantManage manage = vacantManageService.selectBySinglet("bussObj", objCode);
		if (manage != null) {
			VacantRule rule = manage.getVacantRule();
			Character runStatus = manage.getRunStatus();
			if (null == runStatus || (null != runStatus && runStatus.charValue() == 'N')) {
				String dateTime = PapUtil.date(new Date());
				String[] dateTimes = dateTime.split(" ");
				String[] times = dateTimes[1].split(":");
				int startHour = rule.getStartHour();
				int startMinute = rule.getStartMinute();
				boolean isRun = false;
				if (Integer.parseInt(times[0]) == startHour) { // 若小时相等
					if (Integer.parseInt(times[1]) >= startMinute) {
						isRun = true;
					}
				}else if (Integer.parseInt(times[0]) > startHour) {
					isRun = true;
				}
				// 若可运行
				if (isRun) {
					runStatus = 'Y';
					manage.setRunStatus(runStatus);
				}
			}
			
			if (null != runStatus && runStatus.charValue() == 'Y') { // 若是可运行状态
				if (null != rule) {
					Integer numberNum = rule.getNumberNum();
					SqlMap<String, String, Object> sqlMap = new SqlMap<String, String, Object>();
					sqlMap.put("bussObj", "=", objCode);
					sqlMap.put("generateDate", "=", nowDate);
					int numbers = dao.selectTotalRows(sqlMap);
					if (numberNum != null) {
						if (numbers >= numberNum.intValue()) {
							isOver = true;
						}
					}else {
						isOver = true;
					}
				}else {
					isOver = true;
				}
				if (isOver) {
					// 若是指定日期话，号码数量达到后就结束
					if ("1".equals(rule.getGenOptions())) {
						vacantManageService.operateStop(manage);
					}
				}else {
					// 获得业务对象对应的下一个号码
					String code = numberService.getNext(objCode);
					if (null != code) {
						VacantNumber number = new VacantNumber();
						number.setBussObj(objCode); // 业务对象编码
						number.setGenerateDate(nowDate); // 生成日期
						number.setBussNumber(code);
						number.setBussName(manage.getBussName()); // 业务对象名称
						number.setUseStatus('N'); // 使用状态为未使用
						dao.insert(number);
					}
				}
			}
		}
	}

	/**
	 * 功能: 分页查询<br/>
	 * 
	 * 重写：孟雪勤 <br/>
	 * 
	 * @version ：2016-1-2 下午11:41:11<br/>
	 * 
	 * @param entity
	 * @param page
	 * @throws Exception <br/>
	 * @see com.zhongpin.pap.base.BaseServiceImpl#selectAll(com.zhongpin.pap.base.BaseEntity, com.zhongpin.pap.utils.Pager)
	 */
	@Override
	public void selectAll(VacantNumber entity, Pager<VacantNumber> page) throws Exception {
		page.setSortName("bussNumber");
		page.setSortorder(Pager.ASC);
		SqlMap<String, String, Object> sqlMap = new SqlMap<String, String, Object>();
		Map<String, String> ts = entity.getTempStack();
		if (null != ts) {
			// 开始日期
			String startDate = ts.get("startDate");
			if (null != startDate && !"".equals(startDate)) {
				sqlMap.put("generateDate", ">=", startDate);
			}
			// 结束日期
			String endDate = ts.get("endDate");
			if (null != endDate && !"".equals(endDate)) {
				sqlMap.put("generateDate", "<=", endDate);
			}
		}
		dao.selectByConditionHQL(entity, sqlMap, page);
	}

	/**
	 * 功能: <br/>
	 * 
	 * 重写：孟雪勤 <br/>
	 * 
	 * @version ：2016-1-4 下午01:29:43<br/>
	 * 
	 * @return
	 * @throws Exception <br/>
	 * @see com.zhongpin.pap.number.services.IVacantNumberService#selectBussObj()
	 */
	@Override
	public List<BussinessObject> selectBussObj() throws Exception {
		return dao.selectBussObj();
	}
	
}
