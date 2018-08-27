package com.cp.epa.number.services.impl;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.cp.epa.base.BaseServiceImpl;
import com.cp.epa.bussobj.entity.BaseTableColumn;
import com.cp.epa.bussobj.services.IBussinessObjectService;
import com.cp.epa.exception.SystemException;
import com.cp.epa.number.dao.INumberDao;
import com.cp.epa.number.entity.NumberCache;
import com.cp.epa.number.entity.NumberConfig;
import com.cp.epa.number.entity.NumberDetail;
import com.cp.epa.number.services.INumberCacheService;
import com.cp.epa.number.services.INumberConfigService;
import com.cp.epa.number.services.INumberDetailService;
import com.cp.epa.number.services.INumberService;
import com.cp.epa.number.utils.NumberManager;
import com.cp.epa.utils.Pager;
import com.cp.epa.utils.SqlMap;
import com.cp.epa.number.entity.Number;

public class NumberServiceImpl extends BaseServiceImpl<Number,INumberDao> implements INumberService {
	
	@Autowired
	private INumberCacheService numberCacheService;
	@Autowired
	private INumberDetailService numberDetailService;
	@Autowired
	private INumberConfigService numberConfigService;
	@Autowired
	private IBussinessObjectService bussinessObjectService;
	
	//定义缓存Map
	//private static Map<String,Object> cacheMap = new HashMap<String,Object>(); 
	//测试数据
	//private static int num;
	
	private NumberManager numberManager;
	
	/**
	 * 保存号码对象
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @param entity 实体对象
	 * @version Mar 15, 2013 5:05:08 PM <br/>
	 * @return ID 返回值为ID
	 */
	/*
	@SuppressWarnings("unchecked")
	public Object saveAndDetail(Number entity) throws Exception{
		if (entity != null) {
			List<NumberDetail> nds = entity.getNumberDetails();
			if (nds != null) {
				if(!isOverlap(entity))
					throw new SystemException("号码起始值与终止值不能重叠");
				//其他验证，包括起始值大于终止值，年份不能为空，子对象不能为空，号码段不能重复等
				isCheck(entity);
				
			}
		}
		return dao.insert(TypeUtil.getEntitySetDetail(entity)); 
	}
	*/
	/**
	 * 单个对象批量删除
	 * 功能：<br/>
	 * @author 杜中良
	 * @version Apr 1, 2013 10:12:07 AM <br/>
	 */
	public <ID extends Serializable> int deleteByIds(ID[] PK)throws Exception {
		if(PK == null || PK.length==0)
			throw new SystemException("没有选择要删除的信息");
		SqlMap<String,String,String> sqlMap = new SqlMap<String,String,String>();
		for(String _id : (String[])PK){
//			sqlMap.put("numberDetail.number.id", "=", _id);
//			List<NumberCache> nc = numberCacheService.selectHQL(sqlMap);
//			List<NumberConfig> nconfig = numberConfigService.selectHQL(sqlMap);
//			
//			
//			System.out.println("----------------------:"+nconfig.size());
//			
//			
//			
//			sqlMap.clear();
//			if((nc != null && nc.size()>0) || (nconfig != null && nconfig.size()>0))
//				throw new SystemException("已被应用的号码对象不能够被删除");
			
			Number number = dao.selectById(_id);
			if(number.getNumberDetails() != null && number.getNumberDetails().size()>0)
				throw new SystemException("包含明细的号码对象不能够被删除");
			dao.deleteById(_id);
		}
		return PK.length;
	}
	
