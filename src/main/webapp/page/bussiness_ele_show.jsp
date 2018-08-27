<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head><title>业务元素编辑</title>
	<link href="../ligerUI/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
	<link href="../ligerUI/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />	
    <script src="../ligerUI/jquery/jquery-1.6.4.js" type="text/javascript"></script>
	<script src="../ligerUI/jquery-validation/jquery.form.js" type="text/javascript"></script> 
	<script src="../ligerUI/jquery-validation/jquery.validate.js" type="text/javascript"></script> 
    <script src="../ligerUI/jquery-validation/jquery.metadata.js" type="text/javascript"></script>
    <script src="../ligerUI/jquery-validation/messages_cn.js" type="text/javascript"></script>    
    <script src="../ligerUI/ligerUI/js/ligerui.all.js" type="text/javascript"></script>       
	<script src="../ligerUI/ligerUI/js/template/ligerTemplate.js" type="text/javascript"></script>
	<script src="../ligerUI/ligerUI/js/template/ligerXEFormGridShow.js" type="text/javascript"></script>
	<script type="text/javascript" src="../ligerUI/ligerUI/js/pap.js"></script>

    <script type="text/javascript">
	  	
	  var form;
	  var grid;
	  $(function(){


		    var currentPage = $.pap.getOpenPage(window);
		  	var currentParam = currentPage.getParam("param")[0];	
		  	 var groupicon = "../ligerUI/ligerUI/skins/icons/communication.gif";        
	        var formoption={
	    	          labelWidth: 75,
	                  fields:[
	                          { display: "参考元素", name:"e.refCode.ecode",type:"text",attr:{value:'${e.refCode.ecode}',readonly:'readonly'},group: "基础信息", groupicon: groupicon},
	                          { display: "类别", name:"e.classType",type:"text",attr:{value:'${e.classType}',readonly:'readonly'}},
	                          { display: "编码", name:"e.ecode",type:"text",validate:{required:true},newline:true,attr:{value:'${e.ecode}'},options:{readonly:'readonly'}},
	                          { display: "名称", name:"e.ename",type:"text",validate:{required:true},attr:{value:'${e.ename}',readonly:'readonly'}},
	                          { display: "数据类型", name:"e.dataType",type:"select",validate:{required:true},newline:true,options:{
	          		              url:'bussiness_ele!loadDataType',
	          		              valueField: 'fieldcode', //关键项
	                 			  textField: 'fieldname',
	                 			  initValue:'${e.dataType}',
	                 			  readonly:'readonly',
	                 			  selectBoxHeight:110			      
	                          }},
	                          { labelWidth: 1,name:"e.len",hidden:true,type:"int",width:100,options:{width:98,nullText:'长度'},attr:{value:'${e.len}',readonly:'readonly'},validate:{required:true}},
	                          { labelWidth: 1,name:"e.decimalLen",hidden:true,width:108,type:"int",options:{width:106 ,nullText:'小数点位数'},attr:{value:'${e.decimalLen}',readonly:'readonly'},validate:{required:true}},
	                          { display: "业务组件", name:"e.component",type: "select", newline: true,comboboxName: "testSelect", options:{
	                             	  data: [
	                           	        { text: '文本控件', id: 'text' },
	           		                    { text: '日期控件', id: 'date' },
	           		                    { text: '选择控件', id: 'select' },
	           		                    { text: '数字控件', id: 'num' }],
	           		              initValue:"${e.component}",
	           		              readonly:'readonly',
	          		              selectBoxHeight:110
	                           }},
	                          { display: "参数", name:"e.param",type:"text",attr:{value:'${e.param}',readonly:'readonly'}},
	                          { display: "描述", name:"e.descs",type:"textarea",width:405,newline: true,options:{width:400},attr:{value:'${e.descs}',readonly:'readonly'}},
	                          { labelWidth: 100,display: "是否记录日志", name:"e.flagLog",group: "日志标示",groupicon: groupicon,type:"checkbox",attr:{value:'${e.flagLog}',readonly:'readonly'},

	                          },
	                          { labelWidth: 10,name:"valuetype",type:"radiogroup",group: "值类型",width:300,newline: true, groupicon: groupicon,
	                              options:{
	                                 data:[
	                                      {id:0,name:'无'},
	                                      {id:1,name:'范围'},
	                                      {id:2,name:'参照表'},
	                                      {id:3,name:'固定值'}
	                                 ],
	                                 name:'e.valueType',
	                                 rowSize:5,
	                                 width:280,
	                                 textField: 'name' ,                        
	                                 value:'${e.valueType}',
	                                 readonly:'readonly'                     
	                            }
	                          },
	                          { labelWidth: 80,display: "参照表",hidden:true, newline: true,name:"e.refTable",type:"text",attr:{value:'${e.refTable}',readonly:'readonly'}},
	                          { labelWidth: 80,display: "名称",hidden:true, newline: true,name:"e.refFieldName",type:"text",attr:{value:'${e.refFieldName}',readonly:'readonly'}},
	                          { labelWidth: 80,display: "引用字段",hidden:true, newline: true,name:"e.refTabField",type:"text",attr:{value:'${e.refTabField}',readonly:'readonly'}},
	                          { labelWidth: 80,display: "父级字段",hidden:true, newline: true,name:"e.refTabParField",type:"text",attr:{value:'${e.refTabParField}',readonly:'readonly'}},
	                          { labelWidth: 13,hidden:true,newline: true,name:"e.lessValue",type:"text",width:100,options:{width:98,nullText:'最小值'},attr:{value:'${e.lessValue}',readonly:'readonly'}},
	                          { labelWidth: 13,hidden:true,name:"e.greatValue",width:108,type:"text",options:{width:106,nullText:'最大值'},attr:{value:'${e.greatValue}',readonly:'readonly'}}
	         	]
	         }

	       	var gridoption={
	        		  url:'bussiness_ele_detail!loadByEntity',
	        		  parms:{'e.bussinessEle.ecode':currentParam['ecode']},
	                  columns:[
	                     { display:'ecode',name:'bussinessEle.ecode',hide:true},
	            		 { display:'编码',name:'dcode'},
	            		 { display:'值',name:'value'},
	            		 { display:'备用字段1',name:'spareField1'},
	            		 { display:'备用字段2',name:'spareField2'}
	                  ],
	                  usePager: true,
	                  checkbox: false
	                  
	          }

	       
	        var glist=$.pap.createEFormGridShow({form:formoption,grid:gridoption});

	        grid=glist.getGrid();

		   $("#grid1005").attr("style","margin: 0px; padding: 0px; width: 99%;display: none;");

		   if(currentParam['len']){
			   var len = liger.get("e.len") ;
               len.setVisible(true);
		   }

		   if(currentParam['decimalLen']){
			   var deci = liger.get("e.decimalLen") ;
               deci.setVisible(true);
		   }

		   if(currentParam['flagLog']){
			   var log = liger.get("e.flagLog") ;
               log.setValue(true);						   
			   if(currentParam['flagLog']=='on'){
                   log._setChecked(true);
				}
			}


		   if(currentParam['valueType']){
               if(currentParam['valueType']==1){
				   var less = liger.get("e.lessValue") ;
                   less.setVisible(true);
				   var gt = liger.get("e.greatValue") ;
                   gt.setVisible(true);
                }else if(currentParam['valueType']==2){
                     var table=liger.get("e.refTable") ;
                     table.setVisible(true);//
                     var fname=liger.get("e.refFieldName") ;
                     fname.setVisible(true);
                     var field=liger.get("e.refTabField") ;
                     field.setVisible(true);
                     var pfield=liger.get("e.refTabParField") ;
                     pfield.setVisible(true);
        
               }else if(currentParam['valueType']==3){
                 	$("#grid1005").attr("style","margin: 0px; padding: 0px; width: 99%;");
               }
		   }
	          		      
	  	});




  	 
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
