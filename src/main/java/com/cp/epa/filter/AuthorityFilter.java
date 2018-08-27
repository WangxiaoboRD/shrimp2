package com.cp.epa.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.cp.epa.base.Base;
import com.cp.epa.base.BaseEntity;
import com.cp.epa.permission.entity.Users;
import com.cp.epa.permission.services.IUserAuthValueService;
import com.cp.epa.utils.SysContainer;

public class AuthorityFilter<T extends BaseEntity> extends Base implements Filter {

	private static final String EXCLUDESUFFIXS = "excludeSuffixs" ;
	private static final String LOGINPAGEURL="loginPageUrl";
	private static final String LOGINACTIONURL="loginActionUrl";
	private static final String AUTHORITYURL="authorityUrl";
	
	private List<String> excludeItem;
	private String loginPage;
	private String loaginAction;
	private String authority;
	@Autowired
	private IUserAuthValueService userAuthValueServiceImpl;
	
	public void destroy() {
		// TODO Auto-generated method stub
		excludeItem = null;
		loginPage=null;
		loaginAction=null;
		authority = null;
	}

	@SuppressWarnings("unchecked")
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		//user_add.jsp
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse)response; 
		String path = req.getServletPath();
		String paths = req.getContextPath();
		String basePath = req.getScheme()+"://"+req.getServerName()+":"+req.getServerPort()+paths;
		//System.out.println("-----:"+path);
		//HttpSession session = req.getSession();
		HttpSession session = req.getSession(true);
		T t = (T)session.getAttribute("CURRENTUSER");
		boolean b=true;
		try {
			if(t != null && !checkEndFuffix(path) && !checkUrl(path,loginPage) && !checkUrl(path,loaginAction) && !checkUrl(path,authority)){
				SysContainer.set((Users)t);
				b = userAuthValueServiceImpl.validate(path,(Users)t);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(b && (t != null || checkEndFuffix(path) || checkUrl(path,loginPage) || checkUrl(path,loaginAction))){
			chain.doFilter(request, response);
			SysContainer.romove();
		}else{
			if(t == null){
				req.getSession().invalidate();
				res.sendRedirect(basePath+loginPage);
			}else{
				response.setContentType("text/html");
				response.setCharacterEncoding("UTF-8");
				PrintWriter out = response.getWriter();
				out.print("NOPASS");
				out.flush();
				out.close();
			}
			//out.println("<html><head></head>");
			//out.println("<Script LANGUAGE='javascript'> alert('用户权限不足');</script>");
			//out.println("</head>");
			//out.println("<body>");
			//out.println("</body></html>");	
		}
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		excludeItem = Arrays.asList(filterConfig.getInitParameter(EXCLUDESUFFIXS).split(","));
		loginPage = filterConfig.getInitParameter(LOGINPAGEURL);
		loaginAction = filterConfig.getInitParameter(LOGINACTIONURL);
		authority = filterConfig.getInitParameter(AUTHORITYURL);
		// TODO Auto-generated method stub
		ServletContext servletContext = filterConfig.getServletContext();
		WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		AutowireCapableBeanFactory autowireCapableBeanFactory = webApplicationContext.getAutowireCapableBeanFactory();
		autowireCapableBeanFactory.configureBean(this, "userAuthValueServiceImpl");
		//userAuthValueServiceImpl = (IUserAuthValueService)autowireCapableBeanFactory.configureBean(this, "userAuthValueServiceImpl");
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
