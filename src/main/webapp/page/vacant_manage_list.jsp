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
	   	   	              { text: '删除', icon: 'delete', expression:'==0', disabled:true, click: function(){
	   	   	            	  if( grid.selected.length > 0 ){
   	   	          				 var delIds = "";
	   	   	          			 var status = false;
	   	   	          			 $(grid.selected).each(function(i, item){
	   	   	          				delIds += item['id']+",";
	   	   	          				if (item['status'] != 'N') {
	   	   	          					status = true;
		   	   	          				return false;
		   	   	          			}
	   	   	          			 });
		   	   	          		if (status) {
									$.ligerDialog.warn('所选空号对象已启用，不允许删除！');
									return;
								}
	   	   	      	    		$.ligerDialog.confirm('数据删除后不可恢复,你确认要删除?', function(data) {
	   	   	          				if(data){
	   	   	          					$.ligerui.ligerAjax({
	   	   	      							type: "POST",
	   	   	      							async:  false,
	   	   	      							url: "vacant_manage!delete",
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
                	 labelWidth: 90,
                     fields:[
                         { display: "业务对象编码", name: "e.bussObj", type: "text"},
        	 			 { display: "业务对象名称", name: "e.bussName", type: "text" }
				    ]
               	}

        	 var gridoption={
                     columns:[
						{ display: "编号", name: "id", hide: true },
                     	{ display: "业务对象编码", name: "bussObj" },
        	 			{ display: "业务对象名称", name: "bussName" },
        	 			{ display: '规则编号', name: 'vacantRule.id', hide: true },
        	 			{ display: '空号号码数', name: 'vacantRule.numberNum' },
	    				{ display: '空号规则', render: function (rowdata, rowindex, value) {
		    				var cronId = rowdata['vacantRule.id'];
		    				var h = "<a href='javascript:rule_add(" + rowindex + ")'>规则设置</a>";
		    				if (cronId) {
		    					h = "<a href='javascript:rule_modify(" + rowindex + ")'>规则设置</a>";
			    			}
	    					return h;
	    				}},
        	 			{ display: '状态',name: 'status',render: function(data){
		    				var text = data.status;
	    					switch(data.status){
		    					case 'N': text = "停止"; break;
	    						case 'Y': text = "启动"; break;
	    					}
	    					return text;
	    				}},
	    				{ display: '操作', render: function (rowdata, rowindex, value) {
	    					var h = "<a href='javascript:job_on(" + rowindex + ")'>启动</a> &nbsp;&nbsp;&nbsp;";
 	                        h += "<a href='javascript:job_off(" + rowindex + ")'>停止</a> ";
	    	                return h;
			    		}}
              		],
                    url:'vacant_manage!loadByPage',
                    exportBtn: true,
			        exportUrl: 'vacant_manage!exportFile'
             }
           var glist = $.pap.createFormGridList({ toolbar:toolBarOption,form:form,grid:gridoption});
           grid = glist.getGrid();
        });

        // 启动
        var job_on = function(rowindex) {
         	var rowdata = grid.getRow(rowindex);
         	if (!rowdata.vacantRule) {
         		$.ligerDialog.error("空号作业生成规则未设置，不允许启动！");
         		return;
            }
         	if(rowdata.status == 'N'){
 	        	$.ligerui.ligerAjax({
 					type: "POST",
 					async:  false,
 					url: "vacant_manage!start",
 					data: {"e.id": rowdata.id },
 					dataType: "text",
 					success: function(result, status){
 						if(result != ""){
 							tip = $.ligerDialog.tip({ title: '提示信息', content: '空号作业启动！' });
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
  				$.ligerDialog.error("空号作业已经启动！");
  			}
         }

     	// 停止
        var job_off = function(rowindex) {
         	var rowdata = grid.getRow(rowindex);
         	if(rowdata.status == 'Y'){
 	        	$.ligerui.ligerAjax({
 					type: "POST",
 					async:  false,
 					url: "vacant_manage!stop",
 					data: {"e.id": rowdata.id },
 					dataType: "text",
 					success: function(result, status){
 						if(result != ""){
 							tip = $.ligerDialog.tip({ title: '提示信息', content: '空号作业停止！' });
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
  				$.ligerDialog.error("空号作业已经停止！");
  			}
         }
        
      // 规则表达式添加
        function rule_add(rowindex) {
            var rowdata = grid.getRow(rowindex);
            $.ligerDialog.open({ 
     			title:'空号规则设置',
     			url: 'vacant_rule_add.jsp?id=' + rowdata.id + '&bussObj=' + rowdata.bussObj + '&bussName=' + rowdata.bussName,
     			height: 460,
     			width: 500, 
     			param: rowdata,
     			buttons: [ 
     				{ text: '确定', onclick:  function (item, dialog) { 
     					var data = dialog.frame.onSave();
     					data = data + "&id=" + rowdata.id;
     					$.ligerui.ligerAjax({
							url: 'vacant_rule!save',
							dataType: "text",
							data: data,
							success:function(result, textStatus){
								if(result != null && result !=""){
	    							tip = $.ligerDialog.tip({ title: '提示信息', content: '规则设置成功！' });
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
        function rule_modify(rowindex) {
            var rowdata = grid.getRow(rowindex);
            if(rowdata.status == 'N'){
	            $.ligerDialog.open({ 
	     			title:'空号规则设置',
	     			height: 460,
	     			width: 500, 
	     			url: 'vacant_rule!loadUpdateById?id='+ rowdata['vacantRule.id'],
	     			param: rowdata,
	     			buttons: [ 
	     				{ text: '确定', onclick:  function (item, dialog) { 
	     					var data = dialog.frame.onSave();
	     					data = data + "&id=" + rowdata.id;
	     					$.ligerui.ligerAjax({
								url: 'vacant_rule!modifyAll',
								dataType: "text",
								data: data,
								success:function(data, textStatus){
		    						if(data == 'MODIFYOK'){
		    							tip = $.ligerDialog.tip({ title: '提示信息', content: '规则设置成功！' });
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
 				$.ligerDialog.error("启用状态不能设置");
 			}
        }
    </script>
</head>
<body>
</body>
</html>
