package com.cp.epa.bussobj.services.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;

import com.cp.epa.base.BaseServiceImpl;
import com.cp.epa.bussobj.dao.IBaseTableDao;
import com.cp.epa.bussobj.entity.BaseTable;
import com.cp.epa.bussobj.entity.BaseTableColumn;
import com.cp.epa.bussobj.entity.BussinessEle;
import com.cp.epa.bussobj.services.IBaseTableColumnService;
import com.cp.epa.bussobj.services.IBaseTableService;
import com.cp.epa.bussobj.services.IBussinessEleService;
import com.cp.epa.exception.SystemException;
import com.cp.epa.permission.utils.PmUtil;
import com.cp.epa.utils.ISqlMap;
import com.cp.epa.utils.PapUtil;
import com.cp.epa.utils.SqlMap;
/**
 * 
 * 类名：BaseTableServiceImpl  <br />
 *
 * 功能：
 *
 * @author zp <br />
 * 创建时间：2013-7-5 上午09:30:48  <br />
 * @version 2013-7-5
 */
public class BaseTableServiceImpl extends BaseServiceImpl<BaseTable, IBaseTableDao> implements IBaseTableService{

	
	@Autowired
	private IBaseTableColumnService baseTableColumnService;
	
	@Autowired
	private IBussinessEleService bussinessEleService;
	
	
	@Override
	public Object save(BaseTable entity) throws Exception {
		
		BaseTable  baseTable= dao.selectById(entity.getTabCode());		
		if(null!=baseTable){
			throw new SystemException("表名已存在！");
		}
		
	   Object object=dao.insert(entity);		
		List<BaseTableColumn> list=entity.getDetails();		
		
		String rt=checkBussEle(list);
		if(!"".equals(rt)){
			throw new SystemException(rt);
		}
		
		
		if(null!=list&&list.size()!=0){			
			for(BaseTableColumn btc:list){				
				if(null==btc.getFdcode()||"".equals(btc.getFdcode())){
					throw new SystemException("字段名称不能为空！");
				}
				
				String result=checkRecord(btc);
				if(!"".equals(result)){
					throw new SystemException(result);
				}
				
/*				//如果手动，不保存业务元素
				if(btc.getIsHand().equals("Y")){
					btc.setBussinessEle(null);										
				}
*/				
				btc.setBaseTable(entity);
				baseTableColumnService.save(btc);
			}
			
		}else{
			throw new SystemException("明细不能为空！");
		}
				
        return object;
	}
	
	
	//未测试
	public String checkBussEle(List<BaseTableColumn> columns){
		String result="";
		List<BussinessEle> eles=new ArrayList<BussinessEle>();
		Set<BussinessEle> set=new TreeSet<BussinessEle>(new Comparator<BussinessEle>() {
			public int compare(BussinessEle o1, BussinessEle o2) {				
				if(o1.getEcode().compareTo(o2.getEcode())>0){
					return 1;
				}else if(o1.getEcode().compareTo(o2.getEcode())<0){
					return -1;
				}
								
				return 0;
			}		  
		});

		for(BaseTableColumn baseTableColumn:columns){
			if(null!=baseTableColumn.getBussinessEle()){
		        if(!baseTableColumn.getBussinessEle().getEcode().equals("")){
		        	eles.add(baseTableColumn.getBussinessEle());
		        	set.add(baseTableColumn.getBussinessEle());		        	
		        	System.out.println("set size is "+set.size());
		        	
		        }
			}
		}
		
		if(set.size()!=0&&set.size()!=eles.size()){
			result="同一个业务对象不能引用多个相同的业务元素！";
		}
						
		return result;
	}
	
	
	@Override
	public int updateHql(BaseTable entity) throws Exception {
		
		Map<String, String> tempStack = entity.getTempStack();
		if(null != tempStack && null != tempStack.get("delIds") && !"".equals(tempStack.get("delIds"))){
			String delIds = tempStack.get("delIds");
			baseTableColumnService.deleteByIds(delIds.split(","));
		}
				
		List<BaseTableColumn> list=entity.getDetails();		
		String rt=checkBussEle(list);		
		if(!"".equals(rt)){
			throw new SystemException(rt);
		}
		
		if(null!=list&&list.size()!=0){
			
			for(BaseTableColumn btc:list){
				
				String rst=checkRecord(btc);
				if(!"".equals(rst)){
					throw new SystemException(rst);
				}
				
				//更新
				if(null!=btc.getId()){				 
					 Map<String, Object> map=new HashMap<String, Object>();
					 map.put("isPk", btc.getIsPk());
					 map.put("fdcode", btc.getFdcode());
					 if(null!=btc.getBussinessEle()){
						 map.put("bussinessEle.ecode", btc.getBussinessEle().getEcode());						 
					 }
					 map.put("isHand", btc.getIsHand());
					 map.put("dataType", btc.getDataType());
					 if(null!=btc.getLen()){
						 map.put("len", btc.getLen());
					 }
					 if(null!=btc.getDecimalLen()){
						 map.put("decimalLen", btc.getDecimalLen());						 
					 }
					 map.put("defaultValue", btc.getDefaultValue());
					 map.put("isImportantKey", btc.getIsImportantKey());
					 map.put("refTab", btc.getRefTab());
					 map.put("refKey", btc.getRefKey());
					 baseTableColumnService.updateById(btc.getId(), map);
				//新增	
				}else{					
					btc.setBaseTable(entity);
					baseTableColumnService.save(btc);					
				}
				
			}
		}
		
		dao.update(entity);	
			
		return 0;
	}
	
	
	
	