	/**
	 * 更新号码主对象
	 * 功能: <br/>
	 * 
	 * 重写：杜中良 <br/>
	 * 
	 * @version ：Sep 2, 2013 4:38:18 PM<br/>
	 * 
	 * @param number
	 * @throws Exception <br/>
	 * @see com.zhongpin.pap.base.BaseServiceImpl#update(com.zhongpin.pap.base.BaseEntity)
	 */
	public void update(Number number)throws Exception{
		Number _number = dao.selectById(number.getId());
		
		number.setCreateDate(_number.getCreateDate());
		number.setCreateUser(_number.getCreateUser());
		
		if(number.getMarkSub().getEcode()==null || "".equals(number.getMarkSub().getEcode()))
			number.setMarkSub(null);
		dao.merge(number) ;
	}
	
	
	/**
	 * 单据模式的更新
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Jun 16, 2013 5:23:21 PM <br/>
	 */
	/*
	@SuppressWarnings({ "unchecked", "unused" })
	public int updateByDetail(Number number)throws Exception{
		if(number == null)
			throw new SystemException("号码对象不存在");
		//获得当前对象的所有明细
		List<NumberDetail> newNd = number.getNumberDetails();
		if(!isOverlap(number))
			throw new SystemException("号码起始值与终止值不能重叠");
		//其他验证，包括起始值大于终止值，年份不能为空，子对象不能为空，号码段不能重复等
		isCheck(number);
		//获取号码对象的历史明细
		SqlMap<String,String,String> sqlMap = new SqlMap<String,String,String>();
		sqlMap.put("number.id", "=", number.getId());
		List<NumberDetail> oldNd = numberDetailService.selectHQL(sqlMap);
		if(oldNd == null || oldNd.size()==0)
			throw new SystemException("原始明细信息不存在");
		//进行分化处理，将新增的、修改的、删除的明细进行分列
		//--保存新增的明细对象
		List<NumberDetail> _addDetails = new ArrayList<NumberDetail>();
		//--保存修改的明细对象
		List<NumberDetail> _updateDetails = new ArrayList<NumberDetail>();
		//--保存要删除的明细对象
		List<NumberDetail> _deleteDetails = new ArrayList<NumberDetail>();
		
		//--找出新增的
		for(NumberDetail newDetail : newNd){
			if(newDetail.getId() == null){
				_addDetails.add(newDetail);
			}
		}
		//--找出要修改的
		for(NumberDetail newDetail : newNd){
			for(NumberDetail oldDetail: oldNd){
				if((newDetail.getId()) == oldDetail.getId()){
					if(oldDetail.getNumberCache() != null){
						String oldValue=oldDetail.getNumberScope().trim()+oldDetail.getStart()+oldDetail.getMarkExt().trim();
						String newValue=newDetail.getNumberScope().trim()+newDetail.getStart()+newDetail.getMarkExt().trim();
						if(oldDetail.getPrefix() != null){
							oldValue += oldDetail.getPrefix().trim();
						}
						if(newDetail.getPrefix() != null){
							newValue += newDetail.getPrefix().trim();
						}
						if(number.getMarkYear() == 1){
							oldValue += oldDetail.getYear().trim();
							newValue += newDetail.getYear().trim();
						}
						if(number.getMarkSub() == 1){
							oldValue += oldDetail.getSubobject().trim();
							newValue += newDetail.getSubobject().trim();
						}
						
						if(!oldValue.equals(newValue))
							throw new SystemException(" ");
						
						if(oldDetail.getEnd() != newDetail.getEnd()){
							if(newDetail.getEnd() < oldDetail.getNumberCache().getMaxCacheNumber())
								throw new SystemException("当前缓存值必须在起始值与结束值之间");
						}
					}
					_updateDetails.add(newDetail);
					break;
				}
			}
		}
		
		//--找出要删除的
		loop:for(NumberDetail oldDetail: oldNd){
			for(NumberDetail newDetail : newNd){
				if(newDetail.getId()==null || "".equals(newDetail.getId()))
					continue;
				if(oldDetail.getId().equals(newDetail.getId())){
					continue loop;
				}
			}
			if(oldDetail.getNumberCache() != null)
				throw new SystemException("已经使用的明细不能够被删除");
			_deleteDetails.add(oldDetail);
		}*/
		
