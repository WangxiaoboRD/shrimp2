<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head><title>
</title>
    <link href="../ligerUI/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
	<link href="../ligerUI/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />	
    <script src="../ligerUI/jquery/jquery-1.6.4.js" type="text/javascript"></script>
	<script src="../ligerUI/jquery-validation/jquery.form.js" type="text/javascript"></script> 
	<script src="../ligerUI/jquery-validation/jquery.validate.js" type="text/javascript"></script> 
    <script src="../ligerUI/jquery-validation/jquery.metadata.js" type="text/javascript"></script>
    <script src="../ligerUI/jquery-validation/messages_cn.js" type="text/javascript"></script> 
	<script src="../ligerUI/ligerUI/js/core/base.js" type="text/javascript"></script>
	<script src="../ligerUI/ligerUI/js/ligerui.all.js" type="text/javascript"></script>       
	<script src="../ligerUI/ligerUI/js/pap.js" type="text/javascript" ></script>
	<script src="../ligerUI/ligerUI/js/template/ligerTemplate.js" type="text/javascript"></script>
	<script src="../ligerUI/ligerUI/js/template/ligerXEFormGridEdit.js" type="text/javascript"></script>
    <script type="text/javascript">
    var grid = null;
    var delIds = "";// 删除的过滤属性明细id集合
    var delPropertyIds = "";// 删除的属性id集合
    $(function(){
    	var currentPage = $.pap.getOpenPage(window);
	  	var currentParam = currentPage.getParam("param")[0];
  		 var formOption = {
  		 	 url: "bussiness_object!authPropertySet",	// 此处也是必须的
  			 labelWidth: 100, 
       		 space:20,
  			 fields: [
		        { display: "业务对象编码", name: "e.bussCode", type: "text" , options: { value: currentParam['bussCode'], readonly: true }}, 
                { display: "业务对象名称", name: "e.bussName", type: "text", options: { value: currentParam['bussName'], readonly: true }},
                { display: "类全名", name: "e.fullClassName", type: "text", width: 300, options: { value: currentParam['fullClassName'], readonly: true }}
           ]};

  		 var gridOption = {
  				 url: 'buss_obj_auth_property!loadByEntity',
        		 parms:{ 'e.bussObj.bussCode': currentParam['bussCode']},
  				 submitDetailsPrefix: 'e.authProperties',
                 columns: [
					{ display: '编号', name: 'id', hide: true },
					{ display: '绑定状态', name: 'tempStack.bind', hide: true },
					{ display: '属性编号', name: 'bussObjProperty.id', hide: true },
                    { display: '属性名', name: 'propertyFullName', width: 200 },
                    { display: '对应表字段', name: 'bussObjProperty.fieldName', width: 150 },
                    { display: '字段类型', name: 'bussObjProperty.fieldtype', width: 150 },
                    { display: '字段长度', name: 'bussObjProperty.fieldLen', width: 100 },
                    { display: '主键', name: 'bussObjProperty.isPk', render: function(data){
    					 if(data['bussObjProperty.isPk']=='N'){return ""};
 					  	 if(data['bussObjProperty.isPk']=='Y'){return "是"};
 				   	 }},
                    { display: '业务元素', name: 'bussObjProperty.bussEleCode', width: 150 }
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
  		  grid = template.getGrid();

  		    // 记录删除过滤属性id集合
	  		template.appendOtherData = function(){
	            var result= { 'e.tempStack.delIds': delIds, 'e.tempStack.delPropertyIds': delPropertyIds } ;
	         return result;
	       }

  		// 添加按钮窗口
          function addClick(item) {
         	 $.ligerDialog.open({ 
       			title:'搜索',
       			url: 'bussiness_object!loadPropertySelectById?id=' + currentParam['bussCode'], 
       			height: 500,
       			width: 800, 
       			buttons: [ 
       				{ text: '选择', onclick:  function (item, dialog) { 
       					var selRows = dialog.frame.f_select();
       					if (selRows.length < 1) {
       						$.ligerDialog.warn('请至少选择一条记录进行操作');
 	           			}else {
 	           					 var isRoot = false;
		 	           			 $(selRows).each(function(index, data){
		 	           				if (! data.id) {
										isRoot = true;
										return false;
 	 								}
		 	 	           		 });
		 	           			if (isRoot) {
									$.ligerDialog.warn('对不起，您只能选择类结构下的属性选项！');
 								}else {
								  $(selRows).each(function(index, data){
 									var isRepeat = false;
 									$(grid.rows).each(function(index, data1){
 										if (data1['bussObjProperty.id'] == data['id']) {
 											isRepeat = true;
 											return false;
 										}
 									});
 	 									
 									if (! isRepeat) {
 		           						grid.addRow({
 		           			                'bussObjProperty.id': data.id,
 		           			                'propertyFullName': data.quoteObjPropertyName,
 		           			                'bussObjProperty.fieldName': data.fieldName,
 		           			            	'bussObjProperty.fieldtype': data.fieldtype,
 		           			        		'bussObjProperty.fieldLen': data.fieldLen,
 		           			        	 	'bussObjProperty.isPk': data.isPk,
 		           			        	 	'bussObjProperty.bussEleCode': data.bussEleCode
 		           			            });
 	 	 							}
	 	           					dialog.close();
								  });
 								}
        					}
       					}}, 
  						{ text: '取消', onclick: function (item, dialog) { dialog.close(); }}
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
			  var propertyList = "";
              $(selRows).each(function(i, data){
                  if (data['id']) {
					delIds += data['id'] + ",";
					delPropertyIds += data['bussObjProperty.id'] + ",";
					if ("true" == data['tempStack.bind']) {
						propertyList += data['bussObjProperty.propertyName'] + ",";
					}
                  }
				});
				if (propertyList != "") {
					$.ligerDialog.warn('对不起，您所删除的属性[' + propertyList + ']已绑定权限对象字段，不允许删除！');
					return;
				}
              
              // 删除选中的行
              grid.deleteSelectedRow(); 
          }
    });
    </script>
</head>
	<body>
	</body>
</html>
