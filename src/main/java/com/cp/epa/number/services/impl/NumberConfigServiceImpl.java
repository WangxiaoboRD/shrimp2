package com.cp.epa.number.services.impl;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;

import com.cp.epa.base.BaseServiceImpl;
import com.cp.epa.bussobj.entity.BaseTableColumn;
import com.cp.epa.bussobj.entity.BussinessEle;
import com.cp.epa.bussobj.services.IBaseTableColumnService;
import com.cp.epa.bussobj.services.IBussObjPropertyService;
import com.cp.epa.bussobj.services.IBussinessObjectService;
import com.cp.epa.exception.SystemException;
import com.cp.epa.number.dao.INumberConfigDao;
import com.cp.epa.number.entity.Number;
import com.cp.epa.number.entity.NumberConfig;
import com.cp.epa.number.entity.NumberDetail;
import com.cp.epa.number.entity.VacantManage;
import com.cp.epa.number.services.INumberConfigService;
import com.cp.epa.number.services.INumberDetailService;
import com.cp.epa.number.services.INumberService;
import com.cp.epa.number.services.IVacantManageService;
import com.cp.epa.utils.SqlMap;


public class NumberConfigServiceImpl extends BaseServiceImpl<NumberConfig,INumberConfigDao> implements INumberConfigService {
	
	@Autowired
	private IBussinessObjectService bussinessObjectService;
	@Autowired
	private INumberService numberService;
	@Autowired
	private IBussObjPropertyService bussObjPropertyService;
	@Autowired
	private INumberDetailService numberDetailService;
	@Autowired
	private IBaseTableColumnService baseTableColumnService;
	/** 空号管理业务注册 */
	@Resource
	private IVacantManageService vacantManageService;
	
	
	
	/**
	 * 保存号码对象配置信息
	 * 功能: <br/>
	 * 
	 * 重写：杜中良 <br/>
	 * @version ：Aug 22, 2013 4:20:44 PM<br/>
	 * @param nc
	 * @return <br/>
	 * @see com.zhongpin.pap.base.IBaseService#save(com.zhongpin.pap.base.BaseEntity)
	 */
	public Object save(NumberConfig nc)throws Exception{
		
		if(nc == null)
			throw new SystemException("对象信息不能为空");
		//判断号码对象子对象存在的情况下
		if(nc.getNumberDetail().getSubobject() != null && !"".equals(nc.getNumberDetail().getSubobject())){
			//查询业务对象是否有关键属性
			List<BaseTableColumn> lbtc =  bussinessObjectService.selecTableColumns(nc.getObjectCode());
			//业务对象的关键属性不为空
			if(lbtc != null && lbtc.size()>0){
				//判断业务对象如果仅有一个有效的关键属性
				//（有效的关键属性是指，关键属性来源于业务元素，并且该业务元素具备有固定值或者参考表）
				//这个有效的关键属性刚好与号码对象子对象对应的业务元素相同时，可以不用选择关键属性及关键属性的值
				if(checking(lbtc,nc)){
					if(nc.getHingeKey() == null || "".equals(nc.getHingeKey()))
						throw new SystemException("关键属性不能为空");
					
					if(nc.getHingeValue() == null || "".equals(nc.getHingeValue()))
						throw new SystemException("关键属性值不能为空");
					//判断关键属性与号码对象对应的子对象是否一致
					NumberDetail _numberDetail = numberDetailService.selectById(nc.getNumberDetail().getId());
					Number _n = _numberDetail.getNumber();
					
					BaseTableColumn _baseTableColumn = baseTableColumnService.selectById(Integer.parseInt(nc.getHingeKey()));
					if(_baseTableColumn == null)
						throw new SystemException("关键属性与业务元素缺少关联");
					String _ecode = _baseTableColumn.getBussinessEle().getEcode();
					if(_ecode.equals(_n.getMarkSub().getEcode()))
						throw new SystemException("关键属性不能与子对象相同");
					nc.setHingeKey(_ecode);
				}
			}
		}else{
			nc.setHingeKey(null);
			nc.setHingeValue(null);
		}
		
		//验证要保存的对象 业务对象+关键属性+关键值+号码对象明细 不能重复
		List<NumberConfig> lnc = dao.selectAll();
		if(lnc != null && lnc.size() >0){
			for(NumberConfig ncf : lnc){
				if(ncf.equals(nc)){
					throw new SystemException("信息保存重复");
				}
			}
		}
		
		return dao.insert(nc);
	}

