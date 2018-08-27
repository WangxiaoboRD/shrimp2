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
    	var currentPage = $.pap.getOpenPage(window);
    	var currentParam = currentPage.getParam("param")[0];	
        $(function(){
	  		 var formOption = {
	  			 labelWidth: 80, 
           		 space:20,
	  			 fields: [
  			        { display: "角色编码", name: "e.roleCode", type: "text", options: { value: currentParam['roleCode'], readonly: true }}, 
  	                { display: "角色名称", name: "e.roleName", type: "text", options: { value: currentParam['roleName'], readonly: true }}
               ]};
	  		 
	  		 var gridOption={
	  				 url:'check!loadByPage',
	                 columns:[
						{ display: '编号',name: 'code', hide: true },
	    				{ display: '单据名称',name: 'bussName'},
	                    { display: '单据编码',name: 'objName' },
	    				{ display: '审核级别',name: 'objLevel'},
	    				{ display: '操作', width: 150, render: function (rowdata, rowindex, value) {
    	                        var h = "&nbsp;&nbsp;<a href='javascript:allotAuth(" + rowindex + ")'>分配权限</a> &nbsp;&nbsp;";
	    	                    return h;
	    	            }}
	          		],
	          		checkbox: false
             }
            var template = $.pap.createEFormGridShow({form: formOption, grid: gridOption});
	        grid = template.getGrid();
        });

        // 分配权限
        function allotAuth(rowindex) {
            var rowdata = grid.getRow(rowindex);
            var data = [];
			for(var i = 1; i < parseInt(rowdata.objLevel)+1; i++){
			    if(i==1)
			    	data.push({ text: '一级审核', id: '1' });
			    if(i==2)
			    	data.push({ text: '二级审核', id: '2' });
			    if(i==3)
			    	data.push({ text: '三级审核', id: '3' });
			    if(i==4)
			    	data.push({ text: '四级审核', id: '4' });
			    if(i==5)
			    	data.push({ text: '五级审核', id: '5' });
			    if(i==6)
			    	data.push({ text: '六级审核', id: '6' });
			    if(i==7)
			    	data.push({ text: '七级审核', id: '7' });
			    if(i==8)
			    	data.push({ text: '八级审核', id: '8' });
			}
            $.ligerDialog.open({ 
     			title:'分配权限',
     			url: 'check_role_allot_level.jsp',
     			param:{"code":rowdata.code,"objName":rowdata.objName,"bussName":rowdata.bussName,"objLevel":rowdata.objLevel,"roleCode":currentParam['roleCode'],"roleName":currentParam['roleName'],"data":data},
     			height: 300,
     			width: 370, 
     			buttons: [ 
     				{ text: '确定', onclick:  function (item, dialog) { 
     					var data = dialog.frame.onSave();
     					console.dir(data);
     					$.ligerui.ligerAjax({
							url: 'check_role!allotAuth',
							dataType: "text",
							data: data,
							success:function(data, textStatus){
	    						if(data == 'MODIFYOK'){
	    							tip = $.ligerDialog.tip({ title: '提示信息', content: '审核权限分配成功！' });
	    							window.setTimeout(function(){ tip.close()} ,2000); 	
	    						}else {
	    							$.ligerDialog.success("审核权限分配失败！");
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
    </script>
</head>
<body></body>
</html>

