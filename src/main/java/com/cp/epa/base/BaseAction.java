package com.cp.epa.base;

import java.beans.Introspector;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.ParameterAware;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.cp.epa.exception.SystemException;
import com.cp.epa.utils.Pager;
import com.cp.epa.utils.PapUtil;
import com.cp.epa.utils.TypeUtil;
import com.opensymphony.xwork2.ActionSupport;


//@ParentPackage("json-default")
@Results({
	@Result(type="stream",name="down",params={"inputName","stream","contentType","application/octet-stream","bufferSize","1024","contentDisposition","attachment;filename=${docFileName}"}),
	@Result(type="json",name="json",params={"root","result"}),//,
	//@Result(type="stream",name="text",params={"inputName","stream","contentType","text/html:charset=UTF-8"})
})
public class BaseAction<E extends BaseEntity,Service extends IBaseService<E>> extends ActionSupport implements ParameterAware,SessionAware,ApplicationContextAware{

	private static final long serialVersionUID = -8431138094670115604L;
	//日志记录器 
	protected static final Logger logger = Logger.getLogger(BaseAction.class);
	//form提交通过dispatcher返回结果，需要返回的页面后缀
	public static final String LIST = "list";//返回列表页面
	public static final String SUCCESS ="success";//返回成功页面
	public static final String ERROR = "error";//返回错误页面
	public static final String INPUT = "input";//返回输入框页面
	public static final String MODIFY="modify";//返回修改页面
	public static final String EDIT="edit";//返回编辑页面
	public static final String SHOW="show";//返回明细页面
	public static final String NONE = "none"; //不需要视图时返回的页面
	public static final String INDEX="index";//返回主页面
	public static final String DOWN="down";//下载
	//Ajax提交返回结果集的标示
	public static final String JSON = "json";//返回Ajax提交json类型的结果集
	//public static final String TEXT = "text";//返回Ajax提交字符串类型的结果集,适用于stream处理
	//protected ResultEnum ret;
	
	//保存当前用户的到Session的Key键
	protected static final String CURRENTUSER="CURRENTUSER";
	
	//分页对象
	protected Pager<E> pageBean;

	//接收前台传输的参数ID（解决泛型基类中不设置Id的问题）
	protected String id;

	//接收前台批量传输的参数ID
	protected String ids;
	
	//当前实体类对象e
	protected E e;
	
	//当前实体的class对象
	protected Class<E> etype;
	
	//当前的session对象
	protected Map<String, Object> session;
	
	//spring容器ApplicationContext对象，并通过该对象的getBean(Object Name)方法获取实例
	protected ApplicationContext ctx;
	
	//当前action所对应的service服务层组件
	protected Service service;
	
	//收集和填充页面上不属于对象的一些值。相当于DTO
	//protected Map<String, String> tempStack; 该属性已放到BaseEntity类中进行处理
	
	//返回结果集
	protected Map<String, Object> result;
	
	//返回非json类型的字符串消息
	protected String message;
	
	//接受返回值对象的List容器
	protected List<E> elist;
	
	//返回一个流类型的结果集
	protected InputStream  stream;
	
	//--文件对象
	protected File doc;
	
	//--文件类型
	protected String docContentType;
	
	//--文件名称
	protected String docFileName;

	/**
	 * 构造方法
	 * 获取实体具体类型
	 */
	public BaseAction(){
	}
	
	/**
	 * 获取spring容器
	 * 功能: <br/>
	 * 重写：杜中良 <br/>
	 * @version ：Mar 16, 2013 3:20:24 PM<br/>
	 * @param applicationContext
	 * @throws BeansException <br/>
	 * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
	 */
	public void setApplicationContext(ApplicationContext applicationContext)throws BeansException {
		// TODO Auto-generated method stub
		this.ctx = applicationContext;
	}
	
	/**
	 * 初始化当前Action所对应的服务层service组件
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Mar 16, 2013 3:50:15 PM <br/>
	 */
	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init(){
		//获取当前action对象所持有的泛型类型
		Type[] _type = TypeUtil.getActualTypes(this.getClass());
		//获取第一个泛型类型及实体类
		etype = (Class<E>) _type[0];
		//获取第二个泛型类型及service类型，并获取其名称并进行转化
		String _serviceName = Introspector.decapitalize(((Class<Service>)_type[1]).getSimpleName().substring(1)+"Impl");
		//通过转化的名称从spring容器中获得当前action所对应的service对象实例
		service = (Service)ctx.getBean(_serviceName);
		
		 //初始化返回值Map容器
        if(result == null)
			result = new HashMap<String,Object>();
        //初始化返回值List容器
        if(elist == null)
        	elist = new ArrayList<E>();
        if(pageBean == null)
        	pageBean = new Pager<E>();
	}
	
	/**
	 * 功能: <br/>
	 * 获取session
	 * 重写：杜中良 <br/>
	 * @version ：Mar 6, 2013 10:37:01 AM<br/>
	 * @param session <br/>
	 * @see org.apache.struts2.interceptor.SessionAware#setSession(java.util.Map)
	 */
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
	