	/**
	 * 验证业务对象是否已经进行绑定号码对象
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Sep 10, 2013 4:23:17 PM <br/>
	 */
	public boolean selectBussObject(String objId)throws Exception{
		
		if(objId  == null || "".equals(objId))
			throw new SystemException("请选择业务对象");
		SqlMap<String,String,String> sqlmap = new SqlMap<String,String,String>();
		sqlmap.put("objectCode", "=", objId);
		List<NumberConfig> lnc = dao.selectByConditionHQL(sqlmap);
		sqlmap.clear();
		sqlmap = null;
		//判断该业务对象有没有绑定号码对象，没有的话可以往下进行
		if(lnc == null || lnc.size()==0)
			return false;
		//判定如果该业务对象已经绑定号码对象，就必须验证该业务对象是否包含关键属性，如果包含可以往下进行
		if(lnc != null && lnc.size()>0){
			List<BaseTableColumn> lbtc =  bussinessObjectService.selecTableColumns(objId);
			if(lbtc != null && lbtc.size()>0)
				return false;
		}
		return true;
	}
	
	/**
	 * 验证当前选定的号码对象是否已经绑定了业务对象，
	 * 如果已经绑定，那么将不能再绑定其他业务对象
	 * 
	 * 验证当前号码对象是否有子对象，且子对象是否与业务对象的属性一致
	 * 
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Sep 10, 2013 5:13:11 PM <br/>
	 */
	public int selectNumber(NumberConfig nc)throws Exception{
		if(nc == null)
			throw new SystemException("请选择号码对象");
		Number number = numberService.selectById(nc.getNumberDetail().getNumber().getId());
		//判断号码对象是否有子对象
		BussinessEle bussEle = number.getMarkSub();
		//如果包含子对象 判断子对象的值与业务对象中的属性是否一致
		if(bussEle != null){
			String ecode = bussEle.getEcode();
			//获得业务对象全路径
			String fullClassName = bussinessObjectService.selectById(nc.getObjectCode()).getFullClassName();
			//查看业务对象所有的业务元素
			List<BaseTableColumn> lbtc = bussObjPropertyService.selectBaseTableColumns(fullClassName, false);
			//判断业务对象没有任何属性，则重新选择号码对象
			if(lbtc == null || lbtc.size()==0)
				return 1;
			//如果有属性，遍历属性，判断每一个属性是否引用业务元素
			int flag=0;
			for(BaseTableColumn btc : lbtc){
				BussinessEle be = btc.getBussinessEle();
				if(be == null)
					continue;
				if(be.getEcode().equals(ecode))
					flag++; 
			}
			//如果业务对象包含的业务元素与号码对象的子对象不一致，重新选择号码对象
			if(flag == 0)
				return 1;
			
		}
		
		//判断 号码对象如果已经绑定了业务对象，那么该号码对象不能再绑定其他业务对象
		SqlMap<String,String,String> sqlMap = new SqlMap<String,String,String>();
		sqlMap.put("objectCode", "=", nc.getObjectCode());
		List<NumberConfig> lnc = dao.selectByConditionHQL(sqlMap);
		sqlMap.clear();
		
		//判断号码对象已经绑定了业务对象
		if(lnc != null && lnc.size()>0){
			String numberId = lnc.get(0).getNumberDetail().getNumber().getId();
			//判断该业务对象是否是号码对象已经绑定的业务对象,如果不是，重新选择号码对象
			if(!numberId.equals(nc.getNumberDetail().getNumber().getId()))
				return 2;
			lnc.clear();
		}/*else{
			sqlMap.put("numberDetail.number.id", "=", nc.getNumberDetail().getNumber().getId());
			lnc = dao.selectByConditionHQL(sqlMap);
			sqlMap = null;
			if(lnc != null && lnc.size()>0){
				//判断号码对象是否绑定了其他业务对象，如果之前绑定的业务对象与当前业务对象不符，重新选择号码对象
				if(!nc.getObjectCode().equals(lnc.get(0).getObjectCode()))
					return 3;
			}
		}*/
		return 0;
	}
	
