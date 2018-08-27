package com.cp.epa.bussobj.services.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.persistence.Table;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.cp.epa.base.BaseServiceImpl;
import com.cp.epa.bussobj.dao.IBussObjPropertyDao;
import com.cp.epa.bussobj.entity.BaseTableColumn;
import com.cp.epa.bussobj.entity.BussObjProperty;
import com.cp.epa.bussobj.services.IBaseTableColumnService;
import com.cp.epa.bussobj.services.IBussObjPropertyService;
import com.cp.epa.bussobj.utils.DatabaseTypeUtil;
import com.cp.epa.exception.SystemException;
import com.cp.epa.utils.ISqlMap;
import com.cp.epa.utils.Pager;
import com.cp.epa.utils.SqlMap;
/**
 * 
 * 类名：BussObjPropertyServiceImpl  <br />
 *
 * 功能：
 *
 * @author zp <br />
 * 创建时间：2013-8-8 上午10:44:44  <br />
 * @version 2013-8-8
 */
public class BussObjPropertyServiceImpl extends BaseServiceImpl<BussObjProperty, IBussObjPropertyDao> implements IBussObjPropertyService{

	/** 表管理列业务注册 */
	@Autowired
	private IBaseTableColumnService baseTableColumnService;
	
	/**
	 * 功能: 查询业务对象属性<br/>
	 * 
	 * 重写：孟雪勤 <br/>
	 * 
	 * @version ：2014-9-23 下午04:52:37<br/>
	 * 
	 * @param e
	 * @param page
	 * @param objName
	 * @throws Exception <br/>
	 * @see com.zhongpin.pap.bussobj.services.IBussObjPropertyService#loadBussObjProperty(com.zhongpin.pap.bussobj.entity.BussObjProperty, com.zhongpin.pap.utils.Pager, java.lang.String)
	 */
	public void loadBussObjProperty(BussObjProperty e, Pager<BussObjProperty> page, String objName)throws Exception {
		
	    ISqlMap<String, String, Object> sqlMap=new SqlMap<String, String, Object>();
	    sqlMap.put("className", "=", e.getClassName());
	    sqlMap.put("isRoot", "=", "Y");
	    
	    List<BussObjProperty> bussObjProperties=dao.selectByConditionHQL(sqlMap);	
	    sqlMap.clear();
	    if(null!=bussObjProperties&&bussObjProperties.size()!=0){
	    	
	    	String fullClassName=bussObjProperties.get(0).getClassName();
       	    Class<?> cll=Class.forName(fullClassName);
       	    Table bussEle=cll.getAnnotation(Table.class);
       	    String  table=cll.getSimpleName();
       	    if(null!=bussEle){
       	    	String tablename=bussEle.name();
       	    	if(null!=tablename){
       	    		table=tablename;       	    		
       	    	}      	    	
       	    }
       	    //查询出对应的表字段信息
       	    List<BaseTableColumn> refList=baseTableColumnService.selectBySingletAll("baseTable.tabCode",table);
       	    
       	    //类属性		    
		    List<BussObjProperty> list=dao.selectBySingletAll("pid", bussObjProperties.get(0).getId());		    
		    if(null!=list&&list.size()!=0){
		    	List<BussObjProperty> nlist = new ArrayList<BussObjProperty>(); // 新建该nlist作用主要是将list持久化对象变成游离状态的对象，否则会出现问题
		    	for(BussObjProperty bop:list){		
		    		
		    		String quoteObjPropertyName = objName;
	    		    if (null != quoteObjPropertyName) {
	    		    	quoteObjPropertyName = quoteObjPropertyName + "." + bop.getPropertyName();
	    		    	bop.setQuoteObjPropertyName(quoteObjPropertyName);
	    		    }else {
	    		    	bop.setQuoteObjPropertyName(bop.getPropertyName());
	    		    }
		    		    
		    	   String fieldname=bop.getFieldName();		    	   
		    	   if(null!=refList&&refList.size()!=0){		 
		    		   boolean fg=false;
		    		   for(BaseTableColumn bColumn:refList){
		    			    if(bColumn.getFdcode().equals(fieldname)){
		    			    	
		    			    	bop.setIsPk(bColumn.getIsPk().toString());
		    			    	if(null!=bColumn.getBussinessEle()){		    			  
		    			    		String type=new DatabaseTypeUtil().getDataType(bColumn.getBussinessEle().getDataType());
		    			    		bop.setFieldtype(type);
		    			    		if(null!=bColumn.getBussinessEle().getLen()){
		    			    			bop.setFieldLen(bColumn.getBussinessEle().getLen().toString());		    			    			
		    			    		    
		    			    		}
		    			    		bop.setBussEleCode(bColumn.getBussinessEle().getEcode());
		    			    		bop.setDescs(bColumn.getBussinessEle().getEname());
		    			    		
		    			    	}else{
		    			    		bop.setFieldtype(bColumn.getDataType());
		    			    		if(null!=bColumn.getLen()){
		    			    			bop.setFieldLen(bColumn.getLen().toString());		    			    				    			    			
		    			    		}
		    			    		bop.setDescs(bColumn.getDescs());
		    			    	}
		    			    	
		    			    	if("Y".equals(bColumn.getIsImportantKey())){
		    			    		bop.setIsImportantKey("Y");
		    			    	}else{
		    			    		bop.setIsImportantKey("N");
		    			    	}
		    			    	
		    			    	fg=true;
		    			    	bop.setBtc(bColumn);
		    			    	break;		    			    	
		    			    }		    			   		    			   
		    		   }
			    	    if(!fg){
			    	    	bop.setFieldtype("无对应类型");
			    	    }
		    	      }

		    	   BussObjProperty newPro = new BussObjProperty();
		    	   BeanUtils.copyProperties(bop, newPro); // 将持久状态对象转换为游离态对象，否则会出现问题
		    	   nlist.add(newPro);
		    		if(null!=bop.getIsBussObj()){			    			
			    		if(bop.getIsBussObj().equals("Y")){	    			
			    		    bop.setIsexpand("false");
			    			loadBussObjProperty(newPro, page, bop.getQuoteObjPropertyName());			    		 
			    		}
		    		}		    		
		    	}
		       if(null!=nlist && nlist.size()!=0){
		    	   Collections.sort(nlist, new Comparator<BussObjProperty>() {
					   public int compare(BussObjProperty o1, BussObjProperty o2) {					    
						   if(null!=o1.getIsPk()){
							   if("Y".equals(o1.getIsPk())){
								   return 1;
							   }else{
								   if(null!=o2.getIsPk()){
									   if("Y".equals(o2.getIsPk())){
										   return -1;
									   }else{
										   return 0;									   }
									   
								   }else{
									   return 0;
								   }
							   }
						   }						   
						   return 0;
					  }				   
		    	   });
		    	   Collections.reverse(nlist);		    	   
		    	   e.setChildren(nlist);		    	   
		       }
		       List<BussObjProperty> result=new ArrayList<BussObjProperty>();
		       result.add(e);
		       page.setResult(result);
		    }	    	
	    }
		
	}

