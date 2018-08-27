package com.cp.epa.aop;

import java.io.Serializable;
import java.util.Date;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;
import com.cp.epa.base.BaseEntity;
import com.cp.epa.permission.entity.Users;
import com.cp.epa.utils.PapUtil;
import com.cp.epa.utils.SysContainer;

public class EntityInterceotor extends EmptyInterceptor {
	private static final long serialVersionUID = 1L;
	private static final String CREATE_DATE = "createDate";// 创建日期"属性名称
	private static final String MODIFY_DATE = "modifyDate";// 修改日期"属性名称 
	private static final String CREATE_USER = "createUser";// 创建人
	private static final String MODIFY_USER = "modifyUser";// 修改人 
	
	private static Users getCurrentUser(){
//		Map<String, Object> session = SysContext.get();
//		if(session == null){
//			return null;
//		}
//		return (Users)session.get("CURRENTUSER");
		Users u = SysContainer.get();
		return u;
	}
	//保存对象前将调用此方法
	public boolean onSave(Object entity, Serializable id, Object[] state,String[] propertyNames, Type[] types) {
		if(entity instanceof BaseEntity){
			for(int i=0;i<propertyNames.length;i++){
				if(CREATE_DATE.equals(propertyNames[i])){
					if(((BaseEntity) entity).getCreateDate() == null || "".equals(((BaseEntity) entity).getCreateDate()))
						state[i]= PapUtil.date(new Date());
				}else if(CREATE_USER.equals(propertyNames[i])){
					if(((BaseEntity) entity).getCreateUser() == null || "".equals(((BaseEntity) entity).getCreateUser())){
						if(getCurrentUser() != null)
							state[i]= getCurrentUser().getUserRealName();
						else 
							state[i]="";
					}
				}
			}
		}
		return true;
	}
	// 更新对象前将调用此方法
	public boolean onFlushDirty(Object entity, Serializable id,Object[] currentState, Object[] previousState,String[] propertyNames, Type[] types) {
		if(entity instanceof BaseEntity){
			for(int i=0;i<propertyNames.length;i++){
				if(MODIFY_DATE.equals(propertyNames[i])){
					currentState[i]= PapUtil.date(new Date());
				}else if(MODIFY_USER.equals(propertyNames[i])){
					if(getCurrentUser() != null)
						currentState[i]= getCurrentUser().getUserRealName();
					else
						currentState[i]="";
				}
			}
		}
		return true;
	}
}
