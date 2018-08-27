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
    	  	var currentParam = currentPage.getParam("param");	

	  		 var formOption = {
	  			 labelWidth: 80, 
           		 space:20,
	  			 fields: [
  			        { display: "角色编码", name: "e.roleCode", type: "text", options: { value: '${e.roleCode}', readonly: true }}, 
  	                { display: "角色名称", name: "e.roleName", type: "text", options: { value: '${e.roleName}', readonly: true }}
               ]};

	  		 
	  		 var gridOption={
	  				url:'role_auth_value!loadByRoleAuthObj',
	        		parms:{ 
	        			'e.role.roleCode': currentParam['roleCode'],
	        			'e.tempStack.authObjCodes': currentParam['authObjCodes']
	        		},      	        	 
	                 columns:[
						{ display: '编号',name: 'id', hide: true },
	                    { display: '权限对象编码',name: 'authObj.code' },
	    				{ display: '权限对象名称',name: 'authObj.name' },
	    				{ display: '权限字段编码',name: 'authField.code' },
	    				{ display: '权限字段名称',name: 'authField.name' },
	    				{ display: '状态', name: 'allotStatus', render: function(data){
		    				var text = data.allotStatus;
	    					switch(data.allotStatus){
	    						case 'ALLOTED': text = "已分配"; break;
	    						case 'UNALLOT': text = "未分配"; break;
	    					}
	    					return text;
	    				}},
	    				 { display: '操作', width: 150, render: function (rowdata, rowindex, value) {
    	                        var h = "<a href='javascript:allotAuth(" + rowindex + ")'>分配权限</a> &nbsp;&nbsp;";
    	                        h += "<a href='javascript:cancelAuth(" + rowindex + ")'>取消</a> "; 
	    	                    return h;
	    	                }
    	                }
	          		],
	          		checkbox: false
             }
            var template = $.pap.createEFormGridShow({form: formOption, grid: gridOption});
	        grid = template.getGrid();
        });

        // 分配权限
        function allotAuth(rowindex) {
            var rowdata = grid.getRow(rowindex);
            $.ligerDialog.open({ 
     			title:'分配权限',
     			url: 'role_auth_value!loadEntityById?id='+ rowdata['id'],
     			height: 500,
     			width: 800, 
     			onLoaded: function(param){
     				if (param) {
	   		        	var documentF = param.contentDocument || param.document ;//兼容IE 和 FF
	   		            $('div.toolbar-pap',documentF).hide();
       				}
   		        },
     			buttons: [ 
     				{ text: '确定', onclick:  function (item, dialog) { 
     					var data = dialog.frame.onSave();
     					$.ligerui.ligerAjax({
							url: 'role_auth_value!allotAuth',
							dataType: "text",
							data: data,
							success:function(data, textStatus){
	    						if(data == 'MODIFYOK'){
	    							tip = $.ligerDialog.tip({ title: '提示信息', content: '权限分配成功！' });
	    							window.setTimeout(function(){ tip.close()} ,2000); 	
	    						}else {
	    							$.ligerDialog.success("权限分配失败！");
	     						}
	     					},
	    					error: function(XMLHttpRequest,textStatus){
	    						$.ligerDialog.error("错误代码执行");
	    					},
    						complete: function(){ grid.loadData(true);}
						});
     					dialog.close(); 
     				}}, 
					{ text: '取消', onclick:  function (item, dialog) { 
    					dialog.close(); 
    				}}
 				] 
 			});
        }

        // 取消分配的权限即清空
        function cancelAuth(rowindex) {
        	var rowdata = grid.getRow(rowindex);
        	var data = { 'e.id': rowdata['id'] };
        	$.ligerDialog.confirm('取消后已分配的权限将被清除，此操作不可恢复,你确认要取消吗?', function(yesOrNo) {
            	if (yesOrNo) {
	        		$.ligerui.ligerAjax({
						url: 'role_auth_value!cancelAuth',
						dataType: "text",
						data: data,
						success:function(data, textStatus){
							if(data == 'MODIFYOK'){
								tip = $.ligerDialog.tip({ title: '提示信息', content: '取消成功！' });
								window.setTimeout(function(){ tip.close()} ,2000); 	
							}else {
								$.ligerDialog.success("取消失败！");
	 						}
	 					},
						error: function(XMLHttpRequest,textStatus){
							$.ligerDialog.error("错误代码执行");
						},
						complete: function(){ grid.loadData(true);}
					});
                }
            });
        }
	  	
    </script>
</head>
<body></body>
</html>

