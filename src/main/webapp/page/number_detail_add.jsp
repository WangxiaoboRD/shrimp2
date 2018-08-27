<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>创建号码对象明细</title>
	 
    <link href="../ligerUI/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
	<link href="../ligerUI/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />	
    <script src="../ligerUI/jquery/jquery-1.6.4.js" type="text/javascript"></script>
	<script src="../ligerUI/jquery-validation/jquery.form.js" type="text/javascript"></script> 
	<script src="../ligerUI/jquery-validation/jquery.validate.js" type="text/javascript"></script> 
    <script src="../ligerUI/jquery-validation/jquery.metadata.js" type="text/javascript"></script>
    <script src="../ligerUI/jquery-validation/messages_cn.js" type="text/javascript"></script>    
    <script src="../ligerUI/ligerUI/js/ligerui.all.js" type="text/javascript"></script>       
    <script type="text/javascript" src="../ligerUI/ligerUI/js/plugins/XGrid.js"></script>    
	<script src="../ligerUI/ligerUI/js/template/ligerTemplate.js" type="text/javascript"></script>
	<script type="text/javascript" src="../ligerUI/ligerUI/js/pap.js"></script>
	<script type="text/javascript" src="../ligerUI/ligerUI/js/core/ligerAjax.js"></script>
    <script type="text/javascript" src="../ligerUI/ligerUI/js/json2.js"></script>
    
    <script type="text/javascript">
      
	  var form,v;
	  var currentPage = $.pap.getOpenPage(window);
  	  var currentParam = currentPage.getParam("param");
	  $(function(){
	  	 form = $("form").ligerForm({
	  		 inputWidth: 170, labelWidth: 80, space: 20,
             fields: [
             	    { display: "号码对象", name: "e.number.id",newline: true, type: "text" ,options:{value:currentParam['id'],readonly:true}}, 
	                { display: "范围段", name: "e.numberScope",validate:{required:true},newline: true, type: "text" }, 
	                { display: "前缀", name: "e.prefix", newline: true, type: "text"},
	                { display: "起始值", name: "e.startNumber",validate:{required:true,digits:true}, newline: true, type: "text" },
	                { display: "终止值", name: "e.endNumber",validate:{required:true,digits:true}, newline: true, type: "text" },
	                { display: "子对象值", name: "e.subobject", newline: true, type: "text",options:{value:currentParam['subobject'],readonly:true}},
	                { display: "年份", name: "e.year",newline: true, type: "text" ,},
		 			{ display: "外部标示", name: "e.markExt",type: "radiogroup",newline: true, options:{               
				       	data: [{ text: '外部', id: 'Y' },
			                  { text: '内部', id: 'N' }],          
				       	name: "e.markExt",
				       	value: 'Y'     
	                }}
                ]
	  		});
	  		if(currentParam['subobject']=='')
		    	$("input[name='e.subobject']").parent().parent().parent().css("display","none");
        	if(currentParam['markYear']=='')
		    	$("input[name='e.year']").parent().parent().parent().css("display","none");
	  		v= form.validateForm();
	  		
	  	});
	  	var onSave = function(){
	  		$("form").submit();
			if( v.valid() ){
				return $('form').formSerialize();
			}else{
				return null;
			}
	  		/*console.log($('#form1').formSerialize());
			return $('#form1').formSerialize();*/
		}  
    </script>
    <style type="text/css">
         body{ font-size:12px;}
        .l-table-edit {}
        .l-table-edit-td{ padding:4px;}
    </style>

</head>
	<body style="padding: 10px">
		<form/>
		<div style="display: none"></div>
	</body>
</html>
