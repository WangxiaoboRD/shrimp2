<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head><title>修改密码</title>

    <link href="../ligerUI/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
	<link href="../ligerUI/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />	
    <script src="../ligerUI/jquery/jquery-1.6.4.js" type="text/javascript"></script>
	<script src="../ligerUI/jquery-validation/jquery.form.js" type="text/javascript"></script> 
	<script src="../ligerUI/jquery-validation/jquery.validate.js" type="text/javascript"></script> 
    <script src="../ligerUI/jquery-validation/jquery.metadata.js" type="text/javascript"></script>
    <script src="../ligerUI/jquery-validation/messages_cn.js" type="text/javascript"></script>    
    <script src="../ligerUI/ligerUI/js/ligerui.all.js" type="text/javascript"></script>       
	<script type="text/javascript" src="../ligerUI/ligerUI/js/pap.js"></script>
    <script type="text/javascript">
	  	
	  var form,v;
	  $(function(){
		 var groupicon = "../ligerUI/ligerUI/skins/icons/communication.gif";
		 //生成ligerUI form
	  	 form = $("form").ligerForm({
	  		 inputWidth: 170, labelWidth: 80, space: 20,
             fields: [
                { display: "账号", name: "e.userCode", type: "text" , options:{value: '${session.CURRENTUSER.userCode}' }, hidden: true},  
                { display: "原密码", name: "e.tempStack.oldPassword", group: "更改密码", groupicon: groupicon,validate: { required: true }, newline: true, type: "password" },
                { display: "新密码", name: "e.tempStack.newPassword", validate: { required: true }, newline: true, type: "password" },
                { display: "确认新密码", name: "e.userPassword", validate: { required: true }, newline: true, type: "password" }
               ]
	  		 });
	  		 v= form.validateForm();
	  	});
	  	var onSave = function(){
	  		$("form").submit();
			if( v.valid() ){
				var confirmNewPwd = $("input[id='e.userPassword']").val();
				var newPwd = $("input[id='e.tempStack.newPassword']").val();
				if (newPwd != confirmNewPwd) {
					return 1;
				}
				return $('form').formSerialize();
			}else{
				return null;
			}
		}  	 
    </script>
    <style type="text/css">
         body{ font-size:12px;}
        .l-table-edit {}
        .l-table-edit-td{ padding:4px;}
    </style>
</head>
	<body style="padding: 10px">
		<form />
	</body>
</html>
