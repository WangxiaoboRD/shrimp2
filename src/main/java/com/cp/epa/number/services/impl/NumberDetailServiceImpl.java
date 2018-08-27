package com.cp.epa.number.services.impl;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.cp.epa.base.BaseServiceImpl;
import com.cp.epa.exception.SystemException;
import com.cp.epa.number.dao.INumberDetailDao;
import com.cp.epa.number.entity.NumberDetail;
import com.cp.epa.number.services.INumberDetailService;
import com.cp.epa.number.services.INumberService;
import com.cp.epa.number.entity.Number;

public class NumberDetailServiceImpl extends BaseServiceImpl<NumberDetail,INumberDetailDao> implements INumberDetailService {
	
	@Autowired
	private INumberService numberService;
	
	

	/**
	 * 删除
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Sep 5, 2013 4:20:51 PM <br/>
	 */
	public <ID extends Serializable> int deleteByIds(ID[] PK)throws Exception{
		if(PK == null )
			throw new SystemException("请选择要删除的明细");
		for(ID in : PK){
			NumberDetail _n =dao.selectById(in);
			if(_n.getCurrentNumber() != null && !"".equals(_n.getCurrentNumber()))
				throw new SystemException("已经使用的明细不能删除");
		}
		dao.deleteByIds(PK);
		return PK.length;
	}
	
	/**
	 * 保存号码对象明细
	 * 功能: <br/>
	 * 重写：杜中良 <br/>
	 * @version ：Sep 4, 2013 3:11:06 PM<br/>
	 * @param entity
	 * @return
	 * @throws Exception <br/>
	 * @see com.zhongpin.pap.base.BaseServiceImpl#save(com.zhongpin.pap.base.BaseEntity)
	 */
	public Object save(NumberDetail entity) throws Exception{
		// TODO Auto-generated method stub
		Number number = numberService.selectById(entity.getNumber().getId());
		if(entity == null)
			throw new SystemException("明细信息为空！");
		if(number == null)
			throw new SystemException("号码对象为空！");
		//验证
		isCheck(entity,number);
		//判定当年标示与子对象标示不存在时明细信息起始值与终止值不能够重叠	
		
		if(!isOverlap(entity,number))
			throw new SystemException("号码起始值与终止值不能重叠");
		return dao.insert(entity);
		
	}
	
	/**
	 * 更新明细
	 * 功能: <br/>
	 * 
	 * 重写：杜中良 <br/>
	 * 
	 * @version ：Sep 5, 2013 9:34:13 AM<br/>
	 * 
	 * @param entity
	 * @return
	 * @throws Exception <br/>
	 * @see com.zhongpin.pap.base.BaseServiceImpl#updateHql(com.zhongpin.pap.base.BaseEntity)
	 */
	public int updateHql(NumberDetail entity) throws Exception{
		
		if(entity.getCurrentNumber() != null && !"".equals(entity.getCurrentNumber())){
			if(entity.getEndNumber() < Integer.parseInt(entity.getCurrentNumber()))
					throw new SystemException("当前缓存值必须在起始值与结束值之间");
			NumberDetail _tempDetail = dao.selectById(entity.getId());
			_tempDetail.setEndNumber(entity.getEndNumber());
			_tempDetail.setMarkExt(entity.getMarkExt());
			
			entity = _tempDetail;
		}
		Number number = numberService.selectById(entity.getNumber().getId());
		if(entity == null)
			throw new SystemException("明细信息为空！");
		if(number == null)
			throw new SystemException("号码对象为空！");
		//验证
		isCheck(entity,number);
		//判定当年标示与子对象标示不存在时明细信息起始值与终止值不能够重叠	
		if(!isOverlap(entity,number))
			throw new SystemException("号码起始值与终止值不能重叠");
		if(entity.getCurrentNumber() != null && !"".equals(entity.getCurrentNumber())){
			dao.update(entity);
			return 1;
		}
		return dao.updateHql(entity);
	}
	
