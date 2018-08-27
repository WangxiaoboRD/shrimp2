package com.cp.epa.log.actions;

import com.cp.epa.base.BaseAction;
import com.cp.epa.log.entity.ExceptionLog;
import com.cp.epa.log.services.IExceptionLogService;

@SuppressWarnings("serial")
public class ExceptionLogAction extends BaseAction<ExceptionLog,IExceptionLogService>{
	
//	/**
//	 * 跳转到list页面
//	 * 功能：<br/>
//	 *
//	 * @author 杜中良
//	 * @version May 21, 2013 9:34:17 AM <br/>
//	 */
//	public String loadPage()throws Exception{
//		Calendar c = new GregorianCalendar();
//		c.setTime(new Date());//设置时间
//		c.add(Calendar.DATE, -7);
//		String startDate = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());//为空时以当前时间为准
//		String endDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
//	
//		if (null == e) {
//			e = new ExceptionLog();
//		}
//		Map<String, String> tempStack = new HashMap<String, String>();
//		tempStack.put("startDate", startDate);
//		tempStack.put("endDate", endDate);
//		e.setTempStack(tempStack);
//		
//		return LIST;
//	}
	
}
