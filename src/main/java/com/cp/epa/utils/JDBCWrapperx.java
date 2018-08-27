package com.cp.epa.utils;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.annotation.Resource;
import javax.sql.DataSource;

import com.cp.epa.base.Base;

/**
 * jdbc操作封装 类名：JDBCWrapperx
 * 
 * 功能：
 * 
 * @author dzl 创建时间：Sep 24, 2012 3:00:01 PM
 * @version Sep 24, 2012
 */
public class JDBCWrapperx extends Base{
	
	@Resource
	private DataSource dataSource;//数据源
	
	/**
	 * 获取连接
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Dec 19, 2014 10:16:30 AM <br/>
	 */
	public Connection getConnection(){
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
	}

	/**
	 * 
	 * 功能：<br/> 关闭连接
	 * 
	 * @author 杜中良
	 * @version Sep 20, 2012 4:08:16 PM <br/>
	 */
	public void destroy(Connection connection) {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				logger.error("释放数据库连接失败！Err=" + e.getMessage());
			}
		}
	}
	/**
	 * 释放数据库资源
	 * @Description:TODO
	 * @param conn
	 * @param statement
	 * @param rs
	 * void
	 * @exception:
	 * @author: 小丁
	 * @time:2018-1-8 上午11:06:31
	 */
	public void relase(Connection conn,Statement statement,ResultSet rs) {
		
		try{
			if(rs != null)
				rs.close();
		} catch (SQLException e){
			logger.error("释放数据库ResultSet失败！Err=" + e.getMessage());
		}
		
		try{
			if(statement != null)
				statement.close();
		} catch (SQLException e){
			logger.error("释放数据库statement失败！Err=" + e.getMessage());
		}
		
		try{
			if(conn != null)
				conn.close();
		} catch (SQLException e){
			logger.error("释放数据库连接失败！Err=" + e.getMessage());
		}
	}

	// 开启事物
	public boolean beginTransaction(Connection connection) {
		try {
			connection.setAutoCommit(false);
			return true;
		} catch (SQLException e) {
			logger.error("启动数据库事务失败！Err=" + e.getMessage());
			return false;
		}
	}

	// 提交事务
	public boolean commitTransaction(Connection connection) {
		try {
			connection.commit();
			connection.setAutoCommit(true);
			return true;
		} catch (SQLException e) {
			logger.error("提交数据库事务失败！Err=" + e.getMessage());
			return false;
		}

	}

	// 回滚事物
	public boolean rollbackTransaction(Connection connection) {
		try {
			connection.rollback();
			connection.setAutoCommit(true);
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
	public PreparedStatement createPreparedStmt(String sql,Connection connection) {
		try {
			return connection.prepareStatement(sql);
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
	public CallableStatement creatCallableStatement(String procedoreSql,Connection connection) {
		try {
			return connection.prepareCall(procedoreSql);
		} catch (SQLException e) {
			logger.error("创建存储过程失败！Err=" + e.getMessage());
		}
		return null;
	}

	/**
	 * 查询数据库
	 */
	public Object[][] doQuerry(String sql,Connection connection) {
		Object[][] retArry = new Object[0][0];
		Connection conn = connection;
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
	public Object[][] Querry(String sql,Connection connection) {
		Object[][] retArry = new Object[0][0];
		Connection conn = connection;
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
	public Object insert(String sql,Connection connection) {
		Connection conn = connection;
		if (null == conn) 
			return null;
		// --构建Statement
		Statement stmt =null;
		// 执行语句
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(sql,Statement.RETURN_GENERATED_KEYS);
		    ResultSet rs = stmt.getGeneratedKeys();
		    Object autoIncKeyFromApi = null;
		    if (rs.next()) {
		      autoIncKeyFromApi = rs.getObject(1);
		    }
		    return autoIncKeyFromApi;
			
		} catch (SQLException e) {
			logger.error("执行sql更新失败!Err=" + e.getMessage());
		} finally {
			try {
				if (null != stmt) {
					stmt.close();
				}
			} catch (SQLException e) {
				logger.error("释放Jdbc对象失败！Err=" + e.getMessage());
			}
		}
		return null;
	}

	/**
	 * 修改数据
	 * 
	 * @param sql
	 */
	public boolean update(String sql,Connection connection) {
		
		Connection conn = connection;
		if (null == conn) 
			return false;
		// --构建Statement
		Statement stmt =null;
		// 执行语句
		try {
			stmt = conn.createStatement();
			int i = stmt.executeUpdate(sql);
			if(i>0){
				logger.info("更新成功 ！！");
				return true;
			}else {
				logger.error("更新失败 ！！");
				return false;
			}
		}catch (SQLException e) {
			logger.error("更新失败对象失败！Err=" + e.getMessage());
		}finally {
			try {
				if (null != stmt)
					stmt.close();
			} catch (SQLException e) {
				logger.error("释放Jdbc对象失败！Err=" + e.getMessage());
			}
		}
		return false;
	}

	/**
	 * 删除数据 返回值类型修改为Boolean
	 * 
	 * @param sql
	 */
	public boolean delete(String sql,Connection connection) {
		Connection conn = connection;
		if (null == conn) 
			return false;
		// --构建Statement
		Statement stmt =null;
		// 执行语句
		try {
			stmt = conn.createStatement();
			int i = stmt.executeUpdate(sql);
			if(i>0){
				logger.info("更新成功 ！！");
				return true;
			}else {
				logger.error("更新失败 ！！");
				return false;
			}
		}catch (SQLException e) {
			logger.error("更新失败对象失败！Err=" + e.getMessage());
		}finally {
			try {
				if (null != stmt)
					stmt.close();
			} catch (SQLException e) {
				logger.error("释放Jdbc对象失败！Err=" + e.getMessage());
			}
		}
		return false;
	}
}
