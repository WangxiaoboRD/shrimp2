/**
 * 文件名：@IReportService.java <br/>
 * 包名：com.zhongpin.pap.bussobj.services <br/>
 * 项目名：PAP1.0 <br/>
 * @author 孟雪勤 <br/>
 */
package com.cp.epa.report.services;

import java.io.File;

import com.cp.epa.base.IBaseService;
import com.cp.epa.exception.SystemException;
import com.cp.epa.report.entity.Report;

/**
 * 类名：IReportService  <br />
 *
 * 功能：报表业务逻辑层定义
 *
 * @author 孟雪勤 <br />
 * 创建时间：2013-12-10 上午11:50:25  <br />
 * @version 2013-12-10
 */
public interface IReportService extends IBaseService<Report> {

	/**
	 * 功能：报表文件上传<br/>
	 *
	 * @author 孟雪勤
	 * @version 2013-12-11 下午04:54:16 <br/>
	 */
	public void upload(File reportFile, String fileName, boolean isOverride) throws SystemException;
	
	/**
	 * 功能：报表文件删除<br/>
	 *
	 * @author 孟雪勤
	 * @version 2013-12-12 上午11:27:22 <br/>
	 */
	public void reportFileRemove(String fileName) throws SystemException;
}
