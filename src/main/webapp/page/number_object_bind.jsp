<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="s" uri="/struts-tags" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="content-type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
	<meta content='width=330, height=400, initial-scale=1' name='viewport' />
	<link rel="icon" href="favicon.ico" type="../image/x-icon" />
	<title>号码对应业务对象绑定</title>
	<link href="../ligerUI/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
	<link href="../ligerUI/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />	
    <script src="../ligerUI/jquery/jquery-1.6.4.js" type="text/javascript"></script>
	<script src="../ligerUI/jquery-validation/jquery.form.js" type="text/javascript"></script> 
	<script src="../ligerUI/jquery-validation/jquery.validate.js" type="text/javascript"></script> 
    <script src="../ligerUI/jquery-validation/jquery.metadata.js" type="text/javascript"></script>
    <script src="../ligerUI/jquery-validation/messages_cn.js" type="text/javascript"></script>    
    <script src="../ligerUI/ligerUI/js/ligerui.all.js" type="text/javascript"></script>       
	<script src="../ligerUI/ligerUI/js/template/ligerTemplate.js" type="text/javascript"></script>
	<script src="../ligerUI/ligerUI/js/template/ligerXFormGridList.js" type="text/javascript"></script>
	<script type="text/javascript" src="../ligerUI/ligerUI/js/pap.js"></script>
	
    <script type="text/javascript">
        var grid = null;
        var form = null;
        var currentPage = $.pap.getOpenPage(window);
  	    var currentParam = currentPage.getParam("param");
  	    
  	    $(f_initGrid); 
        function f_initGrid(){
 			var toolBarOption = { 
 	              items: [
                  		{ text: '绑定', click: settingClick,disabled:true ,expression:'!=1',icon: 'settings'}
                  ]
            };
	  		var formOptions = {
	  			 inputWidth: 150, 
           		 labelWidth: 60, 
           		 space:20,
	  			 fields: [
	                { display:'业务对象', name:"objectName",newline: true, width:90,type: "text" ,options:{ value:currentParam['bussName'],readonly:true }} ,
	 				{ display:'业务对象', name:"objectCode",hidden:true,type: "text" ,options:{ value:currentParam['bussCode']}},
	 				{ display:'关键属性', name:"hingeKey", type: "select", comboboxName: "hingeKey2", options:{
	                	  //1.直接填写数据
	                	  url:"bussiness_object!loadType?id="+currentParam['bussCode']+"",
			              valueField: 'id', 
					      textField: 'fdcode',
			              selectBoxHeight:180,
			              keySupport:true,
			              onSelected: function (newvalue){
			                   
			                    $.ligerui.ligerAjax({
										url:'bussiness_object!loadTypeValue',
										dataType:'json',
										data:{'id':currentParam['bussCode']},
										success:function(_data,textStatus){
											
											var newData = new Array();
			                    			var _hingeKey = $("input[id='hingeValue2']").ligerGetComboBoxManager();
											if(_data != ''){
												for (i = 0; i < _data.Rows.length; i++){
							                        if (_data.Rows[i].baseColumnId == newvalue){
							                            newData.push(_data.Rows[i]);
							                        }
							                    }
							                    _hingeKey.setData(newData);
											}
										},
										error: function(XMLHttpRequest,textStatus){
										    alert("错误代码执行");
										},
										complete: function(){}
								});
			              }
                	}},
                	{ display :'关键值',name:'hingeValue',type:'select',comboboxNameSubmit:true,comboboxName:'hingeValue2',
				  		options:{
				  		//url:"bussiness_ele_detail!loadType?id="+currentParam['markSub.ecode']+"",
		        		data:null,
		        		valueField: 'value', 
					    textField: 'value',
		        		isMultiSelect: false, 
		        		isShowCheckBox: false,
		        		onBeforeOpen: function(){
		                   var _hingeKey = $("input[id='hingeKey2']").ligerGetComboBoxManager().getValue();
			        	    if( !_hingeKey){
			        	    	$.ligerDialog.warn('请先选择关键属性！');
			        	    	return false ;
		                    }
		                }
				    }}
               	 ],
              	 buttons:[]		
            }
	  		var gridOptions = {
	  			 url:'number_detail!loadByEntity', // 加载明细的url
	  			 parms:{'e.number.id':currentParam['id']}, // 发送url时附加的参数
	  			 delayLoad:false,
	  			 title:'号码对象: '+currentParam['numberName']+' >明细列表',
	  			 columns: [
	  			 	{display:'明细编码',name:'id',width:70,hide:true},
    				{display:'范围段',name:'numberScope',width:70},
    				{display:'前缀',name:'prefix',width:70},
    				{display:'年份',name:'year',width:70},
    				{display:'子对象',name:'subobject',width:70},
    				{display:'开始值',name:'startNumber',width:70},
    				{display:'结束值',name:'endNumber',width:70},
    				{display:'当前值',name:'currentNumber',width:70},
    				{display:'创建日期',name:'createDate',width:100},
    				{display:'外部标示',name:'markExt',width:70,render:function(row){
    					if(row.markExt=='N'){return "内部"};
    					if(row.markExt=='Y'){return "外部"};
    				}}
                ]
	  		};
	  		var page = $.pap.createFormGridList({toolbar:toolBarOption,form: formOptions,grid: gridOptions});
	  		form = page.getForm();
	  		v= form.validateForm();
	  		grid = page.getGrid();
	  		
	  		//业务对象没有关键属性
	  		$.ligerui.ligerAjax({
					url:'number_config!loadType',
					dataType:'text',
					data:{'e.objectCode':currentParam['bussCode'],'e.numberDetail.number.id':currentParam['id']},
					async:false,
					beforeSend:function(XMLHttpRequest){
				  		//如果号码对象没有子对象标示，不用显示关键属性
				  		if(currentParam['ecode'] == ''){
			  				$("input[name='hingeKey']").parent().parent().parent().css("display","none");
				    		$("input[name='hingeValue']").parent().parent().parent().css("display","none");
	  						return false;
	  					}
					},
					success:function(_data,textStatus){
						if(_data == 'false'){
							$("input[name='hingeKey']").parent().parent().parent().css("display","none");
		    				$("input[name='hingeValue']").parent().parent().parent().css("display","none");
						}
					},
					error: function(XMLHttpRequest,textStatus){
					    $.ligerDialog.warn("错误代码执行");
					},
					complete: function(){}
			});
	  		
        } 
        
        //绑定
        var settingClick = function(item){

        	var _hingeKeyPost = $("input[name='hingeKey']").val();
		    var _hingeValuePost = $("input[name='hingeValue']").val();
        	var _bussCodePost = currentParam['bussCode'];
        	var _bussNamePost = currentParam['bussName'];
        	var _id = grid.selected[0]['id'];
        	var _subobject = grid.selected[0]['subobject'];

        	$.ligerui.ligerAjax({
					url:'number_config!save',
					dataType:'text',
					data:{'e.objectCode':_bussCodePost,'e.objectName':_bussNamePost,'e.hingeKey':_hingeKeyPost,'e.hingeValue':_hingeValuePost,'e.numberDetail.id':_id,'e.numberDetail.subobject':_subobject,'e.numberDetail.number.id':currentParam['id']},
					success:function(_data,textStatus){
						if(_data != ''){
							tip = $.ligerDialog.tip({ title: '提示信息', content: '保存成功！' });
							window.setTimeout(function(){tip.close();$.pap.removeTabItem();} ,2000); 	
						}
					},
					error: function(XMLHttpRequest,textStatus){
					     $.ligerDialog.warn("错误代码执行");
					},
					complete: function(){}
			});
        }
    </script>
</head>
<body>
</body>
</html>

