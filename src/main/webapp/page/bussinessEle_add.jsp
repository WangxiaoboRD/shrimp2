<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
    <script type="text/javascript" src="../ligerUI/ligerUI/js/plugins/XGrid.js"></script>    
	<script src="../ligerUI/ligerUI/js/template/ligerTemplate.js" type="text/javascript"></script>
	<script src="../ligerUI/ligerUI/js/template/ligerXEFormGridSave.js" type="text/javascript"></script>
	<script type="text/javascript" src="../ligerUI/ligerUI/js/pap.js"></script>
	<script type="text/javascript" src="../ligerUI/ligerUI/js/core/ligerAjax.js"></script>
    <script type="text/javascript" src="../ligerUI/ligerUI/js/json2.js"></script>
   <script type="text/javascript">
        var grid = null;
	  var v ;
	  var form;
	  var g;
	  $(function(){
        var selectnum=0;
        var groupicon = "../ligerUI/ligerUI/skins/icons/communication.gif";

        var toolBarOption = { 
                   items: [
                		{ text: '保存', click: saveClick, icon: 'add' }
            		]
      	};

        var temp = null;

       var formoption={    
                url:'bussiness_ele!save',
                labelWidth: 75,
                fields:[
                        { display: "参考元素", name:"e.refCode.ecode",type:"text",group: "基础信息", groupicon: groupicon},
                        { display: "类别", name:"e.classType",type:"text"},
                        { display: "编码", name:"e.ecode",type:"text",validate:{required:true},newline:true},
                        { display: "名称", name:"e.ename",type:"text",validate:{required:true}},
                        { display: "数据类型", name:"e.dataType",type:"select",validate:{required:true},newline:true,options:{
        		              url:'bussiness_ele!loadDataType',
        		              initText:'',
        		              valueField: 'fieldcode', //关键项
               			      textField: 'fieldname',
               			      selectBoxHeight:110,
               			      keySupport:true,
               			      onSelected:function(newValue,newtext){
                                selectData(newtext);        	                     
        		             }			      
                        }},
                        { labelWidth: 1,name:"e.len",hidden:true,type:"int",width:100,options:{width:98,nullText:'长度'},validate:{required:true}},
                        { labelWidth: 1,name:"e.decimalLen",hidden:true,width:108,type:"int",options:{width:106 ,nullText:'小数点位数'},validate:{required:true}},
                        { display: "业务组件", name:"e.component",type: "select", newline: true,comboboxName: "testSelect", options:{
                           	  data: [
                         	        { text: '文本控件', id: 'text' },
         		                    { text: '日期控件', id: 'date' },
         		                    { text: '选择控件', id: 'select' },
         		                    { text: '数字控件', id: 'num' }],
         		              initValue:"text",
        		              selectBoxHeight:110
                         }},
                        { display: "参数", name:"e.param",type:"text"},
                        { display: "描述", name:"e.descs",type:"textarea",width:405,newline: true,options:{width:400}},
                        { labelWidth: 100, display: "是否记录日志", name: "e.flagLog", group: "日志标示", groupicon: groupicon, type: "checkbox", options: { checkedValue: 'Y', uncheckedValue: 'N' } },
                        { labelWidth: 10, name: "valuetype", type: "radiogroup", width: 300, newline: true, group: "值类型", groupicon: groupicon,
                            options:{
                               data:[
                                    {id: 0, name: '无'},
                                    {id: 1, name: '范围'},
                                    {id: 2, name: '参照表'},
                                    {id: 3, name: '固定值'}
                               ],
                               name: 'e.valueType',
                               rowSize: 5,
                               width: 280,
                               textField: 'name' ,
                               value: 0,
                               click: function() {
                                    var _ts=$(this);
                                    selectRadio(_ts);
                               }                       
                          }
                        },
                        { labelWidth: 13,hidden:true,newline: true,name:"e.lessValue",type:"text",width:100,options:{width:98,nullText:'最小值'}},
                        { labelWidth: 13,hidden:true,name:"e.greatValue",width:108,type:"text",options:{width:106,nullText:'最大值'}},
                        { labelWidth: 80,display: "参照表",hidden:true, newline: true,name:"e.refTable",type:"select",validate:{required:true},options:{
      		                  url:'base_table!loadType',
      		                  initText:'',
      		                  valueField: 'tabCode', //关键项
             			      textField: 'tabName',
             			      selectBoxHeight:110,
               			      onSelected:function(newValue,newtext){
                            	 selectField(newValue);
    		                 }			      
                        }
      		             },			      
                       { labelWidth: 80,hidden:true,display: "名称", name:"e.refFieldName",type:"select",validate:{required:true},options:{
	       		              initText:'',
	       		              id:'fieldName',
	       		              valueField: 'fdcode', //关键项
	              			  textField: 'descs',
	              			  selectBoxHeight:110
    		             }			      
                       },                       
                       { labelWidth: 80,hidden:true,display: "引用字段", name:"e.refTabField",type:"select",validate:{required:true},options:{
	       		              initText:'',
	       		              id:'refField',
	       		              valueField: 'fdcode', //关键项
	              			  textField: 'descs',
	              			  selectBoxHeight:110
	              	    }},
                       { labelWidth: 80,hidden:true,display: "父级字段", name:"e.refTabParField",type:"select",options:{
        		              initText:'',
        		              valueField: 'fdcode', //关键项
               			      textField: 'descs',
               			      id:'parField',
               			      selectBoxHeight:110		      
                       }}
         		 ]
          }

    	var gridoption={    		 
             columns:[
      				{ display:'编码',name:'dcode',editor: { type: 'text'}},
    				{ display:'值',name:'value',editor: { type: 'text'}},
    				{ display:'备用字段1',name:'spareField1',editor: { type: 'text'}},
    				{ display:'备用字段2',name:'spareField2',editor: { type: 'text'}}
            ],
            toolbar: {
                items: [
	                { text: '添加', click: addClick, icon: 'add' },
	                { line: true },	                
	                { text: '删除', click: deleteClick, icon:'delete'}
            ]}
     			
        }

  
      var glist=$.pap.createEFormGridSave({toolbar:toolBarOption,form:formoption,grid:gridoption});
      g=grid=glist.getGrid();
      form=glist.getForm();

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


	     var selectField=function(text){
	    	  
                  var url="base_table_column!loadField?e.baseTable.tabCode = "+text;
   			      var field=$("input[ligeruiid='parField']").ligerGetComboBoxManager();
   			      field.clear();
   			      field.setUrl(url);

   			      var reffield=$("input[ligeruiid='refField']").ligerGetComboBoxManager();
   			      reffield.clear();
   			      reffield.setUrl(url);
                  
   			      var fdname=$("input[ligeruiid='fieldName']").ligerGetComboBoxManager();
   			      fdname.clear();
   			      fdname.setUrl(url);


	     }

		 
	  	
		   var selectRadio=function(item){
			   var less=$("input[id='e.lessValue']").ligerGetTextBoxManager();
			   var great=$("input[id='e.greatValue']").ligerGetTextBoxManager();	
			   var grids=$("div[class='grid-pap']").children();
			   
			   less._setValue('');
			   great._setValue('');		   
			   less._setNullText();
			   great._setNullText();	   
			   if(item.val()==0){
				   less.setVisible(false);
				   great.setVisible(false);	
				   setComBoxVisible(false);
                   grids.attr("style","margin: 0px; padding: 0px; width: 99%;display: none;");
                   
			   }else if(item.val()=='1'){
				   grids.attr("style","margin: 0px; padding: 0px; width: 99%;display: none;");
				   less.setVisible(true);
				   great.setVisible(true);
				   setComBoxVisible(false);
				}else if(item.val()==2){
					   less.setVisible(false);
					   great.setVisible(false);	
					   setComBoxVisible(true);
				      grids.attr("style","margin: 0px; padding: 0px; width: 99%;display: none;");

			    }else if(item.val()==3){
					   less.setVisible(false);
					   great.setVisible(false);	
					   setComBoxVisible(false);
					   grids.attr("style","margin: 0px; padding: 0px; width: 99%;");
			    }

   		  }

	      var setComBoxVisible=function(fg){

	    	var classcss="";
            if(fg){
                classcss="l-fieldcontainer l-fieldcontainer-first";
		    }else{
                classcss="l-fieldcontainer l-hidden";
	        }
               
            var refTab=$("li[fieldindex='14']");
            refTab.attr("class",classcss);
            var refTabField=$("li[fieldindex='15']");
            refTabField.attr("class",classcss);
            var refTabParField=$("li[fieldindex='16']");
            refTabParField.attr("class",classcss);
            var refTabParFieldName=$("li[fieldindex='17']");
            refTabParFieldName.attr("class",classcss);
		 }



	   		  
			v = form.validateForm();  
			var grids=$("div[class='grid-pap']").children();				
			grids.attr("style","margin: 0px; padding: 0px; width: 99%;display: none;");     
         });



            
      
        //---添加
        var addClick = function(item){
            var i=0;
            grid.addRow({
                 dcode:'',
                 value:'',
                 spareField1: '',
                 spareField2: ''
             },i++,false);
 	    }
   	
	    //--删除
	    var deleteClick = function(item){
	    	if( grid.selected.length > 0 ){
		    	grid.deleteSelectedRow();
	    	}else{
	    		$.ligerDialog.warn('你没有选择任何要删除的记录！')
	    	}
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
			var grid = g ;
			var p =  g.options ;
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
				 return callBackDetails ; 
			}else{
			    return {};
			}
		}  	 



      var saveClick=function(){

	    	var data = onSave();
	    	if(data!=null){
    			$.ligerui.ligerAjax({
					type:"POST",
					async: false,
					url:"bussiness_ele!save",	
					data:data,
					dataType:"text",
					beforeSend:function(){},
					success:function(result, textStatus){
						if(result != null && result !=""){
							tips = $.ligerDialog.tip({ title: '提示信息',modal: true, content: '保存成功！' });
							window.setTimeout(function(){ 
								tips.close();
								$.pap.removeTabItem() ;
							} ,2000);
						}
					},
					error: function(XMLHttpRequest,textStatus){
							$.ligerDialog.error('操作出现异常');
					},
					complete:function(){grid.loadData(true);}
				});    			
    		}
          
 
      }

  
   </script>
 
</head>

<body>

</body>
</html>
