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
	  	
	  var grid;
	  $(function(){
   
	    var currentPage = $.pap.getOpenPage(window);
	  	var currentParam = currentPage.getParam("param")[0];	        
        var formoption={
                labelWidth: 75,
                  fields:[
        	              { display: "表名称", name:"e.tabCode",type:"text",attr:{value:'${e.tabCode}'},options:{readonly:'readonly'}},
        	              { display: "表描述", name:"e.tabName",type:"text",attr:{value:'${e.tabName}'},options:{readonly:'readonly'}},
        	              { display: "表类型", name:"e.tabType",type:"select", newline:true, validate:{required:true}, options:{               
      				       	data: [
      	  					   { text: '系统表', id: 'S' },
      	  		               { text: '业务表', id: 'B' }
      	  			        ],
      	  			        selectBoxHeight: 80,
      	  			        value: '${e.tabType}',
      	  			        readonly: true              
      	  	              }},
        	              { display: "版本", name:"e.tabVer",type:"text",attr:{value:'${e.tabVer}'},options:{readonly:'readonly'}},
        	              { display: "激活状态",name:"e.isActiveable",type:"text",options:{value: '${e.isActiveable}',readonly:'readonly',render: function(v){
      		  	        	var text = v;
    						switch(v){
    							case 'Y': text = "已激活"; break;
    							case 'N': text = "未激活"; break;
    						}
    						return text;
    				  }}}
				    ]
         }

			  
	       
       	var gridoption={
        		  url:'base_table_column!loadByEntity',
        		  parms:{'e.baseTable.tabCode':currentParam['tabCode']},
                  columns:[
        				 { display:'主键',name:'isPk',render: function (item){
                                if (item.isPk =='N')
                                     return '否';
                                 return '是';
                         }},                    
                         { display:'字段名',name:'fdcode'},                   
                        { display:'字段描述',name:'descs'},
         				{ display:'业务元素',name:'bussinessEle.ecode'},
         				{ display:'字段类型',name:'dataType'},
         				{ display:'长度',name:'len'},
         				{ display:'小数位数',name:'decimalLen'},
         				{ display:'默认值',name:'defaultValue'},
         				{ display:'关键属性',name:'isImportantKey',render: function (item){
                             if (item.isImportantKey =='N')
                                 return '否';
                             return '是';
                         }},     
         				{ display:'外键表',name:'refTab'},
         				{ display:'外键字段',name:'refKey'}
                  ],
                  usePager: true,
                  checkbox: false
                  
          }

       
        var glist=$.pap.createEFormGridShow({form:formoption,grid:gridoption});

        grid=glist.getGrid();

              
	  	});


 	 
    </script>
    <style type="text/css">
         body{ font-size:12px;}
        .l-table-edit {}
        .l-table-edit-td{ padding:4px;}
    </style>
</head>
	<body style="padding: 10px" >

	</body>
</html>
