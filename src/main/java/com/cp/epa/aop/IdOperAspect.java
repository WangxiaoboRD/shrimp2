package com.cp.epa.aop;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.transaction.SystemException;

import org.aspectj.lang.JoinPoint;
import org.springframework.beans.factory.annotation.Autowired;

import com.cp.epa.bussobj.services.IBussinessObjectService;
import com.cp.epa.number.entity.NumberConfig;
import com.cp.epa.number.entity.NumberDetail;
import com.cp.epa.number.services.INumberConfigService;
import com.cp.epa.number.services.INumberService;
import com.cp.epa.sysconfig.utils.SysConfigContext;
import com.cp.epa.utils.TypeUtil;
import com.cp.epa.number.entity.Number;


//@Aspect
public class IdOperAspect {
	
	@Autowired
	private INumberConfigService numberConfigService;
	@Autowired
	private IBussinessObjectService bussinessObjectService;
	@Autowired
	private INumberService numberService;
	
	//@Before("AopPointcut.pointCutIdOperExpress()")
	public boolean doBefore(JoinPoint jp) throws SystemException{
		//通过反射获取拦截的对象的实体名称
//		Class<?> clazz = jp.getTarget().getClass();
//		Type[] type = TypeUtil.getActualTypes(clazz);
//		String className1 = ((Class<?>) type[0]).getSimpleName();
//		String classFullName1=((Class<?>) type[0]).getName();
		// 开关
		String status = SysConfigContext.getSwitch("serial.number");
		if(status != null && "N".equals(status))
			return false;
		
		//获得拦截的对象方法的参数
		Object[] objs = jp.getArgs();
		Object obj=null;
		//如果参数不为空
		if(objs.length>0)
			//取第一个参数作为要处理的参数
			obj = objs[0];
		//该参数对象的全路径
		String classFullName = obj.getClass().getName();
		//对参数对象的类名
		String className = obj.getClass().getSimpleName();
		
		//通过类名查询一下该对象是否绑定了号码对象的明细
		boolean b = false;
		try {
			NumberConfig _nc =  numberConfigService.selectBySinglet("objectCode",className);
			/**业务对象绑定号码对象明细*/
			if(_nc != null){
				NumberDetail _nd = _nc.getNumberDetail();
				String subValue=_nd.getSubobject();
				String yearValue=_nd.getYear();
				//绑定的号码对象明细是否有子对象标示
				Number _n = _nd.getNumber();
				/**判定年份是否符合标准*/ //不符合年份标准的不予管理
				if(yearValue != null && !"".equals(yearValue)){
					if(!new SimpleDateFormat("yyyy").format(new Date()).equals(yearValue))
						return b;
				}
				/**如果子对象标示*/ 
				if(_n.getMarkSub()!=null && !"".equals(_n.getMarkSub())){
					//获得该子对象对应的业务对象的属性名称 
					//获得对应对象参数的值
					Object objValue = getValue(obj,_n.getMarkSub().getEcode(),classFullName);
					/*--------比较该属性的值与子对象对应的明细的值是否一致------*/
					if((objValue+"").equals(subValue)){
						/**是否有关键属性*/
						if(_nc.getHingeKey() !=null && !"".equals(_nc.getHingeKey())){
							Object hingValue = getValue(obj,_nc.getHingeKey(),classFullName);
							/*---------判断关键属性的值与号码对象配置中的值是否一致*/
							if((hingValue+"").equals(_nc.getHingeValue())){
								b=true;
							}
						}else{
							//没有关键属性时，直接生成号码对象
							b=true;
						}
					}
				}else{
					//如果没有子对象标示，直接生成号码对象
					b= true;
				}
				
				//判断可以使用号码对象的业务对象进行号码的生成
				if(b){
					//获取对象的ID主键及主键值
					//判断号码对象是否外部
					String extflag = _nd.getMarkExt();
					String pkName = TypeUtil.getClassIdField(obj.getClass());
					String pkType = TypeUtil.getClassIdType(obj.getClass()).getClass().getSimpleName();
					if("Y".equals(extflag)){
						//检查外用时候需要判断传入的业务对象的ID值是否合法
						Object pkValue = TypeUtil.getFieldValue(obj,pkName);
						if("String".equals(pkType)){
							boolean _b =numberService.check(pkValue+"", _nd.getNumber().getId(), _nd.getNumberScope(), yearValue, subValue);
							//如果外用状态下，业务对象的ID值不符合生成规则
							if(!_b){
								//将业务对象ID值 制空
								TypeUtil.setFieldValue(obj, pkName,pkType, null);
							}
						}
					}else{
						//判断原始对象的ID是不是为空
						Object idValue = TypeUtil.getFieldValue(obj, TypeUtil.getClassIdField(obj.getClass())) ;
						if(idValue==null ||"".equals(idValue)){
							//生成号码对象的ID编号
							String code = numberService.get( _nd.getNumber().getId(),  _nd.getNumberScope(), yearValue, subValue);							//将生成的ID设置到业务对象中
							TypeUtil.setFieldValue(obj, pkName,pkType, code);
						}
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return b;
	}
	
	//通过 对象全路径 业务元素编码 查询对应的业务对象属性名称，并通过反射获得值
	private Object getValue(Object obj,String bussEleCode,String classFullName) throws Exception{
		//获得该子对象对应的业务对象的属性名称   
		List<String> lstr =bussinessObjectService.selectFieldName(bussEleCode,classFullName);
		String bussPropty = "";
		if(lstr != null && lstr.size()>0){
			bussPropty = lstr.get(0);
		}
		//通过反射获取该属性的值
		return TypeUtil.getFieldValue(/**Class.forName(classFullName)*/obj,bussPropty);
	}
}
