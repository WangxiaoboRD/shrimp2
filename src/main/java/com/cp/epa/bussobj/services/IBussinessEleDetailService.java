package com.cp.epa.bussobj.services;

import java.util.List;

import com.cp.epa.base.IBaseService;
import com.cp.epa.bussobj.entity.BussinessEleDetail;
/**
 * 
 * 类名：IBussinessEleDetailServcie  <br />
 *
 * 功能：
 *
 * @author zp <br />
 * 创建时间：2013-7-2 上午09:44:31  <br />
 * @version 2013-7-2
 */
public interface IBussinessEleDetailService extends IBaseService<BussinessEleDetail>{
	/**
	 * 获取指定业务元素的业务元素明细信息
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Sep 3, 2013 3:59:37 PM <br/>
	 */
	List<BussinessEleDetail> getBussEleDetail(String id)throws Exception;
	
	/**
	 * 
	 * 功能:配种单弹出框生猪状态选择
	 * @author:wxb
	 * @data:2016-12-1下午09:56:19
	 * @file:IBussinessEleDetailService.java
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	List<BussinessEleDetail> selectBreed(BussinessEleDetail entity)throws Exception;
	}
