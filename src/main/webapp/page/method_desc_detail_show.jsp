<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    
    <title>查看</title>
	<link href="../ligerUI/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
	<link href="../ligerUI/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />	
    <script src="../ligerUI/jquery/jquery-1.6.4.js" type="text/javascript"></script>
	<script src="../ligerUI/jquery-validation/jquery.form.js" type="text/javascript"></script> 
	<script src="../ligerUI/jquery-validation/jquery.validate.js" type="text/javascript"></script> 
    <script src="../ligerUI/jquery-validation/jquery.metadata.js" type="text/javascript"></script>
    <script src="../ligerUI/jquery-validation/messages_cn.js" type="text/javascript"></script>    
    <script src="../ligerUI/ligerUI/js/ligerui.all.js" type="text/javascript"></script>       
	<script src="../ligerUI/ligerUI/js/template/ligerTemplate.js" type="text/javascript"></script>
	<script src="../ligerUI/ligerUI/js/template/ligerXEFormGridShow.js" type="text/javascript"></script>
	<script type="text/javascript" src="../ligerUI/ligerUI/js/pap.js"></script>

    <script type="text/javascript">

	  var form;
	  var grid;
	  $(function(){
	  		 
	  	form= $("form").ligerForm({	  		
	  		inputWidth: 150, labelWidth: 70, space:20,
             fields: [  
             
	              { display: "方法名", name:"e.methodName",type:"text",options:{readonly:'readonly'}},
	              { display: "描述", name:"e.descs",type:"text",options:{readonly:'readonly'}}
	              
             ]
	       });


        

       grid = $("#modify").ligerGrid({
            columns:[                  
                    { display:'参数类型',name:'paramType'},                   
    				{ display:'说明',name:'paramdesc'}
 			],
             selectRowButtonOnly:true, 
             pageSizeOptions:[5,10,15,20],
             pageParmName:'pageBean.pageNo', //当前页数
             pagesizeParmName:'pageBean.pageSize',//每页条数
             sortnameParmName:'pageBean.sortName',//排序字段
             sortorderParmName:'pageBean.sortorder',//排序方向
             usePager: true,        //是否分页                
             alternatingRow: true, //奇数行 和偶数行效果
             enabledEdit: true,
             width: '99%',
             height:'98%', 
             checkbox: false,
             rownumbers:true
             
         });              
	  	});


 	 
    </script>
    <style type="text/css">
         body{ font-size:12px;}
        .l-table-edit {}
        .l-table-edit-td{ padding:4px;}
    </style>
</head>
	<body style="padding: 10px" >
	<form name="form1" method="post" id="form1">
	</form>
	
	  	<div id="modify" style="margin:0; padding:0"></div>	
	</body>
</html>
