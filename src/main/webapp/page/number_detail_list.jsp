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
		             { text: '创建明细', click: addClick,icon: 'add'},
		             { line:true },
	                 { text: '修改',disabled:true , click: modifyClick ,icon: 'modify',expression:'!=1'},
	                 { line:true },
	                 { text: '删除',disabled:true , click: deteleClick,icon:'delete',expression:'<=0'}
         		]
		   };
           var formOption = {
          		fields:[
          		   {display: "号码对象",name: "e.number.id",hidden:true,newline: true, type: "text" ,options:{value:currentParam['id']}},
                   {display: "子对象",name: "e.number.markSub.ename",newline: true, type: "text" ,options:{value:currentParam['markSub.ename'],readonly:true}},
                   //{ display: "值", name: "e.subobject",newline: true, type: "text" } ,
                   { display: "元素值", name:"e.subobject", type: "select", comboboxName: "testSelect", options:{
	                	  //1.直接填写数据
	                	  url:"bussiness_ele_detail!loadType?id="+currentParam['markSub.ecode']+"",
			              valueField: 'value', 
					      textField: 'value',
					      keySupport:true,
			              selectBoxHeight:180
                   }},
                   { display: "创建日期", name:"e.createDate",type:"date" } 
                ]
           }
        	
           var gridOption = {
        		url:'number_detail!loadByPage',//此处设置会直接发送异步请求
        		title:'业务对象明细',
        		columns:[
        			{display:'明细编码',name:'id',width:100,hide:true},
    				{display:'编码',name:'number.id',width:100},
    				{display:'名称',name:'number.numberName',width:150},
    				{display:'范围段',name:'numberScope',width:100},
    				{display:'前缀',name:'prefix',width:100},
    				{display:'年份',name:'year',width:100},
    				{display:'子对象',name:'subobject',width:150},
    				{display:'开始值',name:'startNumber',width:100},
    				{display:'结束值',name:'endNumber',width:100},
    				{display:'当前值',name:'currentNumber',width:100},
    				{display:'外部标示',name:'markExt',width:100,render:function(row){
    					if(row.markExt=='N'){return "内部"};
    					if(row.markExt=='Y'){return "外部"};
    				}},
    				{display:'创建日期',name:'createDate',width:150}
    			] };
        	var page =  $.pap.createFormGridList({
        					toolbar: toolBarOption,
        					form: formOption ,
        					grid: gridOption 
        				});
        	form = page.getForm() ;
        	grid = page.getGrid();
       
        	if(currentParam['markSub.ecode']==''){
        		//隐藏
		    	//$("input[name='e.number.markSub.ename']").attr("disabled","disabled");;
		    	//$("input[name='e.subobject']").attr("disabled","disabled");
		    	
		    	$("input[name='e.number.markSub.ename']").parent().parent().parent().css("display","none");
		    	$("input[name='e.subobject']").parent().parent().parent().css("display","none");
		    	
				//subobject.css('display','none'); 
				//markSub.attr("disabled","disabled");
				//显示
				//$("#id").css('display','block'); 
        	}
        });
        
    	//创建明细
    	var addClick = function(item){
    		/*
    		if( grid.selected.length == 1 ){
    			var objCode = currentParam['fullClassName'];
    			var objName = currentParam['bussName'];
    			var numberCode = grid.selected[0]['id'];
   				//var myIds = grid.selected[0]['id']+","+currentParam['fullClassName'];
	  			$.ligerui.ligerAjax({
						url:"number_config!save",
						async: false,
						dataType:"text",
						data:{"e.objectCode":objCode,"e.numberDetail.id":numberCode,"e.objectName":objName},
						beforeSend:function(){
							if(objCode == '' || numberCode==''){
								$.ligerDialog.error('提交的信息值为空，请查证');
								return false;
							}
						},
						success:function(_data,textStatus){
							if(_data != ""){
								//$.ligerDialog.success("操作成功！");
								tip = $.ligerDialog.tip({ title: '提示信息', content:'保存成功' });
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

    		}else{
	    		$.ligerDialog.warn('你没有选择任何要删除的记录！')
	    	}
	    	*/
	    	
	    	var _markSub = currentParam['markSub.ecode'];
    		var _subobject = $("input[name='e.subobject']").val();
    		var _markYear = currentParam['markYear'];
    		var _id = currentParam['id'];
	    	
	    	if(_markSub != ''){
	    		if(_subobject == ''){
	    			$.ligerDialog.warn('请设置元素值！');
	    			return false;
	    		}
	    	}
	    	
    		$.ligerDialog.open({ 
  				title:'创建号码对象明细',
  				url: 'number_detail_add.jsp', 
  				param:{'markSub':_markSub,'subobject':_subobject,'markYear':_markYear,'id':_id},
  				height: 400,
  				width: 450, 
  				buttons: [ 
  					{ text: '确定', onclick: _saveObj }, 
  					{ text: '取消', onclick: _selectCancel}] 
  			});
    	}
    	//添加确定按钮触发
   		var _saveObj = function(item, dialog){
   			var data = dialog.frame.onSave();
			if(data!=null){
				$.ligerui.ligerAjax({
					url:"number_detail!save",
					dataType:"text",
					data:data,
					success:function(_data,textStatus){
						if(_data != ''){
							dialog.close();
							//$.ligerDialog.success("操作成功！");
							tip = $.ligerDialog.tip({ title: '提示信息', content: '保存信息成功！' });
							window.setTimeout(function(){ tip.close()} ,2000); 	
							grid.loadData(true);
							
						}
					},
					error: function(XMLHttpRequest,textStatus){
					   $.ligerDialog.warn("错误代码执行");
					},
					complete: function(){}
				});
    		}
	    }
	    //---修改
   	    var modifyClick=function(item){
   	    	if(! jQuery.isEmptyObject(grid.selected) ){
   	    		if(grid.selected.length > 1){
   	    			$.ligerDialog.warn('请选择一条要修改的记录！');
   	    		}else if(grid.selected.length ==1){
   	    			$.ligerDialog.open({
						title:'修改明细信息',
						width:400,
						height:450, 
						url: 'number_detail!loadUpdateById?id='+grid.selected[0]['id'],
						buttons: [
			                {text:'确定',onclick:_modfiyOK},
			                {text:'取消',onclick:_selectCancel}
	        			]
	        		});
   	    		}
   	    	}else{
   	    		$.ligerDialog.warn('至少选择一条要修改的记录！');
   	    	}
    	};
    	//修改保存
    	var _modfiyOK = function(item,dialog){
    		
    		var data = dialog.frame.onSave();
    		//alert(data);
    		//console.log(data);
	    	if(data!=null){
				$.ligerui.ligerAjax({
					url:"number_detail!modify",
					dataType:"text",
					data:data,
					success:function(_data,textStatus){
						if(_data == 'MODIFYOK'){
							//$.ligerDialog.success("操作成功！");
							dialog.close();
							tip = $.ligerDialog.tip({ title: '提示信息', content: '成功更新一条记录！' });
							window.setTimeout(function(){ tip.close()} ,2000); 	
							grid.loadData(true);
							
						}
					},
					error: function(XMLHttpRequest,textStatus){
					   $.ligerDialog.warn("错误代码执行");
					},
					complete: function(){}
				});
    		}
    	}
    	
    	
    	//删除
    	var deteleClick = function(item){
    		if( grid.selected.length > 0 ){
	    		$.ligerDialog.confirm('数据删除后不可恢复,你确认要删除?',function(data) {
    				var delIds = "";
    				$(grid.selected).each(function(i,item){
    					delIds+=item['id']+",";
    				});
    				if(data){
					   	$.ligerui.ligerAjax({
							url:"number_detail!delete",
							dataType:"text",
							data:{ids:delIds},
							success:function(_data,textStatus){
								if(_data !=''){
									tip = $.ligerDialog.tip({ title: '提示信息', content: _data+'条记录被删除！' });
									window.setTimeout(function(){ tip.close()} ,2000); 	
									grid.loadData(true);
								}	
							},
							error: function(XMLHttpRequest,textStatus){
									$.ligerDialog.error('操作出现异常');
							},
							complete:function(){}
						});    			
	    			}
    			});    			
	    	}else{
	    		$.ligerDialog.warn('你没有选择任何要删除的记录！')
	    	}
    	}
    	 
	    //取消
    	var _selectCancel = function(item, dialog){
    		dialog.close();
    	}
    </script>
</head>
<body></body>
</html>
