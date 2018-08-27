<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="s" uri="/struts-tags" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="content-type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
	<meta content='width=330, height=400, initial-scale=1' name='viewport' />
	<link rel="icon" href="favicon.ico" type="image/x-icon" />
	<title>功能列表</title>
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
	<script type="text/javascript" src="../ligerUI/ligerUI/js/plugins/XGrid.js"></script>
    <script type="text/javascript">
        var grid = null,toolbar=null,form=null;
        $(function () {

            
        	//测试自动创建 toolbar
        	var toolBarOption = {	id:'toolbar', 
        						    items: [
						                { text: '新增', click: addMenu ,icon:'add'},
						                { line:true },
						                { text: '修改',disabled:true , click: modifyClick ,icon: 'modify',expression:'!=1'},
						                { line:true },
						                { text: '删除', click: deteleClick,icon:'delete',expression:'<=0'},
						                { line:true }
		            		    ]
		     			  };
           var formOption = {
           		labelWidth: 70, 
          		fields:[
                   { display: "功能名称", name: "e.itemName",type: "text" } ,
                   { display: "创建日期", name:"e.createDate",type:"date" } 
                ]
           }
           var gridOption = {
                allowHideColumn: false, 
                rownumbers: true, 
                //colDraggable: true, 
                //rowDraggable: true,
                checkbox:true,
                //data:TreeDeptData , 
                alternatingRow: true,
                usePager: false,  
                tree: { 
                	 columnName: 'itemName',
                     idField: 'id',
                     parentIDField: 'parentId'
                     
                   // columnId: 'deptName',
                   // idField: 'id',
                   // parentIDField: 'pid'
                 },
        		url:'item!loadByEntity',//此处设置会直接发送异步请求
        		async: false,
        		title:'功能列表',
        		columns:[
	        			
	        			{ display: '编码', name: 'id', width: 60, align: 'left' },
	                	{ display: '功能名称', name: 'itemName',width: 200, align: 'left' },
	                	{ display:'功能类型',name:'itemType',render:function(row){
			    				 if(row.itemType==0){return "菜单"};
			    				 if(row.itemType==1){return "按钮"};
			    				 if(row.itemType==2){return "接口"};
			    		}},
	                	{ display: '方法名', name: 'functionName', width: 100, align: 'left' },
	                	{ display: 'URL', name: 'url', width: 300, align: 'left' },
	                	{ display: '参数', name: 'urlParam', width: 180, align: 'left' },
	                	{ display: '序号', name: 'rank', width: 100 },
	    				{ display:'状态',name:'status',render:function(row){
			    				 if(row.status=='Y'){return "启用"};
			    				 if(row.status=='N'){return "禁用"};
			    		}}
    				]
           
           		};
        	var page =  $.pap.createFormGridList({
        					toolbar: toolBarOption,
        					form: formOption ,
        					grid: gridOption 
        				});
        	form = page.getForm() ;
        	grid = page.getGrid();
        	
        });
        
        var addMenu = function(item){
          	//$.pap.addTabItem({ text: '菜单注册', url: 'number_add.jsp'});
        	$.ligerDialog.open({ 
   				title:'添加功能',
   				url: 'item_add.jsp', 
   				height: 450,
   				width: 380, 
   				buttons: [ 
   					{ text: '确定', onclick: _saveObj }, 
   					{ text: '取消', onclick: _selectCancel}] 
   				});
        	
   	    };
   	    
   	   //添加确定按钮触发
   		var _saveObj = function(item, dialog){
	    	var data = dialog.frame.onSave();
	    	if(data!=null){
    			//dataGrid=$("#dataGrid").ligerGetGridManager();
    			$.ligerui.ligerAjax({
					async: false,
					url:"item!save",	
					//url:"user!loadByName",	
					data:data,
					dataType:"text",
					success:function(result, textStatus){
						if(result != null && result !=""){
							tip = $.ligerDialog.tip({ title: '提示信息', content: '成功添加一条记录！' });
							window.setTimeout(function(){ tip.close()} ,2000); 	
							//grid.loadData(true);
						}
					},
					error: function(XMLHttpRequest,textStatus){
							$.ligerDialog.error('操作出现异常');
					},
					complete:function(){grid.loadData(true);}
				});    			
    			dialog.close();
    		}	
	    }
   	    
		//使用页签方法打开
    	var modifyClick = function(item){
			var selectedRows = item.selectGrid.selected ; //.length ;   
   	    	if(! jQuery.isEmptyObject(selectedRows) ){
   	    		if(selectedRows.length > 1){
   	    			$.ligerDialog.warn('请选择一条要修改的记录！');
   	    		}else if(selectedRows.length ==1){
   	    			$.ligerDialog.open({
						title:'修改功能',
						width:380,
						height:450, 
						url: 'item!loadUpdateById?id='+grid.selected[0]['id'],
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
    	
    	//修改确定按钮触发
    	var _modfiyOK = function(item, dialog){
    		var data = dialog.frame.onSave();
	    	if(data!=null){
				$.ligerui.ligerAjax({
					url:"item!modify",
					dataType:"text",
					data:data,
					success:function(_data,textStatus){
						if(_data == 'MODIFYOK'){
							//$.ligerDialog.success("操作成功！");
							tip = $.ligerDialog.tip({ title: '提示信息', content: '成功更新一条记录！' });
							window.setTimeout(function(){ tip.close()} ,2000); 	
							//grid.loadData(true);
							
						}
					},
					error: function(XMLHttpRequest,textStatus){
					    $.ligerDialog.error('操作出现异常');
					},
					complete: function(){ grid.loadData(true);}
				});
				dialog.close();
    		}
    	}
    	
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
							url:"item!delete",
							async: false,
							dataType:"text",
							data:{ids:delIds},
							success:function(_data,textStatus){
								if(_data != ""){
									//$.ligerDialog.success("操作成功！");
									tip = $.ligerDialog.tip({ title: '提示信息', content: _data+'条信息被删除！' });
									window.setTimeout(function(){ tip.close()} ,2000); 	
								}
							},
							error: function(XMLHttpRequest,textStatus){
								$.ligerDialog.error('操作出现异常');
							},
							complete:function(){
								grid.loadData(true);
							}
						});
	    			}
    			});    			
	    	}else{
	    		$.ligerDialog.warn('你没有选择任何要删除的记录！')
	    	}
    	}
    	//取消方法
    	var _selectCancel = function(item, dialog){
    		dialog.close();
    	}
    </script>
</head>
<body></body>
</html>
