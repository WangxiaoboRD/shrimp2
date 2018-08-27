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
	<script src="../ligerUI/ligerUI/js/pap.js" type="text/javascript" ></script>
	<script src="../ligerUI/ligerUI/js/template/ligerTemplate.js" type="text/javascript"></script>
	<script src="../ligerUI/ligerUI/js/template/ligerXEFormGridEdit.js" type="text/javascript"></script>
	<script type="text/javascript" src="../ligerUI/ligerUI/js/plugins/XGrid.js"></script>
    <script type="text/javascript">
    	// 表格
    	var grid = null;
    	var delIds = "";// 删除的手动录入的数据权限id集合
       	var _roleAuthValueId = '${e.id}';// 角色权限规则编号
   	  	var _authTree = '${e.authField.tree}';// 权限树
   	  	// 业务元素值类型
   	  	var _valueType = '${e.authField.bussinessEle.valueType}';// 0 = 无；1=范围段；2=参考表；3=固定值
   	  	var refTable = '${e.authField.bussinessEle.refTable}';// 参考表
   	  	var authValueCarrier = null;// 权限值提交载体
        $(function(){

	   	  	var _bussinessEleCode = '${e.authField.bussinessEle.ecode}';// 业务元素编码
	   	    var dataType = '${e.authField.bussinessEle.dataType}';// 数据类型
	      	var _value = '${e.value}';// 权限规则值
        	
        	// 表单
        	var formOption = {
        		 url: "role_auth_value!modifyAll",
   	  			 labelWidth: 100, 
                 space:20,
   	  			 fields: [
   			        { display: "权限对象编码", name: "e.authObj.code", type: "text", options: { value: '${e.authObj.code}', readonly: true }}, 
   			        { display: "权限对象名称", name: "e.authObj.name", type: "text", options: { value: '${e.authObj.name}', readonly: true }},
   			      	{ display: "权限字段编码", name: "e.authField.code", type: "text", newline: true, options: { value: '${e.authField.code}', readonly: true }},
   			     	{ display: "权限字段名称", name: "e.authField.name", type: "text", options: { value: '${e.authField.name}', readonly: true }}
                  ]
              }
	  		 // 树状，参考表
	  		var refTableTreeGridOption = {
                   url:'role!load',
                   columns: [
					 { display: '名称', name: 'name' },
					 { display: '标识', name: 'id' },
					 { display: '描述', name: 'description' }
            		],
            		tree: {
                        columnId: 'name',
                        idField: 'id',
                        parentIDField: 'parentId'
                    },
            		isChecked: function(rowdata){
						if (_value.indexOf(rowdata.code) != -1){
		    				return true;
		    			}
							return false;
		 			}
            	}

	  		 // 固定值
        	var fixValueGridOption = {
        			url: 'bussiness_ele_detail!loadByEntity',
	        		parms: {'e.bussinessEle.ecode': _bussinessEleCode },
                    columns:[
	 					{ display: '编码', name: 'dcode' },
	 					{ display: '值', name: 'value' }
	             	],
             		isChecked: function(rowdata){
	        			authValueCarrier = 'value';
        				if (_value.indexOf(rowdata.value) != -1){
            				return true;
            			}
 						return false;
         			}
             	}


	  		 // 业务元素值类型：无或范围段
	  		var gridOption = {
                    url: 'role_auth_value_detail!loadByEntity',
                    parms: { 'e.roleAuthValue.id': _roleAuthValueId },
                    columns:[
						{ display: '编号', name: 'id', hide: true }, 
						{ display: '创建人', name: 'createUser', hide: true }, 
						{ display: '创建时间', name: 'createDate', hide: true },    
	 					{ display: '连接符', name: 'connector', editor: { type: 'select', options:{
	                     	  data: [
	                       	     { text: 'and', id: 'and' },
	                             { text: 'or', id: 'or' }
	                           ],
                              selectBoxHeight: 50
                          }}},
	 					{ display: '运算符', name: 'operator', editor: { type: 'select', comboboxName: 'operatorText', options:{
                     	  	data: [
								{ text: '等于', id: '=' },
								{ text: '大于', id: '>' },
                       	      	{ text: '大于等于', id: '>=' },
                              	{ text: '小于', id: '<' },
                              	{ text: '小于等于', id: '<=' }
                            ],
                            onSelected: function(value, text) {
                              if (value) {
	                              if (dataType == 'varText' && value != '=') {
	                            	  $.ligerDialog.warn('该权限字段为字符串类型，只允许选择[等于]运算符！');
	                            	  this.setValue('');
	                            	  this.setText('');
	                            	  return;
	                              }
                              }
                            }
                          }},render: function (data) {
                       	    var text = data.operator;
  	    					switch(data.operator){
  	    						case '=': text = "等于"; break;
  	    						case '>': text = "大于"; break;
  	    						case '>=': text = "大于等于"; break;
  	    						case '<': text = "小于"; break;
  	    						case '<=': text = "小于等于"; break;
  	    					}
  	    					return text;
                        }},
	 					{ display: '值', name: 'value', editor: { type: 'text' }}
             		],
             		toolbar: {
                        items: [
           	                { text: '添加', icon: 'add', click: function(item) {
           	                 	var index = 0;
		           	            grid.addRow({ connector: '', operator: '', value: '' }, index ++ , false);
               	            }},
           	                { line: true },	                
           	                { text: '删除', icon:'delete', click: function(item) {
           	                 	var selRows = grid.getSelecteds();
	           	                if (selRows.length == 0) {
	           	             	   $.ligerDialog.error('请选择要删除的记录！');
	           	 				   return;
	           	                }

		           	           $(selRows).each(function(i, data){
		         					delIds += data['id'] + ",";
		         				});
	           	                //	删除选中的行
	           	                grid.deleteSelectedRow(); 
               	            }}
                        ]
                   }
             	}

      	  	  // 默认是普通表格，业务元素值类型为无或范围段时
      	  	  var gridItem = gridOption;
      	  	  if (_valueType == '2') {// 参考表
      	  		$.ligerui.ligerAjax({
					type: "POST",
					async:  false,
					url: "bussiness_object!loadRefTableInfo",
					data: { 'e.tableCode': refTable, 'e.tempStack.bussinessEleCode': _bussinessEleCode },
					dataType: "json",
					success: function(result, status){
						
					 if (_authTree == '1') {
					 	// 权限树
	          	  		authValueCarrier = result.refPro;
	          	  		// 树状表格默认调用后台的load方法，若为树状表格，需重写后台的load方法
	                	gridItem = {
                            url: result.actionUrl + '!load',
                            columns: [
         						{ display: result.refNameProDesc, name: result.refNamePro },
         						{ display: result.refProDesc, name: authValueCarrier }
                     		],
                     		tree: {
	                			columnName: result.refNamePro,
	                			idField: authValueCarrier,
	                            parentIDField: result.refParPro
                            },
                            onCheckRow: function(checked, data, rowid, rowdata) { 
		                        if (checked == true) {
		                        	selectTreeParent(rowid);
			                    }
		                    },
                     		isChecked: function(rowdata){
                				if (_value.indexOf(rowdata[authValueCarrier]) != -1){
                    				return true;
                    			}
         						return false;
                 			}
                     	}
	              	  }else {
	              		// 非树状，参考表
	              		authValueCarrier = result.refPro;
	                	gridItem = {
                            url: result.actionUrl + '!load',
                            columns: [
         						{ display: result.refProDesc, name: authValueCarrier },
         						{ display: result.refNameProDesc, name: result.refNamePro }
                     		],
                     		isChecked: function(rowdata){
                				if (_value.indexOf(rowdata[authValueCarrier]) != -1){
                    				return true;
                    			}
         						return false;
                 			}
                     	}
	                  }
					}
			   	}); 
          	  	 
          	  }else if (_valueType == '3') { // 固定值
          	  	  gridItem = fixValueGridOption;
          	  }
      	  	 
              var template = $.pap.createEFormGridEdit({ form: formOption, grid: gridItem });
              grid = template.getGrid();

        });

        //	树状表格：递归查询并选中所有父级
        function selectTreeParent(rowid) {
 		   var _parent = grid.getParent(rowid);
 		   if (_parent) {
             var pid = _parent['__id'];
             // 若父级节点不为空且不是顶级节点
             if (pid && pid != -1) {
                 grid.select(pid);
                 // 判断父级是否还有父级
                 selectTreeParent(pid);
             }
 	 	   }
         } 
        
        // 保存权限分配信息
        function onSave() {
            
        	var submitValues =  { 'e.id': _roleAuthValueId, 'e.tempStack.delIds': delIds } ;// 提交值
        	
           // 若是参考表：树状表格, 若是参考表：非树状,若是固定值
           	if (_valueType == '2' || _valueType == '3') {
           		var values = "";
   				$(grid.selected).each(function(i, item){
   					values += item[authValueCarrier]+",";
   				});
   				$.extend(submitValues, { 'e.value': values });
            }else {// 若是手动录入
            	var gridDetails = grid.getEditGridDetails();
            	if(!$.isEmptyObject(gridDetails)){
					$.extend(submitValues, gridDetails);
				}
            }
            return submitValues;
        }
	  	
    </script>
</head>
<body></body>
</html>

