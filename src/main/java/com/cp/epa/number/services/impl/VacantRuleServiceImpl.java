/**
 * 文件名：@VacantRuleServiceImpl.java <br/>
 * 包名：com.zhongpin.pap.number.services.impl <br/>
 * 项目名：ps <br/>
 * @author 孟雪勤 <br/>
 */
package com.cp.epa.number.services.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;

import com.cp.epa.base.BaseServiceImpl;
import com.cp.epa.exception.SystemException;
import com.cp.epa.number.dao.IVacantRuleDao;
import com.cp.epa.number.entity.VacantManage;
import com.cp.epa.number.entity.VacantRule;
import com.cp.epa.number.services.IVacantManageService;
import com.cp.epa.number.services.IVacantRuleService;
import com.cp.epa.utils.PapUtil;

/**
 * 类名：VacantRuleServiceImpl  <br />
 *
 * 功能：
 *
 * @author 孟雪勤 <br />
 * 创建时间：2015-10-7 下午03:25:45  <br />
 * @version 2015-10-7
 */
public class VacantRuleServiceImpl extends BaseServiceImpl<VacantRule, IVacantRuleDao> implements IVacantRuleService {

	/** 空号管理业务注册 */
	@Resource
	private IVacantManageService vacantManageService;
	
	/**
	 * 功能: 保存<br/>
	 * 
	 * 重写：孟雪勤 <br/>
	 * 
	 * @version ：2015-10-8 上午10:11:20<br/>
	 * 
	 * @param entity
	 * @return
	 * @throws Exception <br/>
	 * @see com.zhongpin.pap.base.BaseServiceImpl#save(com.zhongpin.pap.base.BaseEntity)
	 */
	@Override
	public Object save(VacantRule entity) throws Exception {
		String genOptions = entity.getGenOptions();
		// 若是1=指定
		if ("1".equals(genOptions)) {
			// 规则合法性校验
			String execDate = entity.getExecDate();
			if (null == execDate) {
				throw new SystemException("空号规则：执行日期不允许为空！");
			}
			String nowDate = PapUtil.notFullDate(new Date());
			if (Double.parseDouble(execDate.replaceAll("-", "")) < Double.parseDouble(nowDate.replaceAll("-", ""))) {
				throw new SystemException("空号规则：执行日期必须大于系统当前日期！");
			}
		}
		
		// 结束小时必须大于开始小时
		Integer startHour = entity.getStartHour();
		if (null == startHour) {
			throw new SystemException("空号规则：开始时间不允许为空！");
		}
		Integer startMinute = entity.getStartMinute();
		if (null == startMinute) {
			throw new SystemException("空号规则：开始分钟不允许为空！");
		}
		Integer endHour = entity.getEndHour();
		if (null == endHour) {
			throw new SystemException("空号规则：结束时间不允许为空！");
		}
		Integer endMinute = entity.getEndMinute();
		if (null == endMinute) {
			throw new SystemException("空号规则：结束分钟不允许为空！");
		}
		if (startHour.intValue() > endHour.intValue()) {
			throw new SystemException("空号规则：结束时间必须大于开始时间！");
		}else if (startHour.intValue() == endHour.intValue()) {
			if (startMinute.intValue() > endMinute.intValue()) {
				throw new SystemException("空号规则：结束时间必须大于开始时间！");
			}
		}
		
		// 号码数
		Integer num = entity.getNumberNum();
		if (null == num || (null != num && num.intValue() == 0))
			throw new SystemException("空号规则：请指定生成的号码数量！");
		
		entity.setVacantRule(getVacantRule(entity));
		Object pk = dao.insert(entity);
		if (null != pk) {
			VacantManage vm = vacantManageService.selectById(entity.getManageId());
			if (null != vm) {
				vm.setVacantRule(entity);
			}
		}
		return pk;
	}
	
	/**
	 * 功能：获得空号规则<br/>
	 *
	 * @author 孟雪勤
	 * @version 2015-10-8 上午10:42:25 <br/>
	 */
	private String getVacantRule(VacantRule entity) {
		String execDate = entity.getExecDate();
		String cronExpress = "";
		if (null != execDate) {
			String[] dates = execDate.split("-");
			int startHour = entity.getStartHour();
			int startMinute = entity.getStartMinute();
			int endHour = entity.getEndHour();
			int endMinute = entity.getEndMinute();
			int numberNum = entity.getNumberNum();
			
			int seconds = (endHour * 60 * 60 + endMinute * 60) - (startHour * 60 * 60 + startMinute * 60); // 总秒数
			int minutes = (endHour * 60 + endMinute) - (startHour * 60 + startMinute); // 总分钟数
			int hours = endHour - startHour; // 总小时数
			
			int intervalMinute = minutes/numberNum; // 间隔分钟数
			int intervalSecond = seconds/numberNum; // 间隔秒数
			int internalHour = hours/numberNum; // 间隔小时数
			// 若开始小时等于结束小时
			if (startHour == endHour) {
				if (startMinute == endMinute) {
					cronExpress = "0/" + intervalSecond;
					cronExpress += " " + startMinute; // 指定分钟
					cronExpress += " " + startHour; // 指定小时
				}else {
					if (intervalMinute > 0) {
						cronExpress = "0 "; // 指定秒
						cronExpress += startMinute + "-" + endMinute + "/" + intervalMinute; // 指定分钟
						cronExpress += " " + startHour; // 指定小时
					}else {
						cronExpress = "0/" + intervalSecond;
						cronExpress += " " + startMinute + "-" + endMinute; // 指定分钟
						cronExpress += " " + startHour; // 指定小时
					}
				}
			}else {
				if (internalHour > 0) {
					cronExpress = "0 "; // 指定秒
					cronExpress += "0 "; // 指定分钟
					cronExpress += startHour + "-" + endHour + "/" + internalHour; // 指定小时
				}else {
					if (intervalMinute > 0) {
						cronExpress = "0 "; // 指定秒
						cronExpress += "*/" + intervalMinute; // 指定分钟
						cronExpress += " " + startHour + "-" + endHour; // 指定小时
					}else {
						cronExpress = "0/" + intervalSecond;
						cronExpress += " *"; // 指定分钟
						cronExpress += " " + startHour + "-" + endHour; // 指定小时
					}
				}
			}
			
			String genOptions = entity.getGenOptions(); // 生成选项
			// 若是0=每天
			if ("0".equals(genOptions)) {
				cronExpress += " *"; // 指定日期
				cronExpress += " *"; // 指定月份
				cronExpress += " ?"; // 星期
				cronExpress += " *"; // 指定年份
			}else {
				// 若是1=指定日期
				cronExpress += " " + Integer.parseInt(dates[2]); // 指定日期
				cronExpress += " " + Integer.parseInt(dates[1]); // 指定月份
				cronExpress += " ?"; // 星期
				cronExpress += " " + dates[0]; // 指定年份
			}
		}
		
		return cronExpress;
	}
	
	/**
	 * 功能: 更新<br/>
	 * 
	 * 重写：孟雪勤 <br/>
	 * 
	 * @version ：2015-10-8 上午10:29:35<br/>
	 * 
	 * @param entity
	 * @throws Exception <br/>
	 * @see com.zhongpin.pap.base.BaseServiceImpl#update(com.zhongpin.pap.base.BaseEntity)
	 */
	@Override
	public void update(VacantRule entity) throws Exception {
		VacantRule vr = dao.selectById(entity.getId());
		BeanUtils.copyProperties(entity, vr, new String[]{ "createUser", "createDate" });
		vr.setVacantRule(getVacantRule(entity));
		dao.update(vr);
	}
}
