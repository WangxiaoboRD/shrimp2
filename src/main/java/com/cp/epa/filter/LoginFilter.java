package com.cp.epa.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cp.epa.base.BaseEntity;

/**
 * 
 * 类名：LoginFilter  
 *
 * 功能：
 * @deprecated
 * @author dzl 
 * 创建时间：Nov 28, 2013 5:09:47 PM 
 * @version Nov 28, 2013
 */
public class LoginFilter<T extends BaseEntity> implements Filter { 
	
	private static final String EXCLUDESUFFIXS = "excludeSuffixs" ;
	private static final String LOGINPAGEURL="loginPageUrl";
	private static final String LOGINACTIONURL="loginActionUrl";
	
	private List<String> excludeItem;
	private String loginPage;
	private String loaginAction;
	
	public void init(FilterConfig config) throws ServletException {
		excludeItem = Arrays.asList(config.getInitParameter(EXCLUDESUFFIXS).split(","));
		loginPage = config.getInitParameter(LOGINPAGEURL);
		loaginAction = config.getInitParameter(LOGINACTIONURL);
	}
	
	@SuppressWarnings("unchecked")
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse)response; 
		HttpSession session = req.getSession();
		String paths = req.getContextPath();
		String path = req.getServletPath();
		
		//System.out.println("--s---:"+paths);
		//System.out.println("-----:"+path);
		String basePath = req.getScheme()+"://"+req.getServerName()+":"+req.getServerPort()+paths;
		//System.out.println("-----basePath2-----:"+basePath+loginPage);
		
		//System.out.println("--------------filter id ---------------:"+session.getId());
		
		T t = (T)session.getAttribute("CURRENTUSER");		

		if(checkEndFuffix(path) || checkUrl(path,loginPage) || checkUrl(path,loaginAction) || t != null){
			chain.doFilter(request, response);
		}else{
			
			//System.out.println("-----在这里清空了session有木有！！！！！@！！！！----------------------");
			
			req.getSession().invalidate();
			res.sendRedirect(basePath+loginPage);
		}
//		if(!checkEndFuffix(path) && !checkUrl(path,loginPage) && !checkUrl(path,loaginAction) && (t==null)){
//			req.getSession().invalidate();
//			res.sendRedirect(basePath+loginPage);
//			//res.sendRedirect("http://127.0.0.1:8080/PAP2/login.jsp");
//		}else{
//			chain.doFilter(request, response);
//		}
	}

	public void destroy() {
		excludeItem = null;
		loginPage=null;
		loaginAction=null;
	}
	
	private boolean checkEndFuffix(String url){
		if(excludeItem.contains(url.substring(url.lastIndexOf(".")+1)))
			return true;
		return false;
	}
	
	private boolean checkUrl(String path, String url){
		if(path.equals(url))
			return true;
		return false;
	}
	
}
