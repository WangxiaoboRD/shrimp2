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
    <script src="../ligerUI/ligerUI/js/ligerui.all.js" type="text/javascript"></script>       
	<script src="../ligerUI/ligerUI/js/pap.js" type="text/javascript" ></script>
	<script src="../ligerUI/ligerUI/js/template/ligerTemplate.js" type="text/javascript"></script>
	<script src="../ligerUI/ligerUI/js/template/ligerXEFormGridShow.js" type="text/javascript"></script>
    <script type="text/javascript">
        $(function(){
	  		 var formOption = {
	  			 labelWidth: 80, 
           		 space:20,
	  			 fields: [
  			        { display: "角色编码", name: "e.roleCode", type: "text", options: { value: '${e.roleCode}', readonly: true }}, 
  	                { display: "角色名称", name: "e.roleName", type: "text", options: { value: '${e.roleName}', readonly: true }},
  	                { display: "角色状态", name: "e.roleStatus", type: "text", options: { value: '${e.roleStatus}', readonly: true, render: function(v){
  		  	        	var text = v;
  						switch(v){
  							case '1': text = "启用"; break;
  							case '0': text = "停用"; break;
  						}
  						return text;
  				  	  }}},
               ]};

	  		 
	  		 var gridOption={
	  				url:'role!loadAuthObjSet',
	        		parms:{ 'id': '${e.roleCode}' },
	                 columns:[
	                    { display: '权限对象编码',name: 'code' },
	    				{ display: '权限对象名称',name: 'name' },
	    				{ display: '权限对象类型', name: 'type', render: function(data){
		    				var text = data.type;
	    					switch(data.type){
	    						case 'FUNCTION': text = "功能权限"; break;
	    						case 'DATA': text = "数据权限"; break;
	    						case 'BUSINESS': text = "业务权限"; break;
	    					}
	    					return text;
	    				}}
	          		],
              		usePager: true,
                    checkbox: false
             }
             $.pap.createEFormGridShow({form: formOption, grid: gridOption});
        });
	  	
    </script>
</head>
<body></body>
</html>

