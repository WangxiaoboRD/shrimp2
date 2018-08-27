<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>单据审核权限设置</title>
    <link href="../ligerUI/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
	<link href="../ligerUI/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />	
    <script src="../ligerUI/jquery/jquery-1.6.4.js" type="text/javascript"></script>
	<script src="../ligerUI/jquery-validation/jquery.form.js" type="text/javascript"></script> 
	<script src="../ligerUI/jquery-validation/jquery.validate.js" type="text/javascript"></script> 
    <script src="../ligerUI/jquery-validation/jquery.metadata.js" type="text/javascript"></script>
    <script src="../ligerUI/jquery-validation/messages_cn.js" type="text/javascript"></script>    
    <script src="../ligerUI/ligerUI/js/ligerui.all.js" type="text/javascript"></script> 
    <script src="../ligerUI/ligerUI/js/pap.js" type="text/javascript" ></script>      
    <script type="text/javascript">
	  var form,v;
	  var currentPage = $.pap.getOpenPage(window);
      var currentParam = currentPage.getParam("param");
	  $(function(){
		 var groupicon = "../ligerUI/ligerUI/skins/icons/communication.gif";
	  	 form = $("form").ligerForm({
             fields: [
                { display: "单据名称",name:"e.bussName",group: "基础信息", groupicon: groupicon,type:"text",newline: true,validate:{required:true},options:{value:currentParam['bussName'],readonly:true}},
                { display: "单据编码",name:"e.className",type:"hidden",options:{value:currentParam['objName'],readonly:true}},
                { display: "角色名称",name:"e.role.roleName",type:"text",newline: true,validate:{required:true},options:{value:currentParam['roleName'],readonly:true}},
                { display: "角色编码",name:"e.role.roleCode",type:"hidden",options:{value:currentParam['roleCode'],Codereadonly:true}},
                { display: "审核级别", name: "e.checkLevels",group: "审核级别", groupicon: groupicon, newline: true,validate: { required: true }, type: "select", comboboxName: 'levelName', options:{               
			       	isShowCheckBox: true, 
			       	isMultiSelect: true,
			       	split: ",",
		            data:currentParam['data'],
		            selectBoxHeight:110          
                }}
                ]
	  		 });
	  		 v= form.validateForm();
	  	});
	  	var onSave = function(){
	  		$("form").submit();
			if( v.valid() ){
				return $('form').formSerialize();
			}else{
				return null;
			}
		}  
    </script>
</head>
	<body style="padding: 10px">
		<form/>
		<div style="display: none"></div>
	</body>
</html>
