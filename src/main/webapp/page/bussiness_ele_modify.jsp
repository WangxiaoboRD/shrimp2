<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    
    <title>业务元素编辑</title>
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
	  var mangers;
	  var delIds="";
	  var selectnum=0;
      var tableradio=0;	  
	  var radio=0;
	  
	  $(function(){
		   
	        var groupicon = "../ligerUI/ligerUI/skins/icons/communication.gif";
		    var currentPage = $.pap.getOpenPage(window);
		  	var currentParam = currentPage.getParam("param")[0];	        
	        var toolBarOption = { 
                   items: [
                		{ text: '保存', click: saveClick, icon: 'add' }
            		]
	      	};
			  	
		  	
	        var formoption={
	                  url:'bussiness_ele!modify',
	                  labelWidth: 75,
	                  fields:[
                          { display: "参考元素", name:"e.refCode.ecode",type:"text",attr:{value:'${e.refCode.ecode}'},group: "基础信息", groupicon: groupicon},
                          { display: "类别", name:"e.classType",type:"text",attr:{value:'${e.classType}'}},
                          { display: "编码", name:"e.ecode",type:"text",validate:{required:true},newline:true,options:{value:'${e.ecode}', readonly:'readonly'}},
                          { display: "名称", name:"e.ename",type:"text",validate:{required:true},options:{value:'${e.ename}'}},
                          { display: "数据类型", name:"e.dataType",type:"select",validate:{required:true},newline:true,options:{
          		              url:'bussiness_ele!loadDataType',
          		              valueField: 'fieldcode', //关键项
                 			  textField: 'fieldname',
                 			  initValue:'${e.dataType}',
                 			  keySupport:true,
                 			  selectBoxHeight:110,
                 			  onSelected:function(newValue,newtext){
                     			  if(selectnum==0){
                                      selectnum=1;
                         	      }else{
                                      selectData(newtext);
                             	   } 	                     
          		               }			      
                          }},
                          { labelWidth: 1,name:"e.len",hidden:true,type:"int",width:100,options:{width:98,nullText:'长度'},attr:{value:'${e.len}'},validate:{required:true}},
                          { labelWidth: 1,name:"e.decimalLen",hidden:true,width:108,type:"int",options:{width:106 ,nullText:'小数点位数'},attr:{value:'${e.decimalLen}'},validate:{required:true}},
                          { display: "业务组件", name:"e.component",type: "select", newline: true,comboboxName: "testSelect", options:{
                             	  data: [
                           	        { text: '文本控件', id: 'text' },
           		                    { text: '日期控件', id: 'date' },
           		                    { text: '选择控件', id: 'select' },
           		                    { text: '数字控件', id: 'num' }],
           		              	 initValue:"${e.component}",
           		           	     keySupport:true,
          		                 selectBoxHeight:110
                           }},
                          { display: "参数", name:"e.param",type:"text",attr:{value:'${e.param}'}},
                          { display: "描述", name:"e.descs",type:"textarea",width:405,newline: true,options:{width:400},attr:{value:'${e.descs}'}},
                          { labelWidth: 100,display: "是否记录日志", name:"e.flagLog",group: "日志标示",groupicon: groupicon,type:"checkbox", options: { value: '${e.flagLog}', checkedValue: 'Y', uncheckedValue: 'N' }},
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
                                 click:function(){
                                      var _ts=$(this);
                                      selectRadio(_ts);
                                 }                       
                            }
                          },
                          { display: "参照表", name:"e.refTable",hidden:true, type:"select",validate:{required:true},newline:true,options:{
          		              url:'base_table!loadType',
          		              valueField: 'tabCode', //关键项
                 			  textField: 'tabName',
                 			  initValue:'${e.refTable}',
                 			  selectBoxHeight:110,
                 			  onSelected:function(newValue,newtext){
                     			  if(tableradio==0){
                     				 tableradio=1;
                         	      }else{                                     
                                      loadComboxValue(newValue);
                             	   } 	                     
          		               }			      
                          }},
                          { hidden:true,display: "名称", name:"e.refFieldName",type:"select",validate:{required:true},options:{	       		             
 	       		              initValue:'${e.refFieldName}',
 	       		              id:'fieldName',
 	       		              valueField: 'fdcode', //关键项
 	              			  textField: 'descs',
 	              			  selectBoxHeight:110
     		               }			      
                         },                       
                         {hidden:true,display: "引用字段", name:"e.refTabField",type:"select",validate:{required:true},options:{
	       		            //  url:'bussiness_ele!loadJsonType',
	       		              initValue:'${e.refTabField}',
	       		              id:'refField',
	       		              valueField: 'fdcode', //关键项
	              			  textField: 'descs',
	              			  selectBoxHeight:110
       		              }			      
                         },
                         { hidden:true,display: "父级字段", name:"e.refTabParField",type:"select",options:{
       		              //url:'bussiness_ele!loadJsonType',
	       		              initValue:'${e.refTabParField}',
	       		              valueField: 'fdcode', //关键项
              			      textField: 'descs',
              			      id:'parField',
              			      selectBoxHeight:110
	       		             }			      
	                     },
                         { labelWidth: 13,hidden:true,newline: true,name:"e.lessValue",type:"text",width:100,options:{width:98,nullText:'最小值'},attr:{value:'${e.lessValue}'}},
                          { labelWidth: 13,hidden:true,name:"e.greatValue",width:108,type:"text",options:{width:106,nullText:'最大值'},attr:{value:'${e.greatValue}'}}
	         		]
	         }

				  
		       
	       	var gridoption={
	        		  url:'bussiness_ele_detail!loadByEntity',
	        		  parms:{'e.bussinessEle.ecode':currentParam['ecode']},
	                  columns:[
	                       { display:'ecode',name:'bussinessEle.ecode',hide:true},
	                       { display:'编码',name:'dcode',editor: { type: 'text'}},
	            		   { display:'值',name:'value',editor: { type: 'text'}},
	            		   { display:'备用字段1',name:'spareField1',editor: { type: 'text'}},
	            		   { display:'备用字段2',name:'spareField2',editor: { type: 'text'}}
	                  ],
	                  usePager: true,
	                  toolbar: {
	                       items: [
		                     { text: '添加', click: addClick, icon: 'add' },
		                     { line: true },	                
		                     { text: '删除', click: deleteRow, icon:'delete'}		                
		              ]}
	          }

	       
	        var  template=$.pap.createEFormGridEdit({toolbar:toolBarOption,form:formoption,grid:gridoption});
	        grid= template.getGrid();
	        form= template.getForm();
	        v= form.validateForm();
		        
		   var grids=$("div[class='grid-pap']").children();
		   grids.attr("style","margin: 0px; padding: 0px; width: 99%;display: none;");

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
			   if(currentParam['flagLog']=='Y'){
                   log._setChecked(true);
				}
		  }

		   var loadComboxValue=function(tabCode){
               //ligerComBox的clear方法没有清除隐藏域，注意
			  //   alert('hello');
	             var url="base_table_column!loadField?e.baseTable.tabCode = "+tabCode;
			      var fdname=$("input[ligeruiid='fieldName']").ligerGetComboBoxManager();
			      fdname.clear();
			      fdname.setUrl(url);
			      
			      var reft=$("input[ligeruiid='refField']").ligerGetComboBoxManager();
			      reft.clear();
			      reft.setUrl(url);

			      var parreft=$("input[ligeruiid='parField']").ligerGetComboBoxManager();
			      parreft.clear();
			      parreft.setUrl(url);
			      
		   }

	

		   if(currentParam['valueType']){
               if(currentParam['valueType']==1){
				   var less = liger.get("e.lessValue") ;
                   less.setVisible(true);
				   var gt = liger.get("e.greatValue") ;
                   gt.setVisible(true);
                }else if(currentParam['valueType']==2){
		            var refTab=$("li[fieldindex='12']");
		            refTab.attr("class","l-fieldcontainer l-fieldcontainer-first");
		            loadComboxValue(currentParam['refTable']);
		            var refTabName=$("li[fieldindex='13']");
		            refTabName.attr("class","l-fieldcontainer l-fieldcontainer-first");
		            var refTab=$("li[fieldindex='14']");
		            refTab.attr("class","l-fieldcontainer l-fieldcontainer-first");
		            var prefTab=$("li[fieldindex='15']");
		            prefTab.attr("class","l-fieldcontainer l-fieldcontainer-first");
		            
					grids.attr("style","margin: 0px; padding: 0px; width: 99%;display: none;"); 
                }else if(currentParam['valueType']==3){
                	grids.attr("style","margin: 0px; padding: 0px; width: 99%;");
               }
		   }
		        

           
	  	 var selectData=function(text){
			   var len=$("input[id='e.len']").ligerGetTextBoxManager();
			   var deci=$("input[id='e.decimalLen']").ligerGetTextBoxManager();		  
			   len.setVisible(false);
               len._setValue('');
               deci._setValue('');
			   len._setNullText();
			   deci._setNullText();
			   deci.setVisible(false); 
              if(text=='小数'||text=='货币'){                   
                  len.setVisible(true);
                  deci.setVisible(true);
               }else{
                   len.setVisible(true);
                   deci.setVisible(false);
                }
		 }


		   var selectRadio=function(item){
			   var less=$("input[id='e.lessValue']").ligerGetTextBoxManager();
			   var great=$("input[id='e.greatValue']").ligerGetTextBoxManager();	
			   less._setValue('');
			   great._setValue('');		   
			   less._setNullText();
			   great._setNullText();	   
			   var grids=$("div[class='grid-pap']").children();
			   if(item.val()==0){
				   less.setVisible(false);
				   great.setVisible(false);						   					      
				   grids.attr("style","margin: 0px; padding: 0px; width: 99%;display: none;");
				   setComBoxVisible(false);
			   }else if(item.val()=='1'){
				   grids.attr("style","margin: 0px; padding: 0px; width: 99%;display: none;");
				   less.setVisible(true);
				   great.setVisible(true);
				   setComBoxVisible(false);     
				}else if(item.val()==2){
					setComBoxVisible(true);
					grids.attr("style","margin: 0px; padding: 0px; width: 99%;display: none;");
			    }else if(item.val()==3){
				   less.setVisible(false);
				   great.setVisible(false);	
				   grids.attr("style","margin: 0px; padding: 0px; width: 99%;");
				   setComBoxVisible(false);
			    }
   		  }


		      var setComBoxVisible=function(fg){
			    	var classcss="";
		            if(fg){
		                classcss="l-fieldcontainer l-fieldcontainer-first";
				    }else{
		                classcss="l-fieldcontainer l-hidden";
			        }
		               
		            var refTab=$("li[fieldindex='12']");
		            refTab.attr("class",classcss);

		            var refTabName=$("li[fieldindex='13']");
		            refTabName.attr("class",classcss);

		            var preft=$("li[fieldindex='14']");
		            preft.attr("class",classcss);

		            var preftars=$("li[fieldindex='15']");
		            preftars.attr("class",classcss);
		            

		   }

		   v = form.validateForm();  
		 
	  });


      var addClick=function(){
          var i=0;
          grid.addRow({
          	 'bussinessEle.ecode':'',
               dcode:'',
               value:'',
               spareField1: '',
               spareField2: ''
           },i++,false);

      }

      function deleteRow()
      {   
    	  var selRows = grid.getSelecteds();
          if (selRows.length == 0) {
       		$.ligerDialog.error('请选择要删除的记录！');
			 	return;
        	}
          $(selRows).each(function(index, data){
            	 // 记录删除的明细dcode
            	 if (data['dcode']) {
 					delIds += data['dcode'] + ",";
                  }
          });
		  //删除选中的行
          grid.deleteSelectedRow(); 
      }
  


      var  check=function(){

          var len=$("input[id='e.len']").val();
          if(len.length>0){
              var ss=parseInt(len);
              var sdf=isNaN(ss);
              if(isNaN(parseInt(len))){
            	  $("input[id='e.len']").val('');
                }
           }

          var deci=$("input[id='e.decimalLen']").val();
          if(deci.length>0){
              if(isNaN(parseInt(deci))){
            	  $("input[id='e.decimalLen']").val('');
              }
           }

          var less=$("input[id='e.lessValue']").val();
          if(less.length>0){
              if(isNaN(parseInt(less))){
            	  $("input[id='e.lessValue']").val('');
              }
           }
          var gt=$("input[id='e.greatValue']").val();
          if(gt.length>0){
              if(isNaN(parseInt(gt))){
            	  $("input[id='e.greatValue']").val('');
              }
           }

       }



   	    

        var onSave = function(){
			$("form").submit();			
			var p =  grid.options ;
			var columns = p.columns||[] ;
			var prefix = p.submitDetailsPrefix || 'e.details' ;
			var callBackDetails =  {} ; //[] ;
			$.each(grid.getData(),function(i,record){
				var callBackDetail ={} ;
				$.each(columns,function(j,column){
					var columnname = column.name ;
					if(columnname){
						callBackDetails [prefix+'['+i+'].'+columnname] = record[columnname]||'' ;
					}
				});
			});
		
			if( v.valid() ){
		        check();
				var formData = $('form').formToArray();
				$.each(formData,function(i,item){
				     if(item.name){
				        callBackDetails[item.name ] = item.value ||"";
				     }
				});

				callBackDetails['e.tempStack.delIds']=delIds;
				 return callBackDetails ; 
			}else{
			    return {};
			}
		}  	 

        var saveClick=function(){
            var data=onSave();
            if(null!=data){
    			$.ligerui.ligerAjax({
    				type:"POST",
    				async: false,
    				url:"bussiness_ele!modify",
    				data:data,
    				dataType:"text",
    				beforeSend:function(){},
    				success:function(data, textStatus){
    					if(data == 'MODIFYOK'){
    						//$.ligerDialog.success("操作成功！");
    						tip = $.ligerDialog.tip({ title: '提示信息', content: '修改成功！' });
    						window.setTimeout(function(){
        						 tip.close();
        						 $.pap.removeTabItem() ;
        					} ,2000); 	    						
    					}
    					
    				},
    				error: function(XMLHttpRequest,textStatus){
    				    alert("错误代码执行");
    				},
    				complete: function(){ grid.loadData(true);}
    			});
             }					
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
