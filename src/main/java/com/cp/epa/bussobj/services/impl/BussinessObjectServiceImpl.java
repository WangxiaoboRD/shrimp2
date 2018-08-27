package com.cp.epa.bussobj.services.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.cp.epa.annotation.Bill;
import com.cp.epa.annotation.BussEle;
import com.cp.epa.annotation.MethodMeans;
import com.cp.epa.base.BaseServiceImpl;
import com.cp.epa.bussobj.dao.IBussinessObjectDao;
import com.cp.epa.bussobj.entity.BaseTable;
import com.cp.epa.bussobj.entity.BaseTableColumn;
import com.cp.epa.bussobj.entity.BussObjProperty;
import com.cp.epa.bussobj.entity.BussinessEle;
import com.cp.epa.bussobj.entity.BussinessEleDetail;
import com.cp.epa.bussobj.entity.BussinessObject;
import com.cp.epa.bussobj.entity.ImportantObj;
import com.cp.epa.bussobj.entity.MethodDesc;
import com.cp.epa.bussobj.entity.MethodDescDetail;
import com.cp.epa.bussobj.services.IBaseTableColumnService;
import com.cp.epa.bussobj.services.IBaseTableService;
import com.cp.epa.bussobj.services.IBussObjPropertyService;
import com.cp.epa.bussobj.services.IBussinessEleDetailService;
import com.cp.epa.bussobj.services.IBussinessEleService;
import com.cp.epa.bussobj.services.IBussinessObjectService;
import com.cp.epa.bussobj.services.IMethodDescDetailService;
import com.cp.epa.bussobj.services.IMethodDescService;
import com.cp.epa.bussobj.utils.ClassUtils;
import com.cp.epa.check.entity.CheckBill;
import com.cp.epa.check.services.ICheckBillService;
import com.cp.epa.exception.SystemException;
import com.cp.epa.log.entity.ModifyLogObject;
import com.cp.epa.log.services.IModifyLogObjectService;
import com.cp.epa.permission.entity.AuthField;
import com.cp.epa.permission.entity.AuthObj;
import com.cp.epa.permission.entity.BussObjAuthProperty;
import com.cp.epa.permission.services.IAuthObjService;
import com.cp.epa.permission.services.IBussObjAuthPropertyService;
import com.cp.epa.permission.utils.PmUtil;
import com.cp.epa.utils.ISqlMap;
import com.cp.epa.utils.Pager;
import com.cp.epa.utils.SqlMap;

/**
 * 
 * 类名：BussinessObjectServiceImpl <br />
 * 
 * 功能：
 * 
 * @author zp <br />
 *         创建时间：2013-7-10 上午10:06:10 <br />
 * @version 2013-7-10
 */
