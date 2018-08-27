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
	<script src="../ligerUI/ligerUI/js/template/ligerXSFormShow.js" type="text/javascript"></script>
	<script type="text/javascript" src="../ligerUI/ligerUI/js/pap.js"></script>

    <script type="text/javascript">
	  	
	  $(function(){
	    var currentPage = $.pap.getOpenPage(window);
	  	var currentParam = currentPage.getParam("param")[0];	        
        var formOptions={
                labelWidth: 100,
                fields:[
   	              { display: "权限字段编码", name: "e.code", options: { value: '${e.code}', readonly: true }, type: "text" }, 
	  	          { display: "权限字段名称", name: "e.name", options: { value: '${e.name}', readonly: true }, newline: true, type: "text" },
	  	          { display: "权限字段类型", name: "e.type", type: "text", newline: true, options:{ readonly: true, value: '${e.type}', render: function(v){
	  	        	var text = v;
					switch(v){
						case 'FUNCTION': text = "功能权限"; break;
						case 'DATA': text = "数据权限"; break;
						case 'BUSINESS': text = "业务权限"; break;
					}
					return text;
			  	  }}},
	  	          { display: "业务元素", name: 'e.bussinessEle.ename', type: "text", options: { value: '${e.bussinessEle.ename}', readonly: true }, newline: true },
	  	          { display: "数据类型", name: "e.bussinessEle.dataType", newline: true, type: "text",options: { value: '${e.bussinessEle.dataType}', readonly: true }},
	  	          { display: "权限树", name: "e.bussinessEle.dataType", newline: true, type: "text",options: { value: '${e.tree}', readonly: true, render: function(v){
	  	        	var text = v;
					switch(v){
						case '0': text = "否"; break;
						case '1': text = "是"; break;
					}
					return text;
			  	  }}}
		      ]
         }
       
        var template=$.pap.createSFormShow({form: formOptions});
	  	});
 	 
    </script>
    <style type="text/css">
         body{ font-size:12px;}
        .l-table-edit {}
        .l-table-edit-td{ padding:4px;}
    </style>
</head>
	<body>
	</body>
</html>
