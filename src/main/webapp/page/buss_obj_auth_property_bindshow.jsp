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
	<script src="../ligerUI/ligerUI/js/template/ligerXEFormGridShow.js" type="text/javascript"></script>
    <script type="text/javascript">
    	var grid = null;
        $(function(){
        	var currentPage = $.pap.getOpenPage(window);
    	  	var currentParam = currentPage.getParam("param")[0];

    	 // 工具条
    	    var toolBarOption = { 
                items: [
					{ text: '启用', icon: 'right', click: function(item){
						if( grid.selected.length == 0 ){
							$.ligerDialog.warn('请选择要启用的记录！');
							return;
						}

						var enableStatus = false;// 已启用
          				var ids = "";
          				$(grid.selected).each(function(i, item){
          					ids += item['id']+",";
          					if (item['enabled'] == 1) {
          						enableStatus = true;
              				}
          				});

						if (enableStatus) {
							$.ligerDialog.warn('所选信息包含有已启用的权限过滤属性，启用失败！');
							return;
						}
          				
       					$.ligerui.ligerAjax({
   							type: "POST",
   							async: false,
   							url: "buss_obj_auth_property!enable",
   							data: { ids: ids },
   							dataType: "text",
   							success: function(result, status){
   								if(result != null && result !=""){
									var selectedTabid = window.name || window.id ;
									tips = $.ligerDialog.tip({ title: '提示信息',modal: true, content: '启用成功！' });
									window.setTimeout(function(){ 
										tips.close();
									} ,2000);
								}
   							},
   							error: function(XMLHttpRequest,status){
   								$.ligerDialog.error('操作出现异常');
   							},
   							complete:function(){
   								grid.loadData(true);
   							}
   					   	});    			
					}},
					{ text: '禁用', icon: 'lock', click: function(item){
						if( grid.selected.length == 0 ){
							$.ligerDialog.warn('请选择要禁用的记录！');
							return;
						}

						var disableStatus = false;// 未启用
          				var ids = "";
          				$(grid.selected).each(function(i, item){
          					ids += item['id']+",";
          					if (item['enabled'] == 0) {
          						disableStatus = true;
              				}
          				});

						if (disableStatus) {
							$.ligerDialog.warn('所选信息包含有未启用的权限过滤属性，禁用失败！');
							return;
						}
          				
       					$.ligerui.ligerAjax({
   							type: "POST",
   							async: false,
   							url: "buss_obj_auth_property!disable",
   							data: { ids: ids },
   							dataType: "text",
   							success: function(result, status){
   								if(result != null && result !=""){
									var selectedTabid = window.name || window.id ;
									tips = $.ligerDialog.tip({ title: '提示信息',modal: true, content: '禁用成功！' });
									window.setTimeout(function(){ 
										tips.close();
									} ,2000);

								}
   							},
   							error: function(XMLHttpRequest,status){
   								$.ligerDialog.error('操作出现异常');
   							},
   							complete:function(){
   								grid.loadData(true);
   							}
   					   	});    	
					}},
                    { text: '关闭', icon: 'delete', click: function(item){
   						$.pap.removeTabItem() ;
                    }}
                ]
    	    }
    	  	
      		 var formOption = {
      			 labelWidth: 100, 
           		 space:20,
      			 fields: [
    		        { display: "业务对象编码", name: "e.bussCode", type: "text" , options: { value: currentParam['bussCode'], readonly: true }}, 
                    { display: "业务对象名称", name: "e.bussName", type: "text", options: { value: currentParam['bussName'], readonly: true }},
                    { display: "类全名", name: "e.fullClassName", type: "text", width: 300, options: { value: currentParam['fullClassName'], readonly: true }}
               ]};

	  		 var gridOption={
      				url: 'buss_obj_auth_property!loadByEntity',
           		 	parms:{ 'e.bussObj.bussCode': currentParam['bussCode']},
                     columns:[
						{ display: '编号', name: 'id', hide: true },
						{ display: '属性', name: 'propertyFullName' },
						{ display: '数据类型', name: 'bussObjProperty.fieldtype' },
						{ display: '权限对象 ', name: 'authObj.name' },
						{ display: '权限字段', name: 'authField.name' },
						{ display: '状态', name: 'enabled', render: function(data){
							var text = data.enabled;
							switch(data.enabled) {
								case 0: text = '未启用'; break; 
								case 1: text = '已启用'; break;
							}
							return text;
						}}
              		],
              		usePager: true,
                    checkbox: true
             }
             var template = $.pap.createEFormGridShow({toolbar: toolBarOption, form: formOption, grid: gridOption});
             grid = template.getGrid();
        });
	  	
    </script>
</head>
<body></body>
</html>

