/**
 * 文件名：@AllotStatus.java <br/>
 * 包名：com.zhongpin.pap.utils <br/>
 * 项目名：PAP1.0 <br/>
 * @author 孟雪勤 <br/>
 */
package com.cp.epa.permission.utils.enums;

/**
 * 类名：AllotStatus  <br />
 *
 * 功能：角色权限规则：权限分配状态
 *
 * @author 孟雪勤 <br />
 * 创建时间：2013-11-11 下午02:52:38  <br />
 * @version 2013-11-11
 */
public enum AllotStatus {
	
	 // 已分配
	ALLOTED {
		@Override
		public String getValue() {
			return "ALLOTED";
		}
	},
	//未分配
	UNALLOT {
		@Override
		public String getValue() {
			return "UNALLOT";
		}
	}; 
	
	public abstract String getValue();
}
