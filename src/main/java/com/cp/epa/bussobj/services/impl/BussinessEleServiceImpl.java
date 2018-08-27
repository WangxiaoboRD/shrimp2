package com.cp.epa.bussobj.services.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;

import com.cp.epa.base.BaseServiceImpl;
import com.cp.epa.bussobj.dao.IBussinessEleDao;
import com.cp.epa.bussobj.entity.BaseTableColumn;
import com.cp.epa.bussobj.entity.BussinessEle;
import com.cp.epa.bussobj.entity.BussinessEleDetail;
import com.cp.epa.bussobj.services.IBaseTableColumnService;
import com.cp.epa.bussobj.services.IBussinessEleDetailService;
import com.cp.epa.bussobj.services.IBussinessEleService;
import com.cp.epa.exception.SystemException;
import com.cp.epa.permission.entity.AuthField;
import com.cp.epa.permission.services.IAuthFieldService;
import com.cp.epa.permission.utils.PmUtil;
import com.cp.epa.utils.ISqlMap;
import com.cp.epa.utils.SqlMap;
import com.cp.epa.utils.TypeUtil;
/**
 * 
 * 类名：BussinessEleServiceImpl  <br />
 *
 * 功能：
 *
 * @author zp <br />
 * 创建时间：2013-7-1 上午10:33:21  <br />
 * @version 2013-7-1
 */
public class BussinessEleServiceImpl extends BaseServiceImpl<BussinessEle, IBussinessEleDao> implements IBussinessEleService{

	
	@Autowired
	private IBussinessEleDetailService bussinessEleDetailServcie;
	
	/** 表管理列业务注册 */
	@Resource
	private IBaseTableColumnService baseTableColumnService;
	/** 权限字段业务注册 */
	@Resource
	private IAuthFieldService authFieldService;
	
	@Override
	public List<BussinessEle> selectAll() throws Exception {
		
		
	    String type	=dao.getDatabaseType();
		List<BussinessEle> result=new ArrayList<BussinessEle>();		

		if(type.equals("Oracle")||type.equals("Mysql")){
			result.add(new BussinessEle("int", "整数"));
			//result.add(new BussinessEle("float", "浮点"));
			result.add(new BussinessEle("decimal", "小数"));
			result.add(new BussinessEle("varText", "文本"));
			result.add(new BussinessEle("decMoney", "货币"));
			result.add(new BussinessEle("varDate", "日期"));
			result.add(new BussinessEle("varTime", "时间"));
		}
		return result;
	}
	
	
	
