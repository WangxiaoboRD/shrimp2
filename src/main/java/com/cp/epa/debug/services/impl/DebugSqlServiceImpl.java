package com.cp.epa.debug.services.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Vector;

import com.cp.epa.base.BaseServiceImpl;
import com.cp.epa.debug.dao.IDebugSqlDao;
import com.cp.epa.debug.entity.DebugSql;
import com.cp.epa.debug.services.IDebugSqlService;
import com.cp.epa.exception.SystemException;
import com.cp.epa.permission.entity.Users;
import com.cp.epa.utils.Pager;
import com.cp.epa.utils.SqlMap;
import com.p6spy.engine.common.P6SpyOptions;



/**
 * 类名：DebugSqlServiceImpl  <br />
 *
 * 功能：sql调试
 *
 * @author dzl <br />
 * 创建时间：2012-7-9 上午11:14:10  <br />
 * @version 2012-7-9
 */
public class DebugSqlServiceImpl extends BaseServiceImpl<DebugSql, IDebugSqlDao> implements IDebugSqlService {

	/**
	 * 检索信息
	 * 功能：<br/>
	 *
	 * @author dzl
	 * @version Jan 27, 2013 11:45:13 AM <br/>
	 */
	public void findFile(DebugSql debugSql,String statu,Pager<DebugSql> page,Users u)throws Exception{
		if("ON".equals(statu)){
			throw new SystemException("请先关闭Sql调试状态");
		}
		Vector<File> _vf = new Vector<File>(100);
		//获取符合条件的文件
		getFiles(debugSql, _vf, u);
		//将检索到的文件信息插入到数据库中
		insertValue(_vf);
		//if(debugSql == null)
		//	debugSql = new DebugSql();
		//debugSql.setOperatorId(sample.getSmap().get("operatorId"));
		//String startTime = (sample.getSmap().get("startDate")+" "+sample.getSmap().get("startH")+":"+sample.getSmap().get("startM"));
		//String endTime = (sample.getSmap().get("endDate")+" "+sample.getSmap().get("endH")+":"+sample.getSmap().get("endM"));
		selectAll(page);
	}

	
	//检索符合条件的文件
	private void getFiles(DebugSql debugSql, Vector<File> _vf, Users u){
		//获取文件存放的路径
		String path = P6SpyOptions.getLogpath();
		//根据文件路径生成文件数组
		File file = new File(path);
		File[] files = file.listFiles();
		//设置判断条件
		String userId = debugSql.getOperatorId();
		if(userId == null || "".equals(userId))
			userId = u.getUserCode();
		String startTime = "";
		String endTime="";
		Map<String,String> map = debugSql.getTempStack();
		if(map != null){
			startTime = map.get("startDate").replaceAll(" ", "").replaceAll("[-:]", "");
			if(startTime==null ||"".equals(startTime))
				startTime = new SimpleDateFormat("yyyyMMdd").format(new Date())+"000000";
			endTime = map.get("endDate").replaceAll(" ", "").replaceAll("[-:]", "");
			if(endTime==null ||"".equals(endTime))
				endTime = new SimpleDateFormat("yyyyMMdd").format(new Date())+"235959";
		}
		if(!startTime.matches("^\\d+$"))
			return ;
		if(!endTime.matches("^\\d+$"))
			return ;
		long _endTime = Long.parseLong(endTime);
		long _startTime = Long.parseLong(startTime);

		for (int i = 0; i < files.length; i++) {
			//判断是否是路径
			if (!files[i].isDirectory()) {
				//判断文件是否正在被操作
				//if(!file.renameTo(file))
				//	continue;
				String fileName = files[i].getName();
				if(fileName.indexOf("_") == fileName.lastIndexOf("_"))
					continue;
				if(userId != null && !"".equals(userId)){
					if(!fileName.substring(0,fileName.indexOf("_")).contains(userId))
						continue;
				}
				String fileTime = fileName.substring(fileName.indexOf("_")+1,fileName.lastIndexOf("_"));
				if(fileTime.matches("^\\d+$")){
					long _fileTime = Long.parseLong(fileTime);
					if(_fileTime > _startTime && _fileTime < _endTime){
						_vf.add(files[i]);
					}
				}
			}
		}
	}
	//存储数据
	public void insertValue(Vector<File> _vf)throws Exception{
		if (_vf == null || _vf.size() == 0)
			return;
		SqlMap<String,String,String> sqlMap = new SqlMap<String,String,String>();
		for (File f : _vf) {
			String lineTXT = null;
			sqlMap.put("fileName", "=", f.getName());
			dao.delete(sqlMap);
			sqlMap.clear();
			//DebugSql _ds=null;
			InputStreamReader read = new InputStreamReader(new FileInputStream(f));
			BufferedReader bufferedReader = new BufferedReader(read);
			while ((lineTXT = bufferedReader.readLine()) != null) {
				String[] _lineTXT = lineTXT.toString().trim().split("\\|");
				if (_lineTXT.length == 14){
					DebugSql debugSql= new DebugSql();
					
					debugSql.setOperatorId(_lineTXT[1]);//操作人
					debugSql.setOperatorName(_lineTXT[2]);//操作人名称
					debugSql.setOperTime(_lineTXT[0]);//操作时间
					debugSql.setEventMark(_lineTXT[3]);//操作标示
					debugSql.setOperBusiness(_lineTXT[4]);//操作业务
					debugSql.setOperFunction(_lineTXT[5]);//操作方法
					debugSql.setServiceFunction(_lineTXT[6]);//service方法
					debugSql.setFollowFunction(_lineTXT[7]);//跟踪方法
					debugSql.setTimeConsuming(_lineTXT[8]);//操作耗时
					debugSql.setConnectId(_lineTXT[9]);//连接标示
					debugSql.setCategory(_lineTXT[10]);//语句类别
					debugSql.setOldHql(_lineTXT[11]);//hibernateHQl
					debugSql.setOldSql(_lineTXT[12]);//原始语句
					debugSql.setNewSql(_lineTXT[13]);//转换sql
					debugSql.setFileName(f.getName());//提取文件名
					
					dao.insert(debugSql);
				}
			}
		}
//		Connection conn = null;
//		JDBCWrapperx jdbc = null;
//		PreparedStatement ps = null;
//		try {
//			conn = DatabaseManagerFactory.getBaseAdm().getConnection();
//			jdbc = new JDBCWrapperx(conn);
//						
//			String sql = "insert into TS_DEBUGSQL(ID,OPERATORID,OPERATORNAME,OPERTIME,OPERBUSINESS," +
//					"OPERFUNCTION,SERVICEFUNCTION," +
//					"FOLLOWFUNCTION,CONNECTID,CATEGORY," +
//					"OLDHQL,OLDSQL,NEWSQL,EVENTMARK,TIMECONSUMING,NCREATEUSERID," +
//					"SCREATEDATE,SCREATETIME,FILENAME) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
//			jdbc.BeginTransaction();
//			ps = jdbc.createPreparedStmt(sql);
//			for (File f : _vf) {
//				String sqlDel = "delete from TS_DEBUGSQL where FILENAME='"+f.getName()+"'";
//				jdbc.del(sqlDel);
//				InputStreamReader read = new InputStreamReader(new FileInputStream(f));
//				BufferedReader bufferedReader = new BufferedReader(read);
//				String lineTXT = null;
//				//DebugSql _ds=null;
//				while ((lineTXT = bufferedReader.readLine()) != null) {
//					String[] _lineTXT = lineTXT.toString().trim().split("\\|");
//					if (_lineTXT.length == 14){
//						ps.setString(1, (UUID.randomUUID().toString()).replaceAll("-", ""));
//						ps.setString(2, _lineTXT[1]);
//						ps.setString(3, _lineTXT[2]);
//						ps.setString(4, _lineTXT[0]);
//						ps.setString(5, _lineTXT[4]);
//						ps.setString(6, _lineTXT[5]);
//						ps.setString(7, _lineTXT[6]);
//						ps.setString(8, _lineTXT[7]);
//						ps.setString(9, _lineTXT[9]);
//						ps.setString(10, _lineTXT[10]);
//						ps.setString(11, _lineTXT[11]);
//						ps.setString(12, _lineTXT[12]);
//						ps.setString(13, _lineTXT[13]);
//						ps.setString(14, _lineTXT[3]);
//						ps.setString(15, _lineTXT[8]);
//						ps.setString(16, _lineTXT[1]);
//						ps.setString(17, new SimpleDateFormat("yyyyMMdd").format(new Date()));
//						ps.setString(18, new SimpleDateFormat("HHmmss").format(new Date()));
//						ps.setString(19,f.getName());
//						ps.addBatch();
//					}
//				}
//				bufferedReader.close();
//				read.close();
//			}
//			ps.executeBatch();
//			jdbc.CommitTransaction();
//			
//			//删除文件
//			for (File f : _vf)
//				f.delete();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}finally{
//			jdbc.destroy();
//			try {
//				if(conn != null)
//					conn.close();
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			jdbc = null;
//			System.gc();
//		}
	}
}
