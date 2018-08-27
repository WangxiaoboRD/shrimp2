package com.cp.epa.bussobj.services.impl;

import com.cp.epa.base.BaseServiceImpl;
import com.cp.epa.bussobj.dao.IMethodDescDao;
import com.cp.epa.bussobj.entity.MethodDesc;
import com.cp.epa.bussobj.services.IMethodDescService;
/**
 * 
 * 类名：MethodDescServiceImpl  <br />
 *
 * 功能：
 *
 * @author zp <br />
 * 创建时间：2013-7-12 上午10:28:36  <br />
 * @version 2013-7-12
 */
public class MethodDescServiceImpl extends BaseServiceImpl<MethodDesc, IMethodDescDao> implements IMethodDescService{

	
/*	@SuppressWarnings("unchecked")
	@Override
	public List<MethodDesc> selectAll(MethodDesc entity) throws Exception {
		
		
		List<Class> classes=ClassUtils.getClasses("com.zhongpin.pap.bussobj.entity");
		
		List<MethodDesc> methodDescs=new ArrayList<MethodDesc>();
		
		List<Class<?>> list = new ArrayList<Class<?>>();
		if(null!=classes&&classes.size()!=0){			
			for(Class class1:classes){
				String bussobject=null;
				if(null!=entity.getBussinessObject()){
					bussobject=entity.getBussinessObject().getBussCode();					
				}				
				if(null!=bussobject){
					BussEle bussEle=(BussEle) class1.getAnnotation(BussEle.class);
					if(null!=bussEle){
						if(class1.getSimpleName().equals(bussobject)){
							list.add(class1);					
						}
					}									
				}				
			}			
		}
				
		
		if(null!=list&&list.size()!=0){
			
			MethodDesc methodDesc=null;
			
			for(Class<?> class1:list){
				
				String classname="com.zhongpin.pap.bussobj.services.impl."+class1.getSimpleName()+"ServiceImpl";
				
				System.out.println("------------"+classname);
				
				Class<?> class2=null;
				
				try{
					
					class2=Class.forName(classname);

				}catch(Exception e){
					continue;
				}
				
				
				List<Method> liMethods=ClassUtils.getDealMethods(class2);
				
				if(null!=liMethods&&liMethods.size()!=0){
					
					for(Method mm:liMethods){
						
						MethodMeans means=mm.getAnnotation(MethodMeans.class);
						
						if(null!=means){
							
							methodDesc=new MethodDesc();
							
							String descs=means.descs();
							
							if(null!=descs&&!"".equals(descs)){
								
								methodDesc.setDescs(descs);
							}
							
							methodDesc.setClassplace(mm.getDeclaringClass().getName());
							
							methodDesc.setMethodName(mm.getName());
							
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
	                        
							methodDesc.setParamlist(type);	
							
							methodDescs.add(methodDesc);
														
						}
						
					}					
				}				
			}						
		}
		
		return methodDescs;
	}
*/	
	
}