	//其他验证
	@SuppressWarnings("unused")
	private void isCheck(NumberDetail entity,Number number) throws Exception{
		//当明细信息中起始值的长度或者终止值的长度 + 前缀值 大于号码总长度报错
		int startLength = 0;
		int endLength = 0;
		if(entity.getPrefix() == null || "".equals(entity.getPrefix())){
			startLength = (entity.getStartNumber()+"").length();
			endLength = (entity.getEndNumber()+"").length();
		}else{
			startLength = (entity.getStartNumber()+"").length()+entity.getPrefix().length();
			endLength = (entity.getEndNumber()+"").length()+entity.getPrefix().length();
		}
		
		if(startLength > number.getLength() || endLength>number.getLength())
			throw new SystemException("起始值或终止值加上前缀的长度和不能大于总长度");
		//当一个明细信息的起始值大于终止值时报错
		if(entity.getStartNumber() > entity.getEndNumber()){
			throw new SystemException("起始值不能大于终止值！");
		}
		//当开启了年份标示时，年份信息不能为空
		if(number.getMarkYear()==1){
			if(entity.getYear()==null || "".equals(entity.getYear())){
				throw new SystemException("年份不能为空！");
			}
		}
		//当开启子对象标示时，子对象不能为空
		if(number.getMarkSub() !=null && !"".equals(number.getMarkSub())){
			if(entity.getSubobject()==null || "".equals(entity.getSubobject())){
				throw new SystemException("子对象不能为空！");
			}
		}
		//判定号码段信息不能够重复
		List<NumberDetail> details = number.getNumberDetails();
		//假设details里面包含有要操作的对象时，请先进行删除
		if(details != null && details.size()>0){
			for(int i=0;i<details.size();i++){
				if(details.get(i).getId().equals(entity.getId())){
					details.remove(i);
				}
			}
			//if(details.contains(entity))
			//	details.remove(entity);
		}
		for(NumberDetail _nd : details){
			if(entity.equals(_nd)){
				throw new SystemException("号码段信息不能够重复！");
			}
		}
	}
	
	//判定当年标示与子对象标示不存在时明细信息起始值与终止值不能够重叠	
	@SuppressWarnings({ "unchecked", "unused" })
	private boolean isOverlap(NumberDetail entity,Number number){
		//获得当前对象的所有明细
		List<NumberDetail> nds = number.getNumberDetails();
		//假设details里面包含有要操作的对象时，请先进行删除
		if(nds != null && nds.size()>0){
			for(int i=0;i<nds.size();i++){
				if(nds.get(i).getId().equals(entity.getId())){
					nds.remove(i);
				}
			}
			//if(details.contains(entity))
			//	details.remove(entity);
		}
		//对获取的明细进行验证处理
		if (nds == null || nds.size()==0)
			return true;
		nds.add(entity);
		
		//当没有年份标示与子对象标示时，判定重叠
		if(number.getMarkYear()==0 && (number.getMarkSub()==null || "".equals(number.getMarkSub()))){
			//通过起始值进行排序
			Collections.sort(nds, new Comparator() {  
		          public int compare(Object a, Object b) {  
		            long one = ((NumberDetail)a).getStartNumber();  
		            long two = ((NumberDetail)b).getStartNumber();   
		            if(one > two)
		            	return 1;
		            else if(one == two)
		            	return 0;
		            else
		            	return -1;
		          }  
		    });  
			//通过第一条明细的结束值小于第二条明细的起始值，判断明细信息中起始值与结束值有没有重叠
			for(int i=0;i<nds.size();i++){
				NumberDetail _d = nds.get(i);
				if(i+1 < nds.size()){
					NumberDetail _d2 = nds.get(i+1);
					if(_d.getEndNumber() >= _d2.getStartNumber())
						return false;
				}
			}
		}
		nds.remove(entity);
		return true;
	}
	/*
	//判定当年标示与子对象标示不存在时明细信息起始值与终止值不能够重叠	
	@SuppressWarnings({ "unchecked", "unused" })
	private boolean isRepeat(NumberDetail entity,Number number){
		//获得当前对象的所有明细
		List<NumberDetail> nds = number.getNumberDetails();
		//对获取的明细进行验证处理
		if (nds == null || nds.size()==0)
			return true;
		String newDetail = entity.getNumberScope();
		if(number.getMarkYear()==1)
			newDetail += entity.getYear();
		if(number.getMarkSub() != null && !"".equals(number.getMarkSub()))
			newDetail += entity.getSubobject();
		List<String> oldDetais = new ArrayList<String>();
		for(NumberDetail nd : nds){
			String oldDetail=nd.getNumberScope();
			if(number.getMarkYear()==1)
				oldDetail += nd.getYear();
			if(number.getMarkSub() != null && !"".equals(number.getMarkSub()))
				oldDetail += nd.getSubobject();
			oldDetais.add(oldDetail);
		}
		
		for(String _old : oldDetais){
			if(newDetail.equals(_old))
				throw new SystemException("号码对象明细不能够重复！");
		}
		return true;
	}*/
}
