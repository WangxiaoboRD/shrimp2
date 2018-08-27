package com.cp.epa.bussobj.services.impl;

import com.cp.epa.base.BaseServiceImpl;
import com.cp.epa.bussobj.dao.IMethodDescDetailDao;
import com.cp.epa.bussobj.entity.MethodDescDetail;
import com.cp.epa.bussobj.services.IMethodDescDetailService;
/**
 * 
 * 类名：MethodDescDetailServiceImpl  <br />
 *
 * 功能：
 *
 * @author zp <br />
 * 创建时间：2013-7-12 上午10:35:47  <br />
 * @version 2013-7-12
 */
public class MethodDescDetailServiceImpl extends BaseServiceImpl<MethodDescDetail, IMethodDescDetailDao> implements IMethodDescDetailService{

/*	
	@Override
	public List<MethodDescDetail> selectAll(MethodDescDetail entity)
			throws Exception {
		
		String model=entity.getModel();
		
		if(null!=model){
		    model="com.zhongpin.pap.bussobj.services.impl."+model+"ServiceImpl";
			//model="com.zhongpin.pap.entity."+model;
		}
				
		Class<?> class1=Class.forName(model);
		
		List<MethodDescDetail> methodDescDetails=new ArrayList<MethodDescDetail>();
		
		List<Method> liMethods=ClassUtils.getDealMethods(class1);
		
		if(null!=liMethods&&liMethods.size()!=0){
			
			MethodDescDetail methodDescDetail=null;
			
			for(Method mm:liMethods){
				
				MethodMeans means=mm.getAnnotation(MethodMeans.class);
				
				if(null!=means){
										
					Class<?> paramClass[] =mm.getParameterTypes();
					
					String type="";
					
					if(null!=paramClass&&paramClass.length!=0){
					  
						for(Class<?> cll:paramClass){
							type=type+"  ,  "+cll.getName();
						}
						
						if(type.indexOf(",")!=-1){
							
							type=type.substring(type.indexOf(",")+1);

						}
					}
					
					//方法名相同，且参数类型相同。则是要加载的方法
					if(entity.getMethodDesc().getMethodName().equals(mm.getName())&&type.equals(entity.getParamType())){
												
						if(null!=paramClass&&paramClass.length!=0){
														
							String [] parammean=means.param();
														
							for(int i=0;i<paramClass.length;i++){
								
								methodDescDetail=new MethodDescDetail();
								
								Class<?> class2=paramClass[i];
								
								methodDescDetail.setParamType(class2.getName());
								
								if(i+1<=parammean.length){
									
									methodDescDetail.setParamdesc(parammean[i]);
								}
								
								methodDescDetails.add(methodDescDetail);
								
							}							
						}						
					}
				}
				
			}					
		}
		
		return methodDescDetails;
	}
*/	
}