		/*
		for(NumberDetail oldDetail: oldNd){
			for(NumberDetail newDetail : newNd){
			
				if((newDetail.getId()) == oldDetail.getId()){
					if(oldDetail.getNumberCache() != null){
						String oldValue=oldDetail.getNumberScope().trim()+oldDetail.getStart()+oldDetail.getMarkExt().trim();
						String newValue=newDetail.getNumberScope().trim()+newDetail.getStart()+newDetail.getMarkExt().trim();
						if(oldDetail.getPrefix() != null){
							oldValue += oldDetail.getPrefix().trim();
						}
						if(newDetail.getPrefix() != null){
							newValue += newDetail.getPrefix().trim();
						}
						if(number.getMarkYear() == 1){
							oldValue += oldDetail.getYear().trim();
							newValue += newDetail.getYear().trim();
						}
						if(number.getMarkSub() == 1){
							oldValue += oldDetail.getSubobject().trim();
							newValue += newDetail.getSubobject().trim();
						}
						
						if(!oldValue.equals(newValue))
							throw new SystemException("已经使用的明细只允许修改结束值");
						
						if(oldDetail.getEnd() != newDetail.getEnd()){
							if(newDetail.getEnd() < oldDetail.getNumberCache().getMaxCacheNumber())
								throw new SystemException("当前缓存值必须在起始值与结束值之间");
						}
					}
					_updateDetails.add(newDetail);
					break;
				}else {
					_addDetails.add(newDetail);
					if(oldDetail.getNumberCache() != null)
						throw new SystemException("已经使用的明细不能够被删除");
					_deleteDetails.add(oldDetail);
					break;
				}
			}
		}*/
	/*
		//进行持久化处理
		if(_addDetails.size()>0){
			for(NumberDetail __nd : _addDetails){
				__nd.setNumber(number);
			}
			numberDetailService.save(_addDetails);
		}
		if(_updateDetails.size()>0){
			
			for(NumberDetail _uNd : _updateDetails){
				NumberDetail _temUnd = numberDetailService.selectById(_uNd.getId());
				_temUnd.setNumberScope(_uNd.getNumberScope());
				_temUnd.setYear(_uNd.getYear());
				_temUnd.setSubobject(_uNd.getSubobject());
				_temUnd.setStart(_uNd.getStart());
				_temUnd.setEnd(_uNd.getEnd());
				_temUnd.setPrefix(_uNd.getPrefix());
				_temUnd.setMarkExt(_uNd.getMarkExt());
				numberDetailService.update(_temUnd);
			}
		}
			
		if(_deleteDetails.size()>0){
			numberDetailService.delete(_deleteDetails);
		}
		
		//更新主体对象
		Number _n = dao.selectById(number.getId());
		_n.setNumberName(number.getNumberName());
		_n.setWarnNumber(number.getWarnNumber());
		dao.update(_n);
		return 1;
	}*/
	//判定当年标示与子对象标示不存在时明细信息不能重复	
	/*
	@SuppressWarnings({ "unchecked", "unused" })
	private boolean isOverlap(Number number){
		//获得当前对象的所有明细
		List<NumberDetail> nds = number.getNumberDetails();
		//对获取的明细进行验证处理
		if (nds == null || nds.size()==0)
			return false;
		//当没有年份标示与子对象标示时，判定重叠
		if(number.getMarkYear()==0 && number.getMarkSub()==0){
			//通过起始值进行排序
			Collections.sort(nds, new Comparator() {  
		          public int compare(Object a, Object b) {  
		            int one = ((NumberDetail)a).getStart();  
		            int two = ((NumberDetail)b).getStart();   
		            return one- two ;   
		          }  
		    });  
			//通过第一条明细的结束值小于第二条明细的起始值，判断明细信息中起始值与结束值有没有重叠
			for(int i=0;i<nds.size();i++){
				NumberDetail _d = nds.get(i);
				if(i+1 < nds.size()){
					NumberDetail _d2 = nds.get(i+1);
					if(_d.getEnd() >= _d2.getStart())
						return false;
				}
			}
		}
		return true;
	}
	
	//其他验证
	@SuppressWarnings("unused")
	private void isCheck(Number entity){
		if (entity == null) 
			throw new SystemException("号码对象不存在");
		List<NumberDetail> nds = entity.getNumberDetails();
		if(nds == null || nds.size()==0)
			throw new SystemException("明细信息不存在");
		for(NumberDetail _nd : nds){
			//当明细信息中起始值的长度或者终止值的长度 + 前缀值 大于号码总长度报错
			int startLength = 0;
			int endLength = 0;
			if(_nd.getPrefix() == null || "".equals(_nd.getPrefix())){
				startLength = (_nd.getStart()+"").length();
				endLength = (_nd.getEnd()+"").length();
			}else{
				startLength = (_nd.getStart()+"").length()+_nd.getPrefix().length();
				endLength = (_nd.getEnd()+"").length()+_nd.getPrefix().length();
			}
			
			if(startLength > entity.getLength() || endLength>entity.getLength())
				throw new SystemException("起始值或终止值加上前缀的长度和不能大于总长度");
			//当一个明细信息的起始值大于终止值时报错
			if(_nd.getStart() > _nd.getEnd()){
				throw new SystemException("起始值不能大于终止值！");
			}
			//当开启了年份标示时，年份信息不能为空
			if(entity.getMarkYear()==1){
				if(_nd.getYear()==null || "".equals(_nd.getYear())){
					throw new SystemException("年份不能为空！");
				}
			}
			//当开启子对象标示时，子对象不能为空
			if(entity.getMarkSub()==1){
				if(_nd.getSubobject()==null || "".equals(_nd.getSubobject())){
					throw new SystemException("子对象不能为空！");
				}
			}
			//判定号码段信息不能够重复
			int i=0;
			for(NumberDetail _nds : nds){
				if(_nd.equals(_nds)){
					i++;
				}
			}
			if(i>1){
				throw new SystemException("号码段信息不能够重复！");
			}
			//当外用状态为空时，默认不启用
			if(_nd.getMarkExt()==null || "".equals(_nd.getMarkExt())){
				_nd.setMarkExt("N");
			}
		}
	}
	*/
	/**
	 * 获得号码对象一个号码
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Aug 2, 2013 11:20:40 AM <br/>
	 */
	/*
	public synchronized String get(String number,String numberScope,String year,String subobject)throws Exception{
		synchronized(NumberManager.class){
			//当前线程
			System.out.println("--------threadName1-------:"+Thread.currentThread().getName());
			//通过号码对象查询缓存Map中是否包含该号码对象的缓存
			
			num++;
			System.out.println("----:"+num);
			//创建某个号码对象中其中某个明细生成的缓存对象
			Map<String,NumberCache> _numberCacheMap = null;
			_numberCacheMap = (Map)cacheMap.get(number);
			
			if(year != null && !"".equals(year))
				numberScope += year;
			if(subobject != null && !"".equals(subobject))
				numberScope += subobject;
			
			if(_numberCacheMap == null){
				
				//通过号码对象编码获取号码对象
				//通过号吗对象以及阶段号以及年份和子对象信息获取明细
				//通过号码对象以及明细信息创建号码存储对象
				//将号码存储对象保存都数据库
				//将号码存储对象缓存到Map中
				_numberCacheMap = new HashMap<String,NumberCache>();
				_numberCacheMap.put(numberScope, load(number,numberScope,year,subobject));
				cacheMap.put(number, _numberCacheMap);
				
			}else{
				NumberCache cache = _numberCacheMap.get(numberScope);
				if(cache == null){
					_numberCacheMap.put(numberScope,load(number,numberScope,year,subobject));
				}else{
					if(cache.hasNext()){
						_numberCacheMap.put(numberScope, load(number,numberScope,year,subobject));
					}
				}
			}
			//测试
			
			Map<String,Object> _testmap = cacheMap;
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//----------------
			NumberCache _cache = _numberCacheMap.get(numberScope);
			return getNext(_cache);
		}
	}*/
	
	
	 /**
	 * 获取缓存
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Aug 2, 2013 10:35:57 AM <br/>
	 */
	/*
	private  NumberCache load(String number,String numberScope,String year,String subobject) throws Exception{
		return null;
	}
	
	
	private String getNext(NumberCache cache)throws Exception{
		return null;
	}
	
	*/
	
