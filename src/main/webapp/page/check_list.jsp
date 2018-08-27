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
	   	   	              { text: '审核设置', icon: 'settings', click: function(){
                   				$.ligerDialog.open({ 
                   	   				title:'审核设置',
                   	   				url: 'check_add.jsp', 
                   	   				height: 300,
                   	   				width: 400, 
                   	   				buttons: [ 
                   	   					{ text: '确定', onclick: function(item, dialog) {
                   	   						var data = dialog.frame.onSave();
		                   	   		    	if(data!=null){
		                   	   		    	$.ligerui.ligerAjax({
		                   	   						type:"POST",
		                   	   						async: false,
		                   	   						url:"check!save",	
		                   	   						data:data,
		                   	   						dataType:"text",
		                   	   						beforeSend:function(){},
		                   	   						success:function(result, textStatus){
		                   	   							if(result != null && result !=""){
		                   	   							 tip = $.ligerDialog.tip({ title: '提示信息', content: '设置成功！' });
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
                 							title:'修改审核设置',
                 							width:400,
                 							height:300, 
                 							url: 'check!loadUpdateById?id='+grid.selected[0]['code'],
                 							buttons: [
                 				                {text:'确定',onclick: function(item, dialog) {
                 				                	var data = dialog.frame.onSave();
	                 				       	    	if(data!=null){
	                 				       				$.ligerui.ligerAjax({
	                 				       					url:"check!modify",
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
	   	   	              { text: '删除', icon: 'delete', expression:'==0', disabled:true, click: function(){
	   	   	            	  if( grid.selected.length > 0 ){
	   	   	      	    		$.ligerDialog.confirm('数据删除后不可恢复,你确认要删除?', function(data) {
	   	   	          				var delIds = "";
	   	   	          				$(grid.selected).each(function(i, item){
	   	   	          					delIds += item['code']+",";
	   	   	          				});
	   	   	          				if(data){
	   	   	          					$.ligerui.ligerAjax({
	   	   	      							type: "POST",
	   	   	      							async:  false,
	   	   	      							url: "check!delete",
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
                         { display: "单据名", name: "e.bussName", type: "text" },
                         { display: "创建日期", name:"e.createDate",type:"date" } 
					    ]
                	}

        	 var gridoption={
                     columns:[
                        { display: '编码', name: 'code',hide:true},
	    				{ display: '单据名称', name: 'bussName',width:80,frozen: true},
	    				{ display: '单据编号', name: 'objName',frozen: true,width:80},
	    				{ display: '审核级别', name: 'objLevel',frozen: true,width:70,render: function(data){
		    				var text;
	    					switch(data.objLevel){
	    						case '1': text = "一级审核"; break;
	    						case '2': text = "二及审核"; break;
	    						case '3': text = "三级审核"; break;
	    						case '4': text = "四级审核"; break;
	    						case '5': text = "五级审核"; break;
	    						case '6': text = "六级审核"; break;
	    						case '7': text = "七级审核"; break;
	    						case '8': text = "八级审核"; break;
	    					}
	    					return text;
	    				}},
	    				/**
	    				{ display: '一级审核', name: 'oneCheck',width:70,render:function(data){
 		    				if(data.oneCheck=='Y')
 		    					return "&nbsp;<img src='../ligerUI/ligerUI/skins/icons/flag1.gif'/>&nbsp;";
 		    				else if(data.oneCheck=='N')
 		    					return "&nbsp;<img src='../ligerUI/ligerUI/skins/icons/flag2.gif'/>&nbsp;";
	    				}},
	    				{ display: '审核人', name: 'oneCheckUser',width:90},
	    				{ display: '审核日期', name: 'oneCheckDate',width:90},
	    				
	    				{ display: '二级审核', name: 'twoCheck',width:70,render:function(data){
 		    				if(data.twoCheck=='Y')
 		    					return "&nbsp;<img src='../ligerUI/ligerUI/skins/icons/flag1.gif'/>&nbsp;";
 		    				else if(data.oneCheck=='N')
 		    					return "&nbsp;<img src='../ligerUI/ligerUI/skins/icons/flag2.gif'/>&nbsp;";
	    				}},
	    				{ display: '审核人', name: 'twoCheckUser',width:90},
	    				{ display: '审核日期', name: 'twoCheckDate',width:90},
	    				
	    				{ display: '三级审核', name: 'threeCheck',width:70,render:function(data){
 		    				if(data.threeCheck=='Y')
 		    					return "&nbsp;<img src='../ligerUI/ligerUI/skins/icons/flag1.gif'/>&nbsp;";
 		    				else if(data.oneCheck=='N')
 		    					return "&nbsp;<img src='../ligerUI/ligerUI/skins/icons/flag2.gif'/>&nbsp;";
	    				}},
	    				{ display: '审核人', name: 'threeCheckUser',width:90},
	    				{ display: '审核日期', name: 'threeCheckDate',width:90},
	    				
	    				{ display: '四级审核', name: 'fourCheck',width:70,render:function(data){
 		    				if(data.fourCheck=='Y')
 		    					return "&nbsp;<img src='../ligerUI/ligerUI/skins/icons/flag1.gif'/>&nbsp;";
 		    				else if(data.oneCheck=='N')
 		    					return "&nbsp;<img src='../ligerUI/ligerUI/skins/icons/flag2.gif'/>&nbsp;";
	    				}},
	    				{ display: '审核人', name: 'fourCheckUser',width:90},
	    				{ display: '审核日期', name: 'fourCheckDate',width:90},
	    				
	    				{ display: '五级审核', name: 'fiveCheck',width:70,render:function(data){
 		    				if(data.fiveCheck=='Y')
 		    					return "&nbsp;<img src='../ligerUI/ligerUI/skins/icons/flag1.gif'/>&nbsp;";
 		    				else if(data.oneCheck=='N')
 		    					return "&nbsp;<img src='../ligerUI/ligerUI/skins/icons/flag2.gif'/>&nbsp;";
	    				}},
	    				{ display: '审核人', name: 'fiveCheckUser',width:90},
	    				{ display: '审核日期', name: 'fiveCheckDate',width:90},
	    				
	    				{ display: '六级审核', name: 'sixCheck',width:70,render:function(data){
 		    				if(data.sixCheck=='Y')
 		    					return "&nbsp;<img src='../ligerUI/ligerUI/skins/icons/flag1.gif'/>&nbsp;";
 		    				else if(data.oneCheck=='N')
 		    					return "&nbsp;<img src='../ligerUI/ligerUI/skins/icons/flag2.gif'/>&nbsp;";
	    				}},
	    				{ display: '审核人', name: 'sixCheckUser',width:90},
	    				{ display: '审核日期', name: 'sixCheckDate',width:90},
	    				
	    				{ display: '七级审核', name: 'sevenCheck',width:70,render:function(data){
 		    				if(data.sevenCheck=='Y')
 		    					return "&nbsp;<img src='../ligerUI/ligerUI/skins/icons/flag1.gif'/>&nbsp;";
 		    				else if(data.oneCheck=='N')
 		    					return "&nbsp;<img src='../ligerUI/ligerUI/skins/icons/flag2.gif'/>&nbsp;";
	    				}},
	    				{ display: '审核人', name: 'sevenCheckUser',width:90},
	    				{ display: '审核日期', name: 'sevenCheckDate',width:90},
	    				
	    				{ display: '八级审核', name: 'eightCheck',width:70,render:function(data){
 		    				if(data.eightCheck=='Y')
 		    					return "&nbsp;<img src='../ligerUI/ligerUI/skins/icons/flag1.gif'/>&nbsp;";
 		    				else if(data.oneCheck=='N')
 		    					return "&nbsp;<img src='../ligerUI/ligerUI/skins/icons/flag2.gif'/>&nbsp;";
	    				}},
	    				{ display: '审核人', name: 'eightCheckUser',width:90},
	    				{ display: '审核日期', name: 'eightCheckDate',width:90},*/
	    				
	    				{ display: '创建时间', name: 'createDate',width:90},
	    				{ display: '创建人', name: 'createUser',width:90}
              		],
                    url:'check!loadByPage'
             }
          
           var glist = $.pap.createFormGridList({ toolbar:toolBarOption,form:form,grid:gridoption});
           grid = glist.getGrid();
        });
    </script>
</head>
<body>
</body>
</html>
