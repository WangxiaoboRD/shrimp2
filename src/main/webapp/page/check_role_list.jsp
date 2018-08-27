<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="content-type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
	<meta content='width=330, height=400, initial-scale=1' name='viewport' />
	<link rel="icon" href="favicon.ico" type="../image/x-icon" />
	<title>${initParam.title}</title>
	<link href="../ligerUI/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
	<link href="../ligerUI/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />	
    <script src="../ligerUI/jquery/jquery-1.6.4.js" type="text/javascript"></script>
	<script src="../ligerUI/jquery-validation/jquery.form.js" type="text/javascript"></script> 
	<script src="../ligerUI/jquery-validation/jquery.validate.js" type="text/javascript"></script> 
    <script src="../ligerUI/jquery-validation/jquery.metadata.js" type="text/javascript"></script>
    <script src="../ligerUI/jquery-validation/messages_cn.js" type="text/javascript"></script> 
	<script src="../ligerUI/ligerUI/js/core/base.js" type="text/javascript"></script>
	<script type="text/javascript" src="../ligerUI/ligerUI/js/ligerui.all.js"></script>
	<script src="../ligerUI/ligerUI/js/plugins/ligerUtil.js" type="text/javascript" ></script> 
	<script type="text/javascript" src="../ligerUI/ligerUI/js/pap.js"></script>
	<script src="../ligerUI/ligerUI/js/template/ligerTemplate.js" type="text/javascript"></script>
	<script src="../ligerUI/ligerUI/js/template/ligerXFormGridList.js" type="text/javascript"></script>
    <script type="text/javascript">
    	// 表格
        var grid = null;
        $(function () {
        	   var toolBarOption = { 
   	                   items: [
	   	   	                { text: '选择角色', icon: 'settings',click: function(item) {
							       $.pap.addTabItem({ 
							         text: '选择角色', 
							         url: 'check_role_set.jsp'
							      });
						    }},
                 		    { line: true },
	   	   	                { text: '删除', icon: 'delete', expression:'==0', disabled:true, click: function(){
	   	   	            	  if( grid.selected.length > 0 ){
	   	   	      	    		$.ligerDialog.confirm('数据删除后不可恢复,你确认要删除?', function(data) {
	   	   	          				var delIds = "";
	   	   	          				$(grid.selected).each(function(i, item){
	   	   	          					delIds += item['code']+",";
	   	   	          				});
	   	   	          				if(data){
	   	   	          					$.ligerui.ligerAjax({
	   	   	      							type: "POST",
	   	   	      							async:  false,
	   	   	      							url: "check_role!delete",
	   	   	      							data: { ids: delIds },
	   	   	      							dataType: "text",
	   	   	      							success: function(result, status){
	   	   	      								if(result != ""){
	   	   	      									tip = $.ligerDialog.tip({ title: '提示信息', content: result + '条记录被删除！' });
	   	   	      									window.setTimeout(function(){ tip.close()} ,2000); 
	   	   	      								}	
	   	   	      							},
	   	   	      							error: function(XMLHttpRequest,status){
	   	   	      								$.ligerDialog.error('操作出现异常');
	   	   	      							},
	   	   	      							complete:function(){
	   	   	      								grid.loadData(true);
	   	   	      							}
	   	   	      					   	});    			
	   	   	      	    			}
	   	   	          			});    			
	   	   	      	    	}else {
	   	   	      	    		$.ligerDialog.warn('请选择要删除的记录！')
	   	   	      	    	}
	   	   			      }}
                   		]
             	};

        	 form = {
                	 labelWidth: 70,
                     fields:[
                         { display: "单据名称", name:"e.className", type:"select",options:{
		   		             	url:'check_bill!loadType',
		   		                valueField: 'className', //关键项
		          			    textField: 'bussName',
		          			    keySupport:true,
		          			    selectBoxHeight:120
		  		         }},
		  		         { display: "角色名称", name:"e.role.roleCode", type:"select",options:{
		   		             	url:'role!loadType',
		   		                valueField: 'roleCode', //关键项
		          			    textField: 'roleName',
		          			    keySupport:true,
		          			    selectBoxHeight:235,
		          			    selectBoxWidth:220
		  		         }},
                         { display: "创建日期", name:"e.createDate",type:"date" } 
					    ]
                	}

        	 var gridoption={
                     columns:[
                        { display: '编码', name: 'code',hide:true},
	    				{ display: '单据名称', name: 'bussName',width:80},
	    				{ display: '单据编码', name: 'className'},
	    				{ display: '角色编码', name: 'role.roleCode',hide:true},
	    				{ display: '角色名称', name: 'role.roleName',width:120},
	    				{ display: '审核权限编码', name: 'checkLevels',hide:true},
	    				{ display: '审核权限', name: 'checkLevelName',width:230},
	    				{ display: '创建时间', name: 'createDate',width:90},
	    				{ display: '创建人', name: 'createUser',width:90}
              		],
                    url:'check_role!loadByPage'
             }
          
           var glist = $.pap.createFormGridList({ toolbar:toolBarOption,form:form,grid:gridoption});
           grid = glist.getGrid();
        });
    </script>
</head>
<body>
</body>
</html>
