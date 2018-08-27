<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>功能注册</title>
	 
    <link href="../ligerUI/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
	<link href="../ligerUI/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />	
    <script src="../ligerUI/jquery/jquery-1.6.4.js" type="text/javascript"></script>
	<script src="../ligerUI/jquery-validation/jquery.form.js" type="text/javascript"></script> 
	<script src="../ligerUI/jquery-validation/jquery.validate.js" type="text/javascript"></script> 
    <script src="../ligerUI/jquery-validation/jquery.metadata.js" type="text/javascript"></script>
    <script src="../ligerUI/jquery-validation/messages_cn.js" type="text/javascript"></script>    
    <script src="../ligerUI/ligerUI/js/ligerui.all.js" type="text/javascript"></script> 
    
    <script type="text/javascript">
       /*  1.使用 html编写
	  	$(function(){
	  		 $("form").ligerForm();
	  	});*/
	  	// 2.不适用 html 使用js 自动生成效果
	  	var form,v;
	  $(function(){
	  	 var values = "";
	  	 
	  	 $.ajax({
				type:"POST",
				async: false,
				url:"item!loadMaxOrder",
				//data:{ids:checkedEntity.join(',')},
				//data:{ids:delIds},
				dataType:"text",
				beforeSend:function(){},
				success:function(result, textStatus){
					values = result;
				},
				error: function(XMLHttpRequest,textStatus){
					$.ligerDialog.error('获取最大序号出现异常');
				}
		   	}); 	
	
		  //生成ligerUI form
	  	 form = $("form").ligerForm({
	  		 inputWidth: 170, labelWidth: 80, space: 20,
             fields: [
                { display: "功能名称", name: "e.itemName",validate:{required:true},newline: true, type: "text" }, 
              	{ display: "功能类型", name:"e.itemType",type: "select", newline: true,comboboxName: "testSelect", options:{
                           	  data: [
                         	        { text: '菜单', id: '0' },
         		                    { text: '按钮', id: '1' },
         		                    { text: '接口', id: '2' }],
         		              keySupport:true,
         		              initValue:"0",
        		              selectBoxHeight:110,
        		              onSelected:function(newvalue){
        		              	if(newvalue == 0 || newvalue==1 ){
        		              		$("input[name='e.functionName']").parent().parent().parent().css("display","none");
        		              		$("input[name='e.url']").parent().parent().parent().css("display","block");
        		              		$("input[name='e.url']").parent().parent().parent().parent().css("display","block");
        		              		$("input[name='e.urlParam']").parent().parent().parent().css("display","block");
        		              		$("input[name='e.urlParam']").parent().parent().parent().parent().css("display","block");
        		              		$("input[name='e.parentId.id']").parent().parent().parent().css("display","block");
        		              		$("input[name='e.parentId.id']").parent().parent().parent().parent().css("display","block");
        		              		
        		              	}else if(newvalue == 2){
        		              		$("input[name='e.url']").parent().parent().parent().css("display","none");
        		              		$("input[name='e.urlParam']").parent().parent().parent().css("display","none");
        		              		$("input[name='e.parentId.id']").parent().parent().parent().css("display","none");
        		              		$("input[name='e.functionName']").parent().parent().parent().parent().css("display","block");
        		              		$("input[name='e.functionName']").parent().parent().parent().css("display","block");
        		              	}
        		              }
                }},
              	{ display: "所属菜单", name: "e.parentId",newline: true, type: "select",
                  	options:{
                     //valueFieldID:'e.typeName',
					    selectBoxWidth:220,
					    selectBoxHeight: 230, 
					    valueField: 'id', 
					    textField: 'itemName',
					    treeLeafOnly: false,
					    tree: {
							url:'item!loadByConEntity',
							idFieldName :'id',
							parentIDFieldName:'parentId',//配置后没有任何作用滴！！！
							nodeWidth : 200,
							textFieldName:'itemName',//显示的菜单树名称
							attribute: ['id', 'text'],
							attributeMapping:["id","itemName"],//添加到菜单html>li标记上的属性映射值
							checkbox:false,//配置是否 复选框形式展示
							single:true,//配置一次只能选择一个节点
							btnClickToToggleOnly:true,
							isExpand: 1
							//loadParamName:"e.parent.id",//初始化加载的参数名称
							//loadParamValue:-1,//初始化加载的参数值
							//judgeStyleParam:"e.subct"//判断 树节点是叶子or文件夹的参数
			          }
                  }
                },
                { display: "URL", name: "e.url",newline: true, type: "text" }, 
                { display: "参数", name: "e.urlParam",newline: true, type: "text" }, 
                { display: "方法名", name: "e.functionName",newline: true, type: "text" }, 
                { display: "状态", name:"e.status",type: "select", newline: true,comboboxName: "statuSelect", options:{
                           	  data: [
                         	        { text: '启用', id: 'Y' },
         		                    { text: '禁用', id: 'N' },],
         		              initValue:"Y",
         		              keySupport:true,
        		              selectBoxHeight:110
                }},
                { display :'功能序号',name:'e.rank',type:'spinner',newline: true,options:{type: 'int',isNegative:false, minValue: 0, maxValue: 1000000,step:1,value:values}},
                { display :'功能描述',name:'e.itemDesc',type:'textarea',newline: true,options:{width:168}}
                ]
	  		 });
	  		 v= form.validateForm();
	  	});
	  	var onSave = function(){
	  		$("form").submit();
			if( v.valid() ){
				return $('form').formSerialize();
			}else{
				return null;
			}
	  		/*console.log($('#form1').formSerialize());
			return $('#form1').formSerialize();*/
		}  
    </script>
    <style type="text/css">
         body{ font-size:12px;}
        .l-table-edit {}
        .l-table-edit-td{ padding:4px;}
    </style>

</head>
	<body style="padding: 10px">
		<form/>
		<div style="display: none"></div>
	</body>
</html>