	/**
	 * 获得号码对象一个号码
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Aug 2, 2013 11:20:40 AM <br/>
	 */
	public String get(String number,String numberScope,String year,String subobject)throws Exception{
		if(numberManager == null){
			numberManager = new NumberManager(){
				@Override
				public NumberCache load(NumberCache _cache,String number, String numberScope,String year, String subobject) throws Exception {
					// TODO Auto-generated method stub
					//通过号码对象编码获取号码对象
					Number _n = dao.selectById(number);
					//通过号吗对象以及阶段号以及年份和子对象信息获取明细
					if(_n == null)
						throw new SystemException("号码对象不存在");
					SqlMap<String,String,String> map = new SqlMap<String,String,String>();
					map.put("numberScope", "=", numberScope);
					map.put("number.id", "=", number);
					if(year != null && !"".equals(year))
						map.put("year", "=", year);
					if(subobject !=null && !"".equals(subobject))
						map.put("subobject", "=", subobject);
					List<NumberDetail> _nds = numberDetailService.selectHQL(map);
					map.clear();
					if(_nds == null || _nds.size()==0 )
						throw new SystemException("号码对象明细不存在");
					NumberDetail _nd = _nds.get(0);
					//判定如果Map缓存中存在号码存储对象
					if(_cache != null){
						/*
						NumberCache _cacheTmp = _nd.getNumberCache();
						_cacheTmp.setCacheNumber(_n.getCacheNumber());
						_cacheTmp.setCurrent(_cache.getCurrent());
						_cacheTmp.setLength(_n.getLength());
						_cacheTmp.setStep(_n.getStep());
						_cacheTmp.setPrefix(_nd.getPrefix());
						_cacheTmp.setNumberDetail(_cache.getNumberDetail());
						_cacheTmp.setMaxCacheNumber(_cache.getCurrent()+(_n.getCacheNumber()*_n.getStep()));
						*/
						/*
						 * 这种情况主要是缓存的个数用完时需要重新生成缓存对象
						 * 缓存的当前值还是原来的值，
						 * 缓存的个数需要重新指定
						 * 缓存的最大值需要重新指定
						 * 
						 */
						//缓存个数
						_cache.setCacheNumber(_n.getCacheNumber());
						_cache.setCacheNumDyn(_n.getCacheNumber());
						_cache.setCurrentNumber(_cache.getMaxCacheNumber());
						_cache.setCurrentNumDyn(_cache.getMaxCacheNumber());
						
						int maxNumber = _cache.getMaxCacheNumber()+(_cache.getStep()*_n.getCacheNumber());
						if(maxNumber > _nd.getEndNumber())
							throw new SystemException("缓存数值超出指定范围");
						//缓存最大值
						_cache.setMaxCacheNumber(maxNumber);
						_nd.setCurrentNumber(maxNumber+"");
						numberCacheService.merge(_cache);
						
					}else{
						//通过号码对象以及明细信息创建号码存储对象
						_cache = _nd.getNumberCache();
						//判断号码对象是不是为空
						if(_cache == null){
							
							/*
							 * 这种情况是第一次访问该对象，对象的号码对象明细还没有产生过
							 * 一个缓存对象，所以需要重新生成缓存对象
							 * 缓存的当前值就是明细中的起始值，
							 * 缓存的最大值就是当前值+缓存个数*步长
							 * 缓存的个数就是号码对象设置的个数
							 * 
							 */
							_cache = new NumberCache();
							
							// 缓存为空，并且当前值不为空，并且当前值大于开始值时，缓存开始值从号码明细当前值开始
							if (null != _nd.getCurrentNumber() && !"".equals(_nd.getCurrentNumber())) {
								_cache.setCurrentNumber(Integer.parseInt(_nd.getCurrentNumber()));
								_cache.setCurrentNumDyn(Integer.parseInt(_nd.getCurrentNumber()));
							}else {
								_cache.setCurrentNumber(_nd.getStartNumber());
								_cache.setCurrentNumDyn(_nd.getStartNumber());
							}
							//_cache.setCurrentNumber(_nd.getStartNumber());
							//缓存个数
							_cache.setCacheNumber(_n.getCacheNumber());
							_cache.setCacheNumDyn(_n.getCacheNumber());
							//号码长度
							_cache.setLength(_n.getLength());
							//步长
							_cache.setStep(_n.getStep());
							//前缀
							_cache.setPrefix(_nd.getPrefix());
							//明细
							_cache.setNumberDetail(_nd);
							
							int maxNumber = _cache.getCurrentNumber()+(_n.getCacheNumber()*_n.getStep());
							if(maxNumber > _nd.getEndNumber())
								throw new SystemException("缓存数值超出指定范围");
							//明细中的最大缓存值
							_nd.setCurrentNumber(maxNumber+"");
							//最大缓存值
							_cache.setMaxCacheNumber(maxNumber);
							numberCacheService.save(_cache);
							
						}else{
							/*
							 * 这种现象是针对号码对象对应的明细已经生成过缓存对象，
							 * 但是由于系统重启或其他原因，内存中的缓存不存在，但是数据库中的缓存信息还存在
							 * 需要重新生成的缓存对象，要依据数据库存储的老的缓存对象为基本
							 * 
							 * 缓存的当前值设定要以老缓存中的最大缓存值加一，这样做的原因是为了避免重新生成的缓存对象取值重复
							 * 缓存最大值就是以 设定好的当前值+步长*缓存个数
							 */
							//缓存个数
							_cache.setCacheNumber(_n.getCacheNumber());
							_cache.setCacheNumDyn(_n.getCacheNumber());
							//号码长度
							_cache.setLength(_n.getLength());
							//步长
							_cache.setStep(_n.getStep());
							//设置当前值
							_cache.setCurrentNumber(_cache.getMaxCacheNumber());
							_cache.setCurrentNumDyn(_cache.getMaxCacheNumber());
							//前缀
							_cache.setPrefix(_nd.getPrefix());
							//明细
							_cache.setNumberDetail(_nd);
							int maxNumber = _cache.getCurrentNumber()+(_n.getCacheNumber()*_n.getStep());
							if(maxNumber > _nd.getEndNumber())
								throw new SystemException("缓存数值超出指定范围");
							//最大缓存值
							_cache.setMaxCacheNumber(maxNumber);
							//明细中的最大缓存值
							_nd.setCurrentNumber(maxNumber+"");
							numberCacheService.update(_cache);
						}
					}
					numberDetailService.merge(_nd);
					//警告处理
					if((_nd.getEndNumber() - _cache.getMaxCacheNumber())<_n.getWarnNumber()){
						//警告处理
					}
					return _cache;
				}
			};
		}
		return numberManager.get(number, numberScope, year, subobject);
	}	
	
