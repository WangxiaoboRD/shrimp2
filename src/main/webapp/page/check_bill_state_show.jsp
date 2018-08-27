<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>查看</title>
    <link href="../ligerUI/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
	<link href="../ligerUI/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />	
    <script src="../ligerUI/jquery/jquery-1.6.4.js" type="text/javascript"></script>
	<script src="../ligerUI/jquery-validation/jquery.form.js" type="text/javascript"></script> 
	<script src="../ligerUI/jquery-validation/jquery.validate.js" type="text/javascript"></script> 
    <script src="../ligerUI/jquery-validation/jquery.metadata.js" type="text/javascript"></script>
    <script src="../ligerUI/jquery-validation/messages_cn.js" type="text/javascript"></script>    
    <script src="../ligerUI/ligerUI/js/ligerui.all.js" type="text/javascript"></script>       
	<script src="../ligerUI/ligerUI/js/template/ligerTemplate.js" type="text/javascript"></script>
	<script type="text/javascript" src="../ligerUI/ligerUI/js/pap.js"></script>
    <script type="text/javascript">
	  var form,v;
	  var groupicon = "../ligerUI/ligerUI/skins/icons/communication.gif";
	  $(function(){
	  	form = $("form").ligerForm({
	  		 inputWidth: 160, labelWidth: 80, space: 20,
             fields: [
                { display: "审核人", name: "checkState.userName",newline: true, options:{value:'${checkState.userName}', readonly:true},type: "text",group: "审核详情", groupicon: groupicon }, 
                { display: "审核时间",name: "checkState.checkDate",newline: true, type: "text",options:{value:'${checkState.checkDate}',readonly:true}},
                { display: "审核级别",name: "checkState.level",newline: true, type: "text",options:{value:'${checkState.level}',readonly:true,render:function(data){
	    				if(data != null && data =='1')
	    					return "一级审核";
	    				if(data != null && data =='2')
	    					return "二级审核";
	    				if(data != null && data =='3')
	    					return "三级审核";
	    				if(data != null && data =='4')
	    					return "四级审核";
	    				if(data != null && data =='5')
	    					return "五级审核";
	    				if(data != null && data =='6')
	    					return "六级审核";
	    				if(data != null && data =='7')
	    					return "七级审核";
	    				if(data != null && data =='8')
	    					return "八级审核";
	    		}}},
                { display: "审核状态",name: "checkState.checkState",newline: true, type: "text",options:{value:'${checkState.checkState}',readonly:true,render:function(data){
	    				if(data != null && data =='Y')
	    					return "审核通过";
	    				else
	    					return "未审核";
	    		}}}
                
                ]
	  		 });
	  	});
    </script>
</head>
	<body style="padding: 10px">
		<form/>
		<div style="display: none"></div>
	</body>
</html>
