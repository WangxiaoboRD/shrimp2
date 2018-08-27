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
        var grid = null;
        $(function () {
        	 // 工具条
     	   var toolBarOption = { 
                   items: [
   	   				 { text: '审批授权', icon: 'role', expression:'!=1', disabled:true, click: function(item) {
   	   					var selectedRow = item.selectGrid.selected;
	   	   	           	if (selectedRow.length == 0) {
	   	           			$.ligerDialog.warn('请选择要授权的记录！');
	   	           			return;
		   	   	         } 
	    				$.pap.addTabItem({ 
	   	    				text: '单据/授权', 
	   	    				url: 'check_role_allotauth.jsp',
	   	    				param:selectedRow
	   	    			});
  	   	              }}
               		]
          	};
         	
     	 	 var form = {
     	     	  labelWidth: 80,
                  fields:[
                    { display: "角色编码", name: "e.roleCode",type: "text" } ,
	 				{ display: "角色名称", name: "e.roleName", type: "text"},
	 				{ display: "创建时间", name: "e.createDate", type:"date"},
 	    		]
             }

     	 	var gridoption={
                     columns:[
						{ display: '角色编码', name: 'roleCode' },
						{ display: '角色名称', name: 'roleName'},
						{ display: '角色状态', name: 'roleStatus', render: function(row){
							if(row.roleStatus == 1){ return "启用" };
							if(row.roleStatus == 2){ return "停用" };
						}},
						{ display: '创建日期', name: 'createDate'}
              		],
                    url:'role!loadByPage'
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
