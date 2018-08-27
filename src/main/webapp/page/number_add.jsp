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
    <script src="../ligerUI/ligerUI/js/plugins/XGrid.js" type="text/javascript"></script>    
	<script src="../ligerUI/ligerUI/js/pap.js" type="text/javascript" ></script>
	<script src="../ligerUI/ligerUI/js/template/ligerTemplate.js" type="text/javascript"></script>
	<script src="../ligerUI/ligerUI/js/template/ligerXSFormCreate.js" type="text/javascript"></script>
	<script src="../ligerUI/ligerUI/js/core/ligerAjax.js" type="text/javascript" ></script>
    <script src="../ligerUI/ligerUI/js/json2.js" type="text/javascript" ></script>
    <script type="text/javascript">
        $(f_initGrid); 
		var v;
        var form = null;
        function f_initGrid(){
        
	  		 var formOptions = {
	  		 	 url: "number!save",	// 此处也是必须的
	  			 labelWidth: 80, 
           		 space:20,
	  			 fields: [
	 				{ display:'编码',name:'e.id',width:150,type:'text',newline: true,validate:{required:true}},
	 				{ display:'名称',name:'e.numberName',width:150,type:'text',validate:{required:true}},
	 				{ display:'步长',name:'e.step',width:150,type:'text',newline: true,validate:{required:true},options:{value:1}},
	 				{ display:'长度',name:'e.length',width:150,type:'text',validate:{required:true},options:{value:10}},
	 				{ display:'缓存数',name:'e.cacheNumber',width:150,type:'text',newline: true,validate:{required:true},options:{value:30}},
	 				{ display:'警告比数',name:'e.warnNumber',width:150,type:'text',options:{value:30}},
	 				{ display:'年度标示',width:150,type:'checkboxgroup',newline: true,name:'yearmark',
	 					options:{
	 						rowSize:1,
	 						name:'e.markYear',
        					data:[
        						{ text: '选中使用标示', id: '1' }
				            ] 
	 					}
	 				},
	 				{ display: "子对象标示",width:150, name:"e.markSub.ecode",type:"text", type: "select", comboboxName: "testSelect", options:{
	                	  //1.直接填写数据
	                	  url:'bussiness_ele!loadType',
			              valueField: 'ecode', 
					      textField: 'ename',
			              selectBoxHeight:180
                	}}
               ]};
               
              var template =$.pap.createSFormCreate({form: formOptions});
	  		  form = template.getForm() ;
	  		  v = form.validateForm() ;
        }
	  	
    </script>
</head>
<body></body>
</html>

