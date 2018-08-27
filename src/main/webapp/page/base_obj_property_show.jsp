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
	<script src="../ligerUI/ligerUI/js/template/ligerXEFormGridShow.js" type="text/javascript"></script>
	<script type="text/javascript" src="../ligerUI/ligerUI/js/pap.js"></script>

    <script type="text/javascript">
	  	
	  var grid;
	  $(function(){

		    var currentPage = $.pap.getOpenPage(window);
		  	var currentParam = currentPage.getParam("param")[0];	        
		       
	       	var gridoption={
	        		  url:'buss_obj_property!loadBussObjProperty',
	        		  parms:{'e.propertyName':currentParam['bussCode'],'e.className':currentParam['fullClassName']},
	                  columns:[
                           { display: '类结构', name: 'propertyName',id:'id1', width: 200, align: 'left' },
                           { display: '对应字段', name: 'fieldName', width: 120, align: 'left' },                          
                           { display: '字段类型', name: 'fieldtype', width: 120, align: 'left' },
                           { display: '对应长度', name: 'fieldLen', width: 120, align: 'left' },                         
                           { display: '业务元素', name: 'bussEleCode', width: 120, align: 'left' },
                           { display: '主键', name: 'isPk', width: 120, align: 'left' ,render:function(row){
           					   if(row.isPk=='N'){return "否"};
        					   if(row.isPk=='Y'){return "是"};
        				   }},
                           { display: '业务对象', name: 'isBussObj', width: 50, align: 'left',render:function(row){
           					   if(row.isBussObj=='N'){return "否"};
        					   if(row.isBussObj=='Y'){return "是"};
        				   }},
        				   { display: '类名', name: 'className', width: 250, align: 'left' }
        				   
	                  ],
	                  usePager: true,
	                  title:'业务对象/属性',
	                  delayLoad : false,
	  			      alternatingRow: false,
					  tree: { columnId: 'id1' },	 
	                  checkbox: false
	                  
	          }
	   		  
           $.pap.createGrid("maingrid",gridoption);

              
	  	});


 	 
    </script>
    <style type="text/css">
         body{ font-size:12px;}
        .l-table-edit {}
        .l-table-edit-td{ padding:4px;}
    </style>
</head>
	<body style="padding: 10px" >

    <div id="maingrid"></div> 
	</body>
</html>
