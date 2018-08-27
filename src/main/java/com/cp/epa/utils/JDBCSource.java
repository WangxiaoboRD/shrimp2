package com.cp.epa.utils;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import org.apache.log4j.Logger;


/**
 * jdbc操作封装 类名：JDBCWrapperx
 * 
 * 功能：
 * 
 * @author dzl 创建时间：Sep 24, 2012 3:00:01 PM
 * @version Sep 24, 2012
 */
public class JDBCSource{
	// 日志记录器 
	private static final Logger logger = Logger.getLogger(JDBCSource.class);	
	private Connection m_dbcon;
	
	public JDBCSource(String driver,String url,String name,String password,String DataType){
		try {
	        	//加载驱动  
			 	Class.forName(driver);
				//建立连接
				m_dbcon = DriverManager.getConnection(url, name, password);  
			} catch (ClassNotFoundException e) {
				logger.error("数据库连接失败！Err=" + e.getMessage());
			} catch (SQLException e) {
				logger.error("数据库连接失败！Err=" + e.getMessage());
			}  
	}
	//打开连接
	public void openDbConn(String url,String name,String password,String driver){		
		
//        try {
//        	//加载驱动  
//			Class.forName(driver);
//			//建立连接
//			m_dbcon = DriverManager.getConnection(url, name, password);  
//		} catch (ClassNotFoundException e) {
//			logger.error("数据库连接失败！Err=" + e.getMessage());
//		} catch (SQLException e) {
//			logger.error("数据库连接失败！Err=" + e.getMessage());
//		}  
	}
	/**
	 * 
	 * 功能：<br/> 关闭连接
	 * 
	 * @author 杜中良
	 * @version Sep 20, 2012 4:08:16 PM <br/>
	 */
	public void destroy() {
		if (this.m_dbcon != null) {
			try {
				this.m_dbcon.close();
			} catch (SQLException e) {
				logger.error("释放数据库连接失败！Err=" + e.getMessage());
			}
		}
	}

	// 开启事物
	public boolean BeginTransaction() {
		try {
			this.m_dbcon.setAutoCommit(false);
			return true;
		} catch (SQLException e) {
			logger.error("启动数据库事务失败！Err=" + e.getMessage());
			return false;
		}

	}

	// 提交事务
	public boolean CommitTransaction() {
		try {
			this.m_dbcon.commit();
			this.m_dbcon.setAutoCommit(true);
			return true;
		} catch (SQLException e) {
			logger.error("提交数据库事务失败！Err=" + e.getMessage());
			return false;
		}

	}

	// 回滚事物
	public boolean RollbackTransaction() {
		try {
			this.m_dbcon.rollback();
			this.m_dbcon.setAutoCommit(true);
			return true;
		} catch (SQLException e) {
			logger.error("回滚数据库事务失败！Err=" + e.getMessage());
			return false;
		}
	}

	/**
	 * 创建预编译的statement
	 * 
	 * @param sql
	 * @return
	 */
	public PreparedStatement createPreparedStmt(String sql) {
		try {
			return this.m_dbcon.prepareStatement(sql);
		} catch (SQLException e) {
			logger.error("创建预编译的statement失败！Err=" + e.getMessage());
		}
		return null;
	}

	/**
	 * 调用存储过程
	 * 
	 * @param procedoreSql
	 * @return
	 */
	public CallableStatement creatCallableStatement(String procedoreSql) {
		try {
			return this.m_dbcon.prepareCall(procedoreSql);
		} catch (SQLException e) {
			logger.error("创建存储过程失败！Err=" + e.getMessage());
		}
		return null;
	}

