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
	<script src="../ligerUI/ligerUI/js/template/ligerXEFormGridShow.js" type="text/javascript"></script>
	<script type="text/javascript" src="../ligerUI/ligerUI/js/pap.js"></script>

    <script type="text/javascript">
        var grid = null;
        $(function () {

		    var currentPage = $.pap.getOpenPage(window);
		  	var currentParam = currentPage.getParam("param")[0];	
       	
            var toolBarOption = { 
	                items: [
			            { text: '查看明细', click: show, icon:'view'}
             		]
          	};


           var  form={          		 
                 fields:[
                      { display: "业务对象", name: "e.bussinessObject.bussCode",newline: false, type: "text",options:{readonly:'readonly',value:currentParam['bussCode']} }
                  ]
           } 			  
	       
           var gridoption={
          		  title:'业务对象',
                  columns:[
                       { display:'id',name:'id' ,hide:true},
                       { display:'方法名',name:'methodName' },
                       { display:'所在类',name:'classplace' },
       			       { display:'描述',name:'descs'},
                       { display:'参数列表',name:'paramlist'}
           	      ],
           	      delayLoad : false,
           	      usePager: true,
                  url:'method_desc!loadByEntity?e.bussinessObject.bussCode='+currentParam['bussCode']
           }
           var glist=$.pap.createEFormGridShow({ toolbar:toolBarOption,form:form,grid:gridoption});
           grid=glist.getGrid();
           
        });





        

    	var show=function(item){
   	    	if(! jQuery.isEmptyObject(grid.selected) ){
   	    		if(grid.selected.length > 1){
   	    			$.ligerDialog.warn('请选择一条要查看的记录！');
   	    		}else if(grid.selected.length ==1){
   	    			$.ligerDialog.open({
						title:'方法明细',
						width:600,
						height:400, 
						url: 'method_desc_detail_show.jsp',
		    			onLoaded:function(param){
	  				      var documentF = param.contentDocument||param.document ;//兼容IE 和 FF
						    $('input[id="e.methodName"]',documentF).attr('value',grid.selected[0]['methodName']);    
						    $('input[id="e.descs"]',documentF).attr('value',grid.selected[0]['descs']);  		    			
	    			        var manager = param.grid;
	    			        var model=$("input[id='e.bussinessObject.bussCode']").val();
		    			    manager._setUrl('method_desc_detail!loadByEntity?e.methodDesc.id='+grid.selected[0]['id']);   
				        }
	 
	        		});
   	    		}
   	    	}else{
   	    		$.ligerDialog.warn('至少选择一条要查看的记录！');
   	    	}

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