	/**
	 * 按照页面搜索条件分页查询
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Apr 18, 2013 5:57:37 PM <br/>
	 */
	public void selectAll(String id,Number entity,Pager<Number> page)throws Exception{
		if(id == null)
			selectAll(entity, page);
		SqlMap<String,String,String> sqlMap = new SqlMap<String,String,String>();
		sqlMap.put("objectCode", "=", id);
		
		List<NumberConfig> lnc = numberConfigService.selectHQL(sqlMap);
		//判断业务对象没有绑定号码对象
		if(lnc == null || lnc.size()==0){
//			String sql = "select * from number where id not in (select number from numberdetail where id in(select numberdetail from numberconfig))";
//			if(entity != null){
//				if(entity.getId() != null && !"".equals(entity.getId()))
//					sql += " and id='"+entity.getId()+"'";
//			}
			
//			String hql = "from Number e where e.id not in (select nd.number.id from NumberDetail nd where nd.id in(select nc.numberDetail.id from NumberConfig nc))";
//			if(entity != null){
//				if(entity.getId() != null && !"".equals(entity.getId()))
//					hql += " and e.id='"+entity.getId()+"'";
//			}
//			dao.selectByHQLPage(hql, page);
			dao.selectAll(page);
		}else{
			//业务对象已经绑定，判断该业务对象是否有关键属性
			List<BaseTableColumn> lbtc = bussinessObjectService.selecTableColumns(id);
			//关键属性存在
			if(lbtc != null && lbtc.size()>0){
				Number _n = lnc.get(0).getNumberDetail().getNumber();
				List<Number> result = new ArrayList<Number>();
				result.add(_n);
				page.setResult(result);
				page.setTotalCount(result.size());
			}
		}
	}
	/**
	 * 检验业务对象的编码是否符合号码对象生成规则
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Sep 30, 2013 11:02:04 AM <br/>
	 */
	public boolean check(String id,String number,String numberScope,String year,String subobject)throws Exception{
		Number _n = dao.selectById(number);
		//通过号吗对象以及阶段号以及年份和子对象信息获取明细
		if(_n == null)
			throw new SystemException("号码对象不存在");
		SqlMap<String,String,String> map = new SqlMap<String,String,String>();
		map.put("numberScope", "=", numberScope);
		map.put("number.id", "=", number);
		if(year != null && !"".equals(year))
			map.put("year", "=", year);
		if(subobject !=null && !"".equals(subobject))
			map.put("subobject", "=", subobject);
		List<NumberDetail> _nds = numberDetailService.selectHQL(map);
		map.clear();
		if(_nds == null || _nds.size()==0 )
			throw new SystemException("号码对象明细不存在");
		NumberDetail _nd = _nds.get(0);
		return NumberManager.check(id, _n,_nd);

	}

