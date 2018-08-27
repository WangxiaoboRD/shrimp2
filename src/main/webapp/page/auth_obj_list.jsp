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
			   // 工具条
        	   var toolBarOption = { 
   	                   items: [
	   	                  { text: '新增', icon: 'add', click: function() {
	   	   	            	  $.pap.addTabItem({ 
	   	   	      			  	 text: '权限对象/新增',
	   	   	      			 	 url: 'auth_obj_add.jsp'
	   	   	       			  });
	   	   	              }},
	   	   	              { line:true },
	   	   	              { text: '修改', icon: 'modify', expression:'!=1', disabled:true, click: function(item){
	   	   	           		 var selectedRow = item.selectGrid.selected; 
		   	   	           	 if (selectedRow.length == 0) {
	  	   	           			$.ligerDialog.warn('请选择要修改的记录！');
	  	   	           			return;
			   	   	         }
		   	    	         $.pap.addTabItem({ 
			   	    	         text: '权限对象/修改', 
			   	    	         url: 'auth_obj!loadUpdateById?id='+grid.selected[0]['code'],
			   	    	         param:selectedRow 
			   	    	      });
						
	   	   			      }},
	   	   	              { line:true },
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
	   	   	      							url: "auth_obj!delete",
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
	   	   	              { line:true },
	   	   	              { text: '查看', icon: 'view', expression:'!=1', disabled:true, click: function(item){
	   	   	           		 	var selectedRow = item.selectGrid.selected ; 
			   	   	           	if (selectedRow.length == 0) {
		   	   	           			$.ligerDialog.warn('请选择要查看的记录！');
		   	   	           			return;
				   	   	         }
		   	    				$.pap.addTabItem({ 
		   	   	    				text: '权限对象/查看', 
		   	   	    				url: 'auth_obj!loadDetailById?id='+grid.selected[0]['code'],
		   	   	    				param: selectedRow 
		   	   	    			});
	   	   			      }}
                   		]
             	};

        	 form = {
                 labelWidth: 100,
                 fields:[
                     { display: "权限对象编码", name: "e.code", type: "text" } ,
    	 			 { display: "权限对象名称", name: "e.name", type: "text" },
   	 				 { display: "权限对象类型", name: "e.type", type: "select", options:{
    	 				  keySupport:true,
  	                	  data: [
   	                	    { text: '---', id: '' },
   	                       	{ text: '功能权限', id: 'FUNCTION' },
   	               	  		{ text: '数据权限', id: 'DATA' },
   		                    { text: '业务权限', id: 'BUSINESS' }
   		                 ]
    	               }}
	    			]
            	}

        	 var gridoption={
                     columns:[
                        { display: '权限对象编码',name: 'code' },
	    				{ display: '权限对象名称',name: 'name' },
	    				{ display: '权限对象类型',name: 'type', render: function(data){
		    				var text = data.type;
	    					switch(data.type){
	    						case 'FUNCTION': text = "功能权限"; break;
	    						case 'DATA': text = "数据权限"; break;
	    						case 'BUSINESS': text = "业务权限"; break;
	    					}
	    					return text;
	    				}}
              		],
                    url:'auth_obj!loadByPage'
             }
          
           var glist=$.pap.createFormGridList({ toolbar:toolBarOption,form:form,grid:gridoption});

           grid=glist.getGrid();
        });

        // 获得选中数据行
        function f_select(){
        	return grid.getSelectedRows();
        }
       
    </script>
</head>
<body>
</body>
</html>