public class BussinessObjectServiceImpl extends BaseServiceImpl<BussinessObject, IBussinessObjectDao> implements IBussinessObjectService, ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private IModifyLogObjectService modifyLogObjectService;

	@Autowired
	private IBussObjPropertyService bussObjPropertyService;

	@Autowired
	private IMethodDescService methodDescService;

	@Autowired
	private IMethodDescDetailService methodDescDetailService;

	@Autowired
	private IBussinessEleDetailService bussinessEleDetailService;

	@Autowired
	private IBaseTableService baseTableService;

	@Autowired
	private IBaseTableColumnService baseTableColumnService;

	/** 权限过滤属性业务逻辑注册 */
	@Resource
	private IBussObjAuthPropertyService authPropertyService;
	/** 权限对象业务注册 */
	@Resource
	private IAuthObjService authObjService;
	/** 业务元素业务注册 */
	@Resource
	private IBussinessEleService bussinessEleService;
	
	@Resource
	private ICheckBillService checkBillService;

	/**
	 * 
	 * 功能: <br/>
	 * 
	 * 重写：zp <br/>
	 * 刷新业务对象属性到数据库
	 * 
	 * @version ：2013-8-8 上午11:07:17<br/>
	 * 
	 * @throws Exception
	 * <br/>
	 * @see com.zhongpin.pap.services.IBussinessObjectService#refreshProperty()
	 */
	@SuppressWarnings("unchecked")
	public void refreshProperty() throws Exception {
		List<Class> classes = ClassUtils.getClasses("com.cp");
		if (null != classes && classes.size() != 0) {
			List<CheckBill> cblist = new ArrayList<CheckBill>();
			for (Class clazz : classes) {
				BussEle bussEle = (BussEle) clazz.getAnnotation(BussEle.class);
				Bill bill = (Bill)clazz.getAnnotation(Bill.class);
				CheckBill cb = null;
				if(bill != null){
					cb = new CheckBill();
					cb.setBussName(bill.name());
					cb.setClassName(clazz.getSimpleName());
					cb.setFullName(clazz.getName());
					cblist.add(cb);
				}
				BussinessObject bussinessObject = null;
				if (null != bussEle) {
					bussinessObject = new BussinessObject();
					bussinessObject.setBussCode(clazz.getSimpleName());
					bussinessObject.setBussName(bussEle.name());
					bussinessObject.setFullClassName(clazz.getName());
					if (null != bussEle.type() && !"".equals(bussEle.type())) {
						bussinessObject.setBussType(bussEle.type());
					}else {
						Table table = (Table) clazz.getAnnotation(Table.class);
						String tablename = "";
						if (null != table) {
							tablename = table.name();
						}else {
							tablename = clazz.getSimpleName();
						}
						// 表名采用小写字母
						BaseTable baseTable = baseTableService.selectById(tablename.toLowerCase());
						if (null != baseTable) {
							bussinessObject.setTableCode(baseTable.getTabCode());
							bussinessObject.setBussType(baseTable.getTabType().toString());
						}
					}
					BussinessObject boj = dao.selectById(clazz.getSimpleName());
					if (null == boj) {
						dao.insert(bussinessObject);
					}
					ISqlMap<String, String, Object> sqlMap = new SqlMap<String, String, Object>();
					sqlMap.put("className", "=", clazz.getName());
					sqlMap.put("isRoot", "=", "Y");
					List<BussObjProperty> lt = bussObjPropertyService.selectHQL(sqlMap);
					if (null != lt && lt.size() != 0) {
					}else {
						saveModelTree(clazz);
					}
					saveMethodDesc(clazz);
				}
			}
			if(cblist.size()>0){
				for(CheckBill _cb : cblist){
					CheckBill c = checkBillService.selectBySinglet("className", _cb.getClassName());
					if(c == null)
						checkBillService.save(_cb);
				}
			}
		}
	}

	/**
	 * 功能：保存方法<br/>
	 * @author zp
	 * @version 2013-9-4 下午04:18:50 <br/>
	 */
	public void saveMethodDesc(Class<?> class1) throws Exception {

		String classname = "com.cp.epa.services.impl." + class1.getSimpleName() + "ServiceImpl";
		// System.out.println("------------"+classname);
		Class<?> class2 = null;
		MethodDesc methodDesc = null;
		try {
			class2 = Class.forName(classname);
		}catch (Exception e) {
			return;
		}
		List<Method> liMethods = ClassUtils.getDealMethods(class2);
		BussinessObject bussinessObject = null;
		if (null != liMethods && liMethods.size() != 0) {

			for (Method mm : liMethods) {
				MethodMeans means = mm.getAnnotation(MethodMeans.class);
				if (null != means) {
					methodDesc = new MethodDesc();
					String descs = means.descs();
					if (null != descs && !"".equals(descs)) {
						methodDesc.setDescs(descs);
					}
					methodDesc.setClassplace(mm.getDeclaringClass().getName());
					methodDesc.setMethodName(mm.getName());
					Class<?> paramClass[] = mm.getParameterTypes();
					String type = "";
					if (null != paramClass && paramClass.length != 0) {
						for (Class<?> cll : paramClass) {
							type = type + "  ,  " + cll.getName();
						}
						if (type.indexOf(",") != -1) {
							type = type.substring(type.indexOf(",") + 1);
						}
					}

					ISqlMap<String, String, String> sqlMap = new SqlMap<String, String, String>();
					sqlMap.put("methodName", "=", mm.getName());
					sqlMap.put("paramlist", "=", type);
					sqlMap.put("bussinessObject.bussCode", "=", class1.getSimpleName());

					List<MethodDesc> methodDescs = methodDescService.selectHQL(sqlMap);
					if (null != methodDescs && methodDescs.size() != 0) {
						continue;
					}
					methodDesc.setParamlist(type);
					bussinessObject = dao.selectById(class1.getSimpleName());
					methodDesc.setBussinessObject(bussinessObject);
				}
				if (null != methodDesc) {
					if (null != bussinessObject) {
						Object oid = methodDescService.save(methodDesc);
						if (null != oid) {
							saveMethodDescDetail(methodDesc, mm, class1.getSimpleName());
						}
					}
				}
			}
		}
	}

	/**
	 * 
	 * 功能：<br/>
	 * 保存方法明细
	 * 
	 * @author zp
	 * @version 2013-9-4 下午04:49:08 <br/>
	 */
	public void saveMethodDescDetail(MethodDesc methodDesc, Method mm, String className) throws Exception {

		MethodMeans means = mm.getAnnotation(MethodMeans.class);
		MethodDescDetail methodDescDetail = null;
		if (null != means) {
			Class<?> paramClass[] = mm.getParameterTypes();
			String type = "";
			if (null != paramClass && paramClass.length != 0) {
				for (Class<?> cll : paramClass) {
					type = type + "  ,  " + cll.getName();
				}
				if (type.indexOf(",") != -1) {
					type = type.substring(type.indexOf(",") + 1);
				}
			}
			if (null != paramClass && paramClass.length != 0) {
				String[] parammean = means.param();
				for (int i = 0; i < paramClass.length; i++) {
					methodDescDetail = new MethodDescDetail();
					methodDescDetail.setParamList(type);
					methodDescDetail.setMethodDesc(methodDesc);
					methodDescDetail.setModel(className);
					Class<?> class2 = paramClass[i];
					methodDescDetail.setParamType(class2.getName());
					if (i + 1 <= parammean.length) {
						methodDescDetail.setParamdesc(parammean[i]);
					}
					methodDescDetailService.save(methodDescDetail);
				}
			}
		}

	}

	/**
	 * 功能：保存model属性<br/>
	 * @author zp
	 * @version 2013-8-15 下午05:24:44 <br/>
	 * @throws Exception
	 */
	public void saveModelTree(Class<?> class1) throws Exception {

		BussObjProperty bussObjProperty = null;
		bussObjProperty = new BussObjProperty();
		bussObjProperty.setClassName(class1.getName());
		bussObjProperty.setIsRoot("Y");
		bussObjProperty.setPid(-1);
		bussObjProperty.setPropertyName(class1.getSimpleName());
		Object object = bussObjPropertyService.save(bussObjProperty);
		List<Field> fields = ClassUtils.getAllFields(class1);
		int pid = Integer.valueOf(object.toString());
		BussObjProperty bop = null;
		for (Field field : fields) {
			String method = "get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
			Method method2 = null;
			try {
				method2 = class1.getMethod(method, null);
			}catch (Exception e) {
				continue;
			}
			if (null != method2) {
				boolean isadd = true;
				Transient transient1 = method2.getAnnotation(Transient.class);
				if (null != transient1) {
					isadd = false;
				}

				if (isadd) {
					bop = new BussObjProperty();
					bop.setBelongClass(class1.getName());
					bop.setPid(pid);
					bop.setPropertyName(field.getName());
					bop.setIsBussObj("N");
					if (null != field.getType()) {
						Class<?> classtype = field.getType();
						BussEle buss = classtype.getAnnotation(BussEle.class);
						if (null != buss) {
							// 防止属性递归
							if (!class1.getSimpleName().equals(field.getType().getSimpleName())) {
								bop.setClassName(field.getType().getName());
								bop.setIsBussObj("Y");
							}
						}

						Column column = method2.getAnnotation(Column.class);
						if (null != column) {
							// 指定表字段：字段名默认为全小写
							String fieldname = column.name();
							if (null != fieldname) {
								bop.setFieldName(fieldname.toLowerCase()); // 属性对象表字段名转化为全小写
							}else {
								bop.setFieldName(field.getName().toLowerCase()); // 属性对象表字段名转化为全小写
							}
						}else {
							bop.setFieldName(field.getName().toLowerCase());
						}

						JoinColumn joinColumn = method2.getAnnotation(JoinColumn.class);
						if (null != joinColumn) {
							String fieldcolumn = joinColumn.name();
							if (null != fieldcolumn) {
								bop.setFieldName(fieldcolumn.toLowerCase());
							}else {
								bop.setFieldName(field.getName().toLowerCase());
							}
						}
					}
					Object oid = bussObjPropertyService.save(bop);
				}
			}
		}

	}

	//@PostConstruct
	public void onApplicationEvent(ContextRefreshedEvent arg0) {
		logger.info("业务对象扫描启动...........................");
		try {
			refreshProperty();
		}catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("业务对象扫描结束...........................");
	}

	public void refreshSingleModel(BussinessObject bussinessObject) throws Exception {
		ISqlMap<String, String, Object> sqlMap = new SqlMap<String, String, Object>();
		String fullclassName = bussinessObject.getFullClassName();
		sqlMap.put("className", "=", fullclassName);
		sqlMap.put("isRoot", "=", "Y");
		List<BussObjProperty> lt = bussObjPropertyService.selectHQL(sqlMap);
		Class<?> class1 = Class.forName(bussinessObject.getFullClassName());
		if (null != lt && lt.size() != 0) {
			ISqlMap<String, String, Object> sMap = new SqlMap<String, String, Object>();
			sMap.put("pid", "=", lt.get(0).getId());
			bussObjPropertyService.delete(sMap);
			bussObjPropertyService.deleteById(lt.get(0).getId());

			ISqlMap<String, String, String> methodMap = new SqlMap<String, String, String>();
			methodMap.put("bussinessObject.bussCode", "=", class1.getSimpleName());
			methodDescService.delete(methodMap);

			ISqlMap<String, String, String> methodISqlMap = new SqlMap<String, String, String>();
			methodISqlMap.put("model", "=", class1.getSimpleName());
			methodDescDetailService.delete(methodISqlMap);

		}
		// 属性
		saveModelTree(class1);
		// 方法
		saveMethodDesc(class1);

	}

	public List<BaseTableColumn> selecTableColumns(String bussCode) throws Exception {
		BussinessObject bussinessObject = dao.selectById(bussCode);
		return bussObjPropertyService.selectBaseTableColumns(bussinessObject.getFullClassName(), true);
	}

	public List<ImportantObj> selectImportantObjs(String bussCode) throws Exception {
		List<ImportantObj> list = null;
		List<BaseTableColumn> baseTableColumns = selecTableColumns(bussCode);
		if (null != baseTableColumns && baseTableColumns.size() != 0) {
			list = new ArrayList<ImportantObj>();
			for (BaseTableColumn btc : baseTableColumns) {
				if (null != btc.getBussinessEle() && "N".equals(btc.getIsHand())) {
					BussinessEle bussinessEle = btc.getBussinessEle();
					if (bussinessEle.getValueType().equals("2")) {
						list.add(new ImportantObj(bussinessEle.getEcode(), bussinessEle.getRefTable(), btc.getId()));
					}else if (bussinessEle.getValueType().equals("3")) {
						ISqlMap<String, String, String> sqlMap = new SqlMap<String, String, String>();
						sqlMap.put("bussinessEle.ecode", "=", bussinessEle.getEcode());
						List<BussinessEleDetail> bussinessEleDetails = bussinessEleDetailService.selectHQL(sqlMap);
						if (null != bussinessEleDetails && bussinessEleDetails.size() != 0) {
							for (BussinessEleDetail buDetail : bussinessEleDetails) {
								list.add(new ImportantObj(buDetail.getDcode(), buDetail.getValue(), btc.getId()));
							}
						}
					}
				}
			}
		}
		return list;
	}

	public List<String> selectFieldName(String ecode, String fullClassName) throws Exception {

		List<String> result = new ArrayList<String>();

		Class<?> class1 = Class.forName(fullClassName);
		String tableName = "";
		tableName = class1.getSimpleName();
		Table table = class1.getAnnotation(Table.class);
		if (null != table) {
			if (null != table.name() && !"".equals(table.name())) {
				tableName = table.name();
			}
		}

		ISqlMap<String, String, Object> sMap = new SqlMap<String, String, Object>();
		sMap.put("baseTable.tabCode", "=", tableName);
		sMap.put("bussinessEle.ecode", "=", ecode);
		// 查询出对应的表字段信息
		List<BaseTableColumn> refList = baseTableColumnService.selectHQL(sMap);

		ISqlMap<String, String, Object> sqlMap = new SqlMap<String, String, Object>();
		sqlMap.put("className", "=", fullClassName);
		sqlMap.put("isRoot", "=", "Y");
		List<BussObjProperty> bussObjProperties = bussObjPropertyService.selectHQL(sqlMap);

		ISqlMap<String, String, Object> sm = new SqlMap<String, String, Object>();
		sm.put("pid", "=", bussObjProperties.get(0).getId());
		// 查出类属性
		List<BussObjProperty> list = bussObjPropertyService.selectHQL(sm);
		if (null != list && list.size() != 0) {
			N: for (BussObjProperty property : list) {
				String fieldname = property.getFieldName();
				if (null != refList && refList.size() != 0) {
					for (BaseTableColumn btc : refList) {
						if (fieldname.equals(btc.getFdcode())) {
							result.add(fieldname);
							break N;
						}
					}
				}
			}
		}

		return result;
	}

	/**
	 * 功能: 权限过滤属性设置<br/>
	 * 
	 * 重写：孟雪勤 <br/>
	 * 
	 * @version ：2013-11-15 上午11:49:48<br/>
	 * 
	 * @param entity
	 * @throws Exception
	 * <br/>
	 * @see com.zhongpin.pap.bussobj.services.IBussinessObjectService#authPropertySet(com.zhongpin.pap.bussobj.entity.BussinessObject)
	 */
	public void addAuthPropertySet(BussinessObject entity) throws Exception {

		// 过滤属性明细删除
		Map<String, String> tempStack = entity.getTempStack();
		if (null != tempStack) {
			if (!"".equals(tempStack.get("delIds"))) {
				// 删除的过滤属性明细id
				String delIds = tempStack.get("delIds");
				for (String delId : delIds.split(",")) {
					authPropertyService.deleteById(Integer.parseInt(delId));
				}
			}
			// 清空权限过滤属性标识
			if (!"".equals(tempStack.get("delPropertyIds"))) {
				String delPropertyIds = tempStack.get("delPropertyIds");
				List<Integer> pIds = new ArrayList<Integer>();
				for (String pid : delPropertyIds.split(",")) {
					pIds.add(Integer.parseInt(pid));
				}
				updateIsAuthProperty(pIds, "N");
			}
		}

		BussinessObject obj = dao.selectById(entity.getBussCode());

		// 更新后的权限过滤属性明细
		List<BussObjAuthProperty> authProperties = entity.getAuthProperties();
		List<Integer> pIds = new ArrayList<Integer>();
		if (null != authProperties && authProperties.size() > 0) {
			for (BussObjAuthProperty property : authProperties) {
				property.setBussObj(obj);
				if (null == property.getId()) {
					property.setEnabled(0);
					pIds.add(property.getBussObjProperty().getId());
					// 保存权限过滤属性
					authPropertyService.save(property);
				}
			}
		}

		dao.update(obj);
		// 更新业务对象属性的权限过滤属性标识
		updateIsAuthProperty(pIds, "Y");
	}

	/**
	 * 功能：更新业务对象属性权限过滤属性标识<br/>
	 * 
	 * @author 孟雪勤
	 * @version 2013-11-15 下午02:37:51 <br/>
	 */
	private void updateIsAuthProperty(List<Integer> objPropertyIds, String isAuthProperty) throws Exception {
		if (objPropertyIds.size() > 0) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("isAuthProperty", isAuthProperty);
			bussObjPropertyService.updateByIds(objPropertyIds, map);
		}
	}

	/**
	 * 功能: 绑定权限对象<br/>
	 * 
	 * 重写：孟雪勤 <br/>
	 * 
	 * @version ：2013-11-18 下午02:39:27<br/>
	 * 
	 * @param entity
	 * @throws Exception
	 * <br/>
	 * @see com.zhongpin.pap.bussobj.services.IBussinessObjectService#bindAuthObj(com.zhongpin.pap.bussobj.entity.BussinessObject)
	 */
	public void operateBindAuthObj(BussinessObject entity) throws Exception {

		// 权限过滤属性集合
		List<BussObjAuthProperty> authProperties = entity.getAuthProperties();
		if (null != authProperties && authProperties.size() > 0) {
			// 记录权限对象编码
			List<String> authObjCodes = new ArrayList<String>();
			for (BussObjAuthProperty property : authProperties) {
				String authObjCode = property.getAuthObj().getCode();
				// 去除重复的权限对象
				if (null != authObjCode && !"".equals(authObjCode)) {
					if (!authObjCodes.contains(authObjCode)) {
						authObjCodes.add(authObjCode);
					}
				}
			}

			if (null != authObjCodes && authObjCodes.size() > 0) {
				for (String code : authObjCodes) {
					AuthObj authObj = authObjService.selectById(code);
					List<AuthField> fields = authObj.getFieldSet();
					if (null != fields && fields.size() > 0) {
						// 记录匹配项
						int quoteFieldNum = 0;
						for (AuthField field : fields) {
							for (BussObjAuthProperty p : authProperties) {
								if (field.getCode().equals(p.getAuthField().getCode())) {
									quoteFieldNum++;
									break;
								}
							}
						}

						if (quoteFieldNum < fields.size()) {
							throw new SystemException("权限对象[" + code + "]的权限字段未完全绑定该业务对象的属性，绑定失败！");
						}
					}
				}
			}

			for (BussObjAuthProperty authProperty : authProperties) {
				BussObjAuthProperty oldProperty = authPropertyService.selectById(authProperty.getId());
				// 权限对象
				AuthObj obj = authProperty.getAuthObj();
				String authObjCode = PmUtil.emptyToNull(obj.getCode());
				if (null == authObjCode) {
					obj = null;
				}
				oldProperty.setAuthObj(obj);// 更新权限对象

				// 权限字段
				AuthField field = authProperty.getAuthField();
				String authFieldCode = PmUtil.emptyToNull(field.getCode());
				if (null == authFieldCode) {
					field = null;
				}
				oldProperty.setAuthField(field);// 更新权限字段

				// 更新权限过滤属性信息
				authPropertyService.update(oldProperty);
			}
		}
	}

	/**
	 * 功能: 根据参考表编码查询参考表信息<br/>
	 * 重写：孟雪勤 <br/>
	 * 信息包括： 1、action url 2、权限分配提交列 3、表格列集合
	 * 
	 * @version ：2013-11-20 下午04:31:01<br/>
	 * 
	 * @param entity
	 * @return
	 * @throws Exception
	 * <br/>
	 * @see com.zhongpin.pap.bussobj.services.IBussinessObjectService#selectRefTableInfo(com.zhongpin.pap.bussobj.entity.BussinessObject)
	 */
	public Map<String, Object> selectRefTableInfo(BussinessObject entity) throws Exception {

		// 表编码
		String tabCode = entity.getTableCode();

		Map<String, Object> result = new HashMap<String, Object>();

		// 根据表编码查询对应的业务对象
		BussinessObject bussObj = dao.selectBySinglet("tableCode", tabCode);
		if (null == bussObj) {
			throw new SystemException("业务元素参考表[" + tabCode + "]没有对应的业务对象！");
		}

		// action url
		result.put("actionUrl", PmUtil.getActionUrlByBussObj(bussObj.getBussCode()));

		Map<String, String> tempStack = entity.getTempStack();
		if (null != tempStack) {
			// 业务元素编码
			String bussEleCode = PmUtil.emptyToNull(tempStack.get("bussinessEleCode"));
			if (null == bussEleCode) {
				throw new SystemException("业务元素编码参数为空！");
			}

			BussinessEle ele = bussinessEleService.selectById(bussEleCode);
			if (null != ele) {
				// 权限字段树状 1=是 0=否
				String refTabField = ele.getRefTabField();// 参考表字段
				String refTabParField = ele.getRefTabParField();// 参考表父级字段
				String refTabNameField = ele.getRefFieldName();// 参考表名称字段
				// 业务对象类全名
				String fullClassName = bussObj.getFullClassName();
				// 非树状
				String refProName = bussObjPropertyService.selectPropertyName(fullClassName, refTabField);
				if (null != refTabField) {
					// 参考属性名
					result.put("refPro", refProName);
					// 参考属性描述
					result.put("refProDesc", baseTableColumnService.selectDesc(refTabField, tabCode));
				}
				if (null != refTabParField) {
					// 参考父级属性名
					result.put("refParPro", bussObjPropertyService.selectPropertyName(fullClassName, refTabParField));
					// 参考父级属性描述
					result.put("refParProDesc", baseTableColumnService.selectDesc(refTabParField, tabCode));
				}
				if (null != refTabNameField) {
					// 参考名称属性名
					result.put("refNamePro", bussObjPropertyService.selectPropertyName(fullClassName, refTabNameField));
					// 参考名称属性描述
					result.put("refNameProDesc", baseTableColumnService.selectDesc(refTabNameField, tabCode));
				}
			}
		}
		return result;
	}

	/**
	 * 查询业务对象信息，结果集不包含变更日志中的对象 功能：<br/>
	 * 
	 * @author 杜中良
	 * @version Dec 16, 2013 9:54:11 AM <br/>
	 */
	public void selectByCont(BussinessObject entity, Pager<BussinessObject> page) throws Exception {
		// TODO Auto-generated method stub
		List<ModifyLogObject> mlol = modifyLogObjectService.selectAll();
		selectAll(entity, page);
		List<BussinessObject> bl = page.getResult();
		if (mlol != null && mlol.size() > 0) {
			if (bl != null && bl.size() > 0) {
				// 这个处理调用Iterator 本身的方法 remove(),会正常执行
				for (Iterator it = bl.iterator(); it.hasNext();) {
					BussinessObject bo = (BussinessObject) it.next();
					for (ModifyLogObject mbo : mlol) {
						if (bo.getBussCode().equals(mbo.getBussObj().getBussCode()))
							it.remove();
					}
				}
			}
		}
	}
}