	/**
	 * 功能: 根据业务对象编号获取下一个号码<br/>
	 * 
	 * 重写：孟雪勤 <br/>
	 * 
	 * @version ：2015-12-14 下午05:31:54<br/>
	 * 
	 * @param objCode
	 * @return
	 * @throws Exception <br/>
	 * @see com.zhongpin.pap.number.services.INumberService#getNext(java.lang.String)
	 */
	@Override
	public String getNext(String objCode) throws Exception {
		NumberConfig _nc =  numberConfigService.selectBySinglet("objectCode", objCode);
		/**业务对象绑定号码对象明细*/
		if(_nc != null){
			NumberDetail _nd = _nc.getNumberDetail();
			String subValue=_nd.getSubobject();
			String yearValue=_nd.getYear();
			//绑定的号码对象明细是否有子对象标示
			//判定年份是否符合标准,不符合年份标准的不予管理
			if(yearValue != null && !"".equals(yearValue)){
				if(!new SimpleDateFormat("yyyy").format(new Date()).equals(yearValue))
					return null;
			}
			//判断可以使用号码对象的业务对象进行号码的生成
			//获取对象的ID主键及主键值
			//判断号码对象是否外部
			String extflag = _nd.getMarkExt();
			if(!"Y".equals(extflag)){
				//生成号码对象的ID编号
				String code = get( _nd.getNumber().getId(),  _nd.getNumberScope(), yearValue, subValue);
				return code;
			}
		}
		return null;
	}
	
}
