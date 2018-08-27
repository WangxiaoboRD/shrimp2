<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    
    <title>出库编辑</title>
	<link href="../ligerUI/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
	<link href="../ligerUI/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />	
    <script src="../ligerUI/jquery/jquery-1.6.4.js" type="text/javascript"></script>
	<script src="../ligerUI/jquery-validation/jquery.form.js" type="text/javascript"></script> 
	<script src="../ligerUI/jquery-validation/jquery.validate.js" type="text/javascript"></script> 
    <script src="../ligerUI/jquery-validation/jquery.metadata.js" type="text/javascript"></script>
    <script src="../ligerUI/jquery-validation/messages_cn.js" type="text/javascript"></script>    
    <script src="../ligerUI/ligerUI/js/ligerui.all.js" type="text/javascript"></script>      
    <script type="text/javascript" src="../ligerUI/ligerUI/js/plugins/XGrid.js"></script> 
	<script src="../ligerUI/ligerUI/js/template/ligerTemplate.js" type="text/javascript"></script>
	<script src="../ligerUI/ligerUI/js/template/ligerXEFormGridEdit.js" type="text/javascript"></script>
	<script type="text/javascript" src="../ligerUI/ligerUI/js/pap.js"></script>
	<script type="text/javascript" src="../ligerUI/ligerUI/js/json2.js"></script>

    <script type="text/javascript">
	  	
	  var v ;
	  var form;
	  var grid;
	  var delIds = ""; // 存放删除的表列明细编号
	  $(function(){
	    var currentPage = $.pap.getOpenPage(window);
	  	var currentParam = currentPage.getParam("param")[0];	        
        var formoption={
                  url:'base_table!modify',
                  labelWidth: 75,
                  fields:[
	              { display: "表名称", name:"e.tabCode",type:"text",attr:{value:'${e.tabCode}'},options:{readonly:'readonly'}},
	              { display: "表描述", name:"e.tabName",type:"text",attr:{value:'${e.tabName}'},validate:{required:true}},
	              { display: "表类型", name:"e.tabType",type:"select", newline:true, validate:{required:true}, options:{               
				       	data: [
	  					   { text: '系统表', id: 'S' },
	  		               { text: '业务表', id: 'B' }
	  			        ],
	  			        keySupport:true,
	  			        selectBoxHeight: 80,
	  			        value: '${e.tabType}'              
	  	           }},
	              { display: "版本", name:"e.tabVer",type:"text",attr:{value:'${e.tabVer}'},validate:{required:true}}
				]
         }
	       
       	var gridoption={
        		  url:'base_table_column!loadByEntity',
        		  parms:{'e.baseTable.tabCode':currentParam['tabCode']},
                  columns:[
				    { display:'id',name:'id',hiden:true},
 				    { display:'主键',name:'isPk',editor: { type: 'select',comboboxName: "testSelect",options:{
                     	  data: [
                    	     { text: '是', id: 'Y' },
                             { text: '否', id: 'N' }
                          ],
                          
                           selectBoxHeight:50
                    }},render: function (item)
                    {
                        if (item.isPk =='N') return '否';
                        return '是';
                    }},                    
                    { display:'字段名',name:'fdcode',editor: { type: 'text'}},                   
                    { display:'字段描述',name:'descs',editor: { type: 'text'}},
    				{ display:'业务元素',name:'bussinessEle.ecode',editor: { type: 'popup',
                    	valueField: 'ecode', 
                    	textField: 'ename',
                        cascade:true, //选择后事件 == 弹出页面选择后的事件
                        onSelected:function(updateValue,rowdata){                   		
                    		grid.updateRow(rowdata,{
                    			'bussinessEle.ecode': updateValue.ecode
				            });				            
                        },
                        onButtonClick:{ title:"业务元素查询",url:'bussinessEle_list.jsp',
             				width:800,height:600,
             	            hideToolbar:true,dataFunName:'save'}
        		     }},

    				{ display:'字段类型',name:'dataType',editor: { type: 'select',options:{
     		              url:'base_table_column!loadDataBase',
    		              initText:'',
    		              valueField: 'dataType', //关键项
           			      textField: 'dataType'
           		    }}},
    				{ display:'长度',name:'len',editor: { type:'text', digits: true }},
    				{ display:'小数位数',name:'decimalLen',editor: { type:'text', digits: true }},
    				{ display:'默认值',name:'defaultValue',editor: { type: 'text'}},
    				{ display:'关键属性',name:'isImportantKey',editor: { type: 'select',comboboxName: "handSelect",options:{
                     	  data: [
                    	     { text: '是', id: 'Y' },
                           { text: '否', id: 'N' }
                          ],                         
                          selectBoxHeight:50
                      }},render: function (item)
                       {
                          if (item.isImportantKey =='N') return '否';
                           return '是';
                    }},
    				{ display:'外键表',name:'refTab',editor: { type: 'popup',
                    	valueField: 'baseTable.tabCode', 
                    	textField: 'baseTable.tabCode',
                        cascade:true, //选择后事件 == 弹出页面选择后的事件
                        onSelected:function(updateValue,rowdata){                		
                    		grid.updateRow(rowdata,{
                    			'refTab': updateValue['baseTable.tabCode'],
                    			'refKey': updateValue[0]['fdcode']
				            });				            
                        },
                        onButtonClick:{
                             title:"业务元素查询",url:'base_table_column_list.jsp',
                        	 width:650,height:500,
                        	 hideToolbar:true,dataFunName:'save'
                        }
       		        }},
    				{ display:'外键字段',name:'refKey'}
                  ],
                  usePager: true,
                  toolbar: {
                       items: [
	                     { text: '添加', click: addClick, icon: 'add' },
	                     { line: true },	                
	                     { text: '删除', click: deleteRow, icon:'delete'}		                
	              ]}
          }

       
        var template = $.pap.createEFormGridEdit({form:formoption,grid:gridoption});
        grid= template.getGrid();
        template.appendOtherData=function(){
        	return { 'e.tempStack.delIds': delIds };
        }
        
	 });

      //---添加
      var addClick = function(item){
          var i=0;
          grid.addRow({
               'id':'',
               'isPk':'N',
               'fdcode':'',
               'isHand':'N',
               'bussinessEle.ecode':'',
               'bussinessEle.ename':'',
               'bussinessEle.len':'',
               'bussinessEle.decimalLen':'',
               'defaultValue':'',
               'refTab':'',
               'refKey':''
           },i++,false);
	    }
 
	  // 删除
      function deleteRow()
      {   
    	  var selRows = grid.getSelecteds();
          if (selRows.length == 0) {
       		$.ligerDialog.error('请选择要删除的记录！');
			 	return;
        	}
          $(selRows).each(function(index, data){
            	 // 记录删除的明细id
            	 if (data['id']) {
 					delIds += data['id'] + ",";
                  }
          });
		  //删除选中的行
          grid.deleteSelectedRow(); 
      }
  	 
    </script>
    <style type="text/css">
         body{ font-size:12px;}
        .l-table-edit {}
        .l-table-edit-td{ padding:4px;}
    </style>
</head>
	<body style="padding: 10px" >
	</body>
</html>
