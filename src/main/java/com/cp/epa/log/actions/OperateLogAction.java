package com.cp.epa.log.actions;

import com.cp.epa.base.BaseAction;
import com.cp.epa.log.entity.OperateLog;
import com.cp.epa.log.services.IOperateLogService;

public class OperateLogAction extends BaseAction<OperateLog,IOperateLogService>{
	private static final long serialVersionUID = -1397599875384673971L;

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
//			e = new OperateLog();
//		}
//		Map<String, String> tempStack = new HashMap<String, String>();
//		tempStack.put("startDate", startDate);
//		tempStack.put("endDate", endDate);
//		e.setTempStack(tempStack);
//		
//		return LIST;
//	}
}
