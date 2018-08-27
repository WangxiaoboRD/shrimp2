package com.cp.epa.bussobj.services.impl;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Table;

import com.cp.epa.base.BaseServiceImpl;
import com.cp.epa.bussobj.dao.IBaseTableColumnDao;
import com.cp.epa.bussobj.entity.BaseTableColumn;
import com.cp.epa.bussobj.services.IBaseTableColumnService;
import com.cp.epa.utils.ISqlMap;
import com.cp.epa.utils.SqlMap;
/**
 * 
 * 类名：BaseTableColumnServiceImpl  <br />
 *
 * 功能：
 *
 * @author zp <br />
 * 创建时间：2013-7-5 上午09:35:43  <br />
 * @version 2013-7-5
 */
public class BaseTableColumnServiceImpl extends BaseServiceImpl<BaseTableColumn, IBaseTableColumnDao> implements IBaseTableColumnService{

	public List<BaseTableColumn> loadColumns() throws Exception {
		List<BaseTableColumn> list=new ArrayList<BaseTableColumn>();
		String type=dao.getDatabaseType();
		if(type.equals("Mysql")){
			
			list.add(new BaseTableColumn("int"));
			list.add(new BaseTableColumn("varchar"));
			list.add(new BaseTableColumn("char"));
			list.add(new BaseTableColumn("float"));
			list.add(new BaseTableColumn("decimal"));
			
		}else if(type.equals("Oracle")){
			list.add(new BaseTableColumn("int"));
			list.add(new BaseTableColumn("varchar2"));
			list.add(new BaseTableColumn("char"));
			list.add(new BaseTableColumn("float"));
			list.add(new BaseTableColumn("number"));			
		}
		return list;
	}

	
	@Override
	public Object save(BaseTableColumn entity) throws Exception {
		return super.save(entity);
	}

	@SuppressWarnings("unchecked")
	public BaseTableColumn loadDetail(BaseTableColumn baseTableColumn)
			throws Exception {
		ISqlMap<String, String, String> sqlMap=new SqlMap<String, String, String>();
		if(null!=baseTableColumn.getBaseTable()){
			Class class1=Class.forName(baseTableColumn.getBaseTable().getTabCode());
			String fieldname=baseTableColumn.getFdcode();
			String md="get"+fieldname.substring(0, 1).toUpperCase()+fieldname.substring(1);			
			Method method=class1.getMethod(md, null);	
			//
		    Column column=method.getAnnotation(Column.class);
			if(null!=column){
				String mapfield=column.name();
				if(null!=mapfield&&!"".equals(mapfield)){
					sqlMap.put("fdcode", "=", mapfield);
				}				
			}else{
				sqlMap.put("fdcode", "=", baseTableColumn.getFdcode());
			}
			String shortname=baseTableColumn.getBaseTable().getTabCode();
			shortname=shortname.substring(shortname.lastIndexOf(".")+1);
			Table table=(Table) class1.getAnnotation(Table.class);
			if(null!=table){
				String tablename=table.name();
				if(null!=tablename&&!"".equals(tablename)){
					sqlMap.put("baseTable.tabCode", "=",tablename);												
				}else{
					sqlMap.put("baseTable.tabCode", "=",shortname);							
				}
			}else{
				sqlMap.put("baseTable.tabCode", "=",shortname);
			}
		}
		List<BaseTableColumn> list=dao.selectByConditionHQL(sqlMap);
		if(null!=list&&list.size()!=0){
			return list.get(0);
		}else{			
			BaseTableColumn base=new BaseTableColumn();			
			base.setError("未在表管理找到对应字段！请查看");
			return base;
		}
	}

	/**
	 * 功能: 根据字段编码和表编码查询字段描述信息<br/>
	 * 重写：孟雪勤 <br/>
	 * @version ：2013-11-21 下午03:41:56<br/>
	 * @param fdcode
	 * @param tabCode
	 * @return
	 * @throws Exception <br/>
	 * @see com.zhongpin.pap.bussobj.services.IBaseTableColumnService#selectDesc(java.lang.String, java.lang.String)
	 */
	public String selectDesc(String fdcode, String tabCode) throws Exception {
		if (null == fdcode || null == tabCode) 
			return null;
		SqlMap<String, String, String> sqlMap = new SqlMap<String, String, String>();
		// 字段编码
		sqlMap.put("fdcode", "=", fdcode);
		// 表编码
		sqlMap.put("baseTable.tabCode", "=", tabCode.toLowerCase());
		List<BaseTableColumn> columns = dao.selectByConditionHQL(sqlMap);
		if (null != columns && columns.size() > 0) {
			return columns.get(0).getDescs();
		}
		
		return null;
	}
	
	public static void main(String [] nss){
		String name="pap.sss.good";
		
		System.out.println(name.substring(name.lastIndexOf(".")+1));
	}
}
