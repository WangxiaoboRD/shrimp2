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
	<script type="text/javascript" src="../ligerUI/ligerUI/js/plugins/XDialog.js"></script>
	<script type="text/javascript" src="../ligerUI/ligerUI/js/plugins/ligerUtil.js"></script>
	<script src="../ligerUI/ligerUI/js/template/ligerTemplate.js" type="text/javascript"></script>
	<script src="../ligerUI/ligerUI/js/template/ligerXFormGridList.js" type="text/javascript"></script>
	<script type="text/javascript" src="../ligerUI/ligerUI/js/pap.js"></script>
	
    <script type="text/javascript">
        var grid = null;
           $(function () {
                var toolBarOption = { 
   	                   items: [
                   			{ text: '新增', icon: 'add', click: function(){
                   				$.ligerDialog.open({ 
                   	   				title:'添加用户',
                   	   				url: 'users_add.jsp', 
                   	   				height: 420,
                   	   				width: 400, 
                   	   				buttons: [ 
                   	   					{ text: '确定', onclick: function(item, dialog) {
                   	   						var data = dialog.frame.onSave();
		                   	   		    	if(data!=null){
		                   	   		    	$.ligerui.ligerAjax({
		                   	   						type:"POST",
		                   	   						async: false,
		                   	   						url:"users!save",	
		                   	   						data:data,
		                   	   						dataType:"text",
		                   	   						beforeSend:function(){},
		                   	   						success:function(result, textStatus){
		                   	   							if(result != null && result !=""){
		                   	   							 tip = $.ligerDialog.tip({ title: '提示信息', content: '成功添加一条记录！' });
         				       							 window.setTimeout(function(){ tip.close()} ,2000); 	
		                   	   							}
		                   	   						},
		                   	   						error: function(XMLHttpRequest,textStatus){
		                   	   								$.ligerDialog.error('操作出现异常');
		                   	   						},
		                   	   						complete:function(){grid.loadData(true);}
		                   	   					});    			
		                   	   	    			dialog.close();
		                   	   	    		}	
                           	   			}}, 
                   	   					{ text: '取消', onclick: function(item, dialog) {
                           	   				dialog.close();
                       	   				}}
                       	   			] 
                   	   			});
                           	}},
                 		    { line: true },
      		                { text: '修改', icon: 'modify' ,expression:'!=1', disabled:true, click: function(item) {
                 		    	if(! jQuery.isEmptyObject(grid.selected) ){
                 	   	    		if(grid.selected.length > 1){
                 	   	    			$.ligerDialog.warn('请选择一条要修改的记录！');
                 	   	    		}else if(grid.selected.length ==1){
                 	   	    			$.ligerDialog.open({
                 							title:'修改用户',
                 							width:400,
                 							height:420, 
                 							url: 'users!loadUpdateById?id='+grid.selected[0]['userCode'],
                 							buttons: [
                 				                {text:'确定',onclick: function(item, dialog) {
                 				                	var data = dialog.frame.onSave();
	                 				       	    	if(data!=null){
	                 				       				$.ligerui.ligerAjax({
	                 				       					url:"users!modify",
	                 				       					dataType:"text",
	                 				       					data:data,
	                 				       					success:function(_data,textStatus){
	                 				       						if(_data == 'MODIFYOK'){
	                 				       							tip = $.ligerDialog.tip({ title: '提示信息', content: '成功更新一条记录！' });
	                 				       							window.setTimeout(function(){ tip.close()} ,2000); 	
	                 				       						}
	                 				       					},
	                 				       					error: function(XMLHttpRequest,textStatus){
	                 				       					$.ligerDialog.error('操作出现异常');
	                 				       					},
	                 				       					complete: function(){ grid.loadData(true);}
	                 				       				});
	                 				       				
	                 				       				dialog.close();
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
      		                { line: true },
      		                { text: '删除', icon: 'delete', expression:'==0', disabled:true, click: function(item) {
      		                	 if( grid.selected.length > 0 ){
      	   	   	      	    		$.ligerDialog.confirm('数据删除后不可恢复,你确认要删除?', function(data) {
      	   	   	          				var delIds = "";
      	   	   	          				var superMark = false;
      	   	   	          				$(grid.selected).each(function(i, item){
      	   	   	          					delIds += item['userCode']+",";
      	   	   	          					if (item['superMark'] == 'Y') {
      	   	   	          						superMark = true;
          	   	   	          				}
      	   	   	          				});
		      	   	   	          		if (superMark) {
		     								$.ligerDialog.warn('所选择的用户包含有【超级管理员】用户，不允许删除！');
		     								return;
		     							}
      	   	   	          				if(data){
      	   	   	          					$.ligerui.ligerAjax({
      	   	   	      							type: "POST",
      	   	   	      							async:  false,
      	   	   	      							url: "users!delete",
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
          		            }},
          		            { line: true },
   		               		{ text: '授权', icon: 'customers', expression: '!=1', disabled:true, click: function(item){
     		                	 var selectedRow = item.selectGrid.selected ; 
     		   	    	         $.pap.addTabItem({ 
     			   	    	         text: '用户/授权', 
     			   	    	         url: 'users!loadGrantById?id='+grid.selected[0]['userCode'],
     			   	    	         param: selectedRow 
     			   	    	      });
           		            }},
           		         	{ line: true },
           		         	{ text: '修改密码', icon: 'edit-icon', expression:'==0', disabled:true, click: function(item) {
     		                	 if( grid.selected.length > 0 ){
     		                		var _ids = "";
  	   	   	          				$(grid.selected).each(function(i, item){
  	   	   	          					_ids += item['userCode']+",";
  	   	   	          				});
     		                		$.ligerDialog.open({
             							title:'修改密码',
             							width: 380,
             							height: 160, 
             							url: 'users_batchmodifypwd.jsp',
             							onLoaded: function(param){
	     		   	    		       	   var documentF = param.contentDocument || param.document ;//兼容IE 和 FF
	     		   	    		           $('input[id="ids"]',documentF).attr('value', _ids); 
	     		   	    		        },
             							buttons: [
             				                {text:'确定',onclick: function(item, dialog) {
             				                	var data = dialog.frame.onSave();
                 				       	    	if(data!=null){
                 				       				$.ligerui.ligerAjax({
                 				       					url:"users!batchModifyPwd",
                 				       					dataType:"text",
                 				       					data: data,
                 				       					success:function(_data,textStatus){
                 				       						if(_data == 'MODIFYOK'){
                 				       							tip = $.ligerDialog.tip({ title: '提示信息', content: '密码修改成功！' });
                 				       							window.setTimeout(function(){ tip.close()
                         				       					} ,2000); 	
                 				       						}
                 				       					},
                 				       					error: function(XMLHttpRequest,textStatus){
                 				       					$.ligerDialog.error('操作出现异常');
                 				       					}
                 				       				});
                 				       				dialog.close();
                 				           		}
                 				            }},
             				                {text:'取消',onclick: function(item, dialog) {
                 				            	dialog.close();
                 				            }}
             		        			]
             		        		}); 			
     	   	   	      	    	}else {
     	   	   	      	    		$.ligerDialog.warn('请选择要修改密码的记录！')
     	   	   	      	    	}
         		            }},
                  		]
             	};

                form={
                    labelWidth: 70, 
	                fields:[
	                	{ display: "用户账号", name: "e.userCode", newline: true, type: "text"},
	                	{ display: "用户名称", name: "e.userRealName", type: "text"}
				    ]
               	}
   	       
             	var gridoption={
             		url:'users!loadByPage',
                    columns:[
                         { display: '用户账号',name:'userCode'},
                         { display: '用户名称',name:'userRealName'},
                         { display: '所属岗位',name:'post'},
                		 { display: '状态',name:'userStatus',render:function(row){
		    				 if(row.userStatus==1){return "启用"};
		    				 if(row.userStatus==2){return "停用"};
		    			 }},
                         {display: '创建日期', name:'createDate'},
                         { display: '超级管理员标识',name:'superMark',render:function(row){
		    				 if(row.superMark=='Y'){ return "是" };
		    				 if(row.superMark=='N'){ return "否" };
		    			 }}
                 	]
                }
             
              var glist=$.pap.createFormGridList({ toolbar:toolBarOption,form:form,grid:gridoption});
              grid=glist.getGrid();
        });
    	
        function f_search(){
            grid.options.data = $.extend(true, {}, CustomersData);
            grid.loadData(f_getWhere());
        }
        function f_getWhere(){
            if (!grid) return null;
            var clause = function (rowdata, rowindex)
            {
                var key = $("#txtKey").val();
                return rowdata.CustomerID.indexOf(key) > -1;
            };
            return clause; 
        }
    </script>
 
</head>
<body>
</body>
</html>
