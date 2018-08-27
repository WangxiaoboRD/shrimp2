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
                inputWidth: 300,
                fields:[
   	              { display: "提取文件", name: "e.fileName", options: { value: '${e.fileName}' }, type: "displayfield" }, 
	  	          { display: "操作人", name: "e.operatorName", options: { value: '${e.operatorName}' }, newline: true, type: "displayfield" },
	  	          { display: "操作时间", name: "e.operTime", options: { value: '${e.operTime}' }, newline: true, type: "displayfield" },
	  	          { display: "业务对象", name: "e.operBusiness", options: { value: '${e.operBusiness}' }, newline: true, type: "displayfield" },
	  	          { display: "入口方法", name: "e.operFunction", options: { value: '${e.operFunction}' }, newline: true, type: "displayfield" },
	  	      	  { display: "业务方法", name: "e.serviceFunction", options: { value: '${e.serviceFunction}' }, newline: true, type: "displayfield" },
	  	    	  { display: "跟踪方法", name: "e.followFunction", options: { value: '${e.followFunction}' }, newline: true, type: "displayfield" },
	  	    	  { display: "耗时(毫秒)", name: "e.timeConsuming", options: { value: '${e.timeConsuming}' }, newline: true, type: "displayfield" },
		  	      { display: "初始Hql", name: "e.oldHql", options: { value: "${e.oldHql}", readonly: true }, newline: true, type: "textarea", width: 500 },
		  	      { display: "初始Sql", name: "e.oldSql", options: { value: "${e.oldSql}", readonly: true }, newline: true, type: "textarea", width: 500 },
		  	      { display: "转换Sql", name: "e.newSql", options: { value: "${e.newSql}", readonly: true }, newline: true, type: "textarea", width: 500 },
		      ]
         }
       
        var template=$.pap.createSFormShow({form: formOptions});
	  	});
 	 
    </script>
</head>
	<body>
	</body>
</html>
