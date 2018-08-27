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
    var v;
    var form = null;
    var grid = null;
    var delAuthObjCodes = "";// 记录删除的权限对象编码集合
    $(function(){
    	var groupicon = "../ligerUI/ligerUI/skins/icons/communication.gif";
    	var currentPage = $.pap.getOpenPage(window);
	  	var currentParam = currentPage.getParam("param")[0];
  		 var formOption = {
  		 	 url: "role!modifyAll",	// 此处也是必须的
  			 labelWidth: 80, 
       		 space:20,
  			 fields: [
		        { display: "角色编码", name: "e.roleCode",group: "设置角色", groupicon: groupicon, options: { value: "${e.roleCode}", readonly: true }, type: "text" }, 
                { display: "角色名称", name: "e.roleName", type: "text", validate:{ required: true }, newline: true, options: { value: "${e.roleName}" }},
                { display: "角色状态", name: "e.roleStatus",type: "select", newline: true, options:{               
			       	data: [{ text: '启用', id: '1' },
		                  { text: '停用', id: '2' }],          
			       	name: "e.roleStatus",
			       	value: '${e.roleStatus}',
			       	selectBoxHeight: 60     
                }}
           ]};

  		 var gridOption={
  				url:'role!loadAuthObjSet',
        		parms:{ 'id': currentParam['roleCode']},
  				submitDetailsPrefix: 'e.authObjSet',
                 columns:[
                    { display: '权限对象编码',name: 'code' },
    				{ display: '权限对象名称',name: 'name' },
    				{ display: '权限对象类型', name: 'type', render: function(data){
	    				var text = data.type;
    					switch(data.type){
    						case 'FUNCTION': text = "功能权限"; break;
    						case 'DATA': text = "数据权限"; break;
    						case 'BUSINESS': text = "业务权限"; break;
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

  		   // 记录删除权限对象id集合
	  		template.appendOtherData = function(){
	            var result= { 'e.tempStack.delAuthObjCodes': delAuthObjCodes } ;
	         return result;
	       }

  		// 添加按钮窗口
          function addClick(item) {
              // 提交form后valid才会有效
         	$("form").submit();
         	if (! v.valid()) {
         		$.ligerDialog.error('角色信息录入不完整！');
 	    		return;
             }
  		    
         	 $.ligerDialog.open({ 
       			title:'搜索',
       			url: 'auth_obj_list.jsp', 
       			height: 500,
       			width: 800, 
       			onLoaded:function(param){
      		       var documentF = param.contentDocument || param.document ;//兼容IE 和 FF
      		           $('div.toolbar-pap',documentF).hide();
      		       },
       			buttons: [ 
       				{ text: '选择', onclick:  function (item, dialog) { 
       					var selRows = dialog.frame.f_select();
       					if (selRows.length < 1) {
       						$.ligerDialog.warn('请至少选择一条记录进行操作');
 	           			}else {
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
 		           			                'type': data.type
 		           			            });
 									}
	 	           					dialog.close();
								  });
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
				// 记录删除的权限对象编码集合
              $(selRows).each(function(i, data){
            	  delAuthObjCodes += data['code'] + ",";
				});
              
              //	删除选中的行
              grid.deleteSelectedRow(); 
          }
    });
    </script>
</head>
	<body>
	</body>
</html>