	@Override
	public Object save(BussinessEle entity) throws Exception {

        BussinessEle bussinessEle=dao.selectById(entity.getEcode());
		
        if(null!=bussinessEle){
        	throw new SystemException("编码["+entity.getEcode()+"]已存在！");
        }
        
		Object object=dao.insert(entity);
		
		List<BussinessEleDetail> list=entity.getDetails();
		
		if(entity.getValueType().equals("3")){
									
			for(BussinessEleDetail bed:list){		
				
				BussinessEleDetail bDetail=bussinessEleDetailServcie.selectById(bed.getDcode());
				if(null!=bDetail){
					throw new SystemException("编码["+bed.getDcode()+"]已存在！");
				}
				
				bed.setBussinessEle(entity);
				bussinessEleDetailServcie.save(bed);
			}						
		}
				
		return object;
	}
	
	
	@Override
	public int updateHql(BussinessEle entity) throws Exception {
		
		BussinessEle ele = dao.selectById(entity.getEcode());
		
		TypeUtil.copy(entity, ele);
		
		dao.update(ele);
		
		Map<String, String> tempStack = entity.getTempStack();
		if(null != tempStack && null != tempStack.get("delIds") && !"".equals(tempStack.get("delIds"))){			
			//删除业务元素明细
			String delIds = tempStack.get("delIds");
			bussinessEleDetailServcie.deleteByIds(delIds.split(","));
		}
		
		
		if(!entity.getValueType().equals("3")){
		   //如果不是固定值，删除
			ISqlMap<String, String, String> sqlMap=new SqlMap<String, String, String>();
			sqlMap.put("bussinessEle.ecode", "=", entity.getEcode());
			List<BussinessEleDetail> details=bussinessEleDetailServcie.selectHQL(sqlMap);
			if(null!=details&&details.size()!=0){
				bussinessEleDetailServcie.delete(details);
			}
		}else{
			
			List<BussinessEleDetail> list=entity.getDetails();			
			Set<BussinessEleDetail> set=new TreeSet<BussinessEleDetail>(new Comparator<BussinessEleDetail>() {
				public int compare(BussinessEleDetail o1, BussinessEleDetail o2) {					
					if(o1.getDcode().compareTo(o2.getDcode())>0){
						return 1;
					}
					if(o1.getDcode().compareTo(o2.getDcode())<0){
						return -1;
					}
					return 0;
				}			
			});
			
			if(null!=list&&list.size()!=0){		
				
				for(BussinessEleDetail bDetail:list){
					if(!set.add(bDetail)){
						throw new SystemException("编码["+bDetail.getDcode()+"]重复！");
					}					
				}
				for(BussinessEleDetail bed:list){
					//新增的
					if(bed.getBussinessEle().getEcode().equals("")){						
						bed.setBussinessEle(entity);
						bussinessEleDetailServcie.save(bed);
					}else{
						
						ISqlMap<String, String, String> sqlMap=new SqlMap<String, String, String>();
						sqlMap.put("dcode", "=", bed.getDcode());

					   List<BussinessEleDetail>	 lDetails=bussinessEleDetailServcie.selectHQL(sqlMap);
						if(null!=lDetails&&lDetails.size()>1){
							throw new SystemException("编码["+bed.getDcode()+"]重复！");
						}
						
						//更新的
					    Map<String, Object> map2=new HashMap<String, Object>();
					    map2.put("dcode", bed.getDcode());
					    map2.put("value", bed.getValue());
					    map2.put("spareField1", bed.getSpareField1());
					    map2.put("spareField2", bed.getSpareField2());
					    bussinessEleDetailServcie.updateById(bed.getDcode(), map2);
					}					
				}				
			}
		}
				
		return 1;
	}
	
	/**
	 * 通过条件获取业务元素
	 * 条件是 查询 有固定值与参考表的元素
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Aug 31, 2013 10:25:54 AM <br/>
	 */
	public List<BussinessEle> getBussEle()throws Exception{
		//构建条件
		SqlMap<String,String,String> sqlMap = new SqlMap<String,String,String>();
		sqlMap.put("valueType", "in", "2,3");
		//查询
		List<BussinessEle> blist = dao.selectByConditionHQL(sqlMap);
		return blist;
	}



	/**
	 * 功能: 删除<br/>
	 * 
	 * 重写：孟雪勤 <br/>
	 * 
	 * @version ：2013-12-2 下午03:38:45<br/>
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
		// 判断是否被表管理引用
		sqlMap.put("bussinessEle.ecode", "in", PmUtil.arrayToSQLStr((String[])PK));
		List<BaseTableColumn> columns = baseTableColumnService.selectHQL(sqlMap);
		if (null != columns && columns.size() > 0) {
			throw new SystemException("所删除的业务元素已被表管理列明细引用，不允许删除！");
		}
		
		// 判断是否被权限字段引用
		List<AuthField> fields = authFieldService.selectHQL(sqlMap);
		if (null != fields && fields.size() > 0) {
			throw new SystemException("所删除的业务元素已被权限字段引用，不允许删除！");
		}
		
		// 删除业务元素明细
		bussinessEleDetailServcie.delete(sqlMap);
		
		return dao.deleteByIds(PK);
	}
}
