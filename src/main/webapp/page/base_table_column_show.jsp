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
	<script type="text/javascript" src="../ligerUI/ligerUI/js/ligerui.all.new.js"></script> 
    <script type="text/javascript" src="../ligerUI/ligerUI/js/plugins/XGrid.js"></script>
	<script type="text/javascript" src="../ligerUI/json2.js"></script>

    <script type="text/javascript">
	  	
	  var form;
	  $(function(){

		 
	  	form= $("form").ligerForm({
	  		
	  		inputWidth: 150, labelWidth: 70, space:20,
             fields: [  
             
	              { display: "字段名称", name:"e.fdcode",type:"text",attr:{value:'${e.fdcode}'},options:{readonly:'readonly'}},
	              { display: "主键", name:"e.isPk",type:"text",attr:{value:'${e.isPk}'},options:{readonly:'readonly'}},
	              { display: "业务元素名称", name:"e.bussinessEle.ename",type:"text",attr:{value:'${e.bussinessEle.ename}'},options:{readonly:'readonly'}},
	              { display: "业务元素长度", name:"e.bussinessEle.len",type:"text",attr:{value:'${e.bussinessEle.len}'},options:{readonly:'readonly'}},
	              { display: "业务元素小数", name:"e.bussinessEle.decimalLen",type:"text",attr:{value:'${e.bussinessEle.decimalLen}'},options:{readonly:'readonly'}},
	              { display: "类型", name:"e.dataType",type:"text",attr:{value:'${e.dataType}'},options:{readonly:'readonly'}},
	              { display: "长度", name:"e.len",type:"text",attr:{value:'${e.len}'},options:{readonly:'readonly'}},
	              { display: "小数位数", name:"e.decimalLen",type:"text",attr:{value:'${e.decimalLen}'},options:{readonly:'readonly'}},
	              { display: "默认值", name:"e.defaultValue",type:"text",attr:{value:'${e.defaultValue}'},options:{readonly:'readonly'}},
	              { display: "描述", name:"e.descs",type:"text",attr:{value:'${e.descs}'},options:{readonly:'readonly'}}
             ]
	     });

         var error=$("input[id='error']").val();
	     if(error){
	    		$.ligerDialog.warn(error)
		  }



	     
          
	});



		


 	 
    </script>
    <style type="text/css">
         body{ font-size:12px;}
        .l-table-edit {}
        .l-table-edit-td{ padding:4px;}
    </style>
</head>
	<body style="padding: 10px" >
	<form name="form1" method="post" id="form1">
	      <input type="hidden" id="error" value="${e.error }"/>
	</form>
	     
	  	<div id="modify" style="margin:0; padding:0"></div>	
	</body>
</html>
