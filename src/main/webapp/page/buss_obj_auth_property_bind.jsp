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
	<script src="../ligerUI/ligerUI/js/template/ligerXEFormGridEdit.js" type="text/javascript"></script>
    <script type="text/javascript">
	 var grid = null;
	 var rowdata = null;
	 var authObjCode = null;
     $(function(){
        	var currentPage = $.pap.getOpenPage(window);
    	  	var currentParam = currentPage.getParam("param")[0];

      		var formOption = {
      			 url: "bussiness_object!bindAuthObj",	// 此处也是必须的
      			 labelWidth: 100, 
           		 space:20,
      			 fields: [
    		        { display: "业务对象编码", name: "e.bussCode", type: "text" , options: { value: currentParam['bussCode'], readonly: true }}, 
                    { display: "业务对象名称", name: "e.bussName", type: "text", options: { value: currentParam['bussName'], readonly: true }},
                    { display: "类全名", name: "e.fullClassName", type: "text", width: 300, options: { value: currentParam['fullClassName'], readonly: true }}
               ]};

	  		 var gridOption = {
      				url: 'buss_obj_auth_property!loadByEntity',
           		 	parms:{ 'e.bussObj.bussCode': currentParam['bussCode']},
           		 	submitDetailsPrefix: 'e.authProperties',
                     columns:[
						{ display: '编号', name: 'id', hide: true },
						{ display: '属性', name: 'propertyFullName' },
						{ display: '数据类型', name: 'bussObjProperty.fieldtype' },
						{ display: '权限对象 ', name: 'authObj.code', editor: { type: 'select', options:{
	     		              url: 'auth_obj!loadByEntity',
	    		              valueField: 'code', //关键项
	           			      textField: 'code',
	           			      parms: { 'e.type': 'DATA' }
						 }}},
						{ display: '权限字段', name: 'authField.code', editor: { type: 'popup', 
							 options: {
	  	                        onButtonClick: selectAuthField
	  	                	}
						}}
              		],
              		onBeforeEdit: function(e){
                  		rowdata = e.record;
                        if (e.column.name == 'authField.code') {
                            if (rowdata['authObj.code'] == '') {
                            	$.ligerDialog.warn('请先选择权限对象！');
                            	return false;
                            }
                            authObjCode = rowdata['authObj.code'];
                        }
                        return true;
                    }
             }
	  		 var template =$.pap.createEFormGridEdit({form: formOption, grid: gridOption});
	  		  grid = template.getGrid();
        });

     // 权限字段选择页面
     function selectAuthField() {
    	 $.ligerDialog.open({ 
   			title:'搜索',
   			url: 'auth_obj!loadSelectFieldById?id=' + authObjCode, 
   			height: 500,
   			width: 800, 
   			onLoaded:function(param){
  		       var documentF = param.contentDocument || param.document ;//兼容IE 和 FF
  		           $('div.toolbar-pap',documentF).hide();
  		       },
   			buttons: [ 
				{ text: '选择', onclick:  function (item, dialog) { 
					var selRows = dialog.frame.f_select();
					if(selRows.length != 1) {
						$.ligerDialog.warn('请选择一条记录进行操作');
						return;
					}
					var data = dialog.frame.f_selected();
					var isQuote = false;// 是否被该业务对象属性引用
					$(grid.rows).each(function(index, item){
						if (item['authField.code'] == data['code'] && authObjCode == item['authObj.code']) {
							isQuote = true;
							return false;
						}
					});
					if (isQuote) {
						$.ligerDialog.warn('所选权限字段已与该业务对象的其他属性绑定，不允许再绑定！');
						return;
					}
					
					/*if (data['bussinessEle.dataType'] != rowdata['bussObjProperty.fieldtype']) {
						$.ligerDialog.warn('所选权限字段的数据类型与其对应的业务对象属性的数据类型不一致，不允许绑定！');
						return;
					}*/
					grid.updateRow(rowdata, {
						'authField.code': data['code']
      				});
					dialog.close();
				}}, 
				{ text: '取消', onclick:  function (item, dialog) { dialog.close(); }}
			] 
		 });
     }
	  	
    </script>
</head>
<body>
</body>
</html>

