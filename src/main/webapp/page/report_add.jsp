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
	<script src="../ligerUI/ligerUI/js/template/ligerXSFormCreate.js" type="text/javascript"></script>
    <script src="../ligerUI/ligerUI/js/plugins/ajaxfileupload.js" type="text/javascript"></script> 
    <script type="text/javascript">
		var v;
        var form = null;
        $(function(){
	  		 var formOptions = {
	  		 	 url: "report!save",	// 此处也是必须的
	  			 labelWidth: 80, 
           		 space:20,
           		 fileUpload: true,
	  			 fields: [
	  			      { display: "报表编码", name: "e.code", validate: { required: true }, type: "text" }, 
	  	              { display: "报表名称", name: "e.name", validate: { required: true }, newline: true, type: "text" },
	  	              { display: "报表文件", name: "doc", validate: { required: true }, newline: true, type: "file", width: 350 },
	  	              { display: "参数", name: "e.params", options: { nullText: '参数名1=值1&参数名2=值2...多个参数值对以&分隔' }, width: 350, newline: true, type: "text" },
		  	          { display: "描述", name: "e.description", type: "textarea", newline: true, width: 350 }
	               ]
	          };
               
              var template =$.pap.createSFormCreate({form: formOptions});
	  		  form = template.getForm() ;
	  		  v = form.validateForm() ;
        });
	  	
    </script>
</head>
<body>
</body>
</html>

