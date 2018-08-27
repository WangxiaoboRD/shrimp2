package com.cp.epa.bussobj.services;

import java.util.List;
import java.util.Map;

import com.cp.epa.base.IBaseService;
import com.cp.epa.bussobj.entity.BaseTableColumn;
import com.cp.epa.bussobj.entity.BussinessObject;
import com.cp.epa.bussobj.entity.ImportantObj;
import com.cp.epa.utils.Pager;
/**
 * 
 * 类名：IBussinessObjectService  <br />
 *
 * 功能：
 *
 * @author zp <br />
 * 创建时间：2013-7-10 上午10:05:09  <br />
 * @version 2013-7-10
 */
public interface IBussinessObjectService extends IBaseService<BussinessObject>{
	
	/**
	 * 功能：根据参考表编码查询参考表信息<br/>
	 *
	 * @author 孟雪勤
	 * @version 2013-11-20 下午04:30:18 <br/>
	 */
	public Map<String, Object> selectRefTableInfo(BussinessObject entity) throws Exception;
	
	/**
	 * 功能：绑定权限对象<br/>
	 *
	 * @author 孟雪勤
	 * @version 2013-11-18 下午02:38:21 <br/>
	 */
	public void operateBindAuthObj(BussinessObject entity) throws Exception;
	
	/**
	 * 功能：权限过滤属性设置<br/>
	 *
	 * @author 孟雪勤
	 * @version 2013-11-15 上午11:49:26 <br/>
	 */
	public void addAuthPropertySet(BussinessObject entity) throws Exception;
	
	public void refreshProperty()  throws Exception ;
	
	/**
	 * 
	 * 功能：<br/>
	 * 刷新单个Model
	 * @author zp
	 * @version 2013-8-15 下午05:15:13 <br/>
	 */
	public void refreshSingleModel(BussinessObject bussinessObject)throws Exception;
	
	/**
	 * 
	 * 功能：<br/>
	 * 根据业务对象查找到所属关键属性
	 * @author zp
	 * @version 2013-9-6 上午10:37:38 <br/>
	 */
	public List<BaseTableColumn> selecTableColumns(String bussCode)throws Exception;
	
	/**
	 * 
	 * 功能：<br/>
	 * 查所有关键属性的值
	 * @author zp
	 * @version 2013-9-9 下午06:19:14 <br/>
	 */
	public List<ImportantObj> selectImportantObjs(String bussCode)throws Exception;

	/**
	 * 
	 * 功能：<br/>
	 * 通过业务元素编码和类名查找引用该业务元素的业务对象所对应的的属性名称
	 * @author zp
	 * @version 2013-9-29 上午10:10:08 <br/>
	 */
	public List<String> selectFieldName(String ecode,String fullClassName)throws Exception;
	
	/**
	 * 查询业务对象信息，结果集不包含变更日志中的对象
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Dec 16, 2013 9:54:11 AM <br/>
	 */
	public void selectByCont(BussinessObject entity,Pager<BussinessObject> page) throws Exception;
	
	
	

}
