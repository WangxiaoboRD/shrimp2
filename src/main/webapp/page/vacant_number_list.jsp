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

        	   var toolBarOption = { 
   	                   items: [
							{ text: '关闭', icon: 'delete', click: function() {
								  $.pap.removeTabItem();
							}}
	   	   	              /*{ text: '删除', icon: 'delete', expression:'==0', disabled:true, click: function(){
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
	   	   	      							url: "vacant_number!delete",
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
	   	   			      }}*/
                   		]
             	};

        	 form = {
               	 	labelWidth: 70,
                    fields:[
                        { display: "业务对象", name:"e.bussObj", type:"select", options:{
 						    url:'vacant_number!loadBussObj',
 						    valueField: 'bussCode', 
 					        textField: 'bussName',
 					        keySupport:true,
 							selectBoxHeight: 150
 						}},
       	 				{ display: "生成日期", name: "e.tempStack.startDate", type: "date",  space: 5 },
	                	{ display: "至", type: "label", space: 5 },
	                	{ hideLabel: true, name: "e.tempStack.endDate", type: "date" },
	       	 			{ display: "状态", name: "e.useStatus", type: "select", labelWidth: 45, comboboxName: 'statusName', options:{
    	                	  data: [
     	                	    { text: '---', id: '' },
     	                       	{ text: '已使用', id: 'Y' },
     	               	  		{ text: '未使用', id: 'N' }
     		                 ]
	      	               	}
	     	            }
				    ]
               	}

        	 var gridoption={
                     columns:[
                        { display: '编号',name: 'id', width: 120 },
	    				{ display: '业务对象编码',name: 'bussObj', width: 150 },
	    				{ display: '业务对象名称',name: 'bussName', width: 150 },
	    				{ display: '生成日期',name: 'generateDate', width: 120 },
	    				{ display: '生成号码',name: 'bussNumber' },
	    				{ display: '创建时间',name: 'createDate' },
	    				{ display: '状态',name: 'useStatus',render: function(data){
		    				var text = data.useStatus;
	    					switch(data.useStatus){
	    						case 'Y': text = "已使用"; break;
	    						case 'N': text = "未使用"; break;
	    					}
	    					return text;
	    				}}
              		],
                    url:'vacant_number!loadByPage',
                    exportBtn: true,
			        exportUrl: 'vacant_number!exportFile'
             }
          
           var glist = $.pap.createFormGridList({ toolbar:toolBarOption,form:form,grid:gridoption});

           grid = glist.getGrid();
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
