/**
 * 文件名：@ReportAction.java <br/>
 * 包名：com.zhongpin.pap.bussobj.actions <br/>
 * 项目名：PAP1.0 <br/>
 * @author 孟雪勤 <br/>
 */
package com.cp.epa.report.actions;

import com.cp.epa.base.BaseAction;
import com.cp.epa.report.entity.Report;
import com.cp.epa.report.services.IReportService;

/**
 * 类名：ReportAction  <br />
 *
 * 功能：报表控制层处理
 *
 * @author 孟雪勤 <br />
 * 创建时间：2013-12-10 上午11:52:34  <br />
 * @version 2013-12-10
 */
public class ReportAction extends BaseAction<Report, IReportService> {

	private static final long serialVersionUID = -4337248297844857405L;

	/**
	 * 功能: 新增<br/>
	 * 
	 * 重写：孟雪勤 <br/>
	 * 
	 * @version ：2013-12-11 下午04:46:33<br/>
	 * 
	 * @return
	 * @throws Exception <br/>
	 * @see com.zhongpin.pap.base.BaseAction#save()
	 */
	public String save()throws Exception{
		if (null != doc) {
			e.setUrlPath(docFileName);
			service.upload(doc, docFileName, false);
		}
		
		message = (service.save(e)).toString();
		text(message);
		return NONE;
	}
	
	/**
	 * 功能: 修改<br/>
	 * 
	 * 重写：孟雪勤 <br/>
	 * 
	 * @version ：2013-12-12 上午11:33:01<br/>
	 * 
	 * @return
	 * @throws Exception <br/>
	 * @see com.zhongpin.pap.base.BaseAction#modifyAll()
	 */
	public String modifyAll()throws Exception{
		if (null != doc) {
			String resource = e.getUrlPath();
			if (null != resource) {
				e.setUrlPath(docFileName);
				// 上传文件与原来上传的文件名相同则覆盖，
				if (resource.equals(docFileName)) { 
					service.upload(doc, docFileName, true);
				}else {
					//不相同则若存在则提示名称冲突，不相同则修改
					service.upload(doc, docFileName, false);
				}
			}
		}
		service.update(e);
		message="MODIFYOK";
		text(message);
		return NONE;
	}
}
