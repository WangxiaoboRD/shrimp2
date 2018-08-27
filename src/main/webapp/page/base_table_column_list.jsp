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
	  	 $("form").ligerForm({
	  		 inputWidth: 150, labelWidth: 60, space:20,
             fields: [
                 { display: "主键", name: "e.isPk",newline: false, type:"hidden" ,options:{
                	 value:'Y'
                  }}, 
                 { display: "表名", name: "e.tbaseTable.tabCode",newline: false, type: "text" }
                   
               ],
              buttons:[
                   {text:'查询',click:function(item){
                            // item.options.ligerform.validaterForm()	
 							var arra = $("#form1").formToArray();
 							//console.dir(arra)
 							grid.setParms(arra);
			           		grid.loadData(true);
					}},
                   {text:'重置',click:function(item){
						  $("#form1").resetForm();
					}}										
				  ]               
	  		 });
         	
        	
            grid = $("#maingrid4").ligerGrid({
               columns:[
                    { display:'表名',name:'baseTable.tabCode' },
                    { display:'主键',name:'fdcode' }
    			],
                url:'base_table_column!loadByEntity',
                selectRowButtonOnly:true, 
                pageSizeOptions:[5,10,15,20],
                pageParmName:'pageBean.pageNo', //当前页数
                pagesizeParmName:'pageBean.pageSize',//每页条数
                sortnameParmName:'pageBean.sortName',//排序字段
                sortorderParmName:'pageBean.sortorder',//排序方向
                enableSort:true,
                usePager: true,        //是否分页
                delayLoad : true,
                alternatingRow: true, //奇数行 和偶数行效果
                width: '99%',
                height:'98%', 
                checkbox: true,
                rownumbers:true,
                    toolbar: {
                  }
                
            });
        });



    	function save(){
            return grid.getSelecteds();
        }
        


     
   </script>
 
</head>

<body>
	<div class="search" style="margin-bottom: 10px;">
		<form id="form1" name="form1"></form>
		<div class="l-clear"></div>
	</div>
  	<div id="maingrid4" style="margin:0; padding:0"></div>
</body>
</html>
