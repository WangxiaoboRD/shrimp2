<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="s" uri="/struts-tags" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="content-type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
	<meta content='width=330, height=400, initial-scale=1' name='viewport' />
	<link rel="icon" href="favicon.ico" type="image/x-icon" />
	<title>${initParam.title}</title>
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
    
	    $(function(){
	    	var groupicon = "../ligerUI/ligerUI/skins/icons/communication.gif";
	    	$("form").ligerForm({
	  		 	 inputWidth: 170,
	  		 	 labelWidth: 80, 
	  		 	 space: 20,
	  			 fields: [
	  	            { display: "程序跟踪", name: "debugSystem", type: "radiogroup", group: "调试设置", groupicon: groupicon, newline: true, options:{
	             	    data: [
	           	          { text: '开启', id: 'ON' },
	                      { text: '关闭', id: 'OFF' }
	                    ],
			            name: "debugSystem",
			            value: '${systemStatus}'
	          		}},
	          		
	          		{ display: "SQL跟踪", name: "debugSql", type: "radiogroup", newline: true,  options:{
	             	    data: [
	           	          { text: '开启', id: 'ON' },
	                      { text: '关闭', id: 'OFF' }
	                    ],
			            name: "debugSql",
			            value: '${sqlStatus}'
	          		}}
	           ]
	    });
	    // 程序跟踪单选框改变事件
	    $("input[name='debugSystem']").change(function() {
	    	var value = liger.get("debugSystem").getValue();
	    	//if (value == 'ON') {
		    	//选中程序跟踪开启单选框需做的操作
	    	$.ligerui.ligerAjax({
				url:"debug_sql!openOnOff",
				dataType:"json",
				data:{"systemStatus":value},
				success:function(_data,textStatus){
					if(_data.status == "ON"){
						//$.ligerDialog.success("操作成功！");
						tip = $.ligerDialog.tip({ title: '提示信息', content: '程序监控已启动' });
						window.setTimeout(function(){ tip.close()} ,2000); 	
						
					}else if(_data.status == "OFF"){
						tip = $.ligerDialog.tip({ title: '提示信息', content: '程序监控已关闭' });
						window.setTimeout(function(){ tip.close()} ,2000); 	
					}
				},
				error: function(XMLHttpRequest,textStatus){
				    $.ligerDialog.error("错误代码执行");
				},
				complete: function(){}
			});
		    //}else {
		    	//选中程序跟踪关闭单选框需作的操作
			//}
	    });

	 	// SQL跟踪单选框改变事件
	   $("input[name='debugSql']").change(function() {
	    	var value = liger.get("debugSql").getValue();
	    	//if (value == 'ON') {
	    		//选中SQL跟踪开启单选框需做的操作
		   // }else {
		    	//选中SQL跟踪关闭单选框需作的操作
			//}
			
			$.ligerui.ligerAjax({
				url:"debug_sql!openOnOff",
				dataType:"json",
				data:{"sqlStatus":value},
				success:function(_data,textStatus){
					if(_data.status == "ON"){
						//$.ligerDialog.success("操作成功！");
						tip = $.ligerDialog.tip({ title: '提示信息', content: 'SQL监控已启动' });
						window.setTimeout(function(){ tip.close()} ,2000); 	
						
					}else if(_data.status == "OFF"){
						tip = $.ligerDialog.tip({ title: '提示信息', content: 'SQL监控已关闭' });
						window.setTimeout(function(){ tip.close()} ,2000); 	
					}
				},
				error: function(XMLHttpRequest,textStatus){
				    $.ligerDialog.error("错误代码执行");
				},
				complete: function(){}
			});
	    })
	});
    </script>
 
</head>
<body style="padding: 10px">
<form/>
</body>
</html>
