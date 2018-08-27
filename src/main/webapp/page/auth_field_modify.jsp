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
	<script src="../ligerUI/ligerUI/js/template/ligerXSFormModify.js" type="text/javascript"></script>
    <script type="text/javascript">
      var v ;
	  var form;
		 $(function(){
			 // 获得当前页面对象
			 var currentPage = $.pap.getOpenPage(window);
			 // 获得当前页面参数
			 var currentParam = currentPage.getParam("param");	
	  		 var formOptions = {
	  		 	 url: "auth_field!modifyAll",	// 此处也是必须的
	  			 labelWidth: 100, 
           		 space:20,
	  			 fields: [
  			        { display: "权限字段编码", name: "e.code", options: { value: '${e.code}'}, type: "displayfield", newline: true }, 
  	                { display: "权限字段名称", name: "e.name", options: { value: '${e.name}' }, validate: { required: true }, newline: true, type: "text" },
  	                { display: "权限字段类型", name: "e.type", validate: { required: true }, type: "select", newline: true, options:{               
  	                	keySupport:true,
  				       	data: [
  					       	{ text: '功能权限', id: 'FUNCTION' },
  		                  	{ text: '数据权限', id: 'DATA' },
  		                  	{ text: '业务权限', id: 'BUSINESS' }
  			              ],
				       	value: '${e.type}'           
  	                }},
  	               { display: "业务元素", name: 'e.bussinessEle.ecode', type: "select", comboboxName: 'e.bussinessEle.ename', validate: { required: true }, newline: true,
  	                	options: {
 	                		url: 'bussiness_ele!loadByEntity',
  	             		 	selectBoxWidth: 500,
  	             		 	selectBoxHeight: 240,
  	             		    valueField: 'ecode', 
  	             		    textField: 'ename',
  	             		 	cascade: 'e.bussinessEle.dataType', // 级联清空项
  	             		    initValue: '${e.bussinessEle.ecode}',
  	                        grid: {
            		 	       columns:[
                                    { display:'编码',name:'ecode' },
                                    { display:'名称',name:'ename' },
                                    { display:'类别',name:'classType'},
                    				{ display:'数据类型',name:'dataType'},
                    				{ display:'长度',name:'len'},
                    				{ display:'小数位数',name:'decimalLen'},
                    				{ display:'日志记录',name:'flagLog'},
                    				{ display:'值',name:'valueType'}
                               ],
                               url:'bussiness_ele!loadByEntity',
                               onSelectRow: function(rowdata) {
  	             				   $("input[id='e.bussinessEle.dataType']").val(rowdata['dataType']);
                         	   }
	                        }
  	                	}
  	                },
  	                { display: "数据类型", name: "e.bussinessEle.dataType", newline: true, type: "text",options: { value: '${e.bussinessEle.dataType}', readonly: true } },
  	                { display: "权限树", name: "e.tree", type: "select", newline: true, options:{               
  	                	keySupport:true,
  				       	data: [
  					       	{ text: '否', id: '0' },
  		                  	{ text: '是', id: '1' }
  			              ],
  			        	name: "e.tree",
				       	value: '${e.tree}',
				       	selectBoxHeight: 60               
  	                }}
               ]};
             
              var template =$.pap.createSFormModify({form: formOptions});
	  		  form = template.getForm() ;
	  		  v = form.validateForm() ;

		 });
	  	
    </script>
</head>
<body></body>
</html>

