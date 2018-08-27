/**
 * 文件名：@DebugSqlServiceImpl.java <br/>
 * 包名：com.zhongpin.pm.server.service.impl <br/>
 * 项目名：pscm <br/>
 * @author dzl <br/>
 */
package com.cp.epa.debug.services.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.cp.epa.base.BaseServiceImpl;
import com.cp.epa.debug.dao.IDebugSystemDao;
import com.cp.epa.debug.entity.DebugSystem;
import com.cp.epa.debug.services.IDebugSystemService;

/**
 * 类名：DebugSqlServiceImpl  <br />
 *
 * 功能：sql调试
 *
 * @author dzl <br />
 * 创建时间：2012-7-9 上午11:14:10  <br />
 * @version 2012-7-9
 */
public class DebugSystemServiceImpl extends BaseServiceImpl<DebugSystem, IDebugSystemDao> implements IDebugSystemService {

	/**
	 * 保存对象
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Jan 10, 2014 5:30:02 PM <br/>
	 */
	public void saveEntityList(List<String> _list)throws Exception{
		if(_list==null || _list.size()==0)
			return ;
		for (String _value : _list) {
			if (_value != null) {
				String[] _arrayValue = _value.split(",");
				
				DebugSystem debugSystem = new DebugSystem();
				
				debugSystem.setOperatorId(_arrayValue[0]);
				debugSystem.setOperatorName(_arrayValue[1]);
				debugSystem.setOperBusiness(_arrayValue[2]);
				debugSystem.setOperFunction(_arrayValue[3]);
				debugSystem.setOperTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
				if(_arrayValue.length == 5){
					debugSystem.setServiceFunction("");
					debugSystem.setFollowFunction("");
					debugSystem.setTimeConsuming(_arrayValue[4]);
				}else if (_arrayValue.length == 6) {
					debugSystem.setServiceFunction(_arrayValue[4]);
					debugSystem.setFollowFunction("");
					debugSystem.setTimeConsuming(_arrayValue[5]);
				} else {
					
					debugSystem.setServiceFunction(_arrayValue[4]);
					debugSystem.setFollowFunction(_arrayValue[5]);
					debugSystem.setTimeConsuming(_arrayValue[6]);
				}
				
				dao.insert(debugSystem);
			}
		}
	}
}