	public String activeTable(String[] ids) throws Exception {
		
		int result=0;
		if(null!=ids&&ids.length!=0){			  
			for(String id:ids){
				ISqlMap<String, String, String> sqlMap=new SqlMap<String, String, String>();
				sqlMap.put("baseTable.tabCode", "=", id);
				List<BaseTableColumn> detail=baseTableColumnService.selectHQL(sqlMap);
				
				boolean fg=false;
				for(BaseTableColumn bc:detail){
					if(bc.getIsPk()=='Y'){
						fg=true;
					}					
					if(null!=bc.getRefTab()&&!"".equals(bc.getRefTab())){
						if(null==bc.getRefKey()||"".equals(bc.getRefKey())){
							  throw new  SystemException("表"+id+",字段"+bc.getFdcode()+"引用表没有指定主键，不能够激活！");
						}else{
							String tabCode=bc.getRefTab();
							checkRefKey(detail, tabCode);							
						}							
					}
				}				
				if(!fg){
					  throw new  SystemException("表"+id+"没有主键，不能够激活！");
				}				
								
				if(!isTableExits(id.toUpperCase())){
					String createsql=createTable(detail, id);	
					result=operatePhysicalTable(createsql);
					Map<String, Object> mp=new HashMap<String, Object>();
/*					mp.put("baseTable.id", id);
	                List<TableModel> list=tableModelService.findByMap(mp);
					if(null==list||list.size()==0){
						TableModel tableModel=new TableModel();
						tableModel.setBaseTable(new BaseTable(id));
						tableModelService.create(tableModel);
						
					}*/
					mp.clear();
					mp.put("isActiveable",'Y');
					dao.updateById(id, mp);
					
					
				}else{
					//数据不存在
					if(!isDataExits(id.toUpperCase())){
						String createsql=createTable(detail, id);	
						operatePhysicalTable("DROP TABLE "+id.toUpperCase()+" CASCADE CONSTRAINTS");
						result=operatePhysicalTable(createsql);
					}else{						
						throw new SystemException("表["+id.toUpperCase()+"]已有记录，不能进行激活");				
					}
				}
			}
			
			this.getClass().getResource("");
		}
		
		return null;
	}
	
	
	
	
	
