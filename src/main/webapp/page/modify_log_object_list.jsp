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
    <script src="../ligerUI/ligerUI/js/ligerui.all.js" type="text/javascript"></script>       
	<script src="../ligerUI/ligerUI/js/template/ligerTemplate.js" type="text/javascript"></script>
	<script src="../ligerUI/ligerUI/js/template/ligerXFormGridList.js" type="text/javascript"></script>
	<script type="text/javascript" src="../ligerUI/ligerUI/js/pap.js"></script>
	
    <script type="text/javascript">
        var grid = null;
        $(function () {
            var toolBarOption = { 
             	items: [
					{ text: '设置对象', click: addClick ,icon:'add'},
					{ line: true },
					{ text: '删除', click: deleteClick ,icon:'delete'}
          		]
          	 };

           var  form={
           		 labelWidth: 85,
                 fields:[
                     { display: "业务对象编码", name: "e.bussCode", type: "text" },
                     { display: "创建日期", name: "e.bussName", type: "text" }
                  ]
           } 			  
	       
           var gridoption={
                  columns:[
                  	  { display: '编码', name: 'id' },
                      { display: '业务对象编码', name: 'bussObj.bussCode' },
                      { display: '业务对象名称', name: 'bussObj.bussName' },
                      { display: '类全名', name: 'bussObj.fullClassName', width: 300 },
                      { display: '创建日期', name: 'createDate', width: 200 }
      	      	  ],
                  url: 'modify_log_object!loadByPage'
           }
           var glist=$.pap.createFormGridList({ toolbar:toolBarOption,form:form,grid:gridoption});
           grid=glist.getGrid();

        });
        
        //添加
        var addClick = function(){
        	$.ligerDialog.open({ 
				title:'业务对象',
				url: 'bussiness_object_set_list.jsp', 
				height: 500,
				width: 650, 
				onLoaded:function(param){
  		      	   var documentF = param.contentDocument || param.document ;//兼容IE 和 FF
  		           $('div.toolbar-pap',documentF).hide();
  		        },
				buttons: [ 
					{ text: '确定', onclick: function(item, dialog) {
						var data = dialog.frame.onSave();
	 		    		if(data!=null){
	 	    				$.ajax({
		 						type:"POST",
		 						async: false,
		 						url:"modify_log_object!saveAll",	
		 						data:{ids:data},
		 						dataType:"text",
		 						beforeSend:function(){},
		 						success:function(result, textStatus){
		 							if(result != null && result !=""){
		 								tip = $.ligerDialog.tip({ title: '提示信息', content: '操作成功！' });
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
	   				}}] 
				});
        }
        
        //删除
        var deleteClick = function(){
        	 if( grid.selected.length > 0 ){
   	    		$.ligerDialog.confirm('数据删除后不可恢复,你确认要删除?', function(data) {
       				var delIds = "";
       				$(grid.selected).each(function(i, item){
       					delIds += item['id']+",";
       				});
       				if(data){
       					$.ligerui.ligerAjax({
   							type: "POST",
   							async:  false,
   							url: "modify_log_object!delete",
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
        }
        
   </script>
</head>
<body>
</body>
</html>
