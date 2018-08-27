<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head><title>
</title>
    <link href="../ligerUI/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
	<link href="../ligerUI/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />	
    <script src="../ligerUI/jquery/jquery-1.6.4.js" type="text/javascript"></script>
	<script src="../ligerUI/jquery-validation/jquery.form.js" type="text/javascript"></script> 
	<script src="../ligerUI/jquery-validation/jquery.validate.js" type="text/javascript"></script> 
    <script src="../ligerUI/jquery-validation/jquery.metadata.js" type="text/javascript"></script>
    <script src="../ligerUI/jquery-validation/messages_cn.js" type="text/javascript"></script> 
	<script src="../ligerUI/ligerUI/js/core/base.js" type="text/javascript"></script>
	<script src="../ligerUI/ligerUI/js/ligerui.all.js" type="text/javascript"></script>       
	<script src="../ligerUI/ligerUI/js/pap.js" type="text/javascript" ></script>
    <script type="text/javascript">
    var grid = null;
    $(function(){

	  	// 表格
		var gridOption = { 
      		   url: 'buss_obj_property!loadBussObjProperty',
      		   parms: {
    		  	  'e.propertyName': '${e.bussCode}', 
    		  	  'e.className': '${e.fullClassName}'
    		   },
                columns:[
                     { display: '编号', name: 'id', hide: true },
                     { display: '类结构', name: 'propertyName', width: 200 },
                     { display: '引用属性名', name: 'quoteObjPropertyName', width: 200, hide: true },
                     { display: '对应表字段', name: 'fieldName', width: 150 },                          
                     { display: '字段类型', name: 'fieldtype', width: 100 },
                     { display: '字段长度', name: 'fieldLen', width: 100 },                         
                     { display: '主键', name: 'isPk', render: function(data){
     					 if(data.isPk=='N'){return "" };
  					  	 if(data.isPk=='Y'){return "是" };
  				   	 }},
                     { display: '业务元素', name: 'bussEleCode', width: 100 }
                ],
                usePager: false,
                title: '我的表格',
                delayLoad : false,
			    alternatingRow: false,
			    checkbox: true,
				tree: { columnName: 'propertyName' } 
                
        }
     	grid = $.pap.createGrid("maingrid", gridOption);
    });

 	// 获得选中数据行
    function f_select(){
    	return grid.getSelectedRows();
    }
    
    </script>
</head>
	<body>
	    <div id="maingrid"></div> 
	</body>
</html>
