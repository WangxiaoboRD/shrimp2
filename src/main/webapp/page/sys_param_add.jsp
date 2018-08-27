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
    <script type="text/javascript">
		var v;
        var form = null;
        $(function(){
        
        	form = $("form").ligerForm({
   	  		 	 inputWidth: 170,
   	  		 	 labelWidth: 80, 
   	  		 	 space: 20,
	  			 fields: [
  			        { display: "参数编码", name: "e.code", validate: { required: true }, type: "text" }, 
  	                { display: "参数名称", name: "e.name", validate: { required: true }, newline: true, type: "text" },
  	                { display: "默认值", name: "e.defaultValue", validate: { required: true }, newline: true, type: "text" },
  	                { display: "值", name: "e.value", newline: true, type: "text" },
              		{ display: '描述', name: 'e.description', type: 'textarea', newline: true } 
	  	               
               ]
        });
  		v = form.validateForm();
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
</body>
</html>

