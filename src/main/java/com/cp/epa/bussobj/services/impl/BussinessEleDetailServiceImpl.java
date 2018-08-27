package com.cp.epa.bussobj.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cp.epa.base.BaseServiceImpl;
import com.cp.epa.bussobj.dao.IBussinessEleDetailDao;
import com.cp.epa.bussobj.entity.BussinessEleDetail;
import com.cp.epa.bussobj.services.IBussinessEleDetailService;
import com.cp.epa.utils.SqlMap;
/**
 * 
 * 类名：BussinessEleDetailServiceImpl  <br />
 *
 * 功能：
 *
 * @author zp <br />
 * 创建时间：2013-7-2 上午09:46:47  <br />
 * @version 2013-7-2
 */
@Service
public class BussinessEleDetailServiceImpl extends BaseServiceImpl<BussinessEleDetail, IBussinessEleDetailDao> implements IBussinessEleDetailService{
	/**
	 * 获取指定业务元素的业务元素明细信息
	 * 功能: <br/>
	 * 
	 * 重写：杜中良 <br/>
	 * 
	 * @version ：Sep 3, 2013 4:04:02 PM<br/>
	 * 
	 * @param id
	 * @return <br/>
	 * @see com.zhongpin.pap.bussobj.services.IBussinessEleDetailService#getBussEleDetail(java.lang.String)
	 */
	public List<BussinessEleDetail> getBussEleDetail(String id)throws Exception {
		// TODO Auto-generated method stub
		
		SqlMap<String,String,String> sqlMap = new SqlMap<String,String,String>();
		sqlMap.put("bussinessEle.ecode", "=", id);
		List<BussinessEleDetail> lbed = dao.selectByConditionHQL(sqlMap);
		sqlMap.clear();
		return lbed;
	}

	/**
	 * 
	 * 功能:配种单弹出框生猪状态选择
	 * 重写:wxb
	 * 2016-12-1
	 * @see com.zd.epa.bussobj.services.IBussinessEleDetailService#selectBreed(com.zd.epa.bussobj.entity.BussinessEleDetail)
	 */
	@Override
	public List<BussinessEleDetail> selectBreed(BussinessEleDetail entity)
			throws Exception {
		SqlMap<String,String,String> sqlMap = new SqlMap<String,String,String>();
		sqlMap.put("bussinessEle.ecode", "=", "PIGSTATE");
		sqlMap.put("dcode", "in", "'DAIYUN','LIUCHAN','KONGHUAI','FANQING'");
		List<BussinessEleDetail> breedlist = dao.selectByConditionHQL(sqlMap);
		sqlMap.clear();
		return breedlist;
		// TODO Auto-generated method stub
	}
	
	
	

}