	/**
	 * 验证业务对象关键属性与号码对象子对象是否一致
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Sep 11, 2013 5:42:05 PM <br/>
	 * @throws Exception 
	 */
	public boolean selectChecking(NumberConfig nc) throws Exception{
		if(nc == null)
			return false;
		List<BaseTableColumn> lbc = bussinessObjectService.selecTableColumns(nc.getObjectCode());
		return checking(lbc,nc);
	}
	
	/**
	 * 验证
	 * 功能：<br/>
	 * 业务对象如果仅有一个有效的关键属性
	 *（有效的关键属性是指，关键属性来源于业务元素，并且该业务元素具备有固定值或者参考表）
	 * 这个有效的关键属性刚好与号码对象子对象对应的业务元素相同时，可以不用选择关键属性及关键属性的值
	 * @author 杜中良
	 * @version Sep 14, 2013 4:58:58 PM <br/>
	 */
	private boolean checking(List<BaseTableColumn> lbc,NumberConfig nc)throws Exception{
		//业务对象没有关键属性
		if(lbc == null || lbc.size()==0)
			return false;
		//业务对象仅有一个关键属性，且属性刚好与号码对象子对象一致
		if(lbc.size() == 1){
			BaseTableColumn btc = lbc.get(0);
			String _ecode = btc.getBussinessEle().getEcode();
			Number _n = numberService.selectById(nc.getNumberDetail().getNumber().getId());
			//如果一致，不再显示关键属性下拉框
			if(_ecode.equals(_n.getMarkSub().getEcode()))
				return false;
		}
		return true;
	}

	/**
	 * 功能: 根据编号删除<br/>
	 * 
	 * 重写：孟雪勤 <br/>
	 * 
	 * @version ：2015-12-15 下午05:17:41<br/>
	 * 
	 * @param <ID>
	 * @param PK
	 * @return
	 * @throws Exception <br/>
	 * @see com.zhongpin.pap.base.BaseServiceImpl#deleteByIds(ID[])
	 */
	@Override
	public <ID extends Serializable> int deleteByIds(ID[] PK) throws Exception {
		
		// 已被空号管理引用不允许删除
		if (null != PK && PK.length > 0) {
			for (ID id : PK) {
				NumberConfig nc = dao.selectById(id);
				int rows = vacantManageService.selectTotalRows("bussObj", nc.getObjectCode());
				if (rows > 0)
					throw new SystemException("编码【" + nc.getObjectCode() + "】的业务对象已被空号管理引用，不允许删除！");
			}
		}
		return dao.deleteByIds(PK);
	}

	/**
	 * 功能: 空号设置<br/>
	 * 
	 * 重写：孟雪勤 <br/>
	 * 
	 * @version ：2015-10-7 上午11:02:03<br/>
	 * 
	 * @param ids
	 * @throws Exception <br/>
	 * @see com.zhongpin.pap.number.services.INumberConfigService#vacantSet(int[])
	 */
	@Override
	public void operateVacantSet(Integer[] ids) throws Exception {
		if (null != ids && ids.length > 0) {
			for (int id : ids) {
				NumberConfig nc = dao.selectById(id);
				if (null != nc) {
					int rows = vacantManageService.selectTotalRows("bussObj", nc.getObjectCode());
					if (rows > 0)
						throw new SystemException("编码【" + nc.getObjectCode() + "】的业务对象已设置为空号对象，不允许重复设置！");
					VacantManage vm = new VacantManage();
					vm.setBussObj(nc.getObjectCode());
					vm.setBussName(nc.getObjectName());
					vm.setStatus('N');
					vm.setRunStatus('N');
					vacantManageService.save(vm);
				}
			}
		}
	}
	
}
