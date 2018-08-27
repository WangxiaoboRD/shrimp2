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
	<script type="text/javascript" src="../ligerUI/ligerUI/js/pap.js"></script>
	<script src="../ligerUI/ligerUI/js/template/ligerTemplate.js" type="text/javascript"></script>
	<script src="../ligerUI/ligerUI/js/template/ligerXFormGridList.js" type="text/javascript"></script>

    <script type="text/javascript">
    	// 表格
        var grid = null;
        $(function () {

        	   var toolBarOption = { 
   	                   items: [
	   	                  { text: '新增', icon: 'add', click: function() {
	   	                	$.ligerDialog.open({ 
               	   				title:'添加调动作业',
               	   				url: 'quartz_job_add.jsp', 
               	   				height: 320,
               	   				width: 350, 
               	   				buttons: [ 
               	   					{ text: '确定', onclick: function(item, dialog) {
               	   						var data = dialog.frame.onSave();
	                   	   		    	if(data != null){
	                   	   		    	$.ligerui.ligerAjax({
                   	   						type:"POST",
                   	   						async: false,
                   	   						url:"quartz_job!save",	
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
	   	   	              { line:true },
	   	   	              { text: '修改', icon: 'modify', expression:'!=1', disabled:true, click: function(item){
			   	   	           if(! jQuery.isEmptyObject(grid.selected) ){
		         	   	    		if(grid.selected.length > 1){
		         	   	    			$.ligerDialog.warn('请选择一条要修改的记录！');
		         	   	    		}else if(grid.selected.length ==1){ 
		         	   	    			var status = grid.selected[0]['quartzStatus']
		         	   	  		        if (status != 'OFF') {
		         	   	  		      		$.ligerDialog.warn('所选调度作业处于非停止状态，不允许修改！');
		         	   	  		      		return;
			         	   	  		    }
		         	   	    			$.ligerDialog.open({
		         							title:'修改调动作业',
		         							height: 320,
		                   	   				width: 350, 
		         							url: 'quartz_job!loadUpdateById?id='+grid.selected[0]['id'],
		         							buttons: [
		         				                { text:'确定',onclick: function(item, dialog) {
		         				                	var data = dialog.frame.onSave();
		             				       	    	if(data!=null){
		             				       				$.ligerui.ligerAjax({
		             				       					url:"quartz_job!modify",
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
		         				                { text:'取消',onclick: function(item, dialog) {
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
   	   	          				var delIds = "";
   	   	          				var flag = false;
   	   	          				$(grid.selected).each(function(i, item){
   	   	          					delIds += item['id']+",";
		   	   	          			if (item['quartzStatus'] != 'OFF') {
		   	   	          				flag = true;
		         	   	  		    }
   	   	          				});
		   	   	          		if (flag) {
	     	   	  		      		$.ligerDialog.warn('所选调度作业处于非停止状态，不允许删除！');
	     	   	  		      		return;
	         	   	  		    }
	   	   	      	    		$.ligerDialog.confirm('数据删除后不可恢复,你确认要删除?', function(data) {
	   	   	          				if(data){
	   	   	          					$.ligerui.ligerAjax({
	   	   	      							type: "POST",
	   	   	      							async:  false,
	   	   	      							url: "quartz_job!delete",
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
                	 labelWidth: 70,
                     fields:[
        	 			 { display: "作业名称", name: "e.quartzName", type: "text" },
        	 			 { display: '创建日期', name: 'e.createDate', type:"date" }
					    ]
                	}

        	 var gridoption={
                     columns:[
                        { display: '编码', name: 'id', width: 60 },
	    				{ display: '作业名称', name: 'quartzName', width: 100 },
	    				{ display: '所属组', name: 'quartzGroup.name' },
	    				{ display: '作业对象', name: 'className', width: 260 },
	    				{ display: 'cron表达式编号', name: 'cronExpress.id', hide: true },
	    				{ display: '作业规则', render: function (rowdata, rowindex, value) {
		    				var cronId = rowdata['cronExpress.id'];
		    				var h = "<a href='javascript:job_cron_add(" + rowindex + ")'>设置</a>";
		    				if (cronId) {
		    					h = "<a href='javascript:job_cron_modify(" + rowindex + ")'>设置</a>";
			    			}
	    					return h;
	    				}},
	    				{ display: '状态', name: 'quartzStatus', render: function(data){
		    				var text = data.quartzStatus;
	    					switch(data.quartzStatus){
	    						case 'ON': text = "启动"; break;
	    						case 'OFF': text = "停止"; break;
	    						case 'PAUSE': text = "暂停"; break;
	    					}
	    					return text;
	    				}},
	    				{ display: '操作', width: 150, render: function (rowdata, rowindex, value) {

	    					var h = "<a href='javascript:job_on(" + rowindex + ")'>启动</a> &nbsp;&nbsp;";
 	                        h += "<a href='javascript:job_pause(" + rowindex + ")'>暂停</a> &nbsp;&nbsp;"; 
 	                        h += "<a href='javascript:job_resume(" + rowindex + ")'>恢复</a> &nbsp;&nbsp;";
 	                        h += "<a href='javascript:job_off(" + rowindex + ")'>停止</a> &nbsp;&nbsp;";

	    	                return h;
			    		}},
	    				{ display: '描述', name: 'quartzDesc'}
              		],
                    url:'quartz_job!loadByPage'
             }
          
           var glist = $.pap.createFormGridList({ toolbar:toolBarOption,form:form,grid:gridoption});

           grid = glist.getGrid();
        });

        // 规则表达式添加
        function job_cron_add(rowindex) {
            var rowdata = grid.getRow(rowindex);
            $.ligerDialog.open({ 
     			title:'作业规则设置',
     			url: 'quartz_cron_add.jsp?id=' + rowdata.id,
     			height: 500,
     			width: 800, 
     			param: rowdata,
     			buttons: [ 
     				{ text: '确定', onclick:  function (item, dialog) { 
     					var data = dialog.frame.onSave();
     					data = data + "&id=" + rowdata.id;
     					$.ligerui.ligerAjax({
							url: 'quartz_cron!save',
							dataType: "text",
							data: data,
							success:function(result, textStatus){
								if(result != null && result !=""){
	    							tip = $.ligerDialog.tip({ title: '提示信息', content: '作业规则设置成功！' });
	    							window.setTimeout(function(){ tip.close()} ,2000); 	
	    						}else {
	    							$.ligerDialog.success("作业规则设置失败！");
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

        // 规则表达式修改
        function job_cron_modify(rowindex) {
            var rowdata = grid.getRow(rowindex);
            if(rowdata.quartzStatus == 'OFF'){
	            $.ligerDialog.open({ 
	     			title:'作业规则设置',
	     			height: 500,
	     			width: 800, 
	     			url: 'quartz_cron!loadUpdateById?id='+ rowdata['cronExpress.id'],
	     			param: rowdata,
	     			buttons: [ 
	     				{ text: '确定', onclick:  function (item, dialog) { 
	     					var data = dialog.frame.onSave();
	     					data = data + "&id=" + rowdata.id;
	     					$.ligerui.ligerAjax({
								url: 'quartz_cron!modifyAll',
								dataType: "text",
								data: data,
								success:function(data, textStatus){
		    						if(data == 'MODIFYOK'){
		    							tip = $.ligerDialog.tip({ title: '提示信息', content: '作业规则设置成功！' });
		    							window.setTimeout(function(){ tip.close()} ,2000); 	
		    						}else {
		    							$.ligerDialog.success("作业规则设置失败！");
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
 			}else{
 				$.ligerDialog.error("运行状态不能设置");
 			}
        }
        
       // 启动
       var job_on = function(rowindex) {
        	var rowdata = grid.getRow(rowindex);
        	if(rowdata.quartzStatus == 'OFF'){
	        	$.ligerui.ligerAjax({
					type: "POST",
					async:  false,
					url: "quartz_job!run",
					data: {"e.id": rowdata.id },
					dataType: "text",
					success: function(result, status){
						if(result != ""){
							tip = $.ligerDialog.tip({ title: '提示信息', content: '作业启用！' });
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
		   	}else{
 				$.ligerDialog.error("作业已经运行");
 			}
        }

     	// 停止
        var job_off = function(rowindex) {
        	var rowdata = grid.getRow(rowindex);
        	if(rowdata.quartzStatus == 'ON'){
	        	$.ligerui.ligerAjax({
					type: "POST",
					async:  false,
					url: "quartz_job!stop",
					data: {"e.id": rowdata.id },
					dataType: "text",
					success: function(result, status){
						if(result != ""){
							tip = $.ligerDialog.tip({ title: '提示信息', content: '作业停止！' });
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
		   	}else{
 				$.ligerDialog.error("作业已经停止");
 			}
        }

       // 暂停
       var job_pause = function(rowindex) {
        	var rowdata = grid.getRow(rowindex);
        	if(rowdata.quartzStatus == 'ON'){
	        	$.ligerui.ligerAjax({
					type: "POST",
					async:  false,
					url: "quartz_job!pauseTrigger",
					data: { "e.id": rowdata.id },
					dataType: "text",
					success: function(result, status){
						if(result != ""){
							tip = $.ligerDialog.tip({ title: '提示信息', content: '作业暂停！' });
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
		   	}else{
 				$.ligerDialog.error("作业已经停止");
 			}
        }
        
        //恢复
        var job_resume = function(rowindex) {
        	var rowdata = grid.getRow(rowindex);
        	if(rowdata.quartzStatus == 'PAUSE'){
	        	$.ligerui.ligerAjax({
					type: "POST",
					async:  false,
					url: "quartz_job!resumeTrigger",
					data: { "e.id": rowdata.id },
					dataType: "text",
					success: function(result, status){
						if(result != ""){
							tip = $.ligerDialog.tip({ title: '提示信息', content: '恢复运行！' });
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
		   	}else{
 				$.ligerDialog.error("作业非暂停状态");
 			}
        }
    </script>
</head>
<body>
</body>
</html>
