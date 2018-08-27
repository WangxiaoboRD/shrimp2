/**
 * 
 */
(function($) {
	$.fn.ligerXEFormGridSave = function(options) {
		return $.ligerui.run.call(this, "ligerXEFormGridSave", arguments);
	};

	$.fn.ligerGetXEFormGridSaveManager = function() {
		return $.ligerui.run.call(this, "ligerGetXEFormGridSaveManager", arguments);
	};

	$.ligerDefaults.XEFormGridSave = {
		toolbar:{},
		from:{},
		grid:{}
	};

	$.ligerMethos.XEFormGridSave = $.ligerMethos.XEFormGridSave || {};

	$.ligerui.controls.XEFormGridSave = function(element, options) {
		$.ligerui.controls.XEFormGridSave.base.constructor.call(this, element, options);
	};
	
	$.ligerui.controls.XEFormGridSave.ligerExtend($.ligerui.controls.Template,{
			__getType : function() {
				return 'XEFormGridSave';
			},
			__idPrev : function() {
				return 'XEFormGridSave';
			},
			_extendMethods : function() {
				return $.ligerMethos.XEFormGridSave;
			},
			_preRender : function() {
				var g = this, p = this.options;
				// 处理 可编辑表格的配置项信息
				if(p.grid){
					var baseGrid = {
						 detailsIsNotNull:false,// 此属性配置grid表格中的数据提交时不能为空
						 title:'',
						 frozen:false,
		  			     enabledEdit: true,
		  			     usePager:false,
		  			     checkbox: true,
	                     rownumbers:true  } ;
					p.grid = $.extend({}, baseGrid, p.grid );
				}
				var addClick  = null,addClickadd = null;
				 //如果 form中配置了 url 属性
				 if( p.form.url ){
					 var ligerForm = null, 
					     formElement = null ,
						 v = null;
					 var onSave = function(){
						        ligerForm = g.getForm() ;
						        formElement = ligerForm.element ;
						    	v = ligerForm.validateForm();
						  		var callBackDetails = {};
								$(formElement).submit();
								if( v.valid() ){
									//var gridDetails = g.getGrid().getEditGridDetails(); // 仅支持单表头，不支持多表头
									var gridDetails = g.getGrid().getMultiHeaderEditGridDatas(); // 支持多表头，兼容单表头
							  		if(p.grid.detailsIsNotNull && $.isEmptyObject(gridDetails) ){
							  			$.ligerDialog.warn("明细不能为空！") ;
							  			return callBackDetails ;
							  		}
									if(!$.isEmptyObject(gridDetails)){
										$.extend(callBackDetails,gridDetails);
									}
									var formData = $(formElement).formToArray();
									$.each(formData,function(i,item){
									     if(item.name){
									        callBackDetails[item.name ] = item.value ||"";
									     }
									});
									return  callBackDetails ;
								}else{
									return callBackDetails;
								}
					 }//end of onSave
					 // 显示请求遮罩
					 var showMask = function() {
					 	if ($("#loading")) {
							$("#loading").show();
						}
						if ($.ligerDialog) {
							$.ligerDialog.waitting('系统正在处理，请稍候......');
						}
					 }
					 // 隐藏请求遮罩
					  var hideMask = function() {
					 	if( $.ligerDialog ){
							$.ligerDialog.closeWaitting();
						}
						if ($("#loading")) {
							$("#loading").hide();
						}
					 }
					 addClick = function(item){
						    var data = onSave();
					    	if(!$.isEmptyObject(data) ){
								$.ligerui.ligerAjax({
									url: p.form.url,
									dataType: "text",
									data: data,
									beforeSend: function(){
										showMask();
									},
									success:function(result,textStatus){
										if(result != null && result !=""){
											tips = $.ligerDialog.tip({ title: '提示信息',modal: true, content: '保存成功！' });
											window.setTimeout(function(){ 
												tips.close();
												$.pap.removeTabItem();
											} ,2000);
											/*
											var selectedTabid = window.name || window.id ;
											$.ligerDialog.success("操作成功！");
											$.pap.removeTabItem() ; // 关闭当前tab页签
											* */
										}
									},
									complete: function() {
										hideMask();
									}
								});
				    		}
					 } // end of addClick 
					 
					  addClickadd = function(item){
						    var data = onSave();
					    	if(!$.isEmptyObject(data) ){
								$.ligerui.ligerAjax({
									url: p.form.url,
									dataType: "text",
									data: data,
									beforeSend:  function(){
										showMask();
									},
									success:function(result,textStatus){
										if(result != null && result !=""){
											tips = $.ligerDialog.tip({ title: '提示信息',modal: true, content: '保存成功！' });
											window.setTimeout(function(){ 
												tips.close();
												
												// 重置表单
												var excludeClearFields = ligerForm.options.excludeClearFields;
												if (!excludeClearFields) {
													excludeClearFields = true;
												}
												$(formElement).clearForm(excludeClearFields); // 重置表单，可配置清空，具体方法使用请参见jquery.form.js文件
						                    	
												// 清空组合框选中样式
						                    	$.each(ligerForm.options.fields, function(index, field) {
						                    		if (field['type'] == "select") {
						                    			var fieldId = field['name'];
							                    		if (field.options['tree']) {
							                    			fieldId = field['comboboxName'];
								                    	}
							                    		var selectCombo = $("input[id='" + fieldId + "']");
						                    			if (selectCombo.val() == "") {
						                    				$(".l-selected", this.selectCombo).removeClass("l-selected");
						                    			}
						                    		}
						                    	});
												// 清空表格
						                    	$.each(g.getGrid().records, function() {
						                    		g.getGrid().deleteRow(this);
						                    	});
						                    	
											} ,2000);
										}
									},
									complete: function() {
										hideMask();
									}
								});
				    		}
					 } 
					 if($.isEmptyObject(p.toolbar)){
						  p.toolbar = { items:[
							  { text: '保存', click: addClick , icon: 'save'},
							  { text: '保存并继续', click: addClickadd ,icon:'saveandnext'}
						  ]};
					 }
					 
					 // 表格会根据分组按钮收缩/展开自动调整高度
					 $(".togglebtn").live('click', function (){
				    	g.getGrid().setHeight(g.getGrid().options.height);
				     });
				 }else{
					  alert("使用ligerXEFormGridSave模板必须在form配置对象中添加url属性");
			          throw new Error("使用ligerXEFormGridSave模板必须在form配置对象中添加url属性");
				 }
				
			},
			_init : function() {
				 $.ligerui.controls.XEFormGridSave.base._init.call(this);
			},
			_render: function() {
				$.ligerui.controls.XEFormGridSave.base._render.call(this);
			}
			
   });

})(jQuery);