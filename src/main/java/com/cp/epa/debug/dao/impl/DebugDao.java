package com.cp.epa.debug.dao.impl;


public class DebugDao {
	
//	@Autowired
//	private JdbcTemplate jdbcTemplate;
//	
//	/*
//	public  boolean addSystem(final Object...object){
//		if("".equals(object) || object == null || object.length==0){
//			return false;
//		}
//		if(object.getClass().isArray()){
//			String sql = "insert into TS_DEBUGSYSTEM(ID,OPERATORID,OPERATORNAME,OPERTIME,OPERBUSINESS,OPERFUNCTION,SERVICEFUNCTION,FOLLOWFUNCTION,TIMECONSUMING) values(?,?,?,?,?,?,?,?,?)";
//			jdbcTemplate.batchUpdate(sql,new BatchPreparedStatementSetter (){
//				 public int getBatchSize(){
//		             return object.length;
//		          }
//				 public void setValues(PreparedStatement ps,int i) throws SQLException{
//		               String _value =(String)object[i];
//		               System.out.println("-------:"+_value);
//		               if(_value != null){
//		            	   String[] _arrayValue = _value.split(",");
//		            	   ps.setString(1, System.currentTimeMillis()+"");
//		            	   ps.setString(2, _arrayValue[0]);
//		            	   ps.setString(3, _arrayValue[1]);
//		            	   ps.setString(4, new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
//		            	   ps.setString(5, _arrayValue[2]);
//		            	   ps.setString(6, _arrayValue[3]);
//		            	   ps.setString(7, _arrayValue[4]);
//		            	   if(_arrayValue.length == 6){
//		            		   ps.setString(7, null); 
//		            		   ps.setString(8, _arrayValue[5]);
//		            	   }else{
//		            		   ps.setString(7, _arrayValue[5]); 
//		            		   ps.setString(8, _arrayValue[6]);
//		            	   }
//		               }
//		          }
//		    });
//		}else{
//			String _id = System.currentTimeMillis()+""; 
//			String[] _args = object.toString().split(",");
//			String sql = "insert into TS_DEBUGSYSTEM(ID,OPERATORID,OPERATORNAME,OPERTIME,OPERBUSINESS,OPERFUNCTION,SERVICEFUNCTION,FOLLOWFUNCTION,TIMECONSUMING) values('"+_id+"','"+_args[0]+"','"+_args[1]+"','"+_args[2]+"','"+_args[3]+"',null,null,null,'"+_args[4]+"')";
//			jdbcTemplate.execute(sql);
//		}
//		
//		return true;
//	}
//	*/
//	
////	public  boolean addSystem(List<String> _list){
////		if(_list==null || _list.size()==0){
////			return false;
////		}
////		Connection conn = null;
////		JDBCWrapperx jdbc = null;
////		PreparedStatement ps = null;
////		try {
////			conn = DatabaseManagerFactory.getBaseAdm().getConnection();
////			jdbc = new JDBCWrapperx(conn);
////			String sql = "insert into TS_DEBUGSYSTEM(ID,OPERATORID,OPERATORNAME,OPERTIME,OPERBUSINESS,OPERFUNCTION,SERVICEFUNCTION,FOLLOWFUNCTION,TIMECONSUMING,NCREATEUSERID,SCREATEDATE,SCREATETIME) values(?,?,?,?,?,?,?,?,?,?,?,?)";
////			jdbc.BeginTransaction();
////			ps = jdbc.createPreparedStmt(sql);
////			for (String _value : _list) {
////				if (_value != null) {
////					String[] _arrayValue = _value.split(",");
////					ps.setString(1, UUID.randomUUID().toString().replaceAll("-", ""));
////					ps.setString(2, _arrayValue[0]);
////					ps.setString(3, _arrayValue[1]);
////					ps.setString(4, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
////					ps.setString(5, _arrayValue[2]);
////					ps.setString(6, _arrayValue[3]);
////					if(_arrayValue.length == 5){
////						ps.setString(7, null);
////						ps.setString(8, null);
////						ps.setString(9, _arrayValue[4]);
////					}else if (_arrayValue.length == 6) {
////						ps.setString(7, _arrayValue[4]);
////						ps.setString(8, null);
////						ps.setString(9, _arrayValue[5]);
////					} else {
////						ps.setString(7, _arrayValue[4]);
////						ps.setString(8, _arrayValue[5]);
////						ps.setString(9, _arrayValue[6]);
////					}
////					ps.setString(10, _arrayValue[0]);
////					ps.setString(11, new SimpleDateFormat("yyyyMMdd").format(new Date()));
////					ps.setString(12, new SimpleDateFormat("HHmmss").format(new Date()));
////				}
////				ps.addBatch();
////			}
////			int[] in =ps.executeBatch();
////			jdbc.CommitTransaction();
////		} catch (SQLException e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		}finally{
////			jdbc.destroy();
////			try {
////				if(conn != null){
////					conn.close();
////				}
////			} catch (SQLException e) {
////				// TODO Auto-generated catch block
////				e.printStackTrace();
////			}
////			jdbc = null;
////			System.gc();
////		}
////		return true;
////	}
//	
//	public  boolean addSql(){
//		return false;
//	}

}
