package com.cp.epa.debug.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

public class P6spyUtil {

	public static final String system = "DEBUGSYSTEM";
	public static final String sql = "DEBUGSQL";
	public static final String business = "BUSINESS";
	public static final String actionFunction = "ACTIONFUNCTION";
	public static final String daoFunction = "DAOFUNCTION";
	public static final String serviceFunction ="SERVICEFUNCTION";
	public static final String FRONTMARK = "0";
	public static final String BACKMARK = "1";
	public static final String FILENAME="fileName";
	@SuppressWarnings("unchecked")
	public static final Map m = Collections.synchronizedMap(new IdentityHashMap());
	public static final List serviceList = Collections.synchronizedList(new ArrayList());
	public static final List daoList = Collections.synchronizedList(new ArrayList());
	public final static ThreadLocal<String> systemSession = new ThreadLocal<String>(){
		public String initialValue(){
			return "";
		}
	};
	
}
