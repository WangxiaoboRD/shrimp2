/**
 * jQuery ligerUI 1.2.0
 * 
 * http://ligerui.com
 *  
 * Author daomi 2013 [ gd_star@163.com ] 
 * 
 */
(function($) {
	$.fn.ligerXEFormGridEdit = function(options) {
		return $.ligerui.run.call(this, "ligerXEFormGridEdit", arguments);
	};

	$.fn.ligerGetXEFormGridEditManager = function() {
		return $.ligerui.run.call(this, "ligerGetXEFormGridEditManager", arguments);
	};

	$.ligerDefaults.XEFormGridEdit = {
		toolbar:{},
		from:{},
		grid:{}
	};

	$.ligerMethos.XEFormGridEdit = $.ligerMethos.XEFormGridEdit || {};

	$.ligerui.controls.XEFormGridEdit = function(element, options) {
		$.ligerui.controls.XEFormGridEdit.base.constructor.call(this, element, options);
	};
	
	$.ligerui.controls.XEFormGridEdit.ligerExtend($.ligerui.controls.Template,{
			__getType : function() {
				return 'XEFormGridEdit';
			},
			__idPrev : function() {
				return 'XEFormGridEdit';
			},
			_extendMethods : function() {
				return $.ligerMethos.XEFormGridEdit;
			},
			_preRender : function() {
				var g = this, p = this.options;
				// 处理 可编辑表格的配置项信息
				if(p.grid){
					var baseGrid = {
						 delayLoad : false,
						 detailsIsNotNull:false,// 此属性配置grid表格中的数据提交时不能为空
						 title:'',
		  			     enabledEdit: true,
		  			     usePager:false,
		  			     checkbox: true,
	                     rownumbers:true
	                };
					p.grid = $.extend({}, baseGrid, p.grid );
				}
				var addClick  = function(item){ alert(item.text) };
				 //如果 form中配置了 url 属性
				 if( p.form.url ){
					 var ligerForm = null, formElement = null ,
						 v = null;
					 var onSave = function(){
						        ligerForm = g.getForm() ;
						        formElement = ligerForm.element ;
						    	v = ligerForm.validateForm(),
						  		callBackDetails = {};
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
						    var otherdata=g.appendOtherData();//zp添加  TODO
					    	if(!$.isEmptyObject(data)){
							    if(!$.isEmptyObject(otherdata)){
							    	$.extend(data,otherdata);
							    }
								$.ligerui.ligerAjax({
									url: p.form.url,
									dataType: "text",
									data: data,
									beforeSend: function(){
										showMask();
									},
									success:function(result,textStatus){
										if(result != null && result !=""){
											var selectedTabid = window.name || window.id ;
											//$.pap.removeTabItem() ; // 关闭当前tab页签
											tips = $.ligerDialog.tip({ title: '提示信息',modal: true, content: '修改成功！' });
											window.setTimeout(function(){ 
												tips.close();
												$.pap.removeTabItem() ;
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
							  { text: '保存', click: addClick , icon: 'save'}
						  ]};
					 }
					 
					 // 表格会根据分组按钮收缩/展开自动调整高度
				    $(".togglebtn").live('click', function (){
				    	g.getGrid().setHeight(g.getGrid().options.height);
				    });
				 }else{
					  alert("使用ligerXEFormGridEdit模板必须在form配置对象中添加url属性");
			          throw new Error("使用ligerXEFormGridEdit模板必须在form配置对象中添加url属性");
				 }
				
			},
			_init : function() {
				 $.ligerui.controls.XEFormGridEdit.base._init.call(this);
			},
			_render: function(){
				$.ligerui.controls.XEFormGridEdit.base._render.call(this);
			}
   });

})(jQuery);