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
   		        	{ text: '删除', click: deleteClick, icon:'delete', expression:'==0', disabled:true},
   		        	{ line: true },
   		        	{ text: '设为空号', icon:'settings', expression:'==0', disabled:true, click: function(item) {
   		        		var _ids = "";
   	    				$(grid.selected).each(function(i,item){
   	    					_ids+=item['id']+",";
   	    				});
					   	$.ligerui.ligerAjax({
							url:"number_config!vacantSet",
							dataType:"text",
							data:{ids:_ids},
							success:function(_data,textStatus){
								if(_data !=''){
									tip = $.ligerDialog.tip({ title: '提示信息', content: '空号设置成功！' });
									window.setTimeout(function(){ tip.close()} ,2000); 	
									grid.loadData(true);
								}	
							},
							error: function(XMLHttpRequest,textStatus){
								$.ligerDialog.error('操作出现异常');
							}
						});    			
   	   		        }}
              	]
           };

           var  form={
           		 labelWidth: 90, 
                 fields:[
                     { display: "业务编码", name: "e.objectCode",type: "text" },
                     { display: "号码对象编码", name: "e.numberDetail.number.id",type: "text" },
                  	 { display: "创建日期", name: "e.createDate", type: "date" }
                  ]
           } 			  
	       
           var gridoption={
           		  checkbox: true, 
          		  url:'number_config!loadByPage',
                  columns:[
                        { display:'编码',name:'id',width:70,hide:true },
                         { display:'业务名称',name:'objectName',width:150 },
                         { display:'业务编码',name:'objectCode',width:150 },
                         { display:'关键属性',name:'hingeKey',width:150 },
                         { display:'关键值',name:'hingeValue',width:150 },
                         { display:'号码名称',name:'numberDetail.number.numberName',width:100 },
                         { display:'号码编码',name:'numberDetail.number.id',width:100 },
                         { display:'范围段',name:'numberDetail.numberScope',width:150 },
                         { display:'前缀',name:'numberDetail.prefix',width:100 },
                         { display:'年份',name:'numberDetail.year',width:100},
    					 { display:'子对象',name:'numberDetail.subobject',width:150},
                         { display:'起始值',name:'numberDetail.startNumber',width:100 },
                         { display:'终止值',name:'numberDetail.endNumber',width:100 },
                         { display:'当前值',name:'numberDetail.currentNumber' },
                         { display:'外部标示',name:'numberDetail.markExt',width:80,render:function(row){
	    					if(row.numberDetail.markExt=='N'){return "内部"};
	    					if(row.numberDetail.markExt=='Y'){return "外部"};
	    				 }},
                         { display:'创建日期',name:'createDate',width:150}
           	      ]
           }
           var glist=$.pap.createFormGridList({ toolbar:toolBarOption,form:form,grid:gridoption});
           grid=glist.getGrid();

        });
        
        //使用页签方法打开
    	var deleteClick = function(item){
    		if( grid.selected.length > 0 ){
	    		$.ligerDialog.confirm('数据删除后不可恢复,你确认要删除?',function(data) {
    				var delIds = "";
    				$(grid.selected).each(function(i,item){
    					delIds+=item['id']+",";
    				});
    				if(data){
					   	$.ligerui.ligerAjax({
							url:"number_config!delete",
							dataType:"text",
							data:{ids:delIds},
							success:function(_data,textStatus){
								if(_data !=''){
									tip = $.ligerDialog.tip({ title: '提示信息', content: _data+'条记录被删除！' });
									window.setTimeout(function(){ tip.close()} ,2000); 	
									grid.loadData(true);
								}	
							},
							error: function(XMLHttpRequest,textStatus){
									$.ligerDialog.error('操作出现异常');
							},
							complete:function(){}
						});    			
	    			}
    			});    			
	    	}else{
	    		$.ligerDialog.warn('你没有选择任何要删除的记录！')
	    	}
    	}; 

   </script>
 
</head>

<body>
</body>
</html>
