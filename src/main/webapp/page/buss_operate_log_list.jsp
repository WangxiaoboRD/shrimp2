<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="s" uri="/struts-tags" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="content-type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
	<meta content='width=330, height=400, initial-scale=1' name='viewport' />
	<link rel="icon" href="favicon.ico" type="../image/x-icon" />
	<title>操作日志</title>
	<link href="../ligerUI/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
	<link href="../ligerUI/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />	
    <script src="../ligerUI/jquery/jquery-1.6.4.js" type="text/javascript"></script>
	<script src="../ligerUI/jquery-validation/jquery.form.js" type="text/javascript"></script> 
	<script src="../ligerUI/jquery-validation/jquery.validate.js" type="text/javascript"></script> 
    <script src="../ligerUI/jquery-validation/jquery.metadata.js" type="text/javascript"></script>
    <script src="../ligerUI/jquery-validation/messages_cn.js" type="text/javascript"></script>    
    <script src="../ligerUI/ligerUI/js/ligerui.all.js" type="text/javascript"></script>       
	<script src="../ligerUI/ligerUI/js/template/ligerTemplate.js" type="text/javascript"></script>
	<script src="../ligerUI/ligerUI/js/template/ligerXFormGridList.js" type="text/javascript"></script>
	<script type="text/javascript" src="../ligerUI/ligerUI/js/pap.js"></script>
    <script type="text/javascript">
        var grid = null;
        $(function () {     
        	 var toolBarOption = { 
		            items: [
	   		        	{ text: '删除', click: deleteClick, icon:'delete',expression:'==0',disabled:true}
	              	]
	          };  
		     var form={
		      	  inputWidth: 130, labelWidth: 70, space:20,
	              fields:[
	              	 { display: "业务模块", name:"e.moduleId", type:"select", options:{
						    url:'log_type!load',
						    valueField: 'id', 
					        textField: 'name',
					        keySupport:true,
							selectBoxHeight: 200,
							selectBoxWidth: 220
					  }},
					  { display: "业务对象", name:"e.classPath", type:"select", options:{
						    url:'log_class!load',
						    valueField: 'className', 
					        textField: 'name',
					        keySupport:true,
							selectBoxHeight: 200,
							selectBoxWidth: 220
					  }},
	                  { display: "操作人", name: "e.operUserName", labelWidth: 60, type: "text" }, 
	                  { display: "操作日期", name: "e.tempStack.startDate", type: "date", newline: true, space: 10 },
	                  { display: "至", type: "label", space: 10 },
	                  { hideLabel: true, name: "e.tempStack.endDate", type: "date" }
	 			  ]
	          }
	          var gridoption={
	          		url:'buss_operate_log!loadByPage',
	               	columns:[
	    				{ display: '编号', name:'id' },
	    				{ display:'业务模块编号',name:'moduleId', hide: true },
	    				{ display:'业务模块',name:'moduleName' },
	    				{ display:'业务对象路径',name:'classPath', hide: true },
	    				{ display:'业务对象',name:'className' },
	    				{ display:'业务功能',name:'methodName' },
	    				{ display:'操作人',name:'operUserName' },
	    				{ display:'操作时间',name:'operDate', width: 180 },
	    				{ display:'操作IP',name:'ip' }
	    			]
	          }
	          var glist=$.pap.createFormGridList({ toolbar:toolBarOption,form:form,grid:gridoption});
	          grid=glist.getGrid();
   		 });
	    
	    //--删除
	    var deleteClick = function(item){
	    	if( grid.selected.length > 0 ){
	    		$.ligerDialog.confirm('数据删除后不可恢复,你确认要删除?',function(data) {
    				var delIds = "";
    				$(grid.selected).each(function(i,item){
    					delIds+=item['id']+",";
    				});
    				if(data){
    					$.ligerui.ligerAjax({
							url:"buss_operate_log!delete",
							dataType:"text",
							data:{ids:delIds},
							success:function(_data,textStatus){
								if(_data != ''){
									tip = $.ligerDialog.tip({ title: '提示信息', content: _data+'条信息被删除！' });
									window.setTimeout(function(){ tip.close()} ,2000); 	
									grid.loadData(true);
								}
							},
							error: function(XMLHttpRequest,textStatus){
							    $.ligerDialog.error("错误代码执行");
							},
							complete: function(){}
						});
	    			}
    			});    			
	    	}else{
	    		$.ligerDialog.warn('你没有选择任何要删除的记录！')
	    	}
    	}
    </script>
</head>
<body>
</body>
</html>
