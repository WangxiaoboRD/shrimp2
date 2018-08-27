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
        $(function () {
        	//测试自动创建 toolbar
        	var toolBarOption = {	id:'toolbar', 
        						    items: [
						                { text: '新增', click: /*addClick*/addClickTab ,icon:'add'},
						                { line:true },
						                { text: '修改',disabled:true , click: /* modifyClick *//*modifyClick1*/modifyClickTab ,icon: 'modify',expression:'!=1'},
						                { line:true },
						                { text: '删除', click: deteleClick,icon:'delete',expression:'<=0'},
						                { line:true },
						                { text: '明细设置', click: settingClick,disabled:true ,expression:'!=1',icon: 'settings'}
		            		    ]
		     			  };
           var formOption = {
        		labelWidth: 60, 
          		fields:[
                   { display: "编码", name: "e.id",newline: true, type: "text" } ,
                   { display: "名称", name: "e.numberName",type: "text" } ,
                   { display: "创建日期", name:"e.createDate",type:"date" } 
                ]
           }
        	
           var gridOption = {
        		url:'number!loadByPage',//此处设置会直接发送异步请求
        		title:'号码对象',
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
    				{display:'子对象标示',name:'markSub.ecode',width:100,hide:'true'},
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
        
        var addClickTab = function(item){
          	$.pap.addTabItem({ text: '号码对象/新增', url: 'number_add.jsp'});
        	//$.pap.addTabItem({ text: '添加用户信息', url: 'user_add.jsp',param:grid.selected });
        	
   	    };
   	    
		//使用页签方法打开
    	var modifyClickTab = function(item){
			var selectedRows = item.selectGrid.selected ; //.length ;   
   	    	if(! jQuery.isEmptyObject(selectedRows) ){
   	    		if(selectedRows.length > 1){
   	    			$.ligerDialog.warn('请选择一条要修改的记录！');
   	    		}else if(selectedRows.length ==1){
   	    			$.ligerui.ligerAjax({
							url:'number!selectDetail',
							dataType:"text",
							data:{'id':grid.selected[0]['id']},
							success:function(_data,textStatus){
								if(_data == 'OK'){
									$.pap.addTabItem({ text: '号码对象/修改', url: 'number!loadUpdateById?id='+grid.selected[0]['id'],param:selectedRows });
								}else{
									$.ligerDialog.warn('包含明细的对象不能修改！');
								}
							},
							error: function(XMLHttpRequest,textStatus){
							    $.ligerDialog.error("错误代码执行");
							},
							complete: function(){}
					});
   	    		}
   	    	}else{
   	    		$.ligerDialog.warn('至少选择一条要修改的记录！');
   	    	}
    	}; 
    	
    	//删除
    	var deteleClick = function(item){
    		if( grid.selected.length > 0 ){
	    		$.ligerDialog.confirm('数据删除后不可恢复,你确认要删除?',function(value) {
    				if(value){
	    				var delIds = "";
	    				$(grid.selected).each(function(i,item){
	    					delIds+=item['id']+",";
	    				});
    					$.ligerui.ligerAjax({
							url:"number!delete",
							async: false,
							dataType:"text",
							data:{ids:delIds},
							beforeSend:function(){},
							success:function(_data,textStatus){
								if(_data != ""){
									//$.ligerDialog.success("操作成功！");
									tip = $.ligerDialog.tip({ title: '提示信息', content: _data });
									window.setTimeout(function(){ tip.close()} ,2000); 	
									grid.loadData(true);
									
								}
							},
							error: function(XMLHttpRequest,textStatus){
								$.ligerDialog.error('操作出现异常');
							},
							complete:function(){
								//alert("----");
								//grid.loadData(true);
							}
						});
    				
    				
    					/*
	    				$.ajax({
							type:"POST",
							async: false,
							url:"in_stock!delete",
							//data:{ids:checkedEntity.join(',')},
							data:{ids:delIds},
							dataType:"text",
							beforeSend:function(){},
							success:function(result, textStatus){
								if(result !="0"){
									tip = $.ligerDialog.tip({ title: '提示信息', content: result+'条记录被删除！' });
									window.setTimeout(function(){ tip.close()} ,2000); 
								}	
								else
									$.ligerDialog.error("操作失败！");
							},
							error: function(XMLHttpRequest,textStatus){
									$.ligerDialog.error('操作出现异常');
							},
							complete:function(){
								grid.loadData(true);
							}
					   	}); */   			
	    			}
    			});    			
	    	}else{
	    		$.ligerDialog.warn('你没有选择任何要删除的记录！')
	    	}
    	}
    	
    	// 设置明细
    	var settingClick = function(item){
   	    	if(! jQuery.isEmptyObject(grid.selected) ){
   	    		if(grid.selected.length > 1){
   	    			$.ligerDialog.warn('请选择一条要设置的记录！');
   	    		}else if(grid.selected.length ==1){
   	    			$.pap.addTabItem({ text: '设置明细', url: 'number_detail_list.jsp',param:grid.selected });
   	    		}
   	    	}else{
   	    		$.ligerDialog.warn('至少选择一条要设置的记录！');
   	    	}
    	}; 
    </script>
</head>
<body></body>
</html>