	public String  createTable(List<BaseTableColumn> columns ,String tableName)throws Exception{
		
		String databasetype = dao.getDatabaseType();
		String sql="";
		if(databasetype.equals("Oracle")){
			sql=createOracleSql(columns, tableName);			
		}else if(databasetype.equals("Mysql")){
			sql=createMysqlSql(columns, tableName);
		}else if(databasetype.equals("Sqlserver")){
			//sql=createSqlServerSql(columns, tableName);
		}
		System.out.println(sql);
		return sql;
	}

	
	
	

	/**
	 * 
	 * 功能：<br/>
	 * 创建物理表
	 * @author zp
	 * @version 2013-7-5 下午03:58:10 <br/>
	 */
	public int operatePhysicalTable(String sql)throws Exception{
		int result=0;
		Connection conn=dao.getConnection();
		Statement statement=null;
		statement=conn.createStatement();
		result=statement.executeUpdate(sql);
		return result;
	}
	



	//查询主键被引用的次数
	private int isRefTab(List<BaseTableColumn> column ,String refkeyname ,String refTabName){
		int result=0;
		for(BaseTableColumn btc:column){
			if(null!=btc.getRefTab()){
				if(refTabName.equals(btc.getRefTab())&&refkeyname.equals(btc.getRefKey())){
					result++;
				}
			}
		}
		return result;
	}
	
	
	/**
	 * 
	 * 功能：<br/>
	 * 判断表是否存在
	 * @author zp
	 * @version 2013-7-5 下午03:59:45 <br/>
	 */
	private  boolean isTableExits(String tableName)throws Exception{
		boolean result=false;
		Connection connection=dao.getConnection();
		ResultSet rSet=null;
		try {
			DatabaseMetaData metaData=connection.getMetaData();
			rSet=metaData.getTables(null, null, tableName,null);
			if(rSet.next()){
				result=true;
			}else{
				result=false;
			}			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
		    try {
				if(null!=rSet){					
				  rSet.close();
				}				
				if(null!=connection){
					connection.close();
				}								
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 
	 * 功能：<br/>
	 * 判断某张表是否有数据
	 * @author zp
	 * @version 2013-7-5 下午04:00:36 <br/>
	 */
	private boolean isDataExits(String tableName)throws Exception{
		Connection conn=dao.getConnection();
		ResultSet rSet=null;
		Statement statement=null;
		boolean result=false;
		try {
			statement= conn.createStatement();
			String sql="SELECT * FROM "+tableName+"";
			sql=sql.toUpperCase();
			rSet=statement.executeQuery(sql);
			if(rSet.next()){
				result=true;
			}			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	
	
	
	public void checkRefKey(List<BaseTableColumn> column ,String refTab)throws Exception{
		
		ISqlMap<String, String, String> sqlMap=new SqlMap<String, String, String>();
		sqlMap.put("baseTable.tabCode", "=", refTab);
		//引用的表
		List<BaseTableColumn> detail=baseTableColumnService.selectHQL(sqlMap);
		if(detail.size()!=0){
			//遍历主键
			for(BaseTableColumn bcl:detail){			
				if(bcl.getIsPk()=='Y'){
					int result=isRefTab(column, bcl.getFdcode(), refTab);
					if(result>1){
						  throw new  SystemException("引用表"+refTab+"的主键"+bcl.getRefKey()+"被引用多次，不能激活！");
					}else if(result==1){
						
					}else{
						  throw new SystemException("引用表"+refTab+"的主键没有被完全引用！，不能激活！");
					}					
				}
			}
		}		
	}
	
	
	
	
	
	/**
	 * 
	 * 功能：<br/>
	 * 校验记录的合法性
	 * @author zp
	 * @version 2013-7-8 下午08:33:34 <br/>
	 * @throws Exception 
	 * @throws Exception 
	 */
	public String checkRecord(BaseTableColumn column) throws Exception{
		String type="";
		try {
			type = dao.getDatabaseType();
		} catch (Exception e) {
			e.printStackTrace();
		}
		String result="";
		if(type.equals("Mysql")){
		   result=	checkMysqlRecord(column);
		}
		return result;		
	}
	
	
	private String checkMysqlRecord(BaseTableColumn column) throws Exception{
		
		String result="";
		//如果是手动
		BussinessEle ele = column.getBussinessEle();
		if(null != ele && "".equals(ele.getEcode())){
			if(null==column.getDataType()||"".equals(column.getDataType())){
				result="字段["+column.getFdcode()+"]手动创建数据类型不能为空！";
				return result;
			}			
			if(column.getDataType().toUpperCase().equals("VARCHAR")||column.getDataType().toUpperCase().equals("CHAR")){	
				if(null==column.getLen()){
					result="字段["+column.getFdcode()+"]数据长度不能为空！";
					return result;
				}
			}			
			if(column.getDataType().toUpperCase().equals("DECIMAL")){		
				if(null==column.getLen()||null==column.getDecimalLen()){	
					result="字段["+column.getFdcode()+"]长度和小数位数不能为空！";
					return result;
				}
				
				if(null!=column.getDefaultValue()&&!"".equals(column.getDefaultValue())){
					if(!PapUtil.isNum(column.getDefaultValue())){
						result="字段["+column.getFdcode()+"] 默认值不合法";
						return result;
					}					
				}				
			}
			
			if(column.getDataType().toUpperCase().equals("INT")){
				 if(null!=column.getDefaultValue()&&!"".equals(column.getDefaultValue())){
				    if(!PapUtil.isInt(column.getDefaultValue())){
						result="字段["+column.getFdcode()+"] 默认值不合法";
						return result;
				    }	  					 
				 }
			}
			
		}else{			
			if(null==ele){			
				result="字段["+column.getFdcode()+"]业务元素不能为空！";
				return result;
			}			
			BussinessEle bussinessEle=bussinessEleService.selectById(column.getBussinessEle().getEcode());		
			if(null!=bussinessEle.getDataType()){
				if(bussinessEle.getDataType().toUpperCase().equals("INT")){
					 if(null!=column.getDefaultValue()&&!"".equals(column.getDefaultValue())){
					    if(!PapUtil.isInt(column.getDefaultValue())){
							result="字段["+column.getFdcode()+"] 默认值不合法";
							return result;
					    }	  					 
				    }				
				}
				if(bussinessEle.getDataType().toUpperCase().equals("DECIMAL")){
					 if(null!=column.getDefaultValue()&&!"".equals(column.getDefaultValue())){
						    if(!PapUtil.isNum(column.getDefaultValue())){
								result="字段["+column.getFdcode()+"] 默认值不合法";
								return result;
						    }	  					 
					    }								
				}			}

			
		}
		return result;		
	}
	
	
	
	
	
	//mysql创建语句
	public String createMysqlSql(List<BaseTableColumn> columns ,String tableName)throws Exception{
	    tableName=tableName.toUpperCase();
	    String createsql="";
	    createsql=createsql+"CREATE TABLE "+tableName+"(\n\r";
	    String pk="";
		for(BaseTableColumn btc:columns){
			
			if(btc.getIsPk()=='Y'){
				pk=pk+btc.getFdcode()+",";
			}			
			String result=checkRecord(btc);		
			if(!"".equals(result)){
				throw new SystemException(result);
			}
			
			//手动，根据数据库类型拼接
			if("".equals(btc.getBussinessEle().getEcode())){
				 if(null!=btc.getDataType()&&!btc.getDataType().equals("")){
					 if(btc.getDataType().toUpperCase().equals("VARCHAR")||btc.getDataType().toUpperCase().equals("CHAR")){
							if(null!=btc.getDefaultValue()&&!"".equals(btc.getDefaultValue())){
								createsql=createsql+" "+btc.getFdcode().toUpperCase()+"   "+btc.getDataType().toUpperCase()+"("+btc.getLen()+")  DEFAULT '"+btc.getDefaultValue()+"'  NOT NULL , \n\r";
							}else{
								createsql=createsql+" "+btc.getFdcode().toUpperCase()+"   "+btc.getDataType().toUpperCase()+"("+btc.getLen()+")  NOT NULL  , \n\r";
							}						 						 
					 }else if(btc.getDataType().toUpperCase().equals("FLOAT")||btc.getDataType().toUpperCase().equals("INT")){
							if(null!=btc.getDefaultValue()&&!"".equals(btc.getDefaultValue())){
								createsql=createsql+"  "+btc.getFdcode().toUpperCase()+"  "+btc.getDataType().toUpperCase()+" DEFAULT "+btc.getDefaultValue() +"  NOT NULL  , \n\r";					
							}else{
								createsql=createsql+"  "+btc.getFdcode().toUpperCase()+"  "+btc.getDataType().toUpperCase()+"   NOT NULL  , \n\r";					
						    }						 						 
					 }else if(btc.getDataType().toUpperCase().equals("DECIMAL")){
							if(null!=btc.getDefaultValue()&&!"".equals(btc.getDefaultValue())){
								createsql=createsql+"  "+btc.getFdcode().toUpperCase()+"  DECIMAL("+btc.getLen()+","+btc.getDecimalLen()+") DEFAULT "+btc.getDefaultValue() +"  NOT NULL  , \n\r";					
							}else{
								createsql=createsql+"  "+btc.getFdcode().toUpperCase()+"  DECIMAL("+btc.getLen()+","+btc.getDecimalLen()+")   NOT NULL  , \n\r";					
						    }						 
					 }
					 
				 }
				
			}else{
				//根据业务元素类型拼接
				if(null!=btc.getBussinessEle()){
					String busid=btc.getBussinessEle().getEcode();
					BussinessEle bElement=bussinessEleService.selectById(busid);
					if(bElement.getDataType().toUpperCase().equals("VARTEXT")||bElement.getDataType().toUpperCase().equals("VARDATE")||bElement.getDataType().toUpperCase().equals("VARTIME")){
						if(null!=btc.getDefaultValue()&&!"".equals(btc.getDefaultValue())){
							createsql=createsql+" "+btc.getFdcode().toUpperCase()+"   VARCHAR("+bElement.getLen()+")  DEFAULT '"+btc.getDefaultValue()+"'  NOT NULL , \n\r";
						}else{
							createsql=createsql+" "+btc.getFdcode().toUpperCase()+"   VARCHAR("+bElement.getLen()+")  NOT NULL  , \n\r";
						}
					}else if(bElement.getDataType().toUpperCase().equals("INT")||bElement.getDataType().toUpperCase().equals("FLOAT")){
						if(null!=btc.getDefaultValue()&&!"".equals(btc.getDefaultValue())){
							createsql=createsql+"  "+btc.getFdcode().toUpperCase()+"  "+bElement.getDataType().toUpperCase()+" DEFAULT "+btc.getDefaultValue() +"  NOT NULL  , \n\r";					
						}else{
							createsql=createsql+"  "+btc.getFdcode().toUpperCase()+"  "+bElement.getDataType().toUpperCase()+"   NOT NULL  , \n\r";					
					    }
					}else if(bElement.getDataType().toUpperCase().equals("DECIMAL")||bElement.getDataType().toUpperCase().equals("DECMONEY")){
						if(null!=btc.getDefaultValue()&&!"".equals(btc.getDefaultValue())){
							createsql=createsql+"  "+btc.getFdcode().toUpperCase()+"  DECIMAL("+bElement.getLen()+","+bElement.getDecimalLen()+") DEFAULT "+btc.getDefaultValue() +"  NOT NULL  , \n\r";					
						}else{
							createsql=createsql+"  "+btc.getFdcode().toUpperCase()+"  DECIMAL("+bElement.getLen()+","+bElement.getDecimalLen()+")   NOT NULL  , \n\r";					
					    }
					}else if(bElement.getDataType().toUpperCase().equals("CHAR")){
						if(null!=btc.getDefaultValue()&&!"".equals(btc.getDefaultValue())){
							createsql=createsql+"  "+btc.getFdcode().toUpperCase()+"  CHAR("+bElement.getLen()+") DEFAULT "+btc.getDefaultValue() +"  NOT NULL  , \n\r";					
						}else{
							createsql=createsql+"  "+btc.getFdcode().toUpperCase()+"  CHAR("+bElement.getLen()+")   NOT NULL  ,\n\r";					
					    }				
					}
					
				}else{
					throw new SystemException("不是手动的情况下业务元素不能为空！");					
				}				
			}
			
		}		
		if(!"".equals(pk)){
			pk=pk.substring(0,pk.length()-1).toUpperCase();
			tableName=tableName.toUpperCase();
			createsql=createsql+" CONSTRAINT PK_"+tableName+" PRIMARY KEY ("+pk+") \n\r";		
		}
		createsql=createsql+")";
		System.out.println("====================生成创建sql===========================");
		System.out.println(createsql);		
		return createsql;
	}


	
	
	//oracle数据库的sql
	private String createOracleSql(List<BaseTableColumn> columns ,String tableName) throws Exception{
		
	    tableName=tableName.toUpperCase();
	    String createsql="";
	    createsql=createsql+"CREATE TABLE "+tableName+"(\n\r";
	    String pk="";
		for(BaseTableColumn btc:columns){
			if(btc.getIsPk()=='Y'){
				pk=pk+btc.getFdcode()+",";
			}			
			
			String result=checkRecord(btc);		
			if(!"".equals(result)){
				throw new SystemException(result);
			}
			
			if(btc.getIsHand().equals("Y")){
				 if(null!=btc.getDataType()&&!btc.getDataType().equals("")){
					 if(btc.getDataType().toUpperCase().equals("VARCHAR2")||btc.getDataType().toUpperCase().equals("CHAR")){
							if(null!=btc.getDefaultValue()&&!"".equals(btc.getDefaultValue())){
								createsql=createsql+" "+btc.getFdcode().toUpperCase()+"   "+btc.getDataType().toUpperCase()+"("+btc.getLen()+" BYTE)  DEFAULT '"+btc.getDefaultValue()+"'  NOT NULL , \n\r";
							}else{
								createsql=createsql+" "+btc.getFdcode().toUpperCase()+"   "+btc.getDataType().toUpperCase()+"("+btc.getLen()+" BYTE)  NOT NULL  , \n\r";
							}
					 }else if(btc.getDataType().toUpperCase().equals("FLOAT")||btc.getDataType().toUpperCase().equals("INT")){
							if(null!=btc.getDefaultValue()&&!"".equals(btc.getDefaultValue())){
								createsql=createsql+"  "+btc.getFdcode().toUpperCase()+"  "+btc.getDataType().toUpperCase()+" DEFAULT "+btc.getDefaultValue() +"  NOT NULL  , \n\r";					
							}else{
								createsql=createsql+"  "+btc.getFdcode().toUpperCase()+"  "+btc.getDataType().toUpperCase()+"   NOT NULL  , \n\r";					
						    }						 						 
					 }else if(btc.getDataType().toUpperCase().equals("NUMBER")){
							if(null!=btc.getDefaultValue()&&!"".equals(btc.getDefaultValue())){
								createsql=createsql+"  "+btc.getFdcode().toUpperCase()+"  NUMBER("+btc.getLen()+","+btc.getDecimalLen()+") DEFAULT "+btc.getDefaultValue() +"  NOT NULL  , \n\r";					
							}else{
								createsql=createsql+"  "+btc.getFdcode().toUpperCase()+"  NUMBER("+btc.getLen()+","+btc.getDecimalLen()+")   NOT NULL  , \n\r";					
						    }
					 }
				 }
				
			}else{
				String busid=btc.getBussinessEle().getEcode();
				BussinessEle bElement=bussinessEleService.selectById(busid);
				if(bElement.getDataType().toUpperCase().equals("VARTEXT")||bElement.getDataType().toUpperCase().equals("VARDATE")||bElement.getDataType().toUpperCase().equals("VARTIME")){
					if(null!=btc.getDefaultValue()&&!"".equals(btc.getDefaultValue())){
						createsql=createsql+" "+btc.getFdcode().toUpperCase()+"   VARCHAR2("+bElement.getLen()+" BYTE)  DEFAULT '"+btc.getDefaultValue()+"'  NOT NULL , \n\r";
					}else{
						createsql=createsql+" "+btc.getFdcode().toUpperCase()+"   VARCHAR2("+bElement.getLen()+" BYTE)  NOT NULL  , \n\r";
					}
				}else if(bElement.getDataType().toUpperCase().equals("INT")){
					if(null!=btc.getDefaultValue()&&!"".equals(btc.getDefaultValue())){
						createsql=createsql+"  "+btc.getFdcode().toUpperCase()+"  NUMBER(*,0) DEFAULT "+btc.getDefaultValue() +"  NOT NULL  , \n\r";					
					}else{
						createsql=createsql+"  "+btc.getFdcode().toUpperCase()+"  NUMBER(*,0)   NOT NULL  , \n\r";					
				    }
				}else if(bElement.getDataType().toUpperCase().equals("DECIMAL")||bElement.getDataType().toUpperCase().equals("DECMONEY")){
					if(null!=btc.getDefaultValue()&&!"".equals(btc.getDefaultValue())){
						createsql=createsql+"  "+btc.getFdcode().toUpperCase()+"  NUMBER("+bElement.getLen()+","+bElement.getDecimalLen()+") DEFAULT "+btc.getDefaultValue() +"  NOT NULL  , \n\r";					
					}else{
						createsql=createsql+"  "+btc.getFdcode().toUpperCase()+"  NUMBER("+bElement.getLen()+","+bElement.getDecimalLen()+")   NOT NULL  , \n\r";					
				    }
				}else if(bElement.getDataType().toUpperCase().equals("CHAR")){
					if(null!=btc.getDefaultValue()&&!"".equals(btc.getDefaultValue())){
						createsql=createsql+"  "+btc.getFdcode().toUpperCase()+"  CHAR("+bElement.getLen()+" BYTE) DEFAULT "+btc.getDefaultValue() +"  NOT NULL  , \n\r";					
					}else{
						createsql=createsql+"  "+btc.getFdcode().toUpperCase()+"  CHAR("+bElement.getLen()+" BYTE)   NOT NULL  ,\n\r";					
				    }				
				}
			}
		}		
		if(!"".equals(pk)){
			pk=pk.substring(0,pk.length()-1).toUpperCase();
			tableName=tableName.toUpperCase();
			createsql=createsql+" CONSTRAINT PK_"+tableName+" PRIMARY KEY ("+pk+") \n\r";		
		}
		createsql=createsql+")";
		System.out.println(createsql);
		return createsql;
	}


	/**
	 * 功能: 删除<br/>
	 * 
	 * 重写：孟雪勤 <br/>
	 * 
	 * @version ：2013-12-2 下午04:55:51<br/>
	 * 
	 * @param <ID>
	 * @param PK
	 * @return
	 * @throws Exception <br/>
	 * @see com.zhongpin.pap.base.BaseServiceImpl#deleteByIds(ID[])
	 */
	@Override
	public <ID extends Serializable> int deleteByIds(ID[] PK) throws Exception {
		
		// 条件Map
		SqlMap<String, String, Object> sqlMap = new SqlMap<String, String, Object>();
		// 判断是否被业务元素引用
		sqlMap.put("refTable", "in", PmUtil.arrayToSQLStr((String[])PK));
		List<BussinessEle> eles = bussinessEleService.selectHQL(sqlMap);
		if (null != eles && eles.size() > 0) {
			throw new SystemException("所删除的表已被业务元素引用，不允许删除！");
		}
		
		sqlMap.clear();
		// 删除表管理列明细
		sqlMap.put("baseTable.tabCode", "in", PmUtil.arrayToSQLStr((String[])PK));
		baseTableColumnService.delete(sqlMap);
		
		return dao.deleteByIds(PK);
	}
	
/*	public static void main(String sds []){
		System.out.println(Long.MAX_VALUE);		
	}
*/
	
	
}
