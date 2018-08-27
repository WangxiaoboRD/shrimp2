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
   		                { text: '查看属性', click: show, icon:'view',expression:'!=1',disabled:true},
		                { line: true },
		                { text: '查看方法', click: showMethod, icon:'view',expression:'!=1',disabled:true},
		                { line: true },
		                { text: '刷新', click: refreshClick, icon:'refresh',expression:'!=1',disabled:true},
		                { line: true }
		                
              		]
          	};


           var  form={
           		 labelWidth: 45,
                 fields:[
                     { display: "编码", name: "e.bussCode",newline: false, type: "text" }
                  ]
           } 			  
	       
           var gridoption={
                  columns:[
                      { display:'编码',name:'bussCode' },
                      { display:'名称',name:'bussName' },
                      { display:'类全名',name:'fullClassName', width: 400 }
           	      ],
                  url:'bussiness_object!loadByPage'
           }
           var glist=$.pap.createFormGridList({ toolbar:toolBarOption,form:form,grid:gridoption});
           grid=glist.getGrid();

        });

    	var showMethod=function(item){
    		var selectedRows = item.selectGrid.selected;
   			$.pap.addTabItem({ 
			  	 text: '业务对象/查看方法',
			 	 url: 'method_descs_list.jsp',
			 	 param:selectedRows   			 						
 			 })

       }


    	var show=function(item){

    		var selectedRows = item.selectGrid.selected;
   			$.pap.addTabItem({ 
			  	 text: '业务对象/查看属性',
			 	 url: 'base_obj_property_show.jsp',
			 	 param:selectedRows   			 						
 			 })

       }


       var refreshClick=function(){
   	     $.ligerDialog.confirm('是否进行刷新操作?',function(data) {
                if(data){
          		   $.ligerui.ligerAjax({
       				type:"POST",
       				async: false,
       				url:"bussiness_object!refreashSingleModel",
       				data:{'e.fullClassName':grid.selected[0]['fullClassName']},
       				dataType:"text",
       				beforeSend:function(){},
       				success:function(data, textStatus){
       					if(textStatus == 'refresh'){
       						tip = $.ligerDialog.tip({ title: '提示信息', content: '刷新成功！' });
       						window.setTimeout(function(){ tip.close()} ,2000); 	
       						dialog.close();
       						
       					}
       					
       				},
       				error: function(XMLHttpRequest,textStatus){
       				    alert("错误代码执行");
       				},
       				complete: function(){ grid.loadData(true);}
       			});
               }

           });


        }


     
   </script>
 
</head>

<body>
</body>
</html>
