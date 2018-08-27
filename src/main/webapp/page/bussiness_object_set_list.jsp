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
           var  form={
                 labelWidth: 45,
                 fields:[
                     { display: "编码", name: "e.bussCode",newline: false, type: "text" }
                  ]
           } 			  
	       
           var gridoption={
          		  title:'业务对象',
                  columns:[
                           { display:'编码',name:'bussCode' },
                           { display:'名称',name:'bussName' },
                           { display:'类全名',name:'fullClassName', width: 300 }
           	      ],
                  url:'bussiness_object!loadByCont'
           }
           var glist=$.pap.createFormGridList({form:form,grid:gridoption});
           grid=glist.getGrid();

        });

    	var onSave = function(){
    		var delIds="";
    		if( grid.selected.length > 0 ){
				$(grid.selected).each(function(i,item){
					delIds+=item['bussCode']+",";
				});
		    }
			return delIds;
	  		/*console.log($('#form1').formSerialize());
			return $('#form1').formSerialize();*/
		}  

   </script>
 
</head>

<body>
</body>
</html>