	/**
	 * 功能: <br/>
	 * 查找关键属性
	 * 重写：zp <br/>
	 * 
	 * @version ：2013-9-6 上午11:17:25<br/>
	 * 
	 * @param fullClassName
	 * @param filter
	 * @return
	 * @throws Exception <br/>
	 * @see com.zhongpin.pap.bussobj.services.IBussObjPropertyService#selectBaseTableColumns(java.lang.String, boolean)
	 */
	public List<BaseTableColumn> selectBaseTableColumns(String fullClassName,boolean filter)
			throws Exception {

	    ISqlMap<String, String, Object> sqlMap=new SqlMap<String, String, Object>();
	    sqlMap.put("className", "=", fullClassName);
	    sqlMap.put("isRoot", "=", "Y");	    
	    List<BussObjProperty> bussObjProperties=dao.selectByConditionHQL(sqlMap);	 
	    List<BaseTableColumn> result=null;
	    if(null!=bussObjProperties&&bussObjProperties.size()!=0){
	    	result=new ArrayList<BaseTableColumn>();
		    ISqlMap<String, String, Object> sm=new SqlMap<String, String, Object>();
		    sm.put("pid", "=", bussObjProperties.get(0).getId());		 
		    //业务对象属性
		    List<BussObjProperty> list=dao.selectByConditionHQL(sm);		    		    
       	    Class<?> cll=Class.forName(fullClassName);
       	    Table bussEle=cll.getAnnotation(Table.class);
       	    String  table=cll.getSimpleName();
       	    if(null!=bussEle){
       	    	String tablename=bussEle.name();
       	    	if(null!=tablename){
       	    		table=tablename;       	    		
       	    	}      	    	
       	    }
       	    
       	    ISqlMap<String, String, Object> sMap=new SqlMap<String, String, Object>();
       	    sMap.put("baseTable.tabCode", "=", table);		    
       	    //查询出对应的表字段信息
       	    List<BaseTableColumn> refList=baseTableColumnService.selectHQL(sMap);       	    
       	    if(null!=list&&list.size()!=0){
       	    	for(BussObjProperty bop:list){
       	    		String fieldname=bop.getFieldName();   
       	    		if(null!=refList&&refList.size()!=0){
           	    		for(BaseTableColumn btc:refList){
           	    			if(fieldname.equals(btc.getFdcode())){
           	    				if(filter){
               	    				if(btc.getIsImportantKey().equals("Y")){  
           	    					  if(null!=btc.getBussinessEle()){
           	    						  String datatype=btc.getBussinessEle().getValueType();
           	    						  if(datatype.equals("2")||datatype.equals("3")){
           	    							  result.add(btc);       	    						       	    						  
           	    						  }       	    						
           	    					  }   	    					
               	    				}
           	    				}else{
           	    					result.add(btc);
           	    				}
         	    			}
          	    		}
            	    }
      	    	}
       	    } 	    	
	    }		
		return result;
	}


