<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="s" uri="/struts-tags" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="content-type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
	<meta content='width=330, height=400, initial-scale=1' name='viewport' />
	<link rel="icon" href="favicon.ico" type="image/x-icon" />
	<title>号码对象</title>
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
        var grid = null,toolbar=null,form=null;
        var currentPage = $.pap.getOpenPage(window);
  	    var currentParam = currentPage.getParam("param")[0] ;
        $(function () {
        	//测试自动创建 toolbar
        	var toolBarOption = {	id:'toolbar', 
	  				items: [
	                	{ text: '绑定对象', click: settingClick,disabled:true ,expression:'!=1',icon: 'settings'}
	        		]
 			};
            var formOption = {
           		labelWidth: 85, 
          		fields:[
          		   { display:'业务对象编码',name:'bussCode',hidden:true,options:{value:currentParam['bussCode']}},
          		   { display:'业务对象名称',name:'bussName',hidden:true,options:{value:currentParam['bussName']}},
                   { display: "号码对象编码", name: "e.id",newline: true, type: "text" } 
                ]
           }
        	
           var gridOption = {
        		url:"number!loadByPageUse?id="+currentParam['bussCode'],//此处设置会直接发送异步请求
        		title:'当前配置业务对象:'+currentParam['bussName'],
        		columns:[
    				{display:'编码',name:'id',width:150},
    				{display:'名称',name:'numberName',width:150},
    				{display:'号码长度',name:'length',width:100},
    				{display:'号码步长',name:'step',width:100},
    				{display:'缓存个数',name:'cacheNumber',width:100},
    				{display:'警告数',name:'warnNumber',width:100},
    				{display:'年度标示',name:'markYear',width:100,render:function(row){
    					if(row.markYear=='1'){return "启用"};
    					if(row.markYear=='0'){return "停用"};
    				}},
    				{display:'子对象标示',name:'markSub.ename',width:100},
    				{display:'子对象编码',name:'markSub.ecode',width:100,hide:'true'},
    				{display:'创建日期',name:'createDate',width:150}
    			] };
        	var page =  $.pap.createFormGridList({
        					toolbar: toolBarOption,
        					form: formOption ,
        					grid: gridOption 
        				});
        	form = page.getForm() ;
        	grid = page.getGrid();
        	
        });
       
    	// 绑定对象
    	var settingClick = function(item){
   	    	if(! jQuery.isEmptyObject(grid.selected) ){
   	    		if(grid.selected.length > 1){
   	    			$.ligerDialog.warn('请选择一条要设置的记录！');
   	    		}else if(grid.selected.length ==1){
   	    			var _bussCode = currentParam['bussCode'];
   	    			var _id = grid.selected[0]['id'];
   	    			//判定号码对象是否有子对象标示，且与选择的业务对象是否一致，
   	    			//如果一致，判断号码对象是否已经绑定了业务对象，且该业务对象与之前绑定的业务对象是否一致
   	    			$.ligerui.ligerAjax({
							url:'number_config!selectNumber',
							dataType:'text',
							data:{'e.objectCode':_bussCode,'e.numberDetail.number.id':_id},
							success:function(_data,textStatus){
								if(_data == '0'){
				   	    			var _bussName = currentParam['bussName'];
				   	    			var _numberName = grid.selected[0]['numberName'];
				   	    			var _ecode = grid.selected[0]['markSub.ecode'];
				   	    			$.pap.addTabItem({ text: '绑定明细信息', url: 'number_object_bind.jsp',param:{'bussCode':_bussCode,'bussName':_bussName,'id':_id,'numberName':_numberName,'ecode':_ecode}});
								}else if(_data == '3'){
									$.ligerDialog.warn("号码对象已绑定其他业务对象");
								}else if(_data == '2'){
									$.ligerDialog.warn("业务对象已绑定其他号码对象");
								}else if(_data == '1'){
									$.ligerDialog.warn("号码对象子对象与业务对象属性不符");
								}
							},
							error: function(XMLHttpRequest,textStatus){
							    $.ligerDialog.warn("错误代码执行");
							},
							complete: function(){}
					});
   	    		}
   	    	}else{
   	    		$.ligerDialog.warn('至少选择一条要设置的记录！');
   	    	}
    	}
    	
    </script>
</head>
<body></body>
</html>