	/**
	 * 功能: <br/>
	 * 原始参数处理
	 * 重写：杜中良 <br/>
	 * @version ：Mar 6, 2013 10:37:44 AM<br/>
	 * @param arg0 <br/>
	 * @see org.apache.struts2.interceptor.ParameterAware#setParameters(java.util.Map)
	 */
	public void setParameters(Map<String, String[]> m) {
		//获取所有rquest参数
		Map<String, String[]> _map = new HashMap<String,String[]>(m);
		//通过泛型获取对象实例，并将对象参数构建到对象内
		if(e == null){
			try {
				e = this.etype.newInstance();
			} catch (InstantiationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IllegalAccessException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		//对于非对象类型的参数构建到临时对象tempStack中
		//Map<String,Object> map = e.getMap(); 
		Map<String,Object> map = new HashMap<String, Object>();
		Iterator<Entry<String, String[]>> iterator = _map.entrySet().iterator();
        while(iterator.hasNext()) {
            Map.Entry<String,String[]> entry = iterator.next();
            String key = entry.getKey();           
            String[] value = entry.getValue();
            map.put(key, /*Arrays.toString(value).replaceAll("[\\[\\]\\s]", "")*/PapUtil.arrayToString(value));
            /*if(key.startsWith("tempStack")){
            	if(tempStack == null){
            		tempStack = new HashMap<String,String>();
            	}
            	tempStack.put(key.replace("tempStack.", ""), PapUtil.arrayToString(value));
            }*/
        }
        e.setMap(map);
	}

	/**
	 * 处理返回Text类型的值（返回前端客户端字符串）
	 * 功能：<br/>
	 * Ajax提交返回字符串处理，前台ajax需要将dataType设置为text 支持中文
	 * @author 杜中良
	 * @version Mar 13, 2013 4:09:35 PM <br/>
	 */
	public String text(String value){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=UTF-8"); 
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		try {
			response.getWriter().append(value);
			response.getWriter().flush();
			response.getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 处理返回Text类型的值（返回前端客户端字符串）
	 * 功能：<br/>
	 * 通过struts2提供的流的方式将结果集提供到前台ajax的请求响应
	 * 
	 * 没有做中文处理？？？？
	 * @author 杜中良
	 * @version Mar 29, 2013 2:57:25 PM <br/>
	 */
	@SuppressWarnings("deprecation")
	public InputStream textStream(String value){
		stream = new StringBufferInputStream(value);
		return null;
	}
	
	/**
	 * 返回一个空的添加页面 页面的名称必须是 类名_input.jsp
	 * 功能: <br/>
	 * 
	 * 重写：杜中良 <br/>
	 * 
	 * @version ：Nov 10, 2013 9:33:49 AM<br/>
	 * 
	 * @return <br/>
	 * @see com.opensymphony.xwork2.ActionSupport#input()
	 */
	public String input(){
		return INPUT;
	}
	
	/**
	 * 返回一个空的列表页面 页面的名称必须是 类名_list.jsp
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Nov 10, 2013 9:34:15 AM <br/>
	 */
	public String list(){
		return LIST;
	}
	
	/**
	 * 返回一个空的编辑页面 页面的名称必须是 类名_edit.jsp
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Nov 10, 2013 9:34:36 AM <br/>
	 */
	public String edit(){
		return EDIT;
	}
	
	/**
	 * 简单对象存储
	 * 功能：<br/>
	 * @author 杜中良
	 * @version Mar 25, 2013 2:51:58 PM <br/>
	 */
	public String save()throws Exception{
		message = (service.save(e)).toString();
		text(message);
		return NONE;
	}
	/**
	 * 简单查询某个对象
	 * 功能：<br/>
	 * @author 杜中良
	 * @version Mar 22, 2013 10:13:54 AM <br/>
	 */
	public String load()throws Exception{
		elist = service.selectAll();
		result.put("Rows", elist);
		//result.put("Total", elist.size());
		return JSON;
	}
	

	/**
	 * 简单查询某个对象
	 * 功能：<br/>
	 * @author 杜中良
	 * @version Mar 22, 2013 10:13:54 AM <br/>
	 */
	public String loadByEntity()throws Exception{
				
		elist = service.selectAll(e);
		result.put("Rows", elist);
		//result.put("Total", elist.size());
		return JSON;
	}
	
	/**
	 * 简单对象分页查询
	 * 功能：<br/>
	 * @author 杜中良
	 * @version Mar 22, 2013 10:13:54 AM <br/>
	 * @throws SystemException 
	 */
	public String loadByPage()throws Exception{
		service.selectAll(e,pageBean);
		result.put("Rows", pageBean.getResult());
		result.put("Total", pageBean.getTotalCount());
		pageBean.setResult(null);
		result.put("pageBean", pageBean);
		return JSON;
	}
	
	/**
	 * 更新对象值的前置处理
	 * 功能：<br/>
	 * @author 杜中良
	 * @version Mar 22, 2013 3:22:32 PM <br/>
	 */
	public String loadUpdateById()throws Exception{
		//e = service.selectById(etype,id);
		//e = service.selectById(id);
		Integer _id = TypeUtil.getIdType(id,e.getClass());
		e = _id==null?service.selectById(id):service.selectById(_id);
		return MODIFY;
	}
	
	/**
	 * 更具对象查看相信信息
	 * 功能：<br/>
	 * @author 杜中良
	 * @version Mar 22, 2013 3:22:32 PM <br/>
	 */
	public String loadDetailById()throws Exception{
		//e = service.selectById(etype,id);
		//e = service.selectById(id);
		Integer _id = TypeUtil.getIdType(id,e.getClass());
		e = _id==null?service.selectById(id):service.selectById(_id);
		return SHOW;
	}
	
	/**
	 * 简单对象拆分更新
	 * 功能：<br/>
	 * @author 杜中良
	 * @version Apr 1, 2013 9:17:59 AM <br/>
	 */
	public String modify()throws Exception{
		//service.update(e);
		if(service.updateHql(e)>0)
			message="MODIFYOK";
		text(message);
		return NONE;
	}
	/**
	 * 简单对象非拆分更新
	 * 功能：<br/>
	 * @author 杜中良
	 * @version Apr 1, 2013 9:17:59 AM <br/>
	 */
	public String modifyAll()throws Exception{
		//service.update(e);
		service.update(e);
		message="MODIFYOK";
		text(message);
		return NONE;
	}
	
	/**
	 * 简单对象删除
	 * 功能：<br/>
	 * @author 杜中良
	 * @version Apr 1, 2013 12:00:20 PM <br/>
	 */
	public String delete()throws Exception{	
		Integer[] _ids =TypeUtil.getIdsType(ids,e.getClass());
		int k = _ids ==null?service.deleteByIds(ids.split(",")):service.deleteByIds(_ids);
		// service.deleteByIds(ids.split(","));		
		message = k+"";		
		text(message);
		return NONE;
	}
	
	/**
	 * 获取下拉框的值
	 * 功能：<br/>
	 * @author 杜中良
	 * @version Apr 16, 2013 3:10:16 PM <br/>
	 */
	public String loadType()throws Exception{
		elist = service.selectAll();
		result.put("Rows", elist);
		return JSON;
	}
	
	/**
	 * 保存单据模式的对象???????
	 * 功能：<br/>
	 * @author 杜中良
	 * @version Apr 16, 2013 3:10:16 PM <br/>
	 */
	public String saveAndDetail()throws Exception{
		message = (service.saveAndDetail(e)).toString();
		text(message);
		return NONE;
	}
	/**
	 * 上传
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Jun 30, 2013 3:20:39 PM <br/>
	 */
	public String importFile()throws Exception{
		boolean b = service.operFile(doc,docFileName);
		if(b)
			message="IMPORTOK";
		else
			message="IMPORTFAIL";
		result.put("message", message);
		return JSON;
	}
	
	/**
	 * 导出
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Jul 9, 2013 9:53:29 AM <br/>
	 */
	public String exportFile()throws Exception{
		stream = service.exportFile(e);
		docFileName= e.getClass().getSimpleName()+".xls";
		return DOWN;
	}
	
//	/**
//	 * 单据模式的更新
//	 * 功能：<br/>
//	 * @author 杜中良
//	 * @version Apr 1, 2013 9:17:59 AM <br/>
//	 */
//	public String ByDetail()throws Exception{
//		//service.update(e);
//		if(service.updateByDetail(e)>0)
//			message="OK";
//		text(message);
//		return NONE;
//	}
	
	/*public Map<String, String> getTempStack() {
		return tempStack;
	}
	public void setTempStack(Map<String, String> tempStack) {
		this.tempStack = tempStack;
	}*/
	public Map<String, Object> getResult() {
		return result;
	}
	public void setResult(Map<String, Object> result) {
		this.result = result;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public void setId(String id){
		this.id=id;
	}
	public String getId(){
		return id;
	}
	public InputStream getStream() {
		return stream;
	}
	public void setStream(InputStream stream) {
		this.stream = stream;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}

	public Pager<E> getPageBean() {
		return pageBean;
	}

	public void setPageBean(Pager<E> pageBean) {
		this.pageBean = pageBean;
	}

	public File getDoc() {
		return doc;
	}

	public void setDoc(File doc) {
		this.doc = doc;
	}

	public String getDocContentType() {
		return docContentType;
	}

	public void setDocContentType(String docContentType) {
		this.docContentType = docContentType;
	}

	public String getDocFileName() {
		return docFileName;
	}

	public void setDocFileName(String docFileName) {
		this.docFileName = docFileName;
	}

	public List<E> getElist() {
		return elist;
	}

	public void setElist(List<E> elist) {
		this.elist = elist;
	}
	public E getE() {
		return e;
	}

	public void setE(E e) {
		this.e = e;
	}
}