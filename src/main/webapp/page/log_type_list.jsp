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
	<script src="../ligerUI/ligerUI/js/core/base.js" type="text/javascript"></script>
	<script type="text/javascript" src="../ligerUI/ligerUI/js/ligerui.all.js"></script>
	<script src="../ligerUI/ligerUI/js/plugins/ligerUtil.js" type="text/javascript" ></script> 
	<script type="text/javascript" src="../ligerUI/ligerUI/js/pap.js"></script>
	<script src="../ligerUI/ligerUI/js/template/ligerTemplate.js" type="text/javascript"></script>
	<script src="../ligerUI/ligerUI/js/template/ligerXFormGridList.js" type="text/javascript"></script>
    <script type="text/javascript">
    	// 表格
        var grid = null;
        $(function () {
        	   var toolBarOption = { 
   	                   items: [
	   	                  { text: '新增', icon: 'add', click: function(){
                   				$.ligerDialog.open({ 
                   	   				title:'添加窗口',
                   	   				url: 'log_type_add.jsp', 
                   	   				height: 220,
                   	   				width: 350, 
                   	   				buttons: [ 
                   	   					{ text: '确定', onclick: function(item, dialog) {
                   	   						var data = dialog.frame.onSave();
		                   	   		    	if(data!=null){
		                   	   		    	$.ligerui.ligerAjax({
		                   	   						type:"POST",
		                   	   						async: false,
		                   	   						url:"log_type!save",	
		                   	   						data:data,
		                   	   						dataType:"text",
		                   	   						beforeSend:function(){},
		                   	   						success:function(result, textStatus){
		                   	   							if(result != null && result !=""){
		                   	   								tip = $.ligerDialog.tip({ title: '提示信息', content: '成功添加一条记录！' });
         				       							 	window.setTimeout(function(){ tip.close()} ,2000); 	
         				       								dialog.close();
		                   	   							}
		                   	   						},
		                   	   						error: function(XMLHttpRequest,textStatus){
		                   	   							$.ligerDialog.error('操作出现异常');
		                   	   						},
		                   	   						complete:function(){grid.loadData(true);}
		                   	   					});    			
		                   	   	    		}	
                           	   			}}, 
                   	   					{ text: '取消', onclick: function(item, dialog) {
                           	   				dialog.close();
                       	   				}}
                       	   			] 
                   	   			});
                          }},
	   	   	              { line:true },
	   	   	              { text: '修改', icon: 'modify' ,expression:'!=1', disabled:true, click: function(item) {
                 		    	if(! jQuery.isEmptyObject(grid.selected) ){
                 	   	    		if(grid.selected.length > 1){
                 	   	    			$.ligerDialog.warn('请选择一条要修改的记录！');
                 	   	    		}else if(grid.selected.length ==1){
                 	   	    			$.ligerDialog.open({
                 							title:'编辑窗口',
                 							width:350,
                 							height:220, 
                 							url: 'log_type!loadUpdateById?id='+grid.selected[0]['id'],
                 							buttons: [
                 				                {text:'确定',onclick: function(item, dialog) {
                 				                	var data = dialog.frame.onSave();
	                 				       	    	if(data!=null){
	                 				       				$.ligerui.ligerAjax({
	                 				       					url:"log_type!modifyAll",
	                 				       					dataType:"text",
	                 				       					data:data,
	                 				       					success:function(_data,textStatus){
	                 				       						if(_data == 'MODIFYOK'){
	                 				       							tip = $.ligerDialog.tip({ title: '提示信息', content: '成功更新一条记录！' });
	                 				       							window.setTimeout(function(){ tip.close()} ,2000); 	
	                 				       							dialog.close();
	                 				       						}
	                 				       					},
	                 				       					error: function(XMLHttpRequest,textStatus){
	                 				       					$.ligerDialog.error('操作出现异常');
	                 				       					},
	                 				       					complete: function(){ grid.loadData(true);}
	                 				       				});
	                 				           		}
                     				            }},
                 				                {text:'取消',onclick: function(item, dialog) {
                     				            	dialog.close();
                     				            }}
                 		        			]
                 		        		});
                 	   	    		}
                 	   	    	}else{
                 	   	    		$.ligerDialog.warn('至少选择一条要修改的记录！');
                 	   	    	}
          		           }},
	   	   	              { line:true },
	   	   	              { text: '删除', icon: 'delete', expression:'==0', disabled:true, click: function(){
	   	   	            	  if( grid.selected.length > 0 ){
	   	   	      	    		$.ligerDialog.confirm('数据删除后不可恢复，确认要删除吗？', function(data) {
	   	   	          				var delIds = "";
	   	   	          				$(grid.selected).each(function(i, item){
	   	   	          					delIds += item['id']+",";
	   	   	          				});
	   	   	          				if(data){
	   	   	          					$.ligerui.ligerAjax({
	   	   	      							type: "POST",
	   	   	      							async:  false,
	   	   	      							url: "log_type!delete",
	   	   	      							data: { ids: delIds },
	   	   	      							dataType: "text",
	   	   	      							success: function(result, status){
	   	   	      								if(result != ""){
	   	   	      									tip = $.ligerDialog.tip({ title: '提示信息', content: result + '条记录被删除！' });
	   	   	      									window.setTimeout(function(){ tip.close()} ,2000); 
	   	   	      								}	
	   	   	      							},
	   	   	      							error: function(XMLHttpRequest,status){
	   	   	      								$.ligerDialog.error('操作出现异常');
	   	   	      							},
	   	   	      							complete:function(){
	   	   	      								grid.loadData(true);
	   	   	      							}
	   	   	      					   	});    			
	   	   	      	    			}
	   	   	          			});    			
	   	   	      	    	}else {
	   	   	      	    		$.ligerDialog.warn('请选择要删除的记录！')
	   	   	      	    	}
	   	   			      }}
                   		]
             	};

        	 form = {
                	 labelWidth: 45,
                     fields:[
                         { display: "编码", name: "e.id", type: "text" },
        	 			 { display: "名称", name: "e.name", type: "text" }
					    ]
                	}

        	 var gridoption={
                     columns:[
	    				{ display: '编码', name: 'id', width: 150 },
	    				{ display: '名称', name: 'name', width: 150 },
	    				{ display: '创建人', name: 'createUser', width: 150 },
	    				{ display: '创建时间', name: 'createDate', width: 150 }
              		],
                    url:'log_type!loadByPage',
                    exportBtn: true,
			        exportUrl: 'log_type!exportFile'
             }
          
           var glist = $.pap.createFormGridList({ toolbar:toolBarOption,form:form,grid:gridoption});

           grid = glist.getGrid();
        });
    </script>
</head>
<body>
</body>
</html>
