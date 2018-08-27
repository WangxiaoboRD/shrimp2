/**
 * 文件名：@SysConfigContext.java <br/>
 * 包名：com.zhongpin.pap.sysconfig.utils <br/>
 * 项目名：PAP1.0 <br/>
 * @author 孟雪勤 <br/>
 */
package com.cp.epa.sysconfig.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.cp.epa.base.Base;
import com.cp.epa.sysconfig.entity.SysParam;
import com.cp.epa.sysconfig.entity.SysSwitch;
import com.cp.epa.sysconfig.services.ISysParamService;
import com.cp.epa.sysconfig.services.ISysSwitchService;

/**
 * 类名：SysConfigContext  <br />
 *
 * 功能：系统配置信息上下文
 *
 * @author 孟雪勤 <br />
 * 创建时间：2013-12-19 下午04:26:18  <br />
 * @version 2013-12-19
 */
public class SysConfigContext extends Base implements ApplicationListener<ContextRefreshedEvent>{
	
	/** 系统参数缓存 */
	private static final Map<String, SysParam> params = new HashMap<String, SysParam>();
	/** 系统开关缓存 */
	private static final Map<String, String> switchs = new HashMap<String, String>();
	/** 系统参数业务注册 */
	@Autowired
	private ISysParamService sysParamService;
	/** 系统开关业务注册 */
	@Autowired
	private ISysSwitchService sysSwitchService;
	
	/**
	 * 功能: Spring容器初始化<br/>
	 * 
	 * 重写：孟雪勤 <br/>
	 * 
	 * @version ：2013-12-19 下午05:20:59<br/>
	 * 
	 * @param ctx <br/>
	 * @see org.springframework.context.ApplicationListener#onApplicationEvent(org.springframework.context.ApplicationEvent)
	 */
	public void onApplicationEvent(ContextRefreshedEvent ctx) {
		logger.info("系统配置信息初始化开始...........................");
		
		// 初始化系统参数
		initParamCache();
		
		// 初始化开关参数
		initSwitchCache();
		
		logger.info("系统配置信息初始化完毕...........................");
	}
	
	/**
	 * 功能：初始化参数缓存<br/>
	 *
	 * @author 孟雪勤
	 * @version 2013-12-19 下午04:33:32 <br/>
	 */
	public void initParamCache() {
		params.clear();
		
		try {
			List<SysParam> list = sysParamService.selectAll();
			if (null != list && list.size() > 0) {
				for (SysParam param : list) {
					params.put(param.getCode(), param);
				}
			}
		}catch (Exception e) {
			logger.error("系统参数缓存初始化失败...........", e);
		}
	}
	
	/**
	 * 功能：更新参数缓存<br/>
	 *
	 * @author 孟雪勤
	 * @version 2013-12-19 下午04:31:19 <br/>
	 */
	public static void updateParamCache(SysParam param) {
		params.put(param.getCode(), param);
	}
	
	/**
	 * 功能：根据指定key获得系统参数<br/>
	 *
	 * @author 孟雪勤
	 * @version 2013-12-19 下午04:29:35 <br/>
	 */
	public static SysParam getParam(String key) {
		return params.get(key);
	}

	/**
	 * 功能：删除指定id的系统参数缓存<br/>
	 *
	 * @author 孟雪勤
	 * @version 2013-12-19 下午05:33:13 <br/>
	 */
	public static List<SysParam> removeParamCache(String... ids) {
		List<SysParam> _list = new ArrayList<SysParam>();
		
		for (String _id : ids) {
			_list.add(params.remove(_id));
		}
		
		return _list;
	}
	
	/**
	 * 功能：初始化开关缓存<br/>
	 *
	 * @author 孟雪勤
	 * @version 2013-12-20 上午10:44:37 <br/>
	 */
	public void initSwitchCache() {
		switchs.clear();
		
		try {
			List<SysSwitch> list = sysSwitchService.selectAll();
			if (null != list && list.size() > 0) {
				for (SysSwitch _switch : list) {
					switchs.put(_switch.getCode(), _switch.getStatus());
				}
			}
		}catch (Exception e) {
			logger.error("系统开关缓存初始化失败...........", e);
		}
	}
	
	/**
	 * 功能：更新系统开关缓存<br/>
	 *
	 * @author 孟雪勤
	 * @version 2013-12-19 下午04:31:19 <br/>
	 */
	public static void updateSwitchCache(SysSwitch _switch) {
		switchs.put(_switch.getCode(), _switch.getStatus());
	}
	
	/**
	 * 功能：根据指定key获得系统开关<br/>
	 *
	 * @author 孟雪勤
	 * @version 2013-12-19 下午04:29:35 <br/>
	 */
	public static String getSwitch(String key) {
		return switchs.get(key);
	}
	
	/**
	 * 功能：删除指定id的系统开关缓存<br/>
	 *
	 * @author 孟雪勤
	 * @version 2013-12-19 下午05:33:13 <br/>
	 */
	public static void removeSwitchCache(String... ids) {
		for (String _id : ids) {
			switchs.remove(_id);
		}
	}
	
}
