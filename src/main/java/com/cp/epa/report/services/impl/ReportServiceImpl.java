package com.cp.epa.report.services.impl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import com.cp.epa.base.BaseServiceImpl;
import com.cp.epa.exception.SystemException;
import com.cp.epa.permission.utils.PmUtil;
import com.cp.epa.report.dao.IReportDao;
import com.cp.epa.report.entity.Report;
import com.cp.epa.report.services.IReportService;
import com.cp.epa.report.utils.ReportUtil;
import com.cp.epa.utils.SqlMap;

/**
 * 类名：ReportServiceImpl  <br />
 *
 * 功能：报表业务逻辑层实现
 *
 * @author 孟雪勤 <br />
 * 创建时间：2013-12-10 上午11:51:21  <br />
 * @version 2013-12-10
 */
public class ReportServiceImpl extends BaseServiceImpl<Report, IReportDao> implements IReportService {

	/**
	 * 功能: <br/>
	 * 
	 * 重写：孟雪勤 <br/>
	 * 
	 * @version ：2013-12-13 上午10:12:04<br/>
	 * 
	 * @param entity
	 * @return
	 * @throws Exception <br/>
	 * @see com.zhongpin.pap.base.BaseServiceImpl#save(com.zhongpin.pap.base.BaseEntity)
	 */
	@Override
	public Object save(Report entity) throws Exception {
		
		// 编码不允许为空
		String code = entity.getCode();
		if (null == ReportUtil.emptyToNull(code)) {
			throw new SystemException("报表【编码】不允许为空！");
		}
		
		// 名称不允许为空
		if (null == ReportUtil.emptyToNull(entity.getName())) {
			throw new SystemException("报表【名称】不允许为空！");
		}
		
		// 报表文件不允许为空
		if (null == ReportUtil.emptyToNull(entity.getUrlPath())) {
			throw new SystemException("请选择报表文件！");
		}
		
		// 编码不允许重复
		Report report = dao.selectBySinglet("code", code);
		if (null != report) {
			throw new SystemException("报表编码【" + code + "】存在冲突！");
		}
		
		return dao.insert(entity);
	}

	/**
	 * 功能: 修改<br/>
	 * 
	 * 重写：孟雪勤 <br/>
	 * 
	 * @version ：2013-12-12 上午11:34:34<br/>
	 * 
	 * @param entity
	 * @throws Exception <br/>
	 * @see com.zhongpin.pap.base.BaseServiceImpl#update(com.zhongpin.pap.base.BaseEntity)
	 */
	@Override
	public void update(Report entity) throws Exception {
		
		// 名称不允许为空
		if (null == ReportUtil.emptyToNull(entity.getName())) {
			throw new SystemException("报表【名称】不允许为空！");
		}
		
		// 报表文件不允许为空
		if (null == ReportUtil.emptyToNull(entity.getUrlPath())) {
			throw new SystemException("请选择报表文件！");
		}
		
		// 查询报表
		Report report = dao.selectById(entity.getCode());
		if (! report.getUrlPath().equals(entity.getUrlPath())) {
			// 删除报表
			reportFileRemove(report.getUrlPath());
		}
		
		report.setName(entity.getName());
		report.setParams(entity.getParams());
		report.setUrlPath(entity.getUrlPath());
		report.setDescription(entity.getDescription());
		
		dao.update(report);
	}

	/**
	 * 功能: <br/>
	 * 
	 * 重写：孟雪勤 <br/>
	 * 
	 * @version ：2013-12-12 上午11:42:42<br/>
	 * 
	 * @param <ID>
	 * @param PK
	 * @return
	 * @throws Exception <br/>
	 * @see com.zhongpin.pap.base.BaseServiceImpl#deleteByIds(ID[])
	 */
	@Override
	public <ID extends Serializable> int deleteByIds(ID[] PK) throws Exception {
		
		// 删除服务器端报表文件
		SqlMap<String, String, Object> sqlMap = new SqlMap<String, String, Object>();
		sqlMap.put("code", "in", PmUtil.arrayToSQLStr((String[])PK));
		List<Report> reports = dao.selectByConditionHQL(sqlMap);
		if (null != reports && reports.size() > 0) {
			for (Report report : reports) {
				reportFileRemove(report.getUrlPath());
			}
		}
		
		return dao.deleteByIds(PK);
	}



	/**
	 * 功能: 报表文件上传<br/>
	 * 
	 * 重写：孟雪勤 <br/>
	 * 
	 * @version ：2013-12-11 下午04:54:30<br/>
	 * 
	 * @param reportFile
	 * @param savePath
	 * @throws SystemException <br/>
	 * @see com.zhongpin.pap.report.services.IReportService#upload(java.io.File, java.lang.String)
	 */
	public void upload(File reportFile, String fileName, boolean isOverride) throws SystemException {
		
		// 判断上传的文件是否存在名字冲突
		File rfile = ReportUtil.getReportFile(fileName);
		if (rfile.exists()) {
			if (!isOverride) {
				throw new SystemException("上传的报表文件名存在冲突！");
			}
		}
		FileOutputStream bos = null;
		BufferedInputStream bis = null;
		try {
			bos = new FileOutputStream(ReportUtil.getReportFile(null, fileName));
			bis = new BufferedInputStream(new FileInputStream(reportFile));
			//相当于我们的缓存   
			byte[] buff = new byte[1024];
			
			int readLine = 0;
				while(-1 != (readLine = bis.read(buff))){
					// 将b中的数据写到指定的路径
					bos.write(buff,0,readLine);   
				}
			//将写入到内存的数据,刷新到磁盘   
				bos.flush();  

		}catch (Exception e) {
			e.printStackTrace();
		}finally{
	        if(null != bis){
	        	try {
					bis.close();
				}catch (IOException e) {
					e.printStackTrace();
				}
	        }
	        if(null != bos){
	        	try {
					bos.close();
				}catch (IOException e) {
					e.printStackTrace();
				}
	        }
		}
	}

	/**
	 * 功能: 删除报表文件<br/>
	 * 
	 * 重写：孟雪勤 <br/>
	 * 
	 * @version ：2013-12-12 上午11:27:52<br/>
	 * 
	 * @param fileName
	 * @throws SystemException <br/>
	 * @see com.zhongpin.pap.report.services.IReportService#reportFileRemove(java.lang.String)
	 */
	public void reportFileRemove(String fileName) throws SystemException {
		// 获得要删除的文件
		File reportFile = ReportUtil.getReportFile(fileName);
		
		if (null != reportFile) {
			// 若文件存在则删除
			if (reportFile.exists()) {
				if (reportFile.isFile()) {
					reportFile.delete(); // 删除文件
				}
			}
		}
	}
}
