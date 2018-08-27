<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>级别定义</title>
    <link href="../ligerUI/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
	<link href="../ligerUI/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />	
    <script src="../ligerUI/jquery/jquery-1.6.4.js" type="text/javascript"></script>
	<script src="../ligerUI/jquery-validation/jquery.form.js" type="text/javascript"></script> 
	<script src="../ligerUI/jquery-validation/jquery.validate.js" type="text/javascript"></script> 
    <script src="../ligerUI/jquery-validation/jquery.metadata.js" type="text/javascript"></script>
    <script src="../ligerUI/jquery-validation/messages_cn.js" type="text/javascript"></script>    
    <script src="../ligerUI/ligerUI/js/ligerui.all.js" type="text/javascript"></script>       
    <script type="text/javascript">
	  var form,v;
	  $(function(){
		 var groupicon = "../ligerUI/ligerUI/skins/icons/communication.gif";
	  	 form = $("form").ligerForm({
             fields: [
                { display: "编号", name: "e.code", newline: true, options: { value: '${e.code}' }, type: "hidden" },
                { display: "单据名称", name: "e.bussName", newline: true, options: { value: '${e.bussName}', readonly:'true' }, type: "text"},
                { display: "单据编号", name: "e.objName", newline: true, options: { value: '${e.objName}',readonly:'true' }, type: "text" },
                /*
                { display: "选择单据", name:"e.objName",group: "基础信息", groupicon: groupicon,validate: { required: true }, type:"select",newline: true,options:{
   		             	url:'check_bill!loadType',
   		                valueField: 'className', //关键项
          			    textField: 'bussName',
          			    initValue:'${e.objName}',
          			    initText:'${e.bussName}',
          			    selectBoxHeight:140
  		        }},**/
                { display: "审核级别", name: "e.objLevel", newline: true,validate: { required: true }, type: "select", comboboxName: 'levelName', options:{               
			       	data: [
	                  	{ text: '一级审核', id: '1' },
	                  	{ text: '二级审核', id: '2' },
	                  	{ text: '三级审核', id: '3' },
	                  	{ text: '四级审核', id: '4' },
	                  	{ text: '五级审核', id: '5' },
	                  	{ text: '六级审核', id: '6' },
	                  	{ text: '七级审核', id: '7' },
	                  	{ text: '八级审核', id: '8' }
		              ],
		              keySupport:true,
		              value: '${e.objLevel}', 
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