	/**
	 * 查询数据库
	 */
	public Object[][] DoQuerry(String sql) {
		Object[][] retArry = new Object[0][0];
		Connection conn = this.m_dbcon;
		if (null == conn)
			return retArry;

		// --构建Statement
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			// --获取结果集架构信息
			ResultSetMetaData rsmeta = rs.getMetaData();
			int iRow = 0;
			int iCol = rsmeta.getColumnCount();
			Vector<Object[]> tmpvt = new Vector<Object[]>();
			// --读结果集
			while (rs.next()) {
				Object[] arTmp = new Object[iCol];
				for (int i = 0; i < iCol; i++) {
					arTmp[i] = rs.getObject(i + 1);
				}
				tmpvt.add(arTmp);
				iRow++;
			}
			retArry = new Object[iRow + 1][iCol];
			// --添加字段名称头，放在第一行
			for (int i = 0; i < iCol; i++) {
				retArry[0][i] = rsmeta.getColumnName(i + 1);
			}
			// --处理存储在集合中的行数组
			Object[] resTmp = tmpvt.toArray();
			System.arraycopy(resTmp, // src
					0, retArry, // dest
					1, iRow);
		} catch (SQLException e) {
			logger.error("执行Sql查询失败！Err=" + e.getMessage());
		} finally {
			try {
				if (null != rs)
					rs.close();
				if (null != stmt)
					stmt.close();
			} catch (SQLException e) {
				logger.error("释放Jdbc对象失败！Err=" + e.getMessage());
			}

		}
		return retArry;
	}
	
	/**
	 * 查询数据库不带表头
	 */
	public Object[][] Querry(String sql) {
		Object[][] retArry = new Object[0][0];;
		Connection conn = this.m_dbcon;
		if (null == conn)
			return retArry;
		// --构建Statement
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			// --获取结果集架构信息
			ResultSetMetaData rsmeta = rs.getMetaData();
			int iRow = 0;
			int iCol = rsmeta.getColumnCount();
			Vector<Object[]> tmpvt = new Vector<Object[]>();
			// --读结果集
			while (rs.next()) {
				Object[] arTmp = new Object[iCol];
				for (int i = 0; i < iCol; i++) {
					arTmp[i] = rs.getObject(i + 1);
				}
				tmpvt.add(arTmp);
				iRow++;
			}
			retArry = new Object[iRow][iCol];
			// --添加字段名称头，放在第一行
//			for (int i = 0; i < iCol; i++) {
//				retArry[0][i] = rsmeta.getColumnName(i + 1);
//			}
			// --处理存储在集合中的行数组
			Object[] resTmp = tmpvt.toArray();
			System.arraycopy(resTmp,0, retArry, 0, iRow);
		} catch (SQLException e) {
			logger.error("执行Sql查询失败！Err=" + e.getMessage());
		} finally {
			try {
				if (null != rs)
					rs.close();
				if (null != stmt)
					stmt.close();
			} catch (SQLException e) {
				logger.error("释放Jdbc对象失败！Err=" + e.getMessage());
			}

		}
		return retArry;
	}

	/**
	 * 插入数据 返回类型有void类型修改为boolean类型
	 * 
	 * @param sql
	 * @throws SQLException
	 */
	public boolean insert(String sql) {
		Connection conn = this.m_dbcon;
		if (null == conn) {
			return false;
		}

		// --构建Statement
		Statement stmt = null;
		// 执行语句
		try {
			return conn.createStatement().executeUpdate(sql) > 0;
		} catch (SQLException e) {
			logger.error("执行sql更新失败!Err=" + e.getMessage());
			return false;
		} finally {
			try {
				if (null != stmt) {
					stmt.close();
				}
			} catch (SQLException e) {
				logger.error("释放Jdbc对象失败！Err=" + e.getMessage());
			}
		}
	}

	/**
	 * 修改数据
	 * 
	 * @param sql
	 */
	public boolean update(String sql) {
		if (this.insert(sql)) {
			logger.info("更新成功 ！！");
			return true;
		} else {
			logger.error("更新失败 ！！");
			return false;
		}
	}

	/**
	 * 删除数据 返回值类型修改为Boolean
	 * 
	 * @param sql
	 */
	public boolean del(String sql) {
		return this.insert(sql);
	}
}
