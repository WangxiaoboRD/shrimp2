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
    <script src="../ligerUI/ligerUI/js/plugins/XGrid.js" type="text/javascript"></script>    
	<script src="../ligerUI/ligerUI/js/pap.js" type="text/javascript" ></script>
	<script src="../ligerUI/ligerUI/js/template/ligerTemplate.js" type="text/javascript"></script>
	<script src="../ligerUI/ligerUI/js/template/ligerXEFormGridEdit.js" type="text/javascript"></script>
	<script src="../ligerUI/ligerUI/js/core/ligerAjax.js" type="text/javascript" ></script>
    <script src="../ligerUI/ligerUI/js/json2.js" type="text/javascript" ></script>
    <script type="text/javascript">
		var v;
        var form = null;
        var grid = null;
        var deleteIds = "";// 存放删除的明细Id
        $(function(){
        	var currentPage = $.pap.getOpenPage(window);
    	  	var currentParam = currentPage.getParam("param")[0];
    	  	
	  		 var formOption = {
	  		 	 url: "auth_obj!modifyAll",	// 此处也是必须的
	  			 labelWidth: 100, 
           		 space:20,
	  			 fields: [
  			        { display: "权限对象编码", name: "e.code", type: "text", options: { value: '${e.code}', readonly: true }},
  	                { display: "权限对象名称", name: "e.name", validate: { required: true }, type: "text", options: { value: '${e.name}'}},
  	                { display: "权限对象类型", name: "e.type", type: "select", comboboxName: 'typeName', options:{               
  				       	data: [
  					       	{ text: '功能权限', id: 'FUNCTION' },
  		                  	{ text: '数据权限', id: 'DATA' },
  		                  	{ text: '业务权限', id: 'BUSINESS' }
  			              ],
  			            keySupport:true,
  			            selectBoxHeight: 80,
  			          	name: "e.type",
				       	value: '${e.type}',
				       	readonly: true          
  	                }}
               ]};

	  		 var gridOption={
	  				 url:'auth_obj!loadFieldSet',
	        		 parms:{ 'id': currentParam['code']},
	        		 submitDetailsPrefix: 'e.fieldSet',
                     columns:[
                        { display: '权限字段编码',name: 'code' },
	    				{ display: '权限字段名称',name: 'name' },
	    				{ display: '权限字段类型', name: 'type', render: function(data){
		    				var text = data.type;
	    					switch(data.type){
	    						case 'FUNCTION': text = "功能权限"; break;
	    						case 'DATA': text = "数据权限"; break;
	    						case 'BUSINESS': text = "业务权限"; break;
	    					}
	    					return text;
	    				}},
	    				{ display: '业务元素', name: 'bussinessEle.ename' },
	    				{ display: '数据类型', name: 'bussinessEle.dataType' },
	    				{ display: '权限树',name: 'tree',render: function(data){
		    				var text = data.tree;
	    					switch(data.tree){
	    						case 0: text = "否"; break;
	    						case 1: text = "是"; break;
	    					}
	    					return text;
	    				}}
              		],
                    toolbar: {
                       items: [
      		                { text: '添加', icon: 'add', click: addClick },
      		                { line: true },	                
      		                { text: '删除', icon:'delete', click: deleteClick }
      	                ]
      	            }
             }
             
              var template =$.pap.createEFormGridEdit({form: formOption, grid: gridOption});
	  		  form = template.getForm() ;
	  		  v = form.validateForm() ;
	  		  grid = template.getGrid();
        });

         // 添加按钮窗口
         function addClick(item) {
             // 提交form后valid才会有效
        	$("form").submit();
        	if (! v.valid()) {
        		$.ligerDialog.error('权限对象信息录入不完整！');
	    		return;
            }
 		    
        	 $.ligerDialog.open({ 
      			title:'搜索',
      			url: 'auth_field_list.jsp', 
      			height: 500,
      			width: 800, 
      			onLoaded:function(param){
     		       var documentF = param.contentDocument || param.document ;//兼容IE 和 FF
     		           $('div.toolbar-pap',documentF).hide();
     		           $('input[id="e.type"]',documentF).attr('value', $('input[id="e.type"]').val()); 
      		           $('input[id="typeName"]',documentF).attr('value', $('input[id="typeName"]').val()); 
     		       },
      			buttons: [ 
      				{ text: '选择', onclick:  function (item, dialog) { 
      					var selRows = dialog.frame.f_select();
      					if (selRows.length < 1) {
      						$.ligerDialog.warn('请至少选择一条记录进行操作');
	           				}else {
		           				var authType = $("input[id='e.type']").val();
		           				var isValid = true;
	           					$(selRows).each(function(index, data){
		           					if (data["type"] != authType) {
		           						$.ligerDialog.error('所选择的权限字段类型与权限对象类型不一致，请确认！');
		           						isValid = false;
		           						return false;
			           				}
		           				});
							if (isValid) {
								$(selRows).each(function(index, data){
									var isRepeat = false;
									$(grid.rows).each(function(index, data1){
										if (data1.code == data.code) {
											isRepeat = true;
											return false;
										}
									});
									if (! isRepeat) {
		           						grid.addRow({
		           			                'code': data.code,
		           			                'name': data.name,
		           			                'type': data.type,
		           			             	'bussinessEle.ename': data.bussinessEle.ename,
		           			            	'bussinessEle.dataType': data.bussinessEle.dataType,
		           			            	'tree': data.tree
		           			            });
									}
		           				});
	           					dialog.close();
							}
	           			}
       					}}, 
 						{ text: '取消', onclick:  function (item, dialog) { dialog.close(); }}
  					] 
  				 });
         }

         // 删除按钮
         function deleteClick(item) {
             var selRows = grid.getSelecteds();
             if (selRows.length == 0) {
            	 $.ligerDialog.error('请选择要删除的记录！');
				 return;
             }
             //	删除选中的行
             grid.deleteSelectedRow(); 
         }
	  	
    </script>
</head>
<body></body>
</html>

