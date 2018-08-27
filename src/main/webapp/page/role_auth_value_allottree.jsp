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
    	var form = null;
    	var tree = null;
    	var delIds = "";// 删除的手动录入的数据权限id集合
       	var _roleAuthValueId = '${e.id}';// 角色权限规则编号
   	  	var refTable = '${e.authField.bussinessEle.refTable}';// 参考表
      	var _value = '${e.value}';// 权限规则值
   	  	var authValueCarrier = null;// 权限值提交载体
        $(function(){

	   	  	var _bussinessEleCode = '${e.authField.bussinessEle.ecode}';// 业务元素编码
	   	    var dataType = '${e.authField.bussinessEle.dataType}';// 数据类型
        	
        	// 表单
        	form = $("form").ligerForm({
        		 url: "role_auth_value!modifyAll",
   	  			 labelWidth: 100, 
                 space:20,
   	  			 fields: [
   			        { display: "权限对象编码", name: "e.authObj.code", type: "text", options: { value: '${e.authObj.code}', readonly: true }}, 
   			        { display: "权限对象名称", name: "e.authObj.name", type: "text", options: { value: '${e.authObj.name}', readonly: true }},
   			      	{ display: "权限字段编码", name: "e.authField.code", type: "text", newline: true, options: { value: '${e.authField.code}', readonly: true }},
   			     	{ display: "权限字段名称", name: "e.authField.name", type: "text", options: { value: '${e.authField.name}', readonly: true }}
                  ]
              });
            
      	  		$.ligerui.ligerAjax({
					type: "POST",
					async:  false,
					url: "bussiness_object!loadRefTableInfo",
					data: { 'e.tableCode': refTable, 'e.tempStack.bussinessEleCode': _bussinessEleCode },
					dataType: "json",
					success: function(result, status){
					 	// 权限树
	          	  		authValueCarrier = result.refPro;
		          	  	tree = $("#mainTree").ligerTree({
			    				url: result.actionUrl + '!load',
			                    idFieldName: authValueCarrier,
			                    parentIDFieldName: result.refParPro,
			                    nodeWidth: 100,
			                    textFieldName: result.refNamePro,//显示的菜单树名称
			                    attribute: ['id', 'name'],
			                    attributeMapping:[authValueCarrier, result.refNamePro],//添加到菜单html>li标记上的属性映射值
			    				btnClickToToggleOnly:true,
			    				isExpand:2,
			    				onSuccess: function(data) {
			    					initCheckedNodes(data.Rows);
			    				}
			            	});
	              	  }
			   	}); 

        });

       	// 初始化选中节点
       function initCheckedNodes(data) {
           if (data) {
        	 $(data).each(function(i, node){
				var ischecked = "unchecked";
				var checkedId = node[authValueCarrier];
				var fullChecked = checkedId + ":Y"; // 全选中
				var incompleteChecked = checkedId + ":X"; // 半选中
				if (_value.indexOf(checkedId) != -1) {
					var values = _value.split(",");
					if ($.inArray(fullChecked, values) != -1) {
						ischecked = "checked";
	   				}else if ($.inArray(incompleteChecked, values) != -1) {
	   					ischecked = "incomplete";
	   	   			}
					tree.initCheckedNodes(node, ischecked);
   				}
			 });
           }
       }
        	
        // 保存权限分配信息
        function onSave() {

        	var submitValues =  { 'e.id': _roleAuthValueId } ;// 提交值
           // 若是参考表：树状表格
       		var values = "";
       		$(tree.getInComplete()).each(function(i, node){
				values += node.data[authValueCarrier]+":X,"; // 添加半选中状态菜单
			});
			$(tree.getChecked()).each(function(i, node){
				values += node.data[authValueCarrier]+":Y,"; // 添加选中状态的菜单
			});
			$.extend(submitValues, { 'e.value': values });

            return submitValues;
        }
	  	
    </script>
</head>
<body>
<form></form>
<div class="l-scroll">
	 <ul id="mainTree" style="margin-top:3px;">
	 </ul>
</div>
</body>
</html>

