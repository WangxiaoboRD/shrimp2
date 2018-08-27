<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="content-type" content="text/html;charset=utf-8" />
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
    <script src="../ligerUI/ligerUI/js/ligerui.all.js" type="text/javascript"></script>       
	<script src="../ligerUI/ligerUI/js/template/ligerTemplate.js" type="text/javascript"></script>
	<script src="../ligerUI/ligerUI/js/template/ligerXFormGridList.js" type="text/javascript"></script>
	<script type="text/javascript" src="../ligerUI/ligerUI/js/pap.js"></script>
	
	
    <script type="text/javascript">
        var grid = null;
        $(function () {
            var toolBarOption = { 
             	items: [
					{ text: '属性查看', icon: 'view', expression: '!=1', disabled: true, click: function(item) {
						 var selectedRow = item.selectGrid.selected ; 
					       $.pap.addTabItem({ 
					         text: '权限过滤属性/属性查看', 
					         url: 'buss_obj_auth_property_show.jsp',
					         param: selectedRow 
					      });
				     }},
	                { text: '过滤属性设置', icon: 'config', expression: '!=1', disabled: true, click: function(item) {
	                	 var selectedRow = item.selectGrid.selected ; 
	   	    	         $.pap.addTabItem({ 
		   	    	         text: '权限过滤属性/过滤属性设置', 
		   	    	         url: 'buss_obj_auth_property_set.jsp',
		   	    	         param: selectedRow 
		   	    	      });
   	   		        }},
   	   		 		{ text: '权限对象绑定', icon: 'communication', expression: '!=1', disabled: true, click: function(item) {
	   	   		      	 var selectedRow = item.selectGrid.selected ; 
		    	         $.pap.addTabItem({ 
		   	    	         text: '权限过滤属性/权限对象绑定', 
		   	    	         url: 'buss_obj_auth_property_bind.jsp',
		   	    	         param: selectedRow 
		   	    	      });
   	   		        }},
   	   		 		{ text: '绑定信息查看', icon: 'view', expression: '!=1', disabled: true, click: function(item) {
	   	   		      	 var selectedRow = item.selectGrid.selected ; 
		    	         $.pap.addTabItem({ 
		   	    	         text: '权限过滤属性/绑定信息查看', 
		   	    	         url: 'buss_obj_auth_property_bindshow.jsp',
		   	    	         param: selectedRow 
		   	    	      });
   	   		        }}
          		]
          	 };

           var  form={
           		 labelWidth: 100,
                 fields:[
                     { display: "业务对象编码", name: "e.bussCode", type: "text" },
                     { display: "业务对象名称", name: "e.bussName", type: "text" }
                  ]
           } 			  
	       
           var gridoption={
                  columns:[
                      { display: '业务对象编码', name: 'bussCode' },
                      { display: '业务对象名称', name: 'bussName' },
                      { display: '类全名', name: 'fullClassName', width: 500 }
      	      	  ],
                  url: 'bussiness_object!loadByPage'
           }
           var glist=$.pap.createFormGridList({ toolbar:toolBarOption,form:form,grid:gridoption});
           grid=glist.getGrid();

        });
   </script>
</head>
<body>
</body>
</html>
