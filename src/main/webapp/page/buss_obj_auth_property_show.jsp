<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="content-type" content="text/html;charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
	<meta content='width=330, height=400, initial-scale=1' name='viewport' />
	<link rel="icon" href="favicon.ico" type="../image/x-icon" />
	<title></title>
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
    	var currentPage = $.pap.getOpenPage(window);
	  	var currentParam = currentPage.getParam("param")[0];

	  	// 工具条
	    var toolBarOption = { 
            items: [
                { text: '关闭',icon: 'delete', click: function(item){
					$.pap.removeTabItem() ;
                }}
            ]
	    }

	  	// 表格
		var gridOption = { 
      		   url: 'buss_obj_property!loadBussObjProperty',
      		   parms: {
    		  	  'e.propertyName': currentParam['bussCode'], 
    		  	  'e.className': currentParam['fullClassName']
    		   },
                columns:[
                     { display: '类结构', name: 'propertyName' },
                     { display: '权限过滤属性', name: 'isAuthProperty', render: function(data){
     					 if(data.isAuthProperty=='N'){return ""};
  					  	 if(data.isAuthProperty=='Y'){return "是"};
  				   	 }},
                     { display: '对应表字段', name: 'fieldName' },                          
                     { display: '字段类型', name: 'fieldtype' },
                     { display: '字段长度', name: 'fieldLen' },                         
                     { display: '主键', name: 'isPk', render: function(data){
     					 if(data.isPk=='N'){return ""};
  					  	 if(data.isPk=='Y'){return "是"};
  				   	 }},
                     { display: '业务元素', name: 'bussEleCode' }
                ],
                usePager: false,
                delayLoad : false,
			    alternatingRow: false,
			    checkbox: false,
			    title: '数据表格',
				tree: { columnName: 'propertyName' } 
                
        }
	    $.pap.createToolbar("toolbar", toolBarOption);
     	$.pap.createGrid("maingrid", gridOption);

    });
    </script>
</head>
	<body>
		<div id="toolbar"></div> 
	    <div id="maingrid"></div> 
	</body>
</html>