	/**
	 * 功能: 根据条件查询业务对象属性<br/>
	 * 
	 * 重写：孟雪勤 <br/>
	 * 
	 * @version ：2013-11-21 下午04:03:32<br/>
	 * 
	 * @param fullClassName
	 * @param tabFieldCode
	 * @return
	 * @throws Exception <br/>
	 * @see com.zhongpin.pap.bussobj.services.IBussObjPropertyService#selectPropertyName(java.lang.String, java.lang.String)
	 */
	public String selectPropertyName(String fullClassName, String tabFieldCode) throws Exception {
		if (null == fullClassName || null == tabFieldCode) {
			return null;
		}
		
		// 查询参数
		SqlMap<String, String, Object> sqlMap = new SqlMap<String, String, Object>();
		
		sqlMap.put("className", "=", fullClassName);
		sqlMap.put("isRoot", "=", "Y");
		
		List<BussObjProperty> propertys = dao.selectByConditionHQL(sqlMap);
		sqlMap.clear();
		if (null != propertys && propertys.size() > 0) {
			sqlMap.put("pid", "=", propertys.get(0).getId());
		}else {
			throw new SystemException("[" + fullClassName + "]类全名信息在业务对象属性表中不存在！");
		}
		
		// 对象表字段
		sqlMap.put("fieldName", "=", tabFieldCode.toLowerCase());
		
		List<BussObjProperty> properties = dao.selectByConditionHQL(sqlMap);
		if (null != properties && properties.size() > 0) {
			return properties.get(0).getPropertyName();
		}
		
		return null;
	}

}
