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
   		        	{ text: '设置号码对象', click: settingClick, icon:'settings',expression:'!=1',disabled:true}
              	]
           };
           var  form={
           		 inputWidth: 130, 
           		 labelWidth: 60, 
           		 space:20,
                 fields:[
                     { display: "业务编码", name: "e.bussCode",newline: false, type: "text"},
                     { display: "业务名称", name: "e.bussName",newline: false, type: "text"},
                     { display: "创建日期", name: "e.createDate",newline: false, type: "date" }
                  ]
           } 			  
           var gridoption={
          		  url:'bussiness_object!loadByPage',
                  columns:[
                           { display:'编码',name:'bussCode' },
                           { display:'名称',name:'bussName' },
                           { display:'类全名',name:'fullClassName',width:'400'},
                           { display:'创建日期',name:'createDate',width:'150'}
           	      ]
           }
           var page=$.pap.createFormGridList({ toolbar:toolBarOption,form:form,grid:gridoption});
           //var glist=$.pap.createFormGridList({form:form,grid:gridoption});
           grid=page.getGrid();

        });
        
        //使用页签方法打开
    	var settingClick = function(item){
			var selectedRows = item.selectGrid.selected ; //.length ;    		
   	    	if(! jQuery.isEmptyObject(selectedRows) ){
   	    		if(selectedRows.length > 1){
   	    			$.ligerDialog.warn('请选择一条要修改的记录！');
   	    		}else if(selectedRows.length ==1){
   	    			
   	    			var _bussCode = grid.selected[0]['bussCode'];
   	    			$.ligerui.ligerAjax({
							url:'number_config!selectBussObject',
							dataType:'text',
							data:{'id':_bussCode},
							success:function(_data,textStatus){
								if(_data == 'false'){
				   	    			$.pap.addTabItem({ text: '分配号码对象', url: 'number_setting_list.jsp',param:selectedRows });
								}else if(_data == 'true'){
									$.ligerDialog.warn("已绑定号码对象");
								}
							},
							error: function(XMLHttpRequest,textStatus){
							    $.ligerDialog.warn("错误代码执行");
							},
							complete: function(){}
					});
					
					//$.pap.addTabItem({ text: '分配号码对象', url: 'number_setting_list.jsp',param:selectedRows });
   	    		}
   	    	}else{
   	    		$.ligerDialog.warn('至少选择一条要修改的记录！');
   	    	}
    	}; 

   </script>
 
</head>

<body>
</body>
</html>
